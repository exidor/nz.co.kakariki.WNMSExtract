create or replace view int_iub_radio as
select
substring(ucid, 0, 6) as id,
ts,
sum(vsradiolinksetupunsuccesslackbwthiub) as rls_uns,
sum(interm_rnccn_radiolinksetuprequestsrl008cr(
	vsradiolinksetuprequestcsspeech, vsradiolinksetuprequestcsspeechpsdch,
	vsradiolinksetuprequestcsspeechhsdpa, vsradiolinksetuprequestcsspeechpsdchpshsdpa,
	vsradiolinksetuprequestcsspeechpsdchpsdch, vsradiolinksetuprequestcsdata,
	vsradiolinksetuprequestcsdatapsdch,vsradiolinksetuprequestcsdatapshsdpa,
	vsradiolinksetuprequestcsstr, vsradiolinksetuprequestother,
	vsradiolinksetuprequestpsdchdldchul, vsradiolinksetuprequestpshsdpadledchul,
	vsradiolinksetuprequestpshsdpadchul, vsradiolinksetuprequestpshsdpadldchedchul,
	vsradiolinksetuprequestpsdchpsdch, vsradiolinksetuprequestpsdchpshsdpa,
	vsradiolinksetuprequestcsspeechpsdch, vsradiolinksetuprequestcsdatapshsdpa,
	vsradiolinksetuprequestsig)
	) as rls_req
from raw_rnccn_utrancell
group by substring(ucid, 0, 6), ts;
