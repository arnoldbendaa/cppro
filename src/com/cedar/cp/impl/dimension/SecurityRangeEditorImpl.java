// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.SecurityRange;
import com.cedar.cp.api.dimension.SecurityRangeEditor;
import com.cedar.cp.api.dimension.SecurityRangeRow;
import com.cedar.cp.api.dimension.SecurityRangeRowEditor;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionSSO;
import com.cedar.cp.dto.dimension.SecurityRangeImpl;
import com.cedar.cp.dto.dimension.SecurityRangeRowImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.dimension.SecurityRangeAdapter;
import com.cedar.cp.impl.dimension.SecurityRangeEditorSessionImpl;
import com.cedar.cp.impl.dimension.SecurityRangeRowEditorImpl;
import com.cedar.cp.util.StringUtils;
import java.util.Collections;
import java.util.List;

public class SecurityRangeEditorImpl extends BusinessEditorImpl implements SecurityRangeEditor, SubBusinessEditorOwner {

   private SecurityRangeRowEditor mSubEditor;
   private SecurityRangeEditorSessionSSO mServerSessionData;
   private SecurityRangeImpl mEditorData;
   private SecurityRangeAdapter mEditorDataAdapter;


   public SecurityRangeEditorImpl(SecurityRangeEditorSessionImpl session, SecurityRangeEditorSessionSSO serverSessionData, SecurityRangeImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(SecurityRangeEditorSessionSSO serverSessionData, SecurityRangeImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
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

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a SecurityRange");
      } else if(newVisId != null && newVisId.trim().length() != 0) {
         if(newVisId.indexOf(32) != -1) {
            throw new ValidationException("A security range visual id must not contain spaces");
         }
      } else {
         throw new ValidationException("An identifier must be supplied");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a SecurityRange");
      } else if(newDescription == null || newDescription.trim().length() == 0) {
         throw new ValidationException("A description must be supplied");
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
   }

   public EntityList getOwnershipRefs() {
      return ((SecurityRangeEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public SecurityRange getSecurityRange() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new SecurityRangeAdapter((SecurityRangeEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      this.validateVisId(this.mEditorData.getVisId());
      this.validateDescription(this.mEditorData.getDescription());
      this.validateRanges();
   }

   public void setModelRef(ModelRef modelRef) throws ValidationException {
      this.mEditorData.setModelRef((ModelRefImpl)modelRef);
      this.setContentModified();
   }

   public SecurityRangeRowEditor getEditor(SecurityRangeRow row) throws ValidationException {
      if(this.mSubEditor != null) {
         throw new IllegalStateException("Only one subeditor supported at a time");
      } else {
         this.mSubEditor = new SecurityRangeRowEditorImpl(this, (SecurityRangeRowImpl)row);
         return this.mSubEditor;
      }
   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {
      if(this.mSubEditor != editor) {
         throw new CPException("Unknown sub editor in removeSubBusinessEditor");
      } else {
         this.mSubEditor = null;
      }
   }

   public void insertRow(SecurityRangeRowImpl row) throws ValidationException {
      Collections.sort(this.mEditorData.getRanges());
      int index = Collections.binarySearch(this.mEditorData.getRanges(), row);
      if(index >= 0) {
         throw new ValidationException("This exact range is already in the list");
      } else {
         index = -(index + 1);
         SecurityRangeRow nextRow;
         if(index > 0) {
            nextRow = (SecurityRangeRow)this.mEditorData.getRanges().get(index - 1);
            if(nextRow.getTo().compareTo(row.getFrom()) >= 0) {
               throw new ValidationException("Overlaps with range [" + nextRow.getFrom() + "] to [" + nextRow.getTo() + "]");
            }
         }

         if(index < this.mEditorData.getRanges().size()) {
            nextRow = (SecurityRangeRow)this.mEditorData.getRanges().get(index);
            if(row.getTo().compareTo(nextRow.getFrom()) >= 0) {
               throw new ValidationException("Overlaps with range [" + nextRow.getFrom() + "] to [" + nextRow.getTo() + "]");
            }
         }

         this.mEditorData.getRanges().add(index, row);
         this.setContentModified();
      }
   }

   public void updateRow(SecurityRangeRowImpl row, String from, String to) throws ValidationException {
      int index = this.mEditorData.getRanges().indexOf(row);
      this.mEditorData.getRanges().remove(row);

      try {
         SecurityRangeRowImpl e = new SecurityRangeRowImpl();
         e.setFrom(from);
         e.setTo(to);
         this.insertRow(e);
      } catch (ValidationException var7) {
         this.mEditorData.getRanges().add(index - 1, row);
         throw var7;
      }
   }

   public void removeRow(SecurityRangeRow row) throws ValidationException {
      this.mEditorData.getRanges().remove(row);
      this.setContentModified();
   }

   public EntityList getModelAndDimensionOwners() throws CPException {
      return this.getConnection().getListHelper().getAllModelBusAndAccDimensions();
   }

   private void validateRanges() throws ValidationException {
      List ranges = this.mEditorData.getRanges();

      for(int row = 1; row < ranges.size(); ++row) {
         SecurityRangeRowImpl srr1 = (SecurityRangeRowImpl)ranges.get(row - 1);
         SecurityRangeRowImpl srr2 = (SecurityRangeRowImpl)ranges.get(row);
         if(srr1.getTo().compareTo(srr2.getFrom()) >= 0) {
            throw new ValidationException("Range[" + srr1.getFrom() + "] to [" + srr1.getTo() + "] overlaps\n" + "Range[" + srr2.getFrom() + "] to [" + srr2.getTo() + "]");
         }
      }

   }
}
