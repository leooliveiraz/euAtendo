package rocha.euAtendo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SenhaUtil  implements PasswordEncoder {

	public static String criptografarSHA2(String texto) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(texto.getBytes("UTF-8"));		 
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
			String senhaCriptgrafada = hexString.toString();
			return senhaCriptgrafada;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
		  e.printStackTrace();
		  return "";
		}
	}
	
	@Override
	public String encode(CharSequence texto) {
		return criptografarSHA2(texto.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return(encodedPassword.equals(encode(rawPassword)));
	}
}
