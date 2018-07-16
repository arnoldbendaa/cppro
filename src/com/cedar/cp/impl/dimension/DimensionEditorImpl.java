// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.dimension.Dimension;
import com.cedar.cp.api.dimension.DimensionEditor;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionElementEditor;
import com.cedar.cp.api.dimension.DimensionEvent;
import com.cedar.cp.api.dimension.DimensionEventListener;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.ListModelImpl;
import com.cedar.cp.dto.dimension.DimensionEditorSessionSSO;
import com.cedar.cp.dto.dimension.DimensionElementImpl;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.event.ContextEvent;
import com.cedar.cp.dto.dimension.event.InsertDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.UpdateDimensionElementEvent;
import com.cedar.cp.ejb.api.dimension.DimensionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.dimension.DimensionAdapter;
import com.cedar.cp.impl.dimension.DimensionEditorImpl$1;
import com.cedar.cp.impl.dimension.DimensionEditorImpl$2;
import com.cedar.cp.impl.dimension.DimensionEditorImpl$3;
import com.cedar.cp.impl.dimension.DimensionEditorSessionImpl;
import com.cedar.cp.impl.dimension.DimensionElementEditorImpl;
import com.cedar.cp.util.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DimensionEditorImpl extends BusinessEditorImpl implements DimensionEditor, SubBusinessEditorOwner {

   private List mListeners = new ArrayList();
   private DimensionElementEditorImpl mElementEditor;
   private DimensionEditorSessionSSO mServerSessionData;
   private DimensionImpl mEditorData;
   private DimensionAdapter mEditorDataAdapter;


   public DimensionEditorImpl(DimensionEditorSessionImpl session, DimensionEditorSessionSSO serverSessionData, DimensionImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
      this.addListener(InsertDimensionElementEvent.class, new DimensionEditorImpl$1(this));
      this.addListener(RemoveDimensionElementEvent.class, new DimensionEditorImpl$2(this));
      this.addListener(UpdateDimensionElementEvent.class, new DimensionEditorImpl$3(this));
      if(this.getConnection().getConnectionContext() == ConnectionContext.SERVER_SESSION) {
         ArrayList clientEvents = new ArrayList();
         clientEvents.add(new ContextEvent(ConnectionContext.SERVER_SESSION));

         try {
            this.dispatchServerEvents(this.dispatchClientEvents(clientEvents));
         } catch (ValidationException var6) {
            throw new IllegalStateException("Unexpected validation exception:", var6);
         }
      }

   }

   public void updateEditorData(DimensionEditorSessionSSO serverSessionData, DimensionImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setType(int newType) throws ValidationException {
      this.validateType(newType);
      if(this.mEditorData.getType() != newType) {
         this.setContentModified();
         this.mEditorData.setType(newType);
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

   public void setExternalSystemRef(Integer newExternalSystemRef) throws ValidationException {
      this.validateExternalSystemRef(newExternalSystemRef);
      if(this.mEditorData.getExternalSystemRef() == null || !this.mEditorData.getExternalSystemRef().equals(newExternalSystemRef)) {
         this.setContentModified();
         this.mEditorData.setExternalSystemRef(newExternalSystemRef);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 54) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 54 on a Dimension");
      } else if(newVisId == null || newVisId.trim().length() == 0) {
         throw new ValidationException("An identifier must be supplied");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a Dimension");
      } else if(newDescription == null || newDescription.trim().length() == 0) {
         throw new ValidationException("A description must be supplied");
      }
   }

   public void validateType(int newType) throws ValidationException {
      if(newType != 1 && newType != 2 && newType != 3) {
         throw new ValidationException("Invalid type");
      }
   }

   public void validateExternalSystemRef(Integer newExternalSystemRef) throws ValidationException {}

   public Dimension getDimension() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new DimensionAdapter((DimensionEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
      this.mEditorData.setConnectionContext(this.getConnection().getConnectionContext());
   }

   private void saveValidation() throws ValidationException {}

   public DimensionElementEditor getElementEditor(Object key) throws ValidationException, CPException {
      if(this.mElementEditor != null) {
         throw new CPException("Multiple DimensionElementEditor\'s are not supported");
      } else {
         DimensionElementImpl element = (DimensionElementImpl)this.mEditorData.getDimensionElement(key);
         if(element == null) {
            throw new ValidationException("Dimension Element not found:" + key);
         } else {
            this.mElementEditor = new DimensionElementEditorImpl(this.getBusinessSession(), this, element);
            return this.mElementEditor;
         }
      }
   }

   private void processEvent(DimensionEvent event) {
      System.out.println("Encountered unknow event " + event + " skipping...");
   }

   private void insertElement(DimensionElementImpl element) throws ValidationException {
      element.setDimension(this.mEditorData);
      this.mEditorData.addDimensionElement(element);
      if(element.isNullElement()) {
         this.mEditorData.setNullElement(element);
      }

      this.setContentModified();
   }

   private void removeElement(DimensionElementImpl element) {
      this.mEditorData.removeDimensionElement(element);
      if(element.isNullElement()) {
         this.mEditorData.setNullElement((DimensionElement)null);
      }

      this.setContentModified();
   }

   private void processEvent(InsertDimensionElementEvent event) throws ValidationException {
      Object elementId = event.getElementKey();
      String visId = event.getVisId();
      String description = event.getDescription();
      DimensionElementImpl newElement = new DimensionElementImpl();
      newElement.setKey(elementId);
      newElement.setVisId(visId);
      newElement.setDescription(description);
      newElement.setCreditDebit(event.getCreditDebit());
      newElement.setAugCreditDebit(event.getAugCreditDebit());
      newElement.setNotPlannable(event.isNotPlannable());
      newElement.setNullElement(event.isIsNullElement());
      this.insertElement(newElement);
      this.setContentModified();
   }

   private void processEvent(RemoveDimensionElementEvent event) {
      DimensionElementImpl element = (DimensionElementImpl)this.mEditorData.getDimensionElement(event.getElementKey());
      if(element == null) {
         throw new IllegalArgumentException("Unable to find node " + event.getElementKey());
      } else {
         try {
            this.removeElement(element);
         } catch (Exception var4) {
            throw new IllegalStateException(var4.getMessage());
         }

         this.setContentModified();
      }
   }

   private void processEvent(UpdateDimensionElementEvent event) {
      Object id = event.getElementKey();
      String visId = event.getVisId();
      DimensionElementImpl element = (DimensionElementImpl)this.mEditorData.getDimensionElement(id);
      if(element == null) {
         throw new IllegalArgumentException("Unable to find node " + id);
      } else {
         element.setVisId(visId);
         element.setDescription(event.getDescription());
         if(event.getCreditDebit() != null) {
            element.setCreditDebit(event.getCreditDebit().intValue());
         }

         if(event.isDisabled() != null) {
            element.setDisabled(event.isDisabled().booleanValue());
         }

         if(event.getAugCreditDebit() != null) {
            element.setAugCreditDebit(event.getAugCreditDebit().intValue());
         }

         if(event.isNotPlannable() != null) {
            element.setNotPlannable(event.isNotPlannable().booleanValue());
         }

         if(event.isNullElement() != null) {
            element.setNullElement(event.isNullElement().booleanValue());
         }

         if(this.mEditorData.getListModel() != null) {
            ((ListModelImpl)this.mEditorData.getListModel()).fireItemChanged(element);
         }

         this.setContentModified();
      }
   }

   private void dispatchServerEvents(List serverEvents) throws CPException, ValidationException {
      Iterator i = serverEvents.iterator();

      while(i.hasNext()) {
         this.dispatchEvent((DimensionEvent)i.next());
      }

   }

   public DimensionElement insertElement(String visId, String description, int creditDebitType, boolean disabled, boolean notPlannable, int creditDebitOverride) throws DuplicateNameValidationException, ValidationException, CPException {
      this.validateVisId(visId);
      this.validateDescription(description);
      InsertDimensionElementEvent insertDimensionElementEvent = new InsertDimensionElementEvent((Object)null, visId, description, creditDebitType, creditDebitOverride, notPlannable, disabled, false);
      ArrayList clientEvents = new ArrayList();
      clientEvents.add(insertDimensionElementEvent);
      this.dispatchServerEvents(this.dispatchClientEvents(clientEvents));
      return this.mEditorData.getDimensionElement(visId);
   }

   public DimensionElement insertElement(String visId, String description, int creditDebitType, boolean disabled, boolean notPlannable, int creditDebitOverride, boolean isNullElement) throws DuplicateNameValidationException, ValidationException, CPException {
      this.validateVisId(visId);
      this.validateDescription(description);
      InsertDimensionElementEvent insertDimensionElementEvent = new InsertDimensionElementEvent((Object)null, visId, description, creditDebitType, creditDebitOverride, notPlannable, disabled, isNullElement);
      ArrayList clientEvents = new ArrayList();
      clientEvents.add(insertDimensionElementEvent);
      this.dispatchServerEvents(this.dispatchClientEvents(clientEvents));
      return this.mEditorData.getDimensionElement(visId);
   }

   public void removeElement(Object key, String visId) throws ValidationException, CPException {
      ArrayList clientEvents = new ArrayList();
      clientEvents.add(new RemoveDimensionElementEvent(key, visId));
      this.dispatchServerEvents(this.dispatchClientEvents(clientEvents));
   }

   public void updateElement(Object key, String name, String description, int creditDebit, int augCreditDebit, boolean notPlannable, boolean disabled, boolean nullElement) throws ValidationException, CPException {
      ArrayList clientEvents = new ArrayList();
      clientEvents.add(new UpdateDimensionElementEvent(key, (String)null, name, description, Integer.valueOf(creditDebit), Integer.valueOf(augCreditDebit), Boolean.valueOf(notPlannable), Boolean.valueOf(disabled), Boolean.valueOf(nullElement)));
      this.dispatchServerEvents(this.dispatchClientEvents(clientEvents));
   }

   protected List dispatchClientEvents(List events) throws ValidationException, CPException {
      DimensionEditorSessionServer session = ((DimensionEditorSessionImpl)this.getBusinessSession()).getSessionServer();
      return session.processEvents(events);
   }

   private void addListener(Class cls, DimensionEventListener listener) {
      this.mListeners.add(cls);
      this.mListeners.add(listener);
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

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {
      if(editor instanceof DimensionElementEditorImpl) {
         if(editor != this.mElementEditor) {
            throw new CPException("Attempt to remove unknown sub editor : " + editor);
         } else {
            this.mElementEditor = null;
         }
      } else {
         throw new CPException("Attempt to remove unknown sub editor : " + editor);
      }
   }

   public ModelRef queryOwningModel() {
      return ((DimensionEditorSessionImpl)this.getBusinessSession()).mServerSessionData.getModelRef();
   }

   public void setSubmitChangeManagementRequest(boolean b) {
      this.mEditorData.setSubmitChangeManagementRequest(b);
   }

   public boolean isAugmentMode() {
      return this.getConnection().getConnectionContext() == ConnectionContext.INTERACTIVE_GUI && this.mServerSessionData.getEditorData().getExternalSystemRef() != null;
   }

   // $FF: synthetic method
   static void accessMethod000(DimensionEditorImpl x0, InsertDimensionElementEvent x1) throws ValidationException {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod100(DimensionEditorImpl x0, RemoveDimensionElementEvent x1) {
      x0.processEvent(x1);
   }

   // $FF: synthetic method
   static void accessMethod200(DimensionEditorImpl x0, UpdateDimensionElementEvent x1) {
      x0.processEvent(x1);
   }
}
