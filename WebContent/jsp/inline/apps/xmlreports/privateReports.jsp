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
					Stack privateStack = new Stack(); 
					int idx = 0;
					int levelInPrivateStack = 0;
				%>
                <logic:iterate name="xmlReportsForm" property="folders" id="folder" type="com.cedar.cp.utc.struts.xmlreports.FolderDTO" >
                    <%
                        String folderId = folder.getFolderId();
                        String reportsId = folder.getReportsId();						
						String folderKey = folder.getFolderKey();
                        boolean isPublic = folder.isPublic();
                        String name = folder.getName();
						int level = folder.getLevel();
                    %>
					<% 						
						if (idx == 0) 
						{
							privateStack.push(level);								
						}
						else
						{
							while (!privateStack.empty())
							{
								levelInPrivateStack = Integer.valueOf(privateStack.pop().toString());
								if (level > levelInPrivateStack)
								{
									privateStack.push(levelInPrivateStack);
									privateStack.push(level);
									break;
								}
								else if (level == levelInPrivateStack)
								{
									privateStack.push(level);
									out.println("</div>");
									break;
								}
								else
								{
									out.println("</div>");
								}
							}
						}
						idx++;
					%>
	                    <div class="rowdiv">							
							<div class="celldiv1" style='padding-left: <%=level%>em;' >
								<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/sub.jpg" border="0" alt="Hide" onclick="hideShow('<%=folderId%>', this);"/>
								<bean:write name="folder" property="name" />
	                        </div>
							<div class="celldiv2" >
								<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/new.gif" title="New child folder" onclick="newChildFolder('<%=folderId%>',<%=isPublic%>);" >
								<logic:notEqual name="folder" property="level" value="1">
									<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/settings.gif" onclick="alterFolder('<%=folderKey%>','<%=name%>');" title="Folder properties" />
								</logic:notEqual>			
								<logic:notEqual name="folder" property="level" value="1">
									<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/delete.gif" onclick="deleteFolder('<%=folderKey%>');" title="Delete folder" />
								</logic:notEqual>	
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
									<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/move.gif" title="Move Report" onclick="moveReport('<%=reportId%>');" >
									<img style="cursor:pointer;" src="<%=request.getContextPath()%>/images/delete.gif" onclick="deleteReport('<%=reportId%>');" title="Delete report" />
								</div>
							</div>					
                    	</logic:iterate>				
                </logic:iterate>
				<%
					while (!privateStack.empty())
					{
						privateStack.pop();
						out.println("</div>");
					}
				%>
            </div> <!-- end table div -->