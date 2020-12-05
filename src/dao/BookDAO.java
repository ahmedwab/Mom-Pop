package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		Connection con = this.ds.getConnection();

		String query = "select * from Book where bid = '" + bid + "' LIMIT 1";
		PreparedStatement p = con.prepareStatement(query); 
		ResultSet res = p.executeQuery();

		if (res.next()) {
			String author = res.getString("AUTHOR");
			String bTitle = res.getString("TITTLE");
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
		
		Integer bID = Integer.parseInt(bid);
		
		
		BookBean book = retriveBookInfoById(bID.toString());

		if (book != null) {
			
			result = "{" +"bid: " + book.getBookId() + " author: " + book.getAuthor() + " title: " + book.getBookTitle() + " category: " + book.getBookCategory() + " price: " + book.getBookPrice() +  " }";
			return result;

						
		} 
		else {
			return "Non-existant BID, try again";
		}
	}
	
	public String getOrdersByIdJSON (String bid) throws SQLException  {
		String result = "";
		
		Connection con = this.ds.getConnection();
		
		String query = "select * from OrderItem where bid = '" + bid + "' LIMIT 1";
		PreparedStatement p = con.prepareStatement(query); 
		ResultSet res = p.executeQuery();
		
		result = "{";

		if (res.next()) {
			String oId = res.getString("ID");
			String bId = res.getString("BID");
			String qty = res.getString("QTY");
			String price = res.getString("PRICE");
			
			result+= "[ " + "orderID: " + oId + "bookID: " + bId + " quantity: " +  qty + " price: " + price + " ]";
			
		}
		
		result+= "}";
		res.close();
		p.close();
		con.close();
		
		return result;
	}
	
	
	// This is used for the Analytics Page	
	public String generateMonthlyReport (String month, String year) throws SQLException {
		
		boolean found=false;
		
		Connection con = this.ds.getConnection();
		
		
		//*****Sanitization*****//
		String fromDate = year.toString() + "-" + month.toString() + "-01"; //should get "2020-01-01"
		String toDate = year.toString() + "-" + month.toString() + "-31"; //should get "2020-01-31"
		//*****Sanitization*****//


			

		String preparedStatement = "select * from orders WHERE date >= '"+fromDate+"' and '"+toDate+"' <= '2020-12-31'";
		//System.out.println (preparedStatement);
		PreparedStatement p = con.prepareStatement(preparedStatement);

		ResultSet res = p.executeQuery();
			
		String reportResult = "<table style=\"width:100%\">\r\n"
				+ "  <tr>\r\n"
				+ "    <th>ID</th>\r\n"
				+ "    <th>BID</th> \r\n"
				+ "    <th>QTY</th>\r\n"
				+ "    <th>PRICE</th>\r\n"
				+ "  </tr>";

		while (res.next()) {
			found=true;
		
			String oID = res.getString("ID");
			String  stmt= "select * from ORDERITEM WHERE ID ="+oID;
			PreparedStatement ps = con.prepareStatement(stmt);
			ResultSet r = ps.executeQuery();
			
			while(r.next()) {
				String ID = r.getString("ID");
				String BID = r.getString("BID");
				String QTY = r.getString("QTY");
				String PRICE = r.getString("PRICE");
				reportResult+="<tr>\r\n"
						+ "    <td>"+ID+"</td>\r\n"
						+ "    <td>"+BID+"</td>\r\n"
						+ "    <td>"+QTY+"</td>\r\n"
						+ "    <td>"+PRICE+"</td>\r\n"
						+ "  </tr>";
				
			}
			
			
			
		}
		
			
			reportResult+="</table>";
			
			res.close();
			p.close();
			con.close();
			
			if (found==true ) return reportResult;
			else return "No data for these dates";

		
	}

		public String topTen () throws SQLException {
			
			boolean found=false;
			
			Connection con = this.ds.getConnection();
			



			String preparedStatement = "SELECT BID, SUM(QTY) AS TOTAL FROM ORDERITEM\r\n"
					+ "\r\n"
					+ "GROUP BY BID\r\n"
					+ "\r\n"
					+ "ORDER BY TOTAL DESC\r\n"
					+ "LIMIT 10";
			//System.out.println (preparedStatement);
			PreparedStatement p = con.prepareStatement(preparedStatement);

			ResultSet res = p.executeQuery();
				
			String reportResult = "<table style=\"width:100%\">\r\n"
					+ "  <tr>\r\n"
					+ "    <th>BID</th> \r\n"
					+ "    <th>TOTAL ORDERS</th>\r\n"
					+ "  </tr>";

			
				
				while(res.next()) {
					found=true;
					String ID = res.getString("BID");
					String QTY = res.getString("TOTAL");
					reportResult+="<tr>\r\n"
							+ "    <td>"+ID+"</td>\r\n"
							+ "    <td>"+QTY+"</td>\r\n"
							+ "  </tr>";
					
				}
				
				
				
			
				
				reportResult+="</table>";
				
				res.close();
				p.close();
				con.close();
				
				if (found==true ) return reportResult;
				else return "No orders yet";

			
		}
	
	//retrieving books with a given name and category
	public ArrayList<BookBean> findBooks(String name, String category) throws SQLException{
		String query = "select * from Book where tittle like '%" + name +"%' or category  like '%"+category+"%'";

		ArrayList<BookBean>  books = new ArrayList<BookBean> ();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query); 
		ResultSet r = p.executeQuery();
		while (r.next()){
				String title = r.getString("tittle");
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
