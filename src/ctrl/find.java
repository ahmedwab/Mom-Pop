package ctrl;

import java.io.IOException;
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
import bean.ShopCart;
import dao.BookDAO;

/**
 * Servlet implementation class ShopCartServlet
 */
@WebServlet("/find")
public class find extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		
		

		String name = request.getParameter("search-item");
		String category = request.getParameter("category");
		ShopCart sc=(ShopCart) session.getAttribute("cart");
		if (sc==null) {
			sc=new ShopCart();
		}
		
		
		String search_result="";
		
		try {
			BookDAO bd=new BookDAO();
			ArrayList<BookBean>bb=bd.findBooks(name, category);
			
			for(BookBean b: bb) {
				int id= b.getBookId();
				String cat=b.getBookCategory();
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
		} catch (ClassNotFoundException | SQLException e) {
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
