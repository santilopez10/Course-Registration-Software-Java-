import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class AdminReports extends JFrame implements AdminReportsInterface{
	
	boolean started = false;
	
	JButton enter, exit, enter2, goBack, writeClasses;
	JLabel dropDownLabel;
	JComboBox<String> dropDownCourses;
	JTable table;
	JScrollPane scrollPane;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	public AdminReports() {
		
		setSize(1700,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Title Label
		JLabel title = new JLabel("Admin Reports");
		c.fill = GridBagConstraints.CENTER;
		title.setFont(title.getFont().deriveFont(32.0f));
		c.gridwidth = 3;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		//Description Label
		JLabel description = new JLabel("Which report would you like to see?");
		c.insets = new Insets(0,5,0,5);
		description.setFont(description.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 1;
		add(description, c);
		
		//Drop Down Menu
		String[] options = {"All Courses", "Full Courses", 
							"Students in Course", "List of Courses Student is in"};
		final JComboBox<String> dropDown = new JComboBox<String>(options);
		c.fill = GridBagConstraints.HORIZONTAL;
		dropDown.setFont(dropDown.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 2;
		c.gridy = 1;
		add(dropDown, c);
		
		//'Enter' button
		enter = new JButton("Enter");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Remove any conflicting components
				if(started) {
					try {
						remove(table);
						remove(scrollPane);
						remove(writeClasses);
					}
					catch(Exception e) {
						
					}
					try {
						remove(enter2);
						remove(dropDownLabel);
						remove(dropDownCourses);
						remove(scrollPane);
					}
					catch(Exception e) {
						
					}
					revalidate();
					repaint();
				}
				started = true;
				int choice = dropDown.getSelectedIndex();
				dropDownPicker(choice, c);
			}
				
		});
		enter.setFont(enter.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 3;
		c.gridy = 1;
		add(enter, c);
		
		//Go Back Button
		goBack = new JButton("Return");
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				AdminScreen adminScreen = new AdminScreen();
			}
		});
		goBack.setFont(goBack.getFont().deriveFont(24.0f));
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 9;
		add(goBack, c);
		
		//Exit button
		exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				serialize();
				setVisible(false);
				dispose();
			}
				
		});
		exit.setFont(exit.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 2;
		c.gridy = 9;
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
	
	//Choose which method to use based on drop down choice
	public void dropDownPicker(int choice, GridBagConstraints c) {	
		switch(choice) {
			case 0: allCoursesTable(c);
				break;
				
			case 1: try {
						fullCoursesTable(c);
					} 
					catch (IOException e) {
					}
				break;
				
			case 2: studentsInCourse(c);
				break;
				
			case 3: coursesStudentIn(c);
				break;
		}	
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
			System.out.println("AdminReports");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//See the students in a course
	public void studentsInCourse(GridBagConstraints c) {
		//Drop down Label for courses
		dropDownLabel = new JLabel("Which course would you like to view?");
		c.fill = GridBagConstraints.HORIZONTAL;
		dropDownLabel.setFont(dropDownLabel.getFont().deriveFont(26.0f));
		c.gridwidth = 2;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridheight = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 2;
		add(dropDownLabel, c);	
		//Drop Down for courses
		String[] courses = new String[Accounts.courseName.size()];
		for(int i = 0; i < Accounts.courseName.size(); i++) {
			courses[i] = Accounts.courseID.get(i) + ": " + Accounts.courseName.get(i)
							+ " - " + Accounts.instructor.get(i);
		}
		dropDownCourses = new JComboBox<String>(courses);
		c.fill = GridBagConstraints.HORIZONTAL;
		dropDownCourses.setFont(dropDownCourses.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 2;
		c.gridy = 2;
		add(dropDownCourses, c);	
		//'Enter' button for second drop down
		enter2 = new JButton("Enter");
		enter2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Try removing any current table
				try {
					remove(table);
					remove(scrollPane);
				}
				catch(Exception e) {
				}
				//Get the selected course
				int choice = dropDownCourses.getSelectedIndex();
				//Make the column title
				String[] column = {"Students in " + 
									(String)dropDownCourses.getSelectedItem()};
				//Add the list of students
				int numStudents = Accounts.listOfStudents.get(choice).size();
				Object[][] students2D = new Object[numStudents][1];
				for(int i = 0; i < numStudents; i++) {
					students2D[i][0] = Accounts.listOfStudents.get(choice).get(i);
				}
				//Make the table
				table = new JTable(students2D, column);
				table.setFont(table.getFont().deriveFont(20.0f));
				table.setRowHeight(30);
				table.setAutoCreateRowSorter(true);
				scrollPane  = new JScrollPane(table);
				c.fill = GridBagConstraints.CENTER;
				c.gridwidth = 4;
				c.ipady = 150;
				c.ipadx = 900;
				c.gridheight = 3;
				c.gridx = 0;
				c.gridy = 4;
				add(scrollPane, c);
				setVisible(true);
			}	
		});
		enter2.setFont(enter2.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 3;
		c.gridy = 2;
		add(enter2, c);
	}
	
	//Make a table for all the courses
	public void allCoursesTable(GridBagConstraints c) {
		//Make headers for the columns
		String[] columns = {"Course Name", "Course ID", "Max Students", 
				"Current Students","List of Students", "Instructor",
				"Course Section", "Location"};
		//Make a 2D array of all the classes
		Object[][] courses2D = new Object[Accounts.courseName.size()][8];
		for(int i = 0; i < Accounts.courseName.size(); i++) {
			for(int j = 0; j < 8; j++) {
				switch(j) {
					case 0: courses2D[i][j] = Accounts.courseName.get(i);
						break;
					case 1: courses2D[i][j] = Accounts.courseID.get(i);
						break;
					case 2: courses2D[i][j] = String.valueOf(Accounts.maxStudent.get(i));
						break;
					case 3: courses2D[i][j] = String.valueOf(Accounts.currentStudent.get(i));
						break;
					case 4: courses2D[i][j] = Accounts.listOfStudents.get(i).toString();
						break;
					case 5: courses2D[i][j] = Accounts.instructor.get(i);
						break;
					case 6: courses2D[i][j] = String.valueOf(Accounts.section.get(i));
						break;
					case 7: courses2D[i][j] = Accounts.location.get(i);
						break;
					
				}
			}
		}
		//Create the table and add a scroll pane to panel
		table = new JTable(courses2D, columns);
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
		table.setAutoCreateRowSorter(true);
		scrollPane  = new JScrollPane(table);
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 4;
		c.ipady = 150;
		c.ipadx = 900;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 4;
		add(scrollPane, c);
		setVisible(true);
		
	}
	
	//Make a table for the full classes
	public void fullCoursesTable(GridBagConstraints c) throws IOException {	
		
		//Make column headers
		String[] columns = {"Course Name", "Course ID", "Max Students", 
				"Current Students","List of Students", "Instructor",
				"Course Section", "Location"};
		//Get which classes are full
		ArrayList<Integer> fullClasses = new ArrayList<>();
		for(int i = 0; i < Accounts.courseName.size(); i++) {
			if(Accounts.maxStudent.get(i).equals(Accounts.currentStudent.get(i))) {
				fullClasses.add(i);
			}
		}
		//Add make a 2D array of the full classes
		Object[][] courses2D = new Object[fullClasses.size()][8];
		for(int i = 0; i < fullClasses.size(); i++) {
			for(int j = 0; j < 8; j++) {
				switch(j) {
					case 0: courses2D[i][j] = Accounts.courseName.get(fullClasses.get(i));
						break;
					case 1: courses2D[i][j] = Accounts.courseID.get(fullClasses.get(i));
						break;
					case 2: courses2D[i][j] = String.valueOf(Accounts.maxStudent.get(fullClasses.get(i)));
						break;
					case 3: courses2D[i][j] = String.valueOf(Accounts.currentStudent.get(fullClasses.get(i)));
						break;
					case 4: courses2D[i][j] = Accounts.listOfStudents.get(fullClasses.get(i)).toString();
						break;
					case 5: courses2D[i][j] = Accounts.instructor.get(fullClasses.get(i));
						break;
					case 6: courses2D[i][j] = String.valueOf(Accounts.section.get(fullClasses.get(i)));
						break;
					case 7: courses2D[i][j] = Accounts.location.get(fullClasses.get(i));
						break;
					
				}
			}				
		}
		//Create the table and add a scroll pane to panel
		table = new JTable(courses2D, columns);
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
		table.setAutoCreateRowSorter(true);
		scrollPane  = new JScrollPane(table);
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 4;
		c.ipady = 150;
		c.ipadx = 900;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 4;
		add(scrollPane, c);
		
		//Make a button to write classes with
		writeClasses = new JButton("Copy Classes to Text File");
		writeClasses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Write the full classes to the text file
				try {
					writeToFile(courses2D);
					JOptionPane.showMessageDialog(null, "Succcess! Classes were copied.");
				} catch (IOException e) {
				}
			}
		});
		writeClasses.setFont(writeClasses.getFont().deriveFont(24.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.weighty = 0.8;
		c.gridx = 1;
		c.gridy = 2;
		add(writeClasses, c);
		
		setVisible(true);	
	}
	
	//Write full classes to file
	public void writeToFile(Object[][] courses2D) throws IOException {
		//Locate file and create Buffered Writer
		File file = new File("C:src\\FullClasses");
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		//Write the classes to the file
		for(int i = 0; i < courses2D.length; i++) {
			writer.write(Arrays.toString(courses2D[i]));
			writer.newLine();
		}
		//Close buffered writer
		writer.close();
		fileWriter.close();
		JOptionPane.showMessageDialog(null, "Succcess! Classes were copied.");
	}
	
	//View the courses a student is in
	void coursesStudentIn(GridBagConstraints c) {
		//Drop down Label for all the students
		dropDownLabel = new JLabel("Which student would you like to view?");
		c.fill = GridBagConstraints.HORIZONTAL;
		dropDownLabel.setFont(dropDownLabel.getFont().deriveFont(26.0f));
		c.gridwidth = 2;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridheight = 1;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 2;
		add(dropDownLabel, c);	
		//Drop Down for courses
		String[] students = new String[Accounts.StudentUsers.size()];
		for(int i = 0; i < Accounts.StudentUsers.size(); i++) {
			students[i] = Accounts.StudentUsers.get(i).getFirstName() + " "
							+ Accounts.StudentUsers.get(i).getLastName();
		}
		dropDownCourses = new JComboBox<String>(students);
		c.fill = GridBagConstraints.HORIZONTAL;
		dropDownCourses.setFont(dropDownCourses.getFont().deriveFont(26.0f));
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 2;
		c.gridy = 2;
		add(dropDownCourses, c);	
		//'Enter' button for second drop down
		enter2 = new JButton("Enter");
		enter2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Try removing any current table
				try {
					remove(table);
					remove(scrollPane);
				}
				catch(Exception e) {
				}
				String[] columns = {"Course Name", "Course ID", "Max Students", 
						"Current Students","List of Students", "Instructor",
						"Course Section", "Location"};
				//Add make a 2D array of the student's classes
				ArrayList<Integer> classes = Accounts.StudentUsers.get(dropDownCourses.getSelectedIndex()).getMyCourses();
				Object[][] courses2D = new Object[classes.size()][8];
				for(int i = 0; i < classes.size(); i++) {
					for(int j = 0; j < 8; j++) {
						switch(j) {
							case 0: courses2D[i][j] = Accounts.courseName.get(classes.get(i));
								break;
							case 1: courses2D[i][j] = Accounts.courseID.get(classes.get(i));
								break;
							case 2: courses2D[i][j] = String.valueOf(Accounts.maxStudent.get(classes.get(i)));
								break;
							case 3: courses2D[i][j] = String.valueOf(Accounts.currentStudent.get(classes.get(i)));
								break;
							case 4: courses2D[i][j] = Accounts.listOfStudents.get(classes.get(i)).toString();
								break;
							case 5: courses2D[i][j] = Accounts.instructor.get(classes.get(i));
								break;
							case 6: courses2D[i][j] = String.valueOf(Accounts.section.get(classes.get(i)));
								break;
							case 7: courses2D[i][j] = Accounts.location.get(classes.get(i));
								break;
							
						}
					}				
				}
				//Create the table and add a scroll pane to panel
				table = new JTable(courses2D, columns);
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
				table.setAutoCreateRowSorter(true);
				scrollPane  = new JScrollPane(table);
				c.fill = GridBagConstraints.CENTER;
				c.gridwidth = 4;
				c.ipady = 150;
				c.ipadx = 900;
				c.gridheight = 3;
				c.gridx = 0;
				c.gridy = 4;
				add(scrollPane, c);
				setVisible(true);
			}	
		});
		enter2.setFont(enter2.getFont().deriveFont(26.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weighty = 0.8;
		c.gridx = 3;
		c.gridy = 2;
		add(enter2, c);
		setVisible(true);
	}		
}
