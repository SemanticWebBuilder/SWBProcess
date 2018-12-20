package org.semanticwb.process.model.documentation;


import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.process.model.*;
import org.semanticwb.process.model.Process;

import java.util.*;

public class DocumentationInstance extends org.semanticwb.process.model.documentation.base.DocumentationInstanceBase
{
    public DocumentationInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Crea una ruta temporal para las descargas de documentación.
     * @return Ruta a la carpeta de trabajo de la instancia, con un modificador único para archivos temporales.
     */
    public String getDownloadTempPath() {
        String uid = UUID.randomUUID().toString();
        return SWBPortal.getWorkPath()+getProcessRef().getWorkPath()+"/docs/download/"+uid+"/";
    }

    /**
     * Actualiza la información asociada a las actividades de un proceso documentado.
     * @param process Proceso asociado a la documentación.
     * @param container Contenedor de versiones asociado al proceso.
     * @param instance Instancia de la sección de actividades en la documentación.
     */
    public static void updateActivityFromProcess(Process process, TemplateContainer container, DocumentSectionInstance instance) {
        Iterator<SectionElement> itse = SWBComparator.sortSortableObject(instance.listDocuSectionElementInstances());
        List<GraphicalElement> listPa = new ArrayList<>();
        while (itse.hasNext()) {
            SectionElement se = itse.next();
            Activity act = (Activity) se.getSemanticObject().createGenericInstance();
            if (act.getActivityRef().getProcessActivity() != null) {
                act.setTitle(act.getActivityRef().getProcessActivity().getTitle());
                //act.setDescription(act.getActivityRef().getProcessActivity().getDescription());
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
                                    if (ge instanceof org.semanticwb.process.model.SubProcess || ge instanceof Task) {//TODO:Revisar esto para permitir editar otras actividades
                                        String urige = ge.getURI();
                                        org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);
                                        ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
                                        actRef.setProcessActivity(act);
                                        Activity actFin = Activity.ClassMgr.createActivity(model);
                                        actFin.setTitle(act.getTitle());
                                        actFin.setDescription(act.getDescription());
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
                        if (ge instanceof org.semanticwb.process.model.SubProcess || ge instanceof Task) {//TODO:Revisar esto para permitir editar otras actividades
                            String urige = ge.getURI();
                            org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);

                            ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
                            actRef.setProcessActivity(act);
                            Activity actFin = Activity.ClassMgr.createActivity(model);
                            actFin.setTitle(act.getTitle());
                            actFin.setDescription(act.getDescription());
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
}
