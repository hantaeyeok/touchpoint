import React, { useContext, useEffect, useState } from "react";
import { useRef } from "react";
import "@styles/Qna2.css";
import { Routes, Route, Link } from "react-router-dom";
import axios from "axios";
import { useParams } from "react-router-dom";


function QnaAnswer() {

    const { qnaNo } = useParams();
    const [answer, setAnswer] = useState({
            answerNo: 0,
            answerTitle: "",
            answerContent: "",
            answerDate: "",
            files: [],
        });

    console.log("QnA 번호:", qnaNo);

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
                <Link to="/qna"><button>이전으로</button></Link>
                <button>수정하기</button>
            </div>
        </div>
    )
}
export default QnaAnswer