package co.edu.udistrital.structure.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.structure.enums.Role;
import co.edu.udistrital.structure.enums.State;

@Document(collection = "User")
public class User implements Serializable {

	public User() {
		// Basic Empty Constructor
	}

	@Id private String id;

	private String name;

	private String email;

	private String photo;

	private String mobilePhone;

	private String countryCode;

	private String langPreferences;

	private State state;

	private List<String> userContactIdList;

	@Transient private transient List<UserContact> userContactList;

	@Transient private transient UserLangPeferences userLangPreferences;

	private List<Role> roleList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLangPreferences() {
		return langPreferences;
	}

	public void setLangPreferences(String langPreferences) {
		this.langPreferences = langPreferences;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	public List<UserContact> getUserContactList() {
		return userContactList;
	}

	public void setUserContactList(List<UserContact> userContactList) {
		this.userContactList = userContactList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<String> getUserContactIdList() {
		return userContactIdList;
	}

	public void setUserContactIdList(List<String> userContactIdList) {
		this.userContactIdList = userContactIdList;
	}

	public UserLangPeferences getUserLangPreferences() {
		return userLangPreferences;
	}

	public void setUserLangPreferences(UserLangPeferences userLangPreferences) {
		this.userLangPreferences = userLangPreferences;
	}
	

}
