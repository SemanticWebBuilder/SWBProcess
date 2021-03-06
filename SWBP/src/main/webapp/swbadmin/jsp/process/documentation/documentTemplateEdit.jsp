<%-- 
    Document   : documentTemplateEdit
    Created on : 2/10/2014, 01:24:09 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.documentation.DocumentSection"%>
<%@page import="org.semanticwb.process.model.documentation.DocumentTemplate"%>
<%@page import="org.semanticwb.process.model.documentation.TemplateContainer"%>
<%@page import="org.semanticwb.process.resources.documentation.SWPDocumentTemplateResource"%>
<%@page import="java.util.Iterator"%>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(SWPDocumentTemplateResource.PARAM_REQUEST);
    User user = paramRequest.getUser();
    String uridt = request.getParameter("uridt") != null ? request.getParameter("uridt") : "";
    DocumentTemplate docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridt);
    
    SWBResourceURL action = paramRequest.getActionUrl();
    SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);

    String lang = paramRequest.getUser() == null ? "es" : user.getLanguage();

    SWBResourceURL urlrds = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
            .setAction(SWPDocumentTemplateResource.ACTION_REMOVE_DOCUMENT_SECTION);

    SWBResourceURL urleds = paramRequest.getRenderUrl().setMode(SWPDocumentTemplateResource.MODE_EDIT_DOCUMENT_SECTION);

    List<Process> processes = (List<Process>) request.getAttribute(SWPDocumentTemplateResource.LIST_PROCESSES);
    
    if (null == docTemplate) {
        TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology()
                .getGenericObject(request.getParameter("uritc"));
        if (null != tc) {
            docTemplate = tc.getActualTemplate();
        }
    }
if (null != docTemplate) {
    TemplateContainer container = docTemplate.getTemplateContainer();
    SWBResourceURL viewVersion = paramRequest.getRenderUrl().setMode(SWPDocumentTemplateResource.MODE_VIEW_VERSION);
    %>
    <script src="<%= SWBPlatform.getContextPath()%>/swbadmin/jsp/process/utils/jquery.bootstrap-duallistbox.min.js"></script>
    <div class="row no-margin swbp-button-ribbon text-right">
        <a href="<%= paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW) %>"
           class="btn btn-swbp-action" >Volver a la lista</a>
        <a href="<%= viewVersion.setParameter("uritc", container.getURI()) %>" class="btn btn-swbp-action">Versiones</a>
    </div>
    <hr>
    <div class="panel panel-default swbp-panel-head">
        <div class="panel-heading text-center"><%= container.getTitle() %></div>
        <form method="post" id="formTemplate" action="<%= action.setAction(SWBResourceURL.Action_EDIT)%>"
              class="form-horizontal swbp-form" role="form">
            <input type="hidden" name="uridt" value="<%= docTemplate.getURI() %>"/>
            <div class="panel-body swbp-panel-body-card">
                <div class="form-group" id="divtitletc">
                    <label><h5><%=paramRequest.getLocaleString("lblPropTitle")%>:</h5></label>
                    <input type="text" name="titletc" id="titletc" required
                           value="<%= container == null ? docTemplate.getTitle() : container.getTitle()%>"
                           class="form-control"/>
                </div>
                <div class="form-group" id="divtitleTemplate">
                    <label><h5><%=paramRequest.getLocaleString("lblVersion")%>:</h5></label>
                    <%= docTemplate == null ? "" : docTemplate.getVersionValue()%>
                </div>
                <div class="form-group" id="divprocesess">
                    <label><h5><%=paramRequest.getLocaleString("lblPropScope")%>:</h5></label>
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <select multiple="multiple" size="5" name="procesess" id="demo2">
                                <%
                                if (null != processes && !processes.isEmpty()) {
                                    for (Process process : processes) {
                                        String titleProcess = process.getTitle();
                                        String uriProcess = process.getURI();
                                        %>    
                                        <option value="<%= uriProcess%>"
                                                <%= container != null && container.hasProcess(process) ? "selected" : "" %>>
                                            <%= titleProcess%>
                                        </option>
                                        <%
                                    }
                                }
                                %>
                            </select>
                            <script type="text/javascript">
                                var demo2 = $('#demo2').bootstrapDualListbox({
                                    nonSelectedListLabel: 'Disponibles',
                                    selectedListLabel: 'Asignados',
                                    infoTextEmpty: 'No hay procesos',
                                    filterTextClear: 'Borrar filtro',
                                    preserveSelectionOnMove: 'moved',
                                    infoTextFiltered: '{0} de {1}',
                                    infoText: '',
                                    moveSelectedLabel: 'Agregar seleccionado',
                                    moveAllLabel: 'Agregar todos',
                                    removeSelectedLabel: 'Quitar seleccionado',
                                    removeAllLabel: 'Quitar todos',
                                    filterPlaceHolder: 'Filtrar',
                                    moveOnSelect: false
                                });
                            </script>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label>
                        <h5><%=paramRequest.getLocaleString("lblSections")%></h5>
                    </label>
                    <div class="row no-margin text-right">
                        <a
                            href="<%= url.setMode(SWPDocumentTemplateResource.MODE_ADD_DOCUMENT_SECTION)
                            .setParameter("uridt", docTemplate.getURI()).setParameter("uritc", docTemplate.getURI()) %>"
                            data-toggle="modal" data-target="#modalDialog" class="btn btn-default swbp-btn-inter2"
                            role="button">Agregar sección</a>
                    </div>
                </div>
                <%
                if (docTemplate != null) {
                    Iterator<DocumentSection> itds = SWBComparator.sortSortableObject(docTemplate.listDocumentSections());
                    if (itds.hasNext()) {
                        %>
                        <div class="table-responsive-vertical shadow-z-1 swbp-table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th><%=paramRequest.getLocaleString("lblSecActive")%></th>
                                        <th><%=paramRequest.getLocaleString("lblSecTitle")%></th>
                                        <th><%=paramRequest.getLocaleString("lblSecType")%></th>
                                        <th><%=paramRequest.getLocaleString("lblSecOrder")%></th>
                                        <th><%=paramRequest.getLocaleString("lblActions")%></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        while (itds.hasNext()) {
                                            DocumentSection docSection = itds.next();
                                            String titleds = docSection.getTitle();
                                            String typeds = docSection.getSectionType().getDisplayName(lang);
                                            String urids = docSection.getURI();
                                    %>
                                    <tr>
                                        <td data-title="Visible" class="text-center">
                                            <input <%= docSection.isActive() ? "checked=\"true\"" : "" %>
                                                    name="<%= urids%>" id="<%= urids%>" type="checkbox" class="css-checkbox">
                                            <label class="css-label" for="<%= urids%>"></label>
                                        </td>
                                        <td data-title="Nombre"><%= titleds%></td>
                                        <td data-title="Tipo"><%= typeds%></td>
                                        <td data-title="Ordenamiento">
                                            <input type="number" value="<%= docSection.getIndex()%>"
                                                   name="ind<%= docSection.getURI()%>" class="form-control"/></td>
                                        <td data-title="Acciones" class="swbp-action-table">
                                            <a href="<%=urleds.setParameter("urids", urids)%>"
                                               class="btn btn-default col-lg-4 col-md-4" rel="tooltip"
                                               data-original-title="<%=paramRequest.getLocaleString("btnEdit")%>"
                                               title="<%=paramRequest.getLocaleString("btnEdit")%>" >
                                                <span class=" fa fa-pencil"></span>
                                            </a>
                                            <a href="<%= urlrds.setParameter("urids", docSection.getURI()) %>"
                                               class="btn btn-default col-lg-4 col-md-4 btn-ajax-action btn-ajax-action-remove"
                                               rel="tooltip" data-original-title="<%=paramRequest.getLocaleString("btnDelete")%>"
                                               title="<%=paramRequest.getLocaleString("btnDelete")%>" >
                                                <span class="fa fa-trash-o"></span>
                                            </a>
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                        <script>
                            $("a.btn-ajax-action.btn-ajax-action-remove").on("click", function(evt) {
                                evt.preventDefault();
                                if (!confirm("¿Desea eliminar esta sección?")) return false;
                                $.post($(this).attr("href"), function(data) {
                                    window.location.reload(true); 
                                });
                            });
                        </script>
                        <%
                    }
                }
                %>
                <div class="form-group" id="guardaCambios"></div>
            </div>
            <div class="panel-footer text-right">
                <button type="submit" class="btn btn-default">
                    <span class="fa fa-save fa-fw"></span><%= paramRequest.getLocaleString("btnSave")%>
                </button>
            </div>
        </form>
    </div>
    <%
}
%>