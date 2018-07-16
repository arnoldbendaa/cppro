// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.impl.xmlform.XmlFormsProcessImpl;

public abstract class BusinessProcessImpl implements BusinessProcess {

   private CPConnection mConnection;
   private Context mContext;
   protected Set mSecurityStrings;
   protected Set mActiveSessions;


   public BusinessProcessImpl(CPConnection connection) {
      this.mConnection = connection;
      this.mContext = (Context)this.mConnection.getServerContext().getContext();
      this.mSecurityStrings = new HashSet();
      this.mActiveSessions = new HashSet();
   }

   public CPConnection getConnection() {
      return this.mConnection;
   }

   public boolean hasSecurity(String actionToTest) {
      return this.mConnection.getUserContext().hasSecurity(this.getProcessID(), actionToTest);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      throw new RuntimeException("Not supported yet");
   }

   public Collection getActiveSessions() {
      return Collections.unmodifiableSet(this.mActiveSessions);
   }

   public void terminateSession(BusinessSession session) {
      if(session instanceof BusinessSessionImpl) {
         ((BusinessSessionImpl)session).terminate();
      }

      this.mActiveSessions.remove(session);
   }
   
	@SuppressWarnings("rawtypes")
	public void destroy() {
		Iterator it = this.mActiveSessions.iterator();
		while(it.hasNext()){
			it.next();
			it.remove();
		}
		this.mActiveSessions=null;
		
		it = this.mSecurityStrings.iterator();
		while(it.hasNext()){
			it.next();
			it.remove();
		}
		this.mSecurityStrings=null;
		
		mConnection=null;
		mContext=null;
	}

   public Context getContext() {
      return this.mContext;
   }

   public String getProcessName() {
      return "Not implemented";
   }

   protected abstract int getProcessID();
}
