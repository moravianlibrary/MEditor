package cz.fi.muni.xkremser.editor.client;


public enum KrameriusModel {

	MONOGRAPH("monograph"), MONOGRAPHUNIT("monographunit"), PERIODICAL("periodical"), PERIODICALVOLUME("periodicalvolume"), PERIODICALITEM("periodicalitem"), PAGE(
			"page"), INTERNALPART("internalpart")/* , DONATOR("donator") */;

	private KrameriusModel(String value) {
		this.value = value;
	}

	private final String value;

	public String getValue() {
		return value;
	}

	public static String toString(KrameriusModel km) {
		return km.getValue();
	}

	public static KrameriusModel parseString(String s) {
		KrameriusModel[] values = KrameriusModel.values();
		for (KrameriusModel model : values) {
			if (model.getValue().equalsIgnoreCase(s))
				return model;
		}
		throw new RuntimeException("Unsupported type");
	}
}