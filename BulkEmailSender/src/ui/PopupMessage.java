package ui;

public class PopupMessage {
	private static PopupMessage instance = null;

	protected PopupMessage() {
		
	}

	public static PopupMessage getInstance() {
		if (instance == null) {
			instance = new PopupMessage();
		}
		return instance;
	}
	
	
}
