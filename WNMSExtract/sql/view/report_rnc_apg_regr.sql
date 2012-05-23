create or replace view report_rnc_apg_regr as select
id,ap,
(x2*y1 - x1*xy)/d as a,
(n*xy - x1*y1)/d as b,
(((x2*y1 - x1*xy)/d) * y1 + ((n*xy - x1*y1)/d)*xy - y1 * y1 /n) / (y2 - y1 * y1/n) as r
from (
	select * from (
		select
			id,ap,
			count(*) as n,
			sum(extract(epoch from wk)) as x1,
			sum(extract(epoch from wk)*extract(epoch from wk)) as x2,
			sum(occ) as y1,
			sum(extract(epoch from wk)) as y2,
			sum(extract(epoch from wk)*occ) as xy,
			(count(*)*sum(extract(epoch from wk)*extract(epoch from wk)))-(sum(extract(epoch from wk))*sum(extract(epoch from wk))) as d
		from report_rnc_apg_t
		where wk>now()::timestamp - interval'3month'
		group by id,ap
	) sub
	where d != 0
) calc
;