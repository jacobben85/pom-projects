<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sportsml="http://iptc.org/std/SportsML/2008-04-01/" version="2.0">
    <xsl:output method="xml" omit-xml-declaration="yes" />

    <xsl:variable name="sport">
        <xsl:value-of select="lower-case(sportsml:sports-content/sportsml:sports-metadata/sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='sport']/@code-name)" />
    </xsl:variable>

    <xsl:variable name="season">
        <xsl:value-of select="sportsml:sports-content/sportsml:sports-metadata/sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='season']/@code-key" />
    </xsl:variable>
    
    <xsl:variable name="league">
        <xsl:value-of select="replace(sportsml:sports-content/sportsml:sports-metadata/sportsml:sports-content-codes/sportsml:sports-content-code[@code-type='tournament']/@code-key, '([A-Z])', '')" />
    </xsl:variable>
    
    <xsl:variable name="fixture-key">
        <xsl:value-of select="replace(sportsml:sports-content/sportsml:sports-metadata/@fixture-key, ':', '-')" />
    </xsl:variable>
    
    <xsl:variable name="event-key">
        <xsl:value-of select="replace(substring-after(sportsml:sports-content/sportsml:sports-event/sportsml:event-metadata/@event-key, '-e.'), '[A-z]', '')"></xsl:value-of>
    </xsl:variable>

    <xsl:variable name="opta-id">
        <xsl:choose>
            <xsl:when test="sportsml:sports-content/sportsml:sports-metadata/@fixture-key = 'standings'">
                <xsl:value-of select="sportsml:sports-content/sportsml:standing/sportsml:standing-metadata/@date-coverage-type"></xsl:value-of>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$event-key"></xsl:value-of>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:variable name="compound-key">
        <xsl:value-of select="string-join(($sport, $season, $league, $fixture-key, $opta-id), ':')" />
    </xsl:variable>

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
                    <xsl:value-of select="$compound-key" />
                </xsl:attribute>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    <xsl:template match="sportsml:sports-content-code[@code-type='tournament'] | sportsml:sports-content-code[@code-type='team']">
        <xsl:element name="sports-content-code" namespace="http://iptc.org/std/SportsML/2008-04-01/">
            <xsl:attribute name="code-type">
                <xsl:value-of select="./@code-type" />
            </xsl:attribute>
            <xsl:attribute name="code-key">
                <xsl:value-of select="replace(./@code-key, '([A-Z])', '')" />
            </xsl:attribute>
        </xsl:element>
    </xsl:template>
    <xsl:template match="@fixture-key">
        <xsl:attribute name="fixture-key">
            <xsl:value-of select="$fixture-key" />
        </xsl:attribute>
    </xsl:template>
    <xsl:template match="@event-key">
        <xsl:attribute name="event-key">
            <xsl:value-of select="$event-key" />
        </xsl:attribute>
    </xsl:template>
    <xsl:template match="@player-key">
        <xsl:attribute name="player-key">
            <xsl:value-of select="substring-after(., 'p.')" />
        </xsl:attribute>
    </xsl:template>
    <xsl:template match="@team-key">
        <xsl:attribute name="team-key">
            <xsl:value-of select="substring-after(., 't.')" />
        </xsl:attribute>
    </xsl:template>
    <xsl:template match="sportsml:event-actions-soccer">
        <xsl:element name="event-actions-soccer" namespace="http://iptc.org/std/SportsML/2008-04-01/">
            <xsl:for-each select="./*">
                <xsl:sort select="name()"></xsl:sort>
                <xsl:sort select="./@sequence-number"></xsl:sort>
                <xsl:apply-templates select="." />
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>