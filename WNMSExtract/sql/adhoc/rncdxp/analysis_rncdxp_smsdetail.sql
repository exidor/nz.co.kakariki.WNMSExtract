create view analysis_rncdxp_smsdetail as
select
rcid,ts,
sum(rrcattconnestaboriglowpriosig) as smsolp,
sum(rrcattconnestabtermlowpriosig) as smstlp,
sum(rrcattconnestabtermunknown) as smstu
from raw_rnccn_utrancell
group by rcid,ts
order by rcid,ts;