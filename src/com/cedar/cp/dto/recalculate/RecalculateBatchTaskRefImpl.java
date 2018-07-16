package com.cedar.cp.dto.recalculate;

import com.cedar.cp.api.recalculate.RecalculateBatchTaskRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;

import java.io.Serializable;

public class RecalculateBatchTaskRefImpl extends EntityRefImpl implements RecalculateBatchTaskRef, Serializable {

	public RecalculateBatchTaskRefImpl(RecalculateBatchTaskPK key, String narrative) {
		super(key, narrative);
	}

	public RecalculateBatchTaskPK getRecalculateBatchTaskPK() {
		return (RecalculateBatchTaskPK) this.mKey;
	}
}
