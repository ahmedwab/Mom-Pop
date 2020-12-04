package ctrl;

import java.io.IOException;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ShopCart;
import bean.userBean;
import model.BookStoreModel;


/**
 * Servlet implementation class ShopCartServlet
 */
@WebServlet("/CheckOut")
public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookStoreModel model;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOut() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		
		

		
	}
    
    //returns true if string is a number
    public static boolean isNumber(String str) { 
    	  try {  
    	    Double.parseDouble(str);  
    	    return true;
    	  } catch(NumberFormatException e){  
    	    return false;  
    	  }  
    	}
    
    //returns true if inputs are valid
    public static boolean isValid(String cardNumber,String cardSecurity,String expiration, String email) {
    	char at='@';
    	if(cardNumber.length()<16 ||cardNumber.length()>16||cardSecurity.length()<3||cardSecurity.length()>3||expiration.length()<4||expiration.length()>4)
    		return false;
    	if(!isNumber(cardNumber)||!isNumber(cardSecurity)||!isNumber(expiration))
    		return false;
    	for(int i=0;i<email.length();i++) {
    		if(email.charAt(i)==at) return true;
    	}
    	
    	return false;
    	
    	
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		
		Writer resOut = response.getWriter(); 
		
		
		int attempt=1;
		ShopCart cart = (ShopCart) session.getAttribute("cart");
		
		
		String at=(String) session.getAttribute("attempt");
		
		if(at!=null) attempt=Integer.parseInt(at);
		
		
		//get user information
		String user= (String) session.getAttribute("user");
		userBean ub = null;
		if(user!=null) {
			
			try {
				model = new BookStoreModel();
				ub = model.getUser(user);
				
			} catch (NamingException | NoSuchAlgorithmException | SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("firstName", ub.get_firstName());
			session.setAttribute("lastName", ub.get_lastName());
			session.setAttribute("email", ub.get_Email());
			
			
		}
		
		//only relevant fields
		
		String email=Filter.stripXSS(request.getParameter("email"));
		
		String cardNumber=Filter.stripXSS(request.getParameter("payment-card"));
		String cardSecurity=Filter.stripXSS(request.getParameter("payment-security"));
		String expirationDate=Filter.stripXSS(request.getParameter("expiration"));
		String submit=Filter.stripXSS(request.getParameter("submit"));
		
		
		//order completion
		boolean completed=false;
		
		
		//check vaildation if inputs are entered correctly
		if(submit!=null) {
			if(!isValid(cardNumber,cardSecurity,expirationDate,email)) {
				attempt++;
				System.out.println(attempt);
			}
			else
				completed=true;
		
		}
		
		
		String a=Integer.toString(attempt);
		session.setAttribute("attempt",a );
		
		
		if(attempt>1) {
			session.setAttribute("error", "Your order did not go through!");
		}
			
		
		//if more than 3 attempts occur
		if (attempt>3) {
			String output="<!DOCTYPE html>\r\n"
					+ "<html>\r\n"
					+ "    <head><title>Order</title></head>\r\n"
					+ "    <body><h1>We cannot process your order at this time!</h1><br/>"
					+ "		<a href='Main'>Go back to the website</a"
					+ "		</body>\r\n"
					+ "</html>";
			resOut.write(output);
		}
		
		//if user completes order correctly
		else if(completed==true) {
			
			try {
				model = new BookStoreModel();
				
				if(user==null)
					user="Anonymous";
				String street=Filter.stripXSS(request.getParameter("shipping-street1"))+" "+Filter.stripXSS(request.getParameter("shipping-street2"))+", "+Filter.stripXSS(request.getParameter("shipping-city"));
				String province=Filter.stripXSS(request.getParameter("shipping-state"));
				String country="Canada";
				String zip=Filter.stripXSS(request.getParameter("shipping-postal"));
				String semail=Filter.stripXSS(request.getParameter("email"));
				
				//adds address to the table
				int address=model.insertAddress(street, province, country, zip, semail);
				System.out.println(address);

				
				//adds order to the order table
				int orderNumber = model.insertOrder(user, address);
				System.out.println(orderNumber);
				
				
				model.insertOrderItem(cart, orderNumber);
				
				session.setAttribute("cart", null);
				
				
			} catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String output="<!DOCTYPE html>\r\n"
					+ "<html>\r\n"
					+ "    <head><title>Order</title></head>\r\n"
					+ "    <body><h1>Your order has been completed!</h1><br/>"
					+ "		<a href='Main'>Go back to the website</a"
					+ "		</body>\r\n"
					+ "</html>";
			resOut.write(output);
			
		}
		
		
	
		else {
			String target = "/CheckOut.jspx";
			request.getRequestDispatcher(target).forward(request, response); 
			
		}
		

		
		


		
		
		
	
		
	
		
		
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
