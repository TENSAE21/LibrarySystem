package librarysystem;

import java.util.List;
import business.CheckoutEntry;
import business.SystemController;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CheckoutEntryModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private SystemController controller = new SystemController();
	private HashMap<String, CheckoutEntry> entrySet = controller.getCheckOutEntries();
	private List<CheckoutEntry> entries = entrySet.values()
			.stream()
			.collect(Collectors.toList());

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
		// TODO Auto-generated method stub
		return null;
	}
} 