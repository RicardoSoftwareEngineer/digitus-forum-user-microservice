package com.digitusforum.user.util;

import org.springframework.security.crypto.encrypt.Encryptors;

public class Util {
	
	public static String encrypt(String textToEncrypt) {
		return Encryptors.text("password goes here", "8618d57d94674a78").encrypt(textToEncrypt);
	}
	
}
