package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NewBookWindow extends JFrame implements LibWindow{
    public static final NewBookWindow INSTANCE = new NewBookWindow();
//    ControllerInterface ci = new SystemController();
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
    private JTextField ISBNTextField;
    private JTextField authorTextField; // consider! if there is more than one author (add Author button)
    private JTextField checkoutLengthTextField;
    private JTextField numberOfCopiesTextField;

    private JButton submitButton;
    private JButton addAuthorButton;



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
        JLabel AllIDsLabel = new JLabel("Add New Book");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
//        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 10);
        BoxLayout bl = new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS);

        middlePanel.setLayout(bl);

        titleLabel = new JLabel("Title");


        isbnLabel = new JLabel("Book ISBN");
        authorLabel = new JLabel("Author Information");
        checkoutLengthLabel = new JLabel("Checkout Length (Days)");
        numberOfCopiesLabel = new JLabel("Number of Copies");
//        titleLabel = new Label("Title");

        titleTextField = new JTextField();
        ISBNTextField = new JTextField();
        authorTextField = new JTextField();
        checkoutLengthTextField = new JTextField();
        numberOfCopiesTextField = new JTextField();

        submitButton = new JButton("ADD");
        addButtonListener(submitButton);

        middlePanel.add(titleLabel);
        middlePanel.add(titleTextField);
        middlePanel.add(isbnLabel);
        middlePanel.add(ISBNTextField);
        middlePanel.add(authorLabel);
        middlePanel.add(authorTextField);
        middlePanel.add(checkoutLengthLabel);
        middlePanel.add(checkoutLengthTextField);
        middlePanel.add(numberOfCopiesLabel);
        middlePanel.add(numberOfCopiesTextField);
        middlePanel.add(submitButton);

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
            //takes to successful screen
            String title = titleTextField.getText();
            String authorName = authorTextField.getText();
            String checkoutLengthText = checkoutLengthTextField.getText();
            String numCopies = numberOfCopiesTextField.getText();
            String isbnText =  ISBNTextField.getText();

            System.out.printf("Retrieved Values %s/n %s/n %s/n %s/n %s/n", title, authorName, checkoutLengthText, numCopies, isbnText);
//            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<== Back to Main");
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);
    }
}
