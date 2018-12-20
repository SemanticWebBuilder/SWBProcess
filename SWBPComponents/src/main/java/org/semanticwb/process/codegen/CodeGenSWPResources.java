package org.semanticwb.process.codegen;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenSWPModel;
import org.semanticwb.codegen.CodeGeneratorException;
import org.semanticwb.codegen.SWPCodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class CodeGenSWPResources {
    private static final Logger LOG = SWBUtils.getLogger(CodeGenSWPModel.class);
    private static final String CODE_BASE = "SWBPComponents/src/main/java";


    public static void main(String [] args) {
        List<String> owls = new ArrayList<>();
        List<String> prefixes = new ArrayList<>();

        owls.add("SWBPComponents/src/main/java/org/semanticwb/process/codegen/swpresources.owl");

        prefixes.add("swpres");

        SWPCodeGenerator cg = new SWPCodeGenerator();

        try {
            cg.generateCode(CODE_BASE, owls, prefixes);
        } catch (CodeGeneratorException cge) {
            LOG.error(cge);
        }
    }
}
