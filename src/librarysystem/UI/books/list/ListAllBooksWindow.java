package librarysystem.UI.books.list;

import business.Book;

import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ListAllBooksWindow extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static SystemController controller = new SystemController();
	private JFrame parentFrame;
    private static JPanel panel = new JPanel();
    private static JTextField searchField = new JTextField();
    private static JButton resetButton = new JButton("<");
    private static JButton searchButton = new JButton("Search");
    private List<LibraryMember> members = new ArrayList<LibraryMember>();
    private final String[] filters = { "By name", "By ISBN"};
    private JComboBox filterOptions = new JComboBox(filters);
    private static JTableBookModel tableModel = new JTableBookModel();
    private TableRowSorter sorter = new TableRowSorter<JTableBookModel>(tableModel);
    private RowFilter<JTableBookModel, Object> rf = null;
    private JTable table = new JTable(tableModel);
    private JScrollPane scrollPane = new JScrollPane(table);

    public ListAllBooksWindow() {
        setLayout(new BorderLayout());

        /*
         * HEADER MENU WITH SEARCH OPTIONS
         */
        panel.setLayout(new FlowLayout());
        prepareHeaderComponents();
        add(panel, BorderLayout.PAGE_START);

        /*
         * TABLE WITH LIST OF BOOKS
         */
        prepareTableComponents();
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void prepareHeaderComponents() {
        searchField.setPreferredSize(new Dimension(200, 30));
        searchButton.setPreferredSize(new Dimension(75, 35));
        searchButton.addActionListener(this);
        resetButton.setPreferredSize(new Dimension(50, 30));
        resetButton.addActionListener(this);
        filterOptions.setPreferredSize(new Dimension(150, 30));
        filterOptions.setSelectedIndex(0);
        panel.add(resetButton);
        panel.add(searchField);
        panel.add(filterOptions);
        panel.add(searchButton);
    }

    private void prepareTableComponents() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(2).setMaxWidth(90);
        table.getColumnModel().getColumn(2).setMinWidth(90);
        table.addMouseListener(new MouseAdapter() {
            @SuppressWarnings("static-access")
			@Override
            public void mouseClicked(MouseEvent evt) {
                JTable table = (JTable)evt.getSource();
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                if(column == 2) {
                	if (controller.currentAuth == Auth.LIBRARIAN) {
                		JOptionPane.showMessageDialog(parentFrame,"You do not have access to add a new copy", "Message",  JOptionPane.INFORMATION_MESSAGE);
                		return;
                	}
                	String s = (String)JOptionPane.showInputDialog(
                            parentFrame,
                            "How many copies?",
                            "Add new copy",
                            JOptionPane.PLAIN_MESSAGE);
                	
                	if ((s != null) && (s.length() > 0)) {
                	    try {
                	    	int numberOfCopies = Integer.parseInt(s);
                	    	String isbn = (String)table.getValueAt(row, 0);
                        	Book b = tableModel.getBookByISBN(isbn);
                        	controller.addBookCopies(b, numberOfCopies);
                        	JOptionPane.showMessageDialog(parentFrame,"New copies successfully added", "Message",  JOptionPane.INFORMATION_MESSAGE);
                        	System.out.println("Successfully added new copies for book with isbn: " + isbn);
                	    }catch (Exception e) {
                	    	JOptionPane.showMessageDialog(parentFrame,"Enter a valid number", "Message",  JOptionPane.INFORMATION_MESSAGE);
                	    	System.out.println(e.getMessage());
						}
                	}
                }
            }
        });
        table.setRowSorter(sorter);
        scrollPane.setSize(500,300);
        scrollPane.setBackground(Color.gray);
    }
    
    public void setParentJFrame(JFrame parent) {
		this.parentFrame = parent;
	}
    
    void verifyResults() {
    	if(table.getRowCount() == 0) {
        	JOptionPane.showMessageDialog(parentFrame,"No books found", "Message",  JOptionPane.INFORMATION_MESSAGE);
        	rf = RowFilter.regexFilter("Add new copy",2);
        	sorter.setRowFilter(rf);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == searchButton) {
            if(filterOptions.getSelectedIndex() == 0) {
                try {
                    rf = RowFilter.regexFilter(searchField.getText(),1);
                } catch (java.util.regex.PatternSyntaxException ex) {
                	JOptionPane.showMessageDialog(parentFrame, ex.getMessage(), "Message",  JOptionPane.ERROR_MESSAGE);
        			ex.printStackTrace();
        			return;
                }
                
                sorter.setRowFilter(rf);
                verifyResults();
            }
            
            if(filterOptions.getSelectedIndex() == 1) {
                try {
                    rf = RowFilter.regexFilter(searchField.getText(),0);
                } catch (java.util.regex.PatternSyntaxException ex) {
                	JOptionPane.showMessageDialog(parentFrame, ex.getMessage(), "Message",  JOptionPane.ERROR_MESSAGE);
        			ex.printStackTrace();
        			return;
                }
                sorter.setRowFilter(rf);
                verifyResults();
            }
        }

        if(e.getSource() == resetButton) {
            try {
                rf = RowFilter.regexFilter("Add new copy",2);
            } catch (java.util.regex.PatternSyntaxException ex) {
                return;
            }
            sorter.setRowFilter(rf);
        }
    }
    
    public static void notifyTableChanged(Book book) {
    	tableModel.addRow(book);
    }
}


