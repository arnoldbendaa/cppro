// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dataentry;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.ejb.impl.dataentry.ContextInputFactory;
import com.cedar.cp.ejb.impl.dataentry.ContextInputFactory$MyColumn;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.cedar.cp.util.xmlform.StructureColumnValue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class ContextInputFactory$MyFormInputControls {

   private FinanceCubeInput mFinanceCubeInput;
   private int mBudgetCycleId;
   private int[] mStructureElementIds;
   protected int mXAxisIndex;
   protected int mChildDepth;
   private boolean mSecondaryStructure;
   private int mUserId;
   private CalendarInfo mCalendarInfo;
   protected int mFinanceCubeId;
   private int mNumDims;
   protected int[] mTreeStructureIds;
   protected int mCalStructureId;
   private String mRuntimeDataType;
   protected List<ContextInputFactory$MyColumn> mMyColumns;
   private List<String> mQueryNames;
   public CalendarElementNode mCalendarElemNode;
   private Map<String, DataType> mDataTypes;
   private Map<Integer, EntityList> mSecurityAccessDetails;
   public SqlExecutor mSqlExecutor;
   private boolean mFirstSubquery;
   public boolean mIsDftNeeded;
   public boolean mIsContextNonLeaf;
   public boolean mIsContextDisabled;
   public boolean mIsContextNonPlannable;
   public Integer mContextRABudgetState;
   public Integer mContextRAAccessMode;
   private boolean mIsOracle10;
   // $FF: synthetic field
   final ContextInputFactory this$0;


   public ContextInputFactory$MyFormInputControls(ContextInputFactory var1, int userId, FinanceCubeInput config, int budgetCycleId, String contextDataType, int xAxisIndex, int[] structureElementIds, int childDepth, boolean secondaryStructure, CalendarInfo calInfo, Map dataTypes, Map securityAccessDetails) {
      this.this$0 = var1;
      this.mMyColumns = new ArrayList();
      this.mQueryNames = new ArrayList();
      this.mFirstSubquery = true;
      this.mIsContextNonLeaf = false;
      this.mIsContextDisabled = false;
      this.mIsContextNonPlannable = false;
      this.mFinanceCubeInput = config;
      this.mBudgetCycleId = budgetCycleId;
      this.mXAxisIndex = xAxisIndex;
      this.mStructureElementIds = structureElementIds;
      this.mChildDepth = childDepth;
      this.mSecondaryStructure = secondaryStructure;
      this.mUserId = userId;
      this.mCalendarInfo = calInfo;
      if(contextDataType == null) {
         throw new IllegalStateException("Variable contextDataType not defined");
      } else {
         this.mRuntimeDataType = contextDataType;
         this.mDataTypes = dataTypes;
         this.mSecurityAccessDetails = securityAccessDetails;
         this.mFirstSubquery = true;

         try {
            this.mIsOracle10 = ContextInputFactory.accessMethod200(var1).getSqlConnection().getMetaData().getDatabaseMajorVersion() == 10;
         } catch (SQLException var14) {
            throw new RuntimeException("can\'t get oracle version", var14);
         }
      }
   }

   public boolean isGetSpecificModeNoFilter() {
      return this.mChildDepth < 0;
   }

   public boolean isGetSpecificMode() {
      return this.mChildDepth <= 0;
   }

   public boolean isGetNextLevelMode() {
      return this.mChildDepth == 1;
   }

   public boolean isGetAllMode() {
      return this.mChildDepth > 1;
   }

   public boolean isNewRetrieveMethod() {
      boolean newMethod = ContextInputFactory.accessMethod300(this.this$0).equals("1");
      if(newMethod) {
         newMethod = ContextInputFactory.accessMethod400(this.this$0).isGetAllMode() && ContextInputFactory.accessMethod400(this.this$0).getNumDims() - 1 == ContextInputFactory.accessMethod400(this.this$0).getAxisDimIndexes().length && (ContextInputFactory.accessMethod400(this.this$0).getAxisDimIndexes()[0].intValue() != 0 || ContextInputFactory.accessMethod400(this.this$0).getNumDims() > 2);
         if(newMethod && ContextInputFactory.accessMethod400(this.this$0).isGetNextLevelMode() && ContextInputFactory.accessMethod400(this.this$0).isSecondaryStructure()) {
            ContextInputFactory.accessMethod400(this.this$0).mChildDepth = 999;
         }
      }

      return newMethod;
   }

   public FinanceCubeInput getFinanceCubeInput() {
      return this.mFinanceCubeInput;
   }

   public CalendarInfo getCalendarInfo() {
      return this.mCalendarInfo;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public String getRuntimeDataType() {
      return this.mRuntimeDataType;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int cubeId) {
      this.mFinanceCubeId = cubeId;
   }

   public boolean isSecondaryStructure() {
      return this.mSecondaryStructure;
   }

   public int getStructureId(int index) {
      return this.mTreeStructureIds[index];
   }

   public int getStructureElementId(int index) {
      return this.mStructureElementIds[index];
   }

   public int getXAxisIndex() {
      return this.mXAxisIndex;
   }

   public List<String> getQueryNames() {
      return this.mQueryNames;
   }

   public void addQueryName(String queryName) {
      this.mQueryNames.add(queryName);
   }

   public CalendarElementNode getCalendarElementNode() {
      return this.mCalendarElemNode;
   }

   public List<ContextInputFactory$MyColumn> getColumns() {
      return this.mMyColumns;
   }

   public int getCalendarDimIndex() {
      return this.getNumDims() - 1;
   }

   public int getNumDims() {
      return this.mNumDims;
   }

   public boolean isOracle10() {
      return this.mIsOracle10;
   }

   public boolean isRuntimeDataTypeUsed() {
      Iterator i$ = this.getColumns().iterator();

      ContextInputFactory$MyColumn column;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         column = (ContextInputFactory$MyColumn)i$.next();
      } while(column.isFixedDataType());

      return true;
   }

   public boolean isSpecificDataTypeUsed() {
      Iterator i$ = this.getColumns().iterator();

      ContextInputFactory$MyColumn column;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         column = (ContextInputFactory$MyColumn)i$.next();
      } while(!column.isFixedDataType());

      return true;
   }

   public Map<String, DataType> getDataTypes() {
      return this.mDataTypes;
   }

   public EntityList getSecurityAccessDetails(int modelId) {
      return (EntityList)this.mSecurityAccessDetails.get(Integer.valueOf(modelId));
   }

   public List<StructureColumnValue> getStructureColumns() {
      ArrayList retList = new ArrayList();
      Iterator i$ = this.getFinanceCubeInput().getColumnValues().iterator();

      while(i$.hasNext()) {
         Object obj = i$.next();
         if(obj instanceof StructureColumnValue) {
            retList.add((StructureColumnValue)obj);
         }
      }

      return retList;
   }

   public Integer[] getAxisDimIndexes() {
      Integer[] retArray = new Integer[this.getStructureColumns().size()];

      for(int i = 0; i < retArray.length; ++i) {
         retArray[i] = Integer.valueOf(((StructureColumnValue)this.getStructureColumns().get(i)).getDim());
      }

      return retArray;
   }

   public boolean isPreXaxis(int d) {
      for(int i = 0; i < this.getXaxisIndexIndex(); ++i) {
         if(d == ContextInputFactory.accessMethod400(this.this$0).getAxisDimIndexes()[i].intValue()) {
            return true;
         }
      }

      return false;
   }

   public int getXaxisIndexIndex() {
      int xAxisIndexIndex = -1;

      for(int i = 0; i < this.getAxisDimIndexes().length; ++i) {
         if(ContextInputFactory.accessMethod400(this.this$0).getAxisDimIndexes()[i].intValue() == this.getXAxisIndex()) {
            xAxisIndexIndex = i;
            break;
         }
      }

      return xAxisIndexIndex;
   }

   public boolean isDim0AnAxis() {
      Integer[] arr$ = this.getAxisDimIndexes();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int dimIndex = arr$[i$].intValue();
         if(dimIndex == 0) {
            return true;
         }
      }

      return false;
   }

   public boolean isDimAnAxis(int dim) {
      Integer[] arr$ = this.getAxisDimIndexes();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int dimIndex = arr$[i$].intValue();
         if(dimIndex == dim) {
            return true;
         }
      }

      return false;
   }

   public String getSubqueryPrefix() {
      if(this.mFirstSubquery) {
         this.mFirstSubquery = false;
         return "with ";
      } else {
         return ",";
      }
   }

   // $FF: synthetic method
   static int accessMethod002(ContextInputFactory$MyFormInputControls x0, int x1) {
      return x0.mNumDims = x1;
   }

   // $FF: synthetic method
   static int[] accessMethod100(ContextInputFactory$MyFormInputControls x0) {
      return x0.mStructureElementIds;
   }
}
