import React, { useContext, useEffect, useState } from "react";
import { useRef } from "react";
import "@styles/Qna2.css";
import { Routes, Route, Link } from "react-router-dom";




function QnaDetail() {

    const fileImg = `${process.env.PUBLIC_URL}/images/fileAdd.JPG`;
    const [OriginName,setOriginName] = useState('');

    const handleFileChange = (event) => {

        const fileList = Array.from(event.target.files);
        const fileNames = fileList.map((file)=>file.name);
        setOriginName(fileNames);
    };
    
    return(
        <div className="formContainer">
            <div className="formRow">
                <div className="formField">
                <label>작성자</label>
                <span className="fieldValue">작성자</span>
                </div>
                <div className="formField">
                <label>연락처</label>
                <input type="text" placeholder="연락처를 입력하세요" />
                </div>
                <div className="formField">
                <label>작성일</label>
                <input type="date" />
                </div>
            </div>
            <div className="formRow">
                <div className="formField fullWidth">
                <label>제목</label>
                <input type="text" placeholder="제목을 입력하세요" />
                </div>
            </div>
            <div className="formTextarea">
                <textarea placeholder="내용을 입력하세요"></textarea>
            </div>
            <div className="qnaAdd_btn">
                <Link to="/qna"><button>이전으로</button></Link>
                <button>수정하기</button>
            </div>

            <h1>답변</h1>
            <div className="formContainer" style={{ marginTop : "20px" }}>
                <div className="formRow">
                    <div className="formField">
                    <input
                        value={''}
                        type="text" 
                        style={{ display: "none" }}/>
                    <input 
                        value={''}
                        type="text" 
                        style={{ display: "none" }}/>
                    </div>
                </div>
                <div className="formRow">
                    <div className="formField fullWidth">
                    <label>제 목</label>
                    <input type="text" placeholder="제목을 입력하세요" />
                    </div>
                </div>
                <div className="formField">
                <label>파일첨부</label>
                <input 
                    value={OriginName}
                    readOnly 
                    ype="text" 
                    id="fileName" 
                    className="fileName" />
                <label className="fileLabel">
                    <img src={fileImg} className="fileImg"/>
                    <input 
                        type="file" 
                        multiple="multiple"
                        onChange={handleFileChange}
                        className="fileAdd" />
                </label>

                </div>
                <div className="formTextarea">
                    <textarea placeholder="내용을 입력하세요"></textarea>
                </div>
                <div className="qnaAdd_btn">
                    <Link to="/qna"><button>이전으로</button></Link>
                    <button>글 등록</button>
                </div>
            </div>
        </div>
    )
}
export default QnaDetail