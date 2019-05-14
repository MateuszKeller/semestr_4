package system;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dane.Event;

import java.time.LocalTime;

public class Meneger {

//	@SuppressWarnings("null")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Event> eventy = new ArrayList<Event>();
		
		LocalDateTime date = LocalDateTime.now();
	    System.out.println(date);
	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm");
	    
	    String fDate = date.format(dateFormat);
	    System.out.println(fDate);
	    
	    LocalDateTime sDate = LocalDateTime.of(2019, 5, 8, 1, 57);
	    System.out.println(sDate.toString());
	    
	    fDate = sDate.format(dateFormat);
	    System.out.println(fDate);
	    System.out.println("--------------------");
	    
	    LocalTime t = LocalTime.of(0, 30);
	    eventy.add(new Event("Test", sDate, date, "Notatka", "Miejsce", t) );
	    
	    System.out.println(eventy.get(0).toString());
	    
		
	}
}
