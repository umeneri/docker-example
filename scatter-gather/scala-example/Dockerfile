FROM hseeberger/scala-sbt
COPY . /app
WORKDIR /app
#RUN sbt dist \
# && unzip target/universal/scatter-gather-0.0.1.zip
CMD target/universal/scatter-gather-0.0.1/bin/scatter-gather
