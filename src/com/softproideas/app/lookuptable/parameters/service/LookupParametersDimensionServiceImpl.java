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
package com.softproideas.app.lookuptable.parameters.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softproideas.app.core.dictionary.dao.DictionaryDao;
import com.softproideas.app.lookuptable.parameters.dao.LookupParametersDimensionDao;
import com.softproideas.app.lookuptable.parameters.dao.LookupParametersOADao;
import com.softproideas.app.lookuptable.parameters.model.LookupDimensionOADTO;
import com.softproideas.app.lookuptable.parameters.model.LookupParameterDimensionDTO;
import com.softproideas.common.enums.LookupParameterStatus;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;

@Service("lookupParametersDimensionService")
public class LookupParametersDimensionServiceImpl implements LookupParametersDimensionService {

    @Autowired
    LookupParametersDimensionDao lookupParametersDimensionDao;

    @Autowired
    LookupParametersOADao lookupParametersOADao;

    @Autowired
    DictionaryDao dictionaryDao;

    @Override
    public List<LookupParameterDimensionDTO> browseDimensions() {
        try {
            return lookupParametersDimensionDao.browseDimensions();
        } catch (DaoException e) {
            return new ArrayList<LookupParameterDimensionDTO>();
        }
    }

    @Override
    public List<LookupParameterDimensionDTO> browseDimensions(int company) {
        try {
            return lookupParametersDimensionDao.browseDimensions(company);
        } catch (DaoException e) {
            return new ArrayList<LookupParameterDimensionDTO>();
        }
    }

    @Override
    public List<LookupParameterDimensionDTO> importDimensions(int company) throws ServiceException {
        try {
            List<LookupDimensionOADTO> oaDimensions = lookupParametersOADao.browseDimensions(company);
            List<LookupParameterDimensionDTO> dbDimensions = browseDimensions(company);

            addGroups(oaDimensions, company);

            List<LookupParameterDimensionDTO> dbDimensionsActive = new ArrayList<LookupParameterDimensionDTO>();
            List<LookupParameterDimensionDTO> dbDimensionsToDeleteTmp = new ArrayList<LookupParameterDimensionDTO>();

            setStatus(oaDimensions, dbDimensions, dbDimensionsActive, dbDimensionsToDeleteTmp);
            removeFromDb(dbDimensions, dbDimensionsToDeleteTmp);
            dbDimensions.removeAll(dbDimensionsActive);

            List<LookupParameterDimensionDTO> dimensionsToInsert = new ArrayList<LookupParameterDimensionDTO>();
            List<LookupParameterDimensionDTO> dimensionsToUpdate = new ArrayList<LookupParameterDimensionDTO>();
            dimensionsToUpdate.addAll(dbDimensions); // suspended parameters

            classifyUpdateOrInsert(company, oaDimensions, dbDimensionsActive, dimensionsToInsert, dimensionsToUpdate);

            if (dimensionsToInsert.size() > 0) {
                insertDimensions(dimensionsToInsert);
            }
            if (dimensionsToUpdate.size() > 0) {
                updateDimensions(dimensionsToUpdate);
            }

            return lookupParametersDimensionDao.browseDimensions(company);
        } catch (DaoException e) {
            // return new ArrayList<LookupParameterDimensionDTO>();
            return null;
        }
    }

    private void addGroups(List<LookupDimensionOADTO> oaDimensions, int company) {
        int oaDimSize = oaDimensions.size();
        for (int i = 1; i < oaDimSize - 1; i++) {
            if (i < 2) {
                LookupDimensionOADTO dimHierarchyGroup = new LookupDimensionOADTO();
                // dimHierarchyGroup.setDescription(oaDimensions.get(0).getDescription());
                dimHierarchyGroup.setGroup(oaDimensions.get(0).getGroup());
                dimHierarchyGroup.setLeaf(false);
                //dimHierarchyGroup.setParent("parent" + 0);
                dimHierarchyGroup.setStatus(0);
                dimHierarchyGroup.setVisId(company+ "/1-cc-"+oaDimensions.get(0).getGroup());
                oaDimensions.add(0, dimHierarchyGroup);
                oaDimSize++;
            }
            if (!oaDimensions.get(i).getGroup().equals(oaDimensions.get(i - 1).getGroup())) {
                LookupDimensionOADTO dimHierarchyGroup = new LookupDimensionOADTO();
                // dimHierarchyGroup.setDescription(oaDimensions.get(i).getDescription());
                dimHierarchyGroup.setGroup(oaDimensions.get(i).getGroup());
                dimHierarchyGroup.setLeaf(false);
                //dimHierarchyGroup.setParent("parent" + i);
                dimHierarchyGroup.setStatus(0);
                dimHierarchyGroup.setVisId(company+ "/1-cc-" + oaDimensions.get(i).getGroup());
                if (i != 1) {
                    oaDimensions.add(i, dimHierarchyGroup);
                    oaDimSize++;
                }

            }

        }
    }

    private void setStatus(List<LookupDimensionOADTO> oaDimensions, List<LookupParameterDimensionDTO> dbDimensions, List<LookupParameterDimensionDTO> dbDimensionsActive, List<LookupParameterDimensionDTO> dbDimensionsToDeleteTmp) {
        for (LookupParameterDimensionDTO dbDimension: dbDimensions) {
            dbDimension.setRowIndex(-1);
            dbDimension.setStatus(LookupParameterStatus.DELETED);
            oa: for (LookupDimensionOADTO oaDimension: oaDimensions) {
                if (dbDimension.getCostCentre().equals(oaDimension.getVisId()) && dbDimension.getGroup().equalsIgnoreCase(oaDimension.getGroup())) {
                    if (oaDimension.getStatus() == 1) {
                        dbDimension.setStatus(LookupParameterStatus.SUSPENDED);
                    } else if (oaDimension.getStatus() == 0 || oaDimension.getStatus() == -1) {
                        dbDimension.setStatus(LookupParameterStatus.ACTIVE);
                    }
                    dbDimensionsActive.add(dbDimension);
                    break oa;
                }
            }
            if (dbDimension.getStatus() == LookupParameterStatus.DELETED) {
                dbDimensionsToDeleteTmp.add(dbDimension);
            }
        }
    }

    private void classifyUpdateOrInsert(int company, List<LookupDimensionOADTO> oaDimensions, List<LookupParameterDimensionDTO> dbDimensionsActive, List<LookupParameterDimensionDTO> dimensionsToInsert, List<LookupParameterDimensionDTO> dimensionsToUpdate) {
        oa: for (int i = 0; i < oaDimensions.size(); i++) {
            LookupDimensionOADTO oaDimension = oaDimensions.get(i);
            // oaDimension.isLeaf();

            for (LookupParameterDimensionDTO dbParameterActive: dbDimensionsActive) {
                if (dbParameterActive.getCostCentre().equals(oaDimension.getVisId()) && dbParameterActive.getGroup().equalsIgnoreCase(oaDimension.getGroup())) {
                    dbParameterActive.setRowIndex(i);
                    dimensionsToUpdate.add(dbParameterActive);
                    continue oa;
                }
            }

            LookupParameterDimensionDTO dimension = new LookupParameterDimensionDTO();
            dimension.setGroup(oaDimension.getGroup());
            dimension.setCompany(company);
            dimension.setCostCentre(oaDimension.getVisId());
            dimension.setRowIndex(i);
            dimension.setLeaf(oaDimension.isLeaf());
           // dimension.setParent

            if (oaDimension.getStatus() == 1) {
                dimension.setStatus(LookupParameterStatus.SUSPENDED);

            } else if (oaDimension.getStatus() == 0 || oaDimension.getStatus() == -1) {
                dimension.setStatus(LookupParameterStatus.ACTIVE);
            }

            dimensionsToInsert.add(dimension);
        }
    }

    private void removeFromDb(List<LookupParameterDimensionDTO> dbDimensions, List<LookupParameterDimensionDTO> dbDimensionsToDeleteTmp) {
        List<LookupParameterDimensionDTO> dbDimensionsToDelete = new ArrayList<LookupParameterDimensionDTO>();
        for (LookupParameterDimensionDTO dbDimToDelTmp: dbDimensionsToDeleteTmp) {
            for (LookupParameterDimensionDTO dbDimension: dbDimensions) {
                if (dbDimToDelTmp.getCompany() == dbDimension.getCompany() && dbDimToDelTmp.getCostCentre().equalsIgnoreCase(dbDimension.getCostCentre()) && !(dbDimToDelTmp.getGroup().equalsIgnoreCase(dbDimension.getGroup()))) {
                    dbDimensionsToDelete.add(dbDimToDelTmp);
                }
            }
        }
        if (dbDimensionsToDelete.size() > 0) {
            deleteDimensions(dbDimensionsToDelete);
        }
    }

    private void updateDimensions(List<LookupParameterDimensionDTO> parameters) {
        try {
            lookupParametersDimensionDao.updateDimensions(parameters);
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private List<LookupParameterDimensionDTO> insertDimensions(List<LookupParameterDimensionDTO> parameters) {
        try {
            lookupParametersDimensionDao.insertDimensions(parameters);
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return parameters;
    }

    private void deleteDimensions(List<LookupParameterDimensionDTO> parameters) {
        try {
            lookupParametersDimensionDao.deleteDimensions(parameters);
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
