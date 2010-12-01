package cz.fi.muni.xkremser.editor.client.metadata;

import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;

public interface RelatedItemHolder {

	ModsTypeClient getMods();

	ListOfListOfSimpleValuesHolder getRelatedItemAttributeHolder();

}
