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
package com.softproideas.app.reviewbudget.export.controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.google.gson.JsonObject;
import com.softproideas.app.reviewbudget.budget.model.ReviewBudgetDTO;
import com.softproideas.app.reviewbudget.budget.service.BudgetService;
import com.softproideas.app.reviewbudget.export.service.ExportService;
import com.softproideas.app.reviewbudget.xcellform.model.XCellFormDTO;
import com.softproideas.commons.context.CPContextHolder;
import java.util.Iterator;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.aspose.cells.*;
import com.aspose.pdf.SaveFormat;

import AsposeCellsExamples.Utils;
import cppro.utils.MyUtils;
import javax.annotation.PostConstruct;
/**
 * TODO: Brakuje opisu klasy
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Controller
public class ExportController {

	@PostConstruct
	public void initialize() throws Exception {
		String licenseFile = "Aspose.Total.Java.138605.lic";
	     String currentDir = System.getProperty("user.dir");
	     System.out.println("Current dir using System:" +currentDir);

	   //do your stuff
		com.aspose.pdf.License licensePdf = new com.aspose.pdf.License();
		// Call setLicense method to set license
		licensePdf.setLicense(licenseFile);
		if (com.aspose.pdf.Document.isLicensed()) {
			System.out.println("License is Set!");
		}
		com.aspose.cells.License licenseXls = new com.aspose.cells.License();
		licenseXls.setLicense(licenseFile);
		com.aspose.slides.License licensePpt = new com.aspose.slides.License();
		licensePpt.setLicense(licenseFile);

		License license = new License();
		license.setLicense(licenseFile);
		if (License.isLicenseSet()) {
			System.out.println("License is Set!");
		}
	}
    @Autowired
    BudgetService budgetService;

    @Autowired
    CPContextHolder cpContextHolder;

    @Autowired
    ExportService exportService;
    public static String uploadFolder = "/usr/work/wildfly/wildfly-10.0.0.Final/uploadFiles/";
    /**
     * GET /exportToPdf
     * 
     * Method return data which is necessary to spreadSheet application. Data contains excel string (like json), workbook object with properties,
     * context variables, selected dimensions (which are used in creating breadcrumb)
     */
//    @ResponseBody
//    @RequestMapping(value = "/exportToPdf")
//    public void exportToPdf( //
//    		@RequestParam("upload") MultipartFile file,
//            @RequestParam(value = "profileName", required = true) String profileName, //
//            HttpServletResponse response //
//    ) throws Exception {
//        HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
//        String str1 =profileName+ ".xlsx";
//        byte[] processedText = file.getBytes();
//        FileOutputStream fos = new FileOutputStream(uploadFolder+str1);
//        fos.write(processedText);
//        fos.close();
//    	
//        com.aspose.cells.Workbook workbookPdf = new com.aspose.cells.Workbook(uploadFolder+str1);
//        workbookPdf.calculateFormula();
//		PdfSaveOptions opts = new PdfSaveOptions();
//		opts.setOnePagePerSheet(true);
//
//		// Save the document in PDF format
//		workbookPdf.save(uploadFolder+profileName+".pdf", opts);
//		String url  = "/uploadFiles/"+profileName+".pdf";
//		JsonObject result = new JsonObject();
//		result.addProperty("url", url);
//		result.addProperty("fileName", profileName+".pdf");
//		response.getWriter().append(result.toString()).flush();
//    }
//    @ResponseBody
//    @RequestMapping(value = "/exportToDoc")
//    public void exportToDoc( //
//    		@RequestParam("upload") MultipartFile file,
//            @RequestParam(value = "profileName", required = true) String profileName, //
//            HttpServletResponse response //
//    ) throws Exception {
//
//        HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
//        String str1 =profileName+ ".xlsx";
//        byte[] processedText = file.getBytes();
//        FileOutputStream fos = new FileOutputStream(uploadFolder+str1);
//        fos.write(processedText);
//        fos.close();
//    	
//        com.aspose.cells.Workbook workbookPdf = new com.aspose.cells.Workbook(uploadFolder+str1);
//        workbookPdf.calculateFormula();
//		PdfSaveOptions opts = new PdfSaveOptions();
//		opts.setOnePagePerSheet(true);
//
//		// Save the document in PDF format
//		workbookPdf.save(uploadFolder+profileName+".pdf", opts);
//		
//		com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document(uploadFolder+profileName+".pdf");
//		com.aspose.pdf.DocSaveOptions saveOptions = new com.aspose.pdf.DocSaveOptions();
//		// Set output file format as DOCX
//		saveOptions.setFormat(com.aspose.pdf.DocSaveOptions.DocFormat.DocX);
//		saveOptions.setMode(com.aspose.pdf.DocSaveOptions.RecognitionMode.Flow);
//
//		pdfDocument.save(uploadFolder+profileName+".docx",saveOptions);
//
//		String url  = "/uploadFiles/"+profileName+".docx";
//		JsonObject result = new JsonObject();
//		result.addProperty("url", url);
//		result.addProperty("fileName", profileName+".docx");
//		response.getWriter().append(result.toString()).flush();
//        
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/exportToXls")
//    public void exportToXls( //
//            @RequestParam(value = "topNodeId", required = true) int topNodeId, //
//            @RequestParam(value = "modelId", required = true) int modelId, //
//            @RequestParam(value = "budgetCycleId", required = true) int budgetCycleId, //
//            @RequestParam(value = "dataEntryProfileId", required = true) int dataEntryProfileId, //
//            @RequestParam(value = "dim0", required = false) int dim0, //
//            @RequestParam(value = "dim1", required = false) int dim1, //
//            @RequestParam(value = "dim2", required = false) int dim2, //
//            @RequestParam(value = "dataType", required = false) String dataType, //
//            @RequestParam(value = "profileName", required = true) String profileName, //
//            HttpServletResponse response //
//    ) throws Exception {
//
//        HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
//        if ((dim0 != 0) && (dim1 != 0) && (dim2 != 0)) {
//            selectionsMap.put(new Integer(0), new Integer(dim0));
//            selectionsMap.put(new Integer(1), new Integer(dim1));
//            selectionsMap.put(new Integer(2), new Integer(dim2));
//        }
//        XCellFormDTO reviewBudgetDetails = (XCellFormDTO) budgetService.fetchReviewBudgetDetails(topNodeId, modelId, budgetCycleId, dataEntryProfileId, selectionsMap, dataType);
//        Workbook workbook = exportService.exportToXls(reviewBudgetDetails.getExcelFile(), reviewBudgetDetails.getWorkbook());
//        response.setContentType("application/force-download");
//        profileName = profileName.replace(" ", "_");
//        if (workbook instanceof XSSFWorkbook) {
//            response.setHeader("Content-Disposition", "attachment;filename=" + profileName + ".xlsx");
//        } else {
//            response.setHeader("Content-Disposition", "attachment;filename=" + profileName + ".xls");
//        }
//        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
//        workbook.write(response.getOutputStream());
//        // IOUtils.copy(inputStream, );
//        // inputStream.close();
//        response.flushBuffer();
//    }
//    @ResponseBody
//    @RequestMapping(value = "/exportToPpt")
//    public void exportToPpt( //
//    		@RequestParam("upload") MultipartFile file,
//            @RequestParam(value = "profileName", required = true) String profileName, //
//            HttpServletResponse response //
//    ) throws Exception {
//
//       
//        String str1 =profileName+ ".xlsx";
//        byte[] processedText = file.getBytes();
//        FileOutputStream fos = new FileOutputStream(uploadFolder+str1);
//        fos.write(processedText);
//        fos.close();
//
//        com.aspose.cells.Workbook workbookPdf = new com.aspose.cells.Workbook(uploadFolder+str1);
//        workbookPdf.calculateFormula();
//		PdfSaveOptions opts = new PdfSaveOptions();
//		opts.setOnePagePerSheet(true);
//
//		// Save the document in PDF format
//		workbookPdf.save(uploadFolder+profileName+".pdf", opts);
//		
//		com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document(uploadFolder+profileName+".pdf");
//		com.aspose.pdf.PptxSaveOptions saveOptions = new com.aspose.pdf.PptxSaveOptions();
//
//		pdfDocument.save(uploadFolder+profileName+".pptx",saveOptions);
//
//		String url  = "/uploadFiles/"+profileName+".pptx";
//		JsonObject result = new JsonObject();
//		result.addProperty("url", url);
//		result.addProperty("fileName", profileName+".pptx");
//		response.getWriter().append(result.toString()).flush();
//        
//    }
    @ResponseBody
    @RequestMapping(value = "/exportToPpt")
    public void exportToPpt( //
            @RequestParam(value = "topNodeId", required = true) int topNodeId, //
            @RequestParam(value = "modelId", required = true) int modelId, //
            @RequestParam(value = "budgetCycleId", required = true) int budgetCycleId, //
            @RequestParam(value = "dataEntryProfileId", required = true) int dataEntryProfileId, //
            @RequestParam(value = "dim0", required = false) int dim0, //
            @RequestParam(value = "dim1", required = false) int dim1, //
            @RequestParam(value = "dim2", required = false) int dim2, //
            @RequestParam(value = "dataType", required = false) String dataType, //
            @RequestParam(value = "profileName", required = true) String profileName, //
            HttpServletResponse response //
    ) throws Exception {

       
//        String str1 =profileName+ ".xlsx";
//        byte[] processedText = file.getBytes();
//        FileOutputStream fos = new FileOutputStream(uploadFolder+str1);
//        fos.write(processedText);
//        fos.close();
        
        HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
        if ((dim0 != 0) && (dim1 != 0) && (dim2 != 0)) {
            selectionsMap.put(new Integer(0), new Integer(dim0));
            selectionsMap.put(new Integer(1), new Integer(dim1));
            selectionsMap.put(new Integer(2), new Integer(dim2));
        }
        XCellFormDTO reviewBudgetDetails = (XCellFormDTO) budgetService.fetchReviewBudgetDetails(topNodeId, modelId, budgetCycleId, dataEntryProfileId, selectionsMap, dataType);
        Workbook workbook = exportService.exportToXls(reviewBudgetDetails.getExcelFile(), reviewBudgetDetails.getWorkbook());
        profileName = profileName.replace(" ", "_");
        
        String str1 =profileName+ ".xlsx";
        FileOutputStream out = new FileOutputStream(uploadFolder+str1);
        workbook.write(out);
        out.close();

        com.aspose.cells.Workbook workbookPdf = new com.aspose.cells.Workbook(uploadFolder+str1);
        workbookPdf.calculateFormula();
		PdfSaveOptions opts = new PdfSaveOptions();
		opts.setOnePagePerSheet(true);

		// Save the document in PDF format
		workbookPdf.save(uploadFolder+profileName+".pdf", opts);
		
		com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document(uploadFolder+profileName+".pdf");
		com.aspose.pdf.PptxSaveOptions saveOptions = new com.aspose.pdf.PptxSaveOptions();

		pdfDocument.save(uploadFolder+profileName+".pptx",saveOptions);

		String url  = "/uploadFiles/"+profileName+".pptx";
		JsonObject result = new JsonObject();
		result.addProperty("url", url);
		result.addProperty("fileName", profileName+".pptx");
		response.getWriter().append(result.toString()).flush();
        
    }

    @ResponseBody
    @RequestMapping(value = "/exportToPdf")
    public void exportToPdf( //
            @RequestParam(value = "topNodeId", required = true) int topNodeId, //
            @RequestParam(value = "modelId", required = true) int modelId, //
            @RequestParam(value = "budgetCycleId", required = true) int budgetCycleId, //
            @RequestParam(value = "dataEntryProfileId", required = true) int dataEntryProfileId, //
            @RequestParam(value = "dim0", required = false) int dim0, //
            @RequestParam(value = "dim1", required = false) int dim1, //
            @RequestParam(value = "dim2", required = false) int dim2, //
            @RequestParam(value = "dataType", required = false) String dataType, //
            @RequestParam(value = "profileName", required = true) String profileName, //
            HttpServletResponse response //
    ) throws Exception {

        HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
        if ((dim0 != 0) && (dim1 != 0) && (dim2 != 0)) {
            selectionsMap.put(new Integer(0), new Integer(dim0));
            selectionsMap.put(new Integer(1), new Integer(dim1));
            selectionsMap.put(new Integer(2), new Integer(dim2));
        }
        XCellFormDTO reviewBudgetDetails = (XCellFormDTO) budgetService.fetchReviewBudgetDetails(topNodeId, modelId, budgetCycleId, dataEntryProfileId, selectionsMap, dataType);
        Workbook workbook = exportService.exportToXls(reviewBudgetDetails.getExcelFile(), reviewBudgetDetails.getWorkbook());
        profileName = profileName.replace(" ", "_");
        
        String str1 =profileName+ ".xlsx";
        FileOutputStream out = new FileOutputStream(uploadFolder+str1);
        workbook.write(out);
        out.close();
        
    	
        com.aspose.cells.Workbook workbookPdf = new com.aspose.cells.Workbook(uploadFolder+str1);
        workbookPdf.calculateFormula();
		PdfSaveOptions opts = new PdfSaveOptions();
		opts.setOnePagePerSheet(true);

		// Save the document in PDF format
		workbookPdf.save(uploadFolder+profileName+".pdf", opts);
//		String url  = MyUtils.base_url+"uploadFiles/"+profileName+".pdf";
		String url  = "/uploadFiles/"+profileName+".pdf";
		JsonObject result = new JsonObject();
		result.addProperty("url", url);
		result.addProperty("fileName", profileName+".pdf");
		response.getWriter().append(result.toString()).flush();
        
    }
    @ResponseBody
    @RequestMapping(value = "/exportToDoc")
    public void exportToDoc( //
            @RequestParam(value = "topNodeId", required = true) int topNodeId, //
            @RequestParam(value = "modelId", required = true) int modelId, //
            @RequestParam(value = "budgetCycleId", required = true) int budgetCycleId, //
            @RequestParam(value = "dataEntryProfileId", required = true) int dataEntryProfileId, //
            @RequestParam(value = "dim0", required = false) int dim0, //
            @RequestParam(value = "dim1", required = false) int dim1, //
            @RequestParam(value = "dim2", required = false) int dim2, //
            @RequestParam(value = "dataType", required = false) String dataType, //
            @RequestParam(value = "profileName", required = true) String profileName, //
            HttpServletResponse response //
    ) throws Exception {

        HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
        if ((dim0 != 0) && (dim1 != 0) && (dim2 != 0)) {
            selectionsMap.put(new Integer(0), new Integer(dim0));
            selectionsMap.put(new Integer(1), new Integer(dim1));
            selectionsMap.put(new Integer(2), new Integer(dim2));
        }
        XCellFormDTO reviewBudgetDetails = (XCellFormDTO) budgetService.fetchReviewBudgetDetails(topNodeId, modelId, budgetCycleId, dataEntryProfileId, selectionsMap, dataType);
        Workbook workbook = exportService.exportToXls(reviewBudgetDetails.getExcelFile(), reviewBudgetDetails.getWorkbook());
        profileName = profileName.replace(" ", "_");
        
        String str1 =profileName+ ".xlsx";
        FileOutputStream out = new FileOutputStream(uploadFolder+str1);
        workbook.write(out);
        out.close();
        
    	
        com.aspose.cells.Workbook workbookPdf = new com.aspose.cells.Workbook(uploadFolder+str1);
        workbookPdf.calculateFormula();
		PdfSaveOptions opts = new PdfSaveOptions();
		opts.setOnePagePerSheet(true);

		// Save the document in PDF format
		workbookPdf.save(uploadFolder+profileName+".pdf", opts);
		
		com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document(uploadFolder+profileName+".pdf");
		com.aspose.pdf.DocSaveOptions saveOptions = new com.aspose.pdf.DocSaveOptions();
		// Set output file format as DOCX
		saveOptions.setFormat(com.aspose.pdf.DocSaveOptions.DocFormat.DocX);
		saveOptions.setMode(com.aspose.pdf.DocSaveOptions.RecognitionMode.Flow);

		pdfDocument.save(uploadFolder+profileName+".docx",saveOptions);

		
		//String url  = MyUtils.base_url+"uploadFiles/"+profileName+".docx";
		String url  = "/uploadFiles/"+profileName+".docx";
		JsonObject result = new JsonObject();
		result.addProperty("url", url);
		result.addProperty("fileName", profileName+".docx");
		response.getWriter().append(result.toString()).flush();
        
    }

    @ResponseBody
    @RequestMapping(value = "/exportToXls")
    public void exportToXls( //
            @RequestParam(value = "topNodeId", required = true) int topNodeId, //
            @RequestParam(value = "modelId", required = true) int modelId, //
            @RequestParam(value = "budgetCycleId", required = true) int budgetCycleId, //
            @RequestParam(value = "dataEntryProfileId", required = true) int dataEntryProfileId, //
            @RequestParam(value = "dim0", required = false) int dim0, //
            @RequestParam(value = "dim1", required = false) int dim1, //
            @RequestParam(value = "dim2", required = false) int dim2, //
            @RequestParam(value = "dataType", required = false) String dataType, //
            @RequestParam(value = "profileName", required = true) String profileName, //
            HttpServletResponse response //
    ) throws Exception {

        HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
        if ((dim0 != 0) && (dim1 != 0) && (dim2 != 0)) {
            selectionsMap.put(new Integer(0), new Integer(dim0));
            selectionsMap.put(new Integer(1), new Integer(dim1));
            selectionsMap.put(new Integer(2), new Integer(dim2));
        }
        XCellFormDTO reviewBudgetDetails = (XCellFormDTO) budgetService.fetchReviewBudgetDetails(topNodeId, modelId, budgetCycleId, dataEntryProfileId, selectionsMap, dataType);
        Workbook workbook = exportService.exportToXls(reviewBudgetDetails.getExcelFile(), reviewBudgetDetails.getWorkbook());
        response.setContentType("application/force-download");
        profileName = profileName.replace(" ", "_");
        if (workbook instanceof XSSFWorkbook) {
            response.setHeader("Content-Disposition", "attachment;filename=" + profileName + ".xlsx");
        } else {
            response.setHeader("Content-Disposition", "attachment;filename=" + profileName + ".xls");
        }
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        workbook.write(response.getOutputStream());
        // IOUtils.copy(inputStream, );
        // inputStream.close();
        response.flushBuffer();
    }

}
