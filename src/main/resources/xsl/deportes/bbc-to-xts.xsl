<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xts="http://www.xmlteam.com"
	xmlns:bbc="http://www.bbc.co.uk/sport/sports-data"
	xmlns:redirect="org.apache.xalan.xslt.extensions.Redirect"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1.0">
	<xsl:output method="xml" indent="yes"/>

	<!-- passes an xml thru xsl with no significant changes -->


	<!-- global variables -->

	<xsl:variable name="docroot">
		<xsl:choose>
			<xsl:when test="element-available('redirect:open')">/opt/feed/live</xsl:when>
			<xsl:otherwise>file:///opt/feed/live</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="sportsml"><xsl:value-of select="$docroot"/>/sportsml</xsl:variable>
	<xsl:variable name="xmlpath"><xsl:value-of select="$docroot"/>/xsl/xml</xsl:variable>
	<xsl:variable name="league-key">
		<xsl:value-of
			select="sports-content/sports-metadata/sports-content-codes/sports-content-code[@code-type='league']/@code-key"
		/>
	</xsl:variable>
	<xsl:variable name="season-key">
		<xsl:value-of
			select="sports-content/sports-metadata/sports-content-codes/sports-content-code[@code-type='season']/@code-key"
		/>
	</xsl:variable>
	<xsl:variable name="teampath"><xsl:value-of select="$xmlpath"/>/teams/<xsl:value-of
			select="$league-key"/>.xml</xsl:variable>
	<xsl:variable name="bbc-sport-code">
		<xsl:value-of
			select="substring(sports-content/sports-metadata/sports-content-codes/sports-content-code[@code-type='tournament']/@code-key,2,2)"
		/>
	</xsl:variable>
	<xsl:variable name="bbc-pub-code">
        <xsl:choose>
            <xsl:when test="sports-content/sports-metadata/sports-content-codes/sports-content-code[@code-type='publisher']/@code-key='optasports.com'">O</xsl:when>
            <xsl:when test="sports-content/sports-metadata/sports-content-codes/sports-content-code[@code-type='publisher']/@code-key='padatasports.com'">P</xsl:when>
            <xsl:otherwise/>
        </xsl:choose>
	</xsl:variable>


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

	<!-- pass all values through -->
	<xsl:template match="text()">
		<xsl:if test="normalize-space()">
			<xsl:value-of select="."/>
		</xsl:if>
	</xsl:template>

	<!-- pass all values through -->
	<xsl:template match="text()">
		<xsl:if test="normalize-space()">
			<xsl:value-of select="."/>
		</xsl:if>
	</xsl:template>

	<!-- remove bbc s-c-c -->
	<xsl:template match="sports-content-code[@code-source='bbc.co.uk']"/>
	
	<!-- replace tournament with schedule -->
	<xsl:template match="tournament[//schedule]">
		<xsl:element name="schedule">
			<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>
	
	<!-- replace tournament with standing -->
	<xsl:template match="tournament[//standing]">
		<xsl:apply-templates select="//standing"/>
	</xsl:template>
	
	<!-- add coverage value to statistic-metadata -->
	<xsl:template match="statistic-metadata">
		<xsl:element name="statistic-metadata">
		<xsl:attribute name="team-coverage">single-team</xsl:attribute>
		<xsl:attribute name="date-coverage-type">season</xsl:attribute>
		<xsl:attribute name="date-coverage-value"><xsl:value-of select="$season-key"/></xsl:attribute>
			<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>

	<!-- replace tournament-metadata with schedule-metadata -->
	<xsl:template match="tournament-metadata">
		<xsl:element name="schedule-metadata">
		<xsl:attribute name="team-coverage">multi-team</xsl:attribute>
		<xsl:attribute name="date-coverage-type">season</xsl:attribute>
		<xsl:attribute name="date-coverage-value"><xsl:value-of select="$season-key"/></xsl:attribute>
		<xsl:attribute name="competition-scope">league</xsl:attribute>
      <sports-content-codes>
        <sports-content-code code-type="league" code-key="{$league-key}" />
      </sports-content-codes>
			<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>

	<!-- add id to player if none exists -->
	<xsl:template match="player">
		<xsl:variable name="player-key-prefix">
			<xsl:choose>
				<xsl:when test="$bbc-sport-code='FB'"><xsl:value-of  select="concat('P',$bbc-pub-code)"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="concat('P',$bbc-sport-code,$bbc-pub-code)"/></xsl:otherwise>
			</xsl:choose>			
		</xsl:variable>
		<xsl:variable name="vendor-id" select="substring-after(player-metadata/@player-key,$player-key-prefix)"/>
		<player>
				<xsl:attribute name="id">
					<xsl:value-of select="concat('p.',$vendor-id)"/>
				</xsl:attribute>
		<xsl:apply-templates/>
		</player>
	</xsl:template>
	
	<!-- move line-formation-position to player-metadata-soccer -->
	<xsl:template match="player-metadata">
		<xsl:choose>
			<xsl:when test="@line-formation-position">
				<player-metadata>
					<xsl:call-template name="player-key"/>
					<xsl:for-each select="./@*[not(name()='line-formation-position') and not(name()='player-key')]">
						<xsl:attribute name="{name()}">
							<xsl:value-of select="."/>
						</xsl:attribute>    	
					</xsl:for-each>
					<xsl:copy-of select="*"/>
					<player-metadata-soccer>
						<xsl:attribute name="line-formation-position">
							<xsl:value-of select="@line-formation-position"/>
						</xsl:attribute>    	
					</player-metadata-soccer>
				</player-metadata>
				
			</xsl:when>
			<xsl:otherwise>
				<xsl:element name="player-metadata">
					<xsl:apply-templates select="@* | node()"/>
				</xsl:element>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- remove bbc tournament-metadata name -->
	<xsl:template match="tournament-metadata/name"/>

	<!-- remove tournament-stage -->
	<xsl:template match="tournament-stage">
			<xsl:apply-templates/>
	</xsl:template>

	<!-- remove tournament-stage-metadata -->
	<xsl:template match="tournament-stage-metadata"/>

	<!-- remove tournament-round -->
	<xsl:template match="tournament-round">
			<xsl:apply-templates/>
	</xsl:template>

	<!-- remove tournament-round-metadata -->
	<xsl:template match="tournament-round-metadata"/>

	<!-- remove player-stats/@date-coverage-value -->
	<xsl:template match="player-stats/@date-coverage-value"/>

	<!-- make timestamps condensed ISO -->
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

	<!-- change season-stats fixture key -->
	<xsl:template match="@fixture-key[.='season-statistics']">
		<xsl:attribute name="fixture-key">season-stats</xsl:attribute>
	</xsl:template>

	<!-- replace bbc attributes with xts namespace-->
	<xsl:template match="@bbc:*">
		<xsl:attribute name="{concat('xts:',substring-after(name(),'bbc:'))}">
			<xsl:value-of select="."/>
		</xsl:attribute>
	</xsl:template>

	<!-- replace bbc team @id with xts -->
	<xsl:template match="team/@id">
		<xsl:variable name="team-key-prefix" select="concat('T',$bbc-sport-code,'B')"/>
		<xsl:variable name="event-key-prefix" select="concat('E',$bbc-sport-code,$bbc-pub-code)"/>
		<xsl:variable name="vendor-event-id" select="substring-after(substring-before(.,$team-key-prefix),$event-key-prefix)"/>
		<xsl:variable name="vendor-team-id" select="substring-after(.,$team-key-prefix)"/>
		<xsl:attribute name="id">
        <xsl:choose>
            <xsl:when test="string($vendor-event-id)">
		<xsl:value-of select="concat('e.',$vendor-event-id,'t.',$vendor-team-id)"/>
            </xsl:when>
            <xsl:otherwise>
		<xsl:value-of select="concat('t.',$vendor-team-id)"/>
            </xsl:otherwise>
        </xsl:choose>
		</xsl:attribute>
	</xsl:template>

	<!-- replace bbc event-key with xts -->
	<xsl:template match="@event-key">
		<xsl:variable name="event-key-prefix" select="concat('E',$bbc-sport-code,$bbc-pub-code)"/>
		<xsl:variable name="vendor-id" select="substring-after(.,$event-key-prefix)"/>
		<xsl:attribute name="event-key">
		<xsl:value-of select="concat($league-key,'-',$season-key,'-e.',$vendor-id)"/>
		</xsl:attribute>
	</xsl:template>

	<!-- replace bbc site-key with xts -->
	<xsl:template match="@site-key">
		<xsl:variable name="vendor-id" select="substring-after(.,'VFBB')"/>
		<xsl:attribute name="site-key">
<xsl:value-of select="concat('s.',$vendor-id)"/>
		</xsl:attribute>
	</xsl:template>

	<!-- replace bbc player-key with xts -->
	<xsl:template match="@player-key" name="player-key">
		<xsl:variable name="vendor-id">
		<xsl:choose>
            <xsl:when test="$bbc-sport-code='FB'">
            	<xsl:choose>
            		<xsl:when test="@player-key">
            			<xsl:value-of select="substring-after(@player-key,concat('P',$bbc-pub-code))"/>				
            		</xsl:when>
            		<xsl:otherwise>
            			<xsl:value-of select="substring-after(.,concat('P',$bbc-pub-code))"/>				
            		</xsl:otherwise>
            	</xsl:choose>
			</xsl:when>
            <xsl:otherwise>
            	<xsl:choose>
            		<xsl:when test="@player-key">
            			<xsl:value-of select="substring-after(@player-key,concat('P',$bbc-sport-code,$bbc-pub-code))"/>
            		</xsl:when>
            		<xsl:otherwise>
            			<xsl:value-of select="substring-after(.,concat('P',$bbc-sport-code,$bbc-pub-code))"/>
            		</xsl:otherwise>
            	</xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
		</xsl:variable>
		<xsl:attribute name="player-key">
<xsl:value-of select="concat('p.',$vendor-id)"/>
		</xsl:attribute>
	</xsl:template>

	<!-- replace bbc team-key with xts -->
	<xsl:template match="@team-key">
		<xsl:variable name="team-key-prefix" select="concat('T',$bbc-sport-code,'B')"/>
		<xsl:variable name="vendor-id" select="substring-after(.,$team-key-prefix)"/>
		<xsl:attribute name="team-key">
        <xsl:choose>
        	<xsl:when test="$bbc-pub-code='O' and document($teampath)/league/team[@optaid=$vendor-id]">
        		<xsl:value-of select="document($teampath)/league/team[@optaid=$vendor-id]/@smlid"/>
        	</xsl:when>
        	<xsl:when test="$bbc-pub-code='P' and document($teampath)/league/team[@padataid=$vendor-id]">
        		<xsl:value-of select="document($teampath)/league/team[@padataid=$vendor-id]/@smlid"/>
        	</xsl:when>
        	<xsl:otherwise>
        		<xsl:value-of select="concat('t.',$vendor-id)"/>
            </xsl:otherwise>
        </xsl:choose>
		</xsl:attribute>
	</xsl:template>

	<!-- replace bbc team-key with xts -->
	<xsl:template match="@team-idref">
		<xsl:variable name="team-key-prefix" select="concat('T',$bbc-sport-code,'B')"/>
		<xsl:variable name="event-key-prefix" select="concat('E',$bbc-sport-code,$bbc-pub-code)"/>
		<xsl:variable name="vendor-id" select="substring-after(.,$team-key-prefix)"/>
		<xsl:variable name="vendor-event" select="substring-before(substring-after(.,$event-key-prefix),'-')"/>
		<xsl:choose>
			<xsl:when test="string($vendor-event)">
				<xsl:attribute name="team-idref"><xsl:value-of select="concat('e.',$vendor-event,'-t.',$vendor-id)"/></xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="team-idref">		<xsl:value-of select="concat('t.',$vendor-id)"/>
				</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- replace bbc player-key with xts -->
	<xsl:template match="@idref[parent::participant]">
		<xsl:variable name="player-key-prefix">
			<xsl:choose>
				<xsl:when test="$bbc-sport-code='FB'"><xsl:value-of  select="concat('P',$bbc-pub-code)"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="concat('P',$bbc-sport-code,$bbc-pub-code)"/></xsl:otherwise>
			</xsl:choose>			
		</xsl:variable>
		<xsl:variable name="vendor-id" select="substring-after(.,$player-key-prefix)"/>
		<xsl:attribute name="idref">		<xsl:value-of select="concat('p.',$vendor-id)"/>
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template match="@recipient-idref">
		<xsl:variable name="player-key-prefix">
			<xsl:choose>
				<xsl:when test="$bbc-sport-code='FB'"><xsl:value-of  select="concat('P',$bbc-pub-code)"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="concat('P',$bbc-sport-code,$bbc-pub-code)"/></xsl:otherwise>
			</xsl:choose>			
		</xsl:variable>
		<xsl:variable name="vendor-id" select="substring-after(.,$player-key-prefix)"/>
		<xsl:attribute name="recipient-idref"><xsl:value-of select="concat('p.',$vendor-id)"/>
		</xsl:attribute>
	</xsl:template>
	
	<!-- replace bbc phase-caliber-key with xts -->
	<xsl:template match="@phase-caliber-key">
		<xsl:variable name="vendor-id" select="substring-after(.,'TFBB')"/>
		<xsl:attribute name="phase-caliber-key">
<xsl:value-of select="document($teampath)/league/team[@vendorid=$vendor-id]/@smlid"/>
		</xsl:attribute>
	</xsl:template>

	<!-- make sequence-number an integer -->
	<xsl:template match="@sequence-number">
		<xsl:attribute name="sequence-number">
<xsl:value-of select="substring(translate(.,'T+-:',''),5,10)"/>
		</xsl:attribute>
	</xsl:template>

	<!-- change length to distance -->
	<xsl:template match="@length">
		<xsl:attribute name="distance">
<xsl:value-of select="."/>
		</xsl:attribute>
	</xsl:template>

	<!-- Utilities -->
	<!-- the date-time conversion template -->
	<xsl:template name="xml-date-time">
		<xsl:param name="date-time"/>
		<xsl:param name="not-att"/>
		<xsl:variable name="year" select="substring($date-time,0,5)"/>
		<xsl:variable name="month" select="substring($date-time,6,2)"/>
		<xsl:variable name="day" select="substring($date-time,9,2)"/>
		<xsl:variable name="hour" select="substring($date-time,12,2)"/>
		<xsl:variable name="minute" select="substring($date-time,15,2)"/>
		<xsl:variable name="second" select="substring($date-time,18,2)"/>
		<xsl:variable name="zone-hour" select="substring($date-time,20,3)"/>
		<xsl:variable name="zone-minute" select="substring($date-time,24)"/>
		<xsl:attribute name="{name()}"><xsl:value-of select="$year"/><xsl:value-of select="$month"
				/><xsl:value-of select="$day"/>T<xsl:value-of select="$hour"/><xsl:value-of
				select="$minute"/><xsl:value-of select="$second"/><xsl:value-of select="$zone-hour"
				/><xsl:value-of select="$zone-minute"/></xsl:attribute>
	</xsl:template>
</xsl:stylesheet>
