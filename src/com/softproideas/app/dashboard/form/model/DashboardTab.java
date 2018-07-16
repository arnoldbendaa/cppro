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
package com.softproideas.app.dashboard.form.model;

import java.io.Serializable;

import com.softproideas.app.dashboard.form.TabType;

public class DashboardTab implements Serializable {
    private String tabName;
    private CellRange rowsRange;
    private CellRange colsRange;
    private CellRange valuesRange;
    private TabType chartType;
    private String hideCells;
    
    public String getHideCells() {
        return hideCells;
    }
    public void setHideCells(String hideCells) {
        this.hideCells = hideCells;
    }
    public String getTabName() {
        return tabName;
    }
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
    public CellRange getRowsRange() {
        return rowsRange;
    }
    public void setRowsRange(CellRange rowsRange) {
        this.rowsRange = rowsRange;
    }
    public CellRange getColsRange() {
        return colsRange;
    }
    public void setColsRange(CellRange colsRange) {
        this.colsRange = colsRange;
    }
    public CellRange getValuesRange() {
        return valuesRange;
    }
    public void setValuesRange(CellRange valuesRange) {
        this.valuesRange = valuesRange;
    }
	public String getChartType() {
		return chartType.name().toLowerCase();
	}
	public void setChartType(String chartType) {
		this.chartType = TabType.valueOf(TabType.class, chartType.toUpperCase());
	}
    
}
