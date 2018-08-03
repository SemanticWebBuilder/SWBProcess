package org.semanticwb.process.resources.documentation.model;

import org.semanticwb.process.model.RepositoryDirectory;

/**
 * Clase que encapsula la información de una referencia documental en la documentación de un proceso.
 */
public class Reference extends org.semanticwb.process.resources.documentation.model.base.ReferenceBase
{
    /**
     * Constructor.
     * @param base 
     */
    public Reference(org.semanticwb.platform.SemanticObject base)
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
