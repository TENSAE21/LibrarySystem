package librarysystem.UI.members.update;

import business.LibraryMember;
import business.SystemController;
import librarysystem.UI.members.list.ListAllMembersWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UpdateLibraryMemberWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel panel = new JPanel();
    private static GridBagConstraints constraints = new GridBagConstraints();
    private static JButton submitBtn = new JButton("Update");
    private LibraryMember libraryMember;

    private void createInput(JLabel label, JTextField field, int lx, int ly, int fx, int fy) {
        constraints.gridx = lx;
        constraints.gridy = ly;
        constraints.insets = new Insets(0,10,1,10);
        panel.add(label, constraints);
        constraints.gridwidth = 3;
        constraints.gridx = fx;
        constraints.gridy = fy;
        constraints.insets = new Insets(0,10,10,10);
        panel.add(field, constraints);
        constraints.insets = new Insets(0,0,0,0);
    }

    public UpdateLibraryMemberWindow(LibraryMember member) {
    	this.libraryMember = member;
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setTitle("Edit member info");
    	setSize(600, 400);
    	setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    	panel.setSize(600, 400);
        panel.setLayout(new GridBagLayout());
        
        UpdateMemberFields.firstNameTextField.setText(member.getFirstName());
        UpdateMemberFields.lastNameTextField.setText(member.getLastName());
        UpdateMemberFields.phoneNumberTextField.setText(member.getTelephone());
        UpdateMemberFields.streetTextField.setText(member.getAddress().getStreet());
        UpdateMemberFields.cityTextField.setText(member.getAddress().getCity());
        UpdateMemberFields.stateTextField.setText(member.getAddress().getState());
        UpdateMemberFields.zipcodeTextField.setText(member.getAddress().getZip());

        UpdateMemberFields.firstNameTextField.setPreferredSize(new Dimension(200, 30));
        UpdateMemberFields.lastNameTextField.setPreferredSize(new Dimension(200, 30));
        UpdateMemberFields.phoneNumberTextField.setPreferredSize(new Dimension(200, 30));
        UpdateMemberFields.streetTextField.setPreferredSize(new Dimension(200, 30));
        UpdateMemberFields.cityTextField.setPreferredSize(new Dimension(200, 30));
        UpdateMemberFields.stateTextField.setPreferredSize(new Dimension(200, 30));
        UpdateMemberFields.zipcodeTextField.setPreferredSize(new Dimension(200, 30));


        constraints.fill = GridBagConstraints.HORIZONTAL;

        createInput(UpdateMemberFields.firstNameLabel, UpdateMemberFields.firstNameTextField, 0,0,0,1);
        createInput(UpdateMemberFields.lastNameLabel, UpdateMemberFields.lastNameTextField, 3,0,3,1);
        createInput(UpdateMemberFields.phoneNumberLabel, UpdateMemberFields.phoneNumberTextField, 0,2,0,3);

        createInput(UpdateMemberFields.streetLabel, UpdateMemberFields.streetTextField, 0,4,0,5);
        createInput(UpdateMemberFields.cityLabel, UpdateMemberFields.cityTextField, 3,4,3,5);
        createInput(UpdateMemberFields.stateLabel, UpdateMemberFields.stateTextField, 0,6,0,7);
        createInput(UpdateMemberFields.zipcodeLabel, UpdateMemberFields.zipcodeTextField, 3,6,3,7);

        submitBtn.addActionListener(this);
        submitBtn.setToolTipText("Click here to edit the member");
        submitBtn.setPreferredSize(new Dimension(120, 40));
        submitBtn.setForeground(Color.blue);
        constraints.gridx = 2;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10,0,0,0);
        panel.add(submitBtn, constraints);
        
        add(panel);

        setVisible(true);
    }
    
    void updateLibraryMemberInfo() {
    	 HashMap<String, String> member = UpdateMemberFields.getValues();
    	 this.libraryMember.setFirstName(member.get("firstName"));
    	 this.libraryMember.setLastName(member.get("lastName"));
    	 this.libraryMember.setTelephone(member.get("phoneNumber"));
    	 
    	 this.libraryMember.getAddress().setStreet(member.get("street"));
    	 this.libraryMember.getAddress().setCity(member.get("city"));
    	 this.libraryMember.getAddress().setState(member.get("state"));
    	 this.libraryMember.getAddress().setZip(member.get("zipcode"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(!UpdateMemberFields.validateRequiredFields()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Message",  JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(!UpdateMemberFields.validateZipcode()) {
            JOptionPane.showMessageDialog(this, "Zipcode is invalid", "Message",  JOptionPane.WARNING_MESSAGE);
            return;
        }

       
		SystemController systemController = new SystemController();
		updateLibraryMemberInfo();
		systemController.updateLibraryMember(libraryMember);
		JOptionPane.showMessageDialog(this,"library member successfully updated", "Message",  JOptionPane.INFORMATION_MESSAGE);
		UpdateMemberFields.clearAllFields();
		ListAllMembersWindow.notifyTableChanged();
		this.dispose();
		
    }
}


