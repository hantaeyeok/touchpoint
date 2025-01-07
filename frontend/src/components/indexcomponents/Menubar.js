import React from "react";
import "@styles/Menubar.css";

const Menubar = () => {
    return (
        <div className="menu-bar">
            <div className="logo">logo</div>
            <div className="menu-items">
                {/* 제품소개 */}
                <div className="dropdown">
                    <a href="#product" className="dropdown-title">제품소개</a>
                    <div className="dropdown-content">
                        <a href="#product1">제품 1</a>
                        <a href="#product2">제품 2</a>
                    </div>
                </div>

                {/* 솔루션소개 */}
                <div className="dropdown">
                    <a href="#solution" className="dropdown-title">솔루션소개</a>
                    <div className="dropdown-content">
                        <a href="#solution1">솔루션 1</a>
                        <a href="#solution2">솔루션 2</a>
                    </div>
                </div>

                {/* 설치사례 */}
                <div className="dropdown">
                    <a href="#install" className="dropdown-title">설치사례</a>
                    <div className="dropdown-content">
                        <a href="#case">설치사례</a>
                        <a href="#review">설치후기</a>
                    </div>
                </div>

                {/* 고객상담 */}
                <div className="dropdown">
                    <a href="#support" className="dropdown-title">고객상담</a>
                    <div className="dropdown-content">
                        <a href="#faq">FAQ</a>
                        <a href="#contact">문의하기</a>
                    </div>
                </div>
            </div>

            {/* 로그인 / 회원가입 */}
            <div className="login-div">
                <a href="#login">로그인 / 회원가입</a>
            </div>
        </div>
    );
};

export default Menubar;
