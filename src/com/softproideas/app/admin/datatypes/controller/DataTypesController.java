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
package com.softproideas.app.admin.datatypes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.datatypes.model.DataTypeDTO;
import com.softproideas.app.admin.datatypes.model.DataTypeDetailsDTO;
import com.softproideas.app.admin.datatypes.model.DataTypesMeasureClassDTO;
import com.softproideas.app.admin.datatypes.model.DataTypesSubTypeDTO;
import com.softproideas.app.admin.datatypes.service.DataTypesService;
import com.softproideas.app.admin.datatypes.util.DataTypesUtil;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/dataTypes")
@Controller
public class DataTypesController {

    @Autowired
    DataTypesService dataTypesService;

    /**
     * Get all data types from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<DataTypeDTO> browseDataTypes() throws Exception {
        return dataTypesService.browseDataTypes();
    }

    /**
     * Insert data type.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage createDataType(@RequestBody DataTypeDetailsDTO dataType) throws Exception {
        ResponseMessage responseMessage = dataTypesService.save(dataType);
        return responseMessage;
    }

    /**
     * Update data type
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateDataType(@RequestBody DataTypeDetailsDTO dataType) throws Exception {
        ResponseMessage responseMessage = dataTypesService.save(dataType);
        return responseMessage;
    }

    /**
     * Get one data type details.
     */
    @ResponseBody
    @RequestMapping(value = "/{dataTypeId}", method = RequestMethod.GET)
    public DataTypeDetailsDTO fetchDataTypeDetails(@PathVariable int dataTypeId) throws Exception {
        return dataTypesService.fetchDataTypeDetails(dataTypeId);
    }

    /**
     * Delete data type
     */
    @ResponseBody
    @RequestMapping(value = "/{dataTypeId}", method = RequestMethod.DELETE)
    public ResponseMessage deleteDataType(@PathVariable int dataTypeId) throws Exception {
        ResponseMessage responseMessage = dataTypesService.delete(dataTypeId);
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/measureClasses", method = RequestMethod.GET)
    public ArrayList<DataTypesMeasureClassDTO> getMeasureClasses() throws Exception {
		return DataTypesUtil.getMeasureClasses();
    }

    @ResponseBody
    @RequestMapping(value = "/measureClasses/number/{measureClassNumber}", method = RequestMethod.GET)
    public String getMeasureClassName(@PathVariable int measureClassNumber) throws Exception {
        return DataTypesUtil.getMeasureClassName(measureClassNumber);
    }

    @ResponseBody
    @RequestMapping(value = "/measureClasses/name/{measureClassName}", method = RequestMethod.GET)
    public int getMeasureClassNumber(@PathVariable String measureClassName) throws Exception {
        return DataTypesUtil.getMeasureClassNumber(measureClassName);
    }

    @ResponseBody
    @RequestMapping(value = "/subTypes", method = RequestMethod.GET)
    public ArrayList<DataTypesSubTypeDTO> getSubTypes() throws Exception {
        return DataTypesUtil.getSubTypes();
    }

    @ResponseBody
    @RequestMapping(value = "/subTypes/number/{subTypeNumber}", method = RequestMethod.GET)
    public String getSubTypeName(@PathVariable int subTypeNumber) throws Exception {
        return DataTypesUtil.getSubTypeName(subTypeNumber);
    }

    @ResponseBody
    @RequestMapping(value = "/subTypes/name/{subTypeName}", method = RequestMethod.GET)
    public int getSubTypeNumber(@PathVariable String subTypeName) throws Exception {
        return DataTypesUtil.getSubTypeNumber(subTypeName);
    }

}
