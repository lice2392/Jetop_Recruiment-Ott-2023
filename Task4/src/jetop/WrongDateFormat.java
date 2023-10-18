package jetop;

public class WrongDateFormat extends Exception{
	private static final long serialVersionUID = 1L;
	public WrongDateFormat() {}
	public WrongDateFormat(String msg) {
		super(msg);
	}
}
