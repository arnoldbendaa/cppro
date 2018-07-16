// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.budgetlimit.BudgetLimit;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitEditor;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitEditorSession;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitsProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.WebBussinessProcces;
import com.cedar.cp.utc.struts.topdown.BudgetLimitEditForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BudgetLimitEdit extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      String forward = "setupadd";
      CPContext cnx = this.getCPContext(httpServletRequest);
      CPConnection conn = null;
      BudgetLimitEditForm myForm = null;
      String action = "";
      BudgetLimitsProcess process = null;
      BudgetLimitEditor editor = null;
      BudgetLimitEditorSession session = null;
      WebBussinessProcces wbp = null;
      if(actionForm instanceof BudgetLimitEditForm) {
         myForm = (BudgetLimitEditForm)actionForm;
         myForm.setCPContextId(httpServletRequest);
         action = myForm.getSelectedAction();
         if(action == null) {
            action = "";
         }

         if(!action.equals("")) {
            if(action.equals("add")) {
               conn = cnx.getCPConnection();
               process = conn.getBudgetLimitsProcess();
               session = process.getBudgetLimitEditorSession((Object)null);
               editor = session.getBudgetLimitEditor();
               this.populateValues(myForm, editor, false);
               editor.commit();
               session.commit(false);
               process.terminateSession(session);
               forward = "success";
            } else if(action.equals("delete")) {
               conn = cnx.getCPConnection();
               process = conn.getBudgetLimitsProcess();
               process.deleteObjectWithId(myForm.getModelId(), myForm.getFinanceCubeId(), myForm.getDeleteId());
               forward = "success";
            } else if(action.equals("editSetup")) {
               conn = cnx.getCPConnection();
               process = conn.getBudgetLimitsProcess();
               session = process.getBudgetLimitEditorSession(myForm.getModelId(), myForm.getFinanceCubeId(), myForm.getEditId());
               editor = session.getBudgetLimitEditor();
               BudgetLimit limit = editor.getBudgetLimit();
               this.populateIdList(myForm, limit);
               myForm.setMinValue(limit.getMinValue());
               myForm.setMaxValue(limit.getMaxValue());
               wbp = new WebBussinessProcces(process, session, editor);
               this.setWebProccess(httpServletRequest, wbp);
               forward = "setupadd";
            } else if(action.equals("edit")) {
               wbp = this.getWebProcess(httpServletRequest);
               editor = (BudgetLimitEditor)wbp.getEditor();
               this.populateValues(myForm, editor, true);
               if(editor.isContentModified()) {
                  session = (BudgetLimitEditorSession)wbp.getSession();
                  editor.commit();
                  session.commit(false);
               }

               this.removeWebProcess(httpServletRequest);
               forward = "success";
            } else if(action.equals("cancel")) {
               this.removeWebProcess(httpServletRequest);
               forward = "success";
            }
         }
      }

      return actionMapping.findForward(forward);
   }

   private void populateValues(BudgetLimitEditForm form, BudgetLimitEditor editor, boolean edit) throws ValidationException {
      String s = "";
      if(!edit) {
         String[] ids = form.getSelectedIds().split("_\\*_");
         String[] identifiers = form.getSelectedIdentifiers().split("_\\*_");
         int count = ids.length - 1;

         for(int i = 0; i < count; ++i) {
            switch(i) {
            case 0:
               editor.setDim0(this.parseValue(ids[i]));
               break;
            case 1:
               editor.setDim1(this.parseValue(ids[i]));
               break;
            case 2:
               editor.setDim2(this.parseValue(ids[i]));
               break;
            case 3:
               editor.setDim3(this.parseValue(ids[i]));
               break;
            case 4:
               editor.setDim4(this.parseValue(ids[i]));
               break;
            case 5:
               editor.setDim5(this.parseValue(ids[i]));
               break;
            case 6:
               editor.setDim6(this.parseValue(ids[i]));
               break;
            case 7:
               editor.setDim7(this.parseValue(ids[i]));
               break;
            case 8:
               editor.setDim8(this.parseValue(ids[i]));
               break;
            default:
               editor.setDim9(this.parseValue(ids[i]));
            }
         }

         s = identifiers[identifiers.length - 1];
         editor.setDataType(s);
      }

      s = form.getMinValue();
      if(s != null && s.length() > 0) {
         editor.setMinValue(this.scaleValue(form.getMinDoubleValue()));
      } else {
         editor.setMinValue((Long)null);
      }

      s = form.getMaxValue();
      if(s != null && s.length() > 0) {
         editor.setMaxValue(this.scaleValue(form.getMaxDoubleValue()));
      } else {
         editor.setMaxValue((Long)null);
      }

      editor.setBudgetLocationElementId(form.getSelectedRA());
      editor.setFinanceCubeRef(form.getFinanceCubeId());
   }

   private void populateIdList(BudgetLimitEditForm form, BudgetLimit limit) {
      int count = 0;

      for(boolean valid = true; count < 10 && valid; ++count) {
         switch(count) {
         case 0:
            if(limit.getDim0() != 0) {
               form.addElement(limit.getDim0Txt());
            } else {
               valid = false;
            }
            break;
         case 1:
            if(limit.getDim1() != 0) {
               form.addElement(limit.getDim1Txt());
            } else {
               valid = false;
            }
            break;
         case 2:
            if(limit.getDim2() != 0) {
               form.addElement(limit.getDim2Txt());
            } else {
               valid = false;
            }
            break;
         case 3:
            if(limit.getDim3() != 0) {
               form.addElement(limit.getDim3Txt());
            } else {
               valid = false;
            }
            break;
         case 4:
            if(limit.getDim4() != 0) {
               form.addElement(limit.getDim4Txt());
            } else {
               valid = false;
            }
            break;
         case 5:
            if(limit.getDim5() != 0) {
               form.addElement(limit.getDim5Txt());
            } else {
               valid = false;
            }
            break;
         case 6:
            if(limit.getDim6() != 0) {
               form.addElement(limit.getDim6Txt());
            } else {
               valid = false;
            }
            break;
         case 7:
            if(limit.getDim7() != 0) {
               form.addElement(limit.getDim7Txt());
            } else {
               valid = false;
            }
            break;
         case 8:
            if(limit.getDim8() != 0) {
               form.addElement(limit.getDim8Txt());
            } else {
               valid = false;
            }
            break;
         default:
            if(limit.getDim9() != 0) {
               form.addElement(limit.getDim9Txt());
            } else {
               valid = false;
            }
         }
      }

   }
}
