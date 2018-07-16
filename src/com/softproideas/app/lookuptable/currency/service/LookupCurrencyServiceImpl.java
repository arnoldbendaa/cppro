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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softproideas.app.lookuptable.currency.dao.LookupCurrencyDao;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyChangeDTO;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyDTO;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyImportDataDTO;
import com.softproideas.app.lookuptable.currency.util.LookupCurrencyValidator;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.FieldError;
import com.softproideas.commons.model.error.ValidationError;

@Service("lookupCurrencyService")
public class LookupCurrencyServiceImpl implements LookupCurrencyService {

    @Autowired
    LookupCurrencyDao lookupCurrencyDao;

    private static Logger logger = LoggerFactory.getLogger(LookupCurrencyServiceImpl.class);

    @Override
    public List<LookupCurrencyDTO> browseCurrencies() {
        try {
            return lookupCurrencyDao.browseCurrencies();
        } catch (DaoException e) {
            return new ArrayList<LookupCurrencyDTO>();
        }
    }

    @Override
    public List<LookupCurrencyDTO> browseCurrencies(int company, int year) {
        try {
            return lookupCurrencyDao.browseCurrencies(company, year);
        } catch (DaoException e) {
            return new ArrayList<LookupCurrencyDTO>();
        }
    }
    
    @Override
    public LookupCurrencyDTO fetchCurrency(UUID currencyUUID) {
        try {
            return lookupCurrencyDao.fetchCurrency(currencyUUID);
        } catch (DaoException e) {
            return null;
        }
    }
    
    @Override
    @Transactional(rollbackFor=ServiceException.class, propagation=Propagation.REQUIRED)
    public List<LookupCurrencyDTO> saveCurrencies(List<LookupCurrencyDTO> lookupCurrencies) throws ServiceException {
        if (lookupCurrencies.size() < 0) {
            String errorMessage = "Error during saving query! List of currencies is empty.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }
        
        List<LookupCurrencyDTO> currenciesToDelete = new ArrayList<LookupCurrencyDTO>();
        List<LookupCurrencyDTO> currenciesToInsert = new ArrayList<LookupCurrencyDTO>();
        List<LookupCurrencyDTO> currenciesToUpdate = new ArrayList<LookupCurrencyDTO>();
        
        for (LookupCurrencyDTO lookupCurrency: lookupCurrencies) {
           if (lookupCurrency.getCurrencyUUID() == null) {
               currenciesToInsert.add(lookupCurrency);
           } else {
               if (lookupCurrency.getFieldValue() == null) {
                   currenciesToDelete.add(lookupCurrency);
               } else {
                  currenciesToUpdate.add(lookupCurrency);
               }
           }
        }
        if (currenciesToInsert.size() > 0) {
            insertCurrencies(currenciesToInsert);
        }
        if (currenciesToUpdate.size() > 0) {
            updateCurrencies(currenciesToUpdate);
        }
        if (currenciesToDelete.size() > 0) {
            deleteCurrencies(currenciesToDelete);
        }
        
        lookupCurrencies.removeAll(currenciesToDelete);
        return lookupCurrencies;
    }

    @Override
    public LookupCurrencyDTO insertCurrency(LookupCurrencyDTO lookupCurrencyDTO) throws ServiceException {
        ValidationError error = LookupCurrencyValidator.validateCurrency(lookupCurrencyDTO);
        
        List<FieldError> fieldErrors = error.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            String errorMessage = "Error during inserting query! Fields [";
            for (FieldError fieldError: fieldErrors) {
                errorMessage += fieldError.getFieldName().toUpperCase();
                errorMessage += " - " + fieldError.getFieldMessage();
            }
            errorMessage += "] are not valid in currency [period=" + lookupCurrencyDTO.getPeriod() + ", currency=" + lookupCurrencyDTO.getCurrency() + "].";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }
        
        String errorMessage = "Error during inserting query! Currency [year=" + lookupCurrencyDTO.getYear() + ", period=" + lookupCurrencyDTO.getPeriod() + ", company=" + lookupCurrencyDTO.getCompany() + ", currency=" + lookupCurrencyDTO.getCurrency() + "].";
        try {
            LookupCurrencyDTO lookupCurrencyInDatabase = lookupCurrencyDao.fetchCurrency(lookupCurrencyDTO);
            if (lookupCurrencyInDatabase != null) {
                logger.error(errorMessage);
                throw new ServiceException(errorMessage);
            }
        } catch (DaoException e) {
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
        
        try {
            lookupCurrencyDTO.setCurrencyUUID(UUID.randomUUID());
            lookupCurrencyDao.insertCurrency(lookupCurrencyDTO);
            logger.info("Currency [currency=" + lookupCurrencyDTO.getCurrency() + "] has been created.");
            // return lookupCurrencyDao.fetchCurrency(lookupCurrencyDTO); // it is not necessary
            return lookupCurrencyDTO;
        } catch (DaoException e) {
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }
    
    @Override
    @Transactional(rollbackFor=ServiceException.class, propagation=Propagation.REQUIRED)
    public List<LookupCurrencyDTO> insertCurrencies(List<LookupCurrencyDTO> currencies) throws ServiceException {
        String errorMessage = LookupCurrencyValidator.validateCurrencies(currencies);
        
        if (!errorMessage.isEmpty()) {
            errorMessage = "Error during inserting currencies query! " + errorMessage;
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }        
        
        for (LookupCurrencyDTO lookupCurrency: currencies) {
            lookupCurrency.setCurrencyUUID(UUID.randomUUID());
        }
        
        try {
            lookupCurrencyDao.insertCurrencies(currencies);
            logger.info("Currencies have been created.");
            return currencies;
        } catch (DaoException e) {
            errorMessage = "Error during inserting query! Currencies are not created.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public ResponseMessage updateCurrency(LookupCurrencyDTO lookupCurrencyDTO) throws ServiceException {
        ValidationError error = LookupCurrencyValidator.validateCurrency(lookupCurrencyDTO);
        
        if (!error.getFieldErrors().isEmpty()) {
            return error;
        } else {
            String errorMessage = "Error during updating query! Currency [year=" + lookupCurrencyDTO.getYear() + ", period=" + lookupCurrencyDTO.getPeriod() + ", company=" + lookupCurrencyDTO.getCompany() + ", currency=" + lookupCurrencyDTO.getCurrency() + "] doesn't exist in database.";
            
            try {
                LookupCurrencyDTO lookupCurrencyInDatabase = lookupCurrencyDao.fetchCurrency(lookupCurrencyDTO);
                if (lookupCurrencyInDatabase == null) {
                    logger.error(errorMessage);
                    throw new ServiceException(errorMessage);
                }
            } catch (DaoException e) {
                logger.error(errorMessage);
                throw new ServiceException(errorMessage, e);
            }
            
            try {
                boolean result = lookupCurrencyDao.updateCurrencyValue(lookupCurrencyDTO);
                if (result) {
                    logger.info("Currency [currency=" + lookupCurrencyDTO.getCurrency() + "] has been updated.");
                }
                return new ResponseMessage(result);
            } catch (DaoException e) {
                logger.error(errorMessage);
                throw new ServiceException(errorMessage, e);
            }
        }
        
    }
    
    @Override
    @Transactional(rollbackFor=ServiceException.class, propagation=Propagation.REQUIRED)
    public ResponseMessage updateCurrencies(List<LookupCurrencyDTO> currencies) throws ServiceException {
        String errorMessage = LookupCurrencyValidator.validateCurrencies(currencies);
        
        if (!errorMessage.isEmpty()) {
            errorMessage = "Error during updating currencies query! " + errorMessage;
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }  
        
        try {
            boolean result = lookupCurrencyDao.updateCurrenciesValue(currencies);
            logger.info("Currencies have been updated.");
            return new ResponseMessage(result);
        } catch (DaoException e) {
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public ResponseMessage deleteCurrency(UUID currencyUUID) throws ServiceException {
        try {
            boolean result = lookupCurrencyDao.deleteCurrency(currencyUUID);
            logger.info("Currency [uuid=" + currencyUUID + "] has been deleted.");
            return new ResponseMessage(result);
        } catch (DaoException e) {
            String errorMessage = "Error during deleting currency [uuid=" + currencyUUID + "].";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    @Transactional(rollbackFor=ServiceException.class, propagation=Propagation.REQUIRED)
    public ResponseMessage deleteCurrencies(List<LookupCurrencyDTO> currencies) throws ServiceException {
        try {
            boolean result = lookupCurrencyDao.deleteCurrencies(currencies);
            logger.info("Currencies have been deleted.");
            return new ResponseMessage(result);
        } catch (DaoException e) {
            String errorMessage = "Error during deleting currencies.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public LookupCurrencyImportDataDTO upload(Workbook workbook) throws ServiceException {
        LookupCurrencyImportDataDTO data = new LookupCurrencyImportDataDTO();
        
        Sheet sheet = workbook.getSheetAt(0);
        Cell cellCompany = sheet.getRow(0).getCell(2);
        if (cellCompany.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            data.setCompany((int) cellCompany.getNumericCellValue());
        }
        Cell cellYear = sheet.getRow(0).getCell(4);
        if (cellYear.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            data.setYear((int) cellYear.getNumericCellValue());
        }
        
        List<LookupCurrencyDTO> currenciesDTO = new ArrayList<LookupCurrencyDTO>();
        for (int i = 3; i < sheet.getLastRowNum(); i++) {
            String currentCurrency = null;
           
            Row currentRow = sheet.getRow(i);
            Cell rowLabel = currentRow.getCell(0);
            if (rowLabel.getCellType() == Cell.CELL_TYPE_STRING) {
                currentCurrency = rowLabel.getStringCellValue().toUpperCase();
            }
            
            for (int j = 1; j < 17; j++) {
                LookupCurrencyDTO currencyDTO = new LookupCurrencyDTO();
                currencyDTO.setCompany(data.getCompany());
                currencyDTO.setYear(data.getYear());
                currencyDTO.setPeriod(j-1);
                currencyDTO.setCurrency(currentCurrency);
                
                Cell currentCell = currentRow.getCell(j);
                if (currentCell != null) {
                    if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        currencyDTO.setFieldValue(String.valueOf(currentCell.getNumericCellValue()));
                    } else if (currentCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                        currencyDTO.setFieldValue("=" + currentCell.getCellFormula());
                    }
                }
                currenciesDTO.add(currencyDTO);
            }
        }
        
        // Fill imported currencies with UUID
        List<LookupCurrencyDTO> currenciesDB = browseCurrencies(data.getCompany(), data.getYear());
        for (LookupCurrencyDTO currencyDTO: currenciesDTO) {
            for (LookupCurrencyDTO currencyDB: currenciesDB) {
                if (currencyDB.equals(currencyDTO)) {
                    currencyDTO.setCurrencyUUID(currencyDB.getCurrencyUUID());
                    currenciesDB.remove(currencyDB);
                    break;
                }
            }
        }
        for (LookupCurrencyDTO currencyDB: currenciesDB) {
            LookupCurrencyDTO fromDB = new LookupCurrencyDTO();
            fromDB.setCompany(currencyDB.getCompany());
            fromDB.setYear(currencyDB.getYear());
            fromDB.setPeriod(currencyDB.getPeriod());
            fromDB.setCurrency(currencyDB.getCurrency());
            fromDB.setCurrencyUUID(currencyDB.getCurrencyUUID());
            fromDB.setFieldValue(currencyDB.getFieldValue());
            currenciesDTO.add(fromDB);
        }

        data.setCurrencies(currenciesDTO);
        return data;
    }

    @Override
    public boolean updateChanges(List<LookupCurrencyChangeDTO> changes) {
        try {
            return lookupCurrencyDao.updateChanges(changes);
        } catch (DaoException e) {
            return true;
        }
    }

}
