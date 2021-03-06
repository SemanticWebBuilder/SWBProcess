<%-- 
    Document   : duplicateTemplate
    Created on : 9/12/2014, 05:26:29 PM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.documentation.DocumentTemplate"%>
<%@page import="org.semanticwb.process.model.documentation.TemplateContainer"%>
<%@page import="org.semanticwb.process.resources.documentation.SWPDocumentTemplateResource"%>
<%@ page import="java.util.Iterator" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(SWPDocumentTemplateResource.PARAM_REQUEST);
    String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc") : "";
    TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
    SWBResourceURL urlSave = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
            .setAction(SWPDocumentTemplateResource.ACTION_DUPLICATE_TEMPLATE).setParameter("uritc", uritc);

%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h5 class="modal-title">Duplicar plantilla</h5>
        </div>
        <form action="<%= urlSave %>" method="post" id="formdte" class="form-horizontal swbp-form">
            <div class="modal-body">
                <div class="form-group" id="divtitletcd">
                    <label class="col-sm-3 control-label">Título *:</label>
                    <div class="col-sm-8">
                        <input name="titletcd" id="titletcd" required
                               value="<%= tc == null ? "" : tc.getTitle()%>" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">Versión *:</label>
                    <div class="col-sm-8">
                        <select name="versiontemp" class="form-control" required>
                            <option>Seleccione una versión</option>
                            <%
                            if (null != tc) {
                                Iterator<DocumentTemplate> it = SWBComparator.sortByCreated(tc.listTemplates(), false);
                                while (it.hasNext()) {
                                    DocumentTemplate dt = it.next();
                                    %>
                                    <option value="<%= dt.getURI() %>"><%= dt.getVersionValue() %></option>
                                    <%
                                }
                            }
                            %>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6" id="savedtes">
                    <span class="fa fa-save fa-fw"></span><span class="hidden-xs"><%=paramRequest.getLocaleString("btnSave")%></span>
                </button>
                <button type="button" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6" data-dismiss="modal">
                    <span class="fa fa-times fa-fw"></span><span class="hidden-xs"><%=paramRequest.getLocaleString("btnCancel")%></span>
                </button>
            </div>
        </form>
    </div>
</div>
<script>
    (function() {
        var theForm = document.getElementById('formdte');
        if (!theForm) return;

        var isTitleValid = function(element) {
            if (element.required) {
                return !element.validity.valueMissing;
            } else {
                return element.value && element.value !== "";
            }
        };

        var isSelectValid = function(element) {
            if (element.required) {
                return !element.validity.valueMissing;
            } else {
                return element.value && element.value !== "";
            }
        };

        theForm['versiontemp'].addEventListener("change", function(evt) {
            if (isSelectValid(evt.target)) {
                $(evt.target).closest(".form-group").removeClass("has-error");
            } else {
                $(evt.target).closest(".form-group").addClass("has-error");
            }
        });

        theForm['titletcd'].addEventListener("keyup", function(evt) {
            if (isTitleValid(evt.target)) {
                $(evt.target).closest(".form-group").removeClass("has-error");
            } else {
                $(evt.target).closest(".form-group").addClass("has-error");
            }
        });

        theForm.addEventListener('submit', function(evt) {
            var valid = isTitleValid(theForm['titletcd']) && isSelectValid(theForm['versiontemp']);                      
            if (valid) {
                $.ajax({
                    url: $(theForm).attr('action'),
                    cache: false,
                    data: $(theForm).serialize(),
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    success: function(data) {
                        if (data.status === "ok") {
                            var loc = '<%= paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_CONTENT).setMode(SWBResourceURL.Mode_VIEW) %>';
                            window.location = loc;
                        }
                    }
                });
            }
            evt.preventDefault();
            return false;
        });
    })();
</script>