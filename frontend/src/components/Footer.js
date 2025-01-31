import React, { useEffect } from "react";
import "@styles/Footer.css"

const Footer = () => {
    useEffect(() => {
            if (!window.Kakao) {
                const script = document.createElement("script");
                script.src = "https://developers.kakao.com/sdk/js/kakao.js";
                script.async = true;
                script.onload = () => {
                    if (!window.Kakao.isInitialized()) {
                    window.Kakao.init(""); //본인의 카카오 JavaScript 키 입력
                    }
                };
                document.body.appendChild(script);
                } else {
                if (!window.Kakao.isInitialized()) {
                    window.Kakao.init("");
                }
            }
        }, []);
    
        const openKakaoChat = () => {
            if (window.Kakao) {
                window.Kakao.Channel.chat({
                    channelPublicId: "_XLzKn", //본인의 카카오톡 채널 ID 입력
                });
            } else {
                alert("카카오 SDK 로드 중입니다. 잠시 후 다시 시도해주세요.");
                }
            };
    
        const scrollToTop = () => {
            window.scrollTo({ top: 0, behavior: "smooth" });
        };

    return (
    <footer className="footer">
        <div className="footer-top">
            <div className="footer-button">📞 상담전화<br /><span>대표번호 1666-3154</span></div>
            <div className="footer-button" onClick={openKakaoChat}>💬 고객지원<br /><span>1:1 채팅 문의하기</span></div>
            <div className="footer-button">🎉 이벤트 · 혜택<br /><span>행사 또는 혜택 알아보기</span></div>
        </div>
        <div className="footer-bottom">
            <h2 className="footer-title">요잇당</h2>
            <p className="footer-contact">고객센터 <strong>1666-3154</strong></p>
            <p>사업자 등록번호: 498-27-01727</p>
            <p>주소: 경기도 고양시 일산동구 장항로 24 (A동 609호)</p>
            <p>E-mail: <a href="mailto:yoeitdang@naver.com">yoeitdang@naver.com</a></p>
            <p className="footer-copyright">Copyright © 2024 요잇당. All rights reserved.</p>
        </div>
    </footer>
    );
};

export default Footer;
