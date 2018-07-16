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
package com.softproideas.app.admin.financecubeformula.services;

import java.util.List;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.softproideas.app.admin.financecubeformula.model.FinanceCubeFormulaDTO;
import com.softproideas.app.admin.financecubeformula.model.FinanceCubeFormulaDetailsDTO;
import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;
import com.softproideas.app.core.dimension.model.DimensionWithHierarchiesCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

public interface FinanceCubeFormulaService {

    FinanceCubeFormulaDetailsDTO getFinanceCubeFormulaDetails(int modelId, int financeCubeId, int financeCubeFormulaId) throws ServiceException, CPException, ValidationException;

    ResponseMessage saveFinanceCubFormulaDetails(FinanceCubeFormulaDetailsDTO financeCubeFormulaDetailsDTO) throws ServiceException;
    
    List<FinanceCubeFormulaDTO> browseFinanceCubesFormula();

    String getFinanceCubeFormulaComplie(int modelId, int financeCubeId, int financeCubeFormulaId, String formulaText, int formulaType) throws ServiceException;

    ResponseMessage delete(int modelId, int financeCubeId, int financeCubeFormulaId) throws ServiceException;

    List<DimensionWithHierarchiesCoreDTO> fetchModelDimensionsWithHierarchies(int modelId) throws ServiceException;
    //trees
    List<HierarchyNodeLazyDTO> browseDataTypesNode(Integer modelId) throws ServiceException;

    List<HierarchyNodeLazyDTO> dataTypesRoot(Integer modelId, String disableLevel) throws ServiceException;

    List<HierarchyNodeLazyDTO> calendarDimension(Integer modelId, String disableLevel) throws ServiceException;

    List<HierarchyNodeLazyDTO> calendarRoot(Integer modelId, int calendarId,String disableLevel) throws ServiceException, CPException, ValidationException;

    List<HierarchyNodeLazyDTO> browseCalendarNode(Integer modelId, int hierarchyElementId) throws ServiceException, CPException, ValidationException;

    List<HierarchyNodeLazyDTO> hierarchyDimensionCc(Integer modelId, String disableLevel) throws ServiceException, CPException, ValidationException;

    List<HierarchyNodeLazyDTO> hierarchyRootCc(Integer modelId, Integer hierarchyId, String disableLevel) throws ServiceException, CPException, ValidationException;

    List<HierarchyNodeLazyDTO> browseHierarchyCcNode(Integer modelId, int hierarchyElementId) throws ServiceException, CPException, ValidationException;

    List<HierarchyNodeLazyDTO> hierarchyDimensionExp(Integer modelId, String disableLevel) throws ServiceException;

    List<HierarchyNodeLazyDTO> hierarchyRootExp(Integer modelId, Integer hierarchyId) throws ServiceException, CPException, ValidationException;

    List<HierarchyNodeLazyDTO> browseHierarchyExpNode(Integer modelId, int hierarchyElementId) throws ServiceException, CPException, ValidationException;

    DimensionDTO[] getTableHeader(int modelId) throws ServiceException;



}
