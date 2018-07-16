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
package com.softproideas.app.admin.dataeditor.controller;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.softproideas.app.admin.dataeditor.model.DataEditorImportedData;
import com.softproideas.app.admin.dataeditor.model.DataEditorRow;
import com.softproideas.app.admin.dataeditor.model.DataEditorSearchOption;
import com.softproideas.app.admin.dataeditor.model.DimensionDataForModelDTO;
import com.softproideas.app.admin.dataeditor.service.DataEditorService;
import com.softproideas.app.reviewbudget.export.controller.ExportController;
import com.softproideas.commons.model.ResponseMessage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * <p>Spring MVC controller responsible for handling requests from web browser based user interface.
 * Controller is available in our admin panel at the url <em>/adminPanel/#/data-editor/</em>.</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
@RequestMapping(value = "/dataEditor")
@Controller
public class DataEditorController {

    @Autowired
    DataEditorService dataEditorService;

    /**
     * Retrieves all dimension elements for models. Function returns hash map where keys are modelIds and value are stored in {@link DimensionDataForModelDTO}
     * @return map which contain keys - modelIds, value of {@link DimensionDataForModelDTO} 
     */
    @ResponseBody
    @RequestMapping(value = "/dimensionElements", method = RequestMethod.GET)
    public HashMap<Integer, DimensionDataForModelDTO> browseDimensionElements(@RequestParam(value = "modelIds", required = true) int[] modelIds) throws Exception {
        List<Integer> models = new ArrayList<Integer>();
        for (int i: modelIds) {
            models.add(i);
        }
        return dataEditorService.browseDimensionElements(models);
    }

    /**
     * Retrieves all data rows which are matched to search options. 
     * @param dataEditorSearchOption - filter object {@link DataEditorSearchOption}
     */
    @ResponseBody
    @RequestMapping(value = "/display", method = RequestMethod.POST)
    public List<DataEditorRow> displayDataForSearchOption(@RequestBody DataEditorSearchOption dataEditorSearchOption) throws Exception {
        return dataEditorService.displayDataForSearchOption(dataEditorSearchOption);
    }
    @ResponseBody
    @RequestMapping(value = "/exportXls", method = RequestMethod.POST)
    public void exportXls(@RequestBody DataEditorSearchOption dataEditorSearchOption,HttpServletResponse response ) throws Exception {
    	
    	List<DataEditorRow> results = dataEditorService.displayDataForSearchOption(dataEditorSearchOption);
    	int size = results.size();
    	if(size>0){
    		DataEditorRow first = results.get(0);
        	String xcellFileName = first.getDataType()+"_"+first.getFinanceCubeVisId().replaceAll("/", "");
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("CP Import");
	        sheet.setDisplayGridlines(false);
	        int colNum = 0;
	        //set column width
	        sheet.setColumnWidth(colNum++, 5000);
	        sheet.setColumnWidth(colNum++, 4000);
	        sheet.setColumnWidth(colNum++, 4000);
	        sheet.setColumnWidth(colNum++, 800);
	        sheet.setColumnWidth(colNum++, 800);
	        sheet.setColumnWidth(colNum++, 800);
	        sheet.setColumnWidth(colNum++, 800);
	        sheet.setColumnWidth(colNum++, 4000);
	        sheet.setColumnWidth(colNum++, 4000);
	        sheet.setColumnWidth(colNum++, 800);
	        sheet.setColumnWidth(colNum++, 800);
	        sheet.setColumnWidth(colNum++, 800);
	        sheet.setColumnWidth(colNum++, 4000);        
	        //header style
	        XSSFCellStyle cellHeadStyleLeft = workbook.createCellStyle();
	        XSSFFont cellHeadFont = workbook.createFont();
	        cellHeadFont.setBold(true);	        
	        cellHeadStyleLeft.setFont(cellHeadFont);
	        cellHeadStyleLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);	       
	        XSSFCellStyle cellHeadStyleRight = workbook.createCellStyle();
	        cellHeadStyleRight.setFont(cellHeadFont);
	        cellHeadStyleRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
	        
	        //body style
	        byte[] rgb = new byte[3];
	        rgb[0] = (byte) 219; // red
	        rgb[1] = (byte) 238; // green
	        rgb[2] = (byte) 244; // blue
	        XSSFColor bgColor = new XSSFColor(rgb); 
	        XSSFFont cellBodyFont = workbook.createFont();
	        cellBodyFont.setFontHeightInPoints((short)10);

	        XSSFCellStyle BodyrightAlign = workbook.createCellStyle();
	        BodyrightAlign.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
	        BodyrightAlign.setFillForegroundColor(bgColor);
	        BodyrightAlign.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        BodyrightAlign.setBorderTop(CellStyle.BORDER_THIN);
	        BodyrightAlign.setBorderBottom(CellStyle.BORDER_THIN);
	        BodyrightAlign.setBorderRight(CellStyle.BORDER_THIN);
	        BodyrightAlign.setBorderLeft(CellStyle.BORDER_THIN);
	        BodyrightAlign.setFont(cellBodyFont);
	        XSSFCellStyle BodyleftAlign = workbook.createCellStyle();
	        BodyleftAlign.setAlignment(XSSFCellStyle.ALIGN_LEFT);
	        BodyleftAlign.setFillForegroundColor(bgColor);;
	        BodyleftAlign.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        BodyleftAlign.setBorderTop(CellStyle.BORDER_THIN);
	        BodyleftAlign.setBorderBottom(CellStyle.BORDER_THIN);
	        BodyleftAlign.setBorderRight(CellStyle.BORDER_THIN);
	        BodyleftAlign.setBorderLeft(CellStyle.BORDER_THIN);
	        BodyleftAlign.setFont(cellBodyFont);	        
	        
	        //draw table
	        int rowNum = 0;
	        colNum = 0;
	        //first row is empty so I create empty row :)	        
	        Row row = sheet.createRow(rowNum++);
	        row = sheet.createRow(rowNum++);
	        Cell cell = row.createCell(colNum++);
	        cell.setCellValue("Finance Cube");
	        cell.setCellStyle(cellHeadStyleLeft);
	        cell = row.createCell(colNum++);
	        cell.setCellValue("Cost Code");
	        cell.setCellStyle(cellHeadStyleLeft);
	        cell = row.createCell(colNum++);
	        cell.setCellStyle(cellHeadStyleLeft);
	        cell.setCellValue("Expense Code");
	        cell.setCellStyle(cellHeadStyleLeft);	        
	        //empty cells
	        cell = row.createCell(colNum++);
	        cell = row.createCell(colNum++);
	        cell = row.createCell(colNum++);
	        cell = row.createCell(colNum++);
	        cell.setCellStyle(cellHeadStyleRight);
	        cell = row.createCell(colNum++);
	        cell.setCellValue("Year");
	        cell.setCellStyle(cellHeadStyleRight);
	        cell = row.createCell(colNum++);
	        cell.setCellValue("Period");
	        cell.setCellStyle(cellHeadStyleRight);
	        cell = row.createCell(colNum++);
	        cell.setCellStyle(cellHeadStyleRight);
	        cell = row.createCell(colNum++);
	        cell.setCellStyle(cellHeadStyleRight);
	        cell = row.createCell(colNum++);
	        cell.setCellStyle(cellHeadStyleRight);
	        cell = row.createCell(colNum++);
	        String datatype = dataEditorSearchOption.getDataTypes().get(0);
	        cell.setCellValue(datatype);
	        cell.setCellStyle(cellHeadStyleRight);
	        
	        for (DataEditorRow result : results) {
	            row = sheet.createRow(rowNum++);
	            colNum = 0;
	            cell = row.createCell(colNum++);
	            cell.setCellValue(result.getFinanceCubeVisId());
	            cell.setCellStyle(BodyleftAlign);
	            cell = row.createCell(colNum++);
	            cell.setCellValue(result.getCostCenter());
	            cell.setCellStyle(BodyleftAlign);
	            cell = row.createCell(colNum++);
	            cell.setCellValue(result.getExpenseCode());
	            cell.setCellStyle(BodyleftAlign);
	            //empty cells
	            cell = row.createCell(colNum++);
	            cell.setCellStyle(BodyleftAlign);
	            cell = row.createCell(colNum++);
	            cell.setCellStyle(BodyleftAlign);
	            cell = row.createCell(colNum++);
	            cell.setCellStyle(BodyleftAlign);
	            cell = row.createCell(colNum++);
	            cell.setCellStyle(BodyleftAlign);
	            
	            cell = row.createCell(colNum++);
	            cell.setCellValue((Integer)result.getYear());
	            cell.setCellStyle(BodyrightAlign);
	            cell = row.createCell(colNum++);
	            cell.setCellValue((Integer)result.getPeriod());
	            cell.setCellStyle(BodyrightAlign);
	            cell = row.createCell(colNum++);
	            cell.setCellStyle(BodyrightAlign);
	            cell = row.createCell(colNum++);
	            cell.setCellStyle(BodyrightAlign);
	            cell = row.createCell(colNum++);
	            cell.setCellStyle(BodyrightAlign);
	            cell = row.createCell(colNum++);
	            if(result.getValue()==null)
	            	cell.setCellValue(0);
	            else 
	            	cell.setCellValue((int)Double.parseDouble(result.getValue().toString()));
	            cell.setCellStyle(BodyrightAlign);
	        }

	        FileOutputStream outputStream = new FileOutputStream(ExportController.uploadFolder+xcellFileName+".xlsx");
	        workbook.write(outputStream);
			String url  = "/uploadFiles/"+xcellFileName+".xlsx";
			JsonObject result = new JsonObject();
			result.addProperty("url", url);
			result.addProperty("fileName", xcellFileName+".xlsx");
			response.getWriter().append(result.toString()).flush();
    	}else{
			JsonObject result = new JsonObject();
			result.addProperty("url","No result");
			result.addProperty("fileName", "No result");
			response.getWriter().append(result.toString()).flush();
    	}
        

    }

    /**
     * Saves edited rows in data editor
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(@RequestBody List<DataEditorRow> editedRows) throws Exception {
        return dataEditorService.save(editedRows);
    }

    /**
     * Upload/import data from excel file.
     */
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public DataEditorImportedData upload(@ModelAttribute("upload") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("No file was sended.");
        }

        Set<String> acceptedExtensions = new HashSet<String>(Arrays.asList("XLS", "XLSX"));
        String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase();
        if (!acceptedExtensions.contains(extension)) {
            throw new Exception("File wasn't an excel file.");
        }

        InputStream is = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(is);
        is.close();
        return dataEditorService.upload(workbook);
    }

}
