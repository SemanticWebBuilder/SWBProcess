<%--
    Document   : businessControlPanelConfig
    Created on : 1/08/2011, 07:12:02 PM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>

<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
String displayCols = (String) request.getAttribute("displayCols");

if (paramRequest.getMode().equals("config")) {
    SWBResourceURL configActionUrl = paramRequest.getActionUrl().setAction("setDisplay");
    %>
    <h2>Configuraci�n del despliegue</h2>
    <form action="<%=configActionUrl%>" method="post">
        <fieldset><legend>Seleccione las columnas a desplegar:</legend>
            <input type="checkbox" name="idCol" <%=displayCols.contains("idCol")?"checked=\"checked\"":""%>/>
            <label>Identificador de la instancia</label><br>
            <input type="checkbox" name="priorityCol" <%=displayCols.contains("priorityCol")?"checked=\"checked\"":""%>/>
            <label>Prioridad de la instancia</label><br>
            <input type="checkbox" name="nameCol" <%=displayCols.contains("nameCol")?"checked=\"checked\"":""%>/>
            <label>Nombre del proceso</label><br>
            <input type="checkbox" name="sdateCol" <%=displayCols.contains("sdateCol")?"checked=\"checked\"":""%>/>
            <label>Fecha de inicio de la instancia</label><br>
            <input type="checkbox" name="edateCol" <%=displayCols.contains("edateCol")?"checked=\"checked\"":""%>/>
            <label>Fecha de fin de la instancia</label><br>
            <input type="checkbox" name="pendingCol" <%=displayCols.contains("pendingCol")?"checked=\"checked\"":""%>/>
            <label>Actividades pendientes</label><br>
            <input type="checkbox" name="rolesCol" <%=displayCols.contains("rolesCol")?"checked=\"checked\"":""%>/>
            <label>Responsables de las actividades pendientes</label><br>
        </fieldset>
        <fieldset>
            <input type="submit" value="Guardar" >
            <input type="button" value="Regresar" onclick="window.location='<%=paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)%>';" >
        </fieldset>
    </form>
    <%
}
%>