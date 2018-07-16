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
package com.softproideas.app.admin.forms.flatforms.service;

import java.util.List;

import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.lookuptable.auction.model.FlatFormDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

/**
 * 
 * <p> 
 * {@link FlatFormsService} defines methods operating on
 * any implementation of flat form including: JSON based Wijmo 
 * and MS Excel based JXCell. Implementation of this interface
 * is supposed to be specific to Wijmo or JXCell.
 * </p>
 *
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface FlatFormsService {

    /**
     * Retrieves all flat forms of current user.
     * 
     * @return list of {@link FlatFormExtendedCoreDTO}
     * @throws ServiceException
     */
    List<FlatFormExtendedCoreDTO> fetchFlatForms() throws ServiceException;

    /**
     * @param flatFormId
     * @return
     * @throws ServiceException 
     */
    ResponseMessage deleteFlatForms(int flatFormId) throws ServiceException;

    List<FlatFormDTO> fetchFlatFormsForFinanceCubeId(Integer pageSize, Integer pageNumber, String orderby, String[] filters, int financeCubeId) throws ServiceException;

    List<FlatFormDTO> fetchFlatFormsForFinanceCubeId(String orderby, String[] filters, int financeCubeId) throws ServiceException;

    List<FlatFormDTO> fetchFlatFormsForFinanceCubeId(Integer pageNumber, String orderby, String[] filters, int financeCubeId) throws ServiceException;

}
