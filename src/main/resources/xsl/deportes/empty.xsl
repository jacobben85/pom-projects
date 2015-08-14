<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sportsml="http://iptc.org/std/SportsML/2008-04-01/" version="2.0">
    <xsl:output method="xml" omit-xml-declaration="yes" />
    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="sportsml:sports-metadata">
        <xsl:element name="sports-metadata" namespace="http://iptc.org/std/SportsML/2008-04-01/">
            <xsl:apply-templates select="@* | node()"/>
            <xsl:element name="sports-property" namespace="http://iptc.org/std/SportsML/2008-04-01/">
                <xsl:attribute name="formal-name">compound-key</xsl:attribute>
                <xsl:attribute name="value">
                    <xsl:choose>
                        <xsl:when test="./@fixture-key = 'standings'">
                            <xsl:value-of select="string-join((lower-case(./sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='sport']/@code-name), ./sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='season']/@code-key, ./sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='league']/@code-key, ./@fixture-key, ../sportsml:standing/sportsml:standing-metadata/@date-coverage-type), ':')" />
                        </xsl:when>                        
                        <xsl:otherwise>
                            <xsl:value-of select="string-join((lower-case(./sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='sport']/@code-name), ./sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='season']/@code-key, ./sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='league']/@code-key, ./@fixture-key, ../sportsml:sports-event/sportsml:event-metadata/@event-key), ':')" />
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
                <xsl:attribute name="sport">
                    <xsl:value-of select="./sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='sport']/@code-name"></xsl:value-of>
                </xsl:attribute>
                <xsl:attribute name="season">
                    <xsl:value-of select="./sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='season']/@code-key"></xsl:value-of>
                </xsl:attribute>
                <xsl:attribute name="league">
                    <xsl:value-of select="./sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='league']/@code-key"></xsl:value-of>
                </xsl:attribute>
                <xsl:attribute name="fixture">
                    <xsl:value-of select="./@fixture-key"></xsl:value-of>
                </xsl:attribute>
                <xsl:attribute name="key">
                    <xsl:choose>
                        <xsl:when test="./@fixture-key = 'standings'">
                            <xsl:value-of select="../sportsml:standing/sportsml:standing-metadata/@date-coverage-type"></xsl:value-of>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="../sportsml:sports-event/sportsml:event-metadata/@event-key"></xsl:value-of>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>