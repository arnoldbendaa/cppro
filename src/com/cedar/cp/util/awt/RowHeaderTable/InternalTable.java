// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.JMultiLineToolTip;
import com.cedar.cp.util.awt.StatusListener;
import com.cedar.cp.util.awt.RowHeaderTable.InternalTableModel;
import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTable;
import com.cedar.cp.util.xmlform.swing.GroupableTableHeader;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.table.JTableHeader;

public class InternalTable extends JTable {

   private RowHeaderTable mRowHeaderTable;
   protected StatusListener mStatusListener;


   public InternalTable(RowHeaderTable rowHeaderTable, InternalTableModel model) {
      super(model);
      this.mRowHeaderTable = rowHeaderTable;
   }

   public StatusListener getStatusListener() {
      return this.mStatusListener;
   }

   public void setStatusListener(StatusListener statusListener) {
      this.mStatusListener = statusListener;
   }

   public JToolTip createToolTip() {
      return new JMultiLineToolTip();
   }

   public RowHeaderTable getRowHeaderTable() {
      return this.mRowHeaderTable;
   }

   protected JTableHeader createDefaultTableHeader() {
      return new GroupableTableHeader(this.columnModel);
   }

   public void invokeAction(String name) {
      ActionMap map = this.getActionMap();
      Action action = null;
      if(map != null) {
         action = map.get(name);
      }

      action.actionPerformed(new ActionEvent(this, 1001, (String)action.getValue("Name"), EventQueue.getMostRecentEventTime(), this.getCurrentEventModifiers()));
   }

   private int getCurrentEventModifiers() {
      int modifiers = 0;
      AWTEvent currentEvent = EventQueue.getCurrentEvent();
      if(currentEvent instanceof InputEvent) {
         modifiers = ((InputEvent)currentEvent).getModifiers();
      } else if(currentEvent instanceof ActionEvent) {
         modifiers = ((ActionEvent)currentEvent).getModifiers();
      }

      return modifiers;
   }
}
