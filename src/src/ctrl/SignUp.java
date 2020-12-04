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
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REGISTER_PAGE = "/signup.jspx";
	private BookStoreModel model;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession();
		int register=0;
		String ub=null;
		try {
			model = new BookStoreModel();
		} catch (ClassNotFoundException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String submit_register = Filter.stripXSS(request.getParameter("submit-register"));
		if(submit_register != null) {
			String userName_register = Filter.stripXSS(request.getParameter("register-username"));
			String passWord_register = Filter.stripXSS(request.getParameter("register-password"));
			String firstName_register = Filter.stripXSS(request.getParameter("register-firstname"));
			String lastName_register = Filter.stripXSS(request.getParameter("register-lastname"));
			String Email_register = Filter.stripXSS(request.getParameter("register-email"));
			//System.out.println("userName_register is " + userName_register);
			//System.out.println("passWord_register is " + passWord_register);
			//System.out.println("firstName_register is " + firstName_register);
			//System.out.println("lastName_register is " + lastName_register);
			//System.out.println("Email_register is " + Email_register);
			try {
				register =model.userRegister(userName_register, passWord_register, firstName_register, lastName_register, Email_register);
				if(register==1)	{
					ub=userName_register;
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		session.setAttribute("user", ub);
		
		

		
		
		 if(register==0) {
		request.getRequestDispatcher(REGISTER_PAGE).forward(request, response); 
		}
		else 
			request.getRequestDispatcher("Main").forward(request, response); 
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
			
		
	}

}
