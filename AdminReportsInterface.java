import java.awt.GridBagConstraints;
import java.io.IOException;

public interface AdminReportsInterface {
	
	//Choose which method to use based on drop down choice
	public void dropDownPicker(int choice, GridBagConstraints c);
	
	//See the students in a course
	public void studentsInCourse(GridBagConstraints c);
	
	//Make a table for all the courses
	public void allCoursesTable(GridBagConstraints c);
	
	//Make a table for the full classes
	public void fullCoursesTable(GridBagConstraints c) throws IOException;
	
	//Write full classes to file
	public void writeToFile(Object[][] courses2D) throws IOException;
}
