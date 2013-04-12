package controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import ui.MainWindow;

import business.EmailSender;
import domain.Session;

@SuppressWarnings("unused")
public class MainController {
	private Session ses;
	private EmailSender sen = new EmailSender();
	public MainController()
	{
		newSession();
	}
	
	/// -------------------------
	public void newSession()
	{
		ses = new Session();
	}
	public void openSession(String filename) throws Exception
	{
		//TODO: validate session, filename
		ses = new Session(filename);
		//sen = new EmailSender();
	}
	public void saveSession(String filename) throws TransformerException, ParserConfigurationException
	{
		ses.saveXML(filename);
	}
	
	public void runSession()
	{
		
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

	public void setMailCounterObserver(MainWindow mainWindow) {
		sen.setMailCounterObserver(mainWindow);
	}
}
