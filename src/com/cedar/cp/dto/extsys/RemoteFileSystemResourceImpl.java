// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.RemoteFileSystemResource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.tree.TreeNode;

public class RemoteFileSystemResourceImpl implements RemoteFileSystemResource {

   private String mName;
   private int mResourceType;
   private RemoteFileSystemResource mParent;
   private List<RemoteFileSystemResource> mChildren;


   public RemoteFileSystemResourceImpl(String name, int resourceType, RemoteFileSystemResource parent, List<RemoteFileSystemResource> children) {
      this.mName = name;
      this.mResourceType = resourceType;
      this.mParent = parent;
      this.mChildren = children;
   }

   public String toString() {
      return this.mName;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public int getResourceType() {
      return this.mResourceType;
   }

   public void setResourceType(int resourceType) {
      this.mResourceType = resourceType;
   }

   public RemoteFileSystemResource getParent() {
      return this.mParent;
   }

   public void setParent(RemoteFileSystemResource parent) {
      this.mParent = parent;
   }

   public List<RemoteFileSystemResource> getChildren() {
      return this.mChildren;
   }

   public void setChildren(List<RemoteFileSystemResource> children) {
      this.mChildren = children;
   }

   public Enumeration children() {
      return (new Vector(this.mChildren)).elements();
   }

   public boolean isLeaf() {
      return this.mResourceType == 1;
   }

   public boolean getAllowsChildren() {
      return this.mResourceType != 1;
   }

   public int getIndex(TreeNode node) {
      return this.mChildren != null?this.getChildren().indexOf(node):-1;
   }

   public int getChildCount() {
      return this.mChildren != null?this.mChildren.size():0;
   }

   public TreeNode getChildAt(int childIndex) {
      return this.mChildren != null?(RemoteFileSystemResource)this.mChildren.get(childIndex):null;
   }

   public String getPath() {
      Object cNode = this;

      ArrayList pathToRoot;
      for(pathToRoot = new ArrayList(); cNode != null; cNode = ((RemoteFileSystemResourceImpl)cNode).mParent) {
         pathToRoot.add(0, cNode);
      }

      boolean firstItem = true;
      StringBuffer sb = new StringBuffer();

      for(Iterator i$ = pathToRoot.iterator(); i$.hasNext(); firstItem = false) {
         RemoteFileSystemResource node = (RemoteFileSystemResource)i$.next();
         if(!firstItem && !sb.toString().endsWith("/") && !sb.toString().endsWith("\\")) {
            sb.append('/');
         }

         sb.append(node.getName());
      }

      return sb.toString();
   }

   public String getURL() {
      return "file:///" + this.getPath().replace('\\', '/').replace("//", "/");
   }

   public RemoteFileSystemResource getRoot() {
      return (RemoteFileSystemResource)(this.mParent != null?((RemoteFileSystemResourceImpl)this.mParent).getRoot():this);
   }
}
