package co.edu.udistrital.common.util.controller;

import java.io.File;
import java.nio.file.Files;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.common.util.ZyosCDNFTP;

@RestController
@RequestMapping("/manageFile")
public class ManageFileController {

	@RequestMapping(method = RequestMethod.GET, path = "/download")
	public File downloadFile(@RequestParam("fn") String fileName) {
		try {
			System.out.println("Vamos a devoolver aray bytes");
			String filePath = "co/10/communication/2019/5/" + fileName;
			File f = ZyosCDNFTP.downloadResource(filePath);
			byte[] test = Files.readAllBytes(f.toPath());
			System.out.println(test.length);
//			return Files.readAllBytes(f.toPath());
			return f;
		} catch (Exception e) {
			return null;
		}
	}

	@GetMapping(path = "/tesFile")
	public String testFile() {
		return "Found";
	}

}
