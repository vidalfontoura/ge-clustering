package edu.ufpr.cluster.algorithms.functions;

import java.util.List;
import java.util.function.Function;

import edu.ufpr.cluster.algorithm.Point;

public class ChebyshevDistanceFunction implements Function<List<Point>,Double> {

	@Override
	public Double apply(List<Point> points) {
		
		//https://lyfat.wordpress.com/2012/05/22/euclidean-vs-chebyshev-vs-manhattan-distance/
		if(points.size() > 2) {
			throw new IllegalArgumentException("Isn't possible to calculate the distance for more than 2 points: "+points.size());
		}
		
		Point p = points.get(0);
		Point q = points.get(1);
		
		double x = Math.abs(p.getX() - q.getX());
		double y = Math.abs(p.getY() - q.getY());
		
		return x>y?x:y;
	}
	

}
