import "@styles/Qna.css";
import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

function FaqList() {

    const [qTilte, setTilte] = useState('');

    return(
        <div>
            <div className="faqBox1">
                <div className="faqBox2">
                    <span className="q-icon">Q .</span>
                    <span className="q-text">어쩌고 저쩌고 질문내용 입력란</span>
                </div>
                <div class="faq_answer">
                    <span class="a-icon">A.</span>
                    <div className="answer-text">
                        자주 묻는 질문에 대한 답변 등록란<br/>
                        자주 묻는 질문에 대한 답변 등록란<br/>
                        자주 묻는 질문에 대한 답변 등록란
                    </div>
                    <div className="faq_btn">
                        <Link to=""><button className="faq_btn1">수정하기</button></Link>
                        <button className="faq_btn2">삭제하기</button>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default FaqList