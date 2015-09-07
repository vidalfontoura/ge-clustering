package edu.ufpr.jmetal.problem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.ufpr.cluster.algorithm.ClusteringAlgorithm;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.ge.mapper.impl.ClusteringExpressionGrammarMapper;
import edu.ufpr.jmetal.problem.old.impl.DataInstanceReader;
import edu.ufpr.jmetal.solution.impl.VariableIntegerSolution;

public class ClusteringProblem extends AbstractGrammaticalEvolutionProblem {

	private static final long serialVersionUID = 1L;

	private int minCondons;
	private int maxCondons;

	private FitnessFunction fitnessFunction;

	private List<Point> points;

	public ClusteringProblem(String grammarFile, String dataSetFile, int minCondons, int maxCondons)
			throws FileNotFoundException, IOException {

		super(new ClusteringExpressionGrammarMapper(), grammarFile);
		this.maxCondons = maxCondons;
		this.minCondons = minCondons;
		this.points = DataInstanceReader.readPoints(dataSetFile).stream().map(c -> c.getPoint())
				.collect(Collectors.toList());
		this.fitnessFunction = new FitnessFunction();

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
		for (int i = 0; i < numberOfVariables; i++) {
			System.out.print(solution.getVariableValue(i) + ",");
			clusteringSolution.add(solution.getVariableValue(i));
		}

		ClusteringAlgorithm clusteringAlgorithm = (ClusteringAlgorithm) mapper.interpret(clusteringSolution);
		// Clean up points before the execution
		points.stream().forEach(p -> {
			p.clearCluster();
		});
		clusteringAlgorithm.setPoints(points);
		ClusteringContext clusteringContext = clusteringAlgorithm.execute();

		Double fitness = this.fitnessFunction.apply(clusteringContext);
		System.out.println("- Fitness: " + fitness);
		solution.setObjective(0, fitness);

	}

	@Override
	public VariableIntegerSolution createSolution() {
		return new VariableIntegerSolution(this, minCondons, maxCondons);
	}

}
