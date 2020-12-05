package ctrl;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.BookBean;
import bean.ShopCart;
import model.BookStoreModel;

/**
 * Servlet implementation class ShopCartServlet
 */
@WebServlet("/find")
public class find extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookStoreModel model;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public find() {
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
		
		

		String name = Filter.stripXSS(request.getParameter("search-item"));
		String category = Filter.stripXSS(request.getParameter("category"));
		ShopCart sc=(ShopCart) session.getAttribute("cart");
		if (sc==null) {
			sc=new ShopCart();
		}
		
		
		String search_result="";
		
		try {
			model = new BookStoreModel();
			ArrayList<BookBean>bb=model.findBooks(name, category);
			
			for(BookBean b: bb) {
				int id= b.getBookId();
				String title=b.getBookTitle();
				String author=b.getAuthor();
				String image="images/image"+id+".jpg";
				String book="<div class=\"result\" onclick=\"location.href='Book?id="+id+"';\" >\r\n"
						+ "            <img class=\"book-image\" alt=\"book-image\" src='"+image+"'/>\r\n"
						+ "            <h5 class=\"result-title\">"+title+"</h5>\r\n"
						+ "            <h5 class=\"result-authot\">"+author+"</h5>\r\n"
						+ "            \r\n"
						+ "        </div>";
				search_result+=book;
						
						
				
			}
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.setAttribute("result", search_result);
		session.setAttribute("items", sc.getCartSize());

		
	
		
	
		
		String target = "/Search.jspx";
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
