package model;

public class InputValidator {
	
	public void validateMonth (String name) throws Exception {
		if (name.matches("[a-zA-Z]+") ) {
			throw new Exception ("Entry must be digits");
		}
		if (Integer.parseInt(name) > 12 || Integer.parseInt(name) < 1 ) throw new Exception ("Months value has to be between 1 and 12");
	}
	
	public void validateYear (String name) throws Exception {
		if (name.matches("[a-zA-Z]+") ) {
			throw new Exception ("Entry must be digits");
		}

	}

}
