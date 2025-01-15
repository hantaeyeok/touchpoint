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
            <div className="qnaSearch-container">
                <input
                type="text"
                placeholder="검색어를 입력해주세요."
                value={''}
                onChange={''}
                />
                <button>검색</button>
            </div>
            <div className="btn_box">
                <Link to="/addQna"><button className="qna_btn" >글 등록</button></Link>
            </div>
            <div className="qnaList">
                <table>
                    <thead>
                    <tr>
                        <th>NO.</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>등록일</th>
                        <th>상태</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>3.</td>
                        <td>제목 제목 제목 제목 제목 제목 제목 제목 제목 제목</td>
                        <td>게스트</td>
                        <td>25/2/8</td>
                        <td className="status question">질문중</td>
                    </tr>
                    <tr>
                        <td>3.</td>
                        <td>제목 제목 제목 제목 제목</td>
                        <td>게스트</td>
                        <td>25/2/8</td>
                        <td className="status answered">답변완료</td>
                    </tr>
                    <tr>
                        <td>3.</td>
                        <td>제목 제목 제목 제목 제목</td>
                        <td>게스트</td>
                        <td>25/2/8</td>
                        <td className="status question">질문중</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    )
}
export default Qna