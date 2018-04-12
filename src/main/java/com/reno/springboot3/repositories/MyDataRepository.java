package com.reno.springboot3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long>{
    MyData findByid(Long name);

    void deleteById(long id);

    public List<MyData> findByNameLike(String name);
    public List<MyData> findByIdIsNotNullOrderByIdDesc();
    public List<MyData> findByAgeGreaterThan(Integer age);
    public List<MyData> findByAgeBetween(Integer age1, Integer age2);

    @Query("SELECT d FROM MyData d ORDER BY d.name")
    List<MyData> findAllOrderByName();
}
