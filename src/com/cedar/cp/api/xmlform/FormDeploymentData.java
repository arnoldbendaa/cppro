// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform;

import java.util.Map;

public interface FormDeploymentData {

   Object getKey();

   Integer getModelId();
   
   Integer getBudgetCycleId();

   String getIdentifier();

   String getDescription();

   int getAutoExpandDepth();

   boolean isFill();

   boolean isBold();

   boolean isHorz();

   Map getBusinessElements();

   Map getSelection();

   int getMailType();
   
   char getMobile();

   String getMailContent();

   String getAttachmentName();

   byte[] getAttachment();
}
