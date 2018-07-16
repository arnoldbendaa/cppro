package cppro.analysis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cppro.beans.UserAccount;
import cppro.utils.DBUtils;
import cppro.utils.MyUtils;

/**
 * Servlet implementation class CreateAnalysis
 */
@WebServlet("/CreateAnalysis")
public class CreateAnalysis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAnalysis() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
        String level = (String) request.getParameter("level");
        String title = (String) request.getParameter("title");
        System.out.println(level);
        System.out.println(title);
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
        Connection conn = MyUtils.getStoredConnection(request);

		int userId = user.getUserId();
        if(level.equals("1")){//folder create
        	try {
				DBUtils.insertXmlReportFolder(conn, title, userId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else if(level=="2"){//at last layer node create
        	try {
				DBUtils.insertXmlReport(conn, title, userId,0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}       
        }
	}

}
