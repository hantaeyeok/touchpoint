import './App.css';
import Product from '@src/page/Product';
import ProductInsert from '@src/page/ProductInsert';
import Menubar from '@components/indexcomponents/Menubar';
import { Route, Routes, useNavigate } from 'react-router-dom';
import React, { useEffect, useState } from "react";
import Qna from '@components/qna/Qna';
import Faq from '@components/qna/Faq';
import AddFaq from '@components/qna/AddFaq';
import { FaqProvider } from 'context/FaqContext';
import axios from 'axios';


    
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
          </Routes>
        </div>
    </FaqProvider>
);
}

export default App;
