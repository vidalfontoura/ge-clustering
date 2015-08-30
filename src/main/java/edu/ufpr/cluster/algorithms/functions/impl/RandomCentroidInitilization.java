package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.function.Function;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.InitilizationFunction;

public class RandomCentroidInitilization extends InitilizationFunction {

	@Override
	public Void apply(ClusteringContext context) {

		if (context.getClusters().size()>0) {
			throw new RuntimeException("The clusters must not be set already at this point");
		}
		
		int k = context.getK();
		if (k == 0) {
			//TODO: need to put this configuration in other place
			k = JMetalRandom.getInstance().nextInt(2, 10);
		}
		
		for(int i=0; i<k; i++ ) {
			//TODO: check how the bound should be configured. I suspect that the bound should be in the range of the problem
			double x = JMetalRandom.getInstance().nextDouble(2, 10);
			double y = JMetalRandom.getInstance().nextDouble(2, 10);
			//TODO: May need to check if the points are the same or very near?
			Cluster cluster = new Cluster(new Point(x, y));
			
			context.getClusters().add(cluster);
		}
		return null;
	}

}
