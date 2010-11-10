/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.valueobj.metadata;

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
	private List<String> title;

	/** The identifier. */
	private List<String> identifier;

	private List<String> coverage;

	private List<String> relation;

	private List<String> source;

	/** The creator. */
	private List<String> creator;

	/** The publisher. */
	private List<String> publisher;

	/** The contributor. */
	private List<String> contributor;

	/** The date. */
	private List<String> date;

	/** The language. */
	private List<String> language;

	/** The description. */
	private List<String> description;

	/** The format. */
	private List<String> format;

	/** The subject. */
	private List<String> subject;

	/** The type. */
	private List<String> type;

	/** The rights. */
	private List<String> rights;

	/**
	 * Adds the identifier.
	 * 
	 * @param id
	 *          the id
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
	 * @param prefix
	 *          the prefix
	 * @param id
	 *          the id
	 */
	public void addQualifiedIdentifier(String prefix, String id) {
		if (id == null || "".equals(id))
			return;
		if (this.identifier == null) {
			this.identifier = new ArrayList<String>();
		}
		this.identifier.add(prefix + ":" + id);
	}

	public void addTitle(String title) {
		if (title == null || "".equals(title))
			return;
		if (this.title == null) {
			this.title = new ArrayList<String>();
		}
		this.title.add(title);
	}

	public List<String> getTitle() {
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public List<String> getIdentifier() {
		return identifier;
	}

	public void setIdentifier(List<String> identifier) {
		this.identifier = identifier;
	}

	public List<String> getCreator() {
		return creator;
	}

	public void setCreator(List<String> creator) {
		this.creator = creator;
	}

	public List<String> getPublisher() {
		return publisher;
	}

	public void setPublisher(List<String> publisher) {
		this.publisher = publisher;
	}

	public List<String> getContributor() {
		return contributor;
	}

	public void setContributor(List<String> contributor) {
		this.contributor = contributor;
	}

	public List<String> getDate() {
		return date;
	}

	public void setDate(List<String> date) {
		this.date = date;
	}

	public List<String> getLanguage() {
		return language;
	}

	public void setLanguage(List<String> language) {
		this.language = language;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public List<String> getFormat() {
		return format;
	}

	public void setFormat(List<String> format) {
		this.format = format;
	}

	public List<String> getSubject() {
		return subject;
	}

	public void setSubject(List<String> subject) {
		this.subject = subject;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public List<String> getRights() {
		return rights;
	}

	public void setRights(List<String> rights) {
		this.rights = rights;
	}

	public List<String> getCoverage() {
		return coverage;
	}

	public void setCoverage(List<String> coverage) {
		this.coverage = coverage;
	}

	public List<String> getRelation() {
		return relation;
	}

	public void setRelation(List<String> relation) {
		this.relation = relation;
	}

	public List<String> getSource() {
		return source;
	}

	public void setSource(List<String> source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "DublinCore [title=" + title + ", identifier=" + identifier + ", coverage=" + coverage + ", relation=" + relation + ", source=" + source
				+ ", creator=" + creator + ", publisher=" + publisher + ", contributor=" + contributor + ", date=" + date + ", language=" + language + ", description="
				+ description + ", format=" + format + ", subject=" + subject + ", type=" + type + ", rights=" + rights + "]";
	}

}
