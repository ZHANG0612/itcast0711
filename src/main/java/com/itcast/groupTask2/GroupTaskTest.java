package com.itcast.groupTask2;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class GroupTaskTest {

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
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("groupTask.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("groupTask.png");
		Deployment deployment= processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
				 .createDeployment() //����һ���������
				 .name("������")//��Ӳ��������
				 .addInputStream("groupTask.bpmn", inputStreamBpmn)
				 .addInputStream("groupTask.png", inputStreamPng)
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
//		String processDefinitionKey="groupTask";
//		ProcessInstance processInstance= processEngine.getRuntimeService()//������ִ�е�����ʵ����ִ�ж�����ص�Service
//			.startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
//		System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID
//		System.out.println("����ʵ��ID:"+processInstance.getProcessDefinitionId());//���̶���ID
		
		String processDefinitionKey="groupTask";
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
		String assignee = "������";
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
	 * ��ѯ��ǰ�˵�������
	 */
	@Test
	public void findGroupTask(){
		String candidateUser = "СA";
		List<Task> list = processEngine.getTaskService()//������ִ�е����������ص�service
			.createTaskQuery()//���������ѯ����
//				.taskAssignee(assignee)//ָ�����������ѯ��ָ��������
			.taskCandidateUser(candidateUser)
			.orderByTaskCreateTime().asc()
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
	
	/**��ѯ����ִ�е���������˱�*/
	@Test
	public void findRunPersonTask(){
		String taskId="6204";
		List<IdentityLink> list = processEngine.getTaskService()
		.getIdentityLinksForTask(taskId);
		if(list!=null && list.size()>0){
			for(IdentityLink identityLink :list){
				System.out.println(identityLink.getTaskId()+"\t"+identityLink.getType()+"\t"+identityLink.getProcessInstanceId()+"\t"+identityLink.getUserId());
				System.out.println("###################");
			}
		}
	}
	
	/**��ѯ��ʷ����İ����˱�*/
	@Test
	public void findHistoryPersonTask(){
		String processInstanceId="6201";
		List<HistoricIdentityLink> list = processEngine.getHistoryService()
		.getHistoricIdentityLinksForProcessInstance(processInstanceId);
		if(list!=null && list.size()>0){
			for(HistoricIdentityLink historicIdentityLink:list){
				System.out.println(historicIdentityLink.getTaskId()+"\t"+historicIdentityLink.getType()+"\t"+historicIdentityLink.getProcessInstanceId()+"\t"+historicIdentityLink.getUserId());
				
			}
		}
	}
	
	/**
	 * ʰȡ���񣬽�������ָ���������ָ������İ�����
	 */
	@Test
	public void claim(){
		//��������������������
		//����Id
		String taskId="6204";
		String userId="��F";
		//����������񣨿������������еĳ�Ա��Ҳ�����Ƿ�������ĳ�Ա��
		processEngine.getTaskService()
			.claim(taskId, userId);
	}
	
	/**������������˵�������ǰ�ᣬ֮ǰһ���Ǹ�������*/
	@Test
	public void setAssigee(){
		
		//����Id
		String taskId="6204";
		processEngine.getTaskService()
		.setAssignee(taskId, null);
	}
	
	/**������������ӳ�Ա*/
	@Test
	public void addGroupUser(){
		//����Id
		String taskId="6204";
		//��Ա������
		String userId="��H";
		processEngine.getTaskService()
		.addCandidateUser(taskId, userId);
	}
	
	/**����������ɾ����Ա*/
	@Test
	public void deleteGroupUser(){
		//����Id
		String taskId="6204";
		//��Ա������
		String userId="СB";
		processEngine.getTaskService()
		.deleteCandidateUser(taskId, userId);
	}
}
