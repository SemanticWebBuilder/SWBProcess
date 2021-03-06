/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público ('open source'),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, todo
 * ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org.mx
 */
package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Sortable;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;

/**
 * Clase que encapsula las propiedades y funciones de un proceso.
 * 
 * @author Javier Solis
 * @author Hasdai Pacheco
 */
public class Process extends org.semanticwb.process.model.base.ProcessBase {
	private static final Logger log = SWBUtils.getLogger(Process.class);

	// Bloque estático para registrar un observador cuando se modifica la propiedad
	// parentWebPage
	static {
		swp_parentWebPage.registerObserver(new SemanticObserver() {
			@Override
			public void notify(SemanticObject obj, Object prop, String lang, String action) {
				if (obj.instanceOf(Process.sclass)) {
					Process process = (Process) obj.createGenericInstance();
					process.getProcessWebPage().removeAllVirtualParent();
					if (action != null && action.equals("SET") && process.getParentWebPage() != null) {
						process.getProcessWebPage().addVirtualParent(process.getParentWebPage());
					}
				}
			}
		});
	}

	/**
	 * Constructor.
	 * 
	 * @param base
	 */
	public Process(org.semanticwb.platform.SemanticObject base) {
		super(base);
		getProcessWebPage(); // Crea pagina web de proceso

	}

	/**
	 * Crea una instancia del proceso.
	 * 
	 * @return
	 */
	public ProcessInstance createInstance() {
		ProcessInstance inst = null;
		inst = this.getProcessSite().createProcessInstance();
		inst.setProcessType(this);
		inst.setStatus(Instance.STATUS_INIT);

		return inst;
	}

	/**
	 * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware
	 * Globales)
	 * 
	 * @return
	 */
	@Override
	public List<ItemAware> listRelatedItemAware() {
		ArrayList<ItemAware> arr = new ArrayList();
		Iterator<GraphicalElement> it = listContaineds();
		while (it.hasNext()) {
			GraphicalElement graphicalElement = it.next();
			if (graphicalElement instanceof ItemAware) {
				ItemAware item = (ItemAware) graphicalElement;

				if (!item.listInputConnectionObjects().hasNext() && !item.listOutputConnectionObjects().hasNext()) {
					arr.add(item);
				}
			}
		}
		return arr;
	}

	/**
	 * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware
	 * Globales)
	 * 
	 * @return
	 */
	@Override
	public List<ItemAware> listHerarquicalRelatedItemAware() {
		return listRelatedItemAware();
	}

	/**
	 * Obtiene una lista de todos los nodos contenidos en un proceso.
	 * 
	 * @return
	 */
	public Iterator<GraphicalElement> listAllContaineds() {
		ArrayList<GraphicalElement> arr = new ArrayList<>();
		Iterator<GraphicalElement> it = listContaineds();
		while (it.hasNext()) {
			GraphicalElement gElement = it.next();
			arr.add(gElement);
			if (gElement instanceof SubProcess) {
				Iterator<GraphicalElement> it2 = ((SubProcess) (gElement)).listAllContaineds();
				while (it2.hasNext()) {
					GraphicalElement gElement2 = it2.next();
					arr.add(gElement2);
				}
			}
		}
		return arr.iterator();
	}

	@Override
	public WrapperProcessWebPage getProcessWebPage() {
		WrapperProcessWebPage wp = super.getProcessWebPage();
		if (wp == null) {
			wp = WrapperProcessWebPage.ClassMgr.createWrapperProcessWebPage(getId() + "_swp", getProcessSite());
			setProcessWebPage(wp);
			wp.setActive(true);
		}
		return wp;
	}

	@Override
	public boolean isValid() {
		boolean ret = super.isValid();
		if (ret) {
			if (null == getProcessGroup())
				return false; // No pueden existir procesos fuera de un grupo de procesos

			ret = getProcessGroup().isValid() && getProcessGroup().isActive() && !getProcessGroup().isDeleted();
		}
		return ret;
	}

	/**
	 * Verifica si el usuario puede acceder al proceso.
	 * 
	 * @param user
	 *            Usuario.
	 * @return true si el usuario tiene permisos para ver el proceso, false en otro
	 *         caso.
	 */
	public boolean haveAccess(User user) {
		return (isValid() && user.haveAccess(this));
	}

	/**
	 * Verifica si el usuario puede instanciar el proceso.
	 * 
	 * @param user
	 *            Usuario.
	 * @return true si el usuario puede instanciar el proceso, false en otro caso.
	 */
	public boolean canInstantiate(User user) {
		boolean ret = false;
		boolean hasStart = false;
		Iterator<GraphicalElement> elements = listContaineds();
		while (elements.hasNext()) {
			GraphicalElement ge = elements.next();
			if (ge instanceof StartEvent) {
				hasStart = true;
				ret = user.haveAccess(ge);
				break;
			}
		}
		return hasStart && ret;
	}

	@Override
	public String serialize(String format) {
		String ret = "";
		if ("JSON".equalsIgnoreCase(format)) {
			JSONObject model = getProcessJSON();
			try {
				if (null != model)
					ret = model.toString(2);
			} catch (JSONException jse) {
				log.error("Ha ocurrido un error al serializar el modelo", jse);
			}
		}
		return ret;
	}

	/**
	 * Obtiene la representación en formato JSON de un subproceso.
	 * 
	 * @param subprocess
	 *            SubProceso
	 * @param nodes
	 *            Lista de nodos del subproceso
	 */
	public void getSubProcessJSON(org.semanticwb.process.model.Containerable subprocess, JSONArray nodes) {
		JSONObject ele = null;
		JSONObject coele = null;
		try {
			Iterator<GraphicalElement> itFo = subprocess.listContaineds();
			while (itFo.hasNext()) {
				GraphicalElement obj = itFo.next();
				ele = new JSONObject();
				nodes.put(ele);
				ele.put(JSONProperties.PROP_CLASS, obj.getSemanticObject().getSemanticClass().getClassCodeName());
				ele.put(JSONProperties.PROP_TITLE, obj.getTitle());
				ele.put(JSONProperties.PROP_DESCRIPTION, obj.getDescription());
				ele.put(JSONProperties.PROP_URI, obj.getURI());
				ele.put(JSONProperties.PROP_X, obj.getX());
				ele.put(JSONProperties.PROP_Y, obj.getY());
				ele.put(JSONProperties.PROP_W, obj.getWidth());
				ele.put(JSONProperties.PROP_H, obj.getHeight());
				if (obj.getContainer() != null) {
					ele.put(JSONProperties.PROP_CONTAINER, obj.getContainer().getURI());
				} else {
					ele.put(JSONProperties.PROP_CONTAINER, "");
				}
				if (obj.getParent() != null) {
					ele.put(JSONProperties.PROP_PARENT, obj.getParent().getURI());
				} else {
					ele.put(JSONProperties.PROP_PARENT, "");
				}

				if (obj.getLabelSize() != 0) {
					ele.put(JSONProperties.PROP_LABELSIZE, obj.getLabelSize());
				} else {
					ele.put(JSONProperties.PROP_LABELSIZE, 10);
				}

				if (obj instanceof Sortable) {
					Sortable sorble = (Sortable) obj;
					ele.put(JSONProperties.PROP_INDEX, sorble.getIndex());
				}

				if (obj instanceof ActivityConfable) {
					ActivityConfable tsk = (ActivityConfable) obj;
					if (tsk.getLoopCharacteristics() != null) {
						LoopCharacteristics loopC = tsk.getLoopCharacteristics();
						if (loopC instanceof MultiInstanceLoopCharacteristics) {
							ele.put(JSONProperties.PROP_ISMULTIINSTANCE, true);
						} else {
							ele.put(JSONProperties.PROP_ISMULTIINSTANCE, false);
						}

						if (loopC instanceof StandarLoopCharacteristics) {
							ele.put(JSONProperties.PROP_ISLOOP, true);
						} else {
							ele.put(JSONProperties.PROP_ISLOOP, false);
						}
					}
					ele.put(JSONProperties.PROP_ISCOMPENSATION,
							Boolean.toString(tsk.isForCompensation()));
				}

				if (obj instanceof Collectionable) {
					Collectionable colble = (Collectionable) obj;
					if (colble.isCollection()) {
						ele.put(JSONProperties.PROP_ISCOLLECTION, true);
					} else {
						ele.put(JSONProperties.PROP_ISCOLLECTION, false);
					}
				}

				if (obj instanceof Lane) {
					ele.put("lindex", ((Lane) obj).getLindex());
				}

				Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
				while (it.hasNext()) {
					ConnectionObject connectionObject = it.next();
					
					// All connection objects must have source and target to be valid
					if (null != connectionObject.getSource() && null != connectionObject.getTarget()) {
						coele = new JSONObject();
						nodes.put(coele);
						coele.put(JSONProperties.PROP_CLASS, connectionObject.getSemanticObject().getSemanticClass().getClassCodeName());
						coele.put(JSONProperties.PROP_URI, connectionObject.getURI());
						coele.put(JSONProperties.PROP_START, connectionObject.getSource().getURI());
						coele.put(JSONProperties.PROP_END, connectionObject.getTarget().getURI());
						coele.put(JSONProperties.PROP_TITLE, connectionObject.getTitle());
						coele.put(JSONProperties.PROP_CONNPOINTS, connectionObject.getConnectionPoints());
					} else {
						connectionObject.remove();
					}
				}

				if (obj instanceof Containerable) {
					getSubProcessJSON((Containerable) obj, nodes);
				}
			}
		} catch (Exception e) {
			log.error("Error al general el JSON del Modelo.....getSubProcessJSON(" + subprocess.getId() + ", uri:" + subprocess.getURI() + ")", e);
		}
	}

	/**
	 * Obtiene la representación en formato JSON del proceso.
	 * 
	 * @return Objeto con la representación del proceso.
	 */
	private JSONObject getProcessJSON() {
		JSONObject ret = null;
		JSONArray nodes = null;
		JSONObject ele = null;
		JSONObject coele = null;

		try {
			ret = new JSONObject();
			ret.put(JSONProperties.PROP_URI, getURI());
			ret.put(JSONProperties.PROP_TITLE, getTitle());
			ret.put(JSONProperties.PROP_DESCRIPTION, getDescription());
			ret.put(JSONProperties.PROP_CLASS, getSemanticObject().getSemanticClass().getClassCodeName());
			nodes = new JSONArray();
			ret.putOpt("nodes", nodes);

			Iterator<GraphicalElement> itFo = listContaineds();
			while (itFo.hasNext()) {
				GraphicalElement obj = itFo.next();
				ele = new JSONObject();
				nodes.put(ele);
				ele.put(JSONProperties.PROP_CLASS, obj.getSemanticObject().getSemanticClass().getClassCodeName());
				ele.put(JSONProperties.PROP_TITLE, obj.getTitle());
				ele.put(JSONProperties.PROP_DESCRIPTION, obj.getDescription());
				ele.put(JSONProperties.PROP_URI, obj.getURI());
				ele.put(JSONProperties.PROP_X, obj.getX());
				ele.put(JSONProperties.PROP_Y, obj.getY());
				ele.put(JSONProperties.PROP_W, obj.getWidth());
				ele.put(JSONProperties.PROP_H, obj.getHeight());
				if (obj.getContainer() != null) {
					ele.put(JSONProperties.PROP_CONTAINER, obj.getContainer().getURI());
				} else {
					ele.put(JSONProperties.PROP_CONTAINER, "");
				}
				if (obj.getParent() != null) {
					ele.put(JSONProperties.PROP_PARENT, obj.getParent().getURI());
				} else {
					ele.put(JSONProperties.PROP_PARENT, "");
				}

				if (obj.getLabelSize() != 0) {
					ele.put(JSONProperties.PROP_LABELSIZE, obj.getLabelSize());
				} else {
					ele.put(JSONProperties.PROP_LABELSIZE, 10);
				}

				if (obj instanceof Sortable) {
					Sortable sorble = (Sortable) obj;
					ele.put(JSONProperties.PROP_INDEX, sorble.getIndex());
				}

				if (obj instanceof IntermediateCatchEvent) {
					IntermediateCatchEvent ice = (IntermediateCatchEvent) obj;
					ele.put(JSONProperties.PROP_ISINTERRUPTING, ice.isInterruptor());
				}

				if (obj instanceof ActivityConfable) {
					ActivityConfable tsk = (ActivityConfable) obj;
					if (tsk.getLoopCharacteristics() != null) {
						LoopCharacteristics loopC = tsk.getLoopCharacteristics();
						if (loopC instanceof MultiInstanceLoopCharacteristics) {
							ele.put(JSONProperties.PROP_ISMULTIINSTANCE, true);
						} else {
							ele.put(JSONProperties.PROP_ISMULTIINSTANCE, false);
						}

						if (loopC instanceof StandarLoopCharacteristics) {
							ele.put(JSONProperties.PROP_ISLOOP, true);
						} else {
							ele.put(JSONProperties.PROP_ISLOOP, false);
						}
					}
					ele.put(JSONProperties.PROP_ISCOMPENSATION, Boolean.toString(tsk.isForCompensation()));
				}

				if (obj instanceof Collectionable) {
					Collectionable colble = (Collectionable) obj;
					if (colble.isCollection()) {
						ele.put(JSONProperties.PROP_ISCOLLECTION, true);
					} else {
						ele.put(JSONProperties.PROP_ISCOLLECTION, false);
					}
				}

				if (obj instanceof Lane) {
					ele.put("lindex", ((Lane) obj).getLindex());
				}
				Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
				while (it.hasNext()) {
					ConnectionObject connectionObject = it.next();
					
					// All connection objects must have source and target to be valid
					if (null != connectionObject.getSource() && null != connectionObject.getTarget()) {
						coele = new JSONObject();
						nodes.put(coele);
						coele.put(JSONProperties.PROP_CLASS, connectionObject.getSemanticObject().getSemanticClass().getClassCodeName());
						coele.put(JSONProperties.PROP_URI, connectionObject.getURI());
						coele.put(JSONProperties.PROP_START, connectionObject.getSource().getURI());
						coele.put(JSONProperties.PROP_END, connectionObject.getTarget().getURI());
						coele.put(JSONProperties.PROP_TITLE, connectionObject.getTitle());
						coele.put(JSONProperties.PROP_CONNPOINTS, connectionObject.getConnectionPoints());
					} else {
						connectionObject.remove();
					}
				}

				if (obj instanceof Containerable) {
					getSubProcessJSON((Containerable) obj, nodes);
				}
			}

		} catch (Exception e) {
			ret = null;
			log.error(
					"Error al general el JSON del Modelo.....getProcessJSON(" + getTitle() + ", uri:" + getURI() + ")", e);
		}
		return ret;
	}

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
    }
}
