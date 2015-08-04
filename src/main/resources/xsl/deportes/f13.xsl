<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns:sportsml="http://iptc.org/std/SportsML/2008-04-01/"
        exclude-result-prefixes="xs"
        version="2.0">
   <xsl:output media-type="text/html" encoding="UTF-8" indent="yes" />
   <xsl:template match="/">
      <xsl:apply-templates select="Commentary" />
   </xsl:template>
   <xsl:template match="Commentary">
     <xsl:variable name="date" select="substring-before(@game_date, ' ')" />
     <xsl:variable name="time" select="substring-after(@game_date, ' ')" />
      <xsl:variable name="language">
         <xsl:choose>
            <xsl:when test="@lang_id = 'en'">en</xsl:when>
            <xsl:when test="@lang_id = 'es'">es</xsl:when>
            <xsl:when test="@lang_id = 'esl'">es</xsl:when>
         </xsl:choose>
      </xsl:variable>
      <sportsml:sports-content>
        <!-- <xsl:attribute name="xml:lang">
          <xsl:value-of select="$language" />
        </xsl:attribute> -->
         <!-- Document Level Metadata -->
         <sportsml:sports-metadata fixture-key="event:commentary" document-class="event-summary" fixture-name="Event Commentary">
            <xsl:attribute name="doc-id">
               <xsl:value-of select="string-join(('commentary', @competition_id, @season_id, @game_id, @lang_id), '-')" />
            </xsl:attribute>
            <xsl:attribute name="language">
              <xsl:value-of select="@lang_id" />
            </xsl:attribute>
            <xsl:attribute name="date-time">
               <xsl:value-of select="current-dateTime()" />
            </xsl:attribute>
            <sportsml:sports-title>
              <xsl:text>Event Commentary | </xsl:text>
              <xsl:value-of select="concat(@away_team_name, ' v ', @home_team_name)" />
              <xsl:text> | </xsl:text>
              <xsl:value-of select="dateTime(xs:date($date), xs:time($time))" />
            </sportsml:sports-title>
            <sportsml:sports-content-codes>
               <sportsml:sports-content-code code-name="Opta" code-key="publisher:optasports.com" code-type="publisher" />
               <sportsml:sports-content-code code-name="Univision" code-key="distributor:univision.com" code-type="distributor" />
               <sportsml:sports-content-code code-type="sport" code-key="sport:15054000" code-name="Soccer" />
               <!-- TODO: What is the difference between tournament and competition? -->
               <!-- <sportsml:sports-content-code code-type="tournament" code-source="optasports.com">
                  <xsl:attribute name="code-key">
                  </xsl:attribute>
                  <xsl:attribute name="code-name">
                  </xsl:attribute>
               </sportsml:sports-content-code> -->
               <sportsml:sports-content-code code-type="league" code-source="optasports.com">
                  <xsl:attribute name="code-key">
                     <xsl:value-of select="concat('league:', @competition_id)" />
                  </xsl:attribute>
                  <xsl:attribute name="code-name">
                     <xsl:value-of select="@competition" />
                  </xsl:attribute>
               </sportsml:sports-content-code>
               <sportsml:sports-content-code code-type="season">
                  <xsl:attribute name="code-key">
                     <xsl:value-of select="concat('season:', @season_id)" />
                  </xsl:attribute>
                  <xsl:attribute name="code-name">
                     <xsl:value-of select="@season" />
                  </xsl:attribute>
               </sportsml:sports-content-code>
               <sportsml:sports-content-code code-type="team" code-source="optasports.com">
                  <xsl:attribute name="code-key">
                     <xsl:value-of select="concat('team:', @home_team_id)" />
                  </xsl:attribute>
                  <xsl:attribute name="code-name">
                     <xsl:value-of select="@home_team_name" />
                  </xsl:attribute>
               </sportsml:sports-content-code>
               <sportsml:sports-content-code code-type="team" code-source="optasports.com">
                  <xsl:attribute name="code-key">
                     <xsl:value-of select="concat('team:', @away_team_id)" />
                  </xsl:attribute>
                  <xsl:attribute name="code-name">
                     <xsl:value-of select="@away_team_name" />
                  </xsl:attribute>
               </sportsml:sports-content-code>
            </sportsml:sports-content-codes>
         </sportsml:sports-metadata>

         <!-- Event Information and Metadata -->
         <sportsml:sports-event>
            <sportsml:event-metadata>
               <xsl:attribute name="date-coverage-type">event</xsl:attribute>
               <xsl:attribute name="event-status-note">Result</xsl:attribute>
               <xsl:attribute name="event-key">
                  <xsl:value-of select="concat('event:', @competition_id)" />
               </xsl:attribute>
               <!-- TODO: This is an optional field -->
               <!-- <xsl:attribute name="event-status"/> -->
               <!-- TODO: This is an optional field -->
               <!-- <xsl:attribute name="event-outcome-type" /> -->
               <xsl:attribute name="event-name">
                  <xsl:value-of select="concat(@away_team_name, ' v ', @home_team_name)" />
               </xsl:attribute>
               <xsl:attribute name="start-date-time">
                  <xsl:value-of select="dateTime(xs:date($date), xs:time($time))" />
               </xsl:attribute>
               <sportsml:event-metadata-soccer>
                  <xsl:attribute name="week">
                     <xsl:value-of select="@matchday" />
                  </xsl:attribute>
                  <!-- TODO: This is an optional field -->
                  <!-- <xsl:attribute name="period-value" /> -->
                  <!-- TODO: This is an optional field -->
                  <!-- <xsl:attribute name="minutes-elapsed" /> -->
                  <!-- TODO: This is an optional field -->
                  <!-- <sportsml:time-adjustment>
                     <xsl:attribute name="period-extra-time-elapsed" />
                  </sportsml:me-adjustment> -->
               </sportsml:event-metadata-soccer>
            </sportsml:event-metadata>
            <!-- Home Team -->
            <sportsml:team>
               <sportsml:team-metadata>
                  <xsl:attribute name="team-key">
                     <xsl:value-of select="concat('team:',@home_team_id)" />
                  </xsl:attribute>
                  <xsl:attribute name="alignment">home</xsl:attribute>
                  <sportsml:name>
                        <xsl:attribute name="first">
                           <xsl:value-of select="@home_team_name" />
                        </xsl:attribute>
                        <xsl:attribute name="full">
                           <xsl:value-of select="@home_team_name" />
                        </xsl:attribute>
                  </sportsml:name>
               </sportsml:team-metadata>
               <sportsml:team-stats>
                  <xsl:attribute name="score">
                     <xsl:value-of select="@home_score" />
                  </xsl:attribute>
                  <xsl:attribute name="score-opposing">
                     <xsl:value-of select="@away_score" />
                  </xsl:attribute>
                  <!-- TODO: This is an optional field -->
                  <!-- <xsl:attribute name="event-outcome" /> -->
               </sportsml:team-stats>
            </sportsml:team>
            <!-- Away Team -->
            <sportsml:team>
               <sportsml:team-metadata>
                  <xsl:attribute name="team-key">
                     <xsl:value-of select="concat('team:',@away_team_id)" />
                  </xsl:attribute>
                  <xsl:attribute name="alignment">away</xsl:attribute>
                  <sportsml:name>
                     <xsl:attribute name="first">
                        <xsl:value-of select="@away_team_name" />
                     </xsl:attribute>
                     <xsl:attribute name="full">
                        <xsl:value-of select="@away_team_name" />
                     </xsl:attribute>
                  </sportsml:name>
               </sportsml:team-metadata>
               <sportsml:team-stats>
                  <xsl:attribute name="score">
                     <xsl:value-of select="@away_score" />
                  </xsl:attribute>
                  <xsl:attribute name="score-opposing">
                     <xsl:value-of select="@home_score" />
                  </xsl:attribute>
                  <!-- TODO: This is an optional field -->
                  <!-- <xsl:attribute name="event-outcome" /> -->
               </sportsml:team-stats>
            </sportsml:team>
            <!-- Event Actions with Commentary -->
            <sportsml:event-actions>
               <sportsml:event-actions-soccer>
                  <xsl:apply-templates select="message" />
               </sportsml:event-actions-soccer>
            </sportsml:event-actions>
         </sportsml:sports-event>
      </sportsml:sports-content>
   </xsl:template>
   <xsl:template match="message">
      <!-- TODO: Determine the correct action mappings -->
      <xsl:variable name="actionName">
         <xsl:choose>
            <xsl:when test="@type = 'offside'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'corner'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'own goal'">action-soccer-score</xsl:when>
            <xsl:when test="@type = 'attempt saved'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'attempt blocked'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'miss'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'post'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'goal'">action-soccer-score</xsl:when>
            <xsl:when test="@type = 'free kick lost'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'free kick won'">action-soccer-score</xsl:when>
            <xsl:when test="@type = 'penalty lost'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'penalty miss'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'penalty saved'">action-soccer-play</xsl:when>
            <xsl:when test="@type = 'penalty won'">action-soccer-score</xsl:when>
            <xsl:when test="@type = 'red card'">action-soccer-penalty</xsl:when>
            <xsl:when test="@type = 'second yellow card'">action-soccer-penalty</xsl:when>
            <xsl:when test="@type = 'yellow card'">action-soccer-penalty</xsl:when>
            <xsl:when test="@type = 'substitution'">action-soccer-substitution</xsl:when>
            <xsl:when test="starts-with(@type, 'end')">action-soccer-other</xsl:when>
            <xsl:when test="@type = 'lineup'">action-soccer-other</xsl:when>
            <xsl:when test="@type = 'player retired'">action-soccer-other</xsl:when>
            <xsl:when test="@type = 'postponed'">action-soccer-other</xsl:when>
            <xsl:when test="@type = 'start'">action-soccer-other</xsl:when>
            <xsl:when test="@type = 'start delay'">action-soccer-other</xsl:when>
            <xsl:when test="@type = 'end delay'">action-soccer-other</xsl:when>
            <xsl:otherwise>action-soccer-other</xsl:otherwise>
         </xsl:choose>
      </xsl:variable>
      <xsl:variable name="playType">
         <xsl:choose>
            <xsl:when test="@type = 'offside'">offside</xsl:when>
            <xsl:when test="@type = 'corner'">corner</xsl:when>
            <xsl:when test="@type = 'attempt saved'">shot</xsl:when>
            <xsl:when test="@type = 'attempt blocked'">shot</xsl:when>
            <xsl:when test="@type = 'miss'">shot</xsl:when>
            <xsl:when test="@type = 'post'">shot</xsl:when>
            <xsl:when test="@type = 'free kick lost'">shot</xsl:when>
            <xsl:when test="@type = 'penalty lost'">shot</xsl:when>
            <xsl:when test="@type = 'penalty miss'">shot</xsl:when>
            <xsl:when test="@type = 'penalty saved'">shot</xsl:when>
            <xsl:when test="starts-with(@type, 'end')">period-end</xsl:when>
            <xsl:when test="@type = 'lineup'">line-up-announcement</xsl:when>
            <xsl:when test="@type = 'postponed'" />
            <xsl:when test="@type = 'start'">game-start</xsl:when>
            <xsl:when test="@type = 'start delay'">game-delay</xsl:when>
            <xsl:when test="@type = 'end delay'">game-end</xsl:when>
            <xsl:when test="@type = 'player retired'">player-retired</xsl:when>
            <xsl:otherwise>unknown</xsl:otherwise>
         </xsl:choose>
      </xsl:variable>
      <xsl:variable name="scoreAttemptType">
         <xsl:choose>
            <xsl:when test="@type = 'corner'">corner-kick</xsl:when>
            <xsl:when test="@type = 'own goal'">own-goal</xsl:when>
            <xsl:when test="@type = 'attempt saved'">regular</xsl:when>
            <xsl:when test="@type = 'attempt blocked'">regular</xsl:when>
            <xsl:when test="@type = 'miss'">regular</xsl:when>
            <xsl:when test="@type = 'post'">regular</xsl:when>
            <xsl:when test="@type = 'goal'">regular</xsl:when>
            <xsl:when test="@type = 'free kick lost'">freekick</xsl:when>
            <xsl:when test="@type = 'free kick won'">freekick</xsl:when>
            <xsl:when test="@type = 'penalty lost'">penalty</xsl:when>
            <xsl:when test="@type = 'penalty miss'">penalty</xsl:when>
            <xsl:when test="@type = 'penalty saved'">penalty</xsl:when>
            <xsl:when test="@type = 'penalty won'">penalty</xsl:when>
         </xsl:choose>
      </xsl:variable>
      <xsl:variable name="scoreAttemptResult">
         <xsl:choose>
            <xsl:when test="@type = 'attempt saved'">saved</xsl:when>
            <xsl:when test="@type = 'attempt blocked'">blocked</xsl:when>
            <xsl:when test="@type = 'miss'">missed</xsl:when>
            <xsl:when test="@type = 'post'">off-post</xsl:when>
            <xsl:when test="@type = 'free kick lost'">missed</xsl:when>
            <xsl:when test="@type = 'penalty lost'">missed</xsl:when>
            <xsl:when test="@type = 'penalty miss'">missed</xsl:when>
            <xsl:when test="@type = 'penalty saved'">saved</xsl:when>
         </xsl:choose>
      </xsl:variable>
      <xsl:element name="{$actionName}" namespace="http://iptc.org/std/SportsML/2008-04-01/">
         <!-- Common Action Attributes -->
         <xsl:attribute name="id">
            <xsl:value-of select="concat('action-', @id)" />
         </xsl:attribute>
         <xsl:if test="@period">
           <xsl:attribute name="period-value">
              <xsl:value-of select="@period" />
           </xsl:attribute>
        </xsl:if>
        <xsl:if test="@minute">
         <xsl:attribute name="minutes-elapsed">
            <xsl:value-of select="@minute" />
         </xsl:attribute>
       </xsl:if>
       <xsl:if test="@minute | @second">
         <xsl:variable name="minute">
           <xsl:value-of select="@minute" />
         </xsl:variable>
         <xsl:variable name="second">
           <xsl:value-of select="@second" />
         </xsl:variable>
         <xsl:attribute name="period-time-elapsed">
           <xsl:value-of select="concat($minute, $second)" />
         </xsl:attribute>
       </xsl:if>
         <!-- Play Action Specific Attributes -->
         <xsl:if test="$actionName = 'action-soccer-play'">
            <xsl:attribute name="play-type">
               <xsl:value-of select="$playType" />
            </xsl:attribute>
            <xsl:if test="$playType = 'shot'">
               <xsl:attribute name="score-attempt-type">
                  <xsl:value-of select="$scoreAttemptType" />
               </xsl:attribute>
               <xsl:attribute name="score-attempt-result">
                  <xsl:value-of select="$scoreAttemptResult" />
               </xsl:attribute>
            </xsl:if>
         </xsl:if>
         <!-- Score Action Specific Attributes -->
         <xsl:if test="$actionName = 'action-soccer-score'">
            <xsl:attribute name="score-attempt-type">
               <xsl:value-of select="$scoreAttemptType" />
            </xsl:attribute>
         </xsl:if>
         <!-- Penalty Action Specific Attributes -->
         <xsl:if test="$actionName = 'action-soccer-penalty'">
            <xsl:attribute name="penalty-level">
               <xsl:choose>
                  <xsl:when test="@type = 'yellow card'">yellow-card</xsl:when>
                  <xsl:when test="@type = 'second yellow card'">yellow-red-card</xsl:when>
                  <xsl:when test="@type = 'red card'">red-card</xsl:when>
               </xsl:choose>
            </xsl:attribute>
         </xsl:if>
         <!-- Other Action Specific Attributes -->
         <xsl:if test="$actionName = 'action-soccer-other'">
            <xsl:attribute name="action-source">univision-actions</xsl:attribute>
            <xsl:attribute name="action-type">
               <xsl:value-of select="$playType" />
            </xsl:attribute>
         </xsl:if>
         <!-- Comments last so its easy to read -->
         <xsl:attribute name="comment">
            <xsl:value-of select="@comment" />
         </xsl:attribute>
      </xsl:element>
   </xsl:template>
   <xsl:template match="@player_ref1 | @player_ref2">
     <!-- TODO: Not sure if we should convert these for commentary since required fields are missing -->
      <sportsml:action-soccer-play-participant>
         <xsl:attribute name="player-idref">
            <xsl:value-of select="data(.)" />
         </xsl:attribute>
      </sportsml:action-soccer-play-participant>
   </xsl:template>
</xsl:stylesheet>
