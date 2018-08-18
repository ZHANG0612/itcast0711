package com.itcast.processDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessDefinitionTest {

	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	/**
	 * �������̶��壨��classpath��
	 * act_re_deployment��������
	 * act_re_procdef���̶����
	 * act_ge_vytearray��Դ�ļ���
	 * act_ge_property�������ɲ��Ա�
	 */
	@Test
	public void deploymentProcessDefinitionClasspath(){
		Deployment deployment= processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
				 .createDeployment() //����һ���������
				 .name("���̶���")//��Ӳ��������
				 .addClasspathResource("diagrams/helloworld.bpmn")//��classpath����Դ�м��أ�һ��ֻ�ܼ���һ���ļ�
				 .addClasspathResource("diagrams/helloworld.png")//��classpath����Դ�м��أ�һ��ֻ�ܼ���һ���ļ�
				 .deploy();//��ɲ���
				System.out.println("����ID:"+deployment.getId());//1
				System.out.println("��������:"+deployment.getName());//helloworld���ų���
	}
	
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
	 * ��ѯ���̶���
	 */
	@Test
	public void findProcessDefinition(){
			List<ProcessDefinition> list = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
			.createProcessDefinitionQuery()//����һ�����̶���Ĳ�ѯ
			 //ָ����ѯ����	
			//.deploymentId(deploymentId);//ʹ�����̶���ID��ѯ
			//.processDefinitionKey(processDefinitionKey)//ʹ�����̶����key��ѯ
			//.processDefinitionNameLike(processDefinitionNameLike)//ʹ�����̶��������ģ����ѯ
			//����
			.orderByProcessDefinitionVersion().asc()//���հ汾����������
			//.orderByProcessDefinitionName().desc()//�������̶�������ƽ�������
			//���ؽ����
			.list();//����һ�������б���װ���̶���
			//.singleResult();//���ؽ��������
			//.listPage(firstResult, maxResult);//��ҳ��ѯ
			
			if(list!=null && list.size()>0){
				for(ProcessDefinition pd:list){
					System.out.println("���̶���ID:"+pd.getId());//���̶����key+�汾+���������
					System.out.println("���̶��������:"+pd.getName());//��Ӧhelloworld.bpmn�ļ��е�name����ֵ
					System.out.println("���̶����key:"+pd.getKey());//��Ӧhelloworld.bpmn�ļ��е�id����ֵ
					System.out.println("���̶���İ汾:"+pd.getVersion());//�����̶����key��ͬ�£��汾������Ĭ�ϣ�1
					System.out.println("��Դ����bpmn�ļ�:"+pd.getResourceName());
					System.out.println("��Դ����png�ļ�:"+pd.getDiagramResourceName());
					System.out.println("�������ID:"+pd.getDeploymentId());
					System.out.println("######################");
				}
			}
	}
	
	/**
	 * ɾ�����̶���
	 */
	@Test
	public void deleteProcessDefinition(){
		//ʹ�ò���ID�����ɾ��
		String deploymentId="1";
		/**
		 * ����������ɾ��
		 * 		ֻ��ɾ��û�����������̣���������������ͻ��׳��쳣
		 */
		processEngine.getRepositoryService().deleteDeployment(deploymentId);
		/**
		 * ����ɾ��
		 *      ���������Ƿ�������������ɾ��
		 */
		processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
		System.out.println("ɾ���ɹ�");
	}
	
	/**
	 * �鿴����ͼ
	 * @throws IOException 
	 */
	@Test
	public void viewPic() throws IOException{
		//�����ɵ�ͼƬ�ŵ��ļ�����
		String deploymentId="601";
		String resourceName="";
		//��ȡͼƬ��Դ����
		List<String> list = processEngine.getRepositoryService()//
			.getDeploymentResourceNames(deploymentId);
		if(list!=null && list.size()>0){
			for(String name:list){
				if(name.indexOf(".png")>=0){
					resourceName=name;
				}
			}
		}
		//��ȡͼƬ��������
		InputStream inputStream = processEngine.getRepositoryService()//
			.getResourceAsStream(deploymentId, resourceName);
		//��ͼƬ���ɵ�D��Ŀ¼��
		File file=new File("E:/"+resourceName);
		//����������ͼƬд��E����
		FileUtils.copyInputStreamToFile(inputStream, file);
	}
	
	/**
	 * ��ѯ���°汾�����̶���
	 */
	@Test
	public void findLastVersionProcessDefinition(){
		List<ProcessDefinition> list = processEngine.getRepositoryService()//
			.createProcessDefinitionQuery()//
				.orderByProcessDefinitionVersion().asc()//ʹ�����̶���İ汾��������
					.list();
		/**
		 * Map<String,ProcessDefinition>
		 * map���ϵ�key:���̶����key
		 * map���ϵ�value:���̶���Ķ���
		 * map���ϵ��ص㣬��map����keyֵ��ͬ������£���һ�ε�ֵ���滻ǰһ�ε�ֵ
		 */
		Map<String, ProcessDefinition> map=new LinkedHashMap<String,ProcessDefinition>();
		if(list!=null && list.size()>0){
			for(ProcessDefinition pd:list){
				map.put(pd.getKey(), pd);
			}
		}
		List<ProcessDefinition> pdList=new ArrayList<ProcessDefinition>(map.values());
		if(list!=null && list.size()>0){
			for(ProcessDefinition pd:pdList){
				System.out.println("���̶���ID:"+pd.getId());//���̶����key+�汾+���������
				System.out.println("���̶��������:"+pd.getName());//��Ӧhelloworld.bpmn�ļ��е�name����ֵ
				System.out.println("���̶����key:"+pd.getKey());//��Ӧhelloworld.bpmn�ļ��е�id����ֵ
				System.out.println("���̶���İ汾:"+pd.getVersion());//�����̶����key��ͬ�£��汾������Ĭ�ϣ�1
				System.out.println("��Դ����bpmn�ļ�:"+pd.getResourceName());
				System.out.println("��Դ����png�ļ�:"+pd.getDiagramResourceName());
				System.out.println("�������ID:"+pd.getDeploymentId());
				System.out.println("######################");
			}
		}
	}
	
	/**
	 * ɾ�����̶��壨ɾ��key��ͬ�����еĲ�ͬ�汾�����̶��壩
	 */
	@Test
	public void deleteProcessDefinitionByKey(){
		//���̶����key
		String processDefinitionKey="helloworld";
		//��ʹ�����̶����key��ѯ���̶��壬��ѯ�����еİ汾
		List<ProcessDefinition> list=processEngine.getRepositoryService()//
				.createProcessDefinitionQuery()//
					.processDefinitionKey(processDefinitionKey)//ʹ�����̶����key��ѯ
						.list();
		//��������ȡÿ�����̶���Ĳ���ID
		if(list!=null && list.size()>0){
			for(ProcessDefinition pDefinition:list){
				//��ȡ����ID
				String deploymentId=pDefinition.getDeploymentId();
				processEngine.getRepositoryService()
					.deleteDeployment(deploymentId, true);
			}
		}
	}
}
