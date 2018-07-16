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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softproideas.app.core.dictionary.dao.DictionaryDao;
import com.softproideas.app.core.dictionary.dao.DictionaryOADao;
import com.softproideas.app.core.dictionary.util.DictionaryUtil;
import com.softproideas.app.core.dictionary.util.DictionaryValidator;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.common.models.DictionaryDTO;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.FieldError;
import com.softproideas.commons.model.error.ValidationError;

@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    DictionaryDao dictionaryDao;

    @Autowired
    DictionaryOADao dictionaryOADao;

    private static Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    @Override
    public List<DictionaryDTO> browseDictionaries() {
        try {
            return dictionaryDao.browseDictionaries();
        } catch (DaoException e) {
            return new ArrayList<DictionaryDTO>();
        }
    }

    @Override
    public List<DictionaryDTO> browseDictionaries(String type) {
        try {
            return dictionaryDao.browseDictionaries(type);
        } catch (DaoException e) {
            return new ArrayList<DictionaryDTO>();
        }
    }

    @Override
    public DictionaryDTO fetchDictionary(String key) {
        try {
            return dictionaryDao.fetchDictionary(key);
        } catch (DaoException e) {
            return null;
        }
    }

    @Override
    public DictionaryDTO saveDictionary(DictionaryDTO dictionary, String type) throws ServiceException {
        if (type == null) {
            type = dictionary.getType();
        }
        ValidationError error = DictionaryValidator.validateDictionary(dictionary);

        List<FieldError> fieldErrors = error.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            String errorMessage = "Error during inserting query! Fields [";
            for (FieldError fieldError: fieldErrors) {
                errorMessage += fieldError.getFieldName().toUpperCase();
                errorMessage += " - " + fieldError.getFieldMessage();
            }
            errorMessage += "] are not valid in " + dictionary.getValue() + ".";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }
        DictionaryUtil.manageDictionary(dictionary, type);
        insertDictionary(dictionary);
        return dictionary;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public List<DictionaryDTO> saveDictionaries(List<DictionaryDTO> dictionariesDTO, String type) throws ServiceException {
        DictionaryUtil.manageDictionaries(dictionariesDTO, type);
        List<DictionaryDTO> dictionariesDB = browseDictionaries(type);

        List<DictionaryDTO> dictionariesToInsert = new ArrayList<DictionaryDTO>();
        dto: for (DictionaryDTO dictionaryDTO: dictionariesDTO) {
            for (DictionaryDTO dictionaryDB: dictionariesDB) {
                if (dictionaryDB.getKey().equals(dictionaryDTO.getKey())) {
                    continue dto;
                }
            }
            dictionariesToInsert.add(dictionaryDTO);
        }
        dictionariesDTO.removeAll(dictionariesToInsert);

        List<DictionaryDTO> dictionariesDBToDelete = new ArrayList<DictionaryDTO>();
        db: for (DictionaryDTO dictionaryDB: dictionariesDB) {
            for (DictionaryDTO dictionaryDTO: dictionariesDTO) {
                if (dictionaryDB.getKey().equals(dictionaryDTO.getKey())) {
                    continue db;
                }
            }
            dictionariesDBToDelete.add(dictionaryDB);
        }
        dictionariesDB.removeAll(dictionariesDBToDelete);

        List<DictionaryDTO> dictionariesToUpdate = new ArrayList<DictionaryDTO>();
        for (DictionaryDTO dictionaryDTO: dictionariesDTO) {
            for (DictionaryDTO dictionaryDB: dictionariesDB) {
                if (dictionaryDB.getKey().equals(dictionaryDTO.getKey())) {
                    String descriptionDB = dictionaryDB.getDescription();
                    String descriptionDTO = dictionaryDTO.getDescription();
                    if (((descriptionDB != descriptionDTO) && !(descriptionDB != null && descriptionDB.equals(descriptionDTO))) || (dictionaryDB.getRowIndex() != dictionaryDTO.getRowIndex()) || dictionaryDB.getDictionaryProperties().getPrecision() != dictionaryDTO.getDictionaryProperties().getPrecision()) {
                        dictionariesToUpdate.add(dictionaryDTO);
                    }
                }
            }
        }
        if (dictionariesToInsert.size() > 0) {
            insertDictionaries(dictionariesToInsert);
        }
        if (dictionariesToUpdate.size() > 0) {
            updateDictionaries(dictionariesToUpdate);
        }
        if (dictionariesDBToDelete.size() > 0) {
            deleteDictionaries(dictionariesDBToDelete);
        }

        dictionariesDTO.addAll(dictionariesToInsert);
        Collections.sort(dictionariesDTO);
        return dictionariesDTO;
    }

    @Override
    public DictionaryDTO insertDictionary(DictionaryDTO dictionary) throws ServiceException {
        ValidationError error = DictionaryValidator.validateDictionary(dictionary);

        List<FieldError> fieldErrors = error.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            String errorMessage = "Error during inserting query! Fields [";
            for (FieldError fieldError: fieldErrors) {
                errorMessage += fieldError.getFieldName().toUpperCase();
                errorMessage += " - " + fieldError.getFieldMessage();
            }
            errorMessage += "] are not valid in " + dictionary.getValue() + ".";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        try {
            dictionaryDao.insertDictionary(dictionary);
            logger.info("Dictionary [value=" + dictionary.getValue() + "] has been created.");
            return dictionary;
        } catch (DaoException e) {
            String errorMessage = "Error during inserting query! Dictionary [value=" + dictionary.getValue() + "].";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public List<DictionaryDTO> insertDictionaries(List<DictionaryDTO> dictionaries) throws ServiceException {
        String errorMessage = DictionaryValidator.validateDictionaries(dictionaries);

        if (!errorMessage.isEmpty()) {
            errorMessage = "Error during inserting query! " + errorMessage;
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        try {
            dictionaryDao.insertDictionaries(dictionaries);
            logger.info("Dictionaries have been created.");
            return dictionaries;
        } catch (DaoException e) {
            errorMessage = "Error during inserting query! Dictionaries are not created.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public ResponseMessage updateDictionary(DictionaryDTO dictionary) throws ServiceException {
        ValidationError error = DictionaryValidator.validateDictionary(dictionary);
        if (!error.getFieldErrors().isEmpty()) {
            return error;
        } else {
            try {
                boolean result = dictionaryDao.updateDictionary(dictionary);
                logger.info("Dictionary [value=" + dictionary.getValue() + "] has been updated.");
                return new ResponseMessage(result);
            } catch (DaoException e) {
                String errorMessage = "Error during updating dictionary [key=" + dictionary.getKey() + "].";
                logger.error(errorMessage);
                throw new ServiceException(errorMessage, e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public ResponseMessage updateDictionaries(List<DictionaryDTO> dictionaries) throws ServiceException {
        String errorMessage = DictionaryValidator.validateDictionaries(dictionaries);

        if (!errorMessage.isEmpty()) {
            errorMessage = "Error during updating dictionaries query! " + errorMessage;
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        try {
            boolean result = dictionaryDao.updateDictionaries(dictionaries);
            logger.info("Dictionaries have been updated.");
            return new ResponseMessage(result);
        } catch (DaoException e) {
            errorMessage = "Error during updating dictionaries.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public ResponseMessage deleteDictionary(String key) throws ServiceException {
        try {
            boolean result = dictionaryDao.deleteDictionary(key);
            logger.info("Dictionary [key=" + key + "] has been deleted.");
            return new ResponseMessage(result);
        } catch (DaoException e) {
            String errorMessage = "Error during deleting dictionary [key=" + key + "].";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public ResponseMessage deleteDictionaries(List<DictionaryDTO> dictionaries) throws ServiceException {
        try {
            boolean result = dictionaryDao.deleteDictionaries(dictionaries);
            logger.info("Dictionaries have been deleted.");
            return new ResponseMessage(result);
        } catch (DaoException e) {
            String errorMessage = "Error during deleting dictionaries.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public List<DictionaryDTO> importDictionaries(String type) throws ServiceException {
        if (type == null || type.isEmpty()) {
            String errorMessage = "Error during importing dictionaries. Type is null or empty.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }
        if (type.equals("company")) {
            try {
                List<DictionaryDTO> oaCompanies = dictionaryOADao.browseCompanies();
                DictionaryUtil.manageDictionaries(oaCompanies, "company");
                DictionaryUtil.manageOrder(oaCompanies, true);
                return saveDictionaries(oaCompanies, type);
            } catch (DaoException e) {
                String errorMessage = "Error during importing dictionaries with type '" + type + "'.";
                logger.error(errorMessage);
                throw new ServiceException(errorMessage);
            }
        }
        String errorMessage = "Error during importing dictionaries with type '" + type + "'.";
        logger.error(errorMessage);
        throw new ServiceException(errorMessage);
    }

}
