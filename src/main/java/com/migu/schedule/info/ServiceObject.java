package com.migu.schedule.info;

import java.util.Queue;

public class ServiceObject {

    //服务器节点
    private Integer nodeId;

    //服务器消耗值
    private Integer consumeValue;

    //服务器内部消息对列
    private Queue<TaskInfo> taskInfoQueue;


    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getConsumeValue() {
        return consumeValue;
    }

    public void setConsumeValue(Integer consumeValue) {
        this.consumeValue = consumeValue;
    }

    public Queue<TaskInfo> getTaskInfoQueue() {
        return taskInfoQueue;
    }

    public void setTaskInfoQueue(Queue<TaskInfo> taskInfoQueue) {
        this.taskInfoQueue = taskInfoQueue;
    }
}
