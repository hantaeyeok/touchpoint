import React, { useContext, useEffect, useState } from "react";
import qnaImg from "@img/qna.avif";
import "@styles/Qna.css";
import { useNavigate } from "react-router-dom";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import FaqList from "./FaqList";
import { FaqContext } from "../../context/FaqContext";


function AddFaq() {
    
    const [qTitle, setTilte] = useState('');
    const [qContent, setContent] = useState('');
    const navigate = useNavigate(); // 페이지 이동을 위한 hook
    
    const { FaqTodoList, setFaqTodoList } = useContext(FaqContext);
    console.log("FaqTodoList 상태:", FaqTodoList);
    
    const faqSubmit = () =>{
        if(!qTitle || !qContent){
            alert("제목과 내용을 입력해주세요")
            return;
        };

        setFaqTodoList([...FaqTodoList, { qTitle, qContent }]);
        setTilte('');
        setContent('');
        navigate("/faq");
    };

    return(
        <div>
            <h1>시작해볼까</h1>
            <div className="imgBox"> 
                <img src={qnaImg}/>
            </div>
            <div className="qnaList">
                <Link to="/qna">질문하기</Link>
                <Link to="/faq">자주 묻는 질문</Link>
            </div>
            <div className="btn_box">
                <button className="qna_btn" onClick={faqSubmit}>글 등록</button>
            </div>
            <div className="form-container">
                <div className="form-group">
                    <input 
                            value={qTitle}
                            onChange={(event)=>setTilte(event.target.value)} 
                            type="text" 
                            id="title" 
                            name="title" 
                            placeholder="제목을 입력하세요" />
                </div>
                <div className="form-group">
                    <textarea 
                            value={qContent}
                            onChange={(event)=>setContent(event.target.value)}
                            id="content" 
                            name="content" 
                            placeholder="내용을 입력하세요">
                    </textarea>
                </div>
            </div>
        </div>
    )
}
export default AddFaq