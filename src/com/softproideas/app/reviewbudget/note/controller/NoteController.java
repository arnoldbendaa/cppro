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
package com.softproideas.app.reviewbudget.note.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cedar.cp.util.flatform.model.workbook.notes.CellNotesDTO;
import com.cedar.cp.util.flatform.model.workbook.notes.NoteDTO;
import com.softproideas.app.reviewbudget.note.model.NoteUpload;
import com.softproideas.app.reviewbudget.note.service.NoteService;
import com.softproideas.commons.context.CPContextHolder;

/**
 * Controller for managing notes.
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    CPContextHolder cpContextHolder;

    /**
     * GET /notes/cell/{cellPK}/financeCube/{financeCube}
     * 
     * This method returns list of notes in selected cell.
     */
    @ResponseBody
    @RequestMapping("/notes/cell/{cellPK}/financeCube/{financeCube}")
    public CellNotesDTO fetchCellNotes( //
            @PathVariable String cellPK, //
            @PathVariable String financeCube //
    ) throws Exception {

        Integer fcId = Integer.parseInt(financeCube.substring(financeCube.lastIndexOf('|') + 1));
        return noteService.fetchCellNotes(fcId, cellPK);
    }

    /**
     * POST /note/cell/{cellPK}/financeCube/{financeCube}/dataEntryProfile/{dataEntryProfileId}/budgetCycle/{budgetCycleId}
     * 
     * This method creates new note for selected cell.
     */
    @ResponseBody
    @RequestMapping(value = "/note/cell/{cellPK}/financeCube/{financeCube}/dataEntryProfile/{dataEntryProfileId}/budgetCycle/{budgetCycleId}", method = RequestMethod.POST)
    public CellNotesDTO insertNote( //
            @PathVariable String cellPK, //
            @PathVariable String financeCube, //
            @PathVariable int dataEntryProfileId, //
            @PathVariable int budgetCycleId, //
            @RequestParam(value = "company", required = false) String company, //
            @ModelAttribute("upload") NoteUpload uploadedNote //
    ) throws Exception {

        String noteText = uploadedNote.getNote();
        MultipartFile attachment = uploadedNote.getAttachment();

        // Create new note
        NoteDTO newNote = new NoteDTO();
        newNote.setText(noteText);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        newNote.setTime(sdf.format(cal.getTime()));
        newNote.setAuthor(cpContextHolder.getUserName());

        Integer fcId = Integer.parseInt(financeCube.substring(financeCube.lastIndexOf('|') + 1));
        if (attachment != null) {
            noteService.executeNoteAction(cellPK, newNote, dataEntryProfileId, fcId, budgetCycleId, company, attachment.getOriginalFilename(), attachment.getBytes(), false, "INSERT");
        } else {
            noteService.executeNoteAction(cellPK, newNote, dataEntryProfileId, fcId, budgetCycleId, company, null, null, false, "INSERT");
        }

        return noteService.fetchCellNotes(fcId, cellPK);
    }

    /**
     * POST /note/cell/{cellPK}/financeCube/{financeCube}/shouldDeleteOldAttachment/{shouldDeleteOldAttachment}/noteTime/{noteTime}/link/{linkId}/author/{author}
     * 
     * This method updates the note.
     */
    @ResponseBody
    @RequestMapping(value = "/note/cell/{cellPK}/financeCube/{financeCube}/shouldDeleteOldAttachment/{shouldDeleteOldAttachment}/noteTime/{noteTime}/link/{linkId}/author/{author}", method = RequestMethod.POST)
    public CellNotesDTO updateNote( //
            @PathVariable String cellPK, //
            @PathVariable String financeCube, //
            @PathVariable Boolean shouldDeleteOldAttachment, //
            @PathVariable String noteTime, @PathVariable int linkId, //
            @PathVariable String author, @RequestParam(value = "company", required = false) String company, //
            @ModelAttribute("upload") NoteUpload uploadedNote //
    ) throws Exception {

        Integer fcId = Integer.parseInt(financeCube.substring(financeCube.lastIndexOf('|') + 1));

        String noteText = uploadedNote.getNote();
        MultipartFile attachment = uploadedNote.getAttachment();

        NoteDTO updatedNote = new NoteDTO();
        updatedNote.setText(noteText);
        updatedNote.setTime(noteTime);
        updatedNote.setLinkId(linkId);
        updatedNote.setAuthor(author);

        if (attachment != null) {
            noteService.executeNoteAction(cellPK, updatedNote, 0, fcId, 0, company, attachment.getOriginalFilename(), attachment.getBytes(), shouldDeleteOldAttachment, "UPDATE");
        } else {
            noteService.executeNoteAction(cellPK, updatedNote, 0, fcId, 0, company, null, null, shouldDeleteOldAttachment, "UPDATE");
        }

        return noteService.fetchCellNotes(fcId, cellPK);
    }

    /**
     * DELETE /note/cell/{cellPK}/financeCube/{financeCube}/noteTime/{noteTime}/link/{linkId}/author/{author}
     * 
     * This method deletes the note.
     */
    @ResponseBody
    @RequestMapping(value = "/note/cell/{cellPK}/financeCube/{financeCube}/noteTime/{noteTime}/link/{linkId}/author/{author}", method = RequestMethod.DELETE)
    public CellNotesDTO deleteNote( //
            @PathVariable String cellPK, //
            @PathVariable String financeCube, //
            @PathVariable String noteTime, //
            @PathVariable int linkId, //
            @PathVariable String author, //
            @RequestParam(value = "company", required = false) String company //
    ) throws Exception {

        NoteDTO deletedNote = new NoteDTO();
        deletedNote.setTime(noteTime);
        deletedNote.setLinkId(linkId);
        deletedNote.setAuthor(author);

        Integer fcId = Integer.parseInt(financeCube.substring(financeCube.lastIndexOf('|') + 1));
        noteService.executeNoteAction(cellPK, deletedNote, 0, fcId, 0, company, null, null, true, "DELETE");

        return noteService.fetchCellNotes(fcId, cellPK);
    }

}
