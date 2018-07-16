// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ErrorComponent implements ContainerListener {

   private Hashtable mCache;
   private Vector mChildren = new Vector();
   private ErrorComponent mParent;
   private boolean mIsOk = true;
   private boolean mIsIntrinsicallyOk = true;
   private Component mComponent;
   public static final String id = "@(#) $Archive: /fc/ui/base/ErrorComponent.java $ $Revision: 1.3 $";


   private ErrorComponent(Component component, Hashtable cache, Component top) {
      Object currentComponent = component;
      if(component instanceof Container) {
         ((Container)component).addContainerListener(this);
      }

      ErrorComponent parent;
      Container parentComponent;
      for(parent = null; currentComponent != top && !(currentComponent instanceof Dialog) && !(currentComponent instanceof Frame) && !cache.containsKey(currentComponent); currentComponent = parentComponent) {
         parentComponent = ((Component)currentComponent).getParent();
         if(cache.containsKey(parentComponent)) {
            parent = (ErrorComponent)cache.get(parentComponent);
         } else {
            parent = new ErrorComponent(parentComponent, cache, top);
         }
      }

      if(component != top) {
         parent.addChild(this);
         this.mParent = parent;
      }

      this.mComponent = component;
      this.mCache = cache;
      cache.put(component, this);
   }

   public static ErrorComponent getErrorComponent(Component component, Hashtable cache, Component top) {
      return cache.containsKey(component)?(ErrorComponent)cache.get(component):new ErrorComponent(component, cache, top);
   }

   public void setValid(boolean valid, Vector changed) {
      this.mIsIntrinsicallyOk = valid;
      this.setValidRecursive(valid, changed);
   }

   private void setValidRecursive(boolean valid, Vector changed) {
      if(this.mIsOk != valid) {
         this.mIsOk = valid;
         changed.addElement(this.getComponent());
         ErrorComponent parent = this.getParent();
         if(parent != null && parent.isValid() != valid && (!valid || valid && this.areSiblingsClean() && this.mIsIntrinsicallyOk)) {
            parent.setValidRecursive(valid, changed);
         }

      }
   }

   private void addChild(ErrorComponent child) {
      this.mChildren.addElement(child);
   }

   private void removeChild(ErrorComponent child) {
      this.mChildren.remove(child);
   }

   private Enumeration children() {
      return this.mChildren.elements();
   }

   private ErrorComponent getParent() {
      return this.mParent;
   }

   private Component getComponent() {
      return this.mComponent;
   }

   private boolean isValid() {
      return this.mIsOk;
   }

   private boolean areSiblingsClean() {
      boolean result = true;
      if(this.mParent != null) {
         Enumeration e = this.mParent.children();

         while(e.hasMoreElements()) {
            ErrorComponent ec = (ErrorComponent)e.nextElement();
            if(this != ec && !ec.isValid()) {
               result = false;
               break;
            }
         }
      }

      return result;
   }

   public String toString() {
      return this.mParent + "/" + this.mComponent.getClass().getName();
   }

   public void componentAdded(ContainerEvent ce) {}

   public void componentRemoved(ContainerEvent ce) {
      ErrorComponent eComponent = (ErrorComponent)this.mCache.get(ce.getChild());
      ErrorComponent eContainer = (ErrorComponent)this.mCache.get(ce.getContainer());
      if(eComponent != null && eContainer != null) {
         eContainer.removeChild(eComponent);
      }

   }
}
