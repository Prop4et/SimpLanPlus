package exceptions;
//exception used in case of double declaration
public class AlreadyDeclaredException extends Exception{
	//default id
	private static final long serialVersionUID = 1L;

	public AlreadyDeclaredException(String msg) {
		super(msg);
	}
}
