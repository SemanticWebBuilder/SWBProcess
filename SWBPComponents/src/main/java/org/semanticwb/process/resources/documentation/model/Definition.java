package org.semanticwb.process.resources.documentation.model;

import org.semanticwb.process.model.RepositoryDirectory;

/**
 * Clase que encapsula la información de una Definición en la documentación.
 */
public class Definition extends org.semanticwb.process.resources.documentation.model.base.DefinitionBase
{
    /**
     * Constructor.
     * @param base 
     */
    public Definition(org.semanticwb.platform.SemanticObject base)
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
