package com.itcast.sequenceFlow;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class SequenceFlowTest {
	
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
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("sequenceFlow.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("sequenceFlow.png");
		Deployment deployment= processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
				 .createDeployment() //创建一个部署对象
				 .name("连线")//添加部署的名称
				 .addInputStream("sequenceFlow.bpmn", inputStreamBpmn)
				 .addInputStream("sequenceFlow.png", inputStreamPng)
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
		String processDefinitionKey="sequenceFlow";
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
		String assignee = "赵六";
		List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的service
			.createTaskQuery()//创建任务查询对象
			/** 查询条件（where部分）*/
				.taskAssignee(assignee)//指定个人任务查询，指定办理人
				//.taskCandidateUser(candidateUser)//组任务的办理人查询
				//.processDefinitionId(processDefinitionId)//使用流程实例ID查询
				//.executionId(executionId)//使用执行对象ID查询
				/**排序 */
				.orderByTaskCreateTime().asc()//使用创建时间的升序排序
				/**返回结果集*/
				//.singleResult()//返回唯一结果集
				//.count()//返回结果集数量
				//.listPage(firstResult, maxResults);//分页查询
				.list();//返回列表
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
		String taskId="3103";
		//完成任务的同时，设置流程变量，使用流程变量用来指定完成任务后，下一个连线，对应sequenceFlow.bpmn文件中的${message=='不重要'}
		Map<String, Object> variables=new HashMap<String,Object>();
		variables.put("message", "重要");
		processEngine.getTaskService()//与正在执行的任务管理相关的Service
			.complete(taskId,variables);
		System.out.println("完成任务: 任务ID: "+taskId);
	}
	
	/**
	 * 查询流程状态(判断流程正在执行，还是结束)
	 */
	@Test
	public void isProcessEnd(){
		String processInstanceId="801";
		ProcessInstance processInstance = processEngine.getRuntimeService()//表示正在执行的流程实例和流程对象
			.createProcessInstanceQuery()//创建流程实例查询
				.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		if(processInstance==null){
			System.out.println("流程已结束");
		}else{
			System.out.println("流程没有结束");
		}
	}
	
	/**
	 * 查询历史任务
	 */
	@Test
	public void findHistoryTask(){
		String taskAssignee="张三";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
				.createHistoricTaskInstanceQuery()//创建历史任务实例查询
					.taskAssignee(taskAssignee)//指定历史任务的办理人
						.list();
		if(list!=null && list.size()>0){
			for(HistoricTaskInstance hInstance:list){
				System.out.println(hInstance.getId()+"\n"+hInstance.getName()+"\n"+hInstance.getProcessInstanceId()+"\n"+hInstance.getStartTime()+"\n"+hInstance.getStartTime()+"\n"+hInstance.getEndTime());
				System.out.println("##################");
			}
		}
	}
	
	/**
	 * 查询历史流程实例
	 */
	@Test
	public void findHistoryProcessInstance(){
		String processInstanceId="801";
		HistoricProcessInstance hProcessInstance =  processEngine.getHistoryService()//与历史数据（历史表）相关的Service
			.createHistoricProcessInstanceQuery()//创建历史流程实例查询
				.processInstanceId(processInstanceId)
					.singleResult();
		System.out.println(hProcessInstance.getId()+" "+hProcessInstance.getProcessDefinitionId()+" "+hProcessInstance.getStartTime()+" "+hProcessInstance.getEndTime()+" ");
	}
}
