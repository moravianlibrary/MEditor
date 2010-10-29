package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.Arrays;
import java.util.Map;

import com.smartgwt.client.widgets.form.fields.FormItem;

public class Attribute {

	private Class<? extends FormItem> type;
	private String name;
	private String[] labels;
	private String label;
	private String tooltip;
	private Map<String, String> tooltips;

	public Attribute() {
	};

	public Attribute(Class<? extends FormItem> type, String name, String label, String tooltip) {
		super();
		this.type = type;
		this.name = name;
		this.label = label;
		this.tooltip = tooltip;
	}

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

	public Class<? extends FormItem> getType() {
		return type;
	}

	public void setType(Class<? extends FormItem> type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public Map<String, String> getTooltips() {
		return tooltips;
	}

	public void setTooltips(Map<String, String> tooltips) {
		this.tooltips = tooltips;
	}
}
