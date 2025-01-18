import React, { useContext, useEffect, useState } from "react";
import "@styles/Qna2.css";
import { useNavigate } from "react-router-dom";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { FaqContext } from "@context/FaqContext";
import axios from "axios";

function AddQna() {

    const fileImg = `${process.env.PUBLIC_URL}/images/fileAdd.JPG`;
    const [userId,setUserId] = useState('');
    const [phoneNo,setPhoneNo] = useState('');
    const [OriginName,setOriginName] = useState('');
    const [qnaTitle,setQnaTitle] = useState('');
    const [qnaContent,setQnaContent] = useState('');

    
    
    const handleFileChange = (event) => {
        
        const fileList = Array.from(event.target.files);
        const fileNames = fileList.map((file)=> file.name);
        setOriginName(fileNames);
    };
    
    const qnaSubmit = async () => {
        if(!qnaTitle || !qnaContent){
            alert("제목과 내용을 입력해주세요")
            return;
        };

        const newQna = {userId, phoneNo, OriginName, qnaTitle, qnaContent};

        try {
            const response = await axios.post("http://localhost:8989/qna/createQna", newQna);
            console.log("서버 응답:", response.data);
            alert("글이 성공적으로 저장되었습니다!");
        } catch (error) {
        console.error("데이터 저장 중 오류 발생:", error.message);
        alert("글 저장 중 문제가 발생했습니다. 다시 시도해주세요.");
        }
    }

    return(
        <div>
            <div className="formContainer">
                <div className="formRow">
                    <div className="formField">
                    <input
                        value={'usertId'}
                        type="text" 
                        style={{ display: "none" }}/>
                    <input 
                        value={'phoneNo'}
                        type="text" 
                        style={{ display: "none" }}/>
                    </div>
                </div>
                <div className="formRow">
                    <div className="formField fullWidth">
                    <label>제 목</label>
                    <input 
                        onChange={(event)=>setQnaTitle(event.target.value)}
                        type="text" 
                        placeholder="제목을 입력하세요" />
                    </div>
                </div>
                <div className="formField">
                <label>파일첨부</label>
                <input 
                    value={OriginName} 
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
                        onChange={(event)=>setQnaContent(event.target.value)}
                        placeholder="내용을 입력하세요">
                    </textarea>
                </div>
                <div className="qnaAdd_btn">
                    <Link to="/qna"><button>이전으로</button></Link>
                    <button onClick={qnaSubmit}>글 등록</button>
                </div>
            </div>
        </div>
    )
}
export default AddQna