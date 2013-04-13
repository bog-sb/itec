package controller;

import java.io.IOException;
import java.util.Observer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import business.EmailSender;
import domain.Session;

@SuppressWarnings("unused")
public class MainController {
	private Session ses;
	private EmailSender sen = new EmailSender();
	private Validator validator;
	
	public MainController()
	{
		newSession();
		validator = new Validator();
	}
	
	/// -------------------------
	public void newSession()
	{
		ses = new Session();
	}
	public void openSession(String filename) throws Exception
	{
		ses = new Session(filename);
		//sen = new EmailSender();
	}
	public void saveSession(String filename) throws TransformerException, ParserConfigurationException
	{
		ses.saveXML(filename);
	}
	
	public void runSession() throws Exception
	{
		validator.validateSession(ses);
		sen.runSession(ses);
	}
	public void stopSession()
	{
		sen.stopActiveSession();
	}
	public Session getSession()
	{
		return ses;
	}
	
	public void setMailCounterObserver(Observer o){
		sen.setMailCounterObserver(o);
	}
}
