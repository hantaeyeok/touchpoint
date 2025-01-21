import React, { useState, useEffect } from "react";
import { Route, Routes, useNavigate } from 'react-router-dom';
import Login from '@pages/login/Login'; 
import SocalSignUp from '@pages/login/SocalSignUp'; 
import SignUpForm from '@pages/login/SignUpForm'; 
import Menubar from '@components/indexcomponents/Menubar';
import Product from '@pages/product/Product';
import ProductInsert from '@pages/product/ProductInsert';
import Qna from '@pages/Qna/Qna';
import Faq from '@pages/Qna/Faq';
import AddFaq from '@components/qna/AddFaq';
import { FaqProvider } from 'context/FaqContext';
import axios from 'axios';
import './App.css'
import HistoryMain from "@pages/history/HistoryMain";
import RecaptchaTest from "@pages/login/RecaptchaTest ";
import FindPassword from "@pages/login/FindPassword";

function App() {


  
  

  return (<>
  
  
    <FaqProvider>
        <Menubar/>

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
            <Route path="/signupform" element={<SignUpForm />} /> 
            <Route path="/socalsignup" element={<SocalSignUp />} /> 
            <Route path="/history" element={<HistoryMain/>} />
            <Route path="/recaptcha" element={<RecaptchaTest />} />
            <Route path="/findPassword" element={<FindPassword />} />
            
          </Routes>
        </div>
    </FaqProvider>


    














    </>
);
}

export default App;