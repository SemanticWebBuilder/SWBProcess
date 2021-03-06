<%-- 
    Document   : ReportResource
    Created on : 11/03/2013, 05:23:28 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.resources.reports.Report"%>
<%@page import="java.util.Iterator"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
    SWBResourceURL urlAction = paramRequest.getActionUrl();
    Iterator<Report> report = Report.ClassMgr.listReports(paramRequest.getWebPage().getWebSite());
    SWBResourceURL urlDialog = paramRequest.getRenderUrl().setMode("dialog").setCallMethod(SWBResourceURL.Call_DIRECT);
    %>
    <div class="row no-margin swbp-button-ribbon text-right">
        <a class="btn btn-swbp-action" href="<%=url.setMode("add")%>" data-toggle="modal" data-target="#modalDialog"><%=paramRequest.getLocaleString("add")%></a>
    </div>
    <hr/>
    <%
    url.setCallMethod(SWBResourceURL.Call_CONTENT);
    if (report.hasNext()) {
        while (report.hasNext()) {
            Report rp = (Report) report.next();
            if (rp.isValid()) {
                urlDialog = paramRequest.getRenderUrl().setMode("dialog").setCallMethod(SWBResourceURL.Call_DIRECT);
                urlDialog.setParameter("idReport", rp.getId());
                urlDialog.setParameter("action", "export");
                %>
                <div class="swbp-list-element">
                    <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 swbp-list-title">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 swbp-list-text"><%= rp.getTitle() %></div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 swbp-list-action">
                        <a href="<%= rp.listColumnReports().hasNext() ? urlDialog : "#"%>" class="btn btn-default col-xs-4 fa fa-download" data-toggle="modal" data-target="#modalDialog" <%= rp.listColumnReports().hasNext() ? "" : "disabled" %>></a>
                        <a href="<%= url.setMode(SWBResourceURL.Mode_EDIT).setParameter("idReport", rp.getId()) %>" class="btn btn-default col-xs-4 fa fa-pencil"></a>
                        <a href="<%=urlAction.setAction(SWBResourceURL.Action_REMOVE).setParameter("idReport", rp.getURI())%>" class="btn btn-default col-xs-4 fa fa-trash-o" onclick="if (!confirm('<%=paramRequest.getLocaleString("remove") + " " + paramRequest.getLocaleString("report")%>?'))return false;"></a>
                    </div>
                </div>
                <%
            }
        }
    } else {
        %>
        <div class="alert alert-block alert-warning fade in">
            <p><%=paramRequest.getLocaleString("msgNoReports")%></p>
        </div>
        <%
    }
%>