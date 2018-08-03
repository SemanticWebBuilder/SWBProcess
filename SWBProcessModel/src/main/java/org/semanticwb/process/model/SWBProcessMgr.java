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
package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;

import com.hp.hpl.jena.sparql.pfunction.library.assign;

/**
 *
 * @author javier.solis
 */
public class SWBProcessMgr {
	private static Logger LOG = SWBUtils.getLogger(SWBProcessMgr.class);
	private static ConcurrentHashMap<Thread, Thread> linkedThreads = new ConcurrentHashMap<>();
	private static ConcurrentLinkedQueue<User> candidates = new ConcurrentLinkedQueue<>();

	public static final int ASSIGNATION_RANDOM = 1;
	public static final int ASSIGNATION_DISTRIBUTED = 3;
	public static final int ASSIGNATION_PROCESSCREATOR = 4;
	public static final int ASSIGNATION_TASKCREATOR = 5;
	
	private SWBProcessMgr() {
		throw new IllegalStateException("Static class not instantiable");
	}

	public static GenericIterator<ProcessInstance> getProcessInstanceWithStatus(ProcessSite site, int status) {
		GenericIterator<ProcessInstance> it = new org.semanticwb.model.GenericIterator<>(
				site.getSemanticModel().listSubjects(ProcessInstance.swp_processStatus, status));
		return it;
	}

	public static GenericIterator<FlowNodeInstance> getFlowNodeInstanceWithStatus(ProcessSite site, int status) {
		GenericIterator<FlowNodeInstance> it = new org.semanticwb.model.GenericIterator<>(
				site.getSemanticModel().listSubjects(FlowNodeInstance.swp_status, status));
		return it;
	}

	/**
	 * Obtiene una lista de las instancias de tareas de usuario activas.
	 * 
	 * @return Lista de las instancias de tareas de usuario activas.
	 */
	public static ArrayList<FlowNodeInstance> getActiveUserTaskInstances(ProcessSite site) {
		return getUserTaskInstancesWithStatus(site, Instance.STATUS_PROCESSING, null);
	}

	/**
	 * Obtiene una lista de las instancias de tareas de usuario activas para un
	 * proceso determinado.
	 * 
	 * @param process
	 *            Proceso.
	 * @return Lista de las instancias de tareas de usuario activas.
	 */
	public static ArrayList<FlowNodeInstance> getActiveUserTaskInstances(ProcessSite site, Process process) {
		return getUserTaskInstancesWithStatus(site, Instance.STATUS_PROCESSING, process);
	}

	/**
	 * Obtiene una lista de las instancias de tareas de usuario con el estado
	 * proporcionado.
	 * 
	 * @param site
	 *            Sitio Web de procesos (modelo) del cual se obtendrán las
	 *            instancias.
	 * @param status
	 *            Estado de las instancias.
	 * @param process
	 *            Proceso para filtrado de instancias.
	 * @return Lista de las instancias de tareas de usuario con el estado
	 *         proporcionado.
	 */
	public static ArrayList<FlowNodeInstance> getUserTaskInstancesWithStatus(ProcessSite site, int status,
			Process process) {
		ArrayList<FlowNodeInstance> instances = new ArrayList<>();

		Iterator<FlowNodeInstance> it = getFlowNodeInstanceWithStatus(site, status);
		while (it.hasNext()) {
			FlowNodeInstance flowNodeInstance = it.next();
			if (flowNodeInstance.getFlowNodeType() instanceof UserTask) {
				if (process == null || flowNodeInstance.getProcessInstance().getProcessType().equals(process)) {
					instances.add(flowNodeInstance);
				}
			}
		}
		return instances;
	}

	public static List<ProcessInstance> getActiveProcessInstance(ProcessSite site, Process process) {
		ArrayList<ProcessInstance> ret = new ArrayList<>();
		Iterator<ProcessInstance> it = getProcessInstanceWithStatus(site, ProcessInstance.STATUS_PROCESSING);
		while (it.hasNext()) {
			ProcessInstance processInstance = it.next();
			if (processInstance.getProcessType().equals(process)) {
				ret.add(processInstance);
			}
		}
		return ret;
	}

	/**
	 * Crea procesos y espera a que termine de crearlo y este en la primera tarea de
	 * usuario o termine el proceso.
	 * 
	 * @param process
	 * @param user
	 * @return
	 */
	public static ProcessInstance createSynchProcessInstance(Process process, User user) {
		ProcessInstance ret = null;
		addLinkedThread(Thread.currentThread());
		try {
			ret = createProcessInstance(process, user);
		} finally {
			removeLinkedThread(Thread.currentThread());
		}
		return ret;
	}

	/**
	 * Crea proceso en un thread independiente
	 * 
	 * @param process
	 * @param user
	 * @return
	 */
	public static ProcessInstance createProcessInstance(Process process, User user) {
		ProcessInstance pinst = process.createInstance();
		UserGroup usrgrp = user.getUserGroup();
		if (usrgrp != null)
			pinst.setOwnerUserGroup(usrgrp);
		pinst.setOwnerproperties(user);
		pinst.start(user);
		return pinst;
	}

	public static List<FlowNodeInstance> getActiveUserTaskInstances(ProcessSite site, User user) {
		ArrayList ret = new ArrayList();
		// Obtener todos los nodos de flujo activos
		Iterator<FlowNodeInstance> it = getFlowNodeInstanceWithStatus(site, Instance.STATUS_PROCESSING);

		while (it.hasNext()) {
			FlowNodeInstance actins = it.next();
			FlowNode type = actins.getFlowNodeType();
			if (type instanceof UserTask) {
				Process p = type.getProcess();
				if (p.isValid() && actins.haveAccess(user)) {
					ret.add(actins);
				}
			}
		}
		return ret;
	}

	/**
	 * Obtiene una lista de las instancias de tareas de usuario relacionadas con la
	 * instancia de proceso proporcionada.
	 * 
	 * @param pint
	 *            Instancia de proceso.
	 * @param user
	 *            Usuario.
	 * @return Lista de las instancias de tareas de usuario relacionadas con la
	 *         instancia de proceso proporcionada
	 */
	public static List<FlowNodeInstance> getActiveUserTaskInstances(ProcessInstance pint, User user) {
		ArrayList<FlowNodeInstance> ret = new ArrayList<>();
		// Obtener todos los nodos de flujo activos del proceso
		Iterator<FlowNodeInstance> it = getActiveUserTaskInstances(pint.getProcessSite(), pint.getProcessType())
				.iterator();

		while (it.hasNext()) {
			FlowNodeInstance actins = it.next();
			if (actins.haveAccess(user) && actins.getProcessInstance().equals(pint)) {
				ret.add(actins);
			}
		}
		return ret;
	}

	public static Process getProcess(WebPage page) {
		Process ret = null;
		if (page != null) {
			if (page instanceof WrapperProcessWebPage) {
				ret = ((WrapperProcessWebPage) page).getProcess();
			} else {
				ret = getProcess(page.getParent());
			}
		}
		return ret;
	}

	private static boolean haveAccess(FlowNode node, User user) {
		boolean add = false;
		if (user.isValid() && user.haveAccess(node)) {
			add = true;
			GraphicalElement parent = node.getParent();
			if (parent != null && parent instanceof Lane) {
				Lane lane = (Lane) parent;
				if (!user.haveAccess(lane)) {
					add = false;
				}
			}
		}
		return add;
	}

	public static List<User> getUsers(FlowNodeInstance instance) {
		ArrayList<User> arr = new ArrayList<>();
		FlowNode node = instance.getFlowNodeType();
		Iterator<User> it = null;
		boolean groupFilter = node.getProcess().isFilterByOwnerUserGroup();

		if (groupFilter) {
			UserGroup ug = instance.getProcessInstance().getOwnerUserGroup();
			if (ug != null) {
				it = ug.listUsers();
			}
		} else {
			it = instance.getProcessSite().getUserRepository().listUsers();
		}

		if (null != it) {
			while (it.hasNext()) {
				User user = it.next();
				if (user.isValid() && haveAccess(node, user)) {
					arr.add(user);
				}
			}
		}
		return arr;
	}

	/**
	 * Asigna una instancia de tarea de usuario a un usuario de acuerdo a las reglas
	 * de asignación.
	 * 
	 * @param instance
	 *            Instancia de tarea de usuario.
	 * @param user
	 *            Usuario para la asignación.
	 * @return true si la tarea se asignó a algún usuario, false en otro caso.
	 */
	public static boolean assignUserTaskInstance(FlowNodeInstance instance, User user) {
		UserTask task = (UserTask) instance.getFlowNodeType();
		boolean success = false;

		//Fill candidates list
		if (candidates.isEmpty()) {
			Iterator<User> itusr = SWBProcessMgr.getUsers(instance).iterator();
			while (itusr.hasNext()) {
				User next = itusr.next();
				candidates.add(next);
			}
		}
		
		//Check rule
		if (instance.getAssignedto() == null) {
			User candidate = null;
			switch (task.getResourceAssignationRule()) {
				case ASSIGNATION_TASKCREATOR: { //Usuario creador de la tarea
					candidate = user;
					break;
				}
				case ASSIGNATION_PROCESSCREATOR: {//Usuario creador del proceso
					candidate = instance.getProcessInstance().getCreator();
					break;
				}
				case ASSIGNATION_DISTRIBUTED: { //Asignación distribuida
					if (!candidates.isEmpty()) {
						candidate = candidates.remove();
					}
					break;
				}
				default: { //De momento todos son aleatorios
					//TODO: Revisar el caso en que no hay candidatos pero la tarea está restringida por rol
					List<User> users = SWBProcessMgr.getUsers(instance);
					if (!users.isEmpty()) {
						candidate = users.get(new Random().nextInt(users.size()));
					}
				}
			}
			
			instance.setAssignedto(candidate);
			instance.setAssigned(new Date());
			success = true;
		}
		
		return success;
	}

	static Thread addLinkedThread(Thread thread) {
		linkedThreads.put(thread, thread);
		return thread;
	}

	static Thread removeLinkedThread(Thread thread) {
		return linkedThreads.remove(thread);
	}

	static boolean hasLinkedThread(Thread thread) {
		return linkedThreads.contains(thread);
	}
}
