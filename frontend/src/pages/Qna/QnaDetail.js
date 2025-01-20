import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "@styles/Qna2.css";
import { Routes, Route, Link, useNavigate  } from "react-router-dom";
import QnaAnswerAdd from "@components/qna/QnaAnswerAdd";
import QnaAnswer from "@components/qna/QnaAnswer";
import axios from "axios";


function QnaDetail() {

    const navigate = useNavigate();
    const [qnaDetail, setQnaDetail] = useState({
        qnaNo: 0,
        qnaTitle: '',
        qnaContent: '',
        qnaDate: '',
        userId: '',
        phoneNo: '',
        files: []
    });
    
    const { qnaNo } = useParams(); // URL에서 qnaNo 가져오기

    const handleEdit = () => {
        navigate("/qnaEdit", {
            state: {
                qnaNo: qnaNo,
                qnaTitle: qnaDetail.qnaTitle,
                qnaContent: qnaDetail.qnaContent,
                qnaDate: qnaDetail.qnaDate,
                userId: qnaDetail.userId,
                phoneNo: qnaDetail.phoneNo,
                files: qnaDetail.files
            }
        });
    };

    useEffect(()=> {
        const fetchQnas = async () => {
            try {
                const response = await axios.get(`http://localhost:8989/qna/qnaDetail/${qnaNo}`);
                setQnaDetail(response.data.data);
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
                    <label>제 목</label>
                    <span className="fieldValue">{qnaDetail.qnaTitle}</span>
                </div>
            </div>
            <div className="formRow">
                <div className="formField fullWidth">
                    <label>파일첨부</label>
                    {qnaDetail.files.map((file,index)=>(
                        <a key={index} href={file.path} download={file.originName}>
                            {file.OriginName}
                        </a>
                    ))}
                </div>
            </div>
            <div className="qnaContent">
                <span className="fieldValue">{qnaDetail.qnaContent}</span>
            </div>
            <div className="qnaAdd_btn">
                <Link to="/qna"><button>이전으로</button></Link>
                <button onClick={handleEdit}>수정하기</button>
            </div>
            <QnaAnswerAdd/>
            <QnaAnswer/>
        </div>
    )
}
export default QnaDetail