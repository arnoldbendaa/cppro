// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.CPException;
import java.io.Serializable;

public interface JmsConnection {

   int TYPE_QUEUE_SENDER = 1;
   int TYPE_QUEUE_RECEIVER = 2;
   int TYPE_TOPIC_PUBLISHER = 3;
   int TYPE_TOPIC_SUBSCRIBER = 4;


   void createSession() throws CPException;

   void closeSession() throws CPException;

   void closeConnection() throws CPException;

   void send(Serializable var1) throws CPException;

   void send(Serializable var1, long var2) throws CPException;

   Object receive() throws CPException;
}
