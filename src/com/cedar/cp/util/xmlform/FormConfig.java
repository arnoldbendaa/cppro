// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.xmlform.Body;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.ColumnGroup;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.cedar.cp.util.xmlform.Footer;
import com.cedar.cp.util.xmlform.FormConfigListener;
import com.cedar.cp.util.xmlform.FormContext;
import com.cedar.cp.util.xmlform.Header;
import com.cedar.cp.util.xmlform.InputColumnValue;
import com.cedar.cp.util.xmlform.LookupInput;
import com.cedar.cp.util.xmlform.Row;
import com.cedar.cp.util.xmlform.RowInput;
import com.cedar.cp.util.xmlform.XMLWritable;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import com.cedar.cp.util.xmlform.inputs.LookupData;
import com.cedar.cp.util.xmlform.inputs.LookupTarget;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class FormConfig extends DefaultMutableTreeNode {

   public static final int TYPE_TABLE = 0;
   public static final int TYPE_FIXED = 1;
   public static final int TYPE_DYNAMIC = 2;
   public static final int TYPE_FINANCE = 3;
   private String mTitle;
   private int mType;
   private Body mBody;
   private Header mHeader;
   private Footer mFooter;
   private List mInputs = new ArrayList();
   private DefaultMutableTreeNode mInputsNode;
   private Map mInputVariables;
   private Map mVariables = new HashMap();
   private String mFinanceCubeVisId;
   private List modelListeners = new ArrayList();
   private boolean mIsModified = false;


   public FormConfig() {
      this.add(this.mInputsNode = new DefaultMutableTreeNode("Inputs"));
   }

   public String getTitle() {
      return this.mTitle;
   }

   public void setTitle(String title) {
      this.mTitle = title;
      this.setUserObject(title);
   }

   public int getType() {
      return this.mType;
   }

   public void setType(int type) {
      this.mType = type;
   }

   public void setType(String type) {
      if("fixed".equals(type)) {
         this.mType = 1;
      } else if("dynamic".equals(type)) {
         this.mType = 2;
      } else if("finance".equals(type)) {
         this.mType = 3;
      }

   }

   public void setBody(Body body) {
      this.mBody = body;
      this.insert(body, 2);
   }

   public Body getBody() {
      return this.mBody;
   }

   public Header getHeader() {
      return this.mHeader;
   }

   public void setHeader(Header header) {
      this.mHeader = header;
      this.insert(header, 1);
   }

   public Footer getFooter() {
      return this.mFooter;
   }

   public void setFooter(Footer footer) {
      this.mFooter = footer;
      this.insert(footer, 3);
   }

   public void addRowInput(RowInput input) {
      this.mInputs.add(input);
      this.mInputsNode.add(input);
   }

   public void addLookupInput(LookupInput input) {
      this.mInputs.add(input);
      this.mInputsNode.add(input);
   }

   public void addFinanceCubeInput(FinanceCubeInput input) {
      this.mInputs.add(input);
      this.mInputsNode.add(input);
   }

   public Iterator getInputs() {
      return this.mInputs.iterator();
   }

   public int getInputsCount() {
      return this.mInputs.size();
   }

   private boolean isLookupNeeded(LookupInput lookup) {
      Iterator i$ = this.mVariables.keySet().iterator();

      Object key;
      Object o;
      do {
         if(!i$.hasNext()) {
            return true;
         }

         key = i$.next();
         o = this.mVariables.get(key);
      } while(!(o instanceof LookupTarget) || !key.equals(lookup.getId()));

      return false;
   }

   public void buildVariables(PerformanceDatumImpl perf, FormContext cnx) throws Exception {
      this.mVariables.clear();
      this.mInputVariables = new HashMap();
      ArrayList reqdLookups = new ArrayList();
      Iterator iter = this.mInputs.iterator();

      Object input;
      while(iter.hasNext()) {
         input = iter.next();
         if(input instanceof LookupInput && this.isLookupNeeded((LookupInput)input)) {
            reqdLookups.add((LookupInput)input);
         }
      }

      if(reqdLookups.size() > 0) {
         Map iter1 = cnx.getLookupInputs(perf, reqdLookups);
         this.mInputVariables.putAll(iter1);
         Iterator input1 = iter1.keySet().iterator();

         while(input1.hasNext()) {
            Object financeInput = input1.next();
            Object model = iter1.get(financeInput);
            if(model instanceof LookupData && cnx.getVariables().get(financeInput) == null) {
               cnx.getVariables().put(financeInput, model);
            }
         }
      }

      iter = this.mInputs.iterator();

      while(iter.hasNext()) {
         input = iter.next();
         if(input instanceof RowInput) {
            RowInput financeInput2 = (RowInput)input;
            FormInputModel model1 = cnx.getFormInputModel(perf, financeInput2);
            this.mInputVariables.put(financeInput2.getId(), model1);
         } else if(input instanceof FinanceCubeInput) {
            FinanceCubeInput financeInput1 = (FinanceCubeInput)input;
            financeInput1.setParent((MutableTreeNode)null);
            this.mInputVariables.put("cedar.financeCubeInput.key", financeInput1);
            this.mFinanceCubeVisId = financeInput1.getCubeVisId();
         }
      }

      this.mVariables.putAll(this.mInputVariables);
      this.mVariables.putAll(cnx.getVariables());
   }

   public Map getVariables() {
      return this.mVariables;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }

   public void moveColumn(Column movingCol, Column colToReplace) {
      int movingRealIndex = this.getRealIndex(movingCol);
      int replaceRealIndex = this.getReplaceColIndexInRowInput(movingCol, colToReplace);
      this.mBody.moveColumn(movingCol, colToReplace);
      this.moveColumnsInRows(movingRealIndex, replaceRealIndex);
      this.mIsModified = true;
   }

   public boolean isModified() {
      return this.mIsModified;
   }

   private int getReplaceColIndexInRowInput(Column movingCol, Column replaceCol) {
      int movingRealIndex = this.getRealColIndex(movingCol);
      int replaceRealIndex = this.getRealColIndex(replaceCol);
      TreeNode movingColParentNode = movingCol.getParent();
      TreeNode colToReplaceParentNode = replaceCol.getParent();
      if(!movingColParentNode.equals(colToReplaceParentNode)) {
         int colIndexInGroup = colToReplaceParentNode.getIndex(replaceCol);
         if(movingRealIndex < replaceRealIndex) {
            if(replaceRealIndex < colToReplaceParentNode.getChildCount() - 1) {
               replaceRealIndex = colToReplaceParentNode.getChildCount() - 1;
            }
         } else if(colIndexInGroup > 0) {
            TreeNode node = colToReplaceParentNode.getChildAt(0);
            replaceRealIndex = this.getRealIndex(node);
         }
      }

      return replaceRealIndex;
   }

   private int recursiveGetRealIndex(Column col, ColumnGroup colGroup, int index, List trickyList) {
      if(col != null && colGroup != null) {
         List cols = colGroup.getColumns();
         Iterator iter = cols.iterator();

         while(iter.hasNext()) {
            Object object = iter.next();
            if(object instanceof ColumnGroup) {
               ColumnGroup var9 = (ColumnGroup)object;
               index = this.recursiveGetRealIndex(col, var9, index, trickyList);
            } else {
               Column column = (Column)object;
               ++index;
               if(column.getId().equals(col.getId())) {
                  trickyList.add(col);
                  break;
               }
            }
         }
      }

      return index;
   }

   public int getRealIndex(TreeNode node) {
      byte index = -1;
      ArrayList trickyList = new ArrayList(1);
      return this.recursiveGetNodeIndex(node, this.mBody, index, trickyList);
   }

   private int recursiveGetNodeIndex(TreeNode node, TreeNode parentNode, int index, List trickyList) {
      if(node != null && parentNode != null && trickyList != null) {
         Enumeration enumer = parentNode.children();

         while(enumer.hasMoreElements()) {
            TreeNode childNode = (TreeNode)enumer.nextElement();
            if(childNode.children().hasMoreElements()) {
               index = this.recursiveGetNodeIndex(node, childNode, index, trickyList);
            } else {
               ++index;
               if(childNode.equals(node)) {
                  trickyList.add(node);
               }
            }

            if(trickyList != null && trickyList.size() > 0) {
               break;
            }
         }
      }

      return index;
   }

   private int getRealColIndex(Column col) {
      List colList = this.mBody.getColumns();
      ArrayList trickyList = new ArrayList(1);
      int index = -1;
      if(colList != null) {
         Iterator iter = colList.iterator();

         while(iter.hasNext()) {
            Object object = iter.next();
            if(object instanceof ColumnGroup) {
               ColumnGroup var8 = (ColumnGroup)object;
               index = this.recursiveGetRealIndex(col, var8, index, trickyList);
               if(trickyList.size() > 0 && trickyList.get(0) != null) {
                  break;
               }
            } else {
               Column column = (Column)object;
               ++index;
               if(column.getId().equals(col.getId())) {
                  break;
               }
            }
         }
      }

      return index;
   }

   private int getColIndexWithGroup(Column col) {
      boolean index = true;
      TreeNode parentNodeOfCol = col.getParent();
      int index1;
      if(parentNodeOfCol instanceof ColumnGroup) {
         ColumnGroup oldColGroup = (ColumnGroup)parentNodeOfCol;
         index1 = this.mBody.getColumns().indexOf(oldColGroup);
         if(index1 > -1) {
            ;
         }
      } else {
         index1 = this.mBody.getColumns().indexOf(col);
      }

      return index1;
   }

   private void moveColumnsInRows(int colIndex, int newColIndex) {
      if(this.mInputs != null && this.mInputs.size() > 0) {
         for(int i = 0; i < this.mInputs.size(); ++i) {
            Object obj = this.mInputs.get(i);
            if(obj instanceof RowInput) {
               RowInput rowInput = (RowInput)obj;
               List list = rowInput.getOriginalRows();
               if(list != null && list.size() > 0) {
                  for(int j = 0; j < list.size(); ++j) {
                     Row row = (Row)list.get(j);
                     List columnValues = row.getInputColumnValues();
                     if(columnValues != null && columnValues.size() > 0) {
                        InputColumnValue inputColValue = (InputColumnValue)columnValues.get(colIndex);
                        row.removeInputColumnValueAtIndex(colIndex);
                        row.addInputColumnValueAtIndex(newColIndex, inputColValue);
                     }
                  }
               }
            }
         }
      }

   }

   public void addFormConfigListener(FormConfigListener listener) {
      this.modelListeners.add(listener);
   }

   public void formConfigChanged() {
      if(this.modelListeners != null && this.modelListeners.size() > 0) {
         for(int i = 0; i < this.modelListeners.size(); ++i) {
            FormConfigListener listener = (FormConfigListener)this.modelListeners.get(i);
            listener.formConfigChanged();
         }
      }

   }

   public Column getColumnById(String columnId) {
      Iterator i$ = this.mBody.getColumns().iterator();

      while(i$.hasNext()) {
         Object o = i$.next();
         Column column;
         if(o instanceof Column) {
            column = (Column)o;
            if(column.getId().equals(columnId)) {
               return column;
            }
         } else if(o instanceof ColumnGroup) {
            column = ((ColumnGroup)o).getColumnById(columnId);
            if(column != null) {
               return column;
            }
         }
      }

      return null;
   }

   public ColumnGroup getColumnGroup(String columnId) {
      Column column = this.getColumnById(columnId);
      if(column != null) {
         Iterator i$ = this.mBody.getColumns().iterator();

         while(i$.hasNext()) {
            Object o = i$.next();
            if(o instanceof ColumnGroup) {
               ColumnGroup columnGroup = (ColumnGroup)o;
               if(columnGroup.getColumns().contains(column)) {
                  return columnGroup;
               }
            }
         }
      }

      return null;
   }

   public void writeSchemaXml(Writer out, String schema) throws IOException {
      out.write("<cp-form " + schema + " title=\"");
      out.write(XmlUtils.escapeStringForXML(this.mTitle) + "\" type=\"" + this.mType + "\">");
      out.write("<inputs>");
      Iterator iter = this.mInputs.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</inputs>");
      this.mHeader.writeXml(out);
      this.mBody.writeXml(out);
      this.mFooter.writeXml(out);
      out.write("</cp-form>");
   }
}
