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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class NewStudentScreen extends JFrame{
	
	JButton submit, goBack;
	JTextField username, password, firstName, lastName;
	JComboBox<String> adminOrUser;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	public NewStudentScreen() {
		
		//Set size of the frame
		setSize(1700,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title Text
		JLabel title = new JLabel("Register an Account");
		title.setFont(title.getFont().deriveFont(32.0f));
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Admin or user label
		JLabel optionLabel = new JLabel("What kind of account: ");
		optionLabel.setFont(optionLabel.getFont().deriveFont(24.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 1;
		add(optionLabel, c);
		
		//Drop Down for Admin or User
		String[] options = {"Student", "Admin"};
		adminOrUser = new JComboBox<String>(options);
		c.fill = GridBagConstraints.HORIZONTAL;
		adminOrUser.setFont(adminOrUser.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 1;
		add(adminOrUser, c);		
		
		//First Name Text
		JLabel firstLabel = new JLabel("First Name:");
		firstLabel.setFont(firstLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		add(firstLabel, c);	
		
		//firstName text field
		firstName = new JTextField(10);
		firstName.setFont(firstName.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 2;
		add(firstName, c);
		
		//Last Name Text
		JLabel lastLabel = new JLabel("Last Name:");
		lastLabel.setFont(lastLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0, 0, 0, 0);
		add(lastLabel, c);	
		
		//Last Name text field
		lastName = new JTextField(10);
		lastName.setFont(lastName.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 3;
		add(lastName, c);
	
		//Username text
		JLabel userLabel = new JLabel("Username:");
		userLabel.setFont(userLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(0, 0, 0, 0);
		add(userLabel, c);	
		
		//Username text field
		username = new JTextField(10);
		username.setFont(username.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 4;
		add(username, c);
		
		//Password Text
		JLabel passLabel = new JLabel("Password:");
		passLabel.setFont(passLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(0, 0, 0, 0);
		add(passLabel, c);	
		
		//Password text field
		password = new JTextField(10);
		password.setFont(password.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 5;
		add(password, c);
		
		//Submit button
		submit = new JButton("Submit");
		submit.setFont(submit.getFont().deriveFont(24.0f));
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				SubmitButton();
			}
			
		});
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 6;
		add(submit, c);
		
		//Go Back Button
		goBack = new JButton("Return");
		goBack.setFont(goBack.getFont().deriveFont(24.0f));
		goBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				AdminScreen adminScreen = new AdminScreen();
			}
		});
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 6;
		add(goBack, c);	
		
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
			System.out.println("NewStudentScreen");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//Add user as an Admin if not already added and return to home; else give error
	void SubmitButton() {
		System.out.println(adminOrUser.getSelectedIndex());
		if(Accounts.checkUser(username.getText(), password.getText()) == -2) {
			Accounts.addUser(adminOrUser.getSelectedIndex(), username.getText(), password.getText(), firstName.getText(), lastName.getText());
			JOptionPane.showMessageDialog(null, "Succcess! " + adminOrUser.getSelectedItem() + 
											" account was created for " + firstName.getText()
											+ " " + lastName.getText() + ".");
			password.setText("");
			username.setText("");
			firstName.setText("");
			lastName.setText("");
		}
		else {
			JOptionPane.showMessageDialog(null, "Error: this username "
					+ "and password already exists!");
		}
	}
}
