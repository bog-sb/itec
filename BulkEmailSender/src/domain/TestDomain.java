package domain;

import domain.Session;

public class TestDomain {

	public static void testXMLSession() {
		try {
			Session.validateXML("testsession.xml");
			Session session = new Session("testsession.xml");
			session.saveXML("copy.xml");
			Session.validateXML("copy.xml");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		testXMLSession();
	}
}
