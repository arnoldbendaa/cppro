// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.AutoPopulate;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.ColumnGroup;
import com.cedar.cp.util.xmlform.RowHeader;
import com.cedar.cp.util.xmlform.Totals;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class Body extends DefaultMutableTreeNode implements XMLWritable {

   private String mInput;
   private String mDefaultFormat;
   private List mColumns = new ArrayList();
   private RowHeader mRowHeader;
   private AutoPopulate mAutoPopulate;
   private Totals mTotals;
   private int mGradientDepth = -1;
   private String mGradientColor;
   private String mEditBackground;
   private String mEditForeground;
   private String mModifiedBackground;
   private String mModifiedForeground;
   public static final int LEVEL_OF_COLUMN_IN_BODY = 2;


   public Body() {
      this.setUserObject("Body");
   }

   public String getInput() {
      return this.mInput;
   }

   public void setInput(String input) {
      this.mInput = input;
      this.setUserObject("Body input=" + input);
   }

   public String getDefaultFormat() {
      return this.mDefaultFormat == null?"":this.mDefaultFormat;
   }

   public void setDefaultFormat(String format) {
      this.mDefaultFormat = format;
   }

   public void addColumn(Column column) {
      if(this.mRowHeader == null && column.isTypeTree()) {
         this.setRowHeader(new RowHeader());
         this.mRowHeader.addColumn(column);
      } else {
         this.mColumns.add(column);
         if(this.mTotals == null) {
            super.add(column);
         } else {
            super.insert(column, this.getChildCount() - 1);
         }
      }

   }

   public void addColumnGroup(ColumnGroup group) {
      this.mColumns.add(group);
      if(this.mTotals == null) {
         super.add(group);
      } else {
         super.insert(group, this.getChildCount() - 1);
      }

   }

   public List getColumns() {
      ArrayList temp = new ArrayList();
      if(this.mRowHeader != null) {
         temp.addAll(this.mRowHeader.getColumns());
      }

      if(this.mAutoPopulate != null) {
         temp.add(this.mAutoPopulate);
      }

      temp.addAll(this.mColumns);
      return temp;
   }

   public void removeColumn(Column col) {
      this.mColumns.remove(col);
      super.remove(col);
   }

   public void removeColumnGroup(ColumnGroup group) {
      this.mColumns.remove(group);
      super.remove(group);
   }

   public int getIndexOfColumn(Column col) {
      int index = this.recursivelySearchForColumn(this.getColumns(), col, 0);
      if(index >= 0) {
         throw new IllegalStateException("Column " + col + " not found");
      } else {
         return -index;
      }
   }

   public RowHeader getRowHeader() {
      return this.mRowHeader;
   }

   public void setRowHeader(RowHeader rowHeader) {
      this.mRowHeader = rowHeader;
      super.add(rowHeader);
   }

   public AutoPopulate getAutoPopulate() {
      return this.mAutoPopulate;
   }

   public void setAutoPopulate(AutoPopulate autoPopulate) {
      if(this.mAutoPopulate != null) {
         super.remove(this.mAutoPopulate);
      }

      this.mAutoPopulate = autoPopulate;
      if(this.mAutoPopulate != null) {
         this.insert(this.mAutoPopulate, 0);
      }

   }

   public Totals getTotals() {
      return this.mTotals;
   }

   public void setTotals(Totals totals) {
      this.mTotals = totals;
      super.add(totals);
   }

   public void removeTotals() {
      if(this.mTotals != null) {
         super.remove(this.mTotals);
      }

      this.mTotals = null;
   }

   private int recursivelySearchForColumn(List columns, Column col, int index) {
      Iterator iter = columns.iterator();

      while(iter.hasNext()) {
         Object o = iter.next();
         if(col.equals(o)) {
            return -index;
         }

         if(o instanceof Column) {
            ++index;
         } else if(o instanceof ColumnGroup) {
            ColumnGroup grp = (ColumnGroup)o;
            int r = this.recursivelySearchForColumn(grp.getColumns(), col, index);
            if(r < 0) {
               return r;
            }
         }
      }

      return index;
   }

   public int getGradientDepth() {
      return this.mGradientDepth;
   }

   public void setGradientDepth(int gradientDepth) {
      this.mGradientDepth = gradientDepth;
   }

   public String getGradientColor() {
      return this.mGradientColor;
   }

   public void setGradientColor(String gradientColor) {
      this.mGradientColor = gradientColor;
   }

   public String getEditBackground() {
      return this.mEditBackground;
   }

   public void setEditBackground(String editBackground) {
      this.mEditBackground = editBackground;
   }

   public String getEditForeground() {
      return this.mEditForeground;
   }

   public void setEditForeground(String editForeground) {
      this.mEditForeground = editForeground;
   }

   public String getModifiedBackground() {
      return this.mModifiedBackground;
   }

   public void setModifiedBackground(String modifiedBackground) {
      this.mModifiedBackground = modifiedBackground;
   }

   public String getModifiedForeground() {
      return this.mModifiedForeground;
   }

   public void setModifiedForeground(String modifiedForeground) {
      this.mModifiedForeground = modifiedForeground;
   }

   public void moveColumn(Column movingCol, Column replaceCol) {
      if(movingCol != null && replaceCol != null) {
         DefaultMutableTreeNode parentNode1 = (DefaultMutableTreeNode)movingCol.getParent();
         DefaultMutableTreeNode parentNode2 = (DefaultMutableTreeNode)replaceCol.getParent();
         int indexOfParentGroup2;
         int colGroup;
         if(movingCol.getLevel() == 2) {
            indexOfParentGroup2 = this.mColumns.indexOf(movingCol);
            if(parentNode2 instanceof Body) {
               colGroup = this.getRealIndexInBodyColumn(replaceCol);
               this.removeColumnAtIndex(indexOfParentGroup2);
               this.addColumnAtIndex(colGroup, movingCol);
            } else if(parentNode2 instanceof RowHeader) {
               colGroup = this.mRowHeader.getColumns().indexOf(replaceCol);
               this.removeColumnAtIndex(indexOfParentGroup2);
               this.mRowHeader.addColumnAtIndex(colGroup, movingCol);
            } else {
               colGroup = this.getRealIndexInBodyColumn(replaceCol);
               this.removeColumn(movingCol);
               this.addColumnAtIndex(colGroup, movingCol);
            }
         } else if(movingCol.getLevel() > 2) {
            if(parentNode1.equals(parentNode2)) {
               parentNode1.getIndex(movingCol);
               colGroup = parentNode1.getIndex(replaceCol);
               if(parentNode1 instanceof RowHeader) {
                  this.mRowHeader.removeColumn(movingCol);
                  this.mRowHeader.addColumnAtIndex(colGroup, movingCol);
               } else {
                  ColumnGroup columnGroup1 = (ColumnGroup)movingCol.getParent();
                  columnGroup1.removeColumn(movingCol);
                  columnGroup1.addColumnAtIndex(colGroup, movingCol);
               }
            } else if(parentNode1 instanceof RowHeader) {
               this.mRowHeader.removeColumn(movingCol);
               indexOfParentGroup2 = this.mColumns.indexOf(replaceCol);
               this.addColumnAtIndex(indexOfParentGroup2, movingCol);
            } else if(parentNode2 instanceof RowHeader) {
               this.removeColumn(movingCol);
               indexOfParentGroup2 = this.mRowHeader.getColumns().indexOf(replaceCol);
               this.mRowHeader.addColumnAtIndex(indexOfParentGroup2, movingCol);
            } else if(parentNode2.getLevel() > parentNode1.getLevel()) {
               indexOfParentGroup2 = this.getRealIndexInWithinGroup(replaceCol, (ColumnGroup)parentNode1);
               ColumnGroup colGroup1 = (ColumnGroup)parentNode1;
               colGroup1.removeColumn(movingCol);
               colGroup1.addColumnAtIndex(indexOfParentGroup2, movingCol);
            }
         }
      }

   }

   private int getRealIndexInBodyColumn(Column childCol) {
      int childColLevel = childCol.getLevel();
      if(childColLevel > 2) {
         int moveUpwardCount = childColLevel - 2;

         TreeNode parentNode;
         for(parentNode = null; moveUpwardCount > 0; --moveUpwardCount) {
            if(parentNode == null) {
               parentNode = childCol.getParent();
            } else {
               parentNode = parentNode.getParent();
            }
         }

         return this.mColumns.indexOf(parentNode);
      } else {
         return this.mColumns.indexOf(childCol);
      }
   }

   private int getRealIndexInWithinGroup(Column childCol, ColumnGroup groupToFind) {
      DefaultMutableTreeNode parentNodeOfChildCol = (DefaultMutableTreeNode)childCol.getParent();
      int levelOfParentGroup = parentNodeOfChildCol.getLevel();
      int groupToFindLevel = groupToFind.getLevel();
      if(levelOfParentGroup > groupToFindLevel) {
         int moveUpwardCount = levelOfParentGroup - groupToFindLevel;

         TreeNode parentNode;
         for(parentNode = null; moveUpwardCount > 0; --moveUpwardCount) {
            if(parentNode == null) {
               parentNode = childCol.getParent();
            } else {
               parentNode = parentNode.getParent();
            }
         }

         return groupToFind.getColumns().indexOf(parentNode);
      } else {
         return groupToFind.getColumns().indexOf(childCol);
      }
   }

   public void removeColumnAtIndex(int index) {
      Object obj = this.mColumns.get(index);
      if(obj != null && obj instanceof DefaultMutableTreeNode) {
         this.mColumns.remove(index);
         this.remove((DefaultMutableTreeNode)obj);
      }

   }

   public void addColumnAtIndex(int index, Column column) {
      this.mColumns.add(index, column);
      if(this.mRowHeader != null) {
         ++index;
      }

      if(this.mTotals == null) {
         this.insert(column, index);
      } else if(index < this.getChildCount()) {
         super.insert(column, index);
      } else {
         super.insert(column, this.getChildCount() - 1);
      }

   }

   public void writeXml(Writer out) throws IOException {
      out.write("<body ");
      if(this.mInput != null) {
         out.write(" input=\"" + this.mInput + "\"");
      }

      if(this.mDefaultFormat != null && this.mDefaultFormat.trim().length() > 0) {
         out.write(" defaultFormat=\"" + XmlUtils.escapeStringForXML(this.mDefaultFormat) + "\"");
      }

      if(this.mGradientDepth >= 0) {
         out.write(" gradientDepth=\"" + this.mGradientDepth + "\"");
      }

      if(this.mGradientColor != null && this.mGradientColor.trim().length() > 0) {
         out.write(" gradientColor=\"" + XmlUtils.escapeStringForXML(this.mGradientColor) + "\"");
      }

      if(this.mEditBackground != null && this.mEditBackground.trim().length() > 0) {
         out.write(" editBackground=\"" + XmlUtils.escapeStringForXML(this.mEditBackground) + "\"");
      }

      if(this.mEditForeground != null && this.mEditForeground.trim().length() > 0) {
         out.write(" editForeground=\"" + XmlUtils.escapeStringForXML(this.mEditForeground) + "\"");
      }

      if(this.mModifiedBackground != null && this.mModifiedBackground.trim().length() > 0) {
         out.write(" modifiedBackground=\"" + XmlUtils.escapeStringForXML(this.mModifiedBackground) + "\"");
      }

      if(this.mModifiedForeground != null && this.mModifiedForeground.trim().length() > 0) {
         out.write(" modifiedForeground=\"" + XmlUtils.escapeStringForXML(this.mModifiedForeground) + "\"");
      }

      out.write(">");
      if(this.mRowHeader != null) {
         this.mRowHeader.writeXml(out);
      }

      if(this.mAutoPopulate != null) {
         this.mAutoPopulate.writeXml(out);
      }

      Iterator iter = this.mColumns.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      if(this.mTotals != null) {
         this.mTotals.writeXml(out);
      }

      out.write("</body>");
   }
}
