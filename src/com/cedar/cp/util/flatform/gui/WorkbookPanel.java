// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.Pair;
import com.cedar.cp.util.flatform.gui.CellSelectionEvent;
import com.cedar.cp.util.flatform.gui.PropertiesWorksheetsDialog;
import com.cedar.cp.util.flatform.gui.ReorderWorksheetsDialog;
import com.cedar.cp.util.flatform.gui.WorkbookPanel$1;
import com.cedar.cp.util.flatform.gui.WorkbookPanel$2;
import com.cedar.cp.util.flatform.gui.WorkbookPanel$3;
import com.cedar.cp.util.flatform.gui.WorkbookPanel$4;
import com.cedar.cp.util.flatform.gui.WorkbookPanel$5;
import com.cedar.cp.util.flatform.gui.WorkbookPanel$WorkbookTabPaneUI;
import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.model.CellSelectionListener;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.WorkbookListener;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.event.WorkbookEvent;
import com.cedar.cp.util.flatform.model.event.WorkbookReorderWorksheetsEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetFormatsSelectionEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetRenamedEvent;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.TabbedPaneUI;

public class WorkbookPanel extends JTabbedPane implements WorkbookListener {

   protected EventListenerList mCellSelectionListeners = new EventListenerList();
   private AbstractAction mNewWorksheetAction;
   private AbstractAction mRemoveWorksheetAction;
   private AbstractAction mRenameWorksheetAction;
   private AbstractAction mReorderWorksheetsAction;
   private CellSelectionListener mChainCellSelectionListener;
   private Workbook mWorkbook;
   private WorkbookPanel$WorkbookTabPaneUI mPaneUI = new WorkbookPanel$WorkbookTabPaneUI(this);
   private List<Pair<String, WorksheetPanel>> mWorksheetPanels = new ArrayList();
   private boolean mShowHeaders = true;


   public WorkbookPanel() {
      super.setUI(this.mPaneUI);
      this.mChainCellSelectionListener = new WorkbookPanel$1(this);
      this.setTabPlacement(3);
   }

   public void setUI(TabbedPaneUI ui) {}

   public boolean isShowHeaders() {
      return this.mShowHeaders;
   }

   public void setShowHeaders(boolean showHeaders) {
      this.mShowHeaders = showHeaders;
   }

   public Workbook getWorkbook() {
      return this.mWorkbook;
   }

   public void setWorkbook(Workbook workbook) {
      Iterator i$;
      Worksheet worksheet;
      if(this.mWorkbook != null) {
         i$ = this.mWorkbook.getWorksheets().iterator();

         while(i$.hasNext()) {
            worksheet = (Worksheet)i$.next();
            WorksheetPanel worksheetPanel = this.getWorksheetPanel(worksheet.getName());
            this.removeWorksheetPanel(worksheetPanel.getWorksheet().getName());
            worksheetPanel.setCellPickerActionListener((ActionListener)null);
         }

         this.mWorkbook.removeWorkbookListener(this);
      }

      this.mWorkbook = workbook;
      i$ = this.mWorkbook.getWorksheets().iterator();

      while(i$.hasNext()) {
         worksheet = (Worksheet)i$.next();
         this.addWorksheetPanel(new WorksheetPanel(worksheet, this.mChainCellSelectionListener, this.mShowHeaders));
      }

      this.mWorkbook.addWorkbookListener(this);
      this.revalidate();
   }

   private void handleNewWorksheet() {
      while(true) {
         String newSheetName = "New Sheet";
         newSheetName = (String)JOptionPane.showInputDialog(this, "Enter name of sheet", "New sheet", -1, (Icon)null, (Object[])null, newSheetName);
         if(newSheetName != null) {
            if(newSheetName.trim().length() == 0) {
               JOptionPane.showMessageDialog(this, "A sheet must have a name with one or more non whitespace characters.");
               continue;
            }

            if(this.getWorkbook().getWorksheet(newSheetName) != null) {
               JOptionPane.showMessageDialog(this, "A sheet of this name is already present in the workbook. Please select another name.");
               continue;
            }

            this.getWorkbook().newWorksheet(newSheetName);
         }

         return;
      }
   }

   private void handleRemoveWorksheet() {
      int tabIndex = this.getPopupTabIndex();
      WorksheetPanel worksheetPanel = this.getWorksheetPanel(tabIndex);
      if(worksheetPanel != null) {
         Worksheet worksheet = worksheetPanel.getWorksheet();
         if(JOptionPane.showConfirmDialog(this, "Are you sure you wish to remove sheet \'" + worksheet.getName() + "\' ?", "Remove Sheet", 2, 3) == 0) {
            this.getWorkbook().removeWorksheet(worksheet.getName());
         }
      }

   }

   private void handleRenameWorksheet() {
      int tabIndex = this.getPopupTabIndex();
      WorksheetPanel worksheetPanel = this.getWorksheetPanel(tabIndex);
      if(worksheetPanel != null) {
         Worksheet worksheet = worksheetPanel.getWorksheet();
         Component parent = SwingUtilities.getRoot(this);
         PropertiesWorksheetsDialog dialog = null;
         if(parent instanceof JFrame) {
            dialog = new PropertiesWorksheetsDialog((JFrame)parent, this.getWorkbook(), worksheet);
         } else {
            if(!(parent instanceof JDialog)) {
               return;
            }

            dialog = new PropertiesWorksheetsDialog((JDialog)parent, this.getWorkbook(), worksheet);
         }

         dialog.doModal();
      }
   }

   private void handleReorderWorksheets() {
      Component parent = SwingUtilities.getRoot(this);
      ReorderWorksheetsDialog dialog = null;
      if(parent instanceof JFrame) {
         dialog = new ReorderWorksheetsDialog((JFrame)parent, this.getWorkbook());
      } else {
         if(!(parent instanceof JDialog)) {
            return;
         }

         dialog = new ReorderWorksheetsDialog((JDialog)parent, this.getWorkbook());
      }

      dialog.doModal();
   }

   public AbstractAction getNewWorksheetAction() {
      if(this.mNewWorksheetAction == null) {
         this.mNewWorksheetAction = new WorkbookPanel$2(this, "New...");
      }

      return this.mNewWorksheetAction;
   }

   public AbstractAction getRemoveWorksheetAction() {
      if(this.mRemoveWorksheetAction == null) {
         this.mRemoveWorksheetAction = new WorkbookPanel$3(this, "Remove...");
      }

      return this.mRemoveWorksheetAction;
   }

   public AbstractAction getRenameWorksheetAction() {
      if(this.mRenameWorksheetAction == null) {
         this.mRenameWorksheetAction = new WorkbookPanel$4(this, "Properties...");
      }

      return this.mRenameWorksheetAction;
   }

   public AbstractAction getReorderWorksheetsAction() {
      if(this.mReorderWorksheetsAction == null) {
         this.mReorderWorksheetsAction = new WorkbookPanel$5(this, "Reorder...");
      }

      return this.mReorderWorksheetsAction;
   }

   public int getPopupTabIndex() {
      return ((WorkbookPanel$WorkbookTabPaneUI)this.getUI()).getPopupTabIndex();
   }

   public void addCellSelectionListener(CellSelectionListener listener) {
      this.mCellSelectionListeners.add(CellSelectionListener.class, listener);
   }

   public void removeCellSelectionListener(CellSelectionListener listener) {
      this.mCellSelectionListeners.remove(CellSelectionListener.class, listener);
   }

   protected void fireCellSelection(CellSelectionEvent cellSelectionEvent) {
      Object[] listeners = this.mCellSelectionListeners.getListenerList();
      int numListeners = listeners.length;

      for(int i = 0; i < numListeners; i += 2) {
         if(listeners[i] == CellSelectionListener.class) {
            ((CellSelectionListener)listeners[i + 1]).cellSelectionChanged(cellSelectionEvent);
         }
      }

   }

   public void worksheetAdded(WorkbookEvent event) {
      this.addWorksheetPanel(new WorksheetPanel(event.getWorksheet(), this.mChainCellSelectionListener, this.mShowHeaders));
   }

   public void worksheetRemoved(WorkbookEvent event) {
      this.removeWorksheetPanel(event.getWorksheet().getName());
   }

   public void worksheetRenamed(WorksheetRenamedEvent event) {
      WorksheetPanel worksheetPanel = this.getWorksheetPanel(event.getOriginalName());
      int index = this.indexOfWorksheetPanel(event.getOriginalName());
      this.removeWorksheetPanel(event.getOriginalName());
      this.insertWorksheetPanel(index, worksheetPanel);
   }

   public void worksheetMoved(WorkbookReorderWorksheetsEvent event) {
      WorksheetPanel worksheetPanel = this.getWorksheetPanel(event.getOldIndex());
      this.removeWorksheetPanel(event.getOldIndex());
      this.insertWorksheetPanel(event.getNewIndex(), worksheetPanel);
      this.setSelectedIndex(event.getNewIndex());
   }

   public void worsheetFormatsSelection(WorksheetFormatsSelectionEvent e) {}

   public WorksheetPanel getWorksheetPanel(String name) {
      int tabIndex = this.indexOfWorksheetPanel(name);
      return tabIndex < 0?null:(WorksheetPanel)((Pair)this.mWorksheetPanels.get(tabIndex)).getChild2();
   }

   private WorksheetPanel getWorksheetPanel(int index) {
      return (WorksheetPanel)((Pair)this.mWorksheetPanels.get(index)).getChild2();
   }

   private int addWorksheetPanel(WorksheetPanel panel) {
      int index = this.indexOfWorksheetPanel(panel);
      if(index != -1) {
         return index;
      } else {
         if(panel.getWorksheet().getWorkbook().isDesignMode()) {
            this.addTab(panel.getWorksheet().getName(), (Icon)null, panel, (String)null);
         } else if(!panel.getWorksheet().isHidden()) {
            this.addTab(panel.getWorksheet().getName(), (Icon)null, panel, (String)null);
         }

         this.mWorksheetPanels.add(new Pair(panel.getWorksheet().getName(), panel));
         return this.mWorksheetPanels.indexOf(panel);
      }
   }

   private int insertWorksheetPanel(int index, WorksheetPanel panel) {
      int existingIndex = this.indexOfWorksheetPanel(panel);
      if(existingIndex != -1) {
         return -1;
      } else {
         this.insertTab(panel.getWorksheet().getName(), (Icon)null, panel, (String)null, index);
         this.mWorksheetPanels.add(index, new Pair(panel.getWorksheet().getName(), panel));
         return index;
      }
   }

   private WorksheetPanel removeWorksheetPanel(String tabName) {
      int index = this.indexOfWorksheetPanel(tabName);
      if(index == -1) {
         return null;
      } else {
         WorksheetPanel worksheetPanel = this.getWorksheetPanel(tabName);
         this.removeTabAt(index);
         this.mWorksheetPanels.remove(index);
         return worksheetPanel;
      }
   }

   private WorksheetPanel removeWorksheetPanel(int index) {
      WorksheetPanel worksheetPanel = this.getWorksheetPanel(index);
      this.removeTabAt(index);
      this.mWorksheetPanels.remove(index);
      return worksheetPanel;
   }

   private int indexOfWorksheetPanel(String name) {
      for(int i = 0; i < this.mWorksheetPanels.size(); ++i) {
         Pair worksheetPanel = (Pair)this.mWorksheetPanels.get(i);
         if(((String)worksheetPanel.getChild1()).equals(name)) {
            return i;
         }
      }

      return -1;
   }

   private int indexOfWorksheetPanel(WorksheetPanel panel) {
      for(int i = 0; i < this.mWorksheetPanels.size(); ++i) {
         Pair worksheetPanel = (Pair)this.mWorksheetPanels.get(i);
         if(worksheetPanel.getChild2() == panel) {
            return i;
         }
      }

      return -1;
   }

   // $FF: synthetic method
   static void accessMethod200(WorkbookPanel x0) {
      x0.handleNewWorksheet();
   }

   // $FF: synthetic method
   static void accessMethod300(WorkbookPanel x0) {
      x0.handleRemoveWorksheet();
   }

   // $FF: synthetic method
   static void accessMethod400(WorkbookPanel x0) {
      x0.handleRenameWorksheet();
   }

   // $FF: synthetic method
   static void accessMethod500(WorkbookPanel x0) {
      x0.handleReorderWorksheets();
   }
}
