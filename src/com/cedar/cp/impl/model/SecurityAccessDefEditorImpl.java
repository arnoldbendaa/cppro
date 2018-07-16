// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.SecurityRangeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityAccessDef;
import com.cedar.cp.api.model.SecurityAccessDefEditor;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.SecurityAccessDefEditorSessionSSO;
import com.cedar.cp.dto.model.SecurityAccessDefImpl;
import com.cedar.cp.dto.model.SecurityAccessExpressionParser;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.SecurityAccessDefAdapter;
import com.cedar.cp.impl.model.SecurityAccessDefEditorImpl$1;
import com.cedar.cp.impl.model.SecurityAccessDefEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class SecurityAccessDefEditorImpl extends BusinessEditorImpl implements SecurityAccessDefEditor {

   private Map mRanges;
   private SecurityAccessDefEditorSessionSSO mServerSessionData;
   private SecurityAccessDefImpl mEditorData;
   private SecurityAccessDefAdapter mEditorDataAdapter;


   public SecurityAccessDefEditorImpl(SecurityAccessDefEditorSessionImpl session, SecurityAccessDefEditorSessionSSO serverSessionData, SecurityAccessDefImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(SecurityAccessDefEditorSessionSSO serverSessionData, SecurityAccessDefImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setAccessMode(int newAccessMode) throws ValidationException {
      this.validateAccessMode(newAccessMode);
      if(this.mEditorData.getAccessMode() != newAccessMode) {
         this.setContentModified();
         this.mEditorData.setAccessMode(newAccessMode);
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

   public void setExpression(String newExpression) throws ValidationException {
      if(newExpression != null) {
         newExpression = StringUtils.rtrim(newExpression);
      }

      this.validateExpression(newExpression);
      if(this.mEditorData.getExpression() == null || !this.mEditorData.getExpression().equals(newExpression)) {
         this.setContentModified();
         this.mEditorData.setExpression(newExpression);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a SecurityAccessDef");
      } else if(newVisId == null || newVisId.trim().length() == 0) {
         throw new ValidationException("An identifier must be supplied");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a SecurityAccessDef");
      } else if(newDescription == null || newDescription.trim().length() == 0) {
         throw new ValidationException("A description must be supplied");
      }
   }

   public void validateAccessMode(int newAccessMode) throws ValidationException {
      if(newAccessMode != 1 && newAccessMode != 2) {
         throw new ValidationException("Valid values for access mode are 1 and 2");
      }
   }

   public void validateExpression(String newExpression) throws ValidationException {
      if(newExpression != null && newExpression.length() > 2048) {
         throw new ValidationException("length (" + newExpression.length() + ") of Expression must not exceed 2048 on a SecurityAccessDef");
      } else {
         this.validateExpression(newExpression, false);
      }
   }

   public void setModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setModelRef(actualRef);
      this.setContentModified();
   }

   public EntityList getOwnershipRefs() {
      return ((SecurityAccessDefEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public SecurityAccessDef getSecurityAccessDef() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new SecurityAccessDefAdapter((SecurityAccessDefEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      this.validateVisId(this.mEditorData.getVisId());
      this.validateDescription(this.mEditorData.getDescription());
      this.validateAccessMode(this.mEditorData.getAccessMode());
      this.validateExpression(this.mEditorData.getExpression(), true);
   }

   private void validateExpression(String expression, boolean validateRangeRefs) throws ValidationException {
      if(expression != null && expression.trim().length() != 0) {
         SecurityAccessExpressionParser parser = new SecurityAccessExpressionParser(expression);
         if(validateRangeRefs) {
            parser.addListener(new SecurityAccessDefEditorImpl$1(this));
         }

         try {
            parser.parse();
         } catch (ParseException var5) {
            throw new ValidationException(var5.getMessage());
         }
      } else {
         throw new ValidationException("An access expression must be supplied");
      }
   }

   private boolean findRangeRef(String rangeName) {
      if(this.mRanges == null) {
         this.mRanges = new HashMap();
         ModelRefImpl modelRef = (ModelRefImpl)this.mEditorData.getModelRef();
         EntityList rangeList = this.getConnection().getSecurityRangesProcess().getAllSecurityRangesForModel(modelRef.getModelPK().getModelId());
         Object[] ranges = rangeList.getValues("SecurityRange");

         for(int i = 0; i < ranges.length; ++i) {
            SecurityRangeRef srr = (SecurityRangeRef)ranges[i];
            this.mRanges.put(srr.getNarrative(), srr);
         }
      }

      return this.mRanges.get(rangeName) != null;
   }

   // $FF: synthetic method
   static boolean accessMethod000(SecurityAccessDefEditorImpl x0, String x1) {
      return x0.findRangeRef(x1);
   }
}
