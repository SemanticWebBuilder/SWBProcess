package org.semanticwb.process.resources.documentation.model;

/**
 * Clase que encapsula las propiedades de un rol dentro de la documentación de un proceso.
 */
public class Role extends org.semanticwb.process.resources.documentation.model.base.RoleBase
{
    /**
     * Constructor.
     * @param base 
     */
    public Role(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean isInstanceValid() {
        return true;
    }
}
