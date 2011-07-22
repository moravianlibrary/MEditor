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

package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.Arrays;
import java.util.Map;

import com.smartgwt.client.widgets.form.fields.FormItem;

// TODO: Auto-generated Javadoc
/**
 * The Class Attribute.
 */
public class Attribute {

    /** The type. */
    private Class<? extends FormItem> type;

    /** The name. */
    private String name;

    /** The labels. */
    private String[] labels;

    /** The label. */
    private String label;

    /** The tooltip. */
    private String tooltip;

    /** The tooltips. */
    private Map<String, String> tooltips;

    /** The value. */
    private String value;

    /**
     * Instantiates a new attribute.
     */
    public Attribute() {
    };

    /**
     * Instantiates a new attribute.
     * 
     * @param type
     *        the type
     * @param name
     *        the name
     * @param label
     *        the label
     * @param tooltip
     *        the tooltip
     */
    public Attribute(Class<? extends FormItem> type, String name, String label, String tooltip) {
        this.type = type;
        // name does not support spaces
        this.name = name != null ? name.toLowerCase().replaceAll(" ", "_") : name;
        this.label = label;
        this.tooltip = tooltip;
    }

    /**
     * Instantiates a new attribute.
     * 
     * @param type
     *        the type
     * @param name
     *        the name
     * @param label
     *        the label
     * @param tooltip
     *        the tooltip
     * @param value
     *        the value
     */
    public Attribute(Class<? extends FormItem> type, String name, String label, String tooltip, String value) {
        this(type, name, label, tooltip);
        this.value = value;
    }

    /**
     * Instantiates a new attribute.
     * 
     * @param type
     *        the type
     * @param name
     *        the name
     * @param label
     *        the label
     * @param tooltips
     *        the tooltips
     */
    public Attribute(Class<? extends FormItem> type, String name, String label, Map<String, String> tooltips) {
        this(type, name, label, "");
        this.tooltips = tooltips;
        String[] labels = tooltips.keySet().toArray(new String[] {});
        Arrays.sort(labels);
        this.labels = labels;
    }

    /**
     * Instantiates a new attribute.
     * 
     * @param type
     *        the type
     * @param name
     *        the name
     * @param label
     *        the label
     * @param tooltips
     *        the tooltips
     * @param value
     *        the value
     */
    public Attribute(Class<? extends FormItem> type,
                     String name,
                     String label,
                     Map<String, String> tooltips,
                     String value) {
        this(type, name, label, tooltips);
        this.value = value;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public Class<? extends FormItem> getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *        the new type
     */
    public void setType(Class<? extends FormItem> type) {
        this.type = type;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *        the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the labels.
     * 
     * @return the labels
     */
    public String[] getLabels() {
        return labels;
    }

    /**
     * Sets the labels.
     * 
     * @param labels
     *        the new labels
     */
    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label.
     * 
     * @param label
     *        the new label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the tooltip.
     * 
     * @return the tooltip
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * Sets the tooltip.
     * 
     * @param tooltip
     *        the new tooltip
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Gets the tooltips.
     * 
     * @return the tooltips
     */
    public Map<String, String> getTooltips() {
        return tooltips;
    }

    /**
     * Sets the tooltips.
     * 
     * @param tooltips
     *        the tooltips
     */
    public void setTooltips(Map<String, String> tooltips) {
        this.tooltips = tooltips;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value
     *        the new value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
