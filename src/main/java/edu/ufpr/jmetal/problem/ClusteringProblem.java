package edu.ufpr.jmetal.problem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;
import org.uma.jmetal.solution.IntegerSolution;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringAlgorithm;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.ge.mapper.AbstractGrammarMapper;
import edu.ufpr.ge.mapper.impl.ClusteringExpressionGrammarMapper;
import edu.ufpr.jmetal.solution.impl.VariableIntegerSolution;

public class ClusteringProblem extends AbstractGrammaticalEvolutionProblem {
	
	private static final long serialVersionUID = 1L;

	private List<ClassifiedPoint> classifiedPoints;
	
	private List<Point> points;
	
	private int minCondons;
	private int maxCondons;
	
	public ClusteringProblem(String grammarFile, String dataClassificationFile, int minCondons, int maxCondons) throws FileNotFoundException, IOException {
		
		super(new ClusteringExpressionGrammarMapper(),grammarFile);
		
		this.classifiedPoints = DataInstanceReader.readPoints(dataClassificationFile);
		this.points = this.classifiedPoints.stream().map(c -> c.getPoint()).collect(Collectors.toList());
		this.maxCondons = maxCondons;
		this.minCondons = minCondons;
				
	}

	@Override
	public int getNumberOfVariables() {
		return 0;
	}

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	@Override
	public int getNumberOfConstraints() {
		return 0;
	}

	@Override
	public String getName() {
		return "Clustering GE Integer Problem";
	}
	
	public void buildClusteringAlgorithm() {
		
	}

	@Override
	public void evaluate(VariableIntegerSolution solution) {
		int numberOfVariables = solution.getNumberOfVariables();
		
		List<Integer> clusteringSolution = new ArrayList<>();	 
		for(int i=0; i<numberOfVariables; i++) {
			clusteringSolution.add(solution.getVariableValue(i));
		}
		
		ClusteringAlgorithm clusteringAlgorithm = (ClusteringAlgorithm) mapper.interpret(clusteringSolution);
		clusteringAlgorithm.setPoints(points);
		
		List<Cluster> clusters = clusteringAlgorithm.execute();
		System.out.println(clusters.size());
		//Calculate the fitness somehow here
		
		solution.setObjective(0, 10);
		
		
	}

	@Override
	public VariableIntegerSolution createSolution() {
		return new VariableIntegerSolution(this, minCondons, maxCondons);
	}

	 

}
