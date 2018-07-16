package com.cedar.cp.dto.dimension;

import com.cedar.cp.dto.base.AbstractELO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllDimensionElementsForModelELO extends AbstractELO implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String[] mEntity = new String[] { "ModelId", "DimensionElementId", "VisId", "DimensionType" };
    private transient int mModelId;
    private transient int mDimensionElementId;
    private transient String mDimensionElementVisId;
    private transient int mDimensionType;
    
    public AllDimensionElementsForModelELO() {
        super(new String[] { "ModelId", "DimensionElementId", "VisId", "DimensionType" });
    }

    public void add(int modelId, int dimensionElementId, String dimensionElementVisId, int dimensionType) {
        ArrayList<Object> l = new ArrayList<Object>();
        l.add(modelId);
        l.add(dimensionElementId);
        l.add(dimensionElementVisId);
        l.add(dimensionType);
        this.mCollection.add(l);
    }

    @SuppressWarnings("unchecked")
    public void next() {
        if (this.mIterator == null) {
            this.reset();
        }
        ++this.mCurrRowIndex;
        List<Object> l = (List<Object>) this.mIterator.next();
        byte index = 0;
        this.mModelId = (Integer) l.get(index++);
        this.mDimensionElementId = (Integer) l.get(index++);
        this.mDimensionElementVisId = (String) l.get(index++);
        this.mDimensionType = (Integer) l.get(index++);
    }

    public int getModelId() {
        return mModelId;
    }

    public void setModelId(int modelId) {
        this.mModelId = modelId;
    }

    public int getDimensionElementId() {
        return mDimensionElementId;
    }

    public void setDimensionElementId(int dimensionElementId) {
        this.mDimensionElementId = dimensionElementId;
    }

    public String getDimensionElementVisId() {
        return mDimensionElementVisId;
    }

    public void setDimensionElementVisId(String visId) {
        this.mDimensionElementVisId = visId;
    }

    public int getDimensionType() {
        return mDimensionType;
    }

    public void setDimensionType(int dimensionType) {
        this.mDimensionType = dimensionType;
    }

    public boolean includesEntity(String name) {
        for (int i = 0; i < mEntity.length; ++i) {
            if (name.equals(mEntity[i])) {
                return true;
            }
        }
        return false;
    }

}
