/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.valueobj;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * Value object pro Dublin core stream.
 *
 * @author xholcik
 */
public class DublinCore implements IsSerializable {

	/** The title. */
	private String title;

	/** The identifier. */
	private List<String> identifier;

	/** The creator. */
	private List<String> creator;

	/** The publisher. */
	private List<String> publisher;

	/** The contributor. */
	private List<String> contributor;

	/** The date. */
	private String date;

	/** The language. */
	private String language;

	/** The description. */
	private String description;

	/** The format. */
	private String format;

	/** The subject. */
	private List<String> subject;

	/** The type. */
	private String type;

	/** The rights. */
	private String rights;

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the creator.
	 *
	 * @return the creator
	 */
	public List<String> getCreator() {
		return creator;
	}

	/**
	 * Sets the creator.
	 *
	 * @param creator the new creator
	 */
	public void setCreator(List<String> creator) {
		this.creator = creator;
	}

	/**
	 * Gets the publisher.
	 *
	 * @return the publisher
	 */
	public List<String> getPublisher() {
		return publisher;
	}

	/**
	 * Sets the publisher.
	 *
	 * @param publisher the new publisher
	 */
	public void setPublisher(List<String> publisher) {
		this.publisher = publisher;
	}

	/**
	 * Gets the contributor.
	 *
	 * @return the contributor
	 */
	public List<String> getContributor() {
		return contributor;
	}

	/**
	 * Sets the contributor.
	 *
	 * @param contributor the new contributor
	 */
	public void setContributor(List<String> contributor) {
		this.contributor = contributor;
	}

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public List<String> getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(List<String> identifier) {
		this.identifier = identifier;
	}

	/**
	 * Adds the identifier.
	 *
	 * @param id the id
	 */
	public void addIdentifier(String id) {
		if (id == null || "".equals(id))
			return;
		if (this.identifier == null) {
			this.identifier = new ArrayList<String>();
		}
		this.identifier.add(id);
	}

	/**
	 * Adds the qualified identifier.
	 *
	 * @param prefix the prefix
	 * @param id the id
	 */
	public void addQualifiedIdentifier(String prefix, String id) {
		if (id == null || "".equals(id))
			return;
		if (this.identifier == null) {
			this.identifier = new ArrayList<String>();
		}
		this.identifier.add(prefix + ":" + id);
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the format.
	 *
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Sets the format.
	 *
	 * @param format the new format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public List<String> getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(List<String> subject) {
		this.subject = subject;
	}

	/**
	 * Adds the subject.
	 *
	 * @param subj the subj
	 */
	public void addSubject(String subj) {
		if (subj == null || "".equals(subj))
			return;
		if (this.subject == null) {
			this.subject = new ArrayList<String>();
		}
		this.subject.add(subj);
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the rights.
	 *
	 * @return the rights
	 */
	public String getRights() {
		return rights;
	}

	/**
	 * Sets the rights.
	 *
	 * @param rights the new rights
	 */
	public void setRights(String rights) {
		this.rights = rights;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DublinCore [title=" + title + ", identifier=" + identifier + ", creator=" + creator + ", publisher=" + publisher + ", contributor=" + contributor
				+ ", date=" + date + ", language=" + language + ", description=" + description + ", format=" + format + ", subject=" + subject + ", type=" + type
				+ ", rights=" + rights + "]";
	}

}
