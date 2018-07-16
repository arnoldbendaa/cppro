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
package com.softproideas.app.admin.notes.server;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.softproideas.app.admin.notes.mapper.NotesMapper;
import com.softproideas.app.admin.notes.model.NotesDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

@Service("notesService")
public class NotesServiceImpl implements NotesService {

    private static Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /**
     * Get list of selected range Notes.
     */
    @Override
    public List<NotesDTO> browseNotes(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate) throws ServiceException {
        ArrayList<Object[]> notes = null;
        List<NotesDTO> notesDTO;
        try {
            notes = cpContextHolder.getListHelper().getNotesForCostCenters(costCenters, financeCubeId, fromDate, toDate);
        } catch (CPException e) {
            logger.error("Error during get Notes list!", e);
            throw new ServiceException("Error during get Notes list!", e);
        }
        notesDTO = NotesMapper.mapNotesList(notes);
        return notesDTO;
    }

    /**
     * Get open profile link.
     */
    @Override
    public String openProfile(int modelId, Integer costCenter, int financeCubeId, String date) throws ServiceException {
        String profileLink = null;
        ArrayList<Object[]> notes = null;
        ArrayList<Integer> costCenterList = new ArrayList<Integer>();
        int userId = cpContextHolder.getUserContext().getUserId();
        costCenterList.add(costCenter);
        try {
            date = date.substring(0, 10);
            notes = cpContextHolder.getListHelper().getNotesForCostCenters(costCenterList, financeCubeId, date, date);
            Object[] selectedNote = notes.get(0);

            String dim1VisId = ((DimensionElementRef) cpContextHolder.getListHelper().getDimensionElementEntityRef(new DimensionElementPK((Integer) selectedNote[6]))).getNarrative();

            EntityList list = cpContextHolder.getListHelper().getSystemProperty("WEB: Root URL");
            String contextRoot = (String) list.getValueAt(0, "Value");
            profileLink = contextRoot + "/reviewBudget.do?modelId=" + modelId + "&budgetCycleId=" + selectedNote[4] + "&profileRef=DataEntryProfileCK%7CUserPK%7C" + userId + "%7CDataEntryProfilePK%7C" + selectedNote[5] + "&topNodeId=" + costCenter + "&dimensions=" + ((StructureElementRefImpl) selectedNote[1]).getNarrative() + "," + dim1VisId;

        } catch (CPException e) {
            logger.error("Error during open profile!", e);
            throw new ServiceException("Error during open profile!", e);
        }
        return profileLink;
    }

}
