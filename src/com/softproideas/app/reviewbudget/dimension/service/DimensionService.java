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
package com.softproideas.app.reviewbudget.dimension.service;

import java.util.List;

import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.TreeNodeDTO;
import com.softproideas.commons.model.enums.DimensionType;

public interface DimensionService {

    /**
     * Method return first three levels of dimensions in a tree structure.
     * Next levels are returned by the <code>browseChildren</code> method.
     * 
     * @param dimensionType
     *            specific dimension type (ACCOUNT, BUSSINESS, CALENDAR)
     * @param budgetCycleId
     *            budget cycle identifier
     * @param modelId
     *            model identifier, each model has its own dimensions
     * @return first three levels of dimensions in a tree structure
     * @throws ServiceException
     */
    List<TreeNodeDTO> browseDimensions(DimensionType dimensionType, int budgetCycleId, int modelId) throws ServiceException;

    /**
     * Method return children for parent element which are stored on the third level
     * in the tree dimensions.
     * 
     * @param dimensionType
     *            specific dimension type (ACCOUNT, BUSSINESS, CALENDAR)
     * @param budgetCycleId
     *            budget cycle identifier
     * @param parent
     *            parent of dimension elements
     * @return children related to parent
     * @throws ServiceException
     */
    List<TreeNodeDTO> browseChildren(DimensionType dimensionType, int budgetCycleId, ElementDTO parent) throws ServiceException;

    /**
     * Method return all data types in a tree structure.
     * 
     * @return all data types in a tree structure
     * @throws ServiceException
     */
    List<TreeNodeDTO> browseDataTypes() throws ServiceException;

    /**
     * Converts mapping to valid cellPk/notePk
     * 
     * @param modelId
     * @param mapping i/o mapping
     * @param context array of dimensions and datatype
     * @param sheetModel model from sheet properties
     * @return cellPk "dim0,dim1,dim2,dt"
     * @throws ServiceException Throws when is not a leaf.
     */
    String fetchCellPK(int modelId, String mapping, String[] context, String sheetModel) throws ServiceException;
    
    /**
     * Converts dimensions and data type transfer objects to string. Merge dimensions ids and datatype name
     * 
     * @param dimensions
     * @param dataType
     * @return cellPk "dim0,dim1,dim2,dt"
     */
    String fetchCellPK(List<ElementDTO> dimensions, ElementDTO dataType);
    
    /**
     * Method returns details for dimensions stored in mapping and context. Dimensions from context are taken 
     * in the event that some mapping hasn't got a specific dimension
     * @param modelId
     * @param mapping i/o mapping
     * @param context array of dimensions and datatype
     * @param sheetModel model from sheet properties
     * @return 
     * @throws ServiceException Throws when is not a leaf.
     */
    List<ElementDTO> fetchDimensionDetails(int modelId, String mapping, String[] context, String sheetModel) throws ServiceException;
    
    /**
     * Method returns data type details from mapping or contextDataType
     * @param mapping
     * @param contextDataType
     * @return
     */
    ElementDTO fetchDataType(String mapping, String contextDataType);

    /**
     * Check if all dimensions are leafs
     * @param dimensions
     * @return
     */
    boolean checkIfDimensionAreLeafs(List<ElementDTO> dimensions);
}
