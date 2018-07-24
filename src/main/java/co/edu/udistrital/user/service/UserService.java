package co.edu.udistrital.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.user.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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

}
