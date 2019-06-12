package gui;

import java.awt.Color;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import system.DisplayedDateChangeListener;
import system.events.DisplayedDateChanged;
import system.events.InternalEvent;
import system.events.InternalEventListener;
import system.events.ResizeListener;

public class CalendarTable extends JTable implements InternalEventListener{
	
	private CalendarDataModel model;
	
	CalendarTable(){
		super();
		model = new CalendarDataModel();
		super.setModel(model);
		setDefaultRenderer(CalendarDay.class, new CalendarDayRenderer());
		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(false);
//		setSelectionBackground(Color.CYAN);
	}

	@Override
	public void anEventOccurred(InternalEvent e) {
		if (e instanceof DisplayedDateChanged) {
			DisplayedDateChanged newEvent = (DisplayedDateChanged) e; 
			model.setDataToDisplay(newEvent.getNewDate(), newEvent.getEvents());
			this.resizeAndRepaint();
		}		
	}
}
