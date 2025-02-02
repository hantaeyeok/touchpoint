import React, { useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import { FaqProvider } from "context/FaqContext";
import "./App.css";
import Menubar from "@components/indexcomponents/Menubar";
import Product from "@pages/product/Product";
import ProductInsert from "@pages/product/ProductInsert";
import Qna from "@pages/Qna/Qna";
import Faq from "@pages/Qna/Faq";
import AddFaq from "@components/qna/AddFaq";
import HistoryMain from "@pages/history/HistoryMain";
import Login from "@pages/login/Login";
import SocalSignUp from "@pages/login/SocalSignUp";
import SignUpForm from "@pages/login/SignUpForm";
import FindPassword from "@pages/login/FindPassword";
import FindId from "@pages/login/FindId";
import { AuthProvider, useAuth } from "@components/login/AuthProvider";
import LogOut from "@components/login/LogOut";
import AdminPage from "@pages/admin/AdminPage";

function App() {

  return (
    <AuthProvider>
      <Menubar />
      <div>
        {/* 라우트 설정 */}
        <Routes>
          <Route path="/qna/*" element={<Qna />} />
          <Route path="/faq/*" element={<Faq />} />
          <Route path="/addFaq/*" element={<AddFaq />} />
          <Route path="/" element={<h1>메인</h1>} />
          <Route path="/product" element={<Product />} />
          <Route path="/productInsert" element={<ProductInsert />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signupform" element={<SignUpForm />} />
          <Route path="/socalsignup/:token" element={<SocalSignUp />} />
          <Route path="/history" element={<HistoryMain />} />
          <Route path="/findPassword" element={<FindPassword />} />
          <Route path="/findId" element={<FindId />} />
          <Route path="/admin/adminPage" element={<AdminPage />} />
          <Route path="/logout" element={<LogOut />} />
        </Routes>
      </div>
      </AuthProvider>
  );
}


export default App;
