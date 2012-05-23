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
		<xsl:element name="MapRncVcc">
			<xsl:element name="id">
				<xsl:apply-templates select="Operator/attributes"/>
			</xsl:element>
			<xsl:for-each select="RNC">
				<xsl:variable name="rncid">
					<xsl:value-of select="@id"/>
				</xsl:variable>
				
				<xsl:element name="Rnc">
					<xsl:attribute name="id"><xsl:value-of select="$rncid"/></xsl:attribute>
					<xsl:for-each select="RNCEquipment/INode/EM/AtmIf">
						<xsl:variable name="atmifid">
							<xsl:value-of select="@id"/>
						</xsl:variable>
						
						<xsl:element name="AtmIf">
							<xsl:attribute name="id"><xsl:value-of select="$atmifid"/></xsl:attribute>
							
							<xsl:element name="remoteAtmInterfaceLabel">
								<xsl:value-of select="attributes/remoteAtmInterfaceLabel"/>
							</xsl:element>
									
							<xsl:for-each select="Vcc">
								<xsl:variable name="vccid">
									<xsl:value-of select="@id"/>
								</xsl:variable>
								
								<xsl:element name="Vcc">
									<xsl:attribute name="id"><xsl:value-of select="$vccid"/></xsl:attribute>
									
									<xsl:element name="correlationTag">
										<xsl:value-of select="Vcd/attributes/correlationTag"/>
									</xsl:element>
									
									<xsl:element name="RxTrafficDescParm">
										<xsl:for-each select="Vcd/Tm/attributes/rxTrafficDescParm/value">
											<xsl:element name="Value">
												<xsl:value-of select="."/>
											</xsl:element>
										</xsl:for-each>
									</xsl:element>
									
									<xsl:element name="RxTrafficDescType">
										<xsl:value-of select="Vcd/Tm/attributes/rxTrafficDescType"/>
									</xsl:element>
									
									<xsl:element name="TxTrafficDescParm">
										<xsl:for-each select="Vcd/Tm/attributes/txTrafficDescParm/value">
											<xsl:element name="Value">
												<xsl:value-of select="."/>
											</xsl:element>
										</xsl:for-each>
									</xsl:element>
									
									<xsl:element name="TxTrafficDescType">
										<xsl:value-of select="Vcd/Tm/attributes/txTrafficDescType"/>
									</xsl:element>
									
									<xsl:element name="TrafficShaping">
										<xsl:value-of select="Vcd/Tm/attributes/trafficShaping"/>
									</xsl:element>
									
								</xsl:element>
							</xsl:for-each>
						</xsl:element>
					</xsl:for-each>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
