package librarysystem;

import business.Author;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NewBookWindow extends JFrame implements LibWindow{
    public static final NewBookWindow INSTANCE = new NewBookWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
    public JPanel getMainPanel() {
        return mainPanel;
    }
    private JPanel mainPanel;
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
    private JFormattedTextField checkoutLengthTextField;
    private JFormattedTextField numberOfCopiesTextField;

    private JButton submitButton;
    private JButton addAuthorButton;

//    private JComboBox<String> authorComboBox;
    private JList<String> authorJList;



    @Override
    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);
        isInitialized = true;

    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }


    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AddBookLabel = new JLabel("Add New Book");
        Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("< Back");
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addBackButtonListener(backButton);
        topPanel.add(backButton);
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
        checkoutLengthTextField = new JFormattedTextField(formatter);
        formatter.setMaximum(100000);
        numberOfCopiesTextField = new JFormattedTextField(formatter);

        addAuthorButton = new JButton("Add another Author");
        addButtonListener(addAuthorButton);

        String[] authNames = ci.allAuthorNames().toArray(new String[0]);
//        authorComboBox = new JComboBox<String>(authNames);

        authorJList = new JList<>(authNames);
        authorJList.setFixedCellHeight(15);
        authorJList.setFixedCellWidth(100);
//        authorJList.setVisibleRowCount(4);
        authorJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


        middlePanel.add(titleLabel);
        middlePanel.add(titleTextField);
        middlePanel.add(isbnLabel);
        middlePanel.add(ISBNTextField);
        middlePanel.add(authorLabel);
//        middlePanel.add(authorComboBox);
        middlePanel.add(new JScrollPane(authorJList));
        middlePanel.add(checkoutLengthLabel);
        middlePanel.add(checkoutLengthTextField);
        middlePanel.add(numberOfCopiesLabel);
        middlePanel.add(numberOfCopiesTextField);


    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void addButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
//            LibrarySystem.hideAllWindows();
            //takes to successful screen // hidden label in the south red error or green success and check
            String title = titleTextField.getText();
            List<String> authorNames = new ArrayList<>();
            int checkoutLengthText = Integer.parseInt(checkoutLengthTextField.getText());
            int numCopies = Integer.parseInt(numberOfCopiesTextField.getText());
            String isbnText =  ISBNTextField.getText();

            for (Object name: authorJList.getSelectedValues()){
                String s = (String) name;
                authorNames.add(s);
                System.out.println(s);
            }

            ci.addBook(title, isbnText, checkoutLengthText,authorNames, numCopies);

//            System.out.printf("Retrieved Values %s\n %s\n %s\n %s\n", title, checkoutLengthText, numCopies, isbnText);
        });
    }

    private void addAuthorListener(JButton butn) {
        butn.addActionListener(evt -> {
//            LibrarySystem.hideAllWindows();
            //clears author name area but retrieves what was written
            // adds it to author list kept here -> zero out at screen re-render

            String authorName = authorTextField.getText();


            System.out.printf("Retrieved Author %s/n", authorName);

        });
    }



    String checkBookValues()
    {
        return null;
    }

    JPanel getNewBookPanel()
    {
        init();
        pack();
        setVisible(true);

        return mainPanel;
    }
    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
        lowerPanel.setLayout(fl);

        submitButton = new JButton("ADD");
        addButtonListener(submitButton);
        lowerPanel.add(submitButton);

    }
}
