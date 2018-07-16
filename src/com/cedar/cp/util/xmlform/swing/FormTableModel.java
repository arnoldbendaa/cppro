// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import bsh.TargetError;
import com.cedar.cp.util.InterpreterException;
import com.cedar.cp.util.InterpreterWrapper;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.NumberFormatter;
import com.cedar.cp.util.xmlform.AutoPopulate;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.ColumnGroup;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.Header;
import com.cedar.cp.util.xmlform.OnFormLoad;
import com.cedar.cp.util.xmlform.inputs.DefaultFormDataInputModel;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import com.cedar.cp.util.xmlform.inputs.LookupTarget;
import com.cedar.cp.util.xmlform.swing.FormFunctions;
import com.cedar.cp.util.xmlform.swing.FormTableModel$1;
import com.cedar.cp.util.xmlform.swing.FormulaParseException;
import com.cedar.cp.util.xmlform.swing.ValidationMessageStore;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public abstract class FormTableModel extends AbstractTableModel implements ValidationMessageStore {

   protected int mLastEngineRow = -1;
   protected FormConfig mFormConfig;
   protected Integer[] mFormulaColumnIndexes;
   protected DefaultTreeModel mFormulaDependancyTree;
   protected Set mFormulaProcessedColumns;
   protected HashMap mFormulaDependencies;
   protected ArrayList mAllLeafLevelColumns = new ArrayList();
   protected Map mAllLeafLevelColumnIdMappings = new HashMap();
   protected ArrayList mVisibleColumns = new ArrayList();
   protected Map mVisibleColumnIdMappings = new HashMap();
   protected ArrayList mRows = new ArrayList();
   protected List mRowLevels = new ArrayList();
   protected int[] mParentRows;
   protected InterpreterWrapper mEngine;
   protected boolean mDisableEngine = false;
   protected FormFunctions mFormFunctions;
   protected Map mVariables;
   protected boolean mReadOnly;
   protected String mDefaultFormatPattern = "";
   protected transient Map<Object, Map<String, String>> mValidationMessages;
   protected int mFormulaProcessingRow = -1;
   private BigDecimal mMinusOne = new BigDecimal(-1);
   protected StringBuffer mColumnFormulae = new StringBuffer();
   private boolean isModified = false;
   private transient Log mLog = new Log(this.getClass());
   private List<String> mInterpreterExceptionMethods = new ArrayList();
   private List<Integer> mAddedRows;


   public boolean isModified() {
      return this.isModified;
   }

   public void setModified(boolean isModified) {
      this.isModified = isModified;
   }

   void resetLastEngineRow() {
      this.mLastEngineRow = -1;
   }

   protected void appendFormulaForColumn(Column c) {
      String colId = c.getId();
      String formula = c.getFormula();
      this.mColumnFormulae.append(InterpreterWrapper.getColumnFormulaFunction(colId, formula));
   }

   public void rebuildFromConfig(FormConfig config) throws FormulaParseException {
      this.rebuildFromConfig(config, (Map)null);
   }

   public synchronized void moveColumn(int oldIndex, int newIndex) {
      if(this.mVisibleColumns != null && this.mVisibleColumns.size() > 0) {
         Object colObj = this.mVisibleColumns.get(oldIndex);
         Column oldColumn = null;
         if(colObj instanceof Column) {
            oldColumn = (Column)colObj;
         }

         Object newColIndexObj = this.mVisibleColumns.get(newIndex);
         Column newColumn = null;
         if(newColIndexObj instanceof Column) {
            newColumn = (Column)newColIndexObj;
         }

         this.mVisibleColumns.remove(oldIndex);
         this.mVisibleColumns.add(newIndex, oldColumn);
         if(oldColumn != null && newColumn != null) {
            this.mFormConfig.moveColumn(oldColumn, newColumn);
            this.isModified = true;
         }
      }

   }

   public void rebuildFromConfig(FormConfig config, Map contextVariables) throws FormulaParseException {
      this.mFormConfig = config;
      this.mVariables = config.getVariables();
      this.mAllLeafLevelColumns.clear();
      this.mVisibleColumns.clear();
      this.mFormulaColumnIndexes = new Integer[0];
      this.mRows.clear();
      this.mLastEngineRow = -1;
      this.mFormFunctions = new FormTableModel$1(this, this.mVariables);

      try {
         this.mEngine = new InterpreterWrapper();
         this.mEngine.setVariable("FormFunctions", this.mFormFunctions);
         this.mEngine.eval("import java.lang.String;\nimport java.lang.Double;\nimport java.lang.Boolean;\nimport java.util.Date;\nimport java.util.Calendar;\nimport com.cedar.cp.util.xmlform.PropertiesMap;\n;import com.cedar.cp.util.xmlform.CalendarInfo;\ndouble result = 0.0;\nString stringResult = \"\";\nDate dateResult = null;\nString defaultDateFormat = \"yyyy-MM-dd\";\nint rownum = -1;\nboolean isBlank( colValue ) { return FormFunctions.isBlank(colValue); }\nboolean flagSet( colValue ) { return FormFunctions.isFlagSet(colValue); }\nString left( colValue, colLen ) { return FormFunctions.left(colValue,colLen); }\nString right( colValue, colLen ) { return FormFunctions.right(colValue,colLen); }\ndouble round( colValue, scale ) { return FormFunctions.round(colValue,scale); }\nString sysVariable( varName ) { return FormFunctions.sysVar(varName); }\ndouble sum( colName ) { return FormFunctions.sum(colName); }\nboolean isValidDate( date ) { return FormFunctions.isValidDate(date, defaultDateFormat); }\nboolean isValidDate( date, format ) { return FormFunctions.isValidDate(date, format); }\nDate getContextDate() { return FormFunctions.getContextDate(); }\nDate getContextDate( int yearOffset, int monthOffset, int dayOffset) { return FormFunctions.getContextDate(yearOffset, monthOffset, dayOffset); }\nDate getContextColumnDate() { return FormFunctions.getContextColumnDate(); }\nDate getDate( String date ) { return FormFunctions.getDate(date, defaultDateFormat); }\nDate getDate( String date, String format ) { return FormFunctions.getDate(date, format); }\nint getYear( String date ) { return FormFunctions.getYear(date, defaultDateFormat); }\nint getYear( String date, String format ) { return FormFunctions.getYear(date, format); }\nint getYear( java.util.Date date ) { return FormFunctions.getYear(date); }\nint getMonth( String date ) { return FormFunctions.getMonth(date, defaultDateFormat); }\nint getMonth( String date, String format ) { return FormFunctions.getMonth(date, format); }\nint getMonth( java.util.Date date ) { return FormFunctions.getMonth(date); }\nint getDayOfMonth( String date ) { return FormFunctions.dayOfMonth(date, defaultDateFormat); }\nint getDayOfMonth( String date, String format ) { return FormFunctions.dayOfMonth(date, format); }\nint getDayOfMonth( java.util.Date date ) { return FormFunctions.dayOfMonth(date); }\nint getDayOfYear( String date ) { return FormFunctions.dayOfYear(date, defaultDateFormat); }\nint getDayOfYear( String date, String format ) { return FormFunctions.dayOfYear(date, format); }\nint getDayOfYear( java.util.Date date ) { return FormFunctions.dayOfYear(date); }\nint compareDates( dateOneText, dateTwoText, format ) { return FormFunctions.compareDates(dateOneText, dateTwoText, format); }\nint getDifferenceInMonths( String dateOneText, String dateTwoText) { return FormFunctions.getDifferenceInMonths(dateOneText, dateTwoText, null); }\nint getDifferenceInMonths( String dateOneText, String dateTwoText, String format ) { return FormFunctions.getDifferenceInMonths(dateOneText, dateTwoText, format); }\nint getDifferenceInMonths( java.util.Date startDate, java.util.Date endDate ) { return FormFunctions.getDifferenceInMonths(startDate, endDate); }\nint getDaysInMonth( String date ) { return FormFunctions.getDaysInMonth(date, defaultDateFormat); }\nint getDaysInMonth( String date, String format ) { return FormFunctions.getDaysInMonth(date, format); }\nint getDaysInMonth( java.util.Date date ) { return FormFunctions.getDaysInMonth(date); }\nint getDaysInYear( String date ) { return FormFunctions.getDaysInYear(date, defaultDateFormat); }\nint getDaysInYear( String date, String format ) { return FormFunctions.getDaysInYear(date, format); }\nint getDaysInYear( java.util.Date date ) { return FormFunctions.getDaysInYear(date); }\nint getDifferenceInDays( String dateOneText, String dateTwoText ) { return FormFunctions.getDifferenceInDays(dateOneText, dateTwoText, defaultDateFormat); }\nint getDifferenceInDays( String dateOneText, String dateTwoText, String format ) { return FormFunctions.getDifferenceInDays(dateOneText, dateTwoText, format); }\nint getDifferenceInDays( Date startDate, Date endDate ) { return FormFunctions.getDifferenceInDays(startDate, endDate); }\nString calcNewDate( startDate, format, days ) { return FormFunctions.calcNewDate(startDate, format, days); }\nDate adjustDate( java.util.Date date, int yearOffset, int monthOffset, int dayOffset ) { return FormFunctions.adjustDate(date, yearOffset, monthOffset, dayOffset); }\ndouble lookup( String colName, Object colValue ) { return FormFunctions.lookup(colName,colValue,null, null); }\ndouble lookup( String colName, Object colValue, Date date ) { return FormFunctions.lookup(colName,colValue,null,date); }\ndouble lookup( String colName, Object colValue, String partitionValue ) { return FormFunctions.lookup(colName,colValue,partitionValue, null); }\ndouble lookup( String colName, Object colValue, String partitionValue, Date date ) { return FormFunctions.lookup(colName,colValue,partitionValue, date); }\nString lookupString( String colName, Object colValue ) { return FormFunctions.lookupString(colName,colValue,null, null); }\nString lookupString( String colName, Object colValue, Date date ) { return FormFunctions.lookupString(colName,colValue,null, date); }\nString lookupString( String colName, Object colValue, String partitionValue ) { return FormFunctions.lookupString(colName,colValue,partitionValue, null); }\nString lookupString( String colName, Object colValue, String partitionValue, Date date ) { return FormFunctions.lookupString(colName,colValue,partitionValue, date); }\nDate lookupDate( String colName, Object colValue ) { return FormFunctions.lookupDate(colName,colValue,null, null); }\nDate lookupDate( String colName, Object colValue, Date date ) { return FormFunctions.lookupDate(colName,colValue,null, date); }\nDate lookupDate( String colName, Object colValue, String partitionValue ) { return FormFunctions.lookupDate(colName,colValue,partitionValue, null); }\nDate lookupDate( String colName, Object colValue, String partitionValue, Date date ) { return FormFunctions.lookupDate(colName,colValue,partitionValue, date); }\ndouble level() { return FormFunctions.level(); }\nint rowNumber() { return FormFunctions.rowNumber(); }\nString colDataType( colName ) { return FormFunctions.columnDataType(colName); }\nString colPeriodVisId( colName ) { return FormFunctions.columnPeriodVisId(colName); }\nString colPeriodPathToRoot( colName ) { return FormFunctions.columnPeriodPathToRoot(colName); }\nDate colPeriodDate( colName ) { return FormFunctions.columnPeriodDate(colName); }\nString elementVisId( dimIndex ) { return FormFunctions.elementVisId(dimIndex); }\nboolean isReadOnlyNominal(){ FormFunctions.isReadOnlyNominal(); }\nvoid setColumnHeader( colName, colHeaderText ){ FormFunctions.setColumnHeader( colName, colHeaderText ); }\nvoid setColumnGroupHeader( colName, colHeaderText ){ FormFunctions.setColumnGroupHeader( colName, colHeaderText ); }\nvoid registerValidationMessage( String colName, String message ){ FormFunctions.registerValidationMessage( rownum, colName, message ); }\nvoid clearValidationMessage( String colName ){ FormFunctions.clearValidationMessage( rownum, colName ); }\nvoid clearValidationMessages(){ FormFunctions.clearValidationMessage( rownum ); }\nString getBudgetCycleStateAsString(){ return FormFunctions.getBudgetCycleStateAsString(); }\n String getBudgetCycleStateAsString( String costCentre ){ return FormFunctions.getBudgetCycleStateAsString(costCentre); }\n ");
         this.evaluateOnFormLoad(this.mFormConfig.getHeader());
         this.mColumnFormulae.setLength(0);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      this.mDefaultFormatPattern = this.mFormConfig.getBody().getDefaultFormat();
      this.processBody();
      this.fireTableStructureChanged();
      if(contextVariables != null) {
         this.mVariables.putAll(contextVariables);
      }

   }

   public InterpreterWrapper getFormulaEngine() {
      return this.mEngine;
   }

   private void evaluateOnFormLoad(Header header) throws InterpreterException {
      OnFormLoad formLoad = header.getOnFormLoad();
      if(formLoad != null) {
         String formula = formLoad.getFormula();
         if(formula != null) {
            this.mEngine.eval(formula);
         }
      }

   }

   public void setDisableEngine(boolean state) {
      this.mDisableEngine = state;
   }

   protected int recursivelyProcessColumns(List cols, int index) throws FormulaParseException {
      ArrayList formulaCols = new ArrayList();
      int result = this.recurseProcessColumns(cols, formulaCols, index);
      this.mFormulaColumnIndexes = new Integer[formulaCols.size()];
      this.mFormulaDependencies = new HashMap();
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
      this.mFormulaDependancyTree = new DefaultTreeModel(root);
      this.mFormulaProcessedColumns = new HashSet();

      for(int i = 0; i < this.mFormulaColumnIndexes.length; ++i) {
         Integer colIndex = (Integer)formulaCols.get(i);
         this.mFormulaColumnIndexes[i] = colIndex;
         Column col = (Column)this.mAllLeafLevelColumns.get(colIndex.intValue());

         try {
            this.addColumnToDependencyTree(root, col);
            InterpreterWrapper e = new InterpreterWrapper();
            Set expressions = e.getPrimaryExpressions(col.getFormula());
            Iterator i$ = expressions.iterator();

            while(i$.hasNext()) {
               Object expression = i$.next();
               String[] idParts = expression.toString().split("\\.");
               String[] arr$ = idParts;
               int len$ = idParts.length;

               for(int i$1 = 0; i$1 < len$; ++i$1) {
                  String id = arr$[i$1];
                  int expressionColumn = this.getLeafLevelColumnIndexFromId(id);
                  if(expressionColumn != -1) {
                     Object dependencies = (List)this.mFormulaDependencies.get(id);
                     if(dependencies == null) {
                        dependencies = new ArrayList();
                        this.mFormulaDependencies.put(id, dependencies);
                     }

                     ((List)dependencies).add(colIndex);
                  }
               }
            }
         } catch (InterpreterException var20) {
            throw new FormulaParseException(var20, col);
         }
      }

      return result;
   }

   private void addColumnToDependencyTree(DefaultMutableTreeNode parent, Column col) throws InterpreterException {
      DefaultMutableTreeNode me = new DefaultMutableTreeNode(col);
      parent.add(me);
      if(!this.mFormulaProcessedColumns.contains(col)) {
         this.mFormulaProcessedColumns.add(col);
         InterpreterWrapper engine = new InterpreterWrapper();
         Set expressions = engine.getPrimaryExpressions(col.getFormula());
         Iterator it = expressions.iterator();

         while(it.hasNext()) {
            String id = (String)it.next();
            Column childColumn = this.getLeafLevelColumnFromId(id);
            if(childColumn != null && childColumn.getFormula() != null) {
               this.addColumnToDependencyTree(me, childColumn);
            }
         }
      }

   }

   private int recurseProcessColumns(List cols, List formulaCols, int index) {
      Iterator iter = cols.iterator();

      while(iter.hasNext()) {
         Object o = iter.next();
         if(o instanceof Column) {
            Column group = (Column)o;
            if(group.isTypeFormula() || group.isTypeStringFormula()) {
               this.appendFormulaForColumn(group);
               formulaCols.add(new Integer(index));
            }

            this.mAllLeafLevelColumns.add(group);
            this.mAllLeafLevelColumnIdMappings.put(group.getId(), new Integer(index++));
            if(!group.isHidden()) {
               this.mVisibleColumns.add(group);
               this.mVisibleColumnIdMappings.put(group.getId(), new Integer(this.mVisibleColumns.size() - 1));
            }
         } else if(o instanceof ColumnGroup) {
            ColumnGroup var7 = (ColumnGroup)o;
            cols = var7.getColumns();
            index = this.recurseProcessColumns(cols, formulaCols, index);
         }
      }

      return index;
   }

   public BigDecimal getRowLevel(int row) {
      return row >= this.mRowLevels.size()?this.mMinusOne:(BigDecimal)this.mRowLevels.get(row);
   }

   public int getParentRow(int row) {
      return this.mParentRows == null?-1:this.mParentRows[row];
   }

   public void buildParentRows() {
      this.mParentRows = new int[this.mRowLevels.size()];
      int r = 0;

      while(r < this.mParentRows.length) {
         this.mParentRows[r] = -1;
         int thisRowLevel = ((BigDecimal)this.mRowLevels.get(r)).intValue();
         int i = r - 1;

         while(true) {
            if(i >= 0) {
               if(((BigDecimal)this.mRowLevels.get(i)).intValue() >= thisRowLevel) {
                  --i;
                  continue;
               }

               this.mParentRows[r] = i;
            }

            ++r;
            break;
         }
      }

   }

   public Map getLookupMapping(String name, Object partitionKey) {
      LookupTarget lut = (LookupTarget)this.mVariables.get(name);
      if(lut != null) {
         try {
            return partitionKey != null?lut.getMapping(partitionKey):lut.getMapping();
         } catch (Exception var5) {
            throw new IllegalStateException(var5);
         }
      } else {
         this.mLog.info("getLookupMapping", name + " not found");
         return null;
      }
   }

   public FormDataInputModel getNonProtectedValues() {
      ArrayList nonProtectedColumns = new ArrayList();
      Iterator rowData = this.mAllLeafLevelColumns.iterator();

      while(rowData.hasNext()) {
         Object result = rowData.next();
         Column row = (Column)result;
         if(!row.getIsProtected()) {
            nonProtectedColumns.add(row);
         }
      }

      ArrayList var10 = new ArrayList();

      for(int var11 = 0; var11 < this.mRows.size(); ++var11) {
         HashMap var13 = new HashMap();
         Iterator i$ = nonProtectedColumns.iterator();

         while(i$.hasNext()) {
            Object nonProtectedColumn = i$.next();
            Column col = (Column)nonProtectedColumn;
            String colId = col.getId();
            Object value = this.getLeafLevelValueAt(var11, this.getLeafColumnIndex(col));
            var13.put(colId, value);
         }

         var10.add(var13);
      }

      DefaultFormDataInputModel var12 = new DefaultFormDataInputModel(this.mFormConfig);
      var12.setData(var10);
      return var12;
   }

   protected void resetAllEnterableFields() {
      BigDecimal zero = new BigDecimal(0);
      Iterator iter = this.mRows.iterator();

      while(iter.hasNext()) {
         Object[] row = (Object[])((Object[])iter.next());

         for(int i = 0; i < this.mAllLeafLevelColumns.size(); ++i) {
            Column cc = (Column)this.mAllLeafLevelColumns.get(i);
            if(!cc.getIsProtected() && (cc.isTypeNumber() || cc.isTypeDataType() || cc.isTypeFormula())) {
               row[i] = zero;
            } else if(!cc.getIsProtected() && (cc.isTypeString() || cc.isTypeStringFormula() || cc.isTypeCellNote())) {
               row[i] = "";
            }
         }
      }

   }

   public void runFormulaeForRow(Set<Integer> totalRelatedColumnIndexes, int rowIndex, int columnUpdated) {
      boolean leaf = this.isRowALeafRow(rowIndex);
      this.runFormulaeForRow(totalRelatedColumnIndexes, rowIndex, columnUpdated, !leaf);
   }

   protected boolean isRowALeafRow(int rowIndex) {
      boolean leaf = true;
      if(rowIndex < this.mRows.size() - 1) {
         int currentLevel = this.getRowLevel(rowIndex).intValue();
         int nextLevel = this.getRowLevel(rowIndex + 1).intValue();
         if(currentLevel < nextLevel) {
            leaf = false;
         }
      }

      return leaf;
   }

   public void runFormulaeForLeafColumn(Set<Integer> totalRelatedColumnIndexes, int columnIndex) {
      try {
         for(int e = 0; e < this.mRows.size(); ++e) {
            this.updateEngineForRow(e);
            boolean leaf = this.isRowALeafRow(e);
            this.runFormulaeForRowAndLeafColumn(totalRelatedColumnIndexes, e, columnIndex, !leaf);
         }
      } catch (Exception var5) {
         this.mLog.debug("Formula error: " + var5.getMessage());
         var5.printStackTrace();
      }

   }

   protected void runFormulaeForRowAndLeafColumn(Set<Integer> totalRelatedColumnIndexes, int rowIndex, int columnIndex, boolean atParentLevel) {
      if(!this.mDisableEngine) {
         TreeSet formulaStack = new TreeSet();
         formulaStack.add(Integer.valueOf(columnIndex));
         if(!formulaStack.isEmpty()) {
            this.runFormulaStack(totalRelatedColumnIndexes, rowIndex, atParentLevel, formulaStack);
         }

      }
   }

   protected void runFormulaStack(Set<Integer> totalRelatedColumnIndexes, int rowIndex, boolean atParentLevel, Set formulaStack) {
      Set processStack = new TreeSet();
      int maxRuns = 10;

      while(!((Set)formulaStack).isEmpty() && maxRuns-- > 0) {
         Set temp = processStack;
         processStack = formulaStack;
         formulaStack = temp;
         ((Set)temp).clear();
         Iterator iter = ((Set)processStack).iterator();

         while(iter.hasNext()) {
            int colIndex = ((Integer)iter.next()).intValue();
            Column col = (Column)this.mAllLeafLevelColumns.get(colIndex);
            if(!atParentLevel || !col.isAggregated()) {
               String id = InterpreterWrapper.getColumnFormulaFunctionName(col.getId());
               Object result = null;
               this.mFormulaProcessingRow = rowIndex;

               try {
                  this.mEngine.eval(id);
                  result = col.isTypeStringFormula()?this.mEngine.getVariable("stringResult"):this.mEngine.getVariable("result");
               } catch (InterpreterException var16) {
                  if(!(var16.getCause() instanceof TargetError)) {
                     throw new FormulaParseException(var16, col);
                  }

                  if(!this.mInterpreterExceptionMethods.contains(id)) {
                     this.mInterpreterExceptionMethods.add(id);
                     this.mLog.warn(id, var16.getMessage());
                  }
               }

               try {
                  if(result != null) {
                     if(result instanceof Double) {
                        double e = this.mFormFunctions.round(((Double)result).doubleValue(), 4);
                        result = new BigDecimal(String.valueOf(e));
                        this.mEngine.setVariable(col.getId(), Double.valueOf(e));
                     } else {
                        this.mEngine.setVariable(col.getId(), result);
                     }

                     if(this.internalSetValueAndParents(totalRelatedColumnIndexes, result, rowIndex, colIndex, col.isAggregated())) {
                        List var17 = (List)this.mFormulaDependencies.get(col.getId());
                        if(var17 != null) {
                           ((Set)formulaStack).addAll(var17);
                        }
                     }

                     this.updateEngineForRow(rowIndex);
                  }
               } catch (InterpreterException var15) {
                  throw new FormulaParseException(var15, col);
               }
            }
         }
      }

   }

   public void runFormulaeForRow(Set<Integer> totalRelatedColumnIndexes, int rowIndex, int columnUpdated, boolean atParentLevel) throws FormulaParseException {
      if(!this.mDisableEngine) {
         TreeSet formulaStack = new TreeSet();
         DefaultMutableTreeNode root = (DefaultMutableTreeNode)this.mFormulaDependancyTree.getRoot();
         Enumeration e = root.depthFirstEnumeration();

         while(e.hasMoreElements()) {
            DefaultMutableTreeNode fNode = (DefaultMutableTreeNode)e.nextElement();
            if(fNode.isLeaf()) {
               Object userObject = fNode.getUserObject();
               if(userObject instanceof Column) {
                  Column toRun = (Column)fNode.getUserObject();
                  int colIndex = this.getLeafLevelColumnIndexFromId(toRun.getId());
                  formulaStack.add(Integer.valueOf(colIndex));
               }
            }
         }

         if(!formulaStack.isEmpty()) {
            this.runFormulaStack(totalRelatedColumnIndexes, rowIndex, atParentLevel, formulaStack);
         }

      }
   }

   protected void internalSetValueAt(Set<Integer> totalRelatedColumnIndexes, Object value, int rowIndex, int leafIndex) throws FormulaParseException {
      if(this.internalSetValueAndParents(totalRelatedColumnIndexes, value, rowIndex, leafIndex, false)) {
         this.evaluateFormulae(totalRelatedColumnIndexes, rowIndex, leafIndex, value, false);
      }

   }

   protected void updateEngineForRow(int rowIndex) throws InterpreterException {
      if(rowIndex != this.mLastEngineRow) {
         this.mLastEngineRow = rowIndex;
         this.mEngine.setVariable("rownum", Integer.valueOf(rowIndex));

         for(int i = 0; i < this.mAllLeafLevelColumns.size(); ++i) {
            Object v = this.getLeafLevelValueAt(rowIndex, i);
            Column cc = (Column)this.mAllLeafLevelColumns.get(i);
            double val;
            if(cc.isTypeDataType()) {
               val = v != null?((BigDecimal)v).doubleValue():0.0D;
               this.mEngine.setVariable(cc.getId(), Double.valueOf(val));
            } else if(!cc.isTypeNumber() && !cc.isTypeFormula()) {
               String var7 = v != null?v.toString():"";
               this.mEngine.setVariable(cc.getId(), var7);
            } else {
               val = v != null?((BigDecimal)v).doubleValue():0.0D;
               this.mEngine.setVariable(cc.getId(), Double.valueOf(val));
            }
         }
      }

   }

   protected void updateAllFormulae() {
      try {
         TreeSet e = new TreeSet();

         for(int r = 0; r < this.mRows.size(); ++r) {
            this.updateEngineForRow(r);
            this.runFormulaeForRow(e, r, -1);
         }

         this.processTotalRelatedColumns(e);
      } catch (Exception var3) {
         this.mLog.debug("Formula error: " + var3.getMessage());
         var3.printStackTrace();
      }

   }

   public void processTotalRelatedColumns(Set<Integer> totalRelatedColumns) {
      int maxRuns = 10;
      Set processStack = new TreeSet();

      while(!((Set)totalRelatedColumns).isEmpty() && maxRuns-- > 0) {
         ((Set)processStack).clear();
         Set temp = processStack;
         processStack = totalRelatedColumns;
         totalRelatedColumns = temp;
         Iterator i$ = ((Set)processStack).iterator();

         while(i$.hasNext()) {
            int colIndex = ((Integer)i$.next()).intValue();
            this.runFormulaeForLeafColumn((Set)totalRelatedColumns, colIndex);
         }
      }

   }

   private void evaluateFormulae(Set<Integer> totalRelatedColumnIndexes, int rowIndex, int leafIndex, Object amount, boolean atParentLevel) {
      try {
         Column e = (Column)this.mAllLeafLevelColumns.get(leafIndex);
         if(rowIndex == this.mLastEngineRow) {
            if(e.isTypeDataType()) {
               this.mLog.debug("MEASURE: FormTableModel:evaluateFormula");
            } else if(!e.isTypeNumber() && !e.isTypeFormula()) {
               this.mEngine.setVariable(e.getId(), amount.toString());
            } else if(amount instanceof String) {
               this.mEngine.setVariable(e.getId(), Double.valueOf(Double.parseDouble(String.valueOf(amount))));
            } else if(amount instanceof Double) {
               this.mEngine.setVariable(e.getId(), (Double)amount);
            } else {
               this.mEngine.setVariable(e.getId(), Double.valueOf(((BigDecimal)amount).doubleValue()));
            }
         } else {
            this.updateEngineForRow(rowIndex);
         }

         List targets = (List)this.mFormulaDependencies.get(e.getId());
         if(targets != null) {
            TreeSet formulaStack = new TreeSet();
            Iterator iter2 = targets.iterator();

            while(iter2.hasNext()) {
               formulaStack.add(iter2.next());
            }

            this.runFormulaStack(totalRelatedColumnIndexes, rowIndex, atParentLevel, formulaStack);
         }
      } catch (Exception var10) {
         this.mLog.debug("Formula error: " + var10.getMessage());
         var10.printStackTrace();
      }

   }

   protected boolean internalSetValueAndParents(Set<Integer> totalRelatedColumnIndexes, Object value, int rowIndex, int leafIndex, boolean updateParent) {
      Column c = (Column)this.mAllLeafLevelColumns.get(leafIndex);
      if((c.isTypeNumber() || c.isTypeDataType()) && !(value instanceof BigDecimal)) {
         double curValue = 0.0D;

         try {
            curValue = NumberFormatter.parseDouble(value.toString());
         } catch (ParseException var19) {
            ;
         }

         value = new BigDecimal(String.valueOf(curValue));
      }

      Object curValue1 = ((Object[])((Object[])this.mRows.get(rowIndex)))[leafIndex];
      if(value.equals(curValue1)) {
         return false;
      } else {
         ((Object[])((Object[])this.mRows.get(rowIndex)))[leafIndex] = value;
         if((c.isTypeNumber() || c.isTypeFormula() || c.isTypeDataType()) && updateParent) {
            double origValue = ((BigDecimal)curValue1).doubleValue();
            double newValue = ((BigDecimal)value).doubleValue();
            double delta = newValue - origValue;
            int parentRow = this.getParentRow(rowIndex);
            if(parentRow != -1) {
               double currentValue = ((BigDecimal)((Object[])((Object[])this.mRows.get(parentRow)))[leafIndex]).doubleValue();
               double newAmount = delta + currentValue;
               this.internalSetParentValue(totalRelatedColumnIndexes, parentRow, leafIndex, delta, newAmount, updateParent);
            }
         }

         return true;
      }
   }

   protected void internalSetParentValue(Set<Integer> totalRelatedColumnIndexes, int parentRow, int leafIndex, double delta, double newAmount, boolean updateParent) {
      ((Object[])((Object[])this.mRows.get(parentRow)))[leafIndex] = new BigDecimal(newAmount);
      int nextParentRow = this.getParentRow(parentRow);
      if(nextParentRow != -1) {
         double currentValue = ((BigDecimal)((Object[])((Object[])this.mRows.get(nextParentRow)))[leafIndex]).doubleValue();
         double nextNewAmount = delta + currentValue;
         this.internalSetParentValue(totalRelatedColumnIndexes, nextParentRow, leafIndex, delta, nextNewAmount, updateParent);
      }

      this.evaluateFormulae(totalRelatedColumnIndexes, parentRow, leafIndex, new Double(newAmount), true);
   }

   public Column getVisibleColumn(int visColumnIndex) {
      return (Column)this.mVisibleColumns.get(visColumnIndex);
   }

   public String getVisibleColumnToolTipText(int visColumnIndex) {
      Column visibleColumn = (Column)this.mVisibleColumns.get(visColumnIndex);
      return visibleColumn.getToolTipText();
   }

   public boolean isVisibleColumnTypeNumeric(int visColumnIndex) {
      Column visibleColumn = (Column)this.mVisibleColumns.get(visColumnIndex);
      return visibleColumn.isTypeNumber() || visibleColumn.isTypeDataType();
   }

   public String getVisibleColumnLookup(int visColumnIndex) {
      Column visibleColumn = (Column)this.mVisibleColumns.get(visColumnIndex);
      return visibleColumn.getLookup();
   }

   public int getVisibleColumnLookupPartition(int visColumnIndex) {
      Column visibleColumn = (Column)this.mVisibleColumns.get(visColumnIndex);
      String columnName = visibleColumn.getLookupPartition();
      return columnName != null?this.getLeafLevelColumnIndexFromId(columnName):-1;
   }

   public String getColumnFormat(int visColumnIndex) {
      Column visibleColumn = (Column)this.mVisibleColumns.get(visColumnIndex);
      String pattern = visibleColumn.getFormat();
      if(pattern.length() == 0) {
         pattern = this.mDefaultFormatPattern;
      }

      return pattern;
   }

   public boolean isColumnBlankWhenZero(int visColumnIndex) {
      Column visibleColumn = (Column)this.mVisibleColumns.get(visColumnIndex);
      return visibleColumn.isBlankWhenZero();
   }

   public int getColumnAlignment(int visColumnIndex) {
      Column visibleColumn = (Column)this.mVisibleColumns.get(visColumnIndex);
      return visibleColumn.getIntAlignment();
   }

   public Color getColumnBackgroundColor(int visColumnIndex) {
      Column visibleColumn = (Column)this.mVisibleColumns.get(visColumnIndex);
      return visibleColumn.getColumnFormat().getBackgroundColor();
   }

   protected int getLeafLevelColumnIndexFromVisibleColumnIndex(int visIndex) {
      Column visibleColumn = (Column)this.mVisibleColumns.get(visIndex);
      Integer leafIndex = (Integer)this.mAllLeafLevelColumnIdMappings.get(visibleColumn.getId());
      return leafIndex.intValue();
   }

   protected int getLeafLevelColumnCount() {
      return this.mAllLeafLevelColumns.size();
   }

   private int getLeafColumnIndex(Column col) {
      Object o = this.mAllLeafLevelColumnIdMappings.get(col.getId());
      return o != null?((Integer)o).intValue():-1;
   }

   public int getDataRowCount() {
      return this.mRows.size();
   }

   public int getRowCount() {
      return this.mRows.size();
   }

   public int getColumnCount() {
      return this.mVisibleColumns.size();
   }

   public String getColumnId(int col) {
      Column c = (Column)this.mVisibleColumns.get(col);
      return c.getId();
   }

   public String getColumnName(int col) {
      Column c = (Column)this.mVisibleColumns.get(col);
      return c.getHeadingDisplay();
   }

   public int getVisibleColumnIndexFromId(String id) {
      Integer colIndex = (Integer)this.mVisibleColumnIdMappings.get(id);
      return colIndex != null?colIndex.intValue():-1;
   }

   public Column getLeafLevelColumnFromId(String id) {
      Column result = null;
      Integer leafIndex = Integer.valueOf(this.getLeafLevelColumnIndexFromId(id));
      if(leafIndex.intValue() != -1) {
         result = (Column)this.mAllLeafLevelColumns.get(leafIndex.intValue());
      }

      return result;
   }

   public ColumnGroup getColumnGroupById(String id) {
      return this.mFormConfig.getColumnGroup(id);
   }

   public Column getColumnById(String id) {
      return this.mFormConfig.getColumnById(id);
   }

   public int getLeafLevelColumnIndexFromId(String id) {
      Integer colIndex = (Integer)this.mAllLeafLevelColumnIdMappings.get(id);
      return colIndex != null?colIndex.intValue():-1;
   }

   public Class getColumnClass(int colIndex) {
      Column c = (Column)this.mVisibleColumns.get(colIndex);
      return c instanceof AutoPopulate?AutoPopulate.class:(c.isTypeString()?String.class:(c.isTypeDataType()?BigDecimal.class:(!c.isTypeNumber() && !c.isTypeDataType()?(c.isTypeFormula()?BigDecimal.class:(c.isTypeStringFormula()?String.class:(c.isTypeCellNote()?String.class:String.class))):BigDecimal.class)));
   }

   protected Object createNewLeafLevelValue(int colIndex) {
      Column c = (Column)this.mAllLeafLevelColumns.get(colIndex);
      return !c.isTypeString() && !c.isTypeStringFormula() && !c.isTypeCellNote()?new BigDecimal(0):"";
   }

   public List<Integer> getAddedRows() {
      if(this.mAddedRows == null) {
         this.mAddedRows = new ArrayList();
      }

      return this.mAddedRows;
   }

   protected abstract void processBody() throws FormulaParseException;

   public abstract void addNewRow();

   public abstract void addNewRow(boolean var1);

   public abstract void deleteRow(int var1);

   public abstract Object getValueAt(int var1, int var2);

   public abstract Object getLeafLevelValueAt(int var1, int var2);

   public abstract void setValueAt(Object var1, int var2, int var3);

   public abstract List getUserEdits();

   public abstract void setPreviousData(FormDataInputModel var1) throws FormulaParseException;

   public void setPreviousData(FormDataInputModel prevData, boolean readOnly) throws FormulaParseException {
      this.mReadOnly = readOnly;
      this.setPreviousData(prevData);
   }

   public boolean isCellEditable(int rowIndex, int colIndex) {
      if(!this.mReadOnly && rowIndex < this.mRows.size()) {
         Column c = (Column)this.mVisibleColumns.get(colIndex);
         return !c.getIsProtected();
      } else {
         return false;
      }
   }

   public FormConfig getFormConfig() {
      return this.mFormConfig;
   }

   private void initValidationMessages() {
      if(this.mValidationMessages == null) {
         this.mValidationMessages = new HashMap();
      }

   }

   public void registerValidationMessage(Object row, String columnId, String message) {
      this.initValidationMessages();
      Map rowMessages = (Map)this.mValidationMessages.get(row);
      if(rowMessages == null) {
         rowMessages = new HashMap();
         this.mValidationMessages.put(row, rowMessages);
      }

      ((Map)rowMessages).put(columnId, message);
      if(row instanceof Integer) {
         this.fireTableRowsUpdated(((Integer)row).intValue(), ((Integer)row).intValue());
      }

   }

   public void clearValidationMessage(Object row, String columnId) {
      this.initValidationMessages();
      Map rowMessages = (Map)this.mValidationMessages.get(row);
      if(rowMessages != null && rowMessages.get(columnId) != null) {
         rowMessages.remove(columnId);
         if(rowMessages.isEmpty()) {
            this.mValidationMessages.remove(row);
         }

         if(row instanceof Integer) {
            this.fireTableCellUpdated(((Integer)row).intValue(), ((Integer)row).intValue());
         }
      }

   }

   public void clearValidationMessage(Object row) {
      this.initValidationMessages();
      this.mValidationMessages.remove(row);
      if(row instanceof Integer) {
         this.fireTableRowsUpdated(((Integer)row).intValue(), ((Integer)row).intValue());
      }

   }

   public void clearAllValidationMessages() {
      this.initValidationMessages();
      HashMap copy = new HashMap(this.mValidationMessages);
      Iterator i$ = copy.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         this.mValidationMessages.remove(entry.getKey());
         if(entry.getKey() instanceof Integer) {
            this.fireTableRowsUpdated(((Integer)entry.getKey()).intValue(), ((Integer)entry.getKey()).intValue());
         }
      }

   }

   public String getValidationMessage(Object row, String columnId) {
      this.initValidationMessages();
      Map rowMessages = (Map)this.mValidationMessages.get(row);
      return rowMessages == null?null:(String)rowMessages.get(columnId);
   }

   public boolean hasValidationMessages() {
      return this.mValidationMessages != null && !this.mValidationMessages.isEmpty();
   }

   private void adjustValidationMessagesByDelta(int startRow, int delta) {
      this.initValidationMessages();
      HashMap copy = new HashMap(this.mValidationMessages);
      HashMap rowsToMove = new HashMap();
      Iterator i$ = copy.entrySet().iterator();

      Entry<Object,Map<String, String>> entry;
      while(i$.hasNext()) {
         entry = (Entry)i$.next();
         if(entry.getKey() instanceof Integer) {
            int row = ((Integer)entry.getKey()).intValue();
            if(row >= startRow) {
               this.mValidationMessages.remove(entry.getKey());
               rowsToMove.put(entry.getKey(), entry.getValue());
            }
         }
      }

      i$ = rowsToMove.entrySet().iterator();

      while(i$.hasNext()) {
         entry = (Entry)i$.next();
         if(entry.getKey() instanceof Integer) {
            this.mValidationMessages.put(Integer.valueOf(((Integer)entry.getKey()).intValue() + delta), entry.getValue());
         }
      }

   }

   public void adjustValidationMessagesForRowsInserted(int startRow, int size) {
      this.adjustValidationMessagesByDelta(startRow, size);
   }

   public void adjustValidationMessagesForRowsDelete(int startRow, int size) {
      this.initValidationMessages();

      for(int i = startRow; i < startRow + size; ++i) {
         this.clearValidationMessage(Integer.valueOf(i));
      }

      this.adjustValidationMessagesByDelta(startRow, -size);
   }

   public Map<Object, Map<String, String>> getValidationMessages() {
      return this.mValidationMessages;
   }
}
