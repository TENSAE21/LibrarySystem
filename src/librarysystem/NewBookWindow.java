package librarysystem;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import librarysystem.UI.books.list.ListAllBooksWindow;

import librarysystem.UI.members.list.ListAllMembersWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NewBookWindow extends JPanel implements ActionListener {

	ControllerInterface ci = new SystemController();

	private JFrame parentFrame;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;

	private JLabel titleLabel;
	private JLabel isbnLabel;
	private JLabel authorLabel;
	private JLabel checkoutLengthLabel;
	private JLabel numberOfCopiesLabel;
	private JTextField titleTextField;
	private JFormattedTextField ISBNTextField;
	private JTextField authorTextField; // consider! if there is more than one author (add Author button)
	private JComboBox checkoutLengthTextField;
	private JFormattedTextField numberOfCopiesTextField;

	private JButton submitButton;
	private JButton addAuthorButton;

	private JLabel successLabel;

	//    private JComboBox<String> authorComboBox;
	private JList<String> authorJList;

	private static NewBookWindow nbw;

	public NewBookWindow() {
		setSize(600, 400);
		setLayout(new BorderLayout());
		nbw = this;

		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);
		setBorder(new EmptyBorder(10, 10, 10, 10));

	}

	public static NewBookWindow getNBW()
	{return nbw;}
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Add New Book");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		//JButton backButton = new JButton("< Back");
		//backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		//addBackButtonListener(backButton);
		//topPanel.add(backButton);
		topPanel.add(AddBookLabel);
	}

	public void defineMiddlePanel() {
		middlePanel = new JPanel();

		BoxLayout bl = new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS);
		middlePanel.setLayout(bl);

		titleLabel = new JLabel("Title");
		isbnLabel = new JLabel("Book ISBN");
		authorLabel = new JLabel("Author Information");
		checkoutLengthLabel = new JLabel("Checkout Length (Days)");
		numberOfCopiesLabel = new JLabel("Number of Copies");

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);

		String phoneMask= "##-#####";
		String phoneNumber= "1242348";

		MaskFormatter maskFormatter= null;
		try {
			maskFormatter = new MaskFormatter(phoneMask);
			maskFormatter.setValueContainsLiteralCharacters(false);
			maskFormatter.valueToString(phoneNumber) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		formatter.setValueClass(Integer.class);
		formatter.setMaximum(100);
		formatter.setAllowsInvalid(false);

		titleTextField = new JTextField();
		ISBNTextField = new JFormattedTextField(maskFormatter);
		authorTextField = new JTextField();

		String[] checkoutTimes = {"7","21"};
		checkoutLengthTextField = new JComboBox(checkoutTimes);

		formatter.setMaximum(100000);
		numberOfCopiesTextField = new JFormattedTextField(formatter);

		addAuthorButton = new JButton("+ Add New Author");
		addAuthorListener(addAuthorButton);
		JPanel addAuthorPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addAuthorPanel.add(addAuthorButton);

		//        addAuthorButton = new JButton("Add another Author");
		//        addButtonListener(addAuthorButton);

		//        List<String> authorNameList = ci.allAuthorNames();
		//        authorNameList.add("+ Add another Author");

		String[] authNames = ci.allAuthorNames().toArray(new String[0]);
		//        authorComboBox = new JComboBox<String>(authNames);

		//        String[] authButtons = getAuthButtons(authorNameList);
		authorJList = new JList<>(authNames);
		authorJList.setFixedCellHeight(15);
		authorJList.setFixedCellWidth(80);
		//        authorJList.setVisibleRowCount(4);
		authorJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


		middlePanel.add(titleLabel);
		middlePanel.add(titleTextField);
		middlePanel.add(isbnLabel);
		middlePanel.add(ISBNTextField);
		middlePanel.add(authorLabel);
		//        middlePanel.add(authorComboBox);
		middlePanel.add(new JScrollPane(authorJList));
		middlePanel.add(addAuthorPanel);
		middlePanel.add(checkoutLengthLabel);
		middlePanel.add(checkoutLengthTextField);
		middlePanel.add(numberOfCopiesLabel);
		middlePanel.add(numberOfCopiesTextField);


	}

	/*private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}*/


	private void addButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			//            LibrarySystem.hideAllWindows();
			//takes to successful screen // hidden label in the south red error or green success and check
			String title = titleTextField.getText();
			List<String> authorNames = new ArrayList<>();
			String checkoutInput = (String) checkoutLengthTextField.getSelectedItem();
			String copiesInput = numberOfCopiesTextField.getText();
			String isbnText =  ISBNTextField.getText();
			//            System.out.println(isbnText);

			for (Object name: authorJList.getSelectedValues()){
				String s = (String) name;
				authorNames.add(s);
				System.out.println(s);
			}


			boolean arefilled = checkBookValues(authorNames, title, isbnText, checkoutInput, copiesInput);

			if(arefilled) {
				int checkoutLengthText = Integer.parseInt(checkoutInput);
				int numCopies = Integer.parseInt(copiesInput);

				Book persistedBook = ci.addBook(title, isbnText, checkoutLengthText, authorNames, numCopies);
				ListAllBooksWindow.notifyTableChanged(persistedBook);
				successLabel.setText("You have successfully added the book " + title);

				Color successColor = new Color(173, 230, 102);
				successLabel.setBackground(successColor);
				successLabel.setOpaque(true);
				successLabel.setVisible(true);
				int delay = 2000; //milliseconds

				ActionListener taskPerformer = new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						successLabel.setVisible(false);
					}
				};
				new Timer(delay, taskPerformer).start();
				clearFields();

			}
			else{
				successLabel.setText("Please insert all values");
				Color failColor = new Color(240, 76, 103);
				successLabel.setBackground(failColor);
				successLabel.setOpaque(true);
				successLabel.setVisible(true);
				int delay = 2000; //milliseconds

				ActionListener taskPerformer = new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						successLabel.setVisible(false);
					}
				};
				new Timer(delay, taskPerformer).start();

			}


			//            System.out.printf("Retrieved Values %s\n %s\n %s\n %s\n", title, checkoutLengthText, numCopies, isbnText);
		});
	}

	public void updateAuthorList ()
	{
		String[] authNames = ci.allAuthorNames().toArray(new String[0]);
		authorJList.setListData(authNames);
	}
	private void addAuthorListener(JButton butn) {
		butn.addActionListener(evt -> {
			//            LibrarySystem.hideAllWindows();
			//clears author name area but retrieves what was written
			// adds it to author list kept here -> zero out at screen re-render

			String authorName = authorTextField.getText();

			JFrame frame = new JFrame("Add New Author");
			Main.centerFrameOnDesktop(frame);
			//2. Optional: What happens when the frame closes?
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			//3. Create components and put them in the frame.
			//...create emptyLabel...
			AddNewAuthorWindow anaw = new AddNewAuthorWindow(true);
			frame.getContentPane().add(anaw, BorderLayout.CENTER);

			//4. Size the frame.
			frame.pack();

			//5. Show it.
			frame.setVisible(true);

			System.out.printf("Retrieved Author %s/n", authorName);

		});
	}


	boolean checkBookValues(List<String> names, String ... inputs)
	{
		for (String s: inputs) {
			System.out.println(s);
			if (s.isEmpty())
				return false;
		}

		for (String s: names) {
			if (s.isEmpty())
				return false;
		}
		return true;
	}

	void clearFields()
	{
		titleTextField.setText("");
		ISBNTextField.setText("");
		numberOfCopiesTextField.setText("");
		authorJList.clearSelection();
	}

	public void defineLowerPanel() {
		lowerPanel = new JPanel();

		BoxLayout bl = new BoxLayout(lowerPanel, BoxLayout.Y_AXIS);
		lowerPanel.setLayout(bl);

		JPanel buttonPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
		buttonPanel.setLayout(fl);
		//Color successColor = new Color(173,230,102);


		successLabel = new JLabel("");
		successLabel.setVisible(false);

		submitButton = new JButton("ADD");
		addButtonListener(submitButton);
		buttonPanel.add(submitButton);

		lowerPanel.add(successLabel);
		lowerPanel.add(buttonPanel);

	}

	public void setParentJFrame(JFrame parent) {
		this.parentFrame = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
