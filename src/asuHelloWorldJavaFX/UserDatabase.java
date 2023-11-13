package asuHelloWorldJavaFX;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class UserDatabase {

	public UserDatabase() {

	}

	public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
		// Static getInstance method is called with hashing SHA
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		// digest() method called
		// to calculate message digest of an input
		// and return array of byte
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	public static String toHexString(byte[] hash) {
		// Convert byte array into signum representation
		BigInteger number = new BigInteger(1, hash);

		// Convert message digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));

		// Pad with leading zeros
		while (hexString.length() < 64) {
			hexString.insert(0, '0');
		}

		return hexString.toString();
	}

	public Boolean checkUserAuthentication(String userName, String password, File userD)
			throws NoSuchAlgorithmException {

		Scanner userReader;
		try {
			userReader = new Scanner(userD);

			while (userReader.hasNextLine()) {
				String data = userReader.nextLine();
				String[] infos = data.split(":");

				String id = infos[0];
				String pass = infos[1];
				 
				
				if (id.equals(userName) && toHexString(getSHA(password)).equals(pass)) {
					return true;
				}
			}
			userReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}

		return false;
	}

}