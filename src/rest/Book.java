package rest;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import dao.BookDAO;
import dao.SaleDAO;
import model.BookStoreModel;

@Path("products")
public class Book {
	
	@GET
	@Path("/read/")
	@Produces("text/plain")
	
	public String getListOfProductsJSON ( @QueryParam("studentName") String bid ) throws ClassNotFoundException, NamingException {
		String result  = "";
	
		BookStoreModel bookModel =  new BookStoreModel();
		try {
			result = bookModel.getListOfProductsJSON(bid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

		
}
