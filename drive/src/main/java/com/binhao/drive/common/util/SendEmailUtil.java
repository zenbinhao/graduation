package com.binhao.drive.common.util;



import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 *
 * 自动发送邮件
 * 调用javamail，实现邮件收发人的配置，并发送邮件
 * 对QQ和163邮箱使用，首先把邮箱的POP3/SMTP服务，IMAP/SMTP服务打开 首先确定你的网络正常、非代理
 * 如果没有开启，那么这样设置下 设置->账户 ，把这个选上 [√]SMTP发信后保存到服务器
 *
 */

@Slf4j
public class SendEmailUtil {



    public static void sendEmail(String toUser,String sendName) {

        System.out.println("进入方法---------------------------------------------发送邮件");

        String host="smtp.qq.com";//邮箱服务器
        String sendUser="";//发件人登录用户名
        String sendPassWord="";//发件人登录密码

        try {
            Properties props = new Properties();                // 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
            //创建参数配置, 用于连接邮件服务器的参数配置
            props.setProperty("mail.debug", "true");    // 开启debug调试
            props.setProperty("mail.smtp.auth", "true");    // 发送服务器需要身份验证
            props.setProperty("mail.host", host);        // 设置邮件服务器主机名
            props.setProperty("mail.transport.protocol", "smtp");    // 发送邮件协议名称

            /*
             * PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
             * 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
             * 打开下面的注释代码, 开启 SSL 安全连接。
             * SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
             * 需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
             * QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
             *
             *
             */
            String smtpPort = "465";
            props.setProperty("mail.smtp.port", smtpPort);//端口号
            props.setProperty("mail.smtp.ssl.enable", "true");//加认证机制

            // 设置环境信息
            Session session= Session.getInstance(props);        // 根据参数配置，创建会话对象（为了发送邮件准备的）
            //session.setDebug(true); //开启debug调试
            Message msg = new MimeMessage(session);     // 创建邮件对象

            /*
             * 也可以根据已有的eml邮件文件创建 MimeMessage 对象
             * MimeMessage message = new MimeMessage(session, new FileInputStream("MyEmail.eml"));
             */



            // 设置邮件内容
            msg.setSubject("驾校管理系统：");        //设置邮件标题
            // 设置邮件内容


            String content = "[驾校管理系统]:您的学员"+sendName+"已预约了您的课程，请教练员尽快排课\nhttp://localhost:8080/#/";
            msg.setText(content);
            //msg.setContent("这是一封由JavaMail发送的邮件！","text/html;charset=GBK");


            msg.setSentDate(new Date());// 设置发送时间
            // 设置发件人


            msg.setFrom(new InternetAddress(sendUser));


            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toUser));// 设置收件人地址


            msg.saveChanges();//保存设置
            Transport transport = session.getTransport("smtp");
            // 连接邮件服务器
            transport.connect(host,sendUser,sendPassWord);
            //transport.sendMessage(msg, new Address[] {new InternetAddress(toUser)});
            System.out.println("正在发送=============================================");
            transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));// 发送邮件
            transport.close();// 关闭连接
        }
//        catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
        catch (MessagingException e) {
            log.error("邮件发送失败",e);
            throw new RuntimeException(e);

        }finally {
            System.out.println("----------------------------------------------------结束");
        }
    }


}
