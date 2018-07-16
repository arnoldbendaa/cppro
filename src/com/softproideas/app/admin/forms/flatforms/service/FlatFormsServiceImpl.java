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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlform.AllXcellXmlFormsELO;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.softproideas.app.core.flatform.mapper.FlatFormsCoreMapper;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.lookuptable.auction.dao.LookupAuctionDao;
import com.softproideas.app.lookuptable.auction.model.FlatFormDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionFilterDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

/**
 * <p> 
 * {@link FlatFormsServiceImpl} fetches all available Wijmo based forms.
 * </p>
 *
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("flatFormsService")
public class FlatFormsServiceImpl implements FlatFormsService {
    private static Logger logger = LoggerFactory.getLogger(FlatFormsServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    private AllXcellXmlFormsELO allFormsForUser = null;

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.forms.flatforms.service.FlatFormsService#fetchFlatForms() */
    @Override
    public List<FlatFormExtendedCoreDTO> fetchFlatForms() throws ServiceException {
        try {
            // Retrieve all forms current user has rights to.
            this.allFormsForUser = cpContextHolder.getListSessionServer().getAllXcellXmlFormsForLoggedUser();
            // Return data converted to DTO, then JSON
            return FlatFormsCoreMapper.mapAllXcellXmlFormsELO(this.allFormsForUser);
        } catch (Exception e) {
            logger.error("Error during fetching flat forms!", e);
            throw new ServiceException("Error during fetching flat forms!", e);
        }
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.forms.flatforms.service.FlatFormsService#deleteFlatForms(int) */
    @Override
    public ResponseMessage deleteFlatForms(int flatFormId) throws ServiceException {
        XmlFormPK flatFormPK = new XmlFormPK(flatFormId);
        try {
            XmlFormEditorSessionServer server = cpContextHolder.getXmlFormEditorSessionServer();
            server.deleteFormAndProfiles(flatFormPK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (CPException e) {
            logger.error("Error during delete Flat Form!", e);
            throw new ServiceException("Error during delete Flat Form!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during delete Flat Form!", e);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    @Autowired
    LookupAuctionDao lookupAuctionDao;

    public static final Integer defaultPageSize = 100;

    public static final Integer defaultPageNumber = 1;

    @Override
    public List<FlatFormDTO> fetchFlatFormsForFinanceCubeId(final Integer pageSize, final Integer pageNumber, String orderby, String[] filters, int financeCubeId) throws ServiceException {
        try {
            List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
            return lookupAuctionDao.fetchFlatFormsForFinanceCubeId(pageSize, pageNumber, orderby, filterObjects, financeCubeId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<FlatFormDTO> fetchFlatFormsForFinanceCubeId(String orderby, String[] filters, int financeCubeId) throws ServiceException {
        try {
            List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
            return lookupAuctionDao.fetchFlatFormsForFinanceCubeId(defaultPageSize, defaultPageNumber, orderby, filterObjects, financeCubeId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<FlatFormDTO> fetchFlatFormsForFinanceCubeId(Integer pageNumber, String orderby, String[] filters, int financeCubeId) throws ServiceException {
        try {
            List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
            return lookupAuctionDao.fetchFlatFormsForFinanceCubeId(defaultPageSize, pageNumber, orderby, filterObjects, financeCubeId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private List<LookupAuctionFilterDTO> createFilterList(String[] filters) {
        List<LookupAuctionFilterDTO> filterObjects = new ArrayList<LookupAuctionFilterDTO>();
        if (filters != null && filters.length != 0) {
            for (String filter: filters) {
                LookupAuctionFilterDTO filterObject = new LookupAuctionFilterDTO(filter);
                filterObjects.add(filterObject);
            }
        }
        return filterObjects;
    }

}
