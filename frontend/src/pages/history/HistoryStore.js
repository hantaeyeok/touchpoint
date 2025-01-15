import React, { useEffect, useState } from "react";
import "@styles/HistoryStore.css";

const HistoryStore = () => {
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
