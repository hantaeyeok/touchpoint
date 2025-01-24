import React, { useContext, useEffect, useState, useRef } from "react";
import "@styles/Qna2.css";
import { Routes, Route, Link } from "react-router-dom";
import axios from "axios";
import { useParams } from "react-router-dom";
import { handleFileChange } from "./handleFileChange";



function QnaAnswerAdd() {

    const fileImg = `${process.env.PUBLIC_URL}/images/fileAdd.JPG`;
    const [originName,setOriginName] = useState('');
    const [answerTitle,setAnswerTitle] = useState('');
    const [answerContent,setAnswerContent] = useState('');
    const { qnaNo } = useParams();
    const fileInputRef = useRef(null);

    const answerSubmit = async () => {
        if(!answerTitle || !answerContent){
            alert("제목과 내용을 입력해주세요")
            return;
        };

        const formData = new FormData();
        const answer = {
            answerTitle: answerTitle,
            answerContent: answerContent
        };

        formData.append("Answer", new Blob([JSON.stringify(answer)], { type: "application/json" }));
        
        if (fileInputRef.current.files.length > 0) {
            Array.from(fileInputRef.current.files).forEach((file) => {
                formData.append("files", file);
            });
        }

        try {
            const response = await axios.post(`http://localhost:8989/qna/createAnswer/${qnaNo}`, formData,{
                headers: {"Content-Type": "multipart/form-data"}
            });
            console.log("서버 응답:", response.data);
            alert("글이 성공적으로 저장되었습니다!");
            window.location.reload();
        } catch (error) {
        console.error("데이터 저장 중 오류 발생:", error.message);
        alert("글 저장 중 문제가 발생했습니다. 다시 시도해주세요.");
        }
    };

    return(
        <div className="formContainer">
            <h1>답변</h1>
            <div className="formContainer" style={{ marginTop : "20px" }}>
                <div className="formRow">
                    <div className="formField fullWidth">
                        <label>제 목</label>
                        <input 
                            onChange={(event)=>setAnswerTitle(event.target.value)}
                            type="text" 
                            placeholder="제목을 입력하세요" />
                    </div>
                </div>
            </div>
            <div className="formField">
                <label>파일첨부</label>
                <input 
                    value={originName}
                    readOnly 
                    ype="text" 
                    id="fileName" 
                    className="fileName" />
                <label className="fileLabel">
                    <img src={fileImg} className="fileImg"/>
                    <input 
                        type="file"
                        ref={fileInputRef} 
                        multiple="multiple"
                        onChange={(event)=>setOriginName(handleFileChange(event))}
                        className="fileAdd" />
                </label>
            </div>
            <div className="formTextarea">
                <textarea
                        onChange={(event)=>setAnswerContent(event.target.value)}
                        placeholder="내용을 입력하세요">
                </textarea>
            </div>
            <div className="qnaAdd_btn">
                <Link to="/qna"><button>이전으로</button></Link>
                <button onClick={answerSubmit}>글 등록</button>
            </div>
        </div>
    )
}
export default QnaAnswerAdd