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
package com.softproideas.app.admin.changemanagement.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.cm.ChangeMgmtRef;
import com.cedar.cp.dto.cm.AllChangeMgmtsELO;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.softproideas.app.admin.changemanagement.model.ChangeMgmtDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;

public class ChangeMgmtMapper {

    @SuppressWarnings("unchecked")
    public static List<ChangeMgmtDTO> mapAllChangeMgmtsELO(AllChangeMgmtsELO list) {
        List<ChangeMgmtDTO> changeMgmtsDTOList = new ArrayList<ChangeMgmtDTO>();
        for (Iterator<AllChangeMgmtsELO> it = list.iterator(); it.hasNext();) {
            AllChangeMgmtsELO row = it.next();
            ChangeMgmtDTO changeMgmtDTO = new ChangeMgmtDTO();

            ChangeMgmtRef ref = row.getChangeMgmtEntityRef();
            changeMgmtDTO.setChangeMgmtId(((ChangeMgmtPK) ref.getPrimaryKey()).getChangeMgmtId());

            changeMgmtDTO.setCreated(row.getCreatedTime());

            ModelRefImpl modelRefImpl = (ModelRefImpl) row.getModelEntityRef();
            ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(modelRefImpl);
            changeMgmtDTO.setModel(model);

            changeMgmtDTO.setSourceSystem(row.getSourceSystem());
            changeMgmtDTO.setTaskId(row.getTaskId());

            changeMgmtsDTOList.add(changeMgmtDTO);
        }

        return changeMgmtsDTOList;
    }

}
