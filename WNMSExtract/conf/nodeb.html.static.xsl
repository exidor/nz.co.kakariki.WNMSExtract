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
		<xsl:element name="NodeB">
			<!-- assuming zero index always occurs, if not use contains() etc -->
			<xsl:variable name="m1" select="count(/mdc/md/mi/mv/moid[.='NodeBEquipment=0'][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m2" select="count(/mdc/md/mi/mv/moid[.='NodeBEquipment=0,BtsCell=0'][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m3" select="count(/mdc/md/mi/mv/moid[contains(.,'PassiveComponent')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m4" select="count(/mdc/md/mi/mv/moid[contains(.,'IMAGroup')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m5" select="count(/mdc/md/mi/mv/moid[contains(.,'IpRan')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m6" select="count(/mdc/md/mi/mv/moid[contains(.,'CCM')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m7" select="count(/mdc/md/mi/mv/moid[contains(.,'CEM')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m8" select="count(/mdc/md/mi/mv/moid[contains(.,'HSDPAService')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m9" select="count(/mdc/md/mi/mv/moid[contains(.,'HSUPAService')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:element name="id">
				<xsl:apply-templates select="mdc/md/neid/neun"/>
			</xsl:element>
			<xsl:element name="timestamp">
				<xsl:apply-templates select="mdc/mfh/cbt"/>
			</xsl:element>
			<xsl:for-each select="mdc/md/mi">
				<xsl:variable name="p" select="position()"/>
				<xsl:if test="$p=$m1 or $p=$m2 or $p=$m3 or $p=$m4 or $p=$m5 or $p=$m6 or $p=$m7 or $p=$m8 or $p=$m9">
					<xsl:apply-templates select="mv">
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
	<xsl:template match="mv">
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
				<xsl:when test="contains(./moid,',')">
					<xsl:value-of select="substring-before(substring-after(./moid,'='),',')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring-after(./moid,'=')"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!-- ++ -->
		<xsl:variable name="id2">
			<xsl:choose>
				<xsl:when test="contains(substring-after(./moid,','),',')">
					<!-- has 2 or more commas -->
					<xsl:value-of select="substring-after(substring-before(substring-after(./moid,','),','),'=')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring-after(substring-after(./moid,','),'=')"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!-- ++ -->
		<xsl:variable name="id3" select="substring-after(substring-after(substring-after(./moid,','),','),'=')"/>
		<!-- ++ -->
		<xsl:element name="NodeBEquipment">
			<xsl:attribute name="id"><xsl:value-of select="$id1"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$mi_pos=$m1">
					<!-- NBE p=1-->

					<xsl:element name="VS.CEMUsedDCH.Avg">
						<xsl:apply-templates select="r[position()=63]"/>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m2">
					<!-- BC p=2 -->
					<xsl:element name="BtsCell">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>

						<xsl:element name="VS.CellULload.Total.Cum">
							<xsl:apply-templates select="r[position()=21]"/>
						</xsl:element>
						<xsl:element name="VS.CellULload.Total.NbEvt">
							<xsl:apply-templates select="r[position()=23]"/>
						</xsl:element>
						<xsl:element name="VS.CellULload.eDCH.Cum">
							<xsl:apply-templates select="r[position()=26]"/>
						</xsl:element>
						<xsl:element name="VS.CellULload.eDCH.NbEvt">
							<xsl:apply-templates select="r[position()=28]"/>
						</xsl:element>
						<xsl:element name="VS.RadioTxCarrierPwr.Used.Avg">
							<xsl:apply-templates select="r[position()=17]"/>
						</xsl:element>
						<xsl:element name="VS.RadioTxCarrierPwr.Oper.Max">
							<xsl:apply-templates select="r[position()=14]"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m3">
					<!-- PassiveComponent p=4 -->
					<xsl:element name="PassiveComponent">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:element name="PA">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>

							<xsl:element name="VS.PAPwr.Avg">
								<xsl:apply-templates select="r[position()=2]"/>
							</xsl:element>
							<xsl:element name="VS.PAPwr.Max">
								<xsl:apply-templates select="r[position()=4]"/>
							</xsl:element>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m4">
					<!-- IMAGROUP p=5 -->
					<xsl:element name="ImaGroup">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>

						<xsl:element name="VS.imaGroupNbReceivedCell.Avg">
							<xsl:apply-templates select="r[position()=4]"/>
						</xsl:element>
						<xsl:element name="VS.imaGroupNbSentCell.Avg">
							<xsl:apply-templates select="r[position()=9]"/>
						</xsl:element>
						<xsl:element name="VS.imaGroupNbReceivedCell.Max">
							<xsl:apply-templates select="r[position()=6]"/>
						</xsl:element>
						<xsl:element name="VS.imaGroupNbSentCell.Max">
							<xsl:apply-templates select="r[position()=11]"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m5">
					<!-- IPRAN p=7 -->
					<xsl:element name="IpRan">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>

						<xsl:element name="VS.ifOutOctets">
							<xsl:apply-templates select="r[position()=7]"/>
						</xsl:element>
						<xsl:element name="VS.ifOutUserPlaneOctets">
							<xsl:apply-templates select="r[position()=13]"/>
						</xsl:element>
						<xsl:element name="VS.ifInOctets">
							<xsl:apply-templates select="r[position()=1]"/>
						</xsl:element>
						<xsl:element name="VS.ifInUserPlaneOctets">
							<xsl:apply-templates select="r[position()=12]"/>
						</xsl:element>
						<xsl:element name="VS.ifInNUcastPkts">
							<xsl:apply-templates select="r[position()=3]"/>
						</xsl:element>
						<xsl:element name="VS.ifInUcastPkts">
							<xsl:apply-templates select="r[position()=2]"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$mi_pos=$m6">
					<!-- CCM p=9 -->
					<xsl:element name="Board">
						<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
						<xsl:element name="Ccm">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:element name="VS.cpuLoad.Max">
								<xsl:apply-templates select="r[position()=4]"/>
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
							<xsl:element name="VS.cpuLoad.Max">
								<xsl:apply-templates select="r[position()=4]"/>
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
						
							<xsl:element name="VS.HsdpaReceivedCQI.Level0">
								<xsl:apply-templates select="r[position()=58]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level1">
								<xsl:apply-templates select="r[position()=59]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level2">
								<xsl:apply-templates select="r[position()=60]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level3">
								<xsl:apply-templates select="r[position()=61]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level4">
								<xsl:apply-templates select="r[position()=62]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level5">
								<xsl:apply-templates select="r[position()=63]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level6">
								<xsl:apply-templates select="r[position()=64]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level7">
								<xsl:apply-templates select="r[position()=65]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level8">
								<xsl:apply-templates select="r[position()=66]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level9">
								<xsl:apply-templates select="r[position()=67]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level10">
								<xsl:apply-templates select="r[position()=68]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level11">
								<xsl:apply-templates select="r[position()=69]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level12">
								<xsl:apply-templates select="r[position()=70]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level13">
								<xsl:apply-templates select="r[position()=71]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level14">
								<xsl:apply-templates select="r[position()=72]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level15">
								<xsl:apply-templates select="r[position()=73]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level16">
								<xsl:apply-templates select="r[position()=74]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level17">
								<xsl:apply-templates select="r[position()=75]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level18">
								<xsl:apply-templates select="r[position()=76]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level19">
								<xsl:apply-templates select="r[position()=77]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level20">
								<xsl:apply-templates select="r[position()=78]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level21">
								<xsl:apply-templates select="r[position()=79]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level22">
								<xsl:apply-templates select="r[position()=80]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level23">
								<xsl:apply-templates select="r[position()=81]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level24">
								<xsl:apply-templates select="r[position()=82]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level25">
								<xsl:apply-templates select="r[position()=83]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level26">
								<xsl:apply-templates select="r[position()=84]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level27">
								<xsl:apply-templates select="r[position()=85]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level28">
								<xsl:apply-templates select="r[position()=86]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level29">
								<xsl:apply-templates select="r[position()=87]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaReceivedCQI.Level30">
								<xsl:apply-templates select="r[position()=88]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitsMAChs.Cum">
								<xsl:apply-templates select="r[position()=31]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIsUsed">
								<xsl:apply-templates select="r[position()=57]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat6">
								<xsl:apply-templates select="r[position()=113]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat6">
								<xsl:apply-templates select="r[position()=95]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat8">
								<xsl:apply-templates select="r[position()=115]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat8">
								<xsl:apply-templates select="r[position()=97]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat10">
								<xsl:apply-templates select="r[position()=117]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat10">
								<xsl:apply-templates select="r[position()=99]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat14">
								<xsl:apply-templates select="r[position()=121]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat14">
								<xsl:apply-templates select="r[position()=103]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat15">
								<xsl:apply-templates select="r[position()=122]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat15">
								<xsl:apply-templates select="r[position()=104]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat16">
								<xsl:apply-templates select="r[position()=123]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat16">
								<xsl:apply-templates select="r[position()=105]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTxDataBitPerUEcat.UEcat17">
								<xsl:apply-templates select="r[position()=124]"/>
							</xsl:element>
							<xsl:element name="VS.HsdpaTTIperUEcat.UEcat17">
								<xsl:apply-templates select="r[position()=106]"/>
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

							<xsl:element name="VS.eDCHdataBitSentToRNC.Cum">
								<xsl:apply-templates select="r[position()=6]"/>
							</xsl:element>
							<xsl:element name="VS.eDCHactiveUsers.2ms_users.Cum">
								<xsl:apply-templates select="r[position()=21]"/>
							</xsl:element>
							<xsl:element name="VS.eDCHactiveUsers.10ms_users.Cum">
								<xsl:apply-templates select="r[position()=26]"/>
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
