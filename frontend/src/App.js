import React, { useState, useEffect } from "react";
import { Route, Routes, useNavigate } from 'react-router-dom';
import { FaqProvider } from 'context/FaqContext';
import './App.css'
import Menubar from '@components/indexcomponents/Menubar';
import ProductRegister from '@components/productcomponents/ProductRegister';
import ProductEdit from '@components/productcomponents/ProductEdit';
import Product from '@pages/product/Product';
import ProductInsert from '@pages/product/ProductInsert';
import DetailProduct from "@pages/product/DetailProduct";
import ProductChange from "@pages/product/ProductChange";
import Qna from '@pages/Qna/Qna';
import Faq from '@pages/Qna/Faq';
import AddFaq from '@components/qna/AddFaq';
import HistoryMain from "@pages/history/HistoryMain";

import Login from '@pages/login/Login'; 
import SocalSignUp from '@pages/login/SocalSignUp'; 
import SignUpForm from '@pages/login/SignUpForm'; 
import RecaptchaTest from "@pages/login/RecaptchaTest ";
import FindPassword from "@pages/login/FindPassword";
import AuthHandler from "@components/login/AuthHandler";
import FindId from "@pages/login/FindId";

import UseUserId from "@components/login/UseUserId";
import UseAdmin from "@components/login/UseAdmin";

function App() {

  const userId = UseUserId();
  const { userId: adminId, role } = UseAdmin(); // Admin 정보 가져오기
  useEffect(() => {
    if (userId) {
      console.log("Logged-in User ID:", userId);
    } else {
      console.log("No User ID found in cookies.");
    }

    if (adminId && role) {
      console.log("Admin User ID:", adminId, "Role:", role);
    } else {
      console.log("No Admin or Role found in cookies.");
    }
  }, [userId, adminId, role]);
  

  

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
            <Route path="/auth/:token" element={<AuthHandler/>} />

            <Route path="/product" element={<Product/>}/>
            <Route path="/detailProduct/:productId" element={<DetailProduct/>}/>
            <Route path="/productRegister" element={<ProductRegister/>}/>
            <Route path="/productEdit/:productId" element={<ProductEdit/>}/>
            <Route path="/login" element={<Login />} /> 
            <Route path="/signupform" element={<SignUpForm />} /> 
            <Route path="/socalsignup/:token" element={<SocalSignUp />} /> 
            <Route path="/history" element={<HistoryMain/>} />
            <Route path="/recaptcha" element={<RecaptchaTest />} />
            <Route path="/findPassword" element={<FindPassword />} />
            <Route path="/findId" element={<FindId />} />
            
          </Routes>
        </div>
    </FaqProvider>

    </>
);
}



export default App;
// 사용 방법법
// const { userId, role } = useAdmin();

//   return (
//     <div>
//       <h1>Admin Page</h1>
//       {userId && role === "ADMIN" ? (
//         <p>Welcome, Admin {userId}!</p>
//       ) : (
//         <p>You do not have access to this page.</p>
//       )}
//     </div>
//   );