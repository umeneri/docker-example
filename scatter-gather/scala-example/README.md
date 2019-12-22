# ref
sbt dist:
[sbt Reference Manual — sbt by example](https://www.scala-sbt.org/1.x/docs/sbt-by-example.html#Reload+and+create+a+.zip+distribution)


# Install
```
sbt dist
unzip target/universal/scatter-gather-0.0.1.zip -d target/universal
docker build . -t scatter-gather
docker-compose up
```

# curl example
$ curl localhost:5000/search?q=dog,cat

