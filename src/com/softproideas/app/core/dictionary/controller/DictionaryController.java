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
package com.softproideas.app.core.dictionary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.core.dictionary.service.DictionaryService;
import com.softproideas.common.models.DictionaryDTO;

/**
 * 
 * <p>Spring MVC controller responsible for handling requests from web browser based user interface.
 * Controller is available at the url <em>/dictionary</em>.</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/dictionary")
@Controller
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<DictionaryDTO> browseDictionaries() throws Exception {
        return dictionaryService.browseDictionaries();
    }

    @ResponseBody
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public List<DictionaryDTO> browseDictionaries(@PathVariable String type) throws Exception {
        return dictionaryService.browseDictionaries(type);
    }
    
    @ResponseBody
    @RequestMapping(value = "/import/type/{type}", method = RequestMethod.GET)
    public List<DictionaryDTO> importDictionaries(@PathVariable String type) throws Exception {
        return dictionaryService.importDictionaries(type);
    }

    @ResponseBody
    @RequestMapping(value = "/save/type/{type}", method = RequestMethod.POST)
    public List<DictionaryDTO> saveDictionaries(@PathVariable String type, @RequestBody List<DictionaryDTO> dictionariesDTO) throws Exception {
        return dictionaryService.saveDictionaries(dictionariesDTO, type);
    }
    
}
