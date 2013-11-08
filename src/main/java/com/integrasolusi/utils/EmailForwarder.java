package com.integrasolusi.utils;

import com.integrasolusi.pusda.sisda.dao.NotificationEmailDao;
import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.NotificationEmail;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.util.*;

/**
 * Programmer : pancara
 * Date       : 4/15/13
 * Time       : 4:00 PM
 */
public class EmailForwarder {
    private Logger logger = Logger.getLogger(EmailForwarder.class);
    private JavaMailSender mailSender;
    private String replyAddress;
    private String toAddress;
    private TextGenerator textGenerator;

    EmailSenderRunner runner = new EmailSenderRunner();
    private NotificationEmailDao notificationEmailDao;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setReplyAddress(String replyAddress) {
        this.replyAddress = replyAddress;
    }

    public void setNotificationEmailDao(NotificationEmailDao notificationEmailDao) {
        this.notificationEmailDao = notificationEmailDao;
    }

    public void setTextGenerator(TextGenerator textGenerator) {
        this.textGenerator = textGenerator;
    }

    public void init() {
        if (runner.terminated) {
            runner.start();
        }
    }

    private void sendEmail(MimeMessagePreparator message) {
        runner.queueMessage(message);
    }

    public void forwardCommentGuest(CommentGuest comment) {
        MimeMessagePreparator message = createConfirmRegistrationMessage(comment);
        sendEmail(message);
    }

    private MimeMessagePreparator createConfirmRegistrationMessage(final CommentGuest comment) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(javax.mail.internet.MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setSubject("SISDA: Komentar Buku Tamu");
                message.setSentDate(new java.util.Date());
                message.setFrom(replyAddress);

                List<NotificationEmail> notificationEmailList = notificationEmailDao.findAlls();
                if (!StringUtils.isEmpty(toAddress)) {
                    message.setTo(toAddress);
                } else {
                    message.setTo(notificationEmailList.get(0).getAddress());
                    notificationEmailList.remove(0);
                }

                List<String> emails = new LinkedList<>();
                for (NotificationEmail email : notificationEmailList) {
                    emails.add(email.getAddress());
                }

                message.setCc(emails.toArray(new String[0]));
                String text = generateNotifyCommentText(comment);
                mimeMessage.setText(text, "UTF-8", "html");
            }
        };
        return preparator;
    }

    private String generateNotifyCommentText(CommentGuest comment) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("comment", comment);
        return textGenerator.generateText(TextGenerator.NotifyType.NOTIFY_COMMENT_GUEST, model);
    }

    public void terminate() {
        try {
            runner.terminate();
            runner.join();
        } catch (Exception e) {
        }
    }

    private class EmailSenderRunner extends Thread {
        private Queue<MimeMessagePreparator> queue = new LinkedList<MimeMessagePreparator>();
        private boolean terminated = true;

        @Override
        public void run() {
            terminated = false;
            while (!terminated) {
                MimeMessagePreparator message = queue.poll();
                if (message != null) {
                    mailSender.send(message);
                }
                
                if (queue.isEmpty()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        public void queueMessage(MimeMessagePreparator message) {
            queue.add(message);
        }

        public void terminate() {
            terminated = true;
        }
    }
}

