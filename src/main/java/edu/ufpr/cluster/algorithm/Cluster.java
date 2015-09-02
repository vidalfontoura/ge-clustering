package edu.ufpr.cluster.algorithm;

import java.util.ArrayList;
import java.util.List;

import edu.ufpr.cluster.algorithms.functions.DistanceFunction;

public class Cluster {


	private Point centroid;

	private List<Point> points;

	public Cluster() {}

	public Cluster(Point point) {
		this.centroid = point.copy();
	}

	public Point getCentroid() {
		if(centroid == null) {
			centroid = new Point();
		}
		return centroid;
	}

	public void setCentroid(Point point) {
		this.centroid = point.copy();
	}

	public List<Point> getPoints() {
		if (points == null) {
			points = new ArrayList<Point>();
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

		if(!getPoints().isEmpty()) {

			setCentroid(getPoints().get(0).copy());

			if(getPoints().size() != 1) {
				int dimension = getCentroid().getCoordinates().size();

				double[] sumCoordinates = new double[dimension];

				for(int i=0; i<points.size(); i++) {
					List<Double> coordinates = points.get(i).getCoordinates();

					for(int j=0; j<coordinates.size(); j++) {
						Double coordinate = coordinates.get(j);
						sumCoordinates[j] = sumCoordinates[j] + coordinate;
					}	
				}

				for(int i=0; i<sumCoordinates.length; i++) {
					double mean = sumCoordinates[i]/points.size();
					getCentroid().getCoordinates().set(i, mean);
				}
			}
		}
	}

	public boolean isEmpty() {
		return (getPoints().isEmpty() ? true : false);
	}

	@Override
	public String toString() {
		if(getPoints().size() == 0) return "(?.?, ?.?)";
		String str = " {";
		for (Point p : points) {
			str += p + "";
		}
		str += "}";
		return getCentroid().toString()+str;
	}
}
