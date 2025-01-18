import React, { useContext, useEffect, useState } from "react";
import { useRef } from "react";
import "@styles/Qna2.css";
import { Routes, Route, Link } from "react-router-dom";
import axios from "axios";



function QnaAnswer() {


    return(
        <div className="formContainer">
            <h1>답변</h1>
            <div className="formRow">
                <div className="formField">
                    <label>제 목</label>
                    <span className="fieldValue">제 목</span>
                </div>
                <div className="formField">
                    <label>작성일</label>
                    <span className="fieldValue">작성일</span>
                </div>
            </div>
            <div className="formRow">
                <div className="formField fullWidth">
                    <label>파일첨부</label>
                    <span className="fieldValue">파일명</span>
                </div>
            </div>
        </div>
    )
}
export default QnaAnswer