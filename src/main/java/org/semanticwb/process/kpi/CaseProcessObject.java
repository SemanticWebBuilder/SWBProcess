 /* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público ('open source'),
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
 * dirección electrónica: http://www.semanticwebbuilder.org.mx
 */
package org.semanticwb.process.kpi;

import java.util.ArrayList;
import java.util.Iterator;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ItemAwareReference;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.SubProcessInstance;

/**
 *
 * @author Sergio Téllez
 */
public class CaseProcessObject {
	private static Logger LOG = SWBUtils.getLogger(CaseProcessObject.class);

    public static Object sum(Process process, String processObjectURI, String propertyName) {
        Object sum = null;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            sum = getProcessObjects(pinst, processObjectURI, propertyName, sum);
        }
        return sum;
    }

    public static Object average(Process process, String processObjectURI, String propertyName) {
        int instances = 0;
        Object avg = null;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            avg = getProcessObjects(pinst, processObjectURI, propertyName, avg);
            instances++;
        }
        return averageObject(avg,instances);
    }

    public static Object maximum(Process process, String processObjectURI, String propertyName) {
        Object maximum = null;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            maximum = getMaximumProcessObject(pinst, processObjectURI, propertyName, maximum);
        }
        return maximum;
    }

    public static Object minimum(Process process, String processObjectURI, String propertyName) {
        Object minimum = null;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            minimum = getMinimumProcessObject(pinst, processObjectURI, propertyName, minimum);
        }
        return minimum;
    }

    public static ArrayList distincts(Process process, String processObjectURI, String propertyName) {
        ArrayList distincts = new ArrayList();
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            distinctProcessObjects(pinst, processObjectURI, propertyName, distincts);
        }
        return distincts;
    }

    private static void distinctProcessObjects(ProcessInstance pinst, String processObject, String property, ArrayList distincts) {
        Iterator<ItemAwareReference> objit = pinst.listItemAwareReferences();
        while(objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj = item.getProcessObject();
            //TODO: Verificar nombre del ItemAware
            if (obj.getSemanticObject().getSemanticClass().getName().equalsIgnoreCase(processObject)) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (property.equals(sp.getPropId()))
                        distinctObjects(obj.getSemanticObject(), sp, distincts);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                distinctProcessObjects((SubProcessInstance)flobin, processObject, property, distincts);
        }
    }

    private static void distinctProcessObjects(SubProcessInstance spinst, String processObject, String property, ArrayList distincts) {
        Iterator<ItemAwareReference> objit = spinst.listItemAwareReferences();
        while(objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj =  item.getProcessObject();
            //TODO: Verificar nombre del ItemAware
            if (obj.getSemanticObject().getSemanticClass().getName().equalsIgnoreCase(processObject)) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (property.equals(sp.getPropId()))
                        distinctObjects(obj.getSemanticObject(), sp, distincts);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = spinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                distinctProcessObjects((SubProcessInstance)flobin, processObject, property, distincts);
        }
    }

    private static Object getMinimumProcessObject(ProcessInstance pinst, String processObject, String property, Object minimum) {
        Iterator<ItemAwareReference> objit = pinst.listItemAwareReferences();
        while(objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj =  item.getProcessObject();
            //TODO: Verificar nombre del ItemAware
            if (obj.getSemanticObject().getSemanticClass().getName().equalsIgnoreCase(processObject)) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (property.equals(sp.getPropId()))
                        minimum = minimumObject(obj.getSemanticObject(), sp, minimum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                minimum = getMinimumProcessObject((SubProcessInstance)flobin, processObject, property, minimum);
        }
        return minimum;
    }

    private static Object getMinimumProcessObject(SubProcessInstance pinst, String processObject, String property, Object minimum) {
        Iterator<ItemAwareReference> objit = pinst.listItemAwareReferences();
        while(objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj =  item.getProcessObject();
            //TODO: Verificar nombre del ItemAware
            if (obj.getSemanticObject().getSemanticClass().getName().equalsIgnoreCase(processObject)) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (property.equals(sp.getPropId()))
                        minimum = minimumObject(obj.getSemanticObject(), sp, minimum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                minimum = getMinimumProcessObject((SubProcessInstance)flobin, processObject, property, minimum);
        }
        return minimum;
    }

    private static Object getMaximumProcessObject(ProcessInstance pinst, String processObject, String property, Object maximum) {
        Iterator<ItemAwareReference> objit = pinst.listItemAwareReferences();
        while(objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj =  item.getProcessObject();
            //TODO: Verificar nombre del ItemAware
            if (obj.getSemanticObject().getSemanticClass().getName().equalsIgnoreCase(processObject)) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (property.equals(sp.getPropId()))
                        maximum = maximumObject(obj.getSemanticObject(), sp, maximum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                maximum = getMaximumProcessObject((SubProcessInstance)flobin, processObject, property, maximum);
        }
        return maximum;
    }

    private static Object getMaximumProcessObject(SubProcessInstance spinst, String processObject, String property, Object maximum) {
        Iterator<ItemAwareReference> objit = spinst.listItemAwareReferences();
        while(objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj =  item.getProcessObject();
            //TODO: Verificar nombre del ItemAware
            if(obj.getSemanticObject().getSemanticClass().getName().equalsIgnoreCase(processObject)) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (property.equals(sp.getPropId()))
                        maximum = maximumObject(obj.getSemanticObject(), sp, maximum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = spinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                maximum = getMaximumProcessObject((SubProcessInstance)flobin, processObject, property, maximum);
        }
        return maximum;
    }

    private static Object getProcessObjects(ProcessInstance pinst, String processObject, String property, Object sum) {
        Iterator<ItemAwareReference> objit = pinst.listItemAwareReferences();
        while(objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj =  item.getProcessObject();
            //TODO: Verificar nombre del ItemAware
            if(obj.getSemanticObject().getSemanticClass().getName().equalsIgnoreCase(processObject)) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (property.equals(sp.getPropId()))
                        sum = sumatoryObject(obj.getSemanticObject(), sp, sum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                sum = getProcessObjects((SubProcessInstance)flobin, processObject, property, sum);
        }
        return sum;
    }

    private static Object getProcessObjects(SubProcessInstance spinst, String processObject, String property, Object sum) {
        Iterator<ItemAwareReference> objit = spinst.listItemAwareReferences();
        while(objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj =  item.getProcessObject();
            //TODO: Verificar nombre del ItemAware
            if (obj.getSemanticObject().getSemanticClass().getName().equalsIgnoreCase(processObject)) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if(property.equals(sp.getPropId()))
                        sum = sumatoryObject(obj.getSemanticObject(), sp, sum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = spinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                 sum = getProcessObjects((SubProcessInstance)flobin, processObject, property, sum);
        }
        return sum;
    }

    /**
     * Obtener la sumatoria de una propiedad para una instancia de un artefacto
     * (SemanticObject)
     *
     * @param            sob SemanticObject
     * @param            property SemanticProperty
     * @return           Object Valor de la propiedad
     * @see
    */
    private static Object sumatoryObject(SemanticObject sob, SemanticProperty property, Object value) {
        try {
            if(property.isInt()) {
                int ivalue = 0;
                if (null != value)
                    ivalue = ((Integer)value).intValue();
                ivalue += sob.getIntProperty(property);
                value = (Object)ivalue;
            }else if(property.isFloat()) {
                float fvalue = 0;
                if (null != value)
                    fvalue = ((Float)value).floatValue();
                fvalue += sob.getFloatProperty(property);
                value = (Object)fvalue;
            }else if(property.isDouble()) {
                double dvalue = 0.0;
                if (null != value)
                    dvalue = ((Double)value).floatValue();
                dvalue += sob.getDoubleProperty(property);
                value = (Object)dvalue;
            } else if(property.isLong()) {
                long lvalue = 0;
                if (null != value)
                    lvalue = ((Long)value).longValue();
                lvalue += sob.getLongProperty(property);
                value = (Object)lvalue;
            }
        }catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre) {
            LOG.error(rre);
        }catch(Exception e) {
        		LOG.error(e);
        }
        return value;
    }

    /**
     * Obtener el máximo de una propiedad para una instancia de un artefacto
     * (SemanticObject)
     *
     * @param            sob SemanticObject
     * @param            property SemanticProperty
     * @return      		Object Valor de la propiedad
     * @see
    */
    private static Object maximumObject(SemanticObject sob, SemanticProperty property, Object value) {
        try {
            if(property.isInt()){
                int ivalue = 0;
                if (null != value)
                    ivalue = ((Integer)value).intValue();
                if (ivalue < sob.getIntProperty(property))
                    value = sob.getIntProperty(property);
            }else if(property.isFloat()){
                float fvalue = 0;
                if (null != value)
                    fvalue = ((Float)value).floatValue();
                if (fvalue < sob.getFloatProperty(property))
                    value = sob.getFloatProperty(property);
            }else if(property.isDouble()){
                double dvalue = 0.0;
                if (null != value)
                    dvalue = ((Double)value).floatValue();
                if (dvalue < sob.getDoubleProperty(property))
                    value = sob.getDoubleProperty(property);
            } else if(property.isLong()){
                long lvalue = 0;
                if (null != value)
                    lvalue = ((Long)value).longValue();
                if (lvalue < sob.getLongProperty(property))
                    value = sob.getLongProperty(property);
            }
        } catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre){
        		LOG.error(rre);
        } catch(Exception e){
        		LOG.error(e);
        }return value;
    }

    /**
     * Obtener el máximo de una propiedad para una instancia de un artefacto
     * (SemanticObject)
     *
     * @param            sob SemanticObject
     * @param            property SemanticProperty
     * @return      		Object Valor de la propiedad
     * @see
    */
    private static Object minimumObject(SemanticObject sob, SemanticProperty property, Object value) {
        try {
            if (null != value) {
                if(property.isInt()) {
                    int ivalue = ((Integer)value).intValue();
                    if (ivalue > sob.getIntProperty(property))
                        value = sob.getIntProperty(property);
                }else if(property.isFloat()) {
                    float fvalue = ((Float)value).floatValue();
                    if (fvalue > sob.getFloatProperty(property))
                        value = sob.getFloatProperty(property);
                }else if(property.isDouble()) {
                    double dvalue = ((Double)value).floatValue();
                    if (dvalue > sob.getDoubleProperty(property))
                        value = sob.getDoubleProperty(property);
                }else if(property.isLong()) {
                    long lvalue = ((Long)value).longValue();
                    if (lvalue > sob.getLongProperty(property))
                        value = sob.getLongProperty(property);
                }
            }else {
                if(property.isInt())
                    value = sob.getIntProperty(property);
                else if(property.isFloat())
                    value = sob.getFloatProperty(property);
                else if(property.isDouble())
                    value = sob.getDoubleProperty(property);
                else if(property.isLong())
                    value = sob.getLongProperty(property);
            }
        } catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre) {
        		LOG.error(rre);
        } catch(Exception e) {
        		LOG.error(e);
        }
        return value;
    }

    /**
     * Obtener el máximo de una propiedad para una instancia de un artefacto (SemanticObject)
     * @param            sob SemanticObject
     * @param            property SemanticProperty
     * @return      		Object Valor de la propiedad
     * @see
    */
    private static void distinctObjects(SemanticObject sob, SemanticProperty property, ArrayList distincts) {
        try {
            if(property.isInt()) {
                Integer ivalue = sob.getIntProperty(property);
                if (!distincts.contains(ivalue))
                    distincts.add(ivalue);
            }else if(property.isFloat()){
                Float fvalue = sob.getFloatProperty(property);
                if (!distincts.contains(fvalue))
                    distincts.add(fvalue);
            }else if(property.isDouble()){
                Double dvalue = sob.getDoubleProperty(property);
                if (!distincts.contains(dvalue))
                    distincts.add(dvalue);
            }else if(property.isLong()){
                Long lvalue = sob.getLongProperty(property);
                if (!distincts.contains(lvalue))
                    distincts.add(lvalue);
            }
        }catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre) {
        		LOG.error(rre);
        }catch(Exception e) {
        		LOG.error(e);
        }
    }

    private static Object averageObject(Object sum, int instances) {
        double dsum = 0;
        
        if (sum instanceof Integer) {
        		int i = ((Integer)sum).intValue(); 
        		dsum = (double)i;
        } else if (sum instanceof Float) {
        		dsum = ((Float)sum).floatValue(); 
        } else if (sum instanceof Double) {
        		dsum = ((Double)sum).doubleValue();
        } else if (sum instanceof Long) {
        		long i = ((Long)sum).longValue(); 
        		dsum = (double)i;
        }
        
        if (instances > 0) return new Double(dsum /instances);
        return 0L;
    }
}