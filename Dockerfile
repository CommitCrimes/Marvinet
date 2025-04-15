FROM jenkins/jenkins:lts

USER root

# Install git (for shell git clone)
RUN apt-get update && apt-get install -y git && apt-get clean

USER jenkins

# Install plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt
