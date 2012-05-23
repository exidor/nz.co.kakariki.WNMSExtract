create or replace view map_bts_fdd as
select b.beid, b.bcid, b.lcid as blcid, f.fcid, f.lcid as flcid
from snap_fdd_local f
join snap_bts_local b on f.lcid = b.lcid;