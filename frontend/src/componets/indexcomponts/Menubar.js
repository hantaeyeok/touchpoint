import React from "react";
import "./Menubar.css";

const Menubar = () => {
    return (
        <div className="menu-bar">
            <div className="logo">logo</div>
            <div className="menu-items">
                <a href="#product">제품소개</a>
                <a href="#solution">솔루션소개</a>
                <div className="dropdown">
                    <a href="#install" className="dropdown-title">
                        설치사례
                    </a>
                    <div className="dropdown-content">
                        <a href="#case">설치사례</a>
                        <a href="#review">설치후기</a>
                    </div>
                </div>
                <a href="#support">고객상담</a>
                <a href="#login">로그인 / 회원가입</a>
            </div>
        </div>
    );
};

export default Menubar;
