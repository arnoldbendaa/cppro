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
package com.softproideas.app.lookuptable.currency.dao;

import java.util.List;
import java.util.UUID;

import com.softproideas.app.lookuptable.currency.model.LookupCurrencyChangeDTO;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyDTO;
import com.softproideas.common.exceptions.DaoException;

/**
 * 
 * <p>{@link LookupCurrencyDao} defines methods operating on currency elements.</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface LookupCurrencyDao {

    List<LookupCurrencyDTO> browseCurrencies() throws DaoException;
    
    List<LookupCurrencyDTO> browseCurrencies(int company, int year) throws DaoException;

    LookupCurrencyDTO fetchCurrency(UUID currencyUUID) throws DaoException;
    
    LookupCurrencyDTO fetchCurrency(LookupCurrencyDTO lookupCurrency) throws DaoException;
    
    boolean insertCurrency(LookupCurrencyDTO lookupCurrency) throws DaoException;

    boolean insertCurrencies(List<LookupCurrencyDTO> currencies) throws DaoException;
    
    boolean updateCurrency(LookupCurrencyDTO lookupCurrencyDTO) throws DaoException;
    
    boolean updateCurrencyValue(LookupCurrencyDTO lookupCurrencyDTO) throws DaoException;
    
    boolean updateCurrenciesValue(List<LookupCurrencyDTO> currencies) throws DaoException;
    
    boolean deleteCurrency(UUID currencyUUID) throws DaoException;

    boolean deleteCurrencies(List<LookupCurrencyDTO> currencies) throws DaoException;

    boolean updateChanges(List<LookupCurrencyChangeDTO> changes) throws DaoException;

}