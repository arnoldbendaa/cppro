// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter.providers;

import com.coa.idm.filter.SSOProvider;
import java.io.Serializable;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public abstract class BaseProvider implements SSOProvider, Serializable {

   public void init(FilterConfig filterConfig) throws ServletException {}
}
