import './App.css';
import Product from '@src/page/Product';
import ProductInsert from '@src/page/ProductInsert';
import Menubar from '@components/indexcomponents/Menubar';
import { Route, Routes, useNavigate } from 'react-router-dom';
import axios from 'axios'; 
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; 
import HistoryMain from './page/history/HistoryMain';
import React, { useState, useEffect } from 'react';


function App() {

  return (
    <div className="App">
    <Router>
      <Menubar/>

      <Routes>
        <Route path="/" element={<h1>메인</h1>} />
        <Route path="/product" element={<Product/>}/>
        <Route path="/productInsert" element={<ProductInsert/>}/>
        <Route path="/history" element={<HistoryMain></HistoryMain>} />
      </Routes>
    </Router>                                   
    </div>
  );
}

export default App;
