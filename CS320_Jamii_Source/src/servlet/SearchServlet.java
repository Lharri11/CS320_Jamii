package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.SearchController;
import controller.UserController;
import model.Account;
import model.Group;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SearchController controller = null;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
				
		controller = new SearchController();
		String keyword;
		keyword = (String) req.getSession().getAttribute("keyword");
		List<Group> groups = null;
		try {
			if(keyword == null){
				System.out.println("Keyword not found");
			}
			else{
			groups = controller.getGroupsLike(keyword);
			
			}
			System.out.println("debugging: "+ groups.get(0).getName());
			
			
		} catch (SQLException e) {
			
		}
		req.setAttribute("groups", groups);
		
		req.getRequestDispatcher("/_view/results.jsp").forward(req, resp);	
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		

		req.getRequestDispatcher("/_view/results.jsp").forward(req, resp);	
	}

}

/*
 * String keyword=null;
		keyword = req.getParameter("keyword");
		//System.out.println("reached servlet with keyword "+keyword);
		List<Group> groups = null;
		controller = new SearchController();
		try {
			groups = controller.getGroupsLike(keyword);
		}
		catch (SQLException e) {
		}
		req.setAttribute("groups", groups);
		
		*/
