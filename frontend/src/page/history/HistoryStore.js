import React, { useEffect, useState } from "react";
import "@styles/HistoryStore.css";

const HistoryStore = () => {
    const [installationData, setInstallationData] = useState([]); // 상태로 데이터 관리

    // API 호출 함수
    const fetchInstallationData = async () => {
        try {
            const response = await fetch("/api/history"); // Spring Boot API 호출
            const data = await response.json(); // JSON으로 변환
            setInstallationData(data); // 상태 업데이트
        } catch (error) {
            console.error("데이터를 가져오는 중 오류 발생:", error);
        }
    };

    // 컴포넌트가 처음 렌더링될 때 API 호출
    useEffect(() => {
        fetchInstallationData();
    }, []);

    return (
        <div className="store-grid">
            {installationData.map((item) => (
                <div className="store-item" key={item.id}>
                    <img
                        src={item.images[0]} // 첫 번째 이미지를 출력
                        alt={item.title}
                        className="store-image"
                    />
                    <h3 className="store-title">{item.title}</h3>
                    <p className="store-description">{item.description}</p>
                </div>
            ))}
        </div>
    );
};

export default HistoryStore;
