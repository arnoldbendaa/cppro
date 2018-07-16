/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.app.admin.modelmappings.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class ExtSysDataTypesTreeNode implements MutableTreeNode {

   private MutableTreeNode mParent;
   private List<MutableTreeNode> mChildren;
   private Object mObj;


   public ExtSysDataTypesTreeNode(MutableTreeNode parent, Object extsys) {
      this.mParent = parent;
      this.mObj = extsys;
   }

   public TreeNode getChildAt(int childIndex) {
      this.primeChildren();
      return (TreeNode)this.mChildren.get(childIndex);
   }

   public int getChildCount() {
      this.primeChildren();
      return this.mChildren.size();
   }

   public TreeNode getParent() {
      return this.mParent;
   }

   public int getIndex(TreeNode node) {
      this.primeChildren();
      return this.mChildren.indexOf(node);
   }

   public boolean getAllowsChildren() {
      this.primeChildren();
      return true;
   }

   public boolean isLeaf() {
      if(this.mChildren != null) {
         return false;
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceLedger) {
         return false;
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueType) {
         return false;
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) {
         return false;
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) {
         return false;
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) {
         return true;
      } else {
         throw new IllegalStateException("unexpected class " + this.mObj.getClass().getName());
      }
   }

   public void setParent(MutableTreeNode newParent) {
      this.mParent = newParent;
   }

   public Enumeration children() {
      this.primeChildren();
      return (new Vector(this.mChildren)).elements();
   }

   public void insert(MutableTreeNode child, int index) {
      this.primeChildren();
      this.mChildren.add(index, child);
   }

   public void remove(int index) {
      this.primeChildren();
      this.mChildren.remove(index);
   }

   public void remove(MutableTreeNode node) {
      this.primeChildren();
      this.mChildren.remove(node);
   }

   public void setUserObject(Object object) {
      this.mObj = object;
   }

   public Object getUserObject() {
      return this.mObj;
   }

   public void removeFromParent() {
      this.mParent.remove(this);
   }

    private void primeChildren() {
        if (this.mChildren == null) {
            this.mChildren = new ArrayList();
            // global
            if (this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) {
                this.primeFinanceValueTypeChildrenGlobal();
            } else if (this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) {
                this.primeFinanceCurrencyGroupGlobal();
            } else if (this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) {
                this.primeFinanceValueTypeOwnerChildrenGlobal();
            } else if (this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) {
                this.primeFinanceLedgerChildrenGlobal();
            }
            // not global
            else if (this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueType) {
                this.primeFinanceValueTypeChildren();
            } else if (this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) {
                this.primeFinanceCurrencyGroup();
            } else if (this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) {
                this.primeFinanceValueTypeOwnerChildren();
            } else if (this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceLedger) {
                this.primeFinanceLedgerChildren();
            }
        }
    }

   public String toString() {
      return this.mObj.toString();
   }

   private void primeFinanceValueTypeChildrenGlobal() {
      com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType fvt = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType)this.mObj;
      Iterator i$ = fvt.getFinanceCurrencies().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency o = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o));
      }

      i$ = fvt.getFinanceCurrencyGroups().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup o1 = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o1));
      }

   }
   private void primeFinanceValueTypeChildren() {
      com.cedar.cp.api.model.mapping.extsys.FinanceValueType fvt = (com.cedar.cp.api.model.mapping.extsys.FinanceValueType)this.mObj;
      Iterator i$ = fvt.getFinanceCurrencies().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.mapping.extsys.FinanceCurrency o = (com.cedar.cp.api.model.mapping.extsys.FinanceCurrency)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o));
      }

      i$ = fvt.getFinanceCurrencyGroups().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup o1 = (com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o1));
      }

   }
   
   private void primeFinanceLedgerChildrenGlobal() {
      com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger fl = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger)this.mObj;
      Iterator i$ = fl.getFinanceValueTypes().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType o = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o));
      }

      i$ = fl.getFinanceValueTypeOwners().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner o1 = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o1));
      }

   }

   private void primeFinanceLedgerChildren() {
       com.cedar.cp.api.model.mapping.extsys.FinanceLedger fl = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger)this.mObj;
       Iterator i$ = fl.getFinanceValueTypes().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceValueType o = (com.cedar.cp.api.model.mapping.extsys.FinanceValueType)i$.next();
          this.mChildren.add(new ExtSysDataTypesTreeNode(this, o));
       }

       i$ = fl.getFinanceValueTypeOwners().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner o1 = (com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner)i$.next();
          this.mChildren.add(new ExtSysDataTypesTreeNode(this, o1));
       }

    }
   
   private void primeFinanceValueTypeOwnerChildrenGlobal() {
      com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner fl = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner)this.mObj;
      Iterator i$ = fl.getFinanceValueTypes().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType o = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o));
      }

   }

   private void primeFinanceValueTypeOwnerChildren() {
      com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner fl = (com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner)this.mObj;
      Iterator i$ = fl.getFinanceValueTypes().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.mapping.extsys.FinanceValueType o = (com.cedar.cp.api.model.mapping.extsys.FinanceValueType)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o));
      }

   }
   
   private void primeFinanceCurrencyGroupGlobal() {
      com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup fcg = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup)this.mObj;
      Iterator i$ = fcg.getFinanceCurrencies().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency o = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o));
      }

      i$ = fcg.getFinanceCurrencyGroups().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup o1 = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o1));
      }

   }

   private void primeFinanceCurrencyGroup() {
      com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup fcg = (com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup)this.mObj;
      Iterator i$ = fcg.getFinanceCurrencies().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.mapping.extsys.FinanceCurrency o = (com.cedar.cp.api.model.mapping.extsys.FinanceCurrency)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o));
      }

      i$ = fcg.getFinanceCurrencyGroups().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup o1 = (com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup)i$.next();
         this.mChildren.add(new ExtSysDataTypesTreeNode(this, o1));
      }

   }
}
