package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
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

    private Label titleLabel;
    private Label isbnLabel;
    private Label authorLabel;
    private Label checkoutLengthLabel;
    private Label numberOfCopiesLabel;
    private TextArea titleTextArea;
    private TextArea ISBNTextArea;
    private TextArea authorTextArea; // consider! if there is more than one author (add Author button)
    private TextArea checkoutLengthTextArea;




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
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 10);
        middlePanel.setLayout(fl);

        titleLabel = new Label("Title");
        isbnLabel = new Label("Book ISBN");
        authorLabel = new Label("Author Information");
        checkoutLengthLabel = new Label("Checkout Length (Days)");
//        titleLabel = new Label("Title");

        titleTextArea = new TextArea(1,10);
        ISBNTextArea = new TextArea(1,10);
        authorTextArea = new TextArea(1,10);
        checkoutLengthTextArea = new TextArea(1,10);


        middlePanel.add(titleLabel);
        middlePanel.add(titleTextArea);
        middlePanel.add(isbnLabel);
        middlePanel.add(ISBNTextArea);
        middlePanel.add(authorLabel);
        middlePanel.add(authorTextArea);
        middlePanel.add(checkoutLengthLabel);
        middlePanel.add(checkoutLengthTextArea);


    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
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
