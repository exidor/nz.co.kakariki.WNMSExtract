select r,max(occ) as mx,avg(occ) as av, max(occ)-avg(occ) as delta 
from (
	select aptype_rncpart(id) as r,aptype_appart(id) as a,avg(occ) as occ
	from rncapo 
	where aptype(id) like '%tmu'
	and ts between date_trunc('week',now() - interval '2 week') 
		and date_trunc('week',now() - interval '1 week')
	group by aptype_rncpart(id),aptype_appart(id)
) a
group by r