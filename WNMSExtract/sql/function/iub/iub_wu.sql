-- wu weekly union (intermediate table function)
--create type iub_wu_container as (id varchar(8), rlsrequ double precision, rlsunsu double precision, rlru double precision, imadlmmu double precision, imadlamu double precision);
create or replace function iub_wu(timestamp) returns setof iub_wu_container as $$
select id,
rls_req as rls_req_u,
rls_uns as rls_uns_u,
rlr as rlr_u,
ima_xl_mx as ima_xl_mx_u,
ima_xl_av as ima_xl_av_u,
pcm_nb
from (
	select id,rls_req,rls_uns,rlr,ima_xl_mx,ima_xl_av, pcm_nb from iub_wm($1,0)
	union
	select id,rls_req,rls_uns,rlr,ima_xl_mx,ima_xl_av, pcm_nb from iub_wm($1,1)
	union
	select id,rls_req,rls_uns,rlr,ima_xl_mx,ima_xl_av, pcm_nb from iub_wm($1,2)
	union
	select id,rls_req,rls_uns,rlr,ima_xl_mx,ima_xl_av, pcm_nb from iub_wm($1,3)
) a
$$ language 'sql';
