package domain;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLSessionParser extends DefaultHandler {
	
	private Session session;
	private Boolean inFrom, inTo, inSubject, inMessage, inAttachment, inVia,
				inQuantity, inDelay, inCounter, inRandomSubject, inRandomMessage,
				inServer, inServerPort, inUser, inPassword;
	
	XMLSessionParser(Session session) {
		this.session = session;
		inFrom = false;
		inTo = false;
		inSubject = false;
		inMessage = false;
		inAttachment = false;
		inVia = false;
		inQuantity = false;
		inDelay = false;
		inCounter = false;
		inRandomMessage = false;
		inRandomSubject = false;
		inServer = false;
		inServerPort = false;
		inUser = false;
		inPassword = false;
		this.session.setMessage("");
	}
	
    public void characters(char[] buffer, int start, int length) {
    	String buf = new String(buffer, start, length);
    	if (inFrom) {
    		session.setFrom(buf);
    	}
    	else if (inTo) {
    		session.addTo(buf);
    	}
    	else if (inSubject) {
    		session.setSubject(buf);
    	}
    	else if (inMessage) {
    		session.setMessage(session.getMessage() + buf);
    	}
    	else if (inAttachment) {
    		session.addAttachment(buf);
    	}
    	else if (inVia) {
    		session.setVia(buf);
    	}
    	else if (inQuantity) {
    		session.setQuantity(Integer.valueOf(buf));
    	}
    	else if (inDelay) {
    		session.setDelay(Integer.valueOf(buf));
    	}
    	else if (inCounter) {
    		session.setCounter(Boolean.valueOf(buf.toLowerCase()));
    	}
    	else if (inRandomMessage) {
    		session.setRandomMessage(Boolean.valueOf(buf.toLowerCase()));
    	}
    	else if (inRandomSubject) {
    		session.setRandomSubject(Boolean.valueOf(buf.toLowerCase()));
    	}
    	else if (inServerPort) {
    		session.setServerPort(Integer.valueOf(buf));
    	}
    	else if (inServer) {
    		session.setServer(buf);
    	}
    	else if (inUser) {
    		session.setUser(buf);
    	}
    	else if (inPassword) {
    		session.setPassword(buf);
    	}
    }
    
    public void startElement(String uri, String localName,
    		String qName, Attributes attributes) throws SAXException {
    	if (qName.equalsIgnoreCase("from")) {
    		inFrom = true;
    	}
    	else if (qName.equalsIgnoreCase("to")) {
    		inTo = true;
    	}
    	else if (qName.equalsIgnoreCase("Subject")) {
    		inSubject = true;
    	}
    	else if (qName.equalsIgnoreCase("Message")) {
    		inMessage = true;
    	}
    	else if (qName.equalsIgnoreCase("Attachment")) {
    		inAttachment = true;
    	}
    	else if (qName.equalsIgnoreCase("Via")) {
    		inVia = true;
    	}
    	else if (qName.equalsIgnoreCase("Quantity")) {
    		inQuantity = true;
    	}
    	else if (qName.equalsIgnoreCase("Delay")) {
    		inDelay = true;
    	}
    	else if (qName.equalsIgnoreCase("Counter")) {
    		inCounter = true;
    	}
    	else if (qName.equalsIgnoreCase("RandomSubject")) {
    		inRandomSubject = true;
    	}
    	else if (qName.equalsIgnoreCase("RandomMessage")) {
    		inRandomMessage = true;
    	}
    	else if (qName.equalsIgnoreCase("Server")) {
    		inServer = true;
    	}
    	else if (qName.equalsIgnoreCase("ServerPort")) {
    		inServerPort = true;
    	}
    	else if (qName.equalsIgnoreCase("User")) {
    		inUser = true;
    	}
    	else if (qName.equalsIgnoreCase("Password")) {
    		inPassword = true;
    	}
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    	if (qName.equalsIgnoreCase("from")) {
    		inFrom = false;
    	}
    	else if (qName.equalsIgnoreCase("to")) {
    		inTo = false;
    	}
    	else if (qName.equalsIgnoreCase("Subject")) {
    		inSubject = false;
    	}
    	else if (qName.equalsIgnoreCase("Message")) {
    		inMessage = false;
    	}
    	else if (qName.equalsIgnoreCase("Attachment")) {
    		inAttachment = false;
    	}
    	else if (qName.equalsIgnoreCase("Via")) {
    		inVia = false;
    	}
    	else if (qName.equalsIgnoreCase("Quantity")) {
    		inQuantity = false;
    	}
    	else if (qName.equalsIgnoreCase("Delay")) {
    		inDelay = false;
    	}
    	else if (qName.equalsIgnoreCase("Counter")) {
    		inCounter = false;
    	}
    	else if (qName.equalsIgnoreCase("RandomSubject")) {
    		inRandomSubject = false;
    	}
    	else if (qName.equalsIgnoreCase("RandomMessage")) {
    		inRandomMessage = false;
    	}
    	else if (qName.equalsIgnoreCase("Server")) {
    		inServer = false;
    	}
    	else if (qName.equalsIgnoreCase("ServerPort")) {
    		inServerPort = false;
    	}
    	else if (qName.equalsIgnoreCase("User")) {
    		inUser = false;
    	}
    	else if (qName.equalsIgnoreCase("Password")) {
    		inPassword = false;
    	}
    }
    
    public Session getSession() {
    	return this.session;
    }
}
