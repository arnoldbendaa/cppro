<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.Stack" %>
			<div class="tablediv"> <!-- open table div -->
				<div class="rowdiv">
					<div class="thLefDiv"><bean:message key="cp.reports.reports"/></div>
					<div class="thRightDiv"><bean:message key="cp.reports.list.action"/></div>
				</div>
				<% 
					Stack stack = new Stack(); 
					int idx = 0;
					int levelInStack = 0;
				%>
                <logic:iterate name="xmlReportsForm" property="folders" id="folder" type="com.cedar.cp.utc.struts.xmlreports.FolderDTO" >
                    <%
                        String folderId = folder.getFolderId();
                        String reportsId = folder.getReportsId();						
						String folderKey = folder.getFolderKey();
                        boolean isPublic = folder.isPublic();
                        String name = folder.getName();
						int level = folder.getLevel();
						boolean isAdministrator = folder.isEditable();
                    %>
					<% 						
						if (idx == 0) 
						{
							stack.push(level);		
							idx++;
						}
						else
						{
							while (!stack.empty())
							{
								levelInStack = Integer.valueOf(stack.pop().toString());
								if (level > levelInStack)
								{
									// if new level is smaller previous level, just push new level into stack
									stack.push(levelInStack);
									stack.push(level);
									break;
								}
								else if (level == levelInStack)
								{
									// if new level is equal previous level, 
									// close div, push new level into stack and exit loop
									stack.push(level);
									out.println("</div>");
									break;
								}
								else
								{
									// if new level is higher previous level, close div
									out.println("</div>");
								}
							}
						}
						
					%>
	                    <div class="rowdiv">							
							<div class="celldiv1" style='padding-left: <%=level%>em;' >
								<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/sub.jpg" border="0" alt="Hide" onclick="hideShow('<%=folderId%>', this);"/>
								<bean:write name="folder" property="name" />
	                        </div>
							<div class="celldiv2" >
							<%  
								if (isAdministrator == true )
								{
							%>
								<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/new.gif" title="New child folder" onclick="newChildFolder('<%=folderId%>',<%=isPublic%>);" >
								<logic:notEqual name="folder" property="level" value="1">
									<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/settings.gif" onclick="alterFolder('<%=folderKey%>','<%=name%>');" title="Folder properties" />
								</logic:notEqual>			
								<logic:notEqual name="folder" property="level" value="1">
									<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/delete.gif" onclick="deleteFolder('<%=folderKey%>');" title="Delete folder" />
								</logic:notEqual>	
							<%
								}
							%>
							</div>
	                    </div>
						
						<div id='<%=folderId%>'>
                    	<logic:iterate name="folder" property="reports" id="report"  type="com.cedar.cp.utc.struts.xmlreports.FolderReportDTO">
						<%
							String reportId = report.getReportId();
						%>
		                    <div class="rowdiv">
								<div class="celldiv1" style='padding-left: <%=level+1%>em;' onmouseover="selectReportItem(this)">
		                            <%
		                                String scriptString = "javascript:openReportView( '" + report.getReportId() + "' )";
		                            %>
		                            <html:link href="<%=scriptString%>">
		                                <img src="<%=request.getContextPath()%>/images/squares_0.gif" border="0" alt=""/>
		                                <bean:write name="report" property="title" />
		                            </html:link>
		                        </div>
								<div class="celldiv2">
								<%  
									if (isAdministrator == true )
									{
								%>
									<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/move.gif" title="Move Report" onclick="moveReport('<%=reportId%>');" >
									<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/delete.gif" onclick="deleteReport('<%=reportId%>');" title="Delete report" />
								<%
									}
								%>
								</div>
							</div>					
                    	</logic:iterate>				
                </logic:iterate>
				<%
					// write closing tag
					while (!stack.empty())
					{
						stack.pop();
						out.println("</div>");
					}
				%>
            </div> <!-- end table div -->