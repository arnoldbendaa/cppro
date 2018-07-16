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
package com.softproideas.app.admin.hierarchies.service;

import java.util.List;

import com.cedar.cp.api.base.ValidationException;
import com.softproideas.app.admin.hierarchies.model.HierarchyDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyDetailsDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

/**
 * <p>This service is aimed to take care of hierarchies CRUD operations,
 * fetch all available dimensions for create new hierarchy
 * and browse Node</p>
 *
 * @author Szymon Zberaz, Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface HierarchiesService {

    List<HierarchyDTO> browseHierarchy(boolean type) throws ServiceException;

    HierarchyDetailsDTO fetchHierarchyDetails(Integer dimensionId, Integer hierarchyId, boolean type) throws ServiceException;

    List<HierarchyNodeLazyDTO> browseHierarchysNode(Integer dimensionId, Integer hierarchyId, boolean type) throws ServiceException;

    List<HierarchyNodeLazyDTO> browseRoot(Integer dimensionId, Integer hierarchyId, Integer hierarchyElementId, boolean type) throws ServiceException;

    ResponseMessage save(HierarchyDetailsDTO account, boolean type) throws ServiceException, ValidationException;

    List<DimensionCoreDTO> fetchAvailableDimension(boolean type) throws ServiceException;

    ResponseMessage delete(Integer dimensionId, Integer hierarchyId) throws ServiceException;
    
}
