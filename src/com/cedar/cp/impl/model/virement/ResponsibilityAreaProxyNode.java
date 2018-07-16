// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.impl.model.virement.ResponsibilityAreaNodeImpl;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import com.cedar.cp.util.ProxyNode;
import java.util.Vector;

public class ResponsibilityAreaProxyNode implements ProxyNode {

   public static String CLASS_NAME = "com.cedar.cp.impl.model.virement.ResponsibilityAreaProxyNode";
   private OnDemandMutableTreeNode mNode;


   public boolean hasChildren() {
      return !((ResponsibilityAreaNodeImpl)this.mNode.getUserObject()).isLeaf();
   }

   public void populateChildren() {
      Vector children = new Vector();
      ResponsibilityAreaNodeImpl raNode = (ResponsibilityAreaNodeImpl)this.mNode.getUserObject();
      CPConnection conn = raNode.getConnection();
      EntityList childrenEntityList = conn.getListHelper().getRespAreaImmediateChildren(raNode.getStructureId(), raNode.getStructureElementId());

      for(int i = 0; i < childrenEntityList.getNumRows(); ++i) {
         EntityList childEntityList = childrenEntityList.getRowData(i);
         ResponsibilityAreaNodeImpl childNode = new ResponsibilityAreaNodeImpl(conn, childEntityList);
         children.add(new OnDemandMutableTreeNode(childNode, this.mNode.getProxyNodeClassName()));
         childNode.setModelId(raNode.getModelId());
      }

      this.mNode.setChildren(children);
   }

   public void setNode(OnDemandMutableTreeNode pNode) {
      this.mNode = pNode;
   }

}
