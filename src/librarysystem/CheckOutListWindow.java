package librarysystem;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CheckOutListWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	
	static JLabel lblMemberName = new JLabel();
	static JLabel lblMemberID = new JLabel();
	
	static CheckoutEntryModel entryModel = new CheckoutEntryModel();
	private static JTable table = new JTable(entryModel);
	private JScrollPane scrollPane = new JScrollPane(table);

	public CheckOutListWindow() {
		setLayout(new BorderLayout());
		JLabel lblTitle = new JLabel("Check Out List");
		add(lblTitle, BorderLayout.NORTH);

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
}
