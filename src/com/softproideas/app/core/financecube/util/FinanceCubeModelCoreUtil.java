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
package com.softproideas.app.core.financecube.util;

import java.util.List;

import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreDTO;

public class FinanceCubeModelCoreUtil {

    public static FinanceCubeModelCoreDTO findFinanceCubeById(int financeCubeId, List<FinanceCubeModelCoreDTO> financeCubeModelCoreList) {
        for (FinanceCubeModelCoreDTO financeCubeModelCoreDTO: financeCubeModelCoreList) {
            if (financeCubeModelCoreDTO.getFinanceCubeId() == financeCubeId) {
                return financeCubeModelCoreDTO;
            }
        }
        return null;
    }
    
    public static FinanceCubeModelCoreDTO findFinanceCubeByVisId(String financeCubeVisId, List<FinanceCubeModelCoreDTO> financeCubeModelCoreList) {
        for (FinanceCubeModelCoreDTO financeCubeModelCoreDTO: financeCubeModelCoreList) {
            if (financeCubeModelCoreDTO.getFinanceCubeVisId().equals(financeCubeVisId)) {
                return financeCubeModelCoreDTO;
            }
        }
        return null;
    }
    
    public static FinanceCubeModelCoreDTO findFinanceCubeByModelId(int modelId, List<FinanceCubeModelCoreDTO> financeCubeModelCoreList) {
        for (FinanceCubeModelCoreDTO financeCubeModelCoreDTO: financeCubeModelCoreList) {
            if (financeCubeModelCoreDTO.getModel().getModelId() == modelId) {
                return financeCubeModelCoreDTO;
            }
        }
        return null;
    }
    
    public static FinanceCubeModelCoreDTO findFinanceCubeByModelVisId(String modelVisId, List<FinanceCubeModelCoreDTO> financeCubeModelCoreList) {
        for (FinanceCubeModelCoreDTO financeCubeModelCoreDTO: financeCubeModelCoreList) {
            if (financeCubeModelCoreDTO.getModel().getModelVisId().equals(modelVisId)) {
                return financeCubeModelCoreDTO;
            }
        }
        return null;
    }

    public static ModelCoreDTO findModelById(int modelId, List<FinanceCubeModelCoreDTO> financeCubeModelCoreList) {
        for (FinanceCubeModelCoreDTO financeCubeModelCoreDTO: financeCubeModelCoreList) {
            if (financeCubeModelCoreDTO.getModel().getModelId() == modelId) {
                return financeCubeModelCoreDTO.getModel();
            }
        }
        return null;
    }

    public static ModelCoreDTO findModelByVisId(String modelVisId, List<FinanceCubeModelCoreDTO> financeCubeModelCoreList) {
        for (FinanceCubeModelCoreDTO financeCubeModelCoreDTO: financeCubeModelCoreList) {
            if (financeCubeModelCoreDTO.getModel().getModelVisId().equals(modelVisId)) {
                return financeCubeModelCoreDTO.getModel();
            }
        }
        return null;
    }
}
