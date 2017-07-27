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
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org.mx
 */
package org.semanticwb.process.kpi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.utils.DateInterval;
import org.semanticwb.process.utils.Restriction;
import org.semanticwb.process.utils.TimeInterval;

/**
 *
 * @author Sergio Téllez
 */
public class CaseCountSys {

	ArrayList<ProcessInstance> instances = null;
	ArrayList restrictions = null;

	public static final String USER = "user";
	public static final String ROLE = "role";
	public static final String GROUP = "group";
	public static final String ALERTED = "alerted";
	public static final String STATUS = "status";
	public static final String PROCESS = "process";
	public static final String ARTIFACT = "artifact";
	public static final String RESPONSE = "response";

	public CaseCountSys() {
		instances = new ArrayList<>();
		restrictions = new ArrayList();
	}

	public int totalProcessInstance() {
		this.filterProcessInstance();
		return instances.size();
	}

	public void addRestriction(Restriction restriction) {
		restrictions.add(restriction);
	}

	public void addRestrictions(List restrictions) {
		this.restrictions = (ArrayList)restrictions;
	}

	public void removeRestriction(Restriction restriction) {
		this.restrictions.remove(restriction);
	}

	public List<ProcessInstance> listProcessInstance() {
		filterProcessInstance();
		return instances;
	}

	private ArrayList<ProcessInstance> listProcessInstanceByStatus(List<ProcessInstance> instances, String status) {
		return KProcessInstance.listProcessInstancesByStatus(instances, status);
	}

	private ArrayList<ProcessInstance> listProcessInstanceByUser(List<ProcessInstance> instances, String login) {
		User user = null;
		Iterator<UserRepository> listur = SWBContext.listUserRepositories();
		while (listur.hasNext()) {
			UserRepository ur = listur.next();
			if (null != ur.getUserByLogin(login))
				user = ur.getUserByLogin(login);
		}
		if (null != user)
			return (ArrayList<ProcessInstance>)KProcessInstance.listProcessInstancesByUser(instances, user);
		else
			return new ArrayList<>();
	}

	private ArrayList<ProcessInstance> listProcessInstanceByCreatedDate(ArrayList<ProcessInstance> instances, DateInterval idate) {
		Date dateInit = null;
		Date dateFinal = null;
		Calendar calendarInit = null;
		Calendar calendarFinal = null;
		ArrayList<ProcessInstance> onInterval = new ArrayList<>();
		if (!"".equals(idate.getDateInit())) {
			calendarInit = Calendar.getInstance();
			calendarInit.set(idate.getYearDateInit(), idate.getMonthDateInit() - 1, idate.getDayDateInit());
		}
		if (!"".equals(idate.getDateFinal())) {
			calendarFinal = Calendar.getInstance();
			calendarFinal.set(idate.getYearDateFinal(), idate.getMonthDateFinal() - 1, idate.getDayDateFinal());
		}
		if (null != calendarInit)
			dateInit = calendarInit.getTime();
		if (null != calendarFinal)
			dateFinal = calendarFinal.getTime();
		if (null != dateInit) {
			Iterator<ProcessInstance> itpinst = instances.iterator();
			while (itpinst.hasNext()) {
				ProcessInstance pinst = itpinst.next();
				if (pinst.getCreated().after(dateInit) || idate.equalsToDateInit(pinst.getCreated())) {
					if (null != dateFinal) {
						if (pinst.getCreated().before(dateFinal) || idate.equalsToDateFinal(pinst.getCreated()))
							onInterval.add(pinst);
					} else
						onInterval.add(pinst);
				}
			}
		}
		return onInterval;
	}

	private ArrayList<ProcessInstance> listProcessInstanceByResponseTime(ArrayList<ProcessInstance> instances, TimeInterval iTime) {
		ArrayList onInterval = new ArrayList();
		Iterator<ProcessInstance> itpinst = instances.iterator();
		while (itpinst.hasNext()) {
			ProcessInstance pinst = itpinst.next();
			if (TimeInterval.match(iTime.getUnit(), iTime.getOperator(), iTime.getTime(),
					CaseResponseTime.getResponseTimeInstance(pinst)))
				onInterval.add(pinst);
		}
		return onInterval;
	}

	private ArrayList<ProcessInstance> listProcessInstanceByEndedDate(ArrayList<ProcessInstance> instances, DateInterval idate) {
		Date dateInit = null;
		Date dateFinal = null;
		Calendar calendarInit = null;
		Calendar calendarFinal = null;
		ArrayList onInterval = new ArrayList();
		if (!"".equals(idate.getDateInit())) {
			calendarInit = Calendar.getInstance();
			calendarInit.set(idate.getYearDateInit(), idate.getMonthDateInit() - 1, idate.getDayDateInit());
		}
		if (!"".equals(idate.getDateFinal())) {
			calendarFinal = Calendar.getInstance();
			calendarFinal.set(idate.getYearDateFinal(), idate.getMonthDateFinal() - 1, idate.getDayDateFinal());
		}
		if (null != calendarInit)
			dateInit = calendarInit.getTime();
		if (null != calendarFinal)
			dateFinal = calendarFinal.getTime();
		if (null != dateInit) {
			Iterator<ProcessInstance> itpinst = instances.iterator();
			while (itpinst.hasNext()) {
				ProcessInstance pinst = itpinst.next();
				if (null != pinst.getEnded()
						&& (pinst.getEnded().after(dateInit) || idate.equalsToDateInit(pinst.getEnded()))) {
					if (null != dateFinal) {
						if (pinst.getEnded().before(dateFinal) || idate.equalsToDateFinal(pinst.getEnded()))
							onInterval.add(pinst);
					} else
						onInterval.add(pinst);
				}
			}
		}
		return onInterval;
	}

	private ArrayList listProcessInstanceByArtifact(ArrayList instances, ArrayList<Restriction> restrictions) {
		Iterator<Restriction> it = restrictions.iterator();
		ArrayList<ProcessInstance> ret = new ArrayList<>();
		while (it.hasNext()) {
			ret = filterArtifactsProcessInstance(instances, it.next(), new ArrayList());
		}
		return ret;
	}

	private ArrayList<ProcessInstance> filterArtifactsProcessInstance(ArrayList<ProcessInstance> instances, Restriction restriction,
			ArrayList filterinstances) {
		Iterator<ProcessInstance> itpinst = instances.iterator();
		while (itpinst.hasNext()) {
			filterinstances = KProcessInstance.filterArtifactsObjects(itpinst.next(), restriction, filterinstances);
		}
		return filterinstances;
	}

	private ArrayList listProcessInstanceByRole(ArrayList instances, String role) {
		return instances;
	}

	private ArrayList listProcessInstanceByGroup(ArrayList instances, String group) {
		return instances;
	}

	private ArrayList listAlertedProcessInstance(ArrayList instances) {
		return instances;
	}

	public void clear() {
		this.instances.clear();
		this.restrictions.clear();
	}

	private void filterProcessInstance() {
		instances = KProcessInstance.listProcessInstances();
		if (!restrictions.isEmpty()) {
			Iterator it = restrictions.iterator();
			while (it.hasNext()) {
				Restriction restriction = (Restriction) it.next();
				if (PROCESS.equalsIgnoreCase(restriction.getProperty()))
					this.instances = KProcessInstance.listProcessInstancesByProcess(instances,
							(String) restriction.getCriteria());
				else if (STATUS.equalsIgnoreCase(restriction.getProperty()))
					this.instances = listProcessInstanceByStatus(this.instances, (String) restriction.getCriteria());
				else if (USER.equalsIgnoreCase(restriction.getProperty()))
					this.instances = listProcessInstanceByUser(this.instances, (String) restriction.getCriteria());
				else if (String.valueOf(Instance.STATUS_INIT).equalsIgnoreCase(restriction.getProperty()))
					this.instances = listProcessInstanceByCreatedDate(this.instances,
							(DateInterval) restriction.getCriteria());
				else if (String.valueOf(Instance.STATUS_CLOSED).equalsIgnoreCase(restriction.getProperty()))
					this.instances = listProcessInstanceByEndedDate(this.instances,
							(DateInterval) restriction.getCriteria());
				else if (RESPONSE.equalsIgnoreCase(restriction.getProperty()))
					this.instances = listProcessInstanceByResponseTime(this.instances,
							(TimeInterval) restriction.getCriteria());
				else if (ARTIFACT.equalsIgnoreCase(restriction.getProperty()))
					this.instances = listProcessInstanceByArtifact(this.instances,
							(ArrayList<Restriction>) restriction.getCriteria());
				else if (ROLE.equalsIgnoreCase(restriction.getProperty()))
					this.instances = listProcessInstanceByRole(this.instances, (String) restriction.getCriteria());
				else if (GROUP.equalsIgnoreCase(restriction.getProperty()))
					this.instances = listProcessInstanceByGroup(this.instances, (String) restriction.getCriteria());
				else if (ALERTED.equalsIgnoreCase(restriction.getProperty())
						&& "true".equalsIgnoreCase((String) restriction.getCriteria()))
					this.instances = listAlertedProcessInstance(this.instances);
			}
		}
	}
}
