package org.semanticwb.process.resources.documentation.model;

/**
 * Clase que encapsula las propiedades de una política en la documentación de un proceso.
 */
public class Policy extends org.semanticwb.process.resources.documentation.model.base.PolicyBase
{
    /**
     * Constructor.
     * @param base 
     */
    public Policy(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean isInstanceValid() {
        return true;
    }
}
