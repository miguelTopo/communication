package co.edu.udistrital.core.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;
import org.springframework.stereotype.Component;

@Component
public class BundleMessage {

	@Test
	public void test() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("src/main/resources", Locale.ENGLISH);
			System.out.println(bundle.getString("core.yes"));			
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
