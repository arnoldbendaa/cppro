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
package com.softproideas.app.core.form.mapper;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.softproideas.app.core.form.model.FormDTO;
import com.softproideas.commons.model.enums.FormType;

/**
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class FormMapper {

    /**
     * Maps <code>EntityList</code> to list of <code>FormDTO</code>s
     */
    public static List<FormDTO> mapToDTO(EntityList xmlForms) {
        List<FormDTO> xmlFormsList = new ArrayList<FormDTO>();
        for (Object[] xmlForm: xmlForms.getDataAsArray()) {
            FormDTO xmlFormDTO = new FormDTO();
            XmlFormRef xmlFormRef = (XmlFormRef) xmlForm[0];
            xmlFormDTO.setFormId(((XmlFormPK) xmlFormRef.getPrimaryKey()).getXmlFormId());
            xmlFormDTO.setDescription((String) xmlForm[3]);
            xmlFormDTO.setName(xmlFormRef.getNarrative());
            xmlFormDTO.setType((String) xmlForm[4]);
            xmlFormDTO.setDefaultForm((Boolean) xmlForm[5]);
            xmlFormsList.add(xmlFormDTO);
        }
        return xmlFormsList;
    }

    public static FormType mapFormType(int formTypeNum) {
        FormType formType = null;
        if (formTypeNum == 6) {
            formType = FormType.XCELLFORM;
        } else {
            formType = FormType.FINANCEFORM;
        }
        return formType;
    }
}
