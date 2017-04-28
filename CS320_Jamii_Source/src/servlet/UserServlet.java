package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.UserController;
import model.Account;
import model.Group;


public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserController controller = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		String user = (String) req.getSession().getAttribute("username");
		if (user == null) {
			System.out.println("   User: <" + user + "> not logged in or session timed out");
			
			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String button = req.getParameter("buttonPress");
		
		if(button != null){
			if(button.toLowerCase().equals("logout")){
				req.getSession().setAttribute("loggedin",false);
				req.getSession().setAttribute("username", null);
				req.getSession().setAttribute("login_id", -1);
				req.getRequestDispatcher("/_view/home.jsp").forward(req, resp);
			}
		}
		
		List<Group> groups = null;
		controller = new UserController();
		try {
			groups = controller.getUsersGroups(user);
		} catch (SQLException e) {
			
		}
		req.setAttribute("groups", groups);
		
		Account account = new Account();
		controller = new UserController();
		account = controller.returnAccountForUsername(user);
		
		req.setAttribute("account", account);
		
		
			req.getRequestDispatcher("/_view/user.jsp").forward(req, resp);	
		}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			req.getRequestDispatcher("/_view/user.jsp").forward(req, resp);
	}
}
