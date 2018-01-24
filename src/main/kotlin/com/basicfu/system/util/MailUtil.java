package com.basicfu.system.util;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author fuliang
 * @date 2017/06/03
 */
public class MailUtil {
    public static void main(String[] args) {
        sendMail("hellowod测试");
    }
    public static void sendMail(String subject,String message){
        sendMail("basicfu@gmail.com",subject,message);
    }
    public static void sendMail(String message){
        sendMail("basicfu@gmail.com","服务器端异常",message);
    }
    /**
     * 发送邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public static void sendMail(String to, String subject, String content) {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        String host = "smtp.mxhichina.com";
        String username = "admin@dmka.cn";//发件人邮箱
        String password = "Basic1133..";
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.port", "25");
            prop.put("mail.smtp.auth", "true");
            senderImpl.setJavaMailProperties(prop);
            senderImpl.setHost(host);
            senderImpl.setUsername(username);
            senderImpl.setPassword(password);
            MimeMessage mailMessage = senderImpl.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "UTF-8");
            messageHelper.setFrom(username);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            senderImpl.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     *
     * @param receivers   多个收件人
     * @param subject     主题
     * @param content     内容
     * @param attachments 附件
     * @param img         嵌套图片
     */
    public static void sendMail(String[] receivers, String subject, String content, List<File> attachments, Map<String, File> img) {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        String host = "smtp.mxhichina.com";
        String username = "admin@dmka.cn";//发件人邮箱
        String password = "Basic1133..";
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.port", "25");
            prop.put("mail.smtp.auth", "true");
            senderImpl.setJavaMailProperties(prop);
            senderImpl.setHost(host);
            senderImpl.setUsername(username);
            senderImpl.setPassword(password);
            MimeMessage mailMessage = senderImpl.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            messageHelper.setFrom(username);
            messageHelper.setTo(receivers);// 设置收件人邮箱
            messageHelper.setSubject(subject);// 邮件主题
            messageHelper.setText(content, true);// true HTML格式
            if (attachments != null && attachments.size() > 0) {// 设置附件
                for (File file : attachments) {
                    messageHelper.addAttachment(file.getName(), file);
                }
            }
            if (img != null && img.size() > 0) {// 设置嵌套图片
                for (String key : img.keySet()) {
                    File file = img.get(key);
                    messageHelper.addInline(key, file);
                }
            }
            senderImpl.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//messageHelper.setCc(cc);// 设置抄送人
//messageHelper.setBcc(bcc);// 设置暗送人
//            SMTP 服务器地址：smtp.mxhichina.com 端口25， SSL 加密端口465
//            ssl连接加入以下代码
//            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            prop.put("mail.smtp.socketFactory.fallback", "false");
//            prop.put("mail.smtp.socketFactory.port", "465");