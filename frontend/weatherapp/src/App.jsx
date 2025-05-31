import React from 'react'
import Home from './components/Home'
import Login from './components/Login'
import SignUp from './components/SignUp'
import {BrowserRouter,Routes,Route} from 'react-router-dom'
const App = () => {
  return (
    <>
    <div className='bg-black grid min-h-screen'>
      <BrowserRouter>
      <Routes>
        <Route path="/home" element={<Home></Home>}></Route>
        <Route path='/' element={<Login></Login>}></Route>
        <Route path='/login' element={<Login></Login>}></Route>
        <Route path='/signup' element={<SignUp></SignUp>}></Route>
      </Routes>
    </BrowserRouter>
    </div>
    </>
  )
}

export default App