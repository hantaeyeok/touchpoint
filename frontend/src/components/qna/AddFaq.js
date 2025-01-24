import React, { useContext, useEffect, useState } from "react";
import "@styles/Qna.css";
import { useNavigate } from "react-router-dom";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { FaqContext } from "@context/FaqContext";
import axios from "axios";

function AddFaq() {
    const imageUrl = `${process.env.PUBLIC_URL}/images/qna.avif`;
    
    const [faqTitle, setFaqTitle] = useState('');
    const [answer, setAnswer] = useState('');
    const navigate = useNavigate(); // 페이지 이동을 위한 hook
    
    const { FaqTodoList, setFaqTodoList } = useContext(FaqContext);
    console.log("FaqTodoList 상태:", FaqTodoList);
    
    const faqSubmit = async () =>{
        if(!faqTitle || !answer){
            alert("제목과 내용을 입력해주세요")
            return;
        };

        const newFaq = { faqTitle, answer };
        setFaqTodoList([...FaqTodoList, newFaq]);

        try {
            const response = await axios.post("http://localhost:8989/qna/createFaq", newFaq);
            console.log("서버 응답:", response.data);
            alert("글이 성공적으로 저장되었습니다!");
        } catch (error) {
        console.error("데이터 저장 중 오류 발생:", error.message);
        alert("글 저장 중 문제가 발생했습니다. 다시 시도해주세요.");
        }
        
        setFaqTitle('');
        setAnswer('');
        navigate("/faq");
    };

    return(
        <div>
            <h1>시작해볼까</h1>
            <div className="imgBox"> 
                <img src={imageUrl}/>
            </div>
            <div className="qnaList">
                <Link to="/qna">질문하기</Link>
                <Link to="/faq">자주 묻는 질문</Link>
            </div>
            <div className="btn_box">
                <button className="qna_btn" onClick={faqSubmit}>글 등록</button>
            </div>
            <div className="qna-form-container">
                <div className="form-group">
                    <input 
                            className="qnaInput"
                            value={faqTitle}
                            onChange={(event)=>setFaqTitle(event.target.value)} 
                            type="text" 
                            id="title" 
                            name="title" 
                            placeholder="제목을 입력하세요" />
                </div>
                <div className="qna-form-group">
                    <textarea
                            className="qnaTextarea"
                            value={answer}
                            onChange={(event)=>setAnswer(event.target.value)}
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