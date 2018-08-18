package com.itcast.personalTask2;

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

public class PersonalTaskTest {

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
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("personalTask.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("personalTask.png");
		Deployment deployment= processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
				 .createDeployment() //����һ���������
				 .name("��������")//��Ӳ��������
				 .addInputStream("personalTask.bpmn", inputStreamBpmn)
				 .addInputStream("personalTask.png", inputStreamPng)
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
		String processDefinitionKey="personalTask";
		ProcessInstance processInstance= processEngine.getRuntimeService()//������ִ�е�����ʵ����ִ�ж�����ص�Service
			.startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID
		System.out.println("����ʵ��ID:"+processInstance.getProcessDefinitionId());//���̶���ID
	}
	
	/**
	 * ��ѯ��ǰ�˵ĸ�������
	 */
	@Test
	public void findPersonTask(){
		String assignee = "�Ŵ�ɽ";
		List<Task> list = processEngine.getTaskService()//������ִ�е����������ص�service
			.createTaskQuery()//���������ѯ����
				.taskAssignee(assignee)//ָ�����������ѯ��ָ��������
					.list();
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
	public void completePersonalTask(){
		//����ID
		String taskId="5804";
		processEngine.getTaskService()//������ִ�е����������ص�Service
			.complete(taskId);
		System.out.println("�������: ����ID: "+taskId);
	}
	
	/**
	 * �������񣬿��Է�����������һ���˵���һ����
	 */
	@Test
	public void setAssigneeTask(){
		//����Id
		String taskId="5804";
		//ָ��������
		String userId="�Ŵ�ɽ";
		processEngine.getTaskService()
			.setAssignee(taskId, userId);
	}
}
