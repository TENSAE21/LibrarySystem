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

import business.BookCopy;
import business.LibraryMember;
import business.SystemController;

public class CheckOutContinueWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	private JFrame parentFrame; //will need when we want to alert message

	static JLabel lblISBN = new JLabel();
	static JLabel lblTitle = new JLabel();
	static JLabel lblDueDate = new JLabel();

	public CheckOutContinueWindow() {
		setLayout(new BorderLayout());
		add(new JLabel("Checking out continuing ..."), BorderLayout.NORTH);

		JPanel pnlAdd = new JPanel(); 
		pnlAdd.setLayout(new GridBagLayout());  

		GridBagConstraints c = new GridBagConstraints();
		//c.gridwidth = 1;
		//left 
		c.gridx=0;
		c.gridy=0;
		pnlAdd.add(new JLabel("Book Info"), c);
		c.gridy=1;
		pnlAdd.add(new JLabel("ISBN : "), c);
		c.gridy=2;
		pnlAdd.add(new JLabel("Title : "), c);
		c.gridy=3;
		pnlAdd.add(new JLabel("Due Date : "), c);

		//right 
		c.gridx=1;
		c.gridy=1;
		pnlAdd.add(lblISBN, c);
		c.gridy=2;
		pnlAdd.add(lblTitle, c);
		c.gridy=3;
		pnlAdd.add(lblDueDate, c);

		c.gridx=0;
		c.gridy=4;
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new BackButtonListener());
		pnlAdd.add(btnBack, c);

		c.gridx=1;
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ContinueButtonListener());
		pnlAdd.add(btnContinue, c);

		add(pnlAdd, BorderLayout.CENTER);
	}

	public void setParentJFrame(JFrame parent) {
		this.parentFrame = parent;
	}

	class ContinueButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//saving check out
			LibraryMember member = CheckOutSearchWindow.resultMember;
			BookCopy copy = CheckOutSearchWindow.nextAvailableCopy;
			SystemController ctrl = new SystemController();
			if(ctrl.saveCheckOut(member, copy)) {
				JOptionPane.showMessageDialog(parentFrame, "Checking Out Process Succeed", "Message",  JOptionPane.INFORMATION_MESSAGE);

				CheckOutListWindow.lblMemberName.setText(member.getFirstName() + " " + member.getLastName());
				CheckOutListWindow.lblMemberID.setText(member.getMemberId());
				CheckOutListWindow.updateModel(member.getRecord().getEntries());
				SharedWindow.cl.show(SharedWindow.cards, "Check Out List");
			}
			else
				JOptionPane.showMessageDialog(parentFrame, "Checking Out Process failed", "Message",  JOptionPane.ERROR_MESSAGE);
		}
	}
	class BackButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			SharedWindow.cl.show(SharedWindow.cards, "Check Out");
		}
	}
}
