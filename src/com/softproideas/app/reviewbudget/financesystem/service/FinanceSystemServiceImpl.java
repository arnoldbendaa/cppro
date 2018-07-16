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
package com.softproideas.app.reviewbudget.financesystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.FinanceSystemCellData;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.softproideas.app.reviewbudget.financesystem.mapper.InvoiceMapper;
import com.softproideas.app.reviewbudget.financesystem.model.InvoiceDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

/**
 * Service for managing finance data from external systems.
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Service("financeSystemService")
public class FinanceSystemServiceImpl implements FinanceSystemService {

    private static Logger logger = LoggerFactory.getLogger(FinanceSystemServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /* 
     * (non-Javadoc)
     * @see com.softproideas.spreadsheet.webapp.service.FinanceSystemService#browseInvoices(java.lang.String, java.lang.String) 
     */
    @Override
    public InvoiceDTO browseInvoices(String cellPK, String financeCubeToken, int cmpy) throws ServiceException {
        int financeCubeId = 0;
        if (financeCubeToken.startsWith("FinanceCubeCK")) {
            financeCubeId = ((FinanceCubeCK) FinanceCubeCK.getKeyFromTokens(financeCubeToken)).getFinanceCubePK().getFinanceCubeId();
        } else {
            financeCubeId = new Integer(financeCubeToken).intValue();
        }

        String[] urlProperties = browseInvoiceProperties();
        FinanceSystemCellData financeCellSystemData = fetchFinanceSystemCellData(financeCubeId, cellPK, cmpy);
        return InvoiceMapper.mapToInvoiceDTO(financeCellSystemData, urlProperties);
    }

    /**
     * Method returns url, user, password for invoice
     */
    private String[] browseInvoiceProperties() {
        String[] urlProperties = cpContextHolder.getDataEntryProcess().getPropertiesForInvoice();
        return urlProperties;
    }

    /**
     * Method return data related to cell (form cell properties stored in cellPK) and specific finance cube
     */
    private FinanceSystemCellData fetchFinanceSystemCellData(int financeCubeId, String cellPK, int cmpy) throws ServiceException {
        try {
            return cpContextHolder.getDataEntryProcess().getFinanceSystemCellData(financeCubeId, null, null, cellPK, " ", cpContextHolder.getUserId(), cmpy);
        } catch (ValidationException e) {
        	e.printStackTrace();
            logger.error("Can not download invoices");
            throw new ServiceException("Can not download invoices.", e);
        }
    }

}
