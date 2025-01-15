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
    const [qnaDate,setQnaDate] = useState('');
    const [OriginName,setOriginName] = useState('');
    
    return(
        <div>
            <div className="formContainer">
                <div className="formRow">
                    <div className="formField">
                    <input
                        value={userId}
                        type="text" 
                        style={{ display: "none" }}/>
                    <input 
                        value={phoneNo}
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
                    vlaue={OriginName} 
                    onChange={(event)=>setOriginName(event.target.value)}
                    ype="text" 
                    id="fileName" 
                    className="fileName" />
                <label className="fileLabel">
                    <img src={fileImg} className="fileImg"/>
                    <input type="file" multiple="multiple" className="fileAdd" />
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
export default AddQna