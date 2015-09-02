package edu.ufpr.cluster.algorithms.functions.impl;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.InitilizationFunction;

public class RandomCentroidInitilization extends InitilizationFunction {

	@Override
	public void apply(ClusteringContext context) {

		if (context.getClusters().size()>0) {
			throw new RuntimeException("The clusters must not be set already at this point");
		}
		
		int k = context.getK();
		if (k == 0) {
			//TODO: need to put this configuration in other place (2,10)
			k = JMetalRandom.getInstance().nextInt(2, 10);
		}
		
		int dimension = context.getDimensions();
		for(int i=0; i<k; i++ ) {
			//
			Point point = new Point();
			for (int j = 0; j<dimension; j++) {
				//TODO: check how the bound should be configured. I suspect that the bound should be in the range of the problem
				double coordinate = JMetalRandom.getInstance().nextDouble(2,10);
				point.getCoordinates().add(coordinate);
			}
			//TODO: May need to check if the points are the same or very near?
			Cluster cluster = new Cluster(point);
			
			context.getClusters().add(cluster);
		}
	}

}
