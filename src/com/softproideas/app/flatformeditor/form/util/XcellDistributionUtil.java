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
package com.softproideas.app.flatformeditor.form.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionSSO;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.cedar.cp.util.flatform.model.Properties;
import com.cedar.cp.util.flatform.model.Properties.PropEntry;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.reader.XMLReader;

/**
 * @author  Przemysław Giszczak
 * @company Softpro.pl Sp. z o.o.
 *
 */
public class XcellDistributionUtil {

    private XmlFormEditorSessionServer xmlFormEditorSessionServer;
    private CPConnection mConnection;
    EntityList mFinanceCubes;
    /**
     * 
     */
    public XcellDistributionUtil(CPConnection connection) {
        this.mConnection = connection;
        this.xmlFormEditorSessionServer = new XmlFormEditorSessionServer( this.mConnection );
        this.mFinanceCubes = this.mConnection.getListHelper().getAllFinanceCubes();
    }

    /**
     * 1. Method retrieves the source workbook and all of destination workbooks from db.
     * 2. Method merges the source workbook with each of destination workbooks keeping original properties of destination workbook.
     *
     * @param sourcePK
     * @param destinationPK
     * @throws CPException
     * @throws ValidationException 
     */
    public void distributeXcellForm(XmlFormPK sourcePK, List<XmlFormPK> destPKs) throws CPException, ValidationException {
        try {
            XmlFormEditorSessionSSO sourceXmlFormEditor = xmlFormEditorSessionServer.getItemData(sourcePK);

            for (XmlFormPK dest : destPKs) {
                XmlFormEditorSessionSSO destXmlFormEditor = xmlFormEditorSessionServer.getItemData(dest);
                Workbook result =  this.merge(this.loadFromXML(sourceXmlFormEditor.getEditorData().getDefinition()), this.loadFromXML(destXmlFormEditor.getEditorData().getDefinition()), destXmlFormEditor.getEditorData().getFinanceCubeId());
                String destXml = this.writeXML(result);
                destXmlFormEditor.getEditorData().setDefinition(destXml);
                destXmlFormEditor.getEditorData().setExcelFile( sourceXmlFormEditor.getEditorData().getExcelFile() );
                destXmlFormEditor.getEditorData().setJsonForm( sourceXmlFormEditor.getEditorData().getJsonForm() );
                this.save(destXmlFormEditor);
            }
        } catch (Exception e) {
            throw new CPException("Error while coping XcellForm!", e);
        }
    }
    
    /**
     * Merges two Workbooks keeping properties from destination one.
     * 
     * @param source
     * @param destination
     * @return
     */
    private Workbook merge(Workbook source, Workbook destination, int financeCubeId) {
        ModelRef modelRef = null;
        FinanceCubeRef financeCubeRef = null;
        Properties properties = new Properties();
        EntityList hierarchies = null;
        
        FinanceCubePK financeCubePk = new FinanceCubePK(financeCubeId);
        for (int cubeIdx = 0; cubeIdx < this.mFinanceCubes.getNumRows(); cubeIdx++) {
            if ( ((FinanceCubeCK) ((FinanceCubeRef)this.mFinanceCubes.getValueAt(cubeIdx, "FinanceCube")).getPrimaryKey()).getFinanceCubePK().equals(financeCubePk) ) {
                financeCubeRef = (FinanceCubeRef)this.mFinanceCubes.getValueAt(cubeIdx, "FinanceCube");
                modelRef = (ModelRef)this.mFinanceCubes.getValueAt(cubeIdx, "Model");
                cubeIdx = this.mFinanceCubes.getNumRows();
            }
        }
        if ((financeCubeRef != null) && (modelRef != null)) {
            hierarchies = this.mConnection.getModelsProcess().getAllHierarchiesForModel(financeCubeRef);
            PropEntry propEntry = new PropEntry();
            
            propEntry.setName(WorkbookProperties.MODEL_ID.toString());
            propEntry.setValue(modelRef.getTokenizedKey());
            properties.addPropEntry(propEntry);
            
            propEntry.setName(WorkbookProperties.MODEL_VISID.toString());
            propEntry.setValue(modelRef.getNarrative());
            properties.addPropEntry(propEntry);
            
            propEntry.setName(WorkbookProperties.FINANCE_CUBE_ID.toString());
            propEntry.setValue(financeCubeRef.getTokenizedKey());
            properties.addPropEntry(propEntry);
            
            propEntry.setName(WorkbookProperties.FINANCE_CUBE_VISID.toString());
            propEntry.setValue(financeCubeRef.getNarrative());
            properties.addPropEntry(propEntry);

            for(int dimIndex = 0; dimIndex < hierarchies.getNumRows(); ++dimIndex) {
                propEntry.setName(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(dimIndex)));
                propEntry.setValue(hierarchies.getValueAt(dimIndex, "Dimension").toString());
                properties.addPropEntry(propEntry);

                HierarchyRef hierarcyRef = (HierarchyRef)hierarchies.getValueAt(dimIndex, "Hierarchy");
                if(hierarcyRef != null) {
                    propEntry.setName(WorkbookProperties.DIMENSION_$_HIERARCHY_VISID.toString().replace("$", String.valueOf(dimIndex)));
                    propEntry.setValue(hierarcyRef.getNarrative());
                    properties.addPropEntry(propEntry);
                }
            }
        }
        
        Workbook sourceCopy = this.loadFromXML( this.writeXML(source) );
        Workbook result = new Workbook();
        result.setProperties( sourceCopy.getProperties() );
        if (modelRef != null) {
            result.setProperty(WorkbookProperties.MODEL_VISID.toString(), modelRef.toString());
        }
    
        Worksheet tempWorksheet = null;
        Properties tempProperties = null;
        for( Worksheet ws : sourceCopy.getWorksheets() ) {
            
            tempWorksheet = destination.getWorksheet(ws.getName());
            if (tempWorksheet != null) {
                tempProperties = tempWorksheet.getProperties();
                if (tempProperties != null) {
                    ws.setProperties(tempProperties);
                } else {
                    ws.setProperties(properties);
                }
            } else {
                ws.setProperties(properties);
            }

            result.addWorksheet( ws );
            tempWorksheet = null;
        }
        
        return result;
    }
    
    /**
     * Saves merged XmlForm to database
     * 
     * @param xcellForm
     * @throws ValidationException 
     * @throws CPException 
     */
    private void save(XmlFormEditorSessionSSO xcellForm ) throws CPException, ValidationException {
        this.xmlFormEditorSessionServer.update( xcellForm.getEditorData() );
    }
    
    /**
     * Transforms Xml to Workbook object
     * 
     * @param xml
     * @return
     * @throws Exception
     */
    private Workbook loadFromXML(String xml) {
        try {
            XMLReader reader = new XMLReader();
            reader.init();
            StringReader sr = new StringReader(xml);
            reader.parseConfigFile(sr);
            Workbook workbook = reader.getWorkbook();
            // workbook.setResourceHandler(this);
            return workbook;
        } catch (Exception var11) {
            return null;
        }
    }

    /**
     * Transforms Workbook object to string
     * 
     * @param workbook
     * @return
     */
    private String writeXML(Workbook workbook) {
        try {
            StringWriter sw = new StringWriter();
            workbook.writeXml(sw);
            return sw.toString();
        } catch (IOException var11) {
            return null;
        }
    }

}
