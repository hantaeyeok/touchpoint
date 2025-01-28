import React from "react";
import { useRef } from "react";
import "@styles/Qna.css";
import { Routes, Route, Link } from "react-router-dom";
import QnaList from "@components/qna/QnaList";



function Qna() {

    const imageUrl = `${process.env.PUBLIC_URL}/images/qna.avif`;
    const searchInput = useRef(null);
    
    return(
        <div className="qnaPage">
            <div className="imgBox"> 
                <img src={imageUrl}/>
            </div>
            <div className="qnaList">
                <Link to="/qna">질문하기</Link>
                <Link to="/faq">자주 묻는 질문</Link>
            </div>
            <div className="qnaSearch-container">
                <input
                type="text"
                placeholder="검색어를 입력해주세요."
                ref={searchInput}
                value={''}
                onChange={''}
                />
                <button onClick={() => searchInput.current.focus()}>검색</button>
            </div>
            <div className="btn_box">
                <Link to="/addQna"><button className="qna_btn" >글 등록</button></Link>
            </div>
            <QnaList/>
        </div>
    )
}
export default Qna