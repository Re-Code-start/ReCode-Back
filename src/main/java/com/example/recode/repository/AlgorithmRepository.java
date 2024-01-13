package com.example.recode.repository;

import com.example.recode.domain.Algorithm;
import com.example.recode.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    List<Algorithm> findAllByFolder(Folder folder);

}
