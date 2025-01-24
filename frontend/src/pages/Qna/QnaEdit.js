import React, { useEffect, useState, useRef } from "react";
import { useLocation  } from "react-router-dom";
import "@styles/Qna2.css";
import { Routes, Route, Link } from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { handleFileChange } from "@components/qna/handleFileChange";

function QnaEdit() {

    const fileInputRef = useRef(null);
    const navigate = useNavigate();
    const fileImg = `${process.env.PUBLIC_URL}/images/fileAdd.JPG`;
    const location = useLocation();
    const [originName,setOriginName] = useState('');
    const [qnaData, setQnaData] = useState({
        qnaNo: "",
        qnaTitle: "",
        qnaContent: "",
        files: [],
    });

    useEffect(() => {
        if (location.state) {
            setQnaData(location.state);
        }
    }, [location.state]);
    
    useEffect(() => {
        if (qnaData.files.length > 0) {
            const fileNames = qnaData.files.map((file) => file.originName).join(", ");
            setOriginName(fileNames);
        }
    }, [qnaData.files]);

    const editSubmit = async () => {
        if(!qnaData.qnaTitle || !qnaData.qnaContent){
            alert("제목과 내용을 입력해주세요")
            return;
        };

        const formData = new FormData();
        formData.append("QnaDto", new Blob([JSON.stringify(qnaData)], { type: "application/json" }));
        console.log("QnaDto :",qnaData);
        console.log("fileInputRef.current.files:", fileInputRef.current?.files);
        if (fileInputRef.current.files.length > 0) {
            Array.from(fileInputRef.current.files).forEach((file) => {
                formData.append("files", file); //파일 실제 데이터 저장
            });
        }
        console.log("formData :",formData);
        console.log(qnaData.qnaNo);
        
        try {
            const response = await axios.put(`http://localhost:8989/qna/updateQna/${qnaData.qnaNo}`, formData, {
                headers: {"Content-Type": "multipart/form-data"}
            });
            console.log("서버 응답:", response.data);
            alert("글이 성공적으로 저장되었습니다!");
            navigate("/qna");
        } catch (error) {
        console.error("데이터 저장 중 오류 발생:", error.message);
        alert("글 저장 중 문제가 발생했습니다. 다시 시도해주세요.");
        };
    }

    return(
        <div className="formContainer">
            <div className="formRow">
                <div className="formField fullWidth">
                <label>제 목</label>
                <input 
                    value={qnaData.qnaTitle}
                    onChange={(event)=>setQnaData((prev)=>({...prev, qnaTitle : event.target.value}))}
                    type="text" 
                    placeholder="제목을 입력하세요" />
                </div>
            </div>
            <div className="formField">
            <label>파일첨부</label>
                <input 
                    value={originName}
                    readOnly
                    type="text" 
                    id="fileName" 
                    className="fileName" />
                    
                <label className="fileLabel">
                    <img src={fileImg} className="fileImg"/>
                    <input 
                        type="file" 
                        ref={fileInputRef}
                        onChange={(event)=>setOriginName(handleFileChange(event))}
                        multiple="multiple" 
                        className="fileAdd" />
                </label>
            </div>
            <div className="formTextarea">
                <textarea
                    value={qnaData.qnaContent}
                    onChange={(event)=>setQnaData((prev)=>({...prev, qnaContent : event.target.value}))}
                    placeholder="내용을 입력하세요">
                </textarea>
            </div>
            <div className="qnaAdd_btn">
                <button onClick={()=>{navigate(-1)}}>이전으로</button>
                <button onClick={editSubmit}>수정하기</button>
            </div>
        </div>
    )
}
export default QnaEdit