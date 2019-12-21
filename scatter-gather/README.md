# scatter-gather es example

# getting started

sbt dist
unzip target/universal/scatter-gather-0.0.1.zip -d target/universal
docker-compose up

# curl example
$ curl localhost:5000/search?q=dog,cat
