import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom'; // 올바른 import
import Login from '@pages/login/Login'; // Login 컴포넌트 import
import SignUp from '@pages/login/SignUp'; // SignUp 컴포넌트 import
import Menubar from '@components/indexcomponts/Menubar';
import './App.css'
import axios from "axios";

function App() {

  const [data, setData] = useState(null);

  useEffect(() => {
    // Axios를 사용한 API 요청
    axios
      .get("http://localhost:8080/api/data") // 임의의 API URL
      .then((response) => {
        console.log(response.data); // 데이터 확인
        setData(response.data); // 상태에 데이터 저장
      })
      .catch((error) => console.error("Error fetching data:", error)); // 에러 처리
  }, []);


  return (
    <Router>
      <Menubar/>
      <p>{data}</p>
      <Routes>
        <Route path="/login" element={<Login />} /> {/* /login 경로 */}
        <Route path="/signup" element={<SignUp />} /> {/* /signup 경로 */}
      </Routes>
    </Router>
  );
}

export default App;