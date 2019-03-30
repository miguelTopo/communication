package co.edu.udistrital.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class ZyosCDNFTPTest {

	@Test
	public void uploadMp3File() {
		try {
			ZyosCDNResource resource = new ZyosCDNResource();
			resource.setIdEnterprise(10L);
			resource.setFunctionality("communication");
			File f = new File("D:/Music/Miguel/audiotest.mp3");
			resource.setFilePath("D:/Music/Miguel/audiotest.mp3");
			InputStream stream = new FileInputStream(f);
			resource.setInputStream(stream);
			resource.setFileName(ZyosCDNFTP.getFileName(".mp3"));
			resource.setDocument(true);
			ZyosCDNFTP.uploadFileResource(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void uploadMp4File() {
		try {
			ZyosCDNResource resource = new ZyosCDNResource();
			resource.setIdEnterprise(10L);
			resource.setFunctionality("communication");
			File f = new File("D:/Downloads/small.mp4");
			resource.setFilePath("D:/Downloads/small.mp4");
			InputStream stream = new FileInputStream(f);
			resource.setInputStream(stream);
			resource.setFileName(ZyosCDNFTP.getFileName(".mp4"));
			resource.setDocument(true);
			ZyosCDNFTP.uploadFileResource(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getExt() {
		String fileName = "fghjkk.mp4";
		System.out.println(fileName.substring(fileName.lastIndexOf('.'), fileName.length()));
	}

}
