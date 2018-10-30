package org.semanticwb.codegen;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

import java.io.File;
import java.util.List;

/**
 * Class to generate code for SWBProcess Ontology Models.
 */
public class SWPCodeGenerator extends CodeGenerator {
    private static final Logger LOG = SWBUtils.getLogger(SWPCodeGenerator.class);
    private static final  String SWB_ONT = "https://raw.githubusercontent.com/SemanticWebBuilder/SWB/master/src/main/webapp/WEB-INF/owl/swb.owl";
    private static final  String SWP_ONT = "SWBP/src/main/webapp/WEB-INF/owl/ext/swp.owl";

    public SWPCodeGenerator() {
        super();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().addBaseOntology(SWB_ONT);
        SWBPlatform.getSemanticMgr().addBaseOntology(new File(SWP_ONT).toURI().toString());
    }

    public void generateCode(String codeBase, List<String> owlPaths, List<String> prefixes) throws CodeGeneratorException {
        if (null == codeBase || codeBase.isEmpty()) {
            throw new CodeGeneratorException("No source code path provided");
        } else if (null == prefixes || prefixes.isEmpty()) {
            throw new CodeGeneratorException("No prefixes provided");
        }

        LOG.info("Starting code generation...");
        for (String path : owlPaths) {
            File f = new File(path);
            if (f.exists()){
                LOG.info("Loading ontology " + f.getName());
                SWBPlatform.getSemanticMgr().addBaseOntology(f.toURI().toString());
            }
        }
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        File dir = new File(codeBase);
        for (String prefix : prefixes) {
            LOG.info("Code generation for prefix " + prefix);
            generateCode(prefix, false, dir);
        }

        LOG.info("Code generation complete");
    }
}
