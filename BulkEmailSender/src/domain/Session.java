package domain;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;


public class Session {
	private String from, subject, message;
	private Vector<String> to, attachments;
	private String via; // smtp etc.
	private Integer quantity; // number of emails
	private Integer delay; // miliseconds
	private Boolean counter, randomSubject, randomMessage;
	private String server, user, password;
	private Integer serverPort;
	
	
	public Session()
	{
		to = new Vector<String>();
		attachments = new Vector<String>();
		from = "";
		subject = "";
		message = "";
		via = "smtp";
		quantity = 0;
		delay = 0;
		counter = false;
		randomSubject = true;
		randomMessage = false;
		server = "";
		user = "";
		password = "";
		serverPort = 0;
	}
	
	public Session(String XMLPath) throws IOException, SAXException,
			ParserConfigurationException
	{
		to = new Vector<String>();
		attachments = new Vector<String>();
		this.openXML(XMLPath);
	}
	
	public static void validateXML(String XMLPath) throws IOException, SAXException {
		Source xmlFile = new StreamSource(new File(XMLPath));
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("session.xsd"));
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}
	
	public void openXML(String XMLPath) throws IOException, SAXException,
			ParserConfigurationException {
		//Create a "parser factory" for creating SAX parsers
        SAXParserFactory spfac = SAXParserFactory.newInstance();

        //Now use the parser factory to create a SAXParser object
        SAXParser sp = spfac.newSAXParser();

        //Create an instance of this class; it defines all the handler methods
        XMLSessionParser handler = new XMLSessionParser(this);

        //Finally, tell the parser to parse the input and notify the handler
        sp.parse(XMLPath, handler); 
	}
	
	public void saveXML(String XMLPath) throws TransformerException, ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		//root elements
		Document doc = docBuilder.newDocument();

		Element rootElement = doc.createElement("session");
		doc.appendChild(rootElement);

		//from elements
		Element from = doc.createElement("from");
		from.appendChild(doc.createTextNode(this.from));
		rootElement.appendChild(from);
		
		//to elements
		Element tos = doc.createElement("tos");
		for (String toValue : this.to) {
			Element to = doc.createElement("to");
			to.appendChild(doc.createTextNode(toValue));
			tos.appendChild(to);
		}
		rootElement.appendChild(tos);
		
		//subject elements
		Element subject = doc.createElement("subject");
		subject.appendChild(doc.createTextNode(this.subject));
		rootElement.appendChild(subject);
		
		//message elements
		Element message = doc.createElement("message");
		message.appendChild(doc.createTextNode(this.message));
		rootElement.appendChild(message);
		
		//to elements
		Element attachments = doc.createElement("attachments");
		for (String attachmentValue : this.attachments) {
			Element attachment = doc.createElement("attachment");
			attachment.appendChild(doc.createTextNode(attachmentValue));
			attachments.appendChild(attachment);
		}
		rootElement.appendChild(attachments);
		
		//via elements
		Element via = doc.createElement("via");
		via.appendChild(doc.createTextNode(this.via));
		rootElement.appendChild(via);
		
		//server element
		Element server = doc.createElement("server");
		server.appendChild(doc.createTextNode(this.server));
		rootElement.appendChild(server);
		
		//serverPort element
		Element serverPort = doc.createElement("serverPort");
		serverPort.appendChild(doc.createTextNode(Integer.toString(this.serverPort)));
		rootElement.appendChild(serverPort);
		
		//user element
		Element user = doc.createElement("user");
		user.appendChild(doc.createTextNode(this.user));
		rootElement.appendChild(user);
		
		//password element
		Element password = doc.createElement("password");
		password.appendChild(doc.createTextNode(this.password));
		rootElement.appendChild(password);
		
		//Quantity elements
		Element quantity = doc.createElement("quantity");
		quantity.appendChild(doc.createTextNode(Integer.toString(this.quantity)));
		rootElement.appendChild(quantity);
		
		//Delay elements
		Element delay = doc.createElement("delay");
		delay.appendChild(doc.createTextNode(Integer.toString(this.delay)));
		rootElement.appendChild(delay);
		
		//Counter elements
		Element counter = doc.createElement("counter");
		counter.appendChild(doc.createTextNode(Boolean.toString(this.counter)));
		rootElement.appendChild(counter);
		
		//RandomSubject elements
		Element randomSubject = doc.createElement("randomSubject");
		randomSubject.appendChild(doc.createTextNode(Boolean.toString(this.randomSubject)));
		rootElement.appendChild(randomSubject);
		
		//RandomSubject elements
		Element randomMessage = doc.createElement("randomMessage");
		randomMessage.appendChild(doc.createTextNode(Boolean.toString(this.randomMessage)));
		rootElement.appendChild(randomMessage);

	
		//write the content into XML file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);

		StreamResult result =  new StreamResult(new File(XMLPath));
		transformer.transform(source, result);
	}
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}


	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Vector<String> getTo() {
		return to;
	}

	public void setTo(Vector<String> to) {
		this.to = to;
	}
	
	public void addTo(String to) {
		this.to.add(to);
	}

	public Vector<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Vector<String> attachments) {
		this.attachments = attachments;
	}
	
	public void addAttachment(String attachment) {
		this.attachments.add(attachment);
	}
	
	public void setCounter(Boolean counter) {
		this.counter = counter;
	}
	
	public void setRandomSubject(Boolean randomSubject) {
		this.randomSubject = randomSubject;
	}


	public boolean getCounter() {
		// TODO Auto-generated method stub
		return this.counter;
	}

	public boolean getRandomSubject() {
		// TODO Auto-generated method stub
		return this.randomSubject;
	}

	public void setRandomMessage(Boolean randomMessage) {
		// TODO Auto-generated method stub
		this.randomMessage = randomMessage;
	}
	
}
