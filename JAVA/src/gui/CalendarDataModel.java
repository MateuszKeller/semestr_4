package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import dane.Event;

public class CalendarDataModel extends AbstractTableModel {
	
	private LocalDate dateToDisplay = LocalDate.now(); 
	private List<Event> eventsToDisplay = new ArrayList<Event>();
	private Map<Integer, List<Event>> eventsAcrossDays = new HashMap<>();

	public void setDataToDisplay(LocalDate dateToDisplay, List<Event> events) {
		this.dateToDisplay = dateToDisplay;
		this.eventsToDisplay = events;
		
		eventsAcrossDays = new HashMap<>();
        for (int day = 1; day <= dateToDisplay.lengthOfMonth(); ++day) {
            LocalDateTime dayBegin = LocalDateTime.of(dateToDisplay.getYear(), dateToDisplay.getMonth(), day, 0,0,1);
            LocalDateTime dayEnd = LocalDateTime.of(dateToDisplay.getYear(), dateToDisplay.getMonth(), day, 23,59,59);
            for (Event event : events) {
                if(event.getStart().isBefore(dayEnd) && event.getEnd().isAfter(dayBegin)) {
                    eventsAcrossDays.computeIfAbsent(day, none -> new ArrayList<>()).add(event);
                }
            }
        }
	}

	private static final long serialVersionUID = -7100146141891377597L;
	
	private static String[] weekdays = new String[]{
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
			};
	
	@Override
	public Class<?> getColumnClass(int arg0) {
		return CalendarDay.class;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public String getColumnName(int i) {
		return weekdays[i];
	}

	@Override
	public int getRowCount() {
		return 6;
	}

	private int getDayAt(int row, int col) {
        int firstDay = dateToDisplay.minusDays(dateToDisplay.getDayOfMonth()).getDayOfWeek().getValue()-1;
        int cellNumber = row*7 + col;
        return cellNumber - firstDay;
    }
	
	@Override
	public Object getValueAt(int row, int col) {
		int dayOfMonth = getDayAt(row, col);

        String dayLabel = dayOfMonth < 1 || dayOfMonth > dateToDisplay.lengthOfMonth()
                ? "-"
                : String.valueOf(dayOfMonth);
        List<Event> eventsAtDay = eventsAcrossDays.get(dayOfMonth);

        return new CalendarDay(dayLabel, eventsAtDay);
	}
}
