import React from "react";
import "@styles/HistoryStore.css";

const installationData = [
    {
        id: 1,
        title: "핏앤플레이",
        description: "주소: 서울시 강서구 화곡동",
        model1: "모델명1",
        model2: "모델명 12313412515",
        images: ["fit-and-play-main.jpg"],
    },
];

const HistoryStore = () => {
    return (
        <div className="store-grid">
            {installationData.map((item) => (
                <div className="store-item" key={item.id}>
                    <img
                        src={item.images[0]}
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
