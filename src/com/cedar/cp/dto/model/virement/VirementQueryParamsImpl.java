// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementQueryParams;
import com.cedar.cp.api.user.UserRef;
import java.io.Serializable;
import java.util.List;

public class VirementQueryParamsImpl implements VirementQueryParams, Serializable {

   private List<UserRef> mOriginators;
   private List<UserRef> mAuthorisers;


   public VirementQueryParamsImpl(List<UserRef> originators, List<UserRef> authorisers) {
      this.mOriginators = originators;
      this.mAuthorisers = authorisers;
   }

   public List<UserRef> getOriginators() {
      return this.mOriginators;
   }

   public List<UserRef> getAuthorisers() {
      return this.mAuthorisers;
   }
}
