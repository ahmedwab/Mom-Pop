package ctrl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.BookBean;
import bean.ReviewBean;
import bean.ShopCart;
import dao.BookDAO;
import dao.ReviewDAO;

/**
 * Servlet implementation class ShopCartServlet
 */
@WebServlet("/Book")
public class Book extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Book() {
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
		
		BookBean bb = null;
		
		ShopCart sc=(ShopCart) session.getAttribute("cart");

		if (sc==null) {
			sc=new ShopCart();
		}
		
		
		String id="0";
		if(request.getParameter("id")!=null) {
			id=request.getParameter("id");
		}
		BookDAO bd;
		String bookinfo="";
		String user=(String) session.getAttribute("user");
		ShopCart cart = (ShopCart) session.getAttribute("cart");
		if(user==null)user="Anonymous";
		if(id!="0") {
		try {
			bd = new BookDAO();
			bb=bd.retriveBookInfoById(id);
			bookinfo+="            <h3 id=\"product-title\">"+bb.getBookTitle()+"</h3>\r\n"
					+ "            <h5 id=\"product-author\">by "+bb.getAuthor()+"</h5><br/>\r\n"
					+ "            <h4 id=\"product-category\">"+bb.getBookCategory()+"</h4><br/>\r\n"
					+ "            <h4 id=\"product-category\">$"+bb.getBookPrice()+"</h4><br/>\r\n";  
			
		//adding to cart
			if(request.getParameter("add")!=null)
			{
				int quantity=Integer.parseInt(request.getParameter("quantity"));
				BookBean b=bd.retriveBookInfoById(id);
				for(int i=0;i<quantity;i++) {
					sc.addBookToCart(b);
				}
									
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		String image="<img id=\"product-image\" alt=\"book-image\" src=\"images/image"+id+".jpg\"/>\r\n";
		
		
		

		
		//Get reviews
		String reviews="";
		ArrayList <ReviewBean> getReviewsArrayList=new ArrayList <ReviewBean>();
		try {
			ReviewDAO rd= new ReviewDAO();
			if(id!="0")
			getReviewsArrayList=rd.getReviews(id);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		for(ReviewBean rb:getReviewsArrayList) {
			
			reviews+="<div class=\"reviews\">\r\n"
					+ "            <h4>"+rb.getAuthor()+" said</h4>\r\n"
					+ "            <p>"+ rb.getReview()+"</p>\r\n"
					+ "        </div>";
			
		}
		
		
		
		
		String num=request.getParameter("quantity");
		System.out.println(sc);
		request.setAttribute("image", image);
		request.setAttribute("bookinfo", bookinfo);
		request.setAttribute("reviews", reviews);
		request.setAttribute("id", id);
		session.setAttribute("cart", sc);
		session.setAttribute("items", sc.getCartSize());




		// add reviews
				 if(request.getParameter("submit-review")!=null)
						{
							System.out.println("review will be added by "+user);
							String text=request.getParameter("review");
							int bid=Integer.parseInt(id);
							
								ReviewDAO rd;
								try {
									rd = new ReviewDAO();
									rd.insert(bid, user, text);
								} catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
						
							
												
						}
	

	
		String target = "/Book.jspx";
		request.getRequestDispatcher(target).forward(request, response); 
		
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
