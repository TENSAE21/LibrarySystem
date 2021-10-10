package librarysystem;

import java.util.List;
import business.CheckoutEntry;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CheckOutEntryModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	List<CheckoutEntry> entries = (CheckOutSearchWindow.resultMember == null) ? new ArrayList<CheckoutEntry>() : CheckOutSearchWindow.resultMember.getRecord().getEntries();

	private List<CheckoutEntry> rows = entries;

	private String[] columns = {"ISBN", "Check Out Date", "Due Date"};

	public String getColumnName(int column) {
		return columns[column];
	}

	public int getRowCount() {
		return rows.size();
	}

	public int getColumnCount() {
		return columns.length;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		return getValueAt(0, column).getClass();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		 Object value = "??";
	        CheckoutEntry entry = entries.get(rowIndex);
	        switch (columnIndex) {
	            case 0:
	                value = entry.getBookCopy().getBook().getIsbn();
	                break;
	            case 1:
	                value = entry.getCheckoutDate();
	                break;
	            case 2:
	                value = entry.getDueDate();
	                break;
	        }
	        return value;
	}

	public void setTableValues(List<CheckoutEntry> list) {
		if(list == null) {
			return;
		}
		for(CheckoutEntry e : list)
			entries.add(e);
	}
} 