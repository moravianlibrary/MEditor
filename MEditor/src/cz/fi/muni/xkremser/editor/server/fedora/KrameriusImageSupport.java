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
package cz.fi.muni.xkremser.editor.server.fedora;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.swing.JPanel;
import javax.xml.xpath.XPathExpressionException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.lizardtech.djvu.DjVuOptions;
import com.lizardtech.djvu.DjVuPage;
import com.lizardtech.djvubean.DjVuImage;

import cz.fi.muni.xkremser.editor.server.fedora.utils.IOUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class KrameriusImageSupport.
 */
public class KrameriusImageSupport {

	static {
		// disable djvu convertor verbose logging
		DjVuOptions.out = new java.io.PrintStream(new java.io.OutputStream() {
			@Override
			public void write(int b) {
			}
		});
	}

	/**
	 * Read image.
	 *
	 * @param uuid the uuid
	 * @param stream the stream
	 * @param fedoraAccess the fedora access
	 * @param page the page
	 * @param mime the mime
	 * @return the image
	 * @throws XPathExpressionException the x path expression exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Image readImage(String uuid, String stream, FedoraAccess fedoraAccess, int page, ImageMimeType mime) throws XPathExpressionException,
			IOException {
		URL url = new URL("fedora", "", 0, uuid + "/" + stream, new Handler(fedoraAccess));
		return readImage(url, mime, page);
	}

	/**
	 * Read image.
	 *
	 * @param url the url
	 * @param type the type
	 * @param page the page
	 * @return the image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Image readImage(URL url, ImageMimeType type, int page) throws IOException {
		if (type.javaNativeSupport()) {
			return ImageIO.read(url.openStream());
		} else if ((type.equals(ImageMimeType.DJVU)) || (type.equals(ImageMimeType.VNDDJVU)) || (type.equals(ImageMimeType.XDJVU))) {
			com.lizardtech.djvu.Document doc = new com.lizardtech.djvu.Document(url);
			doc.setAsync(false);
			DjVuPage[] p = new DjVuPage[1];
			// read page from the document - index 0, priority 1, favorFast true
			int size = doc.size();
			if ((page != 0) && (page >= size)) {
				page = 0;
			}
			p[0] = doc.getPage(page, 1, true);
			p[0].setAsync(false);
			DjVuImage djvuImage = new DjVuImage(p, true);
			Rectangle pageBounds = djvuImage.getPageBounds(0);
			Image[] images = djvuImage.getImage(new JPanel(), new Rectangle(pageBounds.width, pageBounds.height));
			if (images.length == 1) {
				Image img = images[0];
				return img;
			} else
				return null;
		} else if (type.equals(ImageMimeType.PDF)) {
			PDDocument document = null;
			try {
				document = PDDocument.load(url.openStream());
				int resolution = 96;
				List pages = document.getDocumentCatalog().getAllPages();
				PDPage pdPage = (PDPage) pages.get(page);
				BufferedImage image = pdPage.convertToImage(BufferedImage.TYPE_INT_RGB, resolution);
				return image;
			} finally {
				if (document != null) {
					document.close();
				}
			}
		} else
			throw new IllegalArgumentException("unsupported mimetype '" + type.getValue() + "'");
	}

	/**
	 * Write image to stream.
	 *
	 * @param scaledImage the scaled image
	 * @param javaFormat the java format
	 * @param os the os
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeImageToStream(BufferedImage scaledImage, String javaFormat, OutputStream os) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(scaledImage, javaFormat, bos);
		IOUtils.copyStreams(new ByteArrayInputStream(bos.toByteArray()), os);
	}

	/**
	 * Write full image to stream.
	 *
	 * @param scaledImage the scaled image
	 * @param javaFormat the java format
	 * @param os the os
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeFullImageToStream(Image scaledImage, String javaFormat, OutputStream os) throws IOException {
		BufferedImage bufImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
		Graphics gr = bufImage.getGraphics();
		gr.drawImage(scaledImage, 0, 0, null);
		gr.dispose();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufImage, javaFormat, bos);
		IOUtils.copyStreams(new ByteArrayInputStream(bos.toByteArray()), os);
	}

	/**
	 * Gets the smaller image.
	 *
	 * @param scaledImage the scaled image
	 * @param maxWidth the max width
	 * @param maxHeight the max height
	 * @return the smaller image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BufferedImage getSmallerImage(Image scaledImage, int maxWidth, int maxHeight) throws IOException {
		int width = scaledImage.getWidth(null);
		int height = scaledImage.getHeight(null);
		BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics gr = bufImage.getGraphics();
		gr.drawImage(scaledImage, 0, 0, null);
		if ((width > maxWidth) || height > maxHeight) {
			width /= 2;
			height /= 2;
		}
		if ((width > maxWidth) || height > maxHeight) {
			width /= 2;
			height /= 2;
		}
		if ((width > maxWidth) || height > maxHeight) {
			width /= 2;
			height /= 2;
		}
		if ((width > maxWidth) || height > maxHeight) {
			width /= 2;
			height /= 2;
		}
		if ((width > maxWidth) || height > maxHeight) {
			width /= 2;
			height /= 2;
		}
		return getScaledInstanceJava2D(bufImage, width, height, "", false);
	}

	/**
	 * Write image to stream.
	 *
	 * @param scaledImage the scaled image
	 * @param javaFormat the java format
	 * @param os the os
	 * @param quality the quality
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeImageToStream(Image scaledImage, String javaFormat, OutputStream os, float quality) throws IOException {
		int width = scaledImage.getWidth(null);
		int height = scaledImage.getHeight(null);
		BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics gr = bufImage.getGraphics();
		gr.drawImage(scaledImage, 0, 0, null);
		if (width > 1200 || height > 1200) {
			width /= 2;
			height /= 2;
		}
		if (width > 1200 || height > 1200) {
			width /= 2;
			height /= 2;
		}
		if (width > 1200 || height > 1200) {
			width /= 2;
			height /= 2;
		}
		BufferedImage bufImage2 = getScaledInstanceJava2D(bufImage, width, height, "", false);
		gr.dispose();

		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(javaFormat);
		if (iter.hasNext()) {
			ImageWriter writer = iter.next();
			ImageWriteParam iwp = writer.getDefaultWriteParam();
			iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwp.setCompressionQuality(quality); // an integer between 0 and 1
			writer.setOutput(os);
			IIOImage image = new IIOImage(bufImage2, null, null);
			writer.write(null, image, iwp);
			writer.dispose();

		} else
			throw new IOException("No writer for format '" + javaFormat + "'");

	}

	// public static Image scale(Image img, int targetWidth, int targetHeight) {
	// KConfiguration config = KConfiguration.getInstance();
	// ScalingMethod method =
	// ScalingMethod.valueOf(config.getProperty("scalingMethod",
	// "BICUBIC_STEPPED"));
	// //
	// System.out.println("SCALE:"+method+" width:"+targetWidth+" height:"+targetHeight);
	// switch (method) {
	// case REPLICATE:
	// return img.getScaledInstance(targetWidth, targetHeight,
	// Image.SCALE_REPLICATE);
	// case AREA_AVERAGING:
	// return img.getScaledInstance(targetWidth, targetHeight,
	// Image.SCALE_AREA_AVERAGING);
	// case BILINEAR:
	// return getScaledInstanceJava2D(toBufferedImage(img), targetWidth,
	// targetHeight, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false);
	// case BICUBIC:
	// return getScaledInstanceJava2D(toBufferedImage(img), targetWidth,
	// targetHeight, RenderingHints.VALUE_INTERPOLATION_BICUBIC, false);
	// case NEAREST_NEIGHBOR:
	// return getScaledInstanceJava2D(toBufferedImage(img), targetWidth,
	// targetHeight, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, false);
	// case BILINEAR_STEPPED:
	// return getScaledInstanceJava2D(toBufferedImage(img), targetWidth,
	// targetHeight, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
	// case BICUBIC_STEPPED:
	// return getScaledInstanceJava2D(toBufferedImage(img), targetWidth,
	// targetHeight, RenderingHints.VALUE_INTERPOLATION_BICUBIC, true);
	// case NEAREST_NEIGHBOR_STEPPED:
	// return getScaledInstanceJava2D(toBufferedImage(img), targetWidth,
	// targetHeight, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, true);
	// }
	// return null;
	// }

	/**
	 * Convenience method that returns a scaled instance of the provided.
	 *
	 * @param img the original image to be scaled
	 * @param targetWidth the desired width of the scaled instance, in pixels
	 * @param targetHeight the desired height of the scaled instance, in pixels
	 * @param hint one of the rendering hints that corresponds to
	 * @param higherQuality if true, this method will use a multi-step scaling technique that
	 * provides higher quality than the usual one-step technique (only
	 * useful in downscaling cases, where {@code targetWidth} or
	 * @return a scaled version of the original {@code BufferedImage}
	 * {@code BufferedImage}.
	 * {@code RenderingHints.KEY_INTERPOLATION} (e.g.
	 * {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
	 * {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
	 * {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
	 * {@code targetHeight} is smaller than the original dimensions, and
	 * generally only when the {@code BILINEAR} hint is specified)
	 */
	private static BufferedImage getScaledInstanceJava2D(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {

		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = img;
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w > targetWidth || h > targetHeight);

		return ret;
	}

	/**
	 * To buffered image.
	 *
	 * @param img the img
	 * @return the buffered image
	 */
	private static BufferedImage toBufferedImage(Image img) {
		BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return bufferedImage;
	}

	/**
	 * The Enum ScalingMethod.
	 */
	public static enum ScalingMethod {
		
		/** The REPLICATE. */
		REPLICATE, 
 /** The ARE a_ averaging. */
 AREA_AVERAGING, 
 /** The BILINEAR. */
 BILINEAR, 
 /** The BICUBIC. */
 BICUBIC, 
 /** The NEARES t_ neighbor. */
 NEAREST_NEIGHBOR, 
 /** The BILINEA r_ stepped. */
 BILINEAR_STEPPED, 
 /** The BICUBI c_ stepped. */
 BICUBIC_STEPPED, 
 /** The NEARES t_ neighbo r_ stepped. */
 NEAREST_NEIGHBOR_STEPPED
	}

}
