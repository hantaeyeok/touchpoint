import React, { useContext, useEffect, useState } from "react";
import { useRef } from "react";
import "@styles/Qna2.css";
import { Routes, Route, Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useParams } from "react-router-dom";


function QnaAnswer() {

    const { qnaNo } = useParams();
    const navigate = useNavigate();
    const [answer, setAnswer] = useState({
            answerNo: 0,
            answerTitle: "",
            answerContent: "",
            answerDate: "",
            files: [],
        });

    const handleEdit = () => {
        navigate("/answerEdit", {
            state: {
                qnaNo:qnaNo,
                answerNo: answer.answerNo,
                answerTitle: answer.answerTitle,
                answerContent: answer.answerContent,
                files: answer.files
            }
        });
    };
    

    useEffect(()=> {
        const fetchQnas = async () => {
            try {
                const response = await axios.get(`http://localhost:8989/qna/answer/${qnaNo}`);
                setAnswer(response.data.data);
                console.log("response.data.data 객체:", response.data.data);
            } catch (error) {
                console.error("QNA 데이터를 가져오는 중 오류 발생:", error);
            }
        };
        fetchQnas();
    },[]);

    return(
        <div className="formContainer">
            <h1>답변</h1>
            <div className="formRow">
                <div className="formField">
                    <label>제 목</label>
                    <span className="fieldValue">{answer.answerTitle}</span>
                </div>
                <div className="formField">
                    <label>작성일</label>
                    <span className="fieldValue">{answer.answerDate}</span>
                </div>
            </div>
            <div className="formRow">
                <div className="formField fullWidth">
                    <label>파일첨부</label>
                    {answer.files?.length > 0 ? (
                        answer.files.map((file, index) => (
                            <a key={index} href={file.path} download={file.originName}>
                                {file.originName}
                            </a>
                        ))
                    ) : (
                        <span>첨부된 파일이 없습니다.</span>
                    )}
                </div>
            </div>
            <div className="answerContent">
                <span className="fieldValue">{answer.answerContent}</span>
            </div>
            <div className="qnaAdd_btn">
                <button onClick={()=>{navigate(-1)}}>이전으로</button>
                <button onClick={handleEdit}>수정하기</button>
            </div>
        </div>
    )
}
export default QnaAnswer