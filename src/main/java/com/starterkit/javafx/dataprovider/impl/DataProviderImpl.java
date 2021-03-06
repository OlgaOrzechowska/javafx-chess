package com.starterkit.javafx.dataprovider.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.data.ProfileVO;

/**
 * Provides data.
 */
public class DataProviderImpl implements DataProvider {

	private static final String URL = "http://localhost:8090/user/";
	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	public DataProviderImpl() {
	}

	@Override
	public List<ProfileVO> findProfiles(String login, String name, String surname) {
		LOG.debug("Entering findProfiles()");

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<ProfileVO>> profilesResponse = restTemplate.exchange(
				URL + "search?login=" + login + "&name=" + name + "&surname=" + surname, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ProfileVO>>() {
				});
		List<ProfileVO> profiles = profilesResponse.getBody();

		LOG.debug("Leaving findPersons()");
		return profiles;
	}

	@Override
	public void deleteProfile(long id) {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.exchange(URL + id, HttpMethod.DELETE, null, String.class);
	}

	@Override
	public void updateProfile(ProfileVO profile) {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<ProfileVO> request = new HttpEntity<>(profile);
		restTemplate.exchange(URL, HttpMethod.PUT, request, new ParameterizedTypeReference<ProfileVO>() {
		});
	}

}
