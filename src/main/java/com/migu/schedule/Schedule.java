package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.MainService;
import com.migu.schedule.info.ServiceObject;
import com.migu.schedule.info.TaskInfo;
import com.migu.schedule.unit.ValueComparatorUnit;

import javax.management.Query;
import java.util.*;

/*
 *类名和方法不能修改
 */
public class Schedule {

    //节点注册初始化消耗值
    private static final Integer InitConsumeValue = 0;


    /**
     * <p>按照任务的taskId来排序</p>
     *
     */
    Comparator<TaskInfo> comparatorByTaskId = new Comparator<TaskInfo>(){
        public int compare(TaskInfo taskInfo1, TaskInfo taskInfo2) {
            return (taskInfo1.getTaskId()-taskInfo2.getTaskId());
        }
    };

    Comparator<Integer> comparatorByConsumeValue = new Comparator<Integer>(){
        public int compare(Integer o1, Integer o2) {
            return (MainService.getServiceConsumptionMap().get(o2)-MainService.getServiceConsumptionMap().get(o1));
        }
    };




    public int init() {
        HashMap<Integer, ServiceObject> serviceObjectMap = MainService.getServiceObjectMap();
        serviceObjectMap.clear();
        Queue<TaskInfo> infoLinkedList = MainService.getWaitTaskInfoQueue();
        infoLinkedList.clear();
        HashMap<Integer, TaskInfo> taskInfoHashMap = MainService.getTaskInfoHashMap();
        taskInfoHashMap.clear();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
        if (nodeId <= 0) {
            return ReturnCodeKeys.E004;
        }
        ServiceObject serviceObject = new ServiceObject();
        serviceObject.setNodeId(nodeId);
        serviceObject.setConsumeValue(InitConsumeValue);
        HashMap<Integer, ServiceObject> alreadyObjectMap = MainService.getServiceObjectMap();
        Boolean alreadyExist = Boolean.FALSE;
        if (alreadyObjectMap.get(nodeId) != null) {
            alreadyExist = Boolean.TRUE;
        }
        if (alreadyExist) {
            return ReturnCodeKeys.E005;
        }
        alreadyObjectMap.put(nodeId, serviceObject);
        MainService.getServiceConsumptionMap().put(nodeId,InitConsumeValue);
        return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
        if (nodeId <= 0) {
            return ReturnCodeKeys.E004;
        }

        HashMap<Integer, ServiceObject> serviceObjectMap = MainService.getServiceObjectMap();
        Boolean alreadyExist = Boolean.FALSE;
        ServiceObject alreadyServiceObject = null;
        if (serviceObjectMap.get(nodeId) != null) {
            alreadyExist = Boolean.TRUE;
            alreadyServiceObject = serviceObjectMap.get(nodeId);
        }
        if (!alreadyExist) {
            return ReturnCodeKeys.E007;
        }
        if (alreadyServiceObject != null) {
            Queue<TaskInfo> taskInfoQueue = alreadyServiceObject.getTaskInfoQueue();
           if(taskInfoQueue!=null){
               MainService.getWaitTaskInfoQueue().addAll(taskInfoQueue);
           }
            serviceObjectMap.remove(alreadyServiceObject.getNodeId());
        }
        MainService.getServiceConsumptionMap().remove(nodeId);
        return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption) {
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        HashMap<Integer, TaskInfo> taskInfoHashMap = MainService.getInstance().getTaskInfoHashMap();
        if (taskInfoHashMap.get(taskId) != null) {
            return ReturnCodeKeys.E010;
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(taskId);
        //初始添加未分配节点，默认设为-1
        taskInfo.setNodeId(-1);
        //放入待执行队列中
        MainService.getWaitTaskInfoQueue().add(taskInfo);
        taskInfoHashMap.put(taskId, taskInfo);
        //将消耗存储起来，调度之后取值
        MainService.getTaskConsumptionMap().put(taskId, consumption);
        //任务总量池中记载他。方便查询；
        taskInfoHashMap.put(taskId, taskInfo);
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        HashMap<Integer, TaskInfo> alreadyExitTaskInfoHashMap = MainService.getTaskInfoHashMap();
        if (alreadyExitTaskInfoHashMap.get(taskId) == null) {
            return ReturnCodeKeys.E012;
        }
        TaskInfo waitDelteTask = alreadyExitTaskInfoHashMap.get(taskId);
        //消耗记录里面也删除该条任务记录
        MainService.getTaskConsumptionMap().remove(taskId);
        Queue<TaskInfo> waitTaskInfoQueue = MainService.getWaitTaskInfoQueue();
        if (waitDelteTask.getNodeId() > 0) {
            HashMap<Integer, ServiceObject> serviceObjectMap = MainService.getServiceObjectMap();
            ServiceObject oweServiceObject = serviceObjectMap.get(waitDelteTask.getNodeId());
            oweServiceObject.getTaskInfoQueue().remove(waitDelteTask);
        } else {
            if (waitTaskInfoQueue.contains(waitDelteTask)) {
                waitTaskInfoQueue.remove(waitDelteTask);
            }
        }
        //任务记录中清楚它
        alreadyExitTaskInfoHashMap.remove(taskId);
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
        // TODO 方法未实现
        if (threshold<=0){
            return ReturnCodeKeys.E002;
        }
        HashMap<Integer,Integer> serviceConsumptionMap=MainService.getServiceConsumptionMap();
        Queue<TaskInfo> query=MainService.getWaitTaskInfoQueue();
        //待执行任务不为空时
        if (query!=null){

             List<Map.Entry<Integer,Integer>> serviceList=new ArrayList<Map.Entry<Integer,Integer>>();
             serviceList.addAll(serviceConsumptionMap.entrySet());
             ValueComparatorUnit.ValueComparator vc=new ValueComparatorUnit.ValueComparator();
             Collections.sort(serviceList,vc);

             List<Map.Entry<Integer,Integer>> taskList=new ArrayList<Map.Entry<Integer,Integer>>();
             Collections.sort(taskList,vc);

            HashMap<Integer,ServiceObject> serviceObjectMap =MainService.getServiceObjectMap();






        }






        return ReturnCodeKeys.E000;
    }


   private Map<Integer,Integer> getMostDValueKeys(List<Map.Entry<Integer,Integer>> list){




        return new HashMap<Integer, Integer>();
   }




    public int queryTaskStatus(List<TaskInfo> tasks) {
        if (tasks == null) {
            return ReturnCodeKeys.E016;
        }
        HashMap<Integer, TaskInfo> alreadyExitTaskInfoHashMap = MainService.getTaskInfoHashMap();
        List<TaskInfo> queryResultList = new ArrayList<TaskInfo>(tasks.size());
        HashMap<Integer, ServiceObject> serviceObjectMap = MainService.getServiceObjectMap();
        for (TaskInfo taskInfo : tasks) {
            TaskInfo queryTaskInfo = alreadyExitTaskInfoHashMap.get(taskInfo.getTaskId());
            if (queryTaskInfo != null && queryTaskInfo.getNodeId() > 0) {
                ServiceObject serviceObject = serviceObjectMap.get(queryTaskInfo.getNodeId());
                if (serviceObject.getTaskInfoQueue().contains(taskInfo)) {
                    queryResultList.add(queryTaskInfo);
                }
            } else if (queryTaskInfo != null && queryTaskInfo.getNodeId() <= 0) {
                queryTaskInfo.setNodeId(-1);
                queryResultList.add(queryTaskInfo);
            }
        }
        tasks.clear();

        // 按照taskId排序
        Collections.sort(queryResultList,comparatorByTaskId);
        tasks.addAll(queryResultList);

        return ReturnCodeKeys.E015;
    }


}
