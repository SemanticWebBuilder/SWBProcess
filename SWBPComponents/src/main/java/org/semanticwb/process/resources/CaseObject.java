/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público ('open source'),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org.mx
 */
package org.semanticwb.process.resources;

import java.util.Iterator;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;

import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.SubProcessInstance;

import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.process.resources.kpi.CaseProcessInstance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBClass;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.process.resources.utils.Ajax;
import org.semanticwb.process.resources.kpi.CaseProcessObject;
import org.semanticwb.process.model.ItemAwareReference;

/**
 *
 * @author Sergio Téllez
 */
public class CaseObject extends GenericResource {

    private static final Logger LOG = SWBUtils.getLogger(CaseObject.class);
    private static final String CASE_OBJECT = "CASE_OBJECT";
    private static final String PROCESS_OBJECT = "PROCESS_OBJECT";
    private static final String PLOT_THEME = "plot_theme";

    String opacity = "0.4";
    String colour = "#3090C7";
    String[] colours = {colour, "#1589FF", "#0760F9", "#157DEC", "#6698FF", "", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
    String[] highColours = {"#EB8EBF", "#AB91BC", "#637CB0", "#92C2DF", "#BDDDE4", "#69BF8E", "#B0D990", "#F7FA7B", "#F9DF82", "#E46F6A"};

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (SWBParamRequest.Action_EDIT.equals(paramRequest.getAction()))
            doAdminCase(request, response, paramRequest);
        else
            doAdminResume(request, response, paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setCharacterEncoding("ISO-8859-1");

        PrintWriter out = response.getWriter();
        
        SWBResourceURL edit = paramRequest.getRenderUrl();
        edit.setMode(SWBResourceURL.Mode_EDIT);
        String suri = request.getParameter("suri");
        edit.setParameter("suri", suri);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print("    <button dojoType=\"dijit.form.Button\" onClick=\"submitUrl('" + edit + "',this.domNode); return false;\">" + paramRequest.getLocaleString("config") + "</button>");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("    <legend>" + paramRequest.getLocaleString(CASE_OBJECT) + "</legend>\n");
        if (null != suri)
            doGraph(request, response, paramRequest, suri);
        out.print("  </fieldset>\n");
        out.print("</div>\n");
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
        Process process = getProcess(suri);
        if ("1".equalsIgnoreCase(getAttribute(suri,"plot")))
            doBars(request, response, paramRequest, process);
        else if ("3".equalsIgnoreCase(getAttribute(suri,"plot")))
            doArea(request, response, paramRequest, process);
        else
            doPie(request, response, paramRequest, process);
    }

    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        long lid = System.currentTimeMillis();
        setPlotTheme(getAttribute(process.getURI(), PLOT_THEME));
        out.println("<script type=\"text/javascript\">");
        out.println("   dojo.require(\"dojox.charting.Chart2D\");");
        out.println("   dojo.require(\"dojox.charting.themes.PlotKit.blue\");");
        out.println("   dojo.require(\"dojox.charting.action2d.MoveSlice\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Highlight\");");
        out.println("   makeObjects = function(){");
        out.println("       var chart = new dojox.charting.Chart2D(\""+lid+"_instances\");");
        out.println("       chart.setTheme(dojox.charting.themes.PlotKit.blue);");
        out.println("       chart.addPlot(\"default\", {");
        out.println("           type: \"Pie\",");
        out.println("           font: \"normal normal bold 10pt Tahoma\",");
        out.println("           fontColor: \"white\",");
        out.println("           labelOffset: 40,");
        out.println("           radius: 120");
        out.println("       });");
        out.println("       chart.addSeries(\"ResponseTime\", [");
        out.println("           " + getDataPie(process, paramRequest));
        out.println("       ]);");
        out.println("       var a = new dojox.charting.action2d.MoveSlice(chart, \"default\")");
        out.println("       var b = new dojox.charting.action2d.Highlight(chart, \"default\", {highlight: \"#6698FF\"});");
        out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
        out.println("       chart.render();");
        out.println("   };");
        out.println("   dojo.addOnLoad(makeObjects);");
        out.println("</script>");
        out.println("<div id=\""+lid+"_title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id=\""+lid+"_instances\" style=\"width: 400px; height: 300px;\"></div>");
        out.println("<div id=\""+lid+"_stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getAttribute(process.getURI(),"object_property")) ? getObjectTitle(process.getURI(),paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), PLOT_THEME));
        long lid = System.currentTimeMillis();
        out.println("<div id=\""+lid+"_title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id='"+lid+"_instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var bargraph = new Grafico.BarGraph($('"+lid+"_instances'), " + getData(process) + ",\n");
        out.println("        {\n");
        out.println("           labels :			  " + getTitles(paramRequest) + ",\n");
        out.println("           color :				  '" + colour + "',\n");
        out.println("           meanline :		      false,\n");
        out.println("           grid :                false,\n");
        out.println("           draw_axis :           false,\n");
        out.println("           label_rotation :	  -30,\n");
        out.println("           label_color :         \"#348781\",\n");
        out.println("           hover_color :         \"#6698FF\",\n");
        out.println("           datalabels :          {one: " + getLabels(process, paramRequest) + "}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\""+lid+"_stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getAttribute(process.getURI(),"object_property")) ? getObjectTitle(process.getURI(),paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), PLOT_THEME));
        long lid = System.currentTimeMillis();

        out.println("<div id=\""+lid+"_title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id=\""+lid+"_instances\" style=\"width:400px; height:300px;\"></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var areagraph = new Grafico.AreaGraph($('"+lid+"_instances'), { workload: " + getData(process) + " },");
        out.println("        {");
        out.println("           grid :                false,");
        out.println("           area_opacity :        " + opacity + ",");
        out.println("           plot_padding :        10,");
        out.println("           font_size :           10,");
        out.println("           colors :              { workload: '" + colour + "' },");
        out.println("           label_color :         \"#348781\",");
        if ("1".equalsIgnoreCase(getAttribute(process.getURI(),"display_totals")))
            out.println("           markers :             \"value\",");
        out.println("           meanline :            false,");
        out.println("           draw_axis :           false,\n");
        out.println("           labels :			  " + getTitles(paramRequest) + ",\n");
        out.println("           datalabels :          {workload: '" + getAttribute(process.getURI(),"measurement_unit") + "'}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\""+lid+"_stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getAttribute(process.getURI(),"object_property")) ? getObjectTitle(process.getURI(),paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    public void doAdminCase(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_EDIT);
        url.setParameter("suri", suri);
        url.setAction("resume");
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString(CASE_OBJECT));
        out.print("  </fieldset>\n");
        long lid =System.currentTimeMillis();
        out.print("  <form id=\""+lid+"_case\" name=\"case\" action=" + url.toString() + " method=\"post\" onsubmit=\"submitForm('"+lid+"_case'); return false;\">\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("OBJECT_PROPERTY") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"object_property\" name=\"object_property\">\n");
        Process process = getProcess(suri);
        ProcessInstance pinst = process.getProcessInstance();
        if (null != pinst)
            selectObjectProperty(pinst, out, paramRequest);
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri, "plot")) ? " checked" : "") + "> " + paramRequest.getLocaleString("bars") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(suri, "plot")) ? " checked" : "") + "> " + paramRequest.getLocaleString("pie") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(suri, "plot")) ? " checked" : "") + "> " + paramRequest.getLocaleString("area") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri,PLOT_THEME)) ? " checked" : "") + "> " + paramRequest.getLocaleString("blue") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(suri,PLOT_THEME)) ? " checked" : "") + "> " + paramRequest.getLocaleString("green") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(suri,PLOT_THEME)) ? " checked" : "") + "> " + paramRequest.getLocaleString("red") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("MEASUREMENT_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"measurement_unit\" type=\"text\" name=\"measurement_unit\" value=\"" + (!"".equalsIgnoreCase(getAttribute(suri,"measurement_unit")) ? getAttribute(suri,"measurement_unit") : "") + "\"></td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"display_totals\" type=\"checkbox\" name=\"display_totals\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri,"display_totals")) ? " checked" : "") + "> " + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button  dojoType=\"dijit.form.Button\" type=\"submit\" >"+paramRequest.getLocaleString("apply")+"</button>");
        url = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_VIEW);
        url.setParameter("suri", suri);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=\"submitUrl('" + url + "',this.domNode); return false;\">"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_EDIT);
        url.setParameter("suri", suri);
        updateAttributes(request);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString(CASE_OBJECT));
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute(encode(suri),"")) ? getProcessTitle(suri) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("OBJECT_PROPERTY") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getAttribute(suri, "object_property")) ? getObjectTitle(suri, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + ("1".equalsIgnoreCase(getAttribute(suri,"plot")) ? paramRequest.getLocaleString("bars") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri,"plot")) ? paramRequest.getLocaleString("pie") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri,"plot")) ? paramRequest.getLocaleString("area") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri,PLOT_THEME)) ? paramRequest.getLocaleString("blue") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri,PLOT_THEME)) ? paramRequest.getLocaleString("green") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri,PLOT_THEME)) ? paramRequest.getLocaleString("red") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("MEASUREMENT_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + (!"".equalsIgnoreCase(getAttribute(suri,"measurement_unit")) ? getAttribute(suri,"measurement_unit") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        if ("1".equalsIgnoreCase(getAttribute(suri,"display_totals")))
            out.print("                  <td>" + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        else
            out.print("                  <td>" + paramRequest.getLocaleString("DEFAULT_VIEW") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=\"submitUrl('" + url + "',this.domNode); return false;\">"+paramRequest.getLocaleString("config")+"</button>");
        url = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_VIEW);
        url.setParameter("suri", suri);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=\"submitUrl('" + url + "',this.domNode); return false;\">"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print("</div>\n");
    }

    private void updateAttributes(HttpServletRequest request) {
        String totals = "0";
        StringBuilder processObject = new StringBuilder();
        try {
            if (null != request.getParameter("display_totals")) totals = "1";
            setProcessObject(request.getParameter("suri"),request.getParameter("object_property"),processObject);
            getResourceBase().setAttribute(encode(request.getParameter("suri")),getConfig(request.getParameter("plot"), request.getParameter(PLOT_THEME), request.getParameter("measurement_unit"), totals, request.getParameter("object_property"), processObject.toString()));
            getResourceBase().updateAttributesToDB();
        }catch (SWBException swbe) {
            LOG.error(swbe);
        }
    }

    private void selectObjectProperty(ProcessInstance pinst, PrintWriter out, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ArrayList pobjs = new ArrayList();

        getObjectsFromInstance(pinst, pobjs);
        Iterator<SWBClass> objit = pobjs.iterator();
        while(objit.hasNext()) {
            SWBClass pobj =  objit.next();
            Iterator<SemanticProperty> spit = pobj.getSemanticObject().listProperties();
            while(spit.hasNext()) {
                SemanticProperty sp = spit.next();
                out.print("                  <option value=\"" +  sp.getPropId() + "\"" + (getAttribute(pinst.getProcessType().getURI(),"object_property").equalsIgnoreCase(sp.getPropId()) ? " selected" : "") + " >" + getTitle(sp,paramRequest) +  "</option>\n");
            }
        }
    }

    private void getObjectsFromInstance(Instance inst, ArrayList pobjs) {
        Iterator<ItemAwareReference> objit = inst.listItemAwareReferences();
        while(objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj =  item.getProcessObject();
            //TODO: Verificar nombre del ItemAware
            if (!pobjs.contains(obj))
                pobjs.add(obj);
        }
        Iterator<FlowNodeInstance> foit = null;
        if (inst instanceof ProcessInstance) {
    			foit = ((ProcessInstance)inst).listFlowNodeInstances();
	    }
	    
	    if (inst instanceof SubProcessInstance) {
	    		foit = ((SubProcessInstance)inst).listFlowNodeInstances();
	    }
	    
	    if (null != foit) {
	        while(foit.hasNext()) {
	            FlowNodeInstance flobin = foit.next();
	            if (flobin instanceof SubProcessInstance)
	                getObjectsFromInstance((SubProcessInstance)flobin, pobjs);
	        }
	    }
    }

    private void setPlotTheme(String plotTheme) {
        String[] blueTheme = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
        String[] redTheme = {"#FF0000","#E42217","#E41B17","#F62817","#F62217","#E42217","#F80000","#C80000","#B80000","#900000"};
        String[] greenTheme = {"#4AA02C","#57E964","#59E817","#4CC552","#4CC417","#52D017","#41A317","#3EA99F","#348781","#387C44"};
        if ("1".equalsIgnoreCase(plotTheme)) {
            opacity = "0.4";
            colour = "#3090C7";
            colours = blueTheme;
        }else if ("2".equalsIgnoreCase(plotTheme)) {
            opacity = "0.4";
            colour = "#4CC552";
            colours = greenTheme;
        }else if ("3".equalsIgnoreCase(plotTheme)) {
            opacity = "0.7";
            colour = "#FF0000";
            colours = redTheme;
        }
    }

    private String getData(Process process) {
        Object minimum = null;
        Object average = null;
        Object maximum = null;
        String objectProperty = getAttribute(process.getURI(),"object_property");
        String processObject = getAttribute(process.getURI(),"process_object");
        StringBuilder data = new StringBuilder();
        if (!"".equalsIgnoreCase(objectProperty)) {
            minimum = Ajax.notNull(CaseProcessObject.minimum(process, processObject, objectProperty), "0.0");
            average = Ajax.notNull(CaseProcessObject.average(process, processObject, objectProperty), "0.0");
            maximum = Ajax.notNull(CaseProcessObject.maximum(process, processObject, objectProperty), "0.0");
        }else
            minimum = average = maximum = 0;
        data.append("[" + minimum + "," + average + "," + maximum + "]");
        return data.toString();
    }

    private String getDataPie(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        Object minimum = null;
        Object average = null;
        Object maximum = null;
        StringBuilder data = new StringBuilder();
        String objectProperty = getAttribute(process.getURI(),"object_property");
        String processObject = getAttribute(process.getURI(),"process_object");
        String displayTotals = getAttribute(process.getURI(),"display_totals");
        if (!"".equalsIgnoreCase(objectProperty)) {
            minimum = CaseProcessObject.minimum(process, processObject, objectProperty);
            average = CaseProcessObject.average(process, processObject, objectProperty);
            maximum = CaseProcessObject.maximum(process, processObject, objectProperty);
        }else
            minimum = average = maximum = 0;
        data.append("{y: " + minimum);
        data.append(", text: \"" + paramRequest.getLocaleString("minimum") + "\", color: \"" + colours[0] + "\"" + ("1".equalsIgnoreCase(displayTotals) ? ", tooltip: " + getToolTips(paramRequest, "minimum", minimum, process.getURI()) : "") + "},");
        data.append("{y: " + average);
        data.append(", text: \"" + paramRequest.getLocaleString("average") + "\", color: \"" + colours[1] + "\"" + ("1".equalsIgnoreCase(displayTotals) ? ", tooltip: " + getToolTips(paramRequest, "average", average, process.getURI()) : "") + "},");
        data.append("{y: " + maximum);
        data.append(", text: \"" + paramRequest.getLocaleString("maximum") + "\", color: \"" + colours[2] + "\"" + ("1".equalsIgnoreCase(displayTotals) ? ", tooltip: " + getToolTips(paramRequest, "maximum", maximum, process.getURI()) : "") + "}");
        return data.toString();
    }

    private String getLabels(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        Object min = null;
        Object avg = null;
        Object max = null;
        StringBuilder labels = new StringBuilder();
        StringBuilder minimum = new StringBuilder();
        StringBuilder average = new StringBuilder();
        StringBuilder maximum = new StringBuilder();
        String objectProperty = getAttribute(process.getURI(),"object_property");
        String processObject = getAttribute(process.getURI(),"process_object");
        String measurement_unit = getAttribute(process.getURI(),"measurement_unit");
        String displayTotals = getAttribute(process.getURI(),"display_totals");
        if (!"".equalsIgnoreCase(objectProperty)) {
            min = Ajax.notNull(CaseProcessObject.minimum(process, processObject, objectProperty), "0.0");
            avg = Ajax.notNull(CaseProcessObject.average(process, processObject, objectProperty), "0.0");
            max = Ajax.notNull(CaseProcessObject.maximum(process, processObject, objectProperty), "0.0");
        }else
            min = avg = max = 0;
        minimum.append("" + min + " " + measurement_unit);
        average.append("" + avg + " " + measurement_unit);
        maximum.append("" + max + " " + measurement_unit);
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + " " + ("1".equalsIgnoreCase(displayTotals) ? minimum : "") + "', '" + paramRequest.getLocaleString("average") + " " + ("1".equalsIgnoreCase(displayTotals) ? average : "") + "','" + paramRequest.getLocaleString("maximum") + " " + ("1".equalsIgnoreCase(displayTotals) ? maximum : "") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getTitles(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + "', '" + paramRequest.getLocaleString("average") + "','" + paramRequest.getLocaleString("maximum") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getToolTips(SWBParamRequest paramRequest, String function, Object value, String suri) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        StringBuilder total = new StringBuilder();
        total.append(" " + value);
        total.append(" " + getAttribute(suri,"measurement_unit"));
        labels.append("\"" + getObjectTitle(suri,paramRequest) + " " + paramRequest.getLocaleString(function) + total.toString() + "\"");
        return labels.toString();
    }

    private String getAttribute(String suri, String title) {
        String attribute = "";
        String config = getResourceBase().getAttribute(encode(suri), "");
        if ("plot".equals(title)) {
            if (config.length() > 0) attribute = config.substring(0, 1); else attribute = "2";
        }else if (PLOT_THEME.equals(title)) {
            if (config.length() > 1) attribute = config.substring(1, 2); else attribute = "1";
        }else if ("display_totals".equals(title)) {
            if (config.length() > 2) attribute = config.substring(2, 3); else attribute = "0";
        }else if ("measurement_unit".equals(title) && config.indexOf("MEASUREMENT_UNIT") > -1) {
            attribute = config.substring(config.indexOf("MEASUREMENT_UNIT")+16, config.lastIndexOf("MEASUREMENT_UNIT"));
        }else if ("object_property".equals(title) && config.indexOf("OBJECT_PROPERTY") > -1) {
            attribute = config.substring(config.indexOf("OBJECT_PROPERTY")+15, config.lastIndexOf("OBJECT_PROPERTY"));
        }else if ("process_object".equals(title) && config.indexOf(PROCESS_OBJECT) > -1) {
            attribute = config.substring(config.indexOf(PROCESS_OBJECT)+14, config.lastIndexOf(PROCESS_OBJECT));
        }
        return attribute;
    }

    private String getConfig(String plot, String plotTheme, String measurementUnit, String totals, String objectProperty, String processObject) {
        StringBuilder config = new StringBuilder();
        config.append(plot).append(plotTheme).append(totals);
        if (null != measurementUnit && !"".equals(measurementUnit)) config.append("MEASUREMENT_UNIT"+measurementUnit+"MEASUREMENT_UNIT");
        if (null != objectProperty && !"".equals(objectProperty)) config.append("OBJECT_PROPERTY"+objectProperty+"OBJECT_PROPERTY");
        if (null != processObject && !"".equals(processObject)) config.append(PROCESS_OBJECT+processObject+PROCESS_OBJECT);
        return config.toString();
    }

    private void setProcessObject(String suri, String propId, StringBuilder processObject) {
        ArrayList pobjs = new ArrayList();
        Process process = getProcess(suri);
        ProcessInstance pinst = CaseProcessInstance.pop(process);
        if (null != propId && !"".equals(propId)) {
            getObjectsFromInstance(pinst, pobjs);
            Iterator<SWBClass> objit = pobjs.iterator();
            while(objit.hasNext()) {
                SWBClass pobj =  objit.next();
                Iterator<SemanticProperty> spit = pobj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (propId.equalsIgnoreCase(sp.getPropId()))
                        processObject.append(pobj.getSemanticObject().getSemanticClass().getName());
                }
            }
        }
    }

    private String getObjectTitle(String suri, SWBParamRequest paramRequest) {
        ArrayList pobjs = new ArrayList();
        Process process = getProcess(suri);
        ProcessInstance pinst = CaseProcessInstance.pop(process);
        getObjectsFromInstance(pinst, pobjs);
        StringBuilder propertyname = new StringBuilder();
        Iterator<SWBClass> objit = pobjs.iterator();
        while(objit.hasNext()) {
            SWBClass pobj =  objit.next();
            Iterator<SemanticProperty> spit = pobj.getSemanticObject().listProperties();
            while(spit.hasNext()) {
                SemanticProperty sp = spit.next();
                if (getAttribute(suri, "object_property").equalsIgnoreCase(sp.getPropId()))
                    propertyname.append(getTitle(sp, paramRequest));
            }
        }
        return propertyname.toString();
    }

    private String getProcessTitle(String suri) {
        String title = "";
        if (null != suri) {
            Process process = getProcess(suri);
            if (null != process)
                title = process.getTitle();
        }
        return title;
    }

    private Process getProcess(String suri) {

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(suri);
        Process process = null;
        if (gobj instanceof org.semanticwb.process.model.Process) {
            process = (org.semanticwb.process.model.Process) gobj;
        }
        return process;
    }

    private String getTitle(SemanticProperty sp, SWBParamRequest paramRequest) {
        if (null != sp) {
            if (null != sp.getDisplayName(paramRequest.getUser().getLanguage()))
                return sp.getDisplayName(paramRequest.getUser().getLanguage());
            else
                return sp.getName();
        }else
            return "";
    }

    private String encode(String suri) {
        return java.net.URLEncoder.encode(suri).replaceAll("%", "100");
    }
}
