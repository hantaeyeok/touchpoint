import React, { useState, useEffect } from "react";
import { Route, Routes, useNavigate } from 'react-router-dom';
import Login from '@pages/login/Login'; 
import SignUp from '@pages/login/SignUp'; 
import SocalSignUp from '@pages/login/SocalSignUp'; 
import SignUpForm from '@pages/login/SignUpForm'; 
import Menubar from '@components/indexcomponents/Menubar';
import Product from '@src/page/Product';
import ProductInsert from '@src/page/ProductInsert';
import Qna from '@components/qna/Qna';
import Faq from '@components/qna/Faq';
import AddFaq from '@components/qna/AddFaq';
import { FaqProvider } from 'context/FaqContext';
import axios from 'axios';
import './App.css'


    
function App() {
  const [data, setData] = useState("");

  useEffect(() => {
    // Spring Boot API 호출
    axios
        .get("http://localhost:8989/api/test")
        .then((response) => {
          console.log("Response from server:", response.data);
          setData(response.data);
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    }, []);

  return (
    <FaqProvider>
        <Menubar/>
        <p>받은 데이터: {data}</p> 
        <div>
          {/* 라우트 설정 */}
          <Routes>
            <Route path="/qna/*" element={<Qna />} />
            <Route path="/faq/*" element={<Faq />} />
            <Route path="/addFaq/*" element={<AddFaq />} />
            <Route path="/" element={<h1>메인</h1>} />
            <Route path="/product" element={<Product/>}/>
            <Route path="/productInsert" element={<ProductInsert/>}/>
            <Route path="/login" element={<Login />} /> 
            <Route path="/signup" element={<SignUp />} />
            <Route path="/signupform" element={<SignUpForm />} /> 
            <Route path="/socalsignup" element={<SocalSignUp />} /> 
        
          </Routes>
        </div>
    </FaqProvider>
);
}

export default App;