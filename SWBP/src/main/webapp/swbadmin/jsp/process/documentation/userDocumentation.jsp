<%-- 
    Document   : userDocumentation
    Created on : 9/12/2013, 10:30:59 AM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceModes"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.ProcessGroup"%>
<%@page import="org.semanticwb.process.resources.manager.SWBProcessManagerResource"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    ArrayList<ProcessGroup> getNavPath (WebSite site, ProcessGroup current) {
        ArrayList<ProcessGroup> ret = new ArrayList<>();
        if (current != null) {
            ProcessGroup pivot = current;
            while (pivot != null) {
                ret.add(pivot);
                pivot = pivot.getParentGroup();
            }
        }
        return ret;
    }
%>
<%
SWBParamRequest paramRequest = request.getAttribute(SWBProcessManagerResource.ATT_PARAMREQUEST) != null ? (SWBParamRequest) request.getAttribute(SWBProcessManagerResource.ATT_PARAMREQUEST) : null;
WebSite model = paramRequest.getWebPage().getWebSite();
User user = paramRequest.getUser();
String lang = user.getLanguage();
WebPage webpage = paramRequest.getWebPage();
Role docRole = webpage.getWebSite().getUserRepository()
        .getRole(paramRequest.getResourceBase().getAttribute("docRole"));//TODO: Hacer cnfigurable el rol

Role adminRole = webpage.getWebSite().getUserRepository().getRole("admin");//TODO: Hacer cnfigurable el rol
Resource base = paramRequest.getResourceBase();
List<Descriptiveable> list = request.getAttribute(SWBProcessManagerResource.LIST_PROCESSES) != null ? (List<Descriptiveable>) request.getAttribute(SWBProcessManagerResource.LIST_PROCESSES) : null;

String idpg = request.getParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP) != null ? request.getParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP) : null;
ProcessGroup group1 = ProcessGroup.ClassMgr.getProcessGroup(idpg, model);

SWBResourceURL urlDoc = paramRequest.getRenderUrl().setMode(SWBProcessManagerResource.MODE_VIEW_DOCUMENTATION);
    SWBResourceURL createProcessUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceModes.Call_DIRECT);

createProcessUrl.setMode(SWBProcessManagerResource.MODE_CREATEPROCESS);
if (null != idpg) {
    createProcessUrl.setParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP, idpg);
}

String pag = request.getParameter("p") != null ? request.getParameter("p") : "";
WebPage templatesPage = webpage.getWebSite().getWebPage(base.getAttribute("templatePage"));
WebPage contentsPage = webpage.getWebSite().getWebPage(base.getAttribute("contentPage"));

if (null == templatesPage) templatesPage = webpage;
if (null == contentsPage) contentsPage = webpage;

boolean isAdmin = user.hasRole(adminRole);
boolean isDocumenter = user.hasRole(docRole);
%>
<div class="row no-margin swbp-button-ribbon text-right">
    <%
    if (isDocumenter || isAdmin) {
        %>
        <a href="<%= templatesPage.getUrl() %>?<%= SWBProcessManagerResource.PARAM_PROCESSGROUP %>=<%= idpg %>"
           class="btn btn-swbp-action" title="Plantillas">
            Plantillas
        </a>
        <%
    }

    if (isAdmin) {
        if (null != group1) {
            %>
            <a href="<%= createProcessUrl %>" class="btn btn-swbp-action" data-toggle="modal"
               data-target="#modalDialog">Crear proceso</a>
            <%
        }
        %>
        <a href="" class="btn btn-swbp-action" data-toggle="modal" data-target="#modalDialog">Crear grupo de procesos</a>
        <%
    }
    %>
</div>
<hr/>
<ol class="breadcrumb swbp-breadcrumb">
    <li><a href="<%= paramRequest.getRenderUrl() %>"><span class="fa fa-file-text"></span></a></li>
    <%
    ArrayList<ProcessGroup> nPath = getNavPath(model, group1);
    if (!nPath.isEmpty()) {
        Collections.reverse(nPath);
        for (ProcessGroup _pg : nPath) {
            String cssClass = "";
            if (_pg.getURI().equals(idpg) || nPath.size() == 1) {
                cssClass = "active";
            }
            %>
            <li <%= cssClass.isEmpty() ? "" : "class=\"active\""%>><a
                    href="?<%= SWBProcessManagerResource.PARAM_PROCESSGROUP %>=<%= _pg.getId() %>"><%= _pg.getTitle() %></a></li>
            <%
        }
    }
    %>
</ol>
<%

if (list.isEmpty()) {
    %>
    <div class="alert alert-block alert-warning">
        <p>No hay elementos para mostrar</p>
    </div>
    <%
} else {
    //Sort by language, then by type
    Collections.sort(list, new SWBComparator(lang));
    Collections.sort(list, new Comparator<Descriptiveable>() {
        @Override
        public int compare(Descriptiveable o1, Descriptiveable o2) {
            return o2.getSemanticObject().getSemanticClass().getClassCodeName()
                    .compareTo(o1.getSemanticObject().getSemanticClass().getClassCodeName());
        }
    });
    for (Descriptiveable desc : list) {
        String title = desc.getTitle();
        String idp = desc.getId();
        String clas = desc instanceof Process ? "Proceso" : "Grupo de procesos";
        %>
        <div class="swbp-list-element">
            <%
            if (clas.equals("Proceso")) {
                %>
                <div class="col-lg-7 col-md-7 col-sm-9 col-xs-12 swbp-list-title">
                    <div class="col-lg-2 col-md-2 col-sm-1 col-xs-2 fa fa-cogs swbp-list-icon swbp-align-icon"></div>
                    <div class="col-lg-10  col-md-10 col-sm-11 col-xs-10 swbp-list-text swbp-align-text"><%= title %></div>
                </div>
                <div class="col-lg-5 col-md-5 col-sm-3 col-xs-12 swbp-list-action">
                    <a class="btn btn-default col-xs-4"
                       href="<%= urlDoc.setParameter("idp", idp).setParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP, idpg)%>"
                       role="button">
                        <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 fa fa-eye"></div>
                        <div class="col-lg-8 col-md-8 hidden-sm hidden-xs swbp-list-action-text">Ver</div>
                    </a>
                    <%
                    SWBResourceURL modelUrl = null;
                    if (isAdmin) {
                        modelUrl = paramRequest.getRenderUrl().setMode(SWBProcessManagerResource.MODE_SWBPMODELER);
                        modelUrl.setParameter(SWBProcessManagerResource.PARAM_PROCESSID, desc.getId());
                        modelUrl.setParameter(SWBProcessManagerResource.PARAM_PROCESSGROUP, idpg);
                    }
                    %>
                    <a class="btn btn-default col-xs-4" href="<%= null != modelUrl ? modelUrl : "#" %>"
                       role="button" <%= !isAdmin ? "disabled=\"disabled\"" : "" %>>
                        <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 fa fa-pencil"></div>
                        <div class="col-lg-8 col-md-8 hidden-sm hidden-xs swbp-list-action-text">Editar</div>
                    </a>
                    <%
                    String docUrl = "#";
                    if (isDocumenter || isAdmin) {
                        Process p = (Process) desc;
                        docUrl = contentsPage.getUrl() + "?idp=" +
                                p.getId() + "&_rid=" + paramRequest.getResourceBase().getId() +
                                "&wp=" + paramRequest.getWebPage().getId() + "&" +
                                SWBProcessManagerResource.PARAM_PROCESSGROUP + "=" + idpg + "&p=" + pag;
                    }
                    %>
                    <a class="btn btn-default col-xs-4" role="button"
                       href="<%= docUrl %>" <%= !isDocumenter || !isAdmin ? "disabled=\"disabled\"" : "" %>>
                        <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 fa fa-file-text"></div>
                        <div class="col-lg-8 col-md-8 hidden-sm hidden-xs swbp-list-action-text">Documentar</div>
                    </a>
                </div>
                <%
            } else {
                %>
                <div class="col-xs-12 swbp-list-title swbp-title-xs">
                    <div class="col-lg-1 col-md-1 col-sm-1 col-xs-2 fa fa-folder-open swbp-list-icon"></div>
                    <div class="col-lg-11  col-md-11 col-sm-11 col-xs-10 swbp-list-text">
                        <a
                            href="?<%= SWBProcessManagerResource.PARAM_PROCESSGROUP %>=<%= desc.getSemanticObject().getId()%> "><%= title %></a>
                    </div>
                </div>
                <%
            }
            %>
        </div>
        <%
    }
}
%>