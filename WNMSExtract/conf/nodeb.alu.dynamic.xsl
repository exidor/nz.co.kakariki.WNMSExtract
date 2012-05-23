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
	mi1 NodeBEquipment
	mi2 NodebEquipment,BtsCell
	mi3 NodebEquipment,PcmLink
	mi4 NodebEquipment,PassiveComponent,PA
	mi5 NodebEquipment,ImaGroup
	mi6 NodebEquipment,IpInterface
	mi7 NodebEquipment,IpRan // not available for HybridIuB
	mi8 NodebEquipment,Board,TRM
	mi9 NodebEquipment,Board,CCM
	mi10 NodebEquipment,Board,CEM
	mi11 NodebEquipment,AtmVcc,Aal2
	mi12 NodebEquipment,AtmVcc,Aal5
	mi13 NodebEquipment,BtsCell,HSDPAService
	mi14 NodebEquipment,BtsCell,HSUPAService
	mi15 NodebEquipment,LocalCellGroup
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
		<xsl:element name="NodeB">
			<!-- assuming zero index always occurs, if not use contains() etc -->
			<xsl:variable name="m1" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[.='NodeBEquipment=0'][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m2" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[.='NodeBEquipment=0,BtsCell=0'][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m3" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'PassiveComponent')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m4" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'IMAGroup')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m5" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'IpRan')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m6" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'CCM')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m7" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'CEM')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m8" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'HSDPAService')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m9" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'HSUPAService')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:element name="id">
				<xsl:apply-templates select="alu:mdc/alu:md/alu:neid/alu:neun"/>
			</xsl:element>
			<xsl:element name="timestamp">
				<xsl:apply-templates select="alu:mdc/alu:mfh/alu:cbt"/>
			</xsl:element>
			<xsl:for-each select="alu:mdc/alu:md/alu:mi">
				<xsl:variable name="p" select="position()"/>
				<xsl:if test="$p=$m1 or $p=$m2 or $p=$m3 or $p=$m4 or $p=$m5 or $p=$m6 or $p=$m7 or $p=$m8 or $p=$m9">
					<xsl:apply-templates select="alu:mv">
						<xsl:with-param name="mi_pos" select="$p"/>
						<xsl:with-param name="m1" select="$m1"/>
						<xsl:with-param name="m2" select="$m2"/>
						<xsl:with-param name="m3" select="$m3"/>
						<xsl:with-param name="m4" select="$m4"/>
						<xsl:with-param name="m5" select="$m5"/>
						<xsl:with-param name="m6" select="$m6"/>
						<xsl:with-param name="m7" select="$m7"/>
						<xsl:with-param name="m8" select="$m8"/>
						<xsl:with-param name="m9" select="$m9"/>
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
		<xsl:param name="m4"/>
		<xsl:param name="m5"/>
		<xsl:param name="m6"/>
		<xsl:param name="m7"/>
		<xsl:param name="m8"/>
		<xsl:param name="m9"/>
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
		<xsl:variable name="id2">
			<xsl:choose>
				<xsl:when test="contains(substring-after(./alu:moid,','),',')">
					<!-- has 2 or more commas -->
					<xsl:value-of select="substring-after(substring-before(substring-after(./alu:moid,','),','),'=')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring-after(substring-after(./alu:moid,','),'=')"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!-- ++ -->
		<xsl:variable name="id3" select="substring-after(substring-after(substring-after(./alu:moid,','),','),'=')"/>
		<!-- ++ -->
		<xsl:element name="NodeBEquipment">
			<xsl:attribute name="id"><xsl:value-of select="$id1"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$mi_pos=$m1">
					<!-- NBE p=1-->
					<xsl:variable name="p1" select="count((../alu:mt[.='VS.CEMUsedDCH.Avg'])[1]/preceding-sibling::*)-1"/>
					<xsl:element name="VS.CEMUsedDCH.Avg">
						<xsl:apply-templates select="alu:r[position()=$p1]"/>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m2">
					<!-- BC p=2 -->
					<xsl:element name="BtsCell">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<!-- '-1' is important here as it will change if the structure changes, relies on {mts,gp,mt...} -->
						<xsl:variable name="p1" select="count((../alu:mt[.='VS.CellULload.Total.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p2" select="count((../alu:mt[.='VS.CellULload.Total.NbEvt'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p3" select="count((../alu:mt[.='VS.CellULload.eDCH.Cum'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p4" select="count((../alu:mt[.='VS.CellULload.eDCH.NbEvt'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p5" select="count((../alu:mt[.='VS.RadioTxCarrierPwr.Used.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p6" select="count((../alu:mt[.='VS.RadioTxCarrierPwr.Oper.Max'])[1]/preceding-sibling::*)-1"/>
						<xsl:element name="VS.CellULload.Total.Cum">
							<xsl:apply-templates select="alu:r[position()=$p1]"/>
						</xsl:element>
						<xsl:element name="VS.CellULload.Total.NbEvt">
							<xsl:apply-templates select="alu:r[position()=$p2]"/>
						</xsl:element>
						<xsl:element name="VS.CellULload.eDCH.Cum">
							<xsl:apply-templates select="alu:r[position()=$p3]"/>
						</xsl:element>
						<xsl:element name="VS.CellULload.eDCH.NbEvt">
							<xsl:apply-templates select="alu:r[position()=$p4]"/>
						</xsl:element>
						<xsl:element name="VS.RadioTxCarrierPwr.Used.Avg">
							<xsl:apply-templates select="alu:r[position()=$p5]"/>
						</xsl:element>
						<xsl:element name="VS.RadioTxCarrierPwr.Oper.Max">
							<xsl:apply-templates select="alu:r[position()=$p6]"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m3">
					<!-- PassiveComponent p=4 -->
					<xsl:element name="PassiveComponent">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:element name="PA">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:variable name="p1" select="count((../alu:mt[.='VS.PAPwr.Avg'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p2" select="count((../alu:mt[.='VS.PAPwr.Max'])[1]/preceding-sibling::*)-1"/>
							<xsl:element name="VS.PAPwr.Avg">
								<xsl:apply-templates select="alu:r[position()=$p1]"/>
							</xsl:element>
							<xsl:element name="VS.PAPwr.Max">
								<xsl:apply-templates select="alu:r[position()=$p2]"/>
							</xsl:element>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m4">
					<!-- IMAGROUP p=5 -->
					<xsl:element name="ImaGroup">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:variable name="p1" select="count((../alu:mt[.='VS.imaGroupNbReceivedCell.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p2" select="count((../alu:mt[.='VS.imaGroupNbSentCell.Avg'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p3" select="count((../alu:mt[.='VS.imaGroupNbReceivedCell.Max'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p4" select="count((../alu:mt[.='VS.imaGroupNbSentCell.Max'])[1]/preceding-sibling::*)-1"/>
						<xsl:element name="VS.imaGroupNbReceivedCell.Avg">
							<xsl:apply-templates select="alu:r[position()=$p1]"/>
						</xsl:element>
						<xsl:element name="VS.imaGroupNbSentCell.Avg">
							<xsl:apply-templates select="alu:r[position()=$p2]"/>
						</xsl:element>
						<xsl:element name="VS.imaGroupNbReceivedCell.Max">
							<xsl:apply-templates select="alu:r[position()=$p3]"/>
						</xsl:element>
						<xsl:element name="VS.imaGroupNbSentCell.Max">
							<xsl:apply-templates select="alu:r[position()=$p4]"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m5">
					<!-- IPRAN p=7 -->
					<xsl:element name="IpRan">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:variable name="p1" select="count((../alu:mt[.='VS.ifOutOctets'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p2" select="count((../alu:mt[.='VS.ifOutUserPlaneOctets'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p3" select="count((../alu:mt[.='VS.ifInOctets'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p4" select="count((../alu:mt[.='VS.ifInUserPlaneOctets'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p5" select="count((../alu:mt[.='VS.ifInNUcastPkts'])[1]/preceding-sibling::*)-1"/>
						<xsl:variable name="p6" select="count((../alu:mt[.='VS.ifInUcastPkts'])[1]/preceding-sibling::*)-1"/>
						<xsl:element name="VS.ifOutOctets">
							<xsl:apply-templates select="alu:r[position()=$p1]"/>
						</xsl:element>
						<xsl:element name="VS.ifOutUserPlaneOctets">
							<xsl:apply-templates select="alu:r[position()=$p2]"/>
						</xsl:element>
						<xsl:element name="VS.ifInOctets">
							<xsl:apply-templates select="alu:r[position()=$p3]"/>
						</xsl:element>
						<xsl:element name="VS.ifInUserPlaneOctets">
							<xsl:apply-templates select="alu:r[position()=$p4]"/>
						</xsl:element>
						<xsl:element name="VS.ifInNUcastPkts">
							<xsl:apply-templates select="alu:r[position()=$p5]"/>
						</xsl:element>
						<xsl:element name="VS.ifInUcastPkts">
							<xsl:apply-templates select="alu:r[position()=$p6]"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m6">
					<!-- CCM p=9 -->
					<xsl:element name="Board">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:element name="Ccm">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:variable name="p1" select="count((../alu:mt[.='VS.cpuLoad.Max'])[1]/preceding-sibling::*)-1"/>
							<xsl:element name="VS.cpuLoad.Max">
								<xsl:apply-templates select="alu:r[position()=$p1]"/>
							</xsl:element>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m7">
					<!-- CEM p=10 -->
					<xsl:element name="Board">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:element name="Cem">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:variable name="p1" select="count((../alu:mt[.='VS.cpuLoad.Max'])[1]/preceding-sibling::*)-1"/>
							<xsl:element name="VS.cpuLoad.Max">
								<xsl:apply-templates select="alu:r[position()=$p1]"/>
							</xsl:element>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m8">
					<!-- CQI p=13 -->
					<xsl:element name="BtsCell">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:element name="HSDPAService">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:variable name="p1" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level0'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p2" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level1'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p3" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level2'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p4" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level3'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p5" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level4'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p6" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level5'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p7" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level6'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p8" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level7'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p9" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level8'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p10" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level9'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p11" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level10'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p12" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level11'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p13" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level12'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p14" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level13'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p15" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level14'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p16" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level15'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p17" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level16'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p18" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level17'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p19" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level18'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p20" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level19'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p21" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level20'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p22" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level21'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p23" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level22'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p24" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level23'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p25" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level24'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p26" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level25'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p27" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level26'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p28" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level27'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p29" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level28'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p30" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level29'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p31" select="count((../alu:mt[.='VS.HsdpaReceivedCQI.Level30'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p32" select="count((../alu:mt[.='VS.HsdpaTxDataBitsMAChs.Cum'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p33" select="count((../alu:mt[.='VS.HsdpaTTIsUsed'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p34" select="count((../alu:mt[.='VS.HsdpaTxDataBitPerUEcat.UEcat6'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p35" select="count((../alu:mt[.='VS.HsdpaTTIperUEcat.UEcat6'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p36" select="count((../alu:mt[.='VS.HsdpaTxDataBitPerUEcat.UEcat8'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p37" select="count((../alu:mt[.='VS.HsdpaTTIperUEcat.UEcat8'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p38" select="count((../alu:mt[.='VS.HsdpaTxDataBitPerUEcat.UEcat10'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p39" select="count((../alu:mt[.='VS.HsdpaTTIperUEcat.UEcat10'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p40" select="count((../alu:mt[.='VS.HsdpaTxDataBitPerUEcat.UEcat14'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p41" select="count((../alu:mt[.='VS.HsdpaTTIperUEcat.UEcat14'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p42" select="count((../alu:mt[.='VS.HsdpaTxDataBitPerUEcat.UEcat15'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p43" select="count((../alu:mt[.='VS.HsdpaTTIperUEcat.UEcat15'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p44" select="count((../alu:mt[.='VS.HsdpaTxDataBitPerUEcat.UEcat16'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p45" select="count((../alu:mt[.='VS.HsdpaTTIperUEcat.UEcat16'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p46" select="count((../alu:mt[.='VS.HsdpaTxDataBitPerUEcat.UEcat17'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p47" select="count((../alu:mt[.='VS.HsdpaTTIperUEcat.UEcat17'])[1]/preceding-sibling::*)-1"/>
							<xsl:element name="VS.HsdpaReceivedCQI.Level0">
								<xsl:apply-templates select="alu:r[position()=$p1]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level1">
								<xsl:apply-templates select="alu:r[position()=$p2]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level2">
								<xsl:apply-templates select="alu:r[position()=$p3]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level3">
								<xsl:apply-templates select="alu:r[position()=$p4]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level4">
								<xsl:apply-templates select="alu:r[position()=$p5]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level5">
								<xsl:apply-templates select="alu:r[position()=$p6]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level6">
								<xsl:apply-templates select="alu:r[position()=$p7]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level7">
								<xsl:apply-templates select="alu:r[position()=$p8]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level8">
								<xsl:apply-templates select="alu:r[position()=$p9]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level9">
								<xsl:apply-templates select="alu:r[position()=$p10]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level10">
								<xsl:apply-templates select="alu:r[position()=$p11]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level11">
								<xsl:apply-templates select="alu:r[position()=$p12]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level12">
								<xsl:apply-templates select="alu:r[position()=$p13]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level13">
								<xsl:apply-templates select="alu:r[position()=$p14]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level14">
								<xsl:apply-templates select="alu:r[position()=$p15]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level15">
								<xsl:apply-templates select="alu:r[position()=$p16]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level16">
								<xsl:apply-templates select="alu:r[position()=$p17]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level17">
								<xsl:apply-templates select="alu:r[position()=$p18]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level18">
								<xsl:apply-templates select="alu:r[position()=$p19]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level19">
								<xsl:apply-templates select="alu:r[position()=$p20]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level20">
								<xsl:apply-templates select="alu:r[position()=$p21]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level21">
								<xsl:apply-templates select="alu:r[position()=$p22]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level22">
								<xsl:apply-templates select="alu:r[position()=$p23]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level23">
								<xsl:apply-templates select="alu:r[position()=$p24]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level24">
								<xsl:apply-templates select="alu:r[position()=$p25]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level25">
								<xsl:apply-templates select="alu:r[position()=$p26]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level26">
								<xsl:apply-templates select="alu:r[position()=$p27]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level27">
								<xsl:apply-templates select="alu:r[position()=$p28]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level28">
								<xsl:apply-templates select="alu:r[position()=$p29]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level29">
								<xsl:apply-templates select="alu:r[position()=$p30]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level30">
								<xsl:apply-templates select="alu:r[position()=$p31]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitsMAChs.Cum">
								<xsl:apply-templates select="alu:r[position()=$p32]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIsUsed">
								<xsl:apply-templates select="alu:r[position()=$p33]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat6">
								<xsl:apply-templates select="alu:r[position()=$p34]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat6">
								<xsl:apply-templates select="alu:r[position()=$p35]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat8">
								<xsl:apply-templates select="alu:r[position()=$p36]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat8">
								<xsl:apply-templates select="alu:r[position()=$p37]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat10">
								<xsl:apply-templates select="alu:r[position()=$p38]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat10">
								<xsl:apply-templates select="alu:r[position()=$p39]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat14">
								<xsl:apply-templates select="alu:r[position()=$p40]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat14">
								<xsl:apply-templates select="alu:r[position()=$p41]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat15">
								<xsl:apply-templates select="alu:r[position()=$p42]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat15">
								<xsl:apply-templates select="alu:r[position()=$p43]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat16">
								<xsl:apply-templates select="alu:r[position()=$p44]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat16">
								<xsl:apply-templates select="alu:r[position()=$p45]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat17">
								<xsl:apply-templates select="alu:r[position()=$p46]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat17">
								<xsl:apply-templates select="alu:r[position()=$p47]"/>
							</xsl:element>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m9">
					<!-- CQI p=13 -->
					<xsl:element name="BtsCell">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:element name="HSUPAService">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:variable name="p1" select="count((../alu:mt[.='VS.eDCHdataBitSentToRNC.Cum'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p2" select="count((../alu:mt[.='VS.eDCHactiveUsers.2ms_users.Cum'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p3" select="count((../alu:mt[.='VS.eDCHactiveUsers.10ms_users.Cum'])[1]/preceding-sibling::*)-1"/>
							<xsl:element name="VS.eDCHdataBitSentToRNC.Cum">
								<xsl:apply-templates select="alu:r[position()=$p1]"/>
							</xsl:element>
							<xsl:element name="VS.eDCHactiveUsers.2ms_users.Cum">
								<xsl:apply-templates select="alu:r[position()=$p2]"/>
							</xsl:element>
							<xsl:element name="VS.eDCHactiveUsers.10ms_users.Cum">
								<xsl:apply-templates select="alu:r[position()=$p3]"/>
							</xsl:element>
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
