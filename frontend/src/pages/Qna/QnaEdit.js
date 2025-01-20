import React, { useEffect, useState } from "react";
import { useLocation  } from "react-router-dom";
import { useRef } from "react";
import "@styles/Qna2.css";
import { Routes, Route, Link } from "react-router-dom";
import axios from "axios";


function QnaEdit() {

    const fileImg = `${process.env.PUBLIC_URL}/images/fileAdd.JPG`;
    const location = useLocation();
    const [editTitle,setEditTitle] = useState('');
    const [editContent,setEditContent] = useState('');
    const [originName,setOriginName] = useState('');

    const {
        qnaNo,
        qnaTitle,
        qnaContent,
        files,
    } = location.state;

    const handleFileChange = (event) => {
        
        const fileList = Array.from(event.target.files);
        const fileNames = fileList.map((file)=> file.name);
        setOriginName(fileNames);
    };

    const editSubmit = async () => {
        if(!qnaTitle || !qnaContent){
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
                    value={qnaTitle}
                    onChange={(event)=>setEditTitle(event.target.value)}
                    type="text" 
                    placeholder="제목을 입력하세요" />
                </div>
            </div>
            <div className="formField">
            <label>파일첨부</label>
                <input 
                    value={files.OriginName}
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
                    value={qnaContent}
                    onChange={(event)=>setEditContent(event.target.value)}
                    placeholder="내용을 입력하세요">
                </textarea>
            </div>
            <div className="qnaAdd_btn">
                <Link to={`/qnaDetail${qnaNo}`}><button>이전으로</button></Link>
                <button onClick={editSubmit}>수정하기</button>
            </div>
        </div>
    )
}
export default QnaEdit