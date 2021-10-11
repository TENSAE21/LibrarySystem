package librarysystem.UI.members.update;

import javax.swing.*;
import java.util.HashMap;

abstract class UpdateMemberFields {
    static JLabel firstNameLabel = new JLabel("First name");
    static JLabel lastNameLabel = new JLabel("Last name");
    static JLabel phoneNumberLabel = new JLabel("Phone number");
    static JLabel streetLabel = new JLabel("Street");
    static JLabel cityLabel = new JLabel("City");
    static JLabel stateLabel = new JLabel("State");
    static JLabel zipcodeLabel = new JLabel("Zipcode");

    static JTextField firstNameTextField = new JTextField();
    static JTextField lastNameTextField = new JTextField();
    static JTextField phoneNumberTextField = new JTextField();
    static JTextField streetTextField = new JTextField();
    static JTextField cityTextField = new JTextField();
    static JTextField stateTextField = new JTextField();
    static JTextField zipcodeTextField = new JTextField();

    static boolean validateRequiredFields() {
        if(firstNameTextField.getText().equals("")
                || lastNameTextField.getText().trim().length() == 0
                || phoneNumberTextField.getText().trim().length() == 0
                || streetTextField.getText().trim().length() == 0
                || cityTextField.getText().trim().length() == 0
                || stateTextField.getText().trim().length() == 0
                || zipcodeTextField.getText().trim().length() == 0) {
            return false;
        }
        return true;
    }

    static boolean validateZipcode() {
        return zipcodeTextField.getText().trim().length() == 5 ? true : false;
    }

    static HashMap<String, String> getValues() {
        HashMap<String, String> values = new HashMap<>();
        values.put("firstName", firstNameTextField.getText());
        values.put("lastName", lastNameTextField.getText());
        values.put("phoneNumber", phoneNumberTextField.getText());
        values.put("street", streetTextField.getText());
        values.put("city", cityTextField.getText());
        values.put("state", stateTextField.getText());
        values.put("zipcode", zipcodeTextField.getText());

        return values;
    }
    
    static void clearAllFields() {
    	firstNameTextField.setText("");
		lastNameTextField.setText("");
		phoneNumberTextField.setText("");
		streetTextField.setText("");
		cityTextField.setText("");
		stateTextField.setText("");
		zipcodeTextField.setText("");
	}
}


