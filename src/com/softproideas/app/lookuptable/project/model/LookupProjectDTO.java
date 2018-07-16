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
package com.softproideas.app.lookuptable.project.model;

public class LookupProjectDTO {

    private Integer company;
    private String costcentre;
    private String project;
    private String expensecode;
    private Integer yearno;
    private Integer period;
    private Double baseVal;
    private Double qty;
    private Double cumBaseVal;
    private Double cumQty;
    
    public Integer getCompany() {
        return company;
    }
    public void setCompany(Integer company) {
        this.company = company;
    }
    public String getCostcentre() {
        return costcentre;
    }
    public void setCostcentre(String costcentre) {
        this.costcentre = costcentre;
    }
    public String getProject() {
        return project;
    }
    public void setProject(String project) {
        this.project = project;
    }
    public String getExpensecode() {
        return expensecode;
    }
    public void setExpensecode(String expensecode) {
        this.expensecode = expensecode;
    }
    public Integer getYearno() {
        return yearno;
    }
    public void setYearno(Integer yearno) {
        this.yearno = yearno;
    }
    public Integer getPeriod() {
        return period;
    }
    public void setPeriod(Integer period) {
        this.period = period;
    }
    public Double getBaseVal() {
        return baseVal;
    }
    public void setBaseVal(Double baseVal) {
        this.baseVal = baseVal;
    }
    public Double getQty() {
        return qty;
    }
    public void setQty(Double qty) {
        this.qty = qty;
    }
    public Double getCumBaseVal() {
        return cumBaseVal;
    }
    public void setCumBaseVal(Double cumBaseVal) {
        this.cumBaseVal = cumBaseVal;
    }
    public Double getCumQty() {
        return cumQty;
    }
    public void setCumQty(Double cumQty) {
        this.cumQty = cumQty;
    }
}
