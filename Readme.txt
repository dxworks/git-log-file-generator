Git Log Generator is a utility software which helps you to build git-like log files with some customizations like. This
logs can be used for some analysis on a project with no github history.

Quick Run Commands

// With all args from the command line

java -DprojectPath=<PROJECT_PATH> \
     -Dignorer=<IGNORER_FILE_PATH> \
     -DoutputFile=<OUTPUT_FILE_PATH> \
     -Dconfig.threshold.hours=<POSITIVE_INTEGER> \
     -Dconfig.threshold.minutes=<POSITIVE_INTEGER> \
     -Dconfig.threshold.seconds=<POSITIVE_INTEGER> \
     -jar logGenerator.jar

// With a yml config file

java -DprojectPath=<PROJECT_PATH> -DfileConfig=<CONFIG_FILE_PATH> -Dignorer=<IGNORER_FILE_PATH> -DoutputFile=<OUTPUT_FILE_PATH> -jar logGenerator.jar

// Build command

mvn clean install

// yml config example

threshold:
  hours: 10
  minutes: 0
  seconds: 0

// globs ignorer file example

# BLACK LISTED PATTERNS

**/*.iml
.idea/**
target/**
src/main/resources/**
.git/**

# WHITE LISTED PATTERNS

! src/main/java/**

Please check https://github.com/dxworks/git-log-file-generator/blob/master/Readme.md for more details.