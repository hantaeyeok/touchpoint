import React, { useEffect, useState } from "react";
import "@styles/HistoryStore.css";

const HistoryStore = () => {
    const [installationData, setInstallationData] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch("http://localhost:8989/history");
                if (!response.ok) {
                    throw new Error("Failed to fetch data");
                }
                const data = await response.json();
                setInstallationData(data); // 상태 업데이트
            } catch (error) {
                console.error("Error fetching data:", error);
                setError("데이터를 가져오는 중 오류가 발생했습니다.");
            }
        };
        fetchData(); // 초기 렌더링 시 데이터 가져오기
    }, []); // 의존성 배열 비움

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="store-grid">
            {installationData.map((item) => (
                <div className="store-item" key={item.historyNo}>
                    <img
                        src={item.mainImage}
                        alt={item.storeName}
                        className="store-image"
                    />
                    <h3 className="store-title">{item.storeName}</h3>
                    <p className="store-description">{item.storeAddress}</p>
                    <p className="store-model">모델명: {item.modelName}</p>
                    <p className="store-content">{item.historyContent}</p>
                    <p className="store-date">등록일: {item.historyDate}</p>
                </div>
            ))}
        </div>
    );
};

export default HistoryStore;
