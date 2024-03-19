package application.Controllers.classes;

public class StaffMember {
	private int id;
	private String fname;
	private String lname;
	private String role;
	private String phone_number;
	private String hire_date;
	
	public StaffMember(String fname, String lname, String role, String phone_number, String hire_date) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.role = role;
		this.phone_number = phone_number;
		this.hire_date = hire_date;
	}
	
	public StaffMember(int id, String fname, String lname, String role, String phone_number, String hire_date) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.role = role;
		this.phone_number = phone_number;
		this.hire_date = hire_date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public String getHire_date() {
		return hire_date;
	}
	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}
	
	
}
