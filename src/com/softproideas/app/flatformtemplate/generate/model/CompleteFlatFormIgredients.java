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
/**
 * 
 */
package com.softproideas.app.flatformtemplate.generate.model;

import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class CompleteFlatFormIgredients {
    private byte[] excelFile;
    private CompleteWorkbook completeWorkbook;
    private TemplateDetailsDTO templateDetailsDTO;

    /**
     * @param excelFile2
     * @param completeWorkbook2
     * @param templateDetailsDTO2
     */
    public CompleteFlatFormIgredients(byte[] excelFile2, CompleteWorkbook completeWorkbook2, TemplateDetailsDTO templateDetailsDTO2) {
        excelFile = excelFile2;
        completeWorkbook = completeWorkbook2;
        templateDetailsDTO = templateDetailsDTO2;
    }

    public byte[] getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(byte[] excelFile) {
        this.excelFile = excelFile;
    }

    public CompleteWorkbook getCompleteWorkbook() {
        return completeWorkbook;
    }

    public void setCompleteWorkbook(CompleteWorkbook completeWorkbook) {
        this.completeWorkbook = completeWorkbook;
    }

    public TemplateDetailsDTO getTemplateDetailsDTO() {
        return templateDetailsDTO;
    }

    public void setTemplateDetailsDTO(TemplateDetailsDTO templateDetailsDTO) {
        this.templateDetailsDTO = templateDetailsDTO;
    }
}
