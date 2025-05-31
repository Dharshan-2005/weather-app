import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

const SignUp = () => {
  const [formData,setFormData]=useState({"username":"","email":"","password":""})
  const handleChange=(e)=>{
    setFormData({...formData,[e.target.name]:e.target.value})
  }
  const navigate=useNavigate();
  const handleSubmit=async(e)=>{
    e.preventDefault();
    try{
      const response=await axios.post("http://localhost:8080/api/signin",formData);
      console.log(response.data);
      const token = response.data.token;
      localStorage.setItem('token', token);
      alert("User created successful!");
      navigate("/home")
    }
    catch(err){
      alert("invalid input check the input")
      console.log("invalid input",err);  
    }
  }
  return (
    <>
    <form className='place-self-center w-11/12 min-h-[450px] max-w-md bg-white rounded-3xl' onSubmit={handleSubmit}>
        <h1 className='font-bold text-3xl text-center mt-7'>Sign Up</h1>
        <div>
            <input name="username" values={formData.username} onChange={handleChange} className="mt-10 block mx-auto w-11/12 placeholder:text-slate-600 placeholder:text-xl bg-gray-200 rounded-full 
                border-0 outline-none h-14 p-6" type='text' placeholder='Username'></input>
            <input name="email" values={formData.email} onChange={handleChange} className="mt-5 block mx-auto w-11/12 placeholder:text-slate-600 placeholder:text-xl bg-gray-200 rounded-full 
                border-0 outline-none h-14 p-6" type='email' placeholder='Email'></input>
            <input name="password" values={formData.password} onChange={handleChange} className="mt-5 block mx-auto w-11/12 placeholder:text-slate-600 placeholder:text-xl bg-gray-200 rounded-full 
                border-0 outline-none h-14 p-6" type='password' placeholder='Password'></input>
        </div>
        <button type='submit' className='block mx-auto mt-6 bg-black text-white rounded-full text-xl w-30 h-12
        font-semibold hover:bg-white hover:text-black hover:border-2 hover:shadow-xl mb-3'>Submit</button>
        <hr className='w-11/12 mx-auto mb-2'></hr>
        <Link to="/login" className='px-6'>Already have an account?</Link>
    </form>
    </>
  )
}

export default SignUp