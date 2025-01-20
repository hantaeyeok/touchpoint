import React, { useEffect, useState } from "react";
import "@styles/HistoryStore.css";

const HistoryStore = ({ deleteMode,CheckboxChange }) => {
    const [installationData, setInstallationData] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8989/history")
            .then((response) => response.json())
            .then((data) => setInstallationData(data))
            .catch((error) => console.error("Error fetching data:", error));
    }, []);

    return (
        <div className="store-grid">
            {installationData.map((item) => (
                <div className="store-item-wrapper" key={item.historyNo}>
                    <div className="store-item">
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
                        {deleteMode && ( // 삭제 모드일 때만 체크박스 표시
                            <div className="store-checkbox">
                                 <input
                                    type="checkbox"
                                    id={`checkbox-${item.historyNo}`}
                                    onChange={(e) =>
                                        CheckboxChange(item.historyNo, e.target.checked) // 체크 상태 전달
                                    }
                                />
                                <label htmlFor={`checkbox-${item.historyNo}`}>삭제</label>
                            </div>
                        )}
                    </div>
                </div>
            ))}
        </div>
    );
};

export default HistoryStore;
