package cz.fi.muni.xkremser.editor.client.gin;

import javax.inject.Provider;

import com.google.gwt.core.client.GWT;
import com.google.inject.Singleton;

import cz.fi.muni.xkremser.editor.client.LangConstants;

@Singleton
public class LangProvider implements Provider<LangConstants> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.Provider#get()
	 */
	@Override
	public LangConstants get() {
		return GWT.create(LangConstants.class);
	}

}
