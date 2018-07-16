// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.lookup;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.udeflookup.LookupAdminTaskRequest;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.lookup.LookupTableAdminTask$1;
import com.cedar.cp.ejb.base.async.lookup.LookupTableAdminTask$2;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupColumnDefEVO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupDAO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupEVO;
import com.cedar.cp.util.SqlBuilder;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;

public class LookupTableAdminTask extends AbstractTask {

   final String[] sTimeLvlTxt = new String[]{"None", "Year", "Month", "Day"};
   final String[] sMonthText = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      LookupAdminTaskRequest req = (LookupAdminTaskRequest)this.getRequest();
      if(req.getAction() == LookupAdminTaskRequest.DROP_LOOKUP_TABLES) {
         this.log("Drop lookup table " + req.getGenTableName());
         this.dropTable(req.getGenTableName());
      } else {
         UdefLookupEVO udefEvo = (new UdefLookupDAO()).getDetails(new UdefLookupPK(req.getLookupId()), "<0>");
         if(req.getAction() == LookupAdminTaskRequest.CREATE_LOOKUP_TABLES) {
            this.log("Creating lookup table " + udefEvo.getVisId() + " - " + udefEvo.getDescription());
            this.createTable(udefEvo);
         } else {
            if(req.getAction() != LookupAdminTaskRequest.ALTER_LOOKUP_TABLES) {
               throw new CPException("Unexpected request action code:" + req.getAction());
            }

            this.log("Altering lookup table " + udefEvo.getVisId() + " - " + udefEvo.getDescription());
            this.alterTable(udefEvo);
         }

      }
   }

   public String getEntityName() {
      return "LookupAdminTask";
   }

   public TaskCheckpoint getCheckpoint() {
      return null;
   }

   public int getReportType() {
      return 0;
   }

   private void createTable(UdefLookupEVO udefEvo) {
      this.dropTable(udefEvo.getGenTableName());
      SqlBuilder sqlb = new SqlBuilder(new String[]{"create table ${genTableName}", "    (", "    ${columns}", "    ,CP_USER_SEQ number not null", "    ) tablespace CPMETA"});
      ArrayList colRows = new ArrayList();
      colRows.addAll(udefEvo.getColumnDef());
      Collections.sort(colRows, new LookupTableAdminTask$1(this));
      SqlBuilder sbCols = new SqlBuilder();
      Iterator stmt = colRows.iterator();

      while(stmt.hasNext()) {
         UdefLookupColumnDefEVO sqle = (UdefLookupColumnDefEVO)stmt.next();
         sbCols.addLine((sbCols.getLines().size() == 0?" ":",") + this.getColumnDDL(sqle.getName(), sqle.getType(), sqle.getColumnSize(), sqle.getColumnDp(), sqle.getOptional(), false));
      }

      sqlb.substitute(new String[]{"${genTableName}", udefEvo.getGenTableName()});
      sqlb.substituteLines("${columns}", sbCols);
      CallableStatement stmt1 = null;

      try {
         this.log("ddl:\n" + sqlb.toString());
         stmt1 = this.getConnection().prepareCall("begin cp_utils.createObject(?,?,?); end;");
         stmt1.setString(1, "TABLE");
         stmt1.setString(2, udefEvo.getGenTableName());
         stmt1.setString(3, sqlb.toString());
         stmt1.execute();
      } catch (SQLException var10) {
         var10.printStackTrace();
         throw new RuntimeException(this.getEntityName() + "createTable", var10);
      } finally {
         this.closeStatement(stmt1);
         this.closeConnection();
      }

   }

   private String getColumnDDL(String name, int type, Integer size, Integer dp, boolean optional, boolean defaultValueNeeded) {
      return "X_" + name.toUpperCase() + " " + (type == 1?"number(18," + dp + ")":(type == 2?"char(1)":(type == 3?"date":" varchar(" + size + ")"))) + (defaultValueNeeded?(type == 1?" default 0":(type == 2?" default \'N\'":(type == 3?" default sysdate":" default \' \'"))):"") + (optional?" null":" not null");
   }

   private void dropTable(String tableName) {
      CallableStatement stmt = null;

      try {
         this.log("drop table " + tableName);
         stmt = this.getConnection().prepareCall("begin cp_utils.dropObject(?,?); end;");
         stmt.setString(1, "TABLE");
         stmt.setString(2, tableName);
         stmt.execute();
      } catch (SQLException var7) {
         var7.printStackTrace();
         throw new RuntimeException(this.getEntityName() + "dropTable", var7);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void alterTable(UdefLookupEVO udefEvo) {
      ArrayList colRows = new ArrayList();
      colRows.addAll(udefEvo.getColumnDef());
      Collections.sort(colRows, new LookupTableAdminTask$2(this));
      String schemaName = SystemPropertyHelper.queryStringSystemProperty(this.getConnection(), "SYS: CPSCHEMA", "<noSchemaProperty!>");
      ArrayList currentCols = new ArrayList();

      String addColumns;
      try {
         ResultSet sqlb = this.getConnection().getMetaData().getColumns("%", schemaName, udefEvo.getGenTableName(), "%");

         while(sqlb.next()) {
            addColumns = sqlb.getString("COLUMN_NAME");
            if(!addColumns.equals("CP_USER_SEQ")) {
               currentCols.add(sqlb.getString("COLUMN_NAME"));
            }
         }
      } catch (SQLException var9) {
         var9.printStackTrace();
      }

      for(int var10 = 0; var10 < currentCols.size(); ++var10) {
         addColumns = (String)currentCols.get(var10);
         if(!this.isColumnDefined(addColumns, colRows, udefEvo)) {
            SqlBuilder i$ = new SqlBuilder(new String[]{"alter table ${genTableName} drop column ${columnName}"});
            i$.substitute(new String[]{"${genTableName}", udefEvo.getGenTableName()});
            i$.substitute(new String[]{"${columnName}", addColumns});
            this.executeDDL(i$);
         }
      }

      SqlBuilder var11 = new SqlBuilder(new String[]{"alter table ${genTableName}", "      add", "      (", "      ${addColumns}", "      )"});
      SqlBuilder var12 = new SqlBuilder();
      Iterator var13 = colRows.iterator();

      while(var13.hasNext()) {
         UdefLookupColumnDefEVO colrow = (UdefLookupColumnDefEVO)var13.next();
         if(!this.doesColumnExist(colrow.getName(), currentCols)) {
            var12.addLine((var12.getLines().size() == 0?" ":",") + this.getColumnDDL(colrow.getName(), colrow.getType(), colrow.getColumnSize(), colrow.getColumnDp(), colrow.getOptional(), !colrow.getOptional()));
         }
      }

      if(var12.getLines().size() > 0) {
         var11.substitute(new String[]{"${genTableName}", udefEvo.getGenTableName()});
         var11.substituteLines("${addColumns}", var12);
         this.executeDDL(var11);
      }

      this.adjustTimeAwareColumns(udefEvo);
   }

   private void adjustTimeAwareColumns(UdefLookupEVO udefEvo) {
      if(udefEvo.getTimeLvl() != 0) {
         int prevTimeLvl = ((LookupAdminTaskRequest)this.getRequest()).getPrevTimeLvl();
         int prevYearStartMonth = ((LookupAdminTaskRequest)this.getRequest()).getPrevYearMonthStart();
         boolean prevTimeRange = ((LookupAdminTaskRequest)this.getRequest()).getPrevTimeRange();
         boolean timeLvlChanged = udefEvo.getTimeLvl() != prevTimeLvl;
         boolean timeRangeChanged = udefEvo.getTimeRange() != prevTimeRange;
         int monthDiff = 0;
         if(!timeLvlChanged && udefEvo.getTimeLvl() == 1) {
            monthDiff = udefEvo.getYearStartMonth() - prevYearStartMonth;
         }

         String endDateSource = udefEvo.getTimeRange() && !prevTimeRange?"X_TA_DATE":"X_TA_END_DATE";
         if(timeLvlChanged || timeRangeChanged || monthDiff != 0) {
            if(monthDiff != 0) {
               this.log("year start month changed from " + this.sMonthText[prevYearStartMonth] + " to " + this.sMonthText[udefEvo.getYearStartMonth()]);
            }

            if(timeLvlChanged) {
               this.log("time level changed from " + this.sTimeLvlTxt[prevTimeLvl] + " to " + this.sTimeLvlTxt[udefEvo.getTimeLvl()]);
            }

            if(timeRangeChanged && udefEvo.getTimeRange()) {
               this.log("time range column added");
            }

            if(monthDiff != 0) {
               this.adjustDateColumn(udefEvo, "apply month diff to start date", new SqlBuilder(new String[]{"add_months(X_TA_DATE,${monthDiff})"}), new String[]{"${setColumn}", "X_TA_DATE", "${monthDiff}", String.valueOf(monthDiff)});
               if(udefEvo.getTimeRange()) {
                  this.adjustDateColumn(udefEvo, "apply month diff to end date", new SqlBuilder(new String[]{"add_months(${endDateSource},${monthDiff})"}), new String[]{"${setColumn}", "X_TA_END_DATE", "${monthDiff}", String.valueOf(monthDiff), "${endDateSource}", endDateSource});
               }
            }

            if(timeLvlChanged) {
               switch(udefEvo.getTimeLvl()) {
               case 0:
               case 3:
               default:
                  break;
               case 1:
                  this.adjustDateColumn(udefEvo, "set start month/day to " + (udefEvo.getYearStartMonth() + 1) + "-1", new SqlBuilder(new String[]{"to_date(extract(YEAR from X_TA_DATE)||\'-${yearStartMonth}-01\',\'YYYY-MM-DD\')"}), new String[]{"${setColumn}", "X_TA_DATE", "${yearStartMonth}", String.valueOf(udefEvo.getYearStartMonth() + 1)});
                  if(udefEvo.getTimeRange()) {
                     this.adjustDateColumn(udefEvo, "set end month/day to last day of year", new SqlBuilder(new String[]{"add_months(", "           to_date(", "                   extract(YEAR from ${endDateSource})||\'-${yearStartMonth}-01\'", "                   ,\'YYYY-MM-DD\')", "           ,12", "          ) - 1"}), new String[]{"${setColumn}", "X_TA_END_DATE", "${endDateSource}", endDateSource, "${yearStartMonth}", String.valueOf(udefEvo.getYearStartMonth() + 1)});
                  }
                  break;
               case 2:
                  this.adjustDateColumn(udefEvo, "set start day to 01", new SqlBuilder(new String[]{"to_date(extract(YEAR from X_TA_DATE)||\'-\'||", "        extract(MONTH from X_TA_DATE)||\'-01\',\'YYYY-MM-DD\')"}), new String[]{"${setColumn}", "X_TA_DATE"});
                  if(udefEvo.getTimeRange()) {
                     this.adjustDateColumn(udefEvo, "set end day to last day of month", new SqlBuilder(new String[]{"add_months(", "           to_date(extract(YEAR from ${endDateSource})||\'-\'||", "                   extract(MONTH from ${endDateSource})||\'-01\',\'YYYY-MM-DD\'", "                  )", "           ,1", "          )", " - 1"}), new String[]{"${setColumn}", "X_TA_END_DATE", "${endDateSource}", endDateSource});
                  }
               }
            } else if(timeRangeChanged && udefEvo.getTimeRange()) {
               switch(udefEvo.getTimeLvl()) {
               case 1:
                  this.adjustDateColumn(udefEvo, "set end month/day to last day of year", new SqlBuilder(new String[]{"add_months(", "           to_date(", "                   extract(YEAR from ${endDateSource})||\'-${yearStartMonth}-01\'", "                   ,\'YYYY-MM-DD\')", "           ,12", "          ) - 1"}), new String[]{"${setColumn}", "X_TA_END_DATE", "${endDateSource}", "X_TA_DATE", "${yearStartMonth}", String.valueOf(udefEvo.getYearStartMonth() + 1)});
                  break;
               case 2:
                  this.adjustDateColumn(udefEvo, "set end day to last day of month of start date", new SqlBuilder(new String[]{"add_months(", "           to_date(extract(YEAR from ${endDateSource})||\'-\'||", "                   extract(MONTH from ${endDateSource})||\'-01\',\'YYYY-MM-DD\'", "                  )", "           ,1", "          )", " - 1"}), new String[]{"${setColumn}", "X_TA_END_DATE", "${endDateSource}", "X_TA_DATE"});
               case 3:
               }
            }

         }
      }
   }

   private void adjustDateColumn(UdefLookupEVO udefEvo, String action, SqlBuilder dateAdjustment, String ... substitutions) {
      SqlBuilder sqlb = new SqlBuilder(new String[]{"update ${genTableName}", "set   ${setColumn} = ", "      ${dateAdjustment}"});
      sqlb.substitute(new String[]{"${genTableName}", udefEvo.getGenTableName()});
      sqlb.substituteLines("${dateAdjustment}", dateAdjustment);
      sqlb.substitute(substitutions);
      this.log(" ");
      this.log(action + "\n" + sqlb.toString());
      SqlExecutor sqle = new SqlExecutor(action, this.getDataSource(), sqlb, this._log);
      sqle.executeUpdate();
   }

   private boolean isColumnDefined(String colName, List<UdefLookupColumnDefEVO> colRows, UdefLookupEVO udefEvo) {
      Iterator i$ = colRows.iterator();

      UdefLookupColumnDefEVO colRow;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         colRow = (UdefLookupColumnDefEVO)i$.next();
      } while(!colName.equals("X_" + colRow.getName().toUpperCase()));

      return true;
   }

   private boolean doesColumnExist(String colName, List currentCols) {
      for(int i = 0; i < currentCols.size(); ++i) {
         if(("X_" + colName).toUpperCase().equals(currentCols.get(i))) {
            return true;
         }
      }

      return false;
   }

   private void executeDDL(SqlBuilder ddl) {
      CallableStatement stmt = null;

      try {
         this.log("ddl:\n" + ddl.toString());
         stmt = this.getConnection().prepareCall("begin cp_utils.executeDDL(?); end;");
         stmt.setString(1, ddl.toString());
         stmt.execute();
      } catch (SQLException var7) {
         var7.printStackTrace();
         throw new RuntimeException(this.getEntityName(), var7);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }
}
