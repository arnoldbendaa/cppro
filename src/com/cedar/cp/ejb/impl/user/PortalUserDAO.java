/*    */ package com.cedar.cp.ejb.impl.user;
/*    */ 
/*    */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*    */ import com.cedar.cp.util.Log;
/*    */ import com.cedar.cp.util.Timer;
/*    */ import java.sql.Connection;
/*    */ import java.sql.Date;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class PortalUserDAO extends AbstractDAO
/*    */ {
/* 27 */   private static String INSERT_INTO_PORTAL_TABLE = "insert into CEDAR_PORTAL_CHANGED_PASSWORDS ( CPCP_TARGET_APPLICATION, CPCP_TARGET_USERNAME, CPCP_TARGET_PASSWORD, CPCP_ENCODING_METHOD, CPCP_DATE_CHANGED, CPCP_DISABLED ) values( ?,?,?,?,?,? )";
/*    */ 
/*    */   public void updatePortalTable(String userName, String password, String disabled)
/*    */   {
/* 41 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 42 */     PreparedStatement stmt = null;
/*    */     try
/*    */     {
/* 46 */       stmt = getConnection().prepareStatement(INSERT_INTO_PORTAL_TABLE);
/*    */ 
/* 49 */       int col = 1;
/* 50 */       stmt.setInt(col++, 6);
/* 51 */       stmt.setString(col++, userName);
/* 52 */       stmt.setString(col++, password);
/* 53 */       stmt.setInt(col++, 0);
/* 54 */       stmt.setDate(col++, new Date(System.currentTimeMillis()));
/* 55 */       stmt.setString(col, disabled);
/*    */ 
/* 57 */       stmt.executeUpdate();
/*    */     }
/*    */     catch (SQLException sqle)
/*    */     {
/* 61 */       throw handleSQLException("", sqle);
/*    */     }
/*    */     finally
/*    */     {
/* 65 */       closeStatement(stmt);
/* 66 */       closeConnection();
/*    */ 
/* 68 */       if (timer != null)
/*    */       {
/* 70 */         timer.logDebug("updatePortalTable", "Portal User Insert");
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getEntityName()
/*    */   {
/* 77 */     return "Portal";
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.user.PortalUserDAO
 * JD-Core Version:    0.6.0
 */