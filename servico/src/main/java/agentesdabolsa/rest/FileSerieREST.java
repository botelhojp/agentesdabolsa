package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import agentesdabolsa.business.ImportSerie;
import agentesdabolsa.business.ImportSerieRunnable;
import agentesdabolsa.exception.AppException;

@Path("serie")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class FileSerieREST extends AbstractREST{
	
	@GET
	@Path("/status")
	public Response list() throws NotFoundException {
		return Response.ok().entity(ImportSerie.currentStatus).build();
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		ImportSerie.currentStatus.put("value", "start");
		String uploadedFileLocation = System.getProperty("java.io.tmpdir") + "/" + fileDetail.getFileName();
		writeToFile(uploadedInputStream, uploadedFileLocation);
		String output = "File uploaded to : " + uploadedFileLocation;
		new Thread(new ImportSerieRunnable(uploadedFileLocation)).start();;
		return Response.status(200).entity(output).build();
	}

	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new AppException(Status.INTERNAL_SERVER_ERROR, "detalhe", "erro ao processar o arquivo");
		}

	}

}
