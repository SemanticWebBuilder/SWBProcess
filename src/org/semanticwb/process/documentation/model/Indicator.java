package org.semanticwb.process.documentation.model;

/**
 * Clase que encapsula las propiedades de un indicador dentro de la documentación de un proceso.
 */
public class Indicator extends org.semanticwb.process.documentation.model.base.IndicatorBase 
{
    /**
     * Constructor.
     * @param base 
     */
    public Indicator(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean isInstanceValid() {
        return true;
    }
}
