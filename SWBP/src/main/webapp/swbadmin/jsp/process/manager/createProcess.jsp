<%--
  Author: hasdai
  Date: 03/08/18
  Time: 19:04
--%>
<%@ page import="org.semanticwb.portal.api.SWBParamRequest" %>
<%@ page import="org.semanticwb.portal.api.SWBResourceURL" %>
<%@ page import="org.semanticwb.portal.api.SWBResourceModes" %>
<%@ page import="org.semanticwb.process.model.Process" %>
<%@ page import="org.semanticwb.model.WebSite" %>
<%@ page import="org.semanticwb.process.resources.processmanager.SWBProcessManagerResource" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(SWBProcessManagerResource.ATT_PARAMREQUEST);
WebSite site = paramRequest.getWebPage().getWebSite();

SWBResourceURL createUrl = paramRequest.getActionUrl().setCallMethod(SWBResourceModes.Call_DIRECT);
createUrl.setAction(SWBProcessManagerResource.ACT_CREATEPROCESS);

String processGroupId = request.getParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h5 class="modal-title">Crear Proceso</h5>
        </div>
        <form id="createProcessForm" method="post" action="<%=createUrl%>" class="form-horizontal swbp-form">
            <input type="hidden" name="<%= SWBProcessManagerResource.PARAM_PROCESSGROUP %>" value="<%= processGroupId %>">
            <div class="modal-body">
                <div class="form-group">
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 swbp-modal-property">
                        <label>Título:</label>
                    </div>
                    <div class="col-lg-7 col-md-7 col-sm-7 col-xs-12">
                        <input id="<%= Process.swb_title.getName() %>"
                               name="<%= Process.swb_title.getName() %>" type="text" required class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 swbp-modal-property">
                        <label>Identificador:</label>
                    </div>
                    <div class="col-lg-7 col-md-7 col-sm-7 col-xs-12">
                        <input id="<%= SWBProcessManagerResource.PARAM_PROCESSID %>"
                               name="<%= SWBProcessManagerResource.PARAM_PROCESSID %>" type="text"
                               required class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 swbp-modal-property">
                        <label>Descripción:</label>
                    </div>
                    <div class="col-lg-7 col-md-7 col-sm-7 col-xs-12">
                        <textarea name="<%= Process.swb_description.getName() %>" required class="form-control"></textarea>
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
                    var theForm = document.getElementById('createProcessForm');

                    $('#<%= Process.swb_title.getName() %>').on('keyup', function() {
                        var idField = $('#<%= SWBProcessManagerResource.PARAM_PROCESSID %>');
                        var val = replaceChars4Id($(this).val());
                        idField.val(val);
                    });

                    $('#<%= SWBProcessManagerResource.PARAM_PROCESSID %>').on('keyup blur', function() {
                        var val = $(this).val();
                        if (canCreateSemanticObject('<%=site.getSemanticModel().getName()%>','<%=Process.sclass.getClassId()%>', val) === false) {
                            $(this).parents('.form-group').addClass("has-error");
                        } else {
                            $(this).parents('.form-group').removeClass("has-error");
                        }
                    });

                    if (theForm) {
                        $("#createProcessForm").on("submit", function(evt) {
                            var idField = $('#<%= SWBProcessManagerResource.PARAM_PROCESSID %>');
                            var isValid = canCreateSemanticObject('<%=site.getSemanticModel().getName()%>','<%=Process.sclass.getClassId()%>', idField.val());

                            if (isValid) {
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
                                                toastr.error("Ha ocurrido un error al crear el proceso");
                                            }
                                        }
                                    }
                                });
                            }
                            evt.preventDefault();
                        });
                    }
                });
            })();
        </script>
    </div>
</div>