package com.demo.store.service.impl;

import com.demo.store.service.AWSSESService;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SendRawEmailResponse;

@Slf4j
@Service
public class AWSSESServiceImpl implements AWSSESService {

    /**
     * The Access key ID.
     */
    @Value("${aws.ses.credentials.access-key-id}")
    private String accessKeyId;

    /**
     * The Secret access key.
     */
    @Value("${aws.ses.credentials.secret-access-key}")
    private String secretAccessKey;

    /**
     * The SES region.
     */
    @Value("${aws.ses.region}")
    private String region;

    /**
     * The AWS SES sender email.
     */
    @Value("${aws.ses.sender.email}")
    private String senderEmail;

    /**
     * The AWS SES sender name.
     */
    @Value("${aws.ses.sender.name}")
    private String senderName;

    private static final String SENDER_FORMAT = "\"{0}\" <{1}>";
    private static final String MEDIA_TYPE = "text/html; charset=UTF-8";

    /**
     * Static AWS SES environments
     */
    private static SesClient client = null;

    @PostConstruct
    private void initializeAmazon() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        client = SesClient.builder().region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials)).build();
    }

    @Override
    public void sendEmail(String title, String content, String recipient) {
        this.sendEmail(title, content, recipient, senderEmail, senderName);
    }

    @Override
    public void sendEmail(String title, String content, String recipient, String senderEmail,
            String senderName) {
        this.sendEmail(title, content, recipient, senderEmail, senderName, null);
    }

    private void sendEmail(String title, String content, String recipient, String senderEmail,
            String senderName, List<String> ccAddresses) {
        log.debug("IN - title = {}, content = {}, email = {}, sender email = {}, sender name = {}",
                title, content, recipient, senderEmail, senderName);

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);

        // Add subject, from and to lines
        try {
            message.setSubject(title, "UTF-8");
            message.setFrom(new InternetAddress(senderEmail, senderName));
            if (ccAddresses != null && !ccAddresses.isEmpty()) {
                InternetAddress[] ccAddressesArray = ccAddresses.stream().map(ccAddress -> {
                    try {
                        return InternetAddress.parse(ccAddress);
                    } catch (AddressException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                }).toArray(InternetAddress[]::new);
                message.setRecipients(javax.mail.Message.RecipientType.CC, ccAddressesArray);
            }
            message.setRecipients(javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(recipient));

            // Create a multipart/alternative child container
            MimeMultipart msgBody = new MimeMultipart("alternative");

            // Create a wrapper for the HTML and text parts
            MimeBodyPart wrap = new MimeBodyPart();

            // Define the HTML part
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(content, MEDIA_TYPE);

            // Add the text and HTML parts to the child container
            msgBody.addBodyPart(htmlPart);

            // Add the child container to the wrapper object
            wrap.setContent(msgBody);

            // Create a multipart/mixed parent container
            MimeMultipart msg = new MimeMultipart("mixed");

            // Add the parent container to the message
            message.setContent(msg);

            // Add the multipart/alternative part to the message
            msg.addBodyPart(wrap);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder().data(data).build();

            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage).build();
            SendRawEmailResponse response = client.sendRawEmail(rawEmailRequest);

            int statusCode = response.sdkHttpResponse().statusCode();
            log.info("AWS SES Response: {}", statusCode);
            if (statusCode > 300) {
                log.warn("AWS SES error: {}", response.responseMetadata().toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.debug("OUT");
    }

    @Override
    public void sendStringEmail(String title, String content, String recipient) {
        this.sendStringEmail(title, content, recipient, senderEmail, senderName);
    }

    @Override
    public void sendStringEmail(String title, String content, String recipient, String senderEmail,
            String senderName) {
        log.debug("IN - title = {}, content = {}, email = {}, sender email = {}, sender name = {}",
                title, content, recipient, senderEmail, senderName);
        try {
            Destination destination = Destination.builder().toAddresses(recipient).build();
            String source = MessageFormat.format(SENDER_FORMAT, senderName, senderEmail);
            Message message = Message.builder().subject(Content.builder().data(title).build())
                    .body(Body.builder().text(Content.builder().data(content).build()).build())
                    .build();
            SendEmailRequest emailRequest = SendEmailRequest.builder().source(source)
                    .destination(destination).message(message).build();
            SendEmailResponse response = client.sendEmail(emailRequest);
            int statusCode = response.sdkHttpResponse().statusCode();
            log.info("AWS SES Response: {}", statusCode);
            if (statusCode > 300) {
                log.warn("AWS SES error: {}", response.responseMetadata().toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.debug("OUT");
    }

}
