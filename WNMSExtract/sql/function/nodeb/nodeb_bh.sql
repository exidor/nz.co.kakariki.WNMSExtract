-- create type nodeb_bh_container as (id varchar(8), ts timestamp, val double precision);
create or replace function nodeb_bh(timestamp,int,varchar) returns setof nodeb_bh_container as $$
	 select
		d1.id,
		d1.ts,
		d1.val
		from (
			select
				id,
				ts,
				case when $3='data' then data_bhi
					when $3='speech' then speech_bhi
					when $3='combined' then combined_bhi
					when $3='attempts' then rrc_bhi
		    	end as val
			from bhi_nodeb
			where ts between date_trunc('week',$1) - ($2||' week')::interval
				and date_trunc('week',$1) - ($2||' week')::interval + interval '6 day 23:59:59'
		) d1
join (
	select
		id,
		date_trunc('week',ts) as bh,
		case when $3='data' then max(data_bhi)
			when $3='speech' then max(speech_bhi)
			when $3='combined' then max(combined_bhi)
			when $3='attempts' then max(rrc_bhi)
    end as val
	from bhi_nodeb
	where bhi_flag(speech_bhi,data_bhi,combined_bhi,rrc_bhi)<15
		and ts between date_trunc('week',$1) - ($2||' week')::interval
			and date_trunc('week',$1) - ($2||' week')::interval + interval '6 day 23:59:59'
	group by id,date_trunc('week',ts)
) d2
on d1.id=d2.id
and d1.val=d2.val;

$$ language 'sql';