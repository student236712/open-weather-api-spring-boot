package com.weatherstation.repo;

import com.weatherstation.dto.WeatherDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepo  extends MongoRepository<WeatherDTO,String> {
}
