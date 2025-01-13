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
            <div className="search-container">
                <input
                type="text"
                placeholder="검색어를 입력해주세요."
                value={''}
                onChange={''}
                />
                <button>검색</button>
            </div>
            <div>
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
                        <td>제목 제목 제목 제목 제목</td>
                        <td>게스트</td>
                        <td>25/2/8</td>
                        <td class="status question">질문중</td>
                    </tr>
                    <tr>
                        <td>3.</td>
                        <td>제목 제목 제목 제목 제목</td>
                        <td>게스트</td>
                        <td>25/2/8</td>
                        <td class="status answered">답변완료</td>
                    </tr>
                    <tr>
                        <td>3.</td>
                        <td>제목 제목 제목 제목 제목</td>
                        <td>게스트</td>
                        <td>25/2/8</td>
                        <td class="status question">질문중</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    )
}
export default Qna