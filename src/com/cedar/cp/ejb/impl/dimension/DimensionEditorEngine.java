// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.dimension.DimensionEvent;
import com.cedar.cp.api.dimension.DimensionEventListener;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.event.ContextEvent;
import com.cedar.cp.dto.dimension.event.InsertDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.UpdateDimensionElementEvent;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorEngine$1;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorEngine$2;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorEngine$3;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorEngine$4;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;

public class DimensionEditorEngine implements Serializable {

   private boolean mInsideChangeManagement;
   private boolean mSaveEvents;
   private List mSavedEvents;
   private boolean mDAOValidationDisabled;
   private List mOutputEvents;
   private DimensionDAG mDimensionDAG;
   private int mTemporaryElementIndex;
   private List mListeners;
   protected int mType;
   private transient HierarchyElementFeedDAO mHierarchyElementFeedDAO;
   private DAGContext mDAGContext;


   public DimensionEditorEngine(InitialContext initialContext, DAGContext dagContext, boolean insideCM) {
      this.mSavedEvents = new ArrayList();
      this.mTemporaryElementIndex = -2;
      this.mListeners = new ArrayList();
      this.mInsideChangeManagement = insideCM;
      this.mDAGContext = dagContext;
      this.mOutputEvents = new ArrayList();
      this.addListener(InsertDimensionElementEvent.class, new DimensionEditorEngine$1(this));
      this.addListener(RemoveDimensionElementEvent.class, new DimensionEditorEngine$2(this));
      this.addListener(UpdateDimensionElementEvent.class, new DimensionEditorEngine$3(this));
      this.addListener(ContextEvent.class, new DimensionEditorEngine$4(this));
   }

   public DimensionEditorEngine(InitialContext initialContext, DAGContext dagContext) {
      this(initialContext, dagContext, false);
   }

   public List processEvents(DimensionEvent event) throws ValidationException, CPException {
      this.mOutputEvents.clear();
      this.dispatchEvent(event);
      return this.mOutputEvents;
   }

   public List processEvents(List inputEvents) throws ValidationException, CPException {
      this.mOutputEvents.clear();
      Iterator events = inputEvents.iterator();

      while(events.hasNext()) {
         this.dispatchEvent((DimensionEvent)events.next());
      }

      if(this.isSaveEvents()) {
         this.mSavedEvents.addAll(this.mOutputEvents);
      }

      return this.mOutputEvents;
   }

   private void processEvent(DimensionEvent event) throws CPException, ValidationException {
      System.err.println("Unknown event " + event + " encountered skipping...");
   }

   private void processEvent(InsertDimensionElementEvent event) throws CPException, ValidationException {
      String visId = event.getVisId();
      String description = event.getDescription();
      int creditDebit = event.getCreditDebit();
      int augCreditDebit = event.getAugCreditDebit();
      boolean isNotPlannable = event.isNotPlannable();
      boolean disabled = event.isDisabled();
      boolean isNullElement = event.isIsNullElement();
      if(this.getDimensionDAG().findElement(visId) != null) {
         throw new DuplicateNameValidationException("Duplicate element identifier[" + visId + "]");
      } else {
         List nodes = this.mDimensionDAG.findHierarchyNodes(visId);

         for(int newElement = 0; newElement < nodes.size(); ++newElement) {
            HierarchyNodeDAG nodeDAG = (HierarchyNodeDAG)nodes.get(newElement);
            if(!nodeDAG.isLeaf()) {
               throw new DuplicateNameValidationException("This name clashes with an existing hierarchy element");
            }
         }

         DimensionElementDAG var12 = new DimensionElementDAG(this.getDAGContext(), this.getDimensionDAG(), this.getTempElementNumber(), visId, description, creditDebit, augCreditDebit, isNotPlannable, isNullElement);
         var12.setDisabled(disabled);
         this.getDimensionDAG().addDimensionElement(var12);
         if(isNullElement) {
            this.getDimensionDAG().setNullElement(var12);
         }

         if(!this.isInsideChangeManagement()) {
            this.addEvent(new InsertDimensionElementEvent(var12.getCK(), visId, description, creditDebit, event.getAugCreditDebit(), event.isNotPlannable(), disabled, isNullElement));
         }

      }
   }

   private void processEvent(RemoveDimensionElementEvent event) throws CPException, ValidationException {
      DimensionElementPK pk = this.extractDimensionElementPK(event.getElementKey());
      DimensionElementDAG element = this.getDimensionDAG().findElement(pk);
      if(element == null) {
         throw new IllegalArgumentException("Unable to locate element " + pk);
      } else {
         int deId = element.getPK().getDimensionElementId();
         if(!this.isDAOValidationDisabled() && this.getHierarchyElementFeedDAO().getAllFeedsForDimensionElement(deId).getNumRows() != 0) {
            throw new ValidationException("Unable to delete element [" + element.getVisId() + "]. It is referenced in a hierarchy.\n" + "Please remove it from all hierarchies before attempting to remove the dimension element");
         } else {
            this.getDimensionDAG().removeDimensionElement(element);
            if(element.isNullElement() && this.getDimensionDAG().getNullElement() == element) {
               this.getDimensionDAG().setNullElement((DimensionElementDAG)null);
            }

            if(!this.isInsideChangeManagement()) {
               this.addEvent(event);
            }

         }
      }
   }

   private void processEvent(UpdateDimensionElementEvent event) throws CPException, ValidationException {
      DimensionElementPK pk = this.extractDimensionElementPK(event.getElementKey());
      String visId = event.getVisId();
      if(visId != null && visId.trim().length() != 0) {
         DimensionElementDAG dupVisIdElem = this.getDimensionDAG().findElement(event.getVisId());
         if(dupVisIdElem != null && !dupVisIdElem.getPK().equals(pk)) {
            throw new DuplicateNameValidationException("Duplicate element identifier[" + dupVisIdElem.getVisId() + "]");
         } else {
            DimensionElementDAG element = this.getDimensionDAG().findElement(pk);
            if(element == null) {
               throw new ValidationException("Unable to locate element " + pk);
            } else {
               String origVisId = element.getVisId();
               if(origVisId != null && !origVisId.equals(visId)) {
                  List i$ = this.mDimensionDAG.findHierarchyNodes(visId);

                  for(int hDAG = 0; hDAG < i$.size(); ++hDAG) {
                     HierarchyNodeDAG nodeDAG = (HierarchyNodeDAG)i$.get(hDAG);
                     if(!nodeDAG.isLeaf()) {
                        throw new DuplicateNameValidationException("This name clashes with an existing hierarchy element");
                     }
                  }

                  this.mDimensionDAG.updateDimensionElementVisId(element, origVisId, visId);
               }

               element.setVisId(visId);
               if(event.getDescription() != null && event.getDescription().trim().length() != 0) {
                  element.setDescription(event.getDescription());
               }

               if(event.getCreditDebit() != null) {
                  element.setCreditDebit(event.getCreditDebit().intValue());
               }

               if(event.getAugCreditDebit() != null) {
                  element.setAugCreditDebit(event.getAugCreditDebit().intValue());
               }

               if(event.isNotPlannable() != null) {
                  element.setNotPlannable(event.isNotPlannable().booleanValue());
               }

               if(event.isDisabled() != null) {
                  element.setDisabled(event.isDisabled().booleanValue());
               }

               if(event.isNullElement() != null && event.isNullElement().booleanValue() && this.getDimensionDAG().getNullElement() != null && this.getDimensionDAG().getNullElement() != element) {
                  throw new ValidationException("Only one null element may be defined for a dimension.");
               } else {
                  if(event.isNullElement() != null) {
                     element.setNullElement(event.isNullElement().booleanValue());
                     if(event.isNullElement().booleanValue()) {
                        this.getDimensionDAG().setNullElement(element);
                     }
                  }

                  if(!this.isInsideChangeManagement()) {
                     this.addEvent(new UpdateDimensionElementEvent(event.getElementKey(), origVisId, event.getVisId(), event.getDescription(), event.getCreditDebit(), event.getAugCreditDebit(), event.isNotPlannable(), event.isDisabled(), event.isNullElement()));
                  }

                  if(this.mDimensionDAG.getHierarchies() != null) {
                     Iterator var10 = this.mDimensionDAG.getHierarchies().values().iterator();

                     while(var10.hasNext()) {
                        HierarchyDAG var11 = (HierarchyDAG)var10.next();
                        var11.setRequiresRebuild(true);
                     }
                  }

               }
            }
         }
      } else {
         throw new ValidationException("An identifier must be supplied");
      }
   }

   private void processEvent(ContextEvent contextEvent) {
      if(contextEvent.getContext() == ConnectionContext.SERVER_SESSION) {
         this.setDAOValidationDisabled(true);
      }

   }

   private DimensionElementPK extractDimensionElementPK(Object key) {
      if(key instanceof DimensionElementCK) {
         return ((DimensionElementCK)key).getDimensionElementPK();
      } else if(key instanceof DimensionElementPK) {
         return (DimensionElementPK)key;
      } else {
         throw new IllegalArgumentException("Unknown key type for dimension element");
      }
   }

   private int getTempElementNumber() {
      return this.mTemporaryElementIndex--;
   }

   private void dispatchEvent(DimensionEvent e) throws CPException, ValidationException {
      for(int i = 0; i < this.mListeners.size(); i += 2) {
         Class cls = (Class)this.mListeners.get(i);
         if(cls.equals(e.getClass())) {
            DimensionEventListener listener = (DimensionEventListener)this.mListeners.get(i + 1);
            listener.dispatchEvent(e);
         }
      }

   }

   public DimensionDAG getDimensionDAG() {
      return this.mDimensionDAG;
   }

   public void setDimensionDAG(DimensionDAG dimension) {
      this.mDimensionDAG = dimension;
      this.mSavedEvents.clear();
   }

   public DAGContext getDAGContext() throws CPException {
      if(this.mDAGContext == null) {
         try {
            this.mDAGContext = new DAGContext(new InitialContext());
         } catch (Exception var2) {
            throw new CPException(var2.getMessage(), var2);
         }
      }

      return this.mDAGContext;
   }

   public void setDAGContext(DAGContext DAGContext) {
      this.mDAGContext = DAGContext;
   }

   public int getDimensionType() {
      return this.mType;
   }

   public void setDimensionType(int type) {
      this.mType = type;
   }

   private void addEvent(DimensionEvent event) {
      this.mOutputEvents.add(event);
   }

   private HierarchyElementFeedDAO getHierarchyElementFeedDAO() {
      if(this.mHierarchyElementFeedDAO == null) {
         this.mHierarchyElementFeedDAO = new HierarchyElementFeedDAO();
      }

      return this.mHierarchyElementFeedDAO;
   }

   public boolean isDAOValidationDisabled() {
      return this.mDAOValidationDisabled;
   }

   public void setDAOValidationDisabled(boolean DAOValidationDisabled) {
      this.mDAOValidationDisabled = DAOValidationDisabled;
   }

   private void addListener(Class cls, DimensionEventListener listener) {
      this.mListeners.add(cls);
      this.mListeners.add(listener);
   }

   public boolean isSaveEvents() {
      return this.mSaveEvents;
   }

   public void setSaveEvents(boolean saveEvents) {
      this.mSaveEvents = saveEvents;
   }

   public List getSavedEvents() {
      return this.mSavedEvents;
   }

   public boolean isInsideChangeManagement() {
      return this.mInsideChangeManagement;
   }

   // $FF: synthetic method
   static void accessMethod000(DimensionEditorEngine x0, InsertDimensionElementEvent x1) throws CPException, ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod100(DimensionEditorEngine x0, RemoveDimensionElementEvent x1) throws CPException, ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod200(DimensionEditorEngine x0, UpdateDimensionElementEvent x1) throws CPException, ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod300(DimensionEditorEngine x0, ContextEvent x1) {
      x0.processEvent(x1);
   }
}
