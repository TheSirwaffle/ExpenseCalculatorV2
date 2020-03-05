package main.budget;

public class DoubleLimiter {
	
	public static double limitToTwoDigitsAfterPeriod(double value) {
		String string = Double.toString(value);
		if(string.contains(".")) {
			int index = string.indexOf(".");
			if(string.length() > index + 3) {
				string = string.substring(0, index + 3);
			}
		}
		return Double.parseDouble(string);
	}

}
