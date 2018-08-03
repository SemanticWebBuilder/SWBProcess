package org.semanticwb.process.documentation.model;

/**
 * Clase que encapsula las propiedades de un riesgo en la documentación de un proceso.
 */
public class Risk extends org.semanticwb.process.documentation.model.base.RiskBase 
{
    /**
     * Constructor.
     * @param base 
     */
    public Risk(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean isInstanceValid() {
        return true;
    }
}
