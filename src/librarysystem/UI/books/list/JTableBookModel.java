package librarysystem.UI.books.list;

import business.Book;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class JTableBookModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private List<Book> books = new ArrayList<Book>(
            Arrays.asList(
                    new Book("6473878172", "Advanced Java"),
                    new Book("8372982782", "Mathematics"),
                    new Book("2229108765", "Science"),
                    new Book("3672638874", "Geography"),
                    new Book("1298177254", "Design patterns")
            )
    );

    private List<Book> rows = books;

    private String[] columns = {"ISBN", "TITLE", ""};

    public String getColumnName(int column) {
        return columns[column];
    }
    public int getRowCount() {
        return rows.size();
    }
    public int getColumnCount() {
        return columns.length;
    }
    public Object getValueAt(int row, int column) {
        Object value = "??";
        Book book = books.get(row);
        switch (column) {
            case 0:
                value = book.getIsbn();
                break;
            case 1:
                value = book.getTitle();
                break;
            case 2:
                value = "Add new copy";
                break;
        }
        return value;
    }
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }
}
