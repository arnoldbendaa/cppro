package cppro.conn;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.aspose.pdf.Document;

public class OracleConnUtils {
	static String hostName = "127.0.0.1";
	static String sid = "orcl";
	public static String userName = "";
	public static String password = "";
	public static String connectionURL = "";
	public static String className = "";
    protected transient static DataSource mDataSource;

	public OracleConnUtils() throws ParserConfigurationException, SAXException, IOException {
//		String realPath=getServletContext().getRealPath("/WEB-INF/new.xml"); or "/new.xml"
		File file = new File("db.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
		        .newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		org.w3c.dom.Document document = documentBuilder.parse(file);
		String usr = document.getElementsByTagName("user").item(0).getTextContent();
		String pwd = document.getElementsByTagName("password").item(0).getTextContent();

	}
	public static Connection getOracleConnection() throws ClassNotFoundException, SQLException {

		// Note: Change the connection parameters accordingly.
		// String hostName = "192.168.0.107";
		// String sid = "cpprod01";
		// String userName = "sys as sysdba";
		// String password = "Mariask90";
		Properties pro = System.getProperties();
		String userName = pro.getProperty("userName");
		String password = pro.getProperty("password");
		String connectionUrl = pro.getProperty("connection-url");
		String driverName = pro.getProperty("driver-class");

		return getOracleConnection(connectionUrl, userName, password);
//		String lookupName = "java:jboss/jdbc/fc";
//		 try
//		 {
//		   InitialContext ic = new InitialContext();
//		   mDataSource = ((DataSource)ic.lookup(lookupName));
//		 }
//		 catch (NamingException ne)
//		 {
//		   throw new RuntimeException("error looking up DataSource " + lookupName + ": " + ne.getMessage(), ne);
//		 }
//		Connection test = null;
//		try {
//			test = mDataSource.getConnection();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return test;

	}

	public static Connection getOracleConnection(String connectionURL, String userName, String password)
			throws ClassNotFoundException, SQLException {

		// Declare the class Driver for ORACLE DB
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		try {
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch(ClassNotFoundException ex) {
			   System.out.println("Error: unable to load driver class!");
			   System.exit(1);
			}

		// URL Connection for Oracle
		// Example: jdbc:oracle:thin:@localhost:1521:db11g
		//String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" + sid;
		Connection conn = null;
		conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
}