package co.edu.udistrital.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import co.edu.udistrital.common.util.ZyosCDNFTP;
import co.edu.udistrital.common.util.ZyosCDNResource;
import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.service.UserContactService;
import co.edu.udistrital.core.service.FileSystemStorageService;
import co.edu.udistrital.core.util.CoreConst;
import co.edu.udistrital.core.util.JsonUtil;
import co.edu.udistrital.structure.enums.Role;
import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;
import co.edu.udistrital.user.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserContactService userContactService;
	private final ResponseService responseService;
	private static final String DEFAULT_USER_PROFILE = "https://miedificio.co/cdn/dev/resources/web/co/10/communication/default_user_profile.png";

	@Autowired
	public UserService(@Lazy UserRepository userRepository, @Lazy UserContactService userContactService, @Lazy ResponseService responseService) {
		this.userRepository = userRepository;
		this.userContactService = userContactService;
		this.responseService = responseService;
	}

	/**
	 * Busca todos los objetos de tipo User que se encuentran disponibles en la base de datos
	 * 
	 * @author Angel
	 * @return Listado completo de Usuarios en la base de datos, si no hay ningún usuario devuelve
	 *         un listado vacío
	 */
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	private Optional<User> findOptionalById(String id) {
		return this.userRepository.findById(id);
	}

	public User findById(String id) {
		Optional<User> userOptional = findOptionalById(id);
		return userOptional.isPresent() ? userOptional.get() : new User();
	}

	public List<User> findByIdIn(List<String> userIdList) {
		return userRepository.findByIdIn(userIdList);
	}

	/**
	 * Permite consultar un usuario activo por medio del teléfono celular
	 * 
	 * @author Angel
	 * @param mobilePhone número de celular en formato String
	 * @return Un objeto de tipo User si hay alguna coincidencia con el número de celular de lo
	 *         contrario null
	 */
	public User findByMobilePhoneActive(String mobilePhone) {
		Optional<User> user = userRepository.findByMobilePhoneAndState(mobilePhone, State.ACTIVE);
		return user.isPresent() ? user.get() : null;
	}

	/**
	 * Permite consultar un usuario por medio del teléfono celular y estado
	 * 
	 * @author Angel
	 * @param mobilePhone número de celular en formato String
	 * @param state Estado del usuario que se pretende buscar
	 * @return Un objeto de tipo User si hay alguna coincidencia con el número de celular de lo
	 *         contrario null
	 */
	public User findByMobilePhoneAndState(String mobilePhone, State state) {
		Optional<User> user = userRepository.findByMobilePhoneAndState(mobilePhone, state);
		return user.isPresent() ? user.get() : null;
	}

	/**
	 * Permite consultar si un usuario existe en la plataforma por medio del email y/o el correo
	 * electrónico
	 * 
	 * @author Angel
	 */
	public boolean userActiveInDB(String mobilePhone) {
		User user = findByMobilePhoneActive(mobilePhone);
		return user != null;
	}

	@Transactional
	public boolean save(User user) {
		if (user == null)
			return false;
		this.userRepository.save(user);
		return true;
	}

	public List<User> loadByMobilePhoneActiveState(List<String> mobilePhoneList) {
		if (CollectionUtils.isEmpty(mobilePhoneList))
			return Collections.emptyList();
		return this.userRepository.loadByMobilePhoneActiveState(mobilePhoneList);
	}

	private List<UserContact> loadContactUserListFromMobileList(User user) {
		if (user == null || CollectionUtils.isEmpty(user.getUserContactList()))
			return Collections.emptyList();

		// Obtener el listado de teléfonos que envia el usuario de su libreta de contactos
		List<String> mobilePhoneList = user.getUserContactList().stream().map(u -> u.getUser().getMobilePhone()).collect(Collectors.toList());
		if (mobilePhoneList == null || mobilePhoneList.isEmpty())
			return Collections.emptyList();

		// Inicializar listadopara almacenamiento de los mismos
		List<UserContact> userListToAdd = new ArrayList<>(1);
		// Usuarios existentes en la base de datos que coinciden con los teléfonos de contacto del
		// usuario
		List<User> contactExistList = loadByMobilePhoneActiveState(mobilePhoneList);
		if (contactExistList != null && !contactExistList.isEmpty()) {
			UserContact uc = null;
			Optional<UserContact> userContactOptional = null;
			for (User u : contactExistList) {
				uc = new UserContact();
				uc.setUserId(u.getId());
				userContactOptional = user.getUserContactList().stream()
					.filter(item -> item.getUser().getMobilePhone().trim().equalsIgnoreCase(u.getMobilePhone())).findFirst();
				if (userContactOptional.isPresent())
					uc.setCustomName(StringUtils.isEmpty(userContactOptional.get().getCustomName()) ? userContactOptional.get().getUser().getName()
						: userContactOptional.get().getCustomName());
				userListToAdd.add(uc);
			}
		}
		return userListToAdd;
	}

	@Transactional
	public User userHomeRegister(User user) {
		List<Role> userRoleList = new ArrayList<>(1);
		userRoleList.add(Role.HOME);
		user.setRoleList(userRoleList);
		return userRegister(user, null);
	}

	@Transactional
	public User userSingleRegister(User user, MultipartFile file) {
		User userInDB = findByMobilePhoneActive(user.getMobilePhone());
		if (userInDB != null)
			return userInDB;
		List<Role> roleList = new ArrayList<>();
		roleList.add(Role.SINGLE);
		user.setRoleList(roleList);
		return userRegister(user, file);
	}

	private ZyosCDNResource getBasicCDNResource() {
		ZyosCDNResource resource = new ZyosCDNResource();
		resource.setIdEnterprise(10L);
		resource.setFunctionality("communication");
		resource.setDocument(true);
		return resource;
	}

	private String uploadUserPhoto(MultipartFile file) {
		if (file == null)
			return DEFAULT_USER_PROFILE;
		try {
			ZyosCDNResource resource = getBasicCDNResource();
			String ext = file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length());
			resource.setFileName(ZyosCDNFTP.getFileName(ext));
			resource.setInputStream(file.getInputStream());
			ZyosCDNFTP.uploadFileResource(resource);
			return ZyosCDNFTP.URI_HOST_MAIN + resource.getDatabasePath();
		} catch (Exception e) {
			e.printStackTrace();
			return DEFAULT_USER_PROFILE;
		}
	}

	private User userRegister(User user, MultipartFile file) {
		if (user == null)
			return null;

		List<String> userContactIdList = null;
		List<UserContact> userListToAdd = null;
		if (!CollectionUtils.isEmpty(user.getUserContactList())) {
			userListToAdd = loadContactUserListFromMobileList(user);
			if (!CollectionUtils.isEmpty(userListToAdd)) {
				this.userContactService.saveAll(userListToAdd);
				userContactIdList = userListToAdd.stream().map(UserContact::getId).collect(Collectors.toList());
			}
		}
		user.setUserContactList(null);
		if (!CollectionUtils.isEmpty(userContactIdList))
			user.setUserContactIdList(userContactIdList);
		user.setPhoto(uploadUserPhoto(file));
		user.setState(State.ACTIVE);
		save(user);
		return user;
	}

	@Transactional
	public ResponseEntity<Response> updateLanPreferece(User user) {
		if (user == null || user.getUserLangPreferences() == null || user.getUserLangPreferences().getLanguagePreferenceType() == null)
			ResponseEntity.ok().body(
				responseService.errorResponse("Actualizar preferencias", "Debe proporcionar los adatos básicos: UserId, LanguagePreference", false));
		User userUpdate = userRepository.findById(user.getId()).orElse(new User());
		if (StringUtils.isEmpty(userUpdate.getId()))
			ResponseEntity.ok()
				.body(responseService.errorResponse("Actualizar preferencias", "El usuario que proporcionó no existe en la base de datos", false));
		userUpdate.setLangPreferences(JsonUtil.asString(user.getUserLangPreferences()));
		userRepository.save(userUpdate);
		return ResponseEntity.ok().body(responseService.successResponse("Actualizar preferencias", "Las preferencias se actualizaron correctamente",
			user.getUserLangPreferences()));
	}

	public String getUserLanPreferece(String userId) {
		if (StringUtils.isEmpty(userId))
			return CoreConst.STRING_EMPTY;
		User user = userRepository.findById(userId).orElse(new User());
		return !StringUtils.isEmpty(user.getLangPreferences()) ? user.getLangPreferences() : CoreConst.STRING_EMPTY;
	}


}
