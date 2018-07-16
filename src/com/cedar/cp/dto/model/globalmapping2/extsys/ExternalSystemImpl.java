package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalSystem;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class ExternalSystemImpl extends ExternalEntityImpl implements ExternalSystem, Serializable {

   private List<FinanceCompany> mCompanies;
   private int mSystemType;
   private String mDescription;
   private String mLocation;
   private boolean mEnabled;


   public ExternalSystemImpl() {}

   public ExternalSystemImpl(int systemType, EntityRefImpl entityRef, String location, String description, boolean enabled) {
      super(entityRef);
      this.mSystemType = systemType;
      this.mDescription = description;
      this.mLocation = location;
      this.mEnabled = enabled;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getSystemType() {
      return this.mSystemType;
   }

   public String getLocation() {
      return this.mLocation;
   }

   public boolean isEnabled() {
      return this.mEnabled;
   }

   public List<FinanceCompany> getCompanies() {
      return this.mCompanies;
   }

   public void setCompanies(List<FinanceCompany> companies) {
      this.mCompanies = companies;
   }

   public void setSystemType(int systemType) {
      this.mSystemType = systemType;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public void setLocation(String location) {
      this.mLocation = location;
   }

   public void setEnabled(boolean enabled) {
      this.mEnabled = enabled;
   }
}
