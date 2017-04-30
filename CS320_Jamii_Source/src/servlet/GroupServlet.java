package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.GroupController;
import controller.UserController;
import model.Account;
import model.Group;
import model.Post;


public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GroupController controller = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String user = (String)req.getSession().getAttribute("username");
		if (user == null) {
			System.out.println("   User: <" + user + "> not logged in or session timed out");

			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		
		List<Group> groups = null;
		List<Post> posts = null;
		controller = new GroupController();
		int thisgroup = 0;
		thisgroup = (Integer)req.getSession().getAttribute("GroupID");
		try {
			if(thisgroup == 0){
				System.out.println("No group found");
			}
			else{
				groups = controller.getGroupbyID(thisgroup);
				posts = controller.getPostsbyGroupID(thisgroup);
				//System.out.println(posts.get(0).getText()+"\n"+posts.get(1).getText());

				
			}
		} catch (SQLException e) {

		}
		
		
		
		req.setAttribute("groups", groups);
		req.setAttribute("posts", posts);
		
		Account account = new Account();
		controller = new GroupController();
		account = controller.returnAccountForUsername(user);

		req.setAttribute("account", account);

		req.getRequestDispatcher("/_view/group.jsp").forward(req, resp);		
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/_view/group.jsp").forward(req, resp);
	}
}
