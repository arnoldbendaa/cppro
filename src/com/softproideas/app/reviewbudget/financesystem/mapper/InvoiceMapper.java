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
package com.softproideas.app.reviewbudget.financesystem.mapper;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.dataEntry.FinanceSystemCellData;
import com.softproideas.app.reviewbudget.financesystem.model.InvoiceDTO;
import com.softproideas.app.reviewbudget.financesystem.model.InvoiceDTO.DimensionInfoDTO;
import com.softproideas.app.reviewbudget.financesystem.model.InvoiceDTO.PropertyDTO;

/**
 * DTO Mapper for finance system data from external systems
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class InvoiceMapper {

    /**
     * Fills InvoiceDTO with valid data
     * 
     * @param invoice
     * @param urlProperties url, user, password for invoice
     * @return
     */
    public static InvoiceDTO mapToInvoiceDTO(FinanceSystemCellData invoice, String[] urlProperties) {
        InvoiceDTO dto = new InvoiceDTO();
        
        try{
            if (invoice != null && !invoice.getRows().isEmpty()) {
                dto.setColumnNames(invoice.getColumnNames());
                List<DimensionInfoDTO> dimInfo = convertToDimInfoDTO(invoice.getDimSelectionSummary().split("\t"), dto);
                dto.setDimensionInfo(dimInfo);
                dto.setSelectionInfo(convertToProperties(invoice.getOtherSelectionSummary().split("\t"), dto));
                //String company = getCompanyFromDimInfo(dimInfo);
                List<List<Object>> rows = addURLs(invoice.getRows(), urlProperties);
                dto.setRows(rows);
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        dto.setValidationMessage(invoice.getValidationMessage());
        return dto;
    }

    /**
     * Converts array to PropertyDTO object
     * @param input array of names and values
     * @param invoiceDTO needed to create property object
     * @return
     */
    private static List<PropertyDTO> convertToProperties(String[] input, InvoiceDTO invoiceDTO) {
        List<PropertyDTO> result = new ArrayList<PropertyDTO>();
        for (int i = 0; i < input.length / 2; i++) {
            PropertyDTO property = invoiceDTO.new PropertyDTO();
            property.setName(input[(i * 2)]);
            property.setValue(input[(i * 2) + 1]);
            result.add(property);
        }
        return result;
    }

    /**
     * Converts array to DimensionInfoDTO object
     * @param input array of names, values and descriptions
     * @param invoiceDTO needed to create property object
     * @return
     */
    private static List<DimensionInfoDTO> convertToDimInfoDTO(String[] input, InvoiceDTO invoiceDTO) {
        List<DimensionInfoDTO> result = new ArrayList<DimensionInfoDTO>();
        for (int i = 0; i < input.length / 3; i++) {
            DimensionInfoDTO dimInfo = invoiceDTO.new DimensionInfoDTO();
            dimInfo.setName(input[(i * 3)]);
            dimInfo.setValue(input[(i * 3) + 1]);
            dimInfo.setDescription(input[(i * 3) + 2]);
            result.add(dimInfo);
        }
        return result;
    }

    /**
     * Adds to each row the url to fetch invoice image
     * 
     * @param rows input data
     * @param urlProperties url, user and password for invoice
     * @param company needed for generating valid url
     * @return
     */
    private static List<List<Object>> addURLs(List<List<Object>> rows, String[] urlProperties) {
        List<List<Object>> result = new ArrayList<List<Object>>();
        for (List<Object> row: rows) {
//            row.add(getURLForInvoice(urlProperties, (String) row.get(8), String.valueOf((Integer) row.get(9))));
        	if(row.size() <10)
        		row.add(getURLForInvoice(urlProperties, row.get(8).toString(), null));
        	else         		
        		row.add(getURLForInvoice(urlProperties, row.get(8).toString(), row.get(9).toString()));

            result.add(row);
        }
        return result;
    }

    /**
     * Generates url to invoice picture
     * 
     * @param urlProperties
     * @param docRef
     * @param company
     * @return url or null
     */
    private static String getURLForInvoice(String[] urlProperties, String docRef, String company) {
        if (company != null && company.length() > 0 && urlProperties != null) {
            String docRefTab[] = null;
            docRefTab = docRef.split("/");
            if (docRefTab.length == 2) {
                int docRefNumber = Integer.parseInt(docRefTab[1]);
                String docRefNumberWithLeadingZeros = String.format("%06d", docRefNumber);
                if (company.length() == 1) {
                    company = String.format("%02d", Integer.parseInt(company));
                }
                return urlProperties[0] + "/scripts/DbWebQ.exe?DbQCMD=LOGIN&DbQUser=" + urlProperties[1] + "&DbQPass=" + urlProperties[2] + "&DbQCMDNEXT=SEARCH&DOCID=PURCHASE_INVOICES&S0F=BARCODE&S0O=EQ&S0V=" + company + "%2F" + docRefTab[0] + "%2F" + docRefNumberWithLeadingZeros;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Gets company info form mapped info from external system
     * @param dimInfo
     * @return company or null if not found
     */
    private static String getCompanyFromDimInfo(List<DimensionInfoDTO> dimInfo) {
        for (DimensionInfoDTO info: dimInfo) {
            if (info.getName().equals("Company")) {
                return (String) info.getValue();
            }
        }
        return null;
    }
}
