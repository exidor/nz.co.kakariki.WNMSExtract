create view analysis_rncdxp_csdetail as
select
rcid,ts,
sum(rrcattconnestaborigconvcall) as csoc,
sum(rrcattconnestabtermconvcall) as cstc,
sum(vsrabmeancsvsumcum/36000) as cserl
from raw_rnccn_utrancell
group by rcid,ts
order by rcid,ts;