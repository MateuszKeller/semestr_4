package dane;

public class AppParameters {
	
	private static AppParameters parameters = null;
	public static String sound;
	
//	public String getSound() {
//		return sound;
//	}
//	
	private AppParameters(String arg) {
		sound = arg;
	}
	
	public static AppParameters getInstance(String args) {
		if (AppParameters.parameters == null) {
			parameters = new AppParameters(args);
		}
		return parameters;
	}
	
	
	
}
