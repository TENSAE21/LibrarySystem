package librarysystem;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CheckOutListWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	private JFrame parentFrame; //will need when we want to alert message

	public CheckOutListWindow() {
		setLayout(new BorderLayout());
		JLabel lblTitle = new JLabel("Checking out final stage - listing ...");
		add(lblTitle, BorderLayout.NORTH);
	}

	public void setParentJFrame(JFrame parent) {
		this.parentFrame = parent;
	}
}
