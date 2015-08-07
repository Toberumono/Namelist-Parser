# <a name="readme"></a><a name="Readme"></a>Namelist-Parser
A library for reading from and writing to namelist files (Particularly those used by [WRF](http://www.wrf-model.org/index.php)).

## Usage
### Setup (Using Homebrew/Linuxbrew) (you can, of course, checkout the library and its requirements and build them all yourself, but why bother when there's an easier option?)
#### <a name="rp"></a>Required programs (these are all command line utilities)

* ruby
* curl
* Homebrew/Linuxbrew
* [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (update 45 or higher)

If you don't have these, see [Getting the Required Programs](#gtrp) for how to get them.

#### Compiling the Library

1. Make sure that you have the [Required Programs](#rp).
	+ If you don't, follow the directions in [Getting the Required Programs](#gtrp).
2. Run `brew tap toberumono/tap` (This only needs to be run once.)
	+ If you're on Linux and it cannot find the brew command, run `export PATH=$HOME/.linuxbrew/bin:$PATH`.
3. Run `brew install namelist-parser`
	+ Linuxbrew may have trouble with a few dependencies, running `brew install` for each dependency, while annoying, will fix that problem.
5. To use the library in a project of your own, add the path listed in the caveats section to your classpath.
4. If you want access to the JavaDoc from a program such as Eclipse, it is in the .jar file in the doc folder.  For reading in a web browser, the documentation will be in the directory listed in the "caveats" section of the Homebrew output.
5. For examples, see the source code for my [WRF Runner](https://github.com/Toberumono/WRF-Runner) project.  It uses effectively every feature.

## Help
### <a name="gtrp"></a>Getting the Required Programs

- Linux (note: you may need to change the values of version and update for step 2 to match the files that you downloaded):
	1. Download the appropriate [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
	2. Copy the following script into terminal, and change the values of version and update so that they match the version and update of the JDK you downloaded.  Then run it.
	```bash
	export version=8; export update=51; sudo mkdir /usr/lib/jvm; sudo tar zxvf "jdk-${version}u$update-linux-x64.tar.gz" -C /usr/lib/jvm; sudo ln -sf "/usr/lib/jvm/jdk1.$version.0_$update/bin/*" /usr/bin/
	```
	3. Install wget, git, ruby, and curl. Run: (For systems that use `yum`, replace `apt-get install` with `yum install`)</br>
	```bash
	sudo apt-get install build-essential curl git m4 ruby texinfo libbz2-dev libcurl4-openssl-dev libexpat-dev libncurses-dev zlib1g-dev
	```
	4. Install [Linuxbrew](https://github.com/Homebrew/linuxbrew).  **There is no need to edit the .bashrc or .zshrc files unless you expect to run Linuxbrew frequently**.
	5. Link the executables. Run: `export PATH=$HOME/.linuxbrew/bin:$PATH` (This is why there's no need to edit .bashrc and .zshrc).
- Mac: Ruby and Curl are already installed on Mac, so we don't need to worry about those.
	1. install the appropriate [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
	2. install [Homebrew](http://brew.sh/).
