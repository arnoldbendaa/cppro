<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<div dojoType="dijit.Dialog" id="managerDialog" title="Submit Report" style="display:none;width:400px">
<div class="dialogContent">
	<div id="title" class="heading1">
	</div>
	<form dojoType="dijit.form.Form" action="" id="reportDefForm" method="post">
		<input id="visId" name="visId" type="hidden" />
		<table width="100%" cellspacing="10">
			<tr>
				<td width="60%">
		<div id="raOption">
			Would you like to submit this report for yourself or RA owners ?
		</div>
				</td>
				<td width="40%" align="center">
		<input type="radio" name="raOption" id="selfSelect" value="self" checked="true">Self
		<input type="radio" name="raOption" id="raSelect" value="ra" >RA Owners
				</td>
			</tr>
			<tr>
				<td>
		<div id="rootNodeOption">
			Use this to override the default Root RA node this report will run from
		</div>
				</td>
				<td width="40%" align="center">
		<input type="hidden" id="modelId" name="modelIdOption"/>
		<input id="rootNode" name="rootNodeOption" type="text" readonly="true" onclick="doCCSelect()" />
		<input type="hidden" id="rootNodeId" name="rootNodeIdOption" />
				</td>
			</tr>
			<tr id="depthRow" style="display:none;">
				<td>
		<div id="depthOption">
			Use this dropdown to override the RA depth this report will run to.
		</div>
				</td>
				<td width="40%" align="center">
		<select dojoType="dijit.form.ComboBox" name="depthOption" id="selectedDepth" style="width:80px">
			<option id="-1" >Leaf Only</option>
			<option id="0" >0</option>
			<option id="1" >1</option>
			<option id="2" >2</option>
			<option id="3" >3</option>
			<option id="4" >4</option>
			<option id="5" >5</option>
			<option id="6" >6</option>
			<option id="7" >7</option>
			<option id="8" >8</option>
			<option id="9" >9</option>
			<option id="All" >All</option>
		</select>
				</td>
			</tr>
			<tr>
				<td>
		<div id="groupOption">
			Would you like to group the reports into a single message?
		</div>
				</td>
				<td width="40%" align="center">
		<input type="radio" name="groupOption" value="group" checked="true">Group
		<input type="radio" name="groupOption" value="individual" >Individual
				</td>
			</tr>
			<tr id="paramRow" >
				<td>
		<div id="param">
			Would you like to pass any parameters to the report ?
		</div>
				</td>
				<td>
					<input type="text" name="paramOption" id="paramOption"/>
				</td>
			</tr>
		</table>
	</form>
</div>

<div class="dialogActions">
	<cp:CPButton onclick="doSubmit()" >OK</cp:CPButton>
	<cp:CPButton onclick="closeDialog('managerDialog')" >Cancel</cp:CPButton>
</div>
</div>