import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DeleteCourseScreen extends JFrame{
	
	JButton enter, goBack;
	JComboBox<String> dropDownCourses;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	public DeleteCourseScreen(){
		
		setSize(1700,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title Label
		JLabel title = new JLabel("Delete A Course");
		c.fill = GridBagConstraints.CENTER;
		title.setFont(title.getFont().deriveFont(32.0f));
		c.gridwidth = 3;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Description
		JLabel description = new JLabel("Please choose the course you would like to delete:");
		c.fill = GridBagConstraints.CENTER;
		description.setFont(description.getFont().deriveFont(26.0f));
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 1;
		add(description, c);
		
		//Make the list of courses
		String[] courses = new String[Accounts.courseName.size()];
		for(int i = 0; i < Accounts.courseName.size(); i++) {
			courses[i] = Accounts.courseID.get(i) + ": " + Accounts.courseName.get(i)
							+ " - " + Accounts.instructor.get(i) + " - Section: " + 
							Accounts.section.get(i);
		}
		//Make the drop down for the courses
		dropDownCourses = new JComboBox<String>(courses);
		c.fill = GridBagConstraints.HORIZONTAL;
		dropDownCourses.setFont(dropDownCourses.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 2;
		add(dropDownCourses, c);	
		//'Enter' button for drop down
		enter = new JButton("Enter");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Get the selected course and delete it
				int choice = dropDownCourses.getSelectedIndex();
				JOptionPane.showMessageDialog(null, "Succcess! " + (String)dropDownCourses.getSelectedItem()
												+ " was deleted.");
				Accounts.removeCourse(choice);
				
				//Refresh screen
				setVisible(false);
				dispose();
				DeleteCourseScreen dSC = new DeleteCourseScreen();
			}	
		});
		enter.setFont(enter.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 10, 0, 0);
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 2;
		add(enter, c);
		
		//Go back Button
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
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 7;
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
			System.out.println("DeleteCourseScreen");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
