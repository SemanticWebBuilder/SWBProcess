<%-- 
    Document   : relateActivity
    Created on : 9/12/2014, 10:37:32 AM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.model.VersionInfo"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceModes"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURLImp"%>
<%@page import="org.semanticwb.process.model.RepositoryDirectory"%>
<%@page import="org.semanticwb.process.model.RepositoryElement"%>
<%@page import="org.semanticwb.process.model.RepositoryURL"%>
<%@page import="org.semanticwb.process.resources.ProcessFileRepository"%>
<%@page import="org.semanticwb.process.resources.documentation.SWPDocumentationResource"%>
<%@page import="org.semanticwb.process.model.documentation.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(SWPDocumentationResource.PARAM_REQUEST);

    String uridsi = request.getParameter("uridsi") != null ? request.getParameter("uridsi") : "";
    String urise = request.getParameter("urise") != null ? request.getParameter("urise") : "";

    DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridsi);
    Activity a = (Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urise);

    Iterator<DocumentSectionInstance> itdsi = null;
    DocumentationInstance di = null;
    Map map = new HashMap();
    if (dsi != null) {
        di = dsi.getDocumentationInstance();
        itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
        if (a != null) {
            Iterator<SectionElementRef> itser = a.listSectionElementRefs();
            while (itser.hasNext()) {
                SectionElementRef ser = itser.next();
                map.put(ser.getSectionElement(), ser);
            }
        }
    }
    SWBResourceURL urlAction = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
            .setAction(SWPDocumentationResource.ACTION_RELATED_ACTIVITY);

    boolean canSave = false;
%>
<div class="modal-dialog">
    <div class="modal-content" id="modalContent">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h5 class="modal-title"><%= paramRequest.getLocaleString("lblRelated")%></h5>
        </div>
        <form method="post" class="form-horizontal swbp-form" action="<%= urlAction %>" id="formra">
            <div class="modal-body">
                <%
                if (!itdsi.hasNext()) {
                    %>
                    <div class="alert alert-block alert-warning fade in"><%=paramRequest.getLocaleString("msgNoItems")%></div>    
                    <%
                }
                
                while (itdsi.hasNext()) {
                    DocumentSectionInstance dsinstance = itdsi.next();
                    SemanticClass scls = null;
                    if (dsinstance.getSecTypeDefinition() != null && dsinstance.getSecTypeDefinition().getSectionType() != null) {
                        scls = dsinstance.getSecTypeDefinition().getSectionType().transformToSemanticClass();
                    }
                    if (null != scls && scls.isSubClass(Instantiable.swpdoc_Instantiable, false) &&
                            dsinstance.listDocuSectionElementInstances().hasNext()) {
                        Iterator<SectionElement> elements = dsinstance.listDocuSectionElementInstances();
                        if (elements.hasNext()) {
                            canSave = true;
                            %>
                            <div class="panel panel-default swbp-panel-head no-margin">
                                <div class="panel-heading text-center"><%= dsinstance.getSecTypeDefinition().getTitle() %></div>
                            </div>
                            <%
                            while(elements.hasNext()) {
                                SectionElement se = elements.next();
                                
                                //Get first property of element
                                String[] propst = dsinstance.getSecTypeDefinition().getVisibleProperties().trim().split("\\|");
                                String idprop = propst[0].substring(propst[0].indexOf(";") + 1, propst[0].length());
                                SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                                
                                String value = "";
                                if (se instanceof ElementReference) {
                                    ElementReference er = (ElementReference) se;
                                    se = (SectionElement) er.getElementRef();
                                }
                                if (sp.getPropId().equals(Referable.swpdoc_file.getPropId())) {
                                    Referable ref = (Referable) se;
                                    RepositoryDirectory rd = ref.getRefRepository().getRepositoryDirectory();
                                    SWBResourceURL urlDownload = new SWBResourceURLImp(request, rd.getResource(), rd,
                                            SWBResourceModes.UrlType_RENDER);

                                    urlDownload.setMode(ProcessFileRepository.MODE_GETFILE)
                                            .setCallMethod(SWBResourceURL.Call_DIRECT)
                                            .setParameter("fid", ref.getRefRepository().getId())
                                            .setParameter("verNum", ref.getRefRepository().getLastVersion().getVersionNumber() + "");

                                    RepositoryElement re = (RepositoryElement) ref.getRefRepository();
                                    VersionInfo vi = re.getLastVersion();
                                    if (re instanceof org.semanticwb.process.model.RepositoryFile) {
                                        value = "<a href=\"" + urlDownload + "\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-download\"></i></a> ";
                                    } else if (re instanceof RepositoryURL) {
                                        value = "<a href=\"" + vi.getVersionFile() + "\" target=\"_blank\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-external-link\"></i></a>";
                                    }
                                } else {
                                    value = se.getSemanticObject().getProperty(sp);
                                }

                                %>
                                <div class="swbp-list-item">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 swbp-list-title">
                                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-2">
                                            <input type="checkbox" <% if (map.containsKey(se)) { %> checked="true"<% }%>
                                                   name="<%= se.getURI()%>" id="<%= se.getURI()%>" class="css-checkbox">
                                            <label class="css-label" for="<%= se.getURI() %>"></label>
                                        </div>
                                        <div class="col-lg-11 col-md-11 col-sm-11 col-xs-10 swbp-list-text"><%= value %></div>
                                    </div>
                                </div>
                                <%
                            }
                        }
                    }
                }
                %>
            </div>
            <div class="modal-footer">
                <input type="hidden" name="uridsi" value="<%= uridsi%>">
                <input type="hidden" name="urise" value="<%= urise%>">
                <%
                if (canSave) {
                    %>
                    <button type="submit" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6"><span class="fa fa-plus"></span><span class="hidden-xs"> <%=paramRequest.getLocaleString("btnSave")%></span></button>
                    <%
                }
                %>
                <button type="button" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6" data-dismiss="modal"><span class="fa fa-times"></span><span class="hidden-xs"> <%=paramRequest.getLocaleString("btnCancel")%></span></button>
            </div>
        </form>
    </div>
</div>
<script>
    $(document).ready(function() {
        $("#formra").on("submit", function(evt) {
            var theForm = evt.target;
            $.ajax({
                url: $(theForm).attr('action'),
                cache: false,
                data: $(theForm).serialize(),
                type: 'POST',
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success: function(data) {
                    if (data.status === "ok") {
                        window.location.reload();
                    }
                }
            });
            evt.preventDefault();
        });
    });
</script>