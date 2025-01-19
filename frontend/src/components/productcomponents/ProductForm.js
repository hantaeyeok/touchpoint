
import React, { useEffect, useState } from 'react';

const ProductForm = ({ images, initialData, onSubmit }) => {
    const [productData, setProductData] = useState(initialData || {
        productName: '',
        productCategory: '키오스크/포스',  // 기본값 설정
        shortDescription: '',
        detailedDescription: '',
      });
      
    const [mainImg, setMainImg] = useState(''); 
    const [imgList, setImgList] = useState([]); 

      
    useEffect(() => {  //images가 변경될때마다 실행
        if (initialData) {     //initialData(초기데이터)가 있다면
            setProductData((prevState) => ({    //productData 상태를 업데이트
              ...prevState,                   //기존 상태를 유지
              thumbnailImage: initialData.thumbnailImage || '',  //thumbnailImage를 initialData의 썸네일으로 설정
            }));
          }

        if (images && images.length > 0) {   //images가 유효한지 검사후 
          const formattedImages = images.map((img) => ({    // images 를 새로운 배열로 변환
            id: img.imageId,
            previewUrl: `http://localhost:8989${img.imageUrl}`,  //서버에서 제공된 imageUrl에 http://localhost:8989을 앞에 붙여서 이미지의 전체 URL을 생성
            originFile: null, // 서버에서 가져온 이미지는 파일이 아니므로 null
          }));
          setImgList(formattedImages);      //formattedImages로 변환된 배열을 imgList에 저장
        }
      }, [images]);  //images가 변경될때마다 실행하셈


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
            thumbnailImage: file, // 파일 객체로 저장
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
    

    const onDetailImgSelected = (e) => {
        const now = new Date();
        const id = now.toString(); // 고유 ID 생성
        const reader = new FileReader();
        reader.readAsDataURL(e.target.files[0]);

        reader.onload = () => {
            setImgList((prev) => [
                ...prev,
                { id, previewUrl: reader.result, originFile: e.target.files[0] },
            ]); 
        };
    };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevState) => ({ ...prevState, [name]: value }));
  };

  const handleImageChange = (e) => {
    setProductData((prevState) => ({
      ...prevState,
      thumbnailImage: e.target.files[0],
    }));
  };

  const handleSubmit = (e) => {
    console.log('Submitting data:', productData);
    console.log('Main image:', mainImg);
    e.preventDefault();
    onSubmit(productData, mainImg, imgList); 
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
                                        <label key={img.id}>
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
                                            <input onChange={onDetailImgSelected} accept="image/*" type="file" />
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
