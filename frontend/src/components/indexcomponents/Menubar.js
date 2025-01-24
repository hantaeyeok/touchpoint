import React from "react";
import "@styles/Menubar.css";
import { Link } from "react-router-dom";


const Menubar = () => {


    return (
        <div className="menu-bar">
            
            <Link className="logo" to="/">logo</Link>
            <div className="menu-items">
                <div className="dropdown">
                <Link to="/product" className="dropdown-title" id="productMenu">제품소개</Link>                </div>

                <div className="dropdown">
                    <a href="#solution" className="dropdown-title">솔루션소개</a>
                    <div className="dropdown-content">
                        <a href="#solution1">솔루션 1</a>
                        <a href="#solution2">솔루션 2</a>
                    </div>
                </div>

                <div className="dropdown">
                    <a href="#install" className="dropdown-title">설치사례</a>
                    <div className="dropdown-content">
                        <Link to="/history">설치사례</Link>
                        <a href="#review">설치후기</a>
                    </div>
                </div>

                <div className="dropdown">
                    <a href="#support" className="dropdown-title">고객상담</a>
                    <div className="dropdown-content">
                        <Link to="/qna">QNA</Link>
                        <Link to="/faq">FAQ</Link>
                    </div>
                </div>
            </div>

            <div className="login-div">
                <Link to="/login">로그인 / 회원가입</Link>
            </div>
        </div>
    );
};

export default Menubar;