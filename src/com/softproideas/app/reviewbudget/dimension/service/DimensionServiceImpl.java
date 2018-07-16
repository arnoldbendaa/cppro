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
package com.softproideas.app.reviewbudget.dimension.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyEditor;
import com.cedar.cp.api.dimension.HierarchyEditorSession;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.ModelPK;
import com.softproideas.app.reviewbudget.dimension.mapper.DimensionMapper;
import com.softproideas.app.reviewbudget.dimension.model.DimensionDTO;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.dimension.model.HierarchyDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.TreeNodeDTO;
import com.softproideas.commons.model.enums.DimensionType;
import com.softproideas.commons.util.DimensionUtil;

/**
 * Service for managing dimensions. 2014 All rights reserved to Softpro Ideas
 * Group
 */
@Service("dimensionService")
public class DimensionServiceImpl implements DimensionService {

	private static Logger logger = LoggerFactory.getLogger(DimensionServiceImpl.class);

	@Autowired
	CPContextHolder cpContextHolder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.softproideas.spreadsheet.webapp.service.DimensionService#
	 * browseDimensions(com.softproideas.spreadsheet.webapp.model.enums.
	 * DimensionType, int, int)
	 */
	@Override
	public List<TreeNodeDTO> browseDimensions(DimensionType dimensionType, int budgetCycleId, int modelId)
			throws ServiceException {
		// EntityList dimensions =
		// cpContextHolder.getModelsProcess().getModelDimensions(modelId);
		// int[] dimTypes = new int[dimensions.getNumRows()];
		int[] dimTypes = new int[1];
		dimTypes[0] = dimensionType.getNumber();
		EntityList dimensionsList = browseDimensionList(modelId, dimTypes);

		List<TreeNodeDTO> dimensionTreeNodes = new ArrayList<TreeNodeDTO>();
		for (int i = 0; i < dimensionsList.getNumRows(); i++) {
			DimensionDTO dimension = DimensionMapper.mapToDimensionDTO(dimensionsList, i);
			List<TreeNodeDTO> dimensionChildren = new ArrayList<TreeNodeDTO>();

			EntityList hierList = (EntityList) dimensionsList.getValueAt(i, "Hierarchy");
			for (int j = 0; j < hierList.getNumRows(); ++j) {
				HierarchyDTO hierarchy = DimensionMapper.mapToHierarchyDTO(hierList, j);
				List<TreeNodeDTO> hierarchyChildren = new ArrayList<TreeNodeDTO>();

				EntityList elementsList = (EntityList) hierList.getValueAt(j, "StructureElement");
				for (int k = 0; k < elementsList.getNumRows(); ++k) {
					ElementDTO element = DimensionMapper.mapToElementDTO(elementsList, k);
					List<TreeNodeDTO> elementChildren = browseChildren(dimensionType, budgetCycleId, element);

					TreeNodeDTO treeNode = DimensionMapper.mapToTreeNodeDTO(element, elementChildren);
					hierarchyChildren.add(treeNode);
				}

				TreeNodeDTO hierarchyTreeNode = DimensionMapper.mapToTreeNodeDTO(hierarchy, hierarchyChildren);
				dimensionChildren.add(hierarchyTreeNode);
			}

			TreeNodeDTO dimensionTreeNode = DimensionMapper.mapToTreeNodeDTO(dimension, dimensionChildren);
			dimensionTreeNodes.add(dimensionTreeNode);
		}
		return dimensionTreeNodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.softproideas.spreadsheet.webapp.service.DimensionService#
	 * browseChildren(com.softproideas.spreadsheet.webapp.model.enums.
	 * DimensionType, int,
	 * com.softproideas.spreadsheet.webapp.model.dto.dimension.ElementDTO)
	 */
	@Override
	public List<TreeNodeDTO> browseChildren(DimensionType dimensionType, int budgetCycleId, ElementDTO parent)
			throws ServiceException {
		if (parent == null) {
			return null;
		}
		StructureElementPK primaryKey = new StructureElementPK(parent.getStructureId(), parent.getId());
		EntityList childrenList = browseChildrenList(dimensionType.getNumber(), budgetCycleId, primaryKey);

		List<TreeNodeDTO> treeNodes = new ArrayList<TreeNodeDTO>();
		for (int k = 0; k < childrenList.getNumRows(); ++k) {
			ElementDTO element = DimensionMapper.mapToElementDTO(childrenList, k);
			element.setSelectable(true);
			TreeNodeDTO treeNode = DimensionMapper.mapToTreeNodeDTO(element, null);
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.softproideas.spreadsheet.webapp.service.DimensionService#
	 * browseDataTypes()
	 */
	@Override
	public List<TreeNodeDTO> browseDataTypes() throws ServiceException {
		EntityList dataTypeList = browseDataTypeList();

		List<TreeNodeDTO> dataTypes = new ArrayList<TreeNodeDTO>();

		// indexes of subtypes (store only numbers)
		List<Integer> subTypesIndexes = new ArrayList<Integer>();

		List<TreeNodeDTO> subTypes = new ArrayList<TreeNodeDTO>();
		for (int i = 0; i < dataTypeList.getNumRows(); ++i) {

			Integer subTypeId = ((Integer) dataTypeList.getValueAt(i, "SubType")).intValue();
			int index = subTypesIndexes.indexOf(subTypeId);
			if (index == -1) {
				// there is no subTypeId stored in subTypeIndexes

				// creates branch (subtype), which stores data types by subtype
				index = subTypesIndexes.size();
				subTypesIndexes.add(subTypeId);

				ElementDTO subTypeElement = new ElementDTO();
				subTypeElement.setId(subTypeId);
				subTypeElement.setName(DimensionUtil.getSubTypeName(subTypeId));

				TreeNodeDTO subType = DimensionMapper.mapToTreeNodeDTO(subTypeElement, new ArrayList<TreeNodeDTO>());
				subTypes.add(subType);
			}

			// subtype where we add a one specific data type
			TreeNodeDTO subType = subTypes.get(index);

			// one data type
			ElementDTO elementDTO = DimensionMapper.mapDataTypeToElementDTO(dataTypeList, i);
			TreeNodeDTO treeNode = DimensionMapper.mapToTreeNodeDTO(elementDTO, null);

			subType.getChildren().add(treeNode);
		}

		// actually it is a fake tree node (first branch). it is not stored in
		// database.
		ElementDTO elementDTO = new ElementDTO();
		elementDTO.setName("All Data Types");
		elementDTO.setLeaf(false);
		elementDTO.setDescription("");

		TreeNodeDTO dataTypeTreeNode = DimensionMapper.mapToTreeNodeDTO(elementDTO, subTypes);

		dataTypes.add(dataTypeTreeNode);
		return dataTypes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.softproideas.spreadsheet.webapp.service.DimensionService#fetchCellPK(
	 * int, java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public String fetchCellPK(int modelId, String mapping, String[] context, String sheetModel)
			throws ServiceException {
		String cellPk = "";

		List<ElementDTO> dimensions = fetchDimensionDetails(modelId, mapping, context, sheetModel);
		for (ElementDTO elementDTO : dimensions)
			cellPk += elementDTO.getId() + ",";

		ElementDTO dataType = fetchDataType(mapping, context[3]);
		cellPk += dataType.getName();

		return cellPk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.softproideas.spreadsheet.webapp.service.DimensionService#fetchCellPK(
	 * java.util.List,
	 * com.softproideas.spreadsheet.webapp.model.dto.dimension.ElementDTO)
	 */
	@Override
	public String fetchCellPK(List<ElementDTO> dimensions, ElementDTO dataType) {
		String cellPk = "";
		for (ElementDTO elementDTO : dimensions)
			cellPk += elementDTO.getId() + ",";
		cellPk += dataType.getName();
		return cellPk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.softproideas.spreadsheet.webapp.service.DimensionService#
	 * fetchDimensionDetails(int, java.lang.String, java.lang.String[],
	 * java.lang.String)
	 */
	@Override
	public List<ElementDTO> fetchDimensionDetails(int modelId, String mapping, String[] context, String sheetModel)
			throws ServiceException {

		// if there is a sheetModel then use it
		if (sheetModel != null && !sheetModel.isEmpty()) {

			int sheetModelId = 0;
			ModelPK sheetModelPk = ModelPK.getKeyFromTokens(sheetModel);
			sheetModelId = sheetModelPk.getModelId();

			if (modelId != sheetModelId && sheetModelId > 0) {
				modelId = sheetModelId;
			}
		}

		List<ElementDTO> results = new ArrayList<ElementDTO>();
		EntityList dims = cpContextHolder.getListHelper().getModelDimensions(modelId);
		String[] fixedDimensions = DimensionUtil.fixProperDimensions(mapping, context);

		for (int dimNumber = 0; dimNumber < 3; dimNumber++) {

			// download the id of model's dimension
			DimensionRefImpl dimensionRef = (DimensionRefImpl) dims.getValueAt(dimNumber, "Dimension");
			int dimensionId = dimensionRef.getDimensionPK().getDimensionId();

			HierarchyNode hierarchyNode = fetchHierarchyNodeElement(dimensionId, fixedDimensions[dimNumber]);
			ElementDTO elementDTO;
			if (hierarchyNode != null) {
				elementDTO = DimensionMapper.mapHierarchyNodeToElementDTO(hierarchyNode);
			} else if (dimNumber == 2) {
				int id = fixCalendarDimensionIdentifier(dimensionId, fixedDimensions[dimNumber]);
				elementDTO = new ElementDTO();
				elementDTO.setId(id);
				elementDTO.setName(fixedDimensions[dimNumber]);
				elementDTO.setLeaf(true);
			} else {
				throw new ServiceException("Dimension " + dimNumber + " [" + fixedDimensions[dimNumber]
						+ "] is not found in modelId = " + modelId + ".");
			}
			results.add(elementDTO);
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.softproideas.spreadsheet.webapp.service.DimensionService#
	 * fetchDataType(java.lang.String, java.lang.String)
	 */
	@Override
	public ElementDTO fetchDataType(String mapping, String contextDataType) {
		String dataType = DimensionUtil.getKeyFromMapping(mapping, "dt");
		if (dataType.isEmpty())
			dataType = contextDataType;

		ListHelper process = cpContextHolder.getListHelper();
		EntityList dataTypeDetails = process.getDataTypeDetailsForVisID(dataType);

		ElementDTO elementDTO = new ElementDTO();
		if (dataTypeDetails != null && dataTypeDetails.getNumRows() > 0) {
			elementDTO.setId((Short) dataTypeDetails.getValueAt(0, "DataTypeId"));
			elementDTO.setName((String) dataTypeDetails.getValueAt(0, "VisId"));
			elementDTO.setDescription((String) dataTypeDetails.getValueAt(0, "Description"));
		}
		return elementDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.softproideas.spreadsheet.webapp.service.DimensionService#
	 * checkIfDimensionAreLeafs(java.util.List)
	 */
	@Override
	public boolean checkIfDimensionAreLeafs(List<ElementDTO> dimensions) {
		for (ElementDTO elementDTO : dimensions) {
			if (!elementDTO.isLeaf())
				return false;
		}
		return true;
	}

	/**
	 * Method return dimension list from database as EntityList
	 */
	private EntityList browseDimensionList(int modelId, int[] dimTypes) throws ServiceException {
		try {
			EntityList dimensionsList = cpContextHolder.getListHelper().getTreeInfoForModelDimSeq(modelId, dimTypes);
			return dimensionsList;
		} catch (Exception e) {
			Throwable t = e.getCause();
			if (t instanceof RemoteException) {
				RemoteException re = (RemoteException) t;
				t = re.getCause();
				if (t instanceof EJBException) {
					EJBException ejb = (EJBException) t;
					logger.error("Error during fetching children for dimension!", ejb.getCausedByException());
					throw new ServiceException("Error during fetching children for dimension!",
							ejb.getCausedByException());
				}
			}

			logger.error("Error during fetching children for dimension!", e);
			throw new ServiceException("Error during fetching children for dimension!", e);
		}
	}

	/**
	 * Method return children for parent dimension as EntityList
	 * 
	 * @param dimensionType
	 *            - specific dimension type (ACCOUNT, BUSSINESS, CALENDAR)
	 */
	private EntityList browseChildrenList(int dimensionType, int budgetCycleId, StructureElementPK parentPrimaryKey)
			throws ServiceException {
		try {
			EntityList childrenList = cpContextHolder.getDataEntryProcess().getImmediateChildrenLimitedByPermission(
					dimensionType, cpContextHolder.getUserId(), budgetCycleId, parentPrimaryKey);
			return childrenList;
		} catch (Exception e) {
			Throwable t = e.getCause();
			if (t instanceof RemoteException) {
				RemoteException re = (RemoteException) t;
				t = re.getCause();
				if (t instanceof EJBException) {
					EJBException ejb = (EJBException) t;
					logger.error("Error during fetching children for dimension!", ejb.getCausedByException());
					throw new ServiceException("Error during fetching children for dimension!",
							ejb.getCausedByException());
				}
			}
			logger.error("Error during fetching children for dimension!", e);
			throw new ServiceException("Error during fetching children for dimension!", e);
		}
	}

	/**
	 * Method return datatype from database as EntityList
	 */
	private EntityList browseDataTypeList() throws ServiceException {
		try {
			EntityList dataTypeList = cpContextHolder.getListHelper().getPickerDataTypesWeb(new int[] { 0, 1, 2, 4 },
					false);
			return dataTypeList;
		} catch (Exception e) {
			Throwable t = e.getCause();
			if (t instanceof RemoteException) {
				RemoteException re = (RemoteException) t;
				t = re.getCause();
				if (t instanceof EJBException) {
					EJBException ejb = (EJBException) t;
					logger.error("Error during fetching dataTypes!", ejb.getCausedByException());
					throw new ServiceException("Error during fetching dataTypes!", ejb.getCausedByException());
				}
			}
			logger.error("Error during fetching dataTypes!", e);
			throw new ServiceException("Error during fetching dataTypes!", e);
		}
	}

	/**
	 * Method return hierarchy node for dimensionId and dimensioName.
	 */
	private HierarchyNode fetchHierarchyNodeElement(int dimensionId, String dimensionName) throws ServiceException {
		EntityList entityListHierarchy = cpContextHolder.getHierarchiesProcess()
				.getHierarcyDetailsFromDimId(dimensionId);
		HierarchyRefImpl hierarchyRef = (HierarchyRefImpl) entityListHierarchy.getValueAt(0, "Hierarchy");
		HierarchyCK key = (HierarchyCK) hierarchyRef.getPrimaryKey();
		HierarchyEditorSession session;
		try {
			session = cpContextHolder.getHierarchiesProcess().getHierarchyEditorSession(key);
		} catch (ValidationException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		HierarchyEditorSession mes = (HierarchyEditorSession) session;
		HierarchyEditor mHierarchyEditor = mes.getHierarchyEditor();
		HierarchyNode hierarchyNodeElement = mHierarchyEditor.getHierarchy().findElement(dimensionName);
		return hierarchyNodeElement;
	}

	/**
	 * Method return identifier for calendar dimension with dimensionId and
	 * dimensionName
	 */
	private int fixCalendarDimensionIdentifier(int dimensionId, String dimensionName) throws ServiceException {
		String calVisIdPrefix = dimensionName.substring(0, dimensionName.lastIndexOf('/') + 1);
		String visId = dimensionName.substring(dimensionName.lastIndexOf('/') + 1);
		try {
			int id = cpContextHolder.getDataEntryProcess().getCalYearMonthId(visId, calVisIdPrefix, dimensionId);
			return id;
		} catch (CPException e) {
			throw new ServiceException("Could not find dimension 2.", e);
		} catch (ValidationException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}
