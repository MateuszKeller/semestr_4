package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JComboBox<String> monthsCombo; 



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
	}


	/**
	 * Initialize the contents of the frame.
	 */
	public JSplitPane createCalendarOptionsPane () {
		JSplitPane calendarView = new JSplitPane();
		calendarTable = new CalendarTable(); 
		calendarView.setLeftComponent(calendarTable);
		
		JPanel calendarOptionsPane = new JPanel(); 
		JLabel options = new JLabel("calendar options"); 	
		
		monthsCombo = new JComboBox<String>(months);
		//monthsCombo.setPreferredSize(new Dimension(10,10)); ustawiæ wielkoœæ comboboxa!!!
		
		calendarOptionsPane.setLayout(new BoxLayout(calendarOptionsPane, BoxLayout.Y_AXIS ));
		calendarOptionsPane.add(options);
		calendarOptionsPane.add(monthsCombo);
		calendarOptionsPane.add(addEventButtonCal);
		calendarOptionsPane.add(Box.createVerticalGlue());
		
		
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
				control.changeDisplayedDate(monthsCombo.getSelectedIndex());
				System.out.println("wykonano");
				
			}
		});
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMain = new JMenu("Main");
		menuBar.add(mnMain);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnMain.add(mntmSettings);
		mntmSettings.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				control.showSettingsWindow();			
			}		
		});
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mnMain.add(mntmClose);
		
		JMenu mnImport = new JMenu("Import");
		menuBar.add(mnImport);
		
		JMenuItem mntmFromXML = new JMenuItem("from XML");
		mnImport.add(mntmFromXML);
		mntmFromXML.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showFromXMLWindow();			
			}			
		});
		
		JMenuItem mntmFromOutlook = new JMenuItem("from Outlook");
		mnImport.add(mntmFromOutlook);
		mntmFromOutlook.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showFromOutlookWindow();			
			}			
		});
		
		JMenuItem mntmFromDatabase = new JMenuItem("from Database");
		mnImport.add(mntmFromDatabase);
		mntmFromDatabase.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showFromDatabaseWindow();			
			}			
		});
		
		JMenu mnExport = new JMenu("Export");
		menuBar.add(mnExport);
		
		JMenuItem mntmToXML = new JMenuItem("to XML");
		mnExport.add(mntmToXML);
		mntmToXML.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showToXMLWindow();			
			}			
		});
		
		JMenuItem mntmToOutlook = new JMenuItem("to Outlook");
		mnExport.add(mntmToOutlook);
		mntmToOutlook.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showToOutlookWindow();			
			}			
		});
		
		JMenuItem mntmToDatabase = new JMenuItem("to Database");
		mnExport.add(mntmToDatabase);
		mntmToDatabase.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showToDatabaseWindow();			
			}			
		});
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAboutProgram = new JMenuItem("About Program");
		mnHelp.add(mntmAboutProgram);
		mntmAboutProgram.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showAboutProgramWindow();			
			}			
		});

		addEventButtonCal = new JButton("add event");
		addEventButtonEv = new JButton("add event");
		addContactButton = new JButton("add contact");
		
		JTabbedPane mainPane = new JTabbedPane(JTabbedPane.TOP);
		frame.add(mainPane);
		
		mainPane.addTab("Calendar", createCalendarOptionsPane());
		mainPane.addTab("Contacts", createContactsOptionsPane());
		mainPane.addTab("Events", createEventsOptionsPane());
		
		
		//JPanel optionsView = new ComboBoxMonth();
		
		//JComponent newContentPane = new ComboBoxMonth();
		//newContentPane.setOpaque(true);
		//optionsView.setCon
		
		//ComboBoxMonth box1 = new ComboBoxMonth();
		//optionsView.add(box1);
		
		/*optionsView.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		optionsView.add(box1, constraints);*/
		
	}

}
