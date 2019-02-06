import java.util.ArrayList;

class Student extends User implements StudentInterface{
	
	ArrayList<Integer> myCourses = new ArrayList<>();
	
	//Constructor with parameters
	Student(String userName, String password, String firstName, String lastName){
		super(userName, password, firstName, lastName);
	}
	
	public ArrayList<Integer> getMyCourses(){
		return myCourses;
	}
	
	public void addMyCourses(int course) {
		//Add to array of courses
		myCourses.add(course);
		//Add student to num of students in course and list of students
		Accounts.currentStudent.set(course, Accounts.currentStudent.get(course) + 1);
		Accounts.listOfStudents.get(course).add(getFirstName() + " " + getLastName());
	}
	
	public void removeMyCourse(int course) {
		//Find the course and delete it
		for(int i = 0; i < myCourses.size(); i++) {
			if(myCourses.get(i) == course) {
				myCourses.remove(i);
			}
		}
	}
	
	@Override
	public String toString() {
		return getFirstName() + " " + getLastName();
	}
	
}
