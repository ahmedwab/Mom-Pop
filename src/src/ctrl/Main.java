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
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
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
		
		
		//if user is not logged in, the navbar will suggest a login
		String navbar="<a href=\"Login\">Sign In| Register</a>";
		
		String user=(String) session.getAttribute("user");
		System.out.println(user);
		
		
		//if logged in, username will appear
		if(user!=null) {
			navbar="<a href=\"Login\">"+user+"|Log Out</a>";
		}
		
		
		//creates a new cart if it does not exist
		ShopCart sc=(ShopCart) session.getAttribute("cart");
		if (sc==null) {
			sc=new ShopCart();
		}
		
		
		
		session.setAttribute("items", sc.getCartSize());

		session.setAttribute("nav", navbar);
		
	
		
	
		
		String target = "/WelcomePage.jspx";
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
