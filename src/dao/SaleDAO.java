package dao;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;
import bean.ShopCart;

public class SaleDAO {
	
	DataSource ds;
	
	public SaleDAO() throws ClassNotFoundException {
		
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	
	public int insertAddress(String street, String province, String country, String zip, String email) throws SQLException, NoSuchAlgorithmException {		
		Connection con = this.ds.getConnection();
		// prepared statements prevent SQL injection
	
		String preparedStatement = "INSERT INTO ADDRESS (STREET,PROVINCE,COUNTRY,ZIP,EMAIL) VALUES ('" + street
					+ "','" + province + "','" + country +"','"+ zip+"','" + email +"')";
		PreparedStatement stmt = con.prepareStatement(preparedStatement);

	
		stmt.executeUpdate();
		
		 preparedStatement = "SELECT * FROM ADDRESS  ORDER BY ID DESC LIMIT 1";
		 stmt = con.prepareStatement(preparedStatement);
		 
		 ResultSet res = stmt.executeQuery();
		 
		 int num=0;
		 while(res.next()) {
			 num=Integer.parseInt(res.getString("id"));
		 }
		 stmt.close();
		 con.close();
		 res.close();
			
		
		//returns last id
		return num;
	}
	
	//inserts into orders table
	public int insertOrder(String user, int add) throws SQLException, NoSuchAlgorithmException {		
		Connection con = this.ds.getConnection();
		// prepared statements prevent SQL injection
		
	
	
		
		String preparedStatement = "INSERT INTO ORDERS (USER,ADDRESS,DATE) VALUES ('" + user
				+ "'," + add + ",NOW())";
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		stmt.executeUpdate();
		
		 preparedStatement = "SELECT * FROM ORDERS  ORDER BY ID DESC LIMIT 1";
		 stmt = con.prepareStatement(preparedStatement);
		 
		 ResultSet res = stmt.executeQuery();
		
		

		 int num=0;
		 while(res.next()) {
			 num=Integer.parseInt(res.getString("id"));
		 }
		 stmt.close();
		 con.close();
		 res.close();
			
		//returns last id
		return num;
	}
	
	//insert order items
	public void insertOrderItem(ShopCart items,int orderID) throws SQLException, NoSuchAlgorithmException {		
		Connection con = this.ds.getConnection();
		// prepared statements prevent SQL injection
		
		ShopCart sc = items;
		
		Map<BookBean, Integer> books = sc.getShopCart();
		System.out.println(books);
		//adds orderItem for each book
		for (Map.Entry<BookBean, Integer> entry : books.entrySet()) {
			String preparedStatement = "INSERT INTO OrderItem (id,bid,QTY,price) VALUES (" + orderID
					+ "," + entry.getKey().getBookId() +","+entry.getValue()+","+entry.getKey().getBookPrice()*entry.getValue()+")";
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		stmt.executeUpdate();
		
		}
		System.out.println("items added");
		
			
		
	}

}
