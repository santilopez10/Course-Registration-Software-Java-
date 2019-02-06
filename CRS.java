import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CRS extends JFrame{
	
	JButton signIn;
	
	boolean initialized = false;
	
	ToBeSerialized arrays = new ToBeSerialized();
		
	public CRS() {
		
		//If Screen has already been made just make it visible
		if(initialized) {
			setVisible(true);
			return;
		}
		initialized = true;
		
		
		//Set size of the frame
		setSize(1700,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		//Sign in button
		signIn = new JButton("Sign In");
		signIn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				signInScreen();
			}
			
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		signIn.setFont(signIn.getFont().deriveFont(32.0f));
		c.insets = new Insets(0, 0, 250, 0);
		c.weighty = 0.3;
		c.gridx = 0;
		c.gridy = 3;
		add(signIn, c);
			
		//Title text
		JLabel title = new JLabel("Course Registration System");
		c.fill = GridBagConstraints.HORIZONTAL;
		title.setFont(title.getFont().deriveFont(32.0f));
		c.insets = new Insets(300, 0, 0, 0);
		c.weighty = 0.01;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);	
		
		//By Santiago text
		JLabel santiago = new JLabel("By Santiago Lopez Del Pino");
		c.fill = GridBagConstraints.CENTER;
		santiago.setFont(santiago.getFont().deriveFont(26.0f));
		c.insets = new Insets(0, 0, 40, 0);
		c.weighty = 0.1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		add(santiago, c);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				serialize();
				System.exit(0);
			}
		});
		
		setVisible(true);
		
	}
	
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
			System.out.println("CRS");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//Go to the sign in screen
	void signInScreen() {
		setVisible(false);
		dispose();
		
		SignInScreen sis = new SignInScreen();
	}
	
	
}
