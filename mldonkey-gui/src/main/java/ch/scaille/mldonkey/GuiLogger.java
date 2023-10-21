package ch.scaille.mldonkey;

public class GuiLogger {

	private final Class<?> clazz;

	public GuiLogger(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void log(final String msg) {
		System.err.println(clazz.getName() + ": " + msg); // NOSONAR
	}

	public void log(String msg, Exception e) {
		log(msg);
		e.printStackTrace();
	}

	public void log(Exception e) {
		e.printStackTrace();		
	}

}
