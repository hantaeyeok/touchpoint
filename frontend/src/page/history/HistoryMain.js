import React from "react";
import HistoryStore from "./HistoryStore"; // 하위 컴포넌트 import
import "@styles/HistoryMain.css";

const HistoryMain = () => {
    return (
        <div className="historyMain">
            <h2 className="historyTitle">설치사례</h2>
            {/* 하위 컴포넌트 렌더링 */}
            <HistoryStore />
        </div>
    );
};

export default HistoryMain;
