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
package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.semanticwb.model.VersionInfo;


public class RepositoryElement extends org.semanticwb.process.model.base.RepositoryElementBase 
{
    public RepositoryElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Obtiene el listado de versiones del elemento.
     * @return Lista de versiones del elemento.
     */
    public List<VersionInfo> listVersions() {
        ArrayList<VersionInfo> ret = new ArrayList<VersionInfo>();
        VersionInfo ver = getLastVersion();
        
        if (null != ver) {
            ret.add(ver);
            while (ver.getPreviousVersion() != null) {
                ver = ver.getPreviousVersion();
                ret.add(ver);
            }
        }
        Collections.reverse(ret);
        return ret;
    }
    
    /**
     * Obtiene la lista de elementos que conforman la jerarquía del elemento en el repositorio.
     * Se devuelven por defecto en la lista los ID de los directorios.
     * @param asNames indica si se devuelven en la lista los nombres de los elementos en lugar del ID.
     * @param lang Idioma del usuario, por default "es".
     * @return Lista de elementos que conforman la jerarquía del elemento en el repositorio.
     */
    public ArrayList getElementRepositoryPath(boolean asNames, String lang) {
        ArrayList<String> nodes = new ArrayList<>();
        RepositoryDirectory root = getRepositoryDirectory();
        while (null != root) {
            String title = root.getTitle(null != lang ? lang : "es");
            if (null == title) title = root.getTitle();
            
            nodes.add(asNames ? title : root.getId());
            if (null != root.getParent() && root.getParent() instanceof RepositoryDirectory) {
                root = (RepositoryDirectory) root.getParent();
            } else {
                root = null;
            }
        }
        
        return nodes;
    }
    
    /**
     * Obtiene la clase de icono correspondiente al archivo de acuerdo a su extensión.
     * @return 
     */
    public String getIconClass() { //TODO: Mover extensiones a archivo de propiedades
        String ret = "fa fa-file-o";
        VersionInfo vi = getLastVersion();
        
        if (null != vi) {
            if (this instanceof RepositoryFile) {
                String type = vi.getVersionFile().toLowerCase();
                if (type.endsWith(".bmp") || type.endsWith(".jpg") || type.endsWith(".jpeg") || type.endsWith(".png") || type.endsWith(".svg") || type.endsWith(".gif")) {
                    ret = "fa fa-picture-o";
                } else if (type.endsWith(".pdf")) {
                    ret = "fa fa-file-pdf-o";
                } else if (type.endsWith(".xls") || type.endsWith(".xlsx") || type.endsWith(".numbers")) {
                    ret = "fa fa-file-excel-o";
                } else if (type.endsWith(".html") || type.endsWith(".htm")) {
                    ret = "fa fa-globe";
                } else if (type.endsWith(".ppt") || type.endsWith(".pptx") || type.endsWith(".key")) {
                    ret = "fa fa-file-powerpoint-o";
                } else if (type.endsWith(".exe")) {
                    ret = "fa fa-file";
                } else if (type.endsWith(".txt") || type.endsWith(".properties")) {
                    ret = "fa fa-file-text-o";
                } else if (type.endsWith(".doc") || type.endsWith(".docx") || type.endsWith(".pages")) {
                    ret = "fa fa-file-word-o";
                } else if (type.endsWith(".xml") || type.endsWith(".xsl")) {
                    ret = "fa fa-file-code-o";
                } else if (type.endsWith(".mmap")) {
                    ret = "fa fa-share-alt";
                } else if (type.endsWith(".avi") || type.endsWith(".wmv")) {
                    ret = "fa fa-file-video-o";
                } else if (type.endsWith(".mp3") || type.endsWith(".wav")) {
                    ret = "fa fa-file-audio-o";
                }
            } else if (this instanceof RepositoryURL) {
                ret = "fa fa-link";
            }
        }
        return ret;
    }
}
