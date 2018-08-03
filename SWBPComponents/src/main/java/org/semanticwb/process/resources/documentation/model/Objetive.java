package org.semanticwb.process.resources.documentation.model;

/**
 * Clase que encapsula la información de un objetivo en la documentación de un proceso.
 */
public class Objetive extends org.semanticwb.process.resources.documentation.model.base.ObjetiveBase
{
    /**
     * Constructor.
     * @param base 
     */
    public Objetive(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean isInstanceValid() {
        return true;
    }
}
