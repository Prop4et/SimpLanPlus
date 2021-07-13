package exceptions;
//exception used in case of double declaration
public class NotDeclaredException extends Exception{
	//default id
	private static final long serialVersionUID = 1L;

	public NotDeclaredException(String msg) {
		super(msg);
	}
}
