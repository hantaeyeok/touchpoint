import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom'; // 올바른 import
import Login from '@pages/login/Login'; 
import SignUp from '@pages/login/SignUp'; 
import SocalSignUp from '@pages/login/SocalSignUp'; 
import SignUpForm from '@pages/login/SignUpForm'; 
import Menubar from '@components/indexcomponts/Menubar';
import './App.css'
import axios from "axios";

function App() {
  const [responseData, setResponseData] = useState(null);

  return (
    <Router>
      <Menubar/> 
      <Routes>
        <Route path="/login" element={<Login />} /> 
        <Route path="/signup" element={<SignUp />} />
        <Route path="/signupform" element={<SignUpForm />} /> 
        <Route path="/socalsignup" element={<SocalSignUp />} /> 
        
      </Routes>
    </Router>
  );
}

export default App;