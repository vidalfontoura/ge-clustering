package edu.ufpr.jmetal.problem.old.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.ufpr.cluster.algorithm.Point;

public class DataInstanceReader {

	// TODO: PLEASE REFACTOR THIS METHOD ASAP
	public static List<Point> readPoints(String fileName) throws FileNotFoundException, IOException {
		List<Point> points = new ArrayList<Point>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(DataInstanceReader.class.getResourceAsStream(fileName)))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				Point point = new Point();
				// TODO: This will need to change since the databases files are
				// not in the same format
				for (int i = 0; i < values.length; i++) {
					if (i != values.length - 1) {
						point.getCoordinates().add(Double.valueOf(values[i]));
					} else {
						// TODO: Do nothing this is the class for the
						// pima-indians database
					}
				}
				points.add(point);
			}
		}
		return points;

		// Double max = Double.MIN_VALUE;
		// Double min = Double.MAX_VALUE;
		// for (Point point : points) {
		// Optional<Double> maxPoint = point.getCoordinates().stream().max((o1,
		// o2) -> o1.compareTo(o2));
		// Optional<Double> minPoint = point.getCoordinates().stream().max((o1,
		// o2) -> o2.compareTo(o1));
		// if (maxPoint.get() > max) {
		// max = maxPoint.get();
		// }
		// if (minPoint.get() < min) {
		// min = minPoint.get();
		// }
		// }
		//
		// final double maxf = max;
		// final double minf = min;
		//
		// return points.stream().map(p -> {
		//
		// List<Double> coordinates = p.getCoordinates().stream().map(c -> c =
		// (c - minf) / (maxf - minf))
		// .collect(Collectors.toList());
		// p.setCoordinates(coordinates);
		// return p;
		// }).collect(Collectors.toList());

		// return points;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Point> readPoints = DataInstanceReader.readPoints("/prima-indians-diabetes.data");
		for (Point point : readPoints) {

			List<Double> coordinates = point.getCoordinates();
			System.out.println(coordinates.toString());
			System.out.println(point.getCluster());
		}
	}
}
