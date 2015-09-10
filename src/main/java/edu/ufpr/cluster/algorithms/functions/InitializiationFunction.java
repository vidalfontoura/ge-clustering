package edu.ufpr.cluster.algorithms.functions;

import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.random.ClusteringRandom;

public abstract class InitializiationFunction implements Function<ClusteringContext> {

	public InitializiationFunction(int initialK) {
		if (initialK == 0) {
			// TODO: need to set this bound in other place
			this.initialK = ClusteringRandom.getInstance().nextInt(2, 10);
		} else {
			this.initialK = initialK;
		}

	}

	private int initialK;

	public int getInitialK() {
		return initialK;
	}

	public void setInitialK(int initialK) {
		this.initialK = initialK;
	}

}
