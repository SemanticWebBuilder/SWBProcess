<%-- 
    Document   : addTemplate
    Created on : 20-ene-2016, 15:35:21
    Author     : hasdai
--%>

<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.documentation.*"%>
<%@page import="org.semanticwb.process.resources.documentation.SWPDocumentTemplateResource"%>
<%@ page import="java.util.Iterator" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL act = paramRequest.getActionUrl()
            .setAction(SWPDocumentTemplateResource.ACTION_ADD_DOCUMENT_SECTION);

    String uridt = request.getParameter("uridt") != null ? request.getParameter("uridt") : "";
    DocumentTemplate docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridt);
    
    String lang = "es";
    User user = paramRequest.getUser();
    if (user != null && user.getLanguage() != null) {
        lang = user.getLanguage();
    }
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h5 class="modal-title">Agregar sección</h5>
        </div>
        <%
        if (null == docTemplate) {
            %>
            <div class="modal-body">
                error
            </div>
            <%
        } else {
            boolean hasModel = false;
            boolean hasActivity = false;

            Iterator<DocumentSection> sections = docTemplate.listDocumentSections();
            while (sections.hasNext()) {
                DocumentSection section = sections.next();
                SemanticClass sectionType = section.getSectionType().transformToSemanticClass();
                if (sectionType.getURI().equals(Model.sclass.getURI())) hasModel = true;
                if (sectionType.getURI().equals(Activity.sclass.getURI())) hasActivity = true;
            }
            %>
            <form class="form-horizontal swbp-form" action="<%= act %>" id="formNDS">
                <input type="hidden" name="uridt" value="<%= docTemplate.getURI() %>"/>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">Título: *</label>
                        <div class="col-sm-7">
                            <input name="titleSection" id="titltitleSectione" type="text" required class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><%=paramRequest.getLocaleString("lblSecType")%>*:</label>
                        <div class="col-sm-7">
                            <select required name="dstype" id="dstype" class="form-control">
                                <option value="">Seleccione un tipo</option>
                                <%
                                Iterator<SemanticClass> itSemCls = SWBComparator.sortByDisplayName(SectionElement.sclass.listSubClasses(), lang);
                                while (itSemCls.hasNext()) {
                                    SemanticClass semanticCls = itSemCls.next();
                                    boolean add = true;
                                    if (semanticCls.getURI().equals(Model.sclass.getURI()) && hasModel) {
                                        add = false;
                                    }
                                    if (semanticCls.getURI().equals(Activity.sclass.getURI()) && hasActivity) {
                                        add = false;
                                    }
                                    if (semanticCls.getURI().equals(ElementReference.sclass.getURI())) {
                                        add = false;
                                    }
                                    if (add) {
                                        %><option value="<%=semanticCls.getURI()%>"><%=semanticCls.getDisplayName(lang) %></option><%
                                    }
                                }
                                %>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="saveFormVersion" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6" type="submit">
                        <span class="fa fa-save fa-fw"></span><span class="hidden-xs"><%=paramRequest.getLocaleString("btnSave")%></span>
                    </button>
                    <button type="button" class="btn btn-default pull-right col-lg-3 col-md-3 col-sm-6 col-xs-6" data-dismiss="modal">
                        <span class="fa fa-times fa-fw"></span><span class="hidden-xs">Cancelar</span>
                    </button>
                </div>
            </form>
            <script>
                (function() {
                    var theForm = document.getElementById('formNDS');
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

                    theForm['dstype'].addEventListener("change", function(evt) {
                        if (isSelectValid(evt.target)) {
                            $(evt.target).closest(".form-group").removeClass("has-error");
                        } else {
                            $(evt.target).closest(".form-group").addClass("has-error");
                        }
                    });

                    theForm['titleSection'].addEventListener("change", function(evt) {
                        if (isTitleValid(evt.target)) {
                            $(evt.target).closest(".form-group").removeClass("has-error");
                        } else {
                            $(evt.target).closest(".form-group").addClass("has-error");
                        }
                    });

                    theForm.addEventListener('submit', function(evt) {
                        var valid = isTitleValid(theForm['titleSection']) && isSelectValid(theForm['dstype']);                      
                        if (valid) {
                            $.ajax({
                                url: $(theForm).attr('action'),
                                cache: false,
                                data: $(theForm).serialize(),
                                type: 'POST',
                                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                                success: function(data) {
                                    if (data.status === "ok") {
                                        var loc = '<%= paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_CONTENT).setMode(SWPDocumentTemplateResource.MODE_EDIT_DOCUMENT_SECTION) %>?urids='+encodeURIComponent(data.urids);
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
            <%
        }
        %>
    </div>
</div>