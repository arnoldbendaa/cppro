// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.cc.CcDeployment;
import com.cedar.cp.api.model.cc.CcDeploymentLineEditor;
import com.cedar.cp.api.model.cc.CcMappingLineEditor;

public interface CcDeploymentEditor extends BusinessEditor {

   void setModelId(int var1) throws ValidationException;

   void setXmlformId(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setDimContext0(Integer var1) throws ValidationException;

   void setDimContextArray(Integer[] var1) throws ValidationException;

   void setDimContext1(Integer var1) throws ValidationException;

   void setDimContext2(Integer var1) throws ValidationException;

   void setDimContext3(Integer var1) throws ValidationException;

   void setDimContext4(Integer var1) throws ValidationException;

   void setDimContext5(Integer var1) throws ValidationException;

   void setDimContext6(Integer var1) throws ValidationException;

   void setDimContext7(Integer var1) throws ValidationException;

   void setDimContext8(Integer var1) throws ValidationException;

   void setDimContext9(Integer var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   CcDeployment getCcDeployment();

   CcDeploymentLineEditor getDeploymentLineEditor(Object var1) throws ValidationException;

   void removeDeploymentLine(Object var1) throws ValidationException;

   CcMappingLineEditor getMappingLineEditor(Object var1, Object var2) throws ValidationException;

   void removeMappingLine(Object var1) throws ValidationException;

   String testDeployment(Object[] var1, boolean[] var2) throws ValidationException;
}
