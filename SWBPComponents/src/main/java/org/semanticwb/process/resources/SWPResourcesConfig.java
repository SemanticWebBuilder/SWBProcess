package org.semanticwb.process.resources;


import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebSite;

import java.util.Iterator;

public class SWPResourcesConfig extends org.semanticwb.process.resources.base.SWPResourcesConfigBase {
    public SWPResourcesConfig(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Obtiene el objeto de configuración para los recursos de SWBProcess
     * @param site Sitio Web
     * @return Objeto de configuración creado para los recursos de SWBProcess
     */
    public static SWPResourcesConfig getConfgurationInstance(WebSite site) {
        SWPResourcesConfig ret = null;
        Iterator<SWPResourcesConfig> configList = SWBComparator.sortByCreated(SWPResourcesConfig.ClassMgr.listSWPResourcesConfigs(site));
        if (null != configList && configList.hasNext()) {
            return configList.next();
        } else {
            ret = SWPResourcesConfig.ClassMgr.createSWPResourcesConfig(site);
        }

        return ret;
    }
}
