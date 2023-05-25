package logic;

public class User {

    private String username, password;
    private String pName, lName, email, department, type;
    private boolean isOnline, isFound;
    
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type =  type;
    }

    public void setIsFound(boolean isFound) {
        this.isFound = isFound;
    }

    public boolean getIsFound() {
        return this.isFound;
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

    public String getpName() {
        return this.pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getlName() {
        return this.lName;
    }

    public void setlName(String lName) {
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

    public boolean isOnline() {
        return this.isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

}
