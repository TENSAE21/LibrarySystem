package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import business.CheckoutEntry;

public class CheckOutListWindow extends JPanel{
	private static final long serialVersionUID = 1L;

	static JLabel lblMemberName = new JLabel();
	static JLabel lblMemberID = new JLabel();

	private static CheckOutEntryModel entryModel = new CheckOutEntryModel();
	private static JTable table = new JTable(entryModel);
	private static JScrollPane scrollPane = new JScrollPane(table);

	public CheckOutListWindow() {
		setLayout(new BorderLayout());
		
		JPanel pnlHeader = new JPanel();
		pnlHeader.setLayout(new FlowLayout());
		JLabel lblTitle = new JLabel("Check Out List");
		pnlHeader.add(lblTitle);
		JButton btnDone  = new JButton("Done");
		addDoneButtonListener(btnDone);
		pnlHeader.add(btnDone);
		
		add(pnlHeader, BorderLayout.NORTH);
		
		JPanel pnlAdd = new JPanel(); 
		pnlAdd.setLayout(new GridBagLayout());  

		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;
		//left 
		c.gridx=0;
		c.gridy=0;
		pnlAdd.add(new JLabel("Member Record"), c);
		c.gridy=1;
		pnlAdd.add(new JLabel("Name : "), c);
		c.gridy=2;
		pnlAdd.add(new JLabel("ID : "), c);

		//right 
		c.gridx=1;
		c.gridy=1;
		pnlAdd.add(lblMemberName, c);

		c.gridy=2;
		pnlAdd.add(lblMemberID, c);

		c.gridx=0;
		c.gridy=5;
		pnlAdd.add(new JLabel("List of check out "), c);

		add(pnlAdd, BorderLayout.CENTER);
		add(scrollPane, BorderLayout.SOUTH);
	}

	public static void updateModel(List<CheckoutEntry> list){
		if(entryModel == null) {
			entryModel = new CheckOutEntryModel();
		}
		entryModel.setTableValues(list);
		table.updateUI();
		scrollPane.updateUI();
	}
	
	private void addDoneButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			CheckOutSearchWindow.clearInputs();
			SharedWindow.cl.show(SharedWindow.cards, "Check Out");
		});
	}
}
