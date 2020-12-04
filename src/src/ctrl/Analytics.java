package ctrl;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BookStoreModel;
import model.InputValidator;



/**
 * Servlet implementation class Analytics
 */
@WebServlet("/Analytics")
public class Analytics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Analytics() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    public void init(ServletConfig config) throws ServletException {
    	
  		super.init(config);
  		ServletContext context = getServletContext();
		context.setAttribute("inputValidator", new InputValidator());
		
		try {
			context.setAttribute("newBookModel", new BookStoreModel ());
		} catch (ClassNotFoundException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		
  	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession(true);
		String user=(String) session.getAttribute("user");
		
		if(user!=null && !user.equals("admin")) {
			request.getRequestDispatcher("Login").forward(request, response); 
		}
		
		String reporBtn = request.getParameter("report");
		
		if (reporBtn != null ) {
			
			String enteredMonth = request.getParameter("enteredMonth");	
			InputValidator myValidator = (InputValidator) this.getServletContext().getAttribute("inputValidator");
			
			try {
				myValidator.validateMonth(enteredMonth);
			} catch (Exception e1) {
				request.getSession().setAttribute("inputError", e1.getMessage());
				System.out.println("e1.getMessage()=" + e1.getMessage());
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			String enteredYear = request.getParameter("enteredYear");
			try {
				myValidator.validateYear(enteredMonth);
			} catch (Exception e1) {
				request.getSession().setAttribute("inputError", e1.getMessage());
				System.out.println("e1.getMessage()=" + e1.getMessage());
				

				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			BookStoreModel myBookModel = (BookStoreModel) this.getServletContext().getAttribute("newBookModel");
				
			System.out.println("enteredMonth=" + enteredMonth);
			System.out.println("enteredYear=" + enteredYear);

			

			
			String reportResult = null;
			try {
				reportResult = myBookModel.generateMonthlyReport(enteredMonth, enteredYear);
				request.getSession().setAttribute("reportResult", reportResult);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		BookStoreModel myBookModel;
		try {
			myBookModel = new BookStoreModel();
			String topten= myBookModel.topTen();
			request.getSession().setAttribute("topten", topten);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		String target = "/Analytics.jspx";
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
