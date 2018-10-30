package org.semanticwb.codegen;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

import java.util.ArrayList;
import java.util.List;

public class CodeGenSWPModel {
    private static final Logger LOG = SWBUtils.getLogger(CodeGenSWPModel.class);
    private static final String CODE_BASE = "SWBProcessModel/src/main/java";

    public static void main(String [] args) {
        List<String> owls = new ArrayList<>();
        List<String> prefixes = new ArrayList<>();


        prefixes.add("swp");
        prefixes.add("swpdoc");
        SWPCodeGenerator cg = new SWPCodeGenerator();

        try {
            cg.generateCode(CODE_BASE, new ArrayList<>(), prefixes);
        } catch (CodeGeneratorException cge) {
            LOG.error(cge);
        }
    }
}
