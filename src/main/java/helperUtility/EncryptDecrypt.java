package helperUtility;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import encryptionUtilities.AESEncryptDecryptUtil;
import encryptionUtilities.AESEncryptDecryptUtil.KeySize;

/**
 * Encrypt Decrypt
 * @author Niraj Tiwari
 */
public class EncryptDecrypt {

    private static SecretKey key;
	private static IvParameterSpec ivParameterSpec = AESEncryptDecryptUtil.generateIv();
	private static String algorithm = "AES/CBC/PKCS5Padding";
	
	static {
			key = AESEncryptDecryptUtil.generateKey(KeySize.OneTwentyEight);
	}
	
	/**
	 * Encrypt String
	 * @param textToBeEncrypted
	 * @return encrpyted text
	 */
	public static String encryptString(String textToBeEncrypted) {
		
			return AESEncryptDecryptUtil.encryptText(algorithm, textToBeEncrypted, key, ivParameterSpec);

	}
	
	/**
	 * Decrypt String
	 * @param textToBeDecrypted
	 * @return decrypted text
	 */
	public static String decryptString(String textToBeDecrypted) {
		
		return AESEncryptDecryptUtil.decryptText(algorithm, textToBeDecrypted, key, ivParameterSpec);
	}
	

}
