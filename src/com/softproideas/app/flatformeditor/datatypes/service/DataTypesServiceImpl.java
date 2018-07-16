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
/**
 * 
 */
package com.softproideas.app.flatformeditor.datatypes.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionSSO;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.model.FinanceCubeEditorSessionServer;
import com.softproideas.app.core.datatype.model.DataTypeCoreDTO;
import com.softproideas.app.flatformeditor.datatypes.mapper.DataTypesMapper;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("dataTypesForFlatFormService")
public class DataTypesServiceImpl implements DataTypesService {
    private static Logger logger = LoggerFactory.getLogger(DataTypesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.flatformeditor.datatypes.service.DataTypesService#browseDataTypesForFinanceCube(int) */
    @Override
    public List<DataTypeCoreDTO> browseDataTypesForFinanceCube(int modelId, int financeCubeId) throws ServiceException {
        FinanceCubeImpl financeCubeImpl = getItemData(modelId, financeCubeId);
        List<DataTypeCoreDTO> dataTypesDTO = DataTypesMapper.mapDataTypesFromFinanceCubeImpl(financeCubeImpl);
        return dataTypesDTO;
    }

    /**
     * Get details for selected Finance Cube from database.
     */
    private FinanceCubeImpl getItemData(int modelId, int financeCubeId) throws ServiceException {
        try {
            FinanceCubeEditorSessionServer server = cpContextHolder.getFinanceCubeEditorSessionServer();
            FinanceCubePK financeCubePK = new FinanceCubePK(financeCubeId);
            ModelPK modelPK = new ModelPK(modelId);
            FinanceCubeCK financeCubeCK = new FinanceCubeCK(modelPK, financeCubePK);
            FinanceCubeEditorSessionSSO financeCubeEditorSessionSSO;
            financeCubeEditorSessionSSO = server.getItemData(financeCubeCK);
            FinanceCubeImpl financeCubeImpl = financeCubeEditorSessionSSO.getEditorData();
            return financeCubeImpl;
        } catch (CPException e) {
            logger.error("Error during get Finance Cube!", e);
            throw new ServiceException("Error during get Finance Cube!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get Finance Cube!", e);
            throw new ServiceException("Validation error during get Finance Cube!", e);
        }
    }

}
