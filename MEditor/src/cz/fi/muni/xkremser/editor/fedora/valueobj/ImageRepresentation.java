/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.fedora.valueobj;

// TODO: Auto-generated Javadoc
/**
 * Reprezentace digitalniho objektu.
 *
 * @author xholcik
 */
public class ImageRepresentation {

	/** The filename. */
	private String filename;

	/** The image meta data. */
	private ImageMetaData imageMetaData;

	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the filename.
	 *
	 * @param filename the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Gets the image meta data.
	 *
	 * @return the image meta data
	 */
	public ImageMetaData getImageMetaData() {
		return imageMetaData;
	}

	/**
	 * Sets the image meta data.
	 *
	 * @param imageMetaData the new image meta data
	 */
	public void setImageMetaData(ImageMetaData imageMetaData) {
		this.imageMetaData = imageMetaData;
	}

}
