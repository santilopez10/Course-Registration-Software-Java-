import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class WithdrawCourseScreen extends JFrame{
	
	JComboBox<String> dropDownCourses;
	JButton exit, goBack, enter;
	JLabel dropDownLabel;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	WithdrawCourseScreen(int studentNum) {
		
		setSize(1700, 1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title Label
		JLabel title = new JLabel("Withdraw from a Course");
		title.setFont(title.getFont().deriveFont(32.0f));
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Make drop down and all the sections associated with it
		makeDropDown(c, studentNum);
		
		//Return Button
		goBack = new JButton("Return");
		goBack.setFont(goBack.getFont().deriveFont(24.0f));
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				StudentScreen sS = new StudentScreen(studentNum);
			}
		});
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.ipadx = 0;
		c.ipady = 0;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 4;
		add(goBack, c);
		
		//Exit Button
		exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				serialize();
				setVisible(false);
				dispose();
			}
				
		});
		c.fill = GridBagConstraints.CENTER;
		exit.setFont(exit.getFont().deriveFont(24.0f));
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 2;
		c.gridy = 4;
		add(exit, c);
		
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
			System.out.println("WithdrawCourseScreen");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	void makeDropDown(GridBagConstraints c, int studentNum) {
		//Make drop down label
		dropDownLabel = new JLabel("Which course would you like to withdraw from?");
		c.fill = GridBagConstraints.HORIZONTAL;
		dropDownLabel.setFont(dropDownLabel.getFont().deriveFont(26.0f));
		c.gridwidth = 2;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridheight = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 1;
		add(dropDownLabel, c);	
		//Drop Down for courses
		ArrayList<Integer> myCourses = Accounts.StudentUsers.get(studentNum).getMyCourses();
		String[] courses = new String[myCourses.size()];
		for(int i = 0; i < myCourses.size(); i++) {
			courses[i] = Accounts.courseID.get(myCourses.get(i)) + ": " + Accounts.courseName.get(myCourses.get(i))
							+ " - " + Accounts.instructor.get(myCourses.get(i));
		}
		dropDownCourses = new JComboBox<String>(courses);
		c.fill = GridBagConstraints.HORIZONTAL;
		dropDownCourses.setFont(dropDownCourses.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 2;
		c.gridy = 1;
		add(dropDownCourses, c);
		
		//Enter button
		enter = new JButton("Enter");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Create a string of the student name
				String studentName = Accounts.StudentUsers.get(studentNum).getFirstName();
				studentName = studentName.concat(" " + Accounts.StudentUsers.get(studentNum).getLastName());
				//Remove the student from the list of names and remove course from the student's courses
				int courseToDelete = myCourses.get(dropDownCourses.getSelectedIndex());
				Accounts.listOfStudents.get(courseToDelete).remove(studentName);
				Accounts.currentStudent.set(courseToDelete, Accounts.currentStudent.get(courseToDelete) - 1);
				Accounts.StudentUsers.get(studentNum).removeMyCourse(myCourses.get(dropDownCourses.getSelectedIndex()));
				
				
				JOptionPane.showMessageDialog(null, "Success! You withdrew from " 
														+ dropDownCourses.getSelectedItem());
				
				setVisible(false);
				dispose();
				WithdrawCourseScreen wCS = new WithdrawCourseScreen(studentNum);
				
			}
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		enter.setFont(enter.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 3;
		c.gridy = 1;
		add(enter, c);
		
		setVisible(true);
	}

}
