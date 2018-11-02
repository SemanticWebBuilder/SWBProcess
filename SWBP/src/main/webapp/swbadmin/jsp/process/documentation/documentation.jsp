<%--
    Document   : addDocumentation
    Created on : 5/12/2013, 05:36:08 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceModes"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURLImp"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.RepositoryDirectory"%>
<%@page import="org.semanticwb.process.model.RepositoryElement"%>
<%@page import="org.semanticwb.process.model.RepositoryURL"%>
<%@page import="org.semanticwb.process.model.documentation.*"%>
<%@page import="org.semanticwb.process.resources.ProcessFileRepository"%>
<%@page import="org.semanticwb.process.resources.SWPResourcesConfig"%>
<%@page import="org.semanticwb.process.resources.documentation.SWPDocumentationResource"%>
<%@page import="org.semanticwb.process.resources.processmanager.SWBProcessManagerResource"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(SWPDocumentationResource.PARAM_REQUEST);
    SWPDocumentationResource res = (SWPDocumentationResource) request.getAttribute(SWPDocumentationResource.ATT_RESOURCE);
    WebSite site = paramRequest.getWebPage().getWebSite();
    User user = paramRequest.getUser();

    SWPResourcesConfig resConfig = SWPResourcesConfig.getConfgurationInstance(site);
    boolean showWord = res.isEnableWordExport();
    String editTools = "";

    String idp = request.getParameter("idp") != null ? request.getParameter("idp") : "";
    String idpg = request.getParameter("pg") != null ? request.getParameter("pg") : "";
    String lang = user != null && user.getLanguage() != null ? user.getLanguage() : "es";
    Process p = Process.ClassMgr.getProcess(idp, site);
    String pg = request.getParameter("pg") != null ? request.getParameter("pg") : "";
    String pag = request.getParameter("p") != null ? request.getParameter("p") : "";

    SWBResourceURL urlText = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
            .setAction(SWPDocumentationResource.ACTION_EDIT_TEXT);

    String wp = request.getParameter("wp");
    SWBResourceURL urlUpload = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
            .setAction(SWPDocumentationResource.ACTION_UPLOAD_PICTURE);

    if (res.isUseAdvancedEditor()) {
        editTools = "cut copy paste| link unlink | searchreplace | insertdatetime | spellchecker | formatselect fontselect fontsizeselect";
    }

    if (p != null) {
        %>
        <div class="row no-margin swbp-button-ribbon text-right">
            <a href="<%= paramRequest.getWebPage().getParent().getUrl(lang) %>?pg=<%= idpg %>"
               class="btn btn-swbp-action">Regresar</a>
            <a href="<%= resConfig.getTemplatesPage().getUrl() %>" class="btn btn-swbp-action"><%= paramRequest.getLocaleString("lblAdminTemplates") %></a>
        </div>
        <hr>
        <%

        Iterator<TemplateContainer> itTemplateCont = TemplateContainer.ClassMgr.listTemplateContainerByProcess(p);
        if (itTemplateCont == null || !itTemplateCont.hasNext()) {
            %>
            <div class="alert alert-block alert-warning fade in">
                <%=paramRequest.getLocaleString("msgNoTemplate")%>
            </div>
            <%
        } else {
            //Traer datos
            TemplateContainer templateCont = itTemplateCont.next();
            DocumentationInstance di = DocumentationInstance.getDocumentationInstanceByProcess(p, templateCont);
            Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());//Regerar código para ordenamiento
            ArrayList<DocumentSectionInstance> actives = new ArrayList<>();
            while (itdsi.hasNext()) {
                DocumentSectionInstance dsi = itdsi.next();
                if (null != dsi.getSecTypeDefinition() && dsi.getSecTypeDefinition().isActive()) {
                    actives.add(dsi);
                }
            }
            itdsi = actives.iterator();

            SWBResourceURL urlVersion = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
                    .setMode(SWPDocumentationResource.MODE_VERSION).setParameter("uridi", di.getURI());

            SWBResourceURL urlAdminVersion = paramRequest.getRenderUrl().setMode(SWPDocumentationResource.MODE_ADMIN_VERSION);
            urlAdminVersion.setParameter("idp", idp);
            String _rid = request.getParameter("_rid");
            urlAdminVersion.setParameter("wp", request.getParameter("wp"));
            urlAdminVersion.setParameter("pg", request.getParameter("pg"));
            urlAdminVersion.setParameter("_rid", _rid);

            WebPage wpage = WebPage.ClassMgr.getWebPage(wp, site);

            Resource resource = Resource.ClassMgr.getResource(_rid, site);
            SWBResourceURL urlView = new SWBResourceURLImp(request, resource, wpage, SWBResourceModes.UrlType_RENDER);
            urlView.setMode(SWBProcessManagerResource.MODE_VIEW_DOCUMENTATION);
            urlView.setParameter("idp", idp);

            SWBResourceURL urlZip = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
                    .setMode(SWPDocumentationResource.MODE_DOWNLOAD);

            urlZip.setParameter("idp", idp);
            urlZip.setParameter("urip", p.getURI());
            urlZip.setParameter("uridi", di.getURI());

            if (itdsi.hasNext()) {
                %>
                <form id="svgform" accept-charset="utf-8" method="post" action="<%= urlZip %>">
                    <input type="hidden" id="format" name="format" value="">
                    <input type="hidden" name="idp" value="<%= idp %>">
                    <input type="hidden" name="p" value="<%= p %>">
                    <input type="hidden" name="width" value="">
                    <input type="hidden" name="height" value="">
                    <input type="hidden" id="data" name="data" value="">
                    <input type="hidden" id="viewBox" name="viewBox" value="">
                    <input type="hidden" id="taskSelected" name="taskSelected">
                </form>

                <script type="text/javascript">
                    var downloadVersion = function (format) {
                        var size = getDiagramSize(),
                            form = document.getElementById("svgform"),
                            svg = document.getElementsByTagName("svg")[0];

                        if (svg) {
                            form['data'].value = (new XMLSerializer).serializeToString(svg);
                        }

                        form['format'].value = format;
                        form['width'].value = size.w;
                        form['height'].value = size.h;
                        form.submit();
                    };

                    function getDiagramSize() {
                        <%
                        if (null == p.getData() || p.getData().isEmpty()) {
                            %>
                            return {w: 800, h: 600};
                            <%
                        } else {
                            %>
                            var cw = 0,
                                ch = 0,
                                fx = null,
                                fy = null;

                            for (var i = 0; i < ToolKit.contents.length; i++) {
                                var obj = ToolKit.contents[i];
                                if (obj.typeOf && (obj.typeOf("GraphicalElement") || obj.typeOf("Pool"))) {
                                    if (obj.layer === ToolKit.layer) {
                                        var lastX = obj.getX() + obj.getWidth() / 2;
                                        var lastY = obj.getY() + obj.getHeight() / 2;

                                        if (lastX > cw) {
                                            cw = lastX;
                                            fx = obj;
                                        }

                                        if (lastY > ch) {
                                            ch = lastY;
                                            fy = obj;
                                        }
                                    }
                                }
                            }

                            if (fx && fy) {
                                cw = fx.getX() + fx.getWidth() / 2;
                                ch = fy.getY() + fy.getHeight() / 2;
                            } else {
                                cw = 1200;
                                ch = 900;
                            }
                            return {w: cw, h: ch};
                            <%
                        }
                        %>
                    }
                </script>
                <div class="row hidden-margin">
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 hidden-padding pull-right">
                        <a onclick="downloadVersion('<%= SWPDocumentationResource.FORMAT_HTML %>')"
                       class="btn btn-default btn-block swbp-btn-inter2 hidden-margin">Descargar en HTML</a>
                    </div>
                    <%
                    if (showWord) {
                        %>
                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 hidden-padding pull-right">
                            <a onclick="downloadVersion('<%= SWPDocumentationResource.FORMAT_WORD %>')"
                               class="btn btn-default btn-block swbp-btn-inter2 hidden-margin">Descargar en Word</a>
                        </div>
                        <%
                    }
                    %>
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 hidden-padding pull-right">
                        <a href="<%= urlVersion.setParameter("idp", idp) %>"
                           class="btn btn-default btn-block swbp-btn-inter2 hidden-margin"
                           data-toggle="modal" data-target="#modalDialog">Publicar versión</a>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 hidden-padding pull-right">
                        <a href="<%=urlAdminVersion%>"
                           class="btn btn-default btn-block swbp-btn-inter2 hidden-margin">Versiones publicadas</a>
                    </div>
                </div>
                <div class="panel panel-default swbp-panel-head">
                    <div class="panel-heading text-center"><%= p.getTitle() %>
                        <div class="pull-right">
                            <a class="accordion-toggle fa fa-bars fa-lg" data-toggle="collapse"
                               data-parent="#UniqueName" href="#SWBP-MENU-PROCESO"></a>
                        </div>
                    </div>
                </div>
                <div id="SWBP-MENU-PROCESO" class="panel-collapse collapse">
                    <div class="list-group">
                        <%
                        while (itdsi.hasNext()) {
                            DocumentSectionInstance docSectionInstance = itdsi.next();
                            String idDocSectionInstance = docSectionInstance.getId();
                            %>
                                <a href="#<%= idDocSectionInstance%>"
                                    class="list-group-item"> <%=docSectionInstance.getSecTypeDefinition().getTitle()%></a>
                            <%
                        }
                        %>
                    </div>
                </div>
                <div class="panel-body swbp-panel-body">
                    <%
                    itdsi = actives.iterator();
                    while (itdsi.hasNext()) {
                        DocumentSectionInstance docSectionInstance = itdsi.next();
                        String uriDocSectionInstance = docSectionInstance.getURI();
                        String idDocSectionInstance = docSectionInstance.getId();
                        SWBResourceURL urlEdit = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
                                .setParameter("idp", idp);

                        SWBResourceURL urlTrace = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
                                .setMode(SWPDocumentationResource.MODE_TRACEABLE);

                        SWBResourceURL urlRemove = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
                                .setMode(SWPDocumentationResource.MODE_VIEW_REMOVE);

                        SWBResourceURL urlAction = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE)
                                .setParameter("uridsi", uriDocSectionInstance);

                        SemanticClass cls = docSectionInstance.getSecTypeDefinition() != null &&
                                docSectionInstance.getSecTypeDefinition().getSectionType() != null ?
                                docSectionInstance.getSecTypeDefinition().getSectionType().transformToSemanticClass() : null;

                        Iterator<SectionElement> itse = SWBComparator.sortSortableObject(docSectionInstance.listDocuSectionElementInstances());
                        %>
                        <h4 id="<%=idDocSectionInstance%>">
                            <%= docSectionInstance.getSecTypeDefinition().getTitle() %>
                        </h4>
                        <hr>
                        <%
                        //BusinesRole, BusinessRule, Definition, Format, Indicator, Objetive, Policy, Reference y Risk
                        if (cls != null && cls.isSubClass(Instantiable.swpdoc_Instantiable, false)) {
                            %>
                            <div class="row">
                                <div class="col-lg-3 col-md-4 col-sm-5 col-xs-12 pull-right">
                                    <a href="<%= urlEdit.setMode(SWPDocumentationResource.MODE_EDIT_INSTANTIABLE).setParameter("uridsi", uriDocSectionInstance) %>"
                                       class="btn btn-default btn-block swbp-btn-inter2" data-toggle="modal"
                                       data-target="#modalDialog" >
                                        Agregar
                                    </a>
                                </div>
                            </div>
                            <%
                            if (!itse.hasNext()) {
                                %>
                                <p><%= paramRequest.getLocaleString("msgNoSecInfo") %></p>
                                <%
                            } else {
                                String[] propst = docSectionInstance.getSecTypeDefinition().getVisibleProperties().trim().split("\\|");
                                if (!docSectionInstance.getSecTypeDefinition().getVisibleProperties().isEmpty() && propst.length > 0) {
                                    List<String> listtitle = new ArrayList<>();
                                    List<String> listid = new ArrayList<>();
                                    for (String propt : propst) {
                                        listtitle.add(propt.substring(0, propt.indexOf(";")));
                                        listid.add(propt.substring(propt.indexOf(";") + 1));
                                    }
                                    %>
                                    <div class="table-responsive-vertical shadow-z-1 swbp-table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <%
                                                    for (String title : listtitle) {
                                                        %><th><%= title %></th><%
                                                    }
                                                    %>
                                                    <th class="swbp-actions">
                                                        <%= paramRequest.getLocaleString("lblActions") %>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                RepositoryDirectory repositoryDirec = null;
                                                Referable ref = null;
                                                while (itse.hasNext()) {
                                                    SectionElement se = itse.next();
                                                    String uriSectionElement = se.getURI();
                                                    boolean isReference = false;

                                                    if (se instanceof ElementReference) {
                                                        ElementReference er = (ElementReference) se;
                                                        if (er.getElementRef() == null) {
                                                            docSectionInstance.removeDocuSectionElementInstance(er);
                                                            er.remove();
                                                            continue;
                                                        }
                                                        se = (SectionElement) er.getElementRef();
                                                        er.setIndex(se.getIndex());
                                                        isReference = true;
                                                    }
                                                    if (se instanceof Referable) {
                                                        ref = (Referable) se;
                                                        if (ref.getRefRepository() != null) {
                                                            repositoryDirec = ref.getRefRepository().getRepositoryDirectory();
                                                        } else {
                                                            continue;//TODO: Remove instead of ignore broken references
                                                        }
                                                    }
                                                    %>
                                                    <tr id="trse<%= se.getId() %>">
                                                        <%
                                                        SWBFormMgr mgr = new SWBFormMgr(se.getSemanticObject(),
                                                                null, SWBFormMgr.MODE_VIEW);

                                                        for (String idprop : listid) {
                                                            SemanticProperty sp = SWBPlatform.getSemanticMgr()
                                                                    .getVocabulary().getSemanticPropertyById(idprop);
                                                            %>
                                                            <td data-title="<%= sp.getLabel(lang) %>">
                                                                <%
                                                                if (sp.getPropId().equals(Referable.swpdoc_file.getPropId())) {//Propiedad tipo archivo
                                                                    if (ref.getRefRepository() != null) {
                                                                        String titleref = ref.getRefRepository().getTitle() != null ? ref.getRefRepository().getTitle() : "";
                                                                        String idfile = ref.getRefRepository().getId();
                                                                        SWBResourceURL urlDownload = new SWBResourceURLImp(request, repositoryDirec.getResource(), repositoryDirec, SWBResourceModes.UrlType_RENDER);
                                                                        urlDownload.setMode(ProcessFileRepository.MODE_GETFILE);
                                                                        urlDownload.setCallMethod(SWBResourceURL.Call_DIRECT);
                                                                        urlDownload.setParameter("fid", idfile);
                                                                        RepositoryElement re = ref.getRefRepository();
                                                                        VersionInfo vi = ref.getVersion() != null ? ref.getVersion() : re.getLastVersion();
                                                                        urlDownload.setParameter("verNum", vi.getVersionNumber() + "");
                                                                        if (re instanceof org.semanticwb.process.model.RepositoryFile) {
                                                                            %><a href="<%= urlDownload %>"><%= titleref %> <i class="fa fa-download"></i></a><%
                                                                        } else if (re instanceof RepositoryURL) {
                                                                            %><a href="<%= vi.getVersionFile() %>" target="_blank"><%= titleref %> <i class="fa fa-external-link"></i></a><%
                                                                        }
                                                                    }
                                                                } else { //Propiedades de texto
                                                                    %><%= mgr.renderElement(request, sp, SWBFormMgr.MODE_VIEW) %><%
                                                                }
                                                                %>
                                                            </td>
                                                            <%
                                                        }
                                                        %>
                                                        <td data-title="<%=paramRequest.getLocaleString("lblActions")%>"
                                                            class="swbp-action-table">
                                                            <%
                                                            if (!isReference) {
                                                                %>
                                                                <a href="<%= urlEdit.setMode(SWPDocumentationResource.MODE_EDIT_INSTANTIABLE).setParameter("urise", uriSectionElement) %>"
                                                                   class="btn btn-default col-lg-4 col-md-4"
                                                                   title="<%= paramRequest.getLocaleString("btnEdit") %>"
                                                                   data-toggle="modal" data-target="#modalDialog">
                                                                    <span class="fa fa-pencil"></span>
                                                                </a>
                                                                <%
                                                            } else if (se.getDocumentSectionInst() != null
                                                                    && se.getDocumentSectionInst().getDocumentationInstance() != null
                                                                    && se.getDocumentSectionInst().getDocumentationInstance().getProcessRef() != null) {

                                                                String captureUrl = paramRequest.getWebPage().getUrl(lang);
                                                                Process pr = se.getDocumentSectionInst().getDocumentationInstance().getProcessRef();
                                                                %>
                                                                <a href="<%= captureUrl + "?idp=" + pr.getId() + "&pg=" + pg + "&p=" + pag + "#" + se.getDocumentSectionInst().getId() %>"
                                                                   class="btn btn-default col-lg-4 col-md-4 fa fa-gears"
                                                                   target="_blank" data-original-title="<%= pr.getTitle() %>">
                                                                </a>
                                                                <%
                                                            }
                                                            %>
                                                            <a href="<%= urlTrace.setParameter("uritc", se.getURI()) %>"
                                                               class="btn btn-default col-lg-4 col-md-4"
                                                               data-toggle="modal" data-target="#modalDialog">
                                                                <span class="fa fa-info-circle"></span>
                                                            </a>
                                                            <%
                                                            if (se instanceof Referable) {
                                                                Referable refSe = (Referable) se;
                                                                if (null != refSe.getRefRepository()) {
                                                                    String fileSe = refSe.getRefRepository().getURI();
                                                                    %>
                                                                    <a href="<%= urlRemove.setParameter("urise", uriSectionElement).setParameter("uridsi", uriDocSectionInstance).setParameter("title", (null == se.getTitle() ? "elemento" : se.getTitle())).setParameter("_rid", _rid).setParameter("idp", idp).setParameter("wp", wp).setParameter("fileSe", fileSe) %>"
                                                                       class="btn btn-default col-lg-4 col-md-4"
                                                                       data-toggle="modal" data-target="#modalDialog">
                                                                        <span class="fa fa-trash-o"></span>
                                                                    </a>
                                                                    <%
                                                                }
                                                            } else {
                                                                %>
                                                                <a href="<%= urlRemove.setParameter("urise", uriSectionElement).setParameter("uridsi", uriDocSectionInstance).setParameter("title", (null == se.getTitle() ? "elemento" : se.getTitle())).setParameter("_rid", _rid).setParameter("idp", idp).setParameter("wp", wp) %>"
                                                                   class="btn btn-default col-lg-4 col-md-4"
                                                                   data-toggle="modal" data-target="#modalDialog">
                                                                    <span class="fa fa-trash-o"></span>
                                                                </a>
                                                                <%
                                                            }
                                                            %>
                                                        </td>
                                                    </tr>
                                                    <%
                                                }
                                                %>
                                            </tbody>
                                        </table>
                                    </div>
                                <%
                                } else { //No hay propiedades visibles
                                    %>
                                    <div class="alert alert-block alert-warning">
                                        <%= paramRequest.getLocaleString("msgNoSecInfo") %>
                                    </div>
                                    <%
                                }
                            }
                        } else if (cls.equals(FreeText.sclass)) { //FreeText
                            FreeText ft = (FreeText) SWBPlatform.getSemanticMgr().getOntology()
                                    .getGenericObject(docSectionInstance.getDocuSectionElementInstance().getURI());
                            %>
                            <form method="post" class="form-inline">
                                <div class="form-row">
                                    <textarea class="freetext" id="<%= ft.getURI()%>" name="<%= docSectionInstance.getURI() %>">
                                        <% out.println(ft.getText()); %>
                                    </textarea>
                                </div>
                            </form>
                            <%
                        } else if (cls.equals(Model.sclass)) { //Model
                            if (null != p.getData() && !p.getData().isEmpty()) {
                                String svgPath = SWBPortal.getContextPath() + "/swbadmin/jsp/process/modeler/modelerSVG.jsp";
                                %>
                                <script src="<%= SWBPortal.getContextPath() %>/swbadmin/jsp/process/modeler/toolkit.js"></script>
                                <script src="<%= SWBPortal.getContextPath() %>/swbadmin/jsp/process/modeler/modeler.js"></script>
                                <link href="<%= SWBPortal.getContextPath() %>/swbadmin/jsp/process/commons/css/modeler.css" rel="stylesheet" type="text/css">
                                <div id="swbp-model-container" <%= (null == p.getData() || p.getData().isEmpty()) ? "style=\"display:none;\"" : "" %>>
                                    <jsp:include page="<%= svgPath %>" flush="true"/>
                                </div>
                                <script>
                                    Modeler.init('modeler', {mode: 'view', disableKeyEvents: true, layerNavigation: false}, callbackHandler);
                                    var zoomFactor = 1.1,
                                        panRate = 50;

                                    function callbackHandler() {
                                        var strJSON = <%= p.getData() %>;
                                        Modeler.loadProcess(strJSON);

                                        Modeler._svgSize = getDiagramSize();
                                        fitToScreen();

                                        var viewBox = document.getElementById("modeler").getAttribute('viewBox');

                                        setTimeout(function () {
                                            $('#viewBox').val(viewBox);
                                            fitToScreen();
                                        }, 1000);
                                    }

                                    function zoomin() {
                                        var viewBox = document.getElementById("modeler").getAttribute('viewBox');
                                        var viewBoxValues = viewBox.split(' ');

                                        viewBoxValues[2] = parseFloat(viewBoxValues[2]);
                                        viewBoxValues[3] = parseFloat(viewBoxValues[3]);

                                        viewBoxValues[2] /= zoomFactor;
                                        viewBoxValues[3] /= zoomFactor;

                                        document.getElementById("modeler").setAttribute('viewBox', viewBoxValues.join(' '));
                                    }

                                    function zoomout() {
                                        var viewBox = document.getElementById("modeler").getAttribute('viewBox');
                                        var viewBoxValues = viewBox.split(' ');

                                        viewBoxValues[2] = parseFloat(viewBoxValues[2]);
                                        viewBoxValues[3] = parseFloat(viewBoxValues[3]);

                                        viewBoxValues[2] *= zoomFactor;
                                        viewBoxValues[3] *= zoomFactor;

                                        document.getElementById("modeler").setAttribute('viewBox', viewBoxValues.join(' '));
                                    }

                                    function resetZoom() {
                                        var el = document.getElementById("modeler");
                                        el.setAttribute('viewBox', '0 0 ' + $("#modeler").parent().width() + ' ' + $("#modeler").parent().height());
                                        el.setAttribute('width', '1024');
                                        el.setAttribute('height', '768');
                                    }

                                    function handlePanning(code) {
                                        var viewBox = document.getElementById("modeler").getAttribute('viewBox');
                                        var viewBoxValues = viewBox.split(' ');
                                        viewBoxValues[0] = parseFloat(viewBoxValues[0]);
                                        viewBoxValues[1] = parseFloat(viewBoxValues[1]);

                                        switch (code) {
                                            case 'left':
                                                viewBoxValues[0] += panRate;
                                                break;
                                            case 'right':
                                                viewBoxValues[0] -= panRate;
                                                break;
                                            case 'up':
                                                viewBoxValues[1] += panRate;
                                                break;
                                            case 'down':
                                                viewBoxValues[1] -= panRate;
                                                break;
                                        }
                                        document.getElementById("modeler").setAttribute('viewBox', viewBoxValues.join(' '));
                                    }

                                    function fitToScreen() {
                                        resetZoom();
                                        var ws = $("#modeler").parent().width(),
                                            hs = $("#modeler").parent().height(),
                                            wi = Modeler._svgSize.w,
                                            hi = Modeler._svgSize.h;

                                        if (wi > ws || hi > hs) {
                                            var el = document.getElementById("modeler");
                                            el.setAttribute('viewBox', '0 0 ' + wi + ' ' + hi);
                                            el.setAttribute('width', ws);
                                            el.setAttribute('height', hs);
                                        }
                                    }
                                </script>
                                <%
                            } else {
                                %>No se ha encontrado modelo<%
                            }
                        } else if (cls.equals(Activity.sclass)) {
                            DocumentationInstance.updateActivityFromProcess(p, templateCont, docSectionInstance);
                            itse = SWBComparator.sortSortableObject(docSectionInstance.listDocuSectionElementInstances());
                            %>
                            <div class="table-responsive-vertical shadow-z-1 swbp-table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th><%= paramRequest.getLocaleString("lblActivity") %></th>
                                            <th><%= paramRequest.getLocaleString("lblDescription") %></th>
                                            <th class="swbp-actions"><%= paramRequest.getLocaleString("lblActions") %></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                        urlEdit.setParameter("uridsi", docSectionInstance.getURI());
                                        while (itse.hasNext()) {
                                            SectionElement se = itse.next();
                                            String titleSectionElement = se.getTitle() != null ? se.getTitle() : "";
                                            String uriSectionElement = se.getURI();
                                            SWBResourceURL urlRelated = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
                                                    .setMode(SWPDocumentationResource.MODE_RELATED_ACTIVITY)
                                                    .setParameter("idp", idp).setParameter("uridsi", uriDocSectionInstance);

                                            Activity act = (Activity) se.getSemanticObject().createGenericInstance();
                                            String fill = act.getFill() != null ? ("color: #" + act.getFill()) : "";
                                            SWBResourceURL urlFill = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
                                                    .setAction(SWPDocumentationResource.ACTION_UPDATE_FILL);

                                            String text = act.getDescription() != null ? act.getDescription() : "";
                                            %>
                                            <tr>
                                                <td data-title="<%= paramRequest.getLocaleString("lblTitle") %>"><%= titleSectionElement %></td>
                                                <td data-title="<%= paramRequest.getLocaleString("lblDescription") %>">
                                                    <%= text %>
                                                </td>
                                                <td data-title="<%= paramRequest.getLocaleString("lblActions") %>" class="swbp-action-table">
                                                    <a href="<%= urlEdit.setMode(SWPDocumentationResource.MODE_EDIT_DESCRIPTION).setParameter("urise", uriSectionElement).setParameter("d", new Date().getTime() + "") %>"
                                                       class="btn btn-default col-lg-4 col-md-4" title="<%= paramRequest.getLocaleString("btnEdit") %>"
                                                       data-toggle="modal" data-target="#modalDialog">
                                                        <span class="fa fa-pencil"></span>
                                                    </a>
                                                    <div class="dropdown">
                                                        <a href=""
                                                           class="btn btn-default col-lg-4 col-md-4"
                                                           data-toggle="dropdown"><span class="fa fa-tint" style="<%= !fill.isEmpty() ? fill : "" %>"></span></a>
                                                        <ul class="dropdown-menu list-inline" role="menu">
                                                            <li>
                                                                <a href="<%= urlFill.setParameter("fill", "defaultFill").setParameter("urise", act.getURI()) %>" class="colorPicker">
                                                                    <span class="fa fa-ban" id="defaultFill"></span>
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="<%= urlFill.setParameter("fill", "428bca").setParameter("urise", act.getURI()) %>" class="colorPicker">
                                                                    <span class="fa fa-square" id="428bca" style="color: #428bca;"></span>
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="<%= urlFill.setParameter("fill", "5cb85c").setParameter("urise", act.getURI()) %>" class="colorPicker">
                                                                    <span class="fa fa-square" id="5cb85c" style="color: #5cb85c;"></span>
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="<%= urlFill.setParameter("fill", "5bc0de").setParameter("urise", act.getURI()) %>" class="colorPicker">
                                                                    <span class="fa fa-square"  id="5bc0de" style="color: #5bc0de;"></span>
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="<%= urlFill.setParameter("fill", "f0ad4e").setParameter("urise", act.getURI()) %>" class="colorPicker">
                                                                    <span class="fa fa-square" id="f0ad4e" style="color: #f0ad4e;"></span>
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="<%= urlFill.setParameter("fill", "d9534f").setParameter("urise", act.getURI()) %>" class="colorPicker">
                                                                    <span class="fa fa-square" id="d9534f" style="color: #d9534f;"></span>
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                    <a href="<%= urlRelated.setParameter("urise", se.getURI()) %>" class="btn btn-default col-lg-4 col-md-4" title="<%= paramRequest.getLocaleString("lblRelated") %>"
                                                       data-toggle="modal" data-target="#modalDialog">
                                                        <span class="fa fa-link"></span>
                                                    </a>
                                                </td>
                                            </tr>
                                            <%
                                        }
                                        %>
                                    </tbody>
                                </table>
                            </div>
                            <%
                        }
                    }
                    %>
                </div>
                <script type="text/javascript" src="<%= SWBPortal.getContextPath() %>/swbadmin/jsp/process/utils/tinymce/tinymce.min.js"></script>
                <script type="text/javascript">
                    (function () {
                        $(document).ready(function () {
                            $(".colorPicker").on("click", function (evt) {
                                var ele = evt.target,
                                    link = ele && $(ele).closest("a.colorPicker");

                                if (link) {
                                    $.ajax({
                                        url: link.attr("href"),
                                        type: 'POST',
                                        success: function (data) {
                                            if (data.status === "ok") {
                                                window.location.reload();
                                            }
                                        }
                                    });
                                }
                                evt.preventDefault();
                            });
                        });
                    })();

                    tinymce.init({
                        selector: 'textarea.freetext',
                        entity_encoding: "raw",
                        elementpath: false,
                        language: '<%= paramRequest.getUser().getLanguage() %>',
                        toolbar1: "save | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | \n\
                                        bullist numlist outdent indent | link image table | undo redo code | forecolor backcolor emoticons",
                        toolbar2: "<%= editTools %>",
                        menubar: false,
                        save_enablewhendirty: false,
                        force_br_newlines: true,
                        paste_data_images: true,
                        force_p_newlines: true,
                        relative_urls: true,
                        remove_script_host: false,
                        branding:false,
                        statusbar: false,
                        height: '300px',
                        plugins: [
                            " fullpage save advlist table contextmenu link image textcolor code paste"
                        ],
                        save_onsavecallback: function (ed) {
                            var content = ed.getContent(), id = ed.id;
                            $.ajax({
                                url: '<%= urlText %>',
                                cache: false,
                                data: {data: content, urise: id},
                                type: 'POST',
                                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                                success: function (data) {
                                    if (data && data.status === "ok") {
                                        ed.notificationManager.open({
                                            text: 'Se ha guardado el contenido.'
                                        });
                                    }
                                }
                            });
                        },
                        file_browser_callback: function (field_name, url, type, win) {
                            tinymce.activeEditor.windowManager.open({
                                title: "Cargar archivo",
                                url: "<%= SWBPortal.getContextPath() %>/swbadmin/jsp/process/documentation/uploader.jsp?modelid=<%= wpage.getWebSiteId() %>&urlact=<%= urlUpload %>",
                                width: 600
                            }, {
                                oninsert: function (url, closeDialog) {
                                    win.document.getElementById(field_name).value = url;
                                    if (closeDialog) {
                                        setTimeout(function () {
                                            tinymce.activeEditor.windowManager.close();
                                        }, 500);
                                    }
                                }
                            });
                        }
                    });
                </script>
                <%
            } else {
                %>
                <div class="alert alert-block alert-warning">
                    No hay secciones para mostrar. Agregue secciones a la plantilla de documentación asociada.
                </div>
                <%
            }
        }
    }
%>
