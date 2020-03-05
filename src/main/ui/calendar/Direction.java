package main.ui.calendar;

public enum Direction {
	
	BACK("<"),
	FORWARD(">");
	
	private String text;
	
	private Direction(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public static Direction fromText(String text) {
		Direction result = null;
		for(Direction direction : Direction.values()) {
			if(direction.getText().equals(text)) {
				result = direction;
			}
		}
		return result;
	}

}
