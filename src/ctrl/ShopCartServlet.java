package ctrl;

import java.io.IOException;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ShopCart;

/**
 * Servlet implementation class ShopCartServlet
 */
@WebServlet("/ShopCartServlet")
public class ShopCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
	

		

		
	}
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		
		int cartSize=0;


		
		//for testing purposes:================================
	//	BookBean book1 = new BookBean("1","Title1", "25", "99");
	//	BookBean book2 = new BookBean("2","Title2", "35", "77");
	//	BookBean book3 = new BookBean("1","I SHOULD NOT SEE THIS TITLE AS BID IS THE SAME AS BOOK1", "25", "99"); //same id number entered on purpose for testing
		//====================================================
				
		
		ShopCart cart = (ShopCart) session.getAttribute("cart");
		if (cart == null) {
			System.out.println("cart is empty");
		}
		
		//for testing purposes:================================
//		cart.addBookToCart(book1, 2);
//		cart.addBookToCart(book2, 1);
//		cart.addBookToCart(book3, 2);
		//I book3 and book1 have the same bid, so the qty should be 4!
		//====================================================
		
		else {
		System.out.println("cart = " + cart.toString());
				
		if (cart.getCartSize() > 0) {
			session.setAttribute("hasItems", true);
		}
		
		//===========Remove Book from a cart ===================
		if (request.getParameter("removeItem") != null) {
			//Book that need to be removed:
			String bid = request.getParameter("removeItem");
			System.out.println("bid to be removed = " +bid);
			cart.removeBookByBid(bid);
			System.out.println("after rem cart = " + cart.toString());

			//if all items removed - then empty cart message to be displayed
			if (cart.getCartSize() == 0) session.setAttribute("hasItems", null);
		}
		//====================================================

		
		//===========Increase Qty of a book by 1 ===================
		if (request.getParameter("increaseQty") != null) {
			String bid = request.getParameter("increaseQty");
			System.out.println("bid to be increase qty = " +bid);
			cart.increaseBookQty(bid);
			System.out.println("after incr qty cart = " + cart.toString());
		}
		//====================================================
		
		//===========Decrease Qty of a book by 1 ===================
		if (request.getParameter("decreaseQty") != null) {
			String bid = request.getParameter("decreaseQty");
			System.out.println("bid to be decrease  qty = " +bid);
			cart.decreaseBookQty(bid);
			System.out.println("after decrease qty cart = " + cart.toString());
			
			//if all items removed - then empty cart message to be displayed
			if (cart.getCartSize() == 0) session.setAttribute("hasItems", null);
		}
		
		session.setAttribute("items", cart.getCartSize());
		cartSize = cart.getCartSize();
		//====================================================
		}
		
		if(request.getParameter("checkoutguest-submit")!=null && cartSize>0) {
			request.getRequestDispatcher("CheckOut").forward(request, response); 
		}
		else if(request.getParameter("checkout-submit")!=null  && cartSize>0) {
			if(session.getAttribute("user")!=null)
				request.getRequestDispatcher("CheckOut").forward(request, response); 
			else
				request.getRequestDispatcher("Login?page=CheckOut").forward(request, response); 
		}
		


		else {
		String target = "/ShoppingCart.jspx";
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
