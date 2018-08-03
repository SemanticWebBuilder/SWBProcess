package org.semanticwb.process.resources.documentation.writers;

import java.io.FileNotFoundException;
import java.io.OutputStream;

/**
 *
 * @author hasdai
 */
public interface DocumentWriter {
    public void write(OutputStream ous);
    public void write(String path) throws FileNotFoundException;
}
