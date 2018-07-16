<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>
<body >
	<div dojoType="dijit.layout.TabContainer" tabStrip="false" style="width: 100%;height:400px" tabPosition="top">
		<logic:iterate id="role" name="menuForm" property="actions" indexId="tabIndex">
			<div id="menuTab<bean:write name="tabIndex"/>" dojoType="dijit.layout.ContentPane" title='<bean:write name="role" property="description" />'
				<logic:equal name="tabIndex" value="0">selected="true"</logic:equal> >
				<ul>
					<logic:iterate id="option" name="role" property="options" >
						<li onmouseover="mouseOver(this)" onmouseout="mouseOut(this)" onclick="<bean:write name="option" property="action"/>" class="menuItem">
							<bean:write name="option" property="label"/>
						</li>
					</logic:iterate>
				</ul>
			</div>
		</logic:iterate>
	</div>
</body>

