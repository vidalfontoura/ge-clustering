package edu.ufpr.jmetal.algorithm.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.ge.mapper.impl.SymbolicExpressionGrammarMapper;
import edu.ufpr.ge.operators.crossover.SinglePointCrossoverVariableLength;
import edu.ufpr.ge.operators.mutation.DuplicationMutation;
import edu.ufpr.ge.operators.mutation.IntegerMutation;
import edu.ufpr.ge.operators.mutation.PruneMutation;
import edu.ufpr.jmetal.algorithm.impl.GrammaticalEvolutionAlgorithm;
import edu.ufpr.jmetal.problem.ClusteringProblem;
import edu.ufpr.jmetal.solution.impl.VariableIntegerSolution;

public class ClusteringExperiment extends AbstractAlgorithmRunner {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		JMetalRandom.getInstance().setSeed(100);

		String grammarFile = "src/main/resources/clustergrammar.bnf";
		String dataClassificationFile = "src/main/resources/prima-indians-diabetes.data";
		double crossoverProbability = 1.0;
		double mutationProbability = 0.1;
		double pruneMutationProbability = 0.05;
		double duplicationProbability = 0.05;
		int pruneIndex = 4;
		int maxEvaluations = 10000;
		int populationSize = 6;

		ClusteringProblem problem = new ClusteringProblem(grammarFile, dataClassificationFile, 1, 20);

		CrossoverOperator<VariableIntegerSolution> crossoverOperator = new SinglePointCrossoverVariableLength(
				crossoverProbability);
		MutationOperator<VariableIntegerSolution> mutationOperator = new IntegerMutation(mutationProbability);
		SelectionOperator selectionOperator = new BinaryTournamentSelection<>();

		PruneMutation pruneMutationOperator = new PruneMutation(pruneMutationProbability, pruneIndex);
		DuplicationMutation duplicationMutationOperator = new DuplicationMutation(duplicationProbability);

		GrammaticalEvolutionAlgorithm algorithm = new GrammaticalEvolutionAlgorithm(problem, maxEvaluations,
				populationSize, crossoverOperator, mutationOperator, selectionOperator, pruneMutationOperator,
				duplicationMutationOperator, new SequentialSolutionListEvaluator());

		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
		VariableIntegerSolution solution = algorithm.getResult();
		long computingTime = algorithmRunner.getComputingTime();

		SymbolicExpressionGrammarMapper mapper = new SymbolicExpressionGrammarMapper();
		mapper.loadGrammar("src/main/resources/clustergrammar.bnf");

		System.out.println("Total time of execution: " + computingTime);
		System.out.println("Solution: " + solution.getObjective(0));
		System.out.println("Variables: " + mapper.interpret(solution));

	}

}
