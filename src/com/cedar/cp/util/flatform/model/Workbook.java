// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.RTree;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Properties;
import com.cedar.cp.util.flatform.model.ResourceStorage;
import com.cedar.cp.util.flatform.model.WorkbookListener;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.event.WorkbookEvent;
import com.cedar.cp.util.flatform.model.event.WorkbookReorderWorksheetsEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetFormatsSelectionEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetRenamedEvent;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import com.cedar.cp.util.flatform.model.parser.CellRangeRef;
import com.cedar.cp.util.flatform.model.parser.CellRect;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.Formula;
import com.cedar.cp.util.flatform.model.parser.FormulaEngine;
import com.cedar.cp.util.flatform.model.undo.AddWorksheetEdit;
import com.cedar.cp.util.flatform.model.undo.FFUndoableEditSupport;
import com.cedar.cp.util.flatform.model.undo.MovedWorksheetEdit;
import com.cedar.cp.util.flatform.model.undo.RemoveWorksheetEdit;
import com.cedar.cp.util.flatform.model.undo.RenameWorksheetEdit;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.event.EventListenerList;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;
import org.antlr.runtime.RecognitionException;

public class Workbook implements XMLWritable, Serializable {

   private static final long serialVersionUID = -235832334078457256L;
   private ResourceStorage mResourceHandler;
   private transient EventListenerList mEventListenerList;
   private transient UndoableEditSupport mUndoableEditSupport;
   private List<Worksheet> mWorksheets = new ArrayList();
   private Map<String, Worksheet> mWorksheetsMap = new HashMap();
   private transient FormulaEngine mFormulaEngine;
   private Map<Cell, Set<CellRect>> mGlobalCellRects = new HashMap(1000);
   private Properties mProperties;
   private boolean mDesignMode;
   private transient Log mLog = new Log(this.getClass());
   private boolean formulasOn = true;
   
   private boolean isValid = true;


   public List<Worksheet> getWorksheets() {
      return Collections.unmodifiableList(this.mWorksheets);
   }

   public boolean moveWorksheet(Worksheet worksheet, int newIndex) {
      int oldIndex = this.getWorksheets().indexOf(worksheet);
      boolean worked = this.doMoveWorksheet(worksheet, newIndex);
      if(worked) {
         this.postSingleUndoableEdit(new MovedWorksheetEdit(this, worksheet, oldIndex, newIndex));
      }

      return worked;
   }

   public boolean doMoveWorksheet(Worksheet worksheet, int newIndex) {
      List worksheets = this.getWorksheets();
      int currentIndex = worksheets.indexOf(worksheet);
      if(currentIndex != -1 && newIndex != currentIndex && newIndex >= 0 && newIndex < worksheets.size()) {
         worksheet = (Worksheet)this.mWorksheets.remove(currentIndex);
         this.mWorksheets.add(newIndex, worksheet);
         this.fireWorksheetMoved(worksheet, currentIndex, newIndex);
         return true;
      } else {
         return false;
      }
   }

   public void addWorksheet(Worksheet worksheet) {
      this.doAddWorksheet(worksheet);
      this.postSingleUndoableEdit(new AddWorksheetEdit(this, worksheet));
   }

   public void doAddWorksheet(Worksheet worksheet) {
      this.mWorksheetsMap.put(worksheet.getName().toUpperCase(), worksheet);
      this.mWorksheets.add(worksheet);
      worksheet.setWorkbook(this);
      this.fireWorksheetAdded(worksheet);
   }

   public boolean removeWorksheet(String name) {
      Worksheet worksheet = this.getWorksheet(name);
      if(worksheet != null && this.doRemoveWorksheet(name)) {
         this.postSingleUndoableEdit(new RemoveWorksheetEdit(this, worksheet, name));
         return true;
      } else {
         return false;
      }
   }

   public boolean doRemoveWorksheet(String name) {
      Worksheet worksheet = this.getWorksheet(name);
      if(worksheet != null) {
         this.mWorksheetsMap.remove(worksheet.getName().toUpperCase());
         this.mWorksheets.remove(worksheet);
         worksheet.postRemoveSheet();
         worksheet.setWorkbook((Workbook)null);
         this.fireWorksheetRemoved(worksheet);
         return true;
      } else {
         return false;
      }
   }

   public Worksheet getWorksheet(String name) {
      return (Worksheet)this.mWorksheetsMap.get(name.toUpperCase());
   }

   public Worksheet newWorksheet(String name) {
      if(this.getWorksheet(name) != null) {
         return null;
      } else {
         Worksheet worksheet = new Worksheet(name);
         this.addWorksheet(worksheet);
         return worksheet;
      }
   }

   public boolean renameWorksheet(String worksheetName, String newName, boolean isHidden) {
      Worksheet worksheet = this.getWorksheet(worksheetName);
      boolean oldHidden = worksheet.isHidden();
      boolean isHiddenChanged = isHidden != worksheet.isHidden();
      if(worksheet != null) {
         if(worksheetName.equals(newName)) {
            if(isHiddenChanged) {
               worksheet.setHidden(isHidden);
               this.postSingleUndoableEdit(new RenameWorksheetEdit(this, worksheetName, newName, oldHidden, isHidden));
               return true;
            }
         } else if(this.doRenameWorksheet(worksheetName, newName)) {
            if(isHiddenChanged) {
               Worksheet newWorksheet = this.getWorksheet(newName);
               newWorksheet.setHidden(isHidden);
               this.postSingleUndoableEdit(new RenameWorksheetEdit(this, worksheetName, newName, oldHidden, isHidden));
            }

            return true;
         }
      }

      return false;
   }

   public boolean doRenameWorksheet(String worksheetName, String newName) {
      Worksheet worksheet = this.getWorksheet(worksheetName);
      boolean oldHidden = worksheet.isHidden();
      if(worksheet != null && this.getWorksheet(newName) == null) {
         worksheet.rename(newName);
         this.mWorksheetsMap.remove(worksheetName.toUpperCase());
         this.mWorksheetsMap.put(worksheet.getName().toUpperCase(), worksheet);
         this.fireWorksheetRenamed(worksheet, worksheetName, oldHidden);
         return true;
      } else {
         return false;
      }
   }

   public boolean doRenameWorksheet(String worksheetName, String newName, boolean oldHidden, boolean newHidden) {
      Worksheet worksheet = this.getWorksheet(worksheetName);
      if(worksheet == null && this.getWorksheet(newName) != null) {
         return false;
      } else {
         worksheet.rename(newName);
         worksheet.setHidden(newHidden);
         this.mWorksheetsMap.remove(worksheetName.toUpperCase());
         this.mWorksheetsMap.put(worksheet.getName(), worksheet);
         this.fireWorksheetRenamed(worksheet, worksheetName, oldHidden);
         return true;
      }
   }

   public void initAfterLoad() {
      long t = System.currentTimeMillis();
      Iterator i$ = this.getWorksheets().iterator();

      while(i$.hasNext()) {
         Worksheet worksheet = (Worksheet)i$.next();
         worksheet.initAfterLoad();
      }

      this.recalc();
      this.getLogger().debug("Workbook:initAfter and recalc load time:" + (System.currentTimeMillis() - t));
   }

   public void recalc() {
      long t = System.currentTimeMillis();
      ArrayList roots = new ArrayList(5000);
      Iterator i$ = this.mWorksheets.iterator();

      while(i$.hasNext()) {
         Worksheet worksheet = (Worksheet)i$.next();
         worksheet.queryDependencyTreeRootCells(roots);
      }

      this.recalcDepedencyTree(roots);
      this.getLogger().debug("Workbook::recalc time:" + (System.currentTimeMillis() - t));
   }

   private void recalcDepedencyTree(List<Cell> roots) {
      HashSet visited = new HashSet(1000);
      ArrayList result = new ArrayList(50);
      Iterator i$ = roots.iterator();

      while(i$.hasNext()) {
         Cell cell = (Cell)i$.next();
         this.visitDependencyTree(cell, visited, result);
      }

      this.executeFormulae(result);
   }

   public void recalcDependencyTree(Cell cell) {
      HashSet visited = new HashSet(5000);
      ArrayList result = new ArrayList(500);
      this.visitDependencyTree(cell, visited, result);
      if( this.isFormulasOn() ) this.executeFormulae(result);
   }

   private void executeFormulae(List<Cell> cells) {
      for(int i = cells.size() - 1; i >= 0; --i) {
         Cell recalcCell = (Cell)cells.get(i);
         Worksheet recalcWorksheet = recalcCell.getWorksheet();
         if(recalcCell.getFormula() != null) {
            try {
               int e = recalcCell.getRow();
               int column = recalcCell.getColumn();
               this.getFormulaEngine().execute(recalcWorksheet, e, column, recalcCell.getFormula());
               recalcWorksheet.fireSparse2DArrayCellUpdated(e, column);
            } catch (RecognitionException var7) {
               throw new RuntimeException(var7.getMessage());
            }
         }
      }

   }

   protected void visitDependencyTree(Cell cell, Set<Cell> visited, List<Cell> result) {
      if(cell != null) {
         Worksheet worksheet = cell.getWorksheet();
         visited.add(cell);
         if(cell.getRefs() != null) {
            Iterator i$ = cell.getRefs().iterator();

            while(i$.hasNext()) {
               CellRef cellRef = (CellRef)i$.next();
               Cell childCell = cellRef.getCell(worksheet, cell.getRow(), cell.getColumn());
               if(childCell != null && !visited.contains(childCell)) {
                  this.visitDependencyTree(childCell, visited, result);
               }
            }
         }

         if(cell.getFormula() != null) {
            result.add(cell);
         }
      }

   }

   public void registerFormulaCell(Cell cell) {
      Formula formula = cell.getFormula();
      HashSet rects = new HashSet();
      Iterator i$ = formula.getRefs().iterator();

      while(i$.hasNext()) {
         CellRangeRef cellRect = (CellRangeRef)i$.next();
         CellRect refSheet = new CellRect(cell, cellRect.getStartRef().getWorksheet(cell.getWorksheet()), cellRect.getAbsoluteStartRow(cell.getRow()), cellRect.getAbsoluteStartColumn(cell.getColumn()), cellRect.getAbsoluteEndRow(cell.getRow()), cellRect.getAbsoluteEndColumn(cell.getColumn()));
         rects.add(refSheet);
      }

      i$ = rects.iterator();

      while(i$.hasNext()) {
         CellRect cellRect1 = (CellRect)i$.next();
         Worksheet refSheet1 = cellRect1.getWorksheet();
         if(refSheet1 != null) {
            refSheet1.getCellRectTree().add(new RTree.Rect(cellRect1.getStartColumn(), cellRect1.getStartRow(), cellRect1.getEndColumn(), cellRect1.getEndRow()), cell);
         }
      }

      this.mGlobalCellRects.put(cell, rects);
   }

   public Set<Cell> queryCellReferences(Cell cell) {
      RTree.Rect searchRect = new RTree.Rect(cell.getColumn(), cell.getRow(), cell.getColumn(), cell.getRow());
      HashSet results = new HashSet(50);
      results.addAll(cell.getWorksheet().getCellRectTree().find(searchRect, new ArrayList(50)));
      return results;
   }

   public void deregisterFormulaCell(Cell cell) {
      Set rects = (Set)this.mGlobalCellRects.get(cell);
      if(rects != null) {
         Iterator i$ = rects.iterator();

         while(i$.hasNext()) {
            CellRect rect = (CellRect)i$.next();
            Worksheet rectWorksheet = rect.getWorksheet();
            if(rectWorksheet != null) {
               RTree.Rect rtreeRect = new RTree.Rect(rect.getStartColumn(), rect.getStartRow(), rect.getEndColumn(), rect.getEndRow());
               rectWorksheet.getCellRectTree().remove(rtreeRect, cell);
            }
         }
      }

      this.mGlobalCellRects.remove(cell);
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<workbook>");
      if(this.mProperties != null) {
         this.mProperties.writeXml(out);
      }

      Iterator i$ = this.getWorksheets().iterator();

      while(i$.hasNext()) {
         Worksheet worksheet = (Worksheet)i$.next();
         worksheet.writeXml(out);
      }

      out.write("</workbook>");
   }

   public FormulaEngine getFormulaEngine() {
      if(this.mFormulaEngine == null) {
         this.mFormulaEngine = new FormulaEngine();
      }

      return this.mFormulaEngine;
   }

   public boolean isDesignMode() {
      return this.mDesignMode;
   }

   public void setDesignMode(boolean designMode) {
      this.mDesignMode = designMode;
   }

   public void addUndoableEditListener(UndoableEditListener listener) {
      this.getUndoableEditSupport().addUndoableEditListener(listener);
   }

   public void removeUndoableEditListener(UndoableEditListener listener) {
      this.getUndoableEditSupport().removeUndoableEditListener(listener);
   }

   public void beginGroupEdit() {
      this.getUndoableEditSupport().beginUpdate();
   }

   public void endGroupEdit() {
      this.getUndoableEditSupport().endUpdate();
   }

   public void postSingleUndoableEdit(UndoableEdit edit) {
      UndoableEditSupport undoableEditSupport = this.getUndoableEditSupport();
      undoableEditSupport.beginUpdate();
      undoableEditSupport.postEdit(edit);
      undoableEditSupport.endUpdate();
   }

   private UndoableEditSupport getUndoableEditSupport() {
      if(this.mUndoableEditSupport == null) {
         this.mUndoableEditSupport = new FFUndoableEditSupport(this);
      }

      return this.mUndoableEditSupport;
   }

   public void addWorkbookListener(WorkbookListener l) {
      this.getEventListenerList().add(WorkbookListener.class, l);
   }

   public void removeWorkbookListener(WorkbookListener l) {
      this.getEventListenerList().remove(WorkbookListener.class, l);
   }

   protected void fireWorksheetAdded(Worksheet worksheet) {
      WorkbookEvent workbookEvent = null;
      Object[] listeners = this.getEventListenerList().getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WorkbookListener.class) {
            if(workbookEvent == null) {
               workbookEvent = new WorkbookEvent(this, worksheet);
            }

            ((WorkbookListener)listeners[i + 1]).worksheetAdded(workbookEvent);
         }
      }

   }

   protected void fireWorksheetRemoved(Worksheet worksheet) {
      WorkbookEvent workbookEvent = null;
      Object[] listeners = this.getEventListenerList().getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WorkbookListener.class) {
            if(workbookEvent == null) {
               workbookEvent = new WorkbookEvent(this, worksheet);
            }

            ((WorkbookListener)listeners[i + 1]).worksheetRemoved(workbookEvent);
         }
      }

   }

   protected void fireWorksheetRenamed(Worksheet worksheet, String originalName) {
      WorksheetRenamedEvent workbookEvent = null;
      Object[] listeners = this.getEventListenerList().getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WorkbookListener.class) {
            if(workbookEvent == null) {
               workbookEvent = new WorksheetRenamedEvent(this, worksheet, originalName);
            }

            ((WorkbookListener)listeners[i + 1]).worksheetRenamed(workbookEvent);
         }
      }

   }

   protected void fireWorksheetRenamed(Worksheet worksheet, String originalName, boolean originalHidden) {
      WorksheetRenamedEvent workbookEvent = null;
      Object[] listeners = this.getEventListenerList().getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WorkbookListener.class) {
            if(workbookEvent == null) {
               workbookEvent = new WorksheetRenamedEvent(this, worksheet, originalName, originalHidden);
            }

            ((WorkbookListener)listeners[i + 1]).worksheetRenamed(workbookEvent);
         }
      }

   }

   protected void fireWorksheetMoved(Worksheet worksheet, int oldIndex, int newIndex) {
      WorkbookReorderWorksheetsEvent workbookEvent = null;
      Object[] listeners = this.getEventListenerList().getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WorkbookListener.class) {
            if(workbookEvent == null) {
               workbookEvent = new WorkbookReorderWorksheetsEvent(this, worksheet, oldIndex, newIndex);
            }

            ((WorkbookListener)listeners[i + 1]).worksheetMoved(workbookEvent);
         }
      }

   }

   protected void fireWorksheetFormatsSelection(Worksheet worksheet, int startRow, int startColumn, int endRow, int endColumn, Collection<FormatProperty> formats) {
      WorksheetFormatsSelectionEvent worksheetEvent = null;
      Object[] listeners = this.getEventListenerList().getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WorkbookListener.class) {
            if(worksheetEvent == null) {
               worksheetEvent = new WorksheetFormatsSelectionEvent(worksheet, startRow, startColumn, endRow, endColumn, formats);
            }

            ((WorkbookListener)listeners[i + 1]).worsheetFormatsSelection(worksheetEvent);
         }
      }

   }

   private EventListenerList getEventListenerList() {
      if(this.mEventListenerList == null) {
         this.mEventListenerList = new EventListenerList();
      }

      return this.mEventListenerList;
   }

   public Properties getProperties() {
      return this.mProperties;
   }

   public void setProperties(Properties properties) {
      this.mProperties = properties;
   }

   public String getProperty(String name) {
      return this.mProperties != null?(String)this.mProperties.get(name):null;
   }

   public void setProperty(String propName, String value) {
      if(this.mProperties == null) {
         this.mProperties = new Properties();
      }

      this.mProperties.put(propName, value);
   }

   public int storeResource(String resourceName, byte[] resource) {
      return this.mResourceHandler == null?-1:this.mResourceHandler.storeResource(resourceName, resource);
   }

   public ResourceStorage getResourceHandler() {
      return this.mResourceHandler;
   }

   public void setResourceHandler(ResourceStorage resourceHandler) {
      this.mResourceHandler = resourceHandler;
   }

   private Log getLogger() {
      if(this.mLog == null) {
         this.mLog = new Log(this.getClass());
      }

      return this.mLog;
   }

   public int getModificationCount() {
      int result = 0;
      Worksheet s;
      if(this.mWorksheets != null) {
         for(Iterator i$ = this.mWorksheets.iterator(); i$.hasNext(); result += s.getModificationCount()) {
            s = (Worksheet)i$.next();
         }
      }

      return result;
   }

	public boolean isFormulasOn() {
		return formulasOn;
	}

	public void setFormulasOn(boolean formulasOn) {
		this.formulasOn = formulasOn;
	}

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
