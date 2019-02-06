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

public class StudentScreen extends JFrame{
	final int studentNum;
	
	JButton allCourses, notFullClasses, registerInCourse, 
			withdrawFromCourse, myCourses, exit, logout;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	//Constructor indicating which student signed in
	StudentScreen(int studentNum){
		this.studentNum = studentNum;
		
		setSize(1700, 1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel title = new JLabel("Student Menu");
		title.setFont(title.getFont().deriveFont(32.0f));
		c.fill = GridBagConstraints.CENTER;
		c.weighty = 0.8;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Button for all courses
		allCourses = new JButton("View all Courses");
		allCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				AllCoursesScreen aCS = new AllCoursesScreen(studentNum);
			}
		});
		allCourses.setFont(allCourses.getFont().deriveFont(26.0f));
		c.insets = new Insets(0,5,0,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 1;
		add(allCourses, c);
		
		//View not full courses Course Button
		notFullClasses = new JButton("Unfilled Courses");
		notFullClasses.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				UnfilledClassesScreen uCS = new UnfilledClassesScreen(studentNum);
			}
				
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		notFullClasses.setFont(notFullClasses.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 1;
		add(notFullClasses, c);
		
		//Register in a Course Button
		registerInCourse = new JButton("Register in a Course");
		registerInCourse.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				RegisterInCourse eCS = new RegisterInCourse(studentNum);
			}
				
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		registerInCourse.setFont(registerInCourse.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 2;
		add(registerInCourse, c);
		
		//Withdraw from course button
		withdrawFromCourse = new JButton("Withdraw from Course");
		withdrawFromCourse.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				WithdrawCourseScreen wCS = new WithdrawCourseScreen(studentNum);
			}
				
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		withdrawFromCourse.setFont(withdrawFromCourse.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 2;
		add(withdrawFromCourse, c);
		
		//View my courses Button
		myCourses = new JButton("View my Courses");
		myCourses.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				MyCoursesScreen mCS = new MyCoursesScreen(studentNum);
			}
				
		});
		c.fill = GridBagConstraints.CENTER;
		myCourses.setFont(myCourses.getFont().deriveFont(26.0f));
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 3;
		add(myCourses, c);
		
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
			System.out.println("StudentScreen");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
