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
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>