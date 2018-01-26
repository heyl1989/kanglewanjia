package cn.v1.kanglewanjia.util;

import java.io.ByteArrayOutputStream;

public class Base64 {
	private static final byte[] encodingTable = {
        (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E',
        (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J',
        (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O',
        (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T',
        (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y',
        (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd',
        (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i',
        (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n',
        (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's',
        (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x',
        (byte) 'y', (byte) 'z', (byte) '0', (byte) '1', (byte) '2',
        (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
        (byte) '8', (byte) '9', (byte) '+', (byte) '/', (byte) '=',
    };
	
	private static final byte[] decodingTable;
	static {
	    decodingTable = new byte[128];
	    for (int i = 0; i < 128; i++) {
	        decodingTable[i] = (byte) -1;
	    }
	    for (int i = 'A'; i <= 'Z'; i++) {
	        decodingTable[i] = (byte) (i - 'A');
	    }
	    for (int i = 'a'; i <= 'z'; i++) {
	        decodingTable[i] = (byte) (i - 'a' + 26);
	    }
	    for (int i = '0'; i <= '9'; i++) {
	        decodingTable[i] = (byte) (i - '0' + 52);
	    }
	    decodingTable['+'] = 62;
	    decodingTable['/'] = 63;
	    decodingTable['='] = 64;
	}
	
	public static byte[] encode(byte[] data,int len) {
	    byte[] bytes;
	    int modulus = len % 3;
	    
	    if (modulus == 0) {
	        bytes = new byte[(4 * len) / 3];
	    } else {
	        bytes = new byte[4 * ((len / 3) + 1)];
	    }
	    
	    int dataLength = (len - modulus);
	    int a1;
	    int a2;
	    int a3;
	    
	    for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
	        a1 = data[i] & 0xff;
	        a2 = data[i + 1] & 0xff;
	        a3 = data[i + 2] & 0xff;
	        bytes[j] = encodingTable[(a1 >>> 2) & 0x3f];
	        bytes[j + 1] = encodingTable[((a1 << 4) | (a2 >>> 4)) & 0x3f];
	        bytes[j + 2] = encodingTable[((a2 << 2) | (a3 >>> 6)) & 0x3f];
	        bytes[j + 3] = encodingTable[a3 & 0x3f];
	    }
	    
	    int b1;
	    int b2;
	    int b3;
	    int d1;
	    int d2;
	    
	    switch (modulus) {
	    case 0: /* nothing left to do */
	        break;
	    
	    case 1:
	        d1 = data[len - 1] & 0xff;
	        b1 = (d1 >>> 2) & 0x3f;
	        b2 = (d1 << 4) & 0x3f;
	        bytes[bytes.length - 4] = encodingTable[b1];
	        bytes[bytes.length - 3] = encodingTable[b2];
	        bytes[bytes.length - 2] = (byte) '=';
	        bytes[bytes.length - 1] = (byte) '=';
	        break;
	    
	    case 2:
	        d1 = data[len - 2] & 0xff;
	        d2 = data[len - 1] & 0xff;
	        b1 = (d1 >>> 2) & 0x3f;
	        b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
	        b3 = (d2 << 2) & 0x3f;
	        bytes[bytes.length - 4] = encodingTable[b1];
	        bytes[bytes.length - 3] = encodingTable[b2];
	        bytes[bytes.length - 2] = encodingTable[b3];
	        bytes[bytes.length - 1] = (byte) '=';
	        break;
	    }
	    
	    return bytes;
	}

	public static byte[] encode(byte[] data) {
	    byte[] bytes;
	    int modulus = data.length % 3;
	    
	    if (modulus == 0) {
	        bytes = new byte[(4 * data.length) / 3];
	    } else {
	        bytes = new byte[4 * ((data.length / 3) + 1)];
	    }
	    
	    int dataLength = (data.length - modulus);
	    int a1;
	    int a2;
	    int a3;
	    
	    for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
	        a1 = data[i] & 0xff;
	        a2 = data[i + 1] & 0xff;
	        a3 = data[i + 2] & 0xff;
	        bytes[j] = encodingTable[(a1 >>> 2) & 0x3f];
	        bytes[j + 1] = encodingTable[((a1 << 4) | (a2 >>> 4)) & 0x3f];
	        bytes[j + 2] = encodingTable[((a2 << 2) | (a3 >>> 6)) & 0x3f];
	        bytes[j + 3] = encodingTable[a3 & 0x3f];
	    }
	    
	    int b1;
	    int b2;
	    int b3;
	    int d1;
	    int d2;
	    
	    switch (modulus) {
	    case 0: /* nothing left to do */
	        break;
	    
	    case 1:
	        d1 = data[data.length - 1] & 0xff;
	        b1 = (d1 >>> 2) & 0x3f;
	        b2 = (d1 << 4) & 0x3f;
	        bytes[bytes.length - 4] = encodingTable[b1];
	        bytes[bytes.length - 3] = encodingTable[b2];
	        bytes[bytes.length - 2] = (byte) '=';
	        bytes[bytes.length - 1] = (byte) '=';
	        break;
	    
	    case 2:
	        d1 = data[data.length - 2] & 0xff;
	        d2 = data[data.length - 1] & 0xff;
	        b1 = (d1 >>> 2) & 0x3f;
	        b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
	        b3 = (d2 << 2) & 0x3f;
	        bytes[bytes.length - 4] = encodingTable[b1];
	        bytes[bytes.length - 3] = encodingTable[b2];
	        bytes[bytes.length - 2] = encodingTable[b3];
	        bytes[bytes.length - 1] = (byte) '=';
	        break;
	    }
	    
	    return bytes;
	}
	
	static ByteArrayOutputStream s_byteOS;
	/**
	 * decode given byte array
	 * @param data
	 * @return
	 */
	public static byte[] decode(byte[] data) {
////		ByteArrayOutputStream byteOS;
//		byteOS = new ByteArrayOutputStream();
		if (s_byteOS == null) {
			s_byteOS = new ByteArrayOutputStream();
		}
		
		s_byteOS.reset();
		
		// if empty input
		if (data == null || data.length == 0) {
			return new byte[0];
		}
		
		if (data.length % 4 != 0) {
		//	System.out.println(">>>>>>>>>>>>>>>before data.length: " + data.length);
		}
		
		data = discardNonBase64Bytes(data);
		
		int pSrcIndex = 0;
		byte ch1 = 0, ch2 = 0, ch3 = 0, ch4 = 0;
		
		while (pSrcIndex < data.length) {
			// get the first character
			if (pSrcIndex < data.length) {
				ch1 = data[pSrcIndex++];
				// if not a validate byte
				if (!isValidBase64Byte(ch1)) {
					return new byte[0];
				}
			}
			
			// get the second character
			if (pSrcIndex < data.length) {
				ch2 = data[pSrcIndex++];
				// if not a validate byte
				if (!isValidBase64Byte(ch2)) {
					return new byte[0];
				}
			}
			
			// get the third character
			if (pSrcIndex < data.length) {
				ch3 = data[pSrcIndex++];
				// if not a validate byte
				if (!isValidBase64Byte(ch3)) {
					return new byte[0];
				}
			}
			
			// get the forth character
			if (pSrcIndex < data.length) {
				ch4 = data[pSrcIndex++];
				// if not a validate byte
				if (!isValidBase64Byte(ch4)) {
					return new byte[0];
				}
			}
			
			// if input encoding error
			if((ch1 == encodingTable[64])
					|| (ch2 == encodingTable[64])
		            || (ch3 == encodingTable[64] && ch4 != encodingTable[64])
		            ) {
		            return new byte[0];
			}
			
			// if only one character
			if (ch3 == encodingTable[64]) {
				byte b1 = decodingTable[ch1];
				byte b2 = decodingTable[ch2];
				
				s_byteOS.write((byte) ((b1 << 2) | (b2 >> 4)));
	        }
			// if two character
	        else if (ch4 == encodingTable[64]) {
	        	byte b1 = decodingTable[ch1];
	        	byte b2 = decodingTable[ch2];
	        	byte b3 = decodingTable[ch3];
	        	
	        	s_byteOS.write((byte) ((b1 << 2) | (b2 >> 4)));
	        	s_byteOS.write((byte) ((b2 << 4) | (b3 >> 2)));
	        }
			// if three character
	        else {
	        	byte b1 = decodingTable[ch1];
	        	byte b2 = decodingTable[ch2];
	        	byte b3 = decodingTable[ch3];
	        	byte b4 = decodingTable[ch4];
	        	
	        	s_byteOS.write((byte) ((b1 << 2) | (b2 >> 4)));
	        	s_byteOS.write((byte) ((b2 << 4) | (b3 >> 2)));
	        	s_byteOS.write((byte) ((b3 << 6) | b4));
	        }
		}
		
		byte[] decodedBytes = s_byteOS.toByteArray();
		
		return decodedBytes;
	}
	
	/**
	 * decode given String
	 * @param data
	 * @return
	 */
	public static byte[] decode(String data) {
		if (data == null || data.length() == 0) {
			return new byte[0];
		}
		
		return decode(data.getBytes());
	}
	
	private static byte[] discardNonBase64Bytes(byte[] data) {
	    byte[] temp = new byte[data.length];
	    int bytesCopied = 0;
	    
	    for (int i = 0; i < data.length; i++) {
	        if (isValidBase64Byte(data[i])) {
	            temp[bytesCopied++] = data[i];
	        }
	    }
	    
	    byte[] newData = new byte[bytesCopied];
	    
	    System.arraycopy(temp, 0, newData, 0, bytesCopied);
	    
	    return newData;
	}

	private static boolean isValidBase64Byte(byte b) {
		if (b == '=') {
	        return true;
	    } else if ((b < 0) || (b >= 128)) {
	        return false;
	    } else if (decodingTable[b] == -1) {
	        return false;
	    }
	    
		return true;
	}
	
}