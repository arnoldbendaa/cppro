package cppro.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import cppro.utils.DBUtils;
import cppro.utils.MyUtils;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Servlet implementation class slideShow
 */
@WebServlet("/slideShow")
public class slideShow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public slideShow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = MyUtils.getStoredConnection(request);
		String errorString = null;
		List<String> list = null;
		try {
			list = DBUtils.queryExcelFileName(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		// Store info in request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("fileNameLists", list);
        request.setAttribute("base_url", MyUtils.base_url);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/slideShow.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null; 
	    StringBuffer sb = new StringBuffer();
        String fileName = request.getParameter("fileName");
        String description = null;
        Clob myClob = null;
        Connection conn = MyUtils.getStoredConnection(request);
        JSONArray jArray  = new JSONArray();

        try {
        	jArray = new JSONArray();
        	System.out.println(jArray.toString());
            String sql =
                "select imgurl from excel_slide  where filename='"+fileName+"' order by CREATETIME";
            System.out.println(sql);
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
	        while(rs.next()){
//                myClob = rs.getClob(1);
//                System.out.println("Length of retrieved Clob: " +
//                    myClob.length());
//                sb.append(myClob.getSubString(1, (int) myClob.length()));
	            String imgUrl = rs.getString("imgurl");

                jArray.put(imgUrl);
	        }
	        response.getWriter().append(jArray.toString()).flush();
	        System.out.println(jArray.toString());

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
