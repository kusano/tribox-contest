FROM ubuntu:24.04

RUN ln -sf /usr/share/zoneinfo/Asia/Tokyo /etc/localtime
RUN apt-get update && apt-get install -y \
    curl \
    openjdk-21-jre \
    nodejs \
    npm \
    cron \
    sudo \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN useradd --create-home app
RUN mkdir /app
RUN chown -R app:app /app
WORKDIR /app
USER app

RUN curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz | gzip -d > cs && \
    chmod +x cs && \
    ./cs setup -y && \
    rm cs
ENV PATH="${PATH}:/home/app/.local/share/coursier/bin"

COPY --chown=app:app contestmanager/package.json contestmanager/package-lock.json  /app/contestmanager/
RUN npm install --prefix contestmanager
COPY --chown=app:app contestmanager/ /app/contestmanager/
RUN ln -s /data/config.js /app/contestmanager/config.js
RUN ln -s /data/serviceAccountKey.json /app/contestmanager/secret/serviceAccountKey.json

COPY --chown=app:app app/ app/
COPY --chown=app:app conf/logback.xml conf/
COPY --chown=app:app conf/routes conf/
COPY --chown=app:app project/ project/
COPY --chown=app:app public/ public/
COPY --chown=app:app build.sbt .

RUN sbt clean stage

COPY --chown=app:app docker/crontab.txt .
RUN crontab crontab.txt

EXPOSE 9000

USER root
CMD cron && sudo -u app target/universal/stage/bin/contest -Dconfig.file=/data/prod.conf
