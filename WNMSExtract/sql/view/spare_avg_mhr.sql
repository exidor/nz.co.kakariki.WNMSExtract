create or replace view spare_avg_mhr as
select
	t1.id,
	'spare'::text AS ap,
	t1.hr,
	max(avgSpareTmu(t1.mocc,t2.mocc,t3.mocc,t4.mocc,t5.mocc,t6.mocc,t7.mocc,t8.mocc,t9.mocc,t10.mocc,t11.mocc,t12.mocc)) as mocc
from (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/10/1') t1
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/11/1') t2 on t1.hr=t2.hr and t1.id=t2.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/12/1') t3 on t1.hr=t3.hr and t1.id=t3.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/12/5') t4 on t1.hr=t4.hr and t1.id=t4.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/13/1') t5 on t1.hr=t5.hr and t1.id=t5.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/13/5') t6 on t1.hr=t6.hr and t1.id=t6.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/2/1') t7 on t1.hr=t7.hr and t1.id=t7.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/3/1') t8 on t1.hr=t8.hr and t1.id=t8.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/4/1') t9 on t1.hr=t9.hr and t1.id=t9.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/5/1') t10 on t1.hr=t10.hr and t1.id=t10.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/6/1') t11 on t1.hr=t11.hr and t1.id=t11.id
join (select substring(id,4,strpos(id,'/')-4) as id,hr,mocc from rncap_borg_mhr where id like '%/7/1') t12 on t1.hr=t12.hr and t1.id=t12.id
group by t1.id,t1.hr;