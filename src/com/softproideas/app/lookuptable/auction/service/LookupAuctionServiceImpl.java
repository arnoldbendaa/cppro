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
package com.softproideas.app.lookuptable.auction.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softproideas.app.lookuptable.auction.dao.LookupAuctionDao;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionFilterDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;

@Service("lookupAuctionService")
public class LookupAuctionServiceImpl implements LookupAuctionService {
    
    @Autowired
    LookupAuctionDao lookupAuctionDao;

    @Override
    public List<LookupAuctionDTO> browseAuctions(String orderby, String[] filters) throws ServiceException {
        try {
            List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
            List<LookupAuctionDTO> auctions = lookupAuctionDao.browseAuctions(orderby, filterObjects);
            return auctions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    @Override
    public List<LookupAuctionDTO> browseAuctions(Integer pageNumber, String orderby, String[] filters) throws ServiceException {
        try {
            List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
            List<LookupAuctionDTO> auctions = lookupAuctionDao.browseAuctions(pageNumber, orderby, filterObjects);
            return auctions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
        
    @Override
    public List<LookupAuctionDTO> browseAuctions(Integer pageSize, Integer pageNumber, String orderby, String[] filters) throws ServiceException {

        try {
            List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
            return lookupAuctionDao.browseAuctions(pageSize, pageNumber, orderby, filterObjects);
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

    @Override
    public Integer getRowsCount() throws ServiceException {
        try {
            return lookupAuctionDao.getRowsCount();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<LookupAuctionDTO> browseAuctions(int auctionNumber) throws ServiceException {
        try {
            List<LookupAuctionDTO> auctions = lookupAuctionDao.browseAuctions(auctionNumber);
            return auctions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
