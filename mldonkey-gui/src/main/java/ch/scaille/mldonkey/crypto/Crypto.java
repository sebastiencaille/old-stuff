package ch.scaille.mldonkey.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Crypto {

	private static final int ITERATIONS = 1000;

	private final byte[] salt = new byte[16];

	public Crypto(File folder) {
		try {
			var saltFile = new File(folder, "salt");
			if (saltFile.exists()) {
				readSalt(saltFile);
			} else {
				createSalt();
				writeSalt(saltFile);
			}
		} catch (IOException | NoSuchAlgorithmException e) {
			throw new IllegalStateException("Cannot initialize crypto", e);
		}
	}

	private void writeSalt(File saltFile) throws IOException {
		try (var sout = new FileOutputStream(saltFile)) {
			sout.write(salt);
		}
	}

	private void readSalt(File saltFile) throws IOException {
		try (var sin = new FileInputStream(saltFile)) {
			var read = sin.read(salt);
			if (read != salt.length) {
				throw new IllegalStateException("Cannot read salt: short data");
			}
		}
	}

	private void createSalt() throws NoSuchAlgorithmException {
		var sr = SecureRandom.getInstance("SHA1PRNG");
		var newSalt = new byte[16];
		sr.nextBytes(newSalt);
		System.arraycopy(newSalt, 0, salt, 0, salt.length);
	}

	public String getHash(String content) {
		try {
			var chars = content.toCharArray();
			var spec = new PBEKeySpec(chars, salt, ITERATIONS, 64 * 8);
			var skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			var hash = skf.generateSecret(spec).getEncoded();
			return toHex(hash);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new IllegalStateException("Unable to hash value", e);
		}
	}

	public String toHex(byte[] array) {
		final var bi = new BigInteger(1, array);
		var hex = bi.toString(16);
		final var paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			hex = "0".repeat(paddingLength) + hex;
		}
		return hex;
	}

	public boolean validate(String toValidate, String storedContent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		var storedHash = fromHex(storedContent);
		var spec = new PBEKeySpec(toValidate.toCharArray(), salt, ITERATIONS, storedHash.length * 8);
		var skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		var hashToValidate = skf.generateSecret(spec).getEncoded();

		var diff = storedHash.length ^ hashToValidate.length;
		for (var i = 0; i < storedHash.length && i < hashToValidate.length; i++) {
			diff |= storedHash[i] ^ hashToValidate[i];
		}
		return diff == 0;
	}

	private byte[] fromHex(String hex) {
		var bytes = new byte[hex.length() / 2];
		for (var i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}
