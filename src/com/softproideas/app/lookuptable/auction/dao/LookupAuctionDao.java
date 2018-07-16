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
package com.softproideas.app.lookuptable.auction.dao;

import java.util.List;

import com.softproideas.app.lookuptable.auction.model.DashboardAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.FlatFormDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionFilterDTO;
import com.softproideas.common.exceptions.DaoException;

/**
 * 
 * <p>{@link LookupAuctionDao} defines methods operating on currency elements.</p>
 *
 * @author Jakub Piskorz
 * @email jakub.piskorz@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */

public interface LookupAuctionDao {

    List<LookupAuctionDTO> browseAuctions(String orderby, List<LookupAuctionFilterDTO> filters) throws DaoException;

    List<LookupAuctionDTO> browseAuctions(Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters) throws DaoException;

    List<LookupAuctionDTO> browseAuctions(Integer pageSize, Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filterObjects) throws DaoException;

    Integer getRowsCount() throws DaoException;

    List<LookupAuctionDTO> browseAuctions(int auctionNumber) throws DaoException;

    List<DashboardAuctionDTO> browseAuctionsForCompany(Integer pageSize, Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters, int company) throws DaoException;

    List<DashboardAuctionDTO> browseAuctionsForCompany(Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters, int company) throws DaoException;

    List<DashboardAuctionDTO> browseAuctionsForCompany(String orderby, List<LookupAuctionFilterDTO> filters, int company) throws DaoException;

    List<FlatFormDTO> fetchFlatFormsForFinanceCubeId(Integer pageSize, Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters, int financeCubeId) throws DaoException;

}
