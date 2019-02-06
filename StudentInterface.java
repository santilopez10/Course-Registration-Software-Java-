import java.util.ArrayList;

public interface StudentInterface {
	
	
	//Return the student's courses
	ArrayList<Integer> getMyCourses();
	
	//Add a course to the student's list
	void addMyCourses(int course);
	
	//Remove course from student's list
	void removeMyCourse(int course);
}
