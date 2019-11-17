FROM hseeberger/scala-sbt
COPY . /app
WORKDIR /app
RUN sbt dist \
 && unzip target/universal/sqs-0.1.zip
CMD sqs-0.1/bin/sqs-add-main
