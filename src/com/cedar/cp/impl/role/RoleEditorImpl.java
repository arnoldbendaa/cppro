// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.role;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.role.Role;
import com.cedar.cp.api.role.RoleEditor;
import com.cedar.cp.api.role.RoleSecuritySelection;
import com.cedar.cp.dto.role.RoleEditorSessionSSO;
import com.cedar.cp.dto.role.RoleImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.role.RoleAdapter;
import com.cedar.cp.impl.role.RoleEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

public class RoleEditorImpl extends BusinessEditorImpl implements RoleEditor {

   private RoleEditorSessionSSO mServerSessionData;
   private RoleImpl mEditorData;
   private RoleAdapter mEditorDataAdapter;


   public RoleEditorImpl(RoleEditorSessionImpl session, RoleEditorSessionSSO serverSessionData, RoleImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(RoleEditorSessionSSO serverSessionData, RoleImpl editorData) {
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
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a Role");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 60) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 60 on a Role");
      }
   }

   public Role getRole() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new RoleAdapter((RoleEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(!this.isRoleSecuritySelected((DefaultMutableTreeNode)this.mEditorData.getTreeRoot())) {
         throw new ValidationException("One or more security string must be selected");
      }
   }

   public void setSecurityStringsChanged() {
      this.setContentModified();
   }

   private boolean isRoleSecuritySelected(DefaultMutableTreeNode parentNode) {
      Enumeration e = parentNode.preorderEnumeration();

      while(e.hasMoreElements()) {
         DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)e.nextElement();
         if(childNode.isLeaf() && childNode.getUserObject() instanceof RoleSecuritySelection) {
            RoleSecuritySelection nodeObject = (RoleSecuritySelection)childNode.getUserObject();
            if(nodeObject.isSelected()) {
               return true;
            }
         }
      }

      return false;
   }
}
