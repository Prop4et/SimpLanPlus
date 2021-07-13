package exceptions;
//Exception used in case of type mismatch
public class TypeException extends Exception{
	//default serial id
	private static final long serialVersionUID = 1L;
	
	public TypeException(String msg) {
		super(msg);
	}

}
