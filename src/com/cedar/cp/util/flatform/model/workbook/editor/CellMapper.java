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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;

import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.util.CellFactory;
import com.cedar.cp.util.flatform.model.workbook.util.CellType;
import com.softproideas.util.validation.MappingFunction;

public class CellMapper {
    private static Pattern patternInput;
    private static Pattern patternOutput;
    private static Pattern patternTag;
    private static Pattern patternFormula;
    private static Pattern patternText;
     
    public static void setPatterns() {
        patternInput = Pattern.compile("(input|INPUT|Input|in|IN|In)\\{\\{[^}}]*\\}\\}");
        patternOutput = Pattern.compile("(output|OUTPUT|Output|out|OUT|Out)\\{\\{[^}}]*\\}\\}");
        patternTag = Pattern.compile("(tags|TAGS|Tags|tag|Tag|TAG)\\{\\{[^}}]*\\}\\}");
        patternFormula = Pattern.compile("(formula|FORMULA|Formula)\\{\\{[^}}]*\\}\\}");
        patternText = Pattern.compile("(text|TEXT|Text)\\{\\{[^}}]*\\}\\}");
    }
    
    public static CellDTO manageCellWithMappings(Cell cell, CellType type) {
        CellDTO cellDTO = null;

        String importText;
        boolean correctString;
        importText = cell.toString();
        
        if (FormUtil.checkImportString(importText)) {
            correctString = false;
            importText = cell.getRichStringCellValue().getString();

            if(importText ==null || !MappingFunction.containsAnyMappingFunction(importText)){
                System.out.println();
            }
            
            cellDTO = CellFactory.build(type);
            
            // Input mapping
            Matcher matcher = patternInput.matcher(importText);
            while (matcher.find()) {
                String input = matcher.group();
                input = input.substring(input.indexOf("{") + 2, input.length() - 2); // remove *{{ and }}
                if (MappingFunction.containsAnyMappingFunction(input)) {
                    correctString = true;
                    cellDTO.setInputMapping(input);
                }
                else{
                    cell.setCellValue(input);
                }
            }

            // Output mapping
            matcher = patternOutput.matcher(importText);
            while (matcher.find()) {
                String output = matcher.group();
                output = output.substring(output.indexOf("{") + 2, output.length() - 2); // remove *{{ and }}
                if (MappingFunction.containsAnyMappingFunction(output)) {
                    correctString = true;
                    cellDTO.setOutputMapping(output);
                }
                else{
                    cell.setCellValue(output);
                }
            }
            // Tags
            matcher = patternTag.matcher(importText);
            while (matcher.find()) {
                correctString = true;
                String tags = matcher.group();
                tags = tags.substring(tags.indexOf("{") + 2, tags.length() - 2); // remove *{{ and }}
                String[] tagsArr = tags.split(",");
                for (String tag : tagsArr) {
                    cellDTO.addTag(tag.toLowerCase());
                }
            }

            // Formula
            matcher = patternFormula.matcher(importText);
            while (matcher.find()) {
                String text = matcher.group();
                text = text.substring(text.indexOf("{") + 2, text.length() - 2); // remove *{{ and }}
                if (text.startsWith("=")) {
                    cellDTO.setText(text);
                } else {
                    cellDTO.setText("=" + text);
                }
                correctString = true;
//                if (FormUtil.checkFormulaMapping(text)) {
//                    correctString = true;
//                    if (text.startsWith("=")) {
//                        cellDTO.setText(text);
//                    } else {
//                        cellDTO.setText("=" + text);
//                    }
//                }
//                else{
//                    if (text.startsWith("=")) {
//                        text = text.substring(1);
//                    }
//                    try {
//                        cell.setCellFormula(text);
//                    } catch (FormulaParseException ex) {
//                        correctString = true;
//                    }
//                }
            }

            // Text
            matcher = patternText.matcher(importText);
            while (matcher.find()) {
                correctString = true;
                String text = matcher.group();
                text = text.substring(text.indexOf("{") + 2, text.length() - 2); // remove *{{ and }}
                cell.setCellValue(text);
                if (!GeneralUtils.isEmptyOrNull(cellDTO.getOutputMapping()) || !GeneralUtils.isEmptyOrNull(cellDTO.getInputMapping())) {
                    cellDTO.setText(text);
                }
            }

            if (!correctString) {
                //FormUtil.setErrorCellFormat(cell);
                return null;
            } else {
                RichTextString richTextString = null;
                cell.setCellValue(richTextString);
            }

            cellDTO.setRow(cell.getRowIndex());
            cellDTO.setColumn(cell.getColumnIndex());
        }
        return cellDTO;
    }
}
