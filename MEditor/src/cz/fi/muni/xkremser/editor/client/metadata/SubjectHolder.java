/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.HierarchicalGeographicTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient.CartographicsClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient.GeographicCodeClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class SubjectHolder.
 */
public class SubjectHolder
        extends ListOfSimpleValuesHolder {

    /** The topics. */
    private final ListOfSimpleValuesHolder topics;

    /** The geographics. */
    private final ListOfSimpleValuesHolder geographics;

    /** The genres. */
    private final ListOfSimpleValuesHolder genres;

    /** The occupations. */
    private final ListOfSimpleValuesHolder occupations;

    /** The geo codes. */
    private final ListOfListOfSimpleValuesHolder geoCodes;

    /** The temporals. */
    private final List<DateHolder> temporals;

    /** The title info. */
    private final List<TitleInfoHolder> titleInfo;

    /** The names. */
    private final List<NameHolder> names;

    /** The hieararchical geo. */
    private final List<HierarchicalGeographicHolder> hieararchicalGeo;

    /** The cartographics. */
    private final List<CartographicsHolder> cartographics;

    /**
     * Instantiates a new subject holder.
     */
    public SubjectHolder() {
        this.topics = new ListOfSimpleValuesHolder();
        this.geographics = new ListOfSimpleValuesHolder();
        this.genres = new ListOfSimpleValuesHolder();
        this.occupations = new ListOfSimpleValuesHolder();
        this.geoCodes = new ListOfListOfSimpleValuesHolder(ModsConstants.GEO_CODE, ModsConstants.AUTHORITY);
        temporals = new ArrayList<DateHolder>();
        titleInfo = new ArrayList<TitleInfoHolder>();
        names = new ArrayList<NameHolder>();
        hieararchicalGeo = new ArrayList<HierarchicalGeographicHolder>();
        cartographics = new ArrayList<CartographicsHolder>();
    }

    /**
     * Gets the subject.
     * 
     * @return the subject
     */
    public SubjectTypeClient getSubject() {
        SubjectTypeClient subjectTypeClient = new SubjectTypeClient();
        if (getAttributeForm() != null) {
            subjectTypeClient.setAuthority(getAttributeForm().getValueAsString(ModsConstants.AUTHORITY));
            subjectTypeClient.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
            subjectTypeClient.setId(getAttributeForm().getValueAsString(ModsConstants.ID));
            subjectTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
            subjectTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
            subjectTypeClient.setTransliteration(getAttributeForm()
                    .getValueAsString(ModsConstants.TRANSLITERATION));
            subjectTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
        }
        subjectTypeClient.setTopic(topics.getValues());
        subjectTypeClient.setGeographic(geographics.getValues());
        subjectTypeClient.setGenre(genres.getValues());
        subjectTypeClient.setOccupation(occupations.getValues());
        subjectTypeClient.setTemporal(getDatesFromHolders(temporals));

        // title info
        List<TitleInfoTypeClient> value = new ArrayList<TitleInfoTypeClient>(titleInfo.size());
        for (TitleInfoHolder holder : titleInfo) {
            value.add(holder.getTitleInfo());
        }
        subjectTypeClient.setTitleInfo(value);

        // name
        List<NameTypeClient> value2 = new ArrayList<NameTypeClient>(names.size());
        for (NameHolder holder : names) {
            value2.add(holder.getName());
        }
        subjectTypeClient.setName(value2);

        // geoCodes
        List<GeographicCodeClient> list = null;
        List<List<String>> listOfValues = geoCodes.getListOfList();
        if (listOfValues != null && listOfValues.size() != 0) {
            list = new ArrayList<GeographicCodeClient>();
            for (List<String> values : listOfValues) {
                GeographicCodeClient val = new GeographicCodeClient();
                val.setValue(values.get(0));
                val.setAuthority(PlaceAuthorityClient.fromValue(values.get(1)));
                list.add(val);
            }
        }
        subjectTypeClient.setGeographicCode(list);

        // hieararchicalGeo
        List<HierarchicalGeographicTypeClient> list2 = null;
        if (hieararchicalGeo.size() > 0) {
            list2 = new ArrayList<HierarchicalGeographicTypeClient>();
            for (HierarchicalGeographicHolder holder : hieararchicalGeo) {
                list2.add(holder.getHierarchicalGeographic());
            }
        }

        subjectTypeClient.setHierarchicalGeographic(list2);

        // cartographics
        List<CartographicsClient> list3 = null;
        if (cartographics.size() > 0) {
            list3 = new ArrayList<CartographicsClient>();
            for (CartographicsHolder holder : cartographics) {
                list3.add(holder.getCartographics());
            }
        }
        subjectTypeClient.setCartographics(list3);

        return subjectTypeClient;
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
     * getSubelements()
     */
    @Override
    public List<MetadataHolder> getSubelements() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValue
     * ()
     */
    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValues
     * ()
     */
    @Override
    public List<String> getValues() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
     * getAttributes()
     */
    @Override
    public List<String> getAttributes() {
        throw new UnsupportedOperationException("Mods");
    }

    /**
     * Gets the dates from holders.
     * 
     * @param holders
     *        the holders
     * @return the dates from holders
     */
    private static List<DateTypeClient> getDatesFromHolders(List<DateHolder> holders) {
        List<DateTypeClient> dates = new ArrayList<DateTypeClient>();
        for (DateHolder holder : holders) {
            dates.add(holder.getDate());
        }
        return dates;
    }

    /**
     * Gets the topics.
     * 
     * @return the topics
     */
    public ListOfSimpleValuesHolder getTopics() {
        return topics;
    }

    /**
     * Gets the geographics.
     * 
     * @return the geographics
     */
    public ListOfSimpleValuesHolder getGeographics() {
        return geographics;
    }

    /**
     * Gets the genres.
     * 
     * @return the genres
     */
    public ListOfSimpleValuesHolder getGenres() {
        return genres;
    }

    /**
     * Gets the occupations.
     * 
     * @return the occupations
     */
    public ListOfSimpleValuesHolder getOccupations() {
        return occupations;
    }

    /**
     * Gets the geo codes.
     * 
     * @return the geo codes
     */
    public ListOfListOfSimpleValuesHolder getGeoCodes() {
        return geoCodes;
    }

    /**
     * Gets the temporals.
     * 
     * @return the temporals
     */
    public List<DateHolder> getTemporals() {
        return temporals;
    }

    /**
     * Gets the title info.
     * 
     * @return the title info
     */
    public List<TitleInfoHolder> getTitleInfo() {
        return titleInfo;
    }

    /**
     * Gets the names.
     * 
     * @return the names
     */
    public List<NameHolder> getNames() {
        return names;
    }

    /**
     * Gets the hieararchical geo.
     * 
     * @return the hieararchical geo
     */
    public List<HierarchicalGeographicHolder> getHieararchicalGeo() {
        return hieararchicalGeo;
    }

    /**
     * Gets the cartographics.
     * 
     * @return the cartographics
     */
    public List<CartographicsHolder> getCartographics() {
        return cartographics;
    }

}
