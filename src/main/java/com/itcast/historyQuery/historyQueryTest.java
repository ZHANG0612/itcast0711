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
	 * ��ѯ��ʷ����ʵ��
	 */
	@Test
	public void findHistoryProcessInstance(){
		String processInstanceId="1301";
		HistoricProcessInstance hpi = processEngine.getHistoryService()//����ʷ���ݣ���ʷ����ص�Service
				.createHistoricProcessInstanceQuery()//������ʷ����ʵ����ѯ
				.processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
				.singleResult();
		System.out.println(hpi.getId()+"\t"+hpi.getProcessDefinitionId()+"\t"+hpi.getStartTime()+"\t"+hpi.getEndTime()+"\t"+hpi.getDurationInMillis());
	}
	
	/**
	 * ��ѯ��ʷ�
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
	 * ��ѯ��ʷ����
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
	 * ��ѯ��ʷ����
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
