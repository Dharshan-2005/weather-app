import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
const Home = () => {
  const [city,setCity]=useState("")
  const [query,setQuery]=useState("")
  const [weatherData,setWeatherData]=useState(null)
  const navigate=useNavigate();
  useEffect(()=>{
    if(query){
      const fetchData=async()=>{
        const token = localStorage.getItem('token');
        try{
          const res=await axios.get(`http://localhost:8080/api/weather?city=${encodeURIComponent(query)}`,{
             headers: {
              Authorization: `Bearer ${token}`
            }
          })
          setWeatherData(res.data);
          console.log("got the data")
        }
        catch(err){
          if(err.response.status==401 || err.response.status==403){
            alert("session expired ! please login into the app")
            navigate("/login")
          }
          else{
          alert("Enter the valid name")
          console.log("invalid name",err);
          }
        }
      };
      fetchData();
    }
  },[query])

  const handleSubmit=(e)=>{
    e.preventDefault();
    setQuery(city)
  }
  return (
    <>
    <form className='place-self-center w-11/12 bg-white rounded-3xl min-h-[550px] max-w-md' onSubmit={handleSubmit}>
        <h1 className='font-semibold text-3xl text-center mt-8'>Weather App</h1>
        <div>
            <img src='/logo.webp' className='w-32 h-32 mx-auto'></img>
            <div className='flex justify-center'>
                <input className="w-11/12 placeholder:text-slate-600 placeholder:text-2xl bg-gray-200 rounded-full 
                border-0 outline-none h-14 p-6 shadow-xl" type='text' 
                placeholder='Enter the city name' value={city} onChange={e=>setCity(e.target.value)}></input>
            </div>
            <button type='submit' className='block mx-auto mt-6 bg-black text-white rounded-full text-xl w-30 h-12
            font-semibold hover:bg-white hover:text-black hover:border-2 shadow-xl'>Submit</button>
        </div>
        {
          weatherData && (
            <div className='text-center mt-4'>
              <h2 className='font-semibold text-3xl'>Weather info</h2>
              {/* the below we are doing the object dereferncing */}
              <p className='text-xl '>City: {weatherData.city}</p>
              <p className='text-xl '>Temperature: {weatherData.temp} °C</p>
              <p className='text-xl '>Humidity: {weatherData.humidity}%</p>
              <p className='text-xl '>Wind Speed: {weatherData.windSpeed} m/s</p>
            </div>
          )
        }
    </form>
    </>
  )
}

export default Home