/**
 * Metadata Editor
 * @author Jiri Kremser
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
	 *          the type
	 * @param name
	 *          the name
	 * @param label
	 *          the label
	 * @param tooltip
	 *          the tooltip
	 */
	public Attribute(Class<? extends FormItem> type, String name, String label, String tooltip) {
		super();
		this.type = type;
		this.name = name;
		this.label = label;
		this.tooltip = tooltip;
	}

	public Attribute(Class<? extends FormItem> type, String name, String label, String tooltip, String value) {
		super();
		this.type = type;
		this.name = name;
		this.label = label;
		this.tooltip = tooltip;
		this.value = value;
	}

	/**
	 * Instantiates a new attribute.
	 * 
	 * @param type
	 *          the type
	 * @param name
	 *          the name
	 * @param label
	 *          the label
	 * @param tooltips
	 *          the tooltips
	 */
	public Attribute(Class<? extends FormItem> type, String name, String label, Map<String, String> tooltips) {
		super();
		this.type = type;
		this.name = name;
		this.label = label;
		this.tooltips = tooltips;
		String[] labels = tooltips.keySet().toArray(new String[] {});
		Arrays.sort(labels);
		this.labels = labels;
	}

	public Attribute(Class<? extends FormItem> type, String name, String label, Map<String, String> tooltips, String value) {
		super();
		this.type = type;
		this.name = name;
		this.label = label;
		this.tooltips = tooltips;
		String[] labels = tooltips.keySet().toArray(new String[] {});
		Arrays.sort(labels);
		this.labels = labels;
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
	 *          the new type
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
	 *          the new name
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
	 *          the new labels
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
	 *          the new label
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
	 *          the new tooltip
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
	 *          the tooltips
	 */
	public void setTooltips(Map<String, String> tooltips) {
		this.tooltips = tooltips;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
