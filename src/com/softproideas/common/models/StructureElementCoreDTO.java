/**
 * 
 */
package com.softproideas.common.models;

import java.io.Serializable;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class StructureElementCoreDTO implements Serializable{
    int structureId;
    int structureElementId;
    String structureElementVisId;//label
    String structureElementDescription;

    public int getStructureId() {
        return structureId;
    }

    public void setStructureId(int structureId) {
        this.structureId = structureId;
    }

    public int getStructureElementId() {
        return structureElementId;
    }

    public void setStructureElementId(int structureElementId) {
        this.structureElementId = structureElementId;
    }

    public String getStructureElementVisId() {
        return structureElementVisId;
    }

    public void setStructureElementVisId(String structureElementVisId) {
        this.structureElementVisId = structureElementVisId;
    }

    public String getStructureElementDescription() {
        return structureElementDescription;
    }

    public void setStructureElementDescription(String structureElementDescription) {
        this.structureElementDescription = structureElementDescription;
    }

}
