#�����������̶�����صı�
select * from act_re_deployment  #��������

select * from act_re_procdef  #���̶����

select * from act_ge_bytearray  #��Դ�ļ���

select * from act_ge_property  #�������ɲ��Ա�

#����ʵ����ִ�ж�������
select * from act_ru_execution  #����ִ�е�ִ�ж����    �ֶ� ID_ ��ִ�ж���ID  PROC_INST_ID_ ������ʵ��ID  ACT_ID ����ִ��

select * from act_hi_procinst  #����ʵ������ʷ��    û�н��� END_TIME�ֶ�Ϊnull

select * from act_ru_task  #����ִ�е������(ֻ�нڵ���uerTask��ʱ�򣬸ñ��д�������)

select * from act_hi_taskinst  #������ʷ��ֻ�нڵ���userTask��ʱ�򣬸ñ��д������ݣ�

select * from act_hi_actinst  #���л�ڵ����ʷ��

#���̱���
select * from act_ru_variable #����ִ�е����̱�����

select * from act_hi_varinst #��ʷ���̱�����

#������
select * from act_ru_identitylink  #�����(��������������)

select * from act_hi_identitylink  #��ʷ��������˱�(��������������)

select * from act_id_group  #��ɫ��

select * from act_id_group  #�û���

select * from act_id_membership #�û���ɫ������
