import React from "react";
import "../styles/Header.css";

const Header = () => {
    return (
      <header className="header">
        <div className="logo">Logo</div>
        <nav className="nav">
          <ul className="nav-list">
            <li className="nav-item"><a href="/about"></a></li>
            <li className="nav-item"><a href="/products"></a></li>
            <li className="nav-item"><a href="/solutions"></a></li>
            <li className="nav-item"><a href="/examples"></a></li>
            <li className="nav-item"><a href="/rental"></a></li>
            <li className="nav-item"><a href="/qna">QnA</a></li>
          </ul>
        </nav>
        <div className="auth">
          <a href="/login">로그인</a> / <a href="/signup">회원가입</a>
        </div>
      </header>
    );
  };
  
  export default Header;