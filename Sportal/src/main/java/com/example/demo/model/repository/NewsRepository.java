package com.example.demo.model.repository;

import com.example.demo.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {



    List<News> findByTitleContaining(String name);

    List<News> findByOrderByViewsDesc();

    List<News> findByOrderByCreatedAtDesc();





}
