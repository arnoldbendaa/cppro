// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyEvent;
import com.cedar.cp.api.dimension.HierarchyEventListener;
import com.cedar.cp.dto.dimension.DimensionElementRefImpl;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionSelectionEvent;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.event.FreeDimensionElementsEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.NewDimensionElementListEvent;
import com.cedar.cp.dto.dimension.event.NewHierarchyEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.UpdateHierarchyElementChildOrderEvent;
import com.cedar.cp.dto.dimension.event.UpdateHierarchyElementEvent;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.dimension.AugHierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine$1;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine$2;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine$3;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine$4;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine$5;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine$6;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine$7;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine$8;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;

public class HierarchyEditorEngine implements Serializable {

   private boolean mInsideChangeManagement;
   private boolean mAugmentMode;
   private boolean mSaveEvents;
   private List mSavedEvents = new ArrayList();
   private HierarchyDAG mHierarchyDAG;
   private DimensionDAG mDimensionDAG;
   private int mTemporaryElementIndex = -5000000;
   private List mListeners = new ArrayList();
   private List mRemovedElements = new ArrayList();
   protected int mType;
   private DAGContext mDAGContext;
   private static final String NEW_ELEMENT_NAME = "New Element";
   private static final String NEW_ELEMENT_DESCRIPTION = ".";
   private List mOutputEvents;
   private InitialContext mInitialContext;


   public HierarchyEditorEngine(InitialContext initialContext, DAGContext dagContext, boolean insideChangeManagement) {
      this.mInitialContext = initialContext;
      this.mDAGContext = dagContext;
      this.mOutputEvents = new ArrayList();
      this.mInsideChangeManagement = insideChangeManagement;
      this.addListener(InsertHierarchyElementEvent.class, new HierarchyEditorEngine$1(this));
      this.addListener(RemoveHierarchyElementEvent.class, new HierarchyEditorEngine$2(this));
      this.addListener(UpdateHierarchyElementEvent.class, new HierarchyEditorEngine$3(this));
      this.addListener(MoveHierarchyElementEvent.class, new HierarchyEditorEngine$4(this));
      this.addListener(MoveHierarchyElementFeedEvent.class, new HierarchyEditorEngine$5(this));
      this.addListener(DimensionSelectionEvent.class, new HierarchyEditorEngine$6(this));
      this.addListener(InsertHierarchyElementFeedEvent.class, new HierarchyEditorEngine$7(this));
      this.addListener(RemoveHierarchyElementFeedEvent.class, new HierarchyEditorEngine$8(this));
   }

   public EntityList getAvailableDimensionsForInsert(int dimensionType) throws CPException {
      DimensionDAO dimDAO = new DimensionDAO();
      return dimDAO.queryDimensionOwners(dimensionType);
   }

   public List processEvents(List inputEvents) throws ValidationException, CPException {
      this.mOutputEvents.clear();
      Iterator events = inputEvents.iterator();

      while(events.hasNext()) {
         this.dispatchEvent((HierarchyEvent)events.next());
      }

      if(this.isSaveEvents()) {
         this.mSavedEvents.addAll(this.mOutputEvents);
      }

      return this.mOutputEvents;
   }

   public List processEvents(HierarchyEvent event) throws ValidationException, CPException {
      this.mOutputEvents.clear();
      this.dispatchEvent(event);
      return this.mOutputEvents;
   }

   private void processEvent(HierarchyEvent event) throws CPException, ValidationException {
      System.err.println("Unknown event " + event + " encountered skipping...");
   }

   private void processEvent(DimensionSelectionEvent event) throws CPException {
      HierarchyImpl editorData = new HierarchyImpl((Object)null);
      editorData.setDimensionRef(event.getDimensionRef());

      try {
         DimensionPK e = (DimensionPK)event.getDimensionRef().getPrimaryKey();
         editorData.setModel(this.getDimensionAccessor().queryOwningModel(e));
         DimensionEVO dEVO = this.completeGetNewItemData(editorData);
         if(!this.isInsideChangeManagement()) {
            this.addEvent(new NewHierarchyEvent(editorData));
            this.addEvent(new NewDimensionElementListEvent(this.getDimensionElements(dEVO)));
            if(dEVO.getExternalSystemRef() == null && editorData.getModel() != null) {
               this.setSaveEvents(true);
            }
         }

      } catch (Exception var5) {
         throw new CPException("Processing dimension selection event:", var5);
      }
   }

   public DimensionEVO completeGetNewItemData(HierarchyImpl editorData) throws Exception {
      String DEPENDANTS_FOR_GET_DIMENSION_DATA = "<0>";
      DimensionEVO dEVO = this.getDimensionAccessor().getDetails(editorData.getDimensionRef().getPrimaryKey(), DEPENDANTS_FOR_GET_DIMENSION_DATA);
      this.setDimensionDAG(new DimensionDAG(this.getDAGContext(), dEVO));
      this.setHierarchyDAG(new HierarchyDAG(this.getDAGContext()));
      this.getHierarchyDAG().setDimension(this.getDimensionDAG());
      this.getHierarchyDAG().setRoot(new HierarchyElementDAG(this.getDAGContext(), this.getHierarchyDAG(), this.getTempElementNumber(), "New Element", ".", 2, 0, -1));
      this.getHierarchyDAG().createLightweightDAG(editorData);
      return dEVO;
   }

   public HierarchyDAG insertNewHierarchy(String visId, String description) throws ValidationException {
      this.setHierarchyDAG(new HierarchyDAG(this.getDAGContext()));
      this.getHierarchyDAG().setDimension(this.getDimensionDAG());
      this.getHierarchyDAG().setVisId(visId);
      this.getHierarchyDAG().setDescription(description);
      this.getHierarchyDAG().setRoot(new HierarchyElementDAG(this.getDAGContext(), this.mHierarchyDAG, this.getTempElementNumber(), "New Element", ".", 2, 0, -1));
      return this.getHierarchyDAG();
   }

   private List getDimensionElements(DimensionEVO dEVO) {
      ArrayList dimensionElements = new ArrayList();
      Iterator i = dEVO.getElements().iterator();

      while(i.hasNext()) {
         DimensionElementEVO deEVO = (DimensionElementEVO)i.next();
         DimensionElementDAG deDAG = (DimensionElementDAG)this.mDimensionDAG.getPKMap().get(deEVO.getPK());
         dimensionElements.add(deDAG.createLightweightElement((DimensionImpl)null));
      }

      return dimensionElements;
   }

   private void processEvent(InsertHierarchyElementEvent event) throws CPException, ValidationException {
      int parentId;
      int index;
      HierarchyNodeDAG treeNode;
      HierarchyElementDAG parent;
      String newName;
      String newDescription;
      int newElement;
      AugHierarchyElementDAG numAugChildren;
      if(this.isAugmentMode()) {
         if(event.getParentKey() == null && this.mHierarchyDAG.getRoot() == null) {
            throw new ValidationException("A augmentation element cannot be the root element");
         }

         if(event.getParentKey() == null && this.mHierarchyDAG.getRoot() != null) {
            throw new ValidationException("A parent element reference must be supplied for an augmentation element");
         }

         parentId = this.queryHierarchyElementId(event.getParentKey());
         index = event.getIndex();
         treeNode = this.mHierarchyDAG.find(parentId);
         if(treeNode == null) {
            throw new ValidationException("Unable to locate element with id : " + parentId);
         }

         if(treeNode.isFeeder()) {
            throw new ValidationException("Can\'t add children to Feeder nodes");
         }

         parent = (HierarchyElementDAG)treeNode;
         newName = event.getVisId() != null && event.getVisId().trim().length() != 0?event.getVisId():"New Element";
         newDescription = event.getDescription() != null && event.getDescription().trim().length() != 0?event.getDescription():".";

         for(newElement = 0; this.mHierarchyDAG.findElement(newName) != null; newName = "New Element" + newElement++) {
            ;
         }

         numAugChildren = new AugHierarchyElementDAG(this.getDAGContext(), this.mHierarchyDAG, this.getTempElementNumber(), newName, newDescription, event.getCreditDebit());
         parent.addAug(index, numAugChildren);
         if(!this.isInsideChangeManagement()) {
            this.addEvent(new InsertHierarchyElementEvent(event.getParentKey(), parent.getVisId(), newName, newDescription, index, event.getCreditDebit(), event.getAugCreditDebit(), numAugChildren.getPK()));
         }
      } else if(event.getParentKey() == null && this.mHierarchyDAG.getRoot() == null) {
         HierarchyElementDAG var10 = new HierarchyElementDAG(this.getDAGContext(), this.mHierarchyDAG, this.getTempElementNumber(), event.getVisId(), event.getDescription(), event.getCreditDebit(), event.getAugCreditDebit(), -1);
         this.mHierarchyDAG.setRoot(var10);
         if(!this.isInsideChangeManagement()) {
            InsertHierarchyElementEvent var11 = new InsertHierarchyElementEvent(event.getParentKey(), "", event.getVisId(), event.getDescription(), 0, event.getCreditDebit(), event.getAugCreditDebit(), var10.getPK());
            this.addEvent(var11);
         }
      } else {
         if(event.getParentKey() == null && this.mHierarchyDAG.getRoot() != null) {
            throw new ValidationException("A parent element reference must be supplied");
         }

         parentId = this.queryHierarchyElementId(event.getParentKey());
         index = event.getIndex();
         treeNode = this.mHierarchyDAG.find(parentId);
         if(treeNode == null) {
            throw new ValidationException("Unable to locate element with id : " + parentId);
         }

         if(treeNode.isFeeder()) {
            throw new ValidationException("Can\'t add children to Feeder nodes");
         }

         parent = (HierarchyElementDAG)treeNode;
         newName = event.getVisId() != null && event.getVisId().trim().length() != 0?event.getVisId():"New Element";
         newDescription = event.getDescription() != null && event.getDescription().trim().length() != 0?event.getDescription():".";
         if(this.isInsideChangeManagement()) {
            HierarchyNodeDAG var12 = this.mHierarchyDAG.findElement(newName);
            if(var12 != null) {
               if(!var12.isAugmentElement()) {
                  throw new DuplicateNameValidationException("Duplicate element name:[" + newName + "]");
               }

               numAugChildren = (AugHierarchyElementDAG)var12;
               numAugChildren.removeFromParent();
               this.registerRemovedElements(numAugChildren);
            }
         } else {
            for(newElement = 0; this.mHierarchyDAG.findElement(newName) != null; newName = "New Element" + newElement++) {
               ;
            }
         }

         HierarchyElementDAG var13 = new HierarchyElementDAG(this.getDAGContext(), this.mHierarchyDAG, this.getTempElementNumber(), newName, newDescription, event.getCreditDebit(), event.getAugCreditDebit(), -1);
         parent.add(index, var13);
         if(parent.getAugChildren() != null) {
            int var14 = parent.getAugChildren().size();
            if(index > var14) {
               index = var14;
            }

            parent.addAug(index, var13);
         }

         if(!this.isInsideChangeManagement()) {
            this.addEvent(new InsertHierarchyElementEvent(event.getParentKey(), parent.getVisId(), newName, newDescription, index, event.getCreditDebit(), event.getAugCreditDebit(), var13.getPK()));
         }
      }

   }

   private void processEvent(InsertHierarchyElementFeedEvent event) throws ValidationException, CPException {
      int parentId = this.queryHierarchyElementId(event.getParentKey());
      int index = event.getIndex();
      HierarchyNodeDAG treeNode = this.mHierarchyDAG.find(parentId);
      if(treeNode == null) {
         throw new ValidationException("Unable to locate element " + parentId);
      } else if(treeNode.isFeeder()) {
         throw new ValidationException("Invalid to add children to feeder node");
      } else {
         HierarchyElementDAG parent = (HierarchyElementDAG)treeNode;
         DimensionElementDAG dimensionElement = (DimensionElementDAG)this.getDAGContext().getCache().get(DimensionElementDAG.class, event.getDimensionElementRef().getPrimaryKey());
         if(dimensionElement == null) {
            throw new ValidationException("Unable to locate dimension element:" + event.getDimensionElementRef());
         } else {
            HierarchyNodeDAG duplicateNode = this.mHierarchyDAG.findElement(dimensionElement.getVisId());
            if(duplicateNode != null) {
               if(!duplicateNode.isAugmentElement() || this.isAugmentMode()) {
                  throw new ValidationException("Dimension element [" + dimensionElement.getVisId() + "] clashes with [" + duplicateNode.getVisId() + " - " + duplicateNode.getDescription() + "]");
               }

               AugHierarchyElementDAG newElement = (AugHierarchyElementDAG)duplicateNode;
               newElement.removeFromParent();
               this.registerRemovedElements(newElement);
            }

            HierarchyElementFeedDAG newElement1 = new HierarchyElementFeedDAG(this.getDAGContext(), parent, dimensionElement, index, (HierarchyElementDAG)null, -1, -1);
            parent.add(index, newElement1);
            if(parent.getAugChildren() != null) {
               int numAugChildren = parent.getAugChildren().size();
               if(index > numAugChildren) {
                  index = numAugChildren;
               }

               parent.addAug(index, newElement1);
            }

            if(!this.isInsideChangeManagement()) {
               this.addEvent(new InsertHierarchyElementFeedEvent(event.getParentKey(), index, parent.getVisId(), dimensionElement.getDescription(), event.getDimensionElementRef(), newElement1.getPK(), dimensionElement.isDisabled(), dimensionElement.getCreditDebit(), dimensionElement.getAugCreditDebit()));
            }

         }
      }
   }

   private void processEvent(RemoveHierarchyElementEvent event) throws CPException, ValidationException {
      int id = this.queryHierarchyElementId(event.getElementKey());
      HierarchyElementDAG element;
      HierarchyElementDAG augParent;
      if(this.isAugmentMode()) {
         element = (HierarchyElementDAG)this.mHierarchyDAG.find(id);
         if(element == null) {
            throw new ValidationException("Unable to locate element " + id);
         }

         if(!(element instanceof AugHierarchyElementDAG)) {
            throw new ValidationException("Not allowed to remove normal hierarchy element in augmentation mode.");
         }

         AugHierarchyElementDAG parent = (AugHierarchyElementDAG)element;
         this.registerRemovedElements(parent);
         augParent = parent.getAugParent();
         if(augParent == null) {
            throw new ValidationException("Augmented element cannot be the root or the hierarchy");
         }

         parent.removeFromParent();
         if(!augParent.isAugmentElement() && !augParent.hasImmediateAugmentedChildren()) {
            augParent.revertToNonAugmentedChildList();
         }

         if(!this.isInsideChangeManagement()) {
            this.addEvent(new RemoveHierarchyElementEvent(event.getElementKey(), parent.getVisId()));
            if(!augParent.isAugmentElement() && !augParent.hasImmediateAugmentedChildren()) {
               this.addEvent(new UpdateHierarchyElementChildOrderEvent(augParent.getPrimaryKey(), augParent.getVisId(), augParent.getChildKeys()));
            }
         }
      } else {
         element = (HierarchyElementDAG)this.mHierarchyDAG.find(id);
         if(element == null) {
            throw new ValidationException("Unable to locate element " + id);
         }

         if(this.isAugmentMode()) {
            throw new ValidationException("Not allowed to remove hierarchy element feeds in augmentation mode");
         }

         this.registerRemovedElements(element);
         if(!this.isInsideChangeManagement()) {
            FreeDimensionElementsEvent parent1 = new FreeDimensionElementsEvent();
            element.findDimensionElements(parent1);
            this.addEvent(parent1);
         }

         HierarchyElementDAG parent2 = element.getParent();
         if(parent2 == null) {
            this.mHierarchyDAG.setRoot((HierarchyElementDAG)null);
         } else {
            parent2.remove(element);
         }

         if(element.getAugParent() != null) {
            augParent = element.getAugParent();
            augParent.removeFromAugChildren(element);
         }

         if(!this.isInsideChangeManagement()) {
            this.addEvent(new RemoveHierarchyElementEvent(event.getElementKey(), element.getVisId()));
         }
      }

   }

   private void processEvent(RemoveHierarchyElementFeedEvent event) throws CPException, ValidationException {
      int id = ((HierarchyElementFeedPK)event.getElementKey()).getDimensionElementId();
      HierarchyNodeDAG element = this.mHierarchyDAG.find(id);
      if(element == null) {
         throw new ValidationException("Unable to locate element " + id);
      } else if(!element.isFeeder()) {
         throw new ValidationException("Not a feeder node:" + element.getVisId());
      } else {
         HierarchyElementFeedDAG hefDAG = (HierarchyElementFeedDAG)element;
         this.registerRemovedElements(element);
         HierarchyElementDAG parent = element.getParent();
         if(parent == null) {
            this.mHierarchyDAG.setRoot((HierarchyElementDAG)null);
         } else {
            parent.remove(element);
         }

         if(element.getAugParent() != null) {
            HierarchyElementDAG dimensionElementRef = element.getAugParent();
            dimensionElementRef.removeFromAugChildren(element);
         }

         if(!this.isInsideChangeManagement()) {
            DimensionElementRefImpl dimensionElementRef1 = hefDAG.getDimensionElement().getEntityRef();
            this.addEvent(new RemoveHierarchyElementFeedEvent(event.getElementKey(), hefDAG.getDimensionElement().createLightweightElement((DimensionImpl)null)));
         }

      }
   }

   private int queryHierarchyElementId(Object key) {
      if(key == null) {
         throw new IllegalArgumentException("Null passed to queryHierarchyElementId()");
      } else if(key instanceof HierarchyElementPK) {
         return ((HierarchyElementPK)key).getHierarchyElementId();
      } else if(key instanceof HierarchyElementCK) {
         return ((HierarchyElementCK)key).getHierarchyElementPK().getHierarchyElementId();
      } else {
         throw new IllegalArgumentException("Invalid key for HierarchyElement:" + key.getClass());
      }
   }

   private int queryHierarchyElementFeedId(Object key) {
      if(key == null) {
         throw new IllegalArgumentException("Null passed to queryHierarchyElementFeedId()");
      } else if(key instanceof HierarchyElementFeedPK) {
         return ((HierarchyElementFeedPK)key).getDimensionElementId();
      } else if(key instanceof HierarchyElementFeedCK) {
         return ((HierarchyElementFeedCK)key).getHierarchyElementFeedPK().getDimensionElementId();
      } else {
         throw new IllegalArgumentException("Invalid key for HierarchyElementFeed:" + key.getClass());
      }
   }

   private void processEvent(UpdateHierarchyElementEvent event) throws CPException, ValidationException {
      int id = this.queryHierarchyElementId(event.getElementKey());
      String visId = event.getVisId();
      HierarchyNodeDAG dupVisIdElem = this.mHierarchyDAG.findElement(event.getVisId());
      if(dupVisIdElem != null && dupVisIdElem.getId() != id) {
         throw new DuplicateNameValidationException("Duplicate element identifier");
      } else {
         HierarchyNodeDAG element = this.mHierarchyDAG.find(id);
         if(element == null) {
            throw new ValidationException("Unable to locate element " + id);
         } else {
            String origVisId = element.getVisId();
            if(element.isFeeder()) {
               throw new ValidationException("Nothing to update on feeder nodes");
            } else {
               HierarchyElementDAG he = (HierarchyElementDAG)element;
               if(this.isAugmentMode() && !element.isAugmentElement()) {
                  if(event.getAugCreditDebit() != null) {
                     he.setAugCreditDebit(event.getAugCreditDebit().intValue());
                  }
               } else {
                  he.setVisId(visId);
                  he.setDescription(event.getDescription());
                  if(event.getCreditDebit() != null) {
                     he.setCreditDebit(event.getCreditDebit().intValue());
                  }

                  if(event.getAugCreditDebit() != null) {
                     he.setAugCreditDebit(event.getAugCreditDebit().intValue());
                  }
               }

               if(!this.isInsideChangeManagement()) {
                  this.addEvent(new UpdateHierarchyElementEvent(event.getElementKey(), origVisId, event.getVisId(), event.getDescription(), event.getCreditDebit(), event.getAugCreditDebit()));
               }

            }
         }
      }
   }

   private void processEvent(MoveHierarchyElementEvent event) throws CPException, ValidationException {
      int id = this.queryHierarchyElementId(event.getElementKey());
      boolean parentId = false;
      Object key = event.getParentKey();
      int var9;
      if(key instanceof HierarchyElementCK) {
         var9 = ((HierarchyElementCK)key).getHierarchyElementPK().getHierarchyElementId();
      } else {
         var9 = ((HierarchyElementPK)key).getHierarchyElementId();
      }

      int index = event.getIndex();
      HierarchyNodeDAG element = this.mHierarchyDAG.find(id);
      if(element == null) {
         throw new ValidationException("Unable to locate element " + id);
      } else {
         HierarchyNodeDAG parent = this.mHierarchyDAG.find(var9);
         if(parent == null) {
            throw new ValidationException("Unable to locate element " + var9);
         } else if(parent.isFeeder()) {
            throw new ValidationException("Invalid to add children to feeder node");
         } else if(parent.isNodeAncestor(element)) {
            throw new ValidationException("Illegal move would cause circular reference");
         } else {
            HierarchyElementDAG oldParent;
            if(this.isAugmentMode()) {
               oldParent = element.getActiveParent();
               if(oldParent == null) {
                  throw new ValidationException("Not allowed to move the root node");
               }

               element.validateAugmentModeMove((HierarchyElementDAG)parent, index);
               /*if(oldParent.equals(parent) && parent.getAugChildren() != null && parent.augIndexOf(element) < index) {
                  --index;
               }  It make a problems, I don't know if it is important or not importand */

               this.mHierarchyDAG.setMaintMode(true);
               oldParent.removeFromAugChildren(element);
               parent.addAug(index, element);
               this.mHierarchyDAG.setMaintMode(false);
            } else {
               oldParent = element.getParent();
               if(oldParent == null) {
                  throw new ValidationException("Not allowed to move the root node");
               }

               if(oldParent.equals(parent) && parent.indexOf(element) < index) {
                  --index;
               }

               this.mHierarchyDAG.setMaintMode(true);
               oldParent.remove(element);
               parent.add(index, element);
               this.mHierarchyDAG.setMaintMode(false);
               if(element.getAugParent() != null && !oldParent.equals(parent)) {
                  this.mHierarchyDAG.setMaintMode(true);
                  element.getAugParent().removeFromAugChildren(element);
                  element.setAugParent((HierarchyElementDAG)null);
                  this.mHierarchyDAG.setMaintMode(false);
               }

               if(parent.getAugChildren() != null) {
                  this.mHierarchyDAG.setMaintMode(true);
                  if(index > parent.getAugChildren().size()) {
                     index = parent.getAugChildren().size();
                  }

                  parent.addAug(index, element);
                  this.mHierarchyDAG.setMaintMode(false);
               }
            }

            if(!this.isInsideChangeManagement()) {
               this.addEvent(new MoveHierarchyElementEvent(event.getParentKey(), parent.getVisId(), event.getIndex(), event.getElementKey(), element.getVisId()));
            }

         }
      }
   }

   private void processEvent(MoveHierarchyElementFeedEvent event) throws CPException, ValidationException {
      int id = this.queryHierarchyElementFeedId(event.getElementKey());
      int parentId = 0;
      Object key = event.getParentKey();
      if(key instanceof HierarchyElementCK) {
         parentId = ((HierarchyElementCK)key).getHierarchyElementPK().getHierarchyElementId();
      } else {
         parentId = ((HierarchyElementPK)key).getHierarchyElementId();
      }

      int index = event.getIndex();
      HierarchyNodeDAG element = this.mHierarchyDAG.find(id);
      if(element == null) {
         throw new ValidationException("Unable to locate element " + id);
      } else {
         HierarchyNodeDAG parent = this.mHierarchyDAG.find(parentId);
         if(parent == null) {
            throw new ValidationException("Unable to locate element " + parentId);
         } else if(parent.isFeeder()) {
            throw new ValidationException("Invalid to add children to feeder node");
         } else if(parent.isNodeAncestor(element)) {
            throw new ValidationException("Illegal move would cause circular reference");
         } else {
            if(this.isAugmentMode()) {
               HierarchyElementDAG oldParent = element.getAugParent();
               element.validateAugmentModeMove((HierarchyElementDAG)parent, index);
               if(oldParent != null && oldParent.equals(parent) && parent.getAugChildren() != null && parent.augIndexOf(element) < index) {
                  index--;
               }

               this.mHierarchyDAG.setMaintMode(true);
               if(oldParent != null) {
                  oldParent.removeFromAugChildren(element);
               }

               parent.addAug(index, element);
               this.mHierarchyDAG.setMaintMode(false);
            } else {
               HierarchyElementDAG oldParent = element.getParent();
               if(oldParent == null) {
                  throw new ValidationException("Not allowed to move the root node");
               }

               if(oldParent.equals(parent) && parent.indexOf(element) < index) {
                  index--;
               }

               this.mHierarchyDAG.setMaintMode(true);
               oldParent.remove(element);
               parent.add(index, element);
               this.mHierarchyDAG.setMaintMode(false);
               if(element.getAugParent() != null && !oldParent.equals(parent)) {
                  this.mHierarchyDAG.setMaintMode(true);
                  element.getAugParent().removeFromAugChildren(element);
                  element.setAugParent((HierarchyElementDAG)null);
                  this.mHierarchyDAG.setMaintMode(false);
               }

               if(parent.getAugChildren() != null) {
                  this.mHierarchyDAG.setMaintMode(true);
                  if(index > parent.getAugChildren().size()) {
                     index = parent.getAugChildren().size();
                  }

                  parent.addAug(index, element);
                  this.mHierarchyDAG.setMaintMode(false);
               }
            }

            if(!this.isInsideChangeManagement()) {
               this.addEvent(new MoveHierarchyElementFeedEvent(event.getParentKey(), parent.getVisId(), event.getIndex(), event.getElementKey(), element.getVisId()));
            }

         }
      }
   }

   private int getTempElementNumber() {
      return this.mTemporaryElementIndex--;
   }

   private void addListener(Class cls, HierarchyEventListener listener) {
      this.mListeners.add(cls);
      this.mListeners.add(listener);
   }

   private void dispatchEvent(HierarchyEvent e) throws CPException, ValidationException {
      for(int i = 0; i < this.mListeners.size(); i += 2) {
         Class cls = (Class)this.mListeners.get(i);
         if(cls.equals(e.getClass())) {
            HierarchyEventListener listener = (HierarchyEventListener)this.mListeners.get(i + 1);
            listener.dispatchEvent(e);
         }
      }

   }

   public void updateEVOFromRemovedElementList(HierarchyEVO hEVO) {
      Iterator i = this.mRemovedElements.iterator();

      while(i.hasNext()) {
         HierarchyNodeDAG node = (HierarchyNodeDAG)i.next();
         if(!node.isFeeder()) {
            HierarchyElementDAG element = (HierarchyElementDAG)node;
            element.removeFromEVO(hEVO);
         } else {
            HierarchyElementFeedDAG element1 = (HierarchyElementFeedDAG)node;
            element1.removeFromEVO(hEVO);
         }
      }

      this.mRemovedElements.clear();
   }

   private void registerRemovedElements(HierarchyNodeDAG element) {
      this.mRemovedElements.add(element);
   }

   private void addEvent(HierarchyEvent event) {
      this.mOutputEvents.add(event);
   }

   private InitialContext getInitialContext() throws Exception {
      if(this.mInitialContext == null) {
         this.mInitialContext = new InitialContext();
      }

      return this.mInitialContext;
   }

   public DAGContext getDAGContext() throws CPException {
      if(this.mDAGContext == null) {
         try {
            this.mDAGContext = new DAGContext(this.getInitialContext());
         } catch (Exception var2) {
            throw new CPException(var2.getMessage(), var2);
         }
      }

      return this.mDAGContext;
   }

   public HierarchyDAG getHierarchyDAG() {
      return this.mHierarchyDAG;
   }

   public void setHierarchyDAG(HierarchyDAG hierarchyDAG) {
      this.mHierarchyDAG = hierarchyDAG;
   }

   public DimensionDAG getDimensionDAG() {
      return this.mDimensionDAG;
   }

   public void setDimensionDAG(DimensionDAG dimensionDAG) {
      this.mDimensionDAG = dimensionDAG;
   }

   private DimensionAccessor getDimensionAccessor() throws Exception {
      return this.getDAGContext().getDimensionAccessor();
   }

   public boolean isAugmentMode() {
      return this.mAugmentMode;
   }

   public void setAugmentMode(Boolean augmentMode) {
      if(augmentMode == null) {
         this.mAugmentMode = Boolean.FALSE.booleanValue();
      } else {
         this.mAugmentMode = augmentMode.booleanValue();
      }

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

   public int countElements() {
      return this.mHierarchyDAG.countElements();
   }

   // $FF: synthetic method
   static void accessMethod000(HierarchyEditorEngine x0, InsertHierarchyElementEvent x1) throws CPException, ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod100(HierarchyEditorEngine x0, RemoveHierarchyElementEvent x1) throws CPException, ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod200(HierarchyEditorEngine x0, UpdateHierarchyElementEvent x1) throws CPException, ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod300(HierarchyEditorEngine x0, MoveHierarchyElementEvent x1) throws CPException, ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod400(HierarchyEditorEngine x0, MoveHierarchyElementFeedEvent x1) throws CPException, ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod500(HierarchyEditorEngine x0, DimensionSelectionEvent x1) throws CPException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod600(HierarchyEditorEngine x0, InsertHierarchyElementFeedEvent x1) throws ValidationException, CPException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod700(HierarchyEditorEngine x0, RemoveHierarchyElementFeedEvent x1) throws CPException, ValidationException {
      x0.processEvent(x1);
   }
}
