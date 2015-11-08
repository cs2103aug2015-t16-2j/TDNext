package tdnext;
//@@author Tan Wei Ming
public class TDNextException extends Exception {
	
	public TDNextException(){
		super();
	}
	
	public TDNextException(String message){
		super(message);
	}
	
	public TDNextException(Throwable cause){
		super(cause);
	}
	
	public TDNextException(String message, Throwable cause){
		super(message,cause);
	}
}
