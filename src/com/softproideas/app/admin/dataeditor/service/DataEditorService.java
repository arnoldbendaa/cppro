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
package com.softproideas.app.admin.dataeditor.service;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.softproideas.app.admin.dataeditor.model.DataEditorImportedData;
import com.softproideas.app.admin.dataeditor.model.DataEditorRow;
import com.softproideas.app.admin.dataeditor.model.DataEditorSearchOption;
import com.softproideas.app.admin.dataeditor.model.DimensionDataForModelDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

public interface DataEditorService {

    /**
     * Retrieves all dimension elements for models. Function returns hash map where keys are modelIds and value are stored in {@link DimensionDataForModelDTO}
     * @return map which contain keys - modelIds, value of {@link DimensionDataForModelDTO}
     */
    HashMap<Integer, DimensionDataForModelDTO> browseDimensionElements(List<Integer> modelsIds) throws ServiceException;

    /**
     * Retrieves all data rows which are matched to search options. 
     * @param dataEditorSearchOption - filter object {@link DataEditorSearchOption}
     */
    List<DataEditorRow> displayDataForSearchOption(DataEditorSearchOption dataEditorSearchOption) throws ServiceException;

    /**
     * Saves edited rows in data editor
     */
    ResponseMessage save(List<DataEditorRow> editedRows) throws ServiceException;

    /**
     * Upload/import data from excel file.
     * @return imported data - {@link DataEditorImportedData}
     */
    DataEditorImportedData upload(Workbook workbook) throws ServiceException;
}
