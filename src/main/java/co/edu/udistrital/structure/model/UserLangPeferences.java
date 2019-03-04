package co.edu.udistrital.structure.model;

import co.edu.udistrital.structure.api.LanguagePreferenceType;

public class UserLangPeferences {

	private LanguagePreferenceType languagePreferenceType;

	private LanPreference audioPref;

	private LanPreference textPref;

	private LanPreference videoPref;

	private LanPreference braillePref;

	public LanguagePreferenceType getLanguagePreferenceType() {
		return languagePreferenceType;
	}

	public void setLanguagePreferenceType(LanguagePreferenceType languagePreferenceType) {
		this.languagePreferenceType = languagePreferenceType;
	}

	public LanPreference getAudioPref() {
		return audioPref;
	}

	public void setAudioPref(LanPreference audioPref) {
		this.audioPref = audioPref;
	}

	public LanPreference getTextPref() {
		return textPref;
	}

	public void setTextPref(LanPreference textPref) {
		this.textPref = textPref;
	}

	public LanPreference getVideoPref() {
		return videoPref;
	}

	public void setVideoPref(LanPreference videoPref) {
		this.videoPref = videoPref;
	}

	public LanPreference getBraillePref() {
		return braillePref;
	}

	public void setBraillePref(LanPreference braillePref) {
		this.braillePref = braillePref;
	}


}
