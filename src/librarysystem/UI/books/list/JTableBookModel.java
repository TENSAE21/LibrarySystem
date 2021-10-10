package librarysystem.UI.books.list;

import business.Book;
import business.SystemController;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

class JTableBookModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private SystemController controller = new SystemController();
	private HashMap<String, Book> booksSet = controller.getBooks();
    private List<Book> books = booksSet.values()
            .stream()
            .collect(Collectors.toList());

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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }
    
    public void addRow(Book book) {
    	books.add(book);
    	fireTableDataChanged();
    }
    
    public Book getBookByISBN(String isbn) {
    	return books.stream().filter(b -> b.getIsbn() == isbn).collect(Collectors.toList()).get(0);
    }
}
