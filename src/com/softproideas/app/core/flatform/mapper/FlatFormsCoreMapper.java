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
package com.softproideas.app.core.flatform.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.xmlform.AllXcellXmlFormsELO;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.softproideas.app.core.financecube.model.FinanceCubeCoreDTO;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;

/**
 * <p> 
 * Maps native CP data structures to a JSON friendly data transfer objects.
 * </p>
 *
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class FlatFormsCoreMapper {

	/**
	 * Converts {@link AllXcellXmlFormsELO} to JSON based DTO {@link FlatFormExtendedCoreDTO}
	 * 
	 * @param elo {@link AllXcellXmlFormsELO}
	 * @return list of {@link FlatFormExtendedCoreDTO}
	 */
	public static List<FlatFormExtendedCoreDTO> mapAllXcellXmlFormsELO(AllXcellXmlFormsELO elo) {
		List<FlatFormExtendedCoreDTO> flatFormDTOList = new ArrayList<FlatFormExtendedCoreDTO>();		
		
		for(@SuppressWarnings("unchecked") Iterator<AllXcellXmlFormsELO> it = elo.iterator(); it.hasNext();){
			AllXcellXmlFormsELO row = it.next();
			FlatFormExtendedCoreDTO flatFormDTO = new FlatFormExtendedCoreDTO();
			
			XmlFormRef ref =  row.getXmlFormEntityRef();
			// Id
			flatFormDTO.setFlatFormId(((XmlFormPK)ref.getPrimaryKey()).getXmlFormId());
			// DisplayText
			flatFormDTO.setFlatFormVisId(ref.getNarrative());
			// Description
			flatFormDTO.setFlatFormDescription(row.getDescription());
			// Type
			flatFormDTO.setType(row.getType());
			
			FinanceCubeCoreDTO financeCube = new FinanceCubeCoreDTO();
			financeCube.setFinanceCubeId(row.getFinanceCubeId());
			financeCube.setFinanceCubeVisId(row.getVisId());
			flatFormDTO.setFinanceCube(financeCube);
			
			// UpdateTime
			flatFormDTO.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd").format(row.getUpdatedTime()));
			
			flatFormDTOList.add(flatFormDTO);
			System.out.println();
		}

		return flatFormDTOList;
	}
}
