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
package com.softproideas.app.flatformeditor.form.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEditorSessionSEJB;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.util.CellType;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.app.core.dimension.model.DimensionWithHierarchiesCoreDTO;
import com.softproideas.app.flatformeditor.form.model.FlatFormDetailsDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

/**
 * <p> 
 * This service is aimed to take care of flat form CRUD operations.
 * </p>
 *
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface FormService {

    /**
     * Fetches flat form information and data by id.
     * 
     * @param flatFormId
     * @return
     * @throws Exception
     */
    //public FlatFormDetailsDTO fetchFlatForm(int flatFormId) throws Exception;

    public List<DimensionWithHierarchiesCoreDTO> fetchModelDimensionsWithHierarchies(int modelId);

    public List<DimensionCoreDTO> fetchModelDimensions(int modelId);
    
    public FlatFormDetailsDTO upload(MultipartFile file) throws ServiceException;

    public ResponseMessage save(FlatFormDetailsDTO flatForm, boolean saveAs) throws ServiceException;

    public WorkbookDTO workbookTest(WorkbookDTO workbook) throws Exception;

    public ResponseMessage copyTemplate(List<Integer> flatFormIds) throws ServiceException, CPException, ValidationException;

    public WorkbookDTO manageXmlForm(Workbook workbook, boolean isTemplateProcess);
    
    public XmlFormImpl getXmlFormFromServer(XmlFormEditorSessionSEJB server, int flatFormId) throws ServiceException;

    FlatFormDetailsDTO fetchFlatForm(int flatFormId, CellType cellType) throws Exception;

    FlatFormDetailsDTO fetchFlatFormWithExcelFile(int flatFormId, CellType cellType) throws Exception;

	public ResponseMessage toggleLockFlag(int xmlFormId, boolean flag);

	public ResponseMessage getLockFlag(int xmlFormId);
}