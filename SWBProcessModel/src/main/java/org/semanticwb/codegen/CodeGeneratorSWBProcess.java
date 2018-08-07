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
    private static final  String SWP_ONT = "SWBP/src/main/webapp/WEB-INF/owl/ext/swp.owl";
    private static final  String SWP_PREFIX = "swp";
    private static final  String SWPDOC_PREFIX = "swpdoc";
    private static final  String SOURCECODE_BASE = "SWBProcessModel/src/main/java";
    private static final Logger LOG = SWBUtils.getLogger(CodeGeneratorSWBProcess.class);

    public static void main(String[] args) {
        new CodeGeneratorSWBProcess().codeGen(args);
    }

    public void codeGen(String[] args) {
        LOG.info("Starting code generation for ontology " + SWP_ONT);
        SWBPlatform.createInstance();
        File f = new File(SWP_ONT);
        SWBPlatform.getSemanticMgr().addBaseOntology(SWB_ONT);
        SWBPlatform.getSemanticMgr().addBaseOntology(f.toURI().toString());
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try {
            File dir = new File(SOURCECODE_BASE);
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode(SWP_PREFIX, false, dir);
            codeGeneration.generateCode(SWPDOC_PREFIX, false, dir);
            LOG.info("Class generation complete");
        } catch (CodeGeneratorException cge) {
            cge.printStackTrace();
        }
    }
}
