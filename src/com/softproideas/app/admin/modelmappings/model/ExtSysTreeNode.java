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

import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class ExtSysTreeNode implements MutableTreeNode {

   private MutableTreeNode mParent;
   private List<MutableTreeNode> mChildren;
   private Object mObj;


   public ExtSysTreeNode(MutableTreeNode parent, Object extsys) {
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
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimension) {
         return false;
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup) {
         return false;
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement) {
         return true;
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy) {
         return false;
      } else if(this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement) {
         return false;
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
            if (this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension) {
                this.primeFinanceDimensionChildrenGlobal();
            } else if (this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup) {
                this.primeFinanceDimensionElementGroupChildrenGlobal();
            }
            // not global
            else if (this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimension) {
                this.primeFinanceDimensionChildren();
            } else if (this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup) {
                this.primeFinanceDimensionElementGroupChildren();
            } else {
                if (this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement || this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement) {
                    return;
                }
                // global
                if (this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy) {
                    this.primeFinanceHierarchyChildrenGlobal();
                } else if (this.mObj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement) {
                    this.primeFinanceHierarchyElementGlobal();
                }
                // not global
                else if (this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy) {
                    this.primeFinanceHierarchyChildren();
                } else if (this.mObj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement) {
                    this.primeFinanceHierarchyElement();
                }
            }

        }
    }

   public String toString() {
      return this.mObj.toString();
   }

   private void primeFinanceDimensionChildrenGlobal() {
      com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension fd = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension)this.mObj;
      Iterator i$ = fd.getFinanceDimensionElementGroups().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup hierarchy = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup)i$.next();
         this.mChildren.add(new ExtSysTreeNode(this, hierarchy));
      }

      i$ = fd.getFinanceHierarchies().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy hierarchy1 = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy)i$.next();
         this.mChildren.add(new ExtSysTreeNode(this, hierarchy1));
      }

   }

   private void primeFinanceDimensionChildren() {
       com.cedar.cp.api.model.mapping.extsys.FinanceDimension fd = (com.cedar.cp.api.model.mapping.extsys.FinanceDimension)this.mObj;
       Iterator i$ = fd.getFinanceDimensionElementGroups().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup hierarchy = (com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup)i$.next();
          this.mChildren.add(new ExtSysTreeNode(this, hierarchy));
       }

       i$ = fd.getFinanceHierarchies().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy hierarchy1 = (com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy)i$.next();
          this.mChildren.add(new ExtSysTreeNode(this, hierarchy1));
       }

    }
   
   private void primeFinanceDimensionElementGroupChildrenGlobal() {
      com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup fdeg = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup)this.mObj;
      Iterator i$ = fdeg.getFinanceDimensionElementGroups().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup o = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup)i$.next();
         this.mChildren.add(new ExtSysTreeNode(this, o));
      }

      i$ = fdeg.getFinanceDimensionElements().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement o1 = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement)i$.next();
         this.mChildren.add(new ExtSysTreeNode(this, o1));
      }

   }

   private void primeFinanceDimensionElementGroupChildren() {
       com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup fdeg = (com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup)this.mObj;
       Iterator i$ = fdeg.getFinanceDimensionElementGroups().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup o = (com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup)i$.next();
          this.mChildren.add(new ExtSysTreeNode(this, o));
       }

       i$ = fdeg.getFinanceDimensionElements().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement o1 = (com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement)i$.next();
          this.mChildren.add(new ExtSysTreeNode(this, o1));
       }

    }
   
   private void primeFinanceHierarchyChildrenGlobal() {
      com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy fh = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy)this.mObj;
      Iterator i$ = fh.getFinanceHierarchyElements().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement fde = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement)i$.next();
         this.mChildren.add(new ExtSysTreeNode(this, fde));
      }

      i$ = fh.getFinanceDimensionElements().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement fde1 = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement)i$.next();
         this.mChildren.add(new ExtSysTreeNode(this, fde1));
      }

   }

   private void primeFinanceHierarchyChildren() {
       com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy fh = (com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy)this.mObj;
       Iterator i$ = fh.getFinanceHierarchyElements().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement fde = (com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement)i$.next();
          this.mChildren.add(new ExtSysTreeNode(this, fde));
       }

       i$ = fh.getFinanceDimensionElements().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement fde1 = (com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement)i$.next();
          this.mChildren.add(new ExtSysTreeNode(this, fde1));
       }

    }
   
   private void primeFinanceHierarchyElementGlobal() {
      com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement fhe = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement)this.mObj;
      Iterator i$ = fhe.getFinanceHierarchyElements().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement o = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement)i$.next();
         this.mChildren.add(new ExtSysTreeNode(this, o));
      }

      i$ = fhe.getFinanceDimensionElements().iterator();

      while(i$.hasNext()) {
         com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement o1 = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement)i$.next();
         this.mChildren.add(new ExtSysTreeNode(this, o1));
      }

   }

   private void primeFinanceHierarchyElement() {
       com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement fhe = (com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement)this.mObj;
       Iterator i$ = fhe.getFinanceHierarchyElements().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement o = (com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement)i$.next();
          this.mChildren.add(new ExtSysTreeNode(this, o));
       }

       i$ = fhe.getFinanceDimensionElements().iterator();

       while(i$.hasNext()) {
          com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement o1 = (com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement)i$.next();
          this.mChildren.add(new ExtSysTreeNode(this, o1));
       }

    }
}
