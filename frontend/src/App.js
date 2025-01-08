import React, { useState, useEffect } from 'react';
import axios from 'axios'; // axios를 사용해 Spring Boot API 호출
import './App.css';
import Menubar from './components/indexcomponents/Menubar';

function App() {
  const [data, setData] = useState(""); // Spring Boot 데이터 상태 관리

  useEffect(() => {
    // Spring Boot API 호출
    axios
      .get("http://localhost:8989/api/test")
      .then((response) => {
        console.log("Response from server:", response.data);
        setData(response.data); // API 응답 데이터를 상태로 저장
      })
      .catch((error) => {
        console.error("Error fetching data:"  , error);
      });
  }, []);

  return (
    <div className="index">
      <div className="Menubar">
        <Menubar />
      </div>
     
      <div className="content">
        <h1>{data}</h1>
      </div>
    </div>
  );
}

export default App;
