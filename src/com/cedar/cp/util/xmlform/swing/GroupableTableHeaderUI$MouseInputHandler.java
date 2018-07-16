// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.swing.FormTableModel;
import com.cedar.cp.util.xmlform.swing.GroupableTableHeader;
import com.cedar.cp.util.xmlform.swing.GroupableTableHeaderUI;
import com.cedar.cp.util.xmlform.swing.GroupedColumn;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.MouseInputListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class GroupableTableHeaderUI$MouseInputHandler implements MouseInputListener {

   private int mouseXOffset;
   private Cursor otherCursor;
   // $FF: synthetic field
   final GroupableTableHeaderUI this$0;


   public GroupableTableHeaderUI$MouseInputHandler(GroupableTableHeaderUI var1) {
      this.this$0 = var1;
      this.otherCursor = GroupableTableHeaderUI.accessMethod000();
   }

   public void mouseClicked(MouseEvent e) {}

   private boolean canResize(TableColumn column) {
      return column != null && GroupableTableHeaderUI.accessMethod100(this.this$0).getResizingAllowed() && column.getResizable();
   }

   private TableColumn getResizingColumn(Point p) {
      return this.getResizingColumn(p, GroupableTableHeaderUI.accessMethod200(this.this$0).columnAtPoint(p));
   }

   private TableColumn getResizingColumn(Point p, int column) {
      if(column == -1) {
         return null;
      } else {
         Rectangle r = GroupableTableHeaderUI.accessMethod300(this.this$0).getHeaderRect(column);
         r.grow(-3, 0);
         if(r.contains(p)) {
            return null;
         } else {
            int midPoint = r.x + r.width / 2;
            int columnIndex;
            if(GroupableTableHeaderUI.accessMethod400(this.this$0).getComponentOrientation().isLeftToRight()) {
               columnIndex = p.x < midPoint?column - 1:column;
            } else {
               columnIndex = p.x < midPoint?column:column - 1;
            }

            return columnIndex == -1?null:GroupableTableHeaderUI.accessMethod500(this.this$0).getColumnModel().getColumn(columnIndex);
         }
      }
   }

   public void mousePressed(MouseEvent e) {
      GroupableTableHeaderUI.accessMethod600(this.this$0).setDraggedColumn((TableColumn)null);
      GroupableTableHeaderUI.accessMethod700(this.this$0).setResizingColumn((TableColumn)null);
      GroupableTableHeaderUI.accessMethod800(this.this$0).setDraggedDistance(0);
      Point p = e.getPoint();
      TableColumnModel columnModel = GroupableTableHeaderUI.accessMethod900(this.this$0).getColumnModel();
      int index = GroupableTableHeaderUI.accessMethod1000(this.this$0).columnAtPoint(p);
      if(index != -1) {
         TableColumn resizingColumn = this.getResizingColumn(p, index);
         if(this.canResize(resizingColumn)) {
            GroupableTableHeaderUI.accessMethod1100(this.this$0).setResizingColumn(resizingColumn);
            if(GroupableTableHeaderUI.accessMethod1200(this.this$0).getComponentOrientation().isLeftToRight()) {
               this.mouseXOffset = p.x - resizingColumn.getWidth();
            } else {
               this.mouseXOffset = p.x + resizingColumn.getWidth();
            }
         } else if(GroupableTableHeaderUI.accessMethod1300(this.this$0).getReorderingAllowed()) {
            TableColumn hitColumn = columnModel.getColumn(index);
            GroupableTableHeaderUI.accessMethod1400(this.this$0).setDraggedColumn(hitColumn);
            this.mouseXOffset = p.x;
         }
      }

   }

   private void swapCursor() {
      Cursor tmp = GroupableTableHeaderUI.accessMethod1500(this.this$0).getCursor();
      GroupableTableHeaderUI.accessMethod1600(this.this$0).setCursor(this.otherCursor);
      this.otherCursor = tmp;
   }

   public void mouseMoved(MouseEvent e) {
      if(this.canResize(this.getResizingColumn(e.getPoint())) != (GroupableTableHeaderUI.accessMethod1700(this.this$0).getCursor() == GroupableTableHeaderUI.accessMethod000())) {
         this.swapCursor();
      }

   }

   public void mouseDragged(MouseEvent e) {
      int mouseX = e.getX();
      TableColumn resizingColumn = GroupableTableHeaderUI.accessMethod1800(this.this$0).getResizingColumn();
      TableColumn draggedColumn = GroupableTableHeaderUI.accessMethod1900(this.this$0).getDraggedColumn();
      boolean headerLeftToRight = GroupableTableHeaderUI.accessMethod2000(this.this$0).getComponentOrientation().isLeftToRight();
      int columnIndex;
      int newColumnIndex;
      int indexToMove;
      if(resizingColumn != null) {
         int cm = resizingColumn.getWidth();
         int tableModel;
         if(headerLeftToRight) {
            tableModel = mouseX - this.mouseXOffset;
         } else {
            tableModel = this.mouseXOffset - mouseX;
         }

         resizingColumn.setWidth(tableModel);
         Container formTableModel;
         if(GroupableTableHeaderUI.accessMethod2100(this.this$0).getParent() == null || (formTableModel = GroupableTableHeaderUI.accessMethod2200(this.this$0).getParent().getParent()) == null || !(formTableModel instanceof JScrollPane)) {
            return;
         }

         if(!formTableModel.getComponentOrientation().isLeftToRight() && !headerLeftToRight) {
            JTable draggedDistance = GroupableTableHeaderUI.accessMethod2300(this.this$0).getTable();
            if(draggedDistance != null) {
               JViewport direction = ((JScrollPane)formTableModel).getViewport();
               columnIndex = direction.getWidth();
               newColumnIndex = tableModel - cm;
               indexToMove = draggedDistance.getWidth() + newColumnIndex;
               Dimension width = draggedDistance.getSize();
               width.width += newColumnIndex;
               draggedDistance.setSize(width);
               if(indexToMove >= columnIndex && draggedDistance.getAutoResizeMode() == 0) {
                  Point p = direction.getViewPosition();
                  p.x = Math.max(0, Math.min(indexToMove - columnIndex, p.x + newColumnIndex));
                  direction.setViewPosition(p);
                  this.mouseXOffset += newColumnIndex;
               }
            }
         }
      } else if(draggedColumn != null) {
         TableColumnModel cm1 = GroupableTableHeaderUI.accessMethod2400(this.this$0).getColumnModel();
         TableModel tableModel1 = GroupableTableHeaderUI.accessMethod2500(this.this$0).getTable().getModel();
         FormTableModel formTableModel1 = null;
         if(tableModel1 instanceof FormTableModel) {
            formTableModel1 = (FormTableModel)tableModel1;
         }

         int draggedDistance1 = mouseX - this.mouseXOffset;
         int direction1 = draggedDistance1 < 0?-1:1;
         columnIndex = GroupableTableHeaderUI.accessMethod2600(this.this$0, draggedColumn);
         newColumnIndex = columnIndex + (headerLeftToRight?direction1:-direction1);
         if(0 <= newColumnIndex && newColumnIndex < cm1.getColumnCount()) {
            indexToMove = this.getColIndexToMove(columnIndex, newColumnIndex, direction1, headerLeftToRight);
            int width1 = cm1.getColumn(newColumnIndex).getWidth();
            if(Math.abs(draggedDistance1) > width1 / 2 && indexToMove != columnIndex) {
               this.mouseXOffset += direction1 * width1;
               GroupableTableHeaderUI.accessMethod2700(this.this$0).setDraggedDistance(draggedDistance1 - direction1 * width1);
               cm1.moveColumn(columnIndex, indexToMove);
               if(formTableModel1 != null) {
                  formTableModel1.moveColumn(columnIndex, indexToMove);
               }

               return;
            }
         }

         this.setDraggedDistance(draggedDistance1, columnIndex);
      }

   }

   private int getColIndexToMove(int colIndex, int newColIndex, int direction, boolean headerLeftToRight) {
      TableModel tableModel = GroupableTableHeaderUI.accessMethod2800(this.this$0).getTable().getModel();
      TableColumnModel colModel = GroupableTableHeaderUI.accessMethod2900(this.this$0).getColumnModel();
      if(colIndex >= 0 && newColIndex >= 0 && newColIndex <= colModel.getColumnCount()) {
         TableColumn newCol = colModel.getColumn(newColIndex);
         TableColumn col = colModel.getColumn(colIndex);
         GroupableTableHeader groupableHeader = (GroupableTableHeader)GroupableTableHeaderUI.accessMethod3000(this.this$0);
         Enumeration enumer = groupableHeader.getColumnGroupsEnumeration(col);
         Enumeration enumerOfNewCol = groupableHeader.getColumnGroupsEnumeration(newCol);
         if(tableModel instanceof FormTableModel) {
            FormTableModel topGroupOfCol = (FormTableModel)tableModel;
            Column topGroupOfNewCol = topGroupOfCol.getVisibleColumn(colIndex);
            Column realGroup1 = topGroupOfCol.getVisibleColumn(newColIndex);
            if(topGroupOfNewCol.isTypeTree() || realGroup1.isTypeTree()) {
               return colIndex;
            }
         }

         if(enumer == null && enumerOfNewCol == null) {
            return newColIndex;
         } else {
            GroupedColumn topGroupOfCol1;
            if(enumer == null && enumerOfNewCol != null) {
               topGroupOfCol1 = (GroupedColumn)enumerOfNewCol.nextElement();
               return this.getIndexOverGroup((GroupedColumn)null, topGroupOfCol1, direction);
            } else if(enumer != null && enumerOfNewCol == null) {
               return colIndex;
            } else if(enumer != null && enumerOfNewCol != null) {
               topGroupOfCol1 = (GroupedColumn)enumer.nextElement();
               GroupedColumn topGroupOfNewCol1 = (GroupedColumn)enumerOfNewCol.nextElement();
               GroupedColumn realGroup11 = this.getParentGroupedColumn(col, topGroupOfCol1);
               GroupedColumn realGroup2 = this.getParentGroupedColumn(newCol, topGroupOfNewCol1);
               return realGroup11 != null && realGroup2 != null && realGroup11.getHeaderValue().equals(realGroup2.getHeaderValue())?newColIndex:this.getIndexOverGroup(realGroup11, realGroup2, direction);
            } else {
               return colIndex;
            }
         }
      } else {
         return colIndex;
      }
   }

   private GroupedColumn getParentGroupedColumn(Object object, GroupedColumn groupedColumn) {
      if(groupedColumn == null) {
         return null;
      } else if(groupedColumn.mElements.indexOf(object) > -1) {
         return groupedColumn;
      } else {
         Vector vector = groupedColumn.getColumnGroups();
         if(vector != null && vector.size() > 0) {
            Enumeration enumer = vector.elements();

            while(enumer.hasMoreElements()) {
               Object obj = enumer.nextElement();
               GroupedColumn gcol = null;
               if(obj instanceof GroupedColumn) {
                  gcol = this.getParentGroupedColumn(object, (GroupedColumn)obj);
               }

               if(gcol != null) {
                  return gcol;
               }
            }
         }

         return null;
      }
   }

   private int getIndexOverGroup(GroupedColumn group1, GroupedColumn group2, int direction) {
      Object obj = group2.mElements.get(0);
      byte index = -1;
      if(obj != null) {
         int var6;
         if(direction > -1) {
            var6 = group2.getEndCol();
            return var6++;
         } else {
            var6 = group2.getStartCol();
            return var6 == 0?0:var6--;
         }
      } else {
         GroupableTableHeaderUI.accessMethod3100(this.this$0).debug(String.valueOf(index));
         return index;
      }
   }

   public void mouseReleased(MouseEvent e) {
      this.setDraggedDistance(0, GroupableTableHeaderUI.accessMethod2600(this.this$0, GroupableTableHeaderUI.accessMethod3200(this.this$0).getDraggedColumn()));
      GroupableTableHeaderUI.accessMethod3300(this.this$0).setResizingColumn((TableColumn)null);
      GroupableTableHeaderUI.accessMethod3400(this.this$0).setDraggedColumn((TableColumn)null);
      TableModel tableModel = GroupableTableHeaderUI.accessMethod3500(this.this$0).getTable().getModel();
      if(tableModel != null && tableModel instanceof FormTableModel) {
         FormTableModel formTableModel = (FormTableModel)tableModel;
         if(formTableModel.isModified()) {
            formTableModel.setModified(false);
            formTableModel.getFormConfig().formConfigChanged();
         }
      }

   }

   public void mouseEntered(MouseEvent e) {}

   public void mouseExited(MouseEvent e) {}

   private void setDraggedDistance(int draggedDistance, int column) {
      GroupableTableHeaderUI.accessMethod3600(this.this$0).setDraggedDistance(draggedDistance);
      if(column != -1) {
         GroupableTableHeaderUI.accessMethod3700(this.this$0).getColumnModel().moveColumn(column, column);
      }

   }
}
