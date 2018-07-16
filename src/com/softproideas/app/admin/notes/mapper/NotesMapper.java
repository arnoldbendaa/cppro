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
package com.softproideas.app.admin.notes.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.softproideas.app.admin.notes.model.NotesDTO;

public class NotesMapper {

    public static List<NotesDTO> mapNotesList(ArrayList<Object[]> notes) {
        List<NotesDTO> notesDTO = new ArrayList<NotesDTO>();

        if (notes != null) {
            for (Object[] element: notes) {
                NotesDTO notesElement = new NotesDTO();
                notesElement.setStructureElementId(((StructureElementRefImpl) element[1]).getId());
                notesElement.setUser((String) element[0]);
                notesElement.setCostCenter(((StructureElementRefImpl) element[1]).getNarrative());
                notesElement.setComment((String) element[3]);
                notesElement.setBudgetCycleId((Integer)element[4]);
                notesElement.setDataEntryProfileId((Integer)element[5]);
                notesElement.setUserId((Integer)element[7]);
                String date = ((Timestamp) element[2]).toString().substring(0, 19);
                notesElement.setDate(date);
                notesDTO.add(notesElement);
            }
        }
        return notesDTO;
    }

}
