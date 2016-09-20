package com.starterkit.javafx.dataprovider;

import java.util.Collection;

import com.starterkit.javafx.dataprovider.data.ProfileVO;
import com.starterkit.javafx.dataprovider.impl.DataProviderImpl;

/**
 * Provides data.
 *
 */
public interface DataProvider {

	/**
	 * Instance of this interface.
	 */
	DataProvider INSTANCE = new DataProviderImpl();

	/**
	 * Finds persons by their name, surname and login.
	 *
	 * @param login
	 *            string contained in login
	 * @param name
	 *            string contained in name
	 * @param surname
	 *            string contained in surname
	 * 
	 * @return collection of persons matching the given criteria
	 */
	Collection<ProfileVO> findProfiles(String login, String name, String surname);

	void deleteProfile(long id);

	void updateProfile(ProfileVO profile);
}
