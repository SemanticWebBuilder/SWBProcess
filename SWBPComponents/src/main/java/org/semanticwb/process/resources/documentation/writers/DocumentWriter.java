package org.semanticwb.process.resources.documentation.writers;

import java.io.FileNotFoundException;
import java.io.OutputStream;

/**
 *
 * @author hasdai
 */
public interface DocumentWriter {
    void write(OutputStream ous);
    void write(String path) throws FileNotFoundException;
}
