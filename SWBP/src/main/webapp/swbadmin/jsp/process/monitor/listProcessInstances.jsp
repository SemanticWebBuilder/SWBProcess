<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.RoleRef"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.process.model.*"%>
<%@page import="org.semanticwb.process.resources.tracer.ProcessTracer"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    public String getStatusInstances(ProcessInstance pi, int status) {
        String ret = "";
        if (pi != null) {
            Iterator<FlowNodeInstance> actit = SWBComparator.sortByCreated(pi.listFlowNodeInstances());
            while (actit.hasNext()) {
                FlowNodeInstance obj = actit.next();
                ret += _getStatusInstances(obj, status);
            }
        }
        return (ret);
    }

    public String _getStatusInstances(FlowNodeInstance fi, int status) {
        String ret = "";
        if (fi instanceof SubProcessInstance) {
            SubProcessInstance pi = (SubProcessInstance) fi;
            Iterator<FlowNodeInstance> acit = pi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    ret += _getStatusInstances(actinst, status);
                }
            }
        } else if (fi.getFlowNodeType() instanceof Activity && fi.getStatus() == status) {
            ret += fi.getFlowNodeType().getURI() + "|";
        }
        return ret;
    }
%>

<%!    public void printActivityInstance(WebPage page, FlowNodeInstance ai, JspWriter out) throws IOException {
    String baseimg = SWBPortal.getWebWorkPath() + "/models/" + page.getWebSiteId() + "/css/images/";
    if (!(ai.getFlowNodeType() instanceof Activity)) {
        return;
    }

    String stat = "";
    String color = "";
    String actions="";
    String tOwner = "--";
    String tCreator = "--";

    if (ai.getCreator() != null && ai.getCreator().getFullName() != null) {
        tCreator = ai.getCreator().getFullName();
    }

    if (ai.getFlowNodeType() instanceof UserTask) {
        UserTask tsk = (UserTask) ai.getFlowNodeType();
        Iterator<RoleRef> roles = tsk.listRoleRefs();
        if (roles.hasNext()) tOwner = roles.next().getRole().getDisplayTitle("lang");
    }

    if (ai.getStatus() == Instance.STATUS_INIT) {
        stat = "Iniciada";
    }
    if (ai.getStatus() == Instance.STATUS_ABORTED) {
        stat = "Abortada";
    }
    if (ai.getStatus() == Instance.STATUS_CLOSED) {
        stat = "<img title=\"Completada\" src=\"" + baseimg + "icono-terminado.gif\">";
        color = "color=\"#50b050\"";
        if (ai.getFlowNodeType() instanceof UserTask) {
            actions="--";
        } else {
            actions="-";
        }
    }
    if (ai.getStatus() == Instance.STATUS_OPEN) {
        stat = "Abierta";
    }
    if (ai.getStatus() == Instance.STATUS_PROCESSING) {
        stat = "<img src=\"" + baseimg + "icono-iniciado.gif\">";
        color = "color=\"red\"";
        if (ai.getFlowNodeType() instanceof UserTask) {
            actions = "<a class=\"acc-atender\" href=\"" + ((UserTask)ai.getFlowNodeType()).getTaskWebPage().getUrl() + "?suri=" + ai.getEncodedURI()+ "\">Atender</a>";
        } else {
            actions="-";
        }
    }
    if (ai.getStatus() == Instance.STATUS_STOPED) {
        stat = "Detenida";
    }
    out.println("<tr><td class=\"tban-id\">" + stat + "</td>");
    out.println("<td class=\"tban-tarea\">");
    if (ai.getStatus() == Instance.STATUS_PROCESSING) {
        out.println("<font " + color + ">");
    }
    out.println(ai.getFlowNodeType().getTitle());
    if (ai.getStatus() == Instance.STATUS_PROCESSING) {
        out.println("</font>");
    }
    out.println("</td>"
            + "<td class=\"tban-tarea\">" + tCreator + "</td>"
            + "<td class=\"tban-inicia\">" + SWBUtils.TEXT.getStrDate(ai.getCreated(), "es", "dd/mm/yyyy - hh:mm:ss") + "</td>");
    if (ai.getStatus() == Instance.STATUS_CLOSED) {
        out.println("<td class=\"tban-cerrada\">" + SWBUtils.TEXT.getStrDate(ai.getEnded(), "es", "dd/mm/yyyy - hh:mm:ss") + "</td>");
    } else {
        out.println("<td class=\"tban-cerrada\">-</td>");
    }
    out.println("<td class=\"tban-tarea\">" + tOwner + "</td>");
    out.println("</tr>");
    if (ai instanceof SubProcessInstance) {
        SubProcessInstance pi = (SubProcessInstance) ai;
        Iterator<FlowNodeInstance> acit = pi.listFlowNodeInstances();
        if (acit.hasNext()) {
            while (acit.hasNext()) {
                FlowNodeInstance actinst = acit.next();
                printActivityInstance(page, actinst, out);
            }
        }
    }
}
%>

<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    int mode = (Integer) request.getAttribute("viewMode");
    ArrayList<ProcessInstance> instances = (ArrayList<ProcessInstance>) request.getAttribute("instances");
    WebPage topic = paramRequest.getWebPage();

    Iterator<ProcessInstance> it = null;
    if (instances != null) it = instances.iterator();

    if (mode == ProcessTracer.MODE_TRACKING) {
        if (it != null && it.hasNext()) {
            while (it.hasNext()) {
                ProcessInstance pi = it.next();
%>
<table class="tabla-bandeja">
    <thead>
    <th class="tban-id">Estatus</th>
    <th class="tban-tarea">Tarea</th>
    <th class="tban-tarea">Creador</th>
    <th class="tban-inicia">Iniciado</th>
    <th class="tban-cerrada">Terminado</th>
    <th class="tban-tarea">Responsable</th>
    </thead>
    <tbody>
    <%
        Iterator<FlowNodeInstance> actit = SWBComparator.sortByCreated(pi.listFlowNodeInstances());
        while (actit.hasNext()) {
            FlowNodeInstance obj = actit.next();
            if (obj.getStatus() == Instance.STATUS_PROCESSING)
                printActivityInstance(topic, obj, out);
        }
    %>
    </tbody>
</table>
<a href="#" onclick="history.go(-1);">Regresar</a>
<%
            }
        }
    }
%>