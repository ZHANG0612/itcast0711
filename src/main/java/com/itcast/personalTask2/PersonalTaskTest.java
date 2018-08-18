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
	 * 部署流程定义（从zip）
	 * act_re_deployment部署对象表
	 * act_re_procdef流程定义表
	 * act_ge_vytearray资源文件表
	 * act_ge_property主键生成策略表
	 */
	@Test
	public void deploymentProcessDefinitionInputStream(){
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("personalTask.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("personalTask.png");
		Deployment deployment= processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
				 .createDeployment() //创建一个部署对象
				 .name("个人任务")//添加部署的名称
				 .addInputStream("personalTask.bpmn", inputStreamBpmn)
				 .addInputStream("personalTask.png", inputStreamPng)
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
		String processDefinitionKey="personalTask";
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
		String assignee = "张翠山";
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
	 * 完成我的任务
	 */
	@Test
	public void completePersonalTask(){
		//任务ID
		String taskId="5804";
		processEngine.getTaskService()//与正在执行的任务管理相关的Service
			.complete(taskId);
		System.out.println("完成任务: 任务ID: "+taskId);
	}
	
	/**
	 * 认领任务，可以分配个人任务从一个人到另一个人
	 */
	@Test
	public void setAssigneeTask(){
		//任务Id
		String taskId="5804";
		//指定办理人
		String userId="张翠山";
		processEngine.getTaskService()
			.setAssignee(taskId, userId);
	}
}
