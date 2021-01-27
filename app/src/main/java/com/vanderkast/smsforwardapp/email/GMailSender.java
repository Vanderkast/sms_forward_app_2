package com.vanderkast.smsforwardapp.email;

import com.vanderkast.smsforwardapp.di.Settings;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GMailSender extends Authenticator implements EmailSender {
    static {
        Security.addProvider(new JSSEProvider());
    }

    private final String mailHost = "smtp.gmail.com";
    private final String user;
    private final String password;
    private final Session session;

    @Inject
    public GMailSender() {
        this.user = Settings.EMAIL_ADDRESS;
        this.password = Settings.EMAIL_PASSWORD;

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailHost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    //todo: divide on send with/without attachment
    @Override
    public void send(EmailData data) throws Exception {
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(data.getText().getBytes(), "text/plain"));
        message.setSender(new InternetAddress(data.getRecipientAddress()));
        message.setSubject(data.getSubject());
        message.setDataHandler(handler);
        if (data.getRecipientAddress().indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(data.getRecipientAddress()));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(data.getRecipientAddress()));

        if (data.getAttachment() != null) {
            Multipart attachment = new MimeMultipart();
            MimeBodyPart part = new MimeBodyPart();
            part.setFileName(data.getAttachment());
            part.setDataHandler(new DataHandler(new FileDataSource(part.getFileName())));
            attachment.addBodyPart(part);
            message.setContent(attachment);
        }
        Transport.send(message);
    }

    public static class ByteArrayDataSource implements DataSource {
        private final byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}
