package org.semanticwb.process.model.documentation;


import org.semanticwb.process.model.RepositoryDirectory;

public class Format extends org.semanticwb.process.model.documentation.base.FormatBase
{
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
