package controller;

import java.util.regex.Pattern;

import domain.Session;

public class Validator {
	private static final Pattern rfc2822 = Pattern.compile(
	        "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
	);
	
	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	public static boolean isBoolean(String str) {
		if (str.equals("true") || str.equals("false")){
			return true; 
		}
		return false;
	}
	
	public void validateEmail(String email) throws Exception {
		if (!rfc2822.matcher(email).matches()) {
		    throw new Exception("Invalid address");
		}
	}
	
	public void validateVia(String via) throws Exception {
		if (!via.equals("smtp") || !via.equals("wcf")) {
			throw new Exception("Invalid mail transfer protocol");
		}
	}
		
	public void validatePort(String port) throws Exception {
		if (!isNumeric(port)) {
			throw new Exception("Invalid port");
		}
		Integer intPort = Integer.valueOf(port);
		if (intPort < 0 || intPort > 65535) {
			throw new Exception("Invalid port");
		}
	}
	
	public void validatePort(Integer port) throws Exception {
		if (port < 0 || port > 65535) {
			throw new Exception("Invalid port");
		} 
	}
	
	public void validateSession(Session ses) throws Exception {
		validatePort(ses.getServerPort());
		validateEmail(ses.getFrom());
		if(ses.getTo().size()==0)
			throw new Exception("Missing address.");
		for (String to : ses.getTo()) {
			validateEmail(to);
		}
	}
}
	
