package com.itcast.paralleGateWay;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ParalleGateWayTest {

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
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("paralleGateWay.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("paralleGateWay.png");
		Deployment deployment= processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
				 .createDeployment() //创建一个部署对象
				 .name("并行网管")//添加部署的名称
				 .addInputStream("paralleGateWay.bpmn", inputStreamBpmn)
				 .addInputStream("paralleGateWay.png", inputStreamPng)
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
		String processDefinitionKey="paralleGateWay";
		ProcessInstance processInstance= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
			.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key的启动实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		System.out.println("流程实例ID:"+processInstance.getId());//流程实例ID
		System.out.println("流程实例ID:"+processInstance.getProcessDefinitionId());//流程定义ID
	}
	
	/**
	 * 查询当前人的个人任务
	 */
	@Test
	public void findMyPersonTask(){
		String assignee = "王五";
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
	public void completeMyPersonalTask(){
		//任务ID
		String taskId="4402";
		processEngine.getTaskService()//与正在执行的任务管理相关的Service
			.complete(taskId);
		System.out.println("完成任务: 任务ID: "+taskId);
	}
}
