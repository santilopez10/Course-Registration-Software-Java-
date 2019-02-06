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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class UnfilledClassesScreen extends JFrame{
	
	JTable table;
	JScrollPane scrollPane;
	JButton goBack, exit;
	
	ToBeSerialized arrays = new ToBeSerialized();
	
	public UnfilledClassesScreen(int studentNum){
		setSize(1700, 1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel title = new JLabel("Unfilled Courses");
		title.setFont(title.getFont().deriveFont(32.0f));
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.weighty = 0.8;
		c.gridx = 0;
		c.gridy = 0;
		add(title, c);
		
		makeTable(c);
		
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
		c.gridx = 2;
		c.gridy = 9;
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
		c.gridx = 3;
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
			System.out.println("UnfilledClassesScreen");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//Make table of unfilled classes
	void makeTable(GridBagConstraints c){
		//Make column headers
		String[] columns = {"Course Name", "Course ID", "Max Students", 
				"Current Students","List of Students", "Instructor",
				"Course Section", "Location"};
		//Get which classes are unfilled
		ArrayList<Integer> unfilledClasses = new ArrayList<>();
		for(int i = 0; i < Accounts.courseName.size(); i++) {
			if(!Accounts.maxStudent.get(i).equals(Accounts.currentStudent.get(i))) {
				unfilledClasses.add(i);
			}
		}
		//Add make a 2D array of the unfilled classes
		Object[][] courses2D = new Object[unfilledClasses.size()][8];
		for(int i = 0; i < unfilledClasses.size(); i++) {
			for(int j = 0; j < 8; j++) {
				switch(j) {
					case 0: courses2D[i][j] = Accounts.courseName.get(unfilledClasses.get(i));
						break;
					case 1: courses2D[i][j] = Accounts.courseID.get(unfilledClasses.get(i));
						break;
					case 2: courses2D[i][j] = String.valueOf(Accounts.maxStudent.get(unfilledClasses.get(i)));
						break;
					case 3: courses2D[i][j] = String.valueOf(Accounts.currentStudent.get(unfilledClasses.get(i)));
						break;
					case 4: courses2D[i][j] = Accounts.listOfStudents.get(unfilledClasses.get(i)).toString();
						break;
					case 5: courses2D[i][j] = Accounts.instructor.get(unfilledClasses.get(i));
						break;
					case 6: courses2D[i][j] = String.valueOf(Accounts.section.get(unfilledClasses.get(i)));
						break;
					case 7: courses2D[i][j] = Accounts.location.get(unfilledClasses.get(i));
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
}
