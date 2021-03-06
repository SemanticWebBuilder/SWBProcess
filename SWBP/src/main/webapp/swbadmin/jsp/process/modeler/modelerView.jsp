<%--
    Document   : modelerView
    Created on : 7/05/2013, 12:09:42 PM
    Author     : Hasdai Pacheco <develop@hasdaipacheco.com>
--%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.resources.SVGModeler"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    boolean isViewMode = (Boolean) request.getAttribute("isViewMode");
    boolean showStatus = (Boolean) request.getAttribute("showStatus");

    SWBResourceURL exportUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
    exportUrl.setMode(SVGModeler.MODE_EXPORT);
	Process p = null;

    SemanticObject sogo = null;
    GenericObject go = SWBPlatform.getSemanticMgr().getOntology().getGenericObject(request.getParameter("suri"));
    
    if (null != go) {
        sogo = go.getSemanticObject();
    }
    
    if (null != sogo && sogo.instanceOf(Process.sclass)) {
        p = (Process) go;
    } else if (null != sogo && sogo.instanceOf(ProcessInstance.sclass)) {
        p = ((ProcessInstance)go).getProcessType();
    }
    SWBResourceURL commandUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
    commandUrl.setParameter("suri", p.getURI());
    commandUrl.setMode(SVGModeler.MODE_GATEWAY);
    commandUrl.setAction(SVGModeler.ACT_GETPROCESSJSON);
    WebSite adminSite = SWBContext.getAdminWebSite();
    WebSite site = paramRequest.getWebPage().getWebSite();
    
    String loginUrl = "";
    if (site.getURI().equals(adminSite.getURI())) {
    		loginUrl = SWBPlatform.getContextPath()+"/swbadmin/jsp/login.jsp";
    }

    if (!isViewMode) {
			%>
			<head>
				<script type="text/javascript" src="<%=SWBPortal.getContextPath()%>/swbadmin/js/dojo/dojo/dojo.js" data-dojo-config="async: true"></script>
				<script type="text/javascript" src="<%=SWBPortal.getContextPath()%>/swbadmin/js/jquery/jquery.js"></script>
				<link href="<%=SWBPortal.getContextPath()%>/swbadmin/jsp/process/commons/css/modeler.css" rel="stylesheet" type="text/css">
			</head>
			<%
    }
		%>
		<script type="text/javascript" src="<%=SWBPortal.getContextPath()%>/swbadmin/jsp/process/modeler/toolkit.js?v=<%= Math.floor(Math.random() * 100) %>" charset="utf-8"></script>
		<script type="text/javascript" src="<%=SWBPortal.getContextPath()%>/swbadmin/jsp/process/modeler/modeler.js?v=<%= Math.floor(Math.random() * 100) %>" charset="utf-8"></script>
		<body style="margin: 0px;" onload="Modeler.init('modeler', {mode: '<%=  !isViewMode ? "edit" : "view" %>', loginUrl:'<%= loginUrl %>'}, loadProcess);">
		<%
    	if (null != p) {
			if (!isViewMode) {
				%>
				<jsp:include page="modelerToolbar.jsp" flush="true" />
				<div id="modelerContainer" class="modelerprocess" style="margin-left: 66px;">
					<jsp:include page="modelerSVG.jsp" flush="true" />
				</div>
				<%
       } else {
				%>
				<div class="row no-margin swbp-button-ribbon text-right">
					<a href="" onclick="window.history.go(-1); return false;" class="btn btn-swbp-action">Regresar</a>
				</div>
				<hr />
				<div id="swbp-model-container">
					<jsp:include page="modelerSVG.jsp" flush="true" />
				</div>
				<%
			}
		}
    %>
		<form id="svgform" accept-charset="utf-8" method="post" action="<%=exportUrl%>">
			<input type="hidden" id="output_format" name="output_format" value="">
			<input type="hidden" name="suri" value="<%=p.getURI()%>"> <input type="hidden" id="data" name="data" value=""> <input type="hidden" id="viewBox" name="viewBox" value="">
		</form>
		<%
    SWBResourceURL uploadUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
    uploadUrl.setMode(SVGModeler.MODE_GATEWAY).setAction(SVGModeler.ACT_LOADFILE).setParameter("suri", p.getURI());
    %>
		<div class="overlay" id="overlayBackground">
			<div class="loadDialog">
				<p class="titleBar"><%=paramRequest.getLocaleString("lblLoadModel")%></p>
					<span class="loadDialogCloseButton"> <a href="#" onclick="hideLoadDialog();return false;"><%=paramRequest.getLocaleString("lblClose")%></a></span>
					<form action="<%=uploadUrl%>" method="post">
						<iframe id='target_upload_swpFile' name='target_upload_swpFile' src='' style='display: none'></iframe>
						<div class="loadDialogContent">
							<p>
								<input id="swpFile" name="swpFile" type="file" onChange="javascript:validFileType(this);">
								<script type="text/javascript">
									frame = document.getElementById('target_upload_swpFile');
									function validFileType(element) {
										if (!isFileType(element.value, 'swp|xpdl')) {
											element.value = "";
										}
										return false;
									};
									
									function iframeHandler() {
										//Eliminar los handlers del iframe
										if (frame.detachEvent)
											frame.detachEvent("onload", iframeHandler);
										else
											frame.removeEventListener("load", iframeHandler, false);
										
										var content = "";
										if (frame.contentDocument) {
											content = frame.contentDocument.body.innerHTML;
										} else if (frame.contentWindow) {
											content = frame.contentWindow.document.body.innerHTML;
										} else if (frame.document) {
											content = frame.document.body.innerHTML;
										}
										
										Modeler.loadProcess(content);
									};
									
									function uploadjs_swpFile(forma) {
										var encoding = forma.encoding,
												action = forma.action,
												target = forma.target;
										
										forma.encoding = 'multipart/form-data';
										forma.target = 'target_upload_swpFile';
										
										//Agregar los handlers al iframe
										if (frame.attachEvent)
											frame.attachEvent("onload", iframeHandler);
										else
											frame.addEventListener("load", iframeHandler, true);
										
										forma.submit();
										forma.encoding = encoding;
										forma.action = action;
										forma.target = target;
										hideLoadDialog();
										
										return false;
									};
									
									function isFileType(pFile, pExt) {
										if (pFile.length > 0 && pExt.length > 0) {
											var swFormat = pExt + '|',
													sExt = pFile.substring(pFile.indexOf(".")).toLowerCase(),
													sType = '';
											
											while (swFormat.length > 0) {
												sType = swFormat.substring(0, swFormat.indexOf("|"));
												if (sExt.indexOf(sType) !== -1)
													return true;
												
												swFormat = swFormat.substring(swFormat.indexOf("|") + 1);
											}
											
											while (pExt.indexOf("|") !== - 1)
												pExt = pExt.replace('|', ',');
											
											alert("<%=paramRequest.getLocaleString("msgBadFile")%>");
											return false;
										} else {
											return true;
										}
									};
								</script>
							</p>
						</div>
						<div class='buttonRibbon'>
							<input type="submit" value="<%=paramRequest.getLocaleString("lblSend")%>" onclick="uploadjs_swpFile(this.form); return false;" />
							<input type="button" value="<%=paramRequest.getLocaleString("lblCancel")%>" onclick="hideLoadDialog(); return false;" />
						</div>
					</form>
				</div>
			</div>
			<script type="text/javascript">
				function callbackLoad(response) {
					Modeler.loadProcess(response);
					parent.reloadTreeNodeByURI && parent.reloadTreeNodeByURI('<%=p.getURI()%>');
					hideLoadDialog();
				};

        /*
         Utility function: populates the <FORM> with the SVG data
         and the requested output format, and submits the form.
         */
				function submit_download_form(output_format) {
					var form = document.getElementById("svgform");
					var viewBox = document.getElementById("modeler");
					var element = document.getElementById("viewBox");
					
					element.value = "0 0 " + viewBox.getAttribute('width') + " " + viewBox.getAttribute('height');
					
					if (output_format === "svg" || output_format === "png") {
						// Get the SVG element
						var svg = document.getElementsByTagName("svg")[0];
						// Extract the data as SVG text string
						var svg_xml = (new XMLSerializer).serializeToString(svg);
						
						form['data'].value = svg_xml;
					} else if (output_format === "swp") {
						form['data'].value = JSON.stringify(Modeler.getProcessJSON());
					}
					
					// Submit the <FORM> to the server.
					// The result will be an attachment file to download.
					form['output_format'].value = output_format;
					form.submit();
				};
				
				function showLoadDialog() {
					var ov = document.getElementById("overlayBackground");
					if (ov) {
						ov.style.display = "block";
					}
				};

				function hideLoadDialog() {
					var ov = document.getElementById("overlayBackground");
					if (ov) {
						ov.style.display = "none";
					}
				};
				
        <%
        if (p != null) {
					commandUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
					commandUrl.setMode(SVGModeler.MODE_GATEWAY);
					commandUrl.setAction(SVGModeler.ACT_GETPROCESSJSON);
					commandUrl.setParameter("suri", p.getURI());
					%>
					function loadProcess() {
						if (ToolKit && ToolKit !== null) {
							ToolKit.showTooltip(0, "<%=paramRequest.getLocaleString("loading")%>", 200, "Warning");
						}
						
						Modeler.submitCommand('<%=commandUrl%>', null, callbackLoad);
					};
					<%
				}
        
        if (SWBContext.getAdminWebSite().equals(paramRequest.getWebPage().getWebSite())) {
        		commandUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
        		commandUrl.setMode(SVGModeler.MODE_GATEWAY);
        		commandUrl.setAction(SVGModeler.ACT_STOREPROCESS);
        		commandUrl.setParameter("suri", p.getURI());
        		%>
        		function storeProcess() {
        			var json = Modeler.getProcessJSON();
        			var jsonString = "JSONSTART" + JSON.stringify(json) + "JSONEND";
        			if (ToolKit && ToolKit !== null) {
        				ToolKit.showTooltip(0, "<%=paramRequest.getLocaleString("sending")%>", 200, "Warning");
      				}
        			
        			Modeler.submitCommand('<%=commandUrl%>', jsonString, loadProcess);
      			};
      			<%
    			}
    			%>
  			</script>
		</body>
