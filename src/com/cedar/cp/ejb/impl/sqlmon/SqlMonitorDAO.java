/*     */ package com.cedar.cp.ejb.impl.sqlmon;
/*     */ 
/*     */ import com.cedar.cp.api.base.EntityList;
/*     */ import com.cedar.cp.api.sqlmon.SqlDetails;
/*     */ import com.cedar.cp.dto.base.EntityListImpl;
/*     */ import com.cedar.cp.dto.sqlmon.SqlDetailsImpl;
/*     */ import com.cedar.cp.dto.sqlmon.SqlPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.SqlBuilder;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class SqlMonitorDAO extends AbstractDAO
/*     */ {
/*  31 */   Log _log = new Log(getClass());
/*     */ 
/*     */   public SqlMonitorDAO(Connection connection)
/*     */   {
/*  38 */     super(connection);
/*     */   }
/*     */ 
/*     */   public SqlMonitorDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SqlMonitorDAO(DataSource ds)
/*     */   {
/*  54 */     super(ds);
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/*  59 */     return "SqlMonitorDAO";
/*     */   }
/*     */ 
/*     */   public EntityList getAllOracleSessions()
/*     */   {
/*  68 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "/* SqlMonitorDAO */", "with blocked_sessions as", "(", "select   s2.SID, s2.SERIAL#, s2.USERNAME, s2.MACHINE, s2.MODULE, l2.TYPE", "        ,s1.SID         as BLOCKING_SID", "        ,s1.SERIAL#     as BLOCKING_SERIAL#", "        ,s1.USERNAME    as BLOCKING_USERNAME", "        ,s1.MACHINE     as BLOCKING_MACHINE", "        ,s1.MODULE      as BLOCKING_MODULE", "        ,l1.TYPE        as BLOCKING_TYPE", "from    V$LOCK l1", "        ,V$SESSION s1", "        ,V$LOCK l2", "        ,V$SESSION s2", "where   s1.SID = l1.SID", "and     s2.SID=l2.SID", "and     l1.BLOCK=1 and l2.REQUEST > 0", "and     l1.ID1 = l2.ID1", "and     l2.ID2 = l2.ID2", ")", "select   sess.SID as \"Sid\"", "        ,sess.SERIAL# as \"Serial#\"", "        ,sess.USERNAME as \"User\"", "        ,PROGRAM", "         ||case when sess.USERNAME is not null and sess.STATUS = 'ACTIVE' and sess.TYPE = 'USER' then ' - active' end ", "         as \"Program\"", "        ,case when blcked.BLOCKING_SID is not null then", "              'BLOCKED BY '''||blcked.BLOCKING_SID||','||blcked.BLOCKING_SERIAL#", "              ||''' ('||blcked.BLOCKING_USERNAME", "              ||'@'||blcked.BLOCKING_MACHINE", "              ||' - '||blcked.BLOCKING_MODULE||')'", "              when blcking.BLOCKING_SID is not null  then", "              'lock '||blcking.BLOCKING_TYPE", "              ||' IS BLOCKING '''||blcking.SID||','||blcking.SERIAL#", "              ||''' ('||blcking.USERNAME", "              ||'@'||blcking.MACHINE", "              ||' - '||blcking.MODULE||')'", "              when SQL_TEXT is null then sess.EVENT", "              else SQL_TEXT  end as \"Details\"", "from    V$SESSION sess", "left    join  blocked_sessions blcked", "        on (blcked.SID = sess.SID and blcked.SERIAL# = sess.SERIAL#)", "left    join blocked_sessions blcking", "        on (blcking.BLOCKING_SID = sess.SID and blcking.BLOCKING_SERIAL# = sess.SERIAL#)", "left    join V$SQLTEXT_WITH_NEWLINES sql", "        on (    sql.HASH_VALUE = sess.SQL_HASH_VALUE", "            and sql.ADDRESS = sess.SQL_ADDRESS", "            and sql.PIECE=0", "            )", "order   by  sess.TYPE desc,sess.USERNAME nulls last, sess.STATUS" });
/*     */ 
/* 120 */     SqlExecutor sqle = new SqlExecutor("getAllOracleSessions", getDataSource(), sqlb, this._log);
/* 121 */     sqle.setLogSql(false);
/* 122 */     ResultSet resultSet = sqle.getResultSet();
/* 123 */     EntityListImpl results = null;
/*     */     try
/*     */     {
/* 126 */       String[] columnNames = { "PK", "Sid,Serial#", "User", "Program", "Details" };
/* 127 */       results = new EntityListImpl(columnNames, new Object[0][columnNames.length]);
/* 128 */       while (resultSet.next())
/*     */       {
/* 130 */         List l = new ArrayList();
/* 131 */         int sid = resultSet.getInt("Sid");
/* 132 */         int serialNumber = resultSet.getInt("Serial#");
/* 133 */         SqlPK pk = new SqlPK(sid, serialNumber);
/* 134 */         l.add(pk);
/* 135 */         l.add(String.valueOf(sid) + "," + String.valueOf(serialNumber));
/* 136 */         l.add(resultSet.getObject("User"));
/* 137 */         l.add(resultSet.getObject("Program"));
/* 138 */         String details = resultSet.getString("Details");
/* 139 */         if (details != null)
/*     */         {
/* 141 */           details = details.replaceAll("\r", "");
/* 142 */           details = details.replaceAll("\n", " ");
/* 143 */           details = details.replaceAll("   ", " ");
/* 144 */           details = details.replaceAll("  ", " ");
/* 145 */           details = details.replaceAll(Character.toString('\000'), " ");
/* 146 */           if (details.indexOf("SqlMonitorDAO") > -1)
/* 147 */             details = "-- cp sql monitor --";
/*     */         }
/* 149 */         l.add(details);
/* 150 */         results.add(l);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 156 */       throw new RuntimeException(getEntityName() + " getAllOracleSessions", e);
/*     */     }
/*     */     finally
/*     */     {
/* 160 */       sqle.close();
/*     */     }
/* 162 */     return results;
/*     */   }
/*     */ 
/*     */   public SqlDetails getSqlDetails(Object key)
/*     */   {
/* 167 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "with longOps as", "(", "select  lopsMax.SID,lopsMax.SERIAL#", "        ,'Time: used='||lop.ELAPSED_SECONDS", "         ||' rem='||lop.TIME_REMAINING||chr(10)", "         ||' '||lop.MESSAGE", "         as LONGOPS_MESSAGE", "from    (", "        select  SID,SERIAL#,max(LAST_UPDATE_TIME) LAST_UPDATE_TIME", "        from    V$SESSION_LONGOPS lop", "        group   by SID,SERIAL#", "        ) lopsMax", "join    V$SESSION_LONGOPS lop", "        on (lop.SID = lopsMax.SID", "            and lop.SERIAL# = lopsMax.SERIAL#", "            and lop.LAST_UPDATE_TIME = lopsMax.LAST_UPDATE_TIME)", "where   lop.SOFAR < lop.TOTALWORK", ")", ",blocked_sessions as", "(", "select   s2.SID, s2.SERIAL#, s2.USERNAME, s2.MACHINE, s2.MODULE, l2.TYPE", "        ,s1.SID         as BLOCKING_SID", "        ,s1.SERIAL#     as BLOCKING_SERIAL#", "        ,s1.USERNAME    as BLOCKING_USERNAME", "        ,s1.MACHINE     as BLOCKING_MACHINE", "        ,s1.MODULE      as BLOCKING_MODULE", "        ,l1.TYPE        as BLOCKING_TYPE", "from    V$LOCK l1", "        ,V$SESSION s1", "        ,V$LOCK l2", "        ,V$SESSION s2", "where   s1.SID = l1.SID", "and     s2.SID=l2.SID", "and     l1.BLOCK=1 and l2.REQUEST > 0", "and     l1.ID1 = l2.ID1", "and     l2.ID2 = l2.ID2", ")", "select   'sid,serial#='||substr(sess.SID||','||sess.SERIAL#,1,12)", "         ||' user='||trim(sess.USERNAME)", "         ||' status='||sess.STATUS", "         ||' module='||trim(sess.MODULE)", "         ||chr(10)", "         ||", "         case when sess.ACTION is not null then", "             'action='", "             ||sess.ACTION", "             ||chr(10)", "         end", "         ||", "         case when sess.CLIENT_INFO is not null then", "            'info='||sess.CLIENT_INFO", "            ||chr(10)", "         end", "         ||", "         case when blcked.BLOCKING_SID is not null then", "               'BLOCKED BY '''||blcked.BLOCKING_SID||','||blcked.BLOCKING_SERIAL#", "             ||''' ('||blcked.BLOCKING_USERNAME", "             ||'@'||blcked.BLOCKING_MACHINE", "             ||' - '||blcked.BLOCKING_MODULE||')'", "             ||chr(10)", "         end", "         ||", "         case when blcking.BLOCKING_SID is not null  then", "             ' lock '||blcking.BLOCKING_TYPE", "             ||' IS BLOCKING '''||blcking.SID||','||blcking.SERIAL#", "             ||''' ('||blcking.USERNAME", "             ||'@'||blcking.MACHINE", "             ||' - '||blcking.MODULE||')'", "             || chr(10)", "         end", "         ||'event='||sess.EVENT", "         || chr(10)", "         ||", "         case when OPEN_CURSORS is not null then", "             'open cursors='||OPEN_CURSORS", "             ||chr(10)", "         end", "         ||", "         case when coalesce(t.USED_UREC,t.USED_UBLK) is not null then", "             'undo: recs='||t.USED_UREC", "             ||', blks='||t.USED_UBLK", "             ||chr(10)", "         end", "         ||", "         case when coalesce(t.LOG_IO,t.PHY_IO) is not null then", "             'io: log='||t.LOG_IO", "             ||', phy='||t.PHY_IO", "             ||chr(10)", "         end", "         ||", "         case when LONGOPS_MESSAGE is not null then", "            LONGOPS_MESSAGE", "            ||chr(10)", "         end", "         as DETAILS", "        ,cursor", "         (", "         select  SQL_TEXT", "         from    V$SQLTEXT_WITH_NEWLINES sql", "         where   sql.ADDRESS = sess.SQL_ADDRESS", "         and     sql.HASH_VALUE = sess.SQL_HASH_VALUE", "         order   by sql.PIECE", "         ) as SQL", "from    V$SESSION sess", "left    join longOps", "        on (longOps.SID = sess.SID and longOps.SERIAL# = sess.SERIAL#)", "left    join  blocked_sessions blcked", "        on (blcked.SID = sess.SID and blcked.SERIAL# = sess.SERIAL#)", "left    join blocked_sessions blcking", "        on (blcking.BLOCKING_SID = sess.SID and blcking.BLOCKING_SERIAL# = sess.SERIAL#)", "left    join V$TRANSACTION t", "        on (t.ADDR = sess.TADDR)", "left    join", "        (", "        select  VALUE as OPEN_CURSORS, SID", "        from    V$SESSTAT", "        join    V$STATNAME using (STATISTIC#)", "        where   VALUE > 0", "        and     NAME = 'opened cursors current'", "        ) csrs on (csrs.SID = sess.SID)", "where   sess.SID = <sid> and sess.SERIAL# = <serial>" });
/*     */ 
/* 290 */     SqlExecutor sqle = new SqlExecutor("", getDataSource(), sqlb, this._log);
/* 291 */     sqle.setLogSql(false);
/* 292 */     SqlPK pk = (SqlPK)key;
/* 293 */     sqle.addBindVariable("<sid>", Integer.valueOf(pk.getSid()));
/* 294 */     sqle.addBindVariable("<serial>", Integer.valueOf(pk.getSerialNumber()));
/* 295 */     ResultSet rs = sqle.getResultSet();
/*     */     try
/*     */     {
/* 298 */       if (!rs.next()) {
/*     */         return null;
/*     */       }
/* 300 */       SqlDetailsImpl ret = new SqlDetailsImpl();
/* 301 */       ret.setDetails(rs.getString("DETAILS"));
/* 302 */       StringBuffer sqlsb = new StringBuffer();
/* 303 */       ResultSet sqlRs = (ResultSet)rs.getObject("SQL");
/* 304 */       while (sqlRs.next())
/*     */       {
/* 306 */         sqlsb.append(sqlRs.getString(1));
/*     */       }
/* 308 */       ret.setSql(sqlsb.toString());
/*     */       return ret;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       return null; 
				} 
				finally { sqle.close(); }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.sqlmon.SqlMonitorDAO
 * JD-Core Version:    0.6.0
 */