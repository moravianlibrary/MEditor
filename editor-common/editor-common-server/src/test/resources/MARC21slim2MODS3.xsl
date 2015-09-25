<xsl:stylesheet xmlns:mods="http://www.loc.gov/mods/v3" xmlns:marc="http://www.loc.gov/MARC21/slim"
                xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                exclude-result-prefixes="xlink marc" version="1.0">
    <xsl:output encoding="UTF-8" indent="yes" method="xml"/>
    <xsl:strip-space elements="*"/>
    <xsl:namespace-alias stylesheet-prefix="mods" result-prefix="mods"/>

    <!-- Maintenance note: For each revision, change the content of <mods:recordInfo><mods:recordOrigin> to reflect the new revision number.
	MARC21slim2MODS3-4 (Revision 1.76) 20120201

REvision 1.77.NDK - Added full qualification mods: elements
Revision 1.76.NDK - Field 015 as CCNB
Revision 1.76 - Fixed 242 - 2012/02/01 tmee
Revision 1.75 - Fixed 653 - 2012/01/31 tmee
Revision 1.74 - Fixed 510 note - 2011/07/15 tmee
Revision 1.73 - Fixed 506 540 - 2011/07/11 tmee
Revision 1.72 - Fixed frequency error - 2011/07/07 and 2011/07/14 tmee
Revision 1.71 - Fixed subject titles for subfields t - 2011/04/26 tmee 
Revision 1.70 - Added mapping for OCLC numbers in 035s to go into <mods:identifier type="oclc"> 2011/02/27 - tmee 	
Revision 1.69 - Added mapping for untyped identifiers for 024 - 2011/02/27 tmee 
Revision 1.68 - Added <mods:subject><mods:titleInfo> mapping for 600/610/611 subfields t,p,n - 2010/12/22 tmee
Revision 1.67 - Added frequency values and authority="marcfrequency" for 008/18 - 2010/12/09 tmee
Revision 1.66 - Fixed 008/06=c,d,i,m,k,u, from dateCreated to dateIssued - 2010/12/06 tmee
Revision 1.65 - Added back marcsmd and marccategory for 007 cr- 2010/12/06 tmee
Revision 1.64 - Fixed identifiers - removed isInvalid template - 2010/12/06 tmee
Revision 1.63 - Fixed descriptiveStandard value from aacr2 to aacr - 2010/12/06 tmee
Revision 1.62 - Fixed date mapping for 008/06=e,p,r,s,t - 2010/12/01 tmee
Revision 1.61 - Added 007 mappings for marccategory - 2010/11/12 tmee
Revision 1.60 - Added altRepGroups and 880 linkages for relevant fields, see mapping - 2010/11/26 tmee
Revision 1.59 - Added scriptTerm type=text to language for 546b and 066c - 2010/09/23 tmee
Revision 1.58 - Expanded script template to include code conversions for extended scripts - 2010/09/22 tmee
Revision 1.57 - Added Ldr/07 and Ldr/19 mappings - 2010/09/17 tmee
Revision 1.56 - Mapped 1xx usage="primary" - 2010/09/17 tmee
Revision 1.55 - Mapped UT 240/1xx nameTitleGroup - 2010/09/17 tmee
MODS 3.4
Revision 1.54 - Fixed 086 redundancy - 2010/07/27 tmee
Revision 1.53 - Added direct href for MARC21slimUtils - 2010/07/27 tmee
Revision 1.52 - Mapped 046 subfields c,e,k,l - 2010/04/09 tmee
Revision 1.51 - Corrected 856 transform - 2010/01/29 tmee
Revision 1.50 - Added 210 $2 authority attribute in <mods:titleInfo type=”abbreviated”> 2009/11/23 tmee
Revision 1.49 - Aquifer revision 1.14 - Added 240s (version) data to <mods:titleInfo type="uniform"><mods:title> 2009/11/23 tmee
Revision 1.48 - Aquifer revision 1.27 - Added mapping of 242 second indicator (for nonfiling characters) to <mods:titleInfo><mods:nonSort > subelement  2007/08/08 tmee/dlf
Revision 1.47 - Aquifer revision 1.26 - Mapped 300 subfield f (type of unit) - and g (size of unit) 2009 ntra
Revision 1.46 - Aquifer revision 1.25 - Changed mapping of 767 so that <type="otherVersion>  2009/11/20  tmee
Revision 1.45 - Aquifer revision 1.24 - Changed mapping of 765 so that <type="otherVersion>  2009/11/20  tmee 
Revision 1.44 - Added <mods:recordInfo><mods:recordOrigin> canned text about the version of this stylesheet 2009 ntra
Revision 1.43 - Mapped 351 subfields a,b,c 2009/11/20 tmee
Revision 1.42 - Changed 856 second indicator=1 to go to <mods:location><mods:url displayLabel=”electronic resource”> instead of to <mods:relatedItem type=”otherVersion”><mods:url> 2009/11/20 tmee
Revision 1.41 - Aquifer revision 1.9 Added variable and choice protocol for adding usage=”primary display” 2009/11/19 tmee 
Revision 1.40 - Dropped <mods:note> for 510 and added <mods:relatedItem type="isReferencedBy"> for 510 2009/11/19 tmee
Revision 1.39 - Aquifer revision 1.23 Changed mapping for 762 (Subseries Entry) from <mods:relatedItem type="series"> to <mods:relatedItem type="constituent"> 2009/11/19 tmee
Revision 1.38 - Aquifer revision 1.29 Dropped 007s for electronic versions 2009/11/18 tmee
Revision 1.37 - Fixed date redundancy in output (with questionable dates) 2009/11/16 tmee
Revision 1.36 - If mss material (Ldr/06=d,p,f,t) map 008 dates and 260$c/$g dates to dateCreated 2009/11/24, otherwise map 008 and 260$c/$g to dateIssued 2010/01/08 tmee
Revision 1.35 - Mapped appended detailed dates from 008/07-10 and 008/11-14 to dateIssued or DateCreated w/encoding="marc" 2010/01/12 tmee
Revision 1.34 - Mapped 045b B.C. and C.E. date range info to iso8601-compliant dates in <mods:subject><mods:temporal> 2009/01/08 ntra
Revision 1.33 - Mapped Ldr/06 "o" to <mods:typeOfResource>kit 2009/11/16 tmee
Revision 1.32 - Mapped specific note types from the MODS Note Type list <http://www.loc.gov/standards/mods/mods-notes.html> tmee 2009/11/17
Revision 1.31 - Mapped 540 to <mods:accessCondition type="use and reproduction"> and 506 to <mods:accessCondition type="restriction on access"> and delete mappings of 540 and 506 to <mods:note>
Revision 1.30 - Mapped 037c to <mods:identifier displayLabel=""> 2009/11/13 tmee
Revision 1.29 - Corrected schemaLocation to 3.3 2009/11/13 tmee
Revision 1.28 - Changed mapping from 752,662 g going to mods:hierarchicalGeographic/area instead of "region" 2009/07/30 ntra
Revision 1.27 - Mapped 648 to <mods:subject> 2009/03/13 tmee
Revision 1.26 - Added subfield $s mapping for 130/240/730  2008/10/16 tmee
Revision 1.25 - Mapped 040e to <mods:descriptiveStandard> and Leader/18 to <mods:descriptive standard>aacr2  2008/09/18 tmee
Revision 1.24 - Mapped 852 subfields $h, $i, $j, $k, $l, $m, $t to <mods:shelfLocation> and 852 subfield $u to <mods:physicalLocation> with @xlink 2008/09/17 tmee
Revision 1.23 - Commented out xlink/uri for subfield 0 for 130/240/730, 100/700, 110/710, 111/711 as these are currently unactionable  2008/09/17 tmee
Revision 1.22 - Mapped 022 subfield $l to type "issn-l" subfield $m to output identifier element with corresponding @type and @invalid eq 'yes'2008/09/17 tmee
Revision 1.21 - Mapped 856 ind2=1 or ind2=2 to <mods:relatedItem><mods:location><mods:url>  2008/07/03 tmee
Revision 1.20 - Added genre w/@auth="contents of 2" and type= "musical composition"  2008/07/01 tmee
Revision 1.19 - Added genre offprint for 008/24+ BK code 2  2008/07/01  tmee
Revision 1.18 - Added xlink/uri for subfield 0 for 130/240/730, 100/700, 110/710, 111/711  2008/06/26 tmee
Revision 1.17 - Added mapping of 662 2008/05/14 tmee	
Revision 1.16 - Changed @authority from "marc" to "marcgt" for 007 and 008 codes mapped to a term in <mods:genre> 2007/07/10 tmee
Revision 1.15 - For field 630, moved call to part template outside title element  2007/07/10 tmee
Revision 1.14 - Fixed template isValid and fields 010, 020, 022, 024, 028, and 037 to output additional identifier elements with corresponding @type and @invalid eq 'yes' when subfields z or y (in the case of 022) exist in the MARCXML ::: 2007/01/04 17:35:20 cred
Revision 1.13 - Changed order of output under cartographics to reflect schema  2006/11/28 tmee
Revision 1.12 - Updated to reflect MODS 3.2 Mapping  2006/10/11 tmee
Revision 1.11 - The attribute objectPart moved from <mods:languageTerm> to <mods:language>  2006/04/08  jrad
Revision 1.10 - MODS 3.1 revisions to language and classification elements (plus ability to find marc:collection embedded in wrapper elements such as SRU zs: wrappers)  2006/02/06  ggar
Revision 1.09 - Subfield $y was added to field 242 2004/09/02 10:57 jrad
Revision 1.08 - Subject chopPunctuation expanded and attribute fixes 2004/08/12 jrad
Revision 1.07 - 2004/03/25 08:29 jrad
Revision 1.06 - Various validation fixes 2004/02/20 ntra
Revision 1.05 - MODS2 to MODS3 updates, language unstacking and de-duping, chopPunctuation expanded  2003/10/02 16:18:58  ntra
Revision 1.03 - Additional Changes not related to MODS Version 2.0 by ntra
Revision 1.02 - Added Log Comment  2003/03/24 19:37:42  ckeith
	-->

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="//marc:collection">
                <mods:modsCollection xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                     xsi:schemaLocation="http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-5.xsd">
                    <xsl:for-each select="//marc:collection/marc:record">
                        <mods:mods version="3.5">
                            <xsl:call-template name="marcRecord"/>
                        </mods:mods>
                    </xsl:for-each>
                </mods:modsCollection>
            </xsl:when>
            <xsl:otherwise>
                <!-- <mods:mods xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="3.4"
					xsi:schemaLocation="http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-4.xsd"> -->
                <mods:mods>
                    <xsl:for-each select="//marc:record">
                        <xsl:call-template name="marcRecord"/>
                    </xsl:for-each>
                </mods:mods>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="marcRecord">
        <xsl:variable name="leader" select="marc:leader"/>
        <xsl:variable name="leader6" select="substring($leader,7,1)"/>
        <xsl:variable name="leader7" select="substring($leader,8,1)"/>
        <xsl:variable name="leader19" select="substring($leader,20,1)"/>
        <xsl:variable name="controlField008" select="marc:controlfield[@tag='008']"/>
        <xsl:variable name="typeOf008">
            <xsl:choose>
                <xsl:when test="$leader6='a'">
                    <xsl:choose>
                        <xsl:when
                                test="$leader7='a' or $leader7='c' or $leader7='d' or $leader7='m'"
                                >BK</xsl:when>
                        <xsl:when test="$leader7='b' or $leader7='i' or $leader7='s'">SE</xsl:when>
                    </xsl:choose>
                </xsl:when>
                <xsl:when test="$leader6='t'">BK</xsl:when>
                <xsl:when test="$leader6='p'">MM</xsl:when>
                <xsl:when test="$leader6='m'">CF</xsl:when>
                <xsl:when test="$leader6='e' or $leader6='f'">MP</xsl:when>
                <xsl:when test="$leader6='g' or $leader6='k' or $leader6='o' or $leader6='r'"
                        >VM</xsl:when>
                <xsl:when test="$leader6='c' or $leader6='d' or $leader6='i' or $leader6='j'"
                        >MU</xsl:when>
            </xsl:choose>
        </xsl:variable>

        <!-- titleInfo -->

        <xsl:for-each select="marc:datafield[@tag='245']">
            <xsl:call-template name="createTitleInfoFrom245"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='210']">
            <xsl:call-template name="createTitleInfoFrom210"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='246']">
            <xsl:call-template name="createTitleInfoFrom246"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='240']">
            <xsl:call-template name="createTitleInfoFrom240"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='740']">
            <xsl:call-template name="createTitleInfoFrom740"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='130']">
            <xsl:call-template name="createTitleInfoFrom130"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='730']">
            <xsl:call-template name="createTitleInfoFrom730"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='242']">
            <mods:titleInfo type="translated">
                <!--09/01/04 Added subfield $y-->
                <xsl:for-each select="marc:subfield[@code='y']">
                    <xsl:attribute name="lang">
                        <xsl:value-of select="text()"/>
                    </xsl:attribute>
                </xsl:for-each>

                <!-- AQ1.27 tmee/dlf -->
                <xsl:variable name="title">
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:call-template name="subfieldSelect">
                                <!-- 1/04 removed $h, b -->
                                <xsl:with-param name="codes">a</xsl:with-param>
                            </xsl:call-template>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:variable name="titleChop">
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="$title"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:choose>
                    <xsl:when test="@ind2>0">
                        <mods:nonSort>
                            <xsl:value-of select="substring($titleChop,1,@ind2)"/>
                        </mods:nonSort>
                        <mods:title>
                            <xsl:value-of select="substring($titleChop,@ind2+1)"/>
                        </mods:title>
                    </xsl:when>
                    <xsl:otherwise>
                        <mods:title>
                            <xsl:value-of select="$titleChop"/>
                        </mods:title>
                    </xsl:otherwise>
                </xsl:choose>

                <!-- 1/04 fix -->
                <xsl:call-template name="subtitle"/>
                <xsl:call-template name="part"/>
            </mods:titleInfo>
        </xsl:for-each>

        <!-- name -->

        <xsl:for-each select="marc:datafield[@tag='100']">
            <xsl:call-template name="createNameFrom100"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='110']">
            <xsl:call-template name="createNameFrom110"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='111']">
            <xsl:call-template name="createNameFrom111"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='700']">
            <xsl:call-template name="createNameFrom700"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='710']">
            <xsl:call-template name="createNameFrom710"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='711']">
            <xsl:call-template name="createNameFrom711"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='720']">
            <xsl:call-template name="createNameFrom720"/>
        </xsl:for-each>

        <!--old 7XXs
		<xsl:for-each select="marc:datafield[@tag='700'][not(marc:subfield[@code='t'])]">
			<mods:name type="personal">
				<xsl:call-template name="nameABCDQ"/>
				<xsl:call-template name="affiliation"/>
				<xsl:call-template name="role"/>
			</mods:name>
		</xsl:for-each>
		<xsl:for-each select="marc:datafield[@tag='710'][not(marc:subfield[@code='t'])]">
			<mods:name type="corporate">
				<xsl:call-template name="nameABCDN"/>
				<xsl:call-template name="role"/>
			</mods:name>
		</xsl:for-each>
		<xsl:for-each select="marc:datafield[@tag='711'][not(marc:subfield[@code='t'])]">
			<mods:name type="conference">
				<xsl:call-template name="nameACDEQ"/>
				<xsl:call-template name="role"/>
			</mods:name>
		</xsl:for-each>
		
		<xsl:for-each select="marc:datafield[@tag='720'][not(marc:subfield[@code='t'])]">
		<mods:name>
		<xsl:if test="@ind1=1">
		<xsl:attribute name="type">
		<xsl:text>personal</xsl:text>
		</xsl:attribute>
		</xsl:if>
		<mods:namePart>
		<xsl:value-of select="marc:subfield[@code='a']"/>
		</mods:namePart>
		<xsl:call-template name="role"/>
		</mods:name>
		</xsl:for-each>
-->

        <mods:typeOfResource>
            <xsl:if test="$leader7='c'">
                <xsl:attribute name="collection">yes</xsl:attribute>
            </xsl:if>
            <xsl:if test="$leader6='d' or $leader6='f' or $leader6='p' or $leader6='t'">
                <xsl:attribute name="manuscript">yes</xsl:attribute>
            </xsl:if>
            <xsl:choose>
                <xsl:when test="$leader6='a' or $leader6='t'">text</xsl:when>
                <xsl:when test="$leader6='e' or $leader6='f'">cartographic</xsl:when>
                <xsl:when test="$leader6='c' or $leader6='d'">notated music</xsl:when>
                <xsl:when test="$leader6='i'">sound recording-nonmusical</xsl:when>
                <xsl:when test="$leader6='j'">sound recording-musical</xsl:when>
                <xsl:when test="$leader6='k'">still image</xsl:when>
                <xsl:when test="$leader6='g'">moving image</xsl:when>
                <xsl:when test="$leader6='r'">three dimensional object</xsl:when>
                <xsl:when test="$leader6='m'">software, multimedia</xsl:when>
                <xsl:when test="$leader6='p'">mixed material</xsl:when>
            </xsl:choose>
        </mods:typeOfResource>
        <xsl:if test="substring($controlField008,26,1)='d'">
            <mods:genre authority="marcgt">globe</mods:genre>
        </xsl:if>
        <xsl:if
                test="marc:controlfield[@tag='007'][substring(text(),1,1)='a'][substring(text(),2,1)='r']">
            <mods:genre authority="marcgt">remote-sensing image</mods:genre>
        </xsl:if>
        <xsl:if test="$typeOf008='MP'">
            <xsl:variable name="controlField008-25" select="substring($controlField008,26,1)"/>
            <xsl:choose>
                <xsl:when
                        test="$controlField008-25='a' or $controlField008-25='b' or $controlField008-25='c' or marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='j']">
                    <mods:genre authority="marcgt">map</mods:genre>
                </xsl:when>
                <xsl:when
                        test="$controlField008-25='e' or marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='d']">
                    <mods:genre authority="marcgt">atlas</mods:genre>
                </xsl:when>
            </xsl:choose>
        </xsl:if>
        <xsl:if test="$typeOf008='SE'">
            <xsl:variable name="controlField008-21" select="substring($controlField008,22,1)"/>
            <xsl:choose>
                <xsl:when test="$controlField008-21='d'">
                    <mods:genre authority="marcgt">database</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-21='l'">
                    <mods:genre authority="marcgt">loose-leaf</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-21='m'">
                    <mods:genre authority="marcgt">series</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-21='n'">
                    <mods:genre authority="marcgt">newspaper</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-21='p'">
                    <mods:genre authority="marcgt">periodical</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-21='w'">
                    <mods:genre authority="marcgt">web site</mods:genre>
                </xsl:when>
            </xsl:choose>
        </xsl:if>
        <xsl:if test="$typeOf008='BK' or $typeOf008='SE'">
            <xsl:variable name="controlField008-24" select="substring($controlField008,25,4)"/>
            <xsl:choose>
                <xsl:when test="contains($controlField008-24,'a')">
                    <mods:genre authority="marcgt">abstract or summary</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'b')">
                    <mods:genre authority="marcgt">bibliography</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'c')">
                    <mods:genre authority="marcgt">catalog</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'d')">
                    <mods:genre authority="marcgt">dictionary</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'e')">
                    <mods:genre authority="marcgt">encyclopedia</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'f')">
                    <mods:genre authority="marcgt">handbook</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'g')">
                    <mods:genre authority="marcgt">legal article</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'i')">
                    <mods:genre authority="marcgt">index</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'k')">
                    <mods:genre authority="marcgt">discography</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'l')">
                    <mods:genre authority="marcgt">legislation</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'m')">
                    <mods:genre authority="marcgt">theses</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'n')">
                    <mods:genre authority="marcgt">survey of literature</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'o')">
                    <mods:genre authority="marcgt">review</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'p')">
                    <mods:genre authority="marcgt">programmed text</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'q')">
                    <mods:genre authority="marcgt">filmography</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'r')">
                    <mods:genre authority="marcgt">directory</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'s')">
                    <mods:genre authority="marcgt">statistics</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'t')">
                    <mods:genre authority="marcgt">technical report</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'v')">
                    <mods:genre authority="marcgt">legal case and case notes</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'w')">
                    <mods:genre authority="marcgt">law report or digest</mods:genre>
                </xsl:when>
                <xsl:when test="contains($controlField008-24,'z')">
                    <mods:genre authority="marcgt">treaty</mods:genre>
                </xsl:when>
            </xsl:choose>
            <xsl:variable name="controlField008-29" select="substring($controlField008,30,1)"/>
            <xsl:choose>
                <xsl:when test="$controlField008-29='1'">
                    <mods:genre authority="marcgt">conference publication</mods:genre>
                </xsl:when>
            </xsl:choose>
        </xsl:if>
        <xsl:if test="$typeOf008='CF'">
            <xsl:variable name="controlField008-26" select="substring($controlField008,27,1)"/>
            <xsl:choose>
                <xsl:when test="$controlField008-26='a'">
                    <mods:genre authority="marcgt">numeric data</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-26='e'">
                    <mods:genre authority="marcgt">database</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-26='f'">
                    <mods:genre authority="marcgt">font</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-26='g'">
                    <mods:genre authority="marcgt">game</mods:genre>
                </xsl:when>
            </xsl:choose>
        </xsl:if>
        <xsl:if test="$typeOf008='BK'">
            <xsl:if test="substring($controlField008,25,1)='j'">
                <mods:genre authority="marcgt">patent</mods:genre>
            </xsl:if>
            <xsl:if test="substring($controlField008,25,1)='2'">
                <mods:genre authority="marcgt">offprint</mods:genre>
            </xsl:if>
            <xsl:if test="substring($controlField008,31,1)='1'">
                <mods:genre authority="marcgt">festschrift</mods:genre>
            </xsl:if>
            <xsl:variable name="controlField008-34" select="substring($controlField008,35,1)"/>
            <xsl:if
                    test="$controlField008-34='a' or $controlField008-34='b' or $controlField008-34='c' or $controlField008-34='d'">
                <mods:genre authority="marcgt">biography</mods:genre>
            </xsl:if>
            <xsl:variable name="controlField008-33" select="substring($controlField008,34,1)"/>
            <xsl:choose>
                <xsl:when test="$controlField008-33='e'">
                    <mods:genre authority="marcgt">essay</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='d'">
                    <mods:genre authority="marcgt">drama</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='c'">
                    <mods:genre authority="marcgt">comic strip</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='l'">
                    <mods:genre authority="marcgt">fiction</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='h'">
                    <mods:genre authority="marcgt">humor, satire</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='i'">
                    <mods:genre authority="marcgt">letter</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='f'">
                    <mods:genre authority="marcgt">novel</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='j'">
                    <mods:genre authority="marcgt">short story</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='s'">
                    <mods:genre authority="marcgt">speech</mods:genre>
                </xsl:when>
            </xsl:choose>
        </xsl:if>
        <xsl:if test="$typeOf008='MU'">
            <xsl:variable name="controlField008-30-31" select="substring($controlField008,31,2)"/>
            <xsl:if test="contains($controlField008-30-31,'b')">
                <mods:genre authority="marcgt">biography</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'c')">
                <mods:genre authority="marcgt">conference publication</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'d')">
                <mods:genre authority="marcgt">drama</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'e')">
                <mods:genre authority="marcgt">essay</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'f')">
                <mods:genre authority="marcgt">fiction</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'o')">
                <mods:genre authority="marcgt">folktale</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'h')">
                <mods:genre authority="marcgt">history</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'k')">
                <mods:genre authority="marcgt">humor, satire</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'m')">
                <mods:genre authority="marcgt">memoir</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'p')">
                <mods:genre authority="marcgt">poetry</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'r')">
                <mods:genre authority="marcgt">rehearsal</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'g')">
                <mods:genre authority="marcgt">reporting</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'s')">
                <mods:genre authority="marcgt">sound</mods:genre>
            </xsl:if>
            <xsl:if test="contains($controlField008-30-31,'l')">
                <mods:genre authority="marcgt">speech</mods:genre>
            </xsl:if>
        </xsl:if>
        <xsl:if test="$typeOf008='VM'">
            <xsl:variable name="controlField008-33" select="substring($controlField008,34,1)"/>
            <xsl:choose>
                <xsl:when test="$controlField008-33='a'">
                    <mods:genre authority="marcgt">art original</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='b'">
                    <mods:genre authority="marcgt">kit</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='c'">
                    <mods:genre authority="marcgt">art reproduction</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='d'">
                    <mods:genre authority="marcgt">diorama</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='f'">
                    <mods:genre authority="marcgt">filmstrip</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='g'">
                    <mods:genre authority="marcgt">legal article</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='i'">
                    <mods:genre authority="marcgt">picture</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='k'">
                    <mods:genre authority="marcgt">graphic</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='l'">
                    <mods:genre authority="marcgt">technical drawing</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='m'">
                    <mods:genre authority="marcgt">motion picture</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='n'">
                    <mods:genre authority="marcgt">chart</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='o'">
                    <mods:genre authority="marcgt">flash card</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='p'">
                    <mods:genre authority="marcgt">microscope slide</mods:genre>
                </xsl:when>
                <xsl:when
                        test="$controlField008-33='q' or marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='q']">
                    <mods:genre authority="marcgt">model</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='r'">
                    <mods:genre authority="marcgt">realia</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='s'">
                    <mods:genre authority="marcgt">slide</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='t'">
                    <mods:genre authority="marcgt">transparency</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='v'">
                    <mods:genre authority="marcgt">videorecording</mods:genre>
                </xsl:when>
                <xsl:when test="$controlField008-33='w'">
                    <mods:genre authority="marcgt">toy</mods:genre>
                </xsl:when>
            </xsl:choose>
        </xsl:if>

        <!-- genre -->

        <xsl:for-each select="marc:datafield[@tag=047]">
            <xsl:call-template name="createGenreFrom047"/>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=655]">
            <xsl:call-template name="createGenreFrom655"/>
        </xsl:for-each>

        <xsl:if test="marc:datafield[@tag=260]">
            <mods:originInfo>
                <xsl:call-template name="scriptCode"/>
                <xsl:for-each
                        select="marc:datafield[(@tag=260 or @tag=250) and marc:subfield[@code='a' or code='b' or @code='c' or code='g']]">
                    <xsl:call-template name="z2xx880"/>
                </xsl:for-each>

                <xsl:variable name="MARCpublicationCode"
                              select="normalize-space(substring($controlField008,16,3))"/>
                <xsl:if test="translate($MARCpublicationCode,'|','')">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">marccountry</xsl:attribute>
                            <xsl:value-of select="$MARCpublicationCode"/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:if>
                <xsl:for-each select="marc:datafield[@tag=044]/marc:subfield[@code='c']">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">iso3166</xsl:attribute>
                            <xsl:value-of select="."/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=260]/marc:subfield[@code='a']">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">text</xsl:attribute>
                            <xsl:call-template name="chopPunctuationFront">
                                <xsl:with-param name="chopString">
                                    <xsl:call-template name="chopPunctuation">
                                        <xsl:with-param name="chopString" select="."/>
                                    </xsl:call-template>
                                </xsl:with-param>
                            </xsl:call-template>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='m']">
                    <mods:dateValid point="start">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='n']">
                    <mods:dateValid point="end">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='j']">
                    <mods:dateModified>
                        <xsl:value-of select="."/>
                    </mods:dateModified>
                </xsl:for-each>

                <!-- tmee 1.52 -->

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='c']">
                    <mods:dateIssued encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='e']">
                    <mods:dateIssued encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='k']">
                    <mods:dateCreated encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='l']">
                    <mods:dateCreated encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>

                <!-- tmee 1.35 1.36 dateIssued/nonMSS vs dateCreated/MSS -->
                <xsl:for-each
                        select="marc:datafield[@tag=260]/marc:subfield[@code='b' or @code='c' or @code='g']">
                    <xsl:choose>
                        <xsl:when test="@code='b'">
                            <mods:publisher>
                                <xsl:call-template name="chopPunctuation">
                                    <xsl:with-param name="chopString" select="."/>
                                    <xsl:with-param name="punctuation">
                                        <xsl:text>:,;/ </xsl:text>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </mods:publisher>
                        </xsl:when>
                        <xsl:when test="(@code='c')">
                            <xsl:if test="$leader6='d' or $leader6='f' or $leader6='p' or $leader6='t'">
                                <mods:dateIssued>
                                    <xsl:call-template name="chopPunctuation">
                                        <xsl:with-param name="chopString" select="."/>
                                    </xsl:call-template>
                                </mods:dateIssued>
                            </xsl:if>

                            <xsl:if
                                    test="not($leader6='d' or $leader6='f' or $leader6='p' or $leader6='t')">
                                <mods:dateIssued>
                                    <xsl:call-template name="chopPunctuation">
                                        <xsl:with-param name="chopString" select="."/>
                                    </xsl:call-template>
                                </mods:dateIssued>
                            </xsl:if>
                        </xsl:when>
                        <xsl:when test="@code='g'">
                            <xsl:if test="$leader6='d' or $leader6='f' or $leader6='p' or $leader6='t'">
                                <mods:dateCreated>
                                    <xsl:value-of select="."/>
                                </mods:dateCreated>
                            </xsl:if>
                            <xsl:if
                                    test="not($leader6='d' or $leader6='f' or $leader6='p' or $leader6='t')">
                                <mods:dateCreated>
                                    <xsl:value-of select="."/>
                                </mods:dateCreated>
                            </xsl:if>
                        </xsl:when>
                    </xsl:choose>
                </xsl:for-each>
                <xsl:variable name="dataField260c">
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString"
                                        select="marc:datafield[@tag=260]/marc:subfield[@code='c']"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:variable name="controlField008-7-10"
                              select="normalize-space(substring($controlField008, 8, 4))"/>
                <xsl:variable name="controlField008-11-14"
                              select="normalize-space(substring($controlField008, 12, 4))"/>
                <xsl:variable name="controlField008-6"
                              select="normalize-space(substring($controlField008, 7, 1))"/>

                <!-- tmee 1.35 and 1.36 -->

                <xsl:if
                        test="($controlField008-6='e' or $controlField008-6='p' or $controlField008-6='r' or $controlField008-6='s' or $controlField008-6='t') and ($leader6='d' or $leader6='f' or $leader6='p' or $leader6='t')">
                    <xsl:if test="$controlField008-7-10 and ($controlField008-7-10 != $dataField260c)">
                        <mods:dateIssued encoding="marc">
                            <xsl:value-of select="$controlField008-7-10"
                                    />
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if
                        test="($controlField008-6='e' or $controlField008-6='p' or $controlField008-6='r' or $controlField008-6='s' or $controlField008-6='t') and not($leader6='d' or $leader6='f' or $leader6='p' or $leader6='t')">
                    <xsl:if test="$controlField008-7-10 and ($controlField008-7-10 != $dataField260c)">
                        <mods:dateIssued encoding="marc">
                            <xsl:value-of select="$controlField008-7-10"
                                    />
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if
                        test="$controlField008-6='c' or $controlField008-6='d' or $controlField008-6='i' or $controlField008-6='k' or $controlField008-6='m' or $controlField008-6='u'">
                    <xsl:if test="$controlField008-7-10">
                        <mods:dateIssued encoding="marc" point="start">
                            <xsl:value-of select="$controlField008-7-10"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if
                        test="$controlField008-6='c' or $controlField008-6='d' or $controlField008-6='i' or $controlField008-6='k' or $controlField008-6='m' or $controlField008-6='u'">
                    <xsl:if test="$controlField008-11-14">
                        <mods:dateIssued encoding="marc" point="end">
                            <xsl:value-of select="$controlField008-11-14"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if test="$controlField008-6='q'">
                    <xsl:if test="$controlField008-7-10 and $controlField008-11-14 = '' and ($controlField008-7-10 != $dataField260c)">
                        <mods:dateIssued encoding="marc" qualifier="questionable">
                            <xsl:value-of select="$controlField008-7-10"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>
                <xsl:if test="$controlField008-6='q'">
                    <xsl:if test="$controlField008-7-10 and $controlField008-11-14">
                        <mods:dateIssued encoding="marc" point="start" qualifier="questionable">
                            <xsl:value-of select="$controlField008-7-10"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>
                <xsl:if test="$controlField008-6='q'">
                    <xsl:if test="$controlField008-7-10 and $controlField008-11-14">
                        <mods:dateIssued encoding="marc" point="end" qualifier="questionable">
                            <xsl:value-of select="$controlField008-11-14"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if test="$controlField008-6='t'">
                    <xsl:if test="$controlField008-11-14">
                        <mods:copyrightDate encoding="marc">
                            <xsl:value-of select="$controlField008-11-14"/>
                        </mods:copyrightDate>
                    </xsl:if>
                </xsl:if>
                <xsl:for-each
                        select="marc:datafield[@tag=033][@ind1=0 or @ind1=1]/marc:subfield[@code='a']">
                    <mods:dateCaptured encoding="iso8601">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][1]">
                    <mods:dateCaptured encoding="iso8601" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][2]">
                    <mods:dateCaptured encoding="iso8601" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=250]/marc:subfield[@code='a']">
                    <mods:edition>
                        <xsl:value-of select="."/>
                    </mods:edition>
                </xsl:for-each>
                <xsl:for-each select="marc:leader">
                    <mods:issuance>
                        <xsl:choose>
                            <xsl:when test="$leader7='a' or $leader7='c' or $leader7='d'">monographic</xsl:when>
                            <xsl:when test="$leader7='b' or $leader7='i' or $leader7='s'">continuing</xsl:when>
                            <xsl:when test="$leader7='m' and $leader19='a'">multipart monograph</xsl:when>
                            <xsl:when test="$leader7='m' and ../marc:datafield[@tag=245]/marc:subfield[@code='n' or @code='p']">multipart monograph</xsl:when>
                            <xsl:when test="$leader7='m' and $leader19=' '">single unit</xsl:when>
                            <xsl:otherwise>monographic</xsl:otherwise>
                        </xsl:choose>
                    </mods:issuance>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=310]|marc:datafield[@tag=321]">
                    <mods:frequency authority="marcfrequency">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">ab</xsl:with-param>
                        </xsl:call-template>
                    </mods:frequency>
                </xsl:for-each>
                <!--	1.67 1.72	-->

                <xsl:if test="$typeOf008='SE'">
                    <xsl:for-each select="marc:controlfield[@tag=008]">
                        <xsl:variable name="controlField008-18"
                                      select="substring($controlField008,19,1)"/>
                        <xsl:variable name="frequency">
                            <mods:frequency>
                                <xsl:choose>
                                    <xsl:when test="$controlField008-18='a'">Annual</xsl:when>
                                    <xsl:when test="$controlField008-18='b'">Bimonthly</xsl:when>
                                    <xsl:when test="$controlField008-18='c'">Semiweekly</xsl:when>
                                    <xsl:when test="$controlField008-18='d'">Daily</xsl:when>
                                    <xsl:when test="$controlField008-18='e'">Biweekly</xsl:when>
                                    <xsl:when test="$controlField008-18='f'">Semiannual</xsl:when>
                                    <xsl:when test="$controlField008-18='g'">Biennial</xsl:when>
                                    <xsl:when test="$controlField008-18='h'">Triennial</xsl:when>
                                    <xsl:when test="$controlField008-18='i'">Three times a
                                        week</xsl:when>
                                    <xsl:when test="$controlField008-18='j'">Three times a
                                        month</xsl:when>
                                    <xsl:when test="$controlField008-18='k'">Continuously
                                        updated</xsl:when>
                                    <xsl:when test="$controlField008-18='m'">Monthly</xsl:when>
                                    <xsl:when test="$controlField008-18='q'">Quarterly</xsl:when>
                                    <xsl:when test="$controlField008-18='s'">Semimonthly</xsl:when>
                                    <xsl:when test="$controlField008-18='t'">Three times a
                                        year</xsl:when>
                                    <xsl:when test="$controlField008-18='u'">Unknown</xsl:when>
                                    <xsl:when test="$controlField008-18='w'">Weekly</xsl:when>
                                    <xsl:when test="$controlField008-18='#'">Completely
                                        irregular</xsl:when>
                                    <xsl:otherwise/>
                                </xsl:choose>
                            </mods:frequency>
                        </xsl:variable>
                        <xsl:if test="$frequency!=''">
                            <mods:frequency>
                                <xsl:value-of select="$frequency"/>
                            </mods:frequency>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>
            </mods:originInfo>
        </xsl:if>

        <xsl:if test="marc:datafield[@tag=264][@ind2=0]">
            <mods:originInfo eventType="production">
                <xsl:call-template name="scriptCode"/>
                <xsl:for-each
                        select="marc:datafield[(@tag=260 or @tag=250) and marc:subfield[@code='a' or code='b' or @code='c' or code='g']]">
                    <xsl:call-template name="z2xx880"/>
                </xsl:for-each>

                <xsl:variable name="MARCpublicationCode"
                              select="normalize-space(substring($controlField008,16,3))"/>
                <xsl:if test="translate($MARCpublicationCode,'|','')">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">marccountry</xsl:attribute>
                            <xsl:value-of select="$MARCpublicationCode"/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:if>

                <xsl:for-each select="marc:datafield[@tag=264 and @ind2=0]">

                    <xsl:for-each select="marc:subfield[@code='a' or @code='b']">

                        <xsl:if test="@code='a'">
                            <mods:place>
                                <mods:placeTerm type="text">
                                    <xsl:call-template name="chopPunctuationBoth">
                                        <xsl:with-param name="chopString">
                                            <xsl:call-template name="chopPunctuation">
                                                <xsl:with-param name="chopString" select="."/>
                                            </xsl:call-template>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </mods:placeTerm>
                            </mods:place>
                        </xsl:if>

                        <xsl:if test="@code='b'">
                            <mods:publisher>
                                <xsl:call-template name="chopPunctuationBoth">
                                    <xsl:with-param name="chopString">
                                        <xsl:call-template name="chopPunctuation">
                                            <xsl:with-param name="chopString" select="."/>
                                        </xsl:call-template>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </mods:publisher>
                        </xsl:if>

                    </xsl:for-each>

                    <xsl:for-each select="marc:subfield[@code='c']">
                        <mods:dateOther type="production">
                            <xsl:call-template name="chopPunctuationBoth">
                                <xsl:with-param name="chopString">
                                    <xsl:call-template name="chopPunctuation">
                                        <xsl:with-param name="chopString" select="."/>
                                    </xsl:call-template>
                                </xsl:with-param>
                            </xsl:call-template>
                        </mods:dateOther>
                    </xsl:for-each>

                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=044]/marc:subfield[@code='c']">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">iso3166</xsl:attribute>
                            <xsl:value-of select="."/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='m']">
                    <mods:dateValid point="start">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='n']">
                    <mods:dateValid point="end">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='j']">
                    <mods:dateModified>
                        <xsl:value-of select="."/>
                    </mods:dateModified>
                </xsl:for-each>

                <!-- tmee 1.52 -->

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='c']">
                    <mods:dateIssued encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='e']">
                    <mods:dateIssued encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='k']">
                    <mods:dateCreated encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='l']">
                    <mods:dateCreated encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>

                <xsl:variable name="controlField008-7-10"
                              select="normalize-space(substring($controlField008, 8, 4))"/>
                <xsl:variable name="controlField008-11-14"
                              select="normalize-space(substring($controlField008, 12, 4))"/>
                <xsl:variable name="controlField008-6"
                              select="normalize-space(substring($controlField008, 7, 1))"/>

                <!-- tmee 1.35 and 1.36 -->

                <xsl:if test="$controlField008-6='t'">
                    <xsl:if test="$controlField008-11-14">
                        <mods:copyrightDate encoding="marc">
                            <xsl:value-of select="$controlField008-11-14"/>
                        </mods:copyrightDate>
                    </xsl:if>
                </xsl:if>
                <xsl:for-each
                        select="marc:datafield[@tag=033][@ind1=0 or @ind1=1]/marc:subfield[@code='a']">
                    <mods:dateCaptured encoding="iso8601">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][1]">
                    <mods:dateCaptured encoding="iso8601" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][2]">
                    <mods:dateCaptured encoding="iso8601" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <!--	1.67 1.72	-->
            </mods:originInfo>
        </xsl:if>

        <xsl:if test="marc:datafield[@tag=264][@ind2=1]">
            <mods:originInfo eventType="publication">
                <xsl:call-template name="scriptCode"/>
                <xsl:for-each
                        select="marc:datafield[(@tag=260 or @tag=250) and marc:subfield[@code='a' or code='b' or @code='c' or code='g']]">
                    <xsl:call-template name="z2xx880"/>
                </xsl:for-each>

                <xsl:variable name="MARCpublicationCode"
                              select="normalize-space(substring($controlField008,16,3))"/>
                <xsl:if test="translate($MARCpublicationCode,'|','')">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">marccountry</xsl:attribute>
                            <xsl:value-of select="$MARCpublicationCode"/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:if>

                <xsl:for-each select="marc:datafield[@tag=264 and @ind2=1]">

                    <xsl:for-each select="marc:subfield[@code='a' or @code='b']">

                        <xsl:if test="@code='a'">
                            <mods:place>
                                <mods:placeTerm type="text">
                                    <xsl:call-template name="chopPunctuationBoth">
                                        <xsl:with-param name="chopString">
                                            <xsl:call-template name="chopPunctuation">
                                                <xsl:with-param name="chopString" select="."/>
                                            </xsl:call-template>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </mods:placeTerm>
                            </mods:place>
                        </xsl:if>

                        <xsl:if test="@code='b'">
                            <mods:publisher>
                                <xsl:call-template name="chopPunctuationBoth">
                                    <xsl:with-param name="chopString">
                                        <xsl:call-template name="chopPunctuation">
                                            <xsl:with-param name="chopString" select="."/>
                                        </xsl:call-template>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </mods:publisher>
                        </xsl:if>

                    </xsl:for-each>

                    <xsl:for-each select="marc:subfield[@code='c']">
                        <mods:dateIssued>
                            <xsl:call-template name="chopPunctuationBoth">
                                <xsl:with-param name="chopString">
                                    <xsl:call-template name="chopPunctuationBoth">
                                        <xsl:with-param name="chopString" select="."/>
                                    </xsl:call-template>
                                </xsl:with-param>
                            </xsl:call-template>
                        </mods:dateIssued>
                    </xsl:for-each>

                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=044]/marc:subfield[@code='c']">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">iso3166</xsl:attribute>
                            <xsl:value-of select="."/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='m']">
                    <mods:dateValid point="start">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='n']">
                    <mods:dateValid point="end">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='j']">
                    <mods:dateModified>
                        <xsl:value-of select="."/>
                    </mods:dateModified>
                </xsl:for-each>

                <!-- tmee 1.52 -->

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='c']">
                    <mods:dateIssued encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='e']">
                    <mods:dateIssued encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='k']">
                    <mods:dateCreated encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='l']">
                    <mods:dateCreated encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>

                <xsl:variable name="dataField264c">
                    <xsl:call-template name="chopPunctuationBoth">
                        <xsl:with-param name="chopString"
                                        select="marc:datafield[@tag=264][@ind2=1]/marc:subfield[@code='c']"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:variable name="controlField008-7-10"
                              select="normalize-space(substring($controlField008, 8, 4))"/>
                <xsl:variable name="controlField008-11-14"
                              select="normalize-space(substring($controlField008, 12, 4))"/>
                <xsl:variable name="controlField008-6"
                              select="normalize-space(substring($controlField008, 7, 1))"/>

                <!-- tmee 1.35 and 1.36 -->

                <xsl:if
                        test="($controlField008-6='e' or $controlField008-6='p' or $controlField008-6='r' or $controlField008-6='s' or $controlField008-6='t') and ($leader6='d' or $leader6='f' or $leader6='p' or $leader6='t')">
                    <xsl:if test="$controlField008-7-10 and ($controlField008-7-10 != $dataField264c)">
                        <mods:dateIssued encoding="marc">
                            <xsl:value-of select="$controlField008-7-10"
                                    />
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if
                        test="($controlField008-6='e' or $controlField008-6='p' or $controlField008-6='r' or $controlField008-6='s' or $controlField008-6='t') and not($leader6='d' or $leader6='f' or $leader6='p' or $leader6='t')">
                    <xsl:if test="$controlField008-7-10 and ($controlField008-7-10 != $dataField264c)">
                        <mods:dateIssued encoding="marc">
                            <xsl:value-of select="$controlField008-7-10"
                                    />
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if
                        test="$controlField008-6='c' or $controlField008-6='d' or $controlField008-6='i' or $controlField008-6='k' or $controlField008-6='m' or $controlField008-6='u'">
                    <xsl:if test="$controlField008-7-10">
                        <mods:dateIssued encoding="marc" point="start">
                            <xsl:value-of select="$controlField008-7-10"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if
                        test="$controlField008-6='c' or $controlField008-6='d' or $controlField008-6='i' or $controlField008-6='k' or $controlField008-6='m' or $controlField008-6='u'">
                    <xsl:if test="$controlField008-11-14">
                        <mods:dateIssued encoding="marc" point="end">
                            <xsl:value-of select="$controlField008-11-14"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if test="$controlField008-6='q'">
                    <xsl:if test="$controlField008-7-10 and $controlField008-11-14 = '' and ($controlField008-7-10 != $dataField264c)">
                        <mods:dateIssued encoding="marc" qualifier="questionable">
                            <xsl:value-of select="$controlField008-7-10"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>
                <xsl:if test="$controlField008-6='q'">
                    <xsl:if test="$controlField008-7-10 and $controlField008-11-14">
                        <mods:dateIssued encoding="marc" point="start" qualifier="questionable">
                            <xsl:value-of select="$controlField008-7-10"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>
                <xsl:if test="$controlField008-6='q'">
                    <xsl:if test="$controlField008-7-10 and $controlField008-11-14">
                        <mods:dateIssued encoding="marc" point="end" qualifier="questionable">
                            <xsl:value-of select="$controlField008-11-14"/>
                        </mods:dateIssued>
                    </xsl:if>
                </xsl:if>

                <xsl:if test="$controlField008-6='t'">
                    <xsl:if test="$controlField008-11-14">
                        <mods:copyrightDate encoding="marc">
                            <xsl:value-of select="$controlField008-11-14"/>
                        </mods:copyrightDate>
                    </xsl:if>
                </xsl:if>
                <xsl:for-each
                        select="marc:datafield[@tag=033][@ind1=0 or @ind1=1]/marc:subfield[@code='a']">
                    <mods:dateCaptured encoding="iso8601">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][1]">
                    <mods:dateCaptured encoding="iso8601" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][2]">
                    <mods:dateCaptured encoding="iso8601" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=250]/marc:subfield[@code='a']">
                    <mods:edition>
                        <xsl:value-of select="."/>
                    </mods:edition>
                </xsl:for-each>
                <xsl:for-each select="marc:leader">
                    <mods:issuance>
                        <xsl:choose>
                            <xsl:when test="$leader7='a' or $leader7='c' or $leader7='d'">monographic</xsl:when>
                            <xsl:when test="$leader7='b' or $leader7='i' or $leader7='s'">continuing</xsl:when>
                            <xsl:when test="$leader7='m' and $leader19='a'">multipart monograph</xsl:when>
                            <xsl:when test="$leader7='m' and ../marc:datafield[@tag=245]/marc:subfield[@code='n' or @code='p']">multipart monograph</xsl:when>
                            <xsl:when test="$leader7='m' and $leader19=' '">single unit</xsl:when>
                            <xsl:otherwise>monographic</xsl:otherwise>
                        </xsl:choose>
                    </mods:issuance>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=310]|marc:datafield[@tag=321]">
                    <mods:frequency authority="marcfrequency">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">ab</xsl:with-param>
                        </xsl:call-template>
                    </mods:frequency>
                </xsl:for-each>
                <!--	1.67 1.72	-->

                <xsl:if test="$typeOf008='SE'">
                    <xsl:for-each select="marc:controlfield[@tag=008]">
                        <xsl:variable name="controlField008-18"
                                      select="substring($controlField008,19,1)"/>
                        <xsl:variable name="frequency">
                            <mods:frequency>
                                <xsl:choose>
                                    <xsl:when test="$controlField008-18='a'">Annual</xsl:when>
                                    <xsl:when test="$controlField008-18='b'">Bimonthly</xsl:when>
                                    <xsl:when test="$controlField008-18='c'">Semiweekly</xsl:when>
                                    <xsl:when test="$controlField008-18='d'">Daily</xsl:when>
                                    <xsl:when test="$controlField008-18='e'">Biweekly</xsl:when>
                                    <xsl:when test="$controlField008-18='f'">Semiannual</xsl:when>
                                    <xsl:when test="$controlField008-18='g'">Biennial</xsl:when>
                                    <xsl:when test="$controlField008-18='h'">Triennial</xsl:when>
                                    <xsl:when test="$controlField008-18='i'">Three times a
                                        week</xsl:when>
                                    <xsl:when test="$controlField008-18='j'">Three times a
                                        month</xsl:when>
                                    <xsl:when test="$controlField008-18='k'">Continuously
                                        updated</xsl:when>
                                    <xsl:when test="$controlField008-18='m'">Monthly</xsl:when>
                                    <xsl:when test="$controlField008-18='q'">Quarterly</xsl:when>
                                    <xsl:when test="$controlField008-18='s'">Semimonthly</xsl:when>
                                    <xsl:when test="$controlField008-18='t'">Three times a
                                        year</xsl:when>
                                    <xsl:when test="$controlField008-18='u'">Unknown</xsl:when>
                                    <xsl:when test="$controlField008-18='w'">Weekly</xsl:when>
                                    <xsl:when test="$controlField008-18='#'">Completely
                                        irregular</xsl:when>
                                    <xsl:otherwise/>
                                </xsl:choose>
                            </mods:frequency>
                        </xsl:variable>
                        <xsl:if test="$frequency!=''">
                            <mods:frequency>
                                <xsl:value-of select="$frequency"/>
                            </mods:frequency>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>
            </mods:originInfo>
        </xsl:if>

        <xsl:if test="marc:datafield[@tag=264][@ind2=2]">
            <mods:originInfo eventType="distribution">
                <xsl:call-template name="scriptCode"/>
                <xsl:for-each
                        select="marc:datafield[(@tag=260 or @tag=250) and marc:subfield[@code='a' or code='b' or @code='c' or code='g']]">
                    <xsl:call-template name="z2xx880"/>
                </xsl:for-each>

                <xsl:variable name="MARCpublicationCode"
                              select="normalize-space(substring($controlField008,16,3))"/>
                <xsl:if test="translate($MARCpublicationCode,'|','')">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">marccountry</xsl:attribute>
                            <xsl:value-of select="$MARCpublicationCode"/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:if>

                <xsl:for-each select="marc:datafield[@tag=264 and @ind2=2]">

                    <xsl:for-each select="marc:subfield[@code='a' or @code='b']">

                        <xsl:if test="@code='a'">
                            <mods:place>
                                <mods:placeTerm type="text">
                                    <xsl:call-template name="chopPunctuationBoth">
                                        <xsl:with-param name="chopString">
                                            <xsl:call-template name="chopPunctuation">
                                                <xsl:with-param name="chopString" select="."/>
                                            </xsl:call-template>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </mods:placeTerm>
                            </mods:place>
                        </xsl:if>

                        <xsl:if test="@code='b'">
                            <mods:publisher>
                                <xsl:call-template name="chopPunctuationBoth">
                                    <xsl:with-param name="chopString">
                                        <xsl:call-template name="chopPunctuation">
                                            <xsl:with-param name="chopString" select="."/>
                                        </xsl:call-template>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </mods:publisher>
                        </xsl:if>

                    </xsl:for-each>

                    <xsl:for-each select="marc:subfield[@code='c']">
                        <mods:dateOther type="distribution">
                            <xsl:call-template name="chopPunctuationBoth">
                                <xsl:with-param name="chopString">
                                    <xsl:call-template name="chopPunctuation">
                                        <xsl:with-param name="chopString" select="."/>
                                    </xsl:call-template>
                                </xsl:with-param>
                            </xsl:call-template>
                        </mods:dateOther>
                    </xsl:for-each>

                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=044]/marc:subfield[@code='c']">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">iso3166</xsl:attribute>
                            <xsl:value-of select="."/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='m']">
                    <mods:dateValid point="start">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='n']">
                    <mods:dateValid point="end">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='j']">
                    <mods:dateModified>
                        <xsl:value-of select="."/>
                    </mods:dateModified>
                </xsl:for-each>

                <!-- tmee 1.52 -->

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='c']">
                    <mods:dateIssued encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='e']">
                    <mods:dateIssued encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='k']">
                    <mods:dateCreated encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='l']">
                    <mods:dateCreated encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>

                <xsl:variable name="controlField008-7-10"
                              select="normalize-space(substring($controlField008, 8, 4))"/>
                <xsl:variable name="controlField008-11-14"
                              select="normalize-space(substring($controlField008, 12, 4))"/>
                <xsl:variable name="controlField008-6"
                              select="normalize-space(substring($controlField008, 7, 1))"/>

                <!-- tmee 1.35 and 1.36 -->

                <xsl:if test="$controlField008-6='t'">
                    <xsl:if test="$controlField008-11-14">
                        <mods:copyrightDate encoding="marc">
                            <xsl:value-of select="$controlField008-11-14"/>
                        </mods:copyrightDate>
                    </xsl:if>
                </xsl:if>
                <xsl:for-each
                        select="marc:datafield[@tag=033][@ind1=0 or @ind1=1]/marc:subfield[@code='a']">
                    <mods:dateCaptured encoding="iso8601">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][1]">
                    <mods:dateCaptured encoding="iso8601" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][2]">
                    <mods:dateCaptured encoding="iso8601" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <!--	1.67 1.72	-->
            </mods:originInfo>
        </xsl:if>

        <xsl:if test="marc:datafield[@tag=264][@ind2=3]">
            <mods:originInfo eventType="manufacture">
                <xsl:call-template name="scriptCode"/>
                <xsl:for-each
                        select="marc:datafield[(@tag=260 or @tag=250) and marc:subfield[@code='a' or code='b' or @code='c' or code='g']]">
                    <xsl:call-template name="z2xx880"/>
                </xsl:for-each>

                <xsl:variable name="MARCpublicationCode"
                              select="normalize-space(substring($controlField008,16,3))"/>
                <xsl:if test="translate($MARCpublicationCode,'|','')">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">marccountry</xsl:attribute>
                            <xsl:value-of select="$MARCpublicationCode"/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:if>

                <xsl:for-each select="marc:datafield[@tag=264 and @ind2=3]">

                    <xsl:for-each select="marc:subfield[@code='a' or @code='b']">

                        <xsl:if test="@code='a'">
                            <mods:place>
                                <mods:placeTerm type="text">
                                    <xsl:call-template name="chopPunctuationBoth">
                                        <xsl:with-param name="chopString">
                                            <xsl:call-template name="chopPunctuation">
                                                <xsl:with-param name="chopString" select="."/>
                                            </xsl:call-template>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </mods:placeTerm>
                            </mods:place>
                        </xsl:if>

                        <xsl:if test="@code='b'">
                            <mods:publisher>
                                <xsl:call-template name="chopPunctuationBoth">
                                    <xsl:with-param name="chopString">
                                        <xsl:call-template name="chopPunctuation">
                                            <xsl:with-param name="chopString" select="."/>
                                        </xsl:call-template>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </mods:publisher>
                        </xsl:if>

                    </xsl:for-each>

                    <xsl:for-each select="marc:subfield[@code='c']">
                        <mods:dateOther type="manufacture">
                            <xsl:call-template name="chopPunctuationBoth">
                                <xsl:with-param name="chopString">
                                    <xsl:call-template name="chopPunctuation">
                                        <xsl:with-param name="chopString" select="."/>
                                    </xsl:call-template>
                                </xsl:with-param>
                            </xsl:call-template>
                        </mods:dateOther>
                    </xsl:for-each>

                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=044]/marc:subfield[@code='c']">
                    <mods:place>
                        <mods:placeTerm>
                            <xsl:attribute name="type">code</xsl:attribute>
                            <xsl:attribute name="authority">iso3166</xsl:attribute>
                            <xsl:value-of select="."/>
                        </mods:placeTerm>
                    </mods:place>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='m']">
                    <mods:dateValid point="start">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='n']">
                    <mods:dateValid point="end">
                        <xsl:value-of select="."/>
                    </mods:dateValid>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='j']">
                    <mods:dateModified>
                        <xsl:value-of select="."/>
                    </mods:dateModified>
                </xsl:for-each>

                <!-- tmee 1.52 -->

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='c']">
                    <mods:dateIssued encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='e']">
                    <mods:dateIssued encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateIssued>
                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='k']">
                    <mods:dateCreated encoding="marc" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=046]/marc:subfield[@code='l']">
                    <mods:dateCreated encoding="marc" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCreated>
                </xsl:for-each>

                <xsl:variable name="controlField008-7-10"
                              select="normalize-space(substring($controlField008, 8, 4))"/>
                <xsl:variable name="controlField008-11-14"
                              select="normalize-space(substring($controlField008, 12, 4))"/>
                <xsl:variable name="controlField008-6"
                              select="normalize-space(substring($controlField008, 7, 1))"/>

                <!-- tmee 1.35 and 1.36 -->

                <xsl:if test="$controlField008-6='t'">
                    <xsl:if test="$controlField008-11-14">
                        <mods:copyrightDate encoding="marc">
                            <xsl:value-of select="$controlField008-11-14"/>
                        </mods:copyrightDate>
                    </xsl:if>
                </xsl:if>
                <xsl:for-each
                        select="marc:datafield[@tag=033][@ind1=0 or @ind1=1]/marc:subfield[@code='a']">
                    <mods:dateCaptured encoding="iso8601">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][1]">
                    <mods:dateCaptured encoding="iso8601" point="start">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <xsl:for-each select="marc:datafield[@tag=033][@ind1=2]/marc:subfield[@code='a'][2]">
                    <mods:dateCaptured encoding="iso8601" point="end">
                        <xsl:value-of select="."/>
                    </mods:dateCaptured>
                </xsl:for-each>
                <!--	1.67 1.72	-->
            </mods:originInfo>
        </xsl:if>

        <xsl:if test="marc:datafield[@tag=264][@ind2=4]">
            <mods:originInfo eventType="copyright">
                <xsl:for-each select="marc:datafield[@tag=264 and @ind2=4 and marc:subfield[@code='c']]">
                    <xsl:if test="marc:subfield[@code='c']">
                        <mods:copyrightDate>
                            <xsl:value-of select="marc:subfield[@code='c']"/>
                        </mods:copyrightDate>
                    </xsl:if>
                </xsl:for-each>
            </mods:originInfo>
        </xsl:if>

        <xsl:for-each select="marc:datafield[@tag=880]">
            <xsl:variable name="related_datafield"
                          select="substring-before(marc:subfield[@code='6'],'-')"/>
            <xsl:variable name="occurence_number"
                          select="substring( substring-after(marc:subfield[@code='6'],'-') , 1 , 2 )"/>
            <xsl:variable name="hit"
                          select="../marc:datafield[@tag=$related_datafield and contains(marc:subfield[@code='6'] , concat('880-' , $occurence_number))]/@tag"/>

            <xsl:choose>
                <xsl:when test="$hit='260'">
                    <mods:originInfo>
                        <xsl:call-template name="scriptCode"/>
                        <xsl:for-each
                                select="../marc:datafield[@tag=260 and marc:subfield[@code='a' or code='b' or @code='c' or code='g']]">
                            <xsl:call-template name="z2xx880"/>
                        </xsl:for-each>
                        <xsl:if test="marc:subfield[@code='a']">
                            <mods:place>
                                <mods:placeTerm type="text">
                                    <xsl:value-of select="marc:subfield[@code='a']"/>
                                </mods:placeTerm>
                            </mods:place>
                        </xsl:if>
                        <xsl:if test="marc:subfield[@code='b']">
                            <mods:publisher>
                                <xsl:value-of select="marc:subfield[@code='b']"/>
                            </mods:publisher>
                        </xsl:if>
                        <xsl:if test="marc:subfield[@code='c']">
                            <mods:dateIssued>
                                <xsl:value-of select="marc:subfield[@code='c']"/>
                            </mods:dateIssued>
                        </xsl:if>
                        <xsl:if test="marc:subfield[@code='g']">
                            <mods:dateCreated>
                                <xsl:value-of select="marc:subfield[@code='g']"/>
                            </mods:dateCreated>
                        </xsl:if>
                        <xsl:for-each
                                select="../marc:datafield[@tag=880]/marc:subfield[@code=6][contains(text(),'250')]">
                            <mods:edition>
                                <xsl:value-of select="following-sibling::marc:subfield"/>
                            </mods:edition>
                        </xsl:for-each>
                    </mods:originInfo>
                </xsl:when>
                <xsl:when test="$hit='300'">
                    <mods:physicalDescription>
                        <xsl:for-each select="../marc:datafield[@tag=300]">
                            <xsl:call-template name="z3xx880"/>
                        </xsl:for-each>
                        <xsl:choose>
                            <xsl:when test="not($leader19='a')"> <!-- Not applicable for multiple monographs -->
                                <mods:extent>
                                    <xsl:for-each select="marc:subfield">
                                        <xsl:if test="@code='a' or @code='3' or @code='b' or @code='c'">
                                            <xsl:value-of select="."/>
                                            <xsl:text> </xsl:text>
                                        </xsl:if>
                                    </xsl:for-each>
                                </mods:extent>
                            </xsl:when>
                        </xsl:choose>
                    </mods:physicalDescription>
                </xsl:when>
            </xsl:choose>
        </xsl:for-each>

        <!-- 041 -->
        <xsl:variable name="controlField008-35-37"
                      select="normalize-space(translate(substring($controlField008,36,3),'|#-',''))"/>
        <xsl:if test="$controlField008-35-37">
            <mods:language>
                <mods:languageTerm authority="iso639-2b" type="code">
                    <xsl:value-of select="substring($controlField008,36,3)"/>
                </mods:languageTerm>
            </mods:language>
        </xsl:if>
        <xsl:for-each select="marc:datafield[@tag=041]">
            <xsl:for-each
                    select="marc:subfield[@code='a' or @code='b' or @code='d' or @code='e' or @code='f' or @code='g' or @code='h']">
                <xsl:variable name="langCodes" select="."/>
                <xsl:choose>
                    <xsl:when test="../marc:subfield[@code='2']='rfc3066'">
                        <!-- not stacked but could be repeated -->
                        <xsl:call-template name="rfcLanguages">
                            <xsl:with-param name="nodeNum">
                                <xsl:value-of select="1"/>
                            </xsl:with-param>
                            <xsl:with-param name="usedLanguages">
                                <xsl:text/>
                            </xsl:with-param>
                            <xsl:with-param name="controlField008-35-37">
                                <xsl:value-of select="$controlField008-35-37"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <!-- iso -->
                        <xsl:variable name="allLanguages">
                            <xsl:copy-of select="$langCodes"/>
                        </xsl:variable>
                        <xsl:variable name="currentLanguage">
                            <xsl:value-of select="substring($allLanguages,1,3)"/>
                        </xsl:variable>
                        <xsl:call-template name="isoLanguage">
                            <xsl:with-param name="currentLanguage">
                                <xsl:value-of select="substring($allLanguages,1,3)"/>
                            </xsl:with-param>
                            <xsl:with-param name="remainingLanguages">
                                <xsl:value-of
                                        select="substring($allLanguages,4,string-length($allLanguages)-3)"
                                        />
                            </xsl:with-param>
                            <xsl:with-param name="usedLanguages">
                                <xsl:if test="$controlField008-35-37">
                                    <xsl:value-of select="$controlField008-35-37"/>
                                </xsl:if>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
        </xsl:for-each>

        <xsl:variable name="physicalDescription">
            <!--3.2 change tmee 007/11 -->
            <xsl:if test="$typeOf008='CF' and marc:controlfield[@tag=007][substring(.,12,1)='a']">
                <mods:digitalOrigin>reformatted digital</mods:digitalOrigin>
            </xsl:if>
            <xsl:if test="$typeOf008='CF' and marc:controlfield[@tag=007][substring(.,12,1)='b']">
                <mods:digitalOrigin>digitized microfilm</mods:digitalOrigin>
            </xsl:if>
            <xsl:if test="$typeOf008='CF' and marc:controlfield[@tag=007][substring(.,12,1)='d']">
                <mods:digitalOrigin>digitized other analog</mods:digitalOrigin>
            </xsl:if>
            <xsl:variable name="controlField008-23" select="substring($controlField008,24,1)"/>
            <xsl:variable name="controlField008-29" select="substring($controlField008,30,1)"/>
            <xsl:variable name="check008-23">
                <xsl:if
                        test="$typeOf008='BK' or $typeOf008='MU' or $typeOf008='SE' or $typeOf008='MM'">
                    <xsl:value-of select="true()"/>
                </xsl:if>
            </xsl:variable>
            <xsl:variable name="check008-29">
                <xsl:if test="$typeOf008='MP' or $typeOf008='VM'">
                    <xsl:value-of select="true()"/>
                </xsl:if>
            </xsl:variable>
            <xsl:choose>
                <xsl:when
                        test="($check008-23 and $controlField008-23='f') or ($check008-29 and $controlField008-29='f')">
                    <mods:form authority="marcform">braille</mods:form>
                </xsl:when>
                <xsl:when
                        test="($controlField008-23=' ' and ($leader6='c' or $leader6='d')) or (($typeOf008='BK' or $typeOf008='SE') and ($controlField008-23=' ' or $controlField008-23='r'))">
                    <mods:form authority="marcform">print</mods:form>
                </xsl:when>
                <xsl:when
                        test="$leader6 = 'm' or ($check008-23 and $controlField008-23='s') or ($check008-29 and $controlField008-29='s')">
                    <mods:form authority="marcform">electronic</mods:form>
                </xsl:when>
                <!-- 1.33 -->
                <xsl:when test="$leader6 = 'o'">
                    <mods:form authority="marcform">kit</mods:form>
                </xsl:when>
                <xsl:when
                        test="($check008-23 and $controlField008-23='b') or ($check008-29 and $controlField008-29='b')">
                    <mods:form authority="marcform">microfiche</mods:form>
                </xsl:when>
                <xsl:when
                        test="($check008-23 and $controlField008-23='a') or ($check008-29 and $controlField008-29='a')">
                    <mods:form authority="marcform">microfilm</mods:form>
                </xsl:when>
            </xsl:choose>

            <!-- 1/04 fix -->
            <xsl:if test="marc:datafield[@tag=130]/marc:subfield[@code='h']">
                <mods:form authority="gmd">
                    <xsl:call-template name="chopBrackets">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="marc:datafield[@tag=130]/marc:subfield[@code='h']"
                                    />
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:form>
            </xsl:if>
            <xsl:if test="marc:datafield[@tag=240]/marc:subfield[@code='h']">
                <mods:form authority="gmd">
                    <xsl:call-template name="chopBrackets">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="marc:datafield[@tag=240]/marc:subfield[@code='h']"
                                    />
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:form>
            </xsl:if>
            <xsl:if test="marc:datafield[@tag=242]/marc:subfield[@code='h']">
                <mods:form authority="gmd">
                    <xsl:call-template name="chopBrackets">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="marc:datafield[@tag=242]/marc:subfield[@code='h']"
                                    />
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:form>
            </xsl:if>
            <xsl:if test="marc:datafield[@tag=245]/marc:subfield[@code='h']">
                <mods:form authority="gmd">
                    <xsl:call-template name="chopBrackets">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="marc:datafield[@tag=245]/marc:subfield[@code='h']"
                                    />
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:form>
            </xsl:if>
            <xsl:if test="marc:datafield[@tag=246]/marc:subfield[@code='h']">
                <mods:form authority="gmd">
                    <xsl:call-template name="chopBrackets">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="marc:datafield[@tag=246]/marc:subfield[@code='h']"
                                    />
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:form>
            </xsl:if>
            <xsl:if test="marc:datafield[@tag=730]/marc:subfield[@code='h']">
                <mods:form authority="gmd">
                    <xsl:call-template name="chopBrackets">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="marc:datafield[@tag=730]/marc:subfield[@code='h']"
                                    />
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:form>
            </xsl:if>
            <xsl:for-each select="marc:datafield[@tag=256]/marc:subfield[@code='a']">
                <mods:form>
                    <xsl:value-of select="."/>
                </mods:form>
            </xsl:for-each>
            <xsl:for-each select="marc:controlfield[@tag=007][substring(text(),1,1)='c']">
                <xsl:choose>
                    <xsl:when test="substring(text(),14,1)='a'">
                        <mods:reformattingQuality>access</mods:reformattingQuality>
                    </xsl:when>
                    <xsl:when test="substring(text(),14,1)='p'">
                        <mods:reformattingQuality>preservation</mods:reformattingQuality>
                    </xsl:when>
                    <xsl:when test="substring(text(),14,1)='r'">
                        <mods:reformattingQuality>replacement</mods:reformattingQuality>
                    </xsl:when>
                </xsl:choose>
            </xsl:for-each>
            <!--3.2 change tmee 007/01 -->
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='c'][substring(text(),2,1)='b']">
                <mods:form authority="marccategory">electronic resource</mods:form>
                <mods:form authority="marcsmd">chip cartridge</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='c'][substring(text(),2,1)='c']">
                <mods:form authority="marccategory">electronic resource</mods:form>
                <mods:form authority="marcsmd">computer optical disc cartridge</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='c'][substring(text(),2,1)='j']">
                <mods:form authority="marccategory">electronic resource</mods:form>
                <mods:form authority="marcsmd">magnetic disc</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='c'][substring(text(),2,1)='m']">
                <mods:form authority="marccategory">electronic resource</mods:form>
                <mods:form authority="marcsmd">magneto-optical disc</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='c'][substring(text(),2,1)='o']">
                <mods:form authority="marccategory">electronic resource</mods:form>
                <mods:form authority="marcsmd">optical disc</mods:form>
            </xsl:if>

            <!-- 1.38 AQ 1.29 tmee 	1.66 added marccategory and marcsmd as part of 3.4 -->
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='c'][substring(text(),2,1)='r']">
                <mods:form authority="marccategory">electronic resource</mods:form>
                <mods:form authority="marcsmd">remote</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='c'][substring(text(),2,1)='a']">
                <mods:form authority="marccategory">electronic resource</mods:form>
                <mods:form authority="marcsmd">tape cartridge</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='c'][substring(text(),2,1)='f']">
                <mods:form authority="marccategory">electronic resource</mods:form>
                <mods:form authority="marcsmd">tape cassette</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='c'][substring(text(),2,1)='h']">
                <mods:form authority="marccategory">electronic resource</mods:form>
                <mods:form authority="marcsmd">tape reel</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='d'][substring(text(),2,1)='a']">
                <mods:form authority="marccategory">globe</mods:form>
                <mods:form authority="marcsmd">celestial globe</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='d'][substring(text(),2,1)='e']">
                <mods:form authority="marccategory">globe</mods:form>
                <mods:form authority="marcsmd">earth moon globe</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='d'][substring(text(),2,1)='b']">
                <mods:form authority="marccategory">globe</mods:form>
                <mods:form authority="marcsmd">planetary or lunar globe</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='d'][substring(text(),2,1)='c']">
                <mods:form authority="marccategory">globe</mods:form>
                <mods:form authority="marcsmd">terrestrial globe</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='o'][substring(text(),2,1)='o']">
                <mods:form authority="marccategory">kit</mods:form>
                <mods:form authority="marcsmd">kit</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='d']">
                <mods:form authority="marccategory">map</mods:form>
                <mods:form authority="marcsmd">atlas</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='g']">
                <mods:form authority="marccategory">map</mods:form>
                <mods:form authority="marcsmd">diagram</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='j']">
                <mods:form authority="marccategory">map</mods:form>
                <mods:form authority="marcsmd">map</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='q']">
                <mods:form authority="marccategory">map</mods:form>
                <mods:form authority="marcsmd">model</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='k']">
                <mods:form authority="marccategory">map</mods:form>
                <mods:form authority="marcsmd">profile</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='r']">
                <mods:form authority="marcsmd">remote-sensing image</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='s']">
                <mods:form authority="marccategory">map</mods:form>
                <mods:form authority="marcsmd">section</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='a'][substring(text(),2,1)='y']">
                <mods:form authority="marccategory">map</mods:form>
                <mods:form authority="marcsmd">view</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='h'][substring(text(),2,1)='a']">
                <mods:form authority="marccategory">microform</mods:form>
                <mods:form authority="marcsmd">aperture card</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='h'][substring(text(),2,1)='e']">
                <mods:form authority="marccategory">microform</mods:form>
                <mods:form authority="marcsmd">microfiche</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='h'][substring(text(),2,1)='f']">
                <mods:form authority="marccategory">microform</mods:form>
                <mods:form authority="marcsmd">microfiche cassette</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='h'][substring(text(),2,1)='b']">
                <mods:form authority="marccategory">microform</mods:form>
                <mods:form authority="marcsmd">microfilm cartridge</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='h'][substring(text(),2,1)='c']">
                <mods:form authority="marccategory">microform</mods:form>
                <mods:form authority="marcsmd">microfilm cassette</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='h'][substring(text(),2,1)='d']">
                <mods:form authority="marccategory">microform</mods:form>
                <mods:form authority="marcsmd">microfilm reel</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='h'][substring(text(),2,1)='g']">
                <mods:form authority="marccategory">microform</mods:form>
                <mods:form authority="marcsmd">microopaque</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='m'][substring(text(),2,1)='c']">
                <mods:form authority="marccategory">motion picture</mods:form>
                <mods:form authority="marcsmd">film cartridge</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='m'][substring(text(),2,1)='f']">
                <mods:form authority="marccategory">motion picture</mods:form>
                <mods:form authority="marcsmd">film cassette</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='m'][substring(text(),2,1)='r']">
                <mods:form authority="marccategory">motion picture</mods:form>
                <mods:form authority="marcsmd">film reel</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='n']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">chart</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='c']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">collage</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='d']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">drawing</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='o']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">flash card</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='e']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">painting</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='f']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">photomechanical print</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='g']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">photonegative</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='h']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">photoprint</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='i']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">picture</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='j']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">print</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='k'][substring(text(),2,1)='l']">
                <mods:form authority="marccategory">nonprojected graphic</mods:form>
                <mods:form authority="marcsmd">technical drawing</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='q'][substring(text(),2,1)='q']">
                <mods:form authority="marccategory">notated music</mods:form>
                <mods:form authority="marcsmd">notated music</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='g'][substring(text(),2,1)='d']">
                <mods:form authority="marccategory">projected graphic</mods:form>
                <mods:form authority="marcsmd">filmslip</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='g'][substring(text(),2,1)='c']">
                <mods:form authority="marccategory">projected graphic</mods:form>
                <mods:form authority="marcsmd">filmstrip cartridge</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='g'][substring(text(),2,1)='o']">
                <mods:form authority="marccategory">projected graphic</mods:form>
                <mods:form authority="marcsmd">filmstrip roll</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='g'][substring(text(),2,1)='f']">
                <mods:form authority="marccategory">projected graphic</mods:form>
                <mods:form authority="marcsmd">other filmstrip type</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='g'][substring(text(),2,1)='s']">
                <mods:form authority="marccategory">projected graphic</mods:form>
                <mods:form authority="marcsmd">slide</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='g'][substring(text(),2,1)='t']">
                <mods:form authority="marccategory">projected graphic</mods:form>
                <mods:form authority="marcsmd">transparency</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='r'][substring(text(),2,1)='r']">
                <mods:form authority="marccategory">remote-sensing image</mods:form>
                <mods:form authority="marcsmd">remote-sensing image</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='s'][substring(text(),2,1)='e']">
                <mods:form authority="marccategory">sound recording</mods:form>
                <mods:form authority="marcsmd">cylinder</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='s'][substring(text(),2,1)='q']">
                <mods:form authority="marccategory">sound recording</mods:form>
                <mods:form authority="marcsmd">roll</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='s'][substring(text(),2,1)='g']">
                <mods:form authority="marccategory">sound recording</mods:form>
                <mods:form authority="marcsmd">sound cartridge</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='s'][substring(text(),2,1)='s']">
                <mods:form authority="marccategory">sound recording</mods:form>
                <mods:form authority="marcsmd">sound cassette</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='s'][substring(text(),2,1)='d']">
                <mods:form authority="marccategory">sound recording</mods:form>
                <mods:form authority="marcsmd">sound disc</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='s'][substring(text(),2,1)='t']">
                <mods:form authority="marccategory">sound recording</mods:form>
                <mods:form authority="marcsmd">sound-tape reel</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='s'][substring(text(),2,1)='i']">
                <mods:form authority="marccategory">sound recording</mods:form>
                <mods:form authority="marcsmd">sound-track film</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='s'][substring(text(),2,1)='w']">
                <mods:form authority="marccategory">sound recording</mods:form>
                <mods:form authority="marcsmd">wire recording</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='f'][substring(text(),2,1)='c']">
                <mods:form authority="marccategory">tactile material</mods:form>
                <mods:form authority="marcsmd">braille</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='f'][substring(text(),2,1)='b']">
                <mods:form authority="marccategory">tactile material</mods:form>
                <mods:form authority="marcsmd">combination</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='f'][substring(text(),2,1)='a']">
                <mods:form authority="marccategory">tactile material</mods:form>
                <mods:form authority="marcsmd">moon</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='f'][substring(text(),2,1)='d']">
                <mods:form authority="marccategory">tactile material</mods:form>
                <mods:form authority="marcsmd">tactile, with no writing system</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='t'][substring(text(),2,1)='c']">
                <mods:form authority="marccategory">text</mods:form>
                <mods:form authority="marcsmd">braille</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='t'][substring(text(),2,1)='b']">
                <mods:form authority="marccategory">text</mods:form>
                <mods:form authority="marcsmd">large print</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='t'][substring(text(),2,1)='a']">
                <mods:form authority="marccategory">text</mods:form>
                <mods:form authority="marcsmd">regular print</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='t'][substring(text(),2,1)='d']">
                <mods:form authority="marccategory">text</mods:form>
                <mods:form authority="marcsmd">text in looseleaf binder</mods:form>
            </xsl:if>

            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='v'][substring(text(),2,1)='c']">
                <mods:form authority="marccategory">videorecording</mods:form>
                <mods:form authority="marcsmd">videocartridge</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='v'][substring(text(),2,1)='f']">
                <mods:form authority="marccategory">videorecording</mods:form>
                <mods:form authority="marcsmd">videocassette</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='v'][substring(text(),2,1)='d']">
                <mods:form authority="marccategory">videorecording</mods:form>
                <mods:form authority="marcsmd">videodisc</mods:form>
            </xsl:if>
            <xsl:if
                    test="marc:controlfield[@tag=007][substring(text(),1,1)='v'][substring(text(),2,1)='r']">
                <mods:form authority="marccategory">videorecording</mods:form>
                <mods:form authority="marcsmd">videoreel</mods:form>
            </xsl:if>

            <xsl:for-each
                    select="marc:datafield[@tag=856]/marc:subfield[@code='q'][string-length(.)&gt;1]">
                <mods:internetMediaType>
                    <xsl:value-of select="."/>
                </mods:internetMediaType>
            </xsl:for-each>
            <xsl:for-each select="marc:datafield[@tag=300]">
                <xsl:choose>
                    <xsl:when test="not($leader19='a')"> <!-- Not applicable for multiple monographs -->
                        <mods:extent>
                            <xsl:call-template name="subfieldSelect">
                                <xsl:with-param name="codes">abce3fg</xsl:with-param>
                            </xsl:call-template>
                        </mods:extent>
                    </xsl:when>
                </xsl:choose>
            </xsl:for-each>
            <!-- 1.43 tmee 351 3abc-->
            <xsl:for-each select="marc:datafield[@tag=351]">
                <mods:note type="arrangement">
                    <xsl:for-each select="marc:subfield[@code='3']">
                        <xsl:value-of select="."/>
                        <xsl:text>: </xsl:text>
                    </xsl:for-each>
                    <xsl:call-template name="subfieldSelect">
                        <xsl:with-param name="codes">abc</xsl:with-param>
                    </xsl:call-template>
                </mods:note>
            </xsl:for-each>
        </xsl:variable>

        <xsl:if test="string-length(normalize-space($physicalDescription))">
            <mods:physicalDescription>

                <xsl:if test="marc:datafield[@tag=338]/marc:subfield[@code='a']">
                    <mods:form authority="rdacarrier" type="carrier">
                        <xsl:value-of select="marc:datafield[@tag=338]/marc:subfield[@code='a']" />
                    </mods:form>
                </xsl:if>

                <xsl:if test="marc:datafield[@tag=337]/marc:subfield[@code='a']">
                    <mods:form authority="rdamedia" type="media">
                        <xsl:value-of select="marc:datafield[@tag=337]/marc:subfield[@code='a']" />
                    </mods:form>
                </xsl:if>

                <xsl:for-each select="marc:datafield[@tag=300]">
                    <xsl:call-template name="z3xx880"/>
                </xsl:for-each>
                <xsl:copy-of select="$physicalDescription"/>
            </mods:physicalDescription>
        </xsl:if>

        <xsl:for-each select="marc:datafield[@tag=520]">
            <xsl:call-template name="createAbstractFrom520"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=505]">
            <xsl:call-template name="createTOCFrom505"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=521]">
            <xsl:call-template name="createTargetAudienceFrom521"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=506]">
            <xsl:call-template name="createAccessConditionFrom506"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=540]">
            <xsl:call-template name="createAccessConditionFrom540"/>
        </xsl:for-each>


        <xsl:if test="$typeOf008='BK' or $typeOf008='CF' or $typeOf008='MU' or $typeOf008='VM'">
            <xsl:variable name="controlField008-22" select="substring($controlField008,23,1)"/>
            <xsl:choose>
                <!-- 01/04 fix -->
                <xsl:when test="$controlField008-22='d'">
                    <mods:targetAudience authority="marctarget">adolescent</mods:targetAudience>
                </xsl:when>
                <xsl:when test="$controlField008-22='e'">
                    <mods:targetAudience authority="marctarget">adult</mods:targetAudience>
                </xsl:when>
                <xsl:when test="$controlField008-22='g'">
                    <mods:targetAudience authority="marctarget">general</mods:targetAudience>
                </xsl:when>
                <xsl:when
                        test="$controlField008-22='b' or $controlField008-22='c' or $controlField008-22='j'">
                    <mods:targetAudience authority="marctarget">juvenile</mods:targetAudience>
                </xsl:when>
                <xsl:when test="$controlField008-22='a'">
                    <mods:targetAudience authority="marctarget">preschool</mods:targetAudience>
                </xsl:when>
                <xsl:when test="$controlField008-22='f'">
                    <mods:targetAudience authority="marctarget">specialized</mods:targetAudience>
                </xsl:when>
            </xsl:choose>
        </xsl:if>

        <!-- 1.32 tmee Drop note mapping for 510 and map only to <mods:relatedItem>
		<xsl:for-each select="marc:datafield[@tag=510]">
			<mods:note type="citation/reference">
				<xsl:call-template name="uri"/>
				<xsl:variable name="str">
					<xsl:for-each select="marc:subfield[@code!='6' or @code!='8']">
						<xsl:value-of select="."/>
						<xsl:text> </xsl:text>
					</xsl:for-each>
				</xsl:variable>
				<xsl:value-of select="substring($str,1,string-length($str)-1)"/>
			</mods:note>
		</xsl:for-each>
		-->

        <!-- 245c 362az 502-585 5XX-->

        <xsl:for-each select="marc:datafield[@tag=245]">
            <xsl:call-template name="createNoteFrom245c"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=362]">
            <xsl:call-template name="createNoteFrom362"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=500]">
            <xsl:call-template name="createNoteFrom500"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=502]">
            <xsl:call-template name="createNoteFrom502"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=504]">
            <xsl:call-template name="createNoteFrom504"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=508]">
            <xsl:call-template name="createNoteFrom508"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=511]">
            <xsl:call-template name="createNoteFrom511"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=515]">
            <xsl:call-template name="createNoteFrom515"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=518]">
            <xsl:call-template name="createNoteFrom518"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=524]">
            <xsl:call-template name="createNoteFrom524"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=530]">
            <xsl:call-template name="createNoteFrom530"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=533]">
            <xsl:call-template name="createNoteFrom533"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=534]">
            <xsl:call-template name="createNoteFrom534"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=535]">
            <xsl:call-template name="createNoteFrom535"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=536]">
            <xsl:call-template name="createNoteFrom536"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=538]">
            <xsl:call-template name="createNoteFrom538"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=541]">
            <xsl:call-template name="createNoteFrom541"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=545]">
            <xsl:call-template name="createNoteFrom545"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=546]">
            <xsl:call-template name="createNoteFrom546"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=561]">
            <xsl:call-template name="createNoteFrom561"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=562]">
            <xsl:call-template name="createNoteFrom562"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=581]">
            <xsl:call-template name="createNoteFrom581"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=583]">
            <xsl:call-template name="createNoteFrom583"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=585]">
            <xsl:call-template name="createNoteFrom585"/>
        </xsl:for-each>

        <xsl:for-each
                select="marc:datafield[@tag=501 or @tag=507 or @tag=513 or @tag=514 or @tag=516 or @tag=522 or @tag=525 or @tag=526 or @tag=544 or @tag=547 or @tag=550 or @tag=552 or @tag=555 or @tag=556 or @tag=565 or @tag=567 or @tag=580 or @tag=584 or @tag=586]">
            <xsl:call-template name="createNoteFrom5XX"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=034]">
            <xsl:call-template name="createSubGeoFrom034"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=043]">
            <xsl:call-template name="createSubGeoFrom043"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=045]">
            <xsl:call-template name="createSubTemFrom045"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=255]">
            <xsl:call-template name="createSubGeoFrom255"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=600]">
            <xsl:call-template name="createSubNameFrom600"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=610]">
            <xsl:call-template name="createSubNameFrom610"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=611]">
            <xsl:call-template name="createSubNameFrom611"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=630]">
            <xsl:call-template name="createSubTitleFrom630"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=648]">
            <xsl:call-template name="createSubChronFrom648"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=650]">
            <xsl:call-template name="createSubTopFrom650"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=651]">
            <xsl:call-template name="createSubGeoFrom651"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=653]">
            <xsl:call-template name="createSubFrom653"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=656]">
            <xsl:call-template name="createSubFrom656"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=662]">
            <xsl:call-template name="createSubGeoFrom662752"/>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=752]">
            <xsl:call-template name="createSubGeoFrom662752"/>
        </xsl:for-each>

        <!-- createClassificationFrom 0XX-->
        <xsl:for-each select="marc:datafield[@tag='050']">
            <xsl:call-template name="createClassificationFrom050"/>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='060']">
            <xsl:call-template name="createClassificationFrom060"/>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='080']">
            <xsl:call-template name="createClassificationFrom080"/>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='082']">
            <xsl:call-template name="createClassificationFrom082"/>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='084']">
            <xsl:call-template name="createClassificationFrom084"/>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='086']">
            <xsl:call-template name="createClassificationFrom086"/>
        </xsl:for-each>

        <!--	location	-->
        <xsl:if test="marc:datafield[@tag=852] or marc:datafield[@tag=856] or marc:datafield[@tag=910] or marc:datafield[@tag=440]">
            <mods:location>
                <xsl:for-each select="marc:datafield[@tag=852]">
                    <xsl:call-template name="createLocationFrom852"/>
                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=910]">
                    <xsl:call-template name="createLocationFrom910" />
                </xsl:for-each>

                <xsl:for-each select="marc:datafield[@tag=856]">
                    <xsl:call-template name="createLocationFrom856"/>
                </xsl:for-each>


            </mods:location>
        </xsl:if>

        <xsl:for-each select="marc:datafield[@tag=490][@ind1=0]">
            <xsl:call-template name="createRelatedItemFrom490"/>
        </xsl:for-each>


        <xsl:for-each select="marc:datafield[@tag=440]">
            <mods:relatedItem type="series">
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="subfieldSelect">
                                    <xsl:with-param name="codes">av</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="part"/>
                </mods:titleInfo>
            </mods:relatedItem>
        </xsl:for-each>

        <!-- tmee 1.40 1.74-->

        <xsl:for-each select="marc:datafield[@tag=510]">
            <mods:relatedItem type="isReferencedBy">
                <xsl:for-each select="marc:subfield[@code='a']">
                    <mods:titleInfo>
                        <mods:title>
                            <xsl:value-of select="."/>
                        </mods:title>
                    </mods:titleInfo>
                </xsl:for-each>
                <xsl:for-each select="marc:subfield[@code='b']">
                    <mods:originInfo>
                        <mods:dateOther type="coverage">
                            <xsl:value-of select="."/>
                        </mods:dateOther>
                    </mods:originInfo>
                </xsl:for-each>
                <mods:note>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:call-template name="subfieldSelect">
                                <xsl:with-param name="codes">c</xsl:with-param>
                            </xsl:call-template>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:note>
            </mods:relatedItem>
        </xsl:for-each>


        <xsl:for-each select="marc:datafield[@tag=534]">
            <mods:relatedItem type="original">
                <xsl:call-template name="relatedTitle"/>
                <xsl:call-template name="relatedName"/>
                <xsl:if test="marc:subfield[@code='b' or @code='c']">
                    <mods:originInfo>
                        <xsl:for-each select="marc:subfield[@code='c']">
                            <mods:publisher>
                                <xsl:value-of select="."/>
                            </mods:publisher>
                        </xsl:for-each>
                        <xsl:for-each select="marc:subfield[@code='b']">
                            <mods:edition>
                                <xsl:value-of select="."/>
                            </mods:edition>
                        </xsl:for-each>
                    </mods:originInfo>
                </xsl:if>
                <xsl:call-template name="relatedIdentifierISSN"/>
                <xsl:for-each select="marc:subfield[@code='z']">
                    <mods:identifier type="isbn">
                        <xsl:value-of select="."/>
                    </mods:identifier>
                </xsl:for-each>
                <xsl:call-template name="relatedNote"/>
            </mods:relatedItem>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=700][marc:subfield[@code='t']]">
            <mods:relatedItem>
                <xsl:call-template name="constituentOrRelatedType"/>
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="specialSubfieldSelect">
                                    <xsl:with-param name="anyCodes">tfklmorsv</xsl:with-param>
                                    <xsl:with-param name="axis">t</xsl:with-param>
                                    <xsl:with-param name="afterCodes">g</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="part"/>
                </mods:titleInfo>
                <mods:name type="personal">
                    <mods:namePart>
                        <xsl:call-template name="specialSubfieldSelect">
                            <xsl:with-param name="anyCodes">aq</xsl:with-param>
                            <xsl:with-param name="axis">t</xsl:with-param>
                            <xsl:with-param name="beforeCodes">g</xsl:with-param>
                        </xsl:call-template>
                    </mods:namePart>
                    <xsl:call-template name="termsOfAddress"/>
                    <xsl:call-template name="nameDate"/>
                    <xsl:call-template name="role"/>
                </mods:name>
                <xsl:call-template name="relatedForm"/>
                <xsl:call-template name="relatedIdentifierISSN"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=710][marc:subfield[@code='t']]">
            <mods:relatedItem>
                <xsl:call-template name="constituentOrRelatedType"/>
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="specialSubfieldSelect">
                                    <xsl:with-param name="anyCodes">tfklmorsv</xsl:with-param>
                                    <xsl:with-param name="axis">t</xsl:with-param>
                                    <xsl:with-param name="afterCodes">dg</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="relatedPartNumName"/>
                </mods:titleInfo>
                <mods:name type="corporate">
                    <xsl:for-each select="marc:subfield[@code='a']">
                        <mods:namePart>
                            <xsl:value-of select="."/>
                        </mods:namePart>
                    </xsl:for-each>
                    <xsl:for-each select="marc:subfield[@code='b']">
                        <mods:namePart>
                            <xsl:value-of select="."/>
                        </mods:namePart>
                    </xsl:for-each>
                    <xsl:variable name="tempNamePart">
                        <xsl:call-template name="specialSubfieldSelect">
                            <xsl:with-param name="anyCodes">c</xsl:with-param>
                            <xsl:with-param name="axis">t</xsl:with-param>
                            <xsl:with-param name="beforeCodes">dgn</xsl:with-param>
                        </xsl:call-template>
                    </xsl:variable>
                    <xsl:if test="normalize-space($tempNamePart)">
                        <mods:namePart>
                            <xsl:value-of select="$tempNamePart"/>
                        </mods:namePart>
                    </xsl:if>
                    <xsl:call-template name="role"/>
                </mods:name>
                <xsl:call-template name="relatedForm"/>
                <xsl:call-template name="relatedIdentifierISSN"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=711][marc:subfield[@code='t']]">
            <mods:relatedItem>
                <xsl:call-template name="constituentOrRelatedType"/>
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="specialSubfieldSelect">
                                    <xsl:with-param name="anyCodes">tfklsv</xsl:with-param>
                                    <xsl:with-param name="axis">t</xsl:with-param>
                                    <xsl:with-param name="afterCodes">g</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="relatedPartNumName"/>
                </mods:titleInfo>
                <mods:name type="conference">
                    <mods:namePart>
                        <xsl:call-template name="specialSubfieldSelect">
                            <xsl:with-param name="anyCodes">aqdc</xsl:with-param>
                            <xsl:with-param name="axis">t</xsl:with-param>
                            <xsl:with-param name="beforeCodes">gn</xsl:with-param>
                        </xsl:call-template>
                    </mods:namePart>
                </mods:name>
                <xsl:call-template name="relatedForm"/>
                <xsl:call-template name="relatedIdentifierISSN"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=730][@ind2=2]">
            <mods:relatedItem>
                <xsl:call-template name="constituentOrRelatedType"/>
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="subfieldSelect">
                                    <xsl:with-param name="codes">adfgklmorsv</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="part"/>
                </mods:titleInfo>
                <xsl:call-template name="relatedForm"/>
                <xsl:call-template name="relatedIdentifierISSN"/>
            </mods:relatedItem>
        </xsl:for-each>


        <xsl:for-each select="marc:datafield[@tag=740][@ind2=2]">
            <mods:relatedItem>
                <xsl:call-template name="constituentOrRelatedType"/>
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:value-of select="marc:subfield[@code='a']"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="part"/>
                </mods:titleInfo>
                <xsl:call-template name="relatedForm"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=760]">
            <mods:relatedItem type="series">
                <xsl:call-template name="relatedItem76X-78X"/>
            </mods:relatedItem>
        </xsl:for-each>

        <!--AQ1.23 tmee/dlf -->
        <xsl:for-each select="marc:datafield[@tag=762]">
            <mods:relatedItem type="constituent">
                <xsl:call-template name="relatedItem76X-78X"/>
            </mods:relatedItem>
        </xsl:for-each>

        <!-- AQ1.5, AQ1.7 deleted tags 777 and 787 from the following select for relatedItem mapping -->
        <!-- 1.45 and 1.46 - AQ1.24 and 1.25 tmee-->
        <xsl:for-each
                select="marc:datafield[@tag=765]|marc:datafield[@tag=767]|marc:datafield[@tag=775]">
            <mods:relatedItem type="otherVersion">
                <xsl:call-template name="relatedItem76X-78X"/>
            </mods:relatedItem>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=770]|marc:datafield[@tag=774]">
            <mods:relatedItem type="constituent">
                <xsl:call-template name="relatedItem76X-78X"/>
            </mods:relatedItem>
        </xsl:for-each>


        <xsl:for-each select="marc:datafield[@tag=772]|marc:datafield[@tag=773]">
            <mods:relatedItem type="host">
                <xsl:call-template name="relatedItem76X-78X"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=776]">
            <mods:relatedItem type="otherFormat">
                <xsl:call-template name="relatedItem76X-78X"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=780]">
            <mods:relatedItem type="preceding">
                <xsl:call-template name="relatedItem76X-78X"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=785]">
            <mods:relatedItem type="succeeding">
                <xsl:call-template name="relatedItem76X-78X"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=786]">
            <mods:relatedItem type="original">
                <xsl:call-template name="relatedItem76X-78X"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=800]">
            <mods:relatedItem type="series">
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="specialSubfieldSelect">
                                    <xsl:with-param name="anyCodes">tfklmorsv</xsl:with-param>
                                    <xsl:with-param name="axis">t</xsl:with-param>
                                    <xsl:with-param name="afterCodes">g</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="part"/>
                </mods:titleInfo>
                <mods:name type="personal">
                    <mods:namePart>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="specialSubfieldSelect">
                                    <xsl:with-param name="anyCodes">aq</xsl:with-param>
                                    <xsl:with-param name="axis">t</xsl:with-param>
                                    <xsl:with-param name="beforeCodes">g</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:namePart>
                    <xsl:call-template name="termsOfAddress"/>
                    <xsl:call-template name="nameDate"/>
                    <xsl:call-template name="role"/>
                </mods:name>
                <xsl:call-template name="relatedForm"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=810]">
            <mods:relatedItem type="series">
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="specialSubfieldSelect">
                                    <xsl:with-param name="anyCodes">tfklmorsv</xsl:with-param>
                                    <xsl:with-param name="axis">t</xsl:with-param>
                                    <xsl:with-param name="afterCodes">dg</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="relatedPartNumName"/>
                </mods:titleInfo>
                <mods:name type="corporate">
                    <xsl:for-each select="marc:subfield[@code='a']">
                        <mods:namePart>
                            <xsl:value-of select="."/>
                        </mods:namePart>
                    </xsl:for-each>
                    <xsl:for-each select="marc:subfield[@code='b']">
                        <mods:namePart>
                            <xsl:value-of select="."/>
                        </mods:namePart>
                    </xsl:for-each>
                    <mods:namePart>
                        <xsl:call-template name="specialSubfieldSelect">
                            <xsl:with-param name="anyCodes">c</xsl:with-param>
                            <xsl:with-param name="axis">t</xsl:with-param>
                            <xsl:with-param name="beforeCodes">dgn</xsl:with-param>
                        </xsl:call-template>
                    </mods:namePart>
                    <xsl:call-template name="role"/>
                </mods:name>
                <xsl:call-template name="relatedForm"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=811]">
            <mods:relatedItem type="series">
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="specialSubfieldSelect">
                                    <xsl:with-param name="anyCodes">tfklsv</xsl:with-param>
                                    <xsl:with-param name="axis">t</xsl:with-param>
                                    <xsl:with-param name="afterCodes">g</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="relatedPartNumName"/>
                </mods:titleInfo>
                <mods:name type="conference">
                    <mods:namePart>
                        <xsl:call-template name="specialSubfieldSelect">
                            <xsl:with-param name="anyCodes">aqdc</xsl:with-param>
                            <xsl:with-param name="axis">t</xsl:with-param>
                            <xsl:with-param name="beforeCodes">gn</xsl:with-param>
                        </xsl:call-template>
                    </mods:namePart>
                    <xsl:call-template name="role"/>
                </mods:name>
                <xsl:call-template name="relatedForm"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='830']">
            <mods:relatedItem type="series">
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="subfieldSelect">
                                    <xsl:with-param name="codes">adfgklmorsv</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="part"/>
                </mods:titleInfo>
                <xsl:call-template name="relatedForm"/>
            </mods:relatedItem>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='856'][@ind2='2']/marc:subfield[@code='q']">
            <mods:relatedItem>
                <mods:internetMediaType>
                    <xsl:value-of select="."/>
                </mods:internetMediaType>
            </mods:relatedItem>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='880']">
            <xsl:apply-templates select="self::*" mode="trans880"/>
        </xsl:for-each>


        <!-- 015, 856, 020, 024, 022, 028, 010, 035, 037 -->

        <xsl:for-each select="marc:datafield[@tag='015']">
            <xsl:if test="marc:subfield[@code='a']">
                <mods:identifier type="ccnb">
                    <xsl:value-of select="marc:subfield[@code='a']"/>
                </mods:identifier>
            </xsl:if>
            <xsl:for-each select="marc:subfield[@code='z']">
                <mods:identifier type="ccnb" invalid="yes">
                    <xsl:value-of select="."/>
                </mods:identifier>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='020']">
            <xsl:if test="marc:subfield[@code='a'] and not(starts-with(marc:subfield[@code='a'],'('))">
                <mods:identifier type="isbn">
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString" select="marc:subfield[@code='a']"/>
                    </xsl:call-template>
                </mods:identifier>
            </xsl:if>
            <xsl:for-each select="marc:subfield[@code='z']">
                <xsl:if test="not(starts-with(.,'('))">
                    <mods:identifier type="isbn" invalid="yes">
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString" select="."/>
                        </xsl:call-template>
                    </mods:identifier>
                </xsl:if>
            </xsl:for-each>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='024'][@ind1='0']">
            <xsl:if test="marc:subfield[@code='a']">
                <mods:identifier type="isrc">
                    <xsl:value-of select="marc:subfield[@code='a']"/>
                </mods:identifier>
            </xsl:if>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='024'][@ind1='2']">
            <xsl:if test="marc:subfield[@code='a'] and not(starts-with(marc:subfield[@code='a'],'('))">
                <mods:identifier type="ismn">
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString" select="marc:subfield[@code='a']"/>
                    </xsl:call-template>
                </mods:identifier>
            </xsl:if>
            <xsl:for-each select="marc:subfield[@code='z']">
                <xsl:if test="not(starts-with(.,'('))">
                    <mods:identifier type="ismn" invalid="yes">
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString" select="."/>
                        </xsl:call-template>
                    </mods:identifier>
                </xsl:if>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='024'][@ind1='4']">
            <mods:identifier type="sici">
                <xsl:call-template name="subfieldSelect">
                    <xsl:with-param name="codes">ab</xsl:with-param>
                </xsl:call-template>
            </mods:identifier>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='024'][@ind1='8']">
            <mods:identifier>
                <xsl:value-of select="marc:subfield[@code='a']"/>
            </mods:identifier>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='022']">
            <xsl:if test="marc:subfield[@code='a']">
                <mods:identifier type="issn">
                    <xsl:value-of select="marc:subfield[@code='a']"/>
                </mods:identifier>
            </xsl:if>
            <xsl:for-each select="marc:subfield[@code='z']">
                <mods:identifier type="issn" invalid="yes">
                    <xsl:value-of select="."/>
                </mods:identifier>
            </xsl:for-each>
            <xsl:for-each select="marc:subfield[@code='y']">
                <mods:identifier type="issn" invalid="yes">
                    <xsl:value-of select="."/>
                </mods:identifier>
            </xsl:for-each>
            <xsl:for-each select="marc:subfield[@code='l']">
                <mods:identifier type="issn-l">
                    <xsl:value-of select="."/>
                </mods:identifier>
            </xsl:for-each>
            <xsl:for-each select="marc:subfield[@code='m']">
                <mods:identifier type="issn-l" invalid="yes">
                    <xsl:value-of select="."/>
                </mods:identifier>
            </xsl:for-each>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='010'][marc:subfield[@code='a']]">
            <mods:identifier type="lccn">
                <xsl:value-of select="normalize-space(marc:subfield[@code='a'])"/>
            </mods:identifier>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag='010'][marc:subfield[@code='z']]">
            <mods:identifier type="lccn" invalid="yes">
                <xsl:value-of select="normalize-space(marc:subfield[@code='z'])"/>
            </mods:identifier>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='028']">
            <mods:identifier>
                <xsl:attribute name="type">
                    <xsl:choose>
                        <xsl:when test="@ind1='0'">issue number</xsl:when>
                        <xsl:when test="@ind1='1'">matrix number</xsl:when>
                        <xsl:when test="@ind1='2'">music plate</xsl:when>
                        <xsl:when test="@ind1='3'">music publisher</xsl:when>
                        <xsl:when test="@ind1='4'">videorecording identifier</xsl:when>
                        <xsl:when test="@ind1='5'">other publisher identifier</xsl:when>
                    </xsl:choose>
                </xsl:attribute>
                <xsl:call-template name="subfieldSelect">
                    <xsl:with-param name="codes">
                        <xsl:choose>
                            <xsl:when test="@ind1='0'">ba</xsl:when>
                            <xsl:otherwise>ab</xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:identifier>
        </xsl:for-each>

        <xsl:for-each
                select="marc:datafield[@tag='035'][marc:subfield[@code='a'][contains(text(), '(OCoLC)')]]">
            <mods:identifier type="oclc">
                <xsl:value-of
                        select="normalize-space(substring-after(marc:subfield[@code='a'], '(OCoLC)'))"/>
            </mods:identifier>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag='037']">
            <mods:identifier type="stock number">
                <xsl:if test="marc:subfield[@code='c']">
                    <xsl:attribute name="displayLabel">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">c</xsl:with-param>
                        </xsl:call-template>
                    </xsl:attribute>
                </xsl:if>
                <xsl:call-template name="subfieldSelect">
                    <xsl:with-param name="codes">ab</xsl:with-param>
                </xsl:call-template>
            </mods:identifier>
        </xsl:for-each>


        <!-- 1.51 tmee 20100129-->
        <xsl:for-each select="marc:datafield[@tag='856'][marc:subfield[@code='u']]">
            <xsl:if
                    test="starts-with(marc:subfield[@code='u'],'urn:hdl') or starts-with(marc:subfield[@code='u'],'hdl') or starts-with(marc:subfield[@code='u'],'http://hdl.loc.gov') ">
                <mods:identifier>
                    <xsl:attribute name="type">
                        <xsl:if
                                test="starts-with(marc:subfield[@code='u'],'urn:doi') or starts-with(marc:subfield[@code='u'],'doi')"
                                >doi</xsl:if>
                        <xsl:if
                                test="starts-with(marc:subfield[@code='u'],'urn:hdl') or starts-with(marc:subfield[@code='u'],'hdl') or starts-with(marc:subfield[@code='u'],'http://hdl.loc.gov')"
                                >hdl</xsl:if>
                    </xsl:attribute>
                    <xsl:value-of
                            select="concat('hdl:',substring-after(marc:subfield[@code='u'],'http://hdl.loc.gov/'))"
                            />
                </mods:identifier>
            </xsl:if>


            <xsl:if
                    test="starts-with(marc:subfield[@code='u'],'urn:hdl') or starts-with(marc:subfield[@code='u'],'hdl')">
                <mods:identifier type="hdl">
                    <xsl:if test="marc:subfield[@code='y' or @code='3' or @code='z']">
                        <xsl:attribute name="displayLabel">
                            <xsl:call-template name="subfieldSelect">
                                <xsl:with-param name="codes">y3z</xsl:with-param>
                            </xsl:call-template>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:value-of
                            select="concat('hdl:',substring-after(marc:subfield[@code='u'],'http://hdl.loc.gov/'))"
                            />
                </mods:identifier>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="marc:datafield[@tag=024][@ind1=1]">
            <mods:identifier type="upc">
                <xsl:value-of select="marc:subfield[@code='a']"/>
            </mods:identifier>
        </xsl:for-each>


        <!-- 1.51 tmee 20100129-->
        <xsl:for-each select="marc:datafield[@tag='856'][marc:subfield[@code='u']]">
            <xsl:if
                    test="starts-with(marc:subfield[@code='u'],'urn:hdl') or starts-with(marc:subfield[@code='u'],'hdl') or starts-with(marc:subfield[@code='u'],'http://hdl.loc.gov') ">
                <mods:identifier>
                    <xsl:attribute name="type">
                        <xsl:if
                                test="starts-with(marc:subfield[@code='u'],'urn:doi') or starts-with(marc:subfield[@code='u'],'doi')"
                                >doi</xsl:if>
                        <xsl:if
                                test="starts-with(marc:subfield[@code='u'],'urn:hdl') or starts-with(marc:subfield[@code='u'],'hdl') or starts-with(marc:subfield[@code='u'],'http://hdl.loc.gov')"
                                >hdl</xsl:if>
                    </xsl:attribute>
                    <xsl:value-of
                            select="concat('hdl:',substring-after(marc:subfield[@code='u'],'http://hdl.loc.gov/'))"
                            />
                </mods:identifier>
            </xsl:if>

            <xsl:if
                    test="starts-with(marc:subfield[@code='u'],'urn:hdl') or starts-with(marc:subfield[@code='u'],'hdl')">
                <mods:identifier type="hdl">
                    <xsl:if test="marc:subfield[@code='y' or @code='3' or @code='z']">
                        <xsl:attribute name="displayLabel">
                            <xsl:call-template name="subfieldSelect">
                                <xsl:with-param name="codes">y3z</xsl:with-param>
                            </xsl:call-template>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:value-of
                            select="concat('hdl:',substring-after(marc:subfield[@code='u'],'http://hdl.loc.gov/'))"
                            />
                </mods:identifier>
            </xsl:if>
        </xsl:for-each>


        <xsl:for-each select="marc:datafield[@tag=856][@ind2=2][marc:subfield[@code='u']]">
            <mods:relatedItem>
                <mods:location>
                    <mods:url>
                        <xsl:if test="marc:subfield[@code='y' or @code='3']">
                            <xsl:attribute name="displayLabel">
                                <xsl:call-template name="subfieldSelect">
                                    <xsl:with-param name="codes">y3</xsl:with-param>
                                </xsl:call-template>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="marc:subfield[@code='z']">
                            <xsl:attribute name="note">
                                <xsl:call-template name="subfieldSelect">
                                    <xsl:with-param name="codes">z</xsl:with-param>
                                </xsl:call-template>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:value-of select="marc:subfield[@code='u']"/>
                    </mods:url>
                </mods:location>
            </mods:relatedItem>
        </xsl:for-each>

        <mods:recordInfo>
            <xsl:for-each select="marc:leader[substring($leader,19,1)='a']">
                <mods:descriptionStandard>aacr</mods:descriptionStandard>
            </xsl:for-each>

            <xsl:for-each select="marc:datafield[@tag=040]">
                <xsl:if test="marc:subfield[@code='e']">
                    <mods:descriptionStandard>
                        <xsl:value-of select="marc:subfield[@code='e']"/>
                    </mods:descriptionStandard>
                </xsl:if>
                <mods:recordContentSource authority="marcorg">
                    <xsl:value-of select="marc:subfield[@code='a']"/>
                </mods:recordContentSource>
            </xsl:for-each>
            <xsl:for-each select="marc:controlfield[@tag=008]">
                <mods:recordCreationDate encoding="marc">
                    <xsl:value-of select="substring(.,1,6)"/>
                </mods:recordCreationDate>
            </xsl:for-each>

            <xsl:for-each select="marc:controlfield[@tag=005]">
                <mods:recordChangeDate encoding="iso8601">
                    <xsl:value-of select="."/>
                </mods:recordChangeDate>
            </xsl:for-each>
            <xsl:for-each select="marc:controlfield[@tag=001]">
                <mods:recordIdentifier>
                    <xsl:if test="../marc:controlfield[@tag=003]">
                        <xsl:attribute name="source">
                            <xsl:value-of select="../marc:controlfield[@tag=003]"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:value-of select="."/>
                </mods:recordIdentifier>
            </xsl:for-each>

            <mods:recordOrigin>machine generated</mods:recordOrigin>

            <xsl:for-each select="marc:datafield[@tag=040]/marc:subfield[@code='b']">
                <mods:languageOfCataloging>
                    <mods:languageTerm authority="iso639-2b" type="code">
                        <xsl:value-of select="."/>
                    </mods:languageTerm>
                </mods:languageOfCataloging>
            </xsl:for-each>
        </mods:recordInfo>
    </xsl:template>

    <xsl:template name="displayForm">
        <xsl:for-each select="marc:subfield[@code='c']">
            <mods:displayForm>
                <xsl:value-of select="."/>
            </mods:displayForm>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="affiliation">
        <xsl:for-each select="marc:subfield[@code='u']">
            <mods:affiliation>
                <xsl:value-of select="."/>
            </mods:affiliation>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="uri">
        <xsl:for-each select="marc:subfield[@code='u']|marc:subfield[@code='0']">
            <xsl:attribute name="xlink:href">
                <xsl:value-of select="."/>
            </xsl:attribute>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="role">
        <!-- Not used in NK/MZK 
		<xsl:for-each select="marc:subfield[@code='e']">
			<mods:role>
				<mods:roleTerm type="text">
					<xsl:value-of select="."/>
				</mods:roleTerm>
			</mods:role>
		</xsl:for-each>
		 -->
        <xsl:for-each select="marc:subfield[@code='4']">
            <mods:role>
                <mods:roleTerm authority="marcrelator" type="code">
                    <xsl:value-of select="."/>
                </mods:roleTerm>
            </mods:role>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="part">
        <xsl:variable name="partNumber">
            <xsl:call-template name="specialSubfieldSelect">
                <xsl:with-param name="axis">n</xsl:with-param>
                <xsl:with-param name="anyCodes">n</xsl:with-param>
                <xsl:with-param name="afterCodes">fgkdlmor</xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="partName">
            <xsl:call-template name="specialSubfieldSelect">
                <xsl:with-param name="axis">p</xsl:with-param>
                <xsl:with-param name="anyCodes">p</xsl:with-param>
                <xsl:with-param name="afterCodes">fgkdlmor</xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:if test="string-length(normalize-space($partNumber))">
            <mods:partNumber>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString" select="$partNumber"/>
                </xsl:call-template>
            </mods:partNumber>
        </xsl:if>
        <xsl:if test="string-length(normalize-space($partName))">
            <mods:partName>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString" select="$partName"/>
                </xsl:call-template>
            </mods:partName>
        </xsl:if>
    </xsl:template>
    <xsl:template name="relatedPart">
        <xsl:if test="@tag=773">
            <xsl:for-each select="marc:subfield[@code='g']">
                <mods:part>
                    <mods:text>
                        <xsl:value-of select="."/>
                    </mods:text>
                </mods:part>
            </xsl:for-each>
            <xsl:for-each select="marc:subfield[@code='q']">
                <mods:part>
                    <xsl:call-template name="parsePart"/>
                </mods:part>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>
    <xsl:template name="relatedPartNumName">
        <xsl:variable name="partNumber">
            <xsl:call-template name="specialSubfieldSelect">
                <xsl:with-param name="axis">g</xsl:with-param>
                <xsl:with-param name="anyCodes">g</xsl:with-param>
                <xsl:with-param name="afterCodes">pst</xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="partName">
            <xsl:call-template name="specialSubfieldSelect">
                <xsl:with-param name="axis">p</xsl:with-param>
                <xsl:with-param name="anyCodes">p</xsl:with-param>
                <xsl:with-param name="afterCodes">fgkdlmor</xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <xsl:if test="string-length(normalize-space($partNumber))">
            <mods:partNumber>
                <xsl:value-of select="$partNumber"/>
            </mods:partNumber>
        </xsl:if>
        <xsl:if test="string-length(normalize-space($partName))">
            <mods:partName>
                <xsl:value-of select="$partName"/>
            </mods:partName>
        </xsl:if>
    </xsl:template>
    <xsl:template name="relatedName">
        <xsl:for-each select="marc:subfield[@code='a']">
            <mods:name>
                <mods:namePart>
                    <xsl:value-of select="."/>
                </mods:namePart>
            </mods:name>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedForm">
        <xsl:for-each select="marc:subfield[@code='h']">
            <mods:physicalDescription>
                <mods:form>
                    <xsl:value-of select="."/>
                </mods:form>
            </mods:physicalDescription>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedExtent">
        <xsl:for-each select="marc:subfield[@code='h']">
            <mods:physicalDescription>
                <mods:extent>
                    <xsl:value-of select="."/>
                </mods:extent>
            </mods:physicalDescription>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedNote">
        <xsl:for-each select="marc:subfield[@code='n']">
            <mods:note>
                <xsl:value-of select="."/>
            </mods:note>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedSubject">
        <xsl:for-each select="marc:subfield[@code='j']">
            <mods:subject>
                <mods:temporal encoding="iso8601">
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString" select="."/>
                    </xsl:call-template>
                </mods:temporal>
            </mods:subject>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedIdentifierISSN">
        <xsl:for-each select="marc:subfield[@code='x']">
            <mods:identifier type="issn">
                <xsl:value-of select="."/>
            </mods:identifier>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedIdentifierLocal">
        <xsl:for-each select="marc:subfield[@code='w']">
            <mods:identifier type="local">
                <xsl:value-of select="."/>
            </mods:identifier>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedIdentifier">
        <xsl:for-each select="marc:subfield[@code='o']">
            <mods:identifier>
                <xsl:value-of select="."/>
            </mods:identifier>
        </xsl:for-each>
    </xsl:template>
    <!--tmee 1.40 510 isReferencedBy -->
    <xsl:template name="relatedItem510">
        <xsl:call-template name="displayLabel"/>
        <xsl:call-template name="relatedTitle76X-78X"/>
        <xsl:call-template name="relatedName"/>
        <xsl:call-template name="relatedOriginInfo510"/>
        <xsl:call-template name="relatedLanguage"/>
        <xsl:call-template name="relatedExtent"/>
        <xsl:call-template name="relatedNote"/>
        <xsl:call-template name="relatedSubject"/>
        <xsl:call-template name="relatedIdentifier"/>
        <xsl:call-template name="relatedIdentifierISSN"/>
        <xsl:call-template name="relatedIdentifierLocal"/>
        <xsl:call-template name="relatedPart"/>
    </xsl:template>
    <xsl:template name="relatedItem76X-78X">
        <xsl:call-template name="displayLabel"/>
        <xsl:call-template name="relatedTitle76X-78X"/>
        <xsl:call-template name="relatedName"/>
        <xsl:call-template name="relatedOriginInfo"/>
        <xsl:call-template name="relatedLanguage"/>
        <xsl:call-template name="relatedExtent"/>
        <xsl:call-template name="relatedNote"/>
        <xsl:call-template name="relatedSubject"/>
        <xsl:call-template name="relatedIdentifier"/>
        <xsl:call-template name="relatedIdentifierISSN"/>
        <xsl:call-template name="relatedIdentifierLocal"/>
        <xsl:call-template name="relatedPart"/>
    </xsl:template>
    <xsl:template name="subjectGeographicZ">
        <mods:geographic>
            <xsl:call-template name="chopPunctuation">
                <xsl:with-param name="chopString" select="."/>
            </xsl:call-template>
        </mods:geographic>
    </xsl:template>
    <xsl:template name="subjectTemporalY">
        <mods:temporal>
            <xsl:call-template name="chopPunctuation">
                <xsl:with-param name="chopString" select="."/>
            </xsl:call-template>
        </mods:temporal>
    </xsl:template>
    <xsl:template name="subjectTopic">
        <mods:topic>
            <xsl:call-template name="chopPunctuation">
                <xsl:with-param name="chopString" select="."/>
            </xsl:call-template>
        </mods:topic>
    </xsl:template>
    <!-- 3.2 change tmee 6xx $v genre -->
    <xsl:template name="subjectGenre">
        <mods:genre>
            <xsl:call-template name="chopPunctuation">
                <xsl:with-param name="chopString" select="."/>
            </xsl:call-template>
        </mods:genre>
    </xsl:template>

    <xsl:template name="nameABCDN">
        <xsl:for-each select="marc:subfield[@code='a']">
            <mods:namePart>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString" select="."/>
                </xsl:call-template>
            </mods:namePart>
        </xsl:for-each>
        <xsl:for-each select="marc:subfield[@code='b']">
            <mods:namePart>
                <xsl:value-of select="."/>
            </mods:namePart>
        </xsl:for-each>
        <xsl:if
                test="marc:subfield[@code='c'] or marc:subfield[@code='d'] or marc:subfield[@code='n']">
            <mods:namePart>
                <xsl:call-template name="subfieldSelect">
                    <xsl:with-param name="codes">cdn</xsl:with-param>
                </xsl:call-template>
            </mods:namePart>
        </xsl:if>
    </xsl:template>
    <xsl:template name="nameABCDQ">
        <mods:namePart>
            <xsl:call-template name="chopPunctuation">
                <xsl:with-param name="chopString">
                    <xsl:call-template name="subfieldSelect">
                        <xsl:with-param name="codes">aq</xsl:with-param>
                    </xsl:call-template>
                </xsl:with-param>
                <xsl:with-param name="punctuation">
                    <xsl:text>:,;/ </xsl:text>
                </xsl:with-param>
            </xsl:call-template>
        </mods:namePart>
        <xsl:call-template name="termsOfAddress"/>
        <xsl:call-template name="nameDate"/>
    </xsl:template>
    <xsl:template name="nameACDEQ">
        <mods:namePart>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">acdneq</xsl:with-param>
            </xsl:call-template>
        </mods:namePart>
    </xsl:template>
    <xsl:template name="constituentOrRelatedType">
        <xsl:if test="@ind2=2">
            <xsl:attribute name="type">constituent</xsl:attribute>
        </xsl:if>
    </xsl:template>
    <xsl:template name="relatedTitle">
        <xsl:for-each select="marc:subfield[@code='t']">
            <mods:titleInfo>
                <mods:title>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="."/>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:title>
            </mods:titleInfo>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedTitle76X-78X">
        <xsl:for-each select="marc:subfield[@code='t']">
            <mods:titleInfo>
                <mods:title>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="."/>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:title>
                <xsl:if test="marc:datafield[@tag!=773]and marc:subfield[@code='g']">
                    <xsl:call-template name="relatedPartNumName"/>
                </xsl:if>
            </mods:titleInfo>
        </xsl:for-each>
        <xsl:for-each select="marc:subfield[@code='p']">
            <mods:titleInfo type="abbreviated">
                <mods:title>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="."/>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:title>
                <xsl:if test="marc:datafield[@tag!=773]and marc:subfield[@code='g']">
                    <xsl:call-template name="relatedPartNumName"/>
                </xsl:if>
            </mods:titleInfo>
        </xsl:for-each>
        <xsl:for-each select="marc:subfield[@code='s']">
            <mods:titleInfo type="uniform">
                <mods:title>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="."/>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:title>
                <xsl:if test="marc:datafield[@tag!=773]and marc:subfield[@code='g']">
                    <xsl:call-template name="relatedPartNumName"/>
                </xsl:if>
            </mods:titleInfo>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedOriginInfo">
        <xsl:if test="marc:subfield[@code='b' or @code='d'] or marc:subfield[@code='f']">
            <mods:originInfo>
                <xsl:if test="@tag=775">
                    <xsl:for-each select="marc:subfield[@code='f']">
                        <mods:place>
                            <mods:placeTerm>
                                <xsl:attribute name="type">code</xsl:attribute>
                                <xsl:attribute name="authority">marcgac</xsl:attribute>
                                <xsl:value-of select="."/>
                            </mods:placeTerm>
                        </mods:place>
                    </xsl:for-each>
                </xsl:if>
                <xsl:for-each select="marc:subfield[@code='d']">
                    <mods:publisher>
                        <xsl:value-of select="."/>
                    </mods:publisher>
                </xsl:for-each>
                <xsl:for-each select="marc:subfield[@code='b']">
                    <mods:edition>
                        <xsl:value-of select="."/>
                    </mods:edition>
                </xsl:for-each>
            </mods:originInfo>
        </xsl:if>
    </xsl:template>

    <!-- tmee 1.40 -->

    <xsl:template name="relatedOriginInfo510">
        <xsl:for-each select="marc:subfield[@code='b']">
            <mods:originInfo>
                <mods:dateOther type="coverage">
                    <xsl:value-of select="."/>
                </mods:dateOther>
            </mods:originInfo>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="relatedLanguage">
        <xsl:for-each select="marc:subfield[@code='e']">
            <xsl:call-template name="getLanguage">
                <xsl:with-param name="langString">
                    <xsl:value-of select="."/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="nameDate">
        <xsl:for-each select="marc:subfield[@code='d']">
            <mods:namePart type="date">
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString" select="."/>
                </xsl:call-template>
            </mods:namePart>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="subjectAuthority">
        <xsl:if test="@ind2!=4">
            <xsl:if test="@ind2!=' '">
                <xsl:if test="@ind2!=8">
                    <xsl:if test="@ind2!=9 or marc:subfield[@code='2'] != ''">
                        <xsl:attribute name="authority">
                            <xsl:choose>
                                <xsl:when test="@ind2=0">lcsh</xsl:when>
                                <xsl:when test="@ind2=1">lcshac</xsl:when>
                                <xsl:when test="@ind2=2">mesh</xsl:when>
                                <!-- 1/04 fix -->
                                <xsl:when test="@ind2=3">nal</xsl:when>
                                <xsl:when test="@ind2=5">csh</xsl:when>
                                <xsl:when test="@ind2=6">rvm</xsl:when>
                                <xsl:when test="@ind2=7 or @ind2=9">
                                    <xsl:value-of select="marc:subfield[@code='2']"/>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:attribute>
                    </xsl:if>
                </xsl:if>
            </xsl:if>
        </xsl:if>
    </xsl:template>
    <!-- 1.75 
		fix -->
    <xsl:template name="subject653Type">
        <xsl:if test="@ind2!=' '">
            <xsl:if test="@ind2!='0'">
                <xsl:if test="@ind2!='4'">
                    <xsl:if test="@ind2!='5'">
                        <xsl:if test="@ind2!='6'">
                            <xsl:if test="@ind2!='7'">
                                <xsl:if test="@ind2!='8'">
                                    <xsl:if test="@ind2!='7'">
                                        <xsl:if test="@ind2!='8'">
                                            <xsl:if test="@ind2!='9'">
                                                <xsl:attribute name="type">
                                                    <xsl:choose>
                                                        <xsl:when test="@ind2=1">personal</xsl:when>
                                                        <xsl:when test="@ind2=2">corporate</xsl:when>
                                                        <xsl:when test="@ind2=3">conference</xsl:when>
                                                    </xsl:choose>
                                                </xsl:attribute>
                                            </xsl:if>
                                        </xsl:if>
                                    </xsl:if>
                                </xsl:if>
                            </xsl:if>
                        </xsl:if>
                    </xsl:if>
                </xsl:if>
            </xsl:if>
        </xsl:if>


    </xsl:template>
    <xsl:template name="subjectAnyOrder">
        <xsl:for-each select="marc:subfield[@code='v' or @code='x' or @code='y' or @code='z']">
            <xsl:choose>
                <xsl:when test="@code='v'">
                    <xsl:call-template name="subjectGenre"/>
                </xsl:when>
                <xsl:when test="@code='x'">
                    <xsl:call-template name="subjectTopic"/>
                </xsl:when>
                <xsl:when test="@code='y'">
                    <xsl:call-template name="subjectTemporalY"/>
                </xsl:when>
                <xsl:when test="@code='z'">
                    <xsl:call-template name="subjectGeographicZ"/>
                </xsl:when>
            </xsl:choose>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="specialSubfieldSelect">
        <xsl:param name="anyCodes"/>
        <xsl:param name="axis"/>
        <xsl:param name="beforeCodes"/>
        <xsl:param name="afterCodes"/>
        <xsl:variable name="str">
            <xsl:for-each select="marc:subfield">
                <xsl:if
                        test="contains($anyCodes, @code) or (contains($beforeCodes,@code) and following-sibling::marc:subfield[@code=$axis])      or (contains($afterCodes,@code) and preceding-sibling::marc:subfield[@code=$axis])">
                    <xsl:value-of select="text()"/>
                    <xsl:text> </xsl:text>
                </xsl:if>
            </xsl:for-each>
        </xsl:variable>
        <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
    </xsl:template>



    <xsl:template match="marc:datafield[@tag=656]">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:if test="marc:subfield[@code=2]">
                <xsl:attribute name="authority">
                    <xsl:value-of select="marc:subfield[@code=2]"/>
                </xsl:attribute>
            </xsl:if>
            <mods:occupation>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:value-of select="marc:subfield[@code='a']"/>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:occupation>
        </mods:subject>
    </xsl:template>
    <xsl:template name="termsOfAddress">
        <xsl:if test="marc:subfield[@code='b' or @code='c']">
            <mods:namePart type="termsOfAddress">
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">bc</xsl:with-param>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:namePart>
        </xsl:if>
    </xsl:template>
    <xsl:template name="displayLabel">
        <xsl:if test="marc:subfield[@code='i']">
            <xsl:attribute name="displayLabel">
                <xsl:value-of select="marc:subfield[@code='i']"/>
            </xsl:attribute>
        </xsl:if>
        <xsl:if test="marc:subfield[@code='3']">
            <xsl:attribute name="displayLabel">
                <xsl:value-of select="marc:subfield[@code='3']"/>
            </xsl:attribute>
        </xsl:if>
    </xsl:template>

    <!-- isInvalid
	<xsl:template name="isInvalid">
		<xsl:param name="type"/>
		<xsl:if
			test="marc:subfield[@code='z'] or marc:subfield[@code='y'] or marc:subfield[@code='m']">
			<mods:identifier>
				<xsl:attribute name="type">
					<xsl:value-of select="$type"/>
				</xsl:attribute>
				<xsl:attribute name="invalid">
					<xsl:text>yes</xsl:text>
				</xsl:attribute>
				<xsl:if test="marc:subfield[@code='z']">
					<xsl:value-of select="marc:subfield[@code='z']"/>
				</xsl:if>
				<xsl:if test="marc:subfield[@code='y']">
					<xsl:value-of select="marc:subfield[@code='y']"/>
				</xsl:if>
				<xsl:if test="marc:subfield[@code='m']">
					<xsl:value-of select="marc:subfield[@code='m']"/>
				</xsl:if>
			</mods:identifier>
		</xsl:if>
	</xsl:template>
	-->
    <xsl:template name="subtitle">
        <xsl:if test="marc:subfield[@code='b']">
            <mods:subTitle>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:value-of select="marc:subfield[@code='b']"/>
                        <!--<xsl:call-template name="subfieldSelect">
							<xsl:with-param name="codes">b</xsl:with-param>									
						</xsl:call-template>-->
                    </xsl:with-param>
                </xsl:call-template>
            </mods:subTitle>
        </xsl:if>
    </xsl:template>
    <xsl:template name="script">
        <xsl:param name="scriptCode"/>
        <xsl:attribute name="script">
            <xsl:choose>
                <!-- ISO 15924	and CJK is a local code	20101123-->
                <xsl:when test="$scriptCode='(3'">Arab</xsl:when>
                <xsl:when test="$scriptCode='(4'">Arab</xsl:when>
                <xsl:when test="$scriptCode='(B'">Latn</xsl:when>
                <xsl:when test="$scriptCode='!E'">Latn</xsl:when>
                <xsl:when test="$scriptCode='$1'">CJK</xsl:when>
                <xsl:when test="$scriptCode='(N'">Cyrl</xsl:when>
                <xsl:when test="$scriptCode='(Q'">Cyrl</xsl:when>
                <xsl:when test="$scriptCode='(2'">Hebr</xsl:when>
                <xsl:when test="$scriptCode='(S'">Grek</xsl:when>
            </xsl:choose>
        </xsl:attribute>
    </xsl:template>
    <xsl:template name="parsePart">
        <!-- assumes 773$q= 1:2:3<4
		     with up to 3 levels and one optional start page
		-->
        <xsl:variable name="level1">
            <xsl:choose>
                <xsl:when test="contains(text(),':')">
                    <!-- 1:2 -->
                    <xsl:value-of select="substring-before(text(),':')"/>
                </xsl:when>
                <xsl:when test="not(contains(text(),':'))">
                    <!-- 1 or 1<3 -->
                    <xsl:if test="contains(text(),'&lt;')">
                        <!-- 1<3 -->
                        <xsl:value-of select="substring-before(text(),'&lt;')"/>
                    </xsl:if>
                    <xsl:if test="not(contains(text(),'&lt;'))">
                        <!-- 1 -->
                        <xsl:value-of select="text()"/>
                    </xsl:if>
                </xsl:when>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="sici2">
            <xsl:choose>
                <xsl:when test="starts-with(substring-after(text(),$level1),':')">
                    <xsl:value-of select="substring(substring-after(text(),$level1),2)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="substring-after(text(),$level1)"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="level2">
            <xsl:choose>
                <xsl:when test="contains($sici2,':')">
                    <!--  2:3<4  -->
                    <xsl:value-of select="substring-before($sici2,':')"/>
                </xsl:when>
                <xsl:when test="contains($sici2,'&lt;')">
                    <!-- 1: 2<4 -->
                    <xsl:value-of select="substring-before($sici2,'&lt;')"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$sici2"/>
                    <!-- 1:2 -->
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="sici3">
            <xsl:choose>
                <xsl:when test="starts-with(substring-after($sici2,$level2),':')">
                    <xsl:value-of select="substring(substring-after($sici2,$level2),2)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="substring-after($sici2,$level2)"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="level3">
            <xsl:choose>
                <xsl:when test="contains($sici3,'&lt;')">
                    <!-- 2<4 -->
                    <xsl:value-of select="substring-before($sici3,'&lt;')"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$sici3"/>
                    <!-- 3 -->
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="page">
            <xsl:if test="contains(text(),'&lt;')">
                <xsl:value-of select="substring-after(text(),'&lt;')"/>
            </xsl:if>
        </xsl:variable>
        <xsl:if test="$level1">
            <mods:detail level="1">
                <mods:number>
                    <xsl:value-of select="$level1"/>
                </mods:number>
            </mods:detail>
        </xsl:if>
        <xsl:if test="$level2">
            <mods:detail level="2">
                <mods:number>
                    <xsl:value-of select="$level2"/>
                </mods:number>
            </mods:detail>
        </xsl:if>
        <xsl:if test="$level3">
            <mods:detail level="3">
                <mods:number>
                    <xsl:value-of select="$level3"/>
                </mods:number>
            </mods:detail>
        </xsl:if>
        <xsl:if test="$page">
            <mods:extent unit="page">
                <mods:start>
                    <xsl:value-of select="$page"/>
                </mods:start>
            </mods:extent>
        </xsl:if>
    </xsl:template>
    <xsl:template name="getLanguage">
        <xsl:param name="langString"/>
        <xsl:param name="controlField008-35-37"/>
        <xsl:variable name="length" select="string-length($langString)"/>
        <xsl:choose>
            <xsl:when test="$length=0"/>
            <xsl:when test="$controlField008-35-37=substring($langString,1,3)">
                <xsl:call-template name="getLanguage">
                    <xsl:with-param name="langString" select="substring($langString,4,$length)"/>
                    <xsl:with-param name="controlField008-35-37" select="$controlField008-35-37"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <mods:language>
                    <mods:languageTerm authority="iso639-2b" type="code">
                        <xsl:value-of select="substring($langString,1,3)"/>
                    </mods:languageTerm>
                </mods:language>
                <xsl:call-template name="getLanguage">
                    <xsl:with-param name="langString" select="substring($langString,4,$length)"/>
                    <xsl:with-param name="controlField008-35-37" select="$controlField008-35-37"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="isoLanguage">
        <xsl:param name="currentLanguage"/>
        <xsl:param name="usedLanguages"/>
        <xsl:param name="remainingLanguages"/>
        <xsl:choose>
            <xsl:when test="string-length($currentLanguage)=0"/>
            <xsl:when test="not(contains($usedLanguages, $currentLanguage))">
                <mods:language>
                    <xsl:if test="@code!='a'">
                        <xsl:attribute name="objectPart">
                            <xsl:choose>
                                <xsl:when test="@code='b'">summary or subtitle</xsl:when>
                                <xsl:when test="@code='d'">sung or spoken text</xsl:when>
                                <xsl:when test="@code='e'">libretto</xsl:when>
                                <xsl:when test="@code='f'">table of contents</xsl:when>
                                <xsl:when test="@code='g'">accompanying material</xsl:when>
                                <xsl:when test="@code='h'">translation</xsl:when>
                            </xsl:choose>
                        </xsl:attribute>
                    </xsl:if>
                    <mods:languageTerm authority="iso639-2b" type="code">
                        <xsl:value-of select="$currentLanguage"/>
                    </mods:languageTerm>
                </mods:language>
                <xsl:call-template name="isoLanguage">
                    <xsl:with-param name="currentLanguage">
                        <xsl:value-of select="substring($remainingLanguages,1,3)"/>
                    </xsl:with-param>
                    <xsl:with-param name="usedLanguages">
                        <xsl:value-of select="concat($usedLanguages,$currentLanguage)"/>
                    </xsl:with-param>
                    <xsl:with-param name="remainingLanguages">
                        <xsl:value-of
                                select="substring($remainingLanguages,4,string-length($remainingLanguages))"
                                />
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="isoLanguage">
                    <xsl:with-param name="currentLanguage">
                        <xsl:value-of select="substring($remainingLanguages,1,3)"/>
                    </xsl:with-param>
                    <xsl:with-param name="usedLanguages">
                        <xsl:value-of select="concat($usedLanguages,$currentLanguage)"/>
                    </xsl:with-param>
                    <xsl:with-param name="remainingLanguages">
                        <xsl:value-of
                                select="substring($remainingLanguages,4,string-length($remainingLanguages))"
                                />
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="chopBrackets">
        <xsl:param name="chopString"/>
        <xsl:variable name="string">
            <xsl:call-template name="chopPunctuation">
                <xsl:with-param name="chopString" select="$chopString"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:if test="substring($string, 1,1)='['">
            <xsl:value-of select="substring($string,2, string-length($string)-2)"/>
        </xsl:if>
        <xsl:if test="substring($string, 1,1)!='['">
            <xsl:value-of select="$string"/>
        </xsl:if>
    </xsl:template>
    <xsl:template name="rfcLanguages">
        <xsl:param name="nodeNum"/>
        <xsl:param name="usedLanguages"/>
        <xsl:param name="controlField008-35-37"/>
        <xsl:variable name="currentLanguage" select="."/>
        <xsl:choose>
            <xsl:when test="not($currentLanguage)"/>
            <xsl:when
                    test="$currentLanguage!=$controlField008-35-37 and $currentLanguage!='rfc3066'">
                <xsl:if test="not(contains($usedLanguages,$currentLanguage))">
                    <mods:language>
                        <xsl:if test="@code!='a'">
                            <xsl:attribute name="objectPart">
                                <xsl:choose>
                                    <xsl:when test="@code='b'">summary or subtitle</xsl:when>
                                    <xsl:when test="@code='d'">sung or spoken text</xsl:when>
                                    <xsl:when test="@code='e'">libretto</xsl:when>
                                    <xsl:when test="@code='f'">table of contents</xsl:when>
                                    <xsl:when test="@code='g'">accompanying material</xsl:when>
                                    <xsl:when test="@code='h'">translation</xsl:when>
                                </xsl:choose>
                            </xsl:attribute>
                        </xsl:if>
                        <mods:languageTerm authority="rfc3066" type="code">
                            <xsl:value-of select="$currentLanguage"/>
                        </mods:languageTerm>
                    </mods:language>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise> </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- tmee added 20100106 for 045$b BC and CE date range info -->
    <xsl:template name="dates045b">
        <xsl:param name="str"/>
        <xsl:variable name="first-char" select="substring($str,1,1)"/>
        <xsl:choose>
            <xsl:when test="$first-char ='c'">
                <xsl:value-of select="concat ('-', substring($str, 2))"/>
            </xsl:when>
            <xsl:when test="$first-char ='d'">
                <xsl:value-of select="substring($str, 2)"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$str"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="scriptCode">
        <xsl:variable name="sf06" select="normalize-space(child::marc:subfield[@code='6'])"/>
        <xsl:variable name="sf06a" select="substring($sf06, 1, 3)"/>
        <xsl:variable name="sf06b" select="substring($sf06, 5, 2)"/>
        <xsl:variable name="sf06c" select="substring($sf06, 7)"/>
        <xsl:variable name="scriptCode" select="substring($sf06, 8, 2)"/>
        <xsl:if test="//marc:datafield/marc:subfield[@code='6']">
            <xsl:attribute name="script">
                <xsl:choose>
                    <xsl:when test="$scriptCode=''">Latn</xsl:when>
                    <xsl:when test="$scriptCode='(3'">Arab</xsl:when>
                    <xsl:when test="$scriptCode='(4'">Arab</xsl:when>
                    <xsl:when test="$scriptCode='(B'">Latn</xsl:when>
                    <xsl:when test="$scriptCode='!E'">Latn</xsl:when>
                    <xsl:when test="$scriptCode='$1'">CJK</xsl:when>
                    <xsl:when test="$scriptCode='(N'">Cyrl</xsl:when>
                    <xsl:when test="$scriptCode='(Q'">Cyrl</xsl:when>
                    <xsl:when test="$scriptCode='(2'">Hebr</xsl:when>
                    <xsl:when test="$scriptCode='(S'">Grek</xsl:when>
                </xsl:choose>
            </xsl:attribute>
        </xsl:if>
    </xsl:template>

    <!-- tmee 20100927 for 880s & corresponding fields  20101123 scriptCode -->

    <xsl:template name="xxx880">
        <xsl:if test="child::marc:subfield[@code='6']">
            <xsl:variable name="sf06" select="normalize-space(child::marc:subfield[@code='6'])"/>
            <xsl:variable name="sf06a" select="substring($sf06, 1, 3)"/>
            <xsl:variable name="sf06b" select="substring($sf06, 5, 2)"/>
            <xsl:variable name="sf06c" select="substring($sf06, 7)"/>
            <xsl:variable name="scriptCode" select="substring($sf06, 8, 2)"/>
            <xsl:if test="//marc:datafield/marc:subfield[@code='6']">
                <xsl:attribute name="altRepGroup">
                    <xsl:value-of select="$sf06b"/>
                </xsl:attribute>
                <xsl:attribute name="script">
                    <xsl:choose>
                        <xsl:when test="$scriptCode=''">Latn</xsl:when>
                        <xsl:when test="$scriptCode='(3'">Arab</xsl:when>
                        <xsl:when test="$scriptCode='(4'">Arab</xsl:when>
                        <xsl:when test="$scriptCode='(B'">Latn</xsl:when>
                        <xsl:when test="$scriptCode='!E'">Latn</xsl:when>
                        <xsl:when test="$scriptCode='$1'">CJK</xsl:when>
                        <xsl:when test="$scriptCode='(N'">Cyrl</xsl:when>
                        <xsl:when test="$scriptCode='(Q'">Cyrl</xsl:when>
                        <xsl:when test="$scriptCode='(2'">Hebr</xsl:when>
                        <xsl:when test="$scriptCode='(S'">Grek</xsl:when>
                    </xsl:choose>
                </xsl:attribute>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <xsl:template name="yyy880">
        <xsl:if test="preceding-sibling::marc:subfield[@code='6']">
            <xsl:variable name="sf06"
                          select="normalize-space(preceding-sibling::marc:subfield[@code='6'])"/>
            <xsl:variable name="sf06a" select="substring($sf06, 1, 3)"/>
            <xsl:variable name="sf06b" select="substring($sf06, 5, 2)"/>
            <xsl:variable name="sf06c" select="substring($sf06, 7)"/>
            <xsl:if test="//marc:datafield/marc:subfield[@code='6']">
                <xsl:attribute name="altRepGroup">
                    <xsl:value-of select="$sf06b"/>
                </xsl:attribute>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <xsl:template name="z2xx880">
        <!-- Evaluating the 260 field -->
        <xsl:variable name="x260">
            <xsl:choose>
                <xsl:when test="@tag='260' and marc:subfield[@code='6']">
                    <xsl:variable name="sf06260"
                                  select="normalize-space(child::marc:subfield[@code='6'])"/>
                    <xsl:variable name="sf06260a" select="substring($sf06260, 1, 3)"/>
                    <xsl:variable name="sf06260b" select="substring($sf06260, 5, 2)"/>
                    <xsl:variable name="sf06260c" select="substring($sf06260, 7)"/>
                    <xsl:value-of select="$sf06260b"/>
                </xsl:when>
                <xsl:when
                        test="@tag='250' and ../marc:datafield[@tag='260']/marc:subfield[@code='6']">
                    <xsl:variable name="sf06260"
                                  select="normalize-space(../marc:datafield[@tag='260']/marc:subfield[@code='6'])"/>
                    <xsl:variable name="sf06260a" select="substring($sf06260, 1, 3)"/>
                    <xsl:variable name="sf06260b" select="substring($sf06260, 5, 2)"/>
                    <xsl:variable name="sf06260c" select="substring($sf06260, 7)"/>
                    <xsl:value-of select="$sf06260b"/>
                </xsl:when>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="x250">
            <xsl:choose>
                <xsl:when test="@tag='250' and marc:subfield[@code='6']">
                    <xsl:variable name="sf06250"
                                  select="normalize-space(../marc:datafield[@tag='250']/marc:subfield[@code='6'])"/>
                    <xsl:variable name="sf06250a" select="substring($sf06250, 1, 3)"/>
                    <xsl:variable name="sf06250b" select="substring($sf06250, 5, 2)"/>
                    <xsl:variable name="sf06250c" select="substring($sf06250, 7)"/>
                    <xsl:value-of select="$sf06250b"/>
                </xsl:when>
                <xsl:when
                        test="@tag='260' and ../marc:datafield[@tag='250']/marc:subfield[@code='6']">
                    <xsl:variable name="sf06250"
                                  select="normalize-space(../marc:datafield[@tag='250']/marc:subfield[@code='6'])"/>
                    <xsl:variable name="sf06250a" select="substring($sf06250, 1, 3)"/>
                    <xsl:variable name="sf06250b" select="substring($sf06250, 5, 2)"/>
                    <xsl:variable name="sf06250c" select="substring($sf06250, 7)"/>
                    <xsl:value-of select="$sf06250b"/>
                </xsl:when>
            </xsl:choose>
        </xsl:variable>

        <xsl:choose>
            <xsl:when test="$x250!='' and $x260!=''">
                <xsl:attribute name="altRepGroup">
                    <xsl:value-of select="concat($x250, $x260)"/>
                </xsl:attribute>
            </xsl:when>
            <xsl:when test="$x250!=''">
                <xsl:attribute name="altRepGroup">
                    <xsl:value-of select="$x250"/>
                </xsl:attribute>
            </xsl:when>
            <xsl:when test="$x260!=''">
                <xsl:attribute name="altRepGroup">
                    <xsl:value-of select="$x260"/>
                </xsl:attribute>
            </xsl:when>
        </xsl:choose>
        <xsl:if test="//marc:datafield/marc:subfield[@code='6']"> </xsl:if>
    </xsl:template>

    <xsl:template name="z3xx880">
        <!-- Evaluating the 300 field -->
        <xsl:variable name="x300">
            <xsl:choose>
                <xsl:when test="@tag='300' and marc:subfield[@code='6']">
                    <xsl:variable name="sf06300"
                                  select="normalize-space(child::marc:subfield[@code='6'])"/>
                    <xsl:variable name="sf06300a" select="substring($sf06300, 1, 3)"/>
                    <xsl:variable name="sf06300b" select="substring($sf06300, 5, 2)"/>
                    <xsl:variable name="sf06300c" select="substring($sf06300, 7)"/>
                    <xsl:value-of select="$sf06300b"/>
                </xsl:when>
                <xsl:when
                        test="@tag='351' and ../marc:datafield[@tag='300']/marc:subfield[@code='6']">
                    <xsl:variable name="sf06300"
                                  select="normalize-space(../marc:datafield[@tag='300']/marc:subfield[@code='6'])"/>
                    <xsl:variable name="sf06300a" select="substring($sf06300, 1, 3)"/>
                    <xsl:variable name="sf06300b" select="substring($sf06300, 5, 2)"/>
                    <xsl:variable name="sf06300c" select="substring($sf06300, 7)"/>
                    <xsl:value-of select="$sf06300b"/>
                </xsl:when>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="x351">
            <xsl:choose>
                <xsl:when test="@tag='351' and marc:subfield[@code='6']">
                    <xsl:variable name="sf06351"
                                  select="normalize-space(../marc:datafield[@tag='351']/marc:subfield[@code='6'])"/>
                    <xsl:variable name="sf06351a" select="substring($sf06351, 1, 3)"/>
                    <xsl:variable name="sf06351b" select="substring($sf06351, 5, 2)"/>
                    <xsl:variable name="sf06351c" select="substring($sf06351, 7)"/>
                    <xsl:value-of select="$sf06351b"/>
                </xsl:when>
                <xsl:when
                        test="@tag='300' and ../marc:datafield[@tag='351']/marc:subfield[@code='6']">
                    <xsl:variable name="sf06351"
                                  select="normalize-space(../marc:datafield[@tag='351']/marc:subfield[@code='6'])"/>
                    <xsl:variable name="sf06351a" select="substring($sf06351, 1, 3)"/>
                    <xsl:variable name="sf06351b" select="substring($sf06351, 5, 2)"/>
                    <xsl:variable name="sf06351c" select="substring($sf06351, 7)"/>
                    <xsl:value-of select="$sf06351b"/>
                </xsl:when>
            </xsl:choose>
        </xsl:variable>

        <xsl:choose>
            <xsl:when test="$x351!='' and $x300!=''">
                <xsl:attribute name="altRepGroup">
                    <xsl:value-of select="concat($x351, $x300)"/>
                </xsl:attribute>
            </xsl:when>
            <xsl:when test="$x351!=''">
                <xsl:attribute name="altRepGroup">
                    <xsl:value-of select="$x351"/>
                </xsl:attribute>
            </xsl:when>
            <xsl:when test="$x300!=''">
                <xsl:attribute name="altRepGroup">
                    <xsl:value-of select="$x300"/>
                </xsl:attribute>
            </xsl:when>
        </xsl:choose>
        <xsl:if test="//marc:datafield/marc:subfield[@code='6']"> </xsl:if>
    </xsl:template>



    <xsl:template name="true880">
        <xsl:variable name="sf06" select="normalize-space(marc:subfield[@code='6'])"/>
        <xsl:variable name="sf06a" select="substring($sf06, 1, 3)"/>
        <xsl:variable name="sf06b" select="substring($sf06, 5, 2)"/>
        <xsl:variable name="sf06c" select="substring($sf06, 7)"/>
        <xsl:if test="//marc:datafield/marc:subfield[@code='6']">
            <xsl:attribute name="altRepGroup">
                <xsl:value-of select="$sf06b"/>
            </xsl:attribute>
        </xsl:if>
    </xsl:template>

    <xsl:template match="marc:datafield" mode="trans880">
        <xsl:variable name="dataField880" select="//marc:datafield"/>
        <xsl:variable name="sf06" select="normalize-space(marc:subfield[@code='6'])"/>
        <xsl:variable name="sf06a" select="substring($sf06, 1, 3)"/>
        <xsl:variable name="sf06b" select="substring($sf06, 4)"/>
        <xsl:choose>

            <!--tranforms 880 equiv-->

            <xsl:when test="$sf06a='047'">
                <xsl:call-template name="createGenreFrom047"/>
            </xsl:when>
            <xsl:when test="$sf06a='655'">
                <xsl:call-template name="createGenreFrom655"/>
            </xsl:when>

            <xsl:when test="$sf06a='050'">
                <xsl:call-template name="createClassificationFrom050"/>
            </xsl:when>
            <xsl:when test="$sf06a='060'">
                <xsl:call-template name="createClassificationFrom060"/>
            </xsl:when>
            <xsl:when test="$sf06a='080'">
                <xsl:call-template name="createClassificationFrom080"/>
            </xsl:when>
            <xsl:when test="$sf06a='082'">
                <xsl:call-template name="createClassificationFrom082"/>
            </xsl:when>
            <xsl:when test="$sf06a='084'">
                <xsl:call-template name="createClassificationFrom080"/>
            </xsl:when>
            <xsl:when test="$sf06a='086'">
                <xsl:call-template name="createClassificationFrom082"/>
            </xsl:when>
            <xsl:when test="$sf06a='100'">
                <xsl:call-template name="createNameFrom100"/>
            </xsl:when>
            <xsl:when test="$sf06a='110'">
                <xsl:call-template name="createNameFrom110"/>
            </xsl:when>
            <xsl:when test="$sf06a='111'">
                <xsl:call-template name="createNameFrom110"/>
            </xsl:when>
            <xsl:when test="$sf06a='700'">
                <xsl:call-template name="createNameFrom700"/>
            </xsl:when>
            <xsl:when test="$sf06a='710'">
                <xsl:call-template name="createNameFrom710"/>
            </xsl:when>
            <xsl:when test="$sf06a='711'">
                <xsl:call-template name="createNameFrom710"/>
            </xsl:when>
            <xsl:when test="$sf06a='210'">
                <xsl:call-template name="createTitleInfoFrom210"/>
            </xsl:when>
            <xsl:when test="$sf06a='245'">
                <xsl:call-template name="createTitleInfoFrom245"/>
                <xsl:call-template name="createNoteFrom245c"/>
            </xsl:when>
            <xsl:when test="$sf06a='246'">
                <xsl:call-template name="createTitleInfoFrom246"/>
            </xsl:when>
            <xsl:when test="$sf06a='240'">
                <xsl:call-template name="createTitleInfoFrom240"/>
            </xsl:when>
            <xsl:when test="$sf06a='740'">
                <xsl:call-template name="createTitleInfoFrom740"/>
            </xsl:when>

            <xsl:when test="$sf06a='130'">
                <xsl:call-template name="createTitleInfoFrom130"/>
            </xsl:when>
            <xsl:when test="$sf06a='730'">
                <xsl:call-template name="createTitleInfoFrom730"/>
            </xsl:when>

            <xsl:when test="$sf06a='505'">
                <xsl:call-template name="createTOCFrom505"/>
            </xsl:when>
            <xsl:when test="$sf06a='520'">
                <xsl:call-template name="createAbstractFrom520"/>
            </xsl:when>
            <xsl:when test="$sf06a='521'">
                <xsl:call-template name="createTargetAudienceFrom521"/>
            </xsl:when>
            <xsl:when test="$sf06a='506'">
                <xsl:call-template name="createAccessConditionFrom506"/>
            </xsl:when>
            <xsl:when test="$sf06a='540'">
                <xsl:call-template name="createAccessConditionFrom540"/>
            </xsl:when>

            <!-- note 245 362 etc	-->

            <xsl:when test="$sf06a='245'">
                <xsl:call-template name="createNoteFrom245c"/>
            </xsl:when>
            <xsl:when test="$sf06a='362'">
                <xsl:call-template name="createNoteFrom362"/>
            </xsl:when>
            <xsl:when test="$sf06a='502'">
                <xsl:call-template name="createNoteFrom502"/>
            </xsl:when>
            <xsl:when test="$sf06a='504'">
                <xsl:call-template name="createNoteFrom504"/>
            </xsl:when>
            <xsl:when test="$sf06a='508'">
                <xsl:call-template name="createNoteFrom508"/>
            </xsl:when>
            <xsl:when test="$sf06a='511'">
                <xsl:call-template name="createNoteFrom511"/>
            </xsl:when>
            <xsl:when test="$sf06a='515'">
                <xsl:call-template name="createNoteFrom515"/>
            </xsl:when>
            <xsl:when test="$sf06a='518'">
                <xsl:call-template name="createNoteFrom518"/>
            </xsl:when>
            <xsl:when test="$sf06a='524'">
                <xsl:call-template name="createNoteFrom524"/>
            </xsl:when>
            <xsl:when test="$sf06a='530'">
                <xsl:call-template name="createNoteFrom530"/>
            </xsl:when>
            <xsl:when test="$sf06a='533'">
                <xsl:call-template name="createNoteFrom533"/>
            </xsl:when>
            <xsl:when test="$sf06a='534'">
                <xsl:call-template name="createNoteFrom534"/>
            </xsl:when>
            <xsl:when test="$sf06a='535'">
                <xsl:call-template name="createNoteFrom535"/>
            </xsl:when>
            <xsl:when test="$sf06a='536'">
                <xsl:call-template name="createNoteFrom536"/>
            </xsl:when>
            <xsl:when test="$sf06a='538'">
                <xsl:call-template name="createNoteFrom538"/>
            </xsl:when>
            <xsl:when test="$sf06a='541'">
                <xsl:call-template name="createNoteFrom541"/>
            </xsl:when>
            <xsl:when test="$sf06a='545'">
                <xsl:call-template name="createNoteFrom545"/>
            </xsl:when>
            <xsl:when test="$sf06a='546'">
                <xsl:call-template name="createNoteFrom546"/>
            </xsl:when>
            <xsl:when test="$sf06a='561'">
                <xsl:call-template name="createNoteFrom561"/>
            </xsl:when>
            <xsl:when test="$sf06a='562'">
                <xsl:call-template name="createNoteFrom562"/>
            </xsl:when>
            <xsl:when test="$sf06a='581'">
                <xsl:call-template name="createNoteFrom581"/>
            </xsl:when>
            <xsl:when test="$sf06a='583'">
                <xsl:call-template name="createNoteFrom583"/>
            </xsl:when>
            <xsl:when test="$sf06a='585'">
                <xsl:call-template name="createNoteFrom585"/>
            </xsl:when>

            <!--	note 5XX	-->

            <xsl:when test="$sf06a='501'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='507'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='513'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='514'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='516'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='522'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='525'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='526'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='544'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='552'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='555'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='556'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='565'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='567'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='580'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='584'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>
            <xsl:when test="$sf06a='586'">
                <xsl:call-template name="createNoteFrom5XX"/>
            </xsl:when>

            <!--  subject 034 043 045 255 656 662 752 	-->

            <xsl:when test="$sf06a='034'">
                <xsl:call-template name="createSubGeoFrom034"/>
            </xsl:when>
            <xsl:when test="$sf06a='043'">
                <xsl:call-template name="createSubGeoFrom043"/>
            </xsl:when>
            <xsl:when test="$sf06a='045'">
                <xsl:call-template name="createSubTemFrom045"/>
            </xsl:when>
            <xsl:when test="$sf06a='255'">
                <xsl:call-template name="createSubGeoFrom255"/>
            </xsl:when>

            <xsl:when test="$sf06a='600'">
                <xsl:call-template name="createSubNameFrom600"/>
            </xsl:when>
            <xsl:when test="$sf06a='610'">
                <xsl:call-template name="createSubNameFrom610"/>
            </xsl:when>
            <xsl:when test="$sf06a='611'">
                <xsl:call-template name="createSubNameFrom611"/>
            </xsl:when>

            <xsl:when test="$sf06a='630'">
                <xsl:call-template name="createSubTitleFrom630"/>
            </xsl:when>

            <xsl:when test="$sf06a='648'">
                <xsl:call-template name="createSubChronFrom648"/>
            </xsl:when>
            <xsl:when test="$sf06a='650'">
                <xsl:call-template name="createSubTopFrom650"/>
            </xsl:when>
            <xsl:when test="$sf06a='651'">
                <xsl:call-template name="createSubGeoFrom651"/>
            </xsl:when>


            <xsl:when test="$sf06a='653'">
                <xsl:call-template name="createSubFrom653"/>
            </xsl:when>
            <xsl:when test="$sf06a='656'">
                <xsl:call-template name="createSubFrom656"/>
            </xsl:when>
            <xsl:when test="$sf06a='662'">
                <xsl:call-template name="createSubGeoFrom662752"/>
            </xsl:when>
            <xsl:when test="$sf06a='752'">
                <xsl:call-template name="createSubGeoFrom662752"/>
            </xsl:when>

            <!--  location  852 856 -->

            <xsl:when test="$sf06a='852'">
                <xsl:call-template name="createLocationFrom852"/>
            </xsl:when>
            <xsl:when test="$sf06a='910'">
                <xsl:call-template name="createLocationFrom910" />
            </xsl:when>
            <xsl:when test="$sf06a='856'">
                <xsl:call-template name="createLocationFrom856"/>
            </xsl:when>

            <xsl:when test="$sf06a='490'">
                <xsl:call-template name="createRelatedItemFrom490"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- titleInfo 130 730 245 246 240 740 210 -->

    <!-- 130 -->
    <xsl:template name="createTitleInfoFrom130">
        <xsl:for-each select="marc:datafield[@tag='130'][@ind2!='2']">
            <mods:titleInfo type="uniform">
                <mods:title>
                    <xsl:variable name="str">
                        <xsl:for-each select="marc:subfield">
                            <xsl:if test="(contains('s',@code))">
                                <xsl:value-of select="text()"/>
                                <xsl:text> </xsl:text>
                            </xsl:if>
                            <xsl:if
                                    test="(contains('adfklmors',@code) and (not(../marc:subfield[@code='n' or @code='p']) or (following-sibling::marc:subfield[@code='n' or @code='p'])))">
                                <xsl:value-of select="text()"/>
                                <xsl:text> </xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:variable>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:title>
                <xsl:call-template name="part"/>
            </mods:titleInfo>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="createTitleInfoFrom730">
        <mods:titleInfo type="uniform">
            <mods:title>
                <xsl:variable name="str">
                    <xsl:for-each select="marc:subfield">
                        <xsl:if test="(contains('s',@code))">
                            <xsl:value-of select="text()"/>
                            <xsl:text> </xsl:text>
                        </xsl:if>
                        <xsl:if
                                test="(contains('adfklmors',@code) and (not(../marc:subfield[@code='n' or @code='p']) or (following-sibling::marc:subfield[@code='n' or @code='p'])))">
                            <xsl:value-of select="text()"/>
                            <xsl:text> </xsl:text>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:variable>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
                    </xsl:with-param>
                </xsl:call-template>

            </mods:title>
            <xsl:call-template name="part"/>
        </mods:titleInfo>
    </xsl:template>

    <xsl:template name="createTitleInfoFrom210">
        <mods:titleInfo type="abbreviated">
            <xsl:if test="marc:datafield[@tag='210'][@ind2='2']">
                <xsl:attribute name="authority">
                    <xsl:value-of select="marc:subfield[@code='2']"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:call-template name="xxx880"/>
            <mods:title>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">a</xsl:with-param>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:title>
            <xsl:call-template name="subtitle"/>
        </mods:titleInfo>
    </xsl:template>

    <xsl:template name="createTitleInfoFrom245">
        <mods:titleInfo>
            <xsl:call-template name="xxx880"/>
            <xsl:variable name="title">
                <xsl:choose>
                    <xsl:when test="marc:subfield[@code='b']">
                        <xsl:call-template name="specialSubfieldSelect">
                            <xsl:with-param name="axis">b</xsl:with-param>
                            <xsl:with-param name="beforeCodes">afgk</xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">abfgk</xsl:with-param>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>
            <xsl:variable name="titleChop">
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:value-of select="$title"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:variable>
            <xsl:choose>
                <xsl:when test="@ind2&gt;0">
                    <xsl:if test="@tag!='880'">
                        <mods:nonSort>
                            <xsl:value-of select="substring($titleChop,1,@ind2)"/>
                        </mods:nonSort>
                    </xsl:if>
                    <mods:title>
                        <xsl:value-of select="substring($titleChop,@ind2+1)"/>
                    </mods:title>
                </xsl:when>
                <xsl:otherwise>
                    <mods:title>
                        <xsl:value-of select="$titleChop"/>
                    </mods:title>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="marc:subfield[@code='b']">
                <mods:subTitle>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:call-template name="specialSubfieldSelect">
                                <xsl:with-param name="axis">b</xsl:with-param>
                                <xsl:with-param name="anyCodes">b</xsl:with-param>
                                <xsl:with-param name="afterCodes">afgk</xsl:with-param>
                            </xsl:call-template>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:subTitle>
            </xsl:if>
            <xsl:call-template name="part"/>
        </mods:titleInfo>
    </xsl:template>

    <xsl:template name="createTitleInfoFrom246">
        <mods:titleInfo type="alternative">
            <xsl:call-template name="xxx880"/>
            <xsl:for-each select="marc:subfield[@code='i']">
                <xsl:attribute name="displayLabel">
                    <xsl:value-of select="text()"/>
                </xsl:attribute>
            </xsl:for-each>
            <mods:title>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:call-template name="subfieldSelect">
                            <!-- 1/04 removed $h, $b -->
                            <xsl:with-param name="codes">af</xsl:with-param>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:title>
            <xsl:call-template name="subtitle"/>
            <xsl:call-template name="part"/>
        </mods:titleInfo>
    </xsl:template>

    <!-- 240 nameTitleGroup-->

    <xsl:template name="createTitleInfoFrom240">
        <mods:titleInfo type="uniform">
            <xsl:if
                    test="//marc:datafield[@tag='100']|//marc:datafield[@tag='110']|//marc:datafield[@tag='111']">
                <xsl:attribute name="nameTitleGroup">
                    <xsl:text>1</xsl:text>
                </xsl:attribute>
            </xsl:if>
            <xsl:call-template name="xxx880"/>
            <mods:title>
                <xsl:variable name="str">
                    <xsl:for-each select="marc:subfield">
                        <xsl:if test="(contains('s',@code))">
                            <xsl:value-of select="text()"/>
                            <xsl:text> </xsl:text>
                        </xsl:if>
                        <xsl:if
                                test="(contains('adfklmors',@code) and (not(../marc:subfield[@code='n' or @code='p']) or (following-sibling::marc:subfield[@code='n' or @code='p'])))">
                            <xsl:value-of select="text()"/>
                            <xsl:text> </xsl:text>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:variable>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:title>
            <xsl:call-template name="part"/>
        </mods:titleInfo>
    </xsl:template>

    <xsl:template name="createTitleInfoFrom740">
        <mods:titleInfo type="alternative">
            <xsl:call-template name="xxx880"/>
            <mods:title>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">ah</xsl:with-param>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:title>
            <xsl:call-template name="part"/>
        </mods:titleInfo>
    </xsl:template>

    <!-- name 100 110 111 -->

    <xsl:template name="createNameFrom100">
        <mods:name type="personal">
            <xsl:attribute name="usage">
                <xsl:text>primary</xsl:text>
            </xsl:attribute>
            <xsl:call-template name="xxx880"/>
            <xsl:if test="//marc:datafield[@tag='240']">
                <xsl:attribute name="nameTitleGroup">
                    <xsl:text>1</xsl:text>
                </xsl:attribute>
            </xsl:if>
            <xsl:call-template name="nameABCDQ"/>
            <xsl:call-template name="affiliation"/>
            <xsl:call-template name="role"/>
        </mods:name>
    </xsl:template>

    <xsl:template name="createNameFrom110">
        <mods:name type="corporate">
            <xsl:call-template name="xxx880"/>
            <xsl:if test="//marc:datafield[@tag='240']">
                <xsl:attribute name="nameTitleGroup">
                    <xsl:text>1</xsl:text>
                </xsl:attribute>
            </xsl:if>
            <xsl:call-template name="nameABCDN"/>
            <xsl:call-template name="role"/>
        </mods:name>
    </xsl:template>

    <xsl:template name="createNameFrom111">
        <mods:name type="conference">
            <xsl:call-template name="xxx880"/>
            <xsl:if test="//marc:datafield[@tag='240']">
                <xsl:attribute name="nameTitleGroup">
                    <xsl:text>1</xsl:text>
                </xsl:attribute>
            </xsl:if>
            <xsl:call-template name="nameACDEQ"/>
            <xsl:call-template name="role"/>
        </mods:name>
    </xsl:template>

    <!-- name 700 710 711 720 -->

    <xsl:template name="createNameFrom700">
        <mods:name type="personal">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="nameABCDQ"/>
            <xsl:call-template name="affiliation"/>
            <xsl:call-template name="role"/>
        </mods:name>
    </xsl:template>

    <xsl:template name="createNameFrom710">
        <mods:name type="corporate">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="nameABCDN"/>
            <xsl:call-template name="role"/>
        </mods:name>
    </xsl:template>

    <xsl:template name="createNameFrom711">
        <mods:name type="conference">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="nameACDEQ"/>
            <xsl:call-template name="role"/>
        </mods:name>
    </xsl:template>

    <xsl:template name="createNameFrom720">
        <xsl:if test="marc:datafield[@tag='720'][not(marc:subfield[@code='t'])]">
            <mods:name>
                <xsl:if test="@ind1=1">
                    <xsl:attribute name="type">
                        <xsl:text>personal</xsl:text>
                    </xsl:attribute>
                </xsl:if>
                <mods:namePart>
                    <xsl:value-of select="marc:subfield[@code='a']"/>
                </mods:namePart>
                <xsl:call-template name="role"/>
            </mods:name>
        </xsl:if>
    </xsl:template>

    <!-- genre 047 655	-->

    <xsl:template name="createGenreFrom047">
        <mods:genre authority="marcgt">
            <xsl:attribute name="authority">
                <xsl:value-of select="marc:subfield[@code='2']"/>
            </xsl:attribute>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">abcdef</xsl:with-param>
                <xsl:with-param name="delimeter">-</xsl:with-param>
            </xsl:call-template>
        </mods:genre>
    </xsl:template>
    <xsl:template name="createGenreFrom655">
        <mods:genre authority="marcgt">
            <xsl:attribute name="authority">
                <xsl:value-of select="marc:subfield[@code='2']"/>
            </xsl:attribute>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">abvxyz</xsl:with-param>
                <xsl:with-param name="delimeter">-</xsl:with-param>
            </xsl:call-template>
        </mods:genre>
    </xsl:template>

    <!-- tOC 505 -->

    <xsl:template name="createTOCFrom505">
        <mods:tableOfContents>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">agrt</xsl:with-param>
            </xsl:call-template>
        </mods:tableOfContents>
    </xsl:template>

    <!-- abstract 520 -->

    <xsl:template name="createAbstractFrom520">
        <mods:abstract>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">ab</xsl:with-param>
            </xsl:call-template>
        </mods:abstract>
    </xsl:template>

    <!-- targetAudience 521 -->

    <xsl:template name="createTargetAudienceFrom521">
        <mods:targetAudience>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">ab</xsl:with-param>
            </xsl:call-template>
        </mods:targetAudience>
    </xsl:template>

    <!-- note 245c thru 585 -->

    <xsl:template name="createNoteFrom245c">
        <xsl:choose>
            <xsl:when
                    test="//marc:datafield[@tag='245'] and //marc:datafield[@tag=880]/marc:subfield[@code=6][contains(text(),'245')]">
                <mods:note type="statement of responsibility">
                    <xsl:attribute name="altRepGroup">
                        <xsl:text>00</xsl:text>
                    </xsl:attribute>
                    <xsl:call-template name="scriptCode"/>
                    <xsl:call-template name="subfieldSelect">
                        <xsl:with-param name="codes">c</xsl:with-param>
                    </xsl:call-template>
                </mods:note>
            </xsl:when>
            <xsl:when test="//marc:datafield[@tag='245']/marc:subfield[@code='c']">
                <mods:note type="statement of responsibility">
                    <xsl:call-template name="scriptCode"/>
                    <xsl:call-template name="subfieldSelect">
                        <xsl:with-param name="codes">c</xsl:with-param>
                    </xsl:call-template>
                </mods:note>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="createNoteFrom362">
        <mods:note type="date/sequential designation">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom500">
        <mods:note>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:value-of select="marc:subfield[@code='a']"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom502">
        <mods:note type="thesis">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom504">
        <mods:note type="bibliography">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom508">
        <mods:note type="creation/production credits">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each
                        select="marc:subfield[@code!='u' and @code!='3' and @code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom511">
        <mods:note type="performers">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom515">
        <mods:note type="numbering">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom518">
        <mods:note type="venue">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='3' and @code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom524">
        <mods:note type="preferred citation">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom530">
        <mods:note type="additional physical form">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each
                        select="marc:subfield[@code!='u' and @code!='3' and @code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom533">
        <mods:note type="reproduction">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom534">
        <mods:note type="original version">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom535">
        <mods:note type="original location">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom536">
        <mods:note type="funding">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom538">
        <mods:note type="system details">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom541">
        <mods:note type="acquisition">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom545">
        <mods:note type="biographical/historical">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom546">
        <mods:note type="language">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom561">
        <mods:note type="ownership">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom562">
        <mods:note type="version identification">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom581">
        <mods:note type="publications">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom583">
        <mods:note type="action">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom585">
        <mods:note type="exhibitions">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <xsl:template name="createNoteFrom5XX">
        <mods:note>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="uri"/>
            <xsl:variable name="str">
                <xsl:for-each select="marc:subfield[@code!='6' and @code!='8']">
                    <xsl:value-of select="."/>
                    <xsl:text> </xsl:text>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="substring($str,1,string-length($str)-1)"/>
        </mods:note>
    </xsl:template>

    <!-- subject Geo 034 043 045 255 656 662 752 -->

    <xsl:template name="createSubGeoFrom034">
        <xsl:if
                test="marc:datafield[@tag=034][marc:subfield[@code='d' or @code='e' or @code='f' or @code='g']]">
            <mods:subject>
                <xsl:call-template name="xxx880"/>
                <mods:cartographics>
                    <mods:coordinates>
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">defg</xsl:with-param>
                        </xsl:call-template>
                    </mods:coordinates>
                </mods:cartographics>
            </mods:subject>
        </xsl:if>
    </xsl:template>

    <xsl:template name="createSubGeoFrom043">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:for-each select="marc:subfield[@code='a' or @code='b' or @code='c']">
                <mods:geographicCode>
                    <xsl:attribute name="authority">
                        <xsl:if test="@code='a'">
                            <xsl:text>marcgac</xsl:text>
                        </xsl:if>
                        <xsl:if test="@code='b'">
                            <xsl:value-of select="following-sibling::marc:subfield[@code=2]"/>
                        </xsl:if>
                        <xsl:if test="@code='c'">
                            <xsl:text>iso3166</xsl:text>
                        </xsl:if>
                    </xsl:attribute>
                    <xsl:value-of select="self::marc:subfield"/>
                </mods:geographicCode>
            </xsl:for-each>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubGeoFrom255">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:for-each select="marc:subfield[@code='a' or @code='b' or @code='c']">
                <mods:cartographics>
                    <xsl:if test="@code='a'">
                        <mods:scale>
                            <xsl:value-of select="."/>
                        </mods:scale>
                    </xsl:if>
                    <xsl:if test="@code='b'">
                        <mods:projection>
                            <xsl:value-of select="."/>
                        </mods:projection>
                    </xsl:if>
                    <xsl:if test="@code='c'">
                        <mods:coordinates>
                            <xsl:value-of select="."/>
                        </mods:coordinates>
                    </xsl:if>
                </mods:cartographics>
            </xsl:for-each>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubNameFrom600">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subjectAuthority"/>
            <mods:name type="personal">
                <xsl:call-template name="termsOfAddress"/>
                <mods:namePart>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:call-template name="subfieldSelect">
                                <xsl:with-param name="codes">aq</xsl:with-param>
                            </xsl:call-template>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:namePart>
                <xsl:call-template name="nameDate"/>
                <xsl:call-template name="affiliation"/>
                <xsl:call-template name="role"/>
            </mods:name>
            <xsl:if test="marc:subfield[@code='t']">
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="subfieldSelect">
                                    <xsl:with-param name="codes">t</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="part"/>
                </mods:titleInfo>
            </xsl:if>
            <xsl:call-template name="subjectAnyOrder"/>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubNameFrom610">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subjectAuthority"/>
            <mods:name type="corporate">
                <xsl:for-each select="marc:subfield[@code='a']">
                    <mods:namePart>
                        <xsl:value-of select="."/>
                    </mods:namePart>
                </xsl:for-each>
                <xsl:for-each select="marc:subfield[@code='b']">
                    <mods:namePart>
                        <xsl:value-of select="."/>
                    </mods:namePart>
                </xsl:for-each>
                <xsl:if test="marc:subfield[@code='c' or @code='d' or @code='n' or @code='p']">
                    <mods:namePart>
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">cdnp</xsl:with-param>
                        </xsl:call-template>
                    </mods:namePart>
                </xsl:if>
                <xsl:call-template name="role"/>
            </mods:name>
            <xsl:if test="marc:subfield[@code='t']">
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="subfieldSelect">
                                    <xsl:with-param name="codes">t</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="part"/>
                </mods:titleInfo>
            </xsl:if>
            <xsl:call-template name="subjectAnyOrder"/>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubNameFrom611">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subjectAuthority"/>
            <mods:name type="conference">
                <mods:namePart>
                    <xsl:call-template name="subfieldSelect">
                        <xsl:with-param name="codes">abcdeqnp</xsl:with-param>
                    </xsl:call-template>
                </mods:namePart>
                <xsl:for-each select="marc:subfield[@code='4']">
                    <mods:role>
                        <mods:roleTerm authority="marcrelator" type="code">
                            <xsl:value-of select="."/>
                        </mods:roleTerm>
                    </mods:role>
                </xsl:for-each>
            </mods:name>
            <xsl:if test="marc:subfield[@code='t']">
                <mods:titleInfo>
                    <mods:title>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString">
                                <xsl:call-template name="subfieldSelect">
                                    <xsl:with-param name="codes">tpn</xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </mods:title>
                    <xsl:call-template name="part"/>
                </mods:titleInfo>
            </xsl:if>
            <xsl:call-template name="subjectAnyOrder"/>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubTitleFrom630">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subjectAuthority"/>
            <mods:titleInfo>
                <mods:title>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:call-template name="subfieldSelect">
                                <xsl:with-param name="codes">adfhklor</xsl:with-param>
                            </xsl:call-template>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:title>
                <xsl:call-template name="part"/>
            </mods:titleInfo>
            <xsl:call-template name="subjectAnyOrder"/>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubChronFrom648">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:if test="marc:subfield[@code=2]">
                <xsl:attribute name="authority">
                    <xsl:value-of select="marc:subfield[@code=2]"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:call-template name="uri"/>
            <xsl:call-template name="subjectAuthority"/>
            <mods:temporal>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">abcd</xsl:with-param>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:temporal>
            <xsl:call-template name="subjectAnyOrder"/>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubTopFrom650">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subjectAuthority"/>
            <mods:topic>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">abcd</xsl:with-param>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:topic>
            <xsl:call-template name="subjectAnyOrder"/>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubGeoFrom651">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subjectAuthority"/>
            <xsl:for-each select="marc:subfield[@code='a']">
                <mods:geographic>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString" select="."/>
                    </xsl:call-template>
                </mods:geographic>
            </xsl:for-each>
            <xsl:call-template name="subjectAnyOrder"/>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubFrom653">

        <xsl:if test="@ind2=' '">
            <mods:subject>
                <mods:topic>
                    <xsl:value-of select="."/>
                </mods:topic>
            </mods:subject>
        </xsl:if>
        <xsl:if test="@ind2='0'">
            <mods:subject>
                <mods:topic>
                    <xsl:value-of select="."/>
                </mods:topic>
            </mods:subject>
        </xsl:if>

        <xsl:if test="@ind2='1'">
            <mods:subject>
                <mods:name type="personal">
                    <mods:namePart>
                        <xsl:value-of select="."/>
                    </mods:namePart>
                </mods:name>
            </mods:subject>
        </xsl:if>
        <xsl:if test="@ind2='2'">
            <mods:subject>
                <mods:name type="corporate">
                    <mods:namePart>
                        <xsl:value-of select="."/>
                    </mods:namePart>
                </mods:name>
            </mods:subject>
        </xsl:if>
        <xsl:if test="@ind2='3'">
            <mods:subject>
                <mods:name type="conference">
                    <mods:namePart>
                        <xsl:value-of select="."/>
                    </mods:namePart>
                </mods:name>
            </mods:subject>
        </xsl:if>
        <xsl:if test="@ind2=4">
            <mods:subject>
                <mods:temporal>
                    <xsl:value-of select="."/>
                </mods:temporal>
            </mods:subject>
        </xsl:if>
        <xsl:if test="@ind2=5">
            <mods:subject>
                <mods:geographic>
                    <xsl:value-of select="."/>
                </mods:geographic>
            </mods:subject>
        </xsl:if>

        <xsl:if test="@ind2=6">
            <mods:subject>
                <mods:genre>
                    <xsl:value-of select="."/>
                </mods:genre>
            </mods:subject>
        </xsl:if>
    </xsl:template>

    <xsl:template name="createSubFrom656">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <xsl:if test="marc:subfield[@code=2]">
                <xsl:attribute name="authority">
                    <xsl:value-of select="marc:subfield[@code=2]"/>
                </xsl:attribute>
            </xsl:if>
            <mods:occupation>
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString">
                        <xsl:value-of select="marc:subfield[@code='a']"/>
                    </xsl:with-param>
                </xsl:call-template>
            </mods:occupation>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubGeoFrom662752">
        <mods:subject>
            <xsl:call-template name="xxx880"/>
            <mods:hierarchicalGeographic>
                <xsl:for-each select="marc:subfield[@code='a']">
                    <mods:country>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString" select="."/>
                        </xsl:call-template>
                    </mods:country>
                </xsl:for-each>
                <xsl:for-each select="marc:subfield[@code='b']">
                    <mods:state>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString" select="."/>
                        </xsl:call-template>
                    </mods:state>
                </xsl:for-each>
                <xsl:for-each select="marc:subfield[@code='c']">
                    <mods:county>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString" select="."/>
                        </xsl:call-template>
                    </mods:county>
                </xsl:for-each>
                <xsl:for-each select="marc:subfield[@code='d']">
                    <mods:city>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString" select="."/>
                        </xsl:call-template>
                    </mods:city>
                </xsl:for-each>
                <xsl:for-each select="marc:subfield[@code='e']">
                    <mods:citySection>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString" select="."/>
                        </xsl:call-template>
                    </mods:citySection>
                </xsl:for-each>
                <xsl:for-each select="marc:subfield[@code='g']">
                    <mods:area>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString" select="."/>
                        </xsl:call-template>
                    </mods:area>
                </xsl:for-each>
                <xsl:for-each select="marc:subfield[@code='h']">
                    <mods:extraterrestrialArea>
                        <xsl:call-template name="chopPunctuation">
                            <xsl:with-param name="chopString" select="."/>
                        </xsl:call-template>
                    </mods:extraterrestrialArea>
                </xsl:for-each>
            </mods:hierarchicalGeographic>
        </mods:subject>
    </xsl:template>

    <xsl:template name="createSubTemFrom045">
        <xsl:if
                test="//marc:datafield[@tag=045 and @ind1='2'][marc:subfield[@code='b' or @code='c']]">
            <mods:subject>
                <xsl:call-template name="xxx880"/>
                <mods:temporal encoding="iso8601" point="start">
                    <xsl:call-template name="dates045b">
                        <xsl:with-param name="str" select="marc:subfield[@code='b' or @code='c'][1]"
                                />
                    </xsl:call-template>
                </mods:temporal>
                <mods:temporal encoding="iso8601" point="end">
                    <xsl:call-template name="dates045b">
                        <xsl:with-param name="str" select="marc:subfield[@code='b' or @code='c'][2]"
                                />
                    </xsl:call-template>
                </mods:temporal>
            </mods:subject>
        </xsl:if>
    </xsl:template>

    <!-- classification 050 060 080 082 084 086 -->

    <xsl:template name="createClassificationFrom050">
        <xsl:for-each select="marc:subfield[@code='b']">
            <mods:classification authority="lcc">
                <xsl:call-template name="xxx880"/>
                <xsl:if test="../marc:subfield[@code='3']">
                    <xsl:attribute name="displayLabel">
                        <xsl:value-of select="../marc:subfield[@code='3']"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:value-of select="preceding-sibling::marc:subfield[@code='a'][1]"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="text()"/>
            </mods:classification>
        </xsl:for-each>
        <xsl:for-each
                select="marc:subfield[@code='a'][not(following-sibling::marc:subfield[@code='b'])]">
            <mods:classification authority="lcc">
                <xsl:call-template name="xxx880"/>
                <xsl:if test="../marc:subfield[@code='3']">
                    <xsl:attribute name="displayLabel">
                        <xsl:value-of select="../marc:subfield[@code='3']"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:value-of select="text()"/>
            </mods:classification>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="createClassificationFrom060">
        <mods:classification authority="nlm">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">ab</xsl:with-param>
            </xsl:call-template>
        </mods:classification>
    </xsl:template>
    <xsl:template name="createClassificationFrom080">
        <mods:classification authority="udc">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">abx</xsl:with-param>
            </xsl:call-template>
        </mods:classification>
    </xsl:template>
    <xsl:template name="createClassificationFrom082">
        <mods:classification authority="ddc">
            <xsl:call-template name="xxx880"/>
            <xsl:if test="marc:subfield[@code='2']">
                <xsl:attribute name="edition">
                    <xsl:value-of select="marc:subfield[@code='2']"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">ab</xsl:with-param>
            </xsl:call-template>
        </mods:classification>
    </xsl:template>
    <xsl:template name="createClassificationFrom084">
        <mods:classification>
            <xsl:attribute name="authority">
                <xsl:value-of select="marc:subfield[@code='2']"/>
            </xsl:attribute>
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">ab</xsl:with-param>
            </xsl:call-template>
        </mods:classification>
    </xsl:template>
    <xsl:template name="createClassificationFrom086">
        <xsl:for-each select="marc:datafield[@tag=086][@ind1=0]">
            <mods:classification authority="sudocs">
                <xsl:call-template name="xxx880"/>
                <xsl:value-of select="marc:subfield[@code='a']"/>
            </mods:classification>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=086][@ind1=1]">
            <mods:classification authority="candoc">
                <xsl:call-template name="xxx880"/>
                <xsl:value-of select="marc:subfield[@code='a']"/>
            </mods:classification>
        </xsl:for-each>
        <xsl:for-each select="marc:datafield[@tag=086][@ind1!=1 and @ind1!=0]">
            <mods:classification>
                <xsl:call-template name="xxx880"/>
                <xsl:attribute name="authority">
                    <xsl:value-of select="marc:subfield[@code='2']"/>
                </xsl:attribute>
                <xsl:value-of select="marc:subfield[@code='a']"/>
            </mods:classification>
        </xsl:for-each>
    </xsl:template>

    <!-- identifier 020 024 022 028 010 037 UNDO Nov 23 2010 RG SM-->

    <!-- createRelatedItemFrom490 <xsl:for-each select="marc:datafield[@tag=490][@ind1=0]"> -->

    <xsl:template name="createRelatedItemFrom490">
        <mods:relatedItem type="series">
            <xsl:call-template name="xxx880"/>
            <mods:titleInfo>
                <mods:title>
                    <xsl:call-template name="chopPunctuation">
                        <xsl:with-param name="chopString">
                            <xsl:call-template name="subfieldSelect">
                                <xsl:with-param name="codes">av</xsl:with-param>
                            </xsl:call-template>
                        </xsl:with-param>
                    </xsl:call-template>
                </mods:title>
                <xsl:call-template name="part"/>
            </mods:titleInfo>
        </mods:relatedItem>
    </xsl:template>


    <!-- location 852 856 -->

    <xsl:template name="createLocationFrom852">
        <xsl:if test="marc:subfield[@code='a' or @code='b' or @code='e']">
            <mods:physicalLocation>
                <xsl:call-template name="subfieldSelect">
                    <xsl:with-param name="codes">abe</xsl:with-param>
                </xsl:call-template>
            </mods:physicalLocation>
        </xsl:if>
        <xsl:if test="marc:subfield[@code='u']">
            <mods:physicalLocation>
                <xsl:call-template name="uri"/>
                <xsl:call-template name="subfieldSelect">
                    <xsl:with-param name="codes">u</xsl:with-param>
                </xsl:call-template>
            </mods:physicalLocation>
        </xsl:if>
        <xsl:if
                test="marc:subfield[@code='h' or @code='i' or @code='j' or @code='k' or @code='l' or @code='m' or @code='t']">
            <mods:shelfLocator>
                <xsl:call-template name="subfieldSelect">
                    <xsl:with-param name="codes">hijklmt</xsl:with-param>
                </xsl:call-template>
            </mods:shelfLocator>
        </xsl:if>
    </xsl:template>

    <xsl:template name="createLocationFrom910">
        <xsl:if test="marc:subfield[@code='a']">
            <mods:physicalLocation authority="siglaADR">
                <xsl:call-template name="subfieldSelect">
                    <xsl:with-param name="codes">a</xsl:with-param>
                </xsl:call-template>
            </mods:physicalLocation>
        </xsl:if>
        <xsl:if test="marc:subfield[@code='b']">
            <xsl:call-template name="createShelfLocatorsFor910">
                <xsl:with-param name="list"><xsl:value-of select="marc:subfield[@code='b']" /></xsl:with-param>
                <xsl:with-param name="delimiter">,</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template name="createLocationFrom856">
        <xsl:if test="//marc:datafield[@tag=856][@ind2!=2][marc:subfield[@code='u']]">
            <mods:url displayLabel="electronic resource">
                <!-- 1.41 tmee AQ1.9 added choice protocol for @usage="primary display" -->
                <xsl:variable name="primary">
                    <xsl:choose>
                        <xsl:when
                                test="@ind2=0 and count(preceding-sibling::marc:datafield[@tag=856] [@ind2=0])=0"
                                >true</xsl:when>

                        <xsl:when
                                test="@ind2=1 and 
							count(ancestor::marc:record//marc:datafield[@tag=856][@ind2=0])=0 and 
							count(preceding-sibling::marc:datafield[@tag=856][@ind2=1])=0"
                                >true</xsl:when>

                        <xsl:when
                                test="@ind2!=1 and @ind2!=0 and 
							@ind2!=2 and count(ancestor::marc:record//marc:datafield[@tag=856 and 
							@ind2=0])=0 and count(ancestor::marc:record//marc:datafield[@tag=856 and 
							@ind2=1])=0 and 
							count(preceding-sibling::marc:datafield[@tag=856][@ind2])=0"
                                >true</xsl:when>
                        <xsl:otherwise>false</xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:if test="$primary='true'">
                    <xsl:attribute name="usage">primary display</xsl:attribute>
                </xsl:if>

                <xsl:if test="marc:subfield[@code='y' or @code='3']">
                    <xsl:attribute name="displayLabel">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">y3</xsl:with-param>
                        </xsl:call-template>
                    </xsl:attribute>
                </xsl:if>
                <xsl:if test="marc:subfield[@code='z']">
                    <xsl:attribute name="note">
                        <xsl:call-template name="subfieldSelect">
                            <xsl:with-param name="codes">z</xsl:with-param>
                        </xsl:call-template>
                    </xsl:attribute>
                </xsl:if>
                <xsl:value-of select="marc:subfield[@code='u']"/>
            </mods:url>
        </xsl:if>
    </xsl:template>

    <!-- accessCondition 506 540 -->

    <xsl:template name="createAccessConditionFrom506">
        <mods:accessCondition type="restrictionOnAccess">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">abcd35</xsl:with-param>
            </xsl:call-template>
        </mods:accessCondition>
    </xsl:template>

    <xsl:template name="createAccessConditionFrom540">
        <mods:accessCondition type="useAndReproduction">
            <xsl:call-template name="xxx880"/>
            <xsl:call-template name="subfieldSelect">
                <xsl:with-param name="codes">abcde35</xsl:with-param>
            </xsl:call-template>
        </mods:accessCondition>
    </xsl:template>

    <!-- recordInfo 040 005 001 003 -->

    <!-- 880 global copy template -->
    <xsl:template match="* | @*" mode="global_copy">
        <xsl:copy>
            <xsl:apply-templates select="* | @* | text()" mode="global_copy"/>
        </xsl:copy>
    </xsl:template>


    <xsl:variable name="ascii">
        <xsl:text> !"#$%&amp;'()*+,-./0123456789:;&lt;=&gt;?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~</xsl:text>
    </xsl:variable>

    <xsl:variable name="latin1">
        <xsl:text> ¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ</xsl:text>
    </xsl:variable>
    <!-- Characters that usually don't need to be escaped -->
    <xsl:variable name="safe">
        <xsl:text>!'()*-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~</xsl:text>
    </xsl:variable>

    <xsl:variable name="hex">0123456789ABCDEF</xsl:variable>


    <xsl:template name="datafield">
        <xsl:param name="tag"/>
        <xsl:param name="ind1">
            <xsl:text> </xsl:text>
        </xsl:param>
        <xsl:param name="ind2">
            <xsl:text> </xsl:text>
        </xsl:param>
        <xsl:param name="subfields"/>
        <xsl:element name="marc:datafield">
            <xsl:attribute name="tag">
                <xsl:value-of select="$tag"/>
            </xsl:attribute>
            <xsl:attribute name="ind1">
                <xsl:value-of select="$ind1"/>
            </xsl:attribute>
            <xsl:attribute name="ind2">
                <xsl:value-of select="$ind2"/>
            </xsl:attribute>
            <xsl:copy-of select="$subfields"/>
        </xsl:element>
    </xsl:template>

    <xsl:template name="subfieldSelect">
        <xsl:param name="codes">abcdefghijklmnopqrstuvwxyz</xsl:param>
        <xsl:param name="delimeter">
            <xsl:text> </xsl:text>
        </xsl:param>
        <xsl:variable name="str">
            <xsl:for-each select="marc:subfield">
                <xsl:if test="contains($codes, @code)">
                    <xsl:value-of select="text()"/>
                    <xsl:value-of select="$delimeter"/>
                </xsl:if>
            </xsl:for-each>
        </xsl:variable>
        <xsl:value-of select="substring($str,1,string-length($str)-string-length($delimeter))"/>
    </xsl:template>

    <xsl:template name="createShelfLocatorsFor910">
        <xsl:param name="list" />
        <xsl:param name="delimiter" />
        <xsl:variable name="newlist">
            <xsl:choose>
                <xsl:when test="contains($list, $delimiter)"><xsl:value-of select="normalize-space($list)" /></xsl:when>
                <xsl:otherwise><xsl:value-of select="concat(normalize-space($list), $delimiter)"/></xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="first" select="substring-before($newlist, $delimiter)" />
        <xsl:variable name="remaining" select="substring-after($newlist, $delimiter)" />
        <mods:shelfLocator>
            <xsl:value-of select="$first" />
        </mods:shelfLocator>
        <xsl:if test="$remaining">
            <xsl:call-template name="createShelfLocatorsFor910">
                <xsl:with-param name="list" select="$remaining" />
                <xsl:with-param name="delimiter"><xsl:value-of select="$delimiter"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template name="buildSpaces">
        <xsl:param name="spaces"/>
        <xsl:param name="char">
            <xsl:text> </xsl:text>
        </xsl:param>
        <xsl:if test="$spaces&gt;0">
            <xsl:value-of select="$char"/>
            <xsl:call-template name="buildSpaces">
                <xsl:with-param name="spaces" select="$spaces - 1"/>
                <xsl:with-param name="char" select="$char"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template name="chopPunctuation">
        <xsl:param name="chopString"/>
        <xsl:param name="punctuation">
            <xsl:text>.:,;/ </xsl:text>
        </xsl:param>
        <xsl:variable name="length" select="string-length($chopString)"/>
        <xsl:choose>
            <xsl:when test="$length=0"/>
            <xsl:when test="contains($punctuation, substring($chopString,$length,1))">
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString" select="substring($chopString,1,$length - 1)"/>
                    <xsl:with-param name="punctuation" select="$punctuation"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="not($chopString)"/>
            <xsl:otherwise>
                <xsl:value-of select="$chopString"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="chopPunctuationFront">
        <xsl:param name="chopString"/>
        <xsl:variable name="length" select="string-length($chopString)"/>
        <xsl:choose>
            <xsl:when test="$length=0"/>
            <xsl:when test="contains('.:,;/[ ', substring($chopString,1,1))">
                <xsl:call-template name="chopPunctuationFront">
                    <xsl:with-param name="chopString" select="substring($chopString,2,$length - 1)"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="not($chopString)"/>
            <xsl:otherwise>
                <xsl:value-of select="$chopString"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="chopPunctuationBack">
        <xsl:param name="chopString"/>
        <xsl:param name="punctuation">
            <xsl:text>.:,;/] </xsl:text>
        </xsl:param>
        <xsl:variable name="length" select="string-length($chopString)"/>
        <xsl:choose>
            <xsl:when test="$length=0"/>
            <xsl:when test="contains($punctuation, substring($chopString,$length,1))">
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString" select="substring($chopString,1,$length - 1)"/>
                    <xsl:with-param name="punctuation" select="$punctuation"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="not($chopString)"/>
            <xsl:otherwise>
                <xsl:value-of select="$chopString"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="chopPunctuationBoth">
        <xsl:param name="chopString"/>
        <xsl:param name="punctuation">
            <xsl:text>.:,;/][ </xsl:text>
        </xsl:param>
        <xsl:variable name="length" select="string-length($chopString)"/>
        <xsl:choose>
            <xsl:when test="$length=0"/>
            <xsl:when test="contains($punctuation, substring($chopString,$length,1))">
                <xsl:call-template name="chopPunctuation">
                    <xsl:with-param name="chopString" select="substring($chopString,1,$length - 1)"/>
                    <xsl:with-param name="punctuation" select="$punctuation"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="not($chopString)"/>
            <xsl:otherwise>
                <xsl:value-of select="$chopString"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- nate added 12/14/2007 for lccn.loc.gov: url encode ampersand, etc. -->
    <xsl:template name="url-encode">

        <xsl:param name="str"/>

        <xsl:if test="$str">
            <xsl:variable name="first-char" select="substring($str,1,1)"/>
            <xsl:choose>
                <xsl:when test="contains($safe,$first-char)">
                    <xsl:value-of select="$first-char"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:variable name="codepoint">
                        <xsl:choose>
                            <xsl:when test="contains($ascii,$first-char)">
                                <xsl:value-of select="string-length(substring-before($ascii,$first-char)) + 32"/>
                            </xsl:when>
                            <xsl:when test="contains($latin1,$first-char)">
                                <xsl:value-of select="string-length(substring-before($latin1,$first-char)) + 160"/>
                                <!-- was 160 -->
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:message terminate="no">Warning: string contains a character
                                    that is out of range! Substituting "?".</xsl:message>
                                <xsl:text>63</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <xsl:variable name="hex-digit1" select="substring($hex,floor($codepoint div 16) + 1,1)"/>
                    <xsl:variable name="hex-digit2" select="substring($hex,$codepoint mod 16 + 1,1)"/>
                    <!-- <xsl:value-of select="concat('%',$hex-digit2)"/> -->
                    <xsl:value-of select="concat('%',$hex-digit1,$hex-digit2)"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="string-length($str) &gt; 1">
                <xsl:call-template name="url-encode">
                    <xsl:with-param name="str" select="substring($str,2)"/>
                </xsl:call-template>
            </xsl:if>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
