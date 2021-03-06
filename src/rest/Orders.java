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

@Path("orders")
public class Orders {
	
	@GET
	@Path("/read/")
	@Produces("text/plain")
	
	public String getListOfProductsJSON ( @QueryParam("bid") String bid ) throws ClassNotFoundException, NamingException {
		String result  = "";
	
		BookStoreModel bookModel =  new BookStoreModel();
		try {
			result = bookModel.getOrdersByIdJSON(bid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

		
}
