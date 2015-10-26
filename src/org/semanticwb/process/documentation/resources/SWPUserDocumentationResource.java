package org.semanticwb.process.documentation.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.documentation.resources.utils.SWPUtils;
import org.semanticwb.process.model.ProcessGroup;

/**
 * Componente que permite la visualización de la documentación de un proceso.
 * @author carlos.alvarez
 */
public class SWPUserDocumentationResource extends GenericAdmResource {
    private final Logger log = SWBUtils.getLogger(SWPUserDocumentationResource.class);
    public final static String MODE_VIEW_DOCUMENTATION = "m_vdoc";//Modo EDITAR de sección de documentación
    public final static String MODE_EXPORT_MODEL = "m_expm";
    public final static String PARAM_PROCESSGROUP = "pg";
    public final static String PARAM_SORTTYPE = "sort";
    public final static String SORT_NAME = "name";
    public final static String SORT_NAMEDESC = "namedesc";
    public final static String SORT_DATE = "date";
    public final static String SORT_DATEDESC = "datedesc";
    public final static String SORT_TYPE = "type";
    public final static String SORT_TYPEDESC = "typedesc";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        switch (mode) {
            case MODE_VIEW_DOCUMENTATION:
                doViewDocumentation(request, response, paramRequest);
                break;
            case MODE_EXPORT_MODEL:
                doExportModel(request, response, paramRequest);
                break;
            default:
                super.processRequest(request, response, paramRequest);
                break;
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/userDocumentation.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        String idpg = request.getParameter(PARAM_PROCESSGROUP) != null ? request.getParameter(PARAM_PROCESSGROUP) : "";
        
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            request.setAttribute(SWPUtils.LIST_PROCESSES, getItems(ProcessGroup.ClassMgr.getProcessGroup(idpg, paramRequest.getWebPage().getWebSite())));
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doView, " + path + ", " + ex.getMessage());
        }
    }

    public void doViewDocumentation(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/userDocumentationView.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doViewDocumentation, " + path + ", " + ex.getMessage());
        }
    }

    public void doExportModel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        OutputStream outs = response.getOutputStream();
        String format = request.getParameter("output_format");
        String data = request.getParameter("data");
        String viewBox = request.getParameter("viewBox");
        String[] values = viewBox != null ? viewBox.split("\\ ") : "0 0 3800 2020".split("\\ ");
        Process p = Process.ClassMgr.getProcess(request.getParameter("idp"), paramRequest.getWebPage().getWebSite());
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + (p != null ? p.getTitle() : "Proceso") + "." + format + "\"");
        if ("svg".equalsIgnoreCase(format)) {
            String svg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
                    + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n";
            svg += data;
            //Corregir clases en objetos de datos
            svg = svg.replace("<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\">", "<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\" class=\"itemAware\">");
            svg = svg.replace("<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\">", "<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\" class=\"itemAware\">");
            String vbox = "";
            try {
                vbox = svg.substring(svg.indexOf("viewBox="), svg.indexOf("height="));
                svg = svg.replace(svg.substring(svg.indexOf("viewBox="), svg.indexOf("height=")), "viewBox=\"" + viewBox + "\" ");
            } catch (IndexOutOfBoundsException e) {
                vbox = svg.substring(svg.indexOf("viewBox="), svg.indexOf("class=\"modeler\""));
                svg = svg.replace(svg.substring(svg.indexOf("viewBox="), svg.indexOf("class=\"modeler\"")), "viewBox=\"" + viewBox + "\" ");
            }
            response.setContentType("image/svg+xml");
            outs.write(svg.getBytes("ISO-8859-1"));
        } else if ("png".equals(format)) {
            response.setContentType("image/png; charset=ISO-8859-1");
            String vbox = "";
            try {
                vbox = data.substring(data.indexOf("viewBox="), data.indexOf("height="));
                data = data.replace(data.substring(data.indexOf("viewBox="), data.indexOf("height=")), "viewBox=\"" + viewBox + "\" ");
            } catch (IndexOutOfBoundsException e) {
                vbox = data.substring(data.indexOf("viewBox="), data.indexOf("class=\"modeler\""));
                data = data.replace(data.substring(data.indexOf("viewBox="), data.indexOf("class=\"modeler\"")), "viewBox=\"" + viewBox + "\" ");
            }
            InputStream strStream = new ByteArrayInputStream(data.getBytes("ISO-8859-1"));
            TranscoderInput ti = new TranscoderInput(strStream/*svgFile.toURI().toString()*/);
            TranscoderOutput to = new TranscoderOutput(outs);

            PNGTranscoder t = new PNGTranscoder();
            t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(values[2]) + 2048);
            t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(values[3]) + 1292);
            t.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, Boolean.TRUE);
            try {
                t.transcode(ti, to);
            } catch (TranscoderException ex) {
                log.error("Ocurrió un problema al generar la imagen", ex);
            }
        }
    }
    
    /**
     * Obtiene la lista de items para la tabla de navegación. Incluye objetos {@code Process} y {@code ProcessGroup} a partir del nivel indicado.
     * @param root {@code ProcessGroup} que servirá como raíz del listado.
     * @return Lista de elementos a mostrar en la tabla de navegación.
     */
    public List<Descriptiveable> getItems(ProcessGroup root) {
        List<Descriptiveable> ret = new ArrayList<Descriptiveable>();
        Iterator<ProcessGroup> allGroups;
        
        //Add groups to item list
        if (null == root) {
            allGroups = ProcessGroup.ClassMgr.listProcessGroups(getResourceBase().getWebSite());
        } else {
            allGroups = root.listProcessGroups();
        }
        
        while (allGroups.hasNext()) {
            ProcessGroup group = allGroups.next();
            if (group.isValid()) {
                if (null != root) {
                    ret.add(group);
                } else if (null == group.getParentGroup()) {
                    ret.add(group);
                }
            }
        }
        
        //Add processes to item list if group is not null
        if (null != root) {
            Iterator<Process> processes = root.listProcesses();
            while (processes.hasNext()) {
                Process process = processes.next();
                if (process.isValid()) {
                    ret.add(process);
                }
            }
        }
        
        return ret;
    }
}
