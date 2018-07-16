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
package com.softproideas.app.reviewbudget.note.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.util.flatform.model.workbook.notes.CellNotesDTO;
import com.cedar.cp.util.flatform.model.workbook.notes.NoteDTO;

/**
 * Cell notes mapper to DTO
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class CellNoteMapper {

    /**
     * Sets notePK and list of notes from entity to DTO
     */
    public static CellNotesDTO mapNotesToDTO(EntityList entity, String notePk) {
        CellNotesDTO notes = new CellNotesDTO();
        notes.setNotePk(notePk);
        if (entity.getNumRows() > 0) {

            List<NoteDTO> list = new ArrayList<NoteDTO>();

            for (int i = 0; i < entity.getNumRows(); i++) {
                NoteDTO note = new NoteDTO();
                note.setText((String) entity.getValueAt(i, "STRING_VALUE"));
                note.setAuthor((String) entity.getValueAt(i, "USER_NAME"));
                note.setTime(((Timestamp) entity.getValueAt(i, "CREATED")).toString());
                note.setLinkId((Integer) entity.getValueAt(i, "LINK_ID"));
                note.setLinktype((Integer) entity.getValueAt(i, "LINK_TYPE"));

                list.add(note);
            }
            notes.setNotes(list);
        }
        return notes;

    }

    /**
     * Maps last notes from entity to list of DTOs
     */
    public static List<CellNotesDTO> mapLastNotesToDTOs(EntityList entity) {

        List<CellNotesDTO> result = new ArrayList<CellNotesDTO>();

        for (int i = 0; i < entity.getNumRows(); i++) {

            CellNotesDTO notes = new CellNotesDTO();
            notes.setNotePk((String) entity.getValueAt(i, "NOTE_PK"));
            List<NoteDTO> list = new ArrayList<NoteDTO>();
            NoteDTO note = new NoteDTO();

            note.setText((String) entity.getValueAt(i, "STRING_VALUE"));
            note.setAuthor((String) entity.getValueAt(i, "USER_NAME"));
            note.setTime(((Timestamp) entity.getValueAt(i, "CREATED")).toString());
            note.setLinkId((Integer) entity.getValueAt(i, "LINK_ID"));
            note.setLinktype((Integer) entity.getValueAt(i, "LINK_TYPE"));

            list.add(note);
            notes.setNotes(list);
            try {
                // set dimensions and dataType
                List<String> dimensions = new ArrayList<String>();
                for (int j = 0; j < 3; j++) {
                    String dim = (String) entity.getValueAt(i, "DIM" + j);
                    if (dim == null) {
                        dim = ""; // wrong dimension, don't block app
                    }
                    dimensions.add(dim);
                }
                notes.setDimensions(dimensions);
                String dataType = (String) entity.getValueAt(i, "DATA_TYPE");
                notes.setDataType(dataType == null ? "" : dataType);
            } catch (Exception e) {
                // do nothing, no data availabe
            }
            result.add(notes);
        }
        return result;
    }

}
