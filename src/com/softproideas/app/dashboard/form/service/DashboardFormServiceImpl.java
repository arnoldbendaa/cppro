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
package com.softproideas.app.dashboard.form.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.softproideas.app.core.workbook.model.FlatFormExtractor;
import com.softproideas.app.core.workbook.model.FlatFormExtractorDao;
import com.softproideas.app.dashboard.form.dao.FormDAO;
import com.softproideas.app.dashboard.form.model.DashboardDTO;
import com.softproideas.app.dashboard.form.model.HierarchyElement;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.util.WorkbookUtil;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingFunction;
import com.softproideas.util.validation.MappingValidator;

@Service("dashboardFormService")
public class DashboardFormServiceImpl implements DashboardFormService {

    @Autowired
    private FormDAO formDAO;

    @Autowired
    private CPContextHolder cpContextHolder;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private FlatFormExtractorDao flatFormExtractorDao;

    @Override
    public ResponseMessage insertOrUpdateFreeForm(DashboardDTO freeForm) throws DaoException, ValidationException {
        return formDAO.insertOrUpdateFreeForm(freeForm);
    }

    @Override
    public boolean deleteFreeForm(UUID id) throws DaoException {
        return formDAO.deleteFreeForm(id);
    }

    @Override
    public DashboardDTO getFreeFormByUUID(UUID id) throws DaoException, IOException, ClassNotFoundException {
        return formDAO.getFreeFormByUUID(id);
    }

    @Override
    public HierarchyElement getContextData(String structureElementVisId, int modelId) {
        return formDAO.getContextData(structureElementVisId, modelId);
    }

    @Override
    public Integer exchangeForModelId(Integer financeCubeId) {
        return formDAO.exchangeForModelId(financeCubeId);
    }

    @Override
    public WorkbookDTO sumWorkbooks(List<WorkbookDTO> listWorkbookDTO) {
        WorkbookDTO sumWorkbookDTO = listWorkbookDTO.get(0);

        for (int i = 1; i < listWorkbookDTO.size(); i++) {
            WorkbookDTO nextWorkbookDTO = listWorkbookDTO.get(i);

            List<WorksheetDTO> listWorksheet1 = sumWorkbookDTO.getWorksheets();
            List<WorksheetDTO> listWorksheet2 = nextWorkbookDTO.getWorksheets();

            for (int j = 0; j < listWorksheet1.size(); j++) {
                WorksheetDTO worksheet1 = listWorksheet1.get(j);
                WorksheetDTO worksheet2 = listWorksheet2.get(j);

                List<CellDTO> listCells1 = (List<CellDTO>) worksheet1.getCells();
                List<CellDTO> listCells2 = (List<CellDTO>) worksheet2.getCells();
                if (listCells1.size() != listCells2.size()) {
                    continue;
                }
                for (int k = 0; k < listCells1.size(); k++) {
                    CellExtendedDTO cell1 = (CellExtendedDTO) listCells1.get(k);
                    CellExtendedDTO cell2 = (CellExtendedDTO) listCells2.get(k);

                    if (cell1.getValue() != null || cell2.getValue() != null) {
                        if (cell1.getValue() == null) {
                            cell1.setValue(0.0);
                        }
                        if (cell2.getValue() == null) {
                            cell2.setValue(0.0);
                        }
                        if (cell1.getInputMapping().startsWith("cedar.cp.getCell(") && !cell1.getInputMapping().contains("dim2")) {
                            cell1.setValue(cell1.getValue() + cell2.getValue());
                        }
                    }
                }
            }

        }

        return sumWorkbookDTO;
    }

    public WorkbookDTO calculateWorkbook(WorkbookDTO workbookDTO, List<WorkbookDTO> listWorkbookToSubtract) {

        for (int i = 0; i < listWorkbookToSubtract.size(); i++) {
            WorkbookDTO nextWorkbookDTO = listWorkbookToSubtract.get(i);

            List<WorksheetDTO> listWorksheet1 = workbookDTO.getWorksheets();
            List<WorksheetDTO> listWorksheet2 = nextWorkbookDTO.getWorksheets();

            for (int j = 0; j < listWorksheet1.size(); j++) {
                WorksheetDTO worksheet1 = listWorksheet1.get(j);
                WorksheetDTO worksheet2 = listWorksheet2.get(j);

                List<CellDTO> listCells1 = (List<CellDTO>) worksheet1.getCells();
                List<CellDTO> listCells2 = (List<CellDTO>) worksheet2.getCells();
                if (listCells1.size() != listCells2.size()) {
                    continue;
                }
                for (int k = 0; k < listCells1.size(); k++) {
                    CellExtendedDTO cell1 = (CellExtendedDTO) listCells1.get(k);
                    CellExtendedDTO cell2 = (CellExtendedDTO) listCells2.get(k);

                    if (cell1.getValue() != null || cell2.getValue() != null) {
                        if (cell1.getValue() == null) {
                            cell1.setValue(0.0);
                        }
                        if (cell2.getValue() == null) {
                            cell2.setValue(0.0);
                        }
                        if (cell1.getInputMapping().startsWith("cedar.cp.getCell(") && !cell1.getInputMapping().contains("dim2")) {
                            cell1.setValue(cell1.getValue() - cell2.getValue());
                        }
                    }
                }
            }
        }

        return workbookDTO;
    }

    private WorkbookDTO setDateAndCloneWorkbook(int year, int month, WorkbookDTO workbook) throws CloneNotSupportedException {
        String date = composeDate(year, month);
        WorkbookDTO clone = (WorkbookDTO) workbook.clone();
        clone.getProperties().put(WorkbookProperties.DIMENSION_2_VISID.toString(), date);
        return clone;
    }

    private String composeDate(int year, int month) {
        String temp;
        if (month == 0) {
            temp = "/" + Integer.toString(year);
        } else {
            temp = "/" + Integer.toString(year) + "/" + month;
        }
        return temp;
    }

    private void removeQustionableDIM2FromInputMappings(WorkbookDTO workbook) {
        for (WorksheetDTO w: workbook.getWorksheets()) {
            for (CellDTO c: w.getCells()) {
                String m = c.getInputMapping();
                MappingValidator mv = new MappingValidator(m);
                if (mv.isValid() && mv.getFunction() == MappingFunction.GET_CELL) {
                    Map<String, String> args = mv.getListOfArguments();
                    String dim2 = args.get(MappingArguments.DIM2.toString());
                    if (dim2 != null && dim2.indexOf("?") > -1) {
                        args.remove(MappingArguments.DIM2.toString());
                        m = MappingValidator.buildMapping(MappingValidator.PREFIX, MappingFunction.GET_CELL, args);
                        c.setInputMapping(m);
                    }
                }
            }
        }
    }

    @Override
    public WorkbookDTO prepareWorkbookToSum(WorkbookDTO workbookStart) throws Exception {
        WorkbookUtil.convertWorkbookDTOToExtendedVersion(workbookStart);

        removeQustionableDIM2FromInputMappings(workbookStart);// podzielic na watki?

        String dateFrom = workbookStart.getProperties().get(WorkbookProperties.DIMENSION_2_VISID_FROM.toString());
        String dateTo = workbookStart.getProperties().get(WorkbookProperties.DIMENSION_2_VISID_TO.toString());

        Map<String, String> workbookMap = workbookStart.getProperties();
        for (Iterator<Map.Entry<String, String>> it = workbookMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = it.next();
            if (entry.getKey().equals(WorkbookProperties.DIMENSION_2_VISID_FROM.toString()) || entry.getKey().equals(WorkbookProperties.DIMENSION_2_VISID_TO.toString())) {
                it.remove();
            }
        }
        List<WorkbookDTO> workbooks = new ArrayList<WorkbookDTO>();
        List<WorkbookDTO> workbooksToSubtraction = new ArrayList<WorkbookDTO>();
        String[] parts;
        int monthFrom = 0;
        int yearFrom = 0;
        int monthTo = 0;
        int yearTo = 0;

        parts = dateFrom.split("/");// null pointer exception???
        if (parts.length == 3) {
            yearFrom = Integer.parseInt(parts[1]);
            monthFrom = Integer.parseInt(parts[2]);
        } else {
            yearFrom = Integer.parseInt(parts[1]);
        }
        parts = dateTo.split("/");
        if (parts.length == 3) {
            yearTo = Integer.parseInt(parts[1]);
            monthTo = Integer.parseInt(parts[2]);
        } else {
            yearTo = Integer.parseInt(parts[1]);
        }

        if (yearFrom == yearTo) { // ten sam rok
            if (monthFrom == 0 && monthTo == 0) {
                workbooks.add(setDateAndCloneWorkbook(yearFrom, 0, workbookStart));
                // addWorkbookToList(yearFrom, 0, workbookStart, workbookMap, workbooks);
            } else if (monthTo == 0 && monthFrom < 7) {
                workbooks.add(setDateAndCloneWorkbook(yearFrom, 0, workbookStart));
                for (int j = 1; j < monthFrom; j++) {
                    workbooksToSubtraction.add(setDateAndCloneWorkbook(yearFrom, j, workbookStart));
                }
            } else if (monthTo == 0) {
                for (int j = monthFrom; j <= 12; j++) {
                    workbooks.add(setDateAndCloneWorkbook(yearFrom, j, workbookStart));
                }
            } else if (monthTo >= monthFrom && monthTo - monthFrom > 6) {
                workbooks.add(setDateAndCloneWorkbook(yearFrom, 0, workbookStart));
                for (int j = monthTo + 1; j <= 12; j++) {
                    workbooksToSubtraction.add(setDateAndCloneWorkbook(yearFrom, j, workbookStart));
                }
            } else if (monthTo >= monthFrom) {
                for (int j = monthFrom; j <= monthTo; j++) {
                    workbooks.add(setDateAndCloneWorkbook(yearFrom, j, workbookStart));
                }
            } else {
                // throw new Exception("Incorrect months at summing workbooks");
            }
        } else if (yearFrom < yearTo) { // rozne lata
            for (int i = yearFrom; i <= yearTo; i++) {
                if (i == yearFrom) { // dla roku poczatkowego
                    if (monthFrom < 7) {
                        workbooks.add(setDateAndCloneWorkbook(i, 0, workbookStart));// podzielic na watki?
                        for (int j = 1; j < monthFrom; j++) {
                            workbooksToSubtraction.add(setDateAndCloneWorkbook(i, j, workbookStart));
                        }
                    } else {
                        for (int j = monthFrom; j <= 12; j++) {
                            workbooks.add(setDateAndCloneWorkbook(i, j, workbookStart));
                        }
                    }
                } else if (i == yearTo) { // dla roku koncowego
                    if (monthTo == 0) {// dla calego roku
                        workbooks.add(setDateAndCloneWorkbook(i, 0, workbookStart));
                    } else if (monthTo > 6) {//
                        workbooks.add(setDateAndCloneWorkbook(i, 0, workbookStart));
                        for (int j = monthTo + 1; j <= 12; j++) {
                            workbooksToSubtraction.add(setDateAndCloneWorkbook(i, j, workbookStart));
                        }
                    } else {
                        for (int j = 1; j <= monthTo; j++) {
                            workbooks.add(setDateAndCloneWorkbook(i, j, workbookStart));
                        }
                    }
                } else { // dla roku kazdego pomiedzy dwoma pierwszymi
                    workbooks.add(setDateAndCloneWorkbook(i, 0, workbookStart));
                }
            }
        } else {
            throw new Exception("Incorrect years at summing workbooks");
        }
        Map<Integer, Exception> fail = new HashMap<Integer, Exception>();
        Object lock = new Object();
        CPConnection connection = cpContextHolder.getCPConnection();
        if (taskExecutor == null) {
            int size = 0;
            for (WorkbookDTO workbook: workbooks) {
                addSynchronousTask(lock, fail, size, workbook, connection);
                size++;
            }
            for (WorkbookDTO workbook: workbooksToSubtraction) {
                addSynchronousTask(lock, fail, size, workbook, connection);
                size++;
            }
            while (true) {
                if (fail.size() >= size) {
                    break;
                }
                Thread.currentThread().sleep(100);
            }
            // check if all threads succeeded
            for (Exception e: fail.values()) {
                if (e != null) {
                    throw new Exception(e);
                }
            }
        } else {
            int size = 0;
            for (WorkbookDTO workbook: workbooks) {
                addAsynchronousTask(lock, fail, size, workbook, connection);
                size++;
            }
            for (WorkbookDTO workbook: workbooksToSubtraction) {
                addAsynchronousTask(lock, fail, size, workbook, connection);
                size++;
            }
            while (true) {
                if (fail.size() >= size) {
                    break;
                }
                Thread.currentThread().sleep(100);
            }
            // check if all threads succeeded
            for (Exception e: fail.values()) {
                if (e != null) {
                    throw new Exception(e);
                }
            }
        }
        WorkbookDTO workbookSum = sumWorkbooks(workbooks);
        WorkbookDTO workbookAfterSubtract = calculateWorkbook(workbookSum, workbooksToSubtraction);

        return workbookAfterSubtract;
    }

    private void addSynchronousTask(final Object lock, final Map<Integer, Exception> failed, final int index, final WorkbookDTO workbook, final CPConnection connection) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    synchronousWorkbookTest(workbook, connection);
                    synchronized (lock) {
                        failed.put(index, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    synchronized (lock) {
                        failed.put(index, e);
                    }
                }
            }
        };
        taskExecutor.execute(task);
    }

    private void addAsynchronousTask(final Object lock, final Map<Integer, Exception> failed, final int index, final WorkbookDTO workbook, final CPConnection connection) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    workbookTest(workbook, connection);
                    synchronized (lock) {
                        failed.put(index, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    synchronized (lock) {
                        failed.put(index, e);
                    }
                }
            }
        };
        taskExecutor.execute(task);
    }

    private WorkbookDTO workbookTest(WorkbookDTO workbookDTO, CPConnection connection) throws Exception {
        FlatFormExtractor flatFormExtractor = new FlatFormExtractor(connection, flatFormExtractorDao);
        flatFormExtractor.getValuesForMappings(workbookDTO, connection);
        return workbookDTO;
    }

    private WorkbookDTO synchronousWorkbookTest(WorkbookDTO workbookDTO, CPConnection connection) throws Exception {
        FlatFormExtractor flatFormExtractor = new FlatFormExtractor(connection, flatFormExtractorDao);
        flatFormExtractor.getValuesForMappings(workbookDTO, connection);
        return workbookDTO;
    }
}
