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
package com.softproideas.app.reviewbudget.note.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentEditor;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentEditorSession;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentsProcess;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.ejb.api.dataentry.DataEntryServer;
import com.cedar.cp.ejb.base.cube.CellNote;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.ReviewBudgetCellDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.cedar.cp.util.flatform.model.workbook.notes.CellNotesDTO;
import com.cedar.cp.util.flatform.model.workbook.notes.NoteDTO;
import com.softproideas.app.core.workbook.mapper.review.WorkbookMapper;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.note.mapper.CellNoteMapper;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

/**
 * Service for managing notes.
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Service("noteService")
public class NoteServiceImpl implements NoteService {

    private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    public NoteServiceImpl(){};
    public void Init(CPContext context){
    	cpContextHolder = new CPContextHolder();
    	cpContextHolder.init(context);
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.spreadsheet.webapp.service.BudgetService#fetchCellNotes(int, java.lang.String) */
    @Override
    public CellNotesDTO fetchCellNotes(int financeCubeId, String cellPK) throws ServiceException {
        try {
            EntityList entity = cpContextHolder.getDataEntryProcess().getCellNote(financeCubeId, cellPK, cpContextHolder.getUserId());
            return CellNoteMapper.mapNotesToDTO(entity, cellPK);
        } catch (Exception e) {
            logger.error("Can not download cell notes (financeCubeId=" + financeCubeId + ", cellPK=" + cellPK + ")", e);
            throw new ServiceException("Can not download cell notes", e);
        }
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.spreadsheet.webapp.service.NoteService#fetchLastNotesForFinanceCube(com.cedar.cp.util.flatform.model.Workbook) */
    public HashMap<Integer, List<CellNotesDTO>> fetchLastNotesForFinanceCube(Workbook workbook) throws ServiceException {
        HashMap<Integer, List<CellNotesDTO>> notes = new HashMap<Integer, List<CellNotesDTO>>();
        for (Worksheet worksheet: workbook.getWorksheets()) {
            if (worksheet.getProperties() == null) {
                continue;
            }
            String cubeId = worksheet.getProperties().get(WorkbookProperties.FINANCE_CUBE_ID.toString());
            if (cubeId == null || cubeId.equals("")) {
                continue;
            }
            Integer financeCubeId = Integer.parseInt(cubeId.substring(cubeId.lastIndexOf('|') + 1));
            cubeId = null;

            if (!notes.containsKey(financeCubeId)) {
                try {
                    EntityList entity = cpContextHolder.getDataEntryProcess().getLastNotes(financeCubeId);
                    if (entity.getNumRows() > 0) {
                        notes.put(financeCubeId, CellNoteMapper.mapLastNotesToDTOs(entity));
                    }
                } catch (CPException e) {
                    logger.error("Can not download notes", e);
                    throw new ServiceException("Can not download notes", e);
                } catch (ValidationException e) {
                    logger.error("Can not validate notes", e);
                    throw new ServiceException("Can not validate notes", e);
                }
            }
        }
        return notes;
    }
    
    @Override
    public void fetchLastNotesForFinanceCube(WorkbookDTO workbook, List<ElementDTO> selectedDimensions) throws ServiceException {
        HashMap<Integer, List<CellNotesDTO>> notes = new HashMap<Integer, List<CellNotesDTO>>();
        for (WorksheetDTO worksheet: workbook.getWorksheets()) {
            if (worksheet.getProperties() == null) {
                continue;
            }
            String cubeId = worksheet.getProperties().get(WorkbookProperties.FINANCE_CUBE_ID.toString());
            if (cubeId == null || cubeId.equals("")) {
                continue;
            }
            Integer financeCubeId = Integer.parseInt(cubeId.substring(cubeId.lastIndexOf('|') + 1));
            cubeId = null;

            if (!notes.containsKey(financeCubeId)) {
                try {
                    List<CellNotesDTO> cellNotes = null;
                    EntityList entity = cpContextHolder.getDataEntryProcess().getLastNotes(financeCubeId);
                    if (entity.getNumRows() > 0) {
                        cellNotes = CellNoteMapper.mapLastNotesToDTOs(entity);
                        //notes.put(financeCubeId, cellNotes);
                    }
                    else{
                        cellNotes = new ArrayList<CellNotesDTO>();
                    }
                    for(CellDTO cell: worksheet.getCells()){                        
                        WorkbookMapper.filterCellNotesDTO(selectedDimensions , workbook.getProperties().get(WorkbookProperties.DATA_TYPE.toString()), (ReviewBudgetCellDTO)cell, cellNotes);
                    }
                } catch (CPException e) {
                    logger.error("Can not download notes", e);
                    throw new ServiceException("Can not download notes", e);
                } catch (ValidationException e) {
                    logger.error("Can not validate notes", e);
                    throw new ServiceException("Can not validate notes", e);
                }
            }
        }
        //return notes;
    }

    private int saveAttachment(String fileName, byte[] attachment) throws ServiceException {
        try {
            ExtendedAttachmentsProcess process = cpContextHolder.getExtAttachmentsProcess();
            ExtendedAttachmentEditorSession session;
            session = process.getExtendedAttachmentEditorSessionForId(null);
            ExtendedAttachmentEditor editor = session.getExtendedAttachmentEditor();
            editor.setFileName(fileName);
            editor.setAttatch(attachment);
            editor.commit();
            ExtendedAttachmentPK attachmentPK = (ExtendedAttachmentPK) session.commit(false);
            int attachmentId = attachmentPK.getExtendedAttachmentId();
            process.terminateSession(session);
            return attachmentId;
        } catch (Exception e) {
            logger.error("Can not save attachment", e);
            throw new ServiceException("Can not save attachment.", e);
        }
    }

    private void deleteAttachment(int attachmentId) throws ServiceException {
        try {
            ExtendedAttachmentsProcess process = cpContextHolder.getExtAttachmentsProcess();
            process.deleteObject(new ExtendedAttachmentPK(attachmentId));
        } catch (Exception e) {
            logger.error("Can not delete attachment (attachmentId=" + attachmentId + ")", e);
            throw new ServiceException("Can not delete attachment.", e);
        }
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.spreadsheet.webapp.service.NoteService#executeNoteAction(java.lang.String, com.softproideas.spreadsheet.webapp.model.dto.workbook.NoteDTO, int, int, int, java.lang.String, java.lang.String, byte[], boolean, java.lang.String) */
    @Override
    public void executeNoteAction(String cellPK, NoteDTO note, int dataEntryProfileId, int financeCubeId, int budgetCycleId, String company, String fileName, byte[] attachment, boolean removeAttachment, String action) throws ServiceException {
        // validate
        if (financeCubeId < 1 || dataEntryProfileId < 0 || cellPK == null) {
            throw new ServiceException("Parameters are invalid.");
        }
        if (!action.equals("INSERT") && !action.equals("UPDATE") && !action.equals("DELETE")) {
            throw new ServiceException("HTTP method unsupported.");
        }

        if (!note.getAuthor().equals(cpContextHolder.getUserName()) && !cpContextHolder.getUserContext().isAdmin()) {
            // if you are not the author and admin
            throw new ServiceException("You don\'t have permission.");
        }

        // remove attachment
        if (removeAttachment && note.getLinkId() > 0 && (action.equals("DELETE") || action.equals("UPDATE"))) {
            deleteAttachment(note.getLinkId());
            note.setLinkId(0);
        }
        // save attachment
        if (fileName != null) {
            note.setLinkId(saveAttachment(fileName, attachment));
        }

        // save note
        DataEntryServer server = new DataEntryServer(cpContextHolder.getCPContext().getCPConnection());
        try {
            // DataEntrySEJB discriminates action
            CellNote cellNote = setCellNote(note, cellPK, dataEntryProfileId, company);
            server.saveCellNoteChange(cellNote, financeCubeId, budgetCycleId, action);
        } catch (ValidationException e) {
            logger.error("Can not save the note", e);
            throw new ServiceException("Can not save the note.", e);
        }
    }

    /**
     * Converts DTO to cellNote
     */
    private CellNote setCellNote(NoteDTO note, String cellPK, int dataEntryProfileId, String company) {
        CellNote cellNote = new CellNote();
        // notePk without dataType
        cellNote.setAddr(cellPK.substring(0, cellPK.lastIndexOf(",")));
        cellNote.setDataEntryProfileId(dataEntryProfileId);
        cellNote.setDataType(cellPK.substring(cellPK.lastIndexOf(",") + 1));
        cellNote.setNote(note.getText());
        cellNote.setUserName(cpContextHolder.getUserName());
        cellNote.setCompany(company);
        cellNote.setLinkId(note.getLinkId());
        cellNote.setLinkType(note.getLinktype());
        cellNote.setTimestamp(note.getTime());
        return cellNote;
    }

}
