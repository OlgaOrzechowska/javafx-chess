package com.starterkit.javafx.dataprovider.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Person data.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileVO {

	private Long id;
	private String login;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String aboutMe;
	private String lifeMotto;

	public ProfileVO() {
	}

	public ProfileVO(Long id, String login, String name, String surname, String email, String password, String aboutMe,
			String lifeMotto) {
		super();
		this.id = id;
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.aboutMe = aboutMe;
		this.lifeMotto = lifeMotto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getLifeMotto() {
		return lifeMotto;
	}

	public void setLifeMotto(String lifeMotto) {
		this.lifeMotto = lifeMotto;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "ProfileVO [id=" + id + ", login=" + login + ", name=" + name + ", surname=" + surname + ", email="
				+ email + ", aboutMe=" + aboutMe + ", lifeMotto=" + lifeMotto + "]";
	}

}
