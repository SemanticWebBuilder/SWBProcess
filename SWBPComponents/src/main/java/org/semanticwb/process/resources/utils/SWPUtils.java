package org.semanticwb.process.resources.utils;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * Clase utilitaria para los componentes de documentación de procesos.
 * 
 * @author carlos.alvarez
 * @author Hasdai Pacheco
 */
public class SWPUtils {
	public static final String FORMAT_PNG = "png";
	public static final String FORMAT_SVG = "svg";
	private static final Logger LOG = SWBUtils.getLogger(SWPUtils.class);

	/**
	 * Gets default date formatter for SWBProcess.
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getDateFormatter() {
		return new SimpleDateFormat("dd/MMM/yyyy - hh:mm:ss");
	}

	/**
	 * Copies a resource from SWBAdmin jar package loaded into memory.
	 * 
	 * @param source
	 *            SWBAdmin source file
	 * @param destination
	 *            Destination resource path
	 * @param fileName
	 *            Name of destination resource
	 * @throws IOException
	 * 			  When an error occurs on writing the content of destination file
	 */
	public static void copyFileFromSWBAdmin(String source, String destination, String fileName) throws IOException {
		InputStream inputStream = SWBPortal.getAdminFileStream(source);
		if (null != inputStream) {
			File f = new File(destination);
			if (!f.exists()) {
				f.mkdirs();
			}

			File file = new File(f.getAbsolutePath() + fileName);
			try (OutputStream outputStream = new FileOutputStream(file)) {
				byte[] buffer = new byte[1024];
				int len;
				while ((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
				}
			} catch (IOException ioex) {
				LOG.error(ioex);
				throw ioex;
			}
		}
	}

	/**
	 * Copies a source file into a target file
	 * @param sourceFile
	 * 			  Source file path
	 * @param destFile
	 * 			  Target file path
	 * @throws IOException
	 */
	public static void copyFile(String sourceFile, String destFile) throws IOException {
		// TODO: Revisar por qué es necesario esto en lugar de SWBUtils.IO.copyStream
		InputStream inStream = null;
		OutputStream outStream = null;

		try {
			File afile = new File(sourceFile);
			File bfile = new File(destFile);
			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);
			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			inStream.close();
			outStream.close();
		} catch (IOException e) {
			LOG.error("Error to copy file " + sourceFile, e);
		} finally {
			if (null != inStream) {
				inStream.close();
			}
			if (null != outStream) {
				outStream.close();
			}
		}
	}

	public static void generateImageModel(org.semanticwb.process.model.Process p, String path, String format,
			String data, double width, double height) throws IOException {
		// TODO: Cuando no hay datos del modelo, no debe hacerse nada
		FileOutputStream out = null;

		try {
			File baseDir = new File(path);
			if (!baseDir.exists()) {
				baseDir.mkdirs();
			}

			out = new FileOutputStream(baseDir + "/" + p.getId() + "." + format);
			if (format.equals(FORMAT_SVG)) {
				String svg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
						+ "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n";
				svg += data;
				svg = svg.replace("<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\">",
						"<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\" class=\"itemAware\">");
				svg = svg.replace(
						"<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\">",
						"<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\" class=\"itemAware\">");
				svg = svg.replace("style=\"display: none;\"", "");
				out.write(svg.getBytes("ISO-8859-1"));
			} else if (format.equals(FORMAT_PNG)) {
				// Fix image dimensions
				String widthKey = "width=\"";
				String heightKey = "height=\"";
				if (data.contains(widthKey)) {
					int idx = data.indexOf(widthKey);
					int idx2 = data.indexOf("\"", idx + widthKey.length());
					data = data.substring(0, idx) + widthKey + width + "\"" + data.substring(idx2 + 1, data.length());
				}
				if (data.contains(heightKey)) {
					int idx = data.indexOf(heightKey);
					int idx2 = data.indexOf("\"", idx + heightKey.length());
					data = data.substring(0, idx) + heightKey + height + "\"" + data.substring(idx2 + 1, data.length());
				}

				InputStream strStream = new ByteArrayInputStream(data.getBytes("ISO-8859-1"));
				TranscoderInput ti = new TranscoderInput(strStream/* svgFile.toURI().toString() */);
				TranscoderOutput to = new TranscoderOutput(out);

				PNGTranscoder t = new PNGTranscoder();
				t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(width) + 100);
				t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(height) + 100);
				t.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, Boolean.TRUE);
				t.transcode(ti, to);
			}
			out.flush();
		} catch (Exception e) {
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	public static void saveFile(String src, String dest) throws IOException {
		InputStream is = null;
		try (OutputStream os = new FileOutputStream(dest)) {
			URL url = new URL(src);
			is = url.openStream();

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
		} catch (Exception e) {
			LOG.error("Error on saveFile, " + e.getMessage() + ", " + e.getCause());
		}
	}
}
