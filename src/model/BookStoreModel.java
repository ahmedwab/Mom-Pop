package model;

import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import bean.BookBean;
import bean.ReviewBean;
import bean.ShopCart;
import bean.userBean;
import dao.BookDAO;
import dao.ReviewDAO;
import dao.SaleDAO;
import dao.userDao;


public class BookStoreModel {

	//Part for database///
	BookDAO bookDao;
	SaleDAO saleDao;
	ReviewDAO reviewDAO;
	private userDao userDao;
	
	//establish connetion in the constructor
	public BookStoreModel() throws ClassNotFoundException, NamingException {
		super();
		this.bookDao = new BookDAO();
		this.saleDao = new SaleDAO();
		this.userDao = new userDao();
		this.reviewDAO = new ReviewDAO();
	}
	
	
	public String getJSONbyBookID (String bookId) throws JSONException, JAXBException, SQLException {
		
		BookBean book = this.bookDao.retriveBookInfoById(bookId);
		
		JAXBContext jc = JAXBContext.newInstance(book.getClass());
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		
		StringWriter sw = new StringWriter();
		sw.write("\n");
		marshaller.marshal(book, new StreamResult(sw));	

		String strXML = sw.toString();
		
		JSONObject jsonObject = XML.toJSONObject(strXML);
		
		return jsonObject.toString();
		
	}
	
	//check user Login status, 0 is failed, 1 is success 
	public int userLogin (String userName, String passWord) throws SQLException, NoSuchAlgorithmException {
		int result = userDao.login_check(userName, passWord);
		return result;
	}
	
	public int userRegister (String userName, String passWord, String firstName, String lastName, String Email) throws SQLException, NoSuchAlgorithmException {
		int result = userDao.insert(userName, passWord, firstName, lastName, Email, 1);
		return result;
	}
	
	//Receives a book item with given id
	
	public BookBean retriveBookInfoById(String bid) throws SQLException {
		return this.bookDao.retriveBookInfoById(bid);
	}
	//gets reviews for a given bid
	public ArrayList <ReviewBean> getReviews(String bid) throws SQLException{
		return this.reviewDAO.getReviews(bid);
	}
	//inserts review for given book
	public void insert(int bid, String author, String text) throws SQLException, NoSuchAlgorithmException {	
		this.reviewDAO.insert(bid, author, text);
	}
	public ArrayList<BookBean> findBooks(String name, String category) throws SQLException{
		return this.bookDao.findBooks(name, category);
	}
	
	
	//returns user object for given name
	public userBean getUser(String userName) throws SQLException, NoSuchAlgorithmException{
		return this.userDao.getUser(userName);
	}
	//inserts new address 
	public int insertAddress(String street, String province, String country, String zip, String email) throws SQLException, NoSuchAlgorithmException {		
		return this.saleDao.insertAddress(street, province, country, zip, email);
	}
	
	//inserts new order
	public int insertOrder(String user, int add) throws SQLException, NoSuchAlgorithmException {	
		return this.saleDao.insertOrder(user, add);
	}
	
	//inserts individual items
	public void insertOrderItem(ShopCart items,int orderID) throws SQLException, NoSuchAlgorithmException {	
		this.saleDao.insertOrderItem(items, orderID);
	}
	
	//This is use for REST Requirment
	public String getListOfProductsJSON (String bid) throws SQLException {
			return this.bookDao.getBookInfoJSON(bid);
					
	}
	
	public String getOrdersByIdJSON (String bid) throws SQLException {
		return this.bookDao.getOrdersByIdJSON(bid);
	}
		
		//This is used for the Analystics Page
		public String generateMonthlyReport (String month, String year)  throws SQLException {
			return this.bookDao.generateMonthlyReport(month, year);
		}
		
		public String topTen() throws SQLException {
			return this.bookDao.topTen();
		}
		
	
	
}
