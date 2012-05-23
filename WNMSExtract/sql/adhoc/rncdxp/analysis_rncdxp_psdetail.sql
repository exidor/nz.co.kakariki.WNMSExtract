create view analysis_rncdxp_psdetail as
select
rcid,ts,
sum(rrcattconnestaborigbgrdcall) as psob,
sum(rrcattconnestaborigintactcall) as psoi,
sum(rrcattconnestaborigstrmcal) as psos,
sum(rrcattconnestabtermbgrdcall) as pstb,
sum(rrcattconnestabtermintactcall) as psti,
sum(rrcattconnestabtermstrmcall) as psts,
sum(vsdedicateddownlinkkbytesrlcreferencecelldlrabhsdpa) as psdmhsdpa,
sum(vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib128) as psdmib128,
sum(vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib16) as psdmib16,
sum(vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib256) as psdmib256,
sum(vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib32) as psdmib32,
sum(vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib384) as psdmib384,
sum(vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib64) as psdmib64,
sum(vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib8) as psdmib8,
sum(vsdedicateddownlinkkbytesrlcreferencecelldlrabpsstrother) as psdmibx,
sum(vsdedicateduplinkkbytesrlcreferencecellulrabhsupa) as psumhsdpa,
sum(vsdedicateduplinkkbytesrlcreferencecellulrabpsib128) as psumib128
from raw_rnccn_utrancell
group by rcid,ts
order by rcid,ts;