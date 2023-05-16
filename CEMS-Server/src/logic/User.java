package logic;

public class User {

    private String username, password;
    private String pName, lName, email, Department, type;
    private boolean isOnline, isFound;
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type =  type;
    }

    public void setIsFound(boolean isFound) {
        this.isFound = isFound;
    }

    public boolean getIsFound() {
        return isFound;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

}
