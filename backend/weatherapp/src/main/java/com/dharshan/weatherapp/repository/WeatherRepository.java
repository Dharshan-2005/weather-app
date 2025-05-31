package com.dharshan.weatherapp.repository;

import com.dharshan.weatherapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByEmail(String email);
}
