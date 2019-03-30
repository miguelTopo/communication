package co.edu.udistrital.common.util;

import org.apache.commons.validator.routines.IntegerValidator;

public class ZyosFieldValidator {


	/**
	 * Method that validate if a value is integer number .
	 * 
	 * @param value string with the value
	 * @return true if is numeric
	 */
	public static boolean isInteger(String value) {
		try {
			if (value != null && !value.isEmpty())
				return IntegerValidator.getInstance().isValid(value);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


}
