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
	 * 部署流程定义（从zip）
	 * act_re_deployment部署对象表
	 * act_re_procdef流程定义表
	 * act_ge_vytearray资源文件表
	 * act_ge_property主键生成策略表
	 */
	@Test
	public void deploymentProcessDefinitionInputStream(){
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("groupTask.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("groupTask.png");
		Deployment deployment= processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
				 .createDeployment() //创建一个部署对象
				 .name("组任务")//添加部署的名称
				 .addInputStream("groupTask.bpmn", inputStreamBpmn)
				 .addInputStream("groupTask.png", inputStreamPng)
				 .deploy();//完成部署
				System.out.println("部署ID:"+deployment.getId());//1
				System.out.println("部署名称:"+deployment.getName());//helloworld入门程序
	}
	
	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcessInstance(){
		//流程定义的key
//		String processDefinitionKey="groupTask";
//		ProcessInstance processInstance= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
//			.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key的启动实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
//		System.out.println("流程实例ID:"+processInstance.getId());//流程实例ID
//		System.out.println("流程实例ID:"+processInstance.getProcessDefinitionId());//流程定义ID
		
		String processDefinitionKey="groupTask";
		ProcessInstance processInstance= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
			.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key的启动实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		System.out.println("流程实例ID:"+processInstance.getId());//流程实例ID
		System.out.println("流程实例ID:"+processInstance.getProcessDefinitionId());//流程定义ID
	}
	
	/**
	 * 查询当前人的个人任务
	 */
	@Test
	public void findPersonTask(){
		String assignee = "周芷若";
		List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的service
			.createTaskQuery()//创建任务查询对象
				.taskAssignee(assignee)//指定个人任务查询，指定办理人
					.list();
		if(list!=null && list.size()>0){
			for(Task task:list){
				System.out.println("任务ID:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务的创建时间:"+task.getCreateTime());
				System.out.println("任务的办理人:"+task.getAssignee());
				System.out.println("流程实例ID:"+task.getProcessInstanceId());
				System.out.println("执行对象ID:"+task.getExecutionId());
				System.out.println("流程定义ID:"+task.getProcessDefinitionId());
			}
		}
	}
	
	/**
	 * 查询当前人的组任务
	 */
	@Test
	public void findGroupTask(){
		String candidateUser = "小A";
		List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的service
			.createTaskQuery()//创建任务查询对象
//				.taskAssignee(assignee)//指定个人任务查询，指定办理人
			.taskCandidateUser(candidateUser)
			.orderByTaskCreateTime().asc()
					.list();
		if(list!=null && list.size()>0){
			for(Task task:list){
				System.out.println("任务ID:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务的创建时间:"+task.getCreateTime());
				System.out.println("任务的办理人:"+task.getAssignee());
				System.out.println("流程实例ID:"+task.getProcessInstanceId());
				System.out.println("执行对象ID:"+task.getExecutionId());
				System.out.println("流程定义ID:"+task.getProcessDefinitionId());
			}
		}
	}
	
	/**查询正在执行的任务办理人表*/
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
	
	/**查询历史任务的办理人表*/
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
	 * 拾取任务，将组任务分给个人任务，指定任务的办理人
	 */
	@Test
	public void claim(){
		//将组任务分配给个人任务
		//任务Id
		String taskId="6204";
		String userId="大F";
		//分配个人任务（可以是组任务中的成员，也可以是非组任务的成员）
		processEngine.getTaskService()
			.claim(taskId, userId);
	}
	
	/**将个人任务回退到组任务，前提，之前一定是个组任务*/
	@Test
	public void setAssigee(){
		
		//任务Id
		String taskId="6204";
		processEngine.getTaskService()
		.setAssignee(taskId, null);
	}
	
	/**向组任务中添加成员*/
	@Test
	public void addGroupUser(){
		//任务Id
		String taskId="6204";
		//成员办理人
		String userId="大H";
		processEngine.getTaskService()
		.addCandidateUser(taskId, userId);
	}
	
	/**向组任务中删除成员*/
	@Test
	public void deleteGroupUser(){
		//任务Id
		String taskId="6204";
		//成员办理人
		String userId="小B";
		processEngine.getTaskService()
		.deleteCandidateUser(taskId, userId);
	}
}
