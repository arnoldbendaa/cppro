package com.softproideas.api.cpfunctionsevaluator;

import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.util.xmlform.CalendarInfo;

public interface CPFunctionDao {
    abstract EntityList getAllModelsWeb();

    abstract EntityList getAllBudgetCyclesWeb();

    abstract EntityList getAllSystemProperties();

    abstract EntityList getAllFinanceCubesWebForModel(int var1);

    abstract EntityList getAllDimensionsForModel(int var1);

    abstract EntityList getHierarcyDetailsFromDimId(int var1);

    abstract EntityList getCellValues(int var1, int var2, String[] var3, List<String[]> var4, String company) throws ValidationException;

    abstract EntityList getCellValues(int var1, int var2, String[] var3, List<String[]> var4) throws ValidationException;

    abstract EntityList getOACellValues(int company, List<String[]> cellKeys) throws ValidationException;

    abstract EntityList getCurrencyCellValues(List<String[]> cellKeys) throws ValidationException;

    abstract EntityList getParameterCellValues(List<String[]> cellKeys) throws ValidationException;

    abstract EntityList getAuctionCellValues(List<String[]> cellKeys) throws ValidationException;

    abstract ExtractDataDTO getExtractData(ExtractDataDTO var1) throws ValidationException;

    abstract CalendarInfo getCalendarInfo(String var1) throws ValidationException;

    abstract EntityList getStructureElementIdForModel(int var1) throws ValidationException;
}
