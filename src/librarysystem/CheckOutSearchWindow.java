package librarysystem;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.SystemController;

import javax.swing.JOptionPane;

public class CheckOutSearchWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField txtMemberId = new JTextField(15);
	private JTextField txtISBN = new JTextField(15);

	private JFrame parentFrame;

	public CheckOutSearchWindow() {
		setLayout(new BorderLayout());
		JLabel lblTitle = new JLabel("Checking out");
		add(lblTitle, BorderLayout.NORTH);

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
				if(ctrl.findMemberByID(memberId) == null || ctrl.findBookByISBN(isbn) == null)
					JOptionPane.showMessageDialog(parentFrame, "Wrong Member ID or Not Available Book", "Message",  JOptionPane.ERROR_MESSAGE);
				else {
					System.out.println("we will go next");
					SharedWindow.cl.show(SharedWindow.cards, "Check Out Continue");
				}
			}
		}
	}
}
