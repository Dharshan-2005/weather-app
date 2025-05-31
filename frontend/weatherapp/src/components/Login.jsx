import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios'

const Login = () => {
  const [formData,setFormData]=useState({"email":"","password":""})

  const handleChange=(e)=>{
      setFormData({...formData,[e.target.name]:e.target.value})
  }
  const navigate=useNavigate();
  const handleSubmit=async(e)=>{
    e.preventDefault();
    try{
      const response=await axios.post("http://localhost:8080/api/login",formData)
      console.log(response.data)
      const token = response.data.token;
      localStorage.setItem('token', token);
      alert("Login successful!");
      navigate("/home");
    }
    catch(err){
      alert("login failed check email id or password correctly");
      console.log(err)
    }
  }
  return (
    <>
    <form className='place-self-center w-11/12 min-h-[400px] max-w-md bg-white rounded-3xl' onSubmit={handleSubmit}>
        <h1 className='font-bold text-3xl text-center mt-7'>Login</h1>
        <div>
            <input name="email" className="mt-10 block mx-auto w-11/12 placeholder:text-slate-600 placeholder:text-xl bg-gray-200 rounded-full 
                border-0 outline-none h-14 p-6" type='email' placeholder='Email' value={formData.email} onChange={handleChange}></input>
            <input name="password" className="mt-5 block mx-auto w-11/12 placeholder:text-slate-600 placeholder:text-xl bg-gray-200 rounded-full 
                border-0 outline-none h-14 p-6" type='password' placeholder='Password' value={formData.password} onChange={handleChange}></input>
        </div>
        <button type='submit' className='block mx-auto mt-6 bg-black text-white rounded-full text-xl w-30 h-12
        font-semibold hover:bg-white hover:text-black hover:border-2 hover:shadow-xl mb-4'>Submit</button>
        <hr className='w-11/12 mx-auto mb-2'></hr>
        <Link to="/signup" className='px-6'>new user?</Link>
    </form>
    </>
  )
}

export default Login