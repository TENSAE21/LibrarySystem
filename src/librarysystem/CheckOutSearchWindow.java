package librarysystem;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.BookCopy;
import business.LibraryMember;
import business.SystemController;

import javax.swing.JOptionPane;

public class CheckOutSearchWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	private static JTextField txtMemberId = new JTextField(15);
	private static JTextField txtISBN = new JTextField(15);

	private JFrame parentFrame;

	//to use from next forms
	public static LibraryMember resultMember = null;
	static BookCopy nextAvailableCopy = null;

	public CheckOutSearchWindow() {
		setLayout(new BorderLayout());
		add(new JLabel("Checking out"), BorderLayout.NORTH);

		JPanel pnlAdd = new JPanel(); 
		pnlAdd.setLayout(new GridBagLayout());  

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		//left 
		c.gridx=1;
		c.gridy=0;
		pnlAdd.add(new JLabel("Member ID"), c);
		c.gridy=1;
		pnlAdd.add(txtMemberId, c);

		c.gridy=2;
		pnlAdd.add(new JLabel("ISBN"), c);
		c.gridy=3;
		pnlAdd.add(txtISBN, c);

		c.gridy=4;
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new SearchAuthorButtonListener());
		pnlAdd.add(btnSearch, c);

		//pnlNorth.add(pnlAdd, BorderLayout.NORTH);
		add(pnlAdd, BorderLayout.CENTER);
	}

	public void setParentJFrame(JFrame parent) {
		this.parentFrame = parent;
	}

	class SearchAuthorButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String memberId = txtMemberId.getText();
			String isbn = txtISBN.getText();

			if(memberId.trim().length() == 0 || 
					isbn.trim().length() == 0)
				JOptionPane.showMessageDialog(parentFrame, "Member ID and ISBN should not be empty", "Message",  JOptionPane.WARNING_MESSAGE);
			else
			{
				SystemController ctrl = new SystemController();
				resultMember = ctrl.findMemberByID(memberId);
				nextAvailableCopy = ctrl.findBookByISBN(isbn);
				if(resultMember == null || nextAvailableCopy == null)
					JOptionPane.showMessageDialog(parentFrame, "Wrong Member ID or Not Available Book", "Message",  JOptionPane.ERROR_MESSAGE);
				else {
					if(nextAvailableCopy != null) {
						int length = nextAvailableCopy.getBook().getMaxCheckoutLength();
						LocalDate today = LocalDate.now();
						LocalDate dueDate = today.plusDays(length);
						CheckOutContinueWindow.lblISBN.setText(nextAvailableCopy.getBook().getIsbn());
						CheckOutContinueWindow.lblTitle.setText(nextAvailableCopy.getBook().getTitle());
						CheckOutContinueWindow.lblDueDate.setText(dueDate.toString());
						SharedWindow.cl.show(SharedWindow.cards, "Check Out Continue");
					}
				}
			}
		}
	}

	static void clearInputs() {
		txtMemberId.setText("");
		txtISBN.setText("");
	}
}
