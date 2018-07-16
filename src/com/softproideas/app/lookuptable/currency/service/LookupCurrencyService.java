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
package com.softproideas.app.lookuptable.currency.service;

import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Workbook;

import com.softproideas.app.lookuptable.currency.model.LookupCurrencyChangeDTO;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyDTO;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyImportDataDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

/**
 * 
 * <p>{@link LookupCurrencyService} defines methods operating on currency elements.</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface LookupCurrencyService {

    List<LookupCurrencyDTO> browseCurrencies();
    
    List<LookupCurrencyDTO> browseCurrencies(int company, int year);
    
    LookupCurrencyDTO fetchCurrency(UUID currencyUUID) throws ServiceException;
    
    List<LookupCurrencyDTO> saveCurrencies(List<LookupCurrencyDTO> lookupCurrencies) throws ServiceException;

    LookupCurrencyDTO insertCurrency(LookupCurrencyDTO lookupCurrencyDTO) throws ServiceException;
    
    List<LookupCurrencyDTO> insertCurrencies(List<LookupCurrencyDTO> currencies) throws ServiceException;
    
    ResponseMessage updateCurrency(LookupCurrencyDTO lookupCurrencyDTO) throws ServiceException;
    
    ResponseMessage updateCurrencies(List<LookupCurrencyDTO> currencies) throws ServiceException;
    
    ResponseMessage deleteCurrency(UUID currencyUUID) throws ServiceException;

    ResponseMessage deleteCurrencies(List<LookupCurrencyDTO> currenciesToDelete) throws ServiceException;

    LookupCurrencyImportDataDTO upload(Workbook workbook) throws ServiceException;

    boolean updateChanges(List<LookupCurrencyChangeDTO> changes);

}
