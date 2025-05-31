package com.dharshan.weatherapp.service;

import com.dharshan.weatherapp.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerUserDetailServicce implements UserDetailsService {
    @Autowired
    WeatherRepository weatherRepository;
    @Override
    public UserDetails loadUserByUsername(String emailid) throws UsernameNotFoundException {
        return weatherRepository.findByEmail(emailid)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        new ArrayList<>()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }
}
