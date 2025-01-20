import React, { useState } from "react";
import HistoryStore from "./HistoryStore"; // 하위 컴포넌트 import
import AddHistoryModal from "@components/history/AddHistoryModal"; // 모달 컴포넌트 import
import "@styles/HistoryMain.css";
import axios from "axios";

const HistoryMain = () => {
    const [modalOpen, setModalOpen] = useState(false);   // 모달 오픈 상태
    const [deleteMode, setDeleteMode] = useState(false); // 삭제 모드 상태
    const [selectedItems, setSelectedItems] = useState([]); // 체크된 항목 관리

    // 체크박스 상태 변경 처리
    const handleCheckboxChange = (historyNo, isChecked) => {
        setSelectedItems((prevItems) =>
            isChecked
                ? [...prevItems, historyNo] // 체크된 항목 추가
                : prevItems.filter((item) => item !== historyNo) // 체크 해제된 항목 제거
        );
    };

    // 취소 버튼 동작
    const CancelButtonClick = () => {
        setDeleteMode(false); // 딜리트 모드 비활성화
        setSelectedItems([]); // 선택된 항목 초기화
    };
    // 삭제 버튼 동작
    const DeleteButtonClick = async () => {
        if (!deleteMode) {
            // 딜리트 모드 활성화
            setDeleteMode(true);
            return;
        }

        if (selectedItems.length === 0) {
            // 체크박스에 선택된 항목이 없을 경우
            alert("삭제할 항목을 선택해주세요.");
            return;
        }

        // 확인창 띄우기
        const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
        if (confirmDelete) {
            try {
                // 선택된 항목을 컨트롤러로 전송
                const formData = new FormData();
                const jsonData = JSON.stringify({ historyIds: selectedItems }); // JSON 변환
                formData.append("data", new Blob([jsonData], { type: "application/json" }));

                const response = await axios.post("http://localhost:8989/history/delete", formData);

                if (response.status === 200) {
                    alert("삭제가 완료되었습니다.");
                    setSelectedItems([]); // 선택 항목 초기화
                    setDeleteMode(false); // 딜리트 모드 종료
                } else {
                    alert("삭제에 실패했습니다. 다시 시도해주세요.");
                }
            } catch (error) {
                console.error("서버 오류:", error.response || error);
                alert("서버 오류가 발생했습니다. 다시 시도해주세요.");
            }
        }
    };

    return (
        <div className="historyMain">
            <h2 className="historyTitle">설치사례</h2>
            {/* 버튼 컨테이너 */}
            <div className="button-container">
                <button
                    className="commonButton addHistoryButton"
                    onClick={() => setModalOpen(true)}
                >
                    추가하기
                </button>
                <button
                    className="commonButton deleteHistoryButton"
                    onClick={DeleteButtonClick}
                >
                    삭제하기
                </button>
                {deleteMode && (
                    <button
                        className="commonButton cancelDeleteButton"
                        onClick={CancelButtonClick}
                    >
                        취소하기
                    </button>
                )}
            </div>

            {/* HistoryStore에 deleteMode 및 handleCheckboxChange 전달 */}
            <HistoryStore
                deleteMode={deleteMode}
                CheckboxChange={handleCheckboxChange} // 체크박스 상태 변경 핸들러 전달
            />

            {/* 모달 컴포넌트 */}
            <AddHistoryModal
                isOpen={modalOpen}
                onClose={() => setModalOpen(false)}
            />
        </div>
    );
};

export default HistoryMain;
