package agentesdabolsa.business;

import java.io.File;

public class ImportSerieRunnable implements Runnable {
	
	private File file = null;

	public ImportSerieRunnable(String pathname) {
		this.file = new File(pathname);;
	}

	@Override
	public void run() {
		(new ImportSerie()).importFile(file);
	}

}
