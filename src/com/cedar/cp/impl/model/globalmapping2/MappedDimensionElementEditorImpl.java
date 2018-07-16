package com.cedar.cp.impl.model.globalmapping2;

import java.util.List;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionElement;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionElementEditor;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.globalmapping2.MappedDimensionEditorImpl;
import com.cedar.cp.util.StringUtils;

public class MappedDimensionElementEditorImpl extends SubBusinessEditorImpl implements MappedDimensionElementEditor {

	private MappedDimensionElementImpl mMappedDimensionElement;

	public MappedDimensionElementEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, MappedDimensionElementImpl mappedDimensionElement, boolean newMapping) {
		super(sess, owner);

		try {
			this.mMappedDimensionElement = (MappedDimensionElementImpl) mappedDimensionElement.clone();
		} catch (CloneNotSupportedException e) {
			throw new CPException("Failed to clone MappedDimensionElementImpl", e);
		}

		if (newMapping) {
			this.setContentModified();
		}
	}

	protected void undoModifications() throws CPException {
	}

	protected void saveModifications() throws ValidationException {
		((MappedDimensionEditorImpl) this.getOwner()).updateMappedDimensionElement(this.mMappedDimensionElement);
	}

	public MappedDimensionElement getMappedDimensionElement() {
		return this.mMappedDimensionElement;
	}

	public void setMappingType(int mappingType) throws ValidationException {
		if (mappingType != 0 && mappingType != 1 && mappingType != 2 && mappingType != 3) {
			throw new ValidationException("Not a valid mapping type:" + mappingType);
		} else {
			if (this.mMappedDimensionElement.getMappingType() != mappingType) {
				this.mMappedDimensionElement.setMappingType(mappingType);
				this.setContentModified();
			}
		}
	}

	public void setVisId1(String visId1) throws ValidationException {
		this.validateFieldPresent(visId1);
		if (StringUtils.differ(visId1, this.mMappedDimensionElement.getVisId1())) {
			this.mMappedDimensionElement.setVisId1(visId1);
			this.setContentModified();
		}
	}

	public void setVisId2(String visId2) throws ValidationException {
		if (StringUtils.differ(visId2, this.mMappedDimensionElement.getVisId2())) {
			this.mMappedDimensionElement.setVisId2(visId2);
			this.setContentModified();
		}
	}

	public void setFinanceMappingKey(MappingKey key) throws ValidationException {
		MappingKeyImpl mk = (MappingKeyImpl) key;
		if (mk.length() != 3) {
			throw new ValidationException("Invalid mapping key - expected three key segments:" + key);
		} else {
			this.setVisId1((String) mk.get(0));
			this.setVisId2((String) mk.get(1));
			this.setVisId3((String) mk.get(2));
		}
	}

	private void setVisId3(String visId3) throws ValidationException {
		if (StringUtils.differ(visId3, this.mMappedDimensionElement.getVisId3())) {
			this.mMappedDimensionElement.setVisId3(visId3);
			this.setContentModified();
		}
	}

	public void setSelectedCompanies(List<FinanceCompany> companies) {
		this.mMappedDimensionElement.setSelectedCompanies(companies);
	}

	public void setSelectedCompany(String companyVisId, Boolean value) {
		this.mMappedDimensionElement.setSelectedCompany(companyVisId, value);
	}
}
