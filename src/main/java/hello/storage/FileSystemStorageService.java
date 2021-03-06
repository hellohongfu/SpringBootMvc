package hello.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import hello.repository.AttachFilesRepository;
import hello.model.AttachFiles;
import hello.model.SysUser;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;


    @Autowired
    AttachFilesRepository attachFilesRepository;


    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }

            SysUser principal =  (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String id =principal.getId().toString();
        
            Path p=this.rootLocation.resolve(file.getOriginalFilename());

            Files.copy(file.getInputStream(), p);

            AttachFiles entity=new AttachFiles();

            entity.setObjectId(id);
            entity.setObjectType("user");
            
            entity.setCreatedBy(principal.getUsername());

            entity.setUrl(p.getFileName().toString());

            Date date = new Date();;    
            Calendar rightNow = Calendar.getInstance();;  
            rightNow.setTime(date);;  
            rightNow.set(Calendar.HOUR_OF_DAY,date.getHours()); 
            rightNow.set(Calendar.MINUTE,date.getMinutes());;  
            rightNow.set(Calendar.SECOND,date.getSeconds());;  
            date = rightNow.getTime();

            System.out.print(date.toString());


            entity.setCreatedTime(date);

            attachFilesRepository.save(entity);







        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
