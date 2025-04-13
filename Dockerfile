FROM ubuntu:latest
LABEL authors="buong"

ENTRYPOINT ["top", "-b"]