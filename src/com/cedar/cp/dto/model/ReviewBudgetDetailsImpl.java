// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.api.model.ReviewBudgetSelectOption;
import com.cedar.cp.api.model.ReviewBudgetSelection;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitViolation;
import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
import com.cedar.cp.dto.model.ReviewBudgetSelectOptionImpl;
import com.cedar.cp.dto.model.ReviewBudgetSelectionImpl;
import com.cedar.cp.util.flatform.model.Workbook;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReviewBudgetDetailsImpl implements ReviewBudgetDetails {

   private static final long serialVersionUID = 1216531075907209102L;
   private ReviewBudgetSelectOption mBudgetCycle;
   private ReviewBudgetSelectOption mModel;
   private int mFinanceCubeId;
   private List mStructureElements = new ArrayList();
   private List mColumns = new ArrayList();
   private List mFormData = new ArrayList();
   private int mPeriod;
   private String mDataType;
   private Map mContextVariables;
   private List<RuntimeCubeDeployment> mCubeDeployments;
   private int mAccountDimensionId;
   private ReviewBudgetSelectionImpl[] mDimensionSelections;
   private List mBudgetLimitViolations = new ArrayList();
   private List mBudgetLimitViolationHeadings = new ArrayList();
   private Set mReadOnlyDataTypes = new HashSet();
   private Map<String, DataType> mDataTypes;
   private int mCreditColor;
   private int mDebitColor;
   private boolean mNegateCreditSign;
   private String mDefaultFormatPattern;
   private int mRowHeaderColumnCount;
   private int mRowHeaderMaxDepth;
   private EntityList[] mSelectionLabels;
   private List<Integer> mTreeStructureIndexes;
   private List<String> mTreeStructureVisIds;
   private int[] mStructureIds;
   private int mSheetProtectionLevel;
   private int mBudgetCyclePeriodId;
   private int mFormId;
   private int mFormType;
   private String mSheetLevelNoteSummary;
   private boolean mSheetLevelNoteRead;
   private double mSpreadPrecisionThreshold;
   private boolean mPopSpreadDialog;
   private boolean mServerSideChartOfAccountValidation;
   private boolean mExternalSystemAvailable;
   private String mOnFormLoadFormula;
   private Set<String> mFormDataTypes;
   private int mGradientDepth = 1;
   private Color mGradientColor = null;
   private Color mEditBackground;
   private Color mModifiedBackground;
   private Color mEditForeground;
   private Color mModifiedForeground;
   private Workbook mWorkbook;
   private byte[] mExcelFile;
   private String mJsonForm;
   private Map<Integer, EntityList> mSecurityAccessDetails = new HashMap();


   public void setDimensionsSelection(AllDimensionsForModelELO dimensions) {
      this.mDimensionSelections = new ReviewBudgetSelectionImpl[dimensions.getNumRows()];

      for(int i = 0; i < dimensions.getNumRows(); ++i) {
         String dimVisId = String.valueOf(dimensions.getValueAt(i, "VisId"));
         String description = String.valueOf(dimensions.getValueAt(i, "Description"));
         this.mDimensionSelections[i] = new ReviewBudgetSelectionImpl();
         this.mDimensionSelections[i].setLabel(dimVisId);
      }

      this.mDimensionSelections[0].setSelectOptions(this.mStructureElements);
   }

   public void setSelections(int[] selections) {
      for(int i = 0; i < this.mDimensionSelections.length; ++i) {
         this.mDimensionSelections[i].setCurrentOption(selections[i]);
      }

   }

   public void setModel(int modelId, String narrative) {
      this.mModel = new ReviewBudgetSelectOptionImpl(0, modelId, narrative, -1, -1);
   }

   public void setBudgetCycle(int budgetCycleId, String narrative) {
      this.mBudgetCycle = new ReviewBudgetSelectOptionImpl(0, budgetCycleId, narrative, -1, -1);
   }

   public void addStructureElement(String levelPrefix, int structureElementId, String narrative, int depth) {
      if(depth > 0) {
         StringBuffer sb = new StringBuffer(depth * 6);

         for(int i = 0; i < depth; ++i) {
            sb.append(levelPrefix);
         }

         this.mStructureElements.add(new ReviewBudgetSelectOptionImpl(depth, structureElementId, sb.toString() + narrative, -1, -1));
      } else {
         this.mStructureElements.add(new ReviewBudgetSelectOptionImpl(depth, structureElementId, narrative, -1, -1));
      }

   }

   public void setDimensionSelection(int dim, String visId) {
      this.mDimensionSelections[dim].setLabel(visId);
   }

   public void addDimensionElement(int dim, String levelPrefix, int structureElementId, String narrative, int depth) {
      if(depth > 0) {
         StringBuffer sb = new StringBuffer(depth * 6);

         for(int i = 0; i < depth; ++i) {
            sb.append(levelPrefix);
         }

         this.mDimensionSelections[dim].getSelectOptions().add(new ReviewBudgetSelectOptionImpl(depth, structureElementId, sb.toString() + narrative, -1, -1));
      } else {
         this.mDimensionSelections[dim].getSelectOptions().add(new ReviewBudgetSelectOptionImpl(depth, structureElementId, narrative, -1, -1));
      }

   }

   public void addReadOnlyDataType(String dataType) {
      this.mReadOnlyDataTypes.add(dataType);
   }

   public Map<String, DataType> getDataTypes() {
      return this.mDataTypes;
   }

   public void setDataTypes(Map<String, DataType> dataTypes) {
      this.mDataTypes = dataTypes;
   }

   public void setColumns(List columns) {
      this.mColumns = columns;
   }

   public void setPeriod(int periodId) {
      this.mPeriod = periodId;
      this.mDimensionSelections[this.mDimensionSelections.length - 1].setCurrentOption(periodId);
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public void setFormData(List formData) {
      this.mFormData = formData;
   }

   public String getOnFormLoadFormula() {
      return this.mOnFormLoadFormula;
   }

   public void setOnFormLoadFormula(String onFormLoadFormula) {
      this.mOnFormLoadFormula = onFormLoadFormula;
   }

   public String getDefaultFormatPattern() {
      return this.mDefaultFormatPattern;
   }

   public void setDefaultFormatPattern(String defaultFormatPattern) {
      this.mDefaultFormatPattern = defaultFormatPattern;
   }

   public void setAccountDimensionId(int id) {
      this.mAccountDimensionId = id;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public void setContextVariables(Map variables) {
      this.mContextVariables = variables;
   }

   public void addContextVariables(Map variables) {
      this.mContextVariables.putAll(variables);
   }

   public void setCubeDeployments(List<RuntimeCubeDeployment> mappings) {
      this.mCubeDeployments = mappings;
   }

   public void setTreeStructureIndexes(List indexes) {
      this.mTreeStructureIndexes = indexes;
   }

   public void setTreeStructureVisIds(List treeStructureVisIds) {
      this.mTreeStructureVisIds = treeStructureVisIds;
   }

   public void addBudgetLimitViolation(BudgetLimitViolation violation) {
      this.mBudgetLimitViolations.add(violation);
   }

   public void setBudgetLimitViolationStructureHeadings(List headings) {
      this.mBudgetLimitViolationHeadings = headings;
   }

   public int getDimensionCount() {
      return this.mDimensionSelections.length;
   }

   public ReviewBudgetSelection[] getDimensionSelections() {
      return this.mDimensionSelections;
   }

   public ReviewBudgetSelectOption getBudgetCycle() {
      return this.mBudgetCycle;
   }

   public ReviewBudgetSelectOption getModel() {
      return this.mModel;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public List getStructureElements() {
      return this.mStructureElements;
   }

   public List getColumns() {
      return this.mColumns;
   }

   public List getBudgetLimitViolations() {
      return this.mBudgetLimitViolations;
   }

   public List getBudgetLimitViolationStructureHeadings() {
      return this.mBudgetLimitViolationHeadings;
   }

   public List getFormData() {
      return this.mFormData;
   }

   public int getPeriod() {
      return this.mPeriod;
   }

   public int getAccountDimensionId() {
      return this.mAccountDimensionId;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public Map getContextVariables() {
      return this.mContextVariables;
   }

   public List<RuntimeCubeDeployment> getCubeDeployments() {
      return this.mCubeDeployments == null?Collections.EMPTY_LIST:this.mCubeDeployments;
   }

   public Set getReadOnlyDataTypes() {
      return this.mReadOnlyDataTypes;
   }

   public List<Integer> getTreeStructureIndexes() {
      return this.mTreeStructureIndexes;
   }

   public List<String> getTreeStructureVisIds() {
      return this.mTreeStructureVisIds;
   }

   public int getCreditColor() {
      return this.mCreditColor;
   }

   public void setCreditColor(int creditColor) {
      this.mCreditColor = creditColor;
   }

   public int getDebitColor() {
      return this.mDebitColor;
   }

   public void setDebitColor(int debitColor) {
      this.mDebitColor = debitColor;
   }

   public boolean isNegateCreditSign() {
      return this.mNegateCreditSign;
   }

   public void setNegateCreditSign(boolean negateCreditSign) {
      this.mNegateCreditSign = negateCreditSign;
   }

   public int getRowHeaderColumnCount() {
      return this.mRowHeaderColumnCount;
   }

   public void setRowHeaderColumnCount(int rowHeaderColumnCount) {
      this.mRowHeaderColumnCount = rowHeaderColumnCount;
   }

   public int getRowHeaderMaxDepth() {
      return this.mRowHeaderMaxDepth;
   }

   public void setRowHeaderMaxDepth(int rowHeaderMaxDepth) {
      this.mRowHeaderMaxDepth = rowHeaderMaxDepth;
   }

   public EntityList[] getSelectionLabels() {
      return this.mSelectionLabels;
   }

   public void setSelectionLabels(EntityList[] selectionLabels) {
      this.mSelectionLabels = selectionLabels;
   }

   public int getSheetProtectionLevel() {
      return this.mSheetProtectionLevel;
   }

   public void setSheetProtectionLevel(int sheetProtectionLevel) {
      this.mSheetProtectionLevel = sheetProtectionLevel;
   }

   public int getBudgetCyclePeriodId() {
      return this.mBudgetCyclePeriodId;
   }

   public void setBudgetCyclePeriodId(int budgetCyclePeriodId) {
      this.mBudgetCyclePeriodId = budgetCyclePeriodId;
   }

   public String getSheetLevelNoteSummary() {
      return this.mSheetLevelNoteSummary;
   }

   public void setSheetLevelNoteSummary(String sheetLevelNoteSummary) {
      this.mSheetLevelNoteSummary = sheetLevelNoteSummary;
   }

   public boolean isSheetLevelNoteRead() {
      return this.mSheetLevelNoteRead;
   }

   public void setSheetLevelNoteRead(boolean sheetLevelNoteRead) {
      this.mSheetLevelNoteRead = sheetLevelNoteRead;
   }

   public int getFormId() {
      return this.mFormId;
   }

   public void setFormId(int formId) {
      this.mFormId = formId;
   }

   public int getFormType() {
      return this.mFormType;
   }

   public void setFormType(int formType) {
      this.mFormType = formType;
   }

   public double getSpreadPrecisionThreshold() {
      return this.mSpreadPrecisionThreshold;
   }

   public void setSpreadPrecisionThreshold(double spreadPrecisionThreshold) {
      this.mSpreadPrecisionThreshold = spreadPrecisionThreshold;
   }

   public boolean isServerSideChartOfAccountValidation() {
      return this.mServerSideChartOfAccountValidation;
   }

   public void setServerSideChartOfAccountValidation(boolean serverSideChartOfAccountValidation) {
      this.mServerSideChartOfAccountValidation = serverSideChartOfAccountValidation;
   }

   public boolean isExternalSystemAvailable() {
      return this.mExternalSystemAvailable;
   }

   public void setExternalSystemAvailable(boolean externalSystemAvailable) {
      this.mExternalSystemAvailable = externalSystemAvailable;
   }

   public Set<String> getFormDataTypes() {
      return this.mFormDataTypes;
   }

   public void setFormDataTypes(Set<String> formDataTypes) {
      this.mFormDataTypes = formDataTypes;
   }

   public boolean isPopSpreadDialog() {
      return this.mPopSpreadDialog;
   }

   public void setPopSpreadDialog(boolean popSpreadDialog) {
      this.mPopSpreadDialog = popSpreadDialog;
   }

   public int getGradientDepth() {
      return this.mGradientDepth;
   }

   public void setGradientDepth(int gradientDepth) {
      if(gradientDepth < 1) {
         gradientDepth = 1;
      }

      this.mGradientDepth = gradientDepth;
   }

   public Color getGradientColor() {
      return this.mGradientColor;
   }

   public void setGradientColor(Color gradientColor) {
      this.mGradientColor = gradientColor;
   }

   public Color getEditBackground() {
      return this.mEditBackground;
   }

   public void setEditBackground(Color editBackground) {
      this.mEditBackground = editBackground;
   }

   public Color getEditForeground() {
      return this.mEditForeground;
   }

   public void setEditForeground(Color editForeground) {
      this.mEditForeground = editForeground;
   }

   public Color getModifiedBackground() {
      return this.mModifiedBackground;
   }

   public void setModifiedBackground(Color modifiedBackground) {
      this.mModifiedBackground = modifiedBackground;
   }

   public Color getModifiedForeground() {
      return this.mModifiedForeground;
   }

   public void setModifiedForeground(Color modifiedForeground) {
      this.mModifiedForeground = modifiedForeground;
   }

   public Workbook getWorkbook() {
      return this.mWorkbook;
   }

   public void setWorkbook(Workbook workbook) {
      this.mWorkbook = workbook;
   }
   
   public void setSecurityAccessDetails(Integer modelId, EntityList details) {
      this.mSecurityAccessDetails.put(modelId, details);
   }

   public Map getSecurityAccessDetails() {
      return this.mSecurityAccessDetails;
   }

   public int[] getStructureIds() {
      return this.mStructureIds;
   }

   public void setStructureIds(int[] structureIds) {
      this.mStructureIds = structureIds;
   }
   
	public void setExcelFile(byte[] excel) {
		this.mExcelFile = excel;
	}

	public byte[] getExcelFile() {
		return this.mExcelFile;
	}

	public String getJsonForm() {
		return mJsonForm;
	}

	public void setJsonForm(String mJsonForm) {
		this.mJsonForm = mJsonForm;
	}
}
