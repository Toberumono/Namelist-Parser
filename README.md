# <a name="readme"></a><a name="Readme"></a>Namelist-Parser
A library for reading from and writing to namelist files (Particularly those used by [WRF](http://www.wrf-model.org/index.php)).

## Usage
### Setup (Using Homebrew/Linuxbrew)
##### (you can, of course, checkout the library and build and update it yourself, but why bother when there's an easier option?)
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

#### Linux

1. Download the appropriate [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
2. `cd` into the directory into which you downloaded the JDK (`cd $HOME/Downloads` will likely do it) and run:
	
	```bash
	bash <(wget -qO - https://raw.githubusercontent.com/Toberumono/Miscellaneous/master/java/sudoless_install.sh)
	```
	+ For information on what the script does, see its section in the readme of my [Miscellaneous](https://github.com/Toberumono/Miscellaneous#htujsi) repo.
3. Install [Linuxbrew](https://github.com/Homebrew/linuxbrew#install-linuxbrew-tldr). Run one of the following:
	1. If you have sudo privileges, do the following:
		1. Run the following script to get the required libraries (For systems that use `yum`, replace `apt-get install` with `yum install`):

			```bash
			sudo apt-get install build-essential curl git m4 ruby texinfo libbz2-dev libcurl4-openssl-dev libexpat-dev libncurses-dev zlib1g-dev
			```
		2. Install [Linuxbrew](https://github.com/Homebrew/linuxbrew#install-linuxbrew-tldr).
			+ If you are not comfortable modifying your .bashrc or .zshrc files, follow step c.  Otherwise, modify them.
		3. Run:
	
			```bash
			bash <(wget -qO - https://raw.githubusercontent.com/Toberumono/Miscellaneous/master/linuxbrew/append_paths.sh)
			```
			+ This adds the additional lines to your .bashrc and/or .zshrc files. For more information on how it works, see its section in the readme of my [Miscellaneous](https://github.com/Toberumono/Miscellaneous#htulap) repo.
	2. If you do not have sudo privileges, then you can run the following script.  It will attempt to install [Linuxbrew](https://github.com/Homebrew/linuxbrew) without sudo privileges or will list the missing software that you should ask your system administrator to install if it cannot do so.

		```bash
		bash <(wget -qO - https://raw.githubusercontent.com/Toberumono/Miscellaneous/master/linuxbrew/sudoless_install.sh)
		```
#### Mac

1. Ruby and Curl are already installed on Mac, so we don't need to worry about those.
2. install the appropriate [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
3. install [Homebrew](http://brew.sh/).
