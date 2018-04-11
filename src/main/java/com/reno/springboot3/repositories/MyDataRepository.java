package com.reno.springboot3.repositories;

import com.reno.springboot3.MyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long>{

}
