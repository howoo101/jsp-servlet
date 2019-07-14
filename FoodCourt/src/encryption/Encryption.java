package encryption;

import java.security.MessageDigest;

public class Encryption {
	public String pwdToSha2(String pwd) {
		String sha = "";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(pwd.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<byteData.length; i++) {
				sb.append(Integer.toString((byteData[i]&0xff)+0x100, 16).substring(1));
			}
			sha = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("encrypt Error - NoSuchAlgorithmException");
			sha = null;
		}
		return sha;
	}
	

}
