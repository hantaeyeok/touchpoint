import React, { useState,useEffect } from "react";
import axios from "axios";
import "@styles/UpdateHistoryModal.css";

const UpdateHistoryModal = ({ data, closeUpdateModal }) => {
    useEffect(() => {
        console.log("UpdateHistoryModal 시작됨");
        console.log("받아온 데이터:", data);
        console.log("데이터에있는 historyNo",data.setupHistory?.historyNo)
        
    }, [data]);
    // 입력 필드 상태
    const [formData, setFormData] = useState({
        storeName: data.setupHistory.storeName || "",
        storeAddress: data.setupHistory.storeAddress || "",
        modelName: data.setupHistory.modelName || "",
        description: data.setupHistory.historyContent || "",
    });

    // 메인이미지 상태
    const [mainImage, setMainImage] = useState(data.images?.[0] || null);
    const [mainImagePreview, setMainImagePreview] = useState(
        mainImage ? `http://localhost:8989${mainImage.historyImageName}` : null
    );

    // 서브이미지 상태
    const [subImages, setSubImages] = useState(data.images?.slice(1) || []);
    const [subImagesPreview, setSubImagesPreview] = useState(
        data.images?.slice(1).map((img) => `http://localhost:8989${img.historyImageName}`) || []
    );

    // 관리되는 추가/수정/삭제 이미지 상태
    const [addedImages, setAddedImages] = useState([]);
    const [updatedImages, setUpdatedImages] = useState([]);
    const [deletedImages, setDeletedImages] = useState([]);
    
    // 입력 텍스트 업데이트
    const updateInput = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    // 메인이미지 변경
    const updateMainImage = (e) => {
        const file = e.target.files[0];
        if (file) {
            setMainImage({ file, imageOrder: 0 });
            setMainImagePreview(URL.createObjectURL(file));

            // 수정된 이미지에 추가
            setUpdatedImages((prev) => [
                ...prev.filter((img) => img.imageOrder !== 0),
                { file, imageOrder: 0 },
            ]);
        }
    };

    // 서브이미지 변경
    const updateSubImage = (e, index) => {
        const file = e.target.files[0];
        if (file) {
            const updatedPreviews = [...subImagesPreview];
            updatedPreviews[index] = URL.createObjectURL(file);
            setSubImagesPreview(updatedPreviews);

            const updatedFiles = [...subImages];
            updatedFiles[index] = { file, imageOrder: index + 1 };
            setSubImages(updatedFiles);

            // 수정된 이미지에 추가
            setUpdatedImages((prev) => [
                ...prev.filter((img) => img.imageOrder !== index + 1),
                { file, imageOrder: index + 1 },
            ]);
        }
    };

    // 새 서브이미지 추가
    const addSubImage = (e) => {
        const file = e.target.files[0];
        if (file) {
            const newOrder = subImages.length + 1;
            setSubImages((prev) => [...prev, { file, imageOrder: newOrder }]);
            setSubImagesPreview((prev) => [...prev, URL.createObjectURL(file)]);

            // 추가된 이미지에 기록
            setAddedImages((prev) => [...prev, { file, imageOrder: newOrder }]);
        }
    };

    // 서브이미지 삭제
    const deleteSubImage = (index) => {
        const deletedImage = subImages[index];

        // 삭제된 이미지 기록
        if (deletedImage && !deletedImage.file) {
            setDeletedImages((prev) => [...prev, deletedImage.historyImageName]);
        }

        // 서브이미지 리스트 업데이트
        const updatedSubImages = subImages.filter((_, i) => i !== index);
        setSubImages(updatedSubImages);

        // 서브이미지 미리보기 업데이트
        const updatedPreviews = subImagesPreview.filter((_, i) => i !== index);
        setSubImagesPreview(updatedPreviews);

        // 추가/수정된 이미지에서도 제거
        setAddedImages((prev) => prev.filter((img) => img.imageOrder !== index + 1));
        setUpdatedImages((prev) => prev.filter((img) => img.imageOrder !== index + 1));
    };

    const submitUpdate = async (e) => {
        e.preventDefault();
    
        const form = new FormData();
    
        // 🔹 JSON 데이터 (게시글 정보)
        const jsonData = JSON.stringify({
            historyNo: data.setupHistory?.historyNo,  
            storeName: formData.storeName,
            storeAddress: formData.storeAddress,
            modelName: formData.modelName,
            historyContent: formData.description,
            
        });
        form.append("data", new Blob([jsonData], { type: "application/json" }));
    
        // 수정된 이미지 정보 (파일명 + 오더 매칭)
        const updatedImageInfo = updatedImages.map((img) => ({
            imageOrder: img.imageOrder, // 기존 이미지 순서
            fileName: img.file.name // 파일 이름 (파일 저장 후 매칭)
        }));
    
        updatedImages.forEach((img) => {
            form.append("updatedImages", img.file);  // ✅ 개별 이미지 파일 추가
        });
    
        // 이미지 순서 + 파일명 JSON 추가
        form.append(
            "updatedImageInfo",
            new Blob([JSON.stringify(updatedImageInfo)], { type: "application/json" })
        );
        
         //  추가된 이미지 정보 (파일명 + 오더 매칭)
        const addedImageInfo = addedImages.map((img) => ({
            imageOrder: img.imageOrder,  // 추가된 이미지 순서
            fileName: img.file.name // 파일 이름 (파일 저장 후 매칭) 
        }));

        // 추가된 이미지 파일 추가
        addedImages.forEach((img) => {
            form.append("addedImages", img.file);  // ✅ 개별 이미지 파일 추가
        });

        // 추가된 이미지 순서 + 파일명 JSON 추가
        form.append(
            "addedImageInfo",
            new Blob([JSON.stringify(addedImageInfo)], { type: "application/json" })
        );
        
    
        try {
            const response = await axios.put(
                `http://localhost:8989/history/update`,
                form
            );
    
            if (response.data.data == true) {
                alert("수정이 완료되었습니다!");
                closeUpdateModal();
                window.location.reload();//새로고침
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
                <form onSubmit={submitUpdate}>
                    <input
                        type="text"
                        name="storeName"
                        placeholder="매장명을 입력하세요"
                        value={formData.storeName}
                        onChange={updateInput}
                    />
                    <input
                        type="text"
                        name="storeAddress"
                        placeholder="매장주소를 입력하세요"
                        value={formData.storeAddress}
                        onChange={updateInput}
                    />
                    <input
                        type="text"
                        name="modelName"
                        placeholder="모델명을 입력하세요"
                        value={formData.modelName}
                        onChange={updateInput}
                    />
                    <textarea
                        name="description"
                        placeholder="내용을 입력하세요"
                        value={formData.description}
                        onChange={updateInput}
                    ></textarea>

                    <div className="image-upload-section">
                        {/* 메인이미지 */}
                        <div className="image-upload">
                            <label>메인이미지</label>
                            <div className="image-box-container">
                                {mainImagePreview ? (
                                    <div
                                        className="image-box-preview"
                                        onClick={() => document.getElementById("main-image-input").click()}
                                    >
                                        <img src={mainImagePreview} alt="메인이미지 미리보기" />
                                    </div>
                                ) : (
                                    <div
                                        className="image-box"
                                        onClick={() => document.getElementById("main-image-input").click()}
                                    >
                                        +
                                    </div>
                                )}
                            </div>
                            <input
                                type="file"
                                id="main-image-input"
                                className="hidden-input"
                                accept="image/*"
                                onChange={updateMainImage}
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
                                        onClick={() => document.getElementById(`sub-image-input-${index}`).click()}
                                    >
                                        <img src={image} alt={`서브이미지 ${index + 1}`} />
                                        <input
                                            type="file"
                                            id={`sub-image-input-${index}`}
                                            className="hidden-input"
                                            accept="image/*"
                                            onChange={(e) => updateSubImage(e, index)}
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
                        <button type="button" onClick={closeUpdateModal} className="cancel-button">
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
