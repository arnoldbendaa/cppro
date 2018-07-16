package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.globalmapping2.MappedHierarchy;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Hashtable;
import java.util.List;

public class MappedHierarchyImpl implements MappedHierarchy, Serializable, Cloneable {

   private Object mKey;
   private HierarchyRef mHierarchyRef;
   private String mHierarchyVisId1;
   private String mHierarchyVisId2;
   private String mNewHierarchyVisId;
   private String mNewHierarchyDescription;
   private boolean mResponsibilityAreaHierarchy;
   private Hashtable<String, Boolean> mSelectedCompanies = new Hashtable<String, Boolean>();
   private Hashtable<String, Boolean> mSelectedAllCompanies = new Hashtable<String, Boolean>();
   
   
   public MappedHierarchyImpl(Object key, HierarchyRef hierarchyRef, String hierarchyVisId1, String hierarchyVisId2, String newHierarchyVisId, String newHierarchyDescription, boolean responsibilityAreaHierarchy) {
      this.mKey = key;
      this.mHierarchyRef = hierarchyRef;
      this.mHierarchyVisId1 = hierarchyVisId1;
      this.mHierarchyVisId2 = hierarchyVisId2;
      this.mNewHierarchyVisId = newHierarchyVisId;
      this.mNewHierarchyDescription = newHierarchyDescription;
      this.mResponsibilityAreaHierarchy = responsibilityAreaHierarchy;
   }

   public Object getKey() {
      return this.mKey;
   }

   public MappingKey getFinanceHierarchyKey() {
      return new MappingKeyImpl(this.getHierarchyVisId1(), this.getHierarchyVisId2());
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public HierarchyRef getHierarchyRef() {
      return this.mHierarchyRef;
   }

   public void setHierarchyRef(HierarchyRef hierarchyRef) {
      this.mHierarchyRef = hierarchyRef;
   }

   public String getHierarchyVisId1() {
      return this.mHierarchyVisId1;
   }

   public void setHierarchyVisId1(String hierarchyVisId1) {
      this.mHierarchyVisId1 = hierarchyVisId1;
   }

   public String getHierarchyVisId2() {
      return this.mHierarchyVisId2;
   }

   public void setHierarchyVisId2(String hierarchyVisId2) {
      this.mHierarchyVisId2 = hierarchyVisId2;
   }

   public String getNewHierarchyVisId() {
      return this.mNewHierarchyVisId;
   }

   public void setNewHierarchyVisId(String newHierarchyVisId) {
      this.mNewHierarchyVisId = newHierarchyVisId;
   }

   public String getNewHierarchyDescription() {
      return this.mNewHierarchyDescription;
   }

   public void setNewHierarchyDescription(String newHierarchyDescription) {
      this.mNewHierarchyDescription = newHierarchyDescription;
   }

   public Object clone() throws CloneNotSupportedException {
      MappedHierarchyImpl hier = (MappedHierarchyImpl)super.clone();
      hier.mKey = this.mKey;
      hier.mHierarchyRef = this.mHierarchyRef;
      hier.mHierarchyVisId1 = this.mHierarchyVisId1;
      hier.mHierarchyVisId2 = this.mHierarchyVisId2;
      hier.mNewHierarchyVisId = this.mNewHierarchyVisId;
      hier.mNewHierarchyDescription = this.mNewHierarchyDescription;
      hier.mResponsibilityAreaHierarchy = this.mResponsibilityAreaHierarchy;
      return hier;
   }

   public String toString() {
      return this.getNewHierarchyDescription() == null?null:this.getNewHierarchyDescription();
   }

   public boolean equals(Object obj) {
      return this == obj?true:(obj instanceof MappedHierarchy?((MappedHierarchy)obj).getKey().equals(this.mKey):false);
   }

   public int hashCode() {
      return this.mKey != null?this.mKey.hashCode():0;
   }

   public boolean isResponsibilityAreaHierarchy() {
      return this.mResponsibilityAreaHierarchy;
   }

   public void setResponsibilityAreaHierarchy(boolean responsibilityAreaHierarchy) {
      this.mResponsibilityAreaHierarchy = responsibilityAreaHierarchy;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<mappedHierarchy ");
      XmlUtils.outputAttribute(out, "hierarchyRef", this.mHierarchyRef);
      XmlUtils.outputAttribute(out, "hierarchyVisId1", this.mHierarchyVisId1);
      XmlUtils.outputAttribute(out, "hierarchyVisId2", this.mHierarchyVisId2);
      XmlUtils.outputAttribute(out, "newHierarchyVisId", this.mNewHierarchyVisId);
      XmlUtils.outputAttribute(out, "newHierarchyDescription", this.mNewHierarchyDescription);
      XmlUtils.outputAttribute(out, "responsibilityAreaHierarchy", Boolean.valueOf(this.mResponsibilityAreaHierarchy));
      out.write(" />\n");
   }
   
	public Hashtable<String, Boolean> getSelectedCompanies() {
		return mSelectedCompanies;
	}

	public void setSelectedCompanies(List<FinanceCompany> companies) {
	    this.mSelectedCompanies.clear();
	    this.mSelectedAllCompanies.clear();
	    for (FinanceCompany company : companies) {
			this.mSelectedCompanies.put(company.getCompanyVisId(), false);
			this.mSelectedAllCompanies.put(company.getCompanyVisId(), false);
		}
	}
	
	public void setSelectedCompany(String companyVisId, Boolean value) {
		this.mSelectedCompanies.put(companyVisId, value);
	}
	
	public int isSelectedCompany(String companyVisId) {
		try {
			if (this.mSelectedCompanies.get(companyVisId)) {
				return 2;
			} else {
				return 1;
			}			
		} catch (Exception e) {
			return 0;
		}
	}

	public void setSelectedAllCompany(String companyVisId, Boolean value) {
		this.mSelectedAllCompanies.put(companyVisId, value);
	}
	
	public int isSelectedAllCompany(String companyVisId) {
		try {
			if (this.mSelectedAllCompanies.get(companyVisId)) {
				return 2;
			} else {
				return 1;
			}			
		} catch (Exception e) {
			return 0;
		}
	}
}
