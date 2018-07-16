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
package com.softproideas.app.lookuptable.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softproideas.app.lookuptable.project.dao.LookupProjectDao;
import com.softproideas.app.lookuptable.project.model.LookupProjectDTO;
import com.softproideas.app.lookuptable.project.model.LookupProjectFilterDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;

@Service("lookupProjectService")
public class LookupProjectServiceImpl implements LookupProjectService {
    
    @Autowired
    LookupProjectDao lookupProjectDao;

    @Override
    public List<LookupProjectDTO> browseProjects(String orderby, String[] filters) throws ServiceException {
        try {
            List<LookupProjectFilterDTO> filterObjects = createFilterList(filters);
            List<LookupProjectDTO> projects = lookupProjectDao.browseProjects(orderby, filterObjects);
            return projects;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    @Override
    public List<LookupProjectDTO> browseProjects(Integer pageNumber, String orderby, String[] filters) throws ServiceException {
        try {
            List<LookupProjectFilterDTO> filterObjects = createFilterList(filters);
            List<LookupProjectDTO> projects = lookupProjectDao.browseProjects(pageNumber, orderby, filterObjects);
            return projects;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
        
    @Override
    public List<LookupProjectDTO> browseProjects(Integer pageSize, Integer pageNumber, String orderby, String[] filters) throws ServiceException {

        try {
            List<LookupProjectFilterDTO> filterObjects = createFilterList(filters);
            return lookupProjectDao.browseProjects(pageSize, pageNumber, orderby, filterObjects);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }
    
    private List<LookupProjectFilterDTO> createFilterList(String[] filters) {
        List<LookupProjectFilterDTO> filterObjects = new ArrayList<LookupProjectFilterDTO>();
        if (filters != null && filters.length != 0) {
            for (String filter: filters) {
                LookupProjectFilterDTO filterObject = new LookupProjectFilterDTO(filter);
                filterObjects.add(filterObject);
            }
        }
        return filterObjects;
    }

    @Override
    public Integer getRowsCount() {
        return lookupProjectDao.getRowsCount();
    }

}
