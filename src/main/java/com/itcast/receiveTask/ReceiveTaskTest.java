package com.itcast.receiveTask;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ReceiveTaskTest {

	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	/**
	 * �������̶��壨��zip��
	 * act_re_deployment��������
	 * act_re_procdef���̶����
	 * act_ge_vytearray��Դ�ļ���
	 * act_ge_property�������ɲ��Ա�
	 */
	@Test
	public void deploymentProcessDefinitionInputStream(){
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("receiveTask.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("receiveTask.png");
		Deployment deployment= processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
				 .createDeployment() //����һ���������
				 .name("���ջ����")//��Ӳ��������
				 .addInputStream("receiveTask.bpmn", inputStreamBpmn)
				 .addInputStream("receiveTask.png", inputStreamPng)
				 .deploy();//��ɲ���
				System.out.println("����ID:"+deployment.getId());//1
				System.out.println("��������:"+deployment.getName());//helloworld���ų���
	}
	
	/**
	 * ��������ʵ�����������̱�������ȡ���̱��������ִ��һ��
	 */
	@Test
	public void startProcessInstance(){
		//���̶����key
		String processDefinitionKey="receiveTask";
		ProcessInstance processInstance= processEngine.getRuntimeService()//������ִ�е�����ʵ����ִ�ж�����ص�Service
			.startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID
		System.out.println("����ʵ��ID:"+processInstance.getProcessDefinitionId());//���̶���ID
		
		/**��ѯִ�ж���ID*/
		Execution execution1 = processEngine.getRuntimeService()
			.createExecutionQuery()//����ִ�ж����ѯ
			.processInstanceId(processInstance.getId())//ʹ������ʵ��ID��ѯ
			.activityId("receivetask1")//��ǰ���id����ӦreceiveTask.bpmn�ļ����еĻ�ڵ�id������ֵ
			.singleResult();
			
		/**ʹ�����̱������õ������۶��������ҵ�����*/
		processEngine.getRuntimeService()
			.setVariable(execution1.getId(), "���ܵ������۶�", 21000);
		
		/**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
		processEngine.getRuntimeService()
		.signal(execution1.getId());
		
		/**��ѯִ�ж���ID*/
		Execution execution2 = processEngine.getRuntimeService()
			.createExecutionQuery()//����ִ�ж����ѯ
			.processInstanceId(processInstance.getId())//ʹ������ʵ��ID��ѯ
			.activityId("receivetask2")//��ǰ���id����ӦreceiveTask.bpmn�ļ����еĻ�ڵ�id������ֵ
			.singleResult();
		
		/**�����̱����л�ȡ���ܵ������۶��ֵ */
		Integer value = (Integer) processEngine.getRuntimeService()
			.getVariable(execution2.getId(), "���ܵ������۶�");
		
		System.out.println("���ϰ巢�Ͷ��ţ�����ǣ�"+value);
		
		/**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
		processEngine.getRuntimeService()
		.signal(execution2.getId());
	}
}
