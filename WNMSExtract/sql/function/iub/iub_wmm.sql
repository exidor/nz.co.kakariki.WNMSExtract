--create type iub_wmm_container as (id varchar(8), imadlmmm double precision, imadlamm double precision, rlsreqm double precision, rlsunsm double precision, rlrm double precision);
create or replace function iub_wmm(timestamp) returns setof iub_wmm_container as $$
-- the naive implementation
select
m.id,
m.ima_xl_mmx as ima_xl_mmx,
m.ima_xl_mav as ima_xl_mav,
m.ima_xl_aav as ima_xl_aav,
i.rls_req_u as rls_req_mx,
i.rls_uns_u as rls_uns_mx,
i.rlr_u as rlr_mx,
i.pcm_nb as pcm_nb
from (
	select
		id,
		max(ima_xl_mx_u) as ima_xl_mmx,
		max(ima_xl_av_u) as ima_xl_mav,
		avg(ima_xl_av_u) as ima_xl_aav
	from iub_wu($1)
	group by id
) m
left join (
	select
	a.id,
	a.rls_req_u,
	a.rls_uns_u,
	a.pcm_nb,
	a.rlr_u
	from iub_wu($1) a
	join (
		select id,max(rlr_u) as rlr_mx
		from iub_wu($1)
		group by id
		having max(rlr_u)>0
	) b
	on a.id=b.id
	and a.rlr_u=b.rlr_mx
) i
on m.id=i.id
$$ language 'sql';