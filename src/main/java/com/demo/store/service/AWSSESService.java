package com.demo.store.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface AWSSESService {

    /**
     * Send a raw message to user's email
     *
     * @param title     the email's title
     * @param content   the email's content
     * @param recipient the recipient's email
     */
    public void sendEmail(String title, String content, String recipient);

    /**
     * Send a raw message to user's email
     *
     * @param title       the email's title
     * @param content     the email's content
     * @param recipient   the recipient's email
     * @param senderEmail the sender's email
     * @param senderName  the sender's name
     */
    public void sendEmail(String title, String content, String recipient, String senderEmail,
            String senderName);

    /**
     * Send a string message to user's email
     *
     * @param title     the email's title
     * @param content   the email's content
     * @param recipient the recipient's email
     */
    public void sendStringEmail(String title, String content, String recipient);

    /**
     * Send a string message to user's email
     *
     * @param title       the email's title
     * @param content     the email's content
     * @param recipient   the recipient's email
     * @param senderEmail the sender's email
     * @param senderName  the sender's name
     */
    public void sendStringEmail(String title, String content, String recipient, String senderEmail,
            String senderName);

}
