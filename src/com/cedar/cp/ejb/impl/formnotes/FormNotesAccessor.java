// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.formnotes;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.formnotes.AllFormNotesForBudgetLocationELO;
import com.cedar.cp.dto.formnotes.AllFormNotesForFormAndBudgetLocationELO;
import com.cedar.cp.dto.formnotes.FormNotesPK;
import com.cedar.cp.ejb.impl.formnotes.FormNotesDAO;
import com.cedar.cp.ejb.impl.formnotes.FormNotesEVO;
import com.cedar.cp.ejb.impl.formnotes.FormNotesLocal;
import com.cedar.cp.ejb.impl.formnotes.FormNotesLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FormNotesAccessor implements Serializable {

   private FormNotesLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public FormNotesAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private FormNotesLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (FormNotesLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/FormNotesLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up FormNotesLocalHome", var2);
      }
   }

   private FormNotesLocal getLocal(FormNotesPK pk) throws Exception {
      FormNotesLocal local = (FormNotesLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public FormNotesEVO create(FormNotesEVO evo) throws Exception {
      FormNotesLocal local = this.getLocalHome().create(evo);
      FormNotesEVO newevo = local.getDetails("<UseLoadedEVOs>");
      FormNotesPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(FormNotesPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public FormNotesEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof FormNotesPK?this.getLocal((FormNotesPK)key).getDetails(dependants):null;
   }

   public void setDetails(FormNotesEVO evo) throws Exception {
      FormNotesPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public FormNotesEVO setAndGetDetails(FormNotesEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public FormNotesPK generateKeys(FormNotesPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllFormNotesForBudgetLocationELO getAllFormNotesForBudgetLocation(int param1) {
      FormNotesDAO dao = new FormNotesDAO();
      return dao.getAllFormNotesForBudgetLocation(param1);
   }

   public AllFormNotesForFormAndBudgetLocationELO getAllFormNotesForFormAndBudgetLocation(int param1, int param2) {
      FormNotesDAO dao = new FormNotesDAO();
      return dao.getAllFormNotesForFormAndBudgetLocation(param1, param2);
   }
}
