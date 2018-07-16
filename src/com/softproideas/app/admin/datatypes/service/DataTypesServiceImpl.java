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
package com.softproideas.app.admin.datatypes.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionCSO;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionSSO;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.ejb.api.datatype.DataTypeEditorSessionServer;
import com.cedar.cp.ejb.impl.datatype.DataTypeEditorSessionSEJB;
import com.softproideas.app.admin.datatypes.mapper.DataTypesMapper;
import com.softproideas.app.admin.datatypes.model.DataTypeDTO;
import com.softproideas.app.admin.datatypes.model.DataTypeDetailsDTO;
import com.softproideas.app.admin.datatypes.util.DataTypesValidatorUtil;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("dataTypesService")
public class DataTypesServiceImpl implements DataTypesService {

    private static Logger logger = LoggerFactory.getLogger(DataTypesServiceImpl.class);

    private AllDataTypesELO allDataTypesList;

    @Autowired
    CPContextHolder cpContextHolder;
    DataTypeEditorSessionSEJB server = new DataTypeEditorSessionSEJB();
    
    @Override
    public List<DataTypeDTO> browseDataTypes() throws ServiceException {
        AllDataTypesELO allDataTypes = cpContextHolder.getListSessionServer().getAllDataTypes();
        this.allDataTypesList = allDataTypes;
        List<DataTypeDTO> dataTypesDTOList = DataTypesMapper.mapAllDataTypesELO(allDataTypes);
        return dataTypesDTOList;
    }

    @Override
    public DataTypeDetailsDTO fetchDataTypeDetails(int dataTypeId) throws ServiceException {
//    	DataTypeEditorSessionSEJB server = new ;
        DataTypeImpl dataType = getDataTypeFromServer(dataTypeId, server);
        DataTypeDetailsDTO dataTypeDetailsDTO = DataTypesMapper.mapDataType(dataType);
        return dataTypeDetailsDTO;
    };

    /**
     * Get data type details (for update) or new data type details object (for insert)
     */
    private DataTypeImpl getDataTypeFromServer(int dataTypeId, DataTypeEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during browsing Data Type with id =" + dataTypeId + "!";
        try {
            DataTypePK dataTypePK = new DataTypePK((short) dataTypeId);
            DataTypeEditorSessionSSO dataTypeEditorSessionSSO;
            if (dataTypeId != -1) {
                // edit DataType
                dataTypeEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),dataTypePK);
            } else {
                // create DataType
                dataTypeEditorSessionSSO = server.getNewItemData(cpContextHolder.getUserId());
            }
            DataTypeImpl dataType = dataTypeEditorSessionSSO.getEditorData();
            return dataType;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            throw new ServiceException("Validation " + errorMsg);
        }
    }

    /**
     * Validate and map Data Type details (for update or insert)
     */
    @Override
    public ResponseMessage save(DataTypeDetailsDTO dataType) throws ServiceException {
        ResponseMessage message = null;
//        DataTypeEditorSessionServer server = cpContextHolder.getDataTypeEditorSessionServer();
        DataTypeImpl dataTypeImpl = getDataTypeFromServer(dataType.getDataTypeId(), server);
        ValidationError error = DataTypesValidatorUtil.validateDataTypeDetails(dataTypeImpl, dataType, this.allDataTypesList);
        String method;
        if (dataType.getDataTypeId() != -1) {
            // edit DataType
            method = "edit";
        } else {
            // create DataType
            method = "create";
        }
        if (error.getFieldErrors().isEmpty()) {
            dataTypeImpl = DataTypesMapper.mapDataTypeDetailsDTOToDataTypeDetailsImpl(dataTypeImpl, dataType, cpContextHolder.getUserId());
            message = save(dataTypeImpl, method, server);
        } else {
            error.setMessage("Error during " + method + " Data Type.");
            message = error;
        }
        return message;
    }

    /**
     * Save Data Type details: update or insert
     */
    private ResponseMessage save(DataTypeImpl dataTypeImpl, String operation, DataTypeEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during " + operation + " Data Type.";
        try {
            if (operation.equals("edit")) {
                server.update(new DataTypeEditorSessionCSO(cpContextHolder.getUserId(), dataTypeImpl));
            } else if (operation.equals("create")) {
                server.insert(new DataTypeEditorSessionCSO(cpContextHolder.getUserId(), dataTypeImpl));
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
    }

    /**
     * Delete Data Type
     */
    @Override
    public ResponseMessage delete(int dataTypeId) throws ServiceException {
        String errorMsg = "Error during delete Data Type.";
        try {
//            DataTypeEditorSessionServer server = cpContextHolder.getDataTypeEditorSessionServer();
            DataTypePK dataTypePK = new DataTypePK((short) dataTypeId);
            server.delete(cpContextHolder.getUserId(),dataTypePK);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
    }

}
