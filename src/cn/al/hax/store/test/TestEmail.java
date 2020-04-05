package cn.al.hax.store.test;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestEmail {

	public static void main(String[] args) throws Exception {
		/*
		 * 1.先进行服务器的设置(设置不同账号的服务器设置操作，看你的发送账号是什么服务器的)
		 * 2.进行账号密码的设置操作
		 */
		// 0.1 服务器的设置
		Properties props = new Properties();
		//其他区服务器设置操作
		//props.setProperty("mail.host", "smtp.126.com");
		//props.setProperty("mail.smtp.auth", "true");
		// 0.2 账号和密码
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 126账号和密码（模拟账号，需要自己注册）
				return new PasswordAuthentication("hax@store.com", "hax");
			}
		};
		
		/*
		 * 1。先进进行一个会话session的连接操作，这个地方的session和服务器中的那个session不同
		 * 2.编写邮箱 使用new MineMesage（session）创建一个message对象
		 * 3.配置发件人的账号
		 * 4。配置收件人的账号 以及收件人的中的信息设置： cc:抄送 bcc:暗送  to:表示就是收件人的
		 * 5.设置发送的主题
		 * 6.设置发送的内容（可以 进行编码的设置）
		 * 7.Transport.send(message);消息的发送操作
		 */
		// 1 与126服务器建立连接：Session
		Session session = Session.getDefaultInstance(props, authenticator);

		// 2 编写邮件：Message
		Message message = new MimeMessage(session);
		// 2.1 发件人（模拟账号）
		//message.setFrom(new InternetAddress("itcast@126.com"));
		message.setFrom(new InternetAddress("hax@store.com"));
		// 2.2 收件人 , to:收件人 ， cc ：抄送，bcc：暗送（密送）。（模拟账号）
		message.setRecipient(RecipientType.TO, new InternetAddress("bbb@store.com"));
		// 2.3 主题
		message.setSubject("这是我们得第一份邮件");
		// 2.4 内容
		message.setContent("哈哈，您到我的商城注册了。", "text/html;charset=UTF-8");

		// 3 将消息进行发送：Transport
		Transport.send(message);

	}

}
