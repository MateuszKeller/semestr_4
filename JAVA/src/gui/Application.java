package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import system.Controller;

public class Application {

	private JFrame frame;
	private JTable calendarTable;
	private JTable contactsTable;
	private Controller control;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}


	/**
	 * Initialize the contents of the frame.
	 */
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
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JTabbedPane calendarView = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(calendarView);
		
		JPanel calendarPane = new JPanel();
		calendarView.addTab("Calendar", null, calendarPane, null);
		
		calendarTable = new JTable();
		calendarPane.add(calendarTable);
		
		JPanel contactsPane = new JPanel();
		calendarView.addTab("Contacts", null, contactsPane, null);
		
		contactsTable = new JTable();
		contactsPane.add(contactsTable);
		
		JPanel eventPane = new JPanel();
		calendarView.addTab("Events", null, eventPane, null);
		
		JLabel lblNewLabel = new JLabel("To be done");
		eventPane.add(lblNewLabel);
		
		JPanel optionsView = new JPanel();
		splitPane.setRightComponent(optionsView);
	}

}
