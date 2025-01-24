import React, { useState, useEffect } from "react";
import axios from "axios";
import "@styles/DetailHistoryModal.css";
import UpdateHistoryModal from "@components/history/UpdateHistoryModal";

const DetailHistoryModal = ({ historyNo, onCloseDetail }) => {
    const [item, setItem] = useState(null);
    const [mainImage, setMainImage] = useState("");
    const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);

    useEffect(() => {
        if (historyNo) {
            axios
                .get(`http://localhost:8989/history/${historyNo}`)
                .then((response) => {
                    const { data } = response.data;
                    setItem(data);
                    if (data.images?.length > 0) {
                        setMainImage(data.images[0].historyImageName);
                    }
                })
                .catch((error) => {
                    console.error("Error fetching detail data:", error);
                });
        }
    }, [historyNo]);

    if (!item) {
        return (
            <div className="modal-overlay" onClick={onCloseDetail}>
                <div className="DetailModal-content">
                    <p>Loading...</p>
                </div>
            </div>
        );
    }

    const closeUpdateModal = () => {
        setIsUpdateModalOpen(false);
    };

    return (
        <div className="modal-overlay" onClick={onCloseDetail}>
            {!isUpdateModalOpen && (
                <div
                    className="DetailModal-content"
                    onClick={(e) => e.stopPropagation()}
                >
                    <button className="modal-close" onClick={onCloseDetail}>
                        ×
                    </button>
                    <div className="modal-left">
                        <div className="big-image">
                            <img
                                src={`http://localhost:8989${mainImage}`}
                                alt="Main Preview"
                            />
                        </div>
                        <div className="small-images">
                            {item.images.map((image, index) => (
                                <img
                                    key={index}
                                    src={`http://localhost:8989${image.historyImageName}`}
                                    alt={`Sub Preview ${index + 1}`}
                                    onClick={() => setMainImage(image.historyImageName)}
                                    className={
                                        mainImage === image.historyImageName
                                            ? "thumbnail active"
                                            : "thumbnail"
                                    }
                                />
                            ))}
                        </div>
                    </div>
                    <div className="modal-right">
                        <div className="modal-header">
                            <h2>매장명:{item.storeName}</h2>
                            <p>매장주소:{item.storeAddress}</p>
                            <p>등록일:{item.historyDate}</p>
                        </div>
                        <div className="details">
                            <p>모델명: {item.modelName}</p>
                            <p>내용:{item.historyContent}</p>
                        </div>
                        <div className="modal-footer">
                            <button
                                className="edit-button"
                                onClick={() => setIsUpdateModalOpen(true)}
                            >
                                수정
                            </button>
                            <button className="delete-button">삭제</button>
                        </div>
                    </div>
                </div>
            )}

                {isUpdateModalOpen && (
                <UpdateHistoryModal
                data={item}
                closeUpdateModal={() => {
                    closeUpdateModal(); // 수정 모달 닫기
                    onCloseDetail(); // 상세보기 모달도 닫기
                }}
            />
                )}
            </div>
    );
};

export default DetailHistoryModal;
