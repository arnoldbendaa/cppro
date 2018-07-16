// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.impl.dimension.StructureElementProxyNode;
import com.cedar.cp.util.OnDemandFilterMutableTreeNode;
import java.util.Map;
import java.util.Vector;

public class StructureElementFilterProxyNode extends StructureElementProxyNode {

   public static final String CLASS_NAME = "com.cedar.cp.impl.dimension.StructureElementFilterProxyNode";


   public void populateChildren() {
      Vector children = new Vector();
      StructureElementNodeImpl seNode = (StructureElementNodeImpl)this.mNode.getUserObject();
      CPConnection conn = seNode.getConnection();
      EntityList childrenEntityList = conn.getHierarchysProcess().getImmediateChildrenWithFilter(seNode.getStructureElementRef(), (Map)this.getFilterTreeNode().getFilterParams());

      for(int i = 0; i < childrenEntityList.getNumRows(); ++i) {
         EntityList childEntityList = childrenEntityList.getRowData(i);
         StructureElementNodeImpl childNode = new StructureElementNodeImpl(conn, childEntityList);
         children.add(new OnDemandFilterMutableTreeNode(childNode, this.mNode.getProxyNodeClassName(), this.getFilterTreeNode().getFilterParams()));
      }

      this.mNode.setChildren(children);
   }

   private OnDemandFilterMutableTreeNode getFilterTreeNode() {
      return (OnDemandFilterMutableTreeNode)super.mNode;
   }
}
