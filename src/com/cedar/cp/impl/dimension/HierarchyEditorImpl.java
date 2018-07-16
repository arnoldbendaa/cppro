// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.api.dimension.HierarchyEditor;
import com.cedar.cp.api.dimension.HierarchyElement;
import com.cedar.cp.api.dimension.HierarchyElementEditor;
import com.cedar.cp.api.dimension.HierarchyEvent;
import com.cedar.cp.api.dimension.HierarchyEventListener;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.AugHierarchyElementImpl;
import com.cedar.cp.dto.dimension.DimensionElementListModel;
import com.cedar.cp.dto.dimension.DimensionSelectionEvent;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionSSO;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedImpl;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
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
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionServer;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.dimension.HierarchyAdapter;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$1;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$10;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$11;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$2;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$3;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$4;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$5;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$6;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$7;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$8;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl$9;
import com.cedar.cp.impl.dimension.HierarchyEditorSessionImpl;
import com.cedar.cp.impl.dimension.HierarchyElementEditorImpl;
import com.cedar.cp.util.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.tree.DefaultTreeModel;

public class HierarchyEditorImpl extends BusinessEditorImpl implements HierarchyEditor, SubBusinessEditorOwner {

   private List mListeners = new ArrayList();
   private HierarchyElementEditorImpl mElementEditor;
   private HierarchyEditorSessionSSO mServerSessionData;
   private HierarchyImpl mEditorData;
   private HierarchyAdapter mEditorDataAdapter;


   public HierarchyEditorImpl(HierarchyEditorSessionImpl session, HierarchyEditorSessionSSO serverSessionData, HierarchyImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
      this.addListener(InsertHierarchyElementEvent.class, new HierarchyEditorImpl$1(this));
      this.addListener(RemoveHierarchyElementEvent.class, new HierarchyEditorImpl$2(this));
      this.addListener(MoveHierarchyElementEvent.class, new HierarchyEditorImpl$3(this));
      this.addListener(MoveHierarchyElementFeedEvent.class, new HierarchyEditorImpl$4(this));
      this.addListener(UpdateHierarchyElementEvent.class, new HierarchyEditorImpl$5(this));
      this.addListener(NewHierarchyEvent.class, new HierarchyEditorImpl$6(this));
      this.addListener(NewDimensionElementListEvent.class, new HierarchyEditorImpl$7(this));
      this.addListener(InsertHierarchyElementFeedEvent.class, new HierarchyEditorImpl$8(this));
      this.addListener(RemoveHierarchyElementFeedEvent.class, new HierarchyEditorImpl$9(this));
      this.addListener(FreeDimensionElementsEvent.class, new HierarchyEditorImpl$10(this));
      this.addListener(UpdateHierarchyElementChildOrderEvent.class, new HierarchyEditorImpl$11(this));
      this.dispatchServerEvents(this.mServerSessionData.getEvents());
   }

   public void updateEditorData(HierarchyEditorSessionSSO serverSessionData, HierarchyImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setDimensionId(int newDimensionId) throws ValidationException {
      this.validateDimensionId(newDimensionId);
      if(this.mEditorData.getDimensionId() != newDimensionId) {
         this.setContentModified();
         this.mEditorData.setDimensionId(newDimensionId);
      }
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.setContentModified();
         this.mEditorData.setVisId(newVisId);
      }
   }

   public void setDescription(String newDescription) throws ValidationException {
      if(newDescription != null) {
         newDescription = StringUtils.rtrim(newDescription);
      }

      this.validateDescription(newDescription);
      if(this.mEditorData.getDescription() == null || !this.mEditorData.getDescription().equals(newDescription)) {
         this.setContentModified();
         this.mEditorData.setDescription(newDescription);
      }
   }

   public void validateDimensionId(int newDimensionId) throws ValidationException {}

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 62) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 62 on a Hierarchy - "+newVisId );
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a Hierarchy");
      }
   }

   public void setDimensionRef(DimensionRef ref) throws ValidationException {
      DimensionRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getDimensionEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getDimensionRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getDimensionRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setDimensionRef(actualRef);
      this.setContentModified();
      this.queryNewHierarchyForDimension(actualRef);
   }

   public EntityList getOwnershipRefs() {
      return ((HierarchyEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public Hierarchy getHierarchy() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new HierarchyAdapter((HierarchyEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public ModelRef getModel() {
      return this.mEditorData.getModel();
   }

   public HierarchyElementEditor getElementEditor(HierarchyElement element) throws ValidationException, CPException {
      if(this.mElementEditor != null) {
         throw new ValidationException("Multiple HierarchyElementEditor\'s are not supported");
      } else {
         this.mElementEditor = new HierarchyElementEditorImpl(this.getBusinessSession(), this, (HierarchyElementImpl)element);
         return this.mElementEditor;
      }
   }

   private void processEvent(HierarchyEvent event) {
      System.out.println("Encountered unknow event " + event + " skipping...");
   }

   private void insertElement(HierarchyElementImpl element, HierarchyElementImpl parent, int index) {
      element.setHierarchy(this.mEditorData);
      parent.addChildElement(index, element);
      DefaultTreeModel model = (DefaultTreeModel)this.mEditorData.getTreeModel();
      model.nodesWereInserted(parent, new int[]{index});
      this.setContentModified();
   }

   private void insertElement(HierarchyElementFeedImpl element, HierarchyElementImpl parent, int index) {
      element.setHierarchy(this.mEditorData);
      parent.addChildElement(index, element);
      DefaultTreeModel model = (DefaultTreeModel)this.mEditorData.getTreeModel();
      model.nodesWereInserted(parent, new int[]{index});
      this.setContentModified();
   }

   private void detachElement(HierarchyNode element) throws ValidationException {
      if(!element.isAugmentElement()) {
         throw new ValidationException("Attempt to remove non augment element in augment mode");
      } else {
         AugHierarchyElementImpl he = (AugHierarchyElementImpl)element;
         he.detachFromParent();
         DefaultTreeModel model = (DefaultTreeModel)this.mEditorData.getTreeModel();
         model.nodeStructureChanged(he.getParent());
         this.setContentModified();
      }
   }

   private void moveElement(HierarchyNode element, HierarchyElementImpl newParent, int index) {
      HierarchyElementImpl oldParent = (HierarchyElementImpl)element.getParent();
      DefaultTreeModel model = (DefaultTreeModel)this.mEditorData.getTreeModel();
      if(oldParent.equals(newParent) && newParent.getIndex(element) < index) {
         --index;
      }

      int oldIndex = oldParent.getIndex(element);
      oldParent.removeChildElement(element);
      model.nodesWereRemoved(oldParent, new int[]{oldIndex}, new Object[]{element});
      newParent.addChildElement(index, element);
      model.nodesWereInserted(newParent, new int[]{index});
   }

   private void removeElement(HierarchyNode element) {
      HierarchyElementImpl parent = (HierarchyElementImpl)element.getParent();
      DefaultTreeModel model = (DefaultTreeModel)this.mEditorData.getTreeModel();
      if(parent == null) {
         this.mEditorData.setRoot((HierarchyElementImpl)null);
         model.nodeStructureChanged(element);
      } else {
         int index = parent.getIndex(element);
         parent.removeChildElement(element);
         model.nodesWereRemoved(parent, new int[]{index}, new Object[]{element});
      }

      this.setContentModified();
   }

   private void updateElementChildOrder(HierarchyElementImpl element, Object[] childKeys) {
      DefaultTreeModel model = (DefaultTreeModel)this.mEditorData.getTreeModel();

      for(int i = 0; i < childKeys.length; ++i) {
         HierarchyNode node = element.findElement(childKeys[i]);
         if(node == null) {
            throw new CPException("Failed to locate node:" + childKeys[i]);
         }

         int oldIndex = element.getIndex(node);
         element.removeChildElement(node);
         model.nodesWereRemoved(element, new int[]{oldIndex}, new Object[]{node});
         element.addChildElement(i, node);
         model.nodesWereInserted(element, new int[]{i});
      }

   }

   private void processEvent(InsertHierarchyElementEvent event) {
      Object parentId = event.getParentKey();
      String visId = event.getVisId();
      String description = event.getDescription();
      int index = event.getIndex();
      Object elementId = event.getElementKey();
      HierarchyElementImpl parent = (HierarchyElementImpl)this.mEditorData.findElement(parentId);
      if(parent == null) {
         throw new IllegalArgumentException("Unable to find node " + parentId);
      } else {
         if(this.isAugmentMode()) {
            AugHierarchyElementImpl newElement = new AugHierarchyElementImpl(elementId);
            newElement.setVisId(visId);
            newElement.setDescription(description);
            this.insertElement((HierarchyElementImpl)newElement, parent, index);
         } else {
            HierarchyElementImpl newElement1 = new HierarchyElementImpl(elementId);
            newElement1.setVisId(visId);
            newElement1.setDescription(description);
            this.insertElement(newElement1, parent, index);
         }

         this.setContentModified();
      }
   }

   private void processEvent(RemoveHierarchyElementFeedEvent event) {
      Object id = event.getElementKey();
      HierarchyNode element = this.mEditorData.findElement(id);
      if(element == null) {
         throw new IllegalArgumentException("Unable to find node " + id);
      } else {
         try {
            this.removeElement(element);
         } catch (Exception var5) {
            var5.printStackTrace();
         }

         DimensionElementListModel delm = (DimensionElementListModel)((HierarchyEditorSessionImpl)this.getBusinessSession()).getDimensionElements();
         delm.add(event.getDimensionElement());
         this.setContentModified();
      }
   }

   private void processEvent(RemoveHierarchyElementEvent event) throws ValidationException {
      Object id = event.getElementKey();
      HierarchyNode element = this.mEditorData.findElement(id);
      if(element == null) {
         throw new IllegalArgumentException("Unable to find node " + id);
      } else {
         if(this.isAugmentMode()) {
            this.detachElement(element);
         } else {
            this.removeElement(element);
         }

         this.setContentModified();
      }
   }

   private void processEvent(UpdateHierarchyElementEvent event) {
      Object id = event.getElementKey();
      String visId = event.getVisId();
      HierarchyElementImpl element = (HierarchyElementImpl)this.mEditorData.findElement(id);
      if(element == null) {
         throw new IllegalArgumentException("Unable to find node " + id);
      } else {
         element.setVisId(visId);
         if(event.getDescription() != null && event.getDescription().length() > 0) {
            element.setDescription(event.getDescription());
         }

         if(event.getCreditDebit() != null) {
            element.setCreditDebit(event.getCreditDebit().intValue());
         }

         if(event.getAugCreditDebit() != null) {
            element.setAugCreditDebit(event.getAugCreditDebit().intValue());
         }

         DefaultTreeModel model = (DefaultTreeModel)this.mEditorData.getTreeModel();
         model.nodeChanged(element);
         this.setContentModified();
      }
   }

   private void processEvent(MoveHierarchyElementEvent event) {
      Object id = event.getElementKey();
      Object parentId = event.getParentKey();
      int index = event.getIndex();
      HierarchyElementImpl element = (HierarchyElementImpl)this.mEditorData.findElement(id);
      if(element == null) {
         throw new IllegalArgumentException("Unable to find node " + id);
      } else {
         HierarchyElementImpl parent = (HierarchyElementImpl)this.mEditorData.findElement(parentId);
         if(parent == null) {
            throw new IllegalArgumentException("Unable to find node " + parentId);
         } else {
            HierarchyElementImpl oldParent = (HierarchyElementImpl)element.getParent();
            this.moveElement((HierarchyNode)element, parent, index);
            this.setContentModified();
         }
      }
   }

   private void processEvent(MoveHierarchyElementFeedEvent event) {
      Object id = event.getElementKey();
      Object parentId = event.getParentKey();
      int index = event.getIndex();
      HierarchyElementFeedImpl element = (HierarchyElementFeedImpl)this.mEditorData.findElement(id);
      if(element == null) {
         throw new IllegalArgumentException("Unable to find node " + id);
      } else {
         HierarchyElementImpl parent = (HierarchyElementImpl)this.mEditorData.findElement(parentId);
         if(parent == null) {
            throw new IllegalArgumentException("Unable to find node " + parentId);
         } else {
            HierarchyElementImpl oldParent = (HierarchyElementImpl)element.getParent();
            this.moveElement((HierarchyNode)element, parent, index);
            this.setContentModified();
         }
      }
   }

   private void processEvent(NewHierarchyEvent event) {
      ((HierarchyEditorSessionImpl)this.getBusinessSession()).setEditorData(event.getHierarchy());
   }

   private void processEvent(NewDimensionElementListEvent event) {
      ((HierarchyEditorSessionImpl)this.getBusinessSession()).setDimensionElements(event.getDimensionElements());
   }

   private void processEvent(InsertHierarchyElementFeedEvent event) {
      Object parentId = event.getParentKey();
      DimensionElementRef dimensionElementRef = event.getDimensionElementRef();
      int index = event.getIndex();
      Object elementId = event.getElementKey();
      HierarchyElementImpl parent = (HierarchyElementImpl)this.mEditorData.findElement(parentId);
      if(parent == null) {
         throw new IllegalArgumentException("Unable to find node " + parentId);
      } else {
         HierarchyElementFeedImpl newElement = new HierarchyElementFeedImpl(elementId);
         newElement.setDescription(event.getDescription());
         newElement.setVisId(event.getDimensionElementRef().getNarrative());
         newElement.setChildIndex(event.getIndex());
         newElement.setCreditDebit(event.getCreditDebit());
         newElement.setAugCreditDebit(event.getAugCreditDebit());
         newElement.setDisabled(event.isDisabled());
         this.insertElement(newElement, parent, index);
         DimensionElementListModel availableDimensionElements = (DimensionElementListModel)((HierarchyEditorSessionImpl)this.getBusinessSession()).getDimensionElements();
         availableDimensionElements.remove(event.getDimensionElementRef());
         this.setContentModified();
      }
   }

   private void processEvent(FreeDimensionElementsEvent event) {
      DimensionElementListModel availableDimensionElements = (DimensionElementListModel)((HierarchyEditorSessionImpl)this.getBusinessSession()).getDimensionElements();

      for(int i = 0; i < event.size(); ++i) {
         DimensionElement dimensionElement = (DimensionElement)event.get(i);
         availableDimensionElements.add(dimensionElement);
      }

   }

   private void processEvent(UpdateHierarchyElementChildOrderEvent event) {
      Object id = event.getElementKey();
      HierarchyNode element = this.mEditorData.findElement(id);
      if(element == null) {
         throw new IllegalArgumentException("Unable to find node " + id);
      } else {
         this.updateElementChildOrder((HierarchyElementImpl)element, event.getChildKeys());
         this.setContentModified();
      }
   }

   private void dispatchServerEvents(List serverEvents) throws CPException {
      try {
         Iterator e = serverEvents.iterator();

         while(e.hasNext()) {
            this.dispatchEvent((HierarchyEvent)e.next());
         }

      } catch (Exception var3) {
         var3.printStackTrace();
         throw new CPException(var3.getMessage(), var3);
      }
   }

   private void sendAndProcessResponse(Object event) throws ValidationException, CPException {
      ArrayList clientEvents = new ArrayList();
      clientEvents.add(event);
      this.dispatchServerEvents(this.dispatchClientEvents(clientEvents));
   }

   public void insertElement(Object parent, int index) throws ValidationException, CPException {
      this.sendAndProcessResponse(new InsertHierarchyElementEvent(parent, "", (String)null, "", index, 2, 0, (Object)null));
   }

   public void removeElement(Object key) throws ValidationException, CPException {
      Object event;
      if(!(key instanceof HierarchyElementCK) && !(key instanceof HierarchyElementPK)) {
         if(!(key instanceof HierarchyElementFeedCK) && !(key instanceof HierarchyElementFeedPK)) {
            throw new ValidationException("Unexpected key type:" + key);
         }

         event = new RemoveHierarchyElementFeedEvent(key, (String)null);
      } else {
         event = new RemoveHierarchyElementEvent(key, (String)null);
      }

      this.sendAndProcessResponse(event);
   }

   /**
    * key       primary key of element to move
    * parent    primary key of target element
    * index     index where element to move will be put
    */
   public void moveElement(Object key, Object parent, int index) throws ValidationException, CPException {
      if(!(parent instanceof HierarchyElementFeedPK) && !(parent instanceof HierarchyElementFeedCK)) {
         Object event;
         if(!(key instanceof HierarchyElementFeedPK) && !(key instanceof HierarchyElementFeedCK)) {
            event = new MoveHierarchyElementEvent(parent, (String)null, index, key, (String)null);
         } else {
            event = new MoveHierarchyElementFeedEvent(parent, (String)null, index, key, (String)null);
         }

         this.sendAndProcessResponse(event);
      } else {
         throw new ValidationException("Feeder elements may not contain any child elements");
      }
   }

   public void updateElement(Object key, String name, String description, int creditDebit, int augCreditDebit) throws ValidationException, CPException {
      this.sendAndProcessResponse(new UpdateHierarchyElementEvent(key, (String)null, name, description, Integer.valueOf(creditDebit), Integer.valueOf(augCreditDebit)));
   }

   private void queryNewHierarchyForDimension(DimensionRef dimensionRef) throws ValidationException, CPException {
      this.sendAndProcessResponse(new DimensionSelectionEvent(dimensionRef));
   }

   public ListModel getAvailableDimensionElementRefs() {
      return ((HierarchyEditorSessionImpl)this.getBusinessSession()).getDimensionElements();
   }

   public void insertDimensionElement(Object parent, int index, DimensionElementRef dimensionElementRef) throws ValidationException, CPException {
      this.sendAndProcessResponse(new InsertHierarchyElementFeedEvent(parent, (String)null, index, dimensionElementRef));
   }

   protected List dispatchClientEvents(List clientEvents) throws ValidationException, CPException {
      HierarchyEditorSessionServer session = ((HierarchyEditorSessionImpl)this.getBusinessSession()).getSessionServer();
      return session.processEvents(clientEvents);
   }

   private void addListener(Class cls, HierarchyEventListener listener) {
      this.mListeners.add(cls);
      this.mListeners.add(listener);
   }

   private void dispatchEvent(HierarchyEvent e) throws Exception {
      for(int i = 0; i < this.mListeners.size(); i += 2) {
         Class cls = (Class)this.mListeners.get(i);
         if(cls.equals(e.getClass())) {
            HierarchyEventListener listener = (HierarchyEventListener)this.mListeners.get(i + 1);
            listener.dispatchEvent(e);
         }
      }

   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {
      if(editor instanceof HierarchyElementEditorImpl) {
         if(editor != this.mElementEditor) {
            throw new CPException("Attempt to remove unknown sub editor : " + editor);
         } else {
            this.mElementEditor = null;
         }
      } else {
         throw new CPException("Attempt to remove unknown sub editor : " + editor);
      }
   }

   public void setHierarchy(HierarchyImpl hierarchy) {
      this.mEditorData = hierarchy;
   }

   public void setSubmitChangeManagementRequest(boolean b) {
      this.mEditorData.setSubmitChangeManagementRequest(b);
   }

   public boolean isAugmentMode() {
      return this.mEditorData.getExternalSystemRef() != null;
   }

   // $FF: synthetic method
   static void accessMethod000(HierarchyEditorImpl x0, InsertHierarchyElementEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod100(HierarchyEditorImpl x0, RemoveHierarchyElementEvent x1) throws ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod200(HierarchyEditorImpl x0, MoveHierarchyElementEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod300(HierarchyEditorImpl x0, MoveHierarchyElementFeedEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod400(HierarchyEditorImpl x0, UpdateHierarchyElementEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod500(HierarchyEditorImpl x0, NewHierarchyEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod600(HierarchyEditorImpl x0, NewDimensionElementListEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod700(HierarchyEditorImpl x0, InsertHierarchyElementFeedEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod800(HierarchyEditorImpl x0, RemoveHierarchyElementFeedEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod900(HierarchyEditorImpl x0, FreeDimensionElementsEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod1000(HierarchyEditorImpl x0, UpdateHierarchyElementChildOrderEvent x1) {
      x0.processEvent(x1);
   }
}
