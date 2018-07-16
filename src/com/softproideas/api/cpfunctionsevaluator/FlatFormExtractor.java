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
package com.softproideas.api.cpfunctionsevaluator;

import java.util.Map;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.softproideas.api.cpfunctionsevaluator.FlatFormExtractorPrototype;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingFunction;
import com.softproideas.util.validation.MappingValidator;

/**
 * Class responsible for get and assignment of values ​​to cells in
 * <code>Workbook</code>.
 * 
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com 2014-2015 All rights reserved to Softpro Ideas
 *        Group
 */
// @Service("flatFormExtractor")
// @Scope(value="request")
public class FlatFormExtractor extends FlatFormExtractorPrototype {

	private static Map<String, Map<String, String>> propertiesFormModelVisId;
	private CPConnection connection;

	// @Autowired
	public FlatFormExtractor(CPConnection connection) {
		super(connection);
		this.connection = connection;
	}

	protected String getModelVisId(Indexes indexes, MappingValidator mv,
			WorkbookDTO workbook) {
		if (mv.getOrigin().contains(MappingFunction.GET_GLOB.toString())) {
			return mv.getListOfArguments().get(
					MappingArguments.MODEL.toString());
		} else {
			return workbook.getWorksheets().get(indexes.getWorksheetIndex())
					.getProperties()
					.get(WorkbookProperties.MODEL_VISID.toString());
		}
	}

	protected Map<String, String> getProperties(Indexes indexes,
			MappingValidator mv, WorkbookDTO workbook) {
		if (mv.getOrigin().contains(MappingFunction.GET_GLOB.toString())) {
			return getPropertiesForModelVisId(mv.getListOfArguments().get(
					MappingArguments.MODEL.toString()));
		} else {
			return workbook.getWorksheets().get(indexes.getWorksheetIndex())
					.getProperties();
		}
	}

	private Map<String, String> getPropertiesForModelVisId(String modelVisId) {
		return connection.getListHelper()
				.getPropertiesForModelVisId(modelVisId);
	}

}