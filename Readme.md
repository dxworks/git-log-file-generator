```json
   {
      "projectPath": "Path of the project",
      "config": {
        "threshold": {
          "hours": 10,
          "minutes": 60,
          "seconds": 100
        },
        "rootDir": "All files will pe relative to this path"
      },
      "fileConfig": "Only from the command line",
      "ignorer": "Path to globs file",
      "outputFile": "This is the path where we are gonna put the output"
   }
```

-DprojectPath=/Users/denisfeier/Documents/git-log-file-generator
-Dconfig.threshold.hours=100
-Dignorer=/Users/denisfeier/Documents/git-log-file-generator/src/main/resources/.globs1
-Dconfig.rootDir=/Users/denisfeier/Documents/git-log-file-generator