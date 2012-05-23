package com.alcatel_lucent.nz.wnmsextract.database.rnccn;
/*
 * This file is part of wnmsextract.
 *
 * wnmsextract is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * wnmsextract is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.rnccn.RncFunctionType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.rnccn.UtranCellType;

/*
drop table raw_rnccn_utrancell;
create table raw_rnccn_utrancell(
rcid varchar(12),
ts timestamp,
rfid int,
ucid varchar(12),
RABAttEstabCSSpeechConv decimal,
RABAttEstabPSTrChnDCHHSDSCH decimal,
RABAttEstabPSTrChnEDCHHSDSCH decimal,
RABSuccEstabCSSpeechConv decimal,
RABSuccEstabPSTrChnDCHHSDSCH decimal,
RABSuccEstabPSTrChnEDCHHSDSCH decimal,
RRCAttConnEstabCallReestab decimal,
RRCAttConnEstabDetach decimal,
RRCAttConnEstabEmergency decimal,
RRCAttConnEstabIRATCCO decimal,
RRCAttConnEstabIRATCellResel decimal,
RRCAttConnEstabOrigBgrdCall decimal,
RRCAttConnEstabOrigConvCall decimal,
RRCAttConnEstabOrigHighPrioSig decimal,
RRCAttConnEstabOrigIntactCall decimal,
RRCAttConnEstabOrigLowPrioSig decimal,
RRCAttConnEstabOrigStrmCal decimal,
RRCAttConnEstabOrigSubscCall decimal,
RRCAttConnEstabRegistration decimal,
RRCAttConnEstabSpare12 decimal,
RRCAttConnEstabTermBgrdCall decimal,
RRCAttConnEstabTermConvCall decimal,
RRCAttConnEstabTermHighPrioSig decimal,
RRCAttConnEstabTermIntactCall decimal,
RRCAttConnEstabTermLowPrioSig decimal,
RRCAttConnEstabTermStrmCall decimal,
RRCAttConnEstabTermUnknown decimal,
RRCSuccConnEstabEmergency decimal,
RRCSuccConnEstabOrigBgrdCall decimal,
RRCSuccConnEstabOrigConvCall decimal,
RRCSuccConnEstabOrigIntactCall decimal,
RRCSuccConnEstabTermBgrdCall decimal,
RRCSuccConnEstabTermConvCall decimal,
RRCSuccConnEstabTermIntactCall decimal,
VSCallEstablishmentDurationConversationalCum decimal,
VSCallEstablishmentDurationConversationalNbEvt decimal,
VSCallEstablishmentDurationBackgroundCum decimal,
VSCallEstablishmentDurationInteractiveCum decimal,
VSCallEstablishmentDurationBackgroundNbEvt decimal,
VSCallEstablishmentDurationInteractiveNbEvt decimal,
VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm10p2 decimal,
VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm12p2 decimal,
VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm4p75 decimal,
VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm5p15 decimal,
VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm5p9 decimal,
VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm6p7 decimal,
VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm7p4 decimal,
VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm7p95 decimal,
VSDdUlAmrWbABtBadFrmAmrWbRt1265 decimal,
VSDdUlAmrWbABtBadFrmAmrWbRt660 decimal,
VSDdUlAmrWbABtBadFrmAmrWbRt885 decimal,
VSDdUlAmrWbABtGoodFrmAmrWbRt1265 decimal,
VSDdUlAmrWbABtGoodFrmAmrWbRt660 decimal,
VSDdUlAmrWbABtGoodFrmAmrWbRt885 decimal,
VSDedicatedDownlinkActivityRlcRefCellDlRabHsdpa decimal,
VSDedicatedDownlinkKbytesRlcActiveCellsDlRabCsSpeech decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabCsData64 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabCsSpeech decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabCsStr decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabHsdpa decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabOther decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb128 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb16 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb256 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb32 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb384 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb64 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb8 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStr128 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStr256 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStr384 decimal,
VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStrOther decimal,
VSDedicatedUplinkActivityRlcRefCellUlRabPsIb16 decimal,
VSDedicatedUplinkKbytesRlcReferenceCellUlRabHsupa decimal,
VSDedicatedUplinkKbytesRlcReferenceCellUlRabPsIb128 decimal,
VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsDataCum decimal,
VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsDataNbEvt decimal,
VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsSpeechNbLrAmrAvg float,
VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsSpeechNbLrAmrCum decimal,
VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsSpeechWbAmrCum decimal,
VSDownsizingStep1SuccessDchHsdpa decimal,
VSEdchSucMobilityEdchCallToEdchCallInterFreqMob decimal,
VSEdchSucMobilityEdchCallToEdchCallIntraFreqMob decimal,
VSEdchSucMobilityEdchCallToNonEdchCallInterFreqMob decimal,
VSEdchSucMobilityEdchCallToNonEdchCallIntraFreqMob decimal,
VSEdchSucMobilityEdchCallTTI10ToEdchCallTTI2 decimal,
VSEdchSucMobilityEdchCallTTI2ToEdchCallTTI10 decimal,
VSEdchSucMobilityEdchCallTTI2ToEdchCallTTI2 decimal,
VSEdchSucMobilityNonEdchCallToEdchCallInterFreqMob decimal,
VSEdchSucMobilityNonEdchCallToEdchCallIntraFreqMob decimal,
VSEdchUnsucMobilityEdchCallToEdchCallInterFreqMob decimal,
VSEdchUnsucMobilityEdchCallToEdchCallIntraFreqMob decimal,
VSEdchUnsucMobilityEdchCallToNonEdchCallInterFreqMob decimal,
VSEdchUnsucMobilityEdchCallToNonEdchCallIntraFreqMob decimal,
VSEdchUnsucMobilityEdchCallTTI10ToEdchCallTTI2 decimal,
VSEdchUnsucMobilityEdchCallTTI2ToEdchCallTTI10 decimal,
VSEdchUnsucMobilityEdchCallTTI2ToEdchCallTTI2 decimal,
VSEdchUnsucMobilityNonEdchCallToEdchCallInterFreqMob decimal,
VSEdchUnsucMobilityNonEdchCallToEdchCallIntraFreqMob decimal,
VSFirstRrcConnectionRequestEmergency decimal,
VSFirstRrcConnectionRequestOrigBgrdCall decimal,
VSFirstRrcConnectionRequestOrigConvCall decimal,
VSFirstRrcConnectionRequestOrigIntactCall decimal,
VSFirstRrcConnectionRequestTermBgrdCall decimal,
VSFirstRrcConnectionRequestTermConvCall decimal,
VSFirstRrcConnectionRequestTermIntactCall decimal,
VSHsdpaMobilitySuccessHsdpaToHsdpa decimal,
VSHsdpaMobilitySuccessHsdpaToHsdpaInterFreq decimal,
VSHsdpaMobilitySuccessHsdpaToNonHsdpa decimal,
VSHsdpaMobilitySuccessHsdpaToNonHsdpaInterFreq decimal,
VSHsdpaMobilitySuccessNonHsdpaToHsdpa decimal,
VSHsdpaMobilitySuccessNonHsdpaToHsdpaInterFreq decimal,
VSHsdpaMobilityUnsuccessfulHsdpaToHsdpa decimal,
VSHsdpaMobilityUnsuccessfulHsdpaToHsdpaInterFreq decimal,
VSHsdpaMobilityUnsuccessfulHsdpaToNonHsdpa decimal,
VSHsdpaMobilityUnsuccessfulHsdpaToNonHsdpaInterFreq decimal,
VSHsdpaMobilityUnsuccessfulNonHsdpaToHsdpa decimal,
VSHsdpaMobilityUnsuccessfulNonHsdpaToHsdpaInterFreq decimal,
VSIncomInterFreqHoAttRescue decimal,
VSIncomInterFreqHoAttService decimal,
VSIncomInterFreqHoSucRescue decimal,
VSIncomInterFreqHoSucService decimal,
VSIntraRncOutInterFreqHoAttemptHoWithCmMeasInterBand decimal,
VSIntraRncOutInterFreqHoFailHoWithCmMeasInterBand decimal,
VSIRMTimeDlCodesSF16RsrvHsAvg float,
VSIRMTimeFreeDlCodesBySpreadingFactor128Avg float,
VSIRMTimeFreeDlCodesBySpreadingFactor16Avg float,
VSIRMTimeFreeDlCodesBySpreadingFactor256Avg float,
VSIRMTimeFreeDlCodesBySpreadingFactor32Avg float,
VSIRMTimeFreeDlCodesBySpreadingFactor4Avg float,
VSIRMTimeFreeDlCodesBySpreadingFactor64Avg float,
VSIRMTimeFreeDlCodesBySpreadingFactor8Avg float,
VSIuAbnormalReleaseRequestCsDlAsCnfCsSpeechNbLrAmr decimal,
VSIuAbnormalReleaseRequestCsDlAsCnfCsSpeechWbAmr decimal,
VSIuAbnormalReleaseRequestPsDlAsCnfHsdpa decimal,
VSIuDlAmrFrmFqcFrmBad decimal,
VSIuDlAmrFrmFqcFrmBadRadio decimal,
VSIuDlAmrFrmFqcFrmGood decimal,
VSIuDlAmrWbFrmFqcFrmBad decimal,
VSIuDlAmrWbFrmFqcFrmBadRadio decimal,
VSIuDlAmrWbFrmFqcFrmGood decimal,
VSIuReleaseCompleteCsDlAsCnfCsSpeechNbLrAmr decimal,
VSIuReleaseCompleteCsDlAsCnfCsSpeechWbAmr decimal,
VSOutGoInterFreqHoAttRescue decimal,
VSOutGoInterFreqHoAttService decimal,
VSOutGoInterFreqHoSucRescue decimal,
VSOutGoInterFreqHoSucService decimal,
VSPagingCancelledRecords decimal,
VSPagingDelayedRecords decimal,
VSPagingMessagesSentOnPcch decimal,
VSPagingRecordsUnscheduledCsTerminatingBackgroundCall decimal,
VSPagingRecordsUnscheduledCsTerminatingCauseUnknown decimal,
VSPagingRecordsUnscheduledCsTerminatingConversationalCall decimal,
VSPagingRecordsUnscheduledCsTerminatingHighPrioritySignalling decimal,
VSPagingRecordsUnscheduledCsTerminatingInteractiveCall decimal,
VSPagingRecordsUnscheduledCsTerminatingLowPrioritySignalling decimal,
VSPagingRecordsUnscheduledCsTerminatingStreamingCall decimal,
VSPagingRecordsUnscheduledPsTerminatingBackgroundCall decimal,
VSPagingRecordsUnscheduledPsTerminatingCauseUnknown decimal,
VSPagingRecordsUnscheduledPsTerminatingConversationalCall decimal,
VSPagingRecordsUnscheduledPsTerminatingHighPrioritySignalling decimal,
VSPagingRecordsUnscheduledPsTerminatingInteractiveCall decimal,
VSPagingRecordsUnscheduledPsTerminatingLowPrioritySignalling decimal,
VSPagingRecordsUnscheduledPsTerminatingStreamingCall decimal,
VSPagingRejectedRequests decimal,
VSRABMeanCSVSumCum decimal,
VSRABSuccEstabPSReqNotGrantedDCHHSDSCHGrantedDCHDCH decimal,
VSRABSuccEstabPSReqNotGrantedEDCHHSDSCHGrantedDCHDCH decimal,
VSRABSuccEstabPSReqNotGrantedEDCHHSDSCHGrantedDCHHSDSCH decimal,
VSRadioBearerReleaseSuccessSrcCallHsdpaEdch decimal,
VSRadioBearerReleaseSuccessSrcCallHsdpaR99 decimal,
VSRadioLinkSetupRequestCsData decimal,
VSRadioLinkSetupRequestCsDataPsDch decimal,
VSRadioLinkSetupRequestCsDataPsHsdpa decimal,
VSRadioLinkSetupRequestCsSpeech decimal,
VSRadioLinkSetupRequestCsSpeechHsdpa decimal,
VSRadioLinkSetupRequestCsSpeechPsDch decimal,
VSRadioLinkSetupRequestCsSpeechPsDchPsDch decimal,
VSRadioLinkSetupRequestCsSpeechPsDchPsHsdpa decimal,
VSRadioLinkSetupRequestCsStr decimal,
VSRadioLinkSetupRequestOther decimal,
VSRadioLinkSetupRequestPsDchDlDchUl decimal,
VSRadioLinkSetupRequestPsDchPsDch decimal,
VSRadioLinkSetupRequestPsDchPsHsdpa decimal,
VSRadioLinkSetupRequestPsHsdpaDchUl decimal,
VSRadioLinkSetupRequestPsHsdpaDlDchEdchUl decimal,
VSRadioLinkSetupRequestPsHsdpaDlEdchUl decimal,
VSRadioLinkSetupRequestSig decimal,
VSRadioLinkSetupUnsuccessLackBwthIub decimal,
VSReceivedPagingRequestType2CellDchWithCoreNetworkCs decimal,
VSReceivedPagingRequestType2CellDchWithCoreNetworkPs decimal,
VSReceivedPagingRequestType2CellFachWithCoreNetworkCs decimal,
VSReceivedPagingRequestType2CellFachWithCoreNetworkPs decimal,
VSRrcActiveSetUpdateCompleteProcedure decimal,
VSRrcActiveSetUpdateUnsuccessRrcActiveSetUpdateFailure decimal,
VSRrcActiveSetUpdateUnsuccessTimeout decimal,
VSUplinkRssiCum decimal,
VSUplinkRssiMax decimal,
VSUplinkRssiNbEvt decimal,
VSUpsizingSuccessDchHsdpa decimal,
constraint  raw_rnccn_utrancell_pk primary key (rcid,ts,rfid,ucid)
);
*/

/**
 * Utrancell table inserter. Child of RncFunction table.
 * @author jnramsay
 */
public class ALUUtranCellTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.rnccn.ALUUtranCellTable");

	public static final String TABLE_NAME = "raw_rnccn_utrancell";

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject,String rcid,Timestamp ts){
		RncFunctionType rft = (RncFunctionType) xmlobject;
		int rfid = Integer.parseInt(rft.getId());

		StringBuffer sql1 = new StringBuffer("INSERT INTO "+TABLE_NAME+" (");
	  	StringBuffer sql2 = new StringBuffer(") VALUES (");
	  	for (String col : prepareHeader()){
	  		sql1.append(col+",");
	  		sql2.append("?,");
	  	}
	  	//System.out.println();
	  	String sql = sql1.deleteCharAt(sql1.length()-1).toString()
	  		+sql2.deleteCharAt(sql2.length()-1).toString()+")";
	  	for (UtranCellType uct : rft.getUtranCellArray()){
		  	Connection con = null;
			PreparedStatement ps = null;

			try {

				con = ajc.getConnection();
				ps = con.prepareStatement(sql);
		  		ps.setString(1,rcid);
		  		ps.setTimestamp(2, ts);
		  		ps.setInt(3,rfid);
		  		ps.setString(4,uct.getId());

		  		if("<xml-fragment/>".compareTo(uct.xgetRABAttEstabCSSpeechConv().toString())==0)
		  			ps.setNull(5, Types.INTEGER);
		  		else ps.setLong(5,uct.getRABAttEstabCSSpeechConv().longValue());
		  		if("<xml-fragment/>".compareTo(uct.xgetRABAttEstabPSTrChnDCHHSDSCH().toString())==0)
		  			ps.setNull(6, Types.INTEGER);
		  		else ps.setLong(6,uct.getRABAttEstabPSTrChnDCHHSDSCH().longValue());
		  		if("<xml-fragment/>".compareTo(uct.xgetRABAttEstabPSTrChnEDCHHSDSCH().toString())==0)
		  			ps.setNull(7, Types.INTEGER);
		  		else ps.setLong(7,uct.getRABAttEstabPSTrChnEDCHHSDSCH().longValue());

		  		if("<xml-fragment/>".compareTo(uct.xgetRABSuccEstabCSSpeechConv().toString())==0)
		  			ps.setNull(8, Types.INTEGER);
		  		else ps.setLong(8,uct.getRABSuccEstabCSSpeechConv().longValue());
		  		if("<xml-fragment/>".compareTo(uct.xgetRABSuccEstabPSTrChnDCHHSDSCH().toString())==0)
		  			ps.setNull(9, Types.INTEGER);
		  		else ps.setLong(9,uct.getRABSuccEstabPSTrChnDCHHSDSCH().longValue());
		  		if("<xml-fragment/>".compareTo(uct.xgetRABSuccEstabPSTrChnEDCHHSDSCH().toString())==0)
		  			ps.setNull(10, Types.INTEGER);
		  		else ps.setLong(10,uct.getRABSuccEstabPSTrChnEDCHHSDSCH().longValue());

		  		ps.setLong(11,uct.getRRCAttConnEstabCallReestab().longValue());
		  		ps.setLong(12,uct.getRRCAttConnEstabDetach().longValue());
		  		ps.setLong(13,uct.getRRCAttConnEstabEmergency().longValue());
		  		ps.setLong(14,uct.getRRCAttConnEstabIRATCCO().longValue());
		  		ps.setLong(15,uct.getRRCAttConnEstabIRATCellResel().longValue());
		  		ps.setLong(16,uct.getRRCAttConnEstabOrigBgrdCall().longValue());
		  		ps.setLong(17,uct.getRRCAttConnEstabOrigConvCall().longValue());
		  		ps.setLong(18,uct.getRRCAttConnEstabOrigHighPrioSig().longValue());
		  		ps.setLong(19,uct.getRRCAttConnEstabOrigIntactCall().longValue());
		  		ps.setLong(20,uct.getRRCAttConnEstabOrigLowPrioSig().longValue());
		  		ps.setLong(21,uct.getRRCAttConnEstabOrigStrmCal().longValue());
		  		ps.setLong(22,uct.getRRCAttConnEstabOrigSubscCall().longValue());
		  		ps.setLong(23,uct.getRRCAttConnEstabRegistration().longValue());
		  		ps.setLong(24,uct.getRRCAttConnEstabSpare12().longValue());
		  		ps.setLong(25,uct.getRRCAttConnEstabTermBgrdCall().longValue());
		  		ps.setLong(26,uct.getRRCAttConnEstabTermConvCall().longValue());
		  		ps.setLong(27,uct.getRRCAttConnEstabTermHighPrioSig().longValue());
		  		ps.setLong(28,uct.getRRCAttConnEstabTermIntactCall().longValue());
		  		ps.setLong(29,uct.getRRCAttConnEstabTermLowPrioSig().longValue());
		  		ps.setLong(30,uct.getRRCAttConnEstabTermStrmCall().longValue());
		  		ps.setLong(31,uct.getRRCAttConnEstabTermUnknown().longValue());
		  		ps.setLong(32,uct.getRRCSuccConnEstabEmergency().longValue());
		  		ps.setLong(33,uct.getRRCSuccConnEstabOrigBgrdCall().longValue());
		  		ps.setLong(34,uct.getRRCSuccConnEstabOrigConvCall().longValue());
		  		ps.setLong(35,uct.getRRCSuccConnEstabOrigIntactCall().longValue());
		  		ps.setLong(36,uct.getRRCSuccConnEstabTermBgrdCall().longValue());
		  		ps.setLong(37,uct.getRRCSuccConnEstabTermConvCall().longValue());
		  		ps.setLong(38,uct.getRRCSuccConnEstabTermIntactCall().longValue());
		  		ps.setLong(39,uct.getVSCallEstablishmentDurationConversationalCum().longValue());
		  		ps.setLong(40,uct.getVSCallEstablishmentDurationConversationalNbEvt().longValue());
		  		ps.setLong(41,uct.getVSCallEstablishmentDurationBackgroundCum().longValue());
		  		ps.setLong(42,uct.getVSCallEstablishmentDurationInteractiveCum().longValue());
		  		ps.setLong(43,uct.getVSCallEstablishmentDurationBackgroundNbEvt().longValue());
		  		ps.setLong(44,uct.getVSCallEstablishmentDurationInteractiveNbEvt().longValue());
		  		ps.setLong(45,uct.getVSDdUlAmrABtGoodFrmAmrRtChgLnkFrm10P2().longValue());
		  		ps.setLong(46,uct.getVSDdUlAmrABtGoodFrmAmrRtChgLnkFrm12P2().longValue());
		  		ps.setLong(47,uct.getVSDdUlAmrABtGoodFrmAmrRtChgLnkFrm4P75().longValue());
		  		ps.setLong(48,uct.getVSDdUlAmrABtGoodFrmAmrRtChgLnkFrm5P15().longValue());
		  		ps.setLong(49,uct.getVSDdUlAmrABtGoodFrmAmrRtChgLnkFrm5P9().longValue());
		  		ps.setLong(50,uct.getVSDdUlAmrABtGoodFrmAmrRtChgLnkFrm6P7().longValue());
		  		ps.setLong(51,uct.getVSDdUlAmrABtGoodFrmAmrRtChgLnkFrm7P4().longValue());
		  		ps.setLong(52,uct.getVSDdUlAmrABtGoodFrmAmrRtChgLnkFrm7P95().longValue());
		  		ps.setLong(53,uct.getVSDdUlAmrWbABtBadFrmAmrWbRt1265().longValue());
		  		ps.setLong(54,uct.getVSDdUlAmrWbABtBadFrmAmrWbRt660().longValue());
		  		ps.setLong(55,uct.getVSDdUlAmrWbABtBadFrmAmrWbRt885().longValue());
		  		ps.setLong(56,uct.getVSDdUlAmrWbABtGoodFrmAmrWbRt1265().longValue());
		  		ps.setLong(57,uct.getVSDdUlAmrWbABtGoodFrmAmrWbRt660().longValue());
		  		ps.setLong(58,uct.getVSDdUlAmrWbABtGoodFrmAmrWbRt885().longValue());
		  		ps.setLong(59,uct.getVSDedicatedDownlinkActivityRlcRefCellDlRabHsdpa().longValue());
		  		ps.setLong(60,uct.getVSDedicatedDownlinkKbytesRlcActiveCellsDlRabCsSpeech().longValue());
		  		ps.setLong(61,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabCsData64().longValue());
		  		ps.setLong(62,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabCsSpeech().longValue());
		  		ps.setLong(63,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabCsStr().longValue());
		  		ps.setLong(64,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabHsdpa().longValue());
		  		ps.setLong(65,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabOther().longValue());
		  		ps.setLong(66,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb128().longValue());
		  		ps.setLong(67,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb16().longValue());
		  		ps.setLong(68,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb256().longValue());
		  		ps.setLong(69,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb32().longValue());
		  		ps.setLong(70,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb384().longValue());
		  		ps.setLong(71,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb64().longValue());
		  		ps.setLong(72,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb8().longValue());
		  		ps.setLong(73,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStr128().longValue());
		  		ps.setLong(74,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStr256().longValue());
		  		ps.setLong(75,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStr384().longValue());
		  		ps.setLong(76,uct.getVSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStrOther().longValue());
		  		ps.setLong(77,uct.getVSDedicatedUplinkActivityRlcRefCellUlRabPsIb16().longValue());
		  		ps.setLong(78,uct.getVSDedicatedUplinkKbytesRlcReferenceCellUlRabHsupa().longValue());
		  		ps.setLong(79,uct.getVSDedicatedUplinkKbytesRlcReferenceCellUlRabPsIb128().longValue());
		  		ps.setLong(80,uct.getVSDlAsConfIdAvgNbrEstablishedDlAsCnfCsDataCum().longValue());
		  		ps.setLong(81,uct.getVSDlAsConfIdAvgNbrEstablishedDlAsCnfCsDataNbEvt().longValue());
		  		ps.setFloat(82,uct.getVSDlAsConfIdAvgNbrEstablishedDlAsCnfCsSpeechNbLrAmrAvg().floatValue());
		  		ps.setLong(83,uct.getVSDlAsConfIdAvgNbrEstablishedDlAsCnfCsSpeechNbLrAmrCum().longValue());
		  		ps.setLong(84,uct.getVSDlAsConfIdAvgNbrEstablishedDlAsCnfCsSpeechWbAmrCum().longValue());
		  		ps.setLong(85,uct.getVSDownsizingStep1SuccessDchHsdpa().longValue());
		  		ps.setLong(86,uct.getVSEdchSucMobilityEdchCallToEdchCallInterFreqMob().longValue());
		  		ps.setLong(87,uct.getVSEdchSucMobilityEdchCallToEdchCallIntraFreqMob().longValue());
		  		ps.setLong(88,uct.getVSEdchSucMobilityEdchCallToNonEdchCallInterFreqMob().longValue());
		  		ps.setLong(89,uct.getVSEdchSucMobilityEdchCallToNonEdchCallIntraFreqMob().longValue());
		  		ps.setLong(90,uct.getVSEdchSucMobilityEdchCallTTI10ToEdchCallTTI2().longValue());
		  		ps.setLong(91,uct.getVSEdchSucMobilityEdchCallTTI2ToEdchCallTTI10().longValue());
		  		ps.setLong(92,uct.getVSEdchSucMobilityEdchCallTTI2ToEdchCallTTI2().longValue());
		  		ps.setLong(93,uct.getVSEdchSucMobilityNonEdchCallToEdchCallInterFreqMob().longValue());
		  		ps.setLong(94,uct.getVSEdchSucMobilityNonEdchCallToEdchCallIntraFreqMob().longValue());
		  		ps.setLong(95,uct.getVSEdchUnsucMobilityEdchCallToEdchCallInterFreqMob().longValue());
		  		ps.setLong(96,uct.getVSEdchUnsucMobilityEdchCallToEdchCallIntraFreqMob().longValue());
		  		ps.setLong(97,uct.getVSEdchUnsucMobilityEdchCallToNonEdchCallInterFreqMob().longValue());
		  		ps.setLong(98,uct.getVSEdchUnsucMobilityEdchCallToNonEdchCallIntraFreqMob().longValue());
		  		ps.setLong(99,uct.getVSEdchUnsucMobilityEdchCallTTI10ToEdchCallTTI2().longValue());
		  		ps.setLong(100,uct.getVSEdchUnsucMobilityEdchCallTTI2ToEdchCallTTI10().longValue());
		  		ps.setLong(101,uct.getVSEdchUnsucMobilityEdchCallTTI2ToEdchCallTTI2().longValue());
		  		ps.setLong(102,uct.getVSEdchUnsucMobilityNonEdchCallToEdchCallInterFreqMob().longValue());
		  		ps.setLong(103,uct.getVSEdchUnsucMobilityNonEdchCallToEdchCallIntraFreqMob().longValue());
		  		ps.setLong(104,uct.getVSFirstRrcConnectionRequestEmergency().longValue());
		  		ps.setLong(105,uct.getVSFirstRrcConnectionRequestOrigBgrdCall().longValue());
		  		ps.setLong(106,uct.getVSFirstRrcConnectionRequestOrigConvCall().longValue());
		  		ps.setLong(107,uct.getVSFirstRrcConnectionRequestOrigIntactCall().longValue());
		  		ps.setLong(108,uct.getVSFirstRrcConnectionRequestTermBgrdCall().longValue());
		  		ps.setLong(109,uct.getVSFirstRrcConnectionRequestTermConvCall().longValue());
		  		ps.setLong(110,uct.getVSFirstRrcConnectionRequestTermIntactCall().longValue());
		  		ps.setLong(111,uct.getVSHsdpaMobilitySuccessHsdpaToHsdpa().longValue());
		  		ps.setLong(112,uct.getVSHsdpaMobilitySuccessHsdpaToHsdpaInterFreq().longValue());
		  		ps.setLong(113,uct.getVSHsdpaMobilitySuccessHsdpaToNonHsdpa().longValue());
		  		ps.setLong(114,uct.getVSHsdpaMobilitySuccessHsdpaToNonHsdpaInterFreq().longValue());
		  		ps.setLong(115,uct.getVSHsdpaMobilitySuccessNonHsdpaToHsdpa().longValue());
		  		ps.setLong(116,uct.getVSHsdpaMobilitySuccessNonHsdpaToHsdpaInterFreq().longValue());
		  		ps.setLong(117,uct.getVSHsdpaMobilityUnsuccessfulHsdpaToHsdpa().longValue());
		  		ps.setLong(118,uct.getVSHsdpaMobilityUnsuccessfulHsdpaToHsdpaInterFreq().longValue());
		  		ps.setLong(119,uct.getVSHsdpaMobilityUnsuccessfulHsdpaToNonHsdpa().longValue());
		  		ps.setLong(120,uct.getVSHsdpaMobilityUnsuccessfulHsdpaToNonHsdpaInterFreq().longValue());
		  		ps.setLong(121,uct.getVSHsdpaMobilityUnsuccessfulNonHsdpaToHsdpa().longValue());
		  		ps.setLong(122,uct.getVSHsdpaMobilityUnsuccessfulNonHsdpaToHsdpaInterFreq().longValue());
		  		ps.setLong(123,uct.getVSIncomInterFreqHoAttRescue().longValue());
		  		ps.setLong(124,uct.getVSIncomInterFreqHoAttService().longValue());
		  		ps.setLong(125,uct.getVSIncomInterFreqHoSucRescue().longValue());
		  		ps.setLong(126,uct.getVSIncomInterFreqHoSucService().longValue());
		  		ps.setLong(127,uct.getVSIntraRncOutInterFreqHoAttemptHoWithCmMeasInterBand().longValue());
		  		ps.setLong(128,uct.getVSIntraRncOutInterFreqHoFailHoWithCmMeasInterBand().longValue());
		  		ps.setFloat(129,uct.getVSIRMTimeDlCodesSF16RsrvHsAvg().floatValue());
		  		ps.setFloat(130,uct.getVSIRMTimeFreeDlCodesBySpreadingFactor128Avg().floatValue());
		  		ps.setFloat(131,uct.getVSIRMTimeFreeDlCodesBySpreadingFactor16Avg().floatValue());
		  		ps.setFloat(132,uct.getVSIRMTimeFreeDlCodesBySpreadingFactor256Avg().floatValue());
		  		ps.setFloat(133,uct.getVSIRMTimeFreeDlCodesBySpreadingFactor32Avg().floatValue());
		  		ps.setFloat(134,uct.getVSIRMTimeFreeDlCodesBySpreadingFactor4Avg().floatValue());
		  		ps.setFloat(135,uct.getVSIRMTimeFreeDlCodesBySpreadingFactor64Avg().floatValue());
		  		ps.setFloat(136,uct.getVSIRMTimeFreeDlCodesBySpreadingFactor8Avg().floatValue());
		  		ps.setLong(137,uct.getVSIuAbnormalReleaseRequestCsDlAsCnfCsSpeechNbLrAmr().longValue());
		  		ps.setLong(138,uct.getVSIuAbnormalReleaseRequestCsDlAsCnfCsSpeechWbAmr().longValue());
		  		ps.setLong(139,uct.getVSIuAbnormalReleaseRequestPsDlAsCnfHsdpa().longValue());
		  		ps.setLong(140,uct.getVSIuDlAmrFrmFqcFrmBad().longValue());
		  		ps.setLong(141,uct.getVSIuDlAmrFrmFqcFrmBadRadio().longValue());
		  		ps.setLong(142,uct.getVSIuDlAmrFrmFqcFrmGood().longValue());
		  		ps.setLong(143,uct.getVSIuDlAmrWbFrmFqcFrmBad().longValue());
		  		ps.setLong(144,uct.getVSIuDlAmrWbFrmFqcFrmBadRadio().longValue());
		  		ps.setLong(145,uct.getVSIuDlAmrWbFrmFqcFrmGood().longValue());
		  		ps.setLong(146,uct.getVSIuReleaseCompleteCsDlAsCnfCsSpeechNbLrAmr().longValue());
		  		ps.setLong(147,uct.getVSIuReleaseCompleteCsDlAsCnfCsSpeechWbAmr().longValue());
		  		ps.setLong(148,uct.getVSOutGoInterFreqHoAttRescue().longValue());
		  		ps.setLong(149,uct.getVSOutGoInterFreqHoAttService().longValue());
		  		ps.setLong(150,uct.getVSOutGoInterFreqHoSucRescue().longValue());
		  		ps.setLong(151,uct.getVSOutGoInterFreqHoSucService().longValue());
		  		ps.setLong(152,uct.getVSPagingCancelledRecords().longValue());
		  		ps.setLong(153,uct.getVSPagingDelayedRecords().longValue());
		  		ps.setLong(154,uct.getVSPagingMessagesSentOnPcch().longValue());
		  		ps.setLong(155,uct.getVSPagingRecordsUnscheduledCsTerminatingBackgroundCall().longValue());
		  		ps.setLong(156,uct.getVSPagingRecordsUnscheduledCsTerminatingCauseUnknown().longValue());
		  		ps.setLong(157,uct.getVSPagingRecordsUnscheduledCsTerminatingConversationalCall().longValue());
		  		ps.setLong(158,uct.getVSPagingRecordsUnscheduledCsTerminatingHighPrioritySignalling().longValue());
		  		ps.setLong(159,uct.getVSPagingRecordsUnscheduledCsTerminatingInteractiveCall().longValue());
		  		ps.setLong(160,uct.getVSPagingRecordsUnscheduledCsTerminatingLowPrioritySignalling().longValue());
		  		ps.setLong(161,uct.getVSPagingRecordsUnscheduledCsTerminatingStreamingCall().longValue());
		  		ps.setLong(162,uct.getVSPagingRecordsUnscheduledPsTerminatingBackgroundCall().longValue());
		  		ps.setLong(163,uct.getVSPagingRecordsUnscheduledPsTerminatingCauseUnknown().longValue());
		  		ps.setLong(164,uct.getVSPagingRecordsUnscheduledPsTerminatingConversationalCall().longValue());
		  		ps.setLong(165,uct.getVSPagingRecordsUnscheduledPsTerminatingHighPrioritySignalling().longValue());
		  		ps.setLong(166,uct.getVSPagingRecordsUnscheduledPsTerminatingInteractiveCall().longValue());
		  		ps.setLong(167,uct.getVSPagingRecordsUnscheduledPsTerminatingLowPrioritySignalling().longValue());
		  		ps.setLong(168,uct.getVSPagingRecordsUnscheduledPsTerminatingStreamingCall().longValue());
		  		ps.setLong(169,uct.getVSPagingRejectedRequests().longValue());

		  		if("<xml-fragment/>".compareTo(uct.xgetVSRABMeanCSVSumCum().toString())==0)
		  			ps.setNull(170, Types.INTEGER);
		  		else ps.setLong(170,uct.getVSRABMeanCSVSumCum().longValue());

		  		if("<xml-fragment/>".compareTo(uct.xgetVSRABSuccEstabPSReqNotGrantedDCHHSDSCHGrantedDCHDCH().toString())==0)
		  			ps.setNull(171, Types.INTEGER);
		  		else ps.setLong(171,uct.getVSRABSuccEstabPSReqNotGrantedDCHHSDSCHGrantedDCHDCH().longValue());
		  		if("<xml-fragment/>".compareTo(uct.xgetVSRABSuccEstabPSReqNotGrantedEDCHHSDSCHGrantedDCHDCH().toString())==0)
		  			ps.setNull(172, Types.INTEGER);
		  		else ps.setLong(172,uct.getVSRABSuccEstabPSReqNotGrantedEDCHHSDSCHGrantedDCHDCH().longValue());
		  		if("<xml-fragment/>".compareTo(uct.xgetVSRABSuccEstabPSReqNotGrantedEDCHHSDSCHGrantedDCHHSDSCH().toString())==0)
		  			ps.setNull(173, Types.INTEGER);
		  		else ps.setLong(173,uct.getVSRABSuccEstabPSReqNotGrantedEDCHHSDSCHGrantedDCHHSDSCH().longValue());

		  		ps.setLong(174,uct.getVSRadioBearerReleaseSuccessSrcCallHsdpaEdch().longValue());
		  		ps.setLong(175,uct.getVSRadioBearerReleaseSuccessSrcCallHsdpaR99().longValue());
		  		ps.setLong(176,uct.getVSRadioLinkSetupRequestCsData().longValue());
		  		ps.setLong(177,uct.getVSRadioLinkSetupRequestCsDataPsDch().longValue());
		  		ps.setLong(178,uct.getVSRadioLinkSetupRequestCsDataPsHsdpa().longValue());
		  		ps.setLong(179,uct.getVSRadioLinkSetupRequestCsSpeech().longValue());
		  		ps.setLong(180,uct.getVSRadioLinkSetupRequestCsSpeechHsdpa().longValue());
		  		ps.setLong(181,uct.getVSRadioLinkSetupRequestCsSpeechPsDch().longValue());
		  		ps.setLong(182,uct.getVSRadioLinkSetupRequestCsSpeechPsDchPsDch().longValue());
		  		ps.setLong(183,uct.getVSRadioLinkSetupRequestCsSpeechPsDchPsHsdpa().longValue());
		  		ps.setLong(184,uct.getVSRadioLinkSetupRequestCsStr().longValue());
		  		ps.setLong(185,uct.getVSRadioLinkSetupRequestOther().longValue());
		  		ps.setLong(186,uct.getVSRadioLinkSetupRequestPsDchDlDchUl().longValue());
		  		ps.setLong(187,uct.getVSRadioLinkSetupRequestPsDchPsDch().longValue());
		  		ps.setLong(188,uct.getVSRadioLinkSetupRequestPsDchPsHsdpa().longValue());
		  		ps.setLong(189,uct.getVSRadioLinkSetupRequestPsHsdpaDchUl().longValue());
		  		ps.setLong(190,uct.getVSRadioLinkSetupRequestPsHsdpaDlDchEdchUl().longValue());
		  		ps.setLong(191,uct.getVSRadioLinkSetupRequestPsHsdpaDlEdchUl().longValue());
		  		ps.setLong(192,uct.getVSRadioLinkSetupRequestSig().longValue());
		  		ps.setLong(193,uct.getVSRadioLinkSetupUnsuccessLackBwthIub().longValue());
		  		ps.setLong(194,uct.getVSReceivedPagingRequestType2CellDchWithCoreNetworkCs().longValue());
		  		ps.setLong(195,uct.getVSReceivedPagingRequestType2CellDchWithCoreNetworkPs().longValue());
		  		ps.setLong(196,uct.getVSReceivedPagingRequestType2CellFachWithCoreNetworkCs().longValue());
		  		ps.setLong(197,uct.getVSReceivedPagingRequestType2CellFachWithCoreNetworkPs().longValue());
		  		ps.setLong(198,uct.getVSRrcActiveSetUpdateCompleteProcedure().longValue());
		  		ps.setLong(199,uct.getVSRrcActiveSetUpdateUnsuccessRrcActiveSetUpdateFailure().longValue());
		  		ps.setLong(200,uct.getVSRrcActiveSetUpdateUnsuccessTimeout().longValue());
		  		ps.setLong(201,uct.getVSUplinkRssiCum().longValue());
		  		ps.setLong(202,uct.getVSUplinkRssiMax().longValue());
		  		ps.setLong(203,uct.getVSUplinkRssiNbEvt().longValue());
		  		ps.setLong(204,uct.getVSUpsizingSuccessDchHsdpa().longValue());

		  		jlog.debug("PS::"+ps.toString());
		  		ps.executeUpdate();
		  	}
			catch(XmlValueOutOfRangeException xore){
				System.err.println("Value in transformed XML does not subscribe to XSD defn for "+TABLE_NAME+" :: "+xore);
			}
			catch(SQLException sqle){
				if(!sqle.toString().contains("duplicate key violates unique constraint"))
					System.err.println("Problem mapping to DB "+TABLE_NAME+">"+ps+" :: "+sqle);
			}
			finally {
				try {
					ps.close();
					con.close();
				} catch (Exception e) {
					System.err.println("Undefined Exception :: "+e);
				}
			}
	  	}
	}



	public static ArrayList<String> prepareHeader() {
		ArrayList<String> row = new ArrayList<String>();

		row.add("rcid");
		row.add("ts");
		row.add("rfid");
		row.add("ucid");
		row.add("RABAttEstabCSSpeechConv");
		row.add("RABAttEstabPSTrChnDCHHSDSCH");
		row.add("RABAttEstabPSTrChnEDCHHSDSCH");
		row.add("RABSuccEstabCSSpeechConv");
		row.add("RABSuccEstabPSTrChnDCHHSDSCH");
		row.add("RABSuccEstabPSTrChnEDCHHSDSCH");
		row.add("RRCAttConnEstabCallReestab");
		row.add("RRCAttConnEstabDetach");
		row.add("RRCAttConnEstabEmergency");
		row.add("RRCAttConnEstabIRATCCO");
		row.add("RRCAttConnEstabIRATCellResel");
		row.add("RRCAttConnEstabOrigBgrdCall");
		row.add("RRCAttConnEstabOrigConvCall");
		row.add("RRCAttConnEstabOrigHighPrioSig");
		row.add("RRCAttConnEstabOrigIntactCall");
		row.add("RRCAttConnEstabOrigLowPrioSig");
		row.add("RRCAttConnEstabOrigStrmCal");
		row.add("RRCAttConnEstabOrigSubscCall");
		row.add("RRCAttConnEstabRegistration");
		row.add("RRCAttConnEstabSpare12");
		row.add("RRCAttConnEstabTermBgrdCall");
		row.add("RRCAttConnEstabTermConvCall");
		row.add("RRCAttConnEstabTermHighPrioSig");
		row.add("RRCAttConnEstabTermIntactCall");
		row.add("RRCAttConnEstabTermLowPrioSig");
		row.add("RRCAttConnEstabTermStrmCall");
		row.add("RRCAttConnEstabTermUnknown");
		row.add("RRCSuccConnEstabEmergency");
		row.add("RRCSuccConnEstabOrigBgrdCall");
		row.add("RRCSuccConnEstabOrigConvCall");
		row.add("RRCSuccConnEstabOrigIntactCall");
		row.add("RRCSuccConnEstabTermBgrdCall");
		row.add("RRCSuccConnEstabTermConvCall");
		row.add("RRCSuccConnEstabTermIntactCall");
		row.add("VSCallEstablishmentDurationConversationalCum");
		row.add("VSCallEstablishmentDurationConversationalNbEvt");
		row.add("VSCallEstablishmentDurationBackgroundCum");
		row.add("VSCallEstablishmentDurationInteractiveCum");
		row.add("VSCallEstablishmentDurationBackgroundNbEvt");
		row.add("VSCallEstablishmentDurationInteractiveNbEvt");
		row.add("VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm10p2");
		row.add("VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm12p2");
		row.add("VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm4p75");
		row.add("VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm5p15");
		row.add("VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm5p9");
		row.add("VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm6p7");
		row.add("VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm7p4");
		row.add("VSDdUlAmrABtGoodFrmAmrRtChgLnkFrm7p95");
		row.add("VSDdUlAmrWbABtBadFrmAmrWbRt1265");
		row.add("VSDdUlAmrWbABtBadFrmAmrWbRt660");
		row.add("VSDdUlAmrWbABtBadFrmAmrWbRt885");
		row.add("VSDdUlAmrWbABtGoodFrmAmrWbRt1265");
		row.add("VSDdUlAmrWbABtGoodFrmAmrWbRt660");
		row.add("VSDdUlAmrWbABtGoodFrmAmrWbRt885");
		row.add("VSDedicatedDownlinkActivityRlcRefCellDlRabHsdpa");
		row.add("VSDedicatedDownlinkKbytesRlcActiveCellsDlRabCsSpeech");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabCsData64");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabCsSpeech");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabCsStr");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabHsdpa");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabOther");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb128");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb16");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb256");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb32");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb384");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb64");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsIb8");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStr128");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStr256");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStr384");
		row.add("VSDedicatedDownlinkKbytesRlcReferenceCellDlRabPsStrOther");
		row.add("VSDedicatedUplinkActivityRlcRefCellUlRabPsIb16");
		row.add("VSDedicatedUplinkKbytesRlcReferenceCellUlRabHsupa");
		row.add("VSDedicatedUplinkKbytesRlcReferenceCellUlRabPsIb128");
		row.add("VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsDataCum");
		row.add("VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsDataNbEvt");
		row.add("VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsSpeechNbLrAmrAvg");
		row.add("VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsSpeechNbLrAmrCum");
		row.add("VSDlAsConfIdAvgNbrEstablishedDlAsCnfCsSpeechWbAmrCum");
		row.add("VSDownsizingStep1SuccessDchHsdpa");
		row.add("VSEdchSucMobilityEdchCallToEdchCallInterFreqMob");
		row.add("VSEdchSucMobilityEdchCallToEdchCallIntraFreqMob");
		row.add("VSEdchSucMobilityEdchCallToNonEdchCallInterFreqMob");
		row.add("VSEdchSucMobilityEdchCallToNonEdchCallIntraFreqMob");
		row.add("VSEdchSucMobilityEdchCallTTI10ToEdchCallTTI2");
		row.add("VSEdchSucMobilityEdchCallTTI2ToEdchCallTTI10");
		row.add("VSEdchSucMobilityEdchCallTTI2ToEdchCallTTI2");
		row.add("VSEdchSucMobilityNonEdchCallToEdchCallInterFreqMob");
		row.add("VSEdchSucMobilityNonEdchCallToEdchCallIntraFreqMob");
		row.add("VSEdchUnsucMobilityEdchCallToEdchCallInterFreqMob");
		row.add("VSEdchUnsucMobilityEdchCallToEdchCallIntraFreqMob");
		row.add("VSEdchUnsucMobilityEdchCallToNonEdchCallInterFreqMob");
		row.add("VSEdchUnsucMobilityEdchCallToNonEdchCallIntraFreqMob");
		row.add("VSEdchUnsucMobilityEdchCallTTI10ToEdchCallTTI2");
		row.add("VSEdchUnsucMobilityEdchCallTTI2ToEdchCallTTI10");
		row.add("VSEdchUnsucMobilityEdchCallTTI2ToEdchCallTTI2");
		row.add("VSEdchUnsucMobilityNonEdchCallToEdchCallInterFreqMob");
		row.add("VSEdchUnsucMobilityNonEdchCallToEdchCallIntraFreqMob");
		row.add("VSFirstRrcConnectionRequestEmergency");
		row.add("VSFirstRrcConnectionRequestOrigBgrdCall");
		row.add("VSFirstRrcConnectionRequestOrigConvCall");
		row.add("VSFirstRrcConnectionRequestOrigIntactCall");
		row.add("VSFirstRrcConnectionRequestTermBgrdCall");
		row.add("VSFirstRrcConnectionRequestTermConvCall");
		row.add("VSFirstRrcConnectionRequestTermIntactCall");
		row.add("VSHsdpaMobilitySuccessHsdpaToHsdpa");
		row.add("VSHsdpaMobilitySuccessHsdpaToHsdpaInterFreq");
		row.add("VSHsdpaMobilitySuccessHsdpaToNonHsdpa");
		row.add("VSHsdpaMobilitySuccessHsdpaToNonHsdpaInterFreq");
		row.add("VSHsdpaMobilitySuccessNonHsdpaToHsdpa");
		row.add("VSHsdpaMobilitySuccessNonHsdpaToHsdpaInterFreq");
		row.add("VSHsdpaMobilityUnsuccessfulHsdpaToHsdpa");
		row.add("VSHsdpaMobilityUnsuccessfulHsdpaToHsdpaInterFreq");
		row.add("VSHsdpaMobilityUnsuccessfulHsdpaToNonHsdpa");
		row.add("VSHsdpaMobilityUnsuccessfulHsdpaToNonHsdpaInterFreq");
		row.add("VSHsdpaMobilityUnsuccessfulNonHsdpaToHsdpa");
		row.add("VSHsdpaMobilityUnsuccessfulNonHsdpaToHsdpaInterFreq");
		row.add("VSIncomInterFreqHoAttRescue");
		row.add("VSIncomInterFreqHoAttService");
		row.add("VSIncomInterFreqHoSucRescue");
		row.add("VSIncomInterFreqHoSucService");
		row.add("VSIntraRncOutInterFreqHoAttemptHoWithCmMeasInterBand");
		row.add("VSIntraRncOutInterFreqHoFailHoWithCmMeasInterBand");
		row.add("VSIRMTimeDlCodesSF16RsrvHsAvg");
		row.add("VSIRMTimeFreeDlCodesBySpreadingFactor128Avg");
		row.add("VSIRMTimeFreeDlCodesBySpreadingFactor16Avg");
		row.add("VSIRMTimeFreeDlCodesBySpreadingFactor256Avg");
		row.add("VSIRMTimeFreeDlCodesBySpreadingFactor32Avg");
		row.add("VSIRMTimeFreeDlCodesBySpreadingFactor4Avg");
		row.add("VSIRMTimeFreeDlCodesBySpreadingFactor64Avg");
		row.add("VSIRMTimeFreeDlCodesBySpreadingFactor8Avg");
		row.add("VSIuAbnormalReleaseRequestCsDlAsCnfCsSpeechNbLrAmr");
		row.add("VSIuAbnormalReleaseRequestCsDlAsCnfCsSpeechWbAmr");
		row.add("VSIuAbnormalReleaseRequestPsDlAsCnfHsdpa");
		row.add("VSIuDlAmrFrmFqcFrmBad");
		row.add("VSIuDlAmrFrmFqcFrmBadRadio");
		row.add("VSIuDlAmrFrmFqcFrmGood");
		row.add("VSIuDlAmrWbFrmFqcFrmBad");
		row.add("VSIuDlAmrWbFrmFqcFrmBadRadio");
		row.add("VSIuDlAmrWbFrmFqcFrmGood");
		row.add("VSIuReleaseCompleteCsDlAsCnfCsSpeechNbLrAmr");
		row.add("VSIuReleaseCompleteCsDlAsCnfCsSpeechWbAmr");
		row.add("VSOutGoInterFreqHoAttRescue");
		row.add("VSOutGoInterFreqHoAttService");
		row.add("VSOutGoInterFreqHoSucRescue");
		row.add("VSOutGoInterFreqHoSucService");
		row.add("VSPagingCancelledRecords");
		row.add("VSPagingDelayedRecords");
		row.add("VSPagingMessagesSentOnPcch");
		row.add("VSPagingRecordsUnscheduledCsTerminatingBackgroundCall");
		row.add("VSPagingRecordsUnscheduledCsTerminatingCauseUnknown");
		row.add("VSPagingRecordsUnscheduledCsTerminatingConversationalCall");
		row.add("VSPagingRecordsUnscheduledCsTerminatingHighPrioritySignalling");
		row.add("VSPagingRecordsUnscheduledCsTerminatingInteractiveCall");
		row.add("VSPagingRecordsUnscheduledCsTerminatingLowPrioritySignalling");
		row.add("VSPagingRecordsUnscheduledCsTerminatingStreamingCall");
		row.add("VSPagingRecordsUnscheduledPsTerminatingBackgroundCall");
		row.add("VSPagingRecordsUnscheduledPsTerminatingCauseUnknown");
		row.add("VSPagingRecordsUnscheduledPsTerminatingConversationalCall");
		row.add("VSPagingRecordsUnscheduledPsTerminatingHighPrioritySignalling");
		row.add("VSPagingRecordsUnscheduledPsTerminatingInteractiveCall");
		row.add("VSPagingRecordsUnscheduledPsTerminatingLowPrioritySignalling");
		row.add("VSPagingRecordsUnscheduledPsTerminatingStreamingCall");
		row.add("VSPagingRejectedRequests");
		row.add("VSRABMeanCSVSumCum");
		row.add("VSRABSuccEstabPSReqNotGrantedDCHHSDSCHGrantedDCHDCH");
		row.add("VSRABSuccEstabPSReqNotGrantedEDCHHSDSCHGrantedDCHDCH");
		row.add("VSRABSuccEstabPSReqNotGrantedEDCHHSDSCHGrantedDCHHSDSCH");
		row.add("VSRadioBearerReleaseSuccessSrcCallHsdpaEdch");
		row.add("VSRadioBearerReleaseSuccessSrcCallHsdpaR99");
		row.add("VSRadioLinkSetupRequestCsData");
		row.add("VSRadioLinkSetupRequestCsDataPsDch");
		row.add("VSRadioLinkSetupRequestCsDataPsHsdpa");
		row.add("VSRadioLinkSetupRequestCsSpeech");
		row.add("VSRadioLinkSetupRequestCsSpeechHsdpa");
		row.add("VSRadioLinkSetupRequestCsSpeechPsDch");
		row.add("VSRadioLinkSetupRequestCsSpeechPsDchPsDch");
		row.add("VSRadioLinkSetupRequestCsSpeechPsDchPsHsdpa");
		row.add("VSRadioLinkSetupRequestCsStr");
		row.add("VSRadioLinkSetupRequestOther");
		row.add("VSRadioLinkSetupRequestPsDchDlDchUl");
		row.add("VSRadioLinkSetupRequestPsDchPsDch");
		row.add("VSRadioLinkSetupRequestPsDchPsHsdpa");
		row.add("VSRadioLinkSetupRequestPsHsdpaDchUl");
		row.add("VSRadioLinkSetupRequestPsHsdpaDlDchEdchUl");
		row.add("VSRadioLinkSetupRequestPsHsdpaDlEdchUl");
		row.add("VSRadioLinkSetupRequestSig");
		row.add("VSRadioLinkSetupUnsuccessLackBwthIub");
		row.add("VSReceivedPagingRequestType2CellDchWithCoreNetworkCs");
		row.add("VSReceivedPagingRequestType2CellDchWithCoreNetworkPs");
		row.add("VSReceivedPagingRequestType2CellFachWithCoreNetworkCs");
		row.add("VSReceivedPagingRequestType2CellFachWithCoreNetworkPs");
		row.add("VSRrcActiveSetUpdateCompleteProcedure");
		row.add("VSRrcActiveSetUpdateUnsuccessRrcActiveSetUpdateFailure");
		row.add("VSRrcActiveSetUpdateUnsuccessTimeout");
		row.add("VSUplinkRssiCum");
		row.add("VSUplinkRssiMax");
		row.add("VSUplinkRssiNbEvt");
		row.add("VSUpsizingSuccessDchHsdpa");

		return row;

	}

}
