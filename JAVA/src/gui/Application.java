package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import system.Controller;
import system.events.DisplayedDateChanged;


public class Application {

	private JFrame frame;
	public JFrame getFrame() {
		return frame;
	}

	//TODO przenieœæ wszystkie mnitemy itd jako pola klasy
	//stworzyæ funkcjê dodaj¹c¹ listenery
	private JMenuBar menuBar;
	private JMenu mnMain;
	private JMenuItem mntmSettings;
	private JMenuItem mntmClose;
	private JMenu mnImport;
	private JMenuItem mntmFromXML;
	private JMenuItem mntmFromOutlook;
	private JMenuItem mntmFromDatabase;
	private JMenu mnExport;
	private JMenuItem mntmToXML;
	private JMenuItem mntmToOutlook;
	private JMenuItem mntmToDatabase;
	private JMenu mnHelp;
	private JMenuItem mntmAboutProgram;
	private JTabbedPane mainPane;

	private CalendarTable calendarTable;
	private JTable contactsTable;
	private Controller control;
	private JButton addEventButtonCal; 
	private JButton addContactButton; 
	private JButton addEventButtonEv;
	private String [] months = 
		{"January", "February", "March", "April", 
				"May", "June", "July", "August", 
				"September", "October", "November", "December"};
	private String [] years = {"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};
	private JComboBox<String> monthsCombo; 
	private JComboBox<String> yearsCombo;



	/**
	 * Launch the application.
	 */
	public void mainApplication(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
		control = new Controller();
		control.registerListener(DisplayedDateChanged.class, calendarTable);

		addCalendarListeners();	
		addMenuListeners();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	public JSplitPane createCalendarOptionsPane () {
		JSplitPane calendarView = new JSplitPane();
		calendarTable = new CalendarTable(); 
		calendarView.setLeftComponent(calendarTable);
		
		JPanel calendarOptionsPane = new JPanel(new GridBagLayout()); 
		GridBagConstraints c = new GridBagConstraints();
		
		LocalDate now = LocalDate.now();
		
		monthsCombo = new JComboBox<String>(months);
		monthsCombo.setSelectedIndex(now.getMonthValue() - 1);
		monthsCombo.setPreferredSize(new Dimension(500,20)); //ustawiæ wielkoœæ comboboxa!!!
		//monthsCombo.setPrototypeDisplayValue("xXXxxxxxxxxx");
		
		c.gridx = 0; 
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER; 
		c.fill = GridBagConstraints.NONE;
		calendarOptionsPane.add(monthsCombo, c);
		//JLabel options = new JLabel("calendar options"); 	
		
		
		yearsCombo = new JComboBox<String>(years);
		yearsCombo.setSelectedIndex(now.getYear() - 2018);
		monthsCombo.setPreferredSize(new Dimension(100,20)); //ustawiæ wielkoœæ comboboxa!!!
		monthsCombo.setPrototypeDisplayValue("xXXxxx");
		c.gridx = 1; 
		c.gridy = 0;
		calendarOptionsPane.add(yearsCombo, c);
		
		c.gridx = 1; 
		c.gridy = 1;
		calendarOptionsPane.add(addEventButtonCal,c);
		
		//calendarOptionsPane.add(options);
		
		
		
		//calendarOptionsPane.add(Box.createVerticalGlue());
		
		
		calendarView.setRightComponent(calendarOptionsPane);
		return calendarView;	
	}
	
	public JSplitPane createContactsOptionsPane () {
		JSplitPane contactsView = new JSplitPane();
		JTable contactTable = new JTable(); 
		contactsView.setLeftComponent(contactTable);
		
		JPanel contactsOptionsPane = new JPanel(); 
		JLabel options = new JLabel("contacts options"); 	
		
		contactsOptionsPane.add(options);
		contactsOptionsPane.add(addContactButton);
		
		contactsView.setRightComponent(contactsOptionsPane);
		return contactsView;
	}
	
	public JSplitPane createEventsOptionsPane () {
		JSplitPane eventsView = new JSplitPane();
		JTable eventsTable = new JTable(); 
		eventsView.setLeftComponent(eventsTable);
		
		JPanel eventsOptionsPane = new JPanel(); 
		JLabel options = new JLabel("contacts options"); 	
		
		eventsOptionsPane.add(options);
		eventsOptionsPane.add(addEventButtonEv);
		
		eventsView.setRightComponent(eventsOptionsPane);
		return eventsView;
	}
	
	
	public void addCalendarListeners() {
		monthsCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stu
				control.changeDisplayedDate(monthsCombo.getSelectedIndex(), yearsCombo.getSelectedIndex());
				System.out.println("wykonano");
				
			}
		});
		
		yearsCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.changeDisplayedDate(monthsCombo.getSelectedIndex(),yearsCombo.getSelectedIndex());
				
			}
		});
	}
	
	public void addMenuListeners() {
		
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control.showSettingsWindow();			
			}		
		});
		
		mntmFromXML.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showFromXMLWindow();			
			}			
		});
		
		mntmFromOutlook.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showFromOutlookWindow();			
			}			
		});
		
		mntmFromDatabase.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showFromDatabaseWindow();			
			}			
		});
		
		mntmToXML.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showToXMLWindow();			
			}			
		});
		
		mntmToOutlook.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showToOutlookWindow();			
			}			
		});
		
		mntmToDatabase.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showToDatabaseWindow();			
			}			
		});
		
		mntmAboutProgram.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showAboutProgramWindow();			
			}			
		});
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnMain = new JMenu("Main");
		menuBar.add(mnMain);
		
		mntmSettings = new JMenuItem("Settings");
		mnMain.add(mntmSettings);
		
		mntmClose = new JMenuItem("Close");
		mnMain.add(mntmClose);
		
		mnImport = new JMenu("Import");
		menuBar.add(mnImport);
		
		mntmFromXML = new JMenuItem("from XML");
		mnImport.add(mntmFromXML);
		
		mntmFromOutlook = new JMenuItem("from Outlook");
		mnImport.add(mntmFromOutlook);
		
		mntmFromDatabase = new JMenuItem("from Database");
		mnImport.add(mntmFromDatabase);
		
		mnExport = new JMenu("Export");
		menuBar.add(mnExport);
		
		mntmToXML = new JMenuItem("to XML");
		mnExport.add(mntmToXML);
		
		mntmToOutlook = new JMenuItem("to Outlook");
		mnExport.add(mntmToOutlook);
		
		mntmToDatabase = new JMenuItem("to Database");
		mnExport.add(mntmToDatabase);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAboutProgram = new JMenuItem("About Program");
		mnHelp.add(mntmAboutProgram);
	
		addEventButtonCal = new JButton("add event");
		addEventButtonEv = new JButton("add event");
		addContactButton = new JButton("add contact");
		
		mainPane = new JTabbedPane(JTabbedPane.TOP);
		frame.add(mainPane);
		
		mainPane.addTab("Calendar", createCalendarOptionsPane());
		mainPane.addTab("Contacts", createContactsOptionsPane());
		mainPane.addTab("Events", createEventsOptionsPane());
		
	}

}
