package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import dane.Event;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class EventAddingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField TitleTexfield;
	private JTextField NoteTextfield;
	private JTextField PlaceTextfield;
	private JTextField StartDayTextfield;
	private JTextField StartYearTextfield;
	private JTextField StartHourTextfield;
	private JTextField StartMinuteTextfield;
	private JTextField EndDayTextfield;
	private JTextField EndYearTextfield;
	private JTextField EndHourTextfield;
	private JTextField EndMinuteTextfield;
	private JButton okButton;
	private JButton cancelButton;
	private JComboBox<String> StartMonthCombo;
	private JComboBox<String> EndMonthCombo;
	private JCheckBox AlarmRadio;
	private JDateChooser endDateChooser;
	private JDateChooser startDateChooser;
	private String [] months = 
		{"January", "February", "March", "April", 
				"May", "June", "July", "August", 
				"September", "October", "November", "December"};
	private JTextField AlarmTimeTextfield;
	private JComboBox AlarmTimeCombo;
	private String [] alarmOptions = {"minutes", "hours", "days"};
//	private String[]
	
	private boolean okClicked = false;
	private int setAlarm = 0;

	/**
	 * Launch the application.
	 */

	public LocalDateTime convertToLocalDateTime(Date date, LocalTime time) {
		LocalDate newDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		return LocalDateTime.of(newDate, time);
	}
	
	public static Event showDialog() {
		try {
			EventAddingDialog dialog = new EventAddingDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			if (dialog.okClicked) {
				String title = dialog.TitleTexfield.getText();
				String note = dialog.NoteTextfield.getText();
				String place = dialog.PlaceTextfield.getText();
				
				
//				
//				int startYear = Integer.parseInt(dialog.StartYearTextfield.getText());
//				int startMonth = dialog.StartMonthCombo.getSelectedIndex()+1;
//				int startDay = Integer.parseInt(dialog.StartDayTextfield.getText());
				int startHour = Integer.parseInt(dialog.StartHourTextfield.getText());
				int startMinute = Integer.parseInt(dialog.StartMinuteTextfield.getText());
				
				Date startDate = dialog.startDateChooser.getDate();
				LocalTime newStartTime = LocalTime.of(startHour, startMinute);
				
//				if(startYear < 0) {
//					JOptionPane.showMessageDialog(dialog, "Incorrect start year value");
//					dialog.setVisible(true);
//					dialog.okClicked = false;
//				}
//				if(startDay < 0 || startDay > 31) {
//					JOptionPane.showMessageDialog(dialog, "Incorrect start day value");
//					dialog.setVisible(true);
//					dialog.okClicked = false;
//				}
				if(startHour < 0 || startHour > 23) {
					JOptionPane.showMessageDialog(dialog, "Incorrect start hour value");
					dialog.setVisible(true);
					dialog.okClicked = false;
				}
				if(startMinute < 0 || startMinute > 59) {
					JOptionPane.showMessageDialog(dialog, "Incorrect start minute value");
					dialog.setVisible(true);
					dialog.okClicked = false;
				}
				
				LocalDateTime startTime = dialog.convertToLocalDateTime(startDate, newStartTime);
//				int endYear = Integer.parseInt(dialog.EndYearTextfield.getText());
//				
//				int endMonth = dialog.EndMonthCombo.getSelectedIndex()+1;
//				int endDay = Integer.parseInt(dialog.EndDayTextfield.getText());
				
				Date endDate = dialog.endDateChooser.getDate();
				
				int endHour = Integer.parseInt(dialog.EndHourTextfield.getText());
				int endMinute = Integer.parseInt(dialog.EndMinuteTextfield.getText());
				LocalTime newEndTime= LocalTime.of(endHour, endMinute);
//				if(endYear < 0) {
//					JOptionPane.showMessageDialog(dialog, "Incorrect end year value");
//					dialog.setVisible(true);
//					dialog.okClicked = false;
//				}
//				if(endDay < 0 || startDay > 31) {
//					JOptionPane.showMessageDialog(dialog, "Incorrect end day value");
//					dialog.setVisible(true);
//					dialog.okClicked = false;
//				}
				if(endHour < 0 || startHour > 23) {
					JOptionPane.showMessageDialog(dialog, "Incorrect end hour value");
					dialog.setVisible(true);
					dialog.okClicked = false;
				}
				if(endMinute < 0 || startMinute > 59) {
					JOptionPane.showMessageDialog(dialog, "Incorrect end minute value");
					dialog.setVisible(true);
					dialog.okClicked = false;
				}
				
				LocalDateTime endTime = dialog.convertToLocalDateTime(endDate, newEndTime);
				
				if(endTime.isBefore(startTime)) {
					JOptionPane.showMessageDialog(dialog, "Start date cannot be after end date");
					dialog.setVisible(true);
					dialog.okClicked = false;	
				}
				int number;
				int option;
				if(dialog.setAlarm == 1) {
			
					number = Integer.parseInt(dialog.AlarmTimeTextfield.getText());
					option = dialog.AlarmTimeCombo.getSelectedIndex();
					LocalDateTime alarmTime;
					if(option == 0) {
						alarmTime = startTime.minusMinutes(number);
					} else if(option == 1) {
//						alarmTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute );
						alarmTime = startTime.minusHours(number);
					} else {
//						alarmTime = LocalDateTime.of(startYear, startMonth, startDay + number, startHour, startMinute );
						alarmTime = startTime.minus(Period.ofDays(number));
					}
					System.out.println(alarmTime);
					Event event = new Event(title, startTime, endTime, note, place, alarmTime);
					dialog.dispose();
					return event;
				} else {
					Event event = new Event(title, startTime, endTime, note, place,null);
					dialog.dispose();
					return event;
				}
			} else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	

	/**
	 * Create the dialog.
	 */
	public EventAddingDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 521);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{107, 61, 116, 31, 0};
		gbl_contentPanel.rowHeights = new int[]{22, 0, 0, 0, 0, 0, 0, 0, 24, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel EventTitleLabel = new JLabel("Event title:");
			GridBagConstraints gbc_EventTitleLabel = new GridBagConstraints();
			gbc_EventTitleLabel.anchor = GridBagConstraints.WEST;
			gbc_EventTitleLabel.insets = new Insets(0, 0, 5, 5);
			gbc_EventTitleLabel.gridx = 0;
			gbc_EventTitleLabel.gridy = 0;
			contentPanel.add(EventTitleLabel, gbc_EventTitleLabel);
		}
		{
			TitleTexfield = new JTextField();
			GridBagConstraints gbc_TitleTexfield = new GridBagConstraints();
			gbc_TitleTexfield.gridwidth = 2;
			gbc_TitleTexfield.fill = GridBagConstraints.HORIZONTAL;
			gbc_TitleTexfield.anchor = GridBagConstraints.NORTH;
			gbc_TitleTexfield.insets = new Insets(0, 0, 5, 5);
			gbc_TitleTexfield.gridx = 1;
			gbc_TitleTexfield.gridy = 0;
			contentPanel.add(TitleTexfield, gbc_TitleTexfield);
			TitleTexfield.setColumns(10);
		}
		{
			JLabel NoteLabel = new JLabel("Note:");
			GridBagConstraints gbc_NoteLabel = new GridBagConstraints();
			gbc_NoteLabel.insets = new Insets(0, 0, 5, 5);
			gbc_NoteLabel.anchor = GridBagConstraints.WEST;
			gbc_NoteLabel.gridx = 0;
			gbc_NoteLabel.gridy = 1;
			contentPanel.add(NoteLabel, gbc_NoteLabel);
		}
		{
			NoteTextfield = new JTextField();
			GridBagConstraints gbc_NoteTextfield = new GridBagConstraints();
			gbc_NoteTextfield.gridwidth = 2;
			gbc_NoteTextfield.insets = new Insets(0, 0, 5, 5);
			gbc_NoteTextfield.fill = GridBagConstraints.HORIZONTAL;
			gbc_NoteTextfield.gridx = 1;
			gbc_NoteTextfield.gridy = 1;
			contentPanel.add(NoteTextfield, gbc_NoteTextfield);
			NoteTextfield.setColumns(10);
		}
		{
			JLabel PlaceLabel = new JLabel("Place:");
			GridBagConstraints gbc_PlaceLabel = new GridBagConstraints();
			gbc_PlaceLabel.anchor = GridBagConstraints.WEST;
			gbc_PlaceLabel.insets = new Insets(0, 0, 5, 5);
			gbc_PlaceLabel.gridx = 0;
			gbc_PlaceLabel.gridy = 2;
			contentPanel.add(PlaceLabel, gbc_PlaceLabel);
		}
		{
			PlaceTextfield = new JTextField();
			GridBagConstraints gbc_PlaceTextfield = new GridBagConstraints();
			gbc_PlaceTextfield.gridwidth = 2;
			gbc_PlaceTextfield.insets = new Insets(0, 0, 5, 5);
			gbc_PlaceTextfield.fill = GridBagConstraints.HORIZONTAL;
			gbc_PlaceTextfield.gridx = 1;
			gbc_PlaceTextfield.gridy = 2;
			contentPanel.add(PlaceTextfield, gbc_PlaceTextfield);
			PlaceTextfield.setColumns(10);
		}
		{
			JLabel StartTimeLabel = new JLabel("Start time:");
			GridBagConstraints gbc_StartTimeLabel = new GridBagConstraints();
			gbc_StartTimeLabel.anchor = GridBagConstraints.WEST;
			gbc_StartTimeLabel.insets = new Insets(0, 0, 5, 5);
			gbc_StartTimeLabel.gridx = 0;
			gbc_StartTimeLabel.gridy = 3;
			contentPanel.add(StartTimeLabel, gbc_StartTimeLabel);
		}
		{
			JLabel startDateLabel = new JLabel("Date:");
			GridBagConstraints gbc_startDateLabel = new GridBagConstraints();
			gbc_startDateLabel.anchor = GridBagConstraints.NORTHWEST;
			gbc_startDateLabel.insets = new Insets(0, 0, 5, 5);
			gbc_startDateLabel.gridx = 1;
			gbc_startDateLabel.gridy = 5;
			contentPanel.add(startDateLabel, gbc_startDateLabel);
		}
		{
			startDateChooser = new JDateChooser();
			GridBagConstraints gbc_startDateChooser = new GridBagConstraints();
			gbc_startDateChooser.anchor = GridBagConstraints.NORTH;
			gbc_startDateChooser.insets = new Insets(0, 0, 5, 5);
			gbc_startDateChooser.fill = GridBagConstraints.HORIZONTAL;
			gbc_startDateChooser.gridx = 2;
			gbc_startDateChooser.gridy = 5;
			contentPanel.add(startDateChooser, gbc_startDateChooser);
		}
		
		
//		{
//			JLabel StartDayLabel = new JLabel("Day:");
//			GridBagConstraints gbc_StartDayLabel = new GridBagConstraints();
//			gbc_StartDayLabel.insets = new Insets(0, 0, 5, 5);
//			gbc_StartDayLabel.anchor = GridBagConstraints.WEST;
//			gbc_StartDayLabel.gridx = 1;
//			gbc_StartDayLabel.gridy = 4;
//			contentPanel.add(StartDayLabel, gbc_StartDayLabel);
//		}
//		{
//			StartDayTextfield = new JTextField();
//			GridBagConstraints gbc_StartDayTextfield = new GridBagConstraints();
//			gbc_StartDayTextfield.insets = new Insets(0, 0, 5, 5);
//			gbc_StartDayTextfield.fill = GridBagConstraints.HORIZONTAL;
//			gbc_StartDayTextfield.gridx = 2;
//			gbc_StartDayTextfield.gridy = 4;
//			contentPanel.add(StartDayTextfield, gbc_StartDayTextfield);
//			StartDayTextfield.setColumns(10);
//		}
//		{
//			JLabel StartMonthLabel = new JLabel("Month:");
//			GridBagConstraints gbc_StartMonthLabel = new GridBagConstraints();
//			gbc_StartMonthLabel.insets = new Insets(0, 0, 5, 5);
//			gbc_StartMonthLabel.anchor = GridBagConstraints.WEST;
//			gbc_StartMonthLabel.gridx = 1;
//			gbc_StartMonthLabel.gridy = 5;
//			contentPanel.add(StartMonthLabel, gbc_StartMonthLabel);
//		}
//		{
//			StartMonthCombo = new JComboBox<String>(months);
//			GridBagConstraints gbc_StartMonthCombo = new GridBagConstraints();
//			gbc_StartMonthCombo.insets = new Insets(0, 0, 5, 5);
//			gbc_StartMonthCombo.fill = GridBagConstraints.HORIZONTAL;
//			gbc_StartMonthCombo.gridx = 2;
//			gbc_StartMonthCombo.gridy = 5;
//			contentPanel.add(StartMonthCombo, gbc_StartMonthCombo);
//		}
//		{
//			JLabel StartYearLabel = new JLabel("Year:");
//			GridBagConstraints gbc_StartYearLabel = new GridBagConstraints();
//			gbc_StartYearLabel.insets = new Insets(0, 0, 5, 5);
//			gbc_StartYearLabel.anchor = GridBagConstraints.WEST;
//			gbc_StartYearLabel.gridx = 1;
//			gbc_StartYearLabel.gridy = 6;
//			contentPanel.add(StartYearLabel, gbc_StartYearLabel);
//		}
//		{
//			StartYearTextfield = new JTextField();
//			GridBagConstraints gbc_StartYearTextfield = new GridBagConstraints();
//			gbc_StartYearTextfield.insets = new Insets(0, 0, 5, 5);
//			gbc_StartYearTextfield.fill = GridBagConstraints.HORIZONTAL;
//			gbc_StartYearTextfield.gridx = 2;
//			gbc_StartYearTextfield.gridy = 6;
//			contentPanel.add(StartYearTextfield, gbc_StartYearTextfield);
//			StartYearTextfield.setColumns(10);
//		}
		{
			JLabel StartHourLabel = new JLabel("Hour:");
			GridBagConstraints gbc_StartHourLabel = new GridBagConstraints();
			gbc_StartHourLabel.anchor = GridBagConstraints.WEST;
			gbc_StartHourLabel.insets = new Insets(0, 0, 5, 5);
			gbc_StartHourLabel.gridx = 1;
			gbc_StartHourLabel.gridy = 7;
			contentPanel.add(StartHourLabel, gbc_StartHourLabel);
		}
		{
			StartHourTextfield = new JTextField();
			GridBagConstraints gbc_StartHourTextfield = new GridBagConstraints();
			gbc_StartHourTextfield.insets = new Insets(0, 0, 5, 5);
			gbc_StartHourTextfield.fill = GridBagConstraints.HORIZONTAL;
			gbc_StartHourTextfield.gridx = 2;
			gbc_StartHourTextfield.gridy = 7;
			contentPanel.add(StartHourTextfield, gbc_StartHourTextfield);
			StartHourTextfield.setColumns(10);
		}
		{
			JLabel StartMinuteLabel = new JLabel("Minute:");
			GridBagConstraints gbc_StartMinuteLabel = new GridBagConstraints();
			gbc_StartMinuteLabel.anchor = GridBagConstraints.WEST;
			gbc_StartMinuteLabel.insets = new Insets(0, 0, 5, 5);
			gbc_StartMinuteLabel.gridx = 1;
			gbc_StartMinuteLabel.gridy = 8;
			contentPanel.add(StartMinuteLabel, gbc_StartMinuteLabel);
		}
		{
			StartMinuteTextfield = new JTextField();
			GridBagConstraints gbc_StartMinuteTextfield = new GridBagConstraints();
			gbc_StartMinuteTextfield.fill = GridBagConstraints.HORIZONTAL;
			gbc_StartMinuteTextfield.anchor = GridBagConstraints.NORTH;
			gbc_StartMinuteTextfield.insets = new Insets(0, 0, 5, 5);
			gbc_StartMinuteTextfield.gridx = 2;
			gbc_StartMinuteTextfield.gridy = 8;
			contentPanel.add(StartMinuteTextfield, gbc_StartMinuteTextfield);
			StartMinuteTextfield.setColumns(10);
		}
		{
			JLabel EntTimeLabel = new JLabel("End Time:");
			GridBagConstraints gbc_EntTimeLabel = new GridBagConstraints();
			gbc_EntTimeLabel.anchor = GridBagConstraints.WEST;
			gbc_EntTimeLabel.insets = new Insets(0, 0, 5, 5);
			gbc_EntTimeLabel.gridx = 0;
			gbc_EntTimeLabel.gridy = 9;
			contentPanel.add(EntTimeLabel, gbc_EntTimeLabel);
		}
		/////////////////////
		{
			JLabel startDateLabel = new JLabel("Date:");
			GridBagConstraints gbc_startDateLabel = new GridBagConstraints();
			gbc_startDateLabel.anchor = GridBagConstraints.NORTHWEST;
			gbc_startDateLabel.insets = new Insets(0, 0, 5, 5);
			gbc_startDateLabel.gridx = 1;
			gbc_startDateLabel.gridy = 10;
			contentPanel.add(startDateLabel, gbc_startDateLabel);
		}
		{
			endDateChooser = new JDateChooser();
			GridBagConstraints gbc_endDateChooser = new GridBagConstraints();
			gbc_endDateChooser.anchor = GridBagConstraints.NORTH;
			gbc_endDateChooser.insets = new Insets(0, 0, 5, 5);
			gbc_endDateChooser.fill = GridBagConstraints.HORIZONTAL;
			gbc_endDateChooser.gridx = 2;
			gbc_endDateChooser.gridy = 10;
			contentPanel.add(endDateChooser, gbc_endDateChooser);
		}
		////////////////////////////////////
		
//		{
//			JLabel EndDayLabel = new JLabel("Day:");
//			GridBagConstraints gbc_EndDayLabel = new GridBagConstraints();
//			gbc_EndDayLabel.anchor = GridBagConstraints.WEST;
//			gbc_EndDayLabel.insets = new Insets(0, 0, 5, 5);
//			gbc_EndDayLabel.gridx = 1;
//			gbc_EndDayLabel.gridy = 10;
//			contentPanel.add(EndDayLabel, gbc_EndDayLabel);
//		}
//		{
//			EndDayTextfield = new JTextField();
//			GridBagConstraints gbc_EndDayTextfield = new GridBagConstraints();
//			gbc_EndDayTextfield.insets = new Insets(0, 0, 5, 5);
//			gbc_EndDayTextfield.fill = GridBagConstraints.HORIZONTAL;
//			gbc_EndDayTextfield.gridx = 2;
//			gbc_EndDayTextfield.gridy = 10;
//			contentPanel.add(EndDayTextfield, gbc_EndDayTextfield);
//			EndDayTextfield.setColumns(10);
//		}
//		{
//			JLabel EndMonthLabel = new JLabel("Month:");
//			GridBagConstraints gbc_EndMonthLabel = new GridBagConstraints();
//			gbc_EndMonthLabel.anchor = GridBagConstraints.WEST;
//			gbc_EndMonthLabel.insets = new Insets(0, 0, 5, 5);
//			gbc_EndMonthLabel.gridx = 1;
//			gbc_EndMonthLabel.gridy = 11;
//			contentPanel.add(EndMonthLabel, gbc_EndMonthLabel);
//		}
//		{
//			EndMonthCombo = new JComboBox(months);
//			GridBagConstraints gbc_EndMonthCombo = new GridBagConstraints();
//			gbc_EndMonthCombo.insets = new Insets(0, 0, 5, 5);
//			gbc_EndMonthCombo.fill = GridBagConstraints.HORIZONTAL;
//			gbc_EndMonthCombo.gridx = 2;
//			gbc_EndMonthCombo.gridy = 11;
//			contentPanel.add(EndMonthCombo, gbc_EndMonthCombo);
//		}
//		{
//			JLabel EndYearLabel = new JLabel("Year:");
//			GridBagConstraints gbc_EndYearLabel = new GridBagConstraints();
//			gbc_EndYearLabel.anchor = GridBagConstraints.WEST;
//			gbc_EndYearLabel.insets = new Insets(0, 0, 5, 5);
//			gbc_EndYearLabel.gridx = 1;
//			gbc_EndYearLabel.gridy = 12;
//			contentPanel.add(EndYearLabel, gbc_EndYearLabel);
//		}
//		{
//			EndYearTextfield = new JTextField();
//			GridBagConstraints gbc_EndYearTextfield = new GridBagConstraints();
//			gbc_EndYearTextfield.insets = new Insets(0, 0, 5, 5);
//			gbc_EndYearTextfield.fill = GridBagConstraints.HORIZONTAL;
//			gbc_EndYearTextfield.gridx = 2;
//			gbc_EndYearTextfield.gridy = 12;
//			contentPanel.add(EndYearTextfield, gbc_EndYearTextfield);
//			EndYearTextfield.setColumns(10);
//		}
		{
			JLabel EndHourLabel = new JLabel("Hour:");
			GridBagConstraints gbc_EndHourLabel = new GridBagConstraints();
			gbc_EndHourLabel.anchor = GridBagConstraints.WEST;
			gbc_EndHourLabel.insets = new Insets(0, 0, 5, 5);
			gbc_EndHourLabel.gridx = 1;
			gbc_EndHourLabel.gridy = 13;
			contentPanel.add(EndHourLabel, gbc_EndHourLabel);
		}
		{
			EndHourTextfield = new JTextField();
			GridBagConstraints gbc_EndHourTextfield = new GridBagConstraints();
			gbc_EndHourTextfield.insets = new Insets(0, 0, 5, 5);
			gbc_EndHourTextfield.fill = GridBagConstraints.HORIZONTAL;
			gbc_EndHourTextfield.gridx = 2;
			gbc_EndHourTextfield.gridy = 13;
			contentPanel.add(EndHourTextfield, gbc_EndHourTextfield);
			EndHourTextfield.setColumns(10);
		}
		{
			JLabel EndMinuteLabel = new JLabel("Minute:");
			GridBagConstraints gbc_EndMinuteLabel = new GridBagConstraints();
			gbc_EndMinuteLabel.anchor = GridBagConstraints.WEST;
			gbc_EndMinuteLabel.insets = new Insets(0, 0, 5, 5);
			gbc_EndMinuteLabel.gridx = 1;
			gbc_EndMinuteLabel.gridy = 14;
			contentPanel.add(EndMinuteLabel, gbc_EndMinuteLabel);
		}
		{
			EndMinuteTextfield = new JTextField();
			GridBagConstraints gbc_EndMinuteTextfield = new GridBagConstraints();
			gbc_EndMinuteTextfield.insets = new Insets(0, 0, 5, 5);
			gbc_EndMinuteTextfield.fill = GridBagConstraints.HORIZONTAL;
			gbc_EndMinuteTextfield.gridx = 2;
			gbc_EndMinuteTextfield.gridy = 14;
			contentPanel.add(EndMinuteTextfield, gbc_EndMinuteTextfield);
			EndMinuteTextfield.setColumns(10);
		}
		{
			AlarmRadio = new JCheckBox("Alarm");
			GridBagConstraints gbc_AlarmRadio = new GridBagConstraints();
			gbc_AlarmRadio.insets = new Insets(0, 0, 0, 5);
			gbc_AlarmRadio.gridx = 0;
			gbc_AlarmRadio.gridy = 16;
			AlarmRadio.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					setAlarm = e.getStateChange();
				}
			});
			AlarmRadio.setMnemonic(KeyEvent.VK_C);
			contentPanel.add(AlarmRadio, gbc_AlarmRadio);
		}
		{
			AlarmTimeCombo = new JComboBox<String>(alarmOptions);
			GridBagConstraints gbc_AlarmTimeCombo = new GridBagConstraints();
			gbc_AlarmTimeCombo.insets = new Insets(0, 0, 0, 5);
			gbc_AlarmTimeCombo.fill = GridBagConstraints.HORIZONTAL;
			gbc_AlarmTimeCombo.gridx = 2;
			gbc_AlarmTimeCombo.gridy = 16;
			contentPanel.add(AlarmTimeCombo, gbc_AlarmTimeCombo);
		}
		{
			AlarmTimeTextfield = new JTextField();
			GridBagConstraints gbc_AlarmTimeTextfield = new GridBagConstraints();
			gbc_AlarmTimeTextfield.insets = new Insets(0, 0, 5, 5);
			gbc_AlarmTimeTextfield.fill = GridBagConstraints.HORIZONTAL;
			gbc_AlarmTimeTextfield.gridx = 1;
			gbc_AlarmTimeTextfield.gridy = 16;
			contentPanel.add(AlarmTimeTextfield, gbc_AlarmTimeTextfield);
			EndMinuteTextfield.setColumns(10);
		}
		{
			JLabel BeforeLabel = new JLabel("before");
			GridBagConstraints gbc_BeforeLabel = new GridBagConstraints();
			gbc_BeforeLabel.insets = new Insets(0, 0, 0, 5);
			gbc_BeforeLabel.gridx = 3;
			gbc_BeforeLabel.gridy = 16;
			contentPanel.add(BeforeLabel, gbc_BeforeLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			okButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					okClicked = true;
					setVisible(false);
				}
			});
			
			cancelButton.addActionListener(new ActionListener() {
				
				@Override
				
				public void actionPerformed(ActionEvent e) {
					okClicked = false; 
					setVisible(false);
				}
			});
		}
	}

}
