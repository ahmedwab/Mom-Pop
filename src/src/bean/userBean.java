package bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class userBean {
	private String userName;
	private String passWord;
	private String firstName;
	private String lastName;
	private String Email;
	private int permission;
	
	public userBean(String userName, String password, String firstName, String lastName, String Email, int permission) {
		this.userName = userName;
		this.passWord = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.Email = Email;
		this.permission = permission;
	}
	
	public userBean() {
		// TODO Auto-generated constructor stub
	}

	public String get_userName() {
		return this.userName;
	}
	
	public String get_password() {
		return this.passWord;
	}
	
	public String get_firstName() {
		return this.firstName;
	}
	
	public String get_lastName() {
		return this.lastName;
	}
	
	public String get_Email() {
		return this.Email;
	}
	
	public int permission() {
		return this.permission;
	}
}
