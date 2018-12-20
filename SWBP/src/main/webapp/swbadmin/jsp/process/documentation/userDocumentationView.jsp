<%-- 
    Document   : userDocumentationView
    Created on : 7/05/2013, 12:09:42 PM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.mx>
--%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.Role"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.documentation.Documentation"%>
<%@page import="org.semanticwb.process.model.documentation.DocumentationInstance"%>
<%@page import="org.semanticwb.process.resources.processmanager.SWBProcessManagerResource"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="org.semanticwb.process.resources.SWPResourcesConfig" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	SWBProcessManagerResource res = (SWBProcessManagerResource) request.getAttribute(SWBProcessManagerResource.ATT_RESOURCEINSTANCE);
	SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
	User user = paramRequest.getUser();
	WebSite site = paramRequest.getWebPage().getWebSite();
	String idp = request.getParameter("idp");
	Process process = Process.ClassMgr.getProcess(idp, site);
	String version = request.getParameter("version") != null ? request.getParameter("version") : "";
	Documentation actualVersion = null;
	DocumentationInstance di = null;
	
	if (null != process) {
		actualVersion = Documentation.getActualDocumentationVersion(process);
		if (null != actualVersion && !version.isEmpty()) {
			actualVersion = Documentation.ClassMgr.getDocumentation(version, site);
			idp = actualVersion.getProcess().getId();
		}
		
		di = null != actualVersion ? actualVersion.getDocumentationInstance() : null;
	}
	
	Role docRole = SWPResourcesConfig.getConfgurationInstance(site).getDocumenterRole();
	Role adminRole = SWPResourcesConfig.getConfgurationInstance(site).getAdminRole();
	boolean isDocumenter = user.hasRole(docRole) || user.hasRole(adminRole);
	
	String idpg = request.getParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP);
	if (null == idpg) idpg = "";

%>
<div class="row no-margin swbp-button-ribbon text-right">
    <a
        href="<%= paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP, idpg) %>"
        class="btn btn-swbp-action" title="<%= paramRequest.getLocaleString("lblBack") %>">
        <%= paramRequest.getLocaleString("lblBack") %>
    </a>
</div>
<hr>
<%
	if (null == di) {
		String msg = null == process ? paramRequest.getLocaleString("msgModelError") :
                paramRequest.getLocaleString("lblNoDocumentation");
		%>
		<div class="alert alert-block alert-warning fade in">
			<%= msg %>
		</div>
		<%
	} else {
		String filedoc = actualVersion.getDocWorkPath();
		filedoc += "/index.html";
		File file = new File(filedoc);
		
		if (!file.exists()) {
			%>
			<div class="row no-margin swbp-button-ribbon text-right">
				<a
                    href="<%= paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP, idpg) %>"
                    class="btn btn-swbp-action" title="<%= paramRequest.getLocaleString("lblBack") %>">
					<%= paramRequest.getLocaleString("lblBack") %>
				</a>
			</div>
			<hr>
			<div class="alert alert-block alert-warning fade in">
				<%= paramRequest.getLocaleString("msgNoDocFile") %>
			</div>
			<%
		} else {
			%>
			<div class="panel panel-default swbp-panel-head">
				<div class="panel-heading text-center"><%= process.getTitle() %> (<%= actualVersion.getVersionValue() %>)
					<div class="pull-right hidden-print">
						<a class="accordion-toggle fa fa-bars fa-lg" data-toggle="collapse"
                           data-parent="#UniqueName" href="#SWBP-MENU-PROCESO"></a>
					</div>
				</div>
				<%
				FileInputStream in = new FileInputStream(file);
				out.print(SWBUtils.IO.readInputStream(in,"UTF-8"));
				%>
			</div>
			<script type="text/javascript">
				var winVar;
				$(document).ready(function() {
					var draggingSVG = false, x0SVG, y0SVG;
					var urllocation = window.location + '';
					
					$("svg").mousedown(function(event) {
						draggingSVG = true;
						x0SVG = event.pageX;
						y0SVG = event.pageY;
						$(this).attr('style', 'cursor:move;');
					});
					
					$("svg").mousemove(function(event) {
						if (draggingSVG) {
							var viewBox = document.getElementById("modeler").getAttribute('viewBox');
							var viewBoxValues = viewBox.split(' ');
							viewBoxValues[0] -= event.pageX - x0SVG;
							viewBoxValues[1] -= event.pageY - y0SVG;
							document.getElementById("modeler").setAttribute('viewBox', viewBoxValues.join(' '));
							x0SVG = event.pageX;
							y0SVG = event.pageY;
						}
					});
					
					$("svg").mouseup(function() {
						draggingSVG = false;
						$(this).attr('style', 'cursor:default;');
					});
					
					$("svg").mouseout(function() {
						draggingSVG = false;
					});
					
					urllocation = (urllocation.lastIndexOf('#') > 0) ?
                        urllocation.substring((urllocation.lastIndexOf('#') + 1), urllocation.length) : null;
					
					if (urllocation) {
						$('#myTab0 a[href=#' + urllocation + ']').tab('show');
					}
					
					$("#collapseModelButton").on("click", function() {
						if (!($(this).data('data-state')) || $(this).data('data-state') === "off") {
							$(this).button('toggle');
							$(this).data('data-state', 'on');
							$(this).text('Modelo');
							$(this).removeClass('collapseModelButtonSelected');
						} else {
							$(this).addClass('collapseModelButtonSelected');
							$(this).button('toggle');
							$(this).text('Modelo');
							$(this).data('data-state', 'off');
						}
					});
					
					<%
					if (isDocumenter) {
						%>
						$("#collapseVersionButton").on("click", function() {
							var lblTitle = $('#lblTitle');
							if (!($(this).data('data-state')) || $(this).data('data-state') === "off") {
								$(this).button('toggle');
								$(this).data('data-state', 'on');
								$(this).text('<%= paramRequest.getLocaleString("lblHideVersion") %>');
								lblTitle.html('');
							} else {
								$(this).button('toggle');
								$(this).text('<%= paramRequest.getLocaleString("lblShowVersion") %>');
								$(this).data('data-state', 'off');
								lblTitle.html('<%= actualVersion != null ? (actualVersion.getTitle() != null ? actualVersion.getTitle() : "") : "" %>');
							}
						});
						<%
					}
					%>
					
					$("rect[class~='task']").on("click", function() {
						var task = $(this), idto = task.attr('id');
						idto = idto.substring((idto.lastIndexOf(":") + 1), idto.length);
						$('html,body').animate({scrollTop: $("#" + idto).offset().top - 50}, 1000);
					});

					var viewBox = document.getElementById("modeler").getAttribute('viewBox');
					var element = document.getElementById("viewBox");
					element.value = viewBox;
				});
				
				function removeClassAct(liactivity) {
					window.clearTimeout(winVar);
					winVar = window.setTimeout(function() {
						liactivity.removeClass('liselectedUDV');
					}, 3000);
				}
				
				function submit_download_form(output_format) {
					var form = document.getElementById("svgform");
					if (output_format === "svg" || output_format === "png") {
						var svg = document.getElementsByTagName("svg")[0];
						var svg_xml = (new XMLSerializer).serializeToString(svg);
						form['data'].value = svg_xml;
					}
					form['output_format'].value = output_format;
					form.submit();
				}
			</script>
			<%
			SWBResourceURL exportUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
			exportUrl.setMode(SWBProcessManagerResource.MODE_EXPORT_MODEL);
			%>
			<form id="svgform" accept-charset="utf-8" method="post" action="<%= exportUrl %>">
				<input type="hidden" id="output_format" name="output_format" value="">
				<input type="hidden" name="idp" value="<%= idp %>">
				<input type="hidden" id="data" name="data" value="">
				<input type="hidden" id="viewBox" name="viewBox" value="">
				<input type="hidden" id="taskSelected" name="taskSelected">
			</form>
			<%
		}
	}
%>