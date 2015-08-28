package edu.ufpr.cluster.algorithms.functions;

import java.util.function.Function;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;

public class UniformCentroidInitilization implements Function<ClusteringContext, Void> {

	@Override
	public Void apply(ClusteringContext context) {
		
		if (context.getClusters().size()>0) {
			throw new RuntimeException("The clusters must not be set already at this point");
		}
		
		double xMax = Double.MIN_VALUE;
		double xMin = Double.MAX_VALUE;
		double yMax = Double.MIN_VALUE;
		double yMin = Double.MAX_VALUE;
		
		for(Point point: context.getPoints()) {
			if (point.getX() > xMax) {
				xMax = point.getX();
			}
			
			if (point.getY() > yMax) {
				yMax = point.getY();
			}
			
			if (point.getX() < xMin) {
				xMin = point.getX();
			}
			
			if (point.getY() < yMin) {
				yMin = point.getY();
			}
		}
		int k = context.getK();
		double incrementX = xMax - xMin / k;
		double incrementY = yMax - yMin / k;
		
		for (int i=0; i<k; i++) {
			Cluster cluster = new Cluster(new Point(incrementX, incrementY));

			context.getClusters().add(cluster);
			incrementX = incrementX + incrementX;
			incrementY = incrementY + incrementY;
			
		}
		
		return null;
	}
	

}
