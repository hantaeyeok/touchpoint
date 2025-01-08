import React from "react";
import "@styles/Menubar.css";
import { useNavigate } from "react-router-dom";
import App from '../../App';

const Menubar = () => {

    const navi = useNavigate();

    const toMain = () => {
        navi('/');
    };

    const toProduct = () => {
        navi('/product')
    };
    
   

    return (
        <div className="menu-bar">
            <div className="logo" onClick={toMain}>logo</div>
            <div className="menu-items">
                <div className="dropdown">
                    <a href="#product" className="dropdown-title">제품소개</a>
                    <div className="dropdown-content">
                        <a href="#product" onClick={toProduct}>제품 1</a>
                        <a href="#product" onClick={toProduct} >제품 2</a>
                    </div>
                </div>

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
                        <a href="#case">설치사례</a>
                        <a href="#review">설치후기</a>
                    </div>
                </div>

                <div className="dropdown">
                    <a href="#support" className="dropdown-title">고객상담</a>
                    <div className="dropdown-content">
                        <a href="#faq">FAQ</a>
                        <a href="#contact">문의하기</a>
                    </div>
                </div>
            </div>

            <div className="login-div">
                <a href="#login">로그인 / 회원가입</a>
            </div>
        </div>
    );
};

export default Menubar;