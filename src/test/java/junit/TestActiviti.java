package junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestActiviti {

	/**
	 * ʹ�ô��봴����������Ҫ��23�ű�
	 */
	@Test
	public void createTable(){
		ProcessEngineConfiguration processEngineConfiguration=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useUnicode=true&characterEncoding=utf8");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("123");
		//DB_SCHEMA_UPDATE_TRUE  ��ʾ��������ڣ��Զ�������
		//DB_SCHEMA_UPDATE_FALSE  ���Զ���������Ҫ�����
		//DB_SCHEMA_UPDATE_CREATE_DROP  ��ɾ�����ٴ�����
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//�������ĺ��Ķ���
		ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine:"+processEngine);
		
	}
	
	/**
	 * ʹ�������ļ�������������23�ű�
	 */
	@Test
	public void createTableByXml(){
		ProcessEngine processEngine=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		System.out.println("processEngine:"+processEngine);
	}
}
