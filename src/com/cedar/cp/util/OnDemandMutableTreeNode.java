// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.OnDemandException;
import com.cedar.cp.util.ProxyNode;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class OnDemandMutableTreeNode extends DefaultMutableTreeNode {

   private boolean mIsSelected = false;
   private boolean mNeverAccessed = true;
   private boolean mFakeNode = true;
   private ProxyNode mProxyNode;
   private String mProxyNodeClassName;


   public OnDemandMutableTreeNode(Object pRoot, String pClassName) {
      super(pRoot);
      this.mProxyNodeClassName = pClassName;
      this.instantiateProxyNode();
   }
   
   public OnDemandMutableTreeNode(Object pRoot, String pClassName, boolean isSelected) {
      super(pRoot);
      this.mProxyNodeClassName = pClassName;
      this.instantiateProxyNode();
      this.mIsSelected = isSelected;
   }

   public void instantiateProxyNode() {
      try {
         this.mProxyNode = (ProxyNode)Class.forName(this.mProxyNodeClassName).newInstance();
         this.mProxyNode.setNode(this);
      } catch (InstantiationException var2) {
         throw new OnDemandException("An error occured whilst instantiating the ProxyNode: " + this.mProxyNodeClassName, var2);
      } catch (IllegalAccessException var3) {
         throw new OnDemandException("An error occured whilst instantiating the ProxyNode: " + this.mProxyNodeClassName, var3);
      } catch (ClassNotFoundException var4) {
         throw new OnDemandException("An error occured whilst instantiating the ProxyNode: " + this.mProxyNodeClassName, var4);
      }
   }

   public void hasChildren() {
      this.allowsChildren = this.mProxyNode.hasChildren();
      this.mNeverAccessed = false;
   }

   public void populateChildren() {
      if(this.allowsChildren) {
         this.mProxyNode.populateChildren();
      }

      this.mNeverAccessed = false;
      this.mFakeNode = false;
   }

   public int getChildCount() {
      if(this.mFakeNode) {
         this.populateChildren();
      }

      return this.children == null?0:this.children.size();
   }

   public boolean isLeaf() {
//      if(this.mNeverAccessed) { // cause a bug - never accessed selected leaf looks like having children
         this.hasChildren();
//      }

      return !this.allowsChildren;
   }

   public boolean getAllowsChildren() {
      if(this.mNeverAccessed) {
         this.hasChildren();
      }

      return this.allowsChildren;
   }

   public TreeNode getChildAt(int pIndex) {
      if(this.mFakeNode) {
         this.populateChildren();
      }

      if(this.children == null) {
         throw new ArrayIndexOutOfBoundsException("node has no children");
      } else {
         return (TreeNode)this.children.elementAt(pIndex);
      }
   }

   public void setChildren(Vector pChildren) {
      this.children = pChildren;
      Enumeration e = pChildren.elements();

      while(e.hasMoreElements()) {
         OnDemandMutableTreeNode node = (OnDemandMutableTreeNode)e.nextElement();
         node.setParent(this);
      }

      this.mNeverAccessed = false;
      this.mFakeNode = false;
   }

   public boolean isFakeNode() {
      return this.mFakeNode;
   }

   public String getProxyNodeClassName() {
      return this.mProxyNodeClassName;
   }

   public void setProxyNodeClassName(String pProxyNodeClassName) {
      this.mProxyNodeClassName = pProxyNodeClassName;
   }

   public void insert(MutableTreeNode newChild, int childIndex) {
      if(this.children == null) {
         this.children = new Vector();
      }

      this.allowsChildren = true;
      newChild.setParent(this);
      this.children.insertElementAt(newChild, childIndex);
      this.mNeverAccessed = false;
      this.mFakeNode = false;
   }

   public Object clone() {
      OnDemandMutableTreeNode newNode = null;
      newNode = (OnDemandMutableTreeNode)super.clone();
      newNode.mProxyNodeClassName = this.mProxyNodeClassName;
      newNode.instantiateProxyNode();
      newNode.mNeverAccessed = true;
      newNode.mFakeNode = true;
      return newNode;
   }

   public Enumeration children() {
      if(this.mFakeNode) {
         this.populateChildren();
      }

      return super.children();
   }

   public boolean isSelected() {
      return this.mIsSelected;
   }

   public void setSelected(boolean isSelected) {
      this.mIsSelected = isSelected;
   }
}
