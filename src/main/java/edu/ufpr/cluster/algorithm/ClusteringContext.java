package edu.ufpr.cluster.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ClusteringContext {
	
	private int k;
	
	private Function<List<Point>, Double> distanceFunction;
	
	private List<Cluster> clusters;
	
	private List<Point> points;
	
	private int dimensions;
	
	
	public ClusteringContext(List<Point> points, Function<List<Point>, Double> distanceFunction, int k) {
		this.clusters = new ArrayList<>();
		this.points = points;
		this.distanceFunction = distanceFunction;
		this.k = k;
		
		int lastSize = points.get(0).getCoordinates().size();
		for(Point point: points) {
			int size = point.getCoordinates().size();
			if (lastSize != size) {
				throw new IllegalArgumentException("All points should have the same dimensions");
			}
			lastSize = size;
		}
		this.dimensions = lastSize;
	}
	

	public List<Cluster> getClusters() {
		if (clusters == null) {
			clusters = new ArrayList<Cluster>();
		}
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Function<List<Point>, Double> getDistanceFunction() {
		return distanceFunction;
	}

	public void setDistanceFunction(Function<List<Point>, Double> distanceFunction) {
		this.distanceFunction = distanceFunction;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}


	public int getDimensions() {
		return dimensions;
	}


	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}	
}
