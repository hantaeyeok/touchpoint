import React from "react";
import qnaImg from "../img/qna.avif";
import "@styles/Qna.css";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import FaqList from "./FaqList";


function AddFaq() {
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
                <button className="qna_btn" >글 등록</button>
            </div>
            <div className="form-container">
                <div className="form-group">
                    <input type="text" id="title" name="title" placeholder="제목을 입력하세요" />
                </div>
                <div className="form-group">
                    <textarea id="content" name="content" placeholder="내용을 입력하세요"></textarea>
                </div>
            </div>
        </div>
    )
}
export default AddFaq