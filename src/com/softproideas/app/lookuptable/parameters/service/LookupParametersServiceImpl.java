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
package com.softproideas.app.lookuptable.parameters.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softproideas.app.lookuptable.parameters.dao.LookupParametersDao;
import com.softproideas.app.lookuptable.parameters.model.LookupParameterChangeDTO;
import com.softproideas.app.lookuptable.parameters.model.LookupParameterDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

@Service("lookupParametersService")
public class LookupParametersServiceImpl implements LookupParametersService {

    @Autowired
    LookupParametersDao lookupParametersDao;

    private static Logger logger = LoggerFactory.getLogger(LookupParametersServiceImpl.class);

    @Override
    public List<LookupParameterDTO> browseParameters() {
        try {
            return lookupParametersDao.browseParameters();
        } catch (DaoException e) {
            return new ArrayList<LookupParameterDTO>();
        }
    }

    @Override
    public List<LookupParameterDTO> browseParameters(int company) {
        try {
            return lookupParametersDao.browseParameters(company);
        } catch (DaoException e) {
            return new ArrayList<LookupParameterDTO>();
        }
    }

    @Override
    public List<LookupParameterDTO> saveParameters(List<LookupParameterDTO> parameters) throws ServiceException {
        if (parameters.size() < 0) {
            String errorMessage = "Error during saving query! List of parameters is empty.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        List<LookupParameterDTO> parametersToDelete = new ArrayList<LookupParameterDTO>();
        List<LookupParameterDTO> parametersToInsert = new ArrayList<LookupParameterDTO>();
        List<LookupParameterDTO> parametersToUpdate = new ArrayList<LookupParameterDTO>();

        for (LookupParameterDTO lookupCurrency: parameters) {
            if (lookupCurrency.getParameterUUID() == null) {
                parametersToInsert.add(lookupCurrency);
            } else {
                if (lookupCurrency.getFieldValue() == null) {
                    parametersToDelete.add(lookupCurrency);
                } else {
                    parametersToUpdate.add(lookupCurrency);
                }
            }
        }
        if (parametersToInsert.size() > 0) {
            insertParameters(parametersToInsert);
        }
        if (parametersToUpdate.size() > 0) {
            updateParameters(parametersToUpdate);
        }
        if (parametersToDelete.size() > 0) {
            deleteParameters(parametersToDelete);
        }

        parameters.removeAll(parametersToDelete);
        return parameters;
    }

    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    private List<LookupParameterDTO> insertParameters(List<LookupParameterDTO> parameters) throws ServiceException {
        String errorMessage = "";// TODO validate LookupCurrencyValidator.validateCurrencies(currencies);

        if (!errorMessage.isEmpty()) {
            errorMessage = "Error during inserting parameters query! " + errorMessage;
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        for (LookupParameterDTO lookupCurrency: parameters) {
            lookupCurrency.setParameterUUID(UUID.randomUUID());
        }

        try {
            lookupParametersDao.insertParameters(parameters);
            logger.info("Parameters have been created.");
            return parameters;
        } catch (DaoException e) {
            errorMessage = "Error during inserting query! Parameters are not created.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    private ResponseMessage updateParameters(List<LookupParameterDTO> parameters) throws ServiceException {
        String errorMessage = ""; // TODO validate LookupCurrencyValidator.validateCurrencies(parameters);

        if (!errorMessage.isEmpty()) {
            errorMessage = "Error during updating parameters query! " + errorMessage;
            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        try {
            boolean result = lookupParametersDao.updateParametersValues(parameters);
            logger.info("Parameters have been updated.");
            return new ResponseMessage(result);
        } catch (DaoException e) {
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    private ResponseMessage deleteParameters(List<LookupParameterDTO> parameters) throws ServiceException {
        try {
            boolean result = lookupParametersDao.deleteParameters(parameters);
            logger.info("Parameters have been deleted.");
            return new ResponseMessage(result);
        } catch (DaoException e) {
            String errorMessage = "Error during deleting parameters.";
            logger.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public boolean updateChanges(List<LookupParameterChangeDTO> changes) {
        try {
            return lookupParametersDao.updateChanges(changes);
        } catch (DaoException e) {
            return true;
        }
    }

}
