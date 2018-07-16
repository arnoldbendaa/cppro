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
package com.softproideas.app.core.dictionary.dao;

import java.util.List;

import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.models.DictionaryDTO;

/**
 * 
 * <p>{@link DictionaryDao} defines methods operating on dictionary elements.</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface DictionaryDao {

    List<DictionaryDTO> browseDictionaries() throws DaoException;

    List<DictionaryDTO> browseDictionaries(String type) throws DaoException;

    DictionaryDTO fetchDictionary(String key) throws DaoException;

    boolean insertDictionary(DictionaryDTO dictionary) throws DaoException;

    boolean insertDictionaries(List<DictionaryDTO> dictionaries) throws DaoException;

    boolean updateDictionary(DictionaryDTO dictionary) throws DaoException;

    boolean updateDictionaries(List<DictionaryDTO> dictionaries) throws DaoException;

    boolean deleteDictionary(String key) throws DaoException;

    boolean deleteDictionaries(List<DictionaryDTO> dictionaries) throws DaoException;

}