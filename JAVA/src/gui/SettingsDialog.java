package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JComboBox;

public class SettingsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField amountTextfield;
	private JComboBox PeriodCombo;
	JButton okButton;
	JButton cancelButton;
	private String[] options = new String[] {"days", "months", "years"};
	private boolean okClicked = false;
	

	/**
	 * Launch the application.
	 */
	public static LocalDateTime showSettingsDialog() {
		try {
			SettingsDialog dialog = new SettingsDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			if(dialog.okClicked) {
				int amount = Integer.parseInt(dialog.amountTextfield.getText());
				int option = dialog.PeriodCombo.getSelectedIndex();
				LocalDateTime dueTime;
				if(option == 0) {
					dueTime = LocalDateTime.now().minusDays(amount);
				}
				else if (option == 1) {
					dueTime = LocalDateTime.now().minusMonths(amount);
				} else if(option == 2 ) {
					dueTime = LocalDateTime.now().minusYears(amount);
				} else {
					return null;
				}
				dialog.dispose();
				return dueTime;
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
	public SettingsDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 224);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 116, 25, 144, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel DisposeLabel = new JLabel("Dispose events older than: ");
			GridBagConstraints gbc_DisposeLabel = new GridBagConstraints();
			gbc_DisposeLabel.anchor = GridBagConstraints.WEST;
			gbc_DisposeLabel.gridwidth = 3;
			gbc_DisposeLabel.insets = new Insets(0, 0, 5, 5);
			gbc_DisposeLabel.gridx = 1;
			gbc_DisposeLabel.gridy = 1;
			contentPanel.add(DisposeLabel, gbc_DisposeLabel);
		}
		{
			amountTextfield = new JTextField();
			GridBagConstraints gbc_amountTextfield = new GridBagConstraints();
			gbc_amountTextfield.insets = new Insets(0, 0, 0, 5);
			gbc_amountTextfield.fill = GridBagConstraints.HORIZONTAL;
			gbc_amountTextfield.gridx = 1;
			gbc_amountTextfield.gridy = 3;
			contentPanel.add(amountTextfield, gbc_amountTextfield);
			amountTextfield.setColumns(10);
		}
		{
			PeriodCombo = new JComboBox(options);
			GridBagConstraints gbc_PeriodCombo = new GridBagConstraints();
			gbc_PeriodCombo.insets = new Insets(0, 0, 0, 5);
			gbc_PeriodCombo.fill = GridBagConstraints.HORIZONTAL;
			gbc_PeriodCombo.gridx = 3;
			gbc_PeriodCombo.gridy = 3;
			contentPanel.add(PeriodCombo, gbc_PeriodCombo);
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
