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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.resources.kpi.ResponseTimeStages;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SubProcessInstance;
import org.semanticwb.process.resources.kpi.CaseProcessInstance;

/**
 *
 * @author Sergio Téllez
 */
public class ResponseTime extends GenericResource {
    private static final Logger LOG = SWBUtils.getLogger(ResponseTime.class);
    String opacity = "0.4";
    String colour = "#3090C7";
    String[] colours = {colour, "#1589FF", "#0760F9", "#157DEC", "#6698FF", "", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
    String[] highColours = {"#EB8EBF", "#AB91BC", "#637CB0", "#92C2DF", "#BDDDE4", "#69BF8E", "#B0D990", "#F7FA7B", "#F9DF82", "#E46F6A"};

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (SWBParamRequest.Action_EDIT.equals(paramRequest.getAction())) {
            doAdminCase(request, response, paramRequest);
        } else {
            doAdminResume(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");


        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL edit = paramRequest.getRenderUrl();
        edit.setMode(SWBResourceURL.Mode_EDIT);
        edit.setParameter("suri", suri);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print("    <button  dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + edit.toString() + "',this.domNode);return false;\">" + paramRequest.getLocaleString("config") + "</button>");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("    <legend>" + paramRequest.getLocaleString("RESPONSE_TIME") + "</legend>\n");
        if (null != suri) {
            doGraph(request, response, paramRequest, suri);
        }
        out.print("  </fieldset>\n");
        out.print("</div>\n");
    }

    public void doAdminCase(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_EDIT);
        url.setAction("edit");
        url.setParameter("suri", suri);
        updateAttributes(request);
        out.print("<script>\n");
        out.print(" function stage(form) {\n");
        out.print("     form.action=\"" + url.toString() + "\";\n");
        out.print("     form.submit();\n");
        out.print(" }\n");
        out.print("</script>\n");
        url.setAction("resume");
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("TIME_STAGE"));
        out.print("  </fieldset>\n");
        out.print("  <form id=\"" + getResourceBase().getId() + "/restime\" name=\"restime\" action=" + url.toString() + " method=\"post\" onsubmit=\"submitForm('" + getResourceBase().getId() + "/restime'); return false;\">\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("INIT_STAGE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"init_stage\" name=\"init_stage\" onchange=\"stage(form);\">\n");
        out.print("                          <option value=\"\">Seleccione</option>\n");
        Process process = getProcess(suri);
        ProcessInstance pinst = process.getProcessInstance();
        if (null != pinst) {
            Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
            while (flowbis.hasNext()) {
                FlowNodeInstance obj = flowbis.next();
                selectInitStage(obj, out, paramRequest, suri);
            }
        }
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("FINAL_STAGE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"final_stage\" name=\"final_stage\">\n");
        if (null != pinst) {
            Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
            while (flowbis.hasNext()) {
                FlowNodeInstance obj = flowbis.next();
                selectFinalStage(obj, out, paramRequest, suri);
            }
        }
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
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("blue") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("green") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("red") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("seconds") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("minutes") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("hours") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"4\"" + ("4".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("days") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"display_totals\" type=\"checkbox\" name=\"display_totals\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri, "display_totals")) ? " checked" : "") + "> " + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button  dojoType=\"dijit.form.Button\" type=\"submit\" >" + paramRequest.getLocaleString("apply") + "</button>");
        url = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_VIEW);
        url.setParameter("suri", suri);

        out.print("         <button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + url.toString() + "',this.domNode);return false;\">" + paramRequest.getLocaleString("return") + "</button>");
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_EDIT);
        url.setParameter("suri", suri);
        updateAttributes(request);
        Process process = getProcess(suri);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("TIME_STAGE"));
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute(encode(suri), "")) ? getProcessTitle(suri) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("INIT_STAGE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getAttribute(suri, "init_stage")) ? getStageTitle(process.getId(), getAttribute(suri, "init_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("FINAL_STAGE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getAttribute(suri, "final_stage")) ? getStageTitle(process.getId(), getAttribute(suri, "final_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + ("1".equalsIgnoreCase(getAttribute(suri, "plot")) ? paramRequest.getLocaleString("bars") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri, "plot")) ? paramRequest.getLocaleString("pie") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri, "plot")) ? paramRequest.getLocaleString("area") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? paramRequest.getLocaleString("blue") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? paramRequest.getLocaleString("green") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? paramRequest.getLocaleString("red") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? paramRequest.getLocaleString("seconds") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? paramRequest.getLocaleString("minutes") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? paramRequest.getLocaleString("hours") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("4".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? paramRequest.getLocaleString("days") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        if ("1".equalsIgnoreCase(getAttribute(suri, "display_totals"))) {
            out.print("                  <td>" + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        } else {
            out.print("                  <td>" + paramRequest.getLocaleString("DEFAULT_VIEW") + "</td>\n");
        }
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");

        SWBResourceURL edit = paramRequest.getRenderUrl();
        edit.setMode(SWBResourceURL.Mode_EDIT);
        edit.setParameter("suri", suri);

        out.print("         <button dojoType=\"dijit.form.Button\" onClick=\"submitUrl('" + edit.toString() + "',this.domNode); return false;\">" + paramRequest.getLocaleString("config") + "</button>");
        url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_VIEW);
        url.setParameter("suri", suri);
        out.print("         <button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + url.toString() + "',this.domNode); return false;\">" + paramRequest.getLocaleString("return") + "</button>");
        out.print("     </fieldset>\n");
        out.print("</div>\n");
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
        Process process = getProcess(suri);
        if ("1".equalsIgnoreCase(getAttribute(suri, "plot"))) {
            doBars(request, response, paramRequest, process);
        } else if ("3".equalsIgnoreCase(getAttribute(suri, "plot"))) {
            doArea(request, response, paramRequest, process);
        } else {
            doPie(request, response, paramRequest, process);
        }
    }

    private void updateAttributes(HttpServletRequest request) {
        try {
            if (null != request.getParameter("init_stage")) {
                getResourceBase().setAttribute(encode(request.getParameter("suri")), getConfig(request.getParameter("init_stage"), request.getParameter("final_stage"), request.getParameter("plot"), request.getParameter("plot_theme"), request.getParameter("time_unit"), request.getParameter("display_totals"), request.getParameter("suri")));
                getResourceBase().updateAttributesToDB();
            }
        } catch (SWBException swbe) {
            LOG.error(swbe);
        }
    }

    private String getConfig(String initStage, String finalStage, String plot, String colours, String time, String totals, String suri) {
        StringBuilder config = new StringBuilder();
        if (null != plot && !"".equals(plot)) {
            config.append(plot);
        } else {
            config.append(getAttribute(suri, "plot"));
        }
        if (null != colours && !"".equals(colours)) {
            config.append(colours);
        } else {
            config.append(getAttribute(suri, "plot_theme"));
        }
        if (null != time && !"".equals(time)) {
            config.append(time);
        } else {
            config.append(getAttribute(suri, "time_unit"));
        }
        if (null != totals && !"".equals(totals)) {
            config.append(totals);
        } else {
            config.append(getAttribute(suri, "display_totals"));
        }
        if (null != initStage && !"".equals(initStage)) {
            config.append("INIT_STAGE" + initStage + "INIT_STAGE");
        } else {
            if (!"".equals(getAttribute(suri, "init_stage"))) {
                config.append(getAttribute(suri, "init_stage"));
            }
        }
        if (null != finalStage && !"".equals(finalStage)) {
            config.append("FINAL_STAGE" + finalStage + "FINAL_STAGE");
        } else {
            if (!"".equals(getAttribute(suri, "final_stage"))) {
                config.append(getAttribute(suri, "final_stage"));
            }
        }
        return config.toString();
    }

    private String getAttribute(String suri, String title) {
        String attribute = "";
        String config = getResourceBase().getAttribute(encode(suri), "");
        if ("plot".equals(title)) {
            if (config.length() > 0) {
                attribute = config.substring(0, 1);
            } else {
                attribute = "2";
            }
        } else if ("plot_theme".equals(title)) {
            if (config.length() > 1) {
                attribute = config.substring(1, 2);
            } else {
                attribute = "1";
            }
        } else if ("time_unit".equals(title)) {
            if (config.length() > 2) {
                attribute = config.substring(2, 3);
            } else {
                attribute = "1";
            }
        } else if ("display_totals".equals(title)) {
            if (config.length() > 3) {
                attribute = config.substring(3, 4);
            } else {
                attribute = "0";
            }
        } else if ("init_stage".equals(title) && config.indexOf("INIT_STAGE") > -1) {
            attribute = config.substring(4 + 10, config.lastIndexOf("INIT_STAGE"));
        } else if ("final_stage".equals(title) && config.indexOf("FINAL_STAGE") > -1) {
            attribute = config.substring(config.indexOf("FINAL_STAGE") + 11, config.lastIndexOf("FINAL_STAGE"));
        }
        return attribute;
    }

    private ProcessInstance getProcessInstance(String processId) {
        Iterator<ProcessSite> isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                if (processId.equalsIgnoreCase(process.getId())) {
                    return CaseProcessInstance.pop(process);
                }
            }
        }
        return null;
    }

    private void selectInitStage(FlowNodeInstance ai, PrintWriter out, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
    		if (null == ai) return;
    		
        if (!"".equals(getTitle(ai, paramRequest)) && !"org.semanticwb.process.model.EndEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()) && !"org.semanticwb.process.model.TimerIntermediateCatchEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName())) {
            out.print("                  <option value=\"" + ai.getFlowNodeType().hashCode() + "\"" + (getAttribute(suri, "init_stage").equalsIgnoreCase(("" + ai.getFlowNodeType().hashCode())) ? " selected" : "") + " >" + getTitle(ai, paramRequest) + "</option>\n");
        }
        if (ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance) ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    selectInitStage(actinst, out, paramRequest, suri);
                }
            }
        }
    }

    private GenericIterator<FlowNodeInstance> getTargetStage(FlowNodeInstance ai, String suri) {
        GenericIterator<FlowNodeInstance> targetInstances = null;
        if (("" + ai.getFlowNodeType().hashCode()).equals(getAttribute(suri, "init_stage"))) {
            targetInstances = ai.listTargetInstances();
        } else if (ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance) ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    getTargetStage(actinst, suri);
                }
            }
        }
        return targetInstances;
    }

    private void selectFinalStage(FlowNodeInstance ai, PrintWriter out, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
        ArrayList<FlowNodeInstance> stage = new ArrayList<>();
        setReachableStage(getTargetStage(ai, suri), stage);
        Iterator<FlowNodeInstance> targetStage = stage.listIterator();
        while (targetStage.hasNext()) {
            FlowNodeInstance fni = targetStage.next();
            if (null != fni && !"".equals(getTitle(fni, paramRequest))) {
                out.print("                  <option value=\"" + fni.getFlowNodeType().hashCode() + "\"" + (getAttribute(suri, "final_stage").equalsIgnoreCase(("" + fni.getFlowNodeType().hashCode())) ? " selected" : "") + " >" + getTitle(fni, paramRequest) + "</option>\n");
            }
        }
    }

    private void setReachableStage(GenericIterator<FlowNodeInstance> targetInstances, ArrayList<FlowNodeInstance> stage) {
        if (null != targetInstances) {
            while (targetInstances.hasNext()) {
                FlowNodeInstance fni = targetInstances.next();
                stage.add(fni);
                setReachableStage(fni.listTargetInstances(), stage);
            }
        }
    }

    private String getStageTitle(FlowNodeInstance ai, String stageId, SWBParamRequest paramRequest) {
        String title = null;
        if (stageId.equalsIgnoreCase("" + ai.getFlowNodeType().hashCode())) {
            title = getTitle(ai, paramRequest);
        }
        if (ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance) ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    getStageTitle(actinst, stageId, paramRequest);
                }
            }
        }
        return title;
    }

    private String getStageTitle(String processId, String stageId, SWBParamRequest paramRequest) {
        String title = null;
        ProcessInstance pinst = getProcessInstance(processId);
        if (null != pinst) {
            Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
            while (flowbis.hasNext()) {
                FlowNodeInstance obj = flowbis.next();
                if (null != getStageTitle(obj, stageId, paramRequest)) {
                    title = getStageTitle(obj, stageId, paramRequest);
                }
            }
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

    private void setPlotTheme(String plotTheme) {
        String[] blueTheme = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
        String[] redTheme = {"#FF0000", "#E42217", "#E41B17", "#F62817", "#F62217", "#E42217", "#F80000", "#C80000", "#B80000", "#900000"};
        String[] greenTheme = {"#4AA02C", "#57E964", "#59E817", "#4CC552", "#4CC417", "#52D017", "#41A317", "#3EA99F", "#348781", "#387C44"};
        if ("1".equalsIgnoreCase(plotTheme)) {
            opacity = "0.4";
            colour = "#3090C7";
            colours = blueTheme;
        } else if ("2".equalsIgnoreCase(plotTheme)) {
            opacity = "0.4";
            colour = "#4CC552";
            colours = greenTheme;
        } else if ("3".equalsIgnoreCase(plotTheme)) {
            opacity = "0.7";
            colour = "#FF0000";
            colours = redTheme;
        }
    }

    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), "plot_theme"));
        String initStage = getAttribute(process.getURI(), "init_stage");
        String finalStage = getAttribute(process.getURI(), "final_stage");

        long lid = System.currentTimeMillis();

        out.println("<script type=\"text/javascript\">");
        out.println("   dojo.require(\"dojox.charting.Chart2D\");");
        out.println("   dojo.require(\"dojox.charting.themes.PlotKit.blue\");");
        out.println("   dojo.require(\"dojox.charting.action2d.MoveSlice\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Highlight\");");
        out.println("   makeObjects = function(){");
        out.println("       var chart = new dojox.charting.Chart2D(\"" + lid + "_instances\");");
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
        out.println("<div id=\"" + lid + "_title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id=\"" + lid + "_instances\" style=\"width: 400px; height: 300px;\"></div>");
        out.println("<div id=\"" + lid + "_stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(initStage) ? getStageTitle(process.getId(), initStage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(finalStage) ? getStageTitle(process.getId(), finalStage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), "plot_theme"));
        long lid = System.currentTimeMillis();
        String initStage = getAttribute(process.getURI(), "init_stage");
        String finalStage = getAttribute(process.getURI(), "final_stage");
        out.println("<div id=\"" + lid + "_title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id='" + lid + "_instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var areagraph = new Grafico.AreaGraph($('" + lid + "_instances'), { workload: " + getData(process) + " },");
        out.println("        {");
        out.println("           grid :                false,");
        out.println("           area_opacity :        " + opacity + ",");
        out.println("           plot_padding :        10,");
        out.println("           font_size :           10,");
        out.println("           colors :              { workload: '" + colour + "' },");
        out.println("           label_color :         \"#348781\",");
        if ("1".equalsIgnoreCase(getAttribute(process.getURI(), "display_totals"))) {
            out.println("           markers :             \"value\",");
        }
        out.println("           meanline :            false,");
        out.println("           draw_axis :           false,\n");
        out.println("           labels :			  " + getTitles(paramRequest) + ",\n");
        out.println("           datalabels :          {workload: '" + paramRequest.getLocaleString("time") + "'}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\"" + lid + "_stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(initStage) ? getStageTitle(process.getId(), initStage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(finalStage) ? getStageTitle(process.getId(), finalStage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), "plot_theme"));
        long lid = System.currentTimeMillis();
        String initStage = getAttribute(process.getURI(), "init_stage");
        String finalStage = getAttribute(process.getURI(), "final_stage");
        out.println("<div id=\"" + lid + "_title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id='" + lid + "_instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var bargraph = new Grafico.BarGraph($('" + lid + "_instances'), " + getData(process) + ",\n");
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
        out.println("<div id=\"" + lid + "_stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(initStage) ? getStageTitle(process.getId(), initStage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(finalStage) ? getStageTitle(process.getId(), finalStage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    private String getTitles(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + "', '" + paramRequest.getLocaleString("average") + "','" + paramRequest.getLocaleString("maximum") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getData(Process process) {
        long minimum = 0;
        long average = 0;
        long maximum = 0;
        StringBuilder data = new StringBuilder();
        String initStage = getAttribute(process.getURI(), "init_stage");
        String finalStage = getAttribute(process.getURI(), "final_stage");
        String timeUnit = getAttribute(process.getURI(), "time_unit");
        if (!"".equalsIgnoreCase(initStage) && !"".equalsIgnoreCase(finalStage)) {
            minimum = ResponseTimeStages.getMinimumTimeStages(process, initStage, finalStage);
            average = ResponseTimeStages.getAverageTimeStages(process, initStage, finalStage);
            maximum = ResponseTimeStages.getMaximumTimeStages(process, initStage, finalStage);
        }
        data.append("[");
        if ("1".equalsIgnoreCase(timeUnit)) {
            data.append(minimum / 1000 + "," + average / 1000 + "," + maximum / 1000);
        } else if ("2".equalsIgnoreCase(timeUnit)) {
            data.append(minimum / 60000 + "," + average / 60000 + "," + maximum / 60000);
        } else if ("3".equalsIgnoreCase(timeUnit)) {
            data.append(minimum / 3600000 + "," + average / 3600000 + "," + maximum / 3600000);
        } else {
            data.append(minimum / 86400000 + "," + average / 86400000 + "," + maximum / 86400000);
        }
        data.append("]");
        return data.toString();
    }

    private String getDataPie(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        long minimum = 0;
        long average = 0;
        long maximum = 0;
        StringBuilder data = new StringBuilder();
        String initStage = getAttribute(process.getURI(), "init_stage");
        String finalStage = getAttribute(process.getURI(), "final_stage");
        String timeUnit = getAttribute(process.getURI(), "time_unit");
        String displayTotals = getAttribute(process.getURI(), "display_totals");
        if (!"".equalsIgnoreCase(initStage) && !"".equalsIgnoreCase(finalStage)) {
            minimum = ResponseTimeStages.getMinimumTimeStages(process, initStage, finalStage);
            average = ResponseTimeStages.getAverageTimeStages(process, initStage, finalStage);
            maximum = ResponseTimeStages.getMaximumTimeStages(process, initStage, finalStage);
        }
        data.append("{y: ");
        if ("1".equalsIgnoreCase(timeUnit)) {
            data.append(minimum / 1000);
        } else if ("2".equalsIgnoreCase(timeUnit)) {
            data.append(minimum / 60000);
        } else if ("3".equalsIgnoreCase(timeUnit)) {
            data.append(minimum / 3600000);
        } else {
            data.append(minimum / 86400000);
        }
        data.append(", text: \"" + paramRequest.getLocaleString("minimum") + "\", color: \"" + colours[0] + "\"" + ("1".equalsIgnoreCase(displayTotals) ? ", tooltip: " + getToolTips(paramRequest, timeUnit, "minimum", minimum) : "") + "},");
        data.append("{y: ");
        if ("1".equalsIgnoreCase(timeUnit)) {
            data.append(average / 1000);
        } else if ("2".equalsIgnoreCase(timeUnit)) {
            data.append(average / 60000);
        } else if ("3".equalsIgnoreCase(timeUnit)) {
            data.append(average / 3600000);
        } else {
            data.append(average / 86400000);
        }
        data.append(", text: \"" + paramRequest.getLocaleString("average") + "\", color: \"" + colours[1] + "\"" + ("1".equalsIgnoreCase(displayTotals) ? ", tooltip: " + getToolTips(paramRequest, timeUnit, "average", average) : "") + "},");
        data.append("{y: ");
        if ("1".equalsIgnoreCase(timeUnit)) {
            data.append(maximum / 1000);
        } else if ("2".equalsIgnoreCase(timeUnit)) {
            data.append(maximum / 60000);
        } else if ("3".equalsIgnoreCase(timeUnit)) {
            data.append(maximum / 3600000);
        } else {
            data.append(maximum / 86400000);
        }
        data.append(", text: \"" + paramRequest.getLocaleString("maximum") + "\", color: \"" + colours[2] + "\"" + ("1".equalsIgnoreCase(displayTotals) ? ", tooltip: " + getToolTips(paramRequest, timeUnit, "maximum", maximum) : "") + "}");
        return data.toString();
    }

    private String getLabels(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        long min = 0;
        long avg = 0;
        long max = 0;
        StringBuilder labels = new StringBuilder();
        StringBuilder minimum = new StringBuilder();
        StringBuilder average = new StringBuilder();
        StringBuilder maximum = new StringBuilder();
        String initStage = getAttribute(process.getURI(), "init_stage");
        String finalStage = getAttribute(process.getURI(), "final_stage");
        String timeUnit = getAttribute(process.getURI(), "time_unit");
        String displayTotals = getAttribute(process.getURI(), "display_totals");
        if (!"".equalsIgnoreCase(initStage) && !"".equalsIgnoreCase(finalStage)) {
            min = ResponseTimeStages.getMinimumTimeStages(process, initStage, finalStage);
            avg = ResponseTimeStages.getAverageTimeStages(process, initStage, finalStage);
            max = ResponseTimeStages.getMaximumTimeStages(process, initStage, finalStage);
        }
        if ("1".equalsIgnoreCase(timeUnit)) {
            minimum.append("" + min / 1000 + " " + paramRequest.getLocaleString("seconds"));
            average.append("" + avg / 1000 + " " + paramRequest.getLocaleString("seconds"));
            maximum.append("" + max / 1000 + " " + paramRequest.getLocaleString("seconds"));
        } else if ("2".equalsIgnoreCase(timeUnit)) {
            minimum.append("" + min / 60000 + " " + paramRequest.getLocaleString("minutes"));
            average.append("" + avg / 60000 + " " + paramRequest.getLocaleString("minutes"));
            maximum.append("" + max / 60000 + " " + paramRequest.getLocaleString("minutes"));
        } else if ("3".equalsIgnoreCase(timeUnit)) {
            minimum.append("" + min / 3600000 + " " + paramRequest.getLocaleString("hours"));
            average.append("" + avg / 3600000 + " " + paramRequest.getLocaleString("hours"));
            maximum.append("" + max / 3600000 + " " + paramRequest.getLocaleString("hours"));
        } else {
            minimum.append("" + min / 86400000 + " " + paramRequest.getLocaleString("days"));
            average.append("" + avg / 86400000 + " " + paramRequest.getLocaleString("days"));
            maximum.append("" + max / 86400000 + " " + paramRequest.getLocaleString("days"));
        }
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + " " + ("1".equalsIgnoreCase(displayTotals) ? minimum : "") + "', '" + paramRequest.getLocaleString("average") + " " + ("1".equalsIgnoreCase(displayTotals) ? average : "") + "','" + paramRequest.getLocaleString("maximum") + " " + ("1".equalsIgnoreCase(displayTotals) ? maximum : "") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getToolTips(SWBParamRequest paramRequest, String timeUnit, String function, long time) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        StringBuilder total = new StringBuilder();
        if ("1".equalsIgnoreCase(timeUnit)) {
            total.append(" " + time / 1000);
            total.append(" " + paramRequest.getLocaleString("seconds"));
        } else if ("2".equalsIgnoreCase(timeUnit)) {
            total.append(" " + time / 60000);
            total.append(" " + paramRequest.getLocaleString("minutes"));
        } else if ("3".equalsIgnoreCase(timeUnit)) {
            total.append(" " + time / 3600000);
            total.append(" " + paramRequest.getLocaleString("hours"));
        } else {
            total.append(" " + time / 86400000);
            total.append(" " + paramRequest.getLocaleString("days"));
        }
        labels.append("\"" + paramRequest.getLocaleString("time") + " " + paramRequest.getLocaleString(function) + total.toString() + "\"");
        return labels.toString();
    }

    private String getProcessTitle(String suri) {
        String title = "";
        if (null != suri) {
            Process process = getProcess(suri);
            if (null != process) {
                title = process.getTitle();
            }
        }
        return title;
    }

    private String getTitle(FlowNodeInstance fni, SWBParamRequest paramRequest) {
        if (null != fni) {
            if (null != fni.getFlowNodeType().getTitle(paramRequest.getUser().getLanguage())) {
                return fni.getFlowNodeType().getTitle(paramRequest.getUser().getLanguage());
            } else {
                return fni.getFlowNodeType().getTitle();
            }
        } else {
            return "";
        }
    }

    private String encode(String suri) {
        return java.net.URLEncoder.encode(suri).replaceAll("%", "100");
    }
}
