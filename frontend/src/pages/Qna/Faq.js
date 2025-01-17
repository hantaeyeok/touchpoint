import React, { useState } from "react";
import FaqList from "@components/qna/FaqList";
import "@styles/Qna.css";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";


function Faq() {
    const imageUrl = `${process.env.PUBLIC_URL}/images/qna.avif`;
    return(
                <div>
                    <div className="imgBox"> 
                        <img src={imageUrl}/>
                    </div>
                    <div className="qnaList">
                        <Link to="/qna">질문하기</Link>
                        <Link to="/faq">자주 묻는 질문</Link>
                    </div>
                    <div className="btn_box">
                        <Link to="/addFaq"><button className="qna_btn" >글 등록</button></Link>
                    </div>
                    <FaqList/>
                </div>
    )
}
export default Faq