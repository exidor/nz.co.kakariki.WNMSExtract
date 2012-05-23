-- create type iub_wm_container as (id varchar(8), ts timestamp, rls_req double precision, rls_uns double precision, rlr double precision, ima_dl_mm double precision, ima_dl_am double precision);
create or replace function iub_wm(timestamp,int) returns setof iub_wm_container as $$
select
b.id,
b.wk,
b.rls_req,
b.rls_uns,
case when b.rls_req=0 then 0
     else b.rls_uns/b.rls_req
end as rlr,
a.ima_xl_mx,
a.ima_xl_av,
a.pcm_nb
from (
	select
		i.id,
		date_trunc('week',i.ts) as wk,
		min(i.rls_req) as rls_req,
		i.rls_uns
	from (
		select x.id, x.ts,x.pcm_nb,y.rls_req,y.rls_uns
		from (
			select id,ts,rls_req,rls_uns
			from int_iub_radio_t
		) y
		join (
			select id,ts,pcm_nb
			from int_iub_t
		)x
		on x.id=y.id
		and x.ts=y.ts
		) i
	join (
		select id,date_trunc('week',ts) as wk,
			max(rls_uns) as mv
		from int_iub_radio_t
		where ts between date_trunc('week',$1) - ($2||' week')::interval
			and date_trunc('week',$1) - ($2||' week')::interval + interval '6 day 23:59:59'
		group by id,date_trunc('week',ts)
	) m
	on m.id=i.id
	and m.mv=i.rls_uns
	and m.wk=date_trunc('week',i.ts)
	group by i.id,date_trunc('week',i.ts),i.rls_uns
) b
join (
	select
		id,date_trunc('week',ts) as wk,
		max(max2col(ima_dl_mx,ima_ul_mx)) as ima_xl_mx,
		max(max2col(ima_dl_av,ima_ul_av)) as ima_xl_av,
		max(pcm_nb) as pcm_nb
		from int_iub_t
		where ts between date_trunc('week',$1) - ($2||' week')::interval
			and date_trunc('week',$1) - ($2||' week')::interval + interval '6 day 23:59:59'
		group by id,date_trunc('week',ts)
) a
on a.id=b.id
and a.wk=b.wk
order by b.id,b.wk
$$ language 'sql';