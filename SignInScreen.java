import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SignInScreen extends JFrame{
	
	JButton submit;
	JTextField username, password;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	public SignInScreen() {
		
		//Set size of the frame
		setSize(1700,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title Text
		JLabel title = new JLabel("Sign In");
		c.fill = GridBagConstraints.CENTER;
		title.setFont(title.getFont().deriveFont(32.0f));
		c.gridwidth = 2;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Username text field
		username = new JTextField(10);
		c.fill = GridBagConstraints.HORIZONTAL;
		username.setFont(username.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 1;
		add(username, c);
		
		//Password text field
		password = new JTextField(10);
		c.fill = GridBagConstraints.HORIZONTAL;
		password.setFont(password.getFont().deriveFont(26.0f));
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		add(password, c);
		
		//Submit button
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				SubmitButton();
			}
			
		});
		submit.setFont(submit.getFont().deriveFont(24.0f));
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 3;
		add(submit, c);
		
		//Username text
		JLabel userLabel = new JLabel("Username:");
		c.fill = GridBagConstraints.HORIZONTAL;
		userLabel.setFont(userLabel.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		add(userLabel, c);	
		
		//Password text
		JLabel passLabel = new JLabel("Password:");
		passLabel.setFont(passLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		add(passLabel, c);	
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				serialize();
				System.exit(0);
			}
		});
		
		setVisible(true);
		
	}
	
	//Serialize
	public void serialize() {
		try {
			//FileOutput Stream writes data to a file
			FileOutputStream fos = new FileOutputStream("SerializedInfo.ser");
			
			//ObjectOutputStream writes objects to a stream (A sequence of data)
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//Writes the specific object to the OOS
			oos.writeObject(arrays);
			
			//Close both streams
			oos.close();
			fos.close();
			System.out.println("SignIn");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//Check if user exists and sign in; if not clear text fields and prompt error
	void SubmitButton() {
		int checker = Accounts.checkUser(username.getText(), password.getText());
		if(checker == -1) {
			setVisible(false);
			dispose();
			AdminScreen adminScreen = new AdminScreen();
		}
		else if(checker >= 0) {
			setVisible(false);
			dispose();
			StudentScreen studentScreen = new StudentScreen(checker);
		}
		else {
			JOptionPane.showMessageDialog(null, "Error: username "
					+ "and/or password is incorrect.");
			password.setText("");
			username.setText("");
		}
		
	}
}
