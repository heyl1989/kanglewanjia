package cn.v1.kanglewanjia.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import cn.v1.kanglewanjia.network.NetContract;


public class DefaultEncryption implements Encryption {

	private byte[] secretKey = null;
	private String signKey = "";
	private String myName = "";
	private String sid = "";

	public byte[] getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(byte[] secretKey) {
		this.secretKey = secretKey;
	}

	public String getMyName() {
		return myName;
	}
 
	public void setMyName(String myName) {
		this.myName = myName;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}


	public DefaultEncryption(String sid) {
		this.sid = sid;
		String asekey= "ai@cellphone.com";
		this.secretKey =asekey.getBytes();
		this.signKey = "signkey@aiclient";
	
	}

	public String Encrypt(String src) throws Exception {
//		System.out.println("secretKey--");
//		for(int i=0;i<secretKey.length;i++){
//			//System.out.print(secretKey[i]+" ");
//		}
//		System.out.println("secretKey--");
		SecretKey sKey = new SecretKeySpec(secretKey, "AES");
		Cipher ci = Cipher.getInstance("AES");
		ci.init(Cipher.ENCRYPT_MODE, sKey);
		return parseByte2HexStr(ci.doFinal(src.getBytes()));
	}

	public static String EncryptToken(String src) throws Exception {
//		System.out.println("secretKey--");
//		for(int i=0;i<secretKey.length;i++){
//			//System.out.print(secretKey[i]+" ");
//		}
//		System.out.println("secretKey--");
		SecretKey sKey = new SecretKeySpec(NetContract.TokenAesKey.getBytes(), "AES");
		Cipher ci = Cipher.getInstance("AES");
		ci.init(Cipher.ENCRYPT_MODE, sKey);
		return parseByte2HexStr(ci.doFinal(src.getBytes()));
	}
	
	public static String Encrypt(byte[] src,String secretKey) throws Exception {
//		System.out.println("secretKey--");
//		for(int i=0;i<secretKey.length;i++){
//			//System.out.print(secretKey[i]+" ");
//		}
//		System.out.println("secretKey--");
		SecretKey sKey = new SecretKeySpec(secretKey.getBytes(), "AES");
		Cipher ci = Cipher.getInstance("AES/ECB/PKCS5Padding");
		ci.init(Cipher.ENCRYPT_MODE, sKey);
		byte[] b = ci.doFinal(src);
		/*byte[] b = AES.encryptWithRawKey(secretKey,src);*/
		return new String(Base64.encode(b));
	}

	public String Decrypt(String src) throws Exception {
		SecretKey sKey = new SecretKeySpec(secretKey, "AES");
		Cipher ci = Cipher.getInstance("AES");
		ci.init(Cipher.DECRYPT_MODE, sKey);

		return new String(ci.doFinal(parseHexStr2Byte(src)));
	}

	public String getSignKey() throws Exception {
		return this.signKey;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	
	
	/**
	 * 閻忓繐妫旂花鈺傛交濞戞ê鐓戦弶鐑嗗墯瀹曟煡骞嬮敓锟介弶鈺傜☉閸╋拷
	 * @param buf
	 * @return
	 */

	public static String  parseByte2HexStr(byte buf[]) {
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 閻忓骏鎷�閺夆晜绋戦崺妤佹姜椤掍礁搴婂☉鎾规〃缁ㄢ晜娼诲☉妯虹厬

	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1){
			return null;
		}
		
		byte[] result = new byte[hexStr.length()/2];
		for (int i = 0;i< hexStr.length()/2; i++) {
			
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		
		return result;
	}
	

}
