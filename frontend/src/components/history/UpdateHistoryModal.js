import React, { useState } from "react";
import axios from "axios";
import "@styles/UpdateHistoryModal.css";

const UpdateHistoryModal = ({ data, closeUpdateModal }) => {
    // 초기 데이터 세팅
    const [formData, setFormData] = useState({
        storeName: data.storeName || "",
        storeAddress: data.storeAddress || "",
        modelName: data.modelName || "",
        content: data.historyContent || "",
    });

    const [newMainImage, setNewMainImage] = useState(null); // 새로 선택된 메인 이미지
    const [mainImagePreview, setMainImagePreview] = useState(
        data.images?.[0]?.historyImageName ? `http://localhost:8989${data.images[0].historyImageName}` : null
    );

    const [newSubImages, setNewSubImages] = useState([]); // 새로 선택된 서브 이미지들
    const [subImagesPreview, setSubImagesPreview] = useState(
        data.images?.slice(1).map((img) => `http://localhost:8989${img.historyImageName}`) || []
    );

    const [deletedSubImages, setDeletedSubImages] = useState([]); // 삭제된 서브 이미지 기록

    // 텍스트 입력 처리
    const ChangeFormData = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    // 새로 선택된 메인 이미지 처리
    const mainImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setNewMainImage(file);
            setMainImagePreview(URL.createObjectURL(file));
        }
    };

    // 새로 선택된 서브 이미지 처리
    const subImageChange = (e, index) => {
        const file = e.target.files[0];
        if (file) {
            const newPreview = URL.createObjectURL(file);

            const updatedPreviews = [...subImagesPreview];
            updatedPreviews[index] = newPreview;
            setSubImagesPreview(updatedPreviews);

            const updatedFiles = [...newSubImages];
            updatedFiles[index] = file;
            setNewSubImages(updatedFiles);
        }
    };

    // 서브 이미지 추가
    const addSubImage = (e) => {
        const file = e.target.files[0];
        if (file) {
            setNewSubImages((prev) => [...prev, file]);
            setSubImagesPreview((prev) => [...prev, URL.createObjectURL(file)]);
        }
    };

    // 서브 이미지 삭제
    const deleteSubImage = (index) => {
        const deletedImage = subImagesPreview[index]; // 삭제할 이미지 가져오기

        // 이미 기존 이미지일 경우 삭제 목록에 추가
        if (!newSubImages[index]) {
            setDeletedSubImages((prev) => [...prev, deletedImage]);
        }

        // 미리보기와 새로운 파일 리스트에서 제거
        const updatedPreviews = [...subImagesPreview];
        updatedPreviews.splice(index, 1);
        setSubImagesPreview(updatedPreviews);

        const updatedFiles = [...newSubImages];
        updatedFiles.splice(index, 1);
        setNewSubImages(updatedFiles);
    };

    // 수정 데이터 제출
    const updatesubmit = async (e) => {
        e.preventDefault();

        const data = new FormData();

        const jsonData = JSON.stringify({
            storeName: formData.storeName,
            storeAddress: formData.storeAddress,
            modelName: formData.modelName,
            historyContent: formData.content,
            deletedSubImages, // 삭제된 서브 이미지 경로를 서버로 전송
        });
        data.append("data", new Blob([jsonData], { type: "application/json" }));

        if (newMainImage) {
            data.append("mainImage", newMainImage); // 새로 선택된 메인 이미지만 전송
        }

        newSubImages.forEach((file) => data.append("subImages", file)); // 새로 선택된 서브 이미지만 전송

        try {
            const response = await axios.put(
                `http://localhost:8989/history/update/${data.historyNo}`,
                data
            );

            if (response.data.success) {
                alert("수정이 완료되었습니다!");
                closeUpdateModal(); // 수정 완료 후 모달 닫기
            } else {
                alert("수정 실패!");
            }
        } catch (error) {
            console.error("에러 발생:", error);
            alert("수정 중 오류가 발생했습니다.");
        }
    };

    return (
        <div className="modal-overlay" onClick={closeUpdateModal}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <button className="modal-close" onClick={closeUpdateModal}>
                    ×
                </button>
                <h3>상품 수정</h3>
                <form onSubmit={updatesubmit}>
                    <input
                        type="text"
                        name="storeName"
                        placeholder="매장명을 입력하세요"
                        value={formData.storeName}
                        onChange={ChangeFormData}
                    />
                    <input
                        type="text"
                        name="storeAddress"
                        placeholder="매장주소를 입력하세요"
                        value={formData.storeAddress}
                        onChange={ChangeFormData}
                    />
                    <input
                        type="text"
                        name="modelName"
                        placeholder="모델명을 입력하세요"
                        value={formData.modelName}
                        onChange={ChangeFormData}
                    />
                    <textarea
                        name="content"
                        placeholder="내용을 입력하세요"
                        value={formData.content}
                        onChange={ChangeFormData}
                    ></textarea>

                    <div className="image-upload-section">
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
                                onChange={mainImageChange}
                            />
                        </div>

                        <div className="image-upload">
                            <label>서브이미지</label>
                            <div className="image-box-container">
                                {subImagesPreview.map((image, index) => (
                                    <div key={index} className="image-box-wrapper">
                                        <div
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
                                                onChange={(e) => subImageChange(e, index)}
                                            />
                                        </div>
                                        <button
                                            type="button"
                                            className="imageDelete-button"
                                            onClick={() => deleteSubImage(index)}
                                        >
                                            삭제
                                        </button>
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

                    <div className="button-box">
                        <button
                            type="button"
                            onClick={closeUpdateModal}
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

export default UpdateHistoryModal;
