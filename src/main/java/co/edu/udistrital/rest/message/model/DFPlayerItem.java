package co.edu.udistrital.rest.message.model;

import java.io.Serializable;

public class DFPlayerItem implements Serializable {

	public DFPlayerItem(int folder, int file, String word) {
		this.folder = folder;
		this.file = file;
		this.word = word;
	}

	// Nombre de folder que contiene el archivo
	private int folder;

	// Ubicación de
	private int file;

	private String word;

	public int getFolder() {
		return folder;
	}

	public void setFolder(int folder) {
		this.folder = folder;
	}

	public int getFile() {
		return file;
	}

	public void setFile(int file) {
		this.file = file;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
