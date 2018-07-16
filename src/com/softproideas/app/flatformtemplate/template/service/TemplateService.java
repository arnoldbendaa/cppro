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
package com.softproideas.app.flatformtemplate.template.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.softproideas.app.flatformtemplate.template.model.MoveEvent;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.SelectListElementDTO;

public interface TemplateService {

    TemplateDetailsDTO browseTemplates(Boolean disableDirectories) throws ServiceException;

    TemplateDetailsDTO fetchTemplate(UUID templateUUID) throws ServiceException;

    TemplateDetailsDTO insertTemplate(TemplateDetailsDTO template) throws ServiceException;

    ResponseMessage updateTemplate(TemplateDetailsDTO template, Boolean fullUpdate) throws ServiceException;

    ResponseMessage deleteTemplate(UUID templateUUID) throws ServiceException;

    TemplateDetailsDTO upload(MultipartFile file) throws ServiceException;

    byte[] exportToXLSX(String jsonForm) throws IOException, ServiceException;

    File exportFlatForms(UUID templateUUID) throws ServiceException, SQLException, DaoException;

    ArrayList<SelectListElementDTO> getTemplateTypes();

    /**
     * @param moveEvent
     * @return
     * @throws ServiceException
     */
    ResponseMessage updateTemplateIndex(MoveEvent moveEvent) throws ServiceException;

    WorkbookDTO fileToWorkbookDTO(MultipartFile file) throws IOException, InvalidFormatException;

}
