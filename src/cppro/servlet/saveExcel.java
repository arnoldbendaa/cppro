package cppro.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import cppro.utils.MyUtils;

/**
 * Servlet implementation class saveExcel
 */
@WebServlet(urlPatterns = { "/saveExcel"})
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB
public class saveExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "uploadFiles";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public saveExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath()).flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		PreparedStatement pstmt=null; 
	    ResultSet rs=null; 
	    StringBuffer sb = new StringBuffer();
        String fileName = request.getParameter("fileName");
        fileName = fileName+"---" + System.currentTimeMillis();
        sb.append(request.getParameter("json"));
        Connection conn = MyUtils.getStoredConnection(request);
      try {
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO EXCELFILE(FILENAME,FILECONTENT) VALUES ('"+fileName+"',empty_clob())";
			System.out.println(sql);
			String sql2 = "Select filecontent from excelfile where filename='"+fileName+"' for update";
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			if(rs.next()){ 
				oracle.sql.CLOB clob=(oracle.sql.CLOB)(rs).getClob("FILECONTENT"); 
				BufferedWriter bw=new BufferedWriter(clob.getCharacterOutputStream()); 
				bw.write(sb.toString()); 
				bw.close(); 
			} 
			conn.commit(); //
	        conn.setAutoCommit(true); //
	        System.out.println("data saved"); 
	        rs.close(); 
	        pstmt.close(); 
	        response.getWriter().append("saved").flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
		if (s.trim().startsWith("filename")) {
			return s.substring(s.indexOf("=") + 2, s.length()-1);
		}
		}
		return "";
	}

}
