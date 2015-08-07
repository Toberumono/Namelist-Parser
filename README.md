# Namelist-Parser
A library for reading from and writing to namelist files (Particularly those used by [WRF](http://www.wrf-model.org/index.php)).

## Usage
### Setup
#### <a name="rp"></a>Required programs (these are all command line utilities)

* wget
* ant
* ruby
* curl
* javac update 45 or higher (via the [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html))

If you don't have these, see [Getting the Required Programs](#gtrp) for how to get them.

#### <a name="rl"></a>Required Libraries

* [Structures](https://github.com/Toberumono/Structures)

If you don't have this, see [Getting the Required Libraries](#gtrl) for how to get them.

This uses my Structures library, available at [https://github.com/Toberumono/Structures](https://github.com/Toberumono/Structures).

This is used in my WRF Runner script, available at [https://github.com/Toberumono/WRF-Runner](https://github.com/Toberumono/WRF-Runner).

That's about it.