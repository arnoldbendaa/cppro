// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.definition;

import com.cedar.cp.ejb.api.report.definition.ReportDefinitionEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ReportDefinitionEditorSessionLocalHome extends EJBLocalHome {

   ReportDefinitionEditorSessionLocal create() throws CreateException;
}
