/*     */ package com.cedar.cp.ejb.impl.base;
/*     */ 
/*     */ import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.ejb.EntityContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.jboss.dmr.ModelNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.util.Log;
/*     */

import cppro.conn.OracleConnUtils; 

/*     */ public abstract class AbstractDAO
/*     */   implements Serializable
/*     */ {
/* 516 */   protected transient Log _log = new Log(getClass());
/*     */   protected transient DataSource mDataSource;
/*     */   protected transient EntityContext mEntityContext;
/*     */   protected transient Connection mConnection;
/*     */   protected transient long mTimer;
/*     */   protected transient StringTemplateGroup mStringTemplateGroup;

/*     */   public AbstractDAO()
/*     */   {
				
/*  29 */     String lookupName = "java:jboss/jdbc/fc";
//				JndiTemplate jndi = new JndiTemplate();

				Object test = null;
///*  32 */       InitialContext ic = new InitialContext();
////				ic.bind(lookupName, test);
///*  33 */       this.mDataSource = ((DataSource)ic.lookup(lookupName));
				Properties pro = System.getProperties();
				String userName = pro.getProperty("userName");
				String password = pro.getProperty("password");
				String connectionUrl = pro.getProperty("connection-url");
				String driverName = pro.getProperty("driver-class");
				System.out.println(userName);

				this.mDataSource = new DriverManagerDataSource();
				
				try{
					((DriverManagerDataSource) this.mDataSource).setDriverClassName(driverName);
					((AbstractDriverBasedDataSource) this.mDataSource).setUrl(connectionUrl);
					((AbstractDriverBasedDataSource) this.mDataSource).setUsername(userName);
					((AbstractDriverBasedDataSource) this.mDataSource).setPassword(password);
				
				}catch(Exception ex){
					ex.printStackTrace();
				}
/*     */   }
/*     */ 
/*     */   public AbstractDAO(Connection connection)
/*     */   {
/*  48 */     this.mConnection = connection;
/*     */ 
/*  50 */     this.mDataSource = null;
/*     */   }
/*     */ 
/*     */   public AbstractDAO(DataSource ds)
/*     */   {
/*  59 */     this.mDataSource = ds;
/*     */   }
/*     */ 
/*     */   public AbstractDAO(AbstractDAO abstractDAO)
/*     */   {
/*  68 */     if (abstractDAO.getDataSource() != null)
/*  69 */       this.mDataSource = abstractDAO.getDataSource();
/*     */     else
/*  71 */       this.mConnection = abstractDAO.getConnection();
/*     */   }
/*     */ 
/*     */   public DataSource getDataSource()
/*     */   {
/*  80 */     return this.mDataSource;
/*     */   }
/*     */ 
/*     */   public Connection getConnection()
/*     */   {
/*  88 */     if (this.mConnection != null) {
/*  89 */       return this.mConnection;
/*     */     }
/*     */     try
/*     */     {
/*  93 */       if (this.mDataSource != null) {
/*  94 */         this.mConnection = this.mDataSource.getConnection();
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*  99 */       throw new RuntimeException(sqle.getMessage(), sqle);
/*     */     }
/* 101 */     return this.mConnection;
/*     */   }

	/**
	 * @throws SQLException
	 */
	public static void startOracleDebugger(Connection concection) {
		// Connect to SQL Developer listenning on localhost:4000
		if (Boolean.getBoolean("oracle.debug")) {
			try {
				CallableStatement cs = concection
						.prepareCall("{CALL DBMS_DEBUG_JDWP.CONNECT_TCP(?,?)}");
				cs.setString(1, "localhost");
				cs.setString(2, "4000");
				cs.execute();
				System.out
						.println("Successfully connected debugger to SQL Developer localhost:4000");
			} catch (RuntimeException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @throws SQLException
	 */
	public static void stopOracleDebugger(Connection concection) {
		// Close connection to SQL Developer listenning on localhost:4000
		if (Boolean.getBoolean("oracle.debug")) {
			try {
				if(Boolean.getBoolean("oracle.debug")){
					CallableStatement cs=concection.prepareCall("{CALL DBMS_DEBUG_JDWP.DISCONNECT(DBMS_DEBUG_JDWP.CURRENT_SESSION_ID())}");
					cs.execute();
					System.out.println("Disconnecting debugger from SQL Developer localhost:4000");
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
/*     */ 
/*     */   protected void closeConnection()
/*     */   {
/*     */     try
/*     */     {
/* 111 */       if ((this.mConnection != null) && (!this.mConnection.isClosed()) && (this.mDataSource != null))
/*     */       {
/* 114 */         this.mConnection.close();
/* 115 */         this.mConnection = null;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 120 */       System.err.println("Exception when closing connection " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void closeResultSet(ResultSet result)
/*     */   {
/*     */     try
/*     */     {
/* 131 */       if (result != null)
/*     */       {
/* 133 */         result.close();
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 138 */       System.err.println("Exception when closing result set " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void closeStatement(Statement stmt)
/*     */   {
/*     */     try
/*     */     {
/* 149 */       if (stmt != null)
/*     */       {
/* 151 */         stmt.close();
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 156 */       System.err.println("Exception when closing statement " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public String clobToString(CLOB clob) throws SQLException
/*     */   {
/* 162 */     BufferedReader br = null;
/*     */     try
/*     */     {
/* 165 */       br = new BufferedReader(clob.getCharacterStream());
/* 166 */       char[] chars = new char[(int)clob.length()];
/* 167 */       br.read(chars);
/* 168 */       String str = new String(chars);
/*     */       return str;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 172 */       throw new SQLException(e.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 178 */         br.close(); } catch (IOException e) {
/*     */       }
/*     */     }
/* 180 */     //throw localObject;
/*     */   }
/*     */ 
/*     */   public byte[] blobToByteArray(BLOB blob)
/*     */     throws SQLException
/*     */   {
/* 186 */     InputStream is = null;
/*     */     try
/*     */     {
/* 189 */       is = blob.getBinaryStream();
/* 190 */       byte[] bytes = new byte[(int)blob.length()];
/* 191 */       is.read(bytes);
/* 192 */       byte[] arrayOfByte1 = bytes;
/*     */       return arrayOfByte1;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 196 */       throw new SQLException(e.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 202 */         is.close(); } catch (IOException e) {
/*     */       }
/*     */     }
/* 204 */     //throw localObject;
/*     */   }
/*     */ 
/*     */   public void updateClob(CLOB clob, String str)
/*     */     throws SQLException
/*     */   {
/* 211 */     if (str.length() < clob.length()) {
/* 212 */       clob.trim(str.length());
/*     */     }
/* 214 */     BufferedWriter br = new BufferedWriter(clob.getCharacterOutputStream());
/*     */     try
/*     */     {
/* 217 */       br.write(str);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 221 */       throw new SQLException(e.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 227 */         br.flush();
/* 228 */         br.close();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 232 */         throw new SQLException(e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateBlob(BLOB blob, byte[] bytes) throws SQLException
/*     */   {
/* 239 */     if (bytes == null) {
/* 240 */       bytes = new byte[0];
/*     */     }
/*     */ 
/* 243 */     if (bytes.length < blob.length()) {
/* 244 */       blob.trim(bytes.length);
/*     */     }
/* 246 */     OutputStream os = blob.getBinaryOutputStream();
/*     */     try
/*     */     {
/* 249 */       os.write(bytes);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 253 */       throw new SQLException(e.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 259 */         os.flush();
/* 260 */         os.close();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 264 */         e.printStackTrace();
/* 265 */         throw new SQLException(e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected RuntimeException handleSQLException(String sql, SQLException sqle)
/*     */   {
/* 272 */     return handleSQLException(null, sql, sqle);
/*     */   }
/*     */ 
/*     */   protected RuntimeException handleSQLException(PrimaryKey pk, String sql, SQLException sqle)
/*     */   {
/* 277 */     StringBuffer msg = new StringBuffer();
/* 278 */     if (pk != null) {
/* 279 */       msg.append(pk.toString() + ',');
/*     */     }
/* 281 */     if (sqle.getSQLState() != null) {
/* 282 */       msg.append("sqlState=" + sqle.getSQLState() + ',');
/*     */     }
/* 284 */     if (sqle.getErrorCode() != 0) {
/* 285 */       msg.append("sqlCode=" + sqle.getErrorCode());
/*     */     }
/* 287 */     this._log.error("handleSQLException", msg.toString());
/* 288 */     this._log.error("handleSQLException", sql, sqle);
/*     */ 
/* 290 */     if (sqle.getMessage() != null) {
/* 291 */       msg.append(' ' + sqle.getMessage());
/*     */     }
/* 293 */     CPException e = new CPException(msg.toString());
/*     */ 
/* 295 */     e.setStackTrace(sqle.getStackTrace());
/*     */ 
/* 297 */     return e;
/*     */   }
/*     */ 
/*     */   protected Long checkLong(long value, ResultSet rs)
/*     */     throws SQLException
/*     */   {
/* 304 */     Long obj = null;
/*     */ 
/* 306 */     if (!rs.wasNull()) {
/* 307 */       obj = new Long(value);
/*     */     }
/* 309 */     return obj;
/*     */   }
/*     */ 
/*     */   protected Integer checkInteger(int value, ResultSet rs)
/*     */     throws SQLException
/*     */   {
/* 315 */     Integer obj = null;
/*     */ 
/* 317 */     if (!rs.wasNull()) {
/* 318 */       obj = new Integer(value);
/*     */     }
/* 320 */     return obj;
/*     */   }
/*     */ 
/*     */   protected Short checkShort(short value, ResultSet rs)
/*     */     throws SQLException
/*     */   {
/* 326 */     Short obj = null;
/*     */ 
/* 328 */     if (!rs.wasNull()) {
/* 329 */       obj = new Short(value);
/*     */     }
/* 331 */     return obj;
/*     */   }
/*     */ 
/*     */   protected Boolean checkBoolean(boolean value, ResultSet rs)
/*     */     throws SQLException
/*     */   {
/* 337 */     Boolean obj = null;
/*     */ 
/* 339 */     if (!rs.wasNull()) {
/* 340 */       obj = new Boolean(value);
/*     */     }
/* 342 */     return obj;
/*     */   }
/*     */ 
/*     */   protected Integer getWrappedIntegerFromJdbc(ResultSet rs, int col) throws SQLException
/*     */   {
/* 347 */     int value = rs.getInt(col);
/* 348 */     if (!rs.wasNull()) {
/* 349 */       return new Integer(value);
/*     */     }
/* 351 */     return null;
/*     */   }
/*     */ 
/*     */   protected Short getWrappedShortFromJdbc(ResultSet rs, int col) throws SQLException {
/* 355 */     short value = rs.getShort(col);
/* 356 */     if (!rs.wasNull()) {
/* 357 */       return new Short(value);
/*     */     }
/* 359 */     return null;
/*     */   }
/*     */ 
/*     */   protected Long getWrappedLongFromJdbc(ResultSet rs, int col) throws SQLException {
/* 363 */     long value = rs.getLong(col);
/* 364 */     if (!rs.wasNull()) {
/* 365 */       return new Long(value);
/*     */     }
/* 367 */     return null;
/*     */   }
/*     */ 
/*     */   protected Boolean getWrappedBooleanFromJdbc(ResultSet rs, int col) throws SQLException {
/* 371 */     String value = rs.getString(col);
/* 372 */     if (!rs.wasNull()) {
/* 373 */       return new Boolean(value.equals("Y"));
/*     */     }
/* 375 */     return null;
/*     */   }
/*     */ 
/*     */   protected void setWrappedPrimitiveToJdbc(PreparedStatement stmt, int col, Integer o) throws SQLException
/*     */   {
/* 380 */     if (o == null)
/* 381 */       stmt.setNull(col, 2);
/*     */     else
/* 383 */       stmt.setInt(col, o.intValue());
/*     */   }
/*     */ 
/*     */   protected void setWrappedPrimitiveToJdbc(PreparedStatement stmt, int col, Short o) throws SQLException {
/* 387 */     if (o == null)
/* 388 */       stmt.setNull(col, 2);
/*     */     else
/* 390 */       stmt.setShort(col, o.shortValue());
/*     */   }
/*     */ 
/*     */   protected void setWrappedPrimitiveToJdbc(PreparedStatement stmt, int col, Long o) throws SQLException {
/* 394 */     if (o == null)
/* 395 */       stmt.setNull(col, 2);
/*     */     else
/* 397 */       stmt.setLong(col, o.longValue());
/*     */   }
/*     */ 
/*     */   protected void setWrappedPrimitiveToJdbc(PreparedStatement stmt, int col, Boolean o) throws SQLException {
/* 401 */     if (o == null)
/* 402 */       stmt.setNull(col, 1);
/*     */     else
/* 404 */       stmt.setString(col, o.booleanValue() ? "Y" : " ");
/*     */   }
/*     */ 
/*     */   public abstract String getEntityName();
/*     */ 
/*     */   protected StringTemplate getTemplate(String name)
/*     */   {
/* 417 */     StringTemplate stringTemplate = getTemplateGroup().getTemplateDefinition(name);
/*     */ 
/* 419 */     if (stringTemplate != null) {
/* 420 */       stringTemplate.reset();
/*     */     }
/* 422 */     return stringTemplate;
/*     */   }
/*     */ 
/*     */   private StringTemplateGroup getTemplateGroup()
/*     */   {
/* 431 */     String fullClassName = getClass().getName();
/* 432 */     String stringTemplateFileName = fullClassName.substring(fullClassName.lastIndexOf(46) + 1) + ".stg";
/*     */     try
/*     */     {
/* 435 */       if (this.mStringTemplateGroup == null)
/*     */       {
/* 437 */         this.mStringTemplateGroup = new StringTemplateGroup(getReader(stringTemplateFileName));
/*     */       }
/* 439 */       return this.mStringTemplateGroup;
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/* 443 */     throw new IllegalStateException("Failed to load string template group file:" + stringTemplateFileName);
/*     */   }
/*     */ 
/*     */   protected Reader getReader(String resourceName)
/*     */     throws IOException
/*     */   {
/* 457 */     InputStream is = getClass().getResourceAsStream(resourceName);
/* 458 */     if (is != null)
/*     */     {
/* 460 */       return new InputStreamReader(is);
/*     */     }
/*     */ 
/* 465 */     File f = new File(resourceName);
/* 466 */     return new FileReader(f);
/*     */   }
/*     */ 
/*     */   public void setAllConstraintsDeferred(boolean sessionLevel)
/*     */     throws SQLException
/*     */   {
/* 478 */     PreparedStatement ps = null;
/*     */     try
/*     */     {
/* 481 */       ps = getConnection().prepareStatement(sessionLevel ? "alter session set constraints = deferred" : "set constraints all deferred");
/*     */ 
/* 484 */       ps.executeUpdate();
/*     */     }
/*     */     finally
/*     */     {
/* 488 */       closeStatement(ps);
/* 489 */       closeConnection();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAllConstraintsImmediate(boolean sessionLevel)
/*     */     throws SQLException
/*     */   {
/* 501 */     PreparedStatement ps = null;
/*     */     try
/*     */     {
/* 504 */       ps = getConnection().prepareStatement(sessionLevel ? "alter session set constraints = immediate" : "set constraints all immediate");
/*     */ 
/* 507 */       ps.executeUpdate();
/*     */     }
/*     */     finally
/*     */     {
/* 511 */       closeStatement(ps);
/* 512 */       closeConnection();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.base.AbstractDAO
 * JD-Core Version:    0.6.0
 */