package gui;

import java.time.LocalDate;

import javax.swing.table.AbstractTableModel;

public class CalendarDataModel extends AbstractTableModel {

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
		LocalDate now = LocalDate.of(2019, 6, 15);
		int firstDay = now.minusDays(now.getDayOfMonth()).getDayOfWeek().getValue()-1;
		int cellNumber = row*7 + col;
		int daysInMonth = now.lengthOfMonth();
		String empty = "-";
		int currentCell = cellNumber - firstDay;
		if(currentCell < 1 || currentCell > daysInMonth)
			return empty;
		else
		return String.valueOf(currentCell);
	}

}
