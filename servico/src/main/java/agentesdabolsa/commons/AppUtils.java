package agentesdabolsa.commons;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AppUtils {
	
	private static SimpleDateFormat formato_01 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat formato_02 = new SimpleDateFormat("yyyyMMdd");

	public static String normalize(String value) {
		if (value == null) {
			return null;
		}
		return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static Date makeData(String sData) {
		try {
			return formato_02.parse(sData);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String makeData(Date sData) {
		return formato_01.format(sData);
	}

	public static Float makeFloat(String sFloat) {
		return new Float(sFloat).floatValue();
	}

	public static long makeLong(String string) {
		return new Long(string).longValue();
	}

	public static Hashtable<String, String> getMessage(String key, String value) {
		Hashtable<String, String> e = new Hashtable<String, String>();
		e.put(key, value);
		return e;
	}

	public static String formatMoeda(double value) {
		return NumberFormat.getCurrencyInstance().format(value);
	}

	public static Object cloneObject(Object obj) {
		try {
			Object clone = obj.getClass().newInstance();
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				field.set(clone, field.get(obj));
			}
			return clone;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
				copyInputStream(zipFile.getInputStream(entry),
						new BufferedOutputStream(new FileOutputStream(tempFile)));
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

	public static byte[] serialize(Object obj) {
		try {
			if (obj == null){
				return null;
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
