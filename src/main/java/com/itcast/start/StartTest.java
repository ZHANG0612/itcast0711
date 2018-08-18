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
	 * 部署流程定义（从zip）
	 * act_re_deployment部署对象表
	 * act_re_procdef流程定义表
	 * act_ge_vytearray资源文件表
	 * act_ge_property主键生成策略表
	 */
	@Test
	public void deploymentProcessDefinitionInputStream(){
		InputStream inputStreamBpmn=this.getClass().getResourceAsStream("start.bpmn");
		InputStream inputStreamPng=this.getClass().getResourceAsStream("start.png");
		Deployment deployment= processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
				 .createDeployment() //创建一个部署对象
				 .name("开始活动")//添加部署的名称
				 .addInputStream("start.bpmn", inputStreamBpmn)
				 .addInputStream("start.png", inputStreamPng)
				 .deploy();//完成部署
				System.out.println("部署ID:"+deployment.getId());//1
				System.out.println("部署名称:"+deployment.getName());//helloworld入门程序
	}
	
	/**
	 * 启动流程实例，判断流程是否结束，查询历史
	 */
	@Test
	public void startProcessInstance(){
		//流程定义的key
		String processDefinitionKey="start";
		ProcessInstance processInstance= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
			.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key的启动实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		System.out.println("流程实例ID:"+processInstance.getId());//流程实例ID
		System.out.println("流程实例ID:"+processInstance.getProcessDefinitionId());//流程定义ID
		
		/**判断流程是否结束
		 * 查询正在执行的执行对象表
		 * */
		ProcessInstance rProcessInstance= processEngine.getRuntimeService()//
		.createProcessInstanceQuery()//创建流程实例查询对象
			.processInstanceId(processInstance.getId())
			.singleResult();
		//说明流程实例结束
		if(rProcessInstance==null){
			/**查询历史，获取流程的相关信息**/
			HistoricProcessInstance hpi =processEngine.getHistoryService()
			.createHistoricProcessInstanceQuery()
			.processInstanceId(processInstance.getId())
			.singleResult();
			
			System.out.println(hpi.getId()+"\t"+hpi.getStartTime()+"\t"+hpi.getEndTime()+"\t"+hpi.getDurationInMillis());
			 
		}
	}
}
