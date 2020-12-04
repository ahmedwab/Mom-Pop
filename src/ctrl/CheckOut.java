package ctrl;

import java.io.IOException;
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

import bean.userBean;
import dao.userDao;


/**
 * Servlet implementation class ShopCartServlet
 */
@WebServlet("/CheckOut")
public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		
		int attempt=1;
		
		String user= (String) session.getAttribute("user");
		userBean ub = null;
		if(user!=null) {
			userDao ud;
			try {
				ud = new userDao();
				ub = ud.getUser(user);
			} catch (NamingException | NoSuchAlgorithmException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("firstName", ub.get_firstName());
			session.setAttribute("lastName", ub.get_lastName());
			session.setAttribute("email", ub.get_Email());
			
			
		}
		
		//only relevant fields
		String firstName=request.getParameter("first-name");
		String lastName=request.getParameter("last-name");
		String email=request.getParameter("email");
		
		String cardNumber=request.getParameter("payment-card");
		String cardSecurity=request.getParameter("payment-security");
		String expirationDate=request.getParameter("expiration");


		
		


		
		
		
	
		
	
		
		String target = "/CheckOut.jspx";
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
