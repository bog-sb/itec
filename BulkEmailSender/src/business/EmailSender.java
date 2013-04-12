package business;

import java.util.Observer;

import domain.Session;

public class EmailSender {
	private Thread senderThread;
	private Observer observer;

	public void runSession(Session session) {
		Runnable sender = null;

		if (session.getVia().equals("SMTP")) {
			sender = new SMTPSender(session, observer);
		} else if (session.getVia().equalsIgnoreCase("WCF")) {
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

	public void addMailCounterObserver(Observer o) {
		observer = o;
	}
}
