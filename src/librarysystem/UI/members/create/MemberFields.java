package librarysystem.UI.members.create;

import javax.swing.*;
import java.util.HashMap;

abstract class MemberFields {
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
                || lastNameTextField.getText().equals("")
                || phoneNumberTextField.getText().equals("")
                || streetTextField.getText().equals("")
                || cityTextField.getText().equals("")
                || stateTextField.getText().equals("")
                || zipcodeTextField.getText().equals("")) {
            return false;
        }
        return true;
    }

    static boolean validateZipcode() {
        return zipcodeTextField.getText().length() == 5 ? true : false;
    }

    static HashMap<String, String> getValues() {
        HashMap<String, String> values = new HashMap<>();
        values.put("firstname", firstNameTextField.getText());
        values.put("lastName", lastNameTextField.getText());
        values.put("phoneNumber", phoneNumberTextField.getText());
        values.put("street", streetTextField.getText());
        values.put("city", cityTextField.getText());
        values.put("state", stateTextField.getText());
        values.put("zipcode", zipcodeTextField.getText());

        return values;
    }
}

