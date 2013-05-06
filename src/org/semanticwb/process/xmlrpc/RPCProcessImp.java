/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.xmlrpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.StartEvent;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class RPCProcessImp extends XmlRpcObject implements RPCProcess
{

    @Override
    public void closeProcessInstance(String APIKey, String UserID, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception
    {
        String act = ProcessInstance.ACTION_ACCEPT;
        int stat = ProcessInstance.STATUS_CLOSED;
        
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        ProcessInstance pi = ProcessInstance.ClassMgr.getProcessInstance(InstanceID, site);
        if (pi == null)
        {
            throw new Exception("The ProcessInstance with " + InstanceID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null)
        {
            throw new Exception("The user " + UserID + " was not found");
        }
        if (closeAction != null && closeAction.length()>0) {
            act = closeAction; 
        }
        if (closeStatus >= 2 && closeStatus <= 4) {
            stat = closeStatus;
        }
        pi.close(u, stat, act);
    }

    @Override
    public void closeTaskInstance(String APIKey, String UserID, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception
    {
        String act = FlowNodeInstance.ACTION_ACCEPT;
        int stat = FlowNodeInstance.STATUS_CLOSED;
        
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        FlowNodeInstance fni = FlowNodeInstance.ClassMgr.getFlowNodeInstance(InstanceID, site);
        if (fni == null)
        {
            throw new Exception("The FlowNodeInstance with id " + InstanceID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null)
        {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        if (closeAction != null && closeAction.length()>0) {
            act = closeAction; 
        }
        if (closeStatus >= 2 && closeStatus <= 4) {
            stat = closeStatus;
        }
        fni.close(u, stat, act);
    }

    @Override
    public InstanceInfo[] getProcessInstances(String APIKey, int instanceStatus, String SiteID) throws Exception
    {
        List<InstanceInfo> getProcessInstances = new ArrayList<InstanceInfo>();
        ProcessSite p = ProcessSite.ClassMgr.getProcessSite(SiteID);
        
        if (p == null) {
            throw new Exception("The ProcessSite with id " + SiteID + " was not found");
        }
        Iterator<ProcessInstance> gi = SWBProcessMgr.getProcessInstanceWithStatus(p, instanceStatus);
        if (gi.hasNext()) {
            while (gi.hasNext()) {
                ProcessInstance pi = gi.next();
                getProcessInstances.add(getProcessInstanceInfo(pi));
            }
        }
        return getProcessInstances.toArray(new InstanceInfo[getProcessInstances.size()]);
    }

    @Override
    public FlowNodeInstanceInfo[] listUserTaskInstances(String APIKey, String UserID, String ProcessID, int instanceStatus, String SiteID) throws Exception
    {
        List<FlowNodeInstanceInfo> listUserTaskInstances = new ArrayList<FlowNodeInstanceInfo>();
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        User u = site.getUserRepository().getUser(UserID);
        int status = instanceStatus<0?FlowNodeInstance.STATUS_PROCESSING:instanceStatus;
        
        if (site == null) {
            throw new Exception("The processSite with id " + SiteID + " was not found");
        }
        
        if (u == null) {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        
        org.semanticwb.process.model.Process p = org.semanticwb.process.model.Process.ClassMgr.getProcess(ProcessID, site);
        
        Iterator<FlowNodeInstance> flows = SWBProcessMgr.getUserTaskInstancesWithStatus(site, status, p).iterator();
        while (flows.hasNext()) {
            FlowNodeInstance fni = flows.next();
            if (validUserTaskInstance(fni, u, status)) {
                FlowNodeInstanceInfo info = getFlowNodeInstanceInfo(fni);
                listUserTaskInstances.add(info);
            }
        }
        listUserTaskInstances.toArray(new FlowNodeInstanceInfo[listUserTaskInstances.size()]);
        return listUserTaskInstances.toArray(new FlowNodeInstanceInfo[listUserTaskInstances.size()]);
    }

    @Override
    public int getProcessInstanceStatus(String APIKey, String processInstanceID, String SiteID) throws Exception
    {
        WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        ProcessInstance pi = ProcessInstance.ClassMgr.getProcessInstance(processInstanceID, site);
        if (pi == null)
        {
            throw new Exception("The ProcessInstance with id " + processInstanceID + " was not found");
        }
        return pi.getStatus();
    }
    
    @Override
    public String createProcessInstance(String APIKey, String ProcessID, String UserID, String SiteID) throws Exception {
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null) {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        
        org.semanticwb.process.model.Process p = org.semanticwb.process.model.Process.ClassMgr.getProcess(ProcessID, site);
        if (p == null) {
            throw new Exception("The Process with id " + ProcessID + " was not found");
        }
        ProcessInstance pi = SWBProcessMgr.createProcessInstance(p, u);
        //TODO: Checar por qué no se asigna
        pi.setCreator(u);
        return pi==null?null:pi.getId();
    }

    @Override
    public ProcessInfo[] listUserProcesses(String APIKey, String UserID, String SiteID) throws Exception {
        List<ProcessInfo> processes = new ArrayList<ProcessInfo>();
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        User u = site.getUserRepository().getUser(UserID);
        
        if (site == null) {
            throw new Exception("The processSite with id " + SiteID + " was not found");
        }
        
        if (u == null) {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        
        Iterator<StartEvent> startEvents = StartEvent.ClassMgr.listStartEvents(site);
        while (startEvents.hasNext()) {
            StartEvent sevt = startEvents.next();
            if (sevt.getContainer() != null && sevt.getContainer() instanceof Process && u.haveAccess(sevt)) {
                org.semanticwb.process.model.Process itp = sevt.getProcess();
                if (itp != null && itp.isValid()) {
                    processes.add(getProcessInfo(itp));
                }
            }
        }
        return processes.toArray(new ProcessInfo[processes.size()]);
    }
    
    private InstanceInfo getProcessInstanceInfo(ProcessInstance pi) throws Exception{
        InstanceInfo ret = new InstanceInfo();
        ret.id = pi.getId();
        ret.title = pi.getProcessType().getTitle();
        ret.description = pi.getProcessType().getDescription();
        ret.status = pi.getStatus();
        ret.creator = pi.getCreator()==null?null:pi.getCreator().getId();
        ret.created = pi.getCreated();
        ret.closed = pi.getEnded();
        ret.closedBy = pi.getEndedby()==null?null:pi.getEndedby().getId();
        return ret;
    }
    
    private ProcessInfo getProcessInfo(Process p) throws Exception {
        ProcessInfo ret = new ProcessInfo();
        ret.id = p.getId();
        ret.title = p.getTitle();
        ret.description = p.getDescription();
        ret.processGroup = p.getProcessGroup()==null?null:p.getProcessGroup().getId();
        return ret;
    }
    
    private FlowNodeInstanceInfo getFlowNodeInstanceInfo(FlowNodeInstance flow) throws Exception {
        FlowNodeInstanceInfo ret = new FlowNodeInstanceInfo();
        ret.id = flow.getId();
        ret.title = flow.getFlowNodeType().getTitle();
        ret.description = flow.getFlowNodeType().getDescription();
        ret.status = flow.getStatus();
        ret.creator = flow.getCreator()==null?null:flow.getCreator().getId();
        ret.assignedTo = flow.getAssignedto()==null?null:flow.getAssignedto().getId();
        ret.assigned = flow.getAssigned();
        ret.created = flow.getCreated();
        ret.closed = flow.getEnded();
        ret.closedBy = flow.getEndedby()==null?null:flow.getEndedby().getId();
        ret.subject = flow.getSubject();
        ret.processInstance = getProcessInstanceInfo(flow.getProcessInstance());
        return ret;
    }
    
    private boolean validUserTaskInstance(FlowNodeInstance fni, User user, int statusFilter) {
        boolean hasStatus = false;
        boolean canAccess = false;
        
        org.semanticwb.process.model.Process pType = fni.getProcessInstance().getProcessType();
        
        if (!pType.isValid()) {
            return false;
        }
        canAccess = fni.haveAccess(user);
        if (canAccess) {
            //Verificar filtrado por estatus
            if (statusFilter > 0 && fni.getStatus() == statusFilter) {
                hasStatus = true;
            }
        }
        return canAccess && hasStatus;
    }
}
