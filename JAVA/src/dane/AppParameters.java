package dane;

public class AppParameters {
	
	private static AppParameters parameters = null;
	private String sound;
	
	public String getSound() {
		return sound;
	}
	 
	public static void initialize(String [] arg) {
		parameters = new AppParameters(arg[0]);
	}
	private AppParameters(String arg) {
		sound = arg;
	}
	
	public static AppParameters getInstance() {
		return parameters;
	}
	
	
	
}
