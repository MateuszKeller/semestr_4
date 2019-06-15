package gui;

import dane.Contact;
import dane.Event;
import gui.ContactsPane.ContactsRemover;
import system.Controller;
import system.events.DisplayedContactsChanged;
import system.events.DisplayedDateChanged;
import system.events.DisplayedEventsChanged;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Application {

	private JFrame frame;
	private EventsPane eventsPane;
	private ContactsPane contactsPane;
	
	public JFrame getFrame() {
		return frame;
	}

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
	private JRadioButton allEventsRadio;
	private JRadioButton dayEventsRadio; 
	private JRadioButton weekEventsRadio;
	private JRadioButton monthEventsRadio;
	private JRadioButton yearEventsRadio;
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

	static private class SafeActionListener implements ActionListener {
		
		private final ActionListener wrapped; 
		
		public SafeActionListener(ActionListener wrapped) {
			this.wrapped = wrapped;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				wrapped.actionPerformed(e);
			}catch(Exception exc) {
				String message = "Error ocurred: " + exc.getMessage();
				JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
		control = new Controller();
		control.registerListener(DisplayedDateChanged.class, calendarTable);
		control.registerListener(DisplayedContactsChanged.class, contactsPane);
		control.registerListener(DisplayedEventsChanged.class, eventsPane);

		addCalendarListeners();	
		addMenuListeners();
		
		control.initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	public JSplitPane createCalendarOptionsPane () {
		JSplitPane calendarView = new JSplitPane();
		
		calendarView.setResizeWeight(0.8);
		calendarTable = new CalendarTable(); 		
		
		calendarView.setLeftComponent(new JScrollPane(calendarTable));
		calendarView.getLeftComponent().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				JScrollPane scrollPane = (JScrollPane)e.getComponent();
				JTable table = (JTable)scrollPane.getViewport().getView();
				table.setRowHeight(scrollPane.getViewport().getHeight()/6);
			}
		});
		
		JPanel calendarOptionsPane = new JPanel(new GridBagLayout()); 
		GridBagConstraints c = new GridBagConstraints();
		
		LocalDate now = LocalDate.now();
		
		monthsCombo = new JComboBox<String>(months);
		monthsCombo.setSelectedIndex(now.getMonthValue() - 1);
		
		c.gridx = 0; 
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER; 
		c.fill = GridBagConstraints.NONE;
		calendarOptionsPane.add(monthsCombo, c);		
		
		yearsCombo = new JComboBox<String>(years);
		yearsCombo.setSelectedIndex(now.getYear() - 2018);
		monthsCombo.setPreferredSize(new Dimension(100,20));
		monthsCombo.setPrototypeDisplayValue("xXXxxx");
		c.gridx = 1; 
		c.gridy = 0;
		calendarOptionsPane.add(yearsCombo, c);
		
		c.gridx = 1; 
		c.gridy = 1;
		calendarOptionsPane.add(addEventButtonCal,c);
		addEventButtonCal.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				Event e = EventAddingDialog.showDialog();
				if(e!= null) {
					control.addEvent(e);
					if(allEventsRadio.isSelected()) {
						control.changeDisplayedEvents(0);
					} else if(dayEventsRadio.isSelected()) {
						control.changeDisplayedEvents(1);
					} else if(weekEventsRadio.isSelected()) {
						control.changeDisplayedEvents(2);
					} else if (monthEventsRadio.isSelected()) {
						control.changeDisplayedEvents(3);
					} else if (yearEventsRadio.isSelected()) {
						control.changeDisplayedEvents(4);
					}
				}
			}
		});
	
		calendarView.setRightComponent(calendarOptionsPane);
		return calendarView;	
	}
	
	public JSplitPane createContactsOptionsPane () {
		JSplitPane contactsView = new JSplitPane();
		contactsView.setResizeWeight(0.8);
		ContactsRemover remover = new ContactsRemover() {
			
			@Override
			public void removeContact(Contact con) {
				control.removeContact(con);
				
			}
		};
		contactsPane = new ContactsPane(remover); 
		contactsView.setLeftComponent(contactsPane);
		
		JPanel contactsOptionsPane = new JPanel(); 
		JLabel options = new JLabel("contacts options"); 	
		
		contactsOptionsPane.add(options);
		contactsOptionsPane.add(addContactButton);
		
		addContactButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("contact adding aplication");
				control.addContact(ContactAddingDialog.showDialog());
				
			}
		});
		
		contactsView.setRightComponent(contactsOptionsPane);
		return contactsView;
	}
	
	public JSplitPane createEventsOptionsPane () {
		JSplitPane eventsView = new JSplitPane();
		eventsView.setResizeWeight(0.8);		
		eventsPane = new EventsPane(new EventsPane.EventRemover() {
			@Override
			public void removeEvent(Event e) {
				if(e != null) {
					control.removeEvent(e);
					if(allEventsRadio.isSelected()) {
						control.changeDisplayedEvents(0);
					} else if(dayEventsRadio.isSelected()) {
						control.changeDisplayedEvents(1);
					} else if(weekEventsRadio.isSelected()) {
						control.changeDisplayedEvents(2);
					} else if (monthEventsRadio.isSelected()) {
						control.changeDisplayedEvents(3);
					} else if (yearEventsRadio.isSelected()) {
						control.changeDisplayedEvents(4);
					}
				}
			}
		});
		eventsView.setLeftComponent(new JScrollPane(eventsPane));
		JPanel eventsOptionsPane = new JPanel();
		eventsOptionsPane.setLayout(new BoxLayout(eventsOptionsPane, BoxLayout.PAGE_AXIS)); 
		JLabel options = new JLabel("contacts options"); 
		eventsOptionsPane.add(options);
		
		
		JLabel select = new JLabel("show events:");
		eventsOptionsPane.add(select);
		
		allEventsRadio = new JRadioButton("all events");
		eventsOptionsPane.add(allEventsRadio);
		allEventsRadio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.changeDisplayedEvents(0);
				
			}
		});
		
		dayEventsRadio = new JRadioButton("today");
		eventsOptionsPane.add(dayEventsRadio);
		dayEventsRadio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.changeDisplayedEvents(1);
			}
		});
		
		weekEventsRadio = new JRadioButton("this week");
		eventsOptionsPane.add(weekEventsRadio);
		weekEventsRadio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.changeDisplayedEvents(2);		
			}
		});
		
		monthEventsRadio = new JRadioButton("this month");
		eventsOptionsPane.add(monthEventsRadio);
		monthEventsRadio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.changeDisplayedEvents(3);
				
			}
		});
		
		yearEventsRadio = new JRadioButton("this year");
		eventsOptionsPane.add(yearEventsRadio);
		yearEventsRadio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.changeDisplayedEvents(4);
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(allEventsRadio);
		group.add(dayEventsRadio);
		group.add(weekEventsRadio);
		group.add(monthEventsRadio);
		group.add(yearEventsRadio);
		
		allEventsRadio.setSelected(true);
		
		eventsOptionsPane.add(addEventButtonEv);
		
		addEventButtonEv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                Event ev = EventAddingDialog.showDialog();
                if (ev != null) {
					control.addEvent(ev);
					if(allEventsRadio.isSelected()) {
						control.changeDisplayedEvents(0);
					} else if(dayEventsRadio.isSelected()) {
						control.changeDisplayedEvents(1);
					} else if(weekEventsRadio.isSelected()) {
						control.changeDisplayedEvents(2);
					} else if (monthEventsRadio.isSelected()) {
						control.changeDisplayedEvents(3);
					} else if (yearEventsRadio.isSelected()) {
						control.changeDisplayedEvents(4);
					}
				}
			}
		});
		
		eventsView.setRightComponent(eventsOptionsPane);
		return eventsView;
	}
	
	
	public void addCalendarListeners() {
		monthsCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.changeDisplayedDate(monthsCombo.getSelectedIndex(), yearsCombo.getSelectedIndex());				
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
				LocalDateTime dueTime = SettingsDialog.showSettingsDialog(); 
				if(dueTime != null) {
				control.removeOldEvents(dueTime);	
				}
			}		
		});
		
		mntmFromXML.addActionListener(new SafeActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int userChoice = chooser.showOpenDialog(null);

				if (userChoice == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();

					if (selectedFile.exists()) {
						control.importEventsFromXml(selectedFile);
						JOptionPane.showMessageDialog(null,
								"Import succeeded.", "Success!", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null,
								"File " + selectedFile.getName() + " does not exist!", "Error",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}));
		
		mntmFromOutlook.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showFromOutlookWindow();
			}			
		});
		
		mntmFromDatabase.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int userChoice = chooser.showOpenDialog(null);

				if (userChoice == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();

					if (selectedFile.exists()) {
						control.importDataFromDatabase(selectedFile.getAbsolutePath());
                        JOptionPane.showMessageDialog(null,
								"Import succeeded.", "Success!", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null,
								"Database " + selectedFile.getName() + " does not exist!", "Error",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		mntmToXML.addActionListener(new SafeActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int userChoice = chooser.showSaveDialog(null);

				if (userChoice == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					if (selectedFile.exists()) {
						userChoice = JOptionPane.showConfirmDialog(null,
								"File " + selectedFile.getName() + " already exists, overwrite?",
								"Overwrite?", JOptionPane.YES_NO_OPTION);
					}

					if (!selectedFile.exists() || userChoice == JOptionPane.YES_OPTION) {
						control.exportEventsToXml(selectedFile);
						JOptionPane.showMessageDialog(null,
								"Export succeeded.", "Success!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}));
		
		mntmToOutlook.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showToOutlookWindow();			
			}			
		});
		
		mntmToDatabase.addActionListener(new SafeActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int userChoice = chooser.showSaveDialog(null);

				if (userChoice == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					if (selectedFile.exists()) {
						userChoice = JOptionPane.showConfirmDialog(null,
								"Selected database " + selectedFile.getName() + " will be cleared, continue?",
								"Overwrite?", JOptionPane.YES_NO_OPTION);
						if (userChoice == JOptionPane.YES_OPTION) {
							control.exportDataToDatabase(selectedFile.getAbsolutePath());
							JOptionPane.showMessageDialog(null,
									"Export succeeded.", "Success!", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"You must select existing database!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}));
		
		mntmAboutProgram.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				control.showAboutProgramWindow();			
			}			
		});
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
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
