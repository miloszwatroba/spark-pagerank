## PageRank for Spark

### Compiling a JAR file
Run the following command to create a JAR file.
```
sbt package
```

### Arguments
* Entry point class: PageRank
    1. Path to a file containing pages
    2. Number or iterations
    3. Number of top ranks to print
    4. Output path

### Example usage
```
/bin/spark-submit \
--master spark://spark-master:7077 \
--class PageRank \
/path/to/jarfile.jar /path/to/data.txt 10 10 path/to/output.txt
```

