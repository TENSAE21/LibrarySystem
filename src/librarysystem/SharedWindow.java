package librarysystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import business.SystemController;
import dataaccess.Auth;
import librarysystem.UI.books.list.ListAllBooksWindow;
import librarysystem.UI.members.create.AddNewLibraryMemberWindow;
import librarysystem.UI.members.list.ListAllMembersWindow;

/**
 * 
 * @author wineikhaing
 */

public class SharedWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	String[] items;
	String[] links;
	JList<String> linkList;
	static JPanel cards = new JPanel(new CardLayout());;
	static CardLayout cl;

	public static final int maxWidth = 800;
	public static final int maxLength = 300;

	public SharedWindow() {
		setSize(maxWidth, maxLength);
		String[] items = setItems();
		linkList = new JList<String>(items);				
		createPanels();	
		JSplitPane splitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
		splitPane.setDividerLocation(150);
		add(splitPane, BorderLayout.CENTER);
		highlightDashboard();
	}

	private String[] setItems() {
		if(SystemController.currentAuth == Auth.ADMIN) {
			return new String[] {"Dashboard", "Book List", "Member List", "Add New Book", "Add New Member", "Log Out"};
		}
		else if (SystemController.currentAuth == Auth.LIBRARIAN) {
			return new String[] {"Dashboard", "Book List", "Member List", "Check Out", "Log Out"};
		}
		else if (SystemController.currentAuth == Auth.BOTH) {
			return new String[] {"Dashboard", "Book List", "Member List", "Add New Book", "Add New Member",  "Check Out", "Log Out"};
		}
		return null;
	}

	/* Organize panels into a CardLayout */
	public void createPanels() {
		JPanel pnlDashboard = createDashboardPanel();
		JPanel pnlAddNewAuthor = new AddNewAuthorWindow(false);

		cards.add(pnlDashboard, "Dashboard");
		cards.add(pnlAddNewAuthor, "Add New Author");
		cards.add(new AddNewLibraryMemberWindow(), "Add New Member");
 		cards.add(new NewBookWindow(), "Add New Book");
		cards.add(new ListAllMembersWindow(), "Member List");
		cards.add(new ListAllBooksWindow(), "Book List");

		cards.add(new CheckOutSearchWindow(), "Check Out");
		cards.add(new CheckOutContinueWindow(), "Check Out Continue");
		cards.add(new CheckOutListWindow(), "Check Out List");

		//connect JList elements to CardLayout panels
		linkList.addListSelectionListener(event -> {
			String value = linkList.getSelectedValue().toString();
			if(value.equals("Log Out")) {
				dispose();
				LoginWindow.INSTANCE.setVisible(true);
			}
			cl = (CardLayout) (cards.getLayout());
			cl.show(cards, value);
		});
	}

	private JPanel createDashboardPanel() {
		JPanel pnlDashboard = new JPanel(); 
		pnlDashboard.setLayout(new BorderLayout());
		JLabel lblWelcome = new JLabel("Welcome to our Library System, " + SystemController.currentAuth);
		pnlDashboard.add(lblWelcome, BorderLayout.NORTH);

		String currDirectory = System.getProperty("user.dir");
		String pathToImage = currDirectory+"/src/librarysystem/library.jpg";

		ImageIcon image = new ImageIcon(pathToImage);
		pnlDashboard.add(new JLabel(image), BorderLayout.CENTER);	
		return pnlDashboard;
	}

	private void highlightDashboard() {
		int begn = 0;
		int end = 0;
		linkList.setSelectionInterval(begn, end);
	}
}
