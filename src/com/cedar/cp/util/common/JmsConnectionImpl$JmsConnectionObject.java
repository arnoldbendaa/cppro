// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.common;

import com.cedar.cp.api.base.CPException;
import java.io.Serializable;

interface JmsConnectionImpl$JmsConnectionObject {

   void createSession() throws CPException;

   void closeSession() throws CPException;

   void closeConnection() throws CPException;

   void send(Serializable var1) throws CPException;

   void send(Serializable var1, long var2) throws CPException;

   Object receive() throws CPException;
}
