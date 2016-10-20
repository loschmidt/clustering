package loschmidt.clustering;

/**
 *
 * @author Jan Stourac
 */
public class ClusteringException extends Exception {

	public ClusteringException() {
	}

	public ClusteringException(Throwable t) {
		super(t);
	}

	public ClusteringException(String msg) {
		super(msg);
	}

	public ClusteringException(String msg, Throwable t) {
		super(msg, t);
	}
}
