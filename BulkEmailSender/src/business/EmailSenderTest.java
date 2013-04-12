package business;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import domain.Session;

public class EmailSenderTest {

	@Test
	public void test() {
		Vector<String> to=new Vector<>();
		to.add("bog_sb@yahaoo.com");
		to.add("sb.bogdan@gmail.com");
		
		Vector<String> at=new Vector<>();
		at.add("file.txt");
		at.add("portraits.jpg");
		
		Session s=new Session();
		s.setFrom("sb.bogdan@gmail.com");
		s.setMessage("SMTP mail test loll!!!!");
		s.setTo(to);
		s.setSubject("SMTP test");
		s.setAttachments(at);
		s.setCounter();
		s.setQuantity(3);
		s.setDelay(1000);
		s.unsetRandomSubject();
		
		s.setVia("SMTP");
		EmailSender es=new EmailSender();
		es.runSession(s);
		
	}

}
