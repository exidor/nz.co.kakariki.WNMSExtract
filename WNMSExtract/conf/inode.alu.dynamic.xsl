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
	mi1 RncEquipment,INode,AtmPort
	mi2 RncEquipment,INode,Lp
	mi3 RncEquipment,INode,Lp,Ap
	mi4 RncEquipment,INode,Lp,Ethernet
	mi5 RncEquipment,INode,Lp,Ethernet,TrafficManager,EmissionPriority
	mi6 RncEquipment,INode,Lan,EthernetStatistics
	mi7 RncEquipment,INode,Lan,Vlan,EthernetStatistics
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
		<xsl:element name="INode">
			<xsl:variable name="m1" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'AtmPort')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m2" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'Lp') and not(contains(.,'Ap')) and not(contains(.,'Ethernet'))][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m3" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'Ap')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:variable name="m4" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'Ethernet=') and not(contains(.,'TrafficManager'))][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:element name="id">
				<xsl:apply-templates select="alu:mdc/alu:md/alu:neid/alu:neun"/>
			</xsl:element>
			<xsl:element name="timestamp">
				<xsl:apply-templates select="alu:mdc/alu:mfh/alu:cbt"/>
			</xsl:element>
			<xsl:for-each select="alu:mdc/alu:md/alu:mi">
				<xsl:variable name="p" select="position()"/>
				<xsl:if test="$p=$m1 or $p=$m2 or $p=$m3 or $p=$m4">
					<xsl:apply-templates select="alu:mv">
						<xsl:with-param name="mi_pos" select="$p"/>
						<xsl:with-param name="m1" select="$m1"/>
						<xsl:with-param name="m2" select="$m2"/>
						<xsl:with-param name="m3" select="$m3"/>
						<xsl:with-param name="m4" select="$m4"/>
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
		<xsl:variable name="id3">
			<xsl:choose>
				<xsl:when test="contains(substring-after(substring-after(./alu:moid,','),','),',')">
					<!-- has 3 or more commas -->
					<xsl:value-of select="substring-after(substring-before(substring-after(substring-after(./alu:moid,','),','),','),'=')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring-after(substring-after(substring-after(./alu:moid,','),','),'=')"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!-- ++ -->
		<xsl:variable name="id4" select="substring-after(substring-after(substring-after(substring-after(./alu:moid,','),','),','),'=')"/>
		<!-- ++ -->
		<xsl:element name="RncEquipment">
			<xsl:attribute name="id"><xsl:value-of select="$id1"/></xsl:attribute>
			<xsl:element name="INode">
				<xsl:attribute name="id"><xsl:value-of select="$id2"/></xsl:attribute>
				<xsl:choose>
					<xsl:when test="$mi_pos=$m1">
						<!-- AtmPort p=1-->
						<xsl:element name="AtmPort">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:variable name="p1" select="count((../alu:mt[.='VS.RxMaxCellRate'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p2" select="count((../alu:mt[.='VS.TxMaxCellRate'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p3" select="count((../alu:mt[.='VS.RxAvgCellRate'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p4" select="count((../alu:mt[.='VS.TxAvgCellRate'])[1]/preceding-sibling::*)-1"/>
							<xsl:element name="VS.RxMaxCellRate">
								<xsl:apply-templates select="alu:r[position()=$p1]"/>
							</xsl:element>
							<xsl:element name="VS.TxMaxCellRate">
								<xsl:apply-templates select="alu:r[position()=$p2]"/>
							</xsl:element>
							<xsl:element name="VS.RxAvgCellRate">
								<xsl:apply-templates select="alu:r[position()=$p3]"/>
							</xsl:element>
							<xsl:element name="VS.TxAvgCellRate">
								<xsl:apply-templates select="alu:r[position()=$p4]"/>
							</xsl:element>
						</xsl:element>
					</xsl:when>
					<xsl:when test="$mi_pos=$m2">
						<!-- Ap p=5 -->
						<xsl:element name="Lp">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:variable name="p1" select="count((../alu:mt[.='VS.CpuUtilAvg'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p2" select="count((../alu:mt[.='VS.CpuUtilAvgMax'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p3" select="count((../alu:mt[.='VS.CpuUtilAvgMin'])[1]/preceding-sibling::*)-1"/>
							<xsl:element name="VS.CpuUtilAvg">
								<xsl:apply-templates select="alu:r[position()=$p1]"/>
							</xsl:element>
							<xsl:element name="VS.CpuUtilAvgMax">
								<xsl:apply-templates select="alu:r[position()=$p2]"/>
							</xsl:element>
							<xsl:element name="VS.CpuUtilAvgMin">
								<xsl:apply-templates select="alu:r[position()=$p3]"/>
							</xsl:element>
						</xsl:element>
					</xsl:when>
					<xsl:when test="$mi_pos=$m3">
						<!-- Ap p=5 -->
						<xsl:element name="Lp">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:element name="Ap">
								<xsl:attribute name="id"><xsl:value-of select="$id4"/></xsl:attribute>
								<xsl:variable name="p1" select="count((../alu:mt[.='VS.ApCpuUtilizationAvg'])[1]/preceding-sibling::*)-1"/>
								<xsl:element name="VS.APCpuUtilizationAvg">
									<xsl:apply-templates select="alu:r[position()=$p1]"/>
								</xsl:element>
							</xsl:element>
						</xsl:element>
					</xsl:when>
					<xsl:when test="$mi_pos=$m4">
						<!-- Ethernet p=2 -->
						<xsl:element name="Lp">
							<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
							<xsl:element name="Ethernet">
								<xsl:attribute name="id"><xsl:value-of select="$id4"/></xsl:attribute>
								<!-- '-1' is important here as it will change if the structure changes, relies on seq{mts,gp,mt...} -->
								<xsl:variable name="p1" select="count((../alu:mt[.='VS.avgRxUtilization'])[1]/preceding-sibling::*)-1"/>
								<xsl:variable name="p2" select="count((../alu:mt[.='VS.avgTxUtilization'])[1]/preceding-sibling::*)-1"/>
								<xsl:variable name="p3" select="count((../alu:mt[.='VS.maxRxUtilization'])[1]/preceding-sibling::*)-1"/>
								<xsl:variable name="p4" select="count((../alu:mt[.='VS.maxTxUtilization'])[1]/preceding-sibling::*)-1"/>
								<xsl:element name="VS.avgRxUtilization">
									<xsl:apply-templates select="alu:r[position()=$p1]"/>
								</xsl:element>
								<xsl:element name="VS.avgTxUtilization">
									<xsl:apply-templates select="alu:r[position()=$p2]"/>
								</xsl:element>
								<xsl:element name="VS.maxRxUtilization">
									<xsl:apply-templates select="alu:r[position()=$p3]"/>
								</xsl:element>
								<xsl:element name="VS.maxTxUtilization">
									<xsl:apply-templates select="alu:r[position()=$p4]"/>
								</xsl:element>
							</xsl:element>
						</xsl:element>
					</xsl:when>
				</xsl:choose>
			</xsl:element>
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
