FROM maven:3.3-jdk-8

RUN mkdir -p /home/app

COPY . /home/app

EXPOSE 3000

CMD [ "javac", "/home/app/App.java"]