/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.xpdl;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser de XPDL para generar un modelo JSON de un proceso.
 * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class XPDLParser extends DefaultHandler {
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    private static final Logger LOG = SWBUtils.getLogger(XPDLParser.class);
    public static final String CLASS = "class";

    private String xsdPath;
    private SAXParserFactory spf;
    private Stack<String> xmlElementNames;
    private Stack<JSONObject> xmlElementObjects;
    private HashMap<String, JSONObject>  elements;
    private XPDLProcessor processor;
    private JSONObject processModel;
    private boolean validate = false;
    private Stack<String> contextStack;
    
    /**
     * Constructor. Crea e inicializa el parser sin forzar validación y con manejo de namespaces.
     */
    public XPDLParser() {
        initialize(false, true, null);
    }
    
    /**
     * Constructor. Crea e inicializa un parser forzando validación con el XSD proporcionado.
     * @param xsdPath ruta al archivo XSD para validación.
     */
    public XPDLParser(String xsdPath) {
        initialize(true, true, xsdPath);
    }
    
    /**
     * Inicializa el parser.
     * @param validate Indica si el parser forzará validación.
     * @param nsAware Indica si el parser manejará namespaces.
     * @param xsdPath Ruta al archivo XSD para validación.
     */
    private void initialize(boolean validate, boolean nsAware, String xsdPath) {
        this.validate = validate;
        this.xsdPath = xsdPath;
        
        spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(nsAware);
        spf.setValidating(validate);
        processor = new XPDLProcessor();
    }
    
    /**
     * Parsea el archivo XPDL y genera el modelo JSON del proceso.
     * @param istream Inputsream con el contenido del archivo XPDL.
     * @param normalize Indica si se normalizarán los saltos de línea previo al parseo.
     * @return JSON del modelo del proceso.
     */
    public JSONObject parse(InputStream istream, boolean normalize) {
        try {
            SAXParser parser = spf.newSAXParser();
            
            if (validate) {
                parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
                parser.setProperty(JAXP_SCHEMA_SOURCE, xsdPath);
            }
            
            if (normalize) {
                String xml = SWBUtils.IO.readInputStream(istream);
                istream.close();
                xml = replaceXMLWhiteSpaces(xml);
                parser.parse(SWBUtils.IO.getStreamFromString(xml), this);
            } else {
                parser.parse(istream, this);
            }
        } catch (ParserConfigurationException ex) {
            LOG.error("Error de configuración del parser", ex);
            try {
                processModel = new JSONObject();
                processModel.put("error", "Ocurrió un problema al procesar el archivo del modelo");
            } catch (JSONException ex1) { }
        } catch (SAXException ex) {
            LOG.error("Error al crear el parser", ex);
            try {
                processModel = new JSONObject();
                processModel.put("error", "Ocurrió un problema al procesar el archivo del modelo, verifique que el archivo XPDL está bien formado");
            } catch (JSONException ex1) { }
        } catch (IOException ex) {
            LOG.error("Error al leer el archivo del modelo", ex);
            try {
                processModel = new JSONObject();
                processModel.put("error", "Ocurrió un problema al leer el archivo del modelo");
            } catch (JSONException ex1) { }
        }
        return processModel;
    }
    
    /**
     * Parsea el archivo XPDL y genera el modelo JSON del proceso.
     * @param istream Inputsream con el contenido del archivo XPDL.
     * @return JSON del modelo del proceso.
     */
    public JSONObject parse(InputStream istream) {
        parse(istream, true);
        return processModel;
    }
    
    /**
     * Parsea el archivo XPDL y genera el modelo JSON del proceso.
     * @param file Ruta al archivo XPDL a validar.
     * @return JSON del modelo del proceso.
     * @throws Exception 
     */
    public JSONObject parse(String file) throws FileNotFoundException {
        InputStream xmlStream = new FileInputStream(new File(file));
        return parse(xmlStream);
    }
    
    @Override
    public void startDocument() throws SAXException {
        xmlElementNames = new Stack<>();
        xmlElementObjects = new Stack<>();
        contextStack = new Stack<>();
        elements = new HashMap<>();
        processModel = null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String context = contextStack.isEmpty() ? "Process:1" : contextStack.peek();
        
        try {
            processElement(context, uri, localName, qName, attributes);
        } catch (JSONException ex) {
            LOG.error("Ocurrió un error procesando el elemento", ex);
        }
        
        if ("ActivitySet".equals(localName)) contextStack.push(attributes.getValue("Id"));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("ActivitySet".equals(localName) && !contextStack.isEmpty()) contextStack.pop();
        
        if (!xmlElementNames.isEmpty()) {
            if (xmlElementNames.peek().equals(localName)) {
                xmlElementNames.pop();
            } else {
                //Unmatched closing tag
            }
        } else {
            //Unmatched opening tag
        }
        
        if (!xmlElementObjects.isEmpty()) {
            JSONObject obj = xmlElementObjects.pop();
            String type = obj.optString(CLASS,"");
            
            if (localName.equalsIgnoreCase(type)) {
                elements.put(obj.optString("uri",""), obj);
            } else {
                xmlElementObjects.push(obj);
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        if (xmlElementNames.isEmpty()) {
            try {
                processModel = createProcessJSON(elements);
            } catch (JSONException ex) {
                LOG.error(ex);
            }
        }
    }
    
    /**
     * Genera un modelo JSON del proceso a partir de un hashmap de objetos.
     * @param elements HashMap de objetos recuperados en el parseo del archivo XPDL.
     * @return JSON del modelo del proceso.
     * @throws JSONException 
     */
    private JSONObject createProcessJSON(HashMap<String, JSONObject> elements) throws JSONException {
        JSONObject ret = new JSONObject();
        ret.put(CLASS,"Process");
        ret.put("title","NewProcess");
        ret.put("uri","Process:1");
        
        if (elements != null && !elements.isEmpty()) {
            JSONArray nodes = new JSONArray();
            HashMap<String, ArrayList<JSONObject>> pools = new HashMap<>();
            
            for(String key : elements.keySet()) {
                JSONObject el = elements.get(key);
                String cls = el.optString(CLASS, "");
                String fcls = el.optString("_class", cls);
                String _cls = fcls;
                String impl = el.optString(XPDLProcessor.XPDLEntities.IMPLEMENTATION, "");
                
                if (XPDLProcessor.XPDLEntities.ACTIVITY.equals(cls)) {
                    if (!"".equals(fcls)) { //No es una actividad propiamente
                        if (XPDLProcessor.XPDLEntities.STARTEVENT.equals(fcls) || XPDLProcessor.XPDLEntities.INTERMEDIATEEVENT.equals(fcls) || XPDLProcessor.XPDLEntities.ENDEVENT.equals(fcls)) {
                            String prefix = el.optString(XPDLProcessor.XPDLAttributes.TRIGGER, "");
                            String catchThrow = el.optString(XPDLProcessor.XPDLAttributes.CATCHTHROW, "");        
                            _cls = getEventType(fcls, prefix, catchThrow);

                            el.remove(XPDLProcessor.XPDLAttributes.CATCHTHROW);
                            el.remove(XPDLProcessor.XPDLAttributes.TRIGGER);
                            
                            //Offset events
                            el.put("x", el.getDouble("x")+15);
                            el.put("y", el.getDouble("y")+15);
                            el.remove("w");
                            el.remove("h");
                        } else if (XPDLProcessor.XPDLEntities.ROUTE.equals(fcls)) {
                            String gtwType = el.optString(XPDLProcessor.XPDLAttributes.GATEWAYTYPE,"");
                            String excType = el.optString(XPDLProcessor.XPDLAttributes.EXCLUSIVETYPE,"");
                            boolean instantiate = el.optBoolean(XPDLProcessor.XPDLAttributes.INSTANTIATE,false);
                            _cls = "";
                            
                            if ("Complex".equals(gtwType)) { //Compleja
                                _cls = "ComplexGateway";
                            } else if ("Parallel".equals(gtwType)) { //Paralela
                                _cls = "ParallelGateway";
                                if (instantiate) {
                                    _cls = "ParallelStartEventGateway";
                                }
                            } else if ("Inclusive".equals(gtwType)) { //Paralela 
                                _cls = "InclusiveGateway";
                            } else if ("".equals(gtwType)) { //Es una exclusiva
                                _cls = "ExclusiveGateway";
                                if (instantiate) {
                                    _cls = "ParallelStartEventGateway";
                                } else if ("Event".equals(excType)) {
                                    _cls = "ExclusiveIntermediateEventGateway";
                                }
                            }
                            el.remove(XPDLProcessor.XPDLAttributes.GATEWAYTYPE);
                            el.remove(XPDLProcessor.XPDLAttributes.EXCLUSIVETYPE);
                            el.remove(XPDLProcessor.XPDLAttributes.INSTANTIATE);
                            //Offset gateways
                            el.put("x", el.getDouble("x")+25);
                            el.put("y", el.getDouble("y")+25);
                            el.remove("w");
                            el.remove("h");
                        } else {
                            _cls = "Task";
                            String set = el.optString(XPDLProcessor.XPDLAttributes.ACTIVITYSETID,"");
                            
                            if ("TaskSend".equals(impl)) _cls = "SendTask";
                            else if ("TaskReceive".equals(impl)) _cls = "ReceiveTask";
                            else if ("TaskUser".equals(impl)) _cls = "UserTask";
                            else if ("TaskService".equals(impl)) _cls = "ServiceTask";
                            else if ("TaskScript".equals(impl)) _cls = "ScriptTask";
                            else if ("TaskBusinessRule".equals(impl)) _cls = "BusinessRuleTask";
                            else if ("TaskManual".equals(impl)) _cls = "ManualTask";
                            else if (!"".equals(set)) {
                                _cls = "SubProcess";
                            }
                            //Offset tasks
                            el.put("x", el.getDouble("x")+(el.getDouble("w")/2));
                            el.put("y", el.getDouble("y")+(el.getDouble("h")/2));
                            el.remove(XPDLProcessor.XPDLEntities.IMPLEMENTATION);
                            el.remove(XPDLProcessor.XPDLAttributes.ACTIVITYSETID);
                        }
                    }
                } else if (XPDLProcessor.XPDLEntities.POOL.equals(cls)) {
                    el.remove("container");
                } else if (XPDLProcessor.XPDLEntities.DATASTOREREFERENCE.equals(cls)) {
                    JSONObject referred = elements.get(el.optString(XPDLProcessor.XPDLAttributes.DATASTOREREF,""));
                    if (referred != null) {
                        double x = el.optDouble("x",0)+30;
                        double y = el.optDouble("y",0)+26;
                        referred.put("x", x);
                        referred.put("y", y);
                    }
                } else if (XPDLProcessor.XPDLEntities.ASSOCIATION.equals(cls)) {
                    JSONObject source = elements.get(el.optString(XPDLProcessor.XPDLAttributes.SOURCE, ""));
                    JSONObject target = elements.get(el.optString(XPDLProcessor.XPDLAttributes.TARGET, ""));
                    
                    String sourceCls = "";
                    String targetCls = "";
                    
                    if (source != null) {
                        sourceCls = source.optString("_class", source.optString(CLASS, ""));
                    }
                    if (target != null) {
                        targetCls = target.optString("_class", target.optString(CLASS, ""));
                    }
                    
                    if (XPDLProcessor.XPDLEntities.DATAOBJECT.equals(sourceCls) || XPDLProcessor.XPDLEntities.DATAOBJECT.equals(targetCls)
                            || XPDLProcessor.XPDLEntities.DATASTOREREFERENCE.equals(sourceCls) || XPDLProcessor.XPDLEntities.DATASTOREREFERENCE.equals(targetCls)) {
                        _cls = "DirectionalAssociation";
                    } else {
                        _cls = "AssociationFlow";
                    }
                    //TODO: Verificar asociaciones, agregar sólo en caso de que tengan inicio y fin
                    if (XPDLProcessor.XPDLEntities.DATASTOREREFERENCE.equals(sourceCls)) {
                        el.put("start",source.optString(XPDLProcessor.XPDLAttributes.DATASTOREREF,""));
                    } else {
                        el.put("start",source.getString("uri"));
                    }
                    
                    if (XPDLProcessor.XPDLEntities.DATASTOREREFERENCE.equals(targetCls)) {
                        el.put("end",target.optString(XPDLProcessor.XPDLAttributes.DATASTOREREF,""));
                    } else {
                        el.put("end",target.getString("uri"));
                    }
                }
                
                if (el.optString("container", "").equals("")) {
                    el.put("container", "Process:1");
                }
                if (!XPDLProcessor.XPDLEntities.TRANSITION.equals(cls)) {
                    el.put("labelSize", 12);
                    el.put("index", 0);
                }
                if (!XPDLProcessor.XPDLEntities.WORKFLOWPROCESS.equals(cls)) {
                    if (XPDLProcessor.XPDLEntities.LANE.equals(cls)) {
                        String poolId = el.optString("parent", "");
                        if (!"".equals(poolId)) {
                            ArrayList<JSONObject> lanes = pools.get(poolId);
                            if (lanes == null) {
                                lanes = new ArrayList<>();
                            }
                            lanes.add(el);
                            pools.put(poolId, lanes);
                        }
                    } else {
                        if (XPDLProcessor.XPDLEntities.POOL.equals(cls)) {
                            boolean visible = el.optBoolean(XPDLProcessor.XPDLAttributes.BOUNDARYVISIBLE, false);
                            if (visible) {
                                double w = el.optDouble("w",0);
                                double h = el.optDouble("h",0);
                                el.put("x", el.getDouble("x")+(w/2));
                                el.put("y", el.getDouble("y")+(h/2));
                                nodes.put(el);
                            }
                        } else {
                            nodes.put(el);
                        }
                    }
                }
                
                el.put(CLASS, _cls);
                if ("GroupArtifact".equals(_cls) || "AnnotationArtifact".equals(_cls)) {
                    //Offset artifact
                    el.put("x", el.getDouble("x")+(el.getDouble("w")/2));
                    el.put("y", el.getDouble("y")+(el.getDouble("h")/2));
                } else if ("DataObject".equals(_cls) || "DataInput".equals(_cls) || "DataOutput".equals(_cls)) {
                    //Offset dataobjects
                    el.put("x", el.getDouble("x")+20);
                    el.put("y", el.getDouble("y")+26);
                }
                
                el.remove("_class");
            }
            
            //Sort lanes
            Comparator<JSONObject> posComp = new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2) {
                    double y1 = o1.optDouble("y", 0);
                    double y2 = o2.optDouble("y", 0);
                    
                    if (y1 == y2) return 0;
                    else if (y1 > y2) return 1;
                    else return -1;
                }
            };
            
            for (String key : pools.keySet()) {
                ArrayList<JSONObject> lanes = pools.get(key);
                Collections.sort(lanes, posComp);
                if (lanes != null && !lanes.isEmpty()) {
                    Iterator<JSONObject> it = lanes.iterator();
                    while (it.hasNext()) {
                        JSONObject jSONObject = it.next();
                        nodes.put(jSONObject);
                    }
                }
            }
            ret.put("nodes", nodes);
        }
        return ret;
    }
    
    /**
     * Determina el tipo de un evento.
     * @param type Tipo de evento segun el tag de XPDL
     * @param trigger Propiedad Trigger de XPDL
     * @param catchThrow Tipo de comportamiento del evento en XPDL.
     * @return Tipo de evento acorde a las definiciones de SWB Process.
     */
    private String getEventType(String type, String trigger, String catchThrow) {
        String ret = trigger;
        String posfix = type;
        if ("None".equals(ret)) ret = "";
        
        if (null != trigger) switch (trigger) {
            case "Conditional":
                ret = "Rule";
                break;
            case "ParallelMultiple":
                ret = "Parallel";
                break;
            case "Multiple":
                ret = "Multiple";
                break;
            case "Escalation":
                ret = "Scalation";
                break;
            case "Cancel":
                ret = "Cancelation";
                break;
            case "Terminate":
                ret = "Termination";
                break;
            default:
                break;
        }
        
        //TODO: Los primeros dos casos se pueden simplificar
        if (null != type) switch (type) {
            case XPDLProcessor.XPDLEntities.STARTEVENT:
                posfix = XPDLProcessor.XPDLEntities.STARTEVENT;
                break;
            case XPDLProcessor.XPDLEntities.ENDEVENT:
                posfix = XPDLProcessor.XPDLEntities.ENDEVENT;
                break;
            case XPDLProcessor.XPDLEntities.INTERMEDIATEEVENT:
                if (!"".equals(catchThrow)) {
                    posfix = "IntermediateThrowEvent";
                } else {
                    posfix = "IntermediateCatchEvent";
                }   break;
            default:
                break;
        }
        return ret + posfix;
    }
    
    /**
     * Crea un hashmap con los atributos de un tag en el parseo del archivo XPDL.
     * @param attributes Lista de atributos.
     * @return Mapa de atributos en llave, valor.
     */
    private HashMap<String, String> getAttributeMap(Attributes attributes) {
        HashMap<String, String> ret = new HashMap<>();
        for (int i = 0; i < attributes.getLength(); i++) {
            ret.put(attributes.getLocalName(i), attributes.getValue(i));
        }
        return ret;
    }
    
    /**
     * Procesa un elemento durante el parseo del archivo XPDL.
     * @param uri URI del elemento
     * @param localName Nombre local del elemento.
     * @param qName Qualified name del elemento.
     * @param attributes Attributos del elemento.
     * @throws JSONException 
     */
    private void processElement(String context, String uri, String localName, String qName, Attributes attributes) throws JSONException {
        HashMap<String, String> atts = getAttributeMap(attributes);
        
        if (XPDLProcessor.XPDLEntities.ACTIVITY.equals(localName)) {
            processor.processActivity(context, xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.BLOCKACTIVITY.equals(localName)) {
            processor.processBlockActivity(xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.LANE.equals(localName)) {
            processor.processLane(context, xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.POOL.equals(localName)) {
            processor.processPool(context, xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.NODEGRAPHICSINFO.equals(localName)) {
            processor.processNodeGraphicsInfo(xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.COORDINATES.equals(localName)) {
            processor.processCoordinates(xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.STARTEVENT.equals(localName) || XPDLProcessor.XPDLEntities.INTERMEDIATEEVENT.equals(localName) || XPDLProcessor.XPDLEntities.ENDEVENT.equals(localName)) {
            processor.processEvent(localName, xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.TRIGGERRESULTMESSAGE.equals(localName) || XPDLProcessor.XPDLEntities.TRIGGERRESULTSIGNAL.equals(localName)
                || XPDLProcessor.XPDLEntities.TRIGGERRESULTLINK.equals(localName)) {
            processor.processInterEventResult(xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.ROUTE.equals(localName)) {
            processor.processGateway(xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.DATASTOREREFERENCE.equals(localName) || XPDLProcessor.XPDLEntities.DATASTORE.equals(localName) || XPDLProcessor.XPDLEntities.DATAOBJECT.equals(localName)) {
            processor.processDataObject(context, xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.DATAFIELD.equals(localName)) {
            processor.processDataField(xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.TASKUSER.equals(localName) || XPDLProcessor.XPDLEntities.TASKSERVICE.equals(localName) 
                || XPDLProcessor.XPDLEntities.TASKRECEIVE.equals(localName) || XPDLProcessor.XPDLEntities.TASKSEND.equals(localName)
                || XPDLProcessor.XPDLEntities.TASKSCRIPT.equals(localName) || XPDLProcessor.XPDLEntities.TASKMANUAL.equals(localName)
                || XPDLProcessor.XPDLEntities.TASKBUSINESSRULE.equals(localName)) {
            processor.processTaskImplementation(localName, xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.LOOP.equals(localName)) {
            processor.processLoopType(xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.ARTIFACT.equals(localName)) {
            processor.processArtifact(context, xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.TRANSITION.equals(localName)) {
            processor.processTransition(context, xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.CONDITION.equals(localName)) {
            processor.processConditionType(xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.WORKFLOWPROCESS.equals(localName)) {
            processor.processWorkFlow(xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.ACTIVITYSET.equals(localName)) {
            processor.processActivitySet(context, xmlElementNames, xmlElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.ASSOCIATION.equals(localName)) {
            processor.processAssociation(context, xmlElementNames, xmlElementObjects, atts);
        }
        //Put current tag on top of the stack
        xmlElementNames.push(localName);
    }
    
    /**
     * Sustituye los caracteres de espacio en blanco válidos para la versión 1.0 de XML, pero no permitidos en otras versiones.
     * @param source Fuente XML
     * @return XML sin entidades para caracteres de espacio en blanco.
     */
    private String replaceXMLWhiteSpaces(String source) {
        return source.replaceAll("&#x[20|9|D|A];"," ");//https://www.w3.org/TR/xml/#charsets https://www.w3.org/TR/xml11/#charsets
    }
}