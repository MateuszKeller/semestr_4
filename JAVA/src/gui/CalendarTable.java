package gui;

import java.awt.Color;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import system.DisplayedDateChangeListener;

public class CalendarTable extends JTable implements DisplayedDateChangeListener{
	
	CalendarTable(){
		super(new CalendarDataModel());
		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(false);
		setSelectionBackground(Color.CYAN);
	}

	@Override
	public void dateChange(LocalDate newDate) {
		// TODO Auto-generated method stub
	}
	
	

}
