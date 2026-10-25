# Example de makefile pour compiler le squelette de code distribué
# Vous pouvez compléter ce makefile, mais étant donnée la taille du projet, 
# il est FORTEMENT recommandé d'utiliser un IDE!

# Organisation:
#  1) Les sources (*.java) se trouvent dans le répertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) sont générés dans le répertoire bin
#     La hiérarchie des sources (par package) est conservée.
#
#  3) Une librairie gui.jar est distribuée pour l'interface grapique. 
#     Elle se trouve dans le sous-répertoire lib.
#
# Compilation:
#  Options de javac:
#   -d : répertoire dans lequel sont générés les .class compilés
#   -sourcepath : répertoire dans lequel sont cherchés les .java
#   -classpath : répertoire dans lequel sont cherchées les classes compilées (.class et .jar)

SEP := :
ifeq ($(OS),Windows_NT)
  SEP := ;
endif
JFLAGS     := -Dsun.java2d.opengl=false -Dsun.java2d.xrender=true
SRC_DIR    := src
TEST_DIR   := test
TEST_SRC := $(wildcard test/**/*.java)
BIN_DIR    := bin
TEST_BIN_DIR   := out/test
LIBS       := lib/gui.jar
DOC_DIR    := doc/
JUNIT_LIBS := lib/junit-jupiter-api-5.14.0.jar
JUNIT_ENGINE := lib/junit-jupiter-engine-5.14.0.jar
JUNIT_API_GUARDIAN := lib/apiguardian-api-1.1.2.jar
JUNIT_CONSOLE := lib/junit-platform-console-standalone-1.12.2.jar
SOURCES    := $(shell find $(SRC_DIR) -name "*.java")
TEST_SRCS  := $(shell find $(TEST_DIR) -name "*.java")
LIB_CP = lib/*
.PHONY: all compile run clean test compile-test test-conway test-immigration test-schelling test-boids test-all doc help

all: run

help:
	@echo "Usage: make [target]"
	@echo ""
	@echo "Principales cibles disponibles:" 
	@echo "  make compile        -> compile les sources (src/) vers bin/ (inclut lib/gui.jar)"
	@echo "  make run            -> compile puis lance app.Main (UI) avec les JFLAGS (affichage)"
	@echo "  make clean          -> supprime bin/ et les sorties de test"
	@echo "  make test           -> compile les tests et exécute tous les tests (requiert jars JUnit dans lib/)"
	@echo "  make compile-test   -> compile les tests vers $(TEST_BIN_DIR) (utilise les jars JUnit listés)"
	@echo "  make test-conway    -> exécute uniquement les tests du simulateur Conway (via junit-platform-console)"
	@echo "  make doc            -> génère la javadoc dans $(DOC_DIR) (requiert javadoc)"
	@echo ""

compile:
	@mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) -sourcepath $(SRC_DIR) -classpath $(LIBS) $(SOURCES)

run: compile
	java $(JFLAGS) -classpath $(BIN_DIR)$(SEP)$(LIBS) app.Main

compile-test: compile
	@mkdir -p $(TEST_BIN_DIR)
	javac -d $(TEST_BIN_DIR) -sourcepath test:src -classpath $(BIN_DIR)$(SEP)$(LIBS)$(SEP)$(JUNIT_LIBS)$(SEP)$(JUNIT_ENGINE)$(SEP)$(JUNIT_API_GUARDIAN) $(TEST_SRCS)

test-entities: compile-test
	@echo "Running Cell tests..."
	@java -jar $(JUNIT_CONSOLE) execute --class-path $(TEST_BIN_DIR)$(SEP)$(BIN_DIR)$(SEP)$(LIBS) \
		--select-class entities.CellTest entities.BoidTest


test-conway: compile-test
	@echo "Running Conway Simulator tests..."
	@java -jar $(JUNIT_CONSOLE) execute --class-path $(TEST_BIN_DIR)$(SEP)$(BIN_DIR)$(SEP)$(LIBS) \
		--select-class systems.TestConwaySimulator

test-immigration: compile-test
	@echo "Running Immigration Simulator tests..."
	@java -jar $(JUNIT_CONSOLE) execute --class-path $(TEST_BIN_DIR)$(SEP)$(BIN_DIR)$(SEP)$(LIBS) \
		--select-class systems.TestImmigrationSimulator

test-schelling: compile-test
	@echo "Running Schelling Simulator tests..."
	@java -jar $(JUNIT_CONSOLE) execute --class-path $(TEST_BIN_DIR)$(SEP)$(BIN_DIR)$(SEP)$(LIBS) \
		--select-class systems.TestSchellingSimulator

test-boids: compile-test
	@echo "Running Boids Simulator tests..."
	@java -jar $(JUNIT_CONSOLE) execute --class-path $(TEST_BIN_DIR)$(SEP)$(BIN_DIR)$(SEP)$(LIBS) \
		--select-class systems.TestBoidsSimulator

test-all: compile-test
	@echo "Running all tests..."
	@java -jar $(JUNIT_CONSOLE) execute --class-path $(TEST_BIN_DIR)$(SEP)$(BIN_DIR)$(SEP)$(LIBS) \
		--scan-classpath

test: test-all

doc:
	javadoc -d $(DOC_DIR) -sourcepath $(SRC_DIR) -classpath "$(LIB_CP)" -subpackages app:core:entities:seed:systems:world

clean:
	rm -rf $(BIN_DIR) $(TEST_BIN_DIR) out target
