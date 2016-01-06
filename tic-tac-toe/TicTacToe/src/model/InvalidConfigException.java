package model;

/**
 * Exception that is thrown when the game config is invalid
 * @author Frederik
 *
 */
public class InvalidConfigException extends RuntimeException {
	private static final long serialVersionUID = -7102087643546228380L;

	public InvalidConfigException() {
		super("There is an error in Model.Config.");
	}

	public InvalidConfigException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidConfigException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidConfigException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public InvalidConfigException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
