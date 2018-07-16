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
package com.cedar.cp.util.flatform.model.workbook.editor;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;
import com.cedar.cp.util.flatform.model.workbook.TemplateCellDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.cedar.cp.util.flatform.model.workbook.util.CellFactory;
import com.cedar.cp.util.flatform.model.workbook.util.CellType;
import com.cedar.cp.util.flatform.model.workbook.util.CellUtil;

import cppro.utils.MyLog;

public class WorkbookMapper {

    public static String mapWorkbookDTOToXmlString(WorkbookDTO workbookDTO) {
        StringBuffer workbookString = new StringBuffer();

        workbookString.append("<workbook>");

        // set workbook properties
        workbookString.append("<properties>");
        Map<String, String> workbookProperties = workbookDTO.getProperties();
        for (Entry<String, String> entry: workbookProperties.entrySet()) {
            workbookString.append("<entry name=\'" + entry.getKey() + "\' value=\'" + entry.getValue() + "\' />");
        }
        workbookString.append("</properties>");

        // set worksheets with properties
        List<WorksheetDTO> worksheets = workbookDTO.getWorksheets();
        for (WorksheetDTO worksheet: worksheets) {
            workbookString.append("<worksheet name=\'" + worksheet.getName() + "\'>");

            workbookString.append("<properties>");
            Map<String, String> worksheetProperties = worksheet.getProperties();
            if (worksheetProperties != null) {
                for (Entry<String, String> entry: worksheetProperties.entrySet()) {
                    workbookString.append("<entry name=\'" + entry.getKey() + "\' value=\'" + entry.getValue() + "\' />");
                }
            }
            workbookString.append("</properties>");

            List<? extends CellDTO> cells = worksheet.getCells();
            for (CellDTO cell: cells) {
                workbookString.append("<cell");

                workbookString.append(" row=\'" + cell.getRow() + "\'");
                workbookString.append(" column=\'" + cell.getColumn() + "\'");

                if (cell.getInputMapping() != null) {
                    workbookString.append(" inputMapping=\'" + cell.getInputMapping().replaceAll("'", "&apos;").replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "\'");
                }
                if (cell.getOutputMapping() != null) {
                    workbookString.append(" outputMapping=\'" + cell.getOutputMapping().replaceAll("'", "&apos;").replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "\'");
                }
                if (cell.getText() != null) {
                    workbookString.append(" text=\'" + cell.getText().replaceAll("'", "&apos;").replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "\'");
                }
                if (cell.getTags() != null && cell.getTags().size() != 0) {
                    workbookString.append(" tags=\'" + cell.getTags().toString().replaceAll("\\[|\\]", "").replaceAll("'", "&apos;").replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "\'");
                }
                workbookString.append(" />");
            }

            workbookString.append("</worksheet>");
        }
        workbookString.append("</workbook>");
        return workbookString.toString();
    }

    public static WorkbookDTO mapDefinitionXML(String stringXml, CellType type) throws Exception {
//    	stringXml = stringXml.replaceAll("&apos;","\"");
//    	stringXml = stringXml.replaceAll("''","\"");
        String stringToParse = stringXml.replace("\n", "").replace("\r", "").replaceAll("  +", " ").replace(" <", "<");
        stringToParse = stringToParse.replaceAll("''","'");
//        System.out.println(stringToParse);
       
        Document xml = loadXMLFromString(stringToParse);

        WorkbookDTO workbook = new WorkbookDTO();
        HashMap<String, String> workbookProperties = new HashMap<String, String>();
        List<WorksheetDTO> worksheets = new ArrayList<WorksheetDTO>();

        Node nodeWorkbook = xml.getFirstChild();
        Node currentWorkbookChild = nodeWorkbook.getFirstChild();
        while (currentWorkbookChild != null) {
            if (currentWorkbookChild.getNodeName() == "properties") {
                // set workbook properties
                mapPropertiesFromXMLToDTO(currentWorkbookChild, workbookProperties);
            } else if (currentWorkbookChild.getNodeName() == "worksheet") {
                WorksheetDTO worksheet = new WorksheetDTO();
                List<CellDTO> worksheetcells = new ArrayList<CellDTO>();
                // set worksheet attributes
                if (currentWorkbookChild.getAttributes().getNamedItem("name") != null)
                    worksheet.setName(currentWorkbookChild.getAttributes().getNamedItem("name").getNodeValue());
                // prepare worksheet
                Node currentWorksheetChild = currentWorkbookChild.getFirstChild();
                while (currentWorksheetChild != null) {
                    if (currentWorksheetChild.getNodeName() == "properties") {
                        HashMap<String, String> worksheetProperties = new HashMap<String, String>();
                        // set worksheet properties
                        mapPropertiesFromXMLToDTO(currentWorksheetChild, worksheetProperties);
                        worksheet.setProperties(worksheetProperties);
                    } else if (currentWorksheetChild.getNodeName() == "cell") {
                        // set cell attributes
                        CellDTO newCell = CellFactory.build(type);
                        NamedNodeMap xmlCellAttrs = currentWorksheetChild.getAttributes();
                        if (xmlCellAttrs.getNamedItem("row") != null)
                            newCell.setRow(Integer.parseInt(xmlCellAttrs.getNamedItem("row").getNodeValue()));
                        if (xmlCellAttrs.getNamedItem("column") != null)
                            newCell.setColumn(Integer.parseInt(xmlCellAttrs.getNamedItem("column").getNodeValue()));
                        if (xmlCellAttrs.getNamedItem("inputMapping") != null)
                            newCell.setInputMapping(xmlCellAttrs.getNamedItem("inputMapping").getNodeValue().replaceAll("&apos;", "'").replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
                        if (xmlCellAttrs.getNamedItem("outputMapping") != null)
                            newCell.setOutputMapping(xmlCellAttrs.getNamedItem("outputMapping").getNodeValue().replaceAll("&apos;", "'").replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
                        if (xmlCellAttrs.getNamedItem("tags") != null) {
                            String tags = xmlCellAttrs.getNamedItem("tags").getNodeValue().replaceAll("&apos;", "'").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                            String[] tagsArr = tags.split(",");
                            for (String tag: tagsArr) {
                                newCell.addTag(tag);
                            }
                        }
                        if (xmlCellAttrs.getNamedItem("text") != null) {
                            String text = xmlCellAttrs.getNamedItem("text").getNodeValue().replaceAll("&apos;", "'").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                            if (newCell instanceof CellExtendedDTO) {
                                CellUtil.setFormattedValue(text, (CellExtendedDTO) newCell);
                            } else {
                                newCell.setText(text);
                            }
                        }

                        worksheetcells.add(newCell);
                    }
                    // get next worksheet child and loop again
                    currentWorksheetChild = currentWorksheetChild.getNextSibling();
                }
                worksheet.setCells(worksheetcells);
                worksheets.add(worksheet);
            }
            // get next worbook child and loop again
            currentWorkbookChild = currentWorkbookChild.getNextSibling();
        }
        workbook.setProperties(workbookProperties);
        workbook.setWorksheets(worksheets);

        return workbook;
    }

    private static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8")));
        return builder.parse(is);
    }

    private static void mapPropertiesFromXMLToDTO(Node nodeProps, HashMap<String, String> dtoProps) {
        Node property = nodeProps.getFirstChild();
        while (property != null) {
            NamedNodeMap attrs = property.getAttributes();
            if (attrs.getNamedItem("name") != null && attrs.getNamedItem("value") != null)
                dtoProps.put(attrs.getNamedItem("name").getNodeValue(), attrs.getNamedItem("value").getNodeValue());
            property = property.getNextSibling();
        }
    }

    public static void manageWorksheetsCells(Workbook workbook, List<WorksheetDTO> worksheetsDTO, boolean isTemplateProcess) {
        CellMapper.setPatterns();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            WorksheetDTO worksheetDTO = worksheetsDTO.get(i);

            Sheet sheet = workbook.getSheetAt(i);
            List<CellDTO> cells = manageWorksheetCells(sheet, isTemplateProcess);
            worksheetDTO.setCells(cells);
        }
    }

    private static List<CellDTO> manageWorksheetCells(Sheet sheet, boolean isTemplateProcess) {
        List<CellDTO> cells = new ArrayList<CellDTO>();

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                CellDTO cellDTO = manageCellDTO(cell, isTemplateProcess);
                if (cellDTO != null) {
                    cells.add(cellDTO);
                }
            }
        }
        return cells;
    }

    private static CellDTO manageCellDTO(Cell cell, boolean isTemplateProcess) {
        CellDTO cellDTO = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
            case Cell.CELL_TYPE_BOOLEAN:
            case Cell.CELL_TYPE_ERROR:
            case Cell.CELL_TYPE_NUMERIC:
                break;
            case Cell.CELL_TYPE_FORMULA:
            case Cell.CELL_TYPE_STRING:
                if (isTemplateProcess) {
                    cellDTO = CellMapper.manageCellWithMappings(cell, CellType.TEMPLATE);
                } else {
                    cellDTO = CellMapper.manageCellWithMappings(cell, CellType.BASIC);
                }
                break;
        }
        if (isTemplateProcess) {
            if (CellUtil.isYellowColor(cell)) {
                TemplateCellDTO xCellFormCellDTO = null;

                if (cellDTO == null) {
                    cellDTO = CellFactory.build(CellType.TEMPLATE);
                }
                if (cellDTO instanceof TemplateCellDTO) {
                    xCellFormCellDTO = (TemplateCellDTO) cellDTO;
                    xCellFormCellDTO.setTotal(true);
                    cellDTO = xCellFormCellDTO;
                }
                cellDTO.setRow(cell.getRowIndex());
                cellDTO.setColumn(cell.getColumnIndex());
            }
        }
        return cellDTO;
    }

}
