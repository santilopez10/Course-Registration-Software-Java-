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
import javax.swing.JTextField;

public class EditCourseScreen extends JFrame {
	
	JButton enter, goBack, submit;
	JLabel maxLabel, instructorLabel, sectionLabel, locationLabel;
	JTextField maxStudents, instructor, section, location;
	JComboBox<String> dropDownCourses;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	public EditCourseScreen(){
		
		setSize(1700,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title Label
		JLabel title = new JLabel("Edit A Course");
		c.fill = GridBagConstraints.CENTER;
		title.setFont(title.getFont().deriveFont(32.0f));
		c.gridwidth = 3;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Description
		JLabel description = new JLabel("Please choose the course you would like to edit:");
		c.fill = GridBagConstraints.CENTER;
		description.setFont(description.getFont().deriveFont(26.0f));
		c.gridwidth = 4;
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
				int choice = dropDownCourses.getSelectedIndex();
				try {
					remove(maxLabel);
					remove(instructorLabel);
					remove(sectionLabel);
					remove(locationLabel);
					remove(maxStudents);
					remove(instructor);
					remove(section);
					remove(location);
					remove(submit);
					
				}
				catch(Exception e) {
					
				}
				whatToEdit(c, choice);
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
		c.gridy = 9;
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
			System.out.println("EditCourseScreen");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//Make the popup after the course is chosen
	void whatToEdit(GridBagConstraints c, int choice) {
		
		JLabel question = new JLabel("What would you like to edit?");
		question.setFont(question.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weighty = 0.8;
		add(question, c);
		
		//Max Student label
		maxLabel = new JLabel("Max Students:       (Current: " + Accounts.maxStudent.get(choice) + ")");
		maxLabel.setFont(maxLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(0, 0, 0, 0);
		add(maxLabel, c);	
		
		//Max Students text field
		maxStudents = new JTextField(10);
		maxStudents.setFont(maxStudents.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 4;
		add(maxStudents, c);
		
		//Course Instructor Label
		instructorLabel = new JLabel("Course Instructor:       (Current: " + Accounts.instructor.get(choice) + ")");
		instructorLabel.setFont(instructorLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 5;
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
		c.gridy = 5;
		add(instructor, c);
		
		//Course Section Label
		sectionLabel = new JLabel("Section Number:       (Current: " + Accounts.section.get(choice) + ")");
		sectionLabel.setFont(sectionLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 6;
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
		c.gridy = 6;
		add(section, c);
		
		//Location Label
		locationLabel = new JLabel("Location:       (Current: " + Accounts.location.get(choice) + ")");
		locationLabel.setFont(locationLabel.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 7;
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
		c.gridy = 7;
		add(location, c);
		
		submit = new JButton("Submit");
		submit.setFont(submit.getFont().deriveFont(24.0f));
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				boolean change = false;
				//Ensure text is not empty and can be parsed to int, then replace
				if(!maxStudents.getText().equals("")) {
					try {
						int newMax = Integer.parseInt(maxStudents.getText());
						if(newMax > Accounts.currentStudent.get(choice)) {
							Accounts.maxStudent.set(choice, newMax);
							change = true;
						}
						else {
							JOptionPane.showMessageDialog(null, "Error: Cannot make max size less than current " 
																	+ "amount of students!");
						}
					}
					catch(Exception e) {
						JOptionPane.showMessageDialog(null, "Error: please enter integers " 
															+ "for \"Max Students\"!");
					}
				}
				//Switch instructor if there is a change
				if(!instructor.getText().equals("")) {
					Accounts.instructor.set(choice, instructor.getText());
					change = true;
				}
				//Switch section if there is a change
				if(!section.getText().equals("")) {
					try {
						Accounts.section.set(choice, Integer.parseInt(section.getText()));
						change = true;
					}
					catch(Exception e) {
						JOptionPane.showMessageDialog(null, "Error: please enter integers" 
															+ "for\"Course Section\"!");
					}
				}
				//Switch location if there is a switch
				if(!location.getText().equals("")) {
					Accounts.location.set(choice, location.getText());
					change = true;
				}
				if(change) {
					//Give message
					JOptionPane.showMessageDialog(null, "Success! Your change was " 
														+ "implemented!");
					setVisible(false);
					dispose();
					EditCourseScreen eCS = new EditCourseScreen();
				}
				else {
					JOptionPane.showMessageDialog(null, "Error: Make a change.");
				}
			}
			
		});
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 8;
		add(submit, c);
		
		setVisible(true);
		
	}
}
