.PHONY: all clean dist test publish-local publish

all : clean dist

clean :
	mvn clean

dist :
	mvn compile

test :
	mvn test

publish-local :
	mvn install

publish :
	mvn deploy
