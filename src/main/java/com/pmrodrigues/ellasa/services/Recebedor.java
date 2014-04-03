package com.pmrodrigues.ellasa.services;

public class Recebedor {

	public Recebedor(final String email, final String key) {
		super();
		this.EMAIL = email;
		this.API_KEY = key;
	}

	private final String API_KEY;

	private final String EMAIL;

	public String getAPI_KEY() {
		return API_KEY;
	}

	public String getEMAIL() {
		return EMAIL;
	}

}
