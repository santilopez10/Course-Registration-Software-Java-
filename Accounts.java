import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Accounts {
	static ArrayList<User> AdminUsers = new ArrayList<>();
	static ArrayList<Student> StudentUsers = new ArrayList<>();
	
	static ArrayList<String> courseName = new ArrayList<>();
	static ArrayList<String> courseID = new ArrayList<>();
	static ArrayList<Integer> maxStudent = new ArrayList<>();
	static ArrayList<Integer> currentStudent = new ArrayList<>();
	static ArrayList<ArrayList<String>> listOfStudents = new ArrayList<>();
	static ArrayList<String> instructor = new ArrayList<>();
	static ArrayList<Integer> section = new ArrayList<>();
	static ArrayList<String> location = new ArrayList<>();
	
	
	
	Accounts() {	
		
		//Create an Admin account
		Accounts.addUser(1, "Admin", "Admin01", null, null);
		
		
		//Try deserializing
		if(!deserialize()) {
			//Find File and read contents
			String fileName = "C:src\\MyUniversityCourses.csv";
			String temp = null;
			try {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				bufferedReader.readLine(); //Gets rid of first line
				while((temp = bufferedReader.readLine()) != null) {
					decode(temp);
				}
				bufferedReader.close();
			}
			catch(FileNotFoundException ex){
				System.out.println( "Unable to open file '" + fileName + "'");
				ex.printStackTrace();
			}
			catch (IOException ex) {
				System.out.println( "Error reading file '" + fileName + "'");
				ex.printStackTrace();
			}		
		}
		
	}
	
	//Method to add the info from a String into the 6 arrayLists
	public void decode(String temp) {
		int start = 0;
		int counter = 0;
		int end;
		while(counter < 8) {
			switch(counter) {
				case 0:	end = temp.indexOf(",", start);
						courseName.add(temp.substring(start, end));
						counter++;
						start = end + 1;
						break;					
				case 1:	end = temp.indexOf(",", start);
						courseID.add(temp.substring(start, end));
						counter++;
						start = end + 1;
						break;					
				case 2:	end = temp.indexOf(",", start);
						maxStudent.add(Integer.parseInt(temp.substring(start, end)));
						counter++;
						start = end + 1;
						break;
					
				case 3:	end = temp.indexOf(",", start);
						currentStudent.add(Integer.parseInt(temp.substring(start, end)));
						counter++;
						start = end + 1;
						break;					
				case 4:	end = temp.indexOf(",", start);
						listOfStudents.add(new ArrayList<String>());
						temp.substring(start, end);
						counter++;
						start = end + 1;
						break;					
				case 5:	end = temp.indexOf(",", start);
						instructor.add(temp.substring(start, end));
						counter++;
						start = end + 1;
						break;					
				case 6:	end = temp.indexOf(",", start);
						section.add(Integer.parseInt(temp.substring(start, end)));
						counter++;
						start = end + 1;
						break;					
				case 7:	end = temp.indexOf(",", start);
						location.add(temp.substring(start));
						counter++;
						break;
			}	
		}
	}
	
	//Add a user to the dataBase
	public static void addUser(int typeOfUser, String username, String password, String firstName, String lastName) {

		if(typeOfUser == 1) {
			AdminUsers.add(new Admin(username, password));
		}
		else {
			StudentUsers.add(new Student(username, password, firstName, lastName));
		}
	}
	
	//Check if User exists in the database
	public static int checkUser(String username, String password) {
		//Check Admin username and password in the database
		String full = username.concat(" " + password);
		for(int i = 0; i < AdminUsers.size(); i++) {
			if(full.equals(AdminUsers.get(i).getUserName().concat(" " + AdminUsers.get(i).getPassword()))) {
				return -1;
			}
		}
		//Check Student username and password in Database
		for(int i = 0; i < StudentUsers.size(); i++) {
			if(full.equals(StudentUsers.get(i).getUserName().concat(" " + StudentUsers.get(i).getPassword()))) {
				return i;
			}
		}
		return -2;
	}
	
	//Check if a course is unique
	public static int checkCourse(	String courseName, String courseID, 
									String maxStudent,	String instructor,
									String section, String location) {
		//Make sure all sections have text
		if(courseName.equals("") || courseName.equals(null)) return -3;
		if(courseID.equals("") || courseID.equals(null)) return -3;
		if(maxStudent.equals("") || maxStudent.equals(null)) return -3;
		if(instructor.equals("") || instructor.equals(null)) return -3;
		if(section.equals("") || section.equals(null)) return -3;
		if(location.equals("") || location.equals(null)) return -3;
		//Check if the user input a number into max Students and section
		try {
			Integer.parseInt(maxStudent);
			Integer.parseInt(section);
		}
		catch(NumberFormatException e) {
			return -1;
		}
		
		//Check if there is an identical course
		for(int i = 0; i < Accounts.courseName.size(); i++) {
			if(Accounts.courseName.get(i).equals(courseName)) {
				if(Accounts.courseID.get(i).equals(courseID)) {
					if(Accounts.instructor.get(i).equals(instructor)) {
						if(Accounts.section.get(i) == Integer.parseInt(section)) {
							if(Accounts.location.get(i).equals(location)) return i;
						}
					}
				}
			}
		}
		//It is unique
		return -2;
	}
	
	//Add a course
	public static void addCourse(	String courseName, String courseID, 
									String maxStudent,	String instructor,
									String section, String location) {
		Accounts.courseName.add(courseName);
		Accounts.courseID.add(courseID);
		Accounts.maxStudent.add(Integer.parseInt(maxStudent));
		Accounts.currentStudent.add(0);
		Accounts.listOfStudents.add(new ArrayList<String>());
		Accounts.instructor.add(instructor);
		Accounts.section.add(Integer.parseInt(section));
		Accounts.location.add(location);
	}
	
	//Delete a course
	public static void removeCourse(int courseNum) {
			
		//Find students in the course being removed and delete course from their list
		for(int i = 0; i < Accounts.listOfStudents.get(courseNum).size(); i++) {
			for(int j = 0; j < Accounts.StudentUsers.size(); j++) {
				//Make a string of a students first and last name
				String tempName = Accounts.StudentUsers.get(j).getFirstName();
				tempName = tempName.concat(" " + Accounts.StudentUsers.get(j).getLastName());
				//Find student with matching name and delete the course from their list
				if(Accounts.listOfStudents.get(courseNum).get(i).equals(tempName)) {
					Accounts.StudentUsers.get(j).removeMyCourse(courseNum);
					break;
				}
			}
		}
		Accounts.courseName.remove(courseNum);
		Accounts.courseID.remove(courseNum);
		Accounts.maxStudent.remove(courseNum);
		Accounts.currentStudent.remove(courseNum);
		Accounts.listOfStudents.remove(courseNum);
		Accounts.instructor.remove(courseNum);
		Accounts.section.remove(courseNum);
		Accounts.location.remove(courseNum);
	}
	
	
	//Delete a course based on Course ID
	public static void removeCourse(String courseID) {
		int courseNum = 0;
		for(int i = 0; i < Accounts.courseID.size(); i++) {
			if(Accounts.courseID.get(i).equals(courseID)) {
				courseNum = i;
				break;
			}
		}
		//Find students in the course being removed and delete course from their list
		for(int i = 0; i < Accounts.listOfStudents.get(courseNum).size(); i++) {
			for(int j = 0; j < Accounts.StudentUsers.size(); j++) {
				//Make a string of a students first and last name
				String tempName = Accounts.StudentUsers.get(j).getFirstName();
				tempName = tempName.concat(" " + Accounts.StudentUsers.get(j).getLastName());
				//Find student with matching name and delete the course from their list
				if(Accounts.listOfStudents.get(courseNum).get(i).equals(tempName)) {
					Accounts.StudentUsers.get(j).removeMyCourse(courseNum);
					break;
				}
			}
		}
		Accounts.courseName.remove(courseNum);
		Accounts.courseID.remove(courseNum);
		Accounts.maxStudent.remove(courseNum);
		Accounts.currentStudent.remove(courseNum);
		Accounts.listOfStudents.remove(courseNum);
		Accounts.instructor.remove(courseNum);
		Accounts.section.remove(courseNum);
		Accounts.location.remove(courseNum);
	}
	
	//Deserialize the info
	public boolean deserialize() {
		ToBeSerialized done = null;
		try{
			//FileInputSystem receives bytes from a file
			FileInputStream fis = new FileInputStream("SerializedInfo.ser");
		
			//ObjectInputStream does the deserialization-- it reconstructs the data into an object
			ObjectInputStream ois = new ObjectInputStream(fis);
		
			//Cast as Employee. readObject will take the object from ObjectInputStream
			done = (ToBeSerialized)ois.readObject();
			ois.close();
			fis.close();
			AdminUsers = done.AdminUsers;
			StudentUsers = done.StudentUsers;
			courseName = done.courseName;
			courseID = done.courseID;
			maxStudent = done.maxStudent;
			currentStudent = done.currentStudent;
			listOfStudents = done.listOfStudents;
			instructor = done.instructor;
			section = done.section;
			location = done.location;
		}
		catch(IOException ioe) {
			return false;
		}
		catch(ClassNotFoundException cnfe) {
		    return false;
		}
		return true;
	}
}
