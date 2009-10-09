package org.semanticwb.process;

import org.semanticwb.model.User;

public interface ProcessTraceable extends org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swbps_ended=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#ended");
    public static final org.semanticwb.platform.SemanticProperty swbps_endedby=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#endedby");
    public static final org.semanticwb.platform.SemanticClass swbps_ProcessTraceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessTraceable");
    public java.util.Date getEnded();
    public void setEnded(java.util.Date ended);

    public void setEndedby(User user);

    public void removeEndedby();

    public User getEndedby();
}
