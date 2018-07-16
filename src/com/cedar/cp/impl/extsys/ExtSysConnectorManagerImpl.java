// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extsys;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.table.TableModel;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.FinanceSystemCellData;
import com.cedar.cp.api.extsys.ExtSysConnector;
import com.cedar.cp.api.extsys.ExtSysConnectorManager;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.dataEntry.FinanceSystemCellDataImpl;
import com.cedar.cp.dto.extsys.ExtSysTransactionQueryParams;
import com.cedar.cp.util.Log;

public class ExtSysConnectorManagerImpl implements ExtSysConnectorManager {

   private Map<Integer, ConnectorWrapper> mConnectorMap = new HashMap();
   private Map<Class, Integer> mClassToSQLTypeMap = new HashMap();
   private static ExtSysConnectorManagerImpl sExtSysConnectorManagerImpl;
   protected Log sLog = new Log(ExtSysConnectorManagerImpl.class);


   private int getColumn(TableModel tm, String columnName) {
      for(int i = 0; i < tm.getColumnCount(); ++i) {
         if(tm.getColumnName(i).equals(columnName)) {
            return i;
         }
      }

      return -1;
   }

   public synchronized void startUp(TableModel extSysConnectors) {
      if(extSysConnectors != null && extSysConnectors.getRowCount() > 0) {
         int connectorClassColumn = this.getColumn(extSysConnectors, "connector_class");
         int extSysIdColumn = this.getColumn(extSysConnectors, "external_system_id");
         int propertyNameColumn = this.getColumn(extSysConnectors, "property_name");
         int propertyValueColumn = this.getColumn(extSysConnectors, "property_value");
         int currentExtSysId = -1;
         ConnectorWrapper currentWrapper = null;

         String connectorClassName;
         for(int i$ = 0; i$ < extSysConnectors.getRowCount(); ++i$) {
            int entry = ((Integer)extSysConnectors.getValueAt(i$, extSysIdColumn)).intValue();
            String wrapper = (String)extSysConnectors.getValueAt(i$, connectorClassColumn);
            connectorClassName = (String)extSysConnectors.getValueAt(i$, propertyNameColumn);
            String t = (String)extSysConnectors.getValueAt(i$, propertyValueColumn);
            if(entry != currentExtSysId) {
               currentExtSysId = entry;
               currentWrapper = new ConnectorWrapper(wrapper, entry);
               this.mConnectorMap.put(Integer.valueOf(currentWrapper.getExtSysId()), currentWrapper);
               this.sLog.info("startUp", "Loading connector:" + wrapper);
            }

            if(connectorClassName != null) {
               currentWrapper.getProperties().put(connectorClassName, t);
               this.sLog.info("startUp", "Adding property " + connectorClassName + " value " + t);
            }
         }

         Iterator var21 = this.mConnectorMap.entrySet().iterator();

         while(var21.hasNext()) {
            Entry var22 = (Entry)var21.next();
            ConnectorWrapper var23 = (ConnectorWrapper)var22.getValue();
            connectorClassName = var23.getConnectionClass();

            try {
               Class var24 = Class.forName(connectorClassName, false, Thread.currentThread().getContextClassLoader());
               Object targetConnector = var24.newInstance();
               ExtSysConnectorProxy connector = new ExtSysConnectorProxy(targetConnector);
               var23.setExtSysConnector(connector);

               try {
                  connector.startup(var23.getProperties());
               } catch (Throwable var16) {
                  this.sLog.error("startUp", "Exception in connector startup", var16);
                  var16.printStackTrace();
                  var23.setThrowable(var16);
               }
            } catch (ClassNotFoundException var17) {
               this.sLog.error("startUp", "ClassNotFound for connector class:" + connectorClassName, var17);
               var17.printStackTrace();
               var23.setThrowable(var17);
            } catch (InstantiationException var18) {
               this.sLog.error("startUp", "InstantiationException for connector:" + connectorClassName, var18);
               var18.printStackTrace();
               var23.setThrowable(var18);
            } catch (IllegalAccessException var19) {
               this.sLog.error("startUp", "IllegalAccessException for connector:" + connectorClassName, var19);
               var19.printStackTrace();
               var23.setThrowable(var19);
            } catch (Throwable var20) {
               this.sLog.error("startUp", "Throwable casught for connector:" + connectorClassName, var20);
               var20.printStackTrace();
               var23.setThrowable(var20);
            }
         }
      }

   }

   private ConnectorWrapper getConnectorWrapper(int extSysId) {
      return (ConnectorWrapper)this.mConnectorMap.get(Integer.valueOf(extSysId));
   }

   public FinanceSystemCellData getFinanceSystemCellData(int externalSystemId, ExtSysTransactionQueryParams params, String connectorClass) throws ValidationException {
      ConnectorWrapper wrapper = this.getConnectorWrapper(externalSystemId);
      if(wrapper == null) {
         throw new ValidationException("Connector failed to load/startUp:" + wrapper.getThrowable());
      } else {
         ExtSysConnector connector = wrapper.getThrowable() == null?wrapper.getExtSysConnector():null;
         FinanceSystemCellDataImpl cellData = new FinanceSystemCellDataImpl();
         if(connector == null) {
            throw new ValidationException("Connector failed to load/startUp:" + wrapper.getThrowable());
         } else {
            try {
               StringBuilder t = new StringBuilder();
               HashMap var15 = new HashMap();
               TableModel var16 = connector.queryDrillback(params.getCompanyVisId(), params.getLedgerVisId(), params.getPathVisIds(), params.getElemVisIds(), params.getNullElemVisIds(), params.getYear(), params.getPeriods(), params.getHighestPeriod(), params.getValueType(), params.getCurrency(), params.getCurrencyType(), t, params.getModelProperties());
               cellData.setDimSelectionSummary(t.toString());
               cellData.setOtherSelectionSummary("");

               int outputTotalsLabel;
               for(outputTotalsLabel = 0; outputTotalsLabel < var16.getColumnCount(); ++outputTotalsLabel) {
                  String column = var16.getColumnName(outputTotalsLabel);
                  if(column != null && column.length() > 0 && column.charAt(0) == 33) {
                     var15.put(column, new BigDecimal(0));
                     column = column.substring(1);
                  }

                  cellData.addColumnNameAndType(column, this.getSQLTypeForClass(var16.getColumnClass(outputTotalsLabel)));
               }

               BigDecimal value;
               int var19;
               for(outputTotalsLabel = 0; outputTotalsLabel < var16.getRowCount(); ++outputTotalsLabel) {
                  cellData.newRow();

                  for(var19 = 0; var19 < var16.getColumnCount(); ++var19) {
                     Object columnName = var16.getValueAt(outputTotalsLabel, var19);
                     if(columnName instanceof BigDecimal) {
                        value = (BigDecimal)columnName;
                        if(value.scale() > cellData.getColumnMaxScale(var19)) {
                           cellData.setColumnMaxScale(var19, value.scale());
                        }

                        cellData.addColumnValue(value);
                     } else {
                        cellData.addColumnValue(columnName);
                     }

                     this.updateRunningTotal(var15, var16.getColumnName(var19), columnName);
                  }

                  cellData.storeRow();
               }

               if(!var15.isEmpty()) {
                  cellData.newRow();
                  boolean var17 = false;

                  for(var19 = 0; var19 < var16.getColumnCount(); ++var19) {
                     String var18 = var16.getColumnName(var19);
                     value = (BigDecimal)var15.get(var18);
                     if(value != null) {
                        cellData.addColumnValue(value);
                     } else if(!var17 && value == null && var16.getColumnClass(var19) == String.class) {
                        cellData.addColumnValue("Totals:");
                        var17 = true;
                     } else {
                        cellData.addColumnValue((Object)null);
                     }
                  }

                  cellData.storeRow();
               }

               return cellData;
            } catch (Throwable var14) {
               this.sLog.error("getFinanceSystemCellData", var14.getMessage(), var14);
               var14.printStackTrace();
               cellData.addColumnNameAndType("Message", this.getSQLTypeForClass(String.class));
               EntityList exceptionEntityList = this.convertThrowableToEntityList(var14);

               for(int row = 0; row < exceptionEntityList.getNumRows(); ++row) {
                  cellData.newRow();
                  cellData.addColumnValue(exceptionEntityList.getValueAt(row, exceptionEntityList.getHeadings()[0]));
                  cellData.storeRow();
               }

               cellData.setValidationMessage(var14.getMessage());
               return cellData;
            }
         }
      }
   }

   private void updateRunningTotal(Map<String, BigDecimal> runningTotalMap, String columnName, Object value) {
      if(value != null) {
         BigDecimal runningTotal = (BigDecimal)runningTotalMap.get(columnName);
         if(runningTotal != null) {
            if(value instanceof BigDecimal) {
               runningTotalMap.put(columnName, runningTotal.add((BigDecimal)value));
            } else if(value instanceof Number) {
               runningTotalMap.put(columnName, runningTotal.add(new BigDecimal(((Number)value).doubleValue())));
            }
         }

      }
   }

   private EntityList convertThrowableToEntityList(Throwable t) {
      ArrayList visited;
      for(visited = new ArrayList(); t != null && !visited.contains(t); t = t.getCause()) {
         visited.add(t);
      }

      CharArrayWriter caw = new CharArrayWriter();
      PrintWriter pw = new PrintWriter(caw);

      for(int dataList = visited.size() - 1; dataList >= 0; --dataList) {
         Throwable lnr = (Throwable)visited.get(dataList);
         lnr.printStackTrace(pw);
      }

      ArrayList var11 = new ArrayList();
      LineNumberReader var12 = new LineNumberReader(new StringReader(new String(caw.toCharArray())));
      String line = null;

      try {
         while((line = var12.readLine()) != null) {
            var11.add(line);
         }
      } catch (IOException var10) {
         var10.printStackTrace();
      }

      Object[][] data = new Object[var11.size()][1];

      for(int row = 0; row < var11.size(); ++row) {
         data[row][0] = var11.get(row);
      }

      return new EntityListImpl(new String[]{"A problem occurred executing the drill back - see details below."}, data);
   }

   private int getSQLTypeForClass(Class klass) {
      if(this.mClassToSQLTypeMap.isEmpty()) {
         this.mClassToSQLTypeMap.put(String.class, Integer.valueOf(12));
         this.mClassToSQLTypeMap.put(Integer.class, Integer.valueOf(4));
         this.mClassToSQLTypeMap.put(Short.class, Integer.valueOf(5));
         this.mClassToSQLTypeMap.put(BigDecimal.class, Integer.valueOf(3));
         this.mClassToSQLTypeMap.put(Date.class, Integer.valueOf(91));
         this.mClassToSQLTypeMap.put(Double.class, Integer.valueOf(8));
         this.mClassToSQLTypeMap.put(Float.class, Integer.valueOf(6));
         this.mClassToSQLTypeMap.put(Timestamp.class, Integer.valueOf(93));
         this.mClassToSQLTypeMap.put(Time.class, Integer.valueOf(92));
      }

      Integer sqlType = (Integer)this.mClassToSQLTypeMap.get(klass);
      return sqlType != null?sqlType.intValue():12;
   }

   public void shutdown() {
      Iterator i$ = this.mConnectorMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();

         try {
            ConnectorWrapper t = (ConnectorWrapper)entry.getValue();
            if(t.getExtSysConnector() != null && t.getThrowable() == null) {
               t.getExtSysConnector().closedown();
            }
         } catch (Throwable var4) {
            this.sLog.error("shutdown", "connector closedown threw:" + var4);
            var4.printStackTrace();
         }
      }

      this.mConnectorMap.clear();
   }

   public static synchronized ExtSysConnectorManagerImpl getInstance() {
      if(sExtSysConnectorManagerImpl == null) {
         sExtSysConnectorManagerImpl = new ExtSysConnectorManagerImpl();
      }

      return sExtSysConnectorManagerImpl;
   }
}

class ConnectorWrapper {

	private String mConnectionClass;
	private Throwable mThrowable;
	private int mExtSysId;
	private ExtSysConnector mExtSysConnector;
	private Properties mProperties;

	ConnectorWrapper(String connectionClass,
			int extSysId) {
		this.mProperties = new Properties();
		this.mConnectionClass = connectionClass;
		this.mExtSysId = extSysId;
	}

	public String getConnectionClass() {
		return this.mConnectionClass;
	}

	public Throwable getThrowable() {
		return this.mThrowable;
	}

	public int getExtSysId() {
		return this.mExtSysId;
	}

	public ExtSysConnector getExtSysConnector() {
		return this.mExtSysConnector;
	}

	public void setConnectionClass(String connectionClass) {
		this.mConnectionClass = connectionClass;
	}

	public void setThrowable(Throwable throwable) {
		this.mThrowable = throwable;
	}

	public void setExtSysId(int extSysId) {
		this.mExtSysId = extSysId;
	}

	public void setExtSysConnector(ExtSysConnector extSysConnector) {
		this.mExtSysConnector = extSysConnector;
	}

	public Properties getProperties() {
		return this.mProperties;
	}

	public void setProperties(Properties properties) {
		this.mProperties = properties;
	}

}