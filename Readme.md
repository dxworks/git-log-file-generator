# Git Log Generator

Git Log Generator is a utility software which helps you to build git-like log files with some customizations like. This 
logs can be used for some analysis on a project with no github history.

## All Config Options

Here we have the config options and some examples.

   * From the command line we can provide:
        * projectPath - "Path of the project",
        * config (optional)
            * threshold - "A threshold which helps you with the commits order"
                * hours - "Some integer number which describes threshold in hours"
                * minutes - "Some integer number which describes threshold in minutes"
                * seconds - "Some integer number which describes threshold in seconds"
            * rootDir - "This is a path used most of the time for the ignorer, 
            sets the base path for the globs used in ignorer. (Most of the time it's the same as the projectPath)"
        * ignorer(optional) - "Path to globs file, a unix feature for sorting files. We use it like a gitignore file to build the logs"
        * outputFile(optional) - "Path to a file where we export the logs. (By default the output is STDOUT)"
        * fileConfig(optional) - "Path to a YML file. For the ease of use we can add all the configs in a YML file.",

### Run examples

Run with all the configs from the command line.

```
java -DprojectPath=<PROJECT_PATH> \
     -Dignorer=<IGNORER_FILE_PATH> \
     -DoutputFile=<OUTPUT_FILE_PATH> \
     -Dconfig.threshold.hours=<POSITIVE_INTEGER> \
     -Dconfig.threshold.minutes=<POSITIVE_INTEGER> \
     -Dconfig.threshold.seconds=<POSITIVE_INTEGER> \
     -jar ./target/logGenerator-jar-with-dependencies.jar
```

Run with a config file.

```
java -DprojectPath=<PROJECT_PATH> -DfileConfig=<CONFIG_FILE_PATH> -Dignorer=<IGNORER_FILE_PATH> -DoutputFile=<OUTPUT_FILE_PATH> -jar ./target/logGenerator-jar-with-dependencies.jar
```

### YML Config File Example

```yaml
threshold:
  hours: 10
  minutes: 0
  seconds: 0
rootDir: "PATH TO YOUR ROOT DIR"
```

### GLOBS Ignorer File Example (For a basic java app)

```txt
# BLACK LISTED PATTERNS

**/*.iml
.idea/**
target/**
src/main/resources/**
.git/**

# WHITE LISTED PATTERNS

! src/main/java/**
```
### GLOBS Ignorer File Example (For a basic node.js typescript app)
```txt 
node_modules/**
.git/**
build/**
```

The glogs are a unix feature used to find patterns in paths. 
In this project we use them to mark some paths in the project
as unwanted. For more specific details on globs you can check 
the [MANUAL PAGE](https://man7.org/linux/man-pages/man7/glob.7.html) 
or the [WIKI](https://en.wikipedia.org/wiki/Glob_(programming))

### Output Example
 
Let's take a look of what we can get from a basic project. For this project I used the following globs:

```txt 
node_modules/**
.git/**
build/**
```
 
And the output is:
 
 ```txt
 commit:waKSOSexPPkHUaPUQNhLr6CrsPsW8KgJLv0gdBMEvp
 author:denisfeier
 email:denisfeier@generated.com
 date:Fri, Jul 03 2020 15:26:09
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	mI6LCKO	A	README.md
 :000000	000000	0000000	1c6g3yc	A	src/migration/.gitkeep
 :000000	000000	0000000	dnpuNbm	A	src/subscriber/.gitkeep
 13	0	README.md
 0	0	src/migration/.gitkeep
 0	0	src/subscriber/.gitkeep
 
 commit:ZDJNYxiyWXaYZl0HmzyjFAfXHRQzMcnnPms6fsAFjX
 author:denisfeier
 email:denisfeier@generated.com
 date:Fri, Jul 03 2020 15:29:32
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	LfyXP34	A	package-lock.json
 1354	0	package-lock.json
 
 commit:hFlwajup2kuqX7Gcb753QPATzjEePGuWZvXsoF97bx
 author:denisfeier
 email:denisfeier@generated.com
 date:Fri, Jul 03 2020 16:00:40
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	mawsQD4	A	src/migration/1593781240088-post.ts
 11	0	src/migration/1593781240088-post.ts
 
 commit:lR4ZtYQRRRIxRNzvjX9retXYSAKElzWu4GksxDgEoN
 author:denisfeier
 email:denisfeier@generated.com
 date:Fri, Jul 03 2020 16:00:58
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	cU935E9	A	src/migration/1593781258881-category.ts
 25	0	src/migration/1593781258881-category.ts
 
 commit:m1CdliaOL84xUYJU5BQ38Yq6BzqBQUMN8ay5oEIo03
 author:denisfeier
 email:denisfeier@generated.com
 date:Fri, Jul 03 2020 16:50:01
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	WoAtKhP	A	src/migration/1593784201962-user.ts
 11	0	src/migration/1593784201962-user.ts
 
 commit:mcdPYEMik5pV8GdqPTTmwiGWi2T9ZizcNwrwHRKHEg
 author:denisfeier
 email:denisfeier@generated.com
 date:Fri, Jul 03 2020 21:13:42
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	PyRjqf9	A	.gitignore
 :000000	000000	0000000	BOkDfzi	A	.vscode/settings.json
 :000000	000000	0000000	Dau3iTV	A	ormconfig.json
 :000000	000000	0000000	SdyAuEF	A	package.json
 :000000	000000	0000000	rKj8VRW	A	src/controller/PostGetAllAction.ts
 :000000	000000	0000000	ADVf7XE	A	src/controller/PostGetByIdAction.ts
 :000000	000000	0000000	znrjMYA	A	src/controller/PostSaveAction.ts
 :000000	000000	0000000	c0D7cwZ	A	src/entity/Category.ts
 :000000	000000	0000000	GRg9KgR	A	src/entity/Post.ts
 :000000	000000	0000000	kn0zzDv	A	src/index.ts
 :000000	000000	0000000	xIe5ZzL	A	src/routes.ts
 :000000	000000	0000000	UsCPKPM	A	tsconfig.json
 3	0	.gitignore
 6	0	.vscode/settings.json
 23	0	ormconfig.json
 41	0	package.json
 18	0	src/controller/PostGetAllAction.ts
 25	0	src/controller/PostGetByIdAction.ts
 21	0	src/controller/PostSaveAction.ts
 12	0	src/entity/Category.ts
 22	0	src/entity/Post.ts
 31	0	src/index.ts
 24	0	src/routes.ts
 15	0	tsconfig.json
 
 commit:IvelI1BgdW4gG5R37alzLR1AXLj2LAroIB8EZPB7bO
 author:denisfeier
 email:denisfeier@generated.com
 date:Mon, Nov 16 2020 22:56:55
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	02FSsha	A	.DS_Store
 8	0	.DS_Store
 
 Step:1
 commit:bC346fGRevO0eqSJ01GCINNFVWMwwOrOn45EoPi4HL
 author:denisfeier
 email:denisfeier@generated.com
 date:Fri, Jul 03 2020 16:00:40
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	TSrXq34	M	src/migration/1593781240088-post.ts
 11	0	src/migration/1593781240088-post.ts
 
 commit:X0glzOLp6p3O7OmSPljgGA6n0mhyVyWBqqzmLSw2EV
 author:denisfeier
 email:denisfeier@generated.com
 date:Fri, Jul 03 2020 16:00:58
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	OUtoOs5	M	src/migration/1593781258881-category.ts
 25	0	src/migration/1593781258881-category.ts
 
 commit:UWrgENItXUGhCqEYrlROVHBd61HFwEDxbxjj7ZIMpK
 author:denisfeier
 email:denisfeier@generated.com
 date:Fri, Jul 03 2020 16:50:01
 message:
 Generated
 numstat:
 
 :000000	000000	0000000	lamHZtF	M	src/migration/1593784201962-user.ts
 11	0	src/migration/1593784201962-user.ts
 ```
