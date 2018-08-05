package org.semanticwb.process.resources.manager;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.process.SWBProcess;
import org.semanticwb.process.model.*;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.xpdl.XPDLParser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Componente que permite la navegación entre los procesos y su documentación.
 * @author carlos.alvarez
 * @author Hasdai Pacheco
 */
public class SWBProcessManagerResource extends GenericAdmResource {
    public static final String MODE_VIEW_DOCUMENTATION = "m_vdoc";
    public static final String MODE_EXPORT_MODEL = "m_expm";
    public static final String MODE_SWBPMODELER = "m_modeler";
    public static final String MODE_GATEWAY = "gateway";
    public static final String MODE_CREATEPG = "m_createpg";
    public static final String MODE_EXPORT = "export";
    public static final String MODE_CREATEPROCESS = "m_createprocess";
    public static final String MODE_RESPONSE = "m_response";
    public static final String MODE_GENERATESVG = "gensvg";
    public static final String PARAM_PROCESSGROUP = "pg";
    public static final String PARAM_PROCESSID = "p_pid";
    public static final String PARAM_SORTTYPE = "sort";
    public static final String SORT_NAME = "name";
    public static final String SORT_NAMEDESC = "namedesc";
    public static final String SORT_DATE = "date";
    public static final String SORT_DATEDESC = "datedesc";
    public static final String SORT_TYPE = "type";
    public static final String SORT_TYPEDESC = "typedesc";
    private static final String ERRORSTRING = "{\"error\":\"_JSONERROR_\"}";
    public static final String LIST_PROCESSES = "listTemplates";
    public static final String ATT_PARAMREQUEST = "paramRequest";
    public static final String ACT_CREATEPROCESS = "a_createprocess";
    public static final String ACT_GETPROCESSJSON = "getProcessJSON";
    public static final String ACT_STOREPROCESS = "storeProcess";
    public static final String ACT_LOADFILE = "loadFile";
    private static final String JSONSTART = "JSONSTART";
    private static final String JSONEND = "JSONEND";
    private static final String PROCESS_PREFIX = "http://www.semanticwebbuilder.org/swb4/process";
    private SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    private static final Logger LOG = SWBUtils.getLogger(SWBProcessManagerResource.class);

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String act = response.getAction();
        WebSite site = response.getWebPage().getWebSite();

        if (ACT_CREATEPROCESS.equals(act)) {
            String processGroupId = request.getParameter(PARAM_PROCESSGROUP);
            if (null != processGroupId) {
                String processName = request.getParameter(Process.swb_title.getName());
                String processDescription = request.getParameter(Process.swb_description.getName());
                String processId = request.getParameter(PARAM_PROCESSID);

                if (null != processName && !processName.isEmpty() && null != processId && !processId.isEmpty()) {
                    ProcessGroup pg = ProcessGroup.ClassMgr.getProcessGroup(processGroupId, site);
                    if (null != pg) {
                        Process p = Process.ClassMgr.createProcess(processId, site);
                        p.setProcessGroup(pg);
                        p.setTitle(processName);
                        p.setDescription(processDescription);

                        response.setRenderParameter(PARAM_PROCESSGROUP, processGroupId);
                        response.setRenderParameter("status", "ok");
                        response.setMode(MODE_RESPONSE);
                    }
                }
            }
        } else {
            super.processAction(request, response);
        }
    }

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
            case MODE_CREATEPROCESS:
                doCreateProcess(request, response, paramRequest);
                break;
            case MODE_RESPONSE:
                doResponse(request, response, paramRequest);
                break;
            case MODE_SWBPMODELER:
                doModeler(request, response, paramRequest);
                break;
            case MODE_GATEWAY:
                doGateway(request, response, paramRequest);
                break;
            case MODE_EXPORT:
            case MODE_GENERATESVG:
                doExport(request, response, paramRequest);
                break;
            default:
                super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String path = "/swbadmin/jsp/process/documentation/userDocumentation.jsp";

        RequestDispatcher rd = request.getRequestDispatcher(path);
        String idpg = request.getParameter(PARAM_PROCESSGROUP) != null ? request.getParameter(PARAM_PROCESSGROUP) : "";

        try {
            request.setAttribute(ATT_PARAMREQUEST, paramRequest);
            request.setAttribute(LIST_PROCESSES, getItems(ProcessGroup.ClassMgr.getProcessGroup(idpg, paramRequest.getWebPage().getWebSite())));
            rd.include(request, response);
        } catch (ServletException ex) {
            LOG.error("Error on doView", ex);
        }
    }

    public void doModeler(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/swbadmin/jsp/process/modeler/modeler.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);

        Process p = Process.ClassMgr.getProcess(request.getParameter(PARAM_PROCESSID), paramRequest.getWebPage().getWebSite());
        try {
            request.setAttribute("process", p);
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    /**
     * Modo para administrar las peticiones del modelador. Actúa como gateway
     * para ejecutar las acciones.
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        OutputStream outs = response.getOutputStream();
        String action = paramRequest.getAction();
        GenericObject go = ont.getGenericObject(request.getParameter("suri"));

        if (ACT_GETPROCESSJSON.equals(action)) { //Obtener el JSON del modelo almacenado
            try {
                if (go != null && go instanceof Process) {
                    Process process = (Process) go;
                    String pJson = process.serialize("JSON");
                    if (null == pJson || pJson.isEmpty()) {
                        pJson = ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgJSONPError"));
                    }
                    response.setContentType("application/json");
                    outs.write(pJson.getBytes("UTF-8"));
                } else {
                    LOG.error("Error to create JSON: Process not found");
                    outs.write(ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgNoProcess")).getBytes("UTF-8"));
                }
            } catch (Exception e) {
                LOG.error("Error to create JSON...", e);
                outs.write(ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgJSONPError")).getBytes("UTF-8"));
            }
        } else if (ACT_LOADFILE.equals(action)) { //Carga de modelo desde archivo swp
            response.setContentType("text/html");
            String json = processFile(request);
            if (json == null) {
                json = ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgJSONPError"));
            }
            outs.write(json.getBytes("UTF-8"));
        } else if (ACT_STOREPROCESS.equals(action)) { //Persistir modelo del proceso
            String jsonStr = request.getParameter("jsonString");
            HashMap<String, JSONObject> hmjson = new HashMap<>();

            if (go != null && go instanceof Process) {
                Process process = (Process) go;
                String str_uri = null;
                JSONArray jsarr = null;
                JSONObject jsobj = null;

                try {
                    if (jsonStr.startsWith(JSONSTART) && jsonStr.endsWith(JSONEND)) {
                        jsonStr = jsonStr.replace(JSONSTART, "");
                        jsonStr = jsonStr.replace(JSONEND, "");

                        try {
                            jsobj = new JSONObject(jsonStr);
                            jsarr = jsobj.getJSONArray("nodes");

                            //identificando los elementos asociados directamente al proceso
                            for (int i = 0; i < jsarr.length(); i++) {
                                try {
                                    jsobj = jsarr.getJSONObject(i);
                                    str_uri = jsobj.getString(SWBProcess.JSONProperties.PROP_URI);
                                    hmjson.put(str_uri, jsobj);
                                } catch (Exception ej) {
                                    LOG.error("Error en elemento del JSON. ", ej);
                                }
                            }
                            boolean endsGood = createProcessElements(process, request, response, paramRequest, hmjson);
                            if (endsGood) {
                                process.setData(jsonStr);
                            } else {
                                outs.write(ERRORSTRING.replace("_JSONERROR_", getError(3)).getBytes());
                            }
                        } catch (Exception ejs) {
                            LOG.error("Error en el JSON recibidopara el proceso "+process.getURI(), ejs);
                            outs.write(ERRORSTRING.replace("_JSONERROR_", getError(3)).getBytes());
                        }
                    }
                } catch (Exception e) {
                    LOG.error("Error al leer JSON para el proceso "+process.getURI(), e);
                    outs.write(ERRORSTRING.replace("_JSONERROR_", getError(3)).getBytes());
                }
            } else {
                LOG.error("Error to create JSON: Process not found");
                outs.write(ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgNoProcess")).getBytes());
            }
        }
    }

    public void doViewDocumentation(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String path = "/swbadmin/jsp/process/documentation/userDocumentationView.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(ATT_PARAMREQUEST, paramRequest);
            rd.include(request, response);
        } catch (ServletException ex) {
            LOG.error("Error on doViewDocumentation", ex);
        }
    }

    public void doCreateProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String path = "/swbadmin/jsp/process/manager/createProcess.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(ATT_PARAMREQUEST, paramRequest);
            rd.include(request, response);
        } catch (ServletException ex) {
            LOG.error("Error on doViewDocumentation", ex);
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
            // Corregir clases en objetos de datos
            svg = svg.replace("<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\">",
                    "<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\" class=\"itemAware\">");
            svg = svg.replace(
                    "<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\">",
                    "<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\" class=\"itemAware\">");
            String vbox = "";
            try {
                vbox = svg.substring(svg.indexOf("viewBox="), svg.indexOf("height="));
                svg = svg.replace(svg.substring(svg.indexOf("viewBox="), svg.indexOf("height=")),
                        "viewBox=\"" + viewBox + "\" ");
            } catch (IndexOutOfBoundsException e) {
                vbox = svg.substring(svg.indexOf("viewBox="), svg.indexOf("class=\"modeler\""));
                svg = svg.replace(svg.substring(svg.indexOf("viewBox="), svg.indexOf("class=\"modeler\"")),
                        "viewBox=\"" + viewBox + "\" ");
            }
            response.setContentType("image/svg+xml");
            outs.write(svg.getBytes("ISO-8859-1"));
        } else if ("png".equals(format)) {
            response.setContentType("image/png; charset=ISO-8859-1");
            String vbox = "";
            try {
                vbox = data.substring(data.indexOf("viewBox="), data.indexOf("height="));
                data = data.replace(data.substring(data.indexOf("viewBox="), data.indexOf("height=")),
                        "viewBox=\"" + viewBox + "\" ");
            } catch (IndexOutOfBoundsException e) {
                vbox = data.substring(data.indexOf("viewBox="), data.indexOf("class=\"modeler\""));
                data = data.replace(data.substring(data.indexOf("viewBox="), data.indexOf("class=\"modeler\"")),
                        "viewBox=\"" + viewBox + "\" ");
            }
            InputStream strStream = new ByteArrayInputStream(data.getBytes("ISO-8859-1"));
            TranscoderInput ti = new TranscoderInput(strStream/* svgFile.toURI().toString() */);
            TranscoderOutput to = new TranscoderOutput(outs);

            PNGTranscoder t = new PNGTranscoder();
            t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(values[2]) + 2048);
            t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(values[3]) + 1292);
            t.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, Boolean.TRUE);
            try {
                t.transcode(ti, to);
            } catch (TranscoderException ex) {
                LOG.error("Ocurrió un problema al generar la imagen", ex);
            }
        }
    }

    public void doResponse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, String[]> params = request.getParameterMap();
        Iterator<String> keys = params.keySet().iterator();

        out.print("{");
        while (keys.hasNext()) {
            String key = keys.next();
            String val = request.getParameter(key);
            out.print("\"" + key + "\":\"" + val + "\"");
            if (keys.hasNext())
                out.print(",");
        }
        out.print("}");
    }

    /**
     * Modo para hacer la exportación del modelo del proceso a distintos
     * formatos.
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doExport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        OutputStream outs = response.getOutputStream();
        String format = request.getParameter("output_format");
        String data = request.getParameter("data");
        String viewBox = request.getParameter("viewBox");

        Process p = (Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(request.getParameter("suri"));
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

            response.setContentType("image/svg+xml");
            outs.write(svg.getBytes());
        } else if ("swp".equalsIgnoreCase(format)) {
            response.setContentType("application/json");
            String pJson = "";
            if (p != null) {
                pJson = p.serialize("JSON");
                if (null == pJson || pJson.isEmpty()) {
                    pJson = ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgJSONPError"));
                }
            } else {
                if (data != null && data.length() > 0) {
                    pJson = data;
                }
            }
            outs.write(pJson.getBytes("UTF-8"));
        } else if ("png".equals(format)) {
            response.setContentType("image/png; charset=ISO-8859-1");
            String[] values = viewBox != null ? viewBox.split("\\ ") : "0 0 3800 2020".split("\\ ");
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
                LOG.error("Ocurrió un problema al generar la imagen", ex);
            }
        }
        outs.flush();
        outs.close();
    }

    /**
     * Obtiene la lista de items para la tabla de navegación. Incluye objetos
     * {@code Process} y {@code ProcessGroup} a partir del nivel indicado.
     *
     * @param root {@code ProcessGroup} que servirá como raíz del listado.
     * @return Lista de elementos a mostrar en la tabla de navegación.
     */
    public List<Descriptiveable> getItems(ProcessGroup root) {
        List<Descriptiveable> ret = new ArrayList<>();
        Iterator<ProcessGroup> allGroups;

        // Add groups to item list
        if (null == root) {
            allGroups = ProcessGroup.ClassMgr.listProcessGroups(getResourceBase().getWebSite());
        } else {
            allGroups = root.listProcessGroups();
        }

        while (allGroups.hasNext()) {
            ProcessGroup group = allGroups.next();
            if (group.isValid() && (null != root || null == group.getParentGroup())) {
                ret.add(group);
            }
        }

        // Add processes to item list if group is not null
        if (null != root) {
            Iterator<Process> processes = root.listProcesses();
            while (processes.hasNext()) {
                Process process = processes.next();
                ret.add(process);
            }
        }

        return ret;
    }

    /**
     * Obtiene la cadena de error en base a los mensajes definidos.
     * @param id ID del error
     * @return Cadena de texto con el error especificado
     */
    public String getError(int id) {
        String ret = "ERR:";
        if (id == 0) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_loginfail") + "...";
        } else if (id == 1) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_nouser") + "...";
        } else if (id == 2) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noservice") + "...";
        } else if (id == 3) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_serviceprocessfail") + "...";
        } else if (id == 4) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_parametersprocessfail") + "...";
        } else if (id == 5) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopicmap") + "...";
        } else if (id == 6) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopic") + "...";
        } else if (id == 7) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_usernopermiss") + "...";
        } else if (id == 8) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicAlreadyexist") + "...";
        } else if (id == 9) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_byImplement") + "...";
        } else if (id == 10) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicMapAlreadyExist") + "...";
        } else if (id == 11) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_FileNotFound") + "...";
        } else if (id == 12) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noversions") + "...";
        } else if (id == 13) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_xmlinconsistencyversion") + "...";
        } else if (id == 14) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noResourcesinMemory") + "...";
        } else if (id == 15) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noTemplatesinMemory") + "...";
        } else if (id == 16) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_TemplatenotRemovedfromFileSystem") + "...";
        } else if (id == 17) {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_adminUsernotCreated") + "...";
        } else {
            ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_errornotfound") + "...";
        }
        return ret;
    }

    /**
     * Crea los elementos del proceso. Realiza la verificación del JSON del
     * proceso y crea los elementos especificados.
     *
     * @param process Proceso a reconstruir.
     * @param request
     * @param response
     * @param paramsRequest
     * @param hmjson Mapa de objetos recuperados del JSON.
     * @return true si la creación de los elementos tuvo éxito.
     */
    public boolean createProcessElements(org.semanticwb.process.model.Process process, HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest, HashMap<String, JSONObject> hmjson) {
        //Revisando si existen problemas
        boolean ret = false;
        if (reviewJSON(process, request, response, paramsRequest, hmjson, false)) {
            // no se encontraron problemas en el proceso, actualiza
            ret = reviewJSON(process, request, response, paramsRequest, hmjson, true);
        }
        return ret;
    }

    /**
     * Utilizado para identificar qué elementos del proceso que se han eliminado.
     * Aquí también se eliminan todos los elementos de conexión existentes. Este
     * sólo se debe utilizar cuando se actualiza el modelo del proceso.
     *
     * @param process, Modelo a cargar los elementos del proceso
     * @return Vector, con los uris de los elementos existentes.
     */
    public HashMap<String, String> loadProcessElements(org.semanticwb.process.model.Process process) {
        HashMap<String, String> hmori = new HashMap<>();

        try {
            Iterator<GraphicalElement> itFo = process.listContaineds();
            while (itFo.hasNext()) {
                GraphicalElement obj = itFo.next();
                hmori.put(obj.getURI(), obj.getURI());

                // Se eliminan las conexiones entre GraphicalElements
                Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
                while (it.hasNext()) {
                    ConnectionObject connectionObject = it.next();
                    hmori.put(connectionObject.getURI(), connectionObject.getURI());
                }
                if (obj instanceof Containerable) {
                    loadSubProcessElements((Containerable) obj, hmori);
                }
            }

        } catch (Exception e) {
            LOG.error("Error al general el JSON del Modelo.....loadProcessElements(" + process.getTitle() + ", uri:" + process.getURI() + ")", e);
        }
        return hmori;
    }

    /**
     * Carga los elementos de un subproceso a partir del listado de elementos.
     * @param subprocess Subproceso a construir.
     * @param hmori Lista de elementos recuperados del modelo.
     */
    public void loadSubProcessElements(org.semanticwb.process.model.Containerable subprocess, HashMap hmori) {
        try {
            Iterator<GraphicalElement> itFo = subprocess.listContaineds();
            while (itFo.hasNext()) {
                GraphicalElement obj = itFo.next();
                hmori.put(obj.getURI(), obj.getURI());

                // Se eliminan las conexiones entre GraphicalElements
                Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
                while (it.hasNext()) {
                    ConnectionObject connectionObject = it.next();
                    hmori.put(connectionObject.getURI(), connectionObject.getURI());
                }
                if (obj instanceof Containerable) {
                    loadSubProcessElements((Containerable) obj, hmori);
                }
            }
        } catch (Exception e) {
            LOG.error("Error al general el JSON del Modelo.....loadSubProcessElements(" + subprocess.getId() + ", uri:" + subprocess.getURI() + ")", e);
        }
    }

    /**
     * Verifica que el JSON del moelo esté bien formado.
     *
     * @param process Proceso.
     * @param request
     * @param response
     * @param paramsRequest
     * @param hmjson
     * @param bupdate
     * @return
     */
    public boolean reviewJSON(org.semanticwb.process.model.Process process, HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest, HashMap<String, JSONObject> hmjson, boolean bupdate) {
        boolean ret = true;
        HashMap<String, String> hmori = loadProcessElements(process);
        HashMap<String, String> hmnew = new HashMap<>();
        ProcessSite procsite = process.getProcessSite();
        GenericObject go = null;
        String uri = null, sclass = null, title = null, description = null, container = null, parent = null, start = null, end = null;
        int x, y, w, h, labelSize, index;
        Boolean isMultiInstance, isLoop, isForCompensation, isCollection, isInterrupting;

        SemanticClass semclass = null;
        SemanticModel model = procsite.getSemanticObject().getModel();
        GraphicalElement ge = null;
        ConnectionObject co = null;
        try {
            // Parte para crear y/o actualizar elementos
            Iterator<String> it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                JSONObject json = (JSONObject) hmjson.get(key);
                uri = json.getString(SWBProcess.JSONProperties.PROP_URI);
                sclass = json.getString(SWBProcess.JSONProperties.PROP_CLASS);
                semclass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(PROCESS_PREFIX + "#" + sclass);
                if (semclass == null) {
                    continue;
                }
                try {
                    // primero se crean los elementos graficos del modelo
                    if (semclass.isSubClass(GraphicalElement.swp_GraphicalElement)) {
                        title = json.optString(SWBProcess.JSONProperties.PROP_TITLE, "");//TODO: Sanitize title and description from JSON
                        description = json.optString(SWBProcess.JSONProperties.PROP_DESCRIPTION, "");
                        isMultiInstance = json.optBoolean(SWBProcess.JSONProperties.PROP_ISMULTIINSTANCE, false);
                        isLoop = json.optBoolean(SWBProcess.JSONProperties.PROP_ISLOOP, false);
                        isForCompensation = json.optBoolean(SWBProcess.JSONProperties.PROP_ISCOMPENSATION, false);
                        isInterrupting = json.optBoolean(SWBProcess.JSONProperties.PROP_ISINTERRUPTING, false);
                        isCollection = json.optBoolean(SWBProcess.JSONProperties.PROP_ISCOLLECTION, false);
                        index = json.optInt(SWBProcess.JSONProperties.PROP_INDEX, 1000);

                        x = json.getInt(SWBProcess.JSONProperties.PROP_X);
                        y = json.getInt(SWBProcess.JSONProperties.PROP_Y);
                        w = json.getInt(SWBProcess.JSONProperties.PROP_W);
                        h = json.getInt(SWBProcess.JSONProperties.PROP_H);

                        parent = json.optString(SWBProcess.JSONProperties.PROP_PARENT, "");
                        container = json.optString(SWBProcess.JSONProperties.PROP_CONTAINER, "");
                        labelSize = json.optInt(SWBProcess.JSONProperties.PROP_LABELSIZE, 10);

                        // revisando si el elemento existe
                        if (hmori.get(uri) != null) {
                            go = ont.getGenericObject(uri);

                            if (go instanceof GraphicalElement) {
                                // actualizando datos en elemento existente
                                ge = (GraphicalElement) go;
                                if (!ge.getTitle().equals(title) && bupdate) {
                                    ge.setTitle(title);
                                }
                                if ((null != description && ge.getDescription() != null && !ge.getDescription().equals(description)) || (null != description && ge.getDescription() == null) && bupdate) {
                                    ge.setDescription(description);
                                }

                                if (ge.getX() != x && bupdate) {
                                    ge.setX(x);
                                }
                                if (ge.getY() != y && bupdate) {
                                    ge.setY(y);
                                }
                                if (ge.getWidth() != w && bupdate) {
                                    ge.setWidth(w);
                                }
                                if (ge.getHeight() != h && bupdate) {
                                    ge.setHeight(h);
                                }
                                if (ge.getLabelSize() != labelSize && bupdate) {
                                    ge.setLabelSize(labelSize);
                                }

                                // si es un Sortable se revisa si tiene index
                                if (ge instanceof Sortable) {
                                    Sortable sorble = (Sortable) go;
                                    if (bupdate) {
                                        sorble.setIndex(index);
                                    }
                                }

                                if (ge instanceof CatchEvent) {
                                    CatchEvent ice = (CatchEvent) ge;
                                    if (bupdate) {
                                        ice.setInterruptor(isInterrupting);
                                    }
                                }

                                if (go instanceof ActivityConfable) {  //Task
                                    ActivityConfable tsk = (ActivityConfable) go;

                                    if (isForCompensation && bupdate) {
                                        tsk.setForCompensation(isForCompensation);
                                    }

                                    if (isMultiInstance) {
                                        // si existe no se hace nada se deja el MultiInstanceLoopCharacteristics
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (loopchar == null) // si no existe lo crea
                                        {
                                            // si no existe se crea uno nuevo y se asigna al task
                                            if (bupdate) {
                                                loopchar = MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(procsite);
                                                tsk.setLoopCharacteristics(loopchar);
                                            }
                                        } else if (!(loopchar instanceof MultiInstanceLoopCharacteristics)) {
                                            if (bupdate) {
                                                loopchar.getSemanticObject().remove();
                                            }
                                        }
                                        // si no existe se crea uno nuevo y se asigna al task
                                        if (bupdate) {
                                            loopchar = MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(procsite);
                                            tsk.setLoopCharacteristics(loopchar);
                                        }
                                    } else {
                                        // si existe y cambio y ya no es MultiInstance se elimina el MultiInstanceLoopCharacteristics asociado
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (null != loopchar && loopchar instanceof MultiInstanceLoopCharacteristics && bupdate) {
                                            loopchar.getSemanticObject().remove();
                                        }
                                    }

                                    if (isLoop) {
                                        // si existe no se hace nada se deja el LoopCharacteristics
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (loopchar == null) // si no existe lo crea
                                        {
                                            // si no existe se crea uno nuevo y se asigna al task
                                            if (bupdate) {
                                                loopchar = StandarLoopCharacteristics.ClassMgr.createStandarLoopCharacteristics(procsite);
                                                tsk.setLoopCharacteristics(loopchar);
                                            }
                                        } else if (!(loopchar instanceof StandarLoopCharacteristics)) {
                                            if (bupdate) {
                                                loopchar.getSemanticObject().remove();
                                            }
                                            // si no existe se crea uno nuevo y se asigna al task
                                            if (bupdate) {
                                                loopchar = StandarLoopCharacteristics.ClassMgr.createStandarLoopCharacteristics(procsite);
                                                tsk.setLoopCharacteristics(loopchar);
                                            }
                                        }

                                    } else {
                                        // si existe y cambio y ya no es Loop se elimina el StandarLoopCharacteristics asociado
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (null != loopchar && loopchar instanceof StandarLoopCharacteristics && bupdate) {
                                            loopchar.getSemanticObject().remove();
                                        }
                                    }
                                }

                                // si es un Collectionable se revisa si es colección
                                if (ge instanceof Collectionable) {
                                    Collectionable colble = (Collectionable) go;
                                    if (isCollection && bupdate) {
                                        colble.setCollection(isCollection);
                                    }
                                }
                                // se agrega en este hm para la parte de la secuencia del proceso
                                if (bupdate) {
                                    hmnew.put(uri, go.getURI());
                                }

                            }

                            if (go instanceof Lane && json.has("lindex")) {
                                ((Lane) go).setLindex(json.getInt("lindex"));
                            }
                            // se quita elemento que ha sido actualizado
                            if (bupdate) {
                                hmori.remove(uri);
                            }

                        } else {
                            //Se genera el nuevo elemento
                            long id = model.getCounter(semclass);
                            GenericObject gi = null;
                            if (bupdate) {
                                gi = model.createGenericObject(model.getObjectUri(String.valueOf(id), semclass), semclass);

                                ge = (GraphicalElement) gi;
                                ge.setTitle(title);
                                if (null != description) {
                                    ge.setDescription(description);
                                }
                                ge.setX(x);
                                ge.setY(y);
                                ge.setHeight(h);
                                ge.setWidth(w);

                                // si es un Sortable se revisa si tiene index
                                if (ge instanceof Sortable) {
                                    Sortable sorble = (Sortable) gi;
                                    sorble.setIndex(index);
                                }

                                if (ge instanceof IntermediateCatchEvent) {
                                    IntermediateCatchEvent ice = (IntermediateCatchEvent) ge;
                                    ice.setInterruptor(isInterrupting);
                                }

                                if (ge instanceof ActivityConfable) {  //Task
                                    ActivityConfable tsk = (ActivityConfable) gi;

                                    if (isForCompensation) {
                                        tsk.setForCompensation(isForCompensation);
                                    }

                                    if (isMultiInstance) {
                                        // si existe no se hace nada se deja el MultiInstanceLoopCharacteristics
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (loopchar == null) // si no existe lo crea
                                        {
                                            // si no existe se crea uno nuevo y se asigna al task
                                            loopchar = MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(procsite);
                                            tsk.setLoopCharacteristics(loopchar);
                                        } else if (!(loopchar instanceof MultiInstanceLoopCharacteristics)) {
                                            loopchar.getSemanticObject().remove();
                                        }
                                        // si no existe se crea uno nuevo y se asigna al task
                                        loopchar = MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(procsite);
                                        tsk.setLoopCharacteristics(loopchar);
                                    } else {
                                        // si existe y cambio y ya no es MultiInstance se elimina el MultiInstanceLoopCharacteristics asociado
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (null != loopchar && loopchar instanceof MultiInstanceLoopCharacteristics) {
                                            loopchar.getSemanticObject().remove();
                                        }
                                    }

                                    if (isLoop) {
                                        // si existe no se hace nada se deja el LoopCharacteristics
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (loopchar == null) // si no existe lo crea
                                        {
                                            // si no existe se crea uno nuevo y se asigna al task
                                            loopchar = StandarLoopCharacteristics.ClassMgr.createStandarLoopCharacteristics(procsite);
                                            tsk.setLoopCharacteristics(loopchar);
                                        } else if (!(loopchar instanceof StandarLoopCharacteristics)) {
                                            loopchar.getSemanticObject().remove();
                                            // si no existe se crea uno nuevo y se asigna al task
                                            loopchar = StandarLoopCharacteristics.ClassMgr.createStandarLoopCharacteristics(procsite);
                                            tsk.setLoopCharacteristics(loopchar);
                                        }

                                    } else {
                                        // si existe y cambio y ya no es Loop se elimina el StandarLoopCharacteristics asociado
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (null != loopchar && loopchar instanceof StandarLoopCharacteristics) {
                                            loopchar.getSemanticObject().remove();
                                        }
                                    }
                                }

                                // si es un Collectionable se revisa si es colección
                                if (ge instanceof Collectionable) {
                                    Collectionable colble = (Collectionable) gi;
                                    if (isCollection) {
                                        colble.setCollection(isCollection);
                                    }
                                }

                            }

                            // se agrega nuevo elemento en el hmnew
                            if (bupdate) {
                                hmnew.put(uri, gi.getURI());
                            }

                            if (ge instanceof Lane && json.has("lindex")) {
                                ((Lane) ge).setLindex(json.getInt("lindex"));
                            }

                            if (semclass.equals(UserTask.swp_UserTask)) {

                                if (procsite.getResourceType("ProcessForm") == null && bupdate) {
                                    ResourceType resType = procsite.createResourceType("ProcessForm");
                                    resType.setTitle("ProcessForm");
                                    resType.setTitle("ProcessForm", "en");
                                    resType.setDescription("ProcessForm");
                                    resType.setDescription("ProcessForm", "es");
                                    resType.setResourceClassName("org.semanticwb.process.resources.ProcessForm");
                                    resType.setResourceBundle("org.semanticwb.process.resources.ProcessForm");
                                    resType.setResourceMode(ResourceType.MODE_SYSTEM);
                                }

                                if (procsite.getResourceType("ProcessForm") != null && bupdate) {
                                    Resource res = procsite.createResource();
                                    res.setResourceType(procsite.getResourceType("ProcessForm"));
                                    res.setTitle("ProcessFormResource");
                                    res.setActive(Boolean.TRUE);
                                    ((UserTask) gi).addResource(res);
                                }
                            }
                        } // termina else
                    } // termina if graphicalElement
                } catch (Exception e) {
                    LOG.error("Error al procesar el JSON Object para la creacion y/o actualizar GraphicalElement... ", e);
                    ret = false;
                    break;
                }
            } //termina while

            // Parte para relacionar elementos container y parents
            it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                JSONObject json = (JSONObject) hmjson.get(key);
                uri = json.getString(SWBProcess.JSONProperties.PROP_URI);
                sclass = json.getString(SWBProcess.JSONProperties.PROP_CLASS);

                semclass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(PROCESS_PREFIX + "#" + sclass);
                if (semclass == null) {
                    continue;
                }

                if (semclass.isSubClass(GraphicalElement.swp_GraphicalElement)) {
                    parent = json.optString(SWBProcess.JSONProperties.PROP_PARENT, "");
                    container = json.optString(SWBProcess.JSONProperties.PROP_CONTAINER, "");
                    go = ont.getGenericObject(hmnew.get(uri));
                    ge = null;
                    if (go instanceof GraphicalElement) {
                        ge = (GraphicalElement) go;
                    }
                    if (ge != null && bupdate) {
                        if (container != null && container.trim().length() == 0) {
                            ge.setContainer(process);
                        } else {
                            GenericObject subproc = ont.getGenericObject(hmnew.get(container));
                            if (subproc != null)
                            {
                                ge.setContainer((Containerable) subproc);
                            }
                        }
                        if (parent != null && parent.trim().length() > 0) {
                            GenericObject goGe = ont.getGenericObject(hmnew.get(parent));
                            if (goGe != null && goGe instanceof GraphicalElement) {
                                ge.setParent((GraphicalElement) goGe);
                            }
                        } else if (ge.getParent() != null) {
                            ge.removeParent();
                        }
                    }
                }
            }

            GenericObject gostart = null;
            GenericObject goend = null;
            String sconnpoints = null;

            // Parte para generar el flujo del proceso
            it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                JSONObject json = (JSONObject) hmjson.get(key);
                uri = json.getString(SWBProcess.JSONProperties.PROP_URI);

                sclass = json.getString(SWBProcess.JSONProperties.PROP_CLASS);

                semclass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(PROCESS_PREFIX + "#" + sclass);
                if (semclass == null) {
                    continue;
                }

                if (semclass.isSubClass(ConnectionObject.swp_ConnectionObject)) {
                    start = json.getString(SWBProcess.JSONProperties.PROP_START);
                    end = json.getString(SWBProcess.JSONProperties.PROP_END);

                    title = json.optString(SWBProcess.JSONProperties.PROP_TITLE, "");
                    try {
                        description = json.optString(SWBProcess.JSONProperties.PROP_DESCRIPTION, "");
                    } catch (Exception e) {
                        description = "";
                    }

                    try {
                        sconnpoints = json.getString(SWBProcess.JSONProperties.PROP_CONNPOINTS);
                    } catch (Exception e) {
                        sconnpoints = "";
                    }

                    // revisando si existe elemento coneccion
                    if (hmori.get(uri) != null) {
                        go = ont.getGenericObject(hmori.get(uri));
                        co = (ConnectionObject) go;
                        if (!co.getTitle().equals(title) && bupdate) {
                            co.setTitle(title);
                        }

                        if ((null != description && co.getDescription() != null && !co.getDescription().equals(description)) || (null != description && co.getDescription() == null)  && bupdate) {
                            co.setDescription(description);
                        }

                        if ((null != sconnpoints && co.getConnectionPoints() != null && !co.getConnectionPoints().equals(sconnpoints)) || (null != sconnpoints && co.getConnectionPoints() == null)  && bupdate) {
                            co.setConnectionPoints(sconnpoints);
                        }

                        if (!co.getSource().getURI().equals(start) && hmnew.get(start) != null) {
                            gostart = ont.getGenericObject(hmnew.get(start));
                            if (bupdate) {
                                co.setSource((GraphicalElement) gostart);
                            }
                        }
                        if (!co.getTarget().getURI().equals(end) && hmnew.get(end) != null) {
                            goend = ont.getGenericObject(hmnew.get(end));
                            if (bupdate) {
                                co.setTarget((GraphicalElement) goend);
                            }
                        }
                        if (bupdate) {
                            hmori.remove(uri);
                        }
                    } else {
                        if (hmnew.get(start) != null && hmnew.get(end) != null) {
                            long id = model.getCounter(semclass);
                            go = model.createGenericObject(model.getObjectUri(String.valueOf(id), semclass), semclass);
                            co = (ConnectionObject) go;
                            if (bupdate) {
                                co.setTitle(title);
                            }
                            if (null != description && bupdate) {
                                co.setDescription(description);
                            }
                            if (null != sconnpoints && bupdate) {
                                co.setConnectionPoints(sconnpoints);
                            }
                            gostart = ont.getGenericObject(hmnew.get(start));
                            goend = ont.getGenericObject(hmnew.get(end));
                            if (bupdate) {
                                co.setSource((GraphicalElement) gostart);
                            }
                            if (bupdate) {
                                co.setTarget((GraphicalElement) goend);
                            }
                        }
                    }
                }
            }

            // Eliminando elementos que fueron borrados en el applet Modeler
            // que se tenian en el original pero ya no se necesitan
            it = hmori.keySet().iterator();
            SemanticObject so = null;
            while (it.hasNext()) {
                String key = it.next();
                try {
                    so = ont.getSemanticObject(hmori.get(key));
                    if (so != null && bupdate) {
                        so.remove();
                    }
                } catch (Exception e) {
                    LOG.error("Error al tratar de eliminar elemento del proceso...", e);
                    ret = false;
                    break;
                }
            }

        } catch (Exception e) {
            LOG.error("Error al actualizar el modelo del proceso. Modeler.createProcessElements()", e);
            ret = false;
        }
        return ret;
    }

    /**
     * Procesa los archivos enviados para la carga de modelo.
     *
     * @param request
     * @return Cadena con el JSON contenido en el archivo.
     */
    private String processFile(HttpServletRequest request) {
        String data = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            ServletFileUpload upload = new ServletFileUpload();
            try {
                FileItemIterator it = upload.getItemIterator(request);
                while (it.hasNext()) {
                    FileItemStream item = it.next();
                    if (!item.isFormField()) {//Es un archivo
                        if (item.getName().toLowerCase().endsWith(".swp")) { //Es un archivo swp
                            InputStream stream = item.openStream();
                            java.util.Scanner scanner = new Scanner(stream, "UTF-8").useDelimiter("\\A");
                            if (scanner.hasNext()) {
                                data = scanner.next();
                            }
                        } else {
                            if (item.getName().toLowerCase().endsWith(".xpdl")) {//Es un archivo XPDL
                                InputStream stream = item.openStream();
                                XPDLParser parser = new XPDLParser();
                                JSONObject ret = parser.parse(stream);
                                if (ret != null) {
                                    data = ret.toString(2);
                                }
                            }
                        }
                    }
                }
            } catch (FileUploadException | IOException ex) {
                data = null;
                LOG.error("Error al procesar el archivo", ex);
            } catch (JSONException ex) {
                data = null;
                LOG.error("Error al generar el json del modelo", ex);
            }
        }
        return data;
    }
}
