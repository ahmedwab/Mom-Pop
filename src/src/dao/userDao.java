package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.userBean;

public class userDao {
	private DataSource ds;
	public userDao() throws NamingException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	public int insert(String userName, String Password, String firstName, String lastName, String Email, int permission) throws SQLException, NoSuchAlgorithmException {		
//		String preparedStatement = "insert into Users(userName,firstName,lastName,passWord,Email,permission) values (?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		// prepared statements prevent SQL injection
	
		String preparedStatement = "INSERT INTO Users (userName,firstName,lastName,passWord,Email,permisson) VALUES ('" + userName
					+ "','" + firstName + "','" + lastName + "',HASH('" + Password + "',0),'" + Email + "',"+ permission+")";
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
//		stmt.setString(1, userName);
//		stmt.setString(2, firstName);
//		stmt.setString(3, lastName);
//		stmt.setString(4, Password);
//		stmt.setString(5, Email);
//		stmt.setInt(6, permission);
		//System.out.println("hashed Passowrd is " + hashed_password);
		int result =stmt.executeUpdate();
		return result;
	}
	
	// Return 0 if password not correct, 1 if password is correct
	public int login_check(String userName, String passWord) throws SQLException, NoSuchAlgorithmException{
		int result = 0;
		String password_correct = "";
		String query = "select * from Users where userName = '" + userName + "'";
		String hashed_password = hash_password(passWord);
		Connection con = this.ds.getConnection(); 
		PreparedStatement p = con.prepareStatement(query); 
		ResultSet r = p.executeQuery();
		while(r.next()) {
			password_correct = r.getString("passWord");
			//System.out.println("Password get from database is "+ password_correct);
			//System.out.println("Password enter is " + hashed_password);
		}
		
		if(password_correct.length() == hashed_password.length()) {
			result =1;
			for(int i=0; i< hashed_password.length();i++) {
				int tmp1 = (int)password_correct.charAt(i);
				int tmp2 = (int)hashed_password.charAt(i);
				if (tmp1 != tmp2) {
					result = 0;
				}
			}
		}
		
		return result;
	}
	// Returns user for a given username
		public userBean getUser(String userName) throws SQLException, NoSuchAlgorithmException{
			
			String query = "select * from Users where userName = '" + userName + "'";
			userBean ub=null;
			Connection con = this.ds.getConnection(); 
			PreparedStatement p = con.prepareStatement(query); 
			ResultSet r = p.executeQuery();
			while(r.next()) {
				String firstName = r.getString("firstname");
				String lastName = r.getString("lastname");
				String email = r.getString("email");
				ub= new userBean(userName,"",firstName,lastName,email,1);
				System.out.println(firstName);
				

			}
			
			
			
			return ub;
		}
	
	
	//hash_code method reference https://www.baeldung.com/java-md5
	public static String hash_password(String password) throws NoSuchAlgorithmException {
		
		try { 
			  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(password.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
	}
}
