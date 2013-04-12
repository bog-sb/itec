package business;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import com.independentsoft.exchange.AttachmentId;
import com.independentsoft.exchange.FileAttachment;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ItemInfoResponse;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;

public class WCFSender implements Runnable {
	private domain.Session sess;
	private int counter;

	public WCFSender(domain.Session s) {
		sess = s;
	}

	@Override
	/**
	 * Crates a connection to a smtp sender, creates the emails and sends them
	 * @throws MailConnectException if it cannot connect to host/port
	 * @throws FileNotFoundException if at least one of the paths of the attachments are invalid
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

		try {
			Service service = new Service("https://myserver/ews/Exchange.asmx",
					"user", "pass");

			Message message = new Message();
			message.setSubject("subject");
			message.setBody(new Body(text));
			message.getToRecipients().add(new Mailbox("John@mydomain.com"));

			ItemId messageId = service.createItem(message);

			// Attach file
			FileAttachment fileAttachment = new FileAttachment(
					"file.txt");
			AttachmentId attachmentId = service.createAttachment(
					fileAttachment, messageId);

			// Update messageId
			messageId.setChangeKey(attachmentId.getRootItemChangeKey());

			// Send message
			ItemInfoResponse response = service.send(messageId);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getXmlMessage());

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
