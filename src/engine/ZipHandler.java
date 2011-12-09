package engine;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Handles extracting images from a .zip file for custom texture packages.
 * @author SergeDavid and hithere from StackOverflow.com (original source this is being worked from)
 * @version 0.1
 */
public class ZipHandler {
	private ZipFile zipFile;
	private final String folder = "mods";
	
	public ZipHandler(String path) {
		try {
			zipFile = new ZipFile(System.getProperty("user.dir") + File.separator + folder + File.separator + path + ".zip");
		}
		catch (IOException e) {
			Game.error.ShowError("Could not find " + path + ".zip in the mods folder.");
		}
	}
	
	/**
	 * author hithere from StackOverflow.com
	 */
	public byte[] getEntry(String filePath) {
	    ZipEntry entry = zipFile.getEntry(filePath);
	    try {
	    	int entrySize = (int)entry.getSize();
	        BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
	        byte[] finalByteArray = new byte[entrySize];

	        int bufferSize = 2048;
	        byte[] buffer = new byte[2048];
	        int chunkSize = 0;
	        int bytesRead = 0;

	        while(true) {//Read chunk to buffer
	            chunkSize = bis.read(buffer, 0, bufferSize); //read() returns the number of bytes read
	            if(chunkSize == -1) {//read() returns -1 if the end of the stream has been reached
	                break;
	            }

	            //Write that chunk to the finalByteArray
	            //System.arraycopy(src, srcPos, dest, destPos, length)
	            System.arraycopy(buffer, 0, finalByteArray, bytesRead, chunkSize);
	            bytesRead += chunkSize;
	        }

	        bis.close(); //close BufferedInputStream
	        System.err.println("" + filePath + " size: " + finalByteArray.length + " bytes.");

	        return finalByteArray;
	    }
	    catch (IOException e) {
	        Game.error.ShowError("Couldn't find the texturepack : " + filePath);
	        return null;
	    }
	}
}
