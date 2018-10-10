package hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.model.AttachFiles;


public interface AttachFilesRepository extends JpaRepository<AttachFiles, Long> {
    AttachFiles findById(String  Id);

    List<AttachFiles> findByObjectIdAndObjectType(String ObjectId,String  objectType);

}