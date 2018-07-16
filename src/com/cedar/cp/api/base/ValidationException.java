// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.EntityRef;
import java.io.Serializable;

public class ValidationException extends Exception implements Serializable {

   private EntityRef mEntityRef;


   public ValidationException(String reason) {
      super(reason);
   }

   public ValidationException(EntityRef ref, String reason) {
      super(reason);
      this.mEntityRef = ref;
   }

   public EntityRef getEntityRef() {
      return this.mEntityRef;
   }
}