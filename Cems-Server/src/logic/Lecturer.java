package logic;
import java.util.Objects;

public class Lecturer {
    private String username, password;
    private String pName, lName, email, department, type;
    private boolean isOnline, isFound;

	public Lecturer() {
	}

	public Lecturer(String username, String password, String pName, String lName, String email, String department, String type, boolean isOnline, boolean isFound) {
		this.username = username;
		this.password = password;
		this.pName = pName;
		this.lName = lName;
		this.email = email;
		this.department = department;
		this.type = type;
		this.isOnline = isOnline;
		this.isFound = isFound;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPName() {
		return this.pName;
	}

	public void setPName(String pName) {
		this.pName = pName;
	}

	public String getLName() {
		return this.lName;
	}

	public void setLName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIsOnline() {
		return this.isOnline;
	}

	public boolean getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean isIsFound() {
		return this.isFound;
	}

	public boolean getIsFound() {
		return this.isFound;
	}

	public void setIsFound(boolean isFound) {
		this.isFound = isFound;
	}

	public Lecturer username(String username) {
		setUsername(username);
		return this;
	}

	public Lecturer password(String password) {
		setPassword(password);
		return this;
	}

	public Lecturer pName(String pName) {
		setPName(pName);
		return this;
	}

	public Lecturer lName(String lName) {
		setLName(lName);
		return this;
	}

	public Lecturer email(String email) {
		setEmail(email);
		return this;
	}

	public Lecturer department(String department) {
		setDepartment(department);
		return this;
	}

	public Lecturer type(String type) {
		setType(type);
		return this;
	}

	public Lecturer isOnline(boolean isOnline) {
		setIsOnline(isOnline);
		return this;
	}

	public Lecturer isFound(boolean isFound) {
		setIsFound(isFound);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Lecturer)) {
			return false;
		}
		Lecturer lecturer = (Lecturer) o;
		return Objects.equals(username, lecturer.username) && Objects.equals(password, lecturer.password) && Objects.equals(pName, lecturer.pName) && Objects.equals(lName, lecturer.lName) && Objects.equals(email, lecturer.email) && Objects.equals(department, lecturer.department) && Objects.equals(type, lecturer.type) && isOnline == lecturer.isOnline && isFound == lecturer.isFound;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, pName, lName, email, department, type, isOnline, isFound);
	}

	@Override
	public String toString() {
		return "{" +
			" username='" + getUsername() + "'" +
			", password='" + getPassword() + "'" +
			", pName='" + getPName() + "'" +
			", lName='" + getLName() + "'" +
			", email='" + getEmail() + "'" +
			", department='" + getDepartment() + "'" +
			", type='" + getType() + "'" +
			", isOnline='" + isIsOnline() + "'" +
			", isFound='" + isIsFound() + "'" +
			"}";
	}
}
