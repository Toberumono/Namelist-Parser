#Namelist Parser
A library for reading from and writing to Namelist files (Particularly those used by [WRF](http://www.wrf-model.org/index.php)).

##Usage

See the this project's [wiki](https://github.com/Toberumono/Namelist-Parser/wiki) for usage examples.

###Compiling
There are a two main options for compiling this library:

1. With [Homebrew](http://brew.sh) or [Linuxbrew](https://github.com/Homebrew/linuxbrew)
  + Pros:
    - Makes updating incredibly easy
    - Automatically installs [Apache Ant](http://ant.apache.org/)
    - Handles automatic dependency tracking (for a lot of software)
  + Cons:
    - Setting up [Homebrew](http://brew.sh)/[Linuxbrew](https://github.com/Homebrew/linuxbrew) includes some overhead
    - In rare cases, [Linuxbrew](https://github.com/Homebrew/linuxbrew) can have some compatibility problems
    - Setting up [Linuxbrew](https://github.com/Homebrew/linuxbrew) can require sudo
2. Without [Homebrew](http://brew.sh) or [Linuxbrew](https://github.com/Homebrew/linuxbrew)
  + Pros:
    - Can have less overhead than setting up [Homebrew](http://brew.sh)/[Linuxbrew](https://github.com/Homebrew/linuxbrew)
    - Avoids potential compatibility issues with [Linuxbrew](https://github.com/Homebrew/linuxbrew)
  + Cons:
    - Manually setting up [Ant](http://ant.apache.org/) can be a pain
    - Updating the software and libraries is harder

####Required Software
(these are all command line utilities)<br>
Note: Software that is installed as a part of the setup instructions is not included.

* Both:
  + git
  + [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (update 45 or higher)
* With [Homebrew](http://brew.sh)/[Linuxbrew](https://github.com/Homebrew/linuxbrew):
  + ruby (Already installed on Macs)
  + curl (Already installed on Macs)
* Without [Homebrew](http://brew.sh)/[Linuxbrew](https://github.com/Homebrew/linuxbrew):
  + [Ant](http://ant.apache.org/)

**Note**: If you do not have the needed software, or are not sure, see [Getting the Required Software](#getting-the-required-software) for instructions on how to get the software.

####Getting the Required Software

* Both:
  + git (if `which git` does not show a path):
    * For Mac, install XCode developer tools
    * For Linux, `sudo apt-get install git` or `sudo yum install git` should work
  + [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (update 45 or higher):
    * For Mac: Download the JDK from Oracle here: [http://www.oracle.com/technetwork/java/javase/downloads/index.html](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
    * For Linux: follow the instructions at [https://github.com/Toberumono/Miscellaneous/wiki/Installing-Oracle's-JDK-on-Linux](https://github.com/Toberumono/Miscellaneous/wiki/Installing-Oracle's-JDK-on-Linux)
* With [Homebrew](http://brew.sh)/[Linuxbrew](https://github.com/Homebrew/linuxbrew):
  + See their respective websites for setup instructions.
    - Homebrew: [http://brew.sh](http://brew.sh)
    - Linuxbrew: [https://github.com/Homebrew/linuxbrew](https://github.com/Homebrew/linuxbrew)
* Without [Homebrew](http://brew.sh)/[Linuxbrew](https://github.com/Homebrew/linuxbrew):
  + [Ant](http://ant.apache.org/):
    - See [https://github.com/Toberumono/Miscellaneous/wiki/Installing-Apache-Ant](https://github.com/Toberumono/Miscellaneous/wiki/Installing-Apache-Ant) for setup instructions.

####Compiling With Homebrew/Linuxbrew
Any "run" instruction means, "copy and paste the following into Terminal and hit enter".

1. Run `brew tap Toberumono/tap`
2. Run `brew install namelist-parser`
3. All done!

####Compiling Without Homebrew/Linuxbrew
Any "run" instruction means, "copy and paste the following into Terminal and hit enter".

1. Create the directory that you want to contain the .jar file
2. Run one of the following:
  + For upgradeable builds, run:
    
    ```bash
    op="$(pwd)" && tg="Namelist-Parser" && git clone "https://github.com/Toberumono/$tg.git" && cd "./$tg" && git checkout "$(git describe)" && ant; cd "$op"; unset op tg
    ```
    * This is the recommended option.
    * It clones the repository and then checks out the most recent tagged commit (aka the most recent release)
    * To update, cd into the directory with the .jar file and run:

      ```bash
      op="$(pwd)" && tg="Namelist-Parser" && cd "./$tg" && git fetch && git checkout "$(git describe)" && ant; cd "$op"; unset op tg
      ```
  + For one-time builds, run:

    ```bash
    op="$(pwd)" && tg="Namelist-Parser" && url="https://github.com/Toberumono/$tg" && mkdir "./$tg" && curl -#fSL "$url/archive/$(git ls-remote --tags $url.git | grep -oE '([0-9]+\.)*[0-9]+$' | sort -g | tail -1).tar.gz" | tar -xz --strip-components 1 -C "./$tg" && cd "./$tg" && ant; cd "$op" && rm -rf "$tg"; unset op tg url
    ```
    * This will get the most recent tagged release *only*.
    * While this is not recommended, it is still perfectly safe - it just makes updating a bit trickier.
  + If you *really* want the cutting edge, possibly broken version, run:

    ```bash
    op="$(pwd)" && tg="Namelist-Parser" && git clone "https://github.com/Toberumono/$tg.git" && cd "./$tg" && ant; cd "$op"; unset op tg
    ```
    * If you do not know how to update to the latest version after running this, don't run it.
    * This is not recommended mainly because it is not safe - unless you are willing to do some debugging, avoid this option.
3. All done!
