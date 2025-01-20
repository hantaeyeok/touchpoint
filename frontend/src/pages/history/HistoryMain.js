import React, { useState } from "react";
import HistoryStore from "./HistoryStore"; // 하위 컴포넌트 import
import AddHistoryModal from "@components/history/AddHistoryModal"; // 모달 컴포넌트 import
import "@styles/HistoryMain.css";

const HistoryMain = () => {
    const [modalOpen, setModalOpen] = useState(false);

    return (
        <div className="historyMain">
            <h2 className="historyTitle">설치사례</h2>
            <button className="addHistoryButton" onClick={() => setModalOpen(true)}>
                추가하기
            </button>

            <HistoryStore />

            {/* 모달 컴포넌트 */}
            <AddHistoryModal
                isOpen={modalOpen}
                onClose={() => setModalOpen(false)}
            />
        </div>
    );
};

export default HistoryMain;
