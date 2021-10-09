package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import librarysystem.uimodel.CheckoutEntryModel;

public class CheckOutListWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	private JFrame parentFrame; //will need when we want to alert message

	private final String[] headers 
	= {"ISBN", "Check Out Date", "Due Date"};
	private static final int SCREEN_WIDTH = SharedWindow.maxWidth;
	private static final int SCREEN_HEIGHT = SharedWindow.maxLength;
	private static final int width = (int) (0.75 * SCREEN_WIDTH);
	private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * SCREEN_HEIGHT);
	private final float [] proportions =
		{0.35f, 0.35f, 0.3f};
	CheckoutEntryModel entryModel = new CheckoutEntryModel();
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
		pnlAdd.add(new JLabel("Testing name"), c);

		c.gridy=2;
		pnlAdd.add(new JLabel("Testing id"), c);
		
		c.gridx=0;
		c.gridy=5;
		pnlAdd.add(new JLabel("List of check out "), c);

		add(pnlAdd, BorderLayout.CENTER);

		JTable table = new JTable(entryModel);
		table.setAutoCreateColumnsFromModel(false);
		int num = headers.length;
		for(int i = 0; i < num; ++i) {
			TableColumn column = new TableColumn(i);
			column.setHeaderValue(headers[i]);
			column.setMinWidth(Math.round(proportions[i]*width));
			table.addColumn(column);

			add(table, BorderLayout.SOUTH);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setPreferredSize(
					new Dimension(width, DEFAULT_TABLE_HEIGHT));
			scrollPane.getViewport().add(table);
			setValues(entryModel);
			add(scrollPane, BorderLayout.SOUTH);
		}
		
	}
	
	private void setValues(CheckoutEntryModel model) {
		String[] row0 = {"ISBN1", "2021-02-02", "2021-02-02"};
		String[] row1 = {"ISBN2", "2021-02-02", "2021-02-02"};
		String[] row2 = {"ISBN3", "2021-02-02", "2021-02-02"};
		List<String[]> data = new ArrayList<>();
		data.add(row0);
		data.add(row1);
		data.add(row2);
		model.setTableValues(data);
		
	}
	
	public void setParentJFrame(JFrame parent) {
		this.parentFrame = parent;
	}

	class ContinueButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Gonna continue");	
			SharedWindow.cl.show(SharedWindow.cards, "Check Out List");
		}
	}
}
