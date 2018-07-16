package com.cedar.cp.utc.struts.helper;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.utc.common.CPBaseAJAXAction;
import com.cedar.cp.utc.common.CPContext;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONListHelper extends CPBaseAJAXAction {

	private String mType;
	private String mCustom;
	private String mId;
	private String mKey;
	private String mDescription;
	private String mXtraColumns;
	private CPConnection mConn;
	private HttpServletRequest mRequest;

	public Object processRequest(ActionForm actionForm, HttpServletRequest httpServletRequest, CPContext context, CPConnection conn) throws Exception {
		mConn = conn;
		mRequest = httpServletRequest;

		mCustom = httpServletRequest.getParameter("CustomType");
		mType = httpServletRequest.getParameter("Type");
		mId = httpServletRequest.getParameter("Id");
		mKey = httpServletRequest.getParameter("Key");
		mDescription = httpServletRequest.getParameter("Description");
		if (mDescription == null)
			mDescription = "Description";
		mXtraColumns = httpServletRequest.getParameter("Columns");

		if (mType != null)
		{
			Class c = ListHelper.class;
			ListHelper helper = conn.getListHelper();

			String methodName = "getAll" + mType + "s";
			Method[] methods = c.getMethods();
			Method method = null;
			for (Method m : methods)
			{
				if ((m.getName().equals(methodName)) && (m.getParameterTypes().length == 0))
				{
					method = m;
					break;
				}
			}

			if (method != null) {
				return processEntityList((EntityList) method.invoke(helper, new Object[0]));
			}
			return emptyJSONObject();
		}

		if (mCustom != null)
		{
			return processCustom();
		}

		return emptyJSONObject();
	}

	private Object processCustom() throws Exception	{
		Class me = getClass();
		for (Method m : me.getMethods())
		{
			if (m.getName().equals(mCustom))
			{
				return m.invoke(this, new Object[0]);
			}

		}

		return emptyJSONObject();
	}

	public Object DEProfileforUser() {
		String modelId = mRequest.getParameter("ModelId");
		String budgetCycleId = mRequest.getParameter("budgetCycleId");
		
		EntityList list = mConn.getListHelper().getAllDataEntryProfilesForUser(mConn.getUserContext().getUserId(), Integer.parseInt(modelId), Integer.parseInt(budgetCycleId));

		return processEntityList(list);
	}

	public Object DataTypes() {
		String cubeId = mRequest.getParameter("CubeId");
		EntityList list = mConn.getListHelper().getPickerDataTypesWeb(Integer.parseInt(cubeId), new int[] { 0, 1, 2, 3, 4 }, false);
		return processEntityList(list);
	}

	public Object CubeInfo() {
		String modelId = mRequest.getParameter("ModelId");
		EntityList list = mConn.getListHelper().getFinanceCubesForModel(Integer.parseInt(modelId));
		return processEntityList(list);
	}

	public Object ModelInfoForUser() {
		EntityList list = mConn.getListHelper().getAllModelsWebForUser(mConn.getUserContext().getUserId());
		return processEntityList(list);
	}

	public Object FinanceCubeInfoForUser() {
		EntityList list = mConn.getListHelper().getAllFinanceCubesWebForUser(mConn.getUserContext().getUserId());
		return processEntityList(list);
	}

	public Object UsersInfo() {
		EntityList list = mConn.getListHelper().getAllUserAttributes();
		return processEntityList(list);
	}

	private Object emptyJSONObject() {
		JSONObject returnObject = new JSONObject();
		JSONArray returnList = new JSONArray();

		JSONObject item = new JSONObject();
		try {
			item.put("id", "EmptyList");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnList.put(item);

		try {
			returnObject.put("identifier", "id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			returnObject.put("items", returnList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnObject;
	}

	private Object processEntityList(EntityList list) {
		JSONObject returnObject = new JSONObject();
		JSONArray returnList = new JSONArray();

		if (list != null)
		{
			String[] headings = list.getHeadings();
			List headingList = Arrays.asList(headings);
			if ((mId == null) || (mId.length() == 0)) {
				mId = headings[0];
			}
			if ((mKey == null) && (!mId.equals("index")))
				mKey = mId;
			else {
				mKey = headings[0];
			}
			if (list.getNumRows() == 0)
			{
				JSONObject object = new JSONObject();
				try {
					object.put("id", "EmptyList");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				returnList.put(object);
			}

			for (int i = 0; i < list.getNumRows(); i++)
			{
				JSONObject object = new JSONObject();

				String identifierString = null;
				if ((mId != null) && (!mId.equals("index")))
					identifierString = list.getValueAt(i, mId).toString();
				String descriptionString = null;
				if ((identifierString == null) || (identifierString.length() == 0))
					try {
						object.put("id", i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else {
					try {
						object.put("id", identifierString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Object refObj = list.getValueAt(i, mKey);
				if ((refObj instanceof EntityRef))
				{
					EntityRef ref = (EntityRef) refObj;
					try {
						object.put("key", ref.getTokenizedKey());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						object.put("key", refObj);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (headingList.contains(mDescription))
				{
					descriptionString = list.getValueAt(i, mDescription).toString();
					try {
						object.put("description", descriptionString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (descriptionString != null) {
					try {
						object.put("value", identifierString + " - " + descriptionString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (mXtraColumns != null)
				{
					String[] columns = mXtraColumns.split(",");
					for (String col : columns)
					{
						if (headingList.contains(col))
						{
							Object o = list.getValueAt(i, col);
							if (o != null) {
								try {
									object.put(col, o.toString());
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
				returnList.put(object);
			}
		}

		try {
			returnObject.put("identifier", "id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			returnObject.put("items", returnList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnObject;
	}

	protected String getMimeType() {
		return "text/json-comment-filtered";
	}
}