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
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:alu="http://www.w3.org/TR/REC-xml">
	<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
	<xsl:output method="xml" version="1.0" indent="yes" encoding="UTF-8" standalone="yes"/>
	<!--
++
-->
	<xsl:template match="Operator/attributes">
		<xsl:value-of select="concat(userLabel,'.',mobileCountryCode,'.',mobileNetworkCode)"/>
	</xsl:template>
	<!--
++
-->
	<xsl:template match="snapshot">

<xsl:element name="graphml">
	<xsl:element name="graph">
		<xsl:attribute name="edgedefault"><xsl:text>undirected</xsl:text></xsl:attribute>
		<xsl:element name="key">
			<xsl:attribute name="id"><xsl:text>name</xsl:text></xsl:attribute>
			<xsl:attribute name="for"><xsl:text>node</xsl:text></xsl:attribute>
			<xsl:attribute name="attr.name"><xsl:text>name</xsl:text></xsl:attribute>
			<xsl:attribute name="attr.type"><xsl:text>string</xsl:text></xsl:attribute>
		</xsl:element>
		<!--name-->
		<xsl:element name="key">
			<xsl:attribute name="id"><xsl:text>component</xsl:text></xsl:attribute>
			<xsl:attribute name="for"><xsl:text>node</xsl:text></xsl:attribute>
			<xsl:attribute name="attr.name"><xsl:text>type</xsl:text></xsl:attribute>
			<xsl:attribute name="attr.type"><xsl:text>string</xsl:text></xsl:attribute>
		</xsl:element>
		<!--type-->
		<xsl:element name="key">
			<xsl:attribute name="id"><xsl:text>lat</xsl:text></xsl:attribute>
			<xsl:attribute name="for"><xsl:text>node</xsl:text></xsl:attribute>
			<xsl:attribute name="attr.name"><xsl:text>lat</xsl:text></xsl:attribute>
			<xsl:attribute name="attr.type"><xsl:text>float</xsl:text></xsl:attribute>
		</xsl:element>
		<!--lat-->
		<xsl:element name="key">
			<xsl:attribute name="id"><xsl:text>lng</xsl:text></xsl:attribute>
			<xsl:attribute name="for"><xsl:text>node</xsl:text></xsl:attribute>
			<xsl:attribute name="attr.name"><xsl:text>lng</xsl:text></xsl:attribute>
			<xsl:attribute name="attr.type"><xsl:text>float</xsl:text></xsl:attribute>
		</xsl:element>
		<!--lng-->
<!--
<key id="name" for="node" attr.name="name" attr.type="string"/>
<key id="type" for="node" attr.name="type" attr.type="string"/>
<key id="lat" for="node" attr.name="lat" attr.type="float"/>
<key id="lng" for="node" attr.name="lng" attr.type="float"/>
-->

<!--
<node id="3"><data key="name">MDR_RNC01</data><data key="type">RNC</data></node>
-->

			<xsl:for-each select="RNC">

				<xsl:variable name="rncid">
					<xsl:value-of select="@id"/>
				</xsl:variable>

				<xsl:element name="node">
					<xsl:attribute name="id"><xsl:value-of select="$rncid"/></xsl:attribute>
					<xsl:element name="data">
						<xsl:attribute name="key"><xsl:text>name</xsl:text></xsl:attribute>
						<xsl:value-of select="$rncid"/>
					</xsl:element>
					<xsl:element name="data">
						<xsl:attribute name="key"><xsl:text>component</xsl:text></xsl:attribute>
						<xsl:text>RNC</xsl:text>
					</xsl:element>
					<xsl:element name="data">
						<xsl:attribute name="key"><xsl:text>lat</xsl:text></xsl:attribute>
						<xsl:choose>
							<xsl:when test="substring($rncid,1,3)='MDR'">-36</xsl:when>
							<xsl:when test="substring($rncid,1,3)='HN_'">-36.5</xsl:when>
							<xsl:when test="substring($rncid,1,3)='WN_'">-37</xsl:when>
							<xsl:when test="substring($rncid,1,3)='CH_'">-38</xsl:when>
						</xsl:choose>
					</xsl:element>
					<xsl:element name="data">
						<xsl:attribute name="key"><xsl:text>lng</xsl:text></xsl:attribute>
						<xsl:choose>
							<xsl:when test="substring($rncid,1,3)='MDR'">180</xsl:when>
							<xsl:when test="substring($rncid,1,3)='HN_'">185</xsl:when>
							<xsl:when test="substring($rncid,1,3)='WN_'">190</xsl:when>
							<xsl:when test="substring($rncid,1,3)='CH_'">180</xsl:when>
						</xsl:choose>
					</xsl:element>
				</xsl:element>
				<!--endnode-->
				<xsl:for-each select="RNCEquipment/INode/EM/Iub/attributes">
					<xsl:variable name="iubid">
						<xsl:value-of select="substring(commentText,7,5)"/>
					</xsl:variable>
					<xsl:element name="edge">
						<xsl:attribute name="source"><xsl:value-of select="$rncid"/></xsl:attribute>
						<xsl:attribute name="target"><xsl:value-of select="$iubid"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>

			</xsl:for-each>

			<xsl:for-each select="Site">
				<xsl:variable name="nbid">
					<xsl:value-of select="@id"/>
				</xsl:variable>
				<xsl:element name="node">
					<xsl:attribute name="id"><xsl:value-of select="$nbid"/></xsl:attribute>
					<xsl:element name="data">
						<xsl:attribute name="key"><xsl:text>name</xsl:text></xsl:attribute>
						<xsl:value-of select="$nbid"/>
					</xsl:element>
					<xsl:element name="data">
						<xsl:attribute name="key"><xsl:text>component</xsl:text></xsl:attribute>
						<xsl:text>NodeB</xsl:text>
					</xsl:element>
					<xsl:element name="data">
						<xsl:attribute name="key"><xsl:text>lat</xsl:text></xsl:attribute>
						<xsl:value-of select="attributes/latitude"/>
					</xsl:element>
					<xsl:element name="data">
						<xsl:attribute name="key"><xsl:text>lng</xsl:text></xsl:attribute>
						<xsl:value-of select="attributes/longitude"/>
					</xsl:element>
				</xsl:element>
			</xsl:for-each>

		</xsl:element>
		<!--graph-->
	</xsl:element>

	</xsl:template>
</xsl:stylesheet>
