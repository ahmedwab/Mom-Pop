package model;

import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import bean.BookBean;
import dao.BookDAO;
import dao.SaleDAO;
import dao.userDao;


public class BookStoreModel {

	//Part for database///
	BookDAO bookDao;
	SaleDAO saleDao;
	private userDao userDao;
	
	//establish connetion in the constructor
	public BookStoreModel() throws ClassNotFoundException, NamingException {
		super();
		this.bookDao = new BookDAO();
		this.saleDao = new SaleDAO();
		this.userDao = new userDao();
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
	//This is use for REST Requirment
		public String getListOfProductsJSON (String bid) throws SQLException {
				return this.bookDao.getBookInfoJSON(bid);
					
		}
		
		//This is used for the Analystics Page
		public String generateMonthlyReport (String month, String year)  throws SQLException {
			return this.bookDao.generateMonthlyReport(month, year);
		}
		
	
	
}
