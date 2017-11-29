package javar.lfrivera.util;
//Obtained from: https://github.com/s-u/REngine/blob/master/Rserve/test/StartRserve.java.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javar.lfrivera.entity.PrintHeaderEnum;

/** helper class that consumes output of a process. In addition, it filter output of the REG command on Windows to look for InstallPath registry entry which specifies the location of R. */

class StreamHog extends Thread {

	InputStream is;

	boolean capture;

	String installPath;
	
	/**
	 * The class that allows to print a message on console.
	 */
	private ConsolePrinter printer;

	StreamHog(InputStream is, boolean capture) {

		this.is = is;

		this.capture = capture;

		printer = new ConsolePrinter(PrintHeaderEnum.STREAM_HOG);
		
		start();

	}

	public String getInstallPath() {

		return installPath;

	}

	public void run()

	{

		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line = null;

			while ( (line = br.readLine()) != null) {

				if (capture) { // we are supposed to capture the output from REG command

					int i = line.indexOf("InstallPath");

					if (i >= 0) {

						String s = line.substring(i + 11).trim();

						int j = s.indexOf("REG_SZ");

						if (j >= 0)

							s = s.substring(j + 6).trim();

						installPath = s;

						printer.print("R InstallPath = "+s);

					}

				} else 

					printer.print("Rserve>" + line);

			}

		} catch (IOException e) {

			printer.print(e.getMessage());

		}

	}

}


