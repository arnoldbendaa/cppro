// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.facades;


class CPFunctionsBaseEvaluator$ElementQueryDetails {

   int mDimensionIndex;
   boolean mLabelAndDescr;
   String mVisualId;
   Object mLink;
   String dim;
   
   int getDimIndex(){
       if(dim != null && dim.length() == 4){
           return dim.charAt(3);
       }
       else{
           return -1;
       }
   }

}
