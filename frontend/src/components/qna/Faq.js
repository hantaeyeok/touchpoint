import React, { useState } from "react";
import qnaImg from "@img/qna.avif";
import "@styles/Qna.css";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import FaqList from "./FaqList";

function Faq() {

    return(
                <div>
                    <h1>시작해볼까</h1>
                    <div className="imgBox"> 
                        <img src={qnaImg}/>
                    </div>
                    <div className="qnaList">
                        <Link to="/qna">질문하기</Link>
                        <Link to="/faq">자주 묻는 질문</Link>
                    </div>
                    <div className="btn_box">
                        <Link to="/addFaq"><button className="qna_btn" >글 등록</button></Link>
                    </div>
                    <Routes>
                    </Routes>
                    <FaqList/>
                </div>
    )
}
export default Faq