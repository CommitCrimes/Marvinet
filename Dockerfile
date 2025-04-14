FROM jenkins/jenkins:lts

USER root

# Copie le fichier plugins.txt dans l’image
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

# Installation des plugins via jenkins-plugin-cli
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

USER jenkins
