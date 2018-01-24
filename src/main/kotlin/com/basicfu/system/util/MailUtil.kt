package com.basicfu.system.util

import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import java.io.File
import java.util.*

/**
 * @author fuliang
 * @date 2017/06/03
 */
object MailUtil {

    fun sendMail(subject: String, message: String) {
        sendMail("basicfu@gmail.com", subject, message)
    }

    fun sendMail(message: String) {
        sendMail("basicfu@gmail.com", "服务器端异常", message)
    }

    /**
     * 发送邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    fun sendMail(to: String, subject: String, content: String) {
        val senderImpl = JavaMailSenderImpl()
        val host = "smtp.mxhichina.com"
        val username = "admin@dmka.cn"//发件人邮箱
        val password = "Basic1133.."
        try {
            val prop = Properties()
            prop.put("mail.smtp.port", "25")
            prop.put("mail.smtp.auth", "true")
            senderImpl.javaMailProperties = prop
            senderImpl.host = host
            senderImpl.username = username
            senderImpl.password = password
            val mailMessage = senderImpl.createMimeMessage()
            val messageHelper = MimeMessageHelper(mailMessage, "UTF-8")
            messageHelper.setFrom(username)
            messageHelper.setTo(to)
            messageHelper.setSubject(subject)
            messageHelper.setText(content, true)
            senderImpl.send(mailMessage)
        } catch (e: Exception) {
            e.printStackTrace()
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
    fun sendMail(receivers: Array<String>, subject: String, content: String, attachments: List<File>?, img: Map<String, File>?) {
        val senderImpl = JavaMailSenderImpl()
        val host = "smtp.mxhichina.com"
        val username = "admin@dmka.cn"//发件人邮箱
        val password = "Basic1133.."
        try {
            val prop = Properties()
            prop.put("mail.smtp.port", "25")
            prop.put("mail.smtp.auth", "true")
            senderImpl.javaMailProperties = prop
            senderImpl.host = host
            senderImpl.username = username
            senderImpl.password = password
            val mailMessage = senderImpl.createMimeMessage()
            val messageHelper = MimeMessageHelper(mailMessage, true, "UTF-8")
            messageHelper.setFrom(username)
            messageHelper.setTo(receivers)// 设置收件人邮箱
            messageHelper.setSubject(subject)// 邮件主题
            messageHelper.setText(content, true)// true HTML格式
            if (attachments != null && attachments.size > 0) {// 设置附件
                for (file in attachments) {
                    messageHelper.addAttachment(file.name, file)
                }
            }
            if (img != null && img.size > 0) {// 设置嵌套图片
                for (key in img.keys) {
                    val file = img[key]
                    messageHelper.addInline(key, file)
                }
            }
            senderImpl.send(mailMessage)
        } catch (e: Exception) {
            e.printStackTrace()
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