package dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;
import bean.ReviewBean;

public class ReviewDAO {

	DataSource ds;

	public ReviewDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}

	
	
	//retrieves reviews for given book id
	public ArrayList <ReviewBean> getReviews(String bid) throws SQLException{
		String query = "select * from REVIEW where bid = '" + bid + "'";
		ArrayList<ReviewBean>  reviews = new ArrayList<ReviewBean> ();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query); 
		ResultSet r = p.executeQuery();
		while (r.next()){
				String a = r.getString("author");
				String rev = r.getString("text");
				ReviewBean temp=new ReviewBean(bid,a,rev);
				reviews.add(temp);
			}
		r.close(); p.close(); con.close();
		return reviews;
	 	}



	public void insert(int bid, String author, String text) throws SQLException, NoSuchAlgorithmException {		
		Connection con = this.ds.getConnection();
	
		String preparedStatement = "INSERT INTO REVIEW (bid,author,text) VALUES (" + bid
					+ ",'" + author + "','" + text + "')";
		PreparedStatement stmt = con.prepareStatement(preparedStatement);

		stmt.executeUpdate();
	}
	
		
}
		
	


