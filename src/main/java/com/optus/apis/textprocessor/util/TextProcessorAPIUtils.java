package com.optus.apis.textprocessor.util;

/**
 * Utility methods.
 *
 */
public class TextProcessorAPIUtils {
	
	public static boolean doesStringValueExists(String value) {
		if (value != null && value.trim().length() > 0) {
			return true;
		}
		return false;
	}

}
