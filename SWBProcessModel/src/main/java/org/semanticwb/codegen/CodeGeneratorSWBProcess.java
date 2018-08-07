package org.semanticwb.codegen;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

import java.io.File;

/**
 * Class to generate code for SWBProcess Ontology Model.
 */
public class CodeGeneratorSWBProcess {
    private static final  String SWB_ONT = "https://raw.githubusercontent.com/SemanticWebBuilder/SWB/master/src/main/webapp/WEB-INF/owl/swb.owl";
    private static final  String STARTER_ONT = "SWBP/src/main/webapp/WEB-INF/owl/ext/swp.owl";
    private static final  String ONT_PREFIX = "swp";
    private static final  String SOURCECODE_BASE = "SWBProcessModel/src/main/java";
    private static final Logger LOG = SWBUtils.getLogger(CodeGeneratorSWBProcess.class);

    public static void main(String[] args) {
        new CodeGeneratorSWBProcess().codeGen(args);
    }

    public void codeGen(String[] args) {
        LOG.info("Starting code generation for ontology " + STARTER_ONT);
        SWBPlatform.createInstance();
        File f = new File(STARTER_ONT);
        SWBPlatform.getSemanticMgr().addBaseOntology(SWB_ONT);
        SWBPlatform.getSemanticMgr().addBaseOntology(f.toURI().toString());
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try {
            File dir = new File(SOURCECODE_BASE);
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode(ONT_PREFIX, false, dir);
            LOG.info("Class generation complete");
        } catch (CodeGeneratorException cge) {
            cge.printStackTrace();
        }
    }
}
