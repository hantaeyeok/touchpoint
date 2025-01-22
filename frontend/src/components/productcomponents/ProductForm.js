
import React, { useEffect, useState } from 'react';
import "@styles/ProductInsert.css";

const ProductForm = ({ images, initialData, onSubmit }) => {
    const [productData, setProductData] = useState(initialData || {
        productName: '',
        productCategory: '키오스크/포스',  // 기본값 설정
        shortDescription: '',
        detailedDescription: '',
        //thumbnailImage:'',
        //productImages:'',

      });
      
    const [mainImg, setMainImg] = useState(''); 
    const [imgList, setImgList] = useState([]); 
 
    
    
    const convertUrlToFile = async (url, fileName) => {
        const response = await fetch(url);
        const blob = await response.blob();
        const file = new File([blob], fileName, { type: blob.type });
        return file;
      }; 

    useEffect(() => { //화면에 원래정보 넣어줌
        if (initialData) {                  //initialData에 데이터가 있다면
            setProductData((prevState) => ({
                ...prevState,                   //prevState기존상태를 유지하면서 썸네일이미지 설정정
                thumbnailImage: initialData.thumbnailImage || '',
            }));
      
            if (initialData.thumbnailImage) {    //썸네일 이미지 있으면 실행
                convertUrlToFile(`http://localhost:8989${initialData.thumbnailImage}`, 'thumbnailImage.jpg')  //thumbnailImage.jpg이름의 파일객체로 만듦듦
                .then(file => {
                    setMainImg(file);  // file을 mainImg 상태에 저장
                })
                .catch((error) => {
                    console.error('Error converting URL to file:', error);
                });
            }
      
            // imgList 처리
            if (initialData.productImages && initialData.productImages.length > 0) {  //productImages가 있을경우
                const formattedImages = initialData.productImages.map((img, index) => {  //initialData.productImages 배열을 map 메서드를 사용하여 새로운 형식의 formattedImages 배열로 변환
                return {
                    id: img.imageId,
                    displayOrder:index,
                    previewUrl: `http://localhost:8989${img.imageUrl}`, // 서버에서 제공된 imageUrl
                    originFile: null, // 원래 파일 객체는 null로 설정
                };
                });
                setImgList(formattedImages); // 이미지 목록 설정
            }
        }
    }, [initialData]);

    // 썸네일 이미지 추가 함수
    const onMainImgSelected = (e) => {
        const file = e.target.files[0]; // 선택한 파일
        
        if (!file) {
            console.error('파일이 선택되지 않았습니다.');
            return;
        }
    
        setMainImg(file); // 파일 객체로 저장
        setProductData((prevState) => ({
            ...prevState,
            //thumbnailImage: file, // 파일 객체로 저장
        }));
    
        const reader = new FileReader();
        reader.readAsDataURL(file);
    
        reader.onload = () => {
            console.log('Thumbnail preview generated:', reader.result);
        };
    
        reader.onerror = (error) => {
            console.error("File reading error:", error);
        };
    };
    
    //새로운 이미지 추가될때 기존의 이미지를 지우고 선택한 이미지를 배열에 추가
    const onDetailImgSelected = (e) => {
        const now = new Date();
        const id = now.toString(); // 고유 ID 생성
        const file = e.target.files[0];
        if (!file) return;
    
        const reader = new FileReader();
        reader.readAsDataURL(file);
    
        reader.onload = () => {
            setImgList((prev) => {
                const updatedList = [
                    ...prev,
                    { id, displayOrder: prev.length, previewUrl: reader.result, originFile: file },
                ];
                return updatedList.map((img, index) => ({ ...img, displayOrder: index })); // 순서 재설정
            });
        };
    };
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevState) => ({ ...prevState, [name]: value }));
  };

  const onImgChanged = (id, e) => {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = () => {
        setImgList((prev) =>
            prev
                .map((img) =>
                    img.id === id
                        ? { ...img, previewUrl: reader.result, originFile: file }
                        : img
                )
                .map((img, index) => ({ ...img, displayOrder: index })) // 순서 재설정
        );
    };
    reader.readAsDataURL(file); // 파일 읽기 시작
};

const [deleteImg, setDeleteImg] = useState([]); //삭제할 이미지들
console.log("deleteImg",deleteImg);

const onImgDelete = (id) => {
    setImgList((prev) =>
        prev
            .filter((e) => e.id !== id) // 삭제 대상 제거
            .map((img, index) => ({ ...img, displayOrder: index })) // 순서 재설정
            
    );
    setDeleteImg([...deleteImg, id]);  //원래 기존의 삭제할 이미지들에다가 새로운 id를 추가해준다.
};



const handleSubmit = (e) => {
    e.preventDefault();

    const formattedImgList = [...imgList]
        .sort((a, b) => a.displayOrder - b.displayOrder) // displayOrder 기준으로 정렬
        .map((img, index) => ({ ...img, displayOrder: index })); // 재정렬 후 displayOrder 업데이트

    console.log('Submitting data:', productData);
    console.log('Main image:', mainImg);
    console.log('formattedImgList:', formattedImgList);

    onSubmit(productData, mainImg, formattedImgList, deleteImg  ); // 정렬된 데이터 전송
};
  return (
    <>
            <div className="insertPage">
                <div className="sidebar">
                    <div id="sidebar">
                        <div className="sidebar-menu"><a href="">상품 등록</a></div>
                        <div className="sidebar-menu"><a href="">상품 수정</a></div>
                        <div className="sidebar-menu"><a href="">상품 삭제</a></div>
                        <div className="sidebar-menu"><a href="">대여 관리</a></div>
                    </div>
                </div>

                <div className="insertGride">
                    <div className="insertDiv">
                        <div className="title">상품 등록</div>
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="form-label" className="form-label">카테고리</label>
                                <select name="productCategory" value={productData.productCategory} onChange={handleInputChange} id="category" required>
                                    <option value="키오스크/포스" >키오스크/포스</option>
                                    <option value="출입인증기/CCTV/인터넷" >출입인증기/CCTV/인터넷</option>
                                    <option value="부가상품" >부가상품</option>
                                </select>

                                <input
                                    className="input1"
                                    type="text"
                                    name="productName"
                                    id="pname"
                                    maxLength="100"
                                    placeholder="상품명"
                                    value={productData.productName}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="shortDesc" className="form-label">한줄 설명</label>
                                <input
                                    type="text"
                                    id="shortDesc"
                                    maxLength="150"
                                    placeholder="상품 한줄 설명"
                                    name="shortDescription" 
                                    value={productData.shortDescription}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="details" className="form-label">상세 설명</label>
                                <textarea
                                    id="details"
                                    rows="5"
                                    maxLength="500"
                                    placeholder="상품 상세 설명"
                                    name="detailedDescription" 
                                    value={productData.detailedDescription}
                                    onChange={handleInputChange}
                                ></textarea>
                            </div>

                            <div className="form-group">
                                <label htmlFor="thumbnail" className="form-label">상품 썸네일 이미지</label>
                                
                                <div className="ImgInputWrap">
                                <label>
                                        {productData.thumbnailImage === '' ? '+' : (
                                            <img
                                            style={{
                                                width: '100%',
                                                height: '100%',
                                                objectFit: 'cover',
                                                objectPosition: 'center',
                                            }}
                                            src={
                                                typeof productData.thumbnailImage === 'string'
                                                  ? `http://localhost:8989${productData.thumbnailImage}`
                                                  : productData.thumbnailImage instanceof File
                                                  ? URL.createObjectURL(productData.thumbnailImage)
                                                  : ''
                                              }
                                            alt="Thumbnail Preview"
                                            />
                                        )}
                                        <input
                                            type="file"
                                            name="thumbnailImage"
                                            onChange={onMainImgSelected}
                                            accept="image/*"
                                        />
                                        </label>
                                </div>
                            </div>

                            <div className="form-group">
                                <label htmlFor="detailImage" className="form-label">상품 상세 이미지</label>
                                <div className="ImgInputWrap">
                                {
                                    imgList.map((img) =>
                                        
                                        <label key={img.id}>{/* 이건 원래 들어있던 이미지의 id*/}
                                        
                                            <img
                                                src={img.previewUrl}
                                                alt="Detail"
                                                style={{
                                                    width: '100%',
                                                    height: '100%',
                                                    objectFit: 'cover',
                                                    objectPosition: 'center'
                                                }}
                                            />

                                        <button
                                            style={{  top: '0', right: '0' }}
                                            onClick={() => { onImgDelete(img.id) }}
                                            type='button'>삭제하기
                                        </button>
                                            <input onChange={(e) => onImgChanged(img.id, e)}  accept="image/*" type="file" />
                                        </label>
                                    )
                                }
                                        <label>
                                            +
                                            <input onChange={onDetailImgSelected} accept="image/*" type="file" />
                                        </label> 
                                </div>
                            </div>

                            <div className="buttons">
                                <button type="button" className="square-button">취소하기</button>
                                <button type="submit" className="square-button-fa">등록하기</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
    </>
  );
  
};

export default ProductForm;