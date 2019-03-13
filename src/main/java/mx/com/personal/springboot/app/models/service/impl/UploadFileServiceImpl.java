package mx.com.personal.springboot.app.models.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import mx.com.personal.springboot.app.models.service.IUploadFileService;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
	private static final String UPLOADS = "uploads";

	private Path getPath(String filename) {
		return Paths.get(UPLOADS).resolve(filename).toAbsolutePath();
	}

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		Resource recurso = null;

		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error en el recurso " + pathFoto);
		}

		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFileName);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void deleteAll() throws IOException {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS));
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS));
	}

}
