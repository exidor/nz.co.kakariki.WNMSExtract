create or replace view int_inode_vcc as
select 
	s.rid,
	s.aid,
	s.vid,
	v.ts,
	s.remoteAtmInterfaceLabel as IuType,
	s.correlationTag,
	case when rxtrafficdesctype = 'sameAsTx' 
		then vcc_rate_divisor(txtrafficdesctype, vsacvccingresscellcountclp01, txtrafficdescparmvalue1, txtrafficdescparmvalue2)
		else vcc_rate_divisor(rxtrafficdesctype, vsacvccingresscellcountclp01, rxtrafficdescparmvalue1, rxtrafficdescparmvalue2)
	end as rxUtilisation,
	case when txtrafficdesctype = 'sameAsRx' 
		then vcc_rate_divisor(rxtrafficdesctype, vsacvccegresscellcountclp01, rxtrafficdescparmvalue1, rxtrafficdescparmvalue2)
		else vcc_rate_divisor(txtrafficdesctype, vsacvccegresscellcountclp01, txtrafficdescparmvalue1, txtrafficdescparmvalue2)
	end as txUtilisation

from snap_rnc_vcc s
join raw_inode_vcc v
on s.rid=v.inid
and s.aid=v.apid
and s.vid=v.vid
order by s.rid,s.aid,s.vid,v.ts