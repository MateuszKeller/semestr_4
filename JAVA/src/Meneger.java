import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalTime;

public class Meneger {

//	@SuppressWarnings("null")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		ArrayList<Event> Eventy = new ArrayList<Event>();
		
		LocalDateTime Date = LocalDateTime.now();
	    System.out.println(Date);
	    DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm");
	    
	    String FDate = Date.format(DateFormat);
	    System.out.println(FDate);
	    
	    LocalDateTime SDate = LocalDateTime.of(2019, 5, 8, 1, 57);
	    System.out.println(SDate.toString());
	    
	    FDate = SDate.format(DateFormat);
	    System.out.println(FDate);
	    System.out.println("--------------------");
	    
	    LocalTime T = LocalTime.of(0, 30);
	    Eventy.add(new Event("MAMA", SDate, Date, "BABCIA", "TATA", T) );
	    
	    System.out.println(Eventy.get(0).toString());
	    
		
	}
}
