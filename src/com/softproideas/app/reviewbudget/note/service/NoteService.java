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

import java.util.HashMap;
import java.util.List;

import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.notes.CellNotesDTO;
import com.cedar.cp.util.flatform.model.workbook.notes.NoteDTO;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.common.exceptions.ServiceException;

public interface NoteService {

    /**
     * Downloads all notes for given cell
     */
    CellNotesDTO fetchCellNotes(int financeCubeId, String cellPK) throws ServiceException;

    /**
     * Downloads last notes for each cell, groups them by finance cube
     * 
     * @return map (financeCubeId, list of notes DTO)
     */
    HashMap<Integer, List<CellNotesDTO>> fetchLastNotesForFinanceCube(Workbook workbook) throws ServiceException;

    /**
     * Inserts, update or delete note
     * 
     * @param cellPK
     * @param note
     * @param dataEntryProfileId
     * @param financeCubeId
     * @param budgetCycleId
     * @param company for global mapped model
     * @param fileName don't save attachment when null
     * @param attachment
     * @param action INSERT/UPDATE/DELETE
     * @throws ServiceException
     */
    void executeNoteAction(String cellPK, NoteDTO note, int dataEntryProfileId, int financeCubeId, int budgetCycleId, String company, String fileName, byte[] attachment, boolean removeAttachment, String action) throws ServiceException;

    void fetchLastNotesForFinanceCube(WorkbookDTO workbook, List<ElementDTO> selectedDimensions) throws ServiceException;

	void Init(CPContext context);
}
