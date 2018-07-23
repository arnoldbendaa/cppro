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
package com.softproideas.app.admin.modelmappings.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.swing.tree.MutableTreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.globalmapping2.extsys.ExternalSystem;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger;
import com.cedar.cp.api.model.globalmapping2.extsys.FinancePeriod;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.datatype.DataTypesForImpExpELO;
import com.cedar.cp.dto.model.globalmapping2.AllGlobalMappedModels2ELO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2EditorSessionCSO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2EditorSessionSSO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2Impl;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.dto.model.mapping.AllMappedModelsELO;
import com.cedar.cp.dto.model.mapping.MappedModelEditorSessionSSO;
import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionServer;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionServer;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2EditorSessionSEJB;
import com.cedar.cp.impl.base.CPConnectionImpl;
import com.softproideas.app.admin.datatypes.model.DataTypeDetailsDTO;
import com.softproideas.app.admin.externalsystems.mapper.ExternalSystemMapper;
import com.softproideas.app.admin.externalsystems.model.ExternalSystemDTO;
import com.softproideas.app.admin.modelmappings.mapper.ModelMappingsMapper;
import com.softproideas.app.admin.modelmappings.mapper.ModelMappingsMapperGlobal;
import com.softproideas.app.admin.modelmappings.model.CompanyDTO;
import com.softproideas.app.admin.modelmappings.model.DataTypesTreeNodeDTO;
import com.softproideas.app.admin.modelmappings.model.DimensionAndHierarchiesNodeDTO;
import com.softproideas.app.admin.modelmappings.model.DimensionElementNodeDTO;
import com.softproideas.app.admin.modelmappings.model.DimensionRequestDTO;
import com.softproideas.app.admin.modelmappings.model.ExtSysDataTypesTreeNode;
import com.softproideas.app.admin.modelmappings.model.ExtSysTreeNode;
import com.softproideas.app.admin.modelmappings.model.ExternalRequestDTO;
import com.softproideas.app.admin.modelmappings.model.LedgerDTO;
import com.softproideas.app.admin.modelmappings.model.LedgersRequestDTO;
import com.softproideas.app.admin.modelmappings.model.ModelSuggestedAndDimensionsDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedCalendarDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDetailsDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.model.tree.NodeStaticDTO;

/**
 * <p>This service is aimed to take care of model mapping CRUD operations and import task.</p>
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("modelMappingsService")
public class ModelMappingsServiceImpl implements ModelMappingsService {

    private static Logger logger = LoggerFactory.getLogger(ModelMappingsServiceImpl.class);
    GlobalMappedModel2EditorSessionSEJB server = new GlobalMappedModel2EditorSessionSEJB();
    @Autowired
    CPContextHolder cpContextHolder;

    @Override
    public List<MappedModelDTO> browseMappedModels() throws ServiceException {
        AllMappedModelsELO allMappedModels = cpContextHolder.getListSessionServer().getAllMappedModels();
        AllGlobalMappedModels2ELO allGlobalMappedModels = cpContextHolder.getListSessionServer().getAllGlobalMappedModels2();
        List<MappedModelDTO> mappedModels = ModelMappingsMapperGlobal.mapAllMappedModelsELO(allMappedModels, allGlobalMappedModels);
        return mappedModels;
    }

    @Override
    public ResponseMessage delete(int mappedModelId) throws ServiceException {
        MappedModelPK mappedModelPK = new MappedModelPK(mappedModelId);
        try {
            MappedModelEditorSessionServer server = cpContextHolder.getMappedModelEditorSessionServer();
            server.delete(mappedModelPK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (ValidationException e) {
            logger.error("Validation error during dalete model mapping with Id =" + mappedModelId + "!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during dalete model mapping with Id =" + mappedModelId + "!");
            throw new ServiceException("Error during dalete model mapping with Id =" + mappedModelId + "!", e);
        }
    }
    
    @Override
    public ResponseMessage deleteGlobal(int mappedModelId) throws ServiceException {
        GlobalMappedModel2PK globalMappedModel2PK = new GlobalMappedModel2PK(mappedModelId);
        try {
            GlobalMappedModel2EditorSessionServer server = cpContextHolder.getGlobalMappedModel2EditorSessionServer();
            server.delete(globalMappedModel2PK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (ValidationException e) {
            logger.error("Validation error during dalete global model mapping with Id =" + mappedModelId + "!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during dalete global model mapping with Id =" + mappedModelId + "!");
            throw new ServiceException("Error during dalete model mapping with Id =" + mappedModelId + "!", e);
        }
    }


    
    @Override
    public int issueModelImportTask(boolean safeMode, int mappedModelId) throws ServiceException {
        try {
            MappedModelEditorSessionServer server = cpContextHolder.getMappedModelEditorSessionServer();
            int[] mappedModelIds = new int[] { mappedModelId };
            int taskId;
            taskId = server.issueModelImportTask(safeMode, mappedModelIds);
            return taskId;
        } catch (Exception e) {
            logger.error("Error during issueModelImportTask");
            throw new ServiceException("Error during issueModelImportTask with key =" + mappedModelId + "!", e);
        }
    }
    
    @Override
    public int issueModelImportTaskGlobal(boolean safeMode, int mappedModelId) throws ServiceException {
        try {
            GlobalMappedModel2EditorSessionServer server = cpContextHolder.getGlobalMappedModel2EditorSessionServer();
            int[] mappedModelIds = new int[] { mappedModelId };
            int taskId;
            taskId = server.issueModelImportTask(safeMode, mappedModelIds);
            return taskId;
        } catch (Exception e) {
            logger.error("Error during issueModelImportTask");
            throw new ServiceException("Error during issueModelImportTask with key =" + mappedModelId + "!", e);
        }
    }
    @Override
    public MappedModelDetailsDTO fetchMappedModelDetails(int mappedModelId) throws ServiceException {
//        GlobalMappedModel2EditorSessionServer server = cpContextHolder.getGlobalMappedModel2EditorSessionServer();
        GlobalMappedModel2Impl mappedModelImpl;
        MappedModelDetailsDTO mappedModelDetails;
        try {
            mappedModelImpl = getMappedModelDetailsFromServerGlobal(server, mappedModelId);
            mappedModelDetails = ModelMappingsMapperGlobal.mapMappedModelImpl(mappedModelId, mappedModelImpl);
        } catch (ValidationException e) {
            mappedModelDetails = new MappedModelDetailsDTO();
            mappedModelDetails.setValidationError(e.getMessage());
        }
        return mappedModelDetails;
    }

    @Override
    public List<CompanyDTO> browseExternalCompanies(ExternalRequestDTO externalRequest) throws ServiceException {
        ExternalSystemDTO externalSystemDTO = externalRequest.getExternalSystem();
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany> externalCompanies = getExternalCompaniesGlobal(externalSystemDTO);

        List<CompanyDTO> companies = ModelMappingsMapperGlobal.mapFinanceCompany(externalCompanies);
        return companies;
    }

    @Override
    public List<LedgerDTO> browseExternalLedgersGlobal(LedgersRequestDTO ledgerRequest) throws ServiceException {
        List<LedgerDTO> ledgers = new ArrayList<LedgerDTO>();

        List<CompanyDTO> selectedCompanies = ledgerRequest.getCompanies();
        ExternalSystemDTO externalSystemDTO = ledgerRequest.getExternalSystem();
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany> externalCompanies = getExternalCompaniesGlobal(externalSystemDTO);

        // External companies
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany externalCompany: externalCompanies) {
            MappingKeyImpl key = (MappingKeyImpl) externalCompany.getEntityRef().getPrimaryKey();
            Integer externalCompanyId = Integer.parseInt((String) key.get(0));
            // Selected companies
            for (CompanyDTO selectedCompany: selectedCompanies) {
                if (externalCompanyId == selectedCompany.getCompanyId()) {
                    List<FinanceLedger> externalLedgers = externalCompany.getFinanceLedgers();

                    // External ledgers
                    for (FinanceLedger externalLedger: externalLedgers) {
                        String externalLedgerVisId = (String) ((MappingKeyImpl) externalLedger.getEntityRef().getPrimaryKey()).get(0);
                        String externalLedgerDescription = externalLedger.getEntityRef().getDisplayText();

                        // Available ledgers
                        boolean foundLedger = false;
                        for (LedgerDTO ledger: ledgers) {
                            if (externalLedgerVisId.equals(ledger.getLedgerVisId())) {
                                List<Integer> companies = ledger.getCompanies();
                                if (!companies.contains(externalCompanyId)) {
                                    companies.add(externalCompanyId);
                                }
                                foundLedger = true;
                                break;
                            }
                        }
                        if (!foundLedger) {
                            LedgerDTO ledger = new LedgerDTO();
                            ledger.setLedgerVisId(externalLedgerVisId);
                            ledger.setLedgerDescription(externalLedgerDescription);
                            List<Integer> companies = new ArrayList<Integer>();
                            companies.add(externalCompanyId);
                            ledger.setCompanies(companies);
                            ledgers.add(ledger);
                        }
                    }
                    break;
                }
            }
        }
        return ledgers;
    }

    @Override
    public List<LedgerDTO> browseExternalLedgers(LedgersRequestDTO ledgerRequest) throws ServiceException {
        List<LedgerDTO> ledgers = new ArrayList<LedgerDTO>();

        List<CompanyDTO> selectedCompanies = ledgerRequest.getCompanies();
        ExternalSystemDTO externalSystemDTO = ledgerRequest.getExternalSystem();
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany> externalCompanies = getExternalCompaniesGlobal(externalSystemDTO);

        // External companies
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany externalCompany: externalCompanies) {
            MappingKeyImpl key = (MappingKeyImpl) externalCompany.getEntityRef().getPrimaryKey();
            Integer externalCompanyId = Integer.parseInt((String) key.get(0));
            // Selected companies
            for (CompanyDTO selectedCompany: selectedCompanies) {
                if (externalCompanyId == selectedCompany.getCompanyId()) {
                    List<FinanceLedger> externalLedgers = externalCompany.getFinanceLedgers();

                    // External ledgers
                    for (FinanceLedger externalLedger: externalLedgers) {
                        String externalLedgerVisId = (String) ((MappingKeyImpl) externalLedger.getEntityRef().getPrimaryKey()).get(0);
                        String externalLedgerDescription = externalLedger.getEntityRef().getDisplayText();

                        // Available ledgers
                        boolean foundLedger = false;
                        for (LedgerDTO ledger: ledgers) {
                            if (externalLedgerVisId.equals(ledger.getLedgerVisId())) {
                                List<Integer> companies = ledger.getCompanies();
                                if (!companies.contains(externalCompanyId)) {
                                    companies.add(externalCompanyId);
                                }
                                foundLedger = true;
                                break;
                            }
                        }
                        if (!foundLedger) {
                            LedgerDTO ledger = new LedgerDTO();
                            ledger.setLedgerVisId(externalLedgerVisId);
                            ledger.setLedgerDescription(externalLedgerDescription);
                            List<Integer> companies = new ArrayList<Integer>();
                            companies.add(externalCompanyId);
                            ledger.setCompanies(companies);
                            ledgers.add(ledger);
                        }
                    }
                    break;
                }
            }
        }
        return ledgers;
    }

    @Override
    public ModelSuggestedAndDimensionsDTO fetchModelSuggestedAndDimensions(DimensionRequestDTO dimensionRequest, HttpSession session) throws ServiceException {
        ModelSuggestedAndDimensionsDTO modelSuggestedAndDimensionsDTO = new ModelSuggestedAndDimensionsDTO();
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger = getExternalLedger(dimensionRequest);
        // SuggestedModelVisId
        com.cedar.cp.api.model.mapping.extsys.FinanceCompany externalCompany = externalLedger.getFinanceCompany();
        String suggestedModelVisId = externalCompany.getSuggestedCPModelVisId();
        modelSuggestedAndDimensionsDTO.setSuggestedModelVisId(suggestedModelVisId);
        String suggestedModelDescription = externalCompany.getEntityRef().toString();
        modelSuggestedAndDimensionsDTO.setSuggestedModelDescription(suggestedModelDescription);

        // External calendar
        List<com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear> calendarYear = externalCompany.getFinanceCalendarYears();
        com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear financeCalendarYear = getLastYear(calendarYear);
        modelSuggestedAndDimensionsDTO.setLastExternalYear(financeCalendarYear.getYear());

        // External dimensions
        List<com.cedar.cp.api.model.mapping.extsys.FinanceDimension> externalDimensions = externalLedger.getFinanceDimensions();
        for (com.cedar.cp.api.model.mapping.extsys.FinanceDimension externalDimension: externalDimensions) {
            // externalDimension.getFinanceDimensionElementGroups().get(0).
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_ACCOUNT) {
                DimensionAndHierarchiesNodeDTO nodeRoot = new DimensionAndHierarchiesNodeDTO();
                String rootId = (String) ((MappingKeyImpl) externalDimension.getEntityRef().getPrimaryKey()).get(0);
                String rootVisId = (String) (externalDimension.getEntityRef().getNarrative());
                nodeRoot.setText(rootVisId);
                nodeRoot.setId(rootId);
                nodeRoot.setStateOpened(true);
                nodeRoot.setStateDisabled(true);
                nodeRoot.setResponsibilityHierarchy(false);
                List<com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy> externalHierarchies = externalDimension.getFinanceHierarchies();
                List<NodeStaticDTO> children = ModelMappingsMapperGlobal.mapExternalHierarchies(externalHierarchies);
                nodeRoot.setChildren(children);
                modelSuggestedAndDimensionsDTO.setAccountDimension(nodeRoot);

            } else if (externalDimension.getDimensionType() == FinanceDimension.TYPE_BUSINESS) {
                DimensionAndHierarchiesNodeDTO nodeRoot = new DimensionAndHierarchiesNodeDTO();
                String rootId = (String) ((MappingKeyImpl) externalDimension.getEntityRef().getPrimaryKey()).get(0);
                String rootVisId = (String) (externalDimension.getEntityRef().getNarrative());
                nodeRoot.setText(rootVisId);
                nodeRoot.setId(rootId);
                nodeRoot.setStateOpened(true);
                nodeRoot.setStateDisabled(true);
                nodeRoot.setDescription(rootVisId);
                nodeRoot.setResponsibilityHierarchy(false);
                nodeRoot.setVisId(suggestedModelVisId + externalDimension.getSuggestedCPVisId());
                List<com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy> externalHierarchies = externalDimension.getFinanceHierarchies();
                List<NodeStaticDTO> children = ModelMappingsMapperGlobal.mapExternalHierarchies(externalHierarchies);
                nodeRoot.setChildren(children);
                modelSuggestedAndDimensionsDTO.setBusinessDimension(nodeRoot);
            }
        }

        session.setAttribute("globalExternalLedger", externalLedger);
        return modelSuggestedAndDimensionsDTO;
    }

    @Override
    public ModelSuggestedAndDimensionsDTO fetchModelSuggestedAndDimensionsGlobal(DimensionRequestDTO dimensionRequest, HttpSession session) throws ServiceException {
        ModelSuggestedAndDimensionsDTO modelSuggestedAndDimensionsDTO = new ModelSuggestedAndDimensionsDTO();
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger = getExternalLedgerGlobal(dimensionRequest);
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany externalCompany = externalLedger.getFinanceCompanies().get(0);
        // SuggestedModelVisId
        String suggestedModelVisId = externalCompany.getSuggestedCPModelVisId();
        modelSuggestedAndDimensionsDTO.setSuggestedModelVisId(suggestedModelVisId);
        modelSuggestedAndDimensionsDTO.setSuggestedModelDescription("Global Hierarchy");

        // External calendar
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear> calendarYear = externalLedger.getFinanceCalendarYears();
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear financeCalendarYear = getLastYearGlobal(calendarYear);
        modelSuggestedAndDimensionsDTO.setLastExternalYear(financeCalendarYear.getYear());

        // External dimensions
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension> externalDimensions = externalLedger.getFinanceDimensions();
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension externalDimension: externalDimensions) {
            // externalDimension.getFinanceDimensionElementGroups().get(0).
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_ACCOUNT) {
                DimensionAndHierarchiesNodeDTO nodeRoot = new DimensionAndHierarchiesNodeDTO();
                String rootId = (String) ((MappingKeyImpl) externalDimension.getEntityRef().getPrimaryKey()).get(0);
                String rootVisId = (String) (externalDimension.getEntityRef().getNarrative());
                nodeRoot.setText(rootVisId);
                nodeRoot.setId(rootId);
                nodeRoot.setStateOpened(true);
                nodeRoot.setStateDisabled(true);
                nodeRoot.setResponsibilityHierarchy(false);
                List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy> externalHierarchies = externalDimension.getFinanceHierarchies();
                List<NodeStaticDTO> children = ModelMappingsMapperGlobal.mapExternalHierarchiesGlobal(externalHierarchies);
                nodeRoot.setChildren(children);
                // ExtSysTreeNode extSysTreeNode = new ExtSysTreeNode((MutableTreeNode) null, externalDimension);
                // session.setAttribute("extSysTreeNodeAccouts", extSysTreeNode);
                modelSuggestedAndDimensionsDTO.setAccountDimension(nodeRoot);
            } else if (externalDimension.getDimensionType() == FinanceDimension.TYPE_BUSINESS) {
                // ExtSysTreeNode extSysTreeNode = new ExtSysTreeNode((MutableTreeNode) null, externalDimension);
                // session.setAttribute("extSysTreeNodeBusiness", extSysTreeNode);
                DimensionAndHierarchiesNodeDTO nodeRoot = new DimensionAndHierarchiesNodeDTO();
                String rootId = (String) ((MappingKeyImpl) externalDimension.getEntityRef().getPrimaryKey()).get(0);
                String rootVisId = (String) (externalDimension.getEntityRef().getNarrative());
                nodeRoot.setText(rootVisId);
                nodeRoot.setId(rootId);
                nodeRoot.setStateOpened(true);
                nodeRoot.setStateDisabled(true);
                nodeRoot.setDescription(rootVisId);
                nodeRoot.setResponsibilityHierarchy(false);
                nodeRoot.setVisId(suggestedModelVisId + externalDimension.getSuggestedCPVisId());
                List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy> externalHierarchies = externalDimension.getFinanceHierarchies();
                List<NodeStaticDTO> children = ModelMappingsMapperGlobal.mapExternalHierarchiesGlobal(externalHierarchies);
                nodeRoot.setChildren(children);
                modelSuggestedAndDimensionsDTO.setBusinessDimension(nodeRoot);
            } else if (externalDimension.getDimensionType() == FinanceDimension.TYPE_CALENDAR) {

            }
        }
        session.setAttribute("globalExternalLedger", externalLedger);
        return modelSuggestedAndDimensionsDTO;
    }

    private com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear getLastYearGlobal(List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear> calendarYears) {
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear lastYear = calendarYears.get(0);
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear year : calendarYears) {
            if (year.getYear() > lastYear.getYear()) {
                lastYear = year;
            }
        }
        return lastYear;
    }
    
    private com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear getLastYear(List<com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear> calendarYears) {
        com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear lastYear = calendarYears.get(0);
        for (com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear year : calendarYears) {
            if (year.getYear() > lastYear.getYear()) {
                lastYear = year;
            }
        }
        return lastYear;
    }

    private com.cedar.cp.api.model.globalmapping2.extsys.ExternalSystem getExternalSystemGlobal(com.cedar.cp.dto.model.globalmapping2.extsys.ExternalSystemImpl externalSystemImpl) {
        ExternalSystem externalSystem = com.cedar.cp.impl.model.globalmapping2.extsys.ProxyFactory.newProxy((CPConnectionImpl) cpContextHolder.getCPConnection(), externalSystemImpl);
        return externalSystem;
    }

    private com.cedar.cp.api.model.mapping.extsys.ExternalSystem getExternalSystem(com.cedar.cp.dto.model.mapping.extsys.ExternalSystemImpl externalSystemImpl) {
        com.cedar.cp.api.model.mapping.extsys.ExternalSystem externalSystem = com.cedar.cp.impl.model.mapping.extsys.ProxyFactory.newProxy((CPConnectionImpl) cpContextHolder.getCPConnection(), externalSystemImpl);
        return externalSystem;
    }

    private List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany> getExternalCompaniesGlobal(ExternalSystemDTO externalSystemDTO) {
        com.cedar.cp.dto.model.globalmapping2.extsys.ExternalSystemImpl externalSystemImpl = ExternalSystemMapper.mapExternalSystemDTOGlobal(externalSystemDTO);
        com.cedar.cp.api.model.globalmapping2.extsys.ExternalSystem externalSystem = getExternalSystemGlobal(externalSystemImpl);
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany> externalCompanies = externalSystem.getCompanies();
        return externalCompanies;
    }

    private List<com.cedar.cp.api.model.mapping.extsys.FinanceCompany> getExternalCompanies(ExternalSystemDTO externalSystemDTO) {
        com.cedar.cp.dto.model.mapping.extsys.ExternalSystemImpl externalSystemImpl = ExternalSystemMapper.mapExternalSystemDTO(externalSystemDTO);
        com.cedar.cp.api.model.mapping.extsys.ExternalSystem externalSystem = getExternalSystem(externalSystemImpl);
        List<com.cedar.cp.api.model.mapping.extsys.FinanceCompany> externalCompanies = externalSystem.getCompanies();
        return externalCompanies;
    }

    private com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger getExternalLedgerGlobal(DimensionRequestDTO dimensionRequest) {
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger financeLedger = null;
        String ledgerVisIdRequest = dimensionRequest.getLedger().getLedgerVisId();
        Integer companyIdRequest = dimensionRequest.getLedger().getCompanies().get(0); // get first company (no needs more)
        ExternalSystemDTO externalSystemDTORequest = dimensionRequest.getExternalSystem();

        // External companies
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany> externalCompanies = getExternalCompaniesGlobal(externalSystemDTORequest);

        List<Integer> selectedCompanies = dimensionRequest.getLedger().getCompanies();
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany> selectedExternalCompanies = new ArrayList<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany>();
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany externalCompany: externalCompanies) {
            MappingKeyImpl key = (MappingKeyImpl) externalCompany.getEntityRef().getPrimaryKey();
            Integer externalCompanyId = Integer.parseInt((String) key.get(0));
            if (selectedCompanies.contains(externalCompanyId)) {
                selectedExternalCompanies.add(externalCompany);
            }
        }
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany externalCompany: externalCompanies) {
            MappingKeyImpl key = (MappingKeyImpl) externalCompany.getEntityRef().getPrimaryKey();
            Integer externalCompanyId = Integer.parseInt((String) key.get(0));
            if (externalCompanyId.equals(companyIdRequest)) {
                // External ledgers
                List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger> externalLedgers = externalCompany.getFinanceLedgers();
                for (FinanceLedger externalLedger: externalLedgers) {
                    String externalLedgerVisId = (String) ((MappingKeyImpl) externalLedger.getEntityRef().getPrimaryKey()).get(0);
                    if (externalLedgerVisId.equals(ledgerVisIdRequest)) {
                        externalLedger.setFinanceCompanies(selectedExternalCompanies);
                        financeLedger = externalLedger;
                        break;
                    }
                }
                break;
            }
        }
        return financeLedger;
    }

    private com.cedar.cp.api.model.mapping.extsys.FinanceLedger getExternalLedger(DimensionRequestDTO dimensionRequest) {
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger financeLedger = null;
        String ledgerVisIdRequest = dimensionRequest.getLedger().getLedgerVisId();
        Integer companyIdRequest = dimensionRequest.getLedger().getCompanies().get(0); // get first company (no needs more)
        ExternalSystemDTO externalSystemDTORequest = dimensionRequest.getExternalSystem();

        // External companies
        List<com.cedar.cp.api.model.mapping.extsys.FinanceCompany> externalCompanies = getExternalCompanies(externalSystemDTORequest);

        for (com.cedar.cp.api.model.mapping.extsys.FinanceCompany externalCompany: externalCompanies) {
            MappingKeyImpl key = (MappingKeyImpl) externalCompany.getEntityRef().getPrimaryKey();
            Integer externalCompanyId = Integer.parseInt((String) key.get(0));
            if (externalCompanyId.equals(companyIdRequest)) {
                externalCompany.getFinanceCalendarYears();
                externalCompany.getSuggestedCPModelVisId();
                externalCompany.getEntityRef();
                // External ledgers
                List<com.cedar.cp.api.model.mapping.extsys.FinanceLedger> externalLedgers = externalCompany.getFinanceLedgers();
                for (com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger: externalLedgers) {
                    String externalLedgerVisId = (String) ((MappingKeyImpl) externalLedger.getEntityRef().getPrimaryKey()).get(0);
                    if (externalLedgerVisId.equals(ledgerVisIdRequest)) {
                        // externalLedger.setFinanceCompanies(selectedExternalCompanies);
                        financeLedger = externalLedger;
                        break;
                    }
                }
                break;
            }
        }
        return financeLedger;
    }

    @Override
    public synchronized List<DimensionElementNodeDTO> fetchDimensionElementsGlobal(HttpSession session, String dimension, String selectedHierarchies) {
        List<DimensionElementNodeDTO> nodes = new ArrayList<DimensionElementNodeDTO>();
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension> externalDimensions = externalLedger.getFinanceDimensions();
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension externalDimension: externalDimensions) {

            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_ACCOUNT && dimension.equals("account")) {
                DimensionElementNodeDTO nodeRoot = new DimensionElementNodeDTO();
                String rootId = (String) ((MappingKeyImpl) externalDimension.getEntityRef().getPrimaryKey()).get(0);
                String rootVisId = (String) (externalDimension.getEntityRef().getNarrative());
                nodeRoot.setText(rootVisId);
                nodeRoot.setId("dimension/" + rootId);
                nodeRoot.setStateOpened(true);
                nodeRoot.setStateDisabled(true);
                nodeRoot.setChildren(true);
                nodes.add(nodeRoot);
            }
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_BUSINESS && dimension.equals("business")) {
                DimensionElementNodeDTO nodeRoot = new DimensionElementNodeDTO();
                String rootId = (String) ((MappingKeyImpl) externalDimension.getEntityRef().getPrimaryKey()).get(0);
                String rootVisId = (String) (externalDimension.getEntityRef().getNarrative());
                nodeRoot.setText(rootVisId);
                nodeRoot.setId("dimension/" + rootId);
                nodeRoot.setStateOpened(true);
                nodeRoot.setStateDisabled(true);
                nodeRoot.setChildren(true);
                nodes.add(nodeRoot);
            }
        }

        session.setAttribute("globalExternalLedger", externalLedger);
        return nodes;
    }

    @Override
    public synchronized List<DimensionElementNodeDTO> fetchDimensionElements(HttpSession session, String dimension, String selectedHierarchies) {
        List<DimensionElementNodeDTO> nodes = new ArrayList<DimensionElementNodeDTO>();
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        List<com.cedar.cp.api.model.mapping.extsys.FinanceDimension> externalDimensions = externalLedger.getFinanceDimensions();
        for (com.cedar.cp.api.model.mapping.extsys.FinanceDimension externalDimension: externalDimensions) {

            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_ACCOUNT && dimension.equals("account")) {
                DimensionElementNodeDTO nodeRoot = new DimensionElementNodeDTO();
                String rootId = (String) ((MappingKeyImpl) externalDimension.getEntityRef().getPrimaryKey()).get(0);
                String rootVisId = (String) (externalDimension.getEntityRef().getNarrative());
                nodeRoot.setText(rootVisId);
                nodeRoot.setId("dimension/" + rootId);
                nodeRoot.setStateOpened(true);
                nodeRoot.setStateDisabled(true);
                nodeRoot.setChildren(true);
                nodes.add(nodeRoot);
            }
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_BUSINESS && dimension.equals("business")) {
                DimensionElementNodeDTO nodeRoot = new DimensionElementNodeDTO();
                String rootId = (String) ((MappingKeyImpl) externalDimension.getEntityRef().getPrimaryKey()).get(0);
                String rootVisId = (String) (externalDimension.getEntityRef().getNarrative());
                nodeRoot.setText(rootVisId);
                nodeRoot.setId("dimension/" + rootId);
                nodeRoot.setStateOpened(true);
                nodeRoot.setStateDisabled(true);
                nodeRoot.setChildren(true);
                nodes.add(nodeRoot);
            }
        }

        session.setAttribute("globalExternalLedger", externalLedger);
        return nodes;
    }

    @Override
    public List<DimensionElementNodeDTO> fetchExternalHierarchyGlobal(HttpSession session, String dimension, String selectedHierarchies) throws ServiceException {
        List<DimensionElementNodeDTO> childrenMapped = new ArrayList<DimensionElementNodeDTO>();
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension> externalDimensions = externalLedger.getFinanceDimensions();
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension externalDimension: externalDimensions) {
            externalDimension.getDimensionType();
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_ACCOUNT && dimension.equals("account")) {
                List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy> externalHierarchies = externalDimension.getFinanceHierarchies();
                childrenMapped = ModelMappingsMapperGlobal.mapHierarchyChildrenGlobal(externalHierarchies, selectedHierarchies, externalDimension);
                break;
            }
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_BUSINESS && dimension.equals("business")) {
                List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy> externalHierarchies = externalDimension.getFinanceHierarchies();
                childrenMapped = ModelMappingsMapperGlobal.mapHierarchyChildrenGlobal(externalHierarchies, selectedHierarchies, externalDimension);
                break;
            }
        }
        session.setAttribute("globalExternalLedger", externalLedger);
        return childrenMapped;
    }

    @Override
    public List<DimensionElementNodeDTO> fetchExternalHierarchy(HttpSession session, String dimension, String selectedHierarchies) throws ServiceException {
        List<DimensionElementNodeDTO> childrenMapped = new ArrayList<DimensionElementNodeDTO>();
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        List<com.cedar.cp.api.model.mapping.extsys.FinanceDimension> externalDimensions = externalLedger.getFinanceDimensions();
        for (com.cedar.cp.api.model.mapping.extsys.FinanceDimension externalDimension: externalDimensions) {
            externalDimension.getDimensionType();
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_ACCOUNT && dimension.equals("account")) {
                List<com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy> externalHierarchies = externalDimension.getFinanceHierarchies();
                childrenMapped = ModelMappingsMapperGlobal.mapHierarchyChildren(externalHierarchies, selectedHierarchies, externalDimension);
                break;
            }
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_BUSINESS && dimension.equals("business")) {
                List<com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy> externalHierarchies = externalDimension.getFinanceHierarchies();
                childrenMapped = ModelMappingsMapperGlobal.mapHierarchyChildren(externalHierarchies, selectedHierarchies, externalDimension);
                break;
            }
        }
        session.setAttribute("globalExternalLedger", externalLedger);
        return childrenMapped;
    }

    @Override
    public List<DimensionElementNodeDTO> fetchExternalGroupGlobal(HttpSession session, String dimension, String selectedHierarchies, String groupId) throws ServiceException {
        List<DimensionElementNodeDTO> childrenMapped = new ArrayList<DimensionElementNodeDTO>();
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension> externalDimensions = externalLedger.getFinanceDimensions();
        for (FinanceDimension externalDimension: externalDimensions) {

            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_ACCOUNT && dimension.equals("account")) {
                ExtSysTreeNode extSysTreeNode = new ExtSysTreeNode((MutableTreeNode) null, externalDimension);
                Enumeration children = getChildren(extSysTreeNode, groupId);
                childrenMapped = ModelMappingsMapperGlobal.mapGroupChildren(children, groupId);
                break;
            }
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_BUSINESS && dimension.equals("business")) {
                ExtSysTreeNode extSysTreeNode = new ExtSysTreeNode((MutableTreeNode) null, externalDimension);
                Enumeration children = getChildren(extSysTreeNode, groupId);
                childrenMapped = ModelMappingsMapperGlobal.mapGroupChildren(children, groupId);
                break;
            }
        }
        session.setAttribute("globalExternalLedger", externalLedger);
        return childrenMapped;
    }

    @Override
    public List<DimensionElementNodeDTO> fetchExternalGroup(HttpSession session, String dimension, String selectedHierarchies, String groupId) throws ServiceException {
        List<DimensionElementNodeDTO> childrenMapped = new ArrayList<DimensionElementNodeDTO>();
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        List<com.cedar.cp.api.model.mapping.extsys.FinanceDimension> externalDimensions = externalLedger.getFinanceDimensions();
        for (com.cedar.cp.api.model.mapping.extsys.FinanceDimension externalDimension: externalDimensions) {

            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_ACCOUNT && dimension.equals("account")) {
                ExtSysTreeNode extSysTreeNode = new ExtSysTreeNode((MutableTreeNode) null, externalDimension);
                Enumeration children = getChildren(extSysTreeNode, groupId);
                childrenMapped = ModelMappingsMapperGlobal.mapGroupChildren(children, groupId);
                break;
            }
            if (externalDimension.getDimensionType() == FinanceDimension.TYPE_BUSINESS && dimension.equals("business")) {
                ExtSysTreeNode extSysTreeNode = new ExtSysTreeNode((MutableTreeNode) null, externalDimension);
                Enumeration children = getChildren(extSysTreeNode, groupId);
                childrenMapped = ModelMappingsMapperGlobal.mapGroupChildren(children, groupId);
                break;
            }
        }
        session.setAttribute("globalExternalLedger", externalLedger);
        return childrenMapped;
    }

    private Enumeration getChildren(ExtSysTreeNode extSysTreeNode, String groupId) {
        String[] groupIds = groupId.split("/");
        groupId = groupIds[1];
        groupIds = groupId.split(",");
        if (groupIds.length == 0) {
            groupIds[0] = groupId;
        }
        Enumeration children = extSysTreeNode.children();
        extSysTreeNode.getChildCount();
        for (int i = 0; i < groupIds.length; i++) {
            String id = groupIds[i];
            while (children.hasMoreElements()) {
                ExtSysTreeNode el = (ExtSysTreeNode) children.nextElement();
                Object obj = el.getUserObject();
                // global
                if (obj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup) {
                    com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup userObject = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup) obj;
                    String groupElementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(groupElementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement) {
                    com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement userObject = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement) obj;
                    String groupElementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(groupElementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy) {
                    com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy userObject = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy) obj;
                    String groupElementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(groupElementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement) {
                    com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement userObject = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement) obj;
                    String groupElementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(groupElementId)) {
                        children = el.children();
                        break;
                    }
                }
                // not global
                else if (obj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup) {
                    com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup userObject = (com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup) obj;
                    String groupElementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(groupElementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement) {
                    com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement userObject = (com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement) obj;
                    String groupElementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(groupElementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy) {
                    com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy userObject = (com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy) obj;
                    String groupElementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(groupElementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement) {
                    com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement userObject = (com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement) obj;
                    String groupElementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(groupElementId)) {
                        children = el.children();
                        break;
                    }
                }

            }

        }
        return children;
    }

    @Override
    public List<DataTypesTreeNodeDTO> fetchDataTypesGlobal(HttpSession session, String firstYear) throws ServiceException {
        List<DataTypesTreeNodeDTO> root = new ArrayList<DataTypesTreeNodeDTO>();
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        externalLedger.setSelectedDimensions(externalLedger.getFinanceDimensions());
        externalLedger.setFirstMappedYear(Integer.parseInt(firstYear));
        ExtSysDataTypesTreeNode extSysDataTypesTreeNode = new ExtSysDataTypesTreeNode((MutableTreeNode) null, externalLedger);
        root = ModelMappingsMapperGlobal.mapDatatypeRootGlobal(extSysDataTypesTreeNode);

        return root;
    }

    @Override
    public List<DataTypesTreeNodeDTO> fetchDataTypes(HttpSession session, String firstYear) throws ServiceException {
        List<DataTypesTreeNodeDTO> root = new ArrayList<DataTypesTreeNodeDTO>();
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        externalLedger.setSelectedDimensions(externalLedger.getFinanceDimensions());
        externalLedger.setFirstMappedYear(Integer.parseInt(firstYear));
        ExtSysDataTypesTreeNode extSysDataTypesTreeNode = new ExtSysDataTypesTreeNode((MutableTreeNode) null, externalLedger);
        root = ModelMappingsMapperGlobal.mapDatatypeRoot(extSysDataTypesTreeNode);

        return root;
    }

    private Enumeration getExtSysDataTypesTreeNodeGlobal(ExtSysDataTypesTreeNode extSysDataTypesTreeNode, String elId) {

        String[] ids = elId.split("/");
        elId = ids[1];
        ids = elId.split(",");
        if (ids.length == 0) {
            ids[0] = elId;
        }

        Enumeration children = extSysDataTypesTreeNode.children();
        for (int i = 1; i < ids.length; i++) {
            String id = ids[i];
            while (children.hasMoreElements()) {
                ExtSysDataTypesTreeNode el = (ExtSysDataTypesTreeNode) children.nextElement();
                Object obj = el.getUserObject();
                if (obj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) {
                    com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType userObject = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) obj;
                    String elementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(elementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) {
                    com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger userObject = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) obj;
                    String elementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(elementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) {
                    com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup userObject = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) obj;
                    String elementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(elementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) {
                    com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner userObject = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) obj;
                    String elementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(elementId)) {
                        children = el.children();
                        break;
                    }
                }

            }
        }

        return children;
    }

    private Enumeration getExtSysDataTypesTreeNode(ExtSysDataTypesTreeNode extSysDataTypesTreeNode, String elId) {

        String[] ids = elId.split("/");
        elId = ids[1];
        ids = elId.split(",");
        if (ids.length == 0) {
            ids[0] = elId;
        }

        Enumeration children = extSysDataTypesTreeNode.children();
        for (int i = 1; i < ids.length; i++) {
            String id = ids[i];
            while (children.hasMoreElements()) {
                ExtSysDataTypesTreeNode el = (ExtSysDataTypesTreeNode) children.nextElement();
                Object obj = el.getUserObject();
                if (obj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueType) {
                    com.cedar.cp.api.model.mapping.extsys.FinanceValueType userObject = (com.cedar.cp.api.model.mapping.extsys.FinanceValueType) obj;
                    String elementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(elementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceLedger) {
                    com.cedar.cp.api.model.mapping.extsys.FinanceLedger userObject = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) obj;
                    String elementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(elementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) {
                    com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup userObject = (com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) obj;
                    String elementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(elementId)) {
                        children = el.children();
                        break;
                    }
                } else if (obj instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) {
                    com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner userObject = (com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) obj;
                    String elementId = (String) ((MappingKeyImpl) userObject.getEntityRef().getPrimaryKey()).get(0);
                    if (id.equals(elementId)) {
                        children = el.children();
                        break;
                    }
                }

            }
        }

        return children;
    }

    @Override
    public List<DataTypesTreeNodeDTO> fetchChildrenGlobal(HttpSession session, String id) throws ServiceException {
        List<DataTypesTreeNodeDTO> childrens = new ArrayList<DataTypesTreeNodeDTO>();
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        externalLedger.setSelectedDimensions(externalLedger.getFinanceDimensions());
        externalLedger.setFirstMappedYear(2015); // TODO: czemu na sztywno rok?
        ExtSysDataTypesTreeNode extSysDataTypesTreeNode = new ExtSysDataTypesTreeNode((MutableTreeNode) null, externalLedger);
        Enumeration children = getExtSysDataTypesTreeNodeGlobal(extSysDataTypesTreeNode, id);
        childrens = ModelMappingsMapperGlobal.mapDatatypeChildrenGlobal(children, id);
        return childrens;
    }

    @Override
    public List<DataTypesTreeNodeDTO> fetchChildren(HttpSession session, String id) throws ServiceException {
        List<DataTypesTreeNodeDTO> childrens = new ArrayList<DataTypesTreeNodeDTO>();
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        externalLedger.setSelectedDimensions(externalLedger.getFinanceDimensions());
        externalLedger.setFirstMappedYear(2015); // TODO: czemu na sztywno rok?
        ExtSysDataTypesTreeNode extSysDataTypesTreeNode = new ExtSysDataTypesTreeNode((MutableTreeNode) null, externalLedger);
        Enumeration children = getExtSysDataTypesTreeNode(extSysDataTypesTreeNode, id);
        childrens = ModelMappingsMapperGlobal.mapDatatypeChildren(children, id);
        return childrens;
    }

    @Override
    public List<DataTypesTreeNodeDTO> fetchDataTypeLevel1Global(HttpSession session, String id) throws ServiceException {
        List<DataTypesTreeNodeDTO> childrens = new ArrayList<DataTypesTreeNodeDTO>();
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        externalLedger.setSelectedDimensions(externalLedger.getFinanceDimensions());
        externalLedger.setFirstMappedYear(2015); // TODO: czemu na sztywno rok?
        ExtSysDataTypesTreeNode extSysDataTypesTreeNode = new ExtSysDataTypesTreeNode((MutableTreeNode) null, externalLedger);
        childrens = ModelMappingsMapperGlobal.childrenLevel1(extSysDataTypesTreeNode, id);
        return childrens;
    }

    @Override
    public List<DataTypesTreeNodeDTO> fetchDataTypeLevel1(HttpSession session, String id) throws ServiceException {
        List<DataTypesTreeNodeDTO> childrens = new ArrayList<DataTypesTreeNodeDTO>();
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        externalLedger.setSelectedDimensions(externalLedger.getFinanceDimensions());
        externalLedger.setFirstMappedYear(2015); // TODO: czemu na sztywno rok?
        ExtSysDataTypesTreeNode extSysDataTypesTreeNode = new ExtSysDataTypesTreeNode((MutableTreeNode) null, externalLedger);
        childrens = ModelMappingsMapperGlobal.childrenLevel1(extSysDataTypesTreeNode, id);
        return childrens;
    }

    @Override
    public List<DataTypeDetailsDTO> fetchDataTypes() throws ServiceException {
        DataTypesForImpExpELO dataTypesForImplExp = this.cpContextHolder.getListSessionServer().getDataTypesForImpExp();
        List<DataTypeDetailsDTO> dataTypes = ModelMappingsMapperGlobal.mapDataTypesForImplExp(dataTypesForImplExp);
        return dataTypes;
    }

    @Override
    public ResponseMessage saveMappedModelDetailsGlobal(MappedModelDetailsDTO mappedModelDetails, HttpSession session) throws ServiceException {
//        GlobalMappedModel2EditorSessionServer server = cpContextHolder.getGlobalMappedModel2EditorSessionServer();
        GlobalMappedModel2Impl mappedModelImpl;
        ResponseMessage message;
        try {
            mappedModelImpl = getMappedModelDetailsFromServerGlobal(server, -1);
            com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
            mappedModelImpl = ModelMappingsMapperGlobal.mapNewMappedModelDetailsDTO(mappedModelDetails, mappedModelImpl, externalLedger);
            message = insertGlobal(mappedModelImpl, server);
        } catch (ValidationException e) {
            message = new ResponseMessage(false, e.getMessage());
        }
        return message;
    }

    @Override
    public ResponseMessage saveMappedModelDetails(MappedModelDetailsDTO mappedModelDetails, HttpSession session) throws ServiceException {
        MappedModelEditorSessionServer server = cpContextHolder.getMappedModelEditorSessionServer();
        MappedModelImpl mappedModelImpl = getMappedModelDetailsFromServer(server, -1);
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
        mappedModelImpl = ModelMappingsMapper.mapNewMappedModelDetailsDTO(mappedModelDetails, mappedModelImpl, externalLedger);
        ResponseMessage message = insert(mappedModelImpl, server);
        return message;
    }

    @Override
    public ResponseMessage updateMappedModelDetailsGlobal(MappedModelDetailsDTO mappedModelDetails, HttpSession session) throws ServiceException {
//        GlobalMappedModel2EditorSessionServer server = cpContextHolder.getGlobalMappedModel2EditorSessionServer();
        GlobalMappedModel2Impl mappedModelImpl;
        ResponseMessage message;
        try {
            mappedModelImpl = getMappedModelDetailsFromServerGlobal(server, mappedModelDetails.getMappedModelId());
            com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");
            mappedModelImpl = ModelMappingsMapperGlobal.mapOldMappedModelDetailsDTO(mappedModelDetails, mappedModelImpl, externalLedger);
            message = updateGlobal(mappedModelImpl, server);
        } catch (ValidationException e) {
            message = new ResponseMessage(false, e.getMessage());
        }
        return message;
    }

    @Override
    public ResponseMessage updateMappedModelDetails(MappedModelDetailsDTO mappedModelDetails, HttpSession session) throws ServiceException {
        MappedModelEditorSessionServer server = cpContextHolder.getMappedModelEditorSessionServer();
        MappedModelImpl mappedModelImpl = getMappedModelDetailsFromServer(server, mappedModelDetails.getMappedModelId());
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger externalLedger = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) session.getAttribute("globalExternalLedger");

        mappedModelImpl = ModelMappingsMapper.mapOldMappedModelDetailsDTO(mappedModelDetails, mappedModelImpl, externalLedger);
        ResponseMessage message = update(mappedModelImpl, server);
        return message;
    }

    private MappedModelImpl getMappedModelDetailsFromServer(MappedModelEditorSessionServer server, int mappedModelId) throws ServiceException {
        MappedModelEditorSessionSSO sso = null;
        try {
            if (mappedModelId != -1) {
                MappedModelPK mappedModelPK = new MappedModelPK(mappedModelId);
                sso = server.getItemData(mappedModelPK);
            } else {
                sso = server.getNewItemData();
            }
            MappedModelImpl mappedModelImpl = sso.getEditorData();
            return mappedModelImpl;
        } catch (ValidationException e) {
            logger.error("Unable to open mapped model with id=" + mappedModelId + "! \n " + e.getMessage());
            throw new ServiceException("Unable to open mapped model with id=" + mappedModelId + "! \n " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error during browsing mapped model with id=" + mappedModelId + "!");
            throw new ServiceException("Error during browsing mapped model with id=" + mappedModelId + "!", e);
        }
    }
    
    private GlobalMappedModel2Impl getMappedModelDetailsFromServerGlobal(GlobalMappedModel2EditorSessionSEJB server, int mappedModelId) throws ServiceException, ValidationException {
//    	GlobalMappedModel2EditorSessionServer
    	GlobalMappedModel2EditorSessionSSO sso = null;
        try {
            if (mappedModelId != -1) {
                GlobalMappedModel2PK mappedModelPK = new GlobalMappedModel2PK(mappedModelId);
                sso = server.getItemData(cpContextHolder.getUserId(),mappedModelPK);
            } else {
                sso = server.getNewItemData(cpContextHolder.getUserId());
            }
            GlobalMappedModel2Impl mappedModelImpl = sso.getEditorData();
            return mappedModelImpl;
        } catch (CPException e) {
            logger.error("Error during browsing mapped model with id=" + mappedModelId + "!");
            throw new ServiceException("Error during browsing mapped model with id=" + mappedModelId + "!", e);
        }
    }

    private ResponseMessage insertGlobal(GlobalMappedModel2Impl mappedModelImpl, GlobalMappedModel2EditorSessionSEJB server) throws ServiceException {
        try {
            server.insert(new GlobalMappedModel2EditorSessionCSO(cpContextHolder.getUserId(), mappedModelImpl));
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Error during insert Global Model Mapping");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            throw new ServiceException("Error during insert Global Model Mapping with key =" + mappedModelImpl.getModelVisId() + "!");
        }
    }

    private ResponseMessage insert(MappedModelImpl mappedModelImpl, MappedModelEditorSessionServer server) throws ServiceException {
        try {
            server.insert(mappedModelImpl);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Error during insert Model Mapping");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            throw new ServiceException("Error during insert Model Mapping with key =" + mappedModelImpl.getModelVisId() + "!");
        }
    }

    private ResponseMessage updateGlobal(GlobalMappedModel2Impl mappedModelImpl, GlobalMappedModel2EditorSessionSEJB server) throws ServiceException {
        try {
            server.update(new GlobalMappedModel2EditorSessionCSO(cpContextHolder.getUserId(), mappedModelImpl));
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Error during update Global Model Mapping");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            throw new ServiceException("Error during update Global Model Mapping with key =" + mappedModelImpl.getModelVisId() + "!");
        }
    }

    private ResponseMessage update(MappedModelImpl mappedModelImpl, MappedModelEditorSessionServer server) throws ServiceException {
        try {
            server.update(mappedModelImpl);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Error during update Model Mapping");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            throw new ServiceException("Error during update Model Mapping with key =" + mappedModelImpl.getModelVisId() + "!");
        }
    }

	@Override
	public String getTaskStatus(String taskId) {
		// TODO Auto-generated method stub
		if(taskId.equals("0"))
			return "5";
		MappedModelEditorSessionServer server = cpContextHolder.getMappedModelEditorSessionServer();
		
		return server.getTaskStatus(taskId);
	}
	@Override
	public String getTaskTime(String taskId) {
		// TODO Auto-generated method stub
		if(taskId.equals("0"))
			return "5";
		MappedModelEditorSessionServer server = cpContextHolder.getMappedModelEditorSessionServer();
		
		return server.getTaskTime(taskId);
	}



}
