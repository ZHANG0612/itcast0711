package com.itcast.historyQuery;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

public class historyQueryTest {

	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 查询历史流程实例
	 */
	@Test
	public void findHistoryProcessInstance(){
		String processInstanceId="1301";
		HistoricProcessInstance hpi = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
				.createHistoricProcessInstanceQuery()//创建历史流程实例查询
				.processInstanceId(processInstanceId)//使用流程实例ID查询
				.singleResult();
		System.out.println(hpi.getId()+"\t"+hpi.getProcessDefinitionId()+"\t"+hpi.getStartTime()+"\t"+hpi.getEndTime()+"\t"+hpi.getDurationInMillis());
	}
	
	/**
	 * 查询历史活动
	 */
	@Test
	public void findHistoryActiviti(){
		String processInstanceId="1301";
		List<HistoricActivityInstance> list = processEngine.getHistoryService()
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceStartTime()
				.asc()
				.list();
		if(list!=null && list.size()>0){
			for(HistoricActivityInstance hai:list){
				System.out.println(hai.getId()+"\t"+hai.getProcessInstanceId()+"\t"+hai.getActivityType()+"\t"+hai.getStartTime()+"\t"+hai.getEndTime()+"\t"+hai.getDurationInMillis());
				System.out.println("-------");
			}
		}
	}
	
	/**
	 * 查询历史任务
	 */
	@Test
	public void findHistoryTask(){
		String processInstanceId="1301";
		List<HistoricTaskInstance> list =processEngine.getHistoryService()
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricTaskInstanceStartTime()
				.asc()
				.list();
		if(list!=null && list.size()>0){
			for(HistoricTaskInstance hti:list){
				System.out.println(hti.getId()+"\t"+hti.getName()+"\t"+hti.getProcessInstanceId()+"\t"+hti.getStartTime()+"\t"+hti.getEndTime()+"\t"+hti.getDurationInMillis());
				System.out.println("######");
			}
		}
	}
	
	/**
	 * 查询历史变量
	 */
	@Test
	public void findHistoryProcessVariables(){
		String processInstanceId="1301";
		List<HistoricVariableInstance> list=processEngine.getHistoryService()
				.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByProcessInstanceId()
				.asc()
				.list();
		if(list!=null && list.size()>0){
			for(HistoricVariableInstance hvi:list){
				System.out.println(hvi.getId()+"\t"+hvi.getProcessInstanceId()+"\t"+hvi.getVariableName()+"\t"+hvi.getVariableTypeName()+"\t"+hvi.getValue());
				System.out.println("#####################");
			}
		}
	}
}
