import logo from './logo.svg';
import React, { useEffect, useState } from "react";
import './App.css';
import Qna from './components/qna/Qna';
import Faq from './components/qna/Faq';
import AddFaq from './components/qna/AddFaq';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import axios from "axios";
import Menubar from './components/indexcomponents/Menubar';



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
    <Router>
      <Menubar/>
      <p>받은 데이터: {data}</p> 
      <div>
        {/* 라우트 설정 */}
        <Routes>
          <Route path="/qna" element={<Qna />} />
          <Route path="/faq" element={<Faq />} />
          <Route path="/addFaq" element={<AddFaq />} />
        </Routes>
      </div>
    </Router>
);
}

export default App;
