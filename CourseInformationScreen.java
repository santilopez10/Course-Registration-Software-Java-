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
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CourseInformationScreen extends JFrame{
	
	JButton enter, goBack;
	JComboBox<String> dropDownCourseID;
	JTable table;
	JScrollPane scrollPane;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	public CourseInformationScreen() {
		
		setSize(1700, 1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel title = new JLabel("View Course Information");
		c.fill = GridBagConstraints.CENTER;
		title.setFont(title.getFont().deriveFont(32.0f));
		c.gridwidth = 4;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Description
		JLabel description = new JLabel("Please choose the course you would like to see:");
		c.fill = GridBagConstraints.HORIZONTAL;
		description.setFont(description.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 1;
		add(description, c);
		
		//Make the list of courses
		String[] courses = new String[Accounts.courseID.size()];
		for(int i = 0; i < Accounts.courseID.size(); i++) {
			courses[i] = Accounts.courseID.get(i);
		}
		//Make the drop down for the courses
		dropDownCourseID = new JComboBox<String>(courses);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 10, 0, 0);
		dropDownCourseID.setFont(dropDownCourseID.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 1;
		add(dropDownCourseID, c);	
		//'Enter' button for drop down
		enter = new JButton("Enter");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int choice = dropDownCourseID.getSelectedIndex();
				makeTable(c, choice);
				
			}	
		});
		enter.setFont(enter.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 10, 0, 0);
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 3;
		c.gridy = 1;
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
		c.gridwidth = 4;
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
			System.out.println("CourseInfoScreen");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//Make the table with the course information
	void makeTable(GridBagConstraints c, int choice){
		
		//Make the array of strings for the table header
		String[] column = {"Course Name", "Course ID", "Max Students", 
				"Current Students","List of Students", "Instructor",
				"Course Section", "Location"};
		//Make the 2D array for the table
		Object[][] course = new Object[1][8];
		course[0][0] = Accounts.courseName.get(choice);
		course[0][1] = Accounts.courseID.get(choice);
		course[0][2] = Accounts.maxStudent.get(choice);
		course[0][3] = Accounts.currentStudent.get(choice);
		course[0][4] = Accounts.listOfStudents.get(choice).toString();
		course[0][5] = Accounts.instructor.get(choice);
		course[0][6] = Accounts.section.get(choice);
		course[0][7] = Accounts.location.get(choice);
		//Add the table to the GUI
		table = new JTable(course, column);
		table.getColumnModel().getColumn(1).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.getColumnModel().getColumn(7).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(5);
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(5);
		table.getColumnModel().getColumn(5).setPreferredWidth(90);
		table.getColumnModel().getColumn(6).setPreferredWidth(5);
		table.setFont(table.getFont().deriveFont(20.0f));
		table.setRowHeight(30);
		scrollPane  = new JScrollPane(table);
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 4;
		c.ipady = 200;
		c.ipadx = 900;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 4;
		add(scrollPane, c);
		setVisible(true);
	}
}
