package com.touchpoint.kh.user.common;

public class CertificationNumber {

	public static String getCertificationNumber() {
		
		String certificaionNumber = "";
		for(int count = 0; count < 6; count++) 
			certificaionNumber += (int)(Math.random()*10);
		
		return certificaionNumber;
	}
}
