import java.util.*;

class User implements java.io.Serializable {
	
	// Member Variables
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	
	//Constructor with parameters
	public User(String userName, String password, String firstName, String lastName) {	
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;		
	}
	
	//Getters and Setters
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
