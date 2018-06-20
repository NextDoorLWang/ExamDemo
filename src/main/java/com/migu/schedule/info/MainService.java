package com.migu.schedule.info;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainService {

    private static MainService mainService=new MainService();

    public static MainService getInstance(){
        return mainService;
    }

    //待执行分配任务队列
    private static Queue<TaskInfo> waitTaskInfoQueue =new LinkedList<TaskInfo>();

    //服务器列表
    private static HashMap<Integer,ServiceObject> serviceObjectMap=new HashMap<Integer, ServiceObject>();

    //所有仍然存在的任务，实时存储与消除
    private static HashMap<Integer,TaskInfo> taskInfoHashMap=new HashMap<Integer, TaskInfo>();

    /**
     * <p>任务消耗存储</p>
     */
    private static HashMap<Integer,Integer> taskConsumptionMap =new HashMap<Integer, Integer>();

    /**
     *<p>服务器消耗值存储</p>
     */
    private static HashMap<Integer,Integer> serviceConsumptionMap=new HashMap<Integer, Integer>();


    public static Queue<TaskInfo> getWaitTaskInfoQueue() {
        return waitTaskInfoQueue;
    }


    public static void setWaitTaskInfoQueue(Queue<TaskInfo> waitTaskInfoQueue) {
        MainService.waitTaskInfoQueue = waitTaskInfoQueue;
    }

    public static HashMap<Integer, ServiceObject> getServiceObjectMap() {
        return serviceObjectMap;
    }

    public static void setServiceObjectMap(HashMap<Integer, ServiceObject> serviceObjectMap) {
        MainService.serviceObjectMap = serviceObjectMap;
    }

    public static HashMap<Integer, TaskInfo> getTaskInfoHashMap() {
        return taskInfoHashMap;
    }

    public static void setTaskInfoHashMap(HashMap<Integer, TaskInfo> taskInfoHashMap) {
        MainService.taskInfoHashMap = taskInfoHashMap;
    }

    public static HashMap<Integer, Integer> getTaskConsumptionMap() {
        return taskConsumptionMap;
    }

    public static void setTaskConsumptionMap(HashMap<Integer, Integer> taskConsumptionMap) {
        MainService.taskConsumptionMap = taskConsumptionMap;
    }

    public static HashMap<Integer, Integer> getServiceConsumptionMap() {
        return serviceConsumptionMap;
    }

    public static void setServiceConsumptionMap(HashMap<Integer, Integer> serviceConsumptionMap) {
        MainService.serviceConsumptionMap = serviceConsumptionMap;
    }
}
