import React, { useEffect } from "react";
import "@styles/Sidebar.css";

const Sidebar = () => {
    //카카오 SDK 초기화
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
        <div className="fixed-sidebar">
            <div className="top-button" onClick={scrollToTop}>▲ TOP</div>
            <div className="sidebar-item" onClick={openKakaoChat} >💬 카톡 1:1 상담</div>
            <div className="sidebar-item">📞 365일 24시간 전화상담</div>
            <div className="sidebar-item">📝 블로그</div>
        </div>
    );
};

export default Sidebar;
