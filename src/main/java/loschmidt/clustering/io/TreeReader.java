package loschmidt.clustering.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import loschmidt.clustering.hierarchical.Tree;

/**
 *
 * @author Jan Stourac
 */
public abstract class TreeReader {

	public Tree readTree(File in) throws IOException {
		try (FileReader fr = new FileReader(in)) {
			return readTree(fr);
		}
	}

	public abstract Tree readTree(Reader in) throws IOException;
}
