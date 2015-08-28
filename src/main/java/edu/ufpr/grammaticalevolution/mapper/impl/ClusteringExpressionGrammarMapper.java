package edu.ufpr.grammaticalevolution.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import edu.ufpr.grammaticalevolution.mapper.AbstractGrammarMapper;
import edu.ufpr.grammaticalevolution.representation.Expression;
import edu.ufpr.grammaticalevolution.representation.Node;

public class ClusteringExpressionGrammarMapper extends AbstractGrammarMapper<String> {

    protected int currentIndex;
    protected int numberOfWraps;
    protected int currentDepth;
    protected List<Node> visitedNodes;

    protected int maxDepth;

    public ClusteringExpressionGrammarMapper() {
        super();
        //TODO: check where this should be configured
        maxDepth = 100;
    }

    public ClusteringExpressionGrammarMapper(Node rootNode) {
        super(rootNode);
    }

    public ClusteringExpressionGrammarMapper(int maxNumberOfSelfLoops) {
        this.maxDepth = maxNumberOfSelfLoops;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getNumberOfWraps() {
        return numberOfWraps;
    }

    @Override
    public String interpret(List<Integer> grammarInstance) {
        currentIndex = 0;
        numberOfWraps = 0;
        currentDepth = 1;
        visitedNodes = new ArrayList<>();
        return getNodeValue(rootNode, grammarInstance);
    }

    private String getNodeValue(Node node, List<Integer> grammarInstance) {
        if (node.isTerminal()) {
            return node.getName();
        } else {
            visitedNodes.add(node);
            
            int expressionsSize = node.getExpressions().size();
            if (expressionsSize > 1) {
                currentIndex++;
                if (currentIndex >= grammarInstance.size()) {
                    currentIndex = 0;
                    numberOfWraps++;
                }
            }

            int indexToGet = grammarInstance.get(currentIndex) % expressionsSize;
            Expression expression = node.getExpressions().get(indexToGet);
            for (Node childNode : expression.getNodes()) {
                if (visitedNodes.contains(childNode)) {
                    currentDepth++;
                    break;
                }
            }
            while (currentDepth > maxDepth) {
                currentDepth--;
                grammarInstance.set(currentIndex, grammarInstance.get(currentIndex) + 1);
                indexToGet = grammarInstance.get(currentIndex) % expressionsSize;
                expression = node.getExpressions().get(indexToGet);
                for (Node childNode : expression.getNodes()) {
                    if (visitedNodes.contains(childNode)) {
                        currentDepth++;
                        break;
                    }
                }
            }

            String result = expression
                    .getNodes()
                    .stream()
                    .map(childNode -> getNodeValue(childNode, grammarInstance))
                    .collect(Collectors.joining(" "));

            visitedNodes.remove(node);

            return result;
        }
    }


}
