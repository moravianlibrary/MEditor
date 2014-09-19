<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:marc="http://www.loc.gov/MARC21/slim" xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="marc">
    <xsl:import href="https://raw.github.com/moravianlibrary/MEditor/master/resources/xml/MARC21slimUtils.xsl"/>
    <!--http://www.loc.gov/standards/marcxml/xslt/-->
    <xsl:output method="xml" indent="yes"/>
    <!--
    Fixed 530 Removed type="original" from dc:relation 2010-11-19 tmee
    Fixed 500 fields. 2006-12-11 ntra
    Added ISBN and deleted attributes 6/04 jer
    -->
    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="//marc:collection">
                <oai_dc:dcCollection xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd">
                    <xsl:for-each select="//marc:collection">
                        <xsl:for-each select="//marc:record">
                            <oai_dc:dc>
                                <xsl:call-template name="marcRecord"/>
                            </oai_dc:dc>
                        </xsl:for-each>
                    </xsl:for-each>
                </oai_dc:dcCollection>
            </xsl:when>
            <xsl:otherwise>
                <xsl:for-each select="//marc:record">
                    <oai_dc:dc xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd">
                        <xsl:call-template name="marcRecord"/>
                    </oai_dc:dc>
                </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="marcRecord">
        <xsl:variable name="leader" select="marc:leader"/>
        <xsl:variable name="leader6" select="substring($leader,7,1)"/>
        <xsl:variable name="leader7" select="substring($leader,8,1)"/>
        <xsl:variable name="controlField008" select="marc:controlfield[@tag=008]"/>
        <xsl:for-each select="marc:datafield[@tag=245]">
            <dc:title>
                <xsl:call-template name="subfieldSelect">
                    <!--MZK uprava-->
                    <!--<xsl:with-param name="codes">abfghk</xsl:with-param>-->
                    <xsl:with-param name="codes">anpb</xsl:with-param>
                </xsl:call-template>
            </dc:title>
        </xsl:for-each>
        <!--Uprava MZK, vyhozeni nejakych poli-->
        <xsl:for-each select="marc:datafield[@tag=100]|marc:datafield[@tag=700]">
            <dc:creator>
                <xsl:call-template name="chopPunctuationBack">
                    <xsl:with-param name="chopString">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">a</xsl:with-param>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>
            </dc:creator>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=110]|marc:datafield[@tag=710]">
            <dc:creator>
                <xsl:call-template name="chopPunctuationBack">
                    <xsl:with-param name="chopString">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">ab</xsl:with-param>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>
            </dc:creator>
        </xsl:for-each>
        <!--MZK to nas nezajima-->
        <!--<dc:type>-->
        <!--<xsl:if test="$leader7='c'">-->
        <!--&lt;!&ndash;Remove attribute 6/04 jer&ndash;&gt;-->
        <!--&lt;!&ndash;<xsl:attribute name="collection">yes</xsl:attribute>&ndash;&gt;-->
        <!--<xsl:text>collection</xsl:text>-->
        <!--</xsl:if>-->
        <!--<xsl:if test="$leader6='d' or $leader6='f' or $leader6='p' or $leader6='t'">-->
        <!--&lt;!&ndash;Remove attribute 6/04 jer&ndash;&gt;-->
        <!--&lt;!&ndash;<xsl:attribute name="manuscript">yes</xsl:attribute>&ndash;&gt;-->
        <!--<xsl:text>manuscript</xsl:text>-->
        <!--</xsl:if>-->
        <!--<xsl:choose>-->
        <!--<xsl:when test="$leader6='a' or $leader6='t'">text</xsl:when>-->
        <!--<xsl:when test="$leader6='e' or $leader6='f'">cartographic</xsl:when>-->
        <!--<xsl:when test="$leader6='c' or $leader6='d'">notated music</xsl:when>-->
        <!--<xsl:when test="$leader6='i' or $leader6='j'">sound recording</xsl:when>-->
        <!--<xsl:when test="$leader6='k'">still image</xsl:when>-->
        <!--<xsl:when test="$leader6='g'">moving image</xsl:when>-->
        <!--<xsl:when test="$leader6='r'">three dimensional object</xsl:when>-->
        <!--<xsl:when test="$leader6='m'">software, multimedia</xsl:when>-->
        <!--<xsl:when test="$leader6='p'">mixed material</xsl:when>-->
        <!--</xsl:choose>-->
        <!--</dc:type>-->
        <!--<xsl:for-each select="marc:datafield[@tag=655]">-->
        <!--<dc:type>-->
        <!--<xsl:value-of select="."/>-->
        <!--</dc:type>-->
        <!--</xsl:for-each>-->
        <xsl:for-each select="marc:datafield[@tag=260]">
            <dc:publisher>
                <xsl:call-template name="subfieldSelect">
                    <xsl:with-param name="codes">ab</xsl:with-param>
                </xsl:call-template>
            </dc:publisher>
        </xsl:for-each>
        <xsl:for-each select="marc:controlfield[@tag=008]">
            <!--MZK datum nyni nebere z 260c ale z 008-->
            <xsl:variable name="typeDate" select="substring($controlField008,7,1)"/>
            <dc:date>
                <xsl:choose>
                    <xsl:when test="$typeDate='s'"><xsl:value-of select="substring(.,8,4)"/></xsl:when>
                    <xsl:when test="$typeDate='q'"><xsl:value-of select="substring(.,8,4)"/>-<xsl:value-of select="substring(.,12,4)"/></xsl:when>
                    <xsl:otherwise><xsl:value-of select="marc:datafield[@tag=260]/marc:subfield[@code='c']" /></xsl:otherwise>
                </xsl:choose>


            </dc:date>
        </xsl:for-each>
        <dc:language>
            <xsl:value-of select="substring($controlField008,36,3)"/>
        </dc:language>
        <xsl:for-each select="marc:datafield[@tag=856]/marc:subfield[@code='q']">
            <!--MZK to nas nezajima-->
            <!--<dc:format>-->
            <!--<xsl:value-of select="."/>-->
            <!--</dc:format>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=520]">
            <!--MZK to nas nezajima-->
            <!--<dc:description>-->
            <!--<xsl:value-of select="marc:subfield[@code='a']"/>-->
            <!--</dc:description>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=521]">
            <!--MZK to nas nezajima-->
            <!--<dc:description>-->
            <!--<xsl:value-of select="marc:subfield[@code='a']"/>-->
            <!--</dc:description>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[500&lt;= @tag and @tag&lt;= 599 ][not(@tag=506 or @tag=530 or @tag=540 or @tag=546)]">
            <!--MZK to nas nezajima-->
            <!--<dc:description>-->
            <!--<xsl:value-of select="marc:subfield[@code='a']"/>-->
            <!--</dc:description>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=600]">
            <!--MZK to nas nezajima-->
            <!--<dc:subject>-->
            <!--<xsl:call-template name="subfieldSelect">-->
            <!--<xsl:with-param name="codes">abcdq</xsl:with-param>-->
            <!--</xsl:call-template>-->
            <!--</dc:subject>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=610]">
            <!--MZK to nas nezajima-->
            <!--<dc:subject>-->
            <!--<xsl:call-template name="subfieldSelect">-->
            <!--<xsl:with-param name="codes">abcdq</xsl:with-param>-->
            <!--</xsl:call-template>-->
            <!--</dc:subject>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=611]">
            <!--MZK to nas nezajima-->
            <!--<dc:subject>-->
            <!--<xsl:call-template name="subfieldSelect">-->
            <!--<xsl:with-param name="codes">abcdq</xsl:with-param>-->
            <!--</xsl:call-template>-->
            <!--</dc:subject>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=630]">
            <!--MZK to nas nezajima-->
            <!--<dc:subject>-->
            <!--<xsl:call-template name="subfieldSelect">-->
            <!--<xsl:with-param name="codes">abcdq</xsl:with-param>-->
            <!--</xsl:call-template>-->
            <!--</dc:subject>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=650]">
            <!--MZK to nas nezajima-->
            <!--<dc:subject>-->
            <!--<xsl:call-template name="subfieldSelect">-->
            <!--<xsl:with-param name="codes">abcdq</xsl:with-param>-->
            <!--</xsl:call-template>-->
            <!--</dc:subject>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=653]">
            <!--MZK to nas nezajima-->
            <!--<dc:subject>-->
            <!--<xsl:call-template name="subfieldSelect">-->
            <!--<xsl:with-param name="codes">abcdq</xsl:with-param>-->
            <!--</xsl:call-template>-->
            <!--</dc:subject>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=752]">
            <!--MZK to nas nezajima-->
            <!--<dc:coverage>-->
            <!--<xsl:call-template name="subfieldSelect">-->
            <!--<xsl:with-param name="codes">abcd</xsl:with-param>-->
            <!--</xsl:call-template>-->
            <!--</dc:coverage>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=530]">
            <!--MZK to nas nezajima-->
            <!--<dc:relation>-->
            <!--<xsl:call-template name="subfieldSelect">-->
            <!--<xsl:with-param name="codes">abcdu</xsl:with-param>-->
            <!--</xsl:call-template>-->
            <!--</dc:relation>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=760]|marc:datafield[@tag=762]|marc:datafield[@tag=765]|marc:datafield[@tag=767]|marc:datafield[@tag=770]|marc:datafield[@tag=772]|marc:datafield[@tag=773]|marc:datafield[@tag=774]|marc:datafield[@tag=775]|marc:datafield[@tag=776]|marc:datafield[@tag=777]|marc:datafield[@tag=780]|marc:datafield[@tag=785]|marc:datafield[@tag=786]|marc:datafield[@tag=787]">
            <!--MZK to nas nezajima-->
            <!--<dc:relation>-->
            <!--<xsl:call-template name="subfieldSelect">-->
            <!--<xsl:with-param name="codes">ot</xsl:with-param>-->
            <!--</xsl:call-template>-->
            <!--</dc:relation>-->
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=856]">
            <dc:identifier>
                <xsl:value-of select="marc:subfield[@code='u']"/>
            </dc:identifier>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=020]">
            <dc:identifier>
                <xsl:text>URN:ISBN:</xsl:text>
                <xsl:value-of select="marc:subfield[@code='a']"/>
            </dc:identifier>
        </xsl:for-each>
        <!--MZK to nas nezajima-->
        <!--<xsl:for-each select="marc:datafield[@tag=506]">-->
        <!--<dc:rights>-->
        <!--<xsl:value-of select="marc:subfield[@code='a']"/>-->
        <!--</dc:rights>-->
        <!--</xsl:for-each>-->
        <!--<xsl:for-each select="marc:datafield[@tag=540]">-->
        <!--<dc:rights>-->
        <!--<xsl:value-of select="marc:subfield[@code='a']"/>-->
        <!--</dc:rights>-->
        <!--</xsl:for-each>-->
        <!--</oai_dc:dc>-->
    </xsl:template>
</xsl:stylesheet>

        <!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
        <metaInformation>
        <scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\..\..\javadev4\testsets\diacriticu8.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
        </metaInformation>
        -->