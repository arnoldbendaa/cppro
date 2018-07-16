// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.SqlResultSet;
import com.cedar.cp.util.xmlform.inputs.LookupData$1;
import com.cedar.cp.util.xmlform.inputs.LookupData$LookupRow;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class LookupData implements Serializable {

   private Log mLog = new Log(this.getClass());
   private String mKeyColumnName;
   private String[] mColumnName;
   private Map<Object, Map<Object, List<LookupData$LookupRow>>> mPartitions;
   private Map<Object, List<LookupData$LookupRow>> mLookupKeyData;
   private int mUserSeqColIndex;


   public LookupData(String keyColumn, String partitionColumn, SqlResultSet sourceData) {
      int partitionColumnIndex = partitionColumn != null?sourceData.getColumnNameIndex("X_" + partitionColumn):-1;
      int keyColumnIndex = sourceData.getColumnNameIndex("X_" + keyColumn.toUpperCase());
      int dateColumnIndex = sourceData.getColumnNameIndex("X_TA_DATE");
      int endDateColumnIndex = sourceData.getColumnNameIndex("X_TA_END_DATE");
      int numLookupColumns = sourceData.getNumColumns();
      numLookupColumns -= partitionColumnIndex > -1?3:2;
      this.mKeyColumnName = keyColumn;
      this.mColumnName = new String[numLookupColumns];
      int colIndex = -1;

      int i;
      for(i = 0; i < sourceData.getNumColumns(); ++i) {
         if(i != partitionColumnIndex && i != keyColumnIndex && i != dateColumnIndex && i != endDateColumnIndex) {
            ++colIndex;
            if(sourceData.getColumnName(i).equals("CP_USER_SEQ")) {
               this.mColumnName[colIndex] = sourceData.getColumnName(i);
               this.mUserSeqColIndex = colIndex;
            } else {
               this.mColumnName[colIndex] = sourceData.getColumnName(i).substring(2);
            }
         }
      }

      if(partitionColumnIndex == -1) {
         this.mPartitions = null;
         this.mLookupKeyData = new HashMap();
      } else {
         this.mPartitions = new HashMap();
         this.mLookupKeyData = null;
      }

      boolean var20 = true;

      for(i = 0; i < sourceData.getNumRows(); ++i) {
         Object partitionColumnValue = null;
         Object keyColumnValue = null;
         Date startDateColumnValue = null;
         Date endDateColumnValue = null;
         Object[] lookupColumnValues = new Object[numLookupColumns];
         colIndex = -1;

         for(int valueRows = 0; valueRows < sourceData.getNumColumns(); ++valueRows) {
            if(valueRows == partitionColumnIndex) {
               partitionColumnValue = sourceData.getValueAt(i, valueRows);
            } else if(valueRows == keyColumnIndex) {
               keyColumnValue = sourceData.getValueAt(i, valueRows);
               if(keyColumnValue instanceof BigDecimal) {
                  keyColumnValue = new Double(((BigDecimal)keyColumnValue).doubleValue());
               }
            } else if(valueRows == dateColumnIndex) {
               if(sourceData.getValueAt(i, valueRows) instanceof Date) {
                  startDateColumnValue = (Date)sourceData.getValueAt(i, valueRows);
               } else {
                  if(!(sourceData.getValueAt(i, valueRows) instanceof Timestamp)) {
                     throw new IllegalStateException("column at dateColumnIndex " + valueRows + " is class " + sourceData.getValueAt(i, valueRows).getClass().getName());
                  }

                  startDateColumnValue = new Date(((Timestamp)sourceData.getValueAt(i, valueRows)).getTime());
               }
            } else if(valueRows == endDateColumnIndex) {
               endDateColumnValue = (Date)sourceData.getValueAt(i, valueRows);
            } else {
               ++colIndex;
               lookupColumnValues[colIndex] = sourceData.getValueAt(i, valueRows);
            }
         }

         if(partitionColumnIndex != -1) {
            this.mLookupKeyData = (Map)this.mPartitions.get(partitionColumnValue);
            if(this.mLookupKeyData == null) {
               this.mLookupKeyData = new HashMap();
            }

            this.mPartitions.put(partitionColumnValue, this.mLookupKeyData);
         }

         List var21 = (List)this.mLookupKeyData.get(keyColumnValue);
         if(var21 == null) {
            var21 = new ArrayList();
            this.mLookupKeyData.put(keyColumnValue, var21);
         }

         boolean isDuplicateRow = false;
         Iterator i$ = ((List)var21).iterator();

         while(i$.hasNext()) {
            LookupData$LookupRow row = (LookupData$LookupRow)i$.next();
            if(row.mStartDate.equals(startDateColumnValue) && row.mEndDate.equals(endDateColumnValue)) {
               isDuplicateRow = true;
               break;
            }
         }

         if(!isDuplicateRow) {
            ((List)var21).add(new LookupData$LookupRow(this, startDateColumnValue, endDateColumnValue, lookupColumnValues));
         } else {
            this.mLog.debug(keyColumn + " ignored duplicate " + keyColumnValue);
         }
      }

   }

   public int getColumnNameIndex(String columnName) throws Exception {
      int colIndex = -1;
      if(columnName.equalsIgnoreCase(this.mKeyColumnName)) {
         return -1;
      } else {
         for(int i = 0; i < this.mColumnName.length; ++i) {
            if(this.mColumnName[i] != null && this.mColumnName[i].equalsIgnoreCase(columnName)) {
               colIndex = i;
               break;
            }
         }

         if(colIndex == -1) {
            throw new Exception("column name " + columnName + " not found");
         } else {
            return colIndex;
         }
      }
   }

   public Object getValue(int valueColumnIdx, Object partition, Object key, Date paramDate, String lookupId) throws Exception {
      if(partition == null) {
         if(this.mPartitions != null) {
            throw new Exception("lookupId" + lookupId + ": value requested with no partition specified, but data is partitioned");
         }
      } else {
         if(this.mPartitions == null) {
            throw new Exception("lookupId" + lookupId + ": value requested for partition " + partition + " but data is not partitioned");
         }

         this.mLookupKeyData = (Map)this.mPartitions.get(partition);
         if(this.mLookupKeyData == null) {
            this.logNotFoundMessage(valueColumnIdx, partition, key, paramDate, lookupId);
            return null;
         }
      }

      List valueRows = (List)this.mLookupKeyData.get(key);
      if(valueRows == null) {
         this.logNotFoundMessage(valueColumnIdx, partition, key, paramDate, lookupId);
         return null;
      } else if(valueColumnIdx == -1) {
         return key;
      } else if(paramDate == null) {
         if(valueRows.size() == 1) {
            return ((LookupData$LookupRow)valueRows.get(0)).getValue(valueColumnIdx);
         } else {
            throw new Exception("lookupId" + lookupId + ": key " + key + " has more than 1 row");
         }
      } else {
         int bestDistanceIdx = -1;
         long bestStartDistance = 0L;
         long bestEndDistance = 0L;

         for(int row = 0; row < valueRows.size(); ++row) {
            LookupData$LookupRow z = (LookupData$LookupRow)valueRows.get(row);
            long startDistance = z.getStartDateDistance(paramDate);
            long endDistance = z.getEndDateDistance(paramDate);
            if(startDistance >= 0L && endDistance >= 0L && (bestDistanceIdx == -1 || startDistance < bestStartDistance || startDistance == bestStartDistance && endDistance < bestEndDistance)) {
               bestDistanceIdx = row;
               bestStartDistance = startDistance;
               bestEndDistance = endDistance;
            }
         }

         if(bestDistanceIdx == -1) {
            this.logNotFoundMessage(valueColumnIdx, partition, key, paramDate, lookupId);
            return null;
         } else {
            LookupData$LookupRow var18 = (LookupData$LookupRow)valueRows.get(bestDistanceIdx);
            Object var19 = var18.getValue(valueColumnIdx);
            if(var19 == null) {
               this.logNotFoundMessage(valueColumnIdx, partition, key, paramDate, lookupId);
            }

            return var19;
         }
      }
   }

   private void logNotFoundMessage(int valueColumnIdx, Object partition, Object key, Date paramDate, String lookupId) {
      SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
      StringBuilder sb = new StringBuilder();
      sb.append("not found: lookup=").append(lookupId).append(" ").append(this.mKeyColumnName).append("=").append(key).append(" v=").append(this.mColumnName[valueColumnIdx]).append(paramDate == null?"":" d=").append(paramDate == null?"":df.format(paramDate)).append(partition == null?"":" p=").append(partition == null?"":partition);
      this.mLog.info("getValue", sb.toString());
   }

   public Map getMapping(Object partition, int valueColumnIdx, String lookupId) throws Exception {
      TreeMap returnMap = new TreeMap();
      this.mLookupKeyData = (Map)this.mPartitions.get(partition);
      if(this.mLookupKeyData == null) {
         return null;
      } else {
         Set keySet = this.mLookupKeyData.keySet();
         Iterator i$ = keySet.iterator();

         while(i$.hasNext()) {
            Object key = i$.next();
            returnMap.put(key, this.getValue(valueColumnIdx, partition, key, (Date)null, lookupId));
         }

         return returnMap;
      }
   }

   public Map getMapping(int valueColumnIdx, String lookupId) throws Exception {
      TreeMap returnMap = new TreeMap();
      Set keySet = this.mLookupKeyData.keySet();
      Iterator i$ = keySet.iterator();

      while(i$.hasNext()) {
         Object key = i$.next();
         returnMap.put(key, this.getValue(valueColumnIdx, (Object)null, key, (Date)null, lookupId));
      }

      return returnMap;
   }

   public List getKeysInUserSeq(Date paramDate) throws Exception {
      Set keySet = this.mLookupKeyData.keySet();
      ArrayList l = new ArrayList();
      Iterator l2 = keySet.iterator();

      while(l2.hasNext()) {
         Object i$ = l2.next();
         BigDecimal row = (BigDecimal)this.getValue(this.mUserSeqColIndex, (Object)null, i$, paramDate, this.mKeyColumnName);
         if(row != null) {
            l.add(new Object[]{Integer.valueOf(row.intValue()), i$});
         }
      }

      Collections.sort(l, new LookupData$1(this));
      ArrayList l21 = new ArrayList();
      Iterator i$1 = l.iterator();

      while(i$1.hasNext()) {
         Object[] row1 = (Object[])i$1.next();
         l21.add(row1[1]);
      }

      return l21;
   }
}
