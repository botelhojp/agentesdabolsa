package agentesdabolsa.commons;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AppUtils {

	public static String normalize(String value) {
		if (value == null) {
			return null;
		}
		return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static Date makeData(String sData) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		try {
			return formato.parse(sData);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String makeData(Date sData) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		return formato.format(sData);
	}

	public static Float makeFloat(String sFloat) {
		return new Float(sFloat).floatValue();
	}

	public static long makeLong(String string) {
		return new Long(string).longValue();
	}
	
	public static Hashtable<String, String> getMessage(String key, String value){
		Hashtable<String, String> e = new Hashtable<String, String>();
		e.put(key, value);
		return e;
	}

	public static File descompacta(File _fileZip) {
		try {
			System.out.println(_fileZip.exists());
			ZipFile zipFile = new ZipFile(_fileZip);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				if (entry.isDirectory()) {
					System.err.println("Descompactando diretório: " + entry.getName());
					(new File(entry.getName())).mkdir();
					continue;
				}
				File tempFile = File.createTempFile(_fileZip.getName(), ".tmp");
				System.out.println("Descompactando arquivo:" + entry.getName());
				copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(tempFile)));
				zipFile.close();
				return tempFile;
			}
			zipFile.close();
		} catch (IOException ioe) {
			System.err.println("Erro ao descompactar:" + ioe.getMessage());
		}
		return null;
	}

	private static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);
		in.close();
		out.close();
	}

}
