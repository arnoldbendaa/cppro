// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.ValueMapping;
import java.text.MessageFormat;

public class i18nUtils {

   private static final String[] sDimensionTypeLiterals = new String[]{"Account", "Business", "Calendar"};
   private static final Object[] sDimensionTypeValues = new Object[]{new Integer(1), new Integer(2), new Integer(3)};
   private static ValueMapping sDimensionTypeMapping = null;
   private static final String[] sUserPreferenceTypeLiterals = new String[]{"Font", "Frame", "Import", "Export"};
   private static final Object[] sUserPreferenceTypeValues = new Object[]{new Integer(0), new Integer(1), new Integer(2), new Integer(3)};
   private static ValueMapping sUserPreferenceValueMapping = null;
   private static ValueMapping sUserPreferenceTypeMapping = null;
   private static final String[] sUserPreferencesLiterals = new String[]{"Font Name", "Font Size", "Save Frame Position", "Frame Position Size", "Import Directory", "Export Directory"};
   private static final String[] sUserPreferencesValues = new String[]{"FONT_NAME", "FONT_SIZE", "SAVE_FRAME_POSITION", "FRAME_POSITION_SIZE", "IMPORT_DIRECTORY", "EXPORT_DIRECTORY"};
   private static ValueMapping sUserPreferencesMapping = null;
   private static final String[] sMeasureUnitLiterals = new String[]{"1\'s", "10\'s", "100\'s", "1,000\'s", "10,000\'s", "100,000\'s", "1,000,000\'s"};
   private static final Object[] sMeasureUnitValues = new Object[]{new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7)};
   private static ValueMapping sMeasureUnitMapping = null;
   private static final String[] sXMLFormTypeLiterals = new String[]{"Table form", "Fixed Row form", "Dynamic Row form", "Finance Data Entry form", "Flat form", "Mass Budget Transfer form", "Xcell form", "Xcell form"};
   private static final Object[] sXMLFormTypeValues = new Object[]{new Integer(0), new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7)};
   private static ValueMapping sXMLFormMapping;
   private static final String[] sBudgetCycleLiterals_option1 = new String[]{"Initiated", "In Progress"};
   private static final Object[] sBudgetCycleValues_option1 = new Object[]{new Integer(0), new Integer(1)};
   private static final String[] sBudgetCycleLiterals_option2 = new String[]{"In Progress", "Complete"};
   private static final Object[] sBudgetCycleValues_option2 = new Object[]{new Integer(1), new Integer(2)};
   private static final String[] sBudgetCycleLiterals_option3 = new String[]{"Complete", "Archived"};
   private static final Object[] sBudgetCycleValues_option3 = new Object[]{new Integer(2), new Integer(3)};
   private static final String[] sBudgetCycleLiterals = new String[]{"Initiated", "In Progress", "Complete", "Archived"};
   private static final Object[] sBudgetCycleValues = new Object[]{new Integer(0), new Integer(1), new Integer(2), new Integer(3)};
   private static ValueMapping sBudgetCycleMapping;
   private static final String[] sMeasureScaleLiterals = new String[]{"0 Decimal Places", "1 Decimal Place", "2 Decimal Places", "3 Decimal Places", "4 Decimal Places", "5 Decimal Places", "6 Decimal Places", "7 Decimal Places", "8 Decimal Places", "9 Decimal Places", "10 Decimal Places", "11 Decimal Places", "12 Decimal Places", "13 Decimal Places", "14 Decimal Places"};
   private static final Object[] sMeasureScaleValues = new Object[]{new Integer(0), new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7), new Integer(8), new Integer(9), new Integer(10), new Integer(11), new Integer(12), new Integer(13), new Integer(14)};
   private static ValueMapping sMeasureScaleMapping = null;
   private static final String[] sOverrideDataTypeLiterals = new String[]{"0 Decimal Places", "1 Decimal Place", "2 Decimal Places", "3 Decimal Places", "4 Decimal Places", "5 Decimal Places", "6 Decimal Places", "7 Decimal Places", "8 Decimal Places", "9 Decimal Places", "10 Decimal Places", "11 Decimal Places", "12 Decimal Places", "13 Decimal Places", "14 Decimal Places", "Text"};
   private static final Object[] sOverrideDataTypeValues = new Object[]{new Integer(0), new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7), new Integer(8), new Integer(9), new Integer(10), new Integer(11), new Integer(12), new Integer(13), new Integer(14), new Integer(-1)};
   private static ValueMapping sOverrideDataTypeMapping = null;
   private static final Object[] sCreditDebitValues = new Object[]{new Integer(0), new Integer(1), new Integer(2), new Integer(3)};
   private static final String[] sCreditDebitLiterals = new String[]{"Not Applicable", "Credit", "Debit", "Adopt Parent Setting"};
   private static ValueMapping sCreditDebitMapping = null;
   private static final String[] sApplicationSecurityLiterals = new String[]{"Accounts", "New", "Save", "SaveAs", "Delete", "Export", "Basic Formulae", "New", "Save", "SaveAs", "Delete", "Budget Instructions", "New", "Save", "SaveAs", "Delete", "Business Dimensions", "New", "Save", "SaveAs", "Delete", "Export", "Calendars", "New", "Save", "SaveAs", "Delete", "Export", "Cell Calculations", "New", "Save", "SaveAs", "Delete", "Contributor", "New", "Save", "SaveAs", "Delete", "Data Groups", "New", "Save", "SaveAs", "Delete", "Data Rollup Rules", "Dynamic Dimension Updates", "New", "Save", "SaveAs", "Delete", "Finance Cubes", "New", "Save", "SaveAs", "Delete", "Export", "Import", "Group Allocations", "New", "Save", "SaveAs", "Delete", "Launch", "Group Allocation Sources", "New", "Save", "SaveAs", "Delete", "Group Allocation Targets", "New", "Save", "SaveAs", "Delete", "Lock Monitor", "New", "Save", "SaveAs", "Delete", "Measures", "New", "Save", "SaveAs", "Delete", "Models", "New", "Save", "SaveAs", "Delete", "Navigator", "New", "Save", "SaveAs", "Delete", "Launch", "Preferences", "Edit User Preferences", "Edit Global Preferences", "Power Allocations", "New", "Save", "SaveAs", "Delete", "Power Formulae", "New", "Save", "SaveAs", "Delete", "Privacy", "New", "Save", "SaveAs", "Delete", "Responsibility Areas", "New", "Save", "SaveAs", "Task Monitors", "New", "Save", "SaveAs", "Delete", "Task Scheduler", "New", "Save", "SaveAs", "Delete", "Users", "New", "Save", "SaveAs", "Delete", "Export", "Import", "User Monitor", "New", "Save", "SaveAs", "Delete", "Visibility Profiles", "New", "Save", "SaveAs", "Delete", "Workflow Status", "New", "Save", "SaveAs", "Delete", "Workflow Templates", "New", "Save", "SaveAs", "Delete", "Workflow Viewer", "New", "Save", "SaveAs", "Delete"};
   private static final String[] sApplicationSecurityValues = new String[]{"ACCOUNTS_PROCESS", "ACCOUNTS_PROCESS.New", "ACCOUNTS_PROCESS.Save", "ACCOUNTS_PROCESS.SaveAs", "ACCOUNTS_PROCESS.Delete", "ACCOUNTS_PROCESS.Export", "BASIC_FORMULAE_PROCESS", "BASIC_FORMULAE_PROCESS.New", "BASIC_FORMULAE_PROCESS.Save", "BASIC_FORMULAE_PROCESS.SaveAs", "BASIC_FORMULAE_PROCESS.Delete", "BUDGET_INSTRUCTIONS_PROCESS", "BUDGET_INSTRUCTIONS_PROCESS.New", "BUDGET_INSTRUCTIONS_PROCESS.Save", "BUDGET_INSTRUCTIONS_PROCESS.SaveAs", "BUDGET_INSTRUCTIONS_PROCESS.Delete", "BUSINESS_DIMENSIONS_PROCESS", "BUSINESS_DIMENSIONS_PROCESS.New", "BUSINESS_DIMENSIONS_PROCESS.Save", "BUSINESS_DIMENSIONS_PROCESS.SaveAs", "BUSINESS_DIMENSIONS_PROCESS.Delete", "BUSINESS_DIMENSIONS_PROCESS.Export", "CALENDARS_PROCESS", "CALENDARS_PROCESS.New", "CALENDARS_PROCESS.Save", "CALENDARS_PROCESS.SaveAs", "CALENDARS_PROCESS.Delete", "CALENDARS_PROCESS.Export", "CELL_CALCULATIONS_PROCESS", "CELL_CALCULATIONS_PROCESS.New", "CELL_CALCULATIONS_PROCESS.Save", "CELL_CALCULATIONS_PROCESS.SaveAs", "CELL_CALCULATIONS_PROCESS.Delete", "CONTRIBUTOR_PROCESS", "CONTRIBUTOR_PROCESS.New", "CONTRIBUTOR_PROCESS.Save", "CONTRIBUTOR_PROCESS.SaveAs", "CONTRIBUTOR_PROCESS.Delete", "DATA_GROUPS_PROCESS", "DATA_GROUPS_PROCESS.New", "DATA_GROUPS_PROCESS.Save", "DATA_GROUPS_PROCESS.SaveAs", "DATA_GROUPS_PROCESS.Delete", "DATA_GROUPS_PROCESS.Data Rollup Rules", "DYNAMIC_DIMENSION_UPDATES_PROCESS", "DYNAMIC_DIMENSION_UPDATES_PROCESS.New", "DYNAMIC_DIMENSION_UPDATES_PROCESS.Save", "DYNAMIC_DIMENSION_UPDATES_PROCESS.SaveAs", "DYNAMIC_DIMENSION_UPDATES_PROCESS.Delete", "FINANCE_CUBES_PROCESS", "FINANCE_CUBES_PROCESS.New", "FINANCE_CUBES_PROCESS.Save", "FINANCE_CUBES_PROCESS.SaveAs", "FINANCE_CUBES_PROCESS.Delete", "FINANCE_CUBES_PROCESS.Export", "FINANCE_CUBES_PROCESS.Import", "GROUP_ALLOCATIONS_PROCESS", "GROUP_ALLOCATIONS_PROCESS.New", "GROUP_ALLOCATIONS_PROCESS.Save", "GROUP_ALLOCATIONS_PROCESS.SaveAs", "GROUP_ALLOCATIONS_PROCESS.Delete", "GROUP_ALLOCATIONS_PROCESS.Launch", "GROUP_ALLOCATION_SOURCES_PROCESS", "GROUP_ALLOCATION_SOURCES_PROCESS.New", "GROUP_ALLOCATION_SOURCES_PROCESS.Save", "GROUP_ALLOCATION_SOURCES_PROCESS.SaveAs", "GROUP_ALLOCATION_SOURCES_PROCESS.Delete", "GROUP_ALLOCATION_TARGETS_PROCESS", "GROUP_ALLOCATION_TARGETS_PROCESS.New", "GROUP_ALLOCATION_TARGETS_PROCESS.Save", "GROUP_ALLOCATION_TARGETS_PROCESS.SaveAs", "GROUP_ALLOCATION_TARGETS_PROCESS.Delete", "LOCK_MONITOR_PROCESS", "LOCK_MONITOR_PROCESS.New", "LOCK_MONITOR_PROCESS.Save", "LOCK_MONITOR_PROCESS.SaveAs", "LOCK_MONITOR_PROCESS.Delete", "MEASURES_PROCESS", "MEASURES_PROCESS.New", "MEASURES_PROCESS.Save", "MEASURES_PROCESS.SaveAs", "MEASURES_PROCESS.Delete", "MODELS_PROCESS", "MODELS_PROCESS.New", "MODELS_PROCESS.Save", "MODELS_PROCESS.SaveAs", "MODELS_PROCESS.Delete", "NAVIGATOR_PROCESS", "NAVIGATOR_PROCESS.New", "NAVIGATOR_PROCESS.Save", "NAVIGATOR_PROCESS.SaveAs", "NAVIGATOR_PROCESS.Delete", "NAVIGATOR_PROCESS.Launch", "PREFERENCES_PROCESS", "PREFERENCES_PROCESS.Edit User Preferences", "PREFERENCES_PROCESS.Edit Global Preferences", "POWER_ALLOCATIONS_PROCESS", "POWER_ALLOCATIONS_PROCESS.New", "POWER_ALLOCATIONS_PROCESS.Save", "POWER_ALLOCATIONS_PROCESS.SaveAs", "POWER_ALLOCATIONS_PROCESS.Delete", "POWER_FORMULAE_PROCESS", "POWER_FORMULAE_PROCESS.New", "POWER_FORMULAE_PROCESS.Save", "POWER_FORMULAE_PROCESS.SaveAs", "POWER_FORMULAE_PROCESS.Delete", "PRIVACY_PROCESS", "PRIVACY_PROCESS.New", "PRIVACY_PROCESS.Save", "PRIVACY_PROCESS.SaveAs", "PRIVACY_PROCESS.Delete", "RESPONSIBILITY_AREAS_PROCESS", "RESPONSIBILITY_AREAS_PROCESS.New", "RESPONSIBILITY_AREAS_PROCESS.Save", "RESPONSIBILITY_AREAS_PROCESS.SaveAs", "TASK_MONITORS_PROCESS", "TASK_MONITORS_PROCESS.New", "TASK_MONITORS_PROCESS.Save", "TASK_MONITORS_PROCESS.SaveAs", "TASK_MONITORS_PROCESS.Delete", "TASK_SCHEDULER_PROCESS", "TASK_SCHEDULER_PROCESS.New", "TASK_SCHEDULER_PROCESS.Save", "TASK_SCHEDULER_PROCESS.SaveAs", "TASK_SCHEDULER_PROCESS.Delete", "USERS_PROCESS", "USERS_PROCESS.New", "USERS_PROCESS.Save", "USERS_PROCESS.SaveAs", "USERS_PROCESS.Delete", "USERS_PROCESS.Export", "USERS_PROCESS.Import", "USER_MONITOR_PROCESS", "USER_MONITOR_PROCESS.New", "USER_MONITOR_PROCESS.Save", "USER_MONITOR_PROCESS.SaveAs", "USER_MONITOR_PROCESS.Delete", "VISIBILITY_PROFILES_PROCESS", "VISIBILITY_PROFILES_PROCESS.New", "VISIBILITY_PROFILES_PROCESS.Save", "VISIBILITY_PROFILES_PROCESS.SaveAs", "VISIBILITY_PROFILES_PROCESS.Delete", "WORKFLOW_STATUS_PROCESS", "WORKFLOW_STATUS_PROCESS.New", "WORKFLOW_STATUS_PROCESS.Save", "WORKFLOW_STATUS_PROCESS.SaveAs", "WORKFLOW_STATUS_PROCESS.Delete", "WORKFLOW_TEMPLATES_PROCESS", "WORKFLOW_TEMPLATES_PROCESS.New", "WORKFLOW_TEMPLATES_PROCESS.Save", "WORKFLOW_TEMPLATES_PROCESS.SaveAs", "WORKFLOW_TEMPLATES_PROCESS.Delete", "WORKFLOW_VIEWER_PROCESS", "WORKFLOW_VIEWER_PROCESS.New", "WORKFLOW_VIEWER_PROCESS.Save", "WORKFLOW_VIEWER_PROCESS.SaveAs", "WORKFLOW_VIEWER_PROCESS.Delete"};
   private static ValueMapping sApplicationSecurityMapping = null;
   private static ValueMapping sSecurityStringMapping = null;
   private static final Object[] sTaskStatusValues = new Object[]{new Integer(0), new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7), new Integer(8), new Integer(9), new Integer(10)};
   private static final String[] sTaskStatusLiterals = new String[]{"Created", "Received", "Despatched", "Running", "Failed", "Complete", "Waiting for task", "Commiting checkpoint", "Commiting completion", "Complete (exceptions)", "Unsafe deleted"};
   private static ValueMapping sTaskStatusMapping = null;
   private static final String[] sYesNoLiterals = new String[]{"Yes", "No"};
   private static final Object[] sBooleanValues = new Object[]{Boolean.TRUE, Boolean.FALSE};
   private static ValueMapping sBooleanYesNoMapping = null;
   private static final String[] sContributorDimensionAssignmentLiterals = new String[]{"Header", "Row", "Column"};
   private static final Object[] sContributorDimensionAssignmentValues = new Object[]{new Integer(0), new Integer(1), new Integer(2)};
   private static ValueMapping sContributorDimensionAssignment = null;
   private static final String[] sBasicAllocationTypeLiterals = new String[]{"Even Split", "Pro Rata", "Weighting Factors"};
   private static final Object[] sBasicAllocationTypeValues = new Object[]{new Integer(0), new Integer(1), new Integer(2)};
   private static ValueMapping sBasicAllocationTypeMapping = null;
   private static final String[] sMessageTypeLiterals = new String[]{"System Message", "Email Message", "System and Email Message"};
   private static final Object[] sMessageTypeValues = new Object[]{new Integer(0), new Integer(1), new Integer(2)};
   private static ValueMapping sMessageMapping;
   private static MessageFormat sMessageFormat = new MessageFormat("dummy");


   public static Object[] getDimensionTypeValues() {
      return sDimensionTypeValues;
   }

   public static ValueMapping getDimensionTypeMapping() {
      if(sDimensionTypeMapping == null) {
         sDimensionTypeMapping = new DefaultValueMapping(sDimensionTypeLiterals, sDimensionTypeValues);
      }

      return sDimensionTypeMapping;
   }

   public static Object[] getUserPreferenceTypeValues() {
      return sUserPreferenceTypeValues;
   }

   public static ValueMapping getUserPreferenceTypeMapping() {
      if(sUserPreferenceTypeMapping == null) {
         sUserPreferenceTypeMapping = new DefaultValueMapping(sUserPreferenceTypeLiterals, sUserPreferenceTypeValues);
      }

      return sUserPreferenceTypeMapping;
   }

   public static String[] getUserPreferencesValues() {
      return sUserPreferencesValues;
   }

   public static ValueMapping getUserPreferencesMapping() {
      if(sUserPreferencesMapping == null) {
         sUserPreferencesMapping = new DefaultValueMapping(sUserPreferencesLiterals, sUserPreferencesValues);
      }

      return sUserPreferencesMapping;
   }

   public static ValueMapping getMeasureUnitMapping() {
      if(sMeasureUnitMapping == null) {
         sMeasureUnitMapping = new DefaultValueMapping(sMeasureUnitLiterals, sMeasureUnitValues);
      }

      return sMeasureUnitMapping;
   }

   public static ValueMapping getDataTypeUnitMapping() {
      if(sMeasureUnitMapping == null) {
         sMeasureUnitMapping = new DefaultValueMapping(sMeasureUnitLiterals, sMeasureUnitValues);
      }

      return sMeasureUnitMapping;
   }

   public static ValueMapping getXMLFormTypeMapping() {
      if(sXMLFormMapping == null) {
         sXMLFormMapping = new DefaultValueMapping(sXMLFormTypeLiterals, sXMLFormTypeValues);
      }

      return sXMLFormMapping;
   }

   public static ValueMapping getBudgetCycleMapping(int state) {
      if(state == 0) {
         sBudgetCycleMapping = new DefaultValueMapping(sBudgetCycleLiterals_option1, sBudgetCycleValues_option1);
      } else if(state == 1) {
         sBudgetCycleMapping = new DefaultValueMapping(sBudgetCycleLiterals_option2, sBudgetCycleValues_option2);
      } else if(state == 2) {
         sBudgetCycleMapping = new DefaultValueMapping(sBudgetCycleLiterals_option3, sBudgetCycleValues_option3);
      } else {
         sBudgetCycleMapping = new DefaultValueMapping(sBudgetCycleLiterals, sBudgetCycleValues);
      }

      return sBudgetCycleMapping;
   }

   public static ValueMapping getMeasureScaleMapping() {
      if(sMeasureScaleMapping == null) {
         sMeasureScaleMapping = new DefaultValueMapping(sMeasureScaleLiterals, sMeasureScaleValues);
      }

      return sMeasureScaleMapping;
   }

   public static ValueMapping getDataTypeScaleMapping() {
      if(sMeasureScaleMapping == null) {
         sMeasureScaleMapping = new DefaultValueMapping(sMeasureScaleLiterals, sMeasureScaleValues);
      }

      return sMeasureScaleMapping;
   }

   public static ValueMapping getOverrideDataTypeMapping() {
      if(sOverrideDataTypeMapping == null) {
         sOverrideDataTypeMapping = new DefaultValueMapping(sOverrideDataTypeLiterals, sOverrideDataTypeValues);
      }

      return sOverrideDataTypeMapping;
   }

   public static ValueMapping getCreditDebitMapping() {
      if(sCreditDebitMapping == null) {
         sCreditDebitMapping = new DefaultValueMapping(sCreditDebitLiterals, sCreditDebitValues);
      }

      return sCreditDebitMapping;
   }

   public static String[] getApplicationSecurityValues() {
      return sApplicationSecurityValues;
   }

   public static ValueMapping getApplicationSecurityMapping() {
      if(sApplicationSecurityMapping == null) {
         sApplicationSecurityMapping = new DefaultValueMapping(sApplicationSecurityLiterals, sApplicationSecurityValues);
      }

      return sApplicationSecurityMapping;
   }

   public static ValueMapping getSecurityStringMapping() {
      if(sSecurityStringMapping == null) {
         sSecurityStringMapping = new DefaultValueMapping(sApplicationSecurityValues, sApplicationSecurityLiterals);
      }

      return sSecurityStringMapping;
   }

   public static ValueMapping getTaskStatusMapping() {
      if(sTaskStatusMapping == null) {
         sTaskStatusMapping = new DefaultValueMapping(sTaskStatusLiterals, sTaskStatusValues);
      }

      return sTaskStatusMapping;
   }

   public static ValueMapping getBooleanYesNo() {
      if(sBooleanYesNoMapping == null) {
         sBooleanYesNoMapping = new DefaultValueMapping(sYesNoLiterals, sBooleanValues);
      }

      return sBooleanYesNoMapping;
   }

   public static ValueMapping getContributorDimensionAssignmentMapping() {
      if(sContributorDimensionAssignment == null) {
         sContributorDimensionAssignment = new DefaultValueMapping(sContributorDimensionAssignmentLiterals, sContributorDimensionAssignmentValues);
      }

      return sContributorDimensionAssignment;
   }

   public static ValueMapping getBasicAllocationTypeMapping() {
      if(sBasicAllocationTypeMapping == null) {
         sBasicAllocationTypeMapping = new DefaultValueMapping(sBasicAllocationTypeLiterals, sBasicAllocationTypeValues);
      }

      return sBasicAllocationTypeMapping;
   }

   public static ValueMapping getMessageTypeMapping() {
      if(sMessageMapping == null) {
         sMessageMapping = new DefaultValueMapping(sMessageTypeLiterals, sMessageTypeValues);
      }

      return sMessageMapping;
   }

   public static String messageFormat(String message, Object o) {
      return messageFormat(message, o, (Object)null, (Object)null, (Object)null, (Object)null);
   }

   public static String messageFormat(String message, Object o1, Object o2) {
      return messageFormat(message, o1, o2, (Object)null, (Object)null, (Object)null);
   }

   public static String messageFormat(String message, Object o1, Object o2, Object o3) {
      return messageFormat(message, o1, o2, o3, (Object)null, (Object)null);
   }

   public static String messageFormat(String message, Object o1, Object o2, Object o3, Object o4) {
      return messageFormat(message, o1, o2, o3, o4, (Object)null);
   }

   public static String messageFormat(String message, Object o1, Object o2, Object o3, Object o4, Object o5) {
      return internalFormat(message, new Object[]{o1, o2, o3, o4, o5});
   }

   private static synchronized String internalFormat(String pattern, Object[] objs) {
      sMessageFormat.applyPattern(pattern);
      return sMessageFormat.format(objs);
   }

}
