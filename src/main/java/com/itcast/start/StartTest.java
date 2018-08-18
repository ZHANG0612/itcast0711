package com.itcast.start;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class StartTest {
	
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
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("start.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("start.png");
		Deployment deployment= processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
				 .createDeployment() //����һ���������
				 .name("��ʼ�")//��Ӳ��������
				 .addInputStream("start.bpmn", inputStreamBpmn)
				 .addInputStream("start.png", inputStreamPng)
				 .deploy();//��ɲ���
				System.out.println("����ID:"+deployment.getId());//1
				System.out.println("��������:"+deployment.getName());//helloworld���ų���
	}
	
	/**
	 * ��������ʵ�����ж������Ƿ��������ѯ��ʷ
	 */
	@Test
	public void startProcessInstance(){
		//���̶����key
		String processDefinitionKey="start";
		ProcessInstance processInstance= processEngine.getRuntimeService()//������ִ�е�����ʵ����ִ�ж�����ص�Service
			.startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID
		System.out.println("����ʵ��ID:"+processInstance.getProcessDefinitionId());//���̶���ID
		
		/**�ж������Ƿ����
		 * ��ѯ����ִ�е�ִ�ж����
		 * */
		ProcessInstance rProcessInstance= processEngine.getRuntimeService()//
		.createProcessInstanceQuery()//��������ʵ����ѯ����
			.processInstanceId(processInstance.getId())
			.singleResult();
		//˵������ʵ������
		if(rProcessInstance==null){
			/**��ѯ��ʷ����ȡ���̵������Ϣ**/
			HistoricProcessInstance hpi =processEngine.getHistoryService()
			.createHistoricProcessInstanceQuery()
			.processInstanceId(processInstance.getId())
			.singleResult();
			
			System.out.println(hpi.getId()+"\t"+hpi.getStartTime()+"\t"+hpi.getEndTime()+"\t"+hpi.getDurationInMillis());
			 
		}
	}
}
