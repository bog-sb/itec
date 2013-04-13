package business;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SMTPSender extends Observable implements Runnable {
	private domain.Session sess;

	public SMTPSender(domain.Session s, Observer o) {
		if (o != null) {
			this.addObserver(o);
		}
		sess = s;
	}

	@Override
	/**
	 * @throws IOException if the attached files paths are not valid
	 * @throws 
	 */
	public void run() {
		String host = sess.getServer();
		int port = sess.getServerPort();

		int counter = 0;
		int quantity = sess.getQuantity();
		final String user = sess.getUser();
		final String pass = sess.getPassword();
		String from = sess.getFrom();
		String subject = sess.getSubject();
		String text = sess.getMessage();
		Vector<String> recipientsStrings = sess.getTo();
		Vector<String> attachmentPaths = sess.getAttachments();

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		// properties.put("mail.smtp.starttls.enable", "true");
		Session session;
		// session = Session.getDefaultInstance(properties);
		if (user == null || user.length() == 0) {
			// Get the default Session object.
			session = Session.getDefaultInstance(properties);
		} else {
			// Use authentication to get the session
			properties.put("mail.user", user);
			properties.put("mail.password", pass);
			properties.put("mail.smtp.auth", "true");
			//properties.put("mail.smtp.starttls.enable", "true");
			session = Session.getInstance(properties,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user, pass);
						}
					});
			// }

			try {

				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);

				// Set the SentDate: header field
				message.setSentDate(new Date());

				// Set From: header field
				message.setFrom(new InternetAddress(from));

				// Set Recipients: header field
				InternetAddress[] recipients = new InternetAddress[recipientsStrings
						.size()];
				for (int i = 0; i < recipientsStrings.size(); i++) {
					recipients[i] = new InternetAddress(
							recipientsStrings.get(i));
				}
				message.setRecipients(Message.RecipientType.TO, recipients);

				// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();

				// Fill the message
				messageBodyPart.setText(text);

				// Create a multipar message
				Multipart multipart = new MimeMultipart();

				// Set text message part
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				for (int i = 0; i < attachmentPaths.size(); i++) {
					messageBodyPart = new MimeBodyPart();
					String filename = attachmentPaths.get(i);
					DataSource source = new FileDataSource(filename);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(filename);
					multipart.addBodyPart(messageBodyPart);
				}

				// Send the complete message parts
				message.setContent(multipart);

				for (counter = 0; counter < quantity; counter++) {
					setChanged();
					notifyObservers(counter);
					// Set Subject: header field
					subject = sess.getSubject();

					if (sess.getCounter()) {
						subject = String.valueOf(counter + 1) + ". " + subject;
					}
					if (sess.getRandomSubject()) {
						subject = subject + " "
								+ RandomGenerator.getRandomSubject();
					}
					message.setSubject(subject);

					// Send message
					Transport.send(message);

					// wait until the next message
					Thread.sleep(sess.getDelay());
				}
			} catch (MessagingException | InterruptedException mex) {
				// mex.printStackTrace();
			}
		}

	}
}
