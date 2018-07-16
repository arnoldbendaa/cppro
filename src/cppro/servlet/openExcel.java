package cppro.servlet;

import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cppro.utils.MyUtils;

/**
 * Servlet implementation class openExcel
 */
@WebServlet(urlPatterns = { "/openExcel"})
public class openExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public openExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		PreparedStatement pstmt=null; 
	    StringBuffer sb = new StringBuffer();
        String fileName = request.getParameter("fileName");
        String description = null;
        Clob myClob = null;
        Connection conn = MyUtils.getStoredConnection(request);
        try {
            String sql =
                "Select filecontent from excelfile where filename='"+fileName+"'";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                myClob = rs.getClob(1);
                System.out.println("Length of retrieved Clob: " +
                    myClob.length());
            }
            sb.append(myClob.getSubString(1, (int) myClob.length()));
	        response.getWriter().append(sb.toString()).flush();

        }catch (Exception ex) {
            System.out.println("Unexpected exception: " + ex.toString());
        } finally {
            if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
	}

}
