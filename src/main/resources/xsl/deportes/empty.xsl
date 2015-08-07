<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="xml" omit-xml-declaration="yes" />
    <xsl:template match="@* | node()">
        <xsl:choose>
            <xsl:when test="name() = 'sports-content-codes'">
                <xsl:element name="sports-content-codes">
                    <xsl:attribute name="season"><xsl:value-of select="./sports-content-code[@code-type='season']/@code-key" /></xsl:attribute>
                    <xsl:apply-templates select="@* | node()"/>
                </xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@* | node()"/>
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>