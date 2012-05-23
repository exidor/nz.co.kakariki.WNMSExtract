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
	mi1 RncEquipment,INode,AtmPort,Vcc
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
		<xsl:element name="INodeVcc">
			<xsl:variable name="m1" select="count(/alu:mdc/alu:md/alu:mi/alu:mv/alu:moid[contains(.,'Vcc')][1]/parent::*/parent::*/preceding-sibling::*)"/>
			<xsl:element name="id">
				<xsl:apply-templates select="alu:mdc/alu:md/alu:neid/alu:neun"/>
			</xsl:element>
			<xsl:element name="timestamp">
				<xsl:apply-templates select="alu:mdc/alu:mfh/alu:cbt"/>
			</xsl:element>
			<xsl:for-each select="alu:mdc/alu:md/alu:mi">
				<xsl:variable name="p" select="position()"/>
				<xsl:if test="$p=$m1">
					<xsl:apply-templates select="alu:mv">
						<xsl:with-param name="mi_pos" select="$p"/>
						<xsl:with-param name="m1" select="$m1"/>
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
		<xsl:variable name="id4">
			<xsl:choose>
				<xsl:when test="contains(substring-after(substring-after(substring-after(./alu:moid,','),','),','),',')">
					<!-- has 4 or more commas -->
					<xsl:value-of select="substring-after(substring-before(substring-after(substring-after(substring-after(./alu:moid,','),','),','),','),'=')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring-after(substring-after(substring-after(substring-after(./alu:moid,','),','),','),'=')"/>
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
				<xsl:if test="$id3&gt;807"><!-- discard Iub -->
				<xsl:element name="AtmPort">
					<xsl:attribute name="id"><xsl:value-of select="$id3"/></xsl:attribute>
					<xsl:choose>
					<xsl:when test="$mi_pos=$m1">
						<!-- AtmPort p=1-->
						<xsl:element name="Vcc">
							<xsl:attribute name="id"><xsl:value-of select="$id4"/></xsl:attribute>
							<xsl:variable name="p1" select="count((../alu:mt[.='VS.AcVccIngressCellCountClp0'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p2" select="count((../alu:mt[.='VS.AcVccIngressCellCountClp01'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p3" select="count((../alu:mt[.='VS.AcVccEgressCellCountClp0'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p4" select="count((../alu:mt[.='VS.AcVccEgressCellCountClp01'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p5" select="count((../alu:mt[.='VS.AcVccIngressDiscardedClp0'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p6" select="count((../alu:mt[.='VS.AcVccIngressDiscardedClp01'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p7" select="count((../alu:mt[.='VS.AcVccEgressDiscardedClp0'])[1]/preceding-sibling::*)-1"/>
							<xsl:variable name="p8" select="count((../alu:mt[.='VS.AcVccEgressDiscardedClp01'])[1]/preceding-sibling::*)-1"/>
							<xsl:element name="VS.AcVccIngressCellCountClp0">
								<xsl:apply-templates select="alu:r[position()=$p1]"/>
							</xsl:element>
							<xsl:element name="VS.AcVccIngressCellCountClp01">
								<xsl:apply-templates select="alu:r[position()=$p2]"/>
							</xsl:element>
							<xsl:element name="VS.AcVccEgressCellCountClp0">
								<xsl:apply-templates select="alu:r[position()=$p3]"/>
							</xsl:element>
							<xsl:element name="VS.AcVccEgressCellCountClp01">
								<xsl:apply-templates select="alu:r[position()=$p4]"/>
							</xsl:element>
							<xsl:element name="VS.AcVccIngressDiscardedClp0">
								<xsl:apply-templates select="alu:r[position()=$p5]"/>
							</xsl:element>
							<xsl:element name="VS.AcVccIngressDiscardedClp01">
								<xsl:apply-templates select="alu:r[position()=$p6]"/>
							</xsl:element>
							<xsl:element name="VS.AcVccEgressDiscardedClp0">
								<xsl:apply-templates select="alu:r[position()=$p7]"/>
							</xsl:element>
							<xsl:element name="VS.AcVccEgressDiscardedClp01">
								<xsl:apply-templates select="alu:r[position()=$p8]"/>
							</xsl:element>
						</xsl:element>
					</xsl:when>

				</xsl:choose>
				</xsl:element>
				</xsl:if>
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
