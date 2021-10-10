package librarysystem;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import business.LoginException;
import business.SystemController;

//this is a panel
public class AddNewAuthorWindow extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtFirstName = new JTextField(15);
	private JTextField txtLastName = new JTextField(15);
	private JTextField txtPhoneNumber = new JTextField(15);
	private JTextArea txtBio = new JTextArea();
	private JTextField txtStreet = new JTextField(15);
	private JTextField txtCity = new JTextField(15);
	private JTextField txtState = new JTextField(15);
	private JTextField txtZipCode = new JTextField(15);

	private boolean fromBook;

	private JFrame parentFrame;

	public AddNewAuthorWindow(boolean fromBook) {
		this.fromBook = fromBook;
		setLayout(new BorderLayout());
		JLabel lblTitle = new JLabel("Adding New Author");
		add(lblTitle, BorderLayout.NORTH);

		JPanel pnlAdd = new JPanel(); 
		pnlAdd.setLayout(new GridBagLayout());  

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		//left 
		c.gridx=1;
		c.gridy=0;
		pnlAdd.add(new JLabel("First Name"), c);
		c.gridy=1;
		pnlAdd.add(txtFirstName, c);

		c.gridy=2;
		pnlAdd.add(new JLabel("Street"), c);
		c.gridy=3;
		pnlAdd.add(txtStreet, c);

		c.gridy=4;
		pnlAdd.add(new JLabel("State"), c);
		c.gridy=5;
		pnlAdd.add(txtState, c);

		c.gridy=6;
		pnlAdd.add(new JLabel("Phone Number"), c);
		c.gridy=7;
		pnlAdd.add(txtPhoneNumber, c);
		
		//right 
		c.gridx=2;
		c.gridy=0;
		pnlAdd.add(new JLabel("Last Name"), c);
		c.gridy=1;
		pnlAdd.add(txtLastName, c);

		c.gridy=2;
		pnlAdd.add(new JLabel("City"), c);
		c.gridy=3;
		pnlAdd.add(txtCity, c);

		c.gridy=4;
		pnlAdd.add(new JLabel("Zip Code"), c);
		c.gridy=5;
		pnlAdd.add(txtZipCode, c);
		
		c.gridy=6;
		pnlAdd.add(new JLabel("Bio"), c);
		c.gridy=7;
		pnlAdd.add(txtBio, c);
		txtBio.setRows(3);
		txtBio.setColumns(15);

		c.gridx=2;
		c.gridy=10;
		JButton btnAdd = new JButton("Add Author");
		btnAdd.addActionListener(new AddAuthorButtonListener());
		pnlAdd.add(btnAdd, c);

		//pnlNorth.add(pnlAdd, BorderLayout.NORTH);
		add(pnlAdd, BorderLayout.CENTER);
	}

	public void setParentJFrame(JFrame parent) {
		this.parentFrame = parent;
	}
	class AddAuthorButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String fName = txtFirstName.getText();
			String lName = txtLastName.getText();
			String phNum = txtPhoneNumber.getText();
			String street = txtStreet.getText();
			String city = txtCity.getText();
			String state = txtState.getText();
			String zip = txtZipCode.getText();
			String bio = txtBio.getText();

			if(fName.trim().length() == 0 || 
					lName.trim().length() == 0 ||
					phNum.trim().length() == 0 ||
					street.trim().length() == 0 ||
					city.trim().length() == 0 ||
					state.trim().length() == 0 ||
					zip.trim().length() == 0 ||
					bio.trim().length() == 0)
				JOptionPane.showMessageDialog(parentFrame, "Each author information should not be empty", "Message",  JOptionPane.WARNING_MESSAGE);
			else
			{
				try {
					SystemController systemController = new SystemController();
					systemController.addNewAuthor(fName, lName, phNum, street, city, state, zip, bio);
					JOptionPane.showMessageDialog(parentFrame,"Successful added", "Message",  JOptionPane.INFORMATION_MESSAGE);
					if(fromBook)
					{
						NewBookWindow.getNBW().updateAuthorList();
					}
					clearAllText();
				} catch (LoginException ex) {
					JOptionPane.showMessageDialog(parentFrame, ex.getMessage(), "Message",  JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		}
	}

	private void clearAllText() {
		txtFirstName.setText("");
		txtLastName.setText("");
		txtPhoneNumber.setText("");
		txtBio.setText("");
		txtStreet.setText("");
		txtCity.setText("");
		txtState.setText("");
		txtZipCode.setText("");
	}
}
