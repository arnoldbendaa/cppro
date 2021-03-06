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
package com.softproideas.app.admin.notes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.notes.model.NotesDTO;
import com.softproideas.app.admin.notes.server.NotesService;

@RequestMapping(value = "/notes")
@Controller
public class NotesController {

    @Autowired
    NotesService notesService;

    /**
     * Get list of selected range Notes.
     */
    @ResponseBody
    @RequestMapping(value = "/{costCenters}/{financeCubeId}/{fromDate}/{toDate}", method = RequestMethod.GET)
    public List<NotesDTO> browseNotes(@PathVariable ArrayList<Integer> costCenters, @PathVariable int financeCubeId, @PathVariable String fromDate, @PathVariable String toDate) throws Exception {
        return notesService.browseNotes(costCenters, financeCubeId, fromDate, toDate);
    }

    /**
     * Get open profile link.
     */
    @ResponseBody
    @RequestMapping(value = "/openProfile/{modelId}/{costCenters}/{financeCubeId}/{date}", method = RequestMethod.GET)
    public String openProfile(@PathVariable int modelId, @PathVariable Integer costCenters, @PathVariable int financeCubeId, @PathVariable String date) throws Exception {
        return notesService.openProfile(modelId, costCenters, financeCubeId, date);
    }

}
