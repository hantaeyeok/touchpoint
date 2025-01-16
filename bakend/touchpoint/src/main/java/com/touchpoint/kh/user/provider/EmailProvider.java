package com.touchpoint.kh.user.provider;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailProvider {

	private final JavaMailSender javaMailSender;
	
	private final String SUBJECT = "[TOUCH POINT 키오스크 서비스] 인증메일입니다.";
	
	public boolean sendCerttificaionMail(String email, String certificationNumber) {
		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			
			String htmlcontent = getCertificionMessage(certificationNumber);
			
			messageHelper.setTo(email);
			messageHelper.setSubject(SUBJECT);
			messageHelper.setText(htmlcontent, true);
			
			javaMailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		
		}
		
		return true;
		
	}
	
	
	private String getCertificionMessage(String certificationNumber) {
		
		String certificationMessage = "";
		certificationMessage += "<h1 style='text-align:center;'>[TOUCH POINT 키오스크 서비스] 인증메일</h1>";
		certificationMessage += "<h3 style='text-align:center;> 인증코드 : <strong style = 'font-size:32px; letter-spacing:8px'> " +certificationNumber + "</strong></h3>";
				
		return certificationMessage;
		
	}
}
