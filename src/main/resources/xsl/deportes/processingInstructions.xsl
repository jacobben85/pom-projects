<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sportsml="http://iptc.org/std/SportsML/2008-04-01/" version="2.0">

    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"></xsl:apply-templates>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="sportsml:event-actions-soccer">
        <xsl:element name="event-actions-soccer" namespace="http://iptc.org/std/SportsML/2008-04-01/">
            <xsl:for-each select="./*">
                <xsl:if test="local-name() != local-name(preceding-sibling::element()[1])">
                    <xsl:processing-instruction name="xml-multiple">
                        <xsl:value-of select="local-name()"></xsl:value-of>
                    </xsl:processing-instruction>
                </xsl:if>
                <xsl:apply-templates select="." />
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
    <xsl:template match="//sportsml:official[1]">
        <xsl:processing-instruction name="xml-multiple">
            <xsl:value-of select="local-name()"></xsl:value-of>
        </xsl:processing-instruction>
        <xsl:element name="official" namespace="http://iptc.org/std/SportsML/2008-04-01/">
            <xsl:apply-templates select="@* | node()"></xsl:apply-templates>
        </xsl:element>
    </xsl:template>
    <xsl:template match="//sportsml:associate[1]">
        <xsl:processing-instruction name="xml-multiple">
            <xsl:value-of select="local-name()"></xsl:value-of>
        </xsl:processing-instruction>
        <xsl:element name="associate" namespace="http://iptc.org/std/SportsML/2008-04-01/">
            <xsl:apply-templates select="@* | node()"></xsl:apply-templates>
        </xsl:element>
    </xsl:template>
    <xsl:template match="//sportsml:action-soccer-play-participant[1]">
        <xsl:processing-instruction name="xml-multiple">
            <xsl:value-of select="local-name()"></xsl:value-of>
        </xsl:processing-instruction>
        <xsl:element name="action-soccer-play-participant" namespace="http://iptc.org/std/SportsML/2008-04-01/">
            <xsl:apply-templates select="@* | node()"></xsl:apply-templates>
        </xsl:element>
    </xsl:template>
    <xsl:template match="//sportsml:sports-property[1]">
        <xsl:processing-instruction name="xml-multiple">
            <xsl:value-of select="local-name()"></xsl:value-of>
        </xsl:processing-instruction>
        <xsl:element name="sports-property" namespace="http://iptc.org/std/SportsML/2008-04-01/">
            <xsl:apply-templates select="@* | node()"></xsl:apply-templates>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>