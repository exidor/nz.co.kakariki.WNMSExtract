<?xml version="1.0" encoding="UTF-8"?>
<!--
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
 -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:alu="http://www.w3.org/TR/REC-xml">
	<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
	<!--
	mi1 RncFunction
	mi2 RncFunction,UtranCell
    mi3 RncFunction, NeighbouringRnc
    mi4 RncEquipment,CNode,TMU
    mi5 RncFunction,IubIf,Aal2IfBP
    mi6 RncFunction,IubIf,IpIfBP
	-->
	<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
	<xsl:output method="xml" version="1.0" indent="yes" encoding="UTF-8" standalone="yes"/>
	<!-- template to extract nodeb name -->
	<xsl:template match="neun">
		<xsl:value-of select="."/>
	</xsl:template>
	<!--
++
-->
	<!-- template to format date string -->
	<xsl:template match="cbt">
		<xsl:value-of select="concat(substring(.,1,4),'/',substring(.,5,2),'/',substring(.,7,2),' ',substring(.,9,2),':',substring(.,11,2),':',substring(.,13,2))"/>
	</xsl:template>
	<!--
++
-->
	<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
	<!--
++
-->
	<!-- root template -->
	<xsl:template match="/">
		<xsl:element name="RncCn">
			<xsl:variable name="m1" select="count(/mdc/md/mi/mv/moid[.='RncFunction=0'][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m2" select="count(/mdc/md/mi/mv/moid[contains(.,'UtranCell')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m3" select="count(/mdc/md/mi/mv/moid[contains(.,'NeighbouringRnc')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:element name="id">
				<xsl:apply-templates select="mdc/md/neid/neun"/>
			</xsl:element>
			<xsl:element name="timestamp">
				<xsl:apply-templates select="mdc/mfh/cbt"/>
			</xsl:element>
			<xsl:for-each select="mdc/md/mi">
				<xsl:variable name="p" select="position()"/>
				<xsl:if test="$p=$m1 or $p=$m2 or $p=$m3">
					<xsl:apply-templates select="mv">
						<xsl:with-param name="mi_pos" select="$p"/>
						<xsl:with-param name="m1" select="$m1"/>
						<xsl:with-param name="m2" select="$m2"/>
						<xsl:with-param name="m3" select="$m3"/>
					</xsl:apply-templates>
				</xsl:if>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	<!--
++
++
-->
	<xsl:template match="mv">
		<xsl:param name="mi_pos"/>
		<xsl:param name="m1"/>
		<xsl:param name="m2"/>
		<xsl:param name="m3"/>
		<!-- ++ -->
		<xsl:variable name="id1">
			<xsl:choose>
				<xsl:when test="contains(./moid,',')">
					<xsl:value-of select="substring-before(substring-after(./moid,'='),',')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring-after(./moid,'=')"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!-- ++ -->
		<xsl:variable name="id2" select="substring-after(substring-after(./moid,','),'=')"/>
		<!-- ++ -->
		<xsl:element name="RncFunction">
			<xsl:attribute name="id"><xsl:value-of select="$id1"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$mi_pos=$m1">
					<!-- Ethernet p=2 -->
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCSSpeechConv.Avg"><xsl:apply-templates select="r[position()=809]"/></xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCsConv64.Avg"><xsl:apply-templates select="r[position()=814]"/></xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCsStr.Avg"><xsl:apply-templates select="r[position()=819]"/></xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCSSpeechConv.Max"><xsl:apply-templates select="r[position()=811]"/></xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCsConv64.Max"><xsl:apply-templates select="r[position()=816]"/></xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCsStr.Max"><xsl:apply-templates select="r[position()=821]"/></xsl:element>
					<xsl:element name="VS.ReceivedPagingRequest.WithCoreNetworkCs"><xsl:apply-templates select="r[position()=371]"/></xsl:element>
					<xsl:element name="VS.ReceivedPagingRequest.WithCoreNetworkPs"><xsl:apply-templates select="r[position()=372]"/></xsl:element>
					<xsl:element name="VS.ReceivedPagingRequest.FromCoreNwCsInvalidLac"><xsl:apply-templates select="r[position()=373]"/></xsl:element>
					<xsl:element name="VS.ReceivedPagingRequest.WithCoreNwPsInvalidRac"><xsl:apply-templates select="r[position()=374]"/></xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m2">
					<xsl:element name="UtranCell">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:element name="RAB.AttEstab.CS.SpeechConv"><xsl:apply-templates select="r[position()=3644]"/></xsl:element>
						<xsl:element name="RAB.AttEstab.PS.TrChn.DCH_HSDSCH"><xsl:apply-templates select="r[position()=3656]"/></xsl:element>
						<xsl:element name="RAB.AttEstab.PS.TrChn.EDCH_HSDSCH"><xsl:apply-templates select="r[position()=3657]"/></xsl:element>
						<xsl:element name="RAB.SuccEstab.CS.SpeechConv"><xsl:apply-templates select="r[position()=3662]"/></xsl:element>
						<xsl:element name="RAB.SuccEstab.PS.TrChn.DCH_HSDSCH"><xsl:apply-templates select="r[position()=3673]"/></xsl:element>
						<xsl:element name="RAB.SuccEstab.PS.TrChn.EDCH_HSDSCH"><xsl:apply-templates select="r[position()=3674]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.CallReestab"><xsl:apply-templates select="r[position()=652]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.Detach"><xsl:apply-templates select="r[position()=649]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.Emergency"><xsl:apply-templates select="r[position()=645]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.IRATCCO"><xsl:apply-templates select="r[position()=647]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.IRATCellResel"><xsl:apply-templates select="r[position()=646]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigBgrdCall"><xsl:apply-templates select="r[position()=639]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigConvCall"><xsl:apply-templates select="r[position()=636]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigHighPrioSig"><xsl:apply-templates select="r[position()=650]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigIntactCall"><xsl:apply-templates select="r[position()=638]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigLowPrioSig"><xsl:apply-templates select="r[position()=651]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigStrmCal"><xsl:apply-templates select="r[position()=637]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigSubscCall"><xsl:apply-templates select="r[position()=640]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.Registration"><xsl:apply-templates select="r[position()=648]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.Spare12"><xsl:apply-templates select="r[position()=656]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermBgrdCall"><xsl:apply-templates select="r[position()=644]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermConvCall"><xsl:apply-templates select="r[position()=641]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermHighPrioSig"><xsl:apply-templates select="r[position()=653]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermIntactCall"><xsl:apply-templates select="r[position()=643]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermLowPrioSig"><xsl:apply-templates select="r[position()=654]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermStrmCall"><xsl:apply-templates select="r[position()=642]"/></xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermUnknown"><xsl:apply-templates select="r[position()=655]"/></xsl:element>
						<xsl:element name="RRC.SuccConnEstab.Emergency"><xsl:apply-templates select="r[position()=598]"/></xsl:element>
						<xsl:element name="RRC.SuccConnEstab.OrigBgrdCall"><xsl:apply-templates select="r[position()=592]"/></xsl:element>
						<xsl:element name="RRC.SuccConnEstab.OrigConvCall"><xsl:apply-templates select="r[position()=589]"/></xsl:element>
						<xsl:element name="RRC.SuccConnEstab.OrigIntactCall"><xsl:apply-templates select="r[position()=591]"/></xsl:element>
						<xsl:element name="RRC.SuccConnEstab.TermBgrdCall"><xsl:apply-templates select="r[position()=597]"/></xsl:element>
						<xsl:element name="RRC.SuccConnEstab.TermConvCall"><xsl:apply-templates select="r[position()=594]"/></xsl:element>
						<xsl:element name="RRC.SuccConnEstab.TermIntactCall"><xsl:apply-templates select="r[position()=596]"/></xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Conversational.Cum"><xsl:apply-templates select="r[position()=922]"/></xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Conversational.NbEvt"><xsl:apply-templates select="r[position()=924]"/></xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Background.Cum"><xsl:apply-templates select="r[position()=937]"/></xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Interactive.Cum"><xsl:apply-templates select="r[position()=932]"/></xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Background.NbEvt"><xsl:apply-templates select="r[position()=939]"/></xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Interactive.NbEvt"><xsl:apply-templates select="r[position()=934]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm10p2"><xsl:apply-templates select="r[position()=1602]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm12p2"><xsl:apply-templates select="r[position()=1601]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm4p75"><xsl:apply-templates select="r[position()=1608]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm5p15"><xsl:apply-templates select="r[position()=1607]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm5p9"><xsl:apply-templates select="r[position()=1606]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm6p7"><xsl:apply-templates select="r[position()=1605]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm7p4"><xsl:apply-templates select="r[position()=1604]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm7p95"><xsl:apply-templates select="r[position()=1603]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtBadFrm.AmrWbRt1265"><xsl:apply-templates select="r[position()=1592]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtBadFrm.AmrWbRt660"><xsl:apply-templates select="r[position()=1594]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtBadFrm.AmrWbRt885"><xsl:apply-templates select="r[position()=1593]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtGoodFrm.AmrWbRt1265"><xsl:apply-templates select="r[position()=1589]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtGoodFrm.AmrWbRt660"><xsl:apply-templates select="r[position()=1591]"/></xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtGoodFrm.AmrWbRt885"><xsl:apply-templates select="r[position()=1590]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkActivityRlcRefCell.DlRabHsdpa"><xsl:apply-templates select="r[position()=1876]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcActiveCells.DlRabCsSpeech"><xsl:apply-templates select="r[position()=1726]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabCsData64"><xsl:apply-templates select="r[position()=1759]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabCsSpeech"><xsl:apply-templates select="r[position()=1758]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabCsStr"><xsl:apply-templates select="r[position()=1760]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabHsdpa"><xsl:apply-templates select="r[position()=1761]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabOther"><xsl:apply-templates select="r[position()=1756]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb128"><xsl:apply-templates select="r[position()=1770]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb16"><xsl:apply-templates select="r[position()=1767]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb256"><xsl:apply-templates select="r[position()=1771]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb32"><xsl:apply-templates select="r[position()=1768]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb384"><xsl:apply-templates select="r[position()=1772]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb64"><xsl:apply-templates select="r[position()=1769]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb8"><xsl:apply-templates select="r[position()=1766]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStr128"><xsl:apply-templates select="r[position()=1762]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStr256"><xsl:apply-templates select="r[position()=1763]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStr384"><xsl:apply-templates select="r[position()=1764]"/></xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStrOther"><xsl:apply-templates select="r[position()=1765]"/></xsl:element>
						<xsl:element name="VS.DedicatedUplinkActivityRlcRefCell.UlRabPsIb16"><xsl:apply-templates select="r[position()=1897]"/></xsl:element>
						<xsl:element name="VS.DedicatedUplinkKbytesRlcReferenceCell.UlRabHsupa"><xsl:apply-templates select="r[position()=1755]"/></xsl:element>
						<xsl:element name="VS.DedicatedUplinkKbytesRlcReferenceCell.UlRabPsIb128"><xsl:apply-templates select="r[position()=1753]"/></xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsData.Cum"><xsl:apply-templates select="r[position()=2232]"/></xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsData.NbEvt"><xsl:apply-templates select="r[position()=2234]"/></xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsSpeechNbLrAmr.Avg"><xsl:apply-templates select="r[position()=2223]"/></xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsSpeechNbLrAmr.Cum"><xsl:apply-templates select="r[position()=2222]"/></xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsSpeechWbAmr.Cum"><xsl:apply-templates select="r[position()=2227]"/></xsl:element>
						<xsl:element name="VS.DownsizingStep1Success.DchHsdpa"><xsl:apply-templates select="r[position()=2601]"/></xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallToEdchCallInterFreqMob"><xsl:apply-templates select="r[position()=1086]"/></xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallToEdchCallIntraFreqMob"><xsl:apply-templates select="r[position()=1083]"/></xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallToNonEdchCallInterFreqMob"><xsl:apply-templates select="r[position()=1088]"/></xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallToNonEdchCallIntraFreqMob"><xsl:apply-templates select="r[position()=1085]"/></xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallTTI10ToEdchCallTTI2"><xsl:apply-templates select="r[position()=1089]"/></xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallTTI2ToEdchCallTTI10"><xsl:apply-templates select="r[position()=1091]"/></xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallTTI2ToEdchCallTTI2"><xsl:apply-templates select="r[position()=1090]"/></xsl:element>
						<xsl:element name="VS.EdchSucMobility.NonEdchCallToEdchCallInterFreqMob"><xsl:apply-templates select="r[position()=1087]"/></xsl:element>
						<xsl:element name="VS.EdchSucMobility.NonEdchCallToEdchCallIntraFreqMob"><xsl:apply-templates select="r[position()=1084]"/></xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallToEdchCallInterFreqMob"><xsl:apply-templates select="r[position()=1095]"/></xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallToEdchCallIntraFreqMob"><xsl:apply-templates select="r[position()=1092]"/></xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallToNonEdchCallInterFreqMob"><xsl:apply-templates select="r[position()=1097]"/></xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallToNonEdchCallIntraFreqMob"><xsl:apply-templates select="r[position()=1094]"/></xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallTTI10ToEdchCallTTI2"><xsl:apply-templates select="r[position()=1098]"/></xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallTTI2ToEdchCallTTI10"><xsl:apply-templates select="r[position()=1100]"/></xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallTTI2ToEdchCallTTI2"><xsl:apply-templates select="r[position()=1099]"/></xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.NonEdchCallToEdchCallInterFreqMob"><xsl:apply-templates select="r[position()=1096]"/></xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.NonEdchCallToEdchCallIntraFreqMob"><xsl:apply-templates select="r[position()=1093]"/></xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.Emergency"><xsl:apply-templates select="r[position()=682]"/></xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.OrigBgrdCall"><xsl:apply-templates select="r[position()=676]"/></xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.OrigConvCall"><xsl:apply-templates select="r[position()=673]"/></xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.OrigIntactCall"><xsl:apply-templates select="r[position()=675]"/></xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.TermBgrdCall"><xsl:apply-templates select="r[position()=681]"/></xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.TermConvCall"><xsl:apply-templates select="r[position()=678]"/></xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.TermIntactCall"><xsl:apply-templates select="r[position()=680]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.HsdpaToHsdpa"><xsl:apply-templates select="r[position()=1042]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.HsdpaToHsdpaInterFreq"><xsl:apply-templates select="r[position()=1045]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.HsdpaToNonHsdpa"><xsl:apply-templates select="r[position()=1044]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.HsdpaToNonHsdpaInterFreq"><xsl:apply-templates select="r[position()=1047]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.NonHsdpaToHsdpa"><xsl:apply-templates select="r[position()=1043]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.NonHsdpaToHsdpaInterFreq"><xsl:apply-templates select="r[position()=1046]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.HsdpaToHsdpa"><xsl:apply-templates select="r[position()=1048]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.HsdpaToHsdpaInterFreq"><xsl:apply-templates select="r[position()=1051]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.HsdpaToNonHsdpa"><xsl:apply-templates select="r[position()=1050]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.HsdpaToNonHsdpaInterFreq"><xsl:apply-templates select="r[position()=1053]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.NonHsdpaToHsdpa"><xsl:apply-templates select="r[position()=1049]"/></xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.NonHsdpaToHsdpaInterFreq"><xsl:apply-templates select="r[position()=1052]"/></xsl:element>
						<xsl:element name="VS.IncomInterFreqHoAtt.Rescue"><xsl:apply-templates select="r[position()=467]"/></xsl:element>
						<xsl:element name="VS.IncomInterFreqHoAtt.Service"><xsl:apply-templates select="r[position()=468]"/></xsl:element>
						<xsl:element name="VS.IncomInterFreqHoSuc.Rescue"><xsl:apply-templates select="r[position()=470]"/></xsl:element>
						<xsl:element name="VS.IncomInterFreqHoSuc.Service"><xsl:apply-templates select="r[position()=471]"/></xsl:element>
						<xsl:element name="VS.IntraRncOutInterFreqHoAttempt.HoWithCmMeasInterBand"><xsl:apply-templates select="r[position()=439]"/></xsl:element>
						<xsl:element name="VS.IntraRncOutInterFreqHoFail.HoWithCmMeasInterBand"><xsl:apply-templates select="r[position()=444]"/></xsl:element>
						<xsl:element name="VS.IRMTimeDlCodesSF16RsrvHs.Avg"><xsl:apply-templates select="r[position()=1281]"/></xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.128.Avg"><xsl:apply-templates select="r[position()=1325]"/></xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.16.Avg"><xsl:apply-templates select="r[position()=1310]"/></xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.256.Avg"><xsl:apply-templates select="r[position()=1330]"/></xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.32.Avg"><xsl:apply-templates select="r[position()=1315]"/></xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.4.Avg"><xsl:apply-templates select="r[position()=1300]"/></xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.64.Avg"><xsl:apply-templates select="r[position()=1320]"/></xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.8.Avg"><xsl:apply-templates select="r[position()=1305]"/></xsl:element>
						<xsl:element name="VS.IuAbnormalReleaseRequestCs.DlAsCnfCsSpeechNbLrAmr"><xsl:apply-templates select="r[position()=869]"/></xsl:element>
						<xsl:element name="VS.IuAbnormalReleaseRequestCs.DlAsCnfCsSpeechWbAmr"><xsl:apply-templates select="r[position()=870]"/></xsl:element>
						<xsl:element name="VS.IuAbnormalReleaseRequestPs.DlAsCnfHsdpa"><xsl:apply-templates select="r[position()=853]"/></xsl:element>
						<xsl:element name="VS.IuDlAmrFrmFqc.FrmBad"><xsl:apply-templates select="r[position()=1599]"/></xsl:element>
						<xsl:element name="VS.IuDlAmrFrmFqc.FrmBadRadio"><xsl:apply-templates select="r[position()=1600]"/></xsl:element>
						<xsl:element name="VS.IuDlAmrFrmFqc.FrmGood"><xsl:apply-templates select="r[position()=1598]"/></xsl:element>
						<xsl:element name="VS.IuDlAmrWbFrmFqc.FrmBad"><xsl:apply-templates select="r[position()=1596]"/></xsl:element>
						<xsl:element name="VS.IuDlAmrWbFrmFqc.FrmBadRadio"><xsl:apply-templates select="r[position()=1597]"/></xsl:element>
						<xsl:element name="VS.IuDlAmrWbFrmFqc.FrmGood"><xsl:apply-templates select="r[position()=1595]"/></xsl:element>
						<xsl:element name="VS.IuReleaseCompleteCs.DlAsCnfCsSpeechNbLrAmr"><xsl:apply-templates select="r[position()=861]"/></xsl:element>
						<xsl:element name="VS.IuReleaseCompleteCs.DlAsCnfCsSpeechWbAmr"><xsl:apply-templates select="r[position()=862]"/></xsl:element>
						<xsl:element name="VS.OutGoInterFreqHoAtt.Rescue"><xsl:apply-templates select="r[position()=461]"/></xsl:element>
						<xsl:element name="VS.OutGoInterFreqHoAtt.Service"><xsl:apply-templates select="r[position()=462]"/></xsl:element>
						<xsl:element name="VS.OutGoInterFreqHoSuc.Rescue"><xsl:apply-templates select="r[position()=464]"/></xsl:element>
						<xsl:element name="VS.OutGoInterFreqHoSuc.Service"><xsl:apply-templates select="r[position()=465]"/></xsl:element>
						<xsl:element name="VS.PagingCancelledRecords"><xsl:apply-templates select="r[position()=968]"/></xsl:element>
						<xsl:element name="VS.PagingDelayedRecords"><xsl:apply-templates select="r[position()=970]"/></xsl:element>
						<xsl:element name="VS.PagingMessagesSentOnPcch"><xsl:apply-templates select="r[position()=967]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingBackgroundCall"><xsl:apply-templates select="r[position()=988]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingCauseUnknown"><xsl:apply-templates select="r[position()=991]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingConversationalCall"><xsl:apply-templates select="r[position()=985]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingHighPrioritySignalling"><xsl:apply-templates select="r[position()=989]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingInteractiveCall"><xsl:apply-templates select="r[position()=987]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingLowPrioritySignalling"><xsl:apply-templates select="r[position()=990]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingStreamingCall"><xsl:apply-templates select="r[position()=986]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingBackgroundCall"><xsl:apply-templates select="r[position()=995]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingCauseUnknown"><xsl:apply-templates select="r[position()=998]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingConversationalCall"><xsl:apply-templates select="r[position()=992]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingHighPrioritySignalling"><xsl:apply-templates select="r[position()=996]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingInteractiveCall"><xsl:apply-templates select="r[position()=994]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingLowPrioritySignalling"><xsl:apply-templates select="r[position()=997]"/></xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingStreamingCall"><xsl:apply-templates select="r[position()=993]"/></xsl:element>
						<xsl:element name="VS.PagingRejectedRequests"><xsl:apply-templates select="r[position()=969]"/></xsl:element>
						<xsl:element name="VS.RAB.Mean.CSV.Sum.Cum"><xsl:apply-templates select="r[position()=3726]"/></xsl:element>
						<xsl:element name="VS.RAB.SuccEstab.PS.ReqNotGranted.DCH_HSDSCH_GrantedDCH_DCH"><xsl:apply-templates select="r[position()=3675]"/></xsl:element>
						<xsl:element name="VS.RAB.SuccEstab.PS.ReqNotGranted.EDCH_HSDSCH_GrantedDCH_DCH"><xsl:apply-templates select="r[position()=3677]"/></xsl:element>
						<xsl:element name="VS.RAB.SuccEstab.PS.ReqNotGranted.EDCH_HSDSCH_GrantedDCH_HSDSCH"><xsl:apply-templates select="r[position()=3676]"/></xsl:element>
						<xsl:element name="VS.RadioBearerReleaseSuccess.SrcCallHsdpaEdch"><xsl:apply-templates select="r[position()=2111]"/></xsl:element>
						<xsl:element name="VS.RadioBearerReleaseSuccess.SrcCallHsdpaR99"><xsl:apply-templates select="r[position()=2110]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsData"><xsl:apply-templates select="r[position()=292]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsDataPsDch"><xsl:apply-templates select="r[position()=293]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsDataPsHsdpa"><xsl:apply-templates select="r[position()=294]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeech"><xsl:apply-templates select="r[position()=287]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeechHsdpa"><xsl:apply-templates select="r[position()=289]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeechPsDch"><xsl:apply-templates select="r[position()=288]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeechPsDchPsDch"><xsl:apply-templates select="r[position()=291]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeechPsDchPsHsdpa"><xsl:apply-templates select="r[position()=290]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsStr"><xsl:apply-templates select="r[position()=295]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.Other"><xsl:apply-templates select="r[position()=285]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsDchDlDchUl"><xsl:apply-templates select="r[position()=296]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsDchPsDch"><xsl:apply-templates select="r[position()=300]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsDchPsHsdpa"><xsl:apply-templates select="r[position()=301]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsHsdpaDchUl"><xsl:apply-templates select="r[position()=298]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsHsdpaDlDchEdchUl"><xsl:apply-templates select="r[position()=299]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsHsdpaDlEdchUl"><xsl:apply-templates select="r[position()=297]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.Sig"><xsl:apply-templates select="r[position()=286]"/></xsl:element>
						<xsl:element name="VS.RadioLinkSetupUnsuccess.LackBwthIub"><xsl:apply-templates select="r[position()=97]"/></xsl:element>
						<xsl:element name="VS.ReceivedPagingRequestType2CellDch.WithCoreNetworkCs"><xsl:apply-templates select="r[position()=963]"/></xsl:element>
						<xsl:element name="VS.ReceivedPagingRequestType2CellDch.WithCoreNetworkPs"><xsl:apply-templates select="r[position()=964]"/></xsl:element>
						<xsl:element name="VS.ReceivedPagingRequestType2CellFach.WithCoreNetworkCs"><xsl:apply-templates select="r[position()=965]"/></xsl:element>
						<xsl:element name="VS.ReceivedPagingRequestType2CellFach.WithCoreNetworkPs"><xsl:apply-templates select="r[position()=966]"/></xsl:element>
						<xsl:element name="VS.RrcActiveSetUpdateCompleteProcedure"><xsl:apply-templates select="r[position()=666]"/></xsl:element>
						<xsl:element name="VS.RrcActiveSetUpdateUnsuccess.RrcActiveSetUpdateFailure"><xsl:apply-templates select="r[position()=587]"/></xsl:element>
						<xsl:element name="VS.RrcActiveSetUpdateUnsuccess.Timeout"><xsl:apply-templates select="r[position()=588]"/></xsl:element>
						<xsl:element name="VS.UplinkRssi.Cum"><xsl:apply-templates select="r[position()=547]"/></xsl:element>
						<xsl:element name="VS.UplinkRssi.Max"><xsl:apply-templates select="r[position()=550]"/></xsl:element>
						<xsl:element name="VS.UplinkRssi.NbEvt"><xsl:apply-templates select="r[position()=549]"/></xsl:element>
						<xsl:element name="VS.UpsizingSuccess.DchHsdpa"><xsl:apply-templates select="r[position()=2615]"/></xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m3">
					<xsl:element name="NeighbouringRnc">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:element name="RAB.AttEstab.PS.TrChn.NeighbRnc.DCH_HSDSCH"><xsl:apply-templates select="r[position()=775]"/></xsl:element>
						<xsl:element name="RAB.AttEstab.PS.TrChn.NeighbRnc.EDCH_HSDSCH"><xsl:apply-templates select="r[position()=776]"/></xsl:element>
					</xsl:element>
				</xsl:when>
			</xsl:choose>
		</xsl:element>
	</xsl:template>
	<!--
++
-->
	<xsl:template match="r">
		<xsl:choose>
			<xsl:when test=".='null'">0</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="."/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--
++
-->
</xsl:stylesheet>
