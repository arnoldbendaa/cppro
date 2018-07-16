// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.ErrorComponent;
import com.cedar.awt.ErrorSettable;
import com.cedar.awt.ErrorSettingAdapter;
import java.awt.Component;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ErrorManager {

   private Component mTop;
   private Hashtable mCache = new Hashtable();
   public static final String id = "@(#) $Archive: /fc/ui/base/ErrorManager.java $ $Revision: 1.3 $";


   public ErrorManager(ErrorSettable top) {
      this.mTop = (Component)top;
   }

   private void setValidInternal(boolean valid, Component component, String message, Object info) {
      ErrorComponent ec = ErrorComponent.getErrorComponent(component, this.mCache, this.mTop);
      Vector componentsChanged = new Vector();
      ec.setValid(valid, componentsChanged);
      Enumeration e = componentsChanged.elements();

      while(e.hasMoreElements()) {
         Component c = (Component)e.nextElement();
         ErrorSettingAdapter.setValid(valid, c, message, info);
      }

      if(valid) {
         this.mCache.remove(component);
      }

   }

   public static void setValid(boolean valid, Component component, String message) {
      setValid(valid, component, message, (Object)null);
   }

   public static void setValid(boolean valid, Component component, String message, Object info) {
      ErrorManager em = getManager(component);
      if(em != null) {
         em.setValidInternal(valid, component, message, info);
      }

   }

   public static void setError(Component component, String message) {
      setError(component, message, (Object)null);
   }

   public static void setError(Component component, String message, Object info) {
      setValid(false, component, message, info);
   }

   public static void setWarning(Component component, String message, Object info) {
      setValid(false, component, message, info);
   }

   public static void clearError(Component component) {
      setValid(true, component, (String)null, (Object)null);
   }

   public static ErrorManager getManager(Component component) {
      Object parent = component;

      ErrorSettable settable;
      for(settable = null; parent != null; parent = ((Component)parent).getParent()) {
         if(parent instanceof ErrorSettable) {
            settable = (ErrorSettable)parent;
            break;
         }
      }

      return settable != null?settable.getErrorManager():null;
   }

   public static boolean isInError(Component component) {
      ErrorManager mgr = getManager(component);
      if(mgr != null) {
         ErrorComponent ec = (ErrorComponent)mgr.mCache.get(component);
         if(ec != null) {
            return true;
         }
      }

      return false;
   }
}
