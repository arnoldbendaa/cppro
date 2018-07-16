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
package com.softproideas.app.flatformeditor.form.model;

public class XMLToDTOTest01 {
    private static String xml = 
            "<workbook>"+
                    "<properties>"+
                        "<entry name=\"PROTECTED\" value=\"false\"/>"+
                        "<entry name=\"INVERT_NUMBERS_VALUE\" value=\"true\"/>"+
                        "<entry name=\"MODEL_VISID\" value=\"global/2\"/>"+
                    "</properties>"+
                    "<worksheet name=\"Australia - 500\" showGrid=\"true\" hidden=\"false\" cellFormatVersion=\"0\">"+
                        "<properties>"+
                            "<entry name=\"DIMENSION_0_HIERARCHY_VISID\" value=\"global/2-cc-Group\"/>"+
                            "<entry name=\"MODEL_ID\" value=\"ModelPK|615686\"/>"+
                            "<entry name=\"MODEL_VISID\" value=\"global/2\"/>"+
                            "<entry name=\"DIMENSION_0_VISID\" value=\"global/2-cc\"/>"+
                            "<entry name=\"DIMENSION_1_VISID\" value=\"global/2-exp\"/>"+
                            "<entry name=\"DIMENSION_2_VISID\" value=\"global/2-Cal\"/>"+
                            "<entry name=\"FINANCE_CUBE_ID\" value=\"FinanceCubeCK|ModelPK|615686|FinanceCubePK|615689\"/>"+
                            "<entry name=\"FINANCE_CUBE_VISID\" value=\"global/2-Cube\"/>"+
                            "<entry name=\"DIMENSION_1_HIERARCHY_VISID\" value=\"global/2-exp-PL-GRP\"/>"+
                            "<entry name=\"DIMENSION_2_HIERARCHY_VISID\" value=\"global/2-Cal\"/>"+
                        "</properties>"+
                        "<cell value=\"___test_value___\" row=\"32\" column=\"10\" inputMapping=\"cedar.cp.getCell(dim0=&quot;SF-MOT-CAR&quot;,dim1=&quot;1-1010&quot;,dim2=&quot;2014/1&quot;,dt=&quot;AV&quot;,cmpy=&quot;200&quot;)\"/>"+
                        "<cell value=\"___test_v2___\" row=\"12\" column=\"8\" inputMapping=\"cedar.cp2.getCell(dim0=&quot;SF-MOT-CAR&quot;,dim1=&quot;1-1010&quot;,dim2=&quot;2014/1&quot;,dt=&quot;AV&quot;,cmpy=&quot;200&quot;)\"/>"+
                        "<cell value=\"___t3value___\" row=\"4\" column=\"9\" inputMapping=\"cedar.cp3.getCell(dim0=&quot;SF-MOT-CAR&quot;,dim1=&quot;1-1010&quot;,dim2=&quot;2014/1&quot;,dt=&quot;AV&quot;,cmpy=&quot;200&quot;)\"/>"+
                    "</worksheet>"+
                "</workbook>";

    public static String getXml() {
        return xml;
    }

}
