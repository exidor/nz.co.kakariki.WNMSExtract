create or replace view report_borg_daily_regr as select
id,ap,
(x2*y1 - x1*xy)/d as a,
(n*xy - x1*y1)/d as b,
(((x2*y1 - x1*xy)/d) * y1 + ((n*xy - x1*y1)/d)*xy - y1 * y1 /n) / (y2 - y1 * y1/n) as r
from (
	select
		id,ap,
		count(*) as n,
		sum(extract(epoch from dy)) as x1,
		sum(extract(epoch from dy)*extract(epoch from dy)) as x2,
		sum(aocc) as y1,
		sum(extract(epoch from dy)) as y2,
		sum(extract(epoch from dy)*aocc) as xy,
		(count(*)*sum(extract(epoch from dy)*extract(epoch from dy)))-(sum(extract(epoch from dy))*sum(extract(epoch from dy))) as d
	from report_borg_daily_t
	where dy>now()::timestamp - interval'3week'
	group by id,ap
) calc
;