package junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestActiviti {

	/**
	 * 使用代码创建工作流需要的23张表
	 */
	@Test
	public void createTable(){
		ProcessEngineConfiguration processEngineConfiguration=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useUnicode=true&characterEncoding=utf8");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("123");
		//DB_SCHEMA_UPDATE_TRUE  表示如果表不存在，自动创建表
		//DB_SCHEMA_UPDATE_FALSE  不自动创建表，需要表存在
		//DB_SCHEMA_UPDATE_CREATE_DROP  先删除表再创建表
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//工作流的核心对象
		ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine:"+processEngine);
		
	}
	
	/**
	 * 使用配置文件创建工作流的23张表
	 */
	@Test
	public void createTableByXml(){
		ProcessEngine processEngine=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		System.out.println("processEngine:"+processEngine);
	}
}
