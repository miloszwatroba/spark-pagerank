## PageRank for Spark
[PageRank](https://en.wikipedia.org/wiki/PageRank) algorithm implementation for Spark written in Scala.

### Compiling a JAR file
Run the following command to create a JAR file.
```
sbt package
```

### Arguments
Entry point class: PageRank
1. Path to the file containing pages list
2. Number of iterations
3. Number of top ranks to output
4. Output path

### Sample structure of the input file
This example is a head of the [Berkeley-Stanford web graph](https://snap.stanford.edu/data/web-BerkStan.html) dataset file.  
```
# Directed graph (each unordered pair of nodes is saved once): web-BerkStan.txt 
# Berkely-Stanford web graph from 2002
# Nodes: 685230 Edges: 7600595
# FromNodeId	ToNodeId
1	2
1	5
1	7
1	8
1	9
1	11
1	17
1	254913
1	438238
254913	255378
254913	255379
254913	255383
254913	255384
254913	255392
```

Please note, that just as above, you can freely use comments (#) in the the input file - thiis implementation excludes comment lines from consideration.

### Example usage
```
/bin/spark-submit \
--master spark://spark-master:7077 \
--class PageRank \
/path/to/jarfile.jar /path/to/data.txt 10 10 path/to/output.txt
```

