package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dane.Event;

public class CalendarDataModel extends AbstractTableModel {
	
	private LocalDate dateToDisplay = LocalDate.now(); 
	private List<Event> eventsToDisplay = new ArrayList<Event>();

	public void setDataToDisplay(LocalDate dateToDisplay, List<Event> events) {
		this.dateToDisplay = dateToDisplay;
		this.eventsToDisplay = events;
	}

	private static final long serialVersionUID = -7100146141891377597L;
	
	private static String[] weekdays = new String[]{
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
			};
	
	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
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

	@Override
	public Object getValueAt(int row, int col) {
		String msg; 
		int currentCell = calculateCellNumber(row, col);
		msg = String.valueOf(currentCell);
		if(currentCell != 0) {
		LocalDateTime now = LocalDateTime.of(dateToDisplay.getYear(), dateToDisplay.getMonthValue(), currentCell, 0, 0, 1);
		LocalDateTime now1 = LocalDateTime.of(dateToDisplay.getYear(), dateToDisplay.getMonthValue(), currentCell, 23, 59, 59);

		for(int i = 0; i < eventsToDisplay.size(); i++) {
			if(now1.isAfter(eventsToDisplay.get(i).getStart()) && now.isBefore(eventsToDisplay.get(i).getEnd())) {
				msg = msg + " " + eventsToDisplay.get(i).getTittle();
				}
			}
		}
		return msg;
	}
	
	public int calculateCellNumber(int row, int col) {
		int firstDay = dateToDisplay.minusDays(dateToDisplay.getDayOfMonth()).getDayOfWeek().getValue()-1;
		int cellNumber = row*7 + col;
		int daysInMonth = dateToDisplay.lengthOfMonth();
		//String empty = "-";
		int currentCell = cellNumber - firstDay;
		if(currentCell < 1 || currentCell > daysInMonth)
			return 0;
		else
		return currentCell;
	}

}
