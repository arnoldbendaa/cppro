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
package com.softproideas.app.dashboard.auction.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.cedar.cp.utc.common.CPContext;
import com.softproideas.app.lookuptable.auction.model.DashboardAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionFilterDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

public interface DashboardService {
    Map<String, Map<String, Map<String, String>>> browseAuctions(int auctionNumber) throws ServiceException;

    void fetchExcelMobileFlatForm(Workbook workbook, int auctionNumber) throws ServiceException;

    Set<String> fetchModelsForLoggedUser() throws ServiceException;

//    List<DashboardAuctionDTO> browseAuctionsForCompany(Integer pageSize, Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters, int company) throws DaoException;
//
//    List<DashboardAuctionDTO> browseAuctionsForCompany(Integer pageSize, Integer pageNumber, String orderby, String[] filters, int company) throws DaoException, ServiceException;
//
//    List<DashboardAuctionDTO> browseAuctionsForCompany(Integer pageNumber, String orderby, String[] filters, int company) throws ServiceException;
//
//    List<DashboardAuctionDTO> browseAuctionsForCompany(String orderby, String[] filters, int company) throws ServiceException;

    List<DashboardAuctionDTO> browseAuctionsForCompany(Integer pageSize, Integer pageNumber, String orderby, String[] filters, String modelDescription) throws ServiceException;

    List<DashboardAuctionDTO> browseAuctionsForCompany(String orderby, String[] filters, String modelDescription) throws ServiceException;

    List<DashboardAuctionDTO> browseAuctionsForCompany(Integer pageNumber, String orderby, String[] filters, String modelDescription) throws ServiceException;

    boolean checkAuctionPrivileges(Integer auctionNumber) throws ServiceException, DaoException;

    boolean checkFormPrivileges(String formUUID, Integer currentUserId) throws ServiceException, DaoException;
    
    void Init(int userId,CPContext context);
}
