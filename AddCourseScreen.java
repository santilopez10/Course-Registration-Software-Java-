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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddCourseScreen extends JFrame {
	
	JButton submit, goBack;
	JTextField courseName, courseID, maxStudents, instructor, section, location;
	
	ToBeSerialized arrays = new ToBeSerialized();

	
	public AddCourseScreen() {
		
		//Set size of the frame
		setSize(1700,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title Text
		JLabel title = new JLabel("Create a Course");
		title.setFont(title.getFont().deriveFont(32.0f));
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Course Name Label
		JLabel nameLabel = new JLabel("Course Name:");
		nameLabel.setFont(nameLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		add(nameLabel, c);	
		
		//Course Name text field
		courseName = new JTextField(10);
		courseName.setFont(courseName.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 1;
		add(courseName, c);
		
		//Course ID Label
		JLabel iDLabel = new JLabel("Course ID:");
		iDLabel.setFont(iDLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		add(iDLabel, c);	
		
		//course ID text field
		courseID = new JTextField(10);
		courseID.setFont(courseID.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 2;
		add(courseID, c);
		
		//Max Students Label
		JLabel maxLabel = new JLabel("Max Students:");
		maxLabel.setFont(maxLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0, 0, 0, 0);
		add(maxLabel, c);	
		
		//Max Students text field
		maxStudents = new JTextField(10);
		maxStudents.setFont(maxStudents.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 3;
		add(maxStudents, c);
		
		//Course Instructor Label
		JLabel instructorLabel = new JLabel("Course Instructor:");
		instructorLabel.setFont(instructorLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(0, 0, 0, 0);
		add(instructorLabel, c);	
		
		//Instructor text field
		instructor = new JTextField(10);
		instructor.setFont(instructor.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 4;
		add(instructor, c);
		
		//Course Section Label
		JLabel sectionLabel = new JLabel("Section Number:");
		sectionLabel.setFont(sectionLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(0, 0, 0, 0);
		add(sectionLabel, c);	
		
		//section text field
		section = new JTextField(10);
		section.setFont(section.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 5;
		add(section, c);
		
		//Location Label
		JLabel locationLabel = new JLabel("Location:");
		locationLabel.setFont(locationLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(0, 0, 0, 0);
		add(locationLabel, c);	
		
		//Location text field
		location = new JTextField(10);
		location.setFont(location.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,0, 0);
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 6;
		add(location, c);
		
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
		c.gridy = 7;
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
			System.out.println("addCourseScreen");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//Creates the course unless it has already been created
	void SubmitButton() {
		//Check if course is unique and in correct format
		int checker = Accounts.checkCourse(courseName.getText(), courseID.getText(),
						maxStudents.getText(), instructor.getText(),
						section.getText(), location.getText());
		if(checker == -2) {
			Accounts.addCourse(courseName.getText(), courseID.getText(),
								maxStudents.getText(), instructor.getText(),
								section.getText(), location.getText());
			JOptionPane.showMessageDialog(null, "Succcess! " + courseName.getText()
											+ " was registered.");
			courseName.setText("");
			courseID.setText("");
			maxStudents.setText("");
			instructor.setText("");
			section.setText("");
			location.setText("");

		}
		else if(checker >= 0){
			JOptionPane.showMessageDialog(null, "Error: this course already exists!");
		}
		else if(checker == -3) {
			JOptionPane.showMessageDialog(null, "Error: please enter answers for "
													+ "every field!");
		}
		else {
			JOptionPane.showMessageDialog(null, "Error: please enter integers for "
											+ "\"Max Students\" and \"Section\"!");
		}
	}
}
