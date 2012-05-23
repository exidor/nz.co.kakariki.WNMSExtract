create or replace view report_borg_daily as
select
id,
ap,
date_trunc('day',ts) as dy,
max(occ) as aocc
from (
	-- avg occ over ap types
	select
		id,
		ap,
		ts,
		case
	    	when ap = 'tmu' then sum(occ) / 10
	    	else avg(occ)
	    end as occ
	from (
		-- split id/ap labels
		select
			substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,
			substring(aptype(id), strpos(aptype(id), '/') + 1) AS ap,
			ts,
			occ
		from rncap_borg

		union

		select
			t1.id,
			'spare' AS ap,
			t1.ts,
			avgSpareTmu(t1.occ,t2.occ,t3.occ,t4.occ,t5.occ,t6.occ,t7.occ,t8.occ,t9.occ,t10.occ,t11.occ,t12.occ) as occ

		from (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%10/1') t1
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%11/1') t2 on t1.ts=t2.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%12/1') t3 on t1.ts=t3.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%12/5') t4 on t1.ts=t4.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%13/1') t5 on t1.ts=t5.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%13/5') t6 on t1.ts=t6.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%2/1') t7 on t1.ts=t7.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%3/1') t8 on t1.ts=t8.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%4/1') t9 on t1.ts=t9.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%5/1') t10 on t1.ts=t10.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%6/1') t11 on t1.ts=t11.ts
		join (select substring(aptype(id), 1, strpos(aptype(id), '/') - 1) AS id,ts,occ from rncap_borg where id like '%7/1') t12 on t1.ts=t12.ts

	) b
	where ap like 'tmu'
	or ap like 'rab'
	or ap like 'pc'
	group by id,ap,ts
) a
group by id,ap,date_trunc('day',ts)
;
