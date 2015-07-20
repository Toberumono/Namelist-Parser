package toberumono.namelist.parser;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WGetTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Namelist namelist = NamelistParser.parseNamelist(Paths.get("/Users/joshualipstone/Downloads/namelist.input"));
		NamelistParser.writeNamelist(namelist, Paths.get("/Users/joshualipstone/Downloads/namelist2.input"));
		String test =
				"/usr/local/bin/wget --no-check-certificate -O /dev/null --save-cookies=auth.rda_ucar_edu --post-data=\"email=lipstonejm@ornl.gov&password=aPassword&action=login\" https://rda.ucar.edu/cgi-bin/login";
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(test.split(" "));
		//pb.redirectOutput(Redirect.INHERIT);
		pb.redirectError(Redirect.INHERIT);
		Process proc = pb.start();
		try (Scanner s = new Scanner(proc.getInputStream())) {
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue:" + exitVal);
			s.useDelimiter(Pattern.compile(System.getProperty("line.separator"), Pattern.LITERAL));
			s.forEachRemaining(str -> System.out.println(str));
		}
		finally {
		}
		try {
			String test2 = "/usr/local/bin/wget --no-check-certificate -N --load-cookies=auth.rda_ucar_edu http://rda.ucar.edu/data/ds083.2/grib2/2015/2015.07/fnl_20150705_18_00.grib2";
			pb.command(test2.split(" "));
			Process proc2 = pb.start();
			int exitVal2 = proc2.waitFor();
			System.out.println("Process2 exitValue:" + exitVal2);
		}
		finally {
			pb.command("/bin/rm auth.rda_ucar_edu".split(" "));
			Process proc3 = pb.start();
			int exitVal3 = proc3.waitFor();
			System.out.println("Process3 exitValue:" + exitVal3);
		}
	}
}
