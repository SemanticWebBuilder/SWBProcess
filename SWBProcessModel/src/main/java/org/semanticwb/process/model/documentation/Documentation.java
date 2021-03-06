package org.semanticwb.process.model.documentation;


import org.semanticwb.SWBPortal;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.process.model.Process;

import java.text.NumberFormat;
import java.util.Iterator;

public class Documentation extends org.semanticwb.process.model.documentation.base.DocumentationBase
{
    private static final NumberFormat numf = NumberFormat.getNumberInstance();
    public Documentation(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }


    /**
     * Obtiene la instancia de la documentación asociada al proceso.
     * @return Instancia de documentación asociada al proceso.
     */
    public DocumentationInstance getDocumentationInstance() {
        Iterator<DocumentationInstance> itdi = DocumentationInstance.ClassMgr.listDocumentationInstanceByProcessRef(getProcess(), getProcess().getProcessSite());
        if (itdi != null && itdi.hasNext()) {
            return itdi.next();
        }
        return null;
    }

    /**
     * Obtiene el valor de la siguiente versión, de acuerdo al valor proporcionado.
     * @param prevValue Valor de versión tomado como punto de partida.
     * @return Número de la siguiente versión.
     */
    public static String getNextVersionValue(String prevValue) {
        String prev = prevValue;
        if (null == prevValue || prevValue.isEmpty()) {
            return "1.0";
        }

        numf.setMaximumFractionDigits(1);

        String sver = prev;
        double f = Double.parseDouble(sver);
        f = f + 0.10D;

        String sfver = numf.format(f);
        if (!sfver.contains(".")) {
            sfver = "" + (float) f;
        }
        sver = sfver;
        return sver;
    }

    /**
     * Obtiene la ruta de trabajo del objeto de documentación
     * @return
     */
    public String getDocWorkPath() {
        return SWBPortal.getWorkPath()+getProcess().getWorkPath()+"/docs/"+getId()+"/";
    }

    /**
     * Obtiene la versión actual de la documentación de un proceso.
     *
     * @param process Proceso del cual se requiere obtener la versión actual de
     * la documentación.
     * @return Versión actual de la documentación o null si esta no existe.
     */
    public static Documentation getActualDocumentationVersion(Process process) {
        Iterator<Documentation> itdoc = SWBComparator.sortByCreated(ClassMgr.listDocumentationByProcess(process), true);
        while (itdoc.hasNext()) {
            Documentation doc = itdoc.next();
            if (doc.isActualVersion()) {
                return doc;
            }
        }
        return null;
    }
}
