package com.softproideas.app.core.workbook.model;

import java.sql.Connection;
import java.util.Map;

public interface FlatFormExtractorDao {

    Map<String, String> getPropertiesForModelVisId(String modelVisId);

    Map<String, String> getPropertiesForModelId(String modelId);

	void toggleLockFlag(int userId,int xmlFormId,  boolean flag);

	char getLockFlag(int xmlFormId);

}
