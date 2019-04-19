String binaryFilePath = null;			// String value to store the binary/input file location
MessageDigest dgst = null;
Path inputPath = null;


  binaryFilePath = args[0];

  dgst = MessageDigest.getInstance("SHA-256");
  if(binaryFilePath != null){
			inputPath = Paths.get(binaryFilePath);
			binaryFilePathBytes = Files.readAllBytes(inputPath);		
		}
    
    String hashVal = getHash(binaryFilePathBytes, dgst);


/*
	 * Usage: The function will provide support String value to HEX Buffered String Support.
	 * For Example: input : 00 00 00 a0 will be converted to b'\x00\x00\x00x\a0' in output file.
	 * */
	private static byte[] HexStringToByteArray(String input){

		int len = input.length();

		byte[] inputByte = new byte[len/2];

		for(int i=0; i< len; i+=2){
			inputByte[i/2] = (byte) ((Character.digit(input.charAt(i), 16) << 4)
					+(Character.digit(input.charAt(i+1), 16)));
		}

		return inputByte;
	}

/*
	 * Usage: The function will provide support for ASCII to HEX conversion.
	 * For Example: input = TEST will result in output = 54 45 53 54
	 * For Example: input = test will result in output = 74 65 73 74
	 * */
	private static String convertStringToHex(String input){
		char[] chars = input.toCharArray();
		StringBuffer hexOutput = new StringBuffer();
		
		for(int i = 0; i < chars.length; i++){
			hexOutput.append(Integer.toHexString((int)chars[i]));
		}

		return hexOutput.toString();
	}

/*
	 * Usage: The function will provide the digest value for given data in Byte format
	 * The output will be in hexstring. e.g.: 8774a25d2ff424c9cc919db20c47f691317cea4b8776cb2b73c81794bd54f19b
	 * The function also has a capability to return a base64 encoded digest. Comment/Uncomment the base64 encoding line per the need of the use case.
	 * */
	private static String getHash(byte[] dataBuffer, MessageDigest dgst) throws Exception{
		dgst.update(dataBuffer);
		
		byte[] dgstByte = dgst.digest();

		StringBuffer sb = new StringBuffer();
		for(int i=0; i<dgstByte.length; i++){
			sb.append(Integer.toString((dgstByte[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		System.out.println("dgstByteString HEXString is: " + sb.toString());		
		return sb.toString();
		
		/*
		 * Uncomment below lines to return the message digest with base64 encoded string
		 * */
		
		// System.out.println("dgstByteString B64 of HEX is: " + Base64.encode(sb.toString().getBytes()));
		// return Base64.encode(sb.toString().getBytes());
	}


/*
	 * Usage : The function will provide the HEX value of a given input in the form of applicable buffer size.
	 * paddingAtBottom variable will instruct the program on where to append the padding characters(0x00)
	 * For Example - hexValue=0b, paddingAtBottom = false & bufferSize = 4 will return ==> 00 00 00 0b
	 * For Example - hexValue=510, paddingAtBottom = false & bufferSize = 5 will return ==> 00 00 00 05 10
	 * For Example - hexValue=0b, paddingAtBottom = true & bufferSize = 4 will return ==> 0b 00 00 00
	 * */
	private static String convertToByteHexValue(String hexValue, int bufferSize, boolean paddingAtBottom){
		
		StringBuffer sbTemp = new StringBuffer();
		
		int hexValSize = hexValue.length();
		
		// This will append the hexValue data at the beginning of the output String. e.g.: 0b 00 00 00
		if (paddingAtBottom) {
			sbTemp.append(hexValue);
		}
		
		while (bufferSize*2 >  hexValSize){
				sbTemp.append("0");
				hexValSize++;
		}
		
		// This will append the hexValue data at the end of the output String. e.g.: 00 00 00 0b
		if (!paddingAtBottom) {
			sbTemp.append(hexValue);
		}
		
		return sbTemp.toString();
	}
