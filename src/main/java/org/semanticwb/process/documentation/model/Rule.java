package org.semanticwb.process.documentation.model;

/**
 * Clase que encapsula las propiedades de una regla en la documentación de un proceso.
 */
public class Rule extends org.semanticwb.process.documentation.model.base.RuleBase 
{
    /**
     * Constructor.
     * @param base 
     */
    public Rule(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean isInstanceValid() {
        return true;
    }
}
