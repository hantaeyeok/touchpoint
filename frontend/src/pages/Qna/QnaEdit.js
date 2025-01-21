import React, { useEffect, useState } from "react";
import { useLocation  } from "react-router-dom";
import { useRef } from "react";
import "@styles/Qna2.css";
import { Routes, Route, Link } from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function QnaEdit() {

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

    const handleFileChange = (event) => {
        const fileList = Array.from(event.target.files);
        setOriginName(fileList.map((file)=> file.name));
    };

    const editSubmit = async () => {
        if(!qnaData.qnaTitle || !qnaData.qnaContent){
            alert("제목과 내용을 입력해주세요")
            return;
        };

        
    };

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
                    onChange={(event)=>setOriginName(event.target.value)}
                    readOnly
                    type="text" 
                    id="fileName" 
                    className="fileName" />
                    
                <label className="fileLabel">
                    <img src={fileImg} className="fileImg"/>
                    <input 
                        type="file" 
                        onChange={handleFileChange}
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