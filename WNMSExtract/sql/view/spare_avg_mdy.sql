create or replace view spare_avg_mdy as
select
	t1.id,
	'spare'::text AS ap,
	t1.dy,
	max(avgSpareTmu(t1.mocc,t2.mocc,t3.mocc,t4.mocc,t5.mocc,t6.mocc,t7.mocc,t8.mocc,t9.mocc,t10.mocc,t11.mocc,t12.mocc)) as mocc
from (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/10/1') t1
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/11/1') t2 on t1.dy=t2.dy and t1.id=t2.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/12/1') t3 on t1.dy=t3.dy and t1.id=t3.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/12/5') t4 on t1.dy=t4.dy and t1.id=t4.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/13/1') t5 on t1.dy=t5.dy and t1.id=t5.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/13/5') t6 on t1.dy=t6.dy and t1.id=t6.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/2/1') t7 on t1.dy=t7.dy and t1.id=t7.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/3/1') t8 on t1.dy=t8.dy and t1.id=t8.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/4/1') t9 on t1.dy=t9.dy and t1.id=t9.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/5/1') t10 on t1.dy=t10.dy and t1.id=t10.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/6/1') t11 on t1.dy=t11.dy and t1.id=t11.id
join (select substring(id,4,strpos(id,'/')-4) as id,dy,mocc from rncap_borg_mdy where id like '%/7/1') t12 on t1.dy=t12.dy and t1.id=t12.id
group by t1.id,t1.dy;
;
