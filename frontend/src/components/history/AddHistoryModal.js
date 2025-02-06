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

    // 서브이미지 추가 및 수정 처리
    const insertSubImage = (e, index) => {
        const file = e.target.files[0];
        if (file) {
            const newPreview = URL.createObjectURL(file);

            // 미리보기 상태 업데이트
            const updatedPreviews = [...subImagesPreview];
            updatedPreviews[index] = newPreview; // 해당 인덱스에 이미지 추가/수정
            setSubImagesPreview(updatedPreviews);

            // 파일 상태 업데이트
            const updatedFiles = [...subImages];
            updatedFiles[index] = file; // 해당 인덱스에 파일 추가/수정
            setSubImages(updatedFiles);
        }
    };

    // 새 서브이미지 추가
    const addSubImage = (e) => {
        const file = e.target.files[0];
        if (file) {
            setSubImages((prev) => [...prev, file]);
            setSubImagesPreview((prev) => [...prev, URL.createObjectURL(file)]);
        }
    };

    // 폼 제출 처리
    const historySubmit = async (e) => {
        e.preventDefault();

        const data = new FormData();

        // JSON 데이터를 문자열로 변환하여 "data" 키로 추가
        const jsonData = JSON.stringify({
            storeName: formData.storeName,
            storeAddress: formData.storeAddress,
            modelName: formData.modelName,
            historyContent: formData.description,
        });
        data.append("data", new Blob([jsonData], { type: "application/json" }));// 백엔드에서 @RequestPart("data")로 매핑
        // mainImage 추가
        if (mainImage) {
            data.append("mainImage", mainImage); // 백엔드에서 @RequestPart("mainImage")로 매핑
        }
    
        // subImages 추가
        subImages.forEach((file) => data.append("subImages", file)); // 백엔드에서 @RequestPart("subImages")로 매핑
        

        try {
            const response = await axios.post("http://localhost:8989/history/add", data); // headers를 명시하지 않음

            if (response.data.data === true) {
                console.log(response);
                onClose();
                window.location.reload(); //새로고침
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
                            <div className="image-box-container">
                                {mainImagePreview ? (
                                    <div className="image-box-preview">
                                        <img src={mainImagePreview} alt="메인이미지 미리보기" />
                                    </div>
                                ) : (
                                    <div
                                        className="image-box"
                                        onClick={() => document.querySelector(".hidden-input").click()}
                                    >
                                        +
                                    </div>
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
                            <div className="image-box-container">
                                {subImagesPreview.map((image, index) => (
                                    <div
                                        key={index}
                                        className="image-box-preview"
                                        onClick={() =>
                                            document.getElementById(`sub-image-input-${index}`).click()
                                        }
                                    >
                                        <img src={image} alt={`서브이미지 ${index + 1}`} />
                                        <input
                                            type="file"
                                            id={`sub-image-input-${index}`}
                                            className="hidden-input"
                                            accept="image/*"
                                            onChange={(e) => insertSubImage(e, index)}
                                        />
                                    </div>
                                ))}
                                {subImagesPreview.length < 4 && (
                                    <div
                                        className="image-box"
                                        onClick={() => document.getElementById("new-sub-image-input").click()}
                                    >
                                        +
                                    </div>
                                )}
                            </div>
                            {subImagesPreview.length < 4 && (
                                <input
                                    type="file"
                                    id="new-sub-image-input"
                                    className="hidden-input"
                                    accept="image/*"
                                    onChange={addSubImage}
                                />
                            )}
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
