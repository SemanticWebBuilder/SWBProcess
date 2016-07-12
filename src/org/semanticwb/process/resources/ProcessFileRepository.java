/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
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
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Role;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.ItemAwareStatus;
import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.process.model.RepositoryElement;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.RepositoryURL;

/**
 *
 * @author juan.fernandez
 */
public class ProcessFileRepository extends GenericResource {

    private final Logger log = SWBUtils.getLogger(ProcessFileRepository.class);
    public static final String MODE_GETFILE = "getFile";
    public static final String MODE_PROPS = "props";
    public static final String MODE_ADDFILE = "addFile";
    public static final String MODE_ADDFOLDER = "addFolder";
    public static final String MODE_EDITFOLDER = "editFolder";
    public static final String MODE_HISTORY = "versionHistory";
    public static final String MODE_RESPONSE = "m_response";
    public static final String ACT_NEWFILE = "newfile";
    public static final String ACT_NEWFOLDER = "newfolder";
    public static final String ACT_EDITFOLDER = "updateFolder";
    public static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    public static final String LVL_VIEW = "prop_view";
    public static final String LVL_MODIFY = "prop_modify";
    public static final String LVL_ADMIN = "prop_admin";
    public static final String VALID_FILES = "prop_valid_files";
    public static final String ATT_LEVELUSER = "levelUser";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (MODE_GETFILE.equals(mode)) {
            doGetFile(request, response, paramRequest);
        } else if (MODE_PROPS.equals(mode)) {
            doFileProps(request, response, paramRequest);
        } else if (MODE_HISTORY.equals(mode)) {
            doHistory(request, response, paramRequest);
        } else if (MODE_ADDFILE.equals(mode)) {
            doAddFile(request, response, paramRequest);
        } else if (MODE_ADDFOLDER.equals(mode)) {
            doAddFolder(request, response, paramRequest);
        } else if (MODE_EDITFOLDER.equals(mode)) {
            doEditFolder(request, response, paramRequest);
        } else if (MODE_RESPONSE.equals(mode)) {
            doResponse(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doFileProps(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/repository/repositoryFileProps.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        
        response.setContentType("text/html; charset=UTF-8");
        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("Error including props.jsp", ex);
        }
    }
    
    public void doAddFile(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String jsp = "/swbadmin/jsp/process/repository/repositoryAddFile.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            request.setAttribute(VALID_FILES, getResourceBase().getAttribute(VALID_FILES, ""));
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("Error including add.jsp", ex);
        }
    }
    
    public void doAddFolder(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String jsp = "/swbadmin/jsp/process/repository/repositoryAddFolder.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("Error including addFolder.jsp", ex);
        }
    }
    
   public void doEditFolder(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String jsp = "/swbadmin/jsp/process/repository/repositoryEditFolder.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("Error including props.jsp", ex);
        }
    }
    
    public void doHistory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/repository/repositoryFileVersions.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        
        String fid = request.getParameter("fid");
        String type = request.getParameter("type");
        RepositoryElement re = null;
        
        if (fid != null && fid.length() > 0) {
            if (type != null) {
                if ("url".equals(type)) {
                    re = RepositoryURL.ClassMgr.getRepositoryURL(fid, paramRequest.getWebPage().getWebSite());
                } else if ("file".equals(type)) {
                    re = RepositoryFile.ClassMgr.getRepositoryFile(fid, paramRequest.getWebPage().getWebSite());
                }
            }
        }
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("luser", getLevelUser(paramRequest.getUser()));
            request.setAttribute("versionList", getFileVersions(re));
            request.setAttribute("element", re);
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("Error including versions.jsp", ex);
        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //response.setContentType("text/html; charset=ISO-8859-1");
        //response.setHeader("Cache-Control", "no-cache");
        //response.setHeader("Pragma", "no-cache");
        String jsp = "/swbadmin/jsp/process/repository/repositoryView.jsp";
        String folderPath = null;
        if (paramRequest.getWebPage() instanceof RepositoryDirectory) {
            folderPath = getFolderPath((RepositoryDirectory)paramRequest.getWebPage());
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        //System.out.println("Nivel de permisos: "+getLevelUser(paramRequest.getUser()));
        try {
            int luser = getLevelUser(paramRequest.getUser());
            request.setAttribute("paramRequest", paramRequest);
            if (luser > 0) {
                request.setAttribute("files", listFiles(request, paramRequest));
            }
            request.setAttribute("luser", luser);
            request.setAttribute("path", folderPath);
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("Error including view.jsp", ex);
        }
    }
    
    public void doResponse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map params = request.getParameterMap();
        Iterator<String> keys = params.keySet().iterator();
        
        out.print("{");
        while(keys.hasNext()) {
            String key = keys.next();
            String val = request.getParameter(key);
            out.print("\""+key+"\":\""+val+"\"");
            if (keys.hasNext()) out.print(",");
        }
        out.print("}");
    }

    public void doGetFile(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        String fid = request.getParameter("fid");
        String verNumber = request.getParameter("verNum");
        //int intVer = 1;
        //if (verNumber != null) {
        //    intVer = Integer.parseInt(verNumber);
       // }
        RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, paramRequest.getWebPage().getWebSite());
        //Get last version by default
        VersionInfo ver = repoFile.getLastVersion();
        
        //Get requested version number
        if (null != verNumber && !verNumber.isEmpty()) {
            int intVer = Integer.parseInt(verNumber);
           
            VersionInfo vl = ver;
            if (null != vl) {
                ver = vl;
                while (ver.getPreviousVersion() != null) {
                    if (ver.getVersionNumber() == intVer) {
                        break;
                    }
                    ver = ver.getPreviousVersion();
                }
            }
        }

        if (ver != null) {
            try {
                response.setContentType(DEFAULT_MIME_TYPE);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + ver.getVersionFile() + "\"");

                OutputStream out = response.getOutputStream();
                SWBUtils.IO.copyStream(new FileInputStream(SWBPortal.getWorkPath() + repoFile.getWorkPath() + "/" + ver.getVersionNumber() + "/" + ver.getVersionFile()), out);
            } catch (Exception e) {
                log.error("Error al obtener el archivo del Repositorio de documentos.", e);
            }
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        String id = getResourceBase().getId();

        PrintWriter out = response.getWriter();
        String accion = paramRequest.getAction();
        if (accion == null) {
            accion = "";
        }

        WebSite wsite = getResourceBase().getWebSite(); //wpage.getWebSite();

        out.println("<div class=\"swbform\">");

        if (accion.equals("edit")) {

            SWBResourceURL urlA = paramRequest.getActionUrl();
            urlA.setAction("admin_update");

            out.println("<form id=\"" + id + "_myform_repfile\"  name=\"" + id + "_myform_repfile\" action=\"" + urlA.toString() + "\" method=\"post\" >"); //onsubmit=\"submitForm('" + id + "_myform_repfile');return false;\"

            out.println("<fieldset>");
            out.println("<legend>");
            out.println(paramRequest.getLocaleString("msgFileRepositoryRes"));
            out.println("</legend>");


            out.println("<table width=\"100%\" border=\"0\" >");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            String validFiles = getResourceBase().getAttribute(VALID_FILES, "");
            String ipp = getResourceBase().getAttribute("itemsPerPage", "");
            String ssf = getResourceBase().getAttribute("hideSubFolders", "").equals("true")?"checked":"";
            String aph = getResourceBase().getAttribute("applyToChilds", "").equals("true")?"checked":"";

            out.println("<tr><td colspan=\"2\"><B>" + paramRequest.getLocaleString("msgRolesDefinitionLevel") + "</B></td></tr>");
            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgView") + ":</td>");
            out.println("<td><select name=\"ver\">" + getSelectOptions("ver", wsite, paramRequest) + "</select></td></tr>");
            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgModify") + ":</td>");
            out.println("<td><select name=\"modificar\">" + getSelectOptions("modificar", wsite, paramRequest) + "</select></td></tr>");
            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("msgAdministrate") + ":</td>");
            out.println("<td><select name=\"administrar\">" + getSelectOptions("administrar", wsite, paramRequest) + "</select></td></tr>");

            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("msgValidFiles") + ":</td>");
            out.println("<td><input type=\"text\" name=\"validfiles\"  value=\"" + validFiles + "\"></td></tr>");
            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("itemsPerPage") + ":</td>");
            out.println("<td><input type=\"text\" name=\"itemsPerPage\"  value=\"" + ipp + "\"></td></tr>");
            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("hideSubFolders") + ":</td>");
            out.println("<td><input type=\"checkbox\" name=\"hideSubFolders\"" + ssf + "></td></tr>");
            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("applyToChilds") + ":</td>");
            out.println("<td><input type=\"checkbox\" name=\"applyToChilds\"" + aph + "></td></tr>");

            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" id=\"" + id + "btn\" name=\"btn\" >" + paramRequest.getLocaleString("msgBTNAccept"));
            out.println("</button>");
            out.println("</fieldset>");

            out.println("<fieldset>");
            out.println("<br> * " + paramRequest.getLocaleString("msgNote") + ": " + paramRequest.getLocaleString("msgRolesDependent"));

            out.println("</fieldset>");
            out.println("</form>");
        }

        out.println("</div>");
    }

    /**
     * Construye las opciones de selección para los niveles de administración del componente.
     * @param type Tipo de permiso cuyos valores pueden ser: "ver", "modificar", "administrar".
     * @param wsite Sitio Web del componente a administrar.
     * @param paramRequest SWBParamRequest.
     * @return Cadena con los valores del selector de niveles de administración del componente.
     */
    public String getSelectOptions(String type, WebSite wsite, SWBParamRequest paramRequest) {
        String strTemp = "";
        try {

            Resource base = getResourceBase();
            User user = paramRequest.getUser();

            String selectedItem = "";
            if (type.equals("ver")) {
                selectedItem = base.getAttribute(LVL_VIEW, "0");
            } else if (type.equals("modificar")) {
                selectedItem = base.getAttribute(LVL_MODIFY, "0");

            } else if (type.equals("administrar")) {
                selectedItem = base.getAttribute(LVL_ADMIN, "0");
            }

            strTemp = "<option value=\"-1\">" + paramRequest.getLocaleString("msgNoRolesAvailable") + "</option>";

            Iterator<Role> iRoles = wsite.getUserRepository().listRoles(); //DBRole.getInstance().getRoles(topicmap.getDbdata().getRepository());
            StringBuilder strRules = new StringBuilder("");
            strRules.append("\n<option value=\"0\">").append(paramRequest.getLocaleString("msgSelNone")).append("</option>");
            strRules.append("\n<optgroup label=\"Roles\">");
            while (iRoles.hasNext()) {
                Role oRole = iRoles.next();
                strRules.append("\n<option value=\"").append(oRole.getURI()).append("\" ").append(selectedItem.equals(oRole.getURI()) ? "selected" : "").append(">").append(oRole.getDisplayTitle(user.getLanguage())).append("</option>");
            }
            strRules.append("\n</optgroup>");

            strRules.append("\n<optgroup label=\"User Groups\">");
            Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
            while (iugroups.hasNext()) {
                UserGroup oUG = iugroups.next();
                strRules.append("\n<option value=\"").append(oUG.getURI()).append("\" ").append(selectedItem.equals(oUG.getURI()) ? "selected" : "").append(">").append(oUG.getDisplayTitle(user.getLanguage())).append("</option>");
            }
            strRules.append("\n</optgroup>");
            if (strRules.toString().length() > 0) {
                strTemp = strRules.toString();
            }

        } catch (Exception e) {
        }
        return strTemp;
    }

    /**
     * Obtiene el nivel de permisos del usuario.
     * @param user Usuario.
     * @return Entero que representa el nivel de permisos del usuario.
     */
    public int getLevelUser(User user) {
        if (null == user) return 0;
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        Resource base = getResourceBase();

        GenericObject gobj = null;
        UserGroup ugrp = null;
        Role urole = null;
        
        gobj = ont.getGenericObject(base.getAttribute(LVL_ADMIN));
        if (null != gobj && gobj instanceof UserGroup) ugrp = (UserGroup)gobj;
        if (null != gobj && gobj instanceof Role) urole = (Role)gobj;
        
        //Si no hay rol o grupo para administrar, todos pueden administrar (nivel 3)
        if (null == ugrp && null == urole) return 3;
        //Revisar permisos del usuario
        if (user.hasUserGroup(ugrp) || user.hasRole(urole)) return 3;
        
        //El usuario no puede administrar, revisar si puede modificar
        ugrp = null; urole = null;
        gobj = ont.getGenericObject(base.getAttribute(LVL_MODIFY));
        if (null != gobj && gobj instanceof UserGroup) ugrp = (UserGroup)gobj;
        if (null != gobj && gobj instanceof Role) urole = (Role)gobj;
        
        //Si no hay rol o grupo para modificar, todos pueden modificar (nivel 2)
        if (null == ugrp && null == urole) return 2;
        //Revisar permisos del usuario
        if (user.hasUserGroup(ugrp) || user.hasRole(urole)) return 2;
        
        //El usuario no puede modificar, revisar si puede ver
        ugrp = null; urole = null;
        gobj = ont.getGenericObject(base.getAttribute(LVL_VIEW));
        if (null != gobj && gobj instanceof UserGroup) ugrp = (UserGroup)gobj;
        if (null != gobj && gobj instanceof Role) urole = (Role)gobj;
        
        //Si no hay rol o grupo para ver, todos pueden ver (nivel 1)
        if (null == ugrp && null == urole) return 1;
        //Revisar permisos del usuario
        if (user.hasUserGroup(ugrp) || user.hasRole(urole)) return 1;
        
        return 0; //Sin permisos
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        WebSite site = response.getWebPage().getWebSite();
        
        if (action == null) {
            action = "";
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        if (ACT_NEWFILE.equals(action)) {
            boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
            HashMap<String, Object> params = new HashMap<String, Object>();
            InputStream file = null;
            
            if (isMultiPart) {

                try {
                    List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                    for (FileItem item : items) {
                        if (item.isFormField()) {
                            String name = item.getFieldName();
                            String val = item.getString();
                            params.put(name, val);
                        } else {
                            file = item.getInputStream();
                            params.put("ffile",item.getName());
                        }
                    }
                } catch (FileUploadException fue) {
                    log.error("Error processing file upload", fue);
                }
            } else {
                params.put("ffile", request.getParameter("ffile"));
                params.put("ftitle", request.getParameter("ftitle"));
                params.put("fdescription", request.getParameter("fdescription"));
                params.put("fcomment", request.getParameter("fcomment"));
                params.put("hftype", request.getParameter("hftype"));
                params.put("extfile", request.getParameter("extfile"));
                params.put("newVersion", request.getParameter("newVersion"));
                params.put("fid", request.getParameter("fid"));
                params.put("itemAwStatus", request.getParameter("itemAwStatus"));
            }
            
            String fid = (String)params.get("fid");
            String extfile = (String)params.get("extfile");
            String newVersion = (String) params.get("newVersion");
            String repoEleStat = (String) params.get("itemAwStatus");
            String hftype = (String) params.get("hftype");
            String ftitle = (String) params.get("ftitle");
            String fdescription = (String) params.get("fdescription");
            String fcomment = (String) params.get("fcomment");
            
            String fname = "";
            if (null != file) fname = (String)params.get("ffile");
            
            GenericObject go = null;
            RepositoryDirectory repoDir = (RepositoryDirectory) response.getWebPage();
            if (hftype != null && hftype.equals("file") && null != file) {

                RepositoryFile repoFile = null;
                boolean incremento = Boolean.FALSE;
                if (fid != null) {
                    go = ont.getGenericObject(fid);
                    repoFile = (RepositoryFile) go; //RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getProcessSite());
                    if (newVersion != null && newVersion.equals("nextInt")) {
                        incremento = Boolean.TRUE;
                    }
                } else {
                    repoFile = RepositoryFile.ClassMgr.createRepositoryFile(repoDir.getProcessSite());
                    repoFile.setRepositoryDirectory(repoDir);
                }
                repoFile.setTitle(ftitle);
                repoFile.setDescription(fdescription);

                User usr = response.getUser();

                repoFile.setOwnerUserGroup(usr.getUserGroup());
                
                repoFile.storeFile(file, fname, fcomment, repoEleStat, incremento);
                response.setRenderParameter("status", "ok");
            } else {
                RepositoryURL repoUrl = null;
                boolean incremento = Boolean.FALSE;
                if (fid != null) {
                    go = ont.getGenericObject(fid);
                    repoUrl = (RepositoryURL) go;
                    if (newVersion != null && newVersion.equals("nextInt")) {
                        incremento = Boolean.TRUE;
                    }
                } else {
                    repoUrl = RepositoryURL.ClassMgr.createRepositoryURL(repoDir.getProcessSite());
                    repoUrl.setRepositoryDirectory(repoDir);
                }
                repoUrl.setTitle(ftitle);
                repoUrl.setDescription(fdescription);

                User usr = response.getUser();

                repoUrl.setOwnerUserGroup(usr.getUserGroup());
                repoUrl.storeFile(extfile.startsWith("http://")?extfile:"http://"+extfile, fcomment, incremento, repoEleStat);
                response.setRenderParameter("status", "ok");
            }
            response.setCallMethod(SWBResourceURL.Call_DIRECT);
            response.setMode(MODE_RESPONSE);
        } else if ("removefile".equals(action)) {
            String fid = request.getParameter("fid");

            SemanticObject so = ont.getSemanticObject(fid);
            so.remove();

        } else if ("admin_update".equals(action)) {
            String viewrole = request.getParameter("ver");
            String modifyrole = request.getParameter("modificar");
            String adminrole = request.getParameter("administrar");
            String validfiles = request.getParameter("validfiles");
            String ipp = request.getParameter("itemsPerPage");
            String ssf = request.getParameter("hideSubFolders")!=null?"true":"false";
            String aph = request.getParameter("applyToChilds")!=null?"true":"false";

            try {
                getResourceBase().setAttribute(LVL_VIEW, viewrole);
                getResourceBase().setAttribute(LVL_MODIFY, modifyrole);
                getResourceBase().setAttribute(LVL_ADMIN, adminrole);
                getResourceBase().setAttribute(VALID_FILES, validfiles);
                getResourceBase().setAttribute("itemsPerPage", ipp);
                getResourceBase().setAttribute("hideSubFolders", ssf);
                getResourceBase().setAttribute("applyToChilds", aph);
                getResourceBase().updateAttributesToDB();
            } catch (Exception e) {
                log.error("Error al guardar configuración de niveles de usuario de ProcessFileRepository", e);
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
            response.setAction("edit");
        } else if (ACT_NEWFOLDER.equals(action)) {
            String dirTitle = request.getParameter("ftitle");
            String dirID = request.getParameter("fid");
            String dirDesc = request.getParameter("fdescription");
            
            if (response.getWebPage() instanceof RepositoryDirectory && RepositoryDirectory.ClassMgr.getRepositoryDirectory(dirID, site) == null) {
                if (dirID != null && dirID.length() > 0 && dirTitle != null && dirTitle.length() > 0) {
                    ResourceType rType = getResourceBase().getResourceType();
                    RepositoryDirectory parentDir = (RepositoryDirectory) response.getWebPage();

                    RepositoryDirectory newRepoDir = RepositoryDirectory.ClassMgr.createRepositoryDirectory(dirID, site);
                    newRepoDir.setTitle(dirTitle);
                    newRepoDir.setDescription(dirDesc);
                    newRepoDir.setParent(parentDir);
                    
                    //There should be only one resource in a repositoryDirectory
                    Iterator<Resource> ress = parentDir.listResources();
                    Resource rd = ress.next();
                                        
                    Resource res = site.createResource();
                    res.setResourceType(rType);
                    res.setTitle("REP_"+dirTitle);
                    res.setActive(Boolean.TRUE);
                    res.setAttribute("itemsPerPage", "10");
                    
                    newRepoDir.addResource(res);
                    newRepoDir.setActive(Boolean.TRUE);
                    
                    //EHSP25082015: Parche para herencia de atributos a subcarpetas
                    if (null != rd) {
                        String viewrole = rd.getAttribute(LVL_VIEW);
                        String modifyrole = rd.getAttribute(LVL_MODIFY);
                        String adminrole = rd.getAttribute(LVL_ADMIN);
                        String validfiles = rd.getAttribute(VALID_FILES);
                        String ipp = rd.getAttribute("itemsPerPage");
                        String ssf = rd.getAttribute("hideSubFolders");
                        String aph = rd.getAttribute("applyToChilds","false");
                        
                        if ("true".equals(aph)) {
                            res.setAttribute(LVL_VIEW, viewrole);
                            res.setAttribute(LVL_MODIFY, modifyrole);
                            res.setAttribute(LVL_ADMIN, adminrole);
                            res.setAttribute(VALID_FILES, validfiles);
                            res.setAttribute("itemsPerPage", ipp);
                            res.setAttribute("hideSubFolders", ssf);
                            res.setAttribute("applyToChilds", aph);

                            try {
                                res.updateAttributesToDB();
                            } catch (SWBException swbe) {
                                log.error("Problem setting child properties", swbe);
                            }
                        }
                    }//--EHSP25082015
                }
                response.setRenderParameter("status", "ok");
            }
            response.setMode(MODE_RESPONSE);
        }
        else if(ACT_EDITFOLDER.equals(action)){
            String fid = request.getParameter("fid");
            String newTitle = request.getParameter("titleRep");
            RepositoryDirectory rd = RepositoryDirectory.ClassMgr.getRepositoryDirectory(fid, site);
            if (null != rd && null != newTitle && !newTitle.isEmpty()) {
                rd.setTitle(newTitle);
                response.setRenderParameter("status", "ok");
            }
            response.setMode(MODE_RESPONSE);
        }else {
            super.processAction(request, response);
        }
    }
    
    /**
     * Obtiene el tipo de archivo de acuerdo a su extensión.
     * @param filename
     * @param lang
     * @return 
     */
    public static String getFileType(String filename, String lang) {
        if (filename == null) return null;
        
        String bundle = ProcessFileRepository.class.getName();
        Locale locale = new Locale(lang);
        String file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeUNK", locale);
        String type = filename.toLowerCase();
        
        if (type.endsWith(".bmp")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileImage", locale);;
        } else if (type.endsWith(".pdf")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypePDF", locale);
        } else if (type.endsWith(".xls") || type.endsWith(".xlsx")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeXLS", locale);
        } else if (type.endsWith(".html") || type.endsWith(".htm")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeHTML", locale);
        } else if (type.endsWith(".jpg") || type.endsWith(".jpeg")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeImage", locale);
        } else if (type.endsWith(".ppt") || type.endsWith(".pptx")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypePPT", locale);
        } else if (type.endsWith(".vsd")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeVSD", locale);
        } else if (type.endsWith(".mpp")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeMPP", locale);
        } else if (type.endsWith(".mmap")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeMMAP", locale);
        } else if (type.endsWith(".exe")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeEXE", locale);
        } else if (type.endsWith(".txt")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeTXT", locale);
        } else if (type.endsWith(".properties")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypePROP", locale);
        } else if (type.endsWith(".doc") || type.endsWith(".docx")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeDOC", locale);
        } else if (type.endsWith(".xml")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeXML", locale);
        } else if (type.endsWith(".gif") || type.endsWith(".png")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeImage", locale);
        } else if (type.endsWith(".avi")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeVIDEO", locale);
        } else if (type.endsWith(".mp3")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeAUDIO", locale);
        } else if (type.endsWith(".wav")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeAUDIO", locale);
        } else if (type.endsWith(".xsl")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeXSL", locale);
        } else if (type.startsWith("http://") || type.startsWith("https://") || type.startsWith("ftp://")) {
            file = SWBUtils.TEXT.getLocaleString(bundle, "lblFileTypeURL", locale);
        }
        return file;
    }
    
    private String getFolderPath (RepositoryDirectory root) {
        String ret = root.getId();
        
        WebPage current = root.getParent();
        while (current != null && current instanceof RepositoryDirectory) {
            ret = current.getId() + "|" + ret;
            current = current.getParent();
        }
        return ret.toString();
    }
    
    /**
     * Obtiene la lista de archivos del repositorio.
     * @param request The request.
     * @param paramRequest The SWBParamRequest.
     * @return Lista con los archivos del repositorio.
     */
    private List<GenericObject> listFiles(HttpServletRequest request, SWBParamRequest paramRequest) {
        List<GenericObject> ret = new ArrayList<GenericObject>();
        HashMap<String, GenericObject> hmNodes = new HashMap<String, GenericObject>();
        User user = paramRequest.getUser();
        String lang = "es";
        String _itemsPerPage = getResourceBase().getAttribute("itemsPerPage");
        boolean ssf = "true".equals(getResourceBase().getAttribute("hideSubFolders"));
        int itemsPerPage = 10;
        
        if (_itemsPerPage != null) {
            try {
                itemsPerPage = Integer.parseInt(_itemsPerPage);
            } catch (NumberFormatException ex) {
                itemsPerPage = 10;
            }
        }
        
        int page = 1;
        
        RepositoryDirectory repoDir = null;
        if (paramRequest.getWebPage() instanceof RepositoryDirectory) {
            repoDir = (RepositoryDirectory) paramRequest.getWebPage();
        }
        
        if (repoDir != null) {
            if (user != null && user.getLanguage() != null) {
                lang = user.getLanguage();
            }

            String usrgpo_filter = request.getParameter("usrgpo_filter");
            String orderBy = request.getParameter("sort");
            if (null == orderBy || orderBy.equals("")) {
                orderBy = "title";
            }

            Iterator<RepositoryElement> _itrf = repoDir.listRepositoryElements();
            while (_itrf.hasNext()) {
                RepositoryElement repoFile = _itrf.next();
                ret.add(repoFile);
            }
            
            if (!ssf) {
                Iterator<RepositoryDirectory> folders = repoDir.listChilds();
                while (folders.hasNext()) {
                    RepositoryDirectory folder = folders.next();
                    if (folder.isValid() && user.haveAccess(folder)) {
                        ret.add(folder);
                    }
                }
            }
            
            //SORTING
            Iterator<GenericObject> itrf = ret.iterator();
            while (itrf.hasNext()) {
                GenericObject gobj = itrf.next();
                String type = (gobj instanceof RepositoryElement) ? "file" : "folder";
                VersionInfo version = null;
                UserGroup ownerGroup = null;
                
                //RepositoryFile repoFile = itrf.next();
                if ("file".equals(type)) {
                    version = ((RepositoryElement)gobj).getActualVersion();
                    ownerGroup = ((RepositoryElement)gobj).getOwnerUserGroup();
                }
                
                String skey = gobj.getId();
                boolean showFile = Boolean.FALSE;
                
                if (ownerGroup != null) {
                    String ugid = ownerGroup.getId();
                    if (null != ugid && ugid.equals(usrgpo_filter) || usrgpo_filter == null || usrgpo_filter.equals("")) {
                        showFile = Boolean.TRUE;
                    }
                } else if (usrgpo_filter == null || usrgpo_filter.equals("")) {
                    showFile = Boolean.TRUE;
                }

                if ("file".equals(type) && (!showFile || version == null)) continue;
                
                if (orderBy.equals("title")) {
                    skey = ((Descriptiveable)gobj).getDisplayTitle(lang) + " - " + gobj.getId();
                } else if (orderBy.equals("date")) {
                    if ("file".equals(type)) {
                        skey = version.getCreated().getTime() + " - " + ((Descriptiveable)gobj).getDisplayTitle(lang) + " - " + gobj.getId();
                    } else {
                        skey = ((RepositoryDirectory)gobj).getCreated().getTime() + " - " + ((Descriptiveable)gobj).getDisplayTitle(lang) + " - " + gobj.getId();
                    }
                } else if (orderBy.equals("type")) {
                    String _type = "Directory";
                    
                    if ("file".equals(type)) {
                        String file = version.getVersionFile();
                        _type = getFileType(file, lang);
                    }
                    skey = _type + "-" + ((Descriptiveable)gobj).getDisplayTitle(lang) + " - " + gobj.getId();
                } else if (orderBy.equals("usr")) {
                    User usrc = null;
                    if ("file".equals(type)) {
                        usrc = version.getCreator();
                    } else {
                        usrc = ((Traceable)gobj).getCreator();
                    }
                    
                    skey = " - " + ((Descriptiveable)gobj).getDisplayTitle(lang) + " - " + gobj.getId();
                    if (usrc != null) {
                        skey = usrc.getFullName() + skey;
                    }
                } else if (orderBy.equals("gpousr")) {
                    if (ownerGroup == null) {
                        skey = " - " + " " + " - " + gobj.getId();
                    } else {
                        skey = " - " + ownerGroup.getDisplayTitle(lang) + " - " + gobj.getId();
                    }
                } else if (orderBy.equals("status")) {
                    ItemAwareStatus status = null;
                    
                    if ("file".equals(type)) {
                        ((RepositoryElement)gobj).getStatus();
                    }
                    
                    if (status == null) {
                        skey = " - " + " " + " - " + gobj.getId();
                    } else {
                        skey = " - " + status.getDisplayTitle(lang) + " - " + gobj.getId();
                    }
                }
                hmNodes.put(skey, gobj);
            }

            ArrayList<String> list = new ArrayList<String>(hmNodes.keySet());
            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
            
            //Realizar paginado de elementos
            int maxPages = 1;
            if (request.getParameter("p") != null && !request.getParameter("p").trim().equals("")) {
                page = Integer.valueOf(request.getParameter("p"));
                if (page < 0) page = 1;
            }
        
            if (hmNodes.size() >= itemsPerPage) {
                maxPages = (int)Math.ceil((double)hmNodes.size() / itemsPerPage);
            }
            if (page > maxPages) page = maxPages;

            int sIndex = (page - 1) * itemsPerPage;
            if (hmNodes.size() > itemsPerPage && sIndex > hmNodes.size() - 1) {
                sIndex = hmNodes.size() - itemsPerPage;
            }

            int eIndex = sIndex + itemsPerPage;
            if (eIndex >= hmNodes.size()) eIndex = hmNodes.size();

            request.setAttribute("maxPages", maxPages);
            ret = new ArrayList<GenericObject>();
            
            for (int i = sIndex; i < eIndex; i++) {
                String key = list.get(i);
                ret.add(hmNodes.get(key));
            }
        }
        return ret;
    }
    
    /**
     * Obtiene la lista de versiones de un elemento del repositorio.
     * @param el Elemento del repositorio del cual se obtendrán las versiones.
     * @return Lista de versiones del elemento del repositorio.
     */
    private List<VersionInfo> getFileVersions(RepositoryElement el) {
        ArrayList<VersionInfo> ret = new ArrayList<VersionInfo>();
        
        if (el != null) {
            VersionInfo vi = el.getLastVersion();
            VersionInfo ver = null;

            if (null != vi) {
                ver = vi;
                while (ver.getPreviousVersion() != null) {
                    ver = ver.getPreviousVersion();
                }
            }

            if (ver != null) {
                ret.add(ver);
                while (ver != null) {
                    ver = ver.getNextVersion();
                    if (ver != null) {
                        ret.add(ver);
                    }
                }
            }
        }
        return ret;
    }
}
