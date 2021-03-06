<%-- 
    Document   : saveVersion
    Created on : 22/04/2014, 04:26:33 PM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.resources.documentation.SWPDocumentationResource"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    String uridi = request.getParameter("uridi") != null ? request.getParameter("uridi") : "";
    String idp = request.getParameter("idp") != null ? request.getParameter("idp") : "";
    SWBResourceURL urlAction = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT)
            .setAction(SWPDocumentationResource.ACTION_SAVE_VERSION).setParameter("uridi", uridi);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h5 class="modal-title"><%= paramRequest.getLocaleString("lblSaveVersion")%></h5>
        </div>
        <form id="saveVersion" class="form-horizontal swbp-form" role="form" method="post" action="<%= urlAction%>">
            <input type="hidden" name="uridi" value="<%= uridi%>">
            <input type="hidden" name="idp" value="<%= idp%>">
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-4 control-label">Comentarios *</label>
                    <div class="col-sm-7">
                        <input type="text" name="description" class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6">
                    <span class="fa fa-save fa-fw"></span><span class="hidden-xs"><%=paramRequest.getLocaleString("btnSave")%></span>
                </button>
                <button type="button" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6" data-dismiss="modal">
                    <span class="fa fa-times fa-fw"></span><span class="hidden-xs"><%= paramRequest.getLocaleString("btnCancel")%></span>
                </button>
            </div>
        </form>
    </div>
</div>
<script>
    ($(document).ready(function(){
        $("#saveVersion").on("submit", function(evt){
            var theForm = evt.target;
            $.ajax({
                url: $(theForm).attr('action'),
                cache: false,
                data: $(theForm).serialize(),
                type: 'POST',
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success: function(data) {
                    if (data && data.status === "ok") {
                        window.location.reload();
                    }
                }
            });
            evt.preventDefault();
        });
    })
    )();
</script>