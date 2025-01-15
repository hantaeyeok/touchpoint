import React, { useState } from "react";
import axios from "axios";
import "@styles/AddHistoryModal.css";

const AddHistoryModal = ({ isOpen, onClose }) => {
    // 텍스트 입력 상태
    const [formData, setFormData] = useState({
        storeName: "",
        storeAddress: "",
        modelName: "",
        description: "",
    });

    // 메인이미지 상태
    const [mainImage, setMainImage] = useState(null);
    const [mainImagePreview, setMainImagePreview] = useState(null);

    // 서브이미지 상태
    const [subImages, setSubImages] = useState([]);
    const [subImagesPreview, setSubImagesPreview] = useState([]);

    // 모달이 열리지 않으면 렌더링하지 않음
    if (!isOpen) return null;

    // 텍스트 입력 처리
    const insertText = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));
    };

    // 메인이미지 처리
    const insertMainImage = (e) => {
        const file = e.target.files[0];
        if (file) {
            setMainImage(file); // 파일 객체 저장
            setMainImagePreview(URL.createObjectURL(file)); // 미리보기 URL 생성
        }
    };

    // 서브이미지 처리
    const insertSubImage = (e) => {
        const files = Array.from(e.target.files);
        const newImages = files.map((file) => URL.createObjectURL(file));
        setSubImagesPreview((prevImages) => {
            // 기존 이미지와 새 이미지를 합치되, 최대 4개로 제한
            return [...prevImages, ...newImages].slice(0, 4);
        });
    };

    // 폼 제출 처리
    const historySubmit = async (e) => {
        e.preventDefault();

        const data = new FormData();
        data.append("storeName", formData.storeName);
        data.append("storeAddress", formData.storeAddress);
        data.append("modelName", formData.modelName);
        data.append("description", formData.description);
        if (mainImage) data.append("mainImage", mainImage);
        subImages.forEach((file) => data.append("subImages", file));

        try {
            const response = await axios.post("http://localhost:8989/history/add", data, {
                headers: { "Content-Type": "multipart/form-data" },
            });

            if (response.status === 200) {
                alert("상품 등록 성공!");
                onClose();
            } else {
                alert("상품 등록 실패!");
            }
        } catch (error) {
            console.error("에러 발생:", error);
            alert("상품 등록 중 오류가 발생했습니다.");
        }
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <button className="modal-close" onClick={onClose}>
                    ×
                </button>
                <h3>상품 등록</h3>
                <form onSubmit={historySubmit}>
                    <input
                        type="text"
                        name="storeName"
                        placeholder="매장명을 입력하세요"
                        value={formData.storeName}
                        onChange={insertText}
                    />
                    <input
                        type="text"
                        name="storeAddress"
                        placeholder="매장주소를 입력하세요"
                        value={formData.storeAddress}
                        onChange={insertText}
                    />
                    <input
                        type="text"
                        name="modelName"
                        placeholder="모델명을 입력하세요"
                        value={formData.modelName}
                        onChange={insertText}
                    />
                    <textarea
                        name="description"
                        placeholder="내용을 입력하세요"
                        value={formData.description}
                        onChange={insertText}
                    ></textarea>

                    <div className="image-upload-section">
                        {/* 메인이미지 */}
                        <div className="image-upload">
                            <label>메인이미지</label>
                            {/* 이미지 미리보기 컨테이너 */}
                            <div
                                className={mainImagePreview ? "image-box-preview" : "image-box"}
                                onClick={() => document.querySelector(".hidden-input").click()}
                            >
                                {mainImagePreview ? (
                                    <img src={mainImagePreview} alt="메인이미지 미리보기" />
                                ) : (
                                    "+" // 업로드되지 않은 경우 "+" 표시
                                )}
                            </div>
                            
                            <input
                                type="file"
                                accept="image/*"
                                className="hidden-input"
                                onChange={insertMainImage}
                            />
                        </div>

                        {/* 서브이미지 */}
                        <div className="image-upload">
                            <label>서브이미지</label>
                            <div className="sub-images-preview">
                                {subImagesPreview.map((image, index) => (
                                    <div key={index} className="image-box-preview">
                                        <img src={image} alt={`서브이미지 ${index + 1}`} />
                                    </div>
                                ))}
                                {/* 추가 이미지 박스는 항상 마지막에 위치 */}
                                {subImagesPreview.length < 4 && (
                                    <div
                                        className="image-box"
                                        onClick={() => document.getElementById("sub-images-input").click()}
                                    >
                                        +
                                    </div>
                                )}
                            </div>
                            <input
                                type="file"
                                id="sub-images-input"
                                className="hidden-input"
                                accept="image/*"
                                multiple
                                onChange={insertSubImage}
                            />
                        </div>
                    </div>

                    {/* 버튼 */}
                    <div className="button-box">
                        <button type="button" onClick={onClose} className="cancel-button">
                            취소하기
                        </button>
                        <button type="submit" className="submit-button">
                            저장하기
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default AddHistoryModal;
