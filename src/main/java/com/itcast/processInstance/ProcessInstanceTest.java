package com.itcast.processInstance;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ProcessInstanceTest {
	
	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();

	/**
	 * �������̶��壨��zip��
	 * act_re_deployment��������
	 * act_re_procdef���̶����
	 * act_ge_vytearray��Դ�ļ���
	 * act_ge_property�������ɲ��Ա�
	 */
	@Test
	public void deploymentProcessDefinitionZip(){
		InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
		ZipInputStream zipInputStream=new ZipInputStream(inputStream);
		Deployment deployment= processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
				 .createDeployment() //����һ���������
				 .name("���̶���")//��Ӳ��������
				 .addZipInputStream(zipInputStream)//ָ��zip��ʽ���ļ���ɲ���
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
		String processDefinitionKey="helloworld";
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
		String assignee = "����";
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
		String taskId="1002";
		processEngine.getTaskService()//������ִ�е����������ص�Service
			.complete(taskId);
		System.out.println("�������: ����ID: "+taskId);
	}
	
	/**
	 * ��ѯ����״̬(�ж���������ִ�У����ǽ���)
	 */
	@Test
	public void isProcessEnd(){
		String processInstanceId="801";
		ProcessInstance processInstance = processEngine.getRuntimeService()//��ʾ����ִ�е�����ʵ�������̶���
			.createProcessInstanceQuery()//��������ʵ����ѯ
				.processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
					.singleResult();
		if(processInstance==null){
			System.out.println("�����ѽ���");
		}else{
			System.out.println("����û�н���");
		}
	}
	
	/**
	 * ��ѯ��ʷ����
	 */
	@Test
	public void findHistoryTask(){
		String taskAssignee="����";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()//����ʷ���ݣ���ʷ����ص�Service
				.createHistoricTaskInstanceQuery()//������ʷ����ʵ����ѯ
					.taskAssignee(taskAssignee)//ָ����ʷ����İ�����
						.list();
		if(list!=null && list.size()>0){
			for(HistoricTaskInstance hInstance:list){
				System.out.println(hInstance.getId()+"\n"+hInstance.getName()+"\n"+hInstance.getProcessInstanceId()+"\n"+hInstance.getStartTime()+"\n"+hInstance.getStartTime()+"\n"+hInstance.getEndTime());
				System.out.println("##################");
			}
		}
	}
	
	/**
	 * ��ѯ��ʷ����ʵ��
	 */
	@Test
	public void findHistoryProcessInstance(){
		String processInstanceId="801";
		HistoricProcessInstance hProcessInstance =  processEngine.getHistoryService()//����ʷ���ݣ���ʷ����ص�Service
			.createHistoricProcessInstanceQuery()//������ʷ����ʵ����ѯ
				.processInstanceId(processInstanceId)
					.singleResult();
		System.out.println(hProcessInstance.getId()+" "+hProcessInstance.getProcessDefinitionId()+" "+hProcessInstance.getStartTime()+" "+hProcessInstance.getEndTime()+" ");
	}
}
