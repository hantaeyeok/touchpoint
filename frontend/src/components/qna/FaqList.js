import "@styles/Qna.css";
import React, {useContext, useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { FaqContext } from "@context/FaqContext";
import axios from "axios";

function FaqList() {

    const { FaqTodoList, setFaqTodoList } = useContext(FaqContext);

    useEffect(() => {
        const fetchFaqs = async () => {
            try {
                const response = await axios.get("http://localhost:8989/qna");
                setFaqTodoList(response.data); // 서버에서 받은 데이터를 상태로 설정
            } catch (error) {
                console.error("FAQ 데이터를 가져오는 중 오류 발생:", error);
            }
        };
        fetchFaqs();
    }, [setFaqTodoList]);
    
    return(
        <div>
            {FaqTodoList.map((item,index)=>(

                <div className="faqBox1" key={index}>
                    <div className="faqBox2">
                        <span className="q-icon">Q .</span>
                        <span className="q-text">{item.faqTitle}</span>
                    </div>
                    <div className ="faq_answer">
                        <span className="a-icon">A.</span>
                        <div className="answer-text">{item.answer}</div>
                        <div className="faq_btn">
                            <Link to=""><button className="faq_btn1">수정하기</button></Link>
                            <button className="faq_btn2">삭제하기</button>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    )
}
export default FaqList