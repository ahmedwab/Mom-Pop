package ctrl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BookStoreModel;
/**
 * Servlet implementation class BookStoreServlet
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN_PAGE = "/Login.jspx";
	private BookStoreModel model;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String ub=null;
		String error="";
		int login = 0;
		
		String page= Filter.stripXSS(request.getParameter("page"));
		
		String user=(String) session.getAttribute("user");
		//if logged in, log out
				if(user!=null) {
					user=null;
					session.setAttribute("user", null);
				}
		
		try {
			model = new BookStoreModel();
		} catch (ClassNotFoundException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String submit_login = Filter.stripXSS(request.getParameter("submit-login"));
		if (submit_login != null)
		{
			String userName_login = request.getParameter("login-username");
			String passWord_login = request.getParameter("login-password");
			//System.out.println("userName_login is "+ userName_login);	//test input
			//System.out.println("passWord_login is "+ passWord_login); //test input
			try {
				login =model.userLogin(userName_login, passWord_login);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(login != 1) {
				error="<h4 style=\"color:white;\">Username and password combination is invalid</h4>";
			}
			else {
				ub=userName_login;
			}
		}
		
		session.setAttribute("error", error);
		session.setAttribute("user", ub);
	
		
		
		 if(ub!=null && ub.equals("admin")) {
			request.getRequestDispatcher("Analytics").forward(request, response); 
		}
		
		else if(login==0) {
		request.getRequestDispatcher(LOGIN_PAGE).forward(request, response); 
		}
		 
		 //if valid go to page requested or else go to main
		else {
			if (page!=null) {
				request.getRequestDispatcher(page).forward(request, response); 
			}
			request.getRequestDispatcher("Main").forward(request, response); 
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
