// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.formula.tablemeta;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaColumn;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaKey;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaTable;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MetaTableManager extends AbstractDAO {

   private Map<String, MetaTable> mTableCache = new HashMap();


   public MetaTableManager(Connection connection) {
      super(connection);
   }

   public MetaTableManager() {}

   public MetaTable getTable(String tableName) throws ValidationException {
      if(tableName != null && tableName.trim().length() != 0) {
         MetaTable metaTable = (MetaTable)this.mTableCache.get(tableName.toUpperCase());
         if(metaTable == null) {
            metaTable = this.loadMetaTableData(tableName);
            if(metaTable != null) {
               this.mTableCache.put(tableName.toUpperCase(), metaTable);
            }
         }

         return metaTable;
      } else {
         throw new ValidationException("No table name supplied in MetaTableManager.getTable()");
      }
   }

   public String getEntityName() {
      return "MetaTableManager";
   }

   public void clearCache() {
      this.mTableCache.clear();
   }

   private MetaTable loadMetaTableData(String srcTableName) throws ValidationException {
      String sqlTableName = srcTableName.toUpperCase();
      ResultSet rs = null;

      MetaTable table;
      try {
         DatabaseMetaData e = this.getConnection().getMetaData();
         rs = e.getTables((String)null, (String)null, sqlTableName, new String[]{"TABLE", "VIEW"});
         if(rs.next()) {
            table = new MetaTable();
            table.setName(rs.getString("TABLE_NAME"));
            rs.close();
            rs = e.getColumns((String)null, (String)null, sqlTableName, (String)null);

            while(rs.next()) {
               MetaColumn keys = new MetaColumn();
               keys.setName(rs.getString("COLUMN_NAME"));
               keys.setSQLType(rs.getInt("DATA_TYPE"));
               keys.setLength(rs.getInt("COLUMN_SIZE"));
               table.getColumns().add(keys);
            }

            rs.close();
            rs = e.getIndexInfo((String)null, (String)null, sqlTableName, false, true);
            HashMap keys1 = new HashMap();

            while(rs.next()) {
               int indexType = rs.getInt("TYPE");
               if(indexType != 0) {
                  String indexName = rs.getString("INDEX_NAME");
                  MetaKey key = (MetaKey)keys1.get(indexName);
                  if(key == null) {
                     key = new MetaKey();
                     key.setName(indexName);
                     table.getKeys().add(key);
                     keys1.put(indexName, key);
                     boolean columnName = rs.getBoolean("NON_UNIQUE");
                     key.setUnique(!columnName);
                  }

                  String columnName1 = rs.getString("COLUMN_NAME");
                  MetaColumn column = table.getColumn(columnName1);
                  if(column == null) {
                     throw new ValidationException("Unable to locate key column:" + columnName1 + " in table " + table.getName());
                  }

                  key.getSegments().add(column);
               }
            }

            MetaTable indexType1 = table;
            return indexType1;
         }

         table = null;
      } catch (SQLException var15) {
         throw this.handleSQLException("Failed to query table meta data", var15);
      } finally {
         this.closeResultSet(rs);
         this.closeConnection();
      }

      return table;
   }
}
