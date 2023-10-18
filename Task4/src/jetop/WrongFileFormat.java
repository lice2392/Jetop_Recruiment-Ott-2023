package jetop;

public class WrongFileFormat extends Exception{
	private static final long serialVersionUID = 1L;
	public WrongFileFormat() {}
	public WrongFileFormat(String msg) {
		super(msg);
	}
}
