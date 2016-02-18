package model;

/**
 * Exception that is thrown when GameJTable.setPlayerAt() is called with a player unequal to Player.Player1 or Player.Player2 or null
 * @author Frederik Kammel
 *
 */
public class InvalidPlayerException extends RuntimeException {
	private static final long serialVersionUID = -7102087643546228380L;

	public InvalidPlayerException() {
		super("The specified player does not equal Player1 nor Player2");
	}

	public InvalidPlayerException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidPlayerException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidPlayerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public InvalidPlayerException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
