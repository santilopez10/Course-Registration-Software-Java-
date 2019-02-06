import java.util.ArrayList;

public class ToBeSerialized implements java.io.Serializable{
	ArrayList<User> AdminUsers = Accounts.AdminUsers;
	ArrayList<Student> StudentUsers = Accounts.StudentUsers;
	
	ArrayList<String> courseName = Accounts.courseName;
	ArrayList<String> courseID = Accounts.courseID;
	ArrayList<Integer> maxStudent = Accounts.maxStudent;
	ArrayList<Integer> currentStudent = Accounts.currentStudent;
	ArrayList<ArrayList<String>> listOfStudents = Accounts.listOfStudents;
	ArrayList<String> instructor = Accounts.instructor;
	ArrayList<Integer> section = Accounts.section;
	ArrayList<String> location = Accounts.location;
	
	
}
