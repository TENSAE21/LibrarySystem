package librarysystem.UI.members.create;

import business.LibraryMember;
import business.LibraryMemberFactory;
import business.LoginException;
import business.SystemController;
import librarysystem.UI.members.list.ListAllMembersWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AddNewLibraryMemberWindow extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JFrame parentFrame;
    private static GridBagConstraints constraints = new GridBagConstraints();
    private static JButton submitBtn = new JButton("Submit");

    private void createInput(JLabel label, JTextField field, int lx, int ly, int fx, int fy) {
        constraints.gridx = lx;
        constraints.gridy = ly;
        constraints.insets = new Insets(0,10,1,10);
        add(label, constraints);
        constraints.gridwidth = 3;
        constraints.gridx = fx;
        constraints.gridy = fy;
        constraints.insets = new Insets(0,10,10,10);
        add(field, constraints);
        constraints.insets = new Insets(0,0,0,0);
    }

    public AddNewLibraryMemberWindow() {
        setSize(600, 400);
        setLayout(new GridBagLayout());

        MemberFields.firstNameTextField.setPreferredSize(new Dimension(200, 30));
        MemberFields.lastNameTextField.setPreferredSize(new Dimension(200, 30));
        MemberFields.phoneNumberTextField.setPreferredSize(new Dimension(200, 30));
        MemberFields.streetTextField.setPreferredSize(new Dimension(200, 30));
        MemberFields.cityTextField.setPreferredSize(new Dimension(200, 30));
        MemberFields.stateTextField.setPreferredSize(new Dimension(200, 30));
        MemberFields.zipcodeTextField.setPreferredSize(new Dimension(200, 30));


        constraints.fill = GridBagConstraints.HORIZONTAL;

        createInput(MemberFields.firstNameLabel, MemberFields.firstNameTextField, 0,0,0,1);
        createInput(MemberFields.lastNameLabel, MemberFields.lastNameTextField, 3,0,3,1);
        createInput(MemberFields.phoneNumberLabel, MemberFields.phoneNumberTextField, 0,2,0,3);

        createInput(MemberFields.streetLabel, MemberFields.streetTextField, 0,4,0,5);
        createInput(MemberFields.cityLabel, MemberFields.cityTextField, 3,4,3,5);
        createInput(MemberFields.stateLabel, MemberFields.stateTextField, 0,6,0,7);
        createInput(MemberFields.zipcodeLabel, MemberFields.zipcodeTextField, 3,6,3,7);

        submitBtn.addActionListener(this);
        submitBtn.setToolTipText("Click here to add the new member");
        submitBtn.setPreferredSize(new Dimension(120, 40));
        submitBtn.setForeground(Color.blue);
        constraints.gridx = 2;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10,0,0,0);
        add(submitBtn, constraints);

        setVisible(true);
    }
    
    public void setParentJFrame(JFrame parent) {
		this.parentFrame = parent;
	}
    

    @Override
    public void actionPerformed(ActionEvent e) {

        if(!MemberFields.validateRequiredFields()) {
            JOptionPane.showMessageDialog(parentFrame, "All fields are required", "Message",  JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(!MemberFields.validateZipcode()) {
            JOptionPane.showMessageDialog(parentFrame, "Zipcode is invalid", "Message",  JOptionPane.WARNING_MESSAGE);
            return;
        }

        HashMap<String, String> member = MemberFields.getValues();

        // Persist new libraryMember with serialization
        try {
			SystemController systemController = new SystemController();
			LibraryMember persistedMember = systemController.persistNewLibraryMember(
			member.get("firstName"),
	                member.get("lastName"),
	                member.get("phoneNumber"),
	                member.get("street"),
	                member.get("city"),
	                member.get("state"),
	                member.get("zipcode")
				);
			JOptionPane.showMessageDialog(parentFrame,"New library member successfully created", "Message",  JOptionPane.INFORMATION_MESSAGE);
			MemberFields.clearAllFields();
			ListAllMembersWindow.notifyTableChanged(persistedMember);
		} catch (LoginException ex) {
			JOptionPane.showMessageDialog(parentFrame, ex.getMessage(), "Message",  JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
    }
}

