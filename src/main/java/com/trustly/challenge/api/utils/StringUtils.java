package com.trustly.challenge.api.utils;

public class StringUtils {

	/**
	 * @author Christiano Soneghett
	 * @param hrefUrl = this is the full URL of href that refers to the github file
	 * @return A string vector with the name of the file and the extension of it
	 */
	public static String[] getFileNameAndExtension( String hrefUrl ) {

		String[] splits = hrefUrl.split( "/" );

		String fileName = splits[ splits.length - 1 ];

		String extension = "";

		int i = fileName.lastIndexOf( '.' );
		if ( i >= 0 ) {
			extension = fileName.substring( i + 1 );
			return new String[] { fileName.substring( 0, i ), extension };
		}

		return new String[] { fileName, extension };
	}

	private StringUtils() {

	}
}
