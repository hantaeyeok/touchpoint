import React, { useEffect, useState } from "react";
import DetailHistoryModal from "@components/history/DetailHistoryModal";
import axios from "axios";
import "@styles/HistoryStore.css";

const HistoryStore = ({ deleteMode, CheckboxChange }) => {
    const [installationData, setInstallationData] = useState([]);
    const [selectedHistoryNo, setSelectedHistoryNo] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        axios
            .get("http://localhost:8989/history")
            .then((response) => setInstallationData(response.data || []))
            .catch((error) => console.error("Error fetching data:", error));
    }, []);

    const openDetailModal = (historyNo) => {
        setSelectedHistoryNo(historyNo);
        setIsModalOpen(true);
    };

    const closeDetailModal = () => {
        setSelectedHistoryNo(null);
        setIsModalOpen(false);
    };

    return (
        <div className="store-grid">
            {Array.isArray(installationData) && installationData.length > 0 ? (
                installationData.map((item) => (
                    <div className="store-item-wrapper" key={item.historyNo}>
                        <div
                            className="store-item"
                            onClick={() => openDetailModal(item.historyNo)}
                        >
                            <img
                                src={`http://localhost:8989${item.mainImage}`}
                                alt="상점사진"
                                className="store-image"
                            />
                            <h3 className="store-title">{item.storeName}</h3>
                        </div>
                        <div className="store-info">
                            <p className="store-description">{item.storeAddress}</p>
                            <p className="store-model">모델명: {item.modelName}</p>
                            <p className="store-date">등록일: {item.historyDate}</p>
                            {deleteMode && (
                                <div className="store-checkbox">
                                    <input
                                        type="checkbox"
                                        id={`checkbox-${item.historyNo}`}
                                        onChange={(e) =>
                                            CheckboxChange(item.historyNo, e.target.checked)
                                        }
                                    />
                                    <label htmlFor={`checkbox-${item.historyNo}`}>삭제</label>
                                </div>
                            )}
                        </div>
                    </div>
                ))
            ) : (
                <p>Loading...</p>
            )}

            {/* DetailHistoryModal */}
            {isModalOpen && (
                <DetailHistoryModal
                    historyNo={selectedHistoryNo}
                    onCloseDetail={closeDetailModal} // 디테일 모달 닫는 함수 전달
                />
            )}
        </div>
    );
};

export default HistoryStore;