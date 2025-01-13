import React from "react";
import qnaImg from "@img/qna.avif";
import "@styles/Qna.css";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

function Qna() {
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
                <h3>질문하기 화면</h3>
        </div>
    )
}
export default Qna