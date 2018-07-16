package cppro.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cedar.cp.api.base.CPConnection;
import com.softproideas.app.reviewbudget.budget.service.BudgetService;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

import cppro.beans.UserAccount;
import cppro.conn.ConnectionUtils;
import cppro.utils.DBUtils;
import cppro.utils.MyUtils;
import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;
import java.util.List;
/**
 * Servlet implementation class Excel
 */
@Controller
@WebServlet(urlPatterns = { "/Excel"})
public class Excel extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Autowired
    BudgetService budgetService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Excel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
//		int budgetCycleId = Integer.parseInt(request.getParameter("budgetCycleId"));
//		int modelId = Integer.parseInt(request.getParameter("modelId"));
//		Connection conn = MyUtils.getStoredConnection(request);
//		HttpSession session = request.getSession(false);
//		UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
//		int userId = user.getUserId();
//		String userName = user.getUserName();
//
//		if(request.getParameterMap().containsKey("topNode")){
//			JSONArray result = new JSONArray();
//			String topNodeId = request.getParameter("topNode");
//			try {
//				result = DBUtils.getProfileList(conn,userId,modelId,budgetCycleId);
//				response.getWriter().append(result.toString()).flush();	
//				return;
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		if(request.getParameterMap().containsKey("dim0")){
//			int topNodeId= Integer.parseInt(request.getParameter("topNodeId"));
//			int dataEntryProfileId= Integer.parseInt(request.getParameter("dataEntryProfileId"));
//			int dim0 = Integer.parseInt(request.getParameter("dim0"));
//			int dim1 = Integer.parseInt(request.getParameter("dim1"));
//			int dim2 = Integer.parseInt(request.getParameter("dim2"));
//			String dataType = null;
//			if(request.getParameterMap().containsKey("dataType")){
//				dataType = request.getParameter("dataType");
//			}
//	        HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
//	        if ((dim0 != 0) && (dim1 != 0) && (dim2 != 0)) {
//	            selectionsMap.put(new Integer(0), new Integer(dim0));
//	            selectionsMap.put(new Integer(1), new Integer(dim1));
//	            selectionsMap.put(new Integer(2), new Integer(dim2));
//	        }
//	        try {
//	        	DBUtils.request = request;
//				DBUtils.fetchReviewBudget(conn,topNodeId, modelId, budgetCycleId, dataEntryProfileId, selectionsMap, dataType);
//			} catch (ServiceException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//		String modelName = request.getParameter("modelName");
//		String cycleName = request.getParameter("cycleName");
//
//		int structureElementId = Integer.parseInt(request.getParameter("structureElementId"));
//
//        request.setAttribute("base_url", MyUtils.base_url);
//        request.setAttribute("topNodeId", structureElementId);
//        request.setAttribute("modelId",modelId);
//        request.setAttribute("budgetCycleId", budgetCycleId);
//        request.setAttribute("userId",userId);
//        request.setAttribute("userName", userName);
//        request.setAttribute("isAdmin", 1);
//        request.setAttribute("submitModelName",modelName);
//        request.setAttribute("submitCycleName", cycleName);
//		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/reviewbudgetApp/jsp/main.jsp");
//
//		dispatcher.forward(request, response);
	
		
		
		
//		Connection conn = MyUtils.getStoredConnection(request);
//		String errorString = null;
//		List<String> list = null;
//		try {
//			list = DBUtils.queryExcelFileName(conn);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			errorString = e.getMessage();
//		}
//
//		// Store info in request attribute, before forward to views
//		request.setAttribute("errorString", errorString);
//		request.setAttribute("fileNameLists", list);
//		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/excel.jsp");
//		dispatcher.forward(request, response);

		




		PreparedStatement pstmt=null; 

		    ResultSet rs=null; 
		    StringBuffer sb = new StringBuffer();
		    String sql = null;
		    String id = null;
			try {
		        Connection conn = MyUtils.getStoredConnection(request);
				Statement stmt = conn.createStatement();

				File file = new File("/xml_form.sql");
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = null;
				int i=0;
				String line1=null;
				String definition=null;
				line1 =  bufferedReader.readLine();
				while (line1  != null) {
					try{
					i++;
					if(i<3) {line1 = bufferedReader.readLine();continue;}
					
					int newLine = -1;
					while(newLine<0){	
						sb.append(line1);
						line1 = bufferedReader.readLine();
						newLine = line1.indexOf("INSERT INTO");
					}
					line = sb.toString();	
					sb = new StringBuffer();
					int startWorkbook = line.indexOf("<workbook>");
					int endWorkbook = line.indexOf("</workbook>");
					int startSpread = line.indexOf("{\"spread\"");
					int endZip=-1;int endSpread=-1;
					
					if(startWorkbook>0 &&endWorkbook>0){
						definition = line.substring(startWorkbook,endWorkbook+11);
						sql = line.substring(0,startWorkbook-1);
						sql = sql + "empty_clob(),";
						
						String hexString = "504B0304";  
						byte[] bytes = Hex.decodeHex(hexString.toCharArray());
						String zip1 = new String(bytes, "UTF-8");
						hexString = "D0CF11E0";
						bytes = Hex.decodeHex(hexString.toCharArray());
						String zip2 = new String(bytes, "UTF-8");

//						int startZip = line.indexOf("'504B0304");//zip start
						int startZip = line.indexOf(zip1);
//						if(startZip<0) startZip = line.indexOf("'D0CF11E0");
						if(startZip<0) startZip = line.indexOf(zip2);
						String tem = line.substring(endWorkbook+13,startZip-1);
//						String tem = line.substring(endWorkbook+12,startZip);
						sql = sql+tem;
						if(startZip>0){
							String sub = null;
							if(startSpread>startZip)
								sub = line.substring(startZip+1,startSpread);
//							sub = line.substring(startZip,startSpread);
							else
								sub = line.substring(startZip+1);
//								sub = line.substring(startZip);
							endZip = sub.indexOf("'");
						}
						sql = sql+"empty_blob(),empty_clob())";
						if(startSpread>0){
							String sub = line.substring(startSpread);
							int len = sub.length();
							endSpread = sub.indexOf("}'");
						}
						//GET ID 
						int startId = line.indexOf(") VALUES (");
						if(startId>0){
							String sub = line.substring(startId+10,startId+100);
							int endId = sub.indexOf(",");
							id = sub.substring(0,endId);
						}
//						if(i>3){
						if(id.equals("1688")){
							try {
//								String sql2 = "Select DEFINITION from XML_FORM where XML_FORM_ID='"+id+"' for update ";
								conn.setAutoCommit(false);
//								pstmt = conn.prepareStatement(sql);
//								pstmt.executeUpdate();
//								pstmt = conn.prepareStatement(sql2);
//								rs = pstmt.executeQuery();
//								if(rs.next()){ 
//									oracle.sql.CLOB clob=(oracle.sql.CLOB)(rs).getClob("DEFINITION"); 
//									BufferedWriter bw=new BufferedWriter(clob.getCharacterOutputStream()); 
//									bw.write(definition); 
//									bw.close(); 
//								}
								
//								String sql3 = "Select JSON_FORM from XML_FORM where XML_FORM_ID='"+id+"' for update ";
//								pstmt = conn.prepareStatement(sql3);
//								rs = pstmt.executeQuery();
//								if(rs.next()){ 
//									definition = line.substring(startSpread,startSpread+endSpread+1);
//									oracle.sql.CLOB clob=(oracle.sql.CLOB)(rs).getClob("JSON_FORM"); 
//									BufferedWriter bw=new BufferedWriter(clob.getCharacterOutputStream()); 
//									bw.write(definition); 
//									bw.close(); 
//								} 
								PreparedStatement pstmt1 = conn.prepareStatement("update xml_form set excel_file=? where xml_form_id=?");
								BufferedInputStream in = null;
								definition = line.substring(startZip+1,endZip+startZip+1);
								in = new BufferedInputStream(new StringBufferInputStream(definition));
								int nFileSize = (int)definition.length();
								
								pstmt1.setBinaryStream(1, in,(nFileSize));
								pstmt1.setString(2, (id));  // set the PK value
								pstmt1.executeUpdate();
								conn.commit();
								pstmt1.close();

								
								
//								String sql4 = "Select EXCEL_FILE from XML_FORM where XML_FORM_ID='"+id+"' for update ";
//								pstmt = conn.prepareStatement(sql4);
//								rs = pstmt.executeQuery();
//								if(rs.next()){ 
////									
//									definition = line.substring(startZip,endZip+startZip);
//							          BLOB blob = null;
//							          BufferedOutputStream out = null;
//							          BufferedInputStream in = null;
//							          byte[] buf = null;
//							          int bytesRead= 0;  
//							          blob = ((OracleResultSet)rs).getBLOB(1);
//							          out = new BufferedOutputStream(blob.getBinaryOutputStream());
//							          in = new BufferedInputStream(new StringBufferInputStream(definition));
//							          int nFileSize = (int)definition.length();
//							          buf = new byte[nFileSize];
//							          
//							          while ((bytesRead = in.read(buf)) != -1){
//							               out.write(buf, 0, bytesRead);
//							          }
//
//								} 
						        System.out.println("data saved"); 
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						}
					}
//					line = bufferedReader.readLine();
					}catch(Exception ex){
						ex.printStackTrace();
//						line = bufferedReader.readLine();
					}
					
				}
				response.getWriter().append(sql).flush();	
				conn.commit(); //
		        conn.setAutoCommit(true); //
		        rs.close(); 
		        pstmt.close(); 

				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		
		
		
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	doGet(request, response);
		File file = new File("/root/fetchReviewBudget.json");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line =  bufferedReader.readLine();
		response.getWriter().append(line).flush();
	}

}
