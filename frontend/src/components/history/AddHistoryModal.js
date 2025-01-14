import React, { useState } from "react";
import "@styles/AddHistoryModal.css";

const AddHistoryModal = ({ isOpen, onClose }) => {
    const [mainImage, setMainImage] = useState(null);
    const [subImages, setSubImages] = useState([]);

    if (!isOpen) return null; // 모달이 열리지 않으면 렌더링하지 않음

    const previewMainImage = (e) => {
        const file = e.target.files[0];
        if (file) {
            setMainImage(URL.createObjectURL(file)); // 메인이미지 미리보기 URL 생성
        }
    };

    const previewSubImage = (e) => {
        const files = Array.from(e.target.files).slice(0, 4);
        const newImages = files.map((file) => URL.createObjectURL(file));
        setSubImages((prevImages) => [...prevImages, ...newImages].slice(0, 4)); // 최대 4개 유지
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <button className="modal-close" onClick={onClose}>
                    ×
                </button>
                <h3>상품 등록</h3>
                <form>
                    {/* 텍스트 입력 */}
                    <input type="text" placeholder="매장명을 입력하세요" />
                    <input type="text" placeholder="매장주소를 입력하세요" />
                    <input type="text" placeholder="모델명을 입력하세요" />
                    <textarea placeholder="내용을 입력하세요"></textarea>

                    {/* 이미지 업로드 */}
                    <div className="image-upload-section">
                        <div className="image-upload">
                            <label>메인이미지</label>
                            <div
                                className="image-box"
                                onClick={() => document.getElementById("main-image-input").click()}
                            >
                                {mainImage ? (
                                    <img src={mainImage} alt="메인이미지 미리보기" className="image-preview" />
                                ) : (
                                    "+"
                                )}
                            </div>
                            <input
                                type="file"
                                id="main-image-input"
                                className="hidden-input"
                                accept="image/*"    
                                onChange={previewMainImage}
                            />
                        </div>
                                
                        <div className="image-upload">
                            <label>서브이미지</label>
                            <div className="image-container">
                                <div className="sub-images">
                                    {subImages.map((image, index) => (
                                        <div key={index} className="image-preview">
                                            <img src={image} alt={`서브이미지 ${index + 1}`} />
                                        </div>
                                    ))}
                                    <div
                                        className="image-box"
                                        onClick={() => document.getElementById("sub-images-input").click()}
                                    >
                                        +
                                    </div>
                                </div>
                                <input
                                    type="file"
                                    id="sub-images-input"
                                    className="hidden-input"
                                    accept="image/*"
                                    multiple
                                    onChange={previewSubImage}
                                />
                            </div>
                        </div>
                    </div>

                    {/* 버튼 */}
                    <div className="button-box">
                        <button
                            type="button"
                            onClick={onClose}
                            className="cancel-button"
                        >
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
