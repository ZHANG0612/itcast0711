package com.itcast.exclusiveGateWay;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ExclusiveGateWayTest {

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
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("exclusiveGateWay.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("exclusiveGateWay.png");
		Deployment deployment= processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
				 .createDeployment() //����һ���������
				 .name("��������")//��Ӳ��������
				 .addInputStream("exclusiveGteWay.bpmn", inputStreamBpmn)
				 .addInputStream("exclusiveGteWay.png", inputStreamPng)
				 .deploy();//��ɲ���
				System.out.println("����ID:"+deployment.getId());//1
				System.out.println("��������:"+deployment.getName());//helloworld���ų���
	}
	
	/**
	 * ��������ʵ��
	 */
	@Test
	public void startProcessInstance(){
		//���̶����key
		String processDefinitionKey="exclusiveGateWay";
		ProcessInstance processInstance= processEngine.getRuntimeService()//������ִ�е�����ʵ����ִ�ж�����ص�Service
			.startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID
		System.out.println("����ʵ��ID:"+processInstance.getProcessDefinitionId());//���̶���ID
	}
	
	/**
	 * ��ѯ��ǰ�˵ĸ�������
	 */
	@Test
	public void findMyPersonTask(){
		String assignee = "��С��";
		List<Task> list = processEngine.getTaskService()//������ִ�е����������ص�service
			.createTaskQuery()//���������ѯ����
			/** ��ѯ������where���֣�*/
				.taskAssignee(assignee)//ָ�����������ѯ��ָ��������
				//.taskCandidateUser(candidateUser)//������İ����˲�ѯ
				//.processDefinitionId(processDefinitionId)//ʹ������ʵ��ID��ѯ
				//.executionId(executionId)//ʹ��ִ�ж���ID��ѯ
				/**���� */
				.orderByTaskCreateTime().asc()//ʹ�ô���ʱ�����������
				/**���ؽ����*/
				//.singleResult()//����Ψһ�����
				//.count()//���ؽ��������
				//.listPage(firstResult, maxResults);//��ҳ��ѯ
				.list();//�����б�
		if(list!=null && list.size()>0){
			for(Task task:list){
				System.out.println("����ID:"+task.getId());
				System.out.println("��������:"+task.getName());
				System.out.println("����Ĵ���ʱ��:"+task.getCreateTime());
				System.out.println("����İ�����:"+task.getAssignee());
				System.out.println("����ʵ��ID:"+task.getProcessInstanceId());
				System.out.println("ִ�ж���ID:"+task.getExecutionId());
				System.out.println("���̶���ID:"+task.getProcessDefinitionId());
			}
		}
	}
	
	/**
	 * ����ҵ�����
	 */
	@Test
	public void completeMyPersonalTask(){
		//����ID
		String taskId="3904";
		//��������ͬʱ���������̱�����ʹ�����̱�������ָ������������һ�����ߣ���ӦexclusiveGateWay.bpmn�ļ��е�${money>1000}
		Map<String, Object> variables=new HashMap<String,Object>();
		variables.put("money", 200);
		processEngine.getTaskService()//������ִ�е����������ص�Service
			.complete(taskId,variables);
		System.out.println("�������: ����ID: "+taskId);
	}
	
}
