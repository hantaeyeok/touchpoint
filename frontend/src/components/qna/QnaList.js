import React, {useContext, useEffect, useState } from "react";
import { useRef } from "react";
import "@styles/Qna.css";
import { Routes, Route, Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function QnaList() {
    
    const [qnaList, setQnaList] = useState([]);

    useEffect(() => {
        const fetchQnas = async () => {
            try {
                const response = await axios.get("http://localhost:8989/qna/qnaList");
                setQnaList(response.data); 
            } catch (error) {
                console.error("FAQ 데이터를 가져오는 중 오류 발생:", error);
            }
        };
        fetchQnas();
    }, [setQnaList]);
    
    return(
        <div>
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
                        {qnaList.map((item,index)=>(
                            <tr>
                                <td>{item.qnaNo}</td>
                                <td>{item.qnaTitle}</td>
                                <td>{item.userId}</td>
                                <td>{item.qnaDate}</td>
                                <td className="status question">{item.answerStatus}</td>
                            </tr>
                        ))};
                    </tbody>
                </table>
            </div>
        </div>
    )
}
export default QnaList