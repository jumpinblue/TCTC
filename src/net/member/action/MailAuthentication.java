package net.member.action;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/* SMTP서버와 관련된 정보를 지정 */
public class MailAuthentication {

	public int sendMail(String email) throws MessagingException {
		
		int certificationNumber = (int)(Math.random() * 90000 + 10000);	// 인증번호
		
		String from = "overtimearmy@gmail.com";	// 보내는 사람
		String to = email;	// 받는 사람
		String cc = "";	// 참조(필요없어서 비워둠)
		String subject = "BEFORE YOU GO 회원가입 인증번호";	// 제목
		String content = "안녕하세요! BEFORE YOU GO입니다.\n인증번호는 다음과 같습니다.\n인증번호: " + certificationNumber;	// 내용
		
		// Properties 설정
		// 프로퍼티 값 인스턴스 생성과 기본세션(SMTP 서버 호스트 지정)
		Properties props = new Properties();
		
		// G-Mail SMTP 사용시
		props.put("mail.transport.protocol", "smtp");// 프로토콜 설정
		props.put("mail.smtp.host", "smtp.gmail.com");// gmail SMTP 서비스 주소(호스트)
		props.put("mail.smtp.port", "465");// gmail SMTP 서비스 포트 설정
		// 로그인 할때 Transport Layer Security(TLS)를 사용할 것인지 설정
		// gmail 에선 tls가 필수가 아니므로 해도 그만 안해도 그만
			props.put("mail.smtp.starttls.enable","true");
			// gmail 인증용 Secure Socket Layer(SSL) 설정
			// gmail 에서 인증때 사용해주므로 요건 안해주면 안됨
			props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");// SMTP 인증을 설정
		 
		/**
		  * SMTP 인증이 필요한 경우 반드시 Properties 에 SMTP 인증을 사용한다고 설정하여야 한다.
		  * 그렇지 않으면 인증을 시도조차 하지 않는다.
		  * 그리고 Authenticator 클래스를 상속받은 SMTPAuthenticator 클래스를 생성한다.
		  * getPasswordAuthentication() 메소드만 override 하면 된다.
		  * 머 사실 다른 메소드는 final 메소드여서 override 할 수 조차 없다. -ㅅ-;
		  */
		Authenticator auth = new SMTPAuthenticator(); 
		Session mailSession = Session.getDefaultInstance(props, auth);
		
		// create a message
		Message message = new MimeMessage(mailSession);
		
		// set the from and to address
		message.setFrom(new InternetAddress(from));		// 보내는 사람 설정
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));		// 받는사람 이메일
		
		if(!cc.trim().equals("")) {
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));		// 참조자 이메일
		}
		
		// Setting the Subject and Content Type
		message.setSubject(subject);				// 메일 제목
		message.setText(content);					// 메일 내용
		message.setSentDate(new Date());	// 보내는 날짜 설정
		
		Transport.send(message);		// 메일 보내기
		
		System.out.println("메일 전송완료");
		
		return certificationNumber;
	}
}

/* 보내는 메일 인증 */
class SMTPAuthenticator extends Authenticator {
	 
    protected PasswordAuthentication getPasswordAuthentication() {
         String username = "overtimearmy@gmail.com"; // gmail 사용자;
         String password = "qhrtkgkwlak";  // 패스워드;(복사하지마)
         return new PasswordAuthentication(username, password);
    }
}

