import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useRef } from "react";
import "@styles/Qna2.css";
import { Routes, Route, Link } from "react-router-dom";
import QnaAnswerAdd from "@components/qna/QnaAnswerAdd";
import QnaAnswer from "@components/qna/QnaAnswer";
import axios from "axios";


function QnaDetail() {

    const fileImg = `${process.env.PUBLIC_URL}/images/fileAdd.JPG`;
    const [OriginName,setOriginName] = useState('');
    const [qnaDetail,setQnsDetail] = useState('');
    const { qnaNo } = useParams(); // URL에서 qnaNo 가져오기

    const handleFileChange = (event) => {

        const fileList = Array.from(event.target.files);
        const fileNames = fileList.map((file)=>file.name);
        setOriginName(fileNames);
    };

    useEffect(()=> {
        const fetchQnas = async () => {
            try {
                const response = await axios.get(`http://localhost:8989/qna/qnaDetail/${qnaNo}`);
                setQnsDetail(response.data);
            } catch (error) {
                console.error("QNA 데이터를 가져오는 중 오류 발생:", error);
            }
        };
        fetchQnas();
    },[]);


    
    return(
        <div className="formContainer">
            <div className="formRow">
                <div className="formField">
                    <label>작성자</label>
                    <span className="fieldValue">{qnaDetail.userId}</span>
                </div>
                <div className="formField">
                    <label>연락처</label>
                    <span className="fieldValue">{qnaDetail.phoneNo}</span>
                </div>
                <div className="formField">
                    <label>작성일</label>
                    <span className="fieldValue">{qnaDetail.qnaDate}</span>
                </div>
            </div>
            <div className="formRow">
                <div className="formField fullWidth">
                    <label>제목</label>
                    <span className="fieldValue">{qnaDetail.qnaTitle}</span>
                </div>
            </div>
            <div className="formRow">
                <div className="formField fullWidth">
                    <label>파일첨부</label>
                    <span className="fieldValue">{qnaDetail.originName}</span>
                </div>
            </div>
            <div className="qnaContent">
                <span className="fieldValue">{qnaDetail.qnaContent}</span>
            </div>
            <div className="qnaAdd_btn">
                <Link to="/qna"><button>이전으로</button></Link>
                <button>수정하기</button>
            </div>
            <QnaAnswerAdd/>
            <QnaAnswer/>
        </div>
    )
}
export default QnaDetail