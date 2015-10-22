package org.semanticwb.process.documentation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.Task;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.SubProcess;

/**
 * Clase que encapsula las propiedades de una instancia particular de la documentación de un proceso.
 */
public class DocumentationInstance extends org.semanticwb.process.documentation.model.base.DocumentationInstanceBase {

    /**
     * Constructor.
     * @param base 
     */
    public DocumentationInstance(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Obtiene un mapa con las instancias de secciones asociadas a una instancia de documentación.
     * @param instance Instancia de documentación.
     * @return Mapa con instancias de recciones asociadas a la instancia de documentación.
     */
    public static Map getDocumentSectionInstanceMap(DocumentationInstance instance) {
        Map map = new HashMap();
        Iterator<DocumentSectionInstance> itdsi = instance.listDocumentSectionInstances();
        while (itdsi.hasNext()) {
            DocumentSectionInstance dsit = itdsi.next();
            DocumentSection ds = dsit.getSecTypeDefinition();
            if (null != ds) {
                dsit.setIndex(ds.getIndex());
                map.put(dsit.getSecTypeDefinition().getURI(), dsit.getURI());
            } else {
                dsit.remove();
            }
        }
        return map;
    }

//    public static DocumentationInstance createDocumentSectionInstance(ProcessSite model, DocumentTemplate template, org.semanticwb.process.model.Process process) {
//        //Crea una nueva instancia de documentación
//        DocumentationInstance di = DocumentationInstance.ClassMgr.createDocumentationInstance(model);
//        di.setDocTypeDefinition(template);
//        di.setProcessRef(process);
//        Iterator<DocumentSection> itdsi = di.getDocTypeDefinition().listDocumentSections();
//        while (itdsi.hasNext()) {
//            DocumentSection ds = itdsi.next();
//            DocumentSectionInstance dsi = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
//            dsi.setSecTypeDefinition(ds);
//            dsi.setDocumentationInstance(di);
//
//            di.addDocumentSectionInstance(dsi);
//            SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
//            if (FreeText.sclass.getClassId().equals(cls.getClassId())) {
//                FreeText ft = FreeText.ClassMgr.createFreeText(model);
//                ft.setText("");
//                dsi.addDocuSectionElementInstance(ft);
//                ft.setParentSection(ds);
//                ft.setDocumentTemplate(template);
//            }
//            if (Activity.sclass.getClassId().equals(cls.getClassId())) {
//                Iterator<GraphicalElement> itge = di.getProcessRef().listAllContaineds();
//                while (itge.hasNext()) {
//                    GraphicalElement ge = itge.next();
//                    if (ge instanceof org.semanticwb.process.model.SubProcess || ge instanceof Task) {
//                        String urige = ge.getURI();
//                        org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);
//
//                        ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
//                        actRef.setProcessActivity(act);
//                        Activity actFin = Activity.ClassMgr.createActivity(model);
//                        actFin.setTitle(act.getTitle());
//                        actFin.setDocumentTemplate(template);
////                        actFin.setDescription(act.getDescription());
//                        actFin.setActivityRef(actRef);
//                        actFin.setIndex(ge.getIndex());
//                        dsi.addDocuSectionElementInstance(actFin);
//                        actFin.setParentSection(ds);
//                    }
//                }
//            }
//        }
//        return di;
//    }

//    public static ArrayList listDocumentSections(DocumentTemplate dt, Map map, DocumentationInstance di, ProcessSite model, String clsuri) {
//        Iterator<DocumentSection> itdst = SWBComparator.sortSemanticObjects(dt.listDocumentSections());
//        ArrayList arr = new ArrayList();
//        int i = 0;
//        while (itdst.hasNext()) {
//            DocumentSection dst = itdst.next();
//            if (dst.isActive()) {
//                SemanticClass semcls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dst.getSectionType().getURI());
//                if (!clsuri.contains(semcls.getClassId())) {
//                    if (!map.containsKey(dst.getURI())) {
//                        DocumentSectionInstance dsin = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
//                        dsin.setSecTypeDefinition(dst);
//                        dsin.setDocumentationInstance(di);
//                        di.addDocumentSectionInstance(dsin);
//                        SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsin.getSecTypeDefinition().getSectionType().getURI());
//                        if (FreeText.sclass.getClassId().equals(cls.getClassId())) {
//                            FreeText ft = FreeText.ClassMgr.createFreeText(model);
//                            ft.setText("");
//                            ft.setDocumentTemplate(dt);
//                            SectionElement se = (SectionElement) ft.getSemanticObject().createGenericInstance();
//                            dsin.addDocuSectionElementInstance(se);
//                        }
//                        if (Activity.sclass.getClassId().equals(cls.getClassId())) {
//                            Iterator<GraphicalElement> itge = di.getProcessRef().listAllContaineds();
//                            while (itge.hasNext()) {
//                                GraphicalElement ge = itge.next();
//                                if (ge instanceof org.semanticwb.process.model.SubProcess || ge instanceof Task) {
//                                    String urige = ge.getURI();
//                                    org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);
//
//                                    ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
//                                    actRef.setProcessActivity(act);
//                                    Activity actFin = Activity.ClassMgr.createActivity(model);
//                                    actFin.setTitle(act.getTitle());
//                                    actFin.setDocumentTemplate(dt);
////                                    actFin.setDescription(act.getDescription());
//                                    actFin.setActivityRef(actRef);
//                                    actFin.setIndex(ge.getIndex());
//                                    dsin.addDocuSectionElementInstance(actFin);
//                                    actFin.setParentSection(dst);
//                                }
//                            }
//                        }
//                        arr.add(i, dsin.getURI());
//                    } else {
//                        arr.add(i, map.get(dst.getURI()));
//                    }
//                    i++;
//                }
//            }
//        }
//        return arr;
//    }

//    public static ArrayList listDocumentSectionsForPE(DocumentTemplate dt, Map map, DocumentationInstance di, ProcessSite model, String clsuri) {
//        Iterator<DocumentSection> itdst = SWBComparator.sortSortableObject(dt.listDocumentSections());
//        ArrayList arr = new ArrayList();
//        int i = 0;
//        Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
//        while (itdsi.hasNext()) {
//            DocumentSectionInstance dsi = itdsi.next();
//            SemanticClass semcls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
//            if (!clsuri.contains(semcls.getClassId())) {
//                if (dsi.listDocuSectionElementInstances().hasNext()) {
//                    DocumentSection documentSection = dsi.getSecTypeDefinition();
//                    if (!map.containsKey(documentSection.getURI())) {
//                        arr.add(i, itdst);
//                    } else {
//                        arr.add(i, map.get(documentSection.getURI()));
//                    }
//                    i++;
//                }
//            }
//        }
//        return arr;
//    }

//    public static void verifyActivitiesOfProcess(List<GraphicalElement> list, WebSite model, DocumentSectionInstance dsi, org.semanticwb.process.model.Process process, DocumentTemplate dt) {
//
//        Iterator<GraphicalElement> itge = process.listAllContaineds();
//        while (itge.hasNext()) {
//            GraphicalElement ge = itge.next();
//            if ((ge instanceof org.semanticwb.process.model.SubProcess || ge instanceof Task)) {
//                if (!list.contains(ge)) {
//                    String urige = ge.getURI();
//                    org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);
//                    ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
//                    actRef.setProcessActivity(act);
//                    Activity actFin = Activity.ClassMgr.createActivity(model);
//                    actFin.setDocumentTemplate(dt);
//                    actFin.setTitle(act.getTitle());
////                    actFin.setDescription(act.getDescription());
//                    actFin.setActivityRef(actRef);
//                    actFin.setIndex(ge.getIndex());
//                    dsi.addDocuSectionElementInstance(actFin);
//                    actFin.setParentSection(dsi.getSecTypeDefinition());
//                }
//            }
//        }
//    }

    /**
     * Obtiene la instancia de documentación asociada al proceso especificado.
     * @param process Proceso de interés
     * @param container Contenedor de versiones de la documentación asociado al proceso.
     * @return Instancia de la documentación del proceso especificado.
     */
    public static DocumentationInstance getDocumentationInstanceByProcess(Process process, TemplateContainer container) {
        DocumentationInstance di = null;
        ProcessSite model = process.getProcessSite();
        DocumentTemplate dt = container.getActualTemplate();
        Iterator<DocumentationInstance> itdi = DocumentationInstance.ClassMgr.listDocumentationInstanceByProcessRef(process);
        if (itdi.hasNext()) {//Obtener DocumentationInstance de plantilla actual
            while (itdi.hasNext()) {
                DocumentationInstance dit = itdi.next();
                if (dit.getDocTypeDefinition() != null
                        && dt != null
                        && dit.getDocTypeDefinition().getURI().equals(dt.getURI())) {
                    di = dit;
                    Map map = getDocumentSectionInstanceMap(di);
                    Iterator<DocumentSection> itds = di.getDocTypeDefinition().listDocumentSections();
                    while (itds.hasNext()) {
                        DocumentSection ds = itds.next();
                        if (!map.containsKey(ds.getURI())) {
                            DocumentSectionInstance dsi = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
                            dsi.setSecTypeDefinition(ds);
                            dsi.setDocumentationInstance(dit);
                            di.addDocumentSectionInstance(dsi);
                            dsi.setIndex(ds.getIndex());

                            SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
                            if (FreeText.sclass.getClassId().equals(cls.getClassId())) {
                                FreeText ft = FreeText.ClassMgr.createFreeText(model);
                                ft.setText("");
                                ft.setDocumentTemplate(dt);
                                ft.setDocumentSectionInst(dsi);
                                SectionElement se = (SectionElement) ft.getSemanticObject().createGenericInstance();
                                dsi.addDocuSectionElementInstance(se);
                            }
                            if (Activity.sclass.getClassId().equals(cls.getClassId())) {
                                Iterator<GraphicalElement> itge = di.getProcessRef().listAllContaineds();
                                while (itge.hasNext()) {
                                    GraphicalElement ge = itge.next();
                                    if (ge instanceof org.semanticwb.process.model.SubProcess || ge instanceof Task) {
                                        String urige = ge.getURI();
                                        org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);
                                        ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
                                        actRef.setProcessActivity(act);
                                        Activity actFin = Activity.ClassMgr.createActivity(model);
                                        actFin.setTitle(act.getTitle());
                                        actFin.setDocumentTemplate(dt);
                                        actFin.setActivityRef(actRef);
                                        actFin.setIndex(ge.getIndex());
                                        actFin.setDocumentSectionInst(dsi);
                                        dsi.addDocuSectionElementInstance(actFin);
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }

        if (di == null) { //Crear DocumentationInstance
            di = DocumentationInstance.ClassMgr.createDocumentationInstance(process.getProcessSite());
            di.setDocTypeDefinition(dt);
            di.setProcessRef(process);

            Iterator<DocumentSection> itdsi = di.getDocTypeDefinition().listDocumentSections();
            while (itdsi.hasNext()) {
                DocumentSection ds = itdsi.next();
                DocumentSectionInstance dsi = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
                dsi.setSecTypeDefinition(ds);
                dsi.setDocumentationInstance(di);
                di.addDocumentSectionInstance(dsi);
                dsi.setIndex(ds.getIndex());

                SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
                if (FreeText.sclass.getClassId().equals(cls.getClassId())) {
                    FreeText ft = FreeText.ClassMgr.createFreeText(model);
                    ft.setText("");
                    dsi.addDocuSectionElementInstance(ft);
                    ft.setParentSection(ds);
                    ft.setDocumentTemplate(dt);
                    ft.setDocumentSectionInst(dsi);
                }
                if (Activity.sclass.getClassId().equals(cls.getClassId())) {
                    Iterator<GraphicalElement> itge = di.getProcessRef().listAllContaineds();
                    while (itge.hasNext()) {
                        GraphicalElement ge = itge.next();
                        if (ge instanceof org.semanticwb.process.model.SubProcess || ge instanceof Task) {
                            String urige = ge.getURI();
                            org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);

                            ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
                            actRef.setProcessActivity(act);
                            Activity actFin = Activity.ClassMgr.createActivity(model);
                            actFin.setTitle(act.getTitle());
                            actFin.setDocumentTemplate(dt);
                            actFin.setActivityRef(actRef);
                            actFin.setIndex(ge.getIndex());
                            dsi.addDocuSectionElementInstance(actFin);
                            actFin.setParentSection(ds);
                            actFin.setDocumentSectionInst(dsi);
                        }
                    }
                }
            }
        }
        return di;
    }

    /**
     * Actualiza la información asociada a las actividades de un proceso documentado.
     * @param process Proceso asociado a la documentación.
     * @param container Contenedor de versiones asociado al proceso.
     * @param instance Instancia de la sección de actividades en la documentación.
     */
    public static void updateActivityFromProcess(Process process, TemplateContainer container, DocumentSectionInstance instance) {
        Iterator<SectionElement> itse = SWBComparator.sortSortableObject(instance.listDocuSectionElementInstances());
        List<GraphicalElement> listPa = new ArrayList<GraphicalElement>();
        while (itse.hasNext()) {
            SectionElement se = itse.next();
            Activity act = (Activity) se.getSemanticObject().createGenericInstance();
            if (act.getActivityRef().getProcessActivity() != null) {
                act.setTitle(act.getActivityRef().getProcessActivity().getTitle());
                act.setIndex(act.getActivityRef().getProcessActivity().getIndex());
                act.setParentSection(instance.getSecTypeDefinition());
                listPa.add(act.getActivityRef().getProcessActivity());
            } else {
                se.remove();
                instance.removeDocuSectionElementInstance(se);
            }
        }
        ProcessSite model = process.getProcessSite();
        DocumentTemplate dt = container.getActualTemplate();
        Iterator<GraphicalElement> itge = process.listAllContaineds();
        while (itge.hasNext()) {
            GraphicalElement ge = itge.next();
            if ((ge instanceof SubProcess || ge instanceof Task)) {
                if (!listPa.contains(ge)) {
                    String urige = ge.getURI();
                    org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);
                    ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
                    actRef.setProcessActivity(act);
                    Activity actFin = Activity.ClassMgr.createActivity(model);
                    actFin.setDocumentTemplate(dt);
                    actFin.setTitle(act.getTitle());
                    actFin.setActivityRef(actRef);
                    actFin.setIndex(ge.getIndex());
                    actFin.setDocumentSectionInst(instance);
                    instance.addDocuSectionElementInstance(actFin);
                    actFin.setParentSection(instance.getSecTypeDefinition());
                }
            }
        }
    }
}