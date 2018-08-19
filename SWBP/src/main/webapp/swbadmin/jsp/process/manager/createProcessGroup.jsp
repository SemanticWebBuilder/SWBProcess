<%--
  Author: hasdai
  Date: 03/08/18
  Time: 19:04
--%>
<%@ page import="org.semanticwb.portal.api.SWBParamRequest" %>
<%@ page import="org.semanticwb.portal.api.SWBResourceModes" %>
<%@ page import="org.semanticwb.portal.api.SWBResourceURL" %>
<%@ page import="org.semanticwb.process.model.ProcessGroup" %>
<%@ page import="org.semanticwb.process.resources.manager.SWBProcessManagerResource" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(SWBProcessManagerResource.ATT_PARAMREQUEST);
    SWBResourceURL createUrl = paramRequest.getActionUrl().setCallMethod(SWBResourceModes.Call_DIRECT);
    createUrl.setAction(SWBProcessManagerResource.ACT_CREATEPROCESSGROUP);

    String processGroupId = request.getParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h5 class="modal-title">Crear Grupo de Procesos</h5>
        </div>
        <form id="createProcessGroupForm" method="post" action="<%=createUrl%>" class="form-horizontal swbp-form">
            <input type="hidden" name="<%= SWBProcessManagerResource.PARAM_PROCESSGROUP %>" value="<%= processGroupId %>">
            <div class="modal-body">
                <div class="form-group">
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 swbp-modal-property">
                        <label>Título:</label>
                    </div>
                    <div class="col-lg-7 col-md-7 col-sm-7 col-xs-12">
                        <input id="<%= ProcessGroup.swb_title.getName() %>"
                               name="<%= ProcessGroup.swb_title.getName() %>" type="text" required class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 swbp-modal-property">
                        <label>Descripción:</label>
                    </div>
                    <div class="col-lg-7 col-md-7 col-sm-7 col-xs-12">
                        <textarea name="<%= ProcessGroup.swb_description.getName() %>" required class="form-control"></textarea>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6"><span class="fa fa-check fa-fw"></span>Aceptar</button>
                <button type="button" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6" data-dismiss="modal"><span class="fa fa-times fa-fw"></span>Cancelar</button>
            </div>
        </form>

        <script>
            (function() {
                $(document).ready(function() {
                    var theForm = document.getElementById('createProcessGroupForm');
                    if (theForm) {
                        $("#createProcessGroupForm").on("submit", function(evt) {
                            $.ajax({
                                url: $(theForm).attr('action'),
                                cache: false,
                                data: $(theForm).serialize(),
                                type: 'POST',
                                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                                success: function (data) {
                                    $("#modalDialog").toggle("modal");
                                    if (data.status === "ok") {
                                        window.location.reload();
                                    } else {
                                        if (window.toastr) {
                                            toastr.options.closeButton = true;
                                            toastr.options.positionClass = "toast-bottom-full-width";
                                            toastr.error("Ha ocurrido un error al crear el grupo de procesos");
                                        }
                                    }
                                }
                            });
                            evt.preventDefault();
                        });
                    }
                });
            })();
        </script>
    </div>
</div>
