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
package com.softproideas.app.flatformtemplate.generate.util;

import java.util.ArrayList;
import java.util.List;

import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.core.flatform.util.FlatFormsCoreUtil;
import com.softproideas.app.flatformtemplate.generate.model.GenerateDTO;

public class GenerateUtil {
    
    public static List<String> checkIfDuplicatesExists(GenerateDTO generateDTO, List<FlatFormExtendedCoreDTO> flatForms) {
        List<String> listOfDuplicates = new ArrayList<String>();
        List<FinanceCubeModelCoreDTO> financeCubeModels = generateDTO.getFinanceCubeModels();
        String visId = null;
        for (FinanceCubeModelCoreDTO financeCubeModel: financeCubeModels) {
            visId = getCompanyFromModelVisId(financeCubeModel.getModel().getModelVisId()) + " - " + generateDTO.getName();
            if (FlatFormsCoreUtil.checkIfFlatFormIsInCollection(flatForms, visId) == true) {
                listOfDuplicates.add(visId);
            }
        }
        return listOfDuplicates;
    }
    
    public static String getCompanyFromModelVisId(String modelVisId) {
        if (modelVisId.contains("/")) {
            modelVisId = modelVisId.split("/")[0];
        }
        if (modelVisId.equals("global")) {
            modelVisId = "G";
        }
        return modelVisId;
    }
}
