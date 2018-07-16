// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface CubeUpdateLocal extends EJBLocalObject {

   void executeCubeUpdate(String var1) throws EJBException;

   void executeFlatFormUpdate(String var1) throws EJBException;
}
