package org.semanticwb.process.resources.documentation.model;

import org.semanticwb.process.model.RepositoryDirectory;

/**
 * Clase que encapsula las propiedades de un formato (elemento que asocia un archivo) en la documentación de un proceso.
 */
public class Format extends org.semanticwb.process.resources.documentation.model.base.FormatBase
{
    /**
     * Constructor.
     * @param base 
     */
    public Format(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public boolean hasRepositoryReference() {
        boolean ret = false;
        if (null != this.getRefRepository()) {
            RepositoryDirectory rd = getRefRepository().getRepositoryDirectory();
            return rd != null;
        }
        return ret;
    }

    @Override
    public boolean isInstanceValid() {
        return hasRepositoryReference();
    }
}
