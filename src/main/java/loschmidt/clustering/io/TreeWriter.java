package loschmidt.clustering.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import loschmidt.clustering.hierarchical.Tree;

/**
 *
 * @author Jan Stourac
 */
public abstract class TreeWriter {

	public void write(Tree tree, File out) throws IOException {
		try (FileWriter fw = new FileWriter(out)) {
			write(tree, fw);
		}
	}

	public abstract void write(Tree tree, Writer out) throws IOException;
}
