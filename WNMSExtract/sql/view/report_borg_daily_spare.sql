create or replace view report_borg_daily_spare as
select
id,
ap,
date_trunc('day',hr) as dy,
max(mocc) as aocc
from (
	-- avg occ over ap types
	select
		id,
		ap,
		hr,
		case
	    	when ap = 'tmu' then sum(mocc) / 10
	    	else avg(mocc)
	    end as mocc
	from (
		-- split id/ap labels
		select
			substring(id,4,strpos(id,'/')-4) AS id,
			substring(aptype(id), strpos(aptype(id), '/') + 1) AS ap,
			hr,
			mocc
		from rncap_borg_mhr

		union all

		select id,ap,hr,mocc
		from spare_avg_mhr

	) b
	where ap like 'tmu'
	or ap like 'rab'
	or ap like 'pc'
	or ap like 'spare'
	group by id,ap,hr
) a
group by id,ap,date_trunc('day',hr)
;
