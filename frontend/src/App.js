import React, { useState, useEffect } from 'react';
import axios from 'axios'; 
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; 
import Menubar from './components/indexcomponents/Menubar';
import HistoryMain from './page/history/HistoryMain';

import './App.css';

function App() {
  const [data, setData] = useState(""); 

  useEffect(() => {
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
      <div className="index">
       
        <div className="Menubar">
          <Menubar />
        </div>

        
        <Routes>
          
          <Route path="/" element={<div className="content"><h1>{data}</h1></div>} />

          <Route path="/history" element={<HistoryMain></HistoryMain>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
