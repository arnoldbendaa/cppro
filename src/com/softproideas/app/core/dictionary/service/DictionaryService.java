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
package com.softproideas.app.core.dictionary.service;

import java.util.List;

import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.common.models.DictionaryDTO;
import com.softproideas.commons.model.ResponseMessage;

/**
 * 
 * <p>{@link DictionaryService} defines methods operating on dictionary elements.</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface DictionaryService {

    List<DictionaryDTO> browseDictionaries();

    List<DictionaryDTO> browseDictionaries(String type);

    DictionaryDTO fetchDictionary(String key);

    DictionaryDTO saveDictionary(DictionaryDTO dictionaryDTO, String type) throws ServiceException;

    List<DictionaryDTO> saveDictionaries(List<DictionaryDTO> dictionariesDTO, String type) throws ServiceException;

    DictionaryDTO insertDictionary(DictionaryDTO dictionaryDTO) throws ServiceException;

    List<DictionaryDTO> insertDictionaries(List<DictionaryDTO> dictionaries) throws ServiceException;

    ResponseMessage updateDictionary(DictionaryDTO lookupCurrencyDTO) throws ServiceException;

    ResponseMessage updateDictionaries(List<DictionaryDTO> dictionaries) throws ServiceException;

    ResponseMessage deleteDictionary(String key) throws ServiceException;

    ResponseMessage deleteDictionaries(List<DictionaryDTO> dictionaries) throws ServiceException;

    List<DictionaryDTO> importDictionaries(String type) throws ServiceException;

}
