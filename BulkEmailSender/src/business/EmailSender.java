package business;

import domain.Session;

public class EmailSender {
	private Thread senderThread;

	public void runSession(Session session) {
		Runnable sender = null;

		if (session.getVia().equals("SMTP")) {
			sender = new SMTPSender(session);
		} else if (session.getVia().equals("WCF")) {
			// sender=new WCFSender(session);
		}

		if (sender == null)
			throw new RuntimeException("Invalid protocol selected");

		senderThread = new Thread(sender);
		senderThread.start();

	}

	public void stopActiveSession() {
		senderThread.interrupt();
		try { // //////////////--------------- !!!!!!!!!!!!!!!! TODO check out
				// this
			// exception
			senderThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
