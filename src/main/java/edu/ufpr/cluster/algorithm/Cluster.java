package edu.ufpr.cluster.algorithm;

import java.util.ArrayList;
import java.util.List;

import edu.ufpr.cluster.algorithms.functions.DistanceFunction;

public class Cluster {

	
	private Point centroid;
	
	private List<Point> points;
	

	public Cluster(Point centroid) {
		this.centroid = centroid;
	}

	public Point getCentroid() {
		return centroid;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	public List<Point> getPoints() {
		if (points == null) {
			return new ArrayList<Point>();
		}
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	public void addPoint(Point point) {
		point.setCluster(this);
		getPoints().add(point);
	}
	
	public void updateCentroid() {
		
		int dimension = centroid.getCoordinates().size();
		double[] sumCoordinates = new double[dimension];
		for(int i=0; i<points.size(); i++) {
			
			List<Double> coordinates = points.get(i).getCoordinates();
			for(int j=0; j<coordinates.size(); j++) {
				Double coordinate = coordinates.get(j);
				sumCoordinates[j] = sumCoordinates[j] + coordinate;
			}
			
		}
		
		for(int i=0; i<sumCoordinates.length; i++) {
			double mean = sumCoordinates[i]/dimension;
			centroid.getCoordinates().set(i, mean);
		}
		
	}
	
	
	
}
