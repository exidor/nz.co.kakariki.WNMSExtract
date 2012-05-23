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
	<xsl:template match="alu:neun">
		<xsl:value-of select="."/>
	</xsl:template>
	<!--
++
-->
	<!-- template to format date string -->
	<xsl:template match="alu:cbt">
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
			<xsl:variable name="m1" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[.='RncFunction=0'][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m2" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'UtranCell')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m3" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'NeighbouringRnc')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:element name="id">
				<xsl:apply-templates select="alu:mdc/alu:md/alu:neid/alu:neun"/>
			</xsl:element>
			<xsl:element name="timestamp">
				<xsl:apply-templates select="alu:mdc/alu:mfh/alu:cbt"/>
			</xsl:element>
			<xsl:for-each select="alu:mdc/alu:md/alu:mi">
				<xsl:variable name="p" select="position()"/>
				<xsl:if test="$p=$m1 or $p=$m2 or $p=$m3">
					<xsl:apply-templates select="alu:mv">
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
	<xsl:template match="alu:mv">
		<xsl:param name="mi_pos"/>
		<xsl:param name="m1"/>
		<xsl:param name="m2"/>
		<xsl:param name="m3"/>
		<!-- ++ -->
		<xsl:variable name="id1">
			<xsl:choose>
				<xsl:when test="contains(./alu:moid,',')">
					<xsl:value-of select="substring-before(substring-after(./alu:moid,'='),',')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring-after(./alu:moid,'=')"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!-- ++ -->
		<xsl:variable name="id2" select="substring-after(substring-after(./alu:moid,','),'=')"/>
		<!-- ++ -->
		<xsl:element name="RncFunction">
			<xsl:attribute name="id"><xsl:value-of select="$id1"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$mi_pos=$m1">
					<!-- Ethernet p=2 -->
					<xsl:variable name="p1" select="count((../alu:mt[.='VS.NumberOfRabEstablished.GrantedRabCSSpeechConv.Avg'])[1]/preceding-sibling::*)-1"/>
					<xsl:variable name="p2" select="count((../alu:mt[.='VS.NumberOfRabEstablished.GrantedRabCsConv64.Avg'])[1]/preceding-sibling::*)-1"/>
					<xsl:variable name="p3" select="count((../alu:mt[.='VS.NumberOfRabEstablished.GrantedRabCsStr.Avg'])[1]/preceding-sibling::*)-1"/>
					<xsl:variable name="p4" select="count((../alu:mt[.='VS.NumberOfRabEstablished.GrantedRabCSSpeechConv.Max'])[1]/preceding-sibling::*)-1"/>
					<xsl:variable name="p5" select="count((../alu:mt[.='VS.NumberOfRabEstablished.GrantedRabCsConv64.Max'])[1]/preceding-sibling::*)-1"/>
					<xsl:variable name="p6" select="count((../alu:mt[.='VS.NumberOfRabEstablished.GrantedRabCsStr.Max'])[1]/preceding-sibling::*)-1"/>
					<xsl:variable name="p7" select="count((../alu:mt[.='VS.ReceivedPagingRequest.WithCoreNetworkCs'])[1]/preceding-sibling::*)-1"/>
					<xsl:variable name="p8" select="count((../alu:mt[.='VS.ReceivedPagingRequest.WithCoreNetworkPs'])[1]/preceding-sibling::*)-1"/>
					<xsl:variable name="p9" select="count((../alu:mt[.='VS.ReceivedPagingRequest.FromCoreNwCsInvalidLac'])[1]/preceding-sibling::*)-1"/>
					<xsl:variable name="p10" select="count((../alu:mt[.='VS.ReceivedPagingRequest.WithCoreNwPsInvalidRac'])[1]/preceding-sibling::*)-1"/>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCSSpeechConv.Avg">
						<xsl:apply-templates select="alu:r[position()=$p1]"/>
					</xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCsConv64.Avg">
						<xsl:apply-templates select="alu:r[position()=$p2]"/>
					</xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCsStr.Avg">
						<xsl:apply-templates select="alu:r[position()=$p3]"/>
					</xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCSSpeechConv.Max">
						<xsl:apply-templates select="alu:r[position()=$p4]"/>
					</xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCsConv64.Max">
						<xsl:apply-templates select="alu:r[position()=$p5]"/>
					</xsl:element>
					<xsl:element name="VS.NumberOfRabEstablished.GrantedRabCsStr.Max">
						<xsl:apply-templates select="alu:r[position()=$p6]"/>
					</xsl:element>
					<xsl:element name="VS.ReceivedPagingRequest.WithCoreNetworkCs">
						<xsl:apply-templates select="alu:r[position()=$p7]"/>
					</xsl:element>
					<xsl:element name="VS.ReceivedPagingRequest.WithCoreNetworkPs">
						<xsl:apply-templates select="alu:r[position()=$p8]"/>
					</xsl:element>
					<xsl:element name="VS.ReceivedPagingRequest.FromCoreNwCsInvalidLac">
						<xsl:apply-templates select="alu:r[position()=$p9]"/>
					</xsl:element>
					<xsl:element name="VS.ReceivedPagingRequest.WithCoreNwPsInvalidRac">
						<xsl:apply-templates select="alu:r[position()=$p10]"/>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m2">
					<xsl:element name="UtranCell">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:variable name="p1" select="count((../alu:mt[.='RAB.AttEstab.CS.SpeechConv'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p2" select="count((../alu:mt[.='RAB.AttEstab.PS.TrChn.DCH_HSDSCH'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p3" select="count((../alu:mt[.='RAB.AttEstab.PS.TrChn.EDCH_HSDSCH'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p4" select="count((../alu:mt[.='RAB.SuccEstab.CS.SpeechConv'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p5" select="count((../alu:mt[.='RAB.SuccEstab.PS.TrChn.DCH_HSDSCH'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p6" select="count((../alu:mt[.='RAB.SuccEstab.PS.TrChn.EDCH_HSDSCH'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p7" select="count((../alu:mt[.='RRC.AttConnEstab.CallReestab'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p8" select="count((../alu:mt[.='RRC.AttConnEstab.Detach'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p9" select="count((../alu:mt[.='RRC.AttConnEstab.Emergency'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p10" select="count((../alu:mt[.='RRC.AttConnEstab.IRATCCO'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p11" select="count((../alu:mt[.='RRC.AttConnEstab.IRATCellResel'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p12" select="count((../alu:mt[.='RRC.AttConnEstab.OrigBgrdCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p13" select="count((../alu:mt[.='RRC.AttConnEstab.OrigConvCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p14" select="count((../alu:mt[.='RRC.AttConnEstab.OrigHighPrioSig'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p15" select="count((../alu:mt[.='RRC.AttConnEstab.OrigIntactCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p16" select="count((../alu:mt[.='RRC.AttConnEstab.OrigLowPrioSig'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p17" select="count((../alu:mt[.='RRC.AttConnEstab.OrigStrmCal'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p18" select="count((../alu:mt[.='RRC.AttConnEstab.OrigSubscCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p19" select="count((../alu:mt[.='RRC.AttConnEstab.Registration'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p20" select="count((../alu:mt[.='RRC.AttConnEstab.Spare12'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p21" select="count((../alu:mt[.='RRC.AttConnEstab.TermBgrdCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p22" select="count((../alu:mt[.='RRC.AttConnEstab.TermConvCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p23" select="count((../alu:mt[.='RRC.AttConnEstab.TermHighPrioSig'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p24" select="count((../alu:mt[.='RRC.AttConnEstab.TermIntactCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p25" select="count((../alu:mt[.='RRC.AttConnEstab.TermLowPrioSig'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p26" select="count((../alu:mt[.='RRC.AttConnEstab.TermStrmCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p27" select="count((../alu:mt[.='RRC.AttConnEstab.TermUnknown'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p28" select="count((../alu:mt[.='RRC.SuccConnEstab.Emergency'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p29" select="count((../alu:mt[.='RRC.SuccConnEstab.OrigBgrdCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p30" select="count((../alu:mt[.='RRC.SuccConnEstab.OrigConvCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p31" select="count((../alu:mt[.='RRC.SuccConnEstab.OrigIntactCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p32" select="count((../alu:mt[.='RRC.SuccConnEstab.TermBgrdCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p33" select="count((../alu:mt[.='RRC.SuccConnEstab.TermConvCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p34" select="count((../alu:mt[.='RRC.SuccConnEstab.TermIntactCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p35" select="count((../alu:mt[.='VS.CallEstablishmentDuration.Conversational.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p36" select="count((../alu:mt[.='VS.CallEstablishmentDuration.Conversational.NbEvt'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p37" select="count((../alu:mt[.='VS.CallEstablishmentDuration.Background.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p38" select="count((../alu:mt[.='VS.CallEstablishmentDuration.Interactive.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p39" select="count((../alu:mt[.='VS.CallEstablishmentDuration.Background.NbEvt'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p40" select="count((../alu:mt[.='VS.CallEstablishmentDuration.Interactive.NbEvt'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p41" select="count((../alu:mt[.='VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm10p2'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p42" select="count((../alu:mt[.='VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm12p2'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p43" select="count((../alu:mt[.='VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm4p75'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p44" select="count((../alu:mt[.='VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm5p15'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p45" select="count((../alu:mt[.='VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm5p9'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p46" select="count((../alu:mt[.='VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm6p7'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p47" select="count((../alu:mt[.='VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm7p4'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p48" select="count((../alu:mt[.='VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm7p95'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p49" select="count((../alu:mt[.='VS.DdUlAmrWbABtBadFrm.AmrWbRt1265'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p50" select="count((../alu:mt[.='VS.DdUlAmrWbABtBadFrm.AmrWbRt660'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p51" select="count((../alu:mt[.='VS.DdUlAmrWbABtBadFrm.AmrWbRt885'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p52" select="count((../alu:mt[.='VS.DdUlAmrWbABtGoodFrm.AmrWbRt1265'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p53" select="count((../alu:mt[.='VS.DdUlAmrWbABtGoodFrm.AmrWbRt660'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p54" select="count((../alu:mt[.='VS.DdUlAmrWbABtGoodFrm.AmrWbRt885'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p55" select="count((../alu:mt[.='VS.DedicatedDownlinkActivityRlcRefCell.DlRabHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p56" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcActiveCells.DlRabCsSpeech'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p57" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabCsData64'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p58" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabCsSpeech'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p59" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabCsStr'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p60" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p61" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabOther'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p62" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb128'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p63" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb16'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p64" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb256'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p65" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb32'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p66" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb384'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p67" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb64'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p68" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb8'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p69" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStr128'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p70" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStr256'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p71" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStr384'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p72" select="count((../alu:mt[.='VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStrOther'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p73" select="count((../alu:mt[.='VS.DedicatedUplinkActivityRlcRefCell.UlRabPsIb16'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p74" select="count((../alu:mt[.='VS.DedicatedUplinkKbytesRlcReferenceCell.UlRabHsupa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p75" select="count((../alu:mt[.='VS.DedicatedUplinkKbytesRlcReferenceCell.UlRabPsIb128'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p76" select="count((../alu:mt[.='VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsData.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p77" select="count((../alu:mt[.='VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsData.NbEvt'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p78" select="count((../alu:mt[.='VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsSpeechNbLrAmr.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p79" select="count((../alu:mt[.='VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsSpeechNbLrAmr.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p80" select="count((../alu:mt[.='VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsSpeechWbAmr.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p81" select="count((../alu:mt[.='VS.DownsizingStep1Success.DchHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p82" select="count((../alu:mt[.='VS.EdchSucMobility.EdchCallToEdchCallInterFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p83" select="count((../alu:mt[.='VS.EdchSucMobility.EdchCallToEdchCallIntraFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p84" select="count((../alu:mt[.='VS.EdchSucMobility.EdchCallToNonEdchCallInterFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p85" select="count((../alu:mt[.='VS.EdchSucMobility.EdchCallToNonEdchCallIntraFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p86" select="count((../alu:mt[.='VS.EdchSucMobility.EdchCallTTI10ToEdchCallTTI2'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p87" select="count((../alu:mt[.='VS.EdchSucMobility.EdchCallTTI2ToEdchCallTTI10'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p88" select="count((../alu:mt[.='VS.EdchSucMobility.EdchCallTTI2ToEdchCallTTI2'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p89" select="count((../alu:mt[.='VS.EdchSucMobility.NonEdchCallToEdchCallInterFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p90" select="count((../alu:mt[.='VS.EdchSucMobility.NonEdchCallToEdchCallIntraFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p91" select="count((../alu:mt[.='VS.EdchUnsucMobility.EdchCallToEdchCallInterFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p92" select="count((../alu:mt[.='VS.EdchUnsucMobility.EdchCallToEdchCallIntraFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p93" select="count((../alu:mt[.='VS.EdchUnsucMobility.EdchCallToNonEdchCallInterFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p94" select="count((../alu:mt[.='VS.EdchUnsucMobility.EdchCallToNonEdchCallIntraFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p95" select="count((../alu:mt[.='VS.EdchUnsucMobility.EdchCallTTI10ToEdchCallTTI2'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p96" select="count((../alu:mt[.='VS.EdchUnsucMobility.EdchCallTTI2ToEdchCallTTI10'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p97" select="count((../alu:mt[.='VS.EdchUnsucMobility.EdchCallTTI2ToEdchCallTTI2'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p98" select="count((../alu:mt[.='VS.EdchUnsucMobility.NonEdchCallToEdchCallInterFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p99" select="count((../alu:mt[.='VS.EdchUnsucMobility.NonEdchCallToEdchCallIntraFreqMob'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p100" select="count((../alu:mt[.='VS.FirstRrcConnectionRequest.Emergency'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p101" select="count((../alu:mt[.='VS.FirstRrcConnectionRequest.OrigBgrdCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p102" select="count((../alu:mt[.='VS.FirstRrcConnectionRequest.OrigConvCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p103" select="count((../alu:mt[.='VS.FirstRrcConnectionRequest.OrigIntactCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p104" select="count((../alu:mt[.='VS.FirstRrcConnectionRequest.TermBgrdCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p105" select="count((../alu:mt[.='VS.FirstRrcConnectionRequest.TermConvCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p106" select="count((../alu:mt[.='VS.FirstRrcConnectionRequest.TermIntactCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p107" select="count((../alu:mt[.='VS.HsdpaMobilitySuccess.HsdpaToHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p108" select="count((../alu:mt[.='VS.HsdpaMobilitySuccess.HsdpaToHsdpaInterFreq'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p109" select="count((../alu:mt[.='VS.HsdpaMobilitySuccess.HsdpaToNonHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p110" select="count((../alu:mt[.='VS.HsdpaMobilitySuccess.HsdpaToNonHsdpaInterFreq'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p111" select="count((../alu:mt[.='VS.HsdpaMobilitySuccess.NonHsdpaToHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p112" select="count((../alu:mt[.='VS.HsdpaMobilitySuccess.NonHsdpaToHsdpaInterFreq'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p113" select="count((../alu:mt[.='VS.HsdpaMobilityUnsuccessful.HsdpaToHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p114" select="count((../alu:mt[.='VS.HsdpaMobilityUnsuccessful.HsdpaToHsdpaInterFreq'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p115" select="count((../alu:mt[.='VS.HsdpaMobilityUnsuccessful.HsdpaToNonHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p116" select="count((../alu:mt[.='VS.HsdpaMobilityUnsuccessful.HsdpaToNonHsdpaInterFreq'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p117" select="count((../alu:mt[.='VS.HsdpaMobilityUnsuccessful.NonHsdpaToHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p118" select="count((../alu:mt[.='VS.HsdpaMobilityUnsuccessful.NonHsdpaToHsdpaInterFreq'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p119" select="count((../alu:mt[.='VS.IncomInterFreqHoAtt.Rescue'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p120" select="count((../alu:mt[.='VS.IncomInterFreqHoAtt.Service'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p121" select="count((../alu:mt[.='VS.IncomInterFreqHoSuc.Rescue'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p122" select="count((../alu:mt[.='VS.IncomInterFreqHoSuc.Service'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p123" select="count((../alu:mt[.='VS.IntraRncOutInterFreqHoAttempt.HoWithCmMeasInterBand'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p124" select="count((../alu:mt[.='VS.IntraRncOutInterFreqHoFail.HoWithCmMeasInterBand'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p125" select="count((../alu:mt[.='VS.IRMTimeDlCodesSF16RsrvHs.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p126" select="count((../alu:mt[.='VS.IRMTimeFreeDlCodesBySpreadingFactor.128.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p127" select="count((../alu:mt[.='VS.IRMTimeFreeDlCodesBySpreadingFactor.16.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p128" select="count((../alu:mt[.='VS.IRMTimeFreeDlCodesBySpreadingFactor.256.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p129" select="count((../alu:mt[.='VS.IRMTimeFreeDlCodesBySpreadingFactor.32.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p130" select="count((../alu:mt[.='VS.IRMTimeFreeDlCodesBySpreadingFactor.4.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p131" select="count((../alu:mt[.='VS.IRMTimeFreeDlCodesBySpreadingFactor.64.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p132" select="count((../alu:mt[.='VS.IRMTimeFreeDlCodesBySpreadingFactor.8.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p133" select="count((../alu:mt[.='VS.IuAbnormalReleaseRequestCs.DlAsCnfCsSpeechNbLrAmr'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p134" select="count((../alu:mt[.='VS.IuAbnormalReleaseRequestCs.DlAsCnfCsSpeechWbAmr'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p135" select="count((../alu:mt[.='VS.IuAbnormalReleaseRequestPs.DlAsCnfHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p136" select="count((../alu:mt[.='VS.IuDlAmrFrmFqc.FrmBad'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p137" select="count((../alu:mt[.='VS.IuDlAmrFrmFqc.FrmBadRadio'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p138" select="count((../alu:mt[.='VS.IuDlAmrFrmFqc.FrmGood'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p139" select="count((../alu:mt[.='VS.IuDlAmrWbFrmFqc.FrmBad'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p140" select="count((../alu:mt[.='VS.IuDlAmrWbFrmFqc.FrmBadRadio'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p141" select="count((../alu:mt[.='VS.IuDlAmrWbFrmFqc.FrmGood'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p142" select="count((../alu:mt[.='VS.IuReleaseCompleteCs.DlAsCnfCsSpeechNbLrAmr'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p143" select="count((../alu:mt[.='VS.IuReleaseCompleteCs.DlAsCnfCsSpeechWbAmr'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p144" select="count((../alu:mt[.='VS.OutGoInterFreqHoAtt.Rescue'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p145" select="count((../alu:mt[.='VS.OutGoInterFreqHoAtt.Service'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p146" select="count((../alu:mt[.='VS.OutGoInterFreqHoSuc.Rescue'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p147" select="count((../alu:mt[.='VS.OutGoInterFreqHoSuc.Service'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p148" select="count((../alu:mt[.='VS.PagingCancelledRecords'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p149" select="count((../alu:mt[.='VS.PagingDelayedRecords'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p150" select="count((../alu:mt[.='VS.PagingMessagesSentOnPcch'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p151" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledCs.TerminatingBackgroundCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p152" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledCs.TerminatingCauseUnknown'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p153" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledCs.TerminatingConversationalCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p154" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledCs.TerminatingHighPrioritySignalling'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p155" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledCs.TerminatingInteractiveCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p156" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledCs.TerminatingLowPrioritySignalling'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p157" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledCs.TerminatingStreamingCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p158" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledPs.TerminatingBackgroundCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p159" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledPs.TerminatingCauseUnknown'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p160" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledPs.TerminatingConversationalCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p161" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledPs.TerminatingHighPrioritySignalling'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p162" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledPs.TerminatingInteractiveCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p163" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledPs.TerminatingLowPrioritySignalling'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p164" select="count((../alu:mt[.='VS.PagingRecordsUnscheduledPs.TerminatingStreamingCall'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p165" select="count((../alu:mt[.='VS.PagingRejectedRequests'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p166" select="count((../alu:mt[.='VS.RAB.Mean.CSV.Sum.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p167" select="count((../alu:mt[.='VS.RAB.SuccEstab.PS.ReqNotGranted.DCH_HSDSCH_GrantedDCH_DCH'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p168" select="count((../alu:mt[.='VS.RAB.SuccEstab.PS.ReqNotGranted.EDCH_HSDSCH_GrantedDCH_DCH'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p169" select="count((../alu:mt[.='VS.RAB.SuccEstab.PS.ReqNotGranted.EDCH_HSDSCH_GrantedDCH_HSDSCH'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p170" select="count((../alu:mt[.='VS.RadioBearerReleaseSuccess.SrcCallHsdpaEdch'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p171" select="count((../alu:mt[.='VS.RadioBearerReleaseSuccess.SrcCallHsdpaR99'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p172" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.CsData'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p173" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.CsDataPsDch'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p174" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.CsDataPsHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p175" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.CsSpeech'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p176" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.CsSpeechHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p177" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.CsSpeechPsDch'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p178" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.CsSpeechPsDchPsDch'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p179" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.CsSpeechPsDchPsHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p180" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.CsStr'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p181" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.Other'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p182" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.PsDchDlDchUl'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p183" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.PsDchPsDch'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p184" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.PsDchPsHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p185" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.PsHsdpaDchUl'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p186" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.PsHsdpaDlDchEdchUl'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p187" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.PsHsdpaDlEdchUl'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p188" select="count((../alu:mt[.='VS.RadioLinkSetupRequest.Sig'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p189" select="count((../alu:mt[.='VS.RadioLinkSetupUnsuccess.LackBwthIub'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p190" select="count((../alu:mt[.='VS.ReceivedPagingRequestType2CellDch.WithCoreNetworkCs'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p191" select="count((../alu:mt[.='VS.ReceivedPagingRequestType2CellDch.WithCoreNetworkPs'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p192" select="count((../alu:mt[.='VS.ReceivedPagingRequestType2CellFach.WithCoreNetworkCs'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p193" select="count((../alu:mt[.='VS.ReceivedPagingRequestType2CellFach.WithCoreNetworkPs'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p194" select="count((../alu:mt[.='VS.RrcActiveSetUpdateCompleteProcedure'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p195" select="count((../alu:mt[.='VS.RrcActiveSetUpdateUnsuccess.RrcActiveSetUpdateFailure'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p196" select="count((../alu:mt[.='VS.RrcActiveSetUpdateUnsuccess.Timeout'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p197" select="count((../alu:mt[.='VS.UplinkRssi.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p198" select="count((../alu:mt[.='VS.UplinkRssi.Max'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p199" select="count((../alu:mt[.='VS.UplinkRssi.NbEvt'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p200" select="count((../alu:mt[.='VS.UpsizingSuccess.DchHsdpa'])[1]/preceding-sibling::*)-1"/>
						<xsl:element name="RAB.AttEstab.CS.SpeechConv">
							<xsl:apply-templates select="alu:r[position()=$p1]"/>
						</xsl:element>
						<xsl:element name="RAB.AttEstab.PS.TrChn.DCH_HSDSCH">
							<xsl:apply-templates select="alu:r[position()=$p2]"/>
						</xsl:element>
						<xsl:element name="RAB.AttEstab.PS.TrChn.EDCH_HSDSCH">
							<xsl:apply-templates select="alu:r[position()=$p3]"/>
						</xsl:element>
						<xsl:element name="RAB.SuccEstab.CS.SpeechConv">
							<xsl:apply-templates select="alu:r[position()=$p4]"/>
						</xsl:element>
						<xsl:element name="RAB.SuccEstab.PS.TrChn.DCH_HSDSCH">
							<xsl:apply-templates select="alu:r[position()=$p5]"/>
						</xsl:element>
						<xsl:element name="RAB.SuccEstab.PS.TrChn.EDCH_HSDSCH">
							<xsl:apply-templates select="alu:r[position()=$p6]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.CallReestab">
							<xsl:apply-templates select="alu:r[position()=$p7]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.Detach">
							<xsl:apply-templates select="alu:r[position()=$p8]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.Emergency">
							<xsl:apply-templates select="alu:r[position()=$p9]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.IRATCCO">
							<xsl:apply-templates select="alu:r[position()=$p10]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.IRATCellResel">
							<xsl:apply-templates select="alu:r[position()=$p11]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigBgrdCall">
							<xsl:apply-templates select="alu:r[position()=$p12]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigConvCall">
							<xsl:apply-templates select="alu:r[position()=$p13]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigHighPrioSig">
							<xsl:apply-templates select="alu:r[position()=$p14]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigIntactCall">
							<xsl:apply-templates select="alu:r[position()=$p15]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigLowPrioSig">
							<xsl:apply-templates select="alu:r[position()=$p16]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigStrmCal">
							<xsl:apply-templates select="alu:r[position()=$p17]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.OrigSubscCall">
							<xsl:apply-templates select="alu:r[position()=$p18]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.Registration">
							<xsl:apply-templates select="alu:r[position()=$p19]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.Spare12">
							<xsl:apply-templates select="alu:r[position()=$p20]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermBgrdCall">
							<xsl:apply-templates select="alu:r[position()=$p21]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermConvCall">
							<xsl:apply-templates select="alu:r[position()=$p22]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermHighPrioSig">
							<xsl:apply-templates select="alu:r[position()=$p23]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermIntactCall">
							<xsl:apply-templates select="alu:r[position()=$p24]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermLowPrioSig">
							<xsl:apply-templates select="alu:r[position()=$p25]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermStrmCall">
							<xsl:apply-templates select="alu:r[position()=$p26]"/>
						</xsl:element>
						<xsl:element name="RRC.AttConnEstab.TermUnknown">
							<xsl:apply-templates select="alu:r[position()=$p27]"/>
						</xsl:element>
						<xsl:element name="RRC.SuccConnEstab.Emergency">
							<xsl:apply-templates select="alu:r[position()=$p28]"/>
						</xsl:element>
						<xsl:element name="RRC.SuccConnEstab.OrigBgrdCall">
							<xsl:apply-templates select="alu:r[position()=$p29]"/>
						</xsl:element>
						<xsl:element name="RRC.SuccConnEstab.OrigConvCall">
							<xsl:apply-templates select="alu:r[position()=$p30]"/>
						</xsl:element>
						<xsl:element name="RRC.SuccConnEstab.OrigIntactCall">
							<xsl:apply-templates select="alu:r[position()=$p31]"/>
						</xsl:element>
						<xsl:element name="RRC.SuccConnEstab.TermBgrdCall">
							<xsl:apply-templates select="alu:r[position()=$p32]"/>
						</xsl:element>
						<xsl:element name="RRC.SuccConnEstab.TermConvCall">
							<xsl:apply-templates select="alu:r[position()=$p33]"/>
						</xsl:element>
						<xsl:element name="RRC.SuccConnEstab.TermIntactCall">
							<xsl:apply-templates select="alu:r[position()=$p34]"/>
						</xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Conversational.Cum">
							<xsl:apply-templates select="alu:r[position()=$p35]"/>
						</xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Conversational.NbEvt">
							<xsl:apply-templates select="alu:r[position()=$p36]"/>
						</xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Background.Cum">
							<xsl:apply-templates select="alu:r[position()=$p37]"/>
						</xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Interactive.Cum">
							<xsl:apply-templates select="alu:r[position()=$p38]"/>
						</xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Background.NbEvt">
							<xsl:apply-templates select="alu:r[position()=$p39]"/>
						</xsl:element>
						<xsl:element name="VS.CallEstablishmentDuration.Interactive.NbEvt">
							<xsl:apply-templates select="alu:r[position()=$p40]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm10p2">
							<xsl:apply-templates select="alu:r[position()=$p41]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm12p2">
							<xsl:apply-templates select="alu:r[position()=$p42]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm4p75">
							<xsl:apply-templates select="alu:r[position()=$p43]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm5p15">
							<xsl:apply-templates select="alu:r[position()=$p44]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm5p9">
							<xsl:apply-templates select="alu:r[position()=$p45]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm6p7">
							<xsl:apply-templates select="alu:r[position()=$p46]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm7p4">
							<xsl:apply-templates select="alu:r[position()=$p47]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrABtGoodFrm.AmrRtChgLnkFrm7p95">
							<xsl:apply-templates select="alu:r[position()=$p48]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtBadFrm.AmrWbRt1265">
							<xsl:apply-templates select="alu:r[position()=$p49]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtBadFrm.AmrWbRt660">
							<xsl:apply-templates select="alu:r[position()=$p50]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtBadFrm.AmrWbRt885">
							<xsl:apply-templates select="alu:r[position()=$p51]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtGoodFrm.AmrWbRt1265">
							<xsl:apply-templates select="alu:r[position()=$p52]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtGoodFrm.AmrWbRt660">
							<xsl:apply-templates select="alu:r[position()=$p53]"/>
						</xsl:element>
						<xsl:element name="VS.DdUlAmrWbABtGoodFrm.AmrWbRt885">
							<xsl:apply-templates select="alu:r[position()=$p54]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkActivityRlcRefCell.DlRabHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p55]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcActiveCells.DlRabCsSpeech">
							<xsl:apply-templates select="alu:r[position()=$p56]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabCsData64">
							<xsl:apply-templates select="alu:r[position()=$p57]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabCsSpeech">
							<xsl:apply-templates select="alu:r[position()=$p58]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabCsStr">
							<xsl:apply-templates select="alu:r[position()=$p59]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p60]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabOther">
							<xsl:apply-templates select="alu:r[position()=$p61]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb128">
							<xsl:apply-templates select="alu:r[position()=$p62]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb16">
							<xsl:apply-templates select="alu:r[position()=$p63]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb256">
							<xsl:apply-templates select="alu:r[position()=$p64]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb32">
							<xsl:apply-templates select="alu:r[position()=$p65]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb384">
							<xsl:apply-templates select="alu:r[position()=$p66]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb64">
							<xsl:apply-templates select="alu:r[position()=$p67]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsIb8">
							<xsl:apply-templates select="alu:r[position()=$p68]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStr128">
							<xsl:apply-templates select="alu:r[position()=$p69]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStr256">
							<xsl:apply-templates select="alu:r[position()=$p70]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStr384">
							<xsl:apply-templates select="alu:r[position()=$p71]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedDownlinkKbytesRlcReferenceCell.DlRabPsStrOther">
							<xsl:apply-templates select="alu:r[position()=$p72]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedUplinkActivityRlcRefCell.UlRabPsIb16">
							<xsl:apply-templates select="alu:r[position()=$p73]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedUplinkKbytesRlcReferenceCell.UlRabHsupa">
							<xsl:apply-templates select="alu:r[position()=$p74]"/>
						</xsl:element>
						<xsl:element name="VS.DedicatedUplinkKbytesRlcReferenceCell.UlRabPsIb128">
							<xsl:apply-templates select="alu:r[position()=$p75]"/>
						</xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsData.Cum">
							<xsl:apply-templates select="alu:r[position()=$p76]"/>
						</xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsData.NbEvt">
							<xsl:apply-templates select="alu:r[position()=$p77]"/>
						</xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsSpeechNbLrAmr.Avg">
							<xsl:apply-templates select="alu:r[position()=$p78]"/>
						</xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsSpeechNbLrAmr.Cum">
							<xsl:apply-templates select="alu:r[position()=$p79]"/>
						</xsl:element>
						<xsl:element name="VS.DlAsConfIdAvgNbrEstablished.DlAsCnfCsSpeechWbAmr.Cum">
							<xsl:apply-templates select="alu:r[position()=$p80]"/>
						</xsl:element>
						<xsl:element name="VS.DownsizingStep1Success.DchHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p81]"/>
						</xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallToEdchCallInterFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p82]"/>
						</xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallToEdchCallIntraFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p83]"/>
						</xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallToNonEdchCallInterFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p84]"/>
						</xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallToNonEdchCallIntraFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p85]"/>
						</xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallTTI10ToEdchCallTTI2">
							<xsl:apply-templates select="alu:r[position()=$p86]"/>
						</xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallTTI2ToEdchCallTTI10">
							<xsl:apply-templates select="alu:r[position()=$p87]"/>
						</xsl:element>
						<xsl:element name="VS.EdchSucMobility.EdchCallTTI2ToEdchCallTTI2">
							<xsl:apply-templates select="alu:r[position()=$p88]"/>
						</xsl:element>
						<xsl:element name="VS.EdchSucMobility.NonEdchCallToEdchCallInterFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p89]"/>
						</xsl:element>
						<xsl:element name="VS.EdchSucMobility.NonEdchCallToEdchCallIntraFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p90]"/>
						</xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallToEdchCallInterFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p91]"/>
						</xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallToEdchCallIntraFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p92]"/>
						</xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallToNonEdchCallInterFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p93]"/>
						</xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallToNonEdchCallIntraFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p94]"/>
						</xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallTTI10ToEdchCallTTI2">
							<xsl:apply-templates select="alu:r[position()=$p95]"/>
						</xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallTTI2ToEdchCallTTI10">
							<xsl:apply-templates select="alu:r[position()=$p96]"/>
						</xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.EdchCallTTI2ToEdchCallTTI2">
							<xsl:apply-templates select="alu:r[position()=$p97]"/>
						</xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.NonEdchCallToEdchCallInterFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p98]"/>
						</xsl:element>
						<xsl:element name="VS.EdchUnsucMobility.NonEdchCallToEdchCallIntraFreqMob">
							<xsl:apply-templates select="alu:r[position()=$p99]"/>
						</xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.Emergency">
							<xsl:apply-templates select="alu:r[position()=$p100]"/>
						</xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.OrigBgrdCall">
							<xsl:apply-templates select="alu:r[position()=$p101]"/>
						</xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.OrigConvCall">
							<xsl:apply-templates select="alu:r[position()=$p102]"/>
						</xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.OrigIntactCall">
							<xsl:apply-templates select="alu:r[position()=$p103]"/>
						</xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.TermBgrdCall">
							<xsl:apply-templates select="alu:r[position()=$p104]"/>
						</xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.TermConvCall">
							<xsl:apply-templates select="alu:r[position()=$p105]"/>
						</xsl:element>
						<xsl:element name="VS.FirstRrcConnectionRequest.TermIntactCall">
							<xsl:apply-templates select="alu:r[position()=$p106]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.HsdpaToHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p107]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.HsdpaToHsdpaInterFreq">
							<xsl:apply-templates select="alu:r[position()=$p108]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.HsdpaToNonHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p109]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.HsdpaToNonHsdpaInterFreq">
							<xsl:apply-templates select="alu:r[position()=$p110]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.NonHsdpaToHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p111]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilitySuccess.NonHsdpaToHsdpaInterFreq">
							<xsl:apply-templates select="alu:r[position()=$p112]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.HsdpaToHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p113]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.HsdpaToHsdpaInterFreq">
							<xsl:apply-templates select="alu:r[position()=$p114]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.HsdpaToNonHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p115]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.HsdpaToNonHsdpaInterFreq">
							<xsl:apply-templates select="alu:r[position()=$p116]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.NonHsdpaToHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p117]"/>
						</xsl:element>
						<xsl:element name="VS.HsdpaMobilityUnsuccessful.NonHsdpaToHsdpaInterFreq">
							<xsl:apply-templates select="alu:r[position()=$p118]"/>
						</xsl:element>
						<xsl:element name="VS.IncomInterFreqHoAtt.Rescue">
							<xsl:apply-templates select="alu:r[position()=$p119]"/>
						</xsl:element>
						<xsl:element name="VS.IncomInterFreqHoAtt.Service">
							<xsl:apply-templates select="alu:r[position()=$p120]"/>
						</xsl:element>
						<xsl:element name="VS.IncomInterFreqHoSuc.Rescue">
							<xsl:apply-templates select="alu:r[position()=$p121]"/>
						</xsl:element>
						<xsl:element name="VS.IncomInterFreqHoSuc.Service">
							<xsl:apply-templates select="alu:r[position()=$p122]"/>
						</xsl:element>
						<xsl:element name="VS.IntraRncOutInterFreqHoAttempt.HoWithCmMeasInterBand">
							<xsl:apply-templates select="alu:r[position()=$p123]"/>
						</xsl:element>
						<xsl:element name="VS.IntraRncOutInterFreqHoFail.HoWithCmMeasInterBand">
							<xsl:apply-templates select="alu:r[position()=$p124]"/>
						</xsl:element>
						<xsl:element name="VS.IRMTimeDlCodesSF16RsrvHs.Avg">
							<xsl:apply-templates select="alu:r[position()=$p125]"/>
						</xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.128.Avg">
							<xsl:apply-templates select="alu:r[position()=$p126]"/>
						</xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.16.Avg">
							<xsl:apply-templates select="alu:r[position()=$p127]"/>
						</xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.256.Avg">
							<xsl:apply-templates select="alu:r[position()=$p128]"/>
						</xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.32.Avg">
							<xsl:apply-templates select="alu:r[position()=$p129]"/>
						</xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.4.Avg">
							<xsl:apply-templates select="alu:r[position()=$p130]"/>
						</xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.64.Avg">
							<xsl:apply-templates select="alu:r[position()=$p131]"/>
						</xsl:element>
						<xsl:element name="VS.IRMTimeFreeDlCodesBySpreadingFactor.8.Avg">
							<xsl:apply-templates select="alu:r[position()=$p132]"/>
						</xsl:element>
						<xsl:element name="VS.IuAbnormalReleaseRequestCs.DlAsCnfCsSpeechNbLrAmr">
							<xsl:apply-templates select="alu:r[position()=$p133]"/>
						</xsl:element>
						<xsl:element name="VS.IuAbnormalReleaseRequestCs.DlAsCnfCsSpeechWbAmr">
							<xsl:apply-templates select="alu:r[position()=$p134]"/>
						</xsl:element>
						<xsl:element name="VS.IuAbnormalReleaseRequestPs.DlAsCnfHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p135]"/>
						</xsl:element>
						<xsl:element name="VS.IuDlAmrFrmFqc.FrmBad">
							<xsl:apply-templates select="alu:r[position()=$p136]"/>
						</xsl:element>
						<xsl:element name="VS.IuDlAmrFrmFqc.FrmBadRadio">
							<xsl:apply-templates select="alu:r[position()=$p137]"/>
						</xsl:element>
						<xsl:element name="VS.IuDlAmrFrmFqc.FrmGood">
							<xsl:apply-templates select="alu:r[position()=$p138]"/>
						</xsl:element>
						<xsl:element name="VS.IuDlAmrWbFrmFqc.FrmBad">
							<xsl:apply-templates select="alu:r[position()=$p139]"/>
						</xsl:element>
						<xsl:element name="VS.IuDlAmrWbFrmFqc.FrmBadRadio">
							<xsl:apply-templates select="alu:r[position()=$p140]"/>
						</xsl:element>
						<xsl:element name="VS.IuDlAmrWbFrmFqc.FrmGood">
							<xsl:apply-templates select="alu:r[position()=$p141]"/>
						</xsl:element>
						<xsl:element name="VS.IuReleaseCompleteCs.DlAsCnfCsSpeechNbLrAmr">
							<xsl:apply-templates select="alu:r[position()=$p142]"/>
						</xsl:element>
						<xsl:element name="VS.IuReleaseCompleteCs.DlAsCnfCsSpeechWbAmr">
							<xsl:apply-templates select="alu:r[position()=$p143]"/>
						</xsl:element>
						<xsl:element name="VS.OutGoInterFreqHoAtt.Rescue">
							<xsl:apply-templates select="alu:r[position()=$p144]"/>
						</xsl:element>
						<xsl:element name="VS.OutGoInterFreqHoAtt.Service">
							<xsl:apply-templates select="alu:r[position()=$p145]"/>
						</xsl:element>
						<xsl:element name="VS.OutGoInterFreqHoSuc.Rescue">
							<xsl:apply-templates select="alu:r[position()=$p146]"/>
						</xsl:element>
						<xsl:element name="VS.OutGoInterFreqHoSuc.Service">
							<xsl:apply-templates select="alu:r[position()=$p147]"/>
						</xsl:element>
						<xsl:element name="VS.PagingCancelledRecords">
							<xsl:apply-templates select="alu:r[position()=$p148]"/>
						</xsl:element>
						<xsl:element name="VS.PagingDelayedRecords">
							<xsl:apply-templates select="alu:r[position()=$p149]"/>
						</xsl:element>
						<xsl:element name="VS.PagingMessagesSentOnPcch">
							<xsl:apply-templates select="alu:r[position()=$p150]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingBackgroundCall">
							<xsl:apply-templates select="alu:r[position()=$p151]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingCauseUnknown">
							<xsl:apply-templates select="alu:r[position()=$p152]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingConversationalCall">
							<xsl:apply-templates select="alu:r[position()=$p153]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingHighPrioritySignalling">
							<xsl:apply-templates select="alu:r[position()=$p154]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingInteractiveCall">
							<xsl:apply-templates select="alu:r[position()=$p155]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingLowPrioritySignalling">
							<xsl:apply-templates select="alu:r[position()=$p156]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledCs.TerminatingStreamingCall">
							<xsl:apply-templates select="alu:r[position()=$p157]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingBackgroundCall">
							<xsl:apply-templates select="alu:r[position()=$p158]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingCauseUnknown">
							<xsl:apply-templates select="alu:r[position()=$p159]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingConversationalCall">
							<xsl:apply-templates select="alu:r[position()=$p160]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingHighPrioritySignalling">
							<xsl:apply-templates select="alu:r[position()=$p161]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingInteractiveCall">
							<xsl:apply-templates select="alu:r[position()=$p162]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingLowPrioritySignalling">
							<xsl:apply-templates select="alu:r[position()=$p163]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRecordsUnscheduledPs.TerminatingStreamingCall">
							<xsl:apply-templates select="alu:r[position()=$p164]"/>
						</xsl:element>
						<xsl:element name="VS.PagingRejectedRequests">
							<xsl:apply-templates select="alu:r[position()=$p165]"/>
						</xsl:element>
						<xsl:element name="VS.RAB.Mean.CSV.Sum.Cum">
							<xsl:apply-templates select="alu:r[position()=$p166]"/>
						</xsl:element>
						<xsl:element name="VS.RAB.SuccEstab.PS.ReqNotGranted.DCH_HSDSCH_GrantedDCH_DCH">
							<xsl:apply-templates select="alu:r[position()=$p167]"/>
						</xsl:element>
						<xsl:element name="VS.RAB.SuccEstab.PS.ReqNotGranted.EDCH_HSDSCH_GrantedDCH_DCH">
							<xsl:apply-templates select="alu:r[position()=$p168]"/>
						</xsl:element>
						<xsl:element name="VS.RAB.SuccEstab.PS.ReqNotGranted.EDCH_HSDSCH_GrantedDCH_HSDSCH">
							<xsl:apply-templates select="alu:r[position()=$p169]"/>
						</xsl:element>
						<xsl:element name="VS.RadioBearerReleaseSuccess.SrcCallHsdpaEdch">
							<xsl:apply-templates select="alu:r[position()=$p170]"/>
						</xsl:element>
						<xsl:element name="VS.RadioBearerReleaseSuccess.SrcCallHsdpaR99">
							<xsl:apply-templates select="alu:r[position()=$p171]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsData">
							<xsl:apply-templates select="alu:r[position()=$p172]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsDataPsDch">
							<xsl:apply-templates select="alu:r[position()=$p173]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsDataPsHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p174]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeech">
							<xsl:apply-templates select="alu:r[position()=$p175]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeechHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p176]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeechPsDch">
							<xsl:apply-templates select="alu:r[position()=$p177]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeechPsDchPsDch">
							<xsl:apply-templates select="alu:r[position()=$p178]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsSpeechPsDchPsHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p179]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.CsStr">
							<xsl:apply-templates select="alu:r[position()=$p180]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.Other">
							<xsl:apply-templates select="alu:r[position()=$p181]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsDchDlDchUl">
							<xsl:apply-templates select="alu:r[position()=$p182]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsDchPsDch">
							<xsl:apply-templates select="alu:r[position()=$p183]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsDchPsHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p184]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsHsdpaDchUl">
							<xsl:apply-templates select="alu:r[position()=$p185]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsHsdpaDlDchEdchUl">
							<xsl:apply-templates select="alu:r[position()=$p186]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.PsHsdpaDlEdchUl">
							<xsl:apply-templates select="alu:r[position()=$p187]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupRequest.Sig">
							<xsl:apply-templates select="alu:r[position()=$p188]"/>
						</xsl:element>
						<xsl:element name="VS.RadioLinkSetupUnsuccess.LackBwthIub">
							<xsl:apply-templates select="alu:r[position()=$p189]"/>
						</xsl:element>
						<xsl:element name="VS.ReceivedPagingRequestType2CellDch.WithCoreNetworkCs">
							<xsl:apply-templates select="alu:r[position()=$p190]"/>
						</xsl:element>
						<xsl:element name="VS.ReceivedPagingRequestType2CellDch.WithCoreNetworkPs">
							<xsl:apply-templates select="alu:r[position()=$p191]"/>
						</xsl:element>
						<xsl:element name="VS.ReceivedPagingRequestType2CellFach.WithCoreNetworkCs">
							<xsl:apply-templates select="alu:r[position()=$p192]"/>
						</xsl:element>
						<xsl:element name="VS.ReceivedPagingRequestType2CellFach.WithCoreNetworkPs">
							<xsl:apply-templates select="alu:r[position()=$p193]"/>
						</xsl:element>
						<xsl:element name="VS.RrcActiveSetUpdateCompleteProcedure">
							<xsl:apply-templates select="alu:r[position()=$p194]"/>
						</xsl:element>
						<xsl:element name="VS.RrcActiveSetUpdateUnsuccess.RrcActiveSetUpdateFailure">
							<xsl:apply-templates select="alu:r[position()=$p195]"/>
						</xsl:element>
						<xsl:element name="VS.RrcActiveSetUpdateUnsuccess.Timeout">
							<xsl:apply-templates select="alu:r[position()=$p196]"/>
						</xsl:element>
						<xsl:element name="VS.UplinkRssi.Cum">
							<xsl:apply-templates select="alu:r[position()=$p197]"/>
						</xsl:element>
						<xsl:element name="VS.UplinkRssi.Max">
							<xsl:apply-templates select="alu:r[position()=$p198]"/>
						</xsl:element>
						<xsl:element name="VS.UplinkRssi.NbEvt">
							<xsl:apply-templates select="alu:r[position()=$p199]"/>
						</xsl:element>
						<xsl:element name="VS.UpsizingSuccess.DchHsdpa">
							<xsl:apply-templates select="alu:r[position()=$p200]"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m3">
					<xsl:element name="NeighbouringRnc">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:variable name="p1" select="count((../alu:mt[.='RAB.AttEstab.PS.TrChn.NeighbRnc.DCH_HSDSCH'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p2" select="count((../alu:mt[.='RAB.AttEstab.PS.TrChn.NeighbRnc.EDCH_HSDSCH'])[1]/preceding-sibling::*)-1"/>
						<xsl:element name="RAB.AttEstab.PS.TrChn.NeighbRnc.DCH_HSDSCH">
							<xsl:apply-templates select="alu:r[position()=$p1]"/>
						</xsl:element>
						<xsl:element name="RAB.AttEstab.PS.TrChn.NeighbRnc.EDCH_HSDSCH">
							<xsl:apply-templates select="alu:r[position()=$p2]"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
			</xsl:choose>
		</xsl:element>
	</xsl:template>
	<!--
++
-->
	<xsl:template match="alu:r">
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
