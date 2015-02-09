package org.semanticwb.process.documentation.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;

public class SectionElement extends org.semanticwb.process.documentation.model.base.SectionElementBase {

    public SectionElement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    public static List<SectionElement> listSectionElementByTemplate(DocumentTemplate dt, WebSite model, SemanticClass scls, DocumentSectionInstance dsi) {
        List<SectionElement> list = new ArrayList<SectionElement>();
            if (dt != null) {
                Iterator<SectionElement> itsee = SectionElement.ClassMgr.listSectionElementByDocumentTemplate(dt, model);
                while (itsee.hasNext()) {
                    SectionElement see = itsee.next();
                    SemanticClass sclst = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(see.getParentSection().getSectionType().getURI());
                    if (scls.getClassId().equals(sclst.getClassId())) {
                        if (see.getDocumentSectionInst().getURI().equals(dsi.getURI())
                                || (see instanceof ElementReference)) {
                            continue;
                        }
                        list.add(see);
                    }
                }
            }
        return list;
    }
}
