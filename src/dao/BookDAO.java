package dao;

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

public class BookDAO {

	DataSource ds;

	public BookDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}

	
	
	// ==============================================
	// for Requirement B (search by bid) - returning a book 

	public BookBean retriveBookInfoById(String bid) throws SQLException {
		int bookID = Integer.parseInt(bid);
		Connection con = this.ds.getConnection();

		String query = "select * from Book where bid = '" + bid + "' LIMIT 1";
		PreparedStatement p = con.prepareStatement(query); 
		ResultSet res = p.executeQuery();

		if (res.next()) {
			String bId = res.getString("BID");
			String author = res.getString("AUTHOR");
			String bTitle = res.getString("TITLE");
			String bCategory = res.getString("CATEGORY");
			String price = res.getString("PRICE");

			// creating instance of a BookBean
			BookBean book = new BookBean(bid,author, bTitle, price, bCategory);
			res.close();
			con.close();
			return book;
		} else {
			res.close();
			con.close();
			return null;
		}

	}
	public String getBookInfoJSON (String bid) throws SQLException  {
		String result = "";
		
		BookBean book = retriveBookInfoById(bid);

		if (book != null) {
			
			result = "{ bid: " + book.getBookId() + " title: " + book.getBookTitle() + " category: " + book.getBookCategory() + " price: " + book.getBookPrice() + " }";
			
			return result;

						
		} 
		else {
			return "Non-existant BID, try again";
		}
	}
	
	
	// This is used for the Analytics Page	
	public String generateMonthlyReport (String month, String year) throws SQLException {
		
		String result = "";
		boolean found=false;
		
		Connection con = this.ds.getConnection();

		String preparedStatement = "select * from orders where odate >= '"+year+"-"+month+"-01' and odate <=  '"+year+"-"+month+"-31'";
		//System.out.println (preparedStatement);
		PreparedStatement p = con.prepareStatement(preparedStatement);

		ResultSet res = p.executeQuery();
			
		String reportResult = "<table>"
				+ "<tr><td>ID</td><td>OrderID</td><td>UserName</td><td>BID</td><td>QTY</td><td>Price</td><td>Date</td></tr>";

		while (res.next()) {
			
			found=true;
		
			String oID = res.getString("ORDERID");
			String uname = res.getString("USERNAME");
			String bid = res.getString("BID");
			String qty = res.getString("QTY");
			String odate = res.getString("ODATE");
			
			reportResult += "<tr>" + "<td>" + oID + "</td>" + "<td>" + uname + "</td>" +
								"<td>" + bid + "</td>" + "<td>" + qty + "</td>" +
								"<td>" + odate + "</td>" +
							"</tr>";		
		}
			res.close();
			p.close();
			con.close();
			
			if (found==true ) return reportResult;
			else return "No data for these dates";

		
	}
	//retrieving books with a given name and category
	public ArrayList<BookBean> findBooks(String name, String category) throws SQLException{
		String query = "select * from Book where title like '%" + name +"%' or category  like '%"+category+"%'";

		ArrayList<BookBean>  books = new ArrayList<BookBean> ();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query); 
		ResultSet r = p.executeQuery();
		while (r.next()){
				String title = r.getString("title");
				String bid = r.getString("bid");
				String author = r.getString("author");
				String cat = r.getString("category");
				String price=r.getString("price");
				BookBean book = new BookBean(bid,author, title, price, cat);
				books.add(book);
			}
		r.close(); p.close(); con.close();
		return books;
	 	}
	
		
	

}
