// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.base;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.SqlExecutorException;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.base.SqlResultSetImpl;
import com.cedar.cp.ejb.impl.base.SqlExecutor$1;
import com.cedar.cp.ejb.impl.base.SqlExecutor$BindVariable;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.SqlBuilder;
import com.cedar.cp.util.SqlResultSet;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JdbcUtils;
import com.cedar.cp.util.common.JdbcUtils.ColType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

public class SqlExecutor {

   private String mTraceName;
   private Connection mConnection;
   private boolean mIsConnectionPassedIn;
   private SqlBuilder mSql;
   private StringBuilder mTraceSql = null;
   private boolean mLogSql = true;
   private Log mLog;
   private Timer mTimer;
   private List<SqlExecutor$BindVariable> mPendingBindVariables;
   private List<SqlExecutor$BindVariable> mBindVariables;
   private PreparedStatement mStatement;
   private Integer mFetchSize;
   private Integer mMaxRows;
   private ResultSet mResultSet;
   private boolean mStatementLogged;


   public SqlExecutor(String traceName, Connection conn, SqlBuilder sql, Log log) {
      this.mTraceName = traceName;
      this.mConnection = conn;
      this.mIsConnectionPassedIn = true;
      this.mSql = sql;
      this.mPendingBindVariables = new ArrayList();
      this.mBindVariables = new ArrayList();
      this.mLog = log;
      if(this.mLog != null) {
         this.mTimer = new Timer(this.mLog);
      }

   }

   public SqlExecutor(String traceName, DataSource dataSource, SqlBuilder sql, Log log) {
      this.mTraceName = traceName;

      try {
         this.mConnection = dataSource.getConnection();
      } catch (SQLException var6) {
         throw new SqlExecutorException(var6);
      }

      this.mIsConnectionPassedIn = false;
      this.mSql = sql;
      this.mPendingBindVariables = new ArrayList();
      this.mBindVariables = new ArrayList();
      this.mLog = log;
      if(this.mLog != null) {
         this.mTimer = new Timer(this.mLog);
      }

   }

   public void addBindVariable(String name, String value) {
      this.addBindVariable(name, value, 12);
   }

   public void addBindVariable(String name, Integer value) {
      this.addBindVariable(name, value, 4);
   }

   public void addBindVariable(String name, Long value) {
      this.addBindVariable(name, value, -5);
   }

   public void addBindVariable(String name, Double value) {
      this.addBindVariable(name, value, 8);
   }

   public void addBindVariable(String name, Short value) {
      this.addBindVariable(name, value, 5);
   }

   public void addBindVariable(String name, BigDecimal value) {
      this.addBindVariable(name, value, 3);
   }

   public void addBindVariable(String name, Date value) {
      this.addBindVariable(name, value, 91);
   }

   public void addBindVariable(String name, Timestamp value) {
      this.addBindVariable(name, value, 93);
   }

   public void addBindVariable(String name, Boolean value) {
      if(value == null) {
         this.addBindVariable(name, (Object)null, 12);
      } else {
         this.addBindVariable(name, value.booleanValue()?"Y":" ", 12);
      }

   }

   public void addBindVariable(String name, Object value) {
      this.addBindVariable(name, value, 1111);
   }

   private void addBindVariable(String name, Object value, int type) {
      Iterator i$ = this.mPendingBindVariables.iterator();

      SqlExecutor$BindVariable bv;
      do {
         if(!i$.hasNext()) {
            this.mPendingBindVariables.add(new SqlExecutor$BindVariable(this, name, value, type));
            return;
         }

         bv = (SqlExecutor$BindVariable)i$.next();
      } while(!bv.getName().equals(name));

   }

   public void setFetchSize(Integer fetchSize) {
      this.mFetchSize = fetchSize;
   }

   public void setMaxRows(Integer maxRows) {
      this.mMaxRows = maxRows;
   }

   public void setLogSql(boolean b) {
      this.mLogSql = b;
   }

   public ResultSet getResultSet() {
      try {
         this.executeQuery();
         return this.mResultSet;
      } catch (SQLException var2) {
         if(!this.mStatementLogged) {
            this.mLog.error("getResultSet", this.mTraceSql.toString(), var2);
         }

         throw new SqlExecutorException(var2);
      }
   }

   public boolean next() {
      try {
         return this.mResultSet.next();
      } catch (SQLException var2) {
         throw new SqlExecutorException(var2);
      }
   }

   public int getInt(String columnName) {
      try {
         return this.mResultSet.getInt(columnName);
      } catch (SQLException var3) {
         throw new SqlExecutorException(var3);
      }
   }

   public long getLong(String columnName) {
      try {
         return this.mResultSet.getLong(columnName);
      } catch (SQLException var3) {
         throw new SqlExecutorException(var3);
      }
   }

   public String getString(String columnName) {
      try {
         return this.mResultSet.getString(columnName);
      } catch (SQLException var3) {
         throw new SqlExecutorException(var3);
      }
   }

   public BigDecimal getBigDecimal(String columnName) {
      try {
         return this.mResultSet.getBigDecimal(columnName);
      } catch (SQLException var3) {
         throw new SqlExecutorException(var3);
      }
   }

   public Timestamp getTimestamp(String columnName) {
      try {
         return this.mResultSet.getTimestamp(columnName);
      } catch (SQLException var3) {
         throw new SqlExecutorException(var3);
      }
   }

   public Blob getBlob(String columnName) {
      try {
         return this.mResultSet.getBlob(columnName);
      } catch (SQLException var3) {
         throw new SqlExecutorException(var3);
      }
   }

   public Clob getClob(String columnName) {
      try {
         return this.mResultSet.getClob(columnName);
      } catch (SQLException var3) {
         throw new SqlExecutorException(var3);
      }
   }

   public boolean wasNull() {
      try {
         return this.mResultSet.wasNull();
      } catch (SQLException var2) {
         throw new SqlExecutorException(var2);
      }
   }

   public List<Object[]> getList() {
      try {
         this.executeQuery();
         ArrayList e = new ArrayList();
         int colCount = this.mResultSet.getMetaData().getColumnCount();

         while(this.mResultSet.next()) {
            Object[] row = new Object[colCount];

            for(int i = 0; i < colCount; ++i) {
               row[i] = this.mResultSet.getObject(i + 1);
            }

            e.add(row);
         }

         if(this.mTimer != null && this.mLog.isDebugEnabled()) {
            this.mTimer.logDebug(this.mTraceName, "rows/cols=" + e.size() + "/" + colCount);
         }

         ArrayList var10 = e;
         return var10;
      } catch (SQLException var8) {
         if(!this.mStatementLogged) {
            this.mLog.error("getList", this.mTraceSql.toString(), var8);
         }

         throw new SqlExecutorException(var8);
      } finally {
         this.close();
      }
   }

   public EntityList getEntityList(ColType[] colSpec) {
      EntityListImpl var3;
      try {
         this.executeQuery();
         EntityListImpl e = JdbcUtils.extractToEntityListImpl(colSpec, this.mResultSet);
         if(this.mTimer != null && this.mLog.isDebugEnabled()) {
            this.mTimer.logDebug(this.mTraceName, "rows/cols=" + e.getNumRows() + "/" + e.getNumColumns());
         }

         var3 = e;
      } catch (SQLException var7) {
         if(!this.mStatementLogged) {
            this.mLog.error("getEntityList", this.mTraceSql.toString(), var7);
         }

         throw new SqlExecutorException(var7);
      } finally {
         this.close();
      }

      return var3;
   }

   public EntityList getEntityList() {
      EntityList var2;
      try {
         this.executeQuery();
         EntityList e = this.getEntityList(this.mResultSet);
         if(this.mTimer != null && this.mLog.isDebugEnabled()) {
            this.mTimer.logDebug(this.mTraceName, "rows/cols=" + e.getNumRows() + "/" + e.getNumColumns());
         }

         var2 = e;
      } catch (SQLException var6) {
         if(!this.mStatementLogged) {
            this.mLog.error("getEntityList", this.mTraceSql.toString(), var6);
         }

         throw new SqlExecutorException(var6);
      } finally {
         this.close();
      }

      return var2;
   }

   private EntityList getEntityList(ResultSet rs) throws SQLException {
      ResultSetMetaData meta = rs.getMetaData();
      String[] columnNames = new String[meta.getColumnCount()];

      for(int results = 0; results < meta.getColumnCount(); ++results) {
         columnNames[results] = meta.getColumnName(results + 1);
      }

      EntityListImpl var35 = new EntityListImpl(columnNames, new Object[0][columnNames.length]);

      while(rs.next()) {
         ArrayList l = new ArrayList();

         for(int i = 0; i < columnNames.length; ++i) {
            if(meta.getColumnType(i + 1) == 93) {
               l.add(rs.getTimestamp(i + 1));
            } else if(meta.getColumnType(i + 1) == 2004) {
               Blob o = rs.getBlob(i + 1);
               InputStream br = null;

               try {
                  br = o.getBinaryStream();
                  byte[] e = new byte[(int)o.length()];
                  br.read(e);
                  l.add(e);
               } catch (IOException var33) {
                  throw new SQLException(var33.getMessage());
               } finally {
                  try {
                     br.close();
                  } catch (IOException var30) {
                     ;
                  }

               }
            } else if(meta.getColumnType(i + 1) == 2005) {
               Clob var36 = rs.getClob(i + 1);
               BufferedReader var39 = null;

               try {
                  var39 = new BufferedReader(var36.getCharacterStream());
                  char[] var38 = new char[(int)var36.length()];
                  var39.read(var38);
                  l.add(new String(var38));
               } catch (IOException var31) {
                  throw new SQLException(var31.getMessage());
               } finally {
                  try {
                     var39.close();
                  } catch (IOException var29) {
                     ;
                  }

               }
            } else {
               Object var37 = rs.getObject(i + 1);
               if(var37 instanceof ResultSet) {
                  l.add(this.getEntityList((ResultSet)var37));
               } else {
                  l.add(var37);
               }
            }
         }

         var35.add(l);
      }

      return var35;
   }

   public SqlResultSet getSqlResultSet() {
      SqlResultSet var2;
      try {
         this.executeQuery();
         SqlResultSet e = this.getSqlResultSet(this.mResultSet);
         if(this.mTimer != null && this.mLog.isDebugEnabled()) {
            this.mTimer.logDebug(this.mTraceName, "rows/cols=" + e.getNumRows() + "/" + e.getNumColumns());
         }

         var2 = e;
      } catch (SQLException var6) {
         if(!this.mStatementLogged) {
            this.mLog.error("getSqlResultSet", this.mTraceSql.toString(), var6);
         }

         throw new SqlExecutorException(var6);
      } finally {
         this.close();
      }

      return var2;
   }

   private SqlResultSet getSqlResultSet(ResultSet rs) throws SQLException {
      ResultSetMetaData meta = rs.getMetaData();
      SqlResultSetImpl results = new SqlResultSetImpl(meta.getColumnCount());

      for(int row = 0; row < meta.getColumnCount(); ++row) {
         results.setColumnName(row, meta.getColumnName(row + 1));
         results.setColumnClass(row, meta.getColumnClassName(row + 1));
      }

      while(rs.next()) {
         Object[] var34 = new Object[meta.getColumnCount()];

         for(int i = 0; i < meta.getColumnCount(); ++i) {
            if(meta.getColumnType(i + 1) != 93 && meta.getColumnType(i + 1) != 91 && meta.getColumnType(i + 1) != 92) {
               if(meta.getColumnType(i + 1) == 2004) {
                  Blob o = rs.getBlob(i + 1);
                  InputStream br = null;

                  try {
                     br = o.getBinaryStream();
                     byte[] e = new byte[(int)o.length()];
                     br.read(e);
                     var34[i] = e;
                  } catch (IOException var30) {
                     throw new SQLException(var30.getMessage());
                  } finally {
                     try {
                        br.close();
                     } catch (IOException var28) {
                        ;
                     }

                  }
               } else if(meta.getColumnType(i + 1) == 2005) {
                  Clob var36 = rs.getClob(i + 1);
                  BufferedReader var37 = null;

                  try {
                     var37 = new BufferedReader(var36.getCharacterStream());
                     char[] var38 = new char[(int)var36.length()];
                     var37.read(var38);
                     var34[i] = var38;
                  } catch (IOException var32) {
                     throw new SQLException(var32.getMessage());
                  } finally {
                     try {
                        var37.close();
                     } catch (IOException var29) {
                        ;
                     }

                  }
               } else {
                  Object var35 = rs.getObject(i + 1);
                  if(var35 instanceof ResultSet) {
                     var34[i] = this.getSqlResultSet((ResultSet)var35);
                  } else {
                     var34[i] = var35;
                  }
               }
            } else {
               var34[i] = rs.getTimestamp(i + 1);
            }
         }

         results.addRow(var34);
      }

      return results;
   }

   public int executeUpdate() {
      StringBuilder sql = this.prepareStatementBinds();

      int var3;
      try {
         this.mStatement = this.mConnection.prepareStatement(sql.toString());
         this.setBindVariables();
         int e = this.mStatement.executeUpdate();
         if(this.mTimer != null && this.mLog.isDebugEnabled()) {
            this.mTimer.logDebug(this.mTraceName, "rows=" + e);
         }

         var3 = e;
      } catch (SQLException var7) {
         if(!this.mStatementLogged) {
            this.mLog.error("executeUpdate", this.mTraceSql.toString(), var7);
         }

         throw new SqlExecutorException(var7);
      } finally {
         this.close();
      }

      return var3;
   }

   private void executeQuery() throws SQLException {
      StringBuilder sql = this.prepareStatementBinds();
      this.mStatement = this.mConnection.prepareStatement(sql.toString());
      this.setBindVariables();
      if(this.mFetchSize != null) {
         this.mStatement.setFetchSize(this.mFetchSize.intValue());
      }

      if(this.mMaxRows != null) {
         this.mStatement.setMaxRows(this.mMaxRows.intValue());
      }

      try {
         this.mResultSet = this.mStatement.executeQuery();
      } catch (RuntimeException var3) {
         if(!this.mStatementLogged) {
            this.mLog.error("executeUpdate", this.mTraceSql.toString());
         }

         var3.printStackTrace();
         throw var3;
      }
   }

   private StringBuilder prepareStatementBinds() {
      Iterator ptr = this.mPendingBindVariables.iterator();

      while(ptr.hasNext()) {
         SqlExecutor$BindVariable actualSql = (SqlExecutor$BindVariable)ptr.next();
         String i$ = actualSql.getName();
         Object bv = actualSql.getValue();
         int type = actualSql.getType();
         String sql = this.mSql.toStringBuilder().toString();

         for(int ptr1 = sql.indexOf(i$); ptr1 > -1; ptr1 = sql.indexOf(i$, ptr1 + i$.length())) {
            SqlExecutor$BindVariable bv1 = new SqlExecutor$BindVariable(this, i$, bv, type, ptr1);
            this.mBindVariables.add(bv1);
         }
      }

      Collections.sort(this.mBindVariables, new SqlExecutor$1(this));
      this.mStatementLogged = false;
      this.mTraceSql = new StringBuilder(this.mSql.toStringBuilder());
      int ptr2 = -1;
      Iterator actualSql1 = this.mBindVariables.iterator();

      while(actualSql1.hasNext()) {
         SqlExecutor$BindVariable i$1 = (SqlExecutor$BindVariable)actualSql1.next();
         String bv2 = i$1.getName();
         ptr2 = this.mTraceSql.indexOf(bv2, ptr2 + 1);
         if(ptr2 != -1) {
            this.mTraceSql.replace(ptr2, ptr2 + i$1.getName().length(), i$1.getDebugString());
            ptr2 += i$1.getDebugString().length();
         }
      }

      if(this.mLog != null && this.mLog.isDebugEnabled() && this.mLogSql) {
         this.mStatementLogged = true;
         this.mLog.debug(this.mTraceName, "\n" + this.mTraceSql.toString());
      }

      StringBuilder actualSql2 = this.mSql.toStringBuilder();
      Iterator i$2 = this.mBindVariables.iterator();

      while(i$2.hasNext()) {
         SqlExecutor$BindVariable bv3 = (SqlExecutor$BindVariable)i$2.next();
         ptr2 = actualSql2.indexOf(bv3.getName());
         if(ptr2 > -1) {
            actualSql2.replace(ptr2, ptr2 + bv3.getName().length(), "?");
         }
      }

      return actualSql2;
   }

   private void setBindVariables() throws SQLException {
      int col = 0;
      Iterator i$ = this.mBindVariables.iterator();

      while(i$.hasNext()) {
         SqlExecutor$BindVariable bv = (SqlExecutor$BindVariable)i$.next();
         if(bv.getPosition() > -1) {
            ++col;
            if(bv.getValue() == null) {
               this.mStatement.setNull(col, bv.getType());
            } else if(bv.getType() == 1111) {
               this.mStatement.setObject(col, bv.getValue());
            } else {
               this.mStatement.setObject(col, bv.getValue(), bv.getType());
            }
         }
      }

   }

   public void close() {
      try {
         if(this.mResultSet != null) {
            this.mResultSet.close();
            this.mResultSet = null;
         }

         if(this.mStatement != null) {
            this.mStatement.close();
            this.mStatement = null;
         }

         if(this.mConnection != null) {
            if(!this.mIsConnectionPassedIn) {
               this.mConnection.close();
            }

            this.mConnection = null;
         }

      } catch (Exception var2) {
         throw new SqlExecutorException(var2);
      }
   }

   public static String getCLobContent(CLOB clob) throws SQLException {
      BufferedReader br = null;

      String var3;
      try {
         br = new BufferedReader(clob.getCharacterStream());
         char[] e = new char[(int)clob.length()];
         br.read(e);
         var3 = new String(e);
      } catch (SQLException var13) {
         throw new SqlExecutorException(var13);
      } catch (IOException var14) {
         throw new RuntimeException(var14);
      } finally {
         try {
            br.close();
         } catch (IOException var12) {
            ;
         }

      }

      return var3;
   }

   public static byte[] getBLobContent(BLOB blob) {
      InputStream is = null;

      byte[] var3;
      try {
         is = blob.getBinaryStream();
         byte[] e = new byte[(int)blob.length()];
         is.read(e);
         var3 = e;
      } catch (SQLException var13) {
         throw new SqlExecutorException(var13);
      } catch (IOException var14) {
         throw new RuntimeException(var14);
      } finally {
         try {
            if(is != null) {
               is.close();
            }
         } catch (IOException var12) {
            ;
         }

      }

      return var3;
   }
}
