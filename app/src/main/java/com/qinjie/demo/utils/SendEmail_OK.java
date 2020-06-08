package com.qinjie.demo.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail_OK {
	public static String ownEmailAccount = null;
	public static String ownEmailPassword = null;
	public static Properties properties = null;
	public static String myEmailSMTPHost = null;
	public static Session session = null;
	static {
		//�����ʼ���stmp��������ַ
		myEmailSMTPHost = "smtp.163.com";
		//�Լ����׵��˺ź���Ȩ��
		ownEmailAccount = "qj18780122553@163.com";
		ownEmailPassword = "qj18780122553";
		//��ʼ��Properties
		properties = new Properties();
		// �����ʼ�������õ�Э��smtp
		properties.setProperty("mail.transport.protocol", "smtp");
		// ���÷������ʼ���������smtp��ַ
		// ���������׵�����smtp��������ַΪ��
		properties.setProperty("mail.smtp.host", myEmailSMTPHost);
		// ������֤����
		properties.setProperty("mail.smtp.auth", "true");
		//�õ��ͷ������ĻỰ
		session = Session.getInstance(properties);
//		session.setDebug(true);
	}
	

	public static boolean sendMessage(String receiver, String name, String subject, String content, int num) {
		//���ݻỰ����һ����Ϣ
		Message message = new MimeMessage(session);

		//�����ʼ����ռ����Ǳ���ʾ��ʱ��
		try {
			message.setFrom(new InternetAddress(ownEmailAccount, "校园零食商店", "utf-8"));
			//���÷��ͷ�ʽ
			switch(num) {
			case 1:
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver, name, "utf-8"));
				break;
			case 2:
				message.setRecipient(Message.RecipientType.CC, new InternetAddress(receiver, name, "utf-8"));
				break;
			case 3:
				message.setRecipient(Message.RecipientType.BCC, new InternetAddress(receiver, name, "utf-8"));
				break;
			default:
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver, name, "utf-8"));
			}

			message.setSentDate(new Date());
			message.setSubject(subject);
			message.setContent(content, "text/html;charset=utf-8");
			message.saveChanges();
		} catch (MessagingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		//��������
		Transport transport = null;
		try {

			transport = session.getTransport();
			transport.connect(ownEmailAccount, ownEmailPassword);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if (transport != null)
				try {
					transport.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		return true;
	}

	public static void main(String args[]){
		SendEmail_OK.sendMessage("1768923041@qq.com", "1768923041@qq.com", "验证码", "您本次的验证码316161", 0);
	}
}
