<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xts="http://www.xmlteam.com"
    xmlns="http://iptc.org/std/SportsML/2008-04-01/" xmlns:bbc="http://www.bbc.co.uk/sport/sports-data" version="1.0">
    <xsl:output method="xml" indent="yes"/>

    <!-- passes an xml thru xsl with no significant changes -->

    <!-- fix the comment -->
    <xsl:template match="commment">
        <xsl:value-of select="."/>
    </xsl:template>

    <!-- pass all elements through -->
    <xsl:template match="*">
        <xsl:element name="{name()}">
            <xsl:apply-templates select="@* | node()"/>
        </xsl:element>
    </xsl:template>

    <!-- pass all attributes through -->
    <xsl:template match="@*">
        <xsl:attribute name="{name()}">
            <xsl:value-of select="."/>
        </xsl:attribute>
    </xsl:template>

    <!-- pass all content through -->
    <xsl:template match="text()">
        <xsl:if test="normalize-space()">
            <xsl:value-of select="."/>
        </xsl:if>
    </xsl:template>

    <!-- add namespace and remove @path-id -->
    <xsl:template match="sports-content">
        <sports-content xmlns="http://iptc.org/std/SportsML/2008-04-01/">
            <xsl:apply-templates/>
        </sports-content>
    </xsl:template>

    <!-- convert doc date-time and event start-date-time into XML dateTime -->
    <xsl:template match="@date-time">
        <xsl:call-template name="xml-date-time">
            <xsl:with-param name="date-time" select="."/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template match="@start-date-time">
        <xsl:call-template name="xml-date-time">
            <xsl:with-param name="date-time" select="."/>
        </xsl:call-template>
    </xsl:template>

    <!-- remove @revision-id -->
    <xsl:template match="@revision-id"/>

    <!-- remove invalid s-c-c types -->
    <xsl:template match="sports-content-code[@code-type='tournament']"/>
    <xsl:template match="sports-content-code[@code-type='action-listing']"/>
    <xsl:template match="sports-content-code[@code-type='participant-event-listing']"/>

    <!-- remove invalid capacity -->
    <xsl:template match="site-stats/@capacity"/>

    <!-- remove hold credits -->
    <xsl:template match="@event-credit[.='hold']"/>

    <!-- convert duration into XS type -->
    <xsl:template match="@duration">
        <xsl:attribute name="{name()}">
            <xsl:call-template name="duration">
                <xsl:with-param name="duration" select="."/>
            </xsl:call-template>
        </xsl:attribute>
    </xsl:template>

    <!-- convert integer to text -->
    <xsl:template match="action-baseball-play/@outs-recorded">
        <xsl:attribute name="{name()}">
            <xsl:choose>
                <xsl:when test=".=0">none</xsl:when>
                <xsl:when test=".=1">one</xsl:when>
                <xsl:when test=".=2">two</xsl:when>
                <xsl:when test=".=3">three</xsl:when>
                <xsl:otherwise/>
            </xsl:choose>
        </xsl:attribute>
    </xsl:template>

    <!-- remove invalid action attributes -->
    <xsl:template match="action-american-football-score/@date-coverage-type"/>
    <xsl:template match="action-american-football-score/@date-coverage-value"/>
    <xsl:template match="action-american-football-score/@publisher-key"/>
    <xsl:template match="action-american-football-play-participant/@yardage"/>

    <!-- remove invalid out type -->
    <xsl:template match="action-baseball-play/@out-type[.='double-play']"/>

    <!-- remove statistic add-on to TSN mlb boxscore -->
    <xsl:template match="sports-content[sports-metadata/@fixture-key='event-stats']/statistic"/>

    <!-- fix up site-metadata -->
    <xsl:template match="site-metadata">
        <xsl:element name="{name()}">
            <xsl:if test="../site-stats/@capacity">
                <xsl:attribute name="capacity">
                    <xsl:value-of select="../site-stats/@capacity"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:element>
    </xsl:template>

    <!-- remove xts attributes -->
    <xsl:template match="@xts:*"/>

    <!-- remove bbc attributes -->
    <xsl:template match="@bbc:*"/>

    <!-- Utilities -->
    <!-- the date-time conversion template -->
    <xsl:template name="xml-date-time">
        <xsl:param name="date-time"/>
        <xsl:param name="not-att"/>
        <xsl:choose>
            <xsl:when test="contains($date-time,':')">
                <xsl:choose>
                    <xsl:when test="$not-att='yes'">
                        <xsl:value-of select="$date-time"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="{name()}">
                            <xsl:value-of select="$date-time"/>
                        </xsl:attribute>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="year" select="substring($date-time,0,5)"/>
                <xsl:variable name="month" select="substring($date-time,5,2)"/>
                <xsl:variable name="day" select="substring($date-time,7,2)"/>
                <xsl:variable name="hour" select="substring($date-time,10,2)"/>
                <xsl:variable name="minute" select="substring($date-time,12,2)"/>
                <xsl:variable name="second" select="substring($date-time,14,2)"/>
                <xsl:variable name="zone-hour" select="substring($date-time,16,3)"/>
                <xsl:variable name="zone-minute" select="substring($date-time,19)"/>
                <xsl:choose>
                    <xsl:when test="$not-att='yes'">
                        <xsl:value-of select="$year"/>-<xsl:value-of select="$month"/>-<xsl:value-of
                            select="$day"/>T<xsl:value-of select="$hour"/>:<xsl:value-of
                            select="$minute"/>:<xsl:value-of select="$second"/>
                        <xsl:value-of select="$zone-hour"/>:<xsl:value-of select="$zone-minute"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="{name()}">
                            <xsl:value-of select="$year"/>-<xsl:value-of select="$month"
                                />-<xsl:value-of select="$day"/>T<xsl:value-of select="$hour"
                                />:<xsl:value-of select="$minute"/>:<xsl:value-of select="$second"/>
                            <xsl:value-of select="$zone-hour"/>:<xsl:value-of select="$zone-minute"
                            />
                        </xsl:attribute>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- duration conversion template -->
    <xsl:template name="duration">
        <xsl:param name="duration"/>
        <xsl:choose>
            <xsl:when test="contains($duration,':')">
                <xsl:variable name="hours" select="substring-before($duration,':')"/>
                <xsl:variable name="minutes" select="substring-after($duration,':')"/>
                <xsl:text>PT</xsl:text>
                <xsl:value-of select="$hours"/>
                <xsl:text>H</xsl:text>
                <xsl:value-of select="$minutes"/>
                <xsl:text>M</xsl:text>
            </xsl:when>
            <xsl:otherwise/>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>