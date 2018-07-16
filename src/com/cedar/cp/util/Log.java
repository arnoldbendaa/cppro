// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class Log implements Serializable {

   private transient Logger _cat = null;
   private String _sourceClass;


   public Log(Class component_, boolean stripName) {
      this._sourceClass = component_.getName();
      int ptr = this._sourceClass.lastIndexOf(46) - 1;
      ptr = this._sourceClass.lastIndexOf(46, ptr) + 1;
      this._sourceClass = this._sourceClass.substring(ptr);
   }

   public Log(Class component_) {
      this._sourceClass = component_.getName();
   }

   private Logger getLogger() {
      if(this._cat == null) {
         this._cat = Logger.getLogger(this._sourceClass);
      }

      return this._cat;
   }

   public boolean isDebugEnabled() {
      //return this.getLogger().isDebugEnabled();
	   return true;
   }

   public boolean isInfoEnabled() {
      return this.getLogger().isInfoEnabled();
   }

   public void verbose(String sourceMethod_, Object message_) {
      if(this.getLogger().isEnabledFor(Priority.DEBUG)) {
         this.getLogger().debug(sourceMethod_ + ' ' + message_);
      }

   }

   public void debug(String sourceMethod_, Object message_) {
      if(this.getLogger().isEnabledFor(Priority.DEBUG)) {
         this.getLogger().debug(sourceMethod_ + ' ' + message_);
      }

   }

   public void debug(String sourceMethod_) {
      if(this.getLogger().isEnabledFor(Priority.DEBUG)) {
         this.getLogger().debug(sourceMethod_);
      }

   }

   public void info(String sourceMethod_, Object message_) {
      if(this.getLogger().isEnabledFor(Priority.INFO)) {
         this.getLogger().info(sourceMethod_ + ' ' + message_);
      }

   }

   public void warn(String sourceMethod_, Object message_) {
      if(this.getLogger().isEnabledFor(Priority.WARN)) {
         this.getLogger().warn(sourceMethod_ + ' ' + message_);
      }

   }

   public void error(String sourceMethod_, Object message_) {
      if(this.getLogger().isEnabledFor(Priority.ERROR)) {
         this.getLogger().error(sourceMethod_ + ' ' + message_);
      }

   }

   public void error(String sourceMethod_, Object message_, Throwable e_) {
      if(this.getLogger().isEnabledFor(Priority.ERROR)) {
         this.getLogger().error(sourceMethod_ + ' ' + message_, e_);
      }

   }
}
