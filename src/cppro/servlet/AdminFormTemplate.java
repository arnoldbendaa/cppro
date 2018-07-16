package cppro.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.DeflaterOutputStream;

import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.ejb.base.common.util.HttpUtils;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.softproideas.app.core.datatype.model.DataTypeCoreDTO;
import com.softproideas.app.core.financecube.mapper.FinanceCubeCoreMapper;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreWithGlobalDTO;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDetailsDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationTreeDTO;
import com.softproideas.app.flatformtemplate.generate.model.GenerateDTO;
import com.softproideas.app.flatformtemplate.template.model.TemplateDTO;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import com.softproideas.app.flatformtemplate.template.service.TemplateService;
import com.softproideas.app.flatformtemplate.template.service.TemplateServiceImpl;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.tree.NodeStateDTO;

import cppro.beans.UserAccount;
import cppro.utils.DBUtils;
import cppro.utils.MyUtils;
import org.apache.poi.ss.usermodel.Workbook;
/**
 * Servlet implementation class AdminFormTemplate
 */
@WebServlet("/AdminFormTemplate")
public class AdminFormTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminFormTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession(false);
		UserAccount user = (UserAccount) MyUtils.getLoginedUser(session);
		int userId = user.getUserId();
		String userName = user.getUserName();

		if (request.getParameterMap().containsKey("method")) {
			String method = request.getParameter("method");
			if (method.equals("financeCubes")) {
				List<FinanceCubeModelCoreDTO> result = DBUtils.browseFinanceCubes(conn, userId);
				String json = new Gson().toJson(result);
				response.getWriter().append(json).flush();
				return;
			}
			if (method.equals("configurationFormTemplate")) {
				try {
					ConfigurationTreeDTO result = DBUtils.browseConfigurations(false);
					UUID confUUID = result.getConfigurationUUID();
					String confVisId = result.getConfigurationVisId();
					UUID parentUUID = result.getParentUUID();
					int versionNum = result.getVersionNum();
					String type = result.getType();
					List<ConfigurationTreeDTO> children = result.getChildren();
					String text = result.getText();
					NodeStateDTO state = result.getState();
					boolean dictionary = result.isDirectory();

					JSONObject json = new JSONObject();
					try {
						json.put("confVisId", confVisId);
						json.put("parentUUID", parentUUID);
						json.put("versionNum", versionNum);
						json.put("type", type);
						json.put("children", children);
						json.put("state", state);
						json.put("dictionary", dictionary);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					response.setContentType("application/json");
					response.getWriter().append(json.toString()).flush();
					return;

				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (method.equals("templateGenerate")) {
				try {
					TemplateDetailsDTO result = DBUtils.browseTemplates(false);
					String json = new Gson().toJson(result);
					response.setContentType("application/json");
					response.getWriter().append(json).flush();
					return;
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (method.equals("templateMoldel")) {
				try {
					List<ModelCoreWithGlobalDTO> result = DBUtils.browseModelsForLoggedUser(userId);
					String json = new Gson().toJson(result);
					response.getWriter().append(json).flush();
					return;

				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (method.equals("templateDataType")) {
				try {
					List<DataTypeCoreDTO> result = DBUtils.browseDataTypes();
					String json = new Gson().toJson(result);
					response.getWriter().append(json).flush();
					return;
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (method.equals("fetchTemplate")) {
				String uuid = request.getParameter("uuid");
				UUID templateUUID = UUID.fromString(uuid);
				TemplateService templateService = new TemplateServiceImpl();
				try {
					try {
						AllUsersELO allUsers = DBUtils.getAllUsers();
						TemplateDetailsDTO result = DBUtils.fetchTemplate(templateUUID, allUsers);
						String json = new Gson().toJson(result);
						response.getWriter().append(json).flush();
						return;

					} catch (ClassNotFoundException | SQLException | DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (method.equals("configurations")) {
				String method1 = request.getParameter("method1");
				if (method1.equals("fetchConfiguration")) {
					String uuid = request.getParameter("uuid");
					try {
						ConfigurationDetailsDTO result = DBUtils.fetchConfiguration(UUID.fromString(uuid));
						String json = new Gson().toJson(result);
						response.getWriter().append(json).flush();
						return;
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			if (method.equals("exportToXls")) {
				UUID templateUUID = UUID.fromString(request.getParameter("templateUUID"));
				AllUsersELO allUsers;
				try {
		            allUsers = DBUtils.getAllUsers();
		            TemplateDetailsDTO templateDetails = DBUtils.fetchTemplate(templateUUID, allUsers);
		            
		            String description = templateDetails.getDescription();
		            String fileName = templateDetails.getVisId();
		            if (!description.equals("")) {
		              fileName = fileName + " - " + templateDetails.getDescription();
		            }
		            DBUtils dbUtils = new DBUtils();
		            byte[] excelFile = IOUtils.toByteArray(dbUtils.convertJsonToExcel(URI.create("http://localhost:9170"), templateDetails.getJsonForm(), "", "xlsx", new Boolean[] { Boolean.valueOf(false) }));
		            
		            response.setContentType("application/force-download");
		            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		            response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		            OutputStream out = response.getOutputStream();
		            out.write(excelFile);
		            response.flushBuffer();
				} catch (ClassNotFoundException|ServiceException|SQLException|DaoException|EJBException|ValidationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (method.equals("rename")) {

			}
		}

		RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/flatformtemplateApp/jsp/main.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
        Connection conn = MyUtils.getStoredConnection(request);
		int userId = user.getUserId();
		DBUtils.userId = userId;
		DBUtils dbutils = new DBUtils();

		String data = MyUtils.getInputStream(request);
		JsonParser parser = new JsonParser();
		JsonElement mJson = parser.parse(data);
		Gson gson = new Gson();

		if (request.getParameterMap().containsKey("method")) {
			String method = request.getParameter("method");
			if(method.equals("template")){
				String method1 = request.getParameter("method1");
				TemplateDetailsDTO templateDetailsDto = gson.fromJson(mJson, TemplateDetailsDTO.class);
				if(method1.equals("insertTree")){
			        TemplateDTO returnedtemplate;
					try {
						returnedtemplate = dbutils.insertTemplate(templateDetailsDto);
						String jsonOut = new Gson().toJson(returnedtemplate);
						response.getWriter().append(jsonOut).flush();
						return;

					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(method.equals("configurations")){
				String method1 = request.getParameter("method1");
				ConfigurationDTO configTreeDto = gson.fromJson(mJson, ConfigurationDTO.class);
				if(method1.equals("insertTree")){
					try {
						ConfigurationTreeDTO temp = new ConfigurationTreeDTO();
						temp.setConfigurationUUID(configTreeDto.getConfigurationUUID());
						temp.setConfigurationVisId(configTreeDto.getConfigurationVisId());
						temp.setDirectory(configTreeDto.isDirectory());
						temp.setParentUUID(configTreeDto.getParentUUID());
						temp.setIndex(configTreeDto.getIndex());
						temp.setVersionNum(configTreeDto.getVersionNum());
						ConfigurationTreeDTO returnedconfiguration = dbutils.insertConfiguration(temp);
						String jsonOut = new Gson().toJson(returnedconfiguration,ConfigurationDTO.class);
						response.getWriter().append(jsonOut).flush();
						return;

					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		//get user Id 
		GenerateDTO generateDto = gson.fromJson(mJson, GenerateDTO.class);
		try {
			ResponseMessage responseMessage = dbutils.generateForms(generateDto);
			String jsonOut = new Gson().toJson(responseMessage);
			response.getWriter().append(jsonOut).flush();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		DBUtils dbutils = new DBUtils();

		if (method.equals("save") || method.equals("rename")) {
			String data = MyUtils.getInputStream(request);
			try {
				JsonParser parser = new JsonParser();
				JsonElement mJson = parser.parse(data);
				Gson gson = new Gson();
				TemplateDetailsDTO templateDetailsDto = gson.fromJson(mJson, TemplateDetailsDTO.class);

				ResponseMessage responseMessage = null;
				if (method.equals("save"))
					responseMessage = dbutils.updateTemplate(templateDetailsDto, true);
				else
					responseMessage = dbutils.updateTemplate(templateDetailsDto, false);
				String jsonOut = new Gson().toJson(responseMessage);
				response.getWriter().append(jsonOut).flush();
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (method.equals("configurations")) {
			String method1 = request.getParameter("method1");
			String data = MyUtils.getInputStream(request);

			if(method1.equals("update")){
				UUID uuid = UUID.fromString(request.getParameter("uuid"));
				try {
					JsonParser parser = new JsonParser();
					JsonElement mJson = parser.parse(data);
					Gson gson = new Gson();
					ConfigurationDetailsDTO configurationDetailsDto = gson.fromJson(mJson, ConfigurationDetailsDTO.class);

					ResponseMessage responseMessage = null;

					responseMessage = dbutils.updateConfiguration(configurationDetailsDto);
					String jsonOut = new Gson().toJson(responseMessage);
					response.getWriter().append(jsonOut).flush();
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(method1.equals("rename")){
				UUID uuid = UUID.fromString(request.getParameter("uuid"));
				JsonParser parser = new JsonParser();
				JsonElement mJson = parser.parse(data);
				Gson gson = new Gson();
				try {
					JsonObject jobject = mJson.getAsJsonObject();
					String visId = jobject.get("configurationVisId").toString();
					int versionNumber= Integer.parseInt(jobject.get("versionNum").toString());
					ResponseMessage responseMessage = dbutils.updateConfigurationName(uuid, visId, versionNumber);
					String jsonOut = new Gson().toJson(responseMessage);
					response.getWriter().append(jsonOut).flush();

				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DBUtils dbutils = new DBUtils();

		String method = request.getParameter("method");
		if (method.equals("delete")) {
			UUID uuid = UUID.fromString(request.getParameter("uuid"));
			try {
				ResponseMessage responseMessage = dbutils.deleteTemplate(uuid);
				String jsonOut = new Gson().toJson(responseMessage);
				response.getWriter().append(jsonOut).flush();

			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method.equals("configurations")){
			UUID uuid = UUID.fromString(request.getParameter("uuid"));
			try {
				ResponseMessage responseMessage = dbutils.deleteConfiguration(uuid);
				String jsonOut = new Gson().toJson(responseMessage);
				response.getWriter().append(jsonOut).flush();

			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	protected List<UserCoreDTO> jsonArrayToArrayList(JSONArray objAvailableUsers) {
		try {
			List<UserCoreDTO> list = new ArrayList<UserCoreDTO>();
			if (objAvailableUsers != null) {
				int len = (objAvailableUsers).length();
				for (int i = 0; i < len; i++) {
					JSONObject item = objAvailableUsers.getJSONObject(i);
					UserCoreDTO dto = new UserCoreDTO(item.getInt("userId"), item.getString("userName"),
							item.getString("userFullName"), item.getBoolean("userDisabled"));
					list.add(dto);

				}

			}
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	protected List<WorksheetDTO> jsonArrayToWorkSheets(JSONArray objWorksheets) {
		try {
			List<WorksheetDTO> list = new ArrayList<WorksheetDTO>();
			if (objWorksheets != null) {
				int len = (objWorksheets).length();
				for (int i = 0; i < len; i++) {
					JSONObject item = objWorksheets.getJSONObject(i);
					WorksheetDTO worksheetDto = new WorksheetDTO();

					boolean isValid = item.getBoolean("isValid");
					String name = item.getString("name");
					Map<String, String> properties = toMap(item.getJSONObject("properties"));

					JSONArray jsonCells = item.getJSONArray("cells");

					// UserCoreDTO dto = new
					// UserCoreDTO(item.getInt("userId"),item.getString("userName"),item.getString("userFullName"),item.getBoolean("userDisabled"));
					// list.add(dto);

				}

			}
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Map<String, String> toMap(JSONObject object) throws JSONException {
		Map<String, String> map = new HashMap<String, String>();

		Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, (String) value);
		}
		return map;
	}

	public List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}


}
