import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Alarm {
	
	LocalTime Before;
	private DateTimeFormatter TimeFormat; // = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm");
	
	//DŸwiêki //
	public Alarm(LocalTime T) { Before = T; }
	
	public String toString()
	{
		String RET = "";
		if(Before.getHour() == 0)
		{
			TimeFormat = DateTimeFormatter.ofPattern("m");
			RET += Before.format(TimeFormat) + " minut przed"; 
		}
		else
		{
			TimeFormat = DateTimeFormatter.ofPattern("H");
			RET += Before.format(TimeFormat) + " godzin przed";
		}
			
		return RET;
	}

}
