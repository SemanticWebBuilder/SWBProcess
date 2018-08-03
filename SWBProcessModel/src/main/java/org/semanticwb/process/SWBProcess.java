package org.semanticwb.process;

public class SWBProcess {
	
	private SWBProcess() { }
	
    public static class JSONProperties {
        public static final String PROP_CLASS = "class";
        public static final String PROP_TITLE = "title";
        public static final String PROP_DESCRIPTION = "description";
        public static final String PROP_CONNPOINTS = "connectionPoints";
        public static final String PROP_URI = "uri";
        public static final String PROP_X = "x";
        public static final String PROP_Y = "y";
        public static final String PROP_W = "w";
        public static final String PROP_H = "h";
        public static final String PROP_START = "start";
        public static final String PROP_END = "end";
        public static final String PROP_PARENT = "parent";
        public static final String PROP_CONTAINER = "container";
        public static final String PROP_ISMULTIINSTANCE = "isMultiInstance";
        public static final String PROP_ISSEQMULTIINSTANCE = "isSequentialMultiInstance";
        public static final String PROP_ISCOLLECTION = "isCollection";
        public static final String PROP_ISLOOP = "isLoop";
        public static final String PROP_ISCOMPENSATION = "isForCompensation";
        public static final String PROP_ISADHOC = "isAdHoc";
        public static final String PROP_ISTRANSACTION = "isTransaction";
        public static final String PROP_ISINTERRUPTING = "isInterrupting";
        public static final String PROP_LABELSIZE = "labelSize";
        public static final String PROP_INDEX = "index";
        
        private JSONProperties() { }
    }
}