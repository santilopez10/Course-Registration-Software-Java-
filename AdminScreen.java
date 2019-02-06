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

public class AdminScreen extends JFrame{
	
	JButton newCourse, deleteCourse, editCourse, courseInfo, registerStudent,
			reports, exit, logout;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	public AdminScreen() {
		
		setSize(1700,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title Label
		JLabel title = new JLabel("Admin Menu");
		c.fill = GridBagConstraints.CENTER;
		title.setFont(title.getFont().deriveFont(32.0f));
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Add Course Button
		newCourse = new JButton("Add a Course");
		newCourse.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				AddCourseScreen acs = new AddCourseScreen();
			}
				
		});
		newCourse.setFont(newCourse.getFont().deriveFont(26.0f));
		c.insets = new Insets(0,5,0,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 1;
		add(newCourse, c);
		
		//Delete a Course Button
		deleteCourse = new JButton("Delete a Course");
		deleteCourse.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				DeleteCourseScreen acs = new DeleteCourseScreen();
			}
				
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		deleteCourse.setFont(deleteCourse.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 1;
		add(deleteCourse, c);
		
		//Edit a Course Button
		editCourse = new JButton("Edit a Course");
		editCourse.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				EditCourseScreen eCS = new EditCourseScreen();
			}
				
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		editCourse.setFont(editCourse.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 2;
		add(editCourse, c);
		
		//Get Course Info Button
		courseInfo = new JButton("Course Information");
		courseInfo.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				CourseInformationScreen cIS = new CourseInformationScreen();
			}
				
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		courseInfo.setFont(courseInfo.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 2;
		add(courseInfo, c);
		
		//Register a Student Button
		registerStudent = new JButton("Register an Account");
		registerStudent.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				NewStudentScreen newStudent = new NewStudentScreen();
			}
				
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		registerStudent.setFont(registerStudent.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 3;
		add(registerStudent, c);
		
		//Reports Button
		reports = new JButton("Reports");
		reports.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				AdminReports ar = new AdminReports();
			}
				
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		reports.setFont(reports.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 3;
		add(reports, c);
		
		//Exit button
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
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 4;
		add(exit, c);
		
		//Logout Button
		logout = new JButton("Logout");
		logout.setFont(logout.getFont().deriveFont(24.0f));
		logout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				CRS crs = new CRS();
			}
		});
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 4;
		add(logout, c);
		
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
			System.out.println("AdminScreen");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
