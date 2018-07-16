// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.client;

import com.coa.portal.client.Application;
import java.io.Serializable;
import java.security.Principal;

public interface PortalPrincipal extends Principal, Serializable {

   String getPortalUserId();

   String getApplicationUserId();

   String getApplicationPassword();

   Application getApplication();
}
