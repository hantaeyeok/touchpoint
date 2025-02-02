import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "@components/login/AuthProvider";
import "@styles/Menubar.css";

const Menubar = () => {
  const { isAuthenticated, userId, role } = useAuth();

  return (
    <div className="menu-bar">
      <Link className="logo" to="/">logo</Link>
      <div className="menu-items">
        <div className="dropdown">
          <a href="#product" className="dropdown-title">제품소개</a>
          <div className="dropdown-content">
            <Link to="/product">제품 1</Link>
            <Link to="/product">제품 2</Link>
          </div>
        </div>
        <div className="dropdown">
          <a href="#solution" className="dropdown-title">솔루션소개</a>
          <div className="dropdown-content">
            <Link to="/solution1">솔루션 1</Link>
            <Link to="/solution2">솔루션 2</Link>
          </div>
        </div>
        <div className="dropdown">
          <a href="#support" className="dropdown-title">고객지원</a>
          <div className="dropdown-content">
            <Link to="/faq">FAQ</Link>
            <Link to="/qna">Q&A</Link>
          </div>
        </div>
        <div className="dropdown">
          {isAuthenticated ? (
            <>
              <Link className="dropdown-title" to="/logout">로그아웃</Link>
              <div className="dropdown-content">
                {userId && (
                  <Link to="/delete-account">탈퇴하기</Link>
                )}
                {role === "ROLE_ADMIN" && (
                  <Link to="/admin/adminPage">관리자페이지</Link>
                )}
              </div>
            </>
          ) : (
            <Link className="dropdown-title" to="/login">로그인 / 회원가입</Link>
          )}
        </div>
      </div>
    </div>
  );
};

export default Menubar;
