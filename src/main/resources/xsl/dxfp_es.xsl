<!--
    Document   : dxfp.xsl
    Created on : April 20, 2015, 3:22 PM
    Author     : jbjohn
    Description:
        Tranform the Closed Caption files to Ooyala supported format.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tts="http://www.w3.org/2006/04/ttaf1#styling" xmlns="http://www.w3.org/ns/ttml" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" />
    <xsl:template match="@* | node()">
        <xsl:choose>
            <xsl:when test="(name() = 'div') or (name() = 'p')">
                <xsl:if test="name() = 'div'">
                    <xsl:element name="div">
                        <xsl:attribute name="xml:lang">Spanish</xsl:attribute>
                        <xsl:choose>
                            <xsl:when test="@style"><xsl:attribute name="style"><xsl:value-of select="@style" /></xsl:attribute></xsl:when>
                            <xsl:otherwise><xsl:attribute name="style">defaultCaption</xsl:attribute></xsl:otherwise>
                        </xsl:choose>
                        <xsl:attribute name="xml_lang">es</xsl:attribute>
                        <xsl:apply-templates select="@* | node()"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="name() = 'p'">
                    <xsl:element name="p">
                        <xsl:attribute name="begin"><xsl:value-of select="@begin" /></xsl:attribute>
                        <xsl:attribute name="end"><xsl:value-of select="@end" /></xsl:attribute>
                        <xsl:if test="@style">
                            <xsl:attribute name="style">
                                <xsl:value-of select="@style" />
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="@region">
                            <xsl:attribute name="region">
                                <xsl:value-of select="@region" />
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:value-of select="."></xsl:value-of>
                    </xsl:element>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@* | node()"/>
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>