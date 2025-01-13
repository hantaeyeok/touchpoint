import "@styles/Qna.css";
import React, {useContext, useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { FaqContext } from "../../context/FaqContext";

function FaqList() {

    const { FaqTodoList } = useContext(FaqContext);
    
    return(
        <div>
            {FaqTodoList.map((item,index)=>(

                <div className="faqBox1" key={index}>
                    <div className="faqBox2">
                        <span className="q-icon">Q .</span>
                        <span className="q-text">{item.qTitle}</span>
                    </div>
                    <div className ="faq_answer">
                        <span className="a-icon">A.</span>
                        <div className="answer-text">{item.qContent}</div>
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