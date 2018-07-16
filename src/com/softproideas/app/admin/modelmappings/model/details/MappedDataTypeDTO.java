/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.app.admin.modelmappings.model.details;

public class MappedDataTypeDTO {
    private int mappedDateTypeId;
    private int dataTypeId;
    private String dataTypeVisId;
    private String valueType;
    private String currency;
    private String balType;
    private int impExpStatus;
    private Integer impStartYearOffset;
    private Integer impEndYearOffset;
    private Integer expStartYearOffset;
    private Integer expEndYearOffset;

    public int getMappedDateTypeId() {
        return mappedDateTypeId;
    }

    public void setMappedDateTypeId(int mappedDateTypeId) {
        this.mappedDateTypeId = mappedDateTypeId;
    }

    public int getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(int dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getDataTypeVisId() {
        return dataTypeVisId;
    }

    public void setDataTypeVisId(String dataTypeVisId) {
        this.dataTypeVisId = dataTypeVisId;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBalType() {
        return balType;
    }

    public void setBalType(String balType) {
        this.balType = balType;
    }

    public int getImpExpStatus() {
        return impExpStatus;
    }

    public void setImpExpStatus(int impExpStatus) {
        this.impExpStatus = impExpStatus;
    }

    public Integer getImpStartYearOffset() {
        return impStartYearOffset;
    }

    public void setImpStartYearOffset(Integer impStartYearOffset) {
        this.impStartYearOffset = impStartYearOffset;
    }

    public Integer getImpEndYearOffset() {
        return impEndYearOffset;
    }

    public void setImpEndYearOffset(Integer impEndYearOffset) {
        this.impEndYearOffset = impEndYearOffset;
    }

    public Integer getExpStartYearOffset() {
        return expStartYearOffset;
    }

    public void setExpStartYearOffset(Integer expStartYearOffset) {
        this.expStartYearOffset = expStartYearOffset;
    }

    public Integer getExpEndYearOffset() {
        return expEndYearOffset;
    }

    public void setExpEndYearOffset(Integer expEndYearOffset) {
        this.expEndYearOffset = expEndYearOffset;
    }

}
