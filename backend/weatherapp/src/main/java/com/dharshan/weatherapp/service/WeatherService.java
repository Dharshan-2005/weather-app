package com.dharshan.weatherapp.service;

import com.dharshan.weatherapp.dto.WeatherResponse;
import com.dharshan.weatherapp.model.Users;
import com.dharshan.weatherapp.repository.WeatherRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class WeatherService {

    private final String api_key="7db7f4dc24f41ff2956b0ddce4ddf5da";

    WeatherRepository weatherRepository;
    PasswordEncoder passwordEncoder;
    WeatherService(WeatherRepository weatherRepository, PasswordEncoder passwordEncoder){
        this.weatherRepository=weatherRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public boolean validateLogin(Users data){
        String email=data.getEmail();
        String password= data.getPassword();
        Optional<Users> user=weatherRepository.findByEmail(email);
        if(user.isPresent()){
            Users users=user.get();
            return passwordEncoder.matches(password,users.getPassword());
        }
        return false;
    }
    public Users createUser(Users data){
        int id=data.getId();
        Optional<Users> user=weatherRepository.findById(id);
        if(user.isPresent()){
            return null;
        }
        data.setPassword(passwordEncoder.encode(data.getPassword()));
        return weatherRepository.save(data);
    }


    public WeatherResponse getData(String city){
        String url="https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + api_key + "&units=metric";
//        the below code is used to make the Http get request
        RestTemplate restTemplate=new RestTemplate();
//        the below code is used to get the object from the url and return type is String
        String json=restTemplate.getForObject(url,String.class);
//        let us parse this json data
        JSONObject jsondata=new JSONObject(json);
//        get the data from the main and wind
        JSONObject main=jsondata.getJSONObject("main");
        JSONObject wind=jsondata.getJSONObject("wind");

//        herw we will not autowire this as we don't want this object to be managed by bean
        WeatherResponse weatherResponse=new WeatherResponse();
        weatherResponse.setCity(jsondata.getString("name"));
        weatherResponse.setHumidity(main.getInt("humidity"));
        weatherResponse.setTemp(main.getDouble("temp"));
        weatherResponse.setWindSpeed(wind.getDouble("speed"));
        System.out.println("The data is send to the client");
        return weatherResponse;
    }
}
