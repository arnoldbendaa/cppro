package com.cedar.cp.ejb.api.model.globalmapping2;

import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface GlobalMappedModel2EditorSessionLocalHome extends EJBLocalHome {

   GlobalMappedModel2EditorSessionLocal create() throws CreateException;
}
