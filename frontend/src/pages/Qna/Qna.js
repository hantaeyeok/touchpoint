import React from "react";

import "@styles/Qna.css";
import { Routes, Route, Link } from "react-router-dom";




function Qna() {
    const imageUrl = `${process.env.PUBLIC_URL}/images/qna.avif`;
    return(
        <div>
            <h1>시작해볼까</h1>
            <div className="imgBox"> 
                <img src={imageUrl}/>
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