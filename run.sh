grammarFile=/clustergrammar.bnf
database=/prima-indians-diabetes.data
evaluations=60000
resultFolder=result
seed=1
population=100
minCondons=1
maxCondons=20
threads=1

crossoverProbability=0.95
mutationProbability=0.1
pruneIndex=10
pruneProbability=0.05
duplicationProbability=0.05
clusteringSeed=100

java -jar ge-clustering-jar-with-dependencies.jar \
	-g $grammarFile 								  \
	-d $database									  \
	-m $evaluations 								  \
	-r $resultFolder 								\
	-s $seed 										\
	-p $population 									\
	-minC $minCondons 								\
	-maxC $maxCondons 								\
	-t $threads										\
	-cx $crossoverProbability 						\
	-mx $mutationProbability						\
	-pi $pruneIndex									\
	-px $pruneProbability 							\
	-dx $duplicationProbability 					\
	-cs $clusteringSeed							