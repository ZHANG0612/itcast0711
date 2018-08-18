package com.itcast.processVariables;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessVariablesTest {

	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * �������̶��壨��Inputstream��
	 * act_re_deployment��������
	 * act_re_procdef���̶����
	 * act_ge_vytearray��Դ�ļ���
	 * act_ge_property�������ɲ��Ա�
	 */
	@Test
	public void deploymentProcessDefinitionInputStream(){
		InputStream inputStreambpmn=this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
		InputStream inputStreampng=this.getClass().getResourceAsStream("/diagrams/processVariables.png");
		Deployment deployment= processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
				 .createDeployment() //����һ���������
				 .name("���̶���")//��Ӳ��������
				 .addInputStream("processVariables.bpmn", inputStreambpmn)//ʹ����Դ�ļ������ƣ�Ҫ������Դ�ļ�������Ҫһ�£�
				 .addInputStream("processVariables.png", inputStreampng)//ʹ����Դ�ļ������ƣ�Ҫ������Դ�ļ�������Ҫһ�£�
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
		String processDefinitionKey="processVariables";
		ProcessInstance processInstance= processEngine.getRuntimeService()//������ִ�е�����ʵ����ִ�ж�����ص�Service
			.startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID
		System.out.println("����ʵ��ID:"+processInstance.getProcessDefinitionId());//���̶���ID
	}
	
	/**
	 * �������̱���
	 */
	@Test
	public void setVariables(){
		/**����������ִ�У�*/
		TaskService taskService=processEngine.getTaskService();
		//����ID
		String taskId="1804";
		/**һ���������̱�����ʹ�û�����������*/
//		taskService.setVariable(taskId, "�������", 3);
//		taskService.setVariable(taskId, "�������", new Date());
//		taskService.setVariable(taskId, "���ԭ��", "�ؼ�̽��");
		/**�����������̱�����ʹ��javabean����*/
		/**
		 * ��һ��javaBean��ʵ�����л������õ����̱����У�Ҫ��javabean�����Բ����ٷ����仯
		 *   ��������仯���ٻ�ȡ��ʱ���׳��쳣
		 *   �����������person��������ӣ�
		 *      private static final long serialVersionUID = 1L;
		 *      ͬʱʵ��Serializable
		 */
		Person person=new Person();
		person.setId(20);
		person.setName("�仨");
		taskService.setVariable(taskId, "��Ա��Ϣ���̶��汾��", person);
		System.out.println("�������̱����ɹ���");
	}
	
	/**
	 * ��ȡ���̱���
	 */
	@Test
	public void getVariables(){
		/**����������ִ�У�*/
		TaskService taskService=processEngine.getTaskService();
		//����ID
		String taskId="1804";
		/**һ����ȡ���̱�����ʹ�û�����������*/
//		Integer day=(Integer) taskService.getVariable(taskId, "�������");
//		Date date=(Date) taskService.getVariable(taskId, "�������");
//		String reason=(String) taskService.getVariable(taskId, "���ԭ��");
//		System.out.println("���������"+day);
//		System.out.println("������ڣ�"+date);
//		System.out.println("���ԭ��"+reason);
		/**��ȡ���̱�����ʹ��javabean����*/
		Person person=(Person) taskService.getVariable(taskId, "��Ա��Ϣ���̶��汾��");
		System.out.println("��ԱID��"+person.getId());
		System.out.println("��Ա������"+person.getName());
	}
	
	/**
	 * ģ�����úͻ�ȡ���̱����ĳ���
	 */
	@Test
	public void setAndGetVariables(){
		/**������ʵ����ִ�ж�������ִ�У�*/
		RuntimeService runtimeService=processEngine.getRuntimeService();
		/**����������ִ�У�*/
		TaskService taskService=processEngine.getTaskService();
		
		/**�������̱���*/
		//runtimeService.setVariable(executionId, variableName, value);//��ʾʹ��ִ�ж���ID�����̱��������ƣ��������̱�����ֵ��һ��ֻ������һ��ֵ��
		//runtimeService.setVariables(executionId, variables);//��ʾʹ��ִ�ж���ID��Map�����������̱�����map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ��һ�����ö��ֵ��
		
		//taskService.setVariable(taskId, variableName, value);//��ʾʹ��ִ�ж���ID�����̱��������ƣ��������̱�����ֵ��һ��ֻ������һ��ֵ��
		//taskService.setVariables(taskId, variables);//��ʾʹ��ִ�ж���ID��Map�����������̱�����map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ��һ�����ö��ֵ��
		//runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);//��������ʵ����ͬʱ�������������̱�������Map����
		//taskService.complete(taskId,variables);//��������ͬʱ���������̱�������Map����
		
		/**��ȡ���̱���*/
		//runtimeService.getVariable(executionId, variableName);//ʹ��ִ�ж���ID�����̱��������ƣ���ȡ���̱�����ֵ
		//runtimeService.getVariables(executionId);//ʹ��ִ�ж���ID����ȡ���е����̱����������̱������õ�Map������
		//runtimeService.getVariables(executionId, variableNames);//ʹ��ִ�ж���ID����ȡ���̱�����ֵ��ͨ���������̱��������ƴ�ŵ������У���ȡָ�����̱������Ƶ����̱�����ֵ��ֵ��ŵ�Map������
		
		//taskService.getVariable(taskId, variableName);//ʹ��ִ�ж���ID�����̱��������ƣ���ȡ���̱�����ֵ
		//taskService.getVariables(executionId);//ʹ��ִ�ж���ID����ȡ���е����̱����������̱������õ�Map������
	    //taskService.getVariables(executionId, variableNames);//ʹ��ִ�ж���ID����ȡ���̱�����ֵ��ͨ���������̱��������ƴ�ŵ������У���ȡָ�����̱������Ƶ����̱�����ֵ��ֵ��ŵ�Map������
	}
	
	/**
	 * ����ҵ�����
	 */
	@Test
	public void completeMyPersonalTask(){
		//����ID
		String taskId="2102";
		processEngine.getTaskService()//������ִ�е����������ص�Service
			.complete(taskId);
		System.out.println("�������: ����ID: "+taskId);
	}
	
	/**
	 * ��ѯ���̱�������ʷ��
	 */
	@Test
	public void findHistoryProcessVariables(){
		List<HistoricVariableInstance> list = processEngine.getHistoryService()//
			.createHistoricVariableInstanceQuery()//����һ����ʷ�����̱�����ѯ����
				.variableName("�������")
					.list();
		if(list!=null && list.size()>0){
			for(HistoricVariableInstance historicVariableInstance:list){
				System.out.println(historicVariableInstance.getId()+"\t"+historicVariableInstance.getProcessInstanceId()+"\t"+historicVariableInstance.getVariableName()+"\t"+historicVariableInstance.getVariableTypeName()+"\t"+historicVariableInstance.getValue());
				System.out.println("#################################");
			}
		}
	}
}
