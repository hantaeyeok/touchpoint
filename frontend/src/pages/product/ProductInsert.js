import "@styles/ProductInsert.css";
import React, { useState, useEffect } from 'react';
import axios from "axios";


const onChangeTitle = (e) => {
    console.log(e.target.value); // 입력값 출력 (추후 상태 관리 가능)
};

function ProductInsert() {

    const [type, setType] = useState(''); 

    const [kioskList, setKioskList] = useState(''); 
    const [cctvList, setCctvList] = useState(''); 
    const [otherList, setOtherList] = useState(''); 

    const [mainImg, setMainImg] = useState('');  // 썸네일 이미지 한장만 저장
    const [imgList, setImgList] = useState([]);  // 상세 이미지 여러장 저장

    const [title, setTitle] = useState('');
    const [shortDesc, setShortDesc] = useState('');
    const [details, setDetails] = useState('');
    const [kind, setKind] = useState('키오스크/포스');
    
    const onChangeTitle = e =>{
        setTitle(e.target.value); //작성된 제목
    }
    const onChangeShortDesc = e =>{
        setShortDesc(e.target.value); //작성된 제목
    }
    const onChangeDetails = e =>{
        setDetails(e.target.value);
    }
    const onChangeKind = (e) =>{
        setKind(e.target.value); 
        console.log(e.target.value);
    }
   
    
    const [obj, setObj] = useState({
        productName: '',
        productCategory: '',
        shortDescription: '',
        detailedDescription: '',
        thumbnailImage: '',

    });

    
    const onChangeButton = e => {
        const updatedObj = {
            productName: title,
            productCategory: kind,
            shortDescription: shortDesc,
            detailedDescription: details,
            thumbnailImage: mainImg.name, // 파일 이름 설정
            //imageUrl : imgList.map(img => img.originFile.name) 
        };
    
        setObj(updatedObj);
    
        const updatedList =
            kind === 'kiosk'
                ? [...kioskList, updatedObj]
                : kind === 'cctv'
                ? [...cctvList, updatedObj]
                : [...otherList, updatedObj];
    
        kind === 'kiosk'
            ? setKioskList(updatedList)
            : kind === 'cctv'
            ? setCctvList(updatedList)
            : setOtherList(updatedList);
    
        post(updatedObj, mainImg, imgList); // 파일 객체를 전달
    };
    
    const post = (data, file, imageFiles) => {
        const formData = new FormData();
        formData.append('product', JSON.stringify(data)); // JSON 데이터를 추가
        formData.append('upfile', file); // 파일 추가 (업로드할 파일 객체 전달)
        //formData.append('images', file);

        // 상세 이미지 파일을 반복문으로 추가
        imageFiles.forEach((imageFile) => {
            if (imageFile.originFile) {
                formData.append('images', imageFile.originFile); // 각 파일을 'images' 키로 추가
            }
        });

        axios
            .post('http://localhost:8989/product/save', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            })
            .then(response => {
                console.log('Response:', response.data);
            })
            .catch(error => {
                if (error.response) {
                    console.error('Server Error:', error.response.data);
                } else {
                    console.error('Error:', error.message);
                }
            });
    };
    
    // 썸네일 이미지 추가 함수
    const onMainImgSelected = (e) => {
        const file = e.target.files[0]; // 선택한 파일
        
    
        if (!file) return; // 파일이 없는 경우 종료
    
        setMainImg(file); // 파일 객체로 저장
    
        // 이미지를 미리보기용으로 읽어서 출력
        const reader = new FileReader();
        reader.readAsDataURL(file);
    
        reader.onload = () => {
            // 미리보기 이미지 설정
            console.log(reader.result); // 이미지 데이터 URI 출력
        };
    
        reader.onerror = (error) => {
            console.error("File reading error:", error);
        };
    };
    

    // 상세 이미지 추가 함수
    const onDetailImgSelected = (e) => {
        const now = new Date();
        const id = now.toString(); // 고유 ID 생성
        const reader = new FileReader();
        reader.readAsDataURL(e.target.files[0]);

        reader.onload = () => {
            setImgList((prev) => [
                ...prev,
                { id, previewUrl: reader.result, originFile: e.target.files[0] },
            ]); // 상세 이미지 리스트에 추가(url이랑 오리진 파일이랑 뭔차이)
        };
    };

    
    const [button, setButton] = useState('');


    const onChangeHandler = e =>{
        console.log(e.target.name);
        console.log(e.target.value);
    }

    
    
    

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
                        <form>
                            <div className="form-group">
                                <label htmlFor="form-label" className="form-label">카테고리</label>
                                <select name="category" id="category" onChange={onChangeKind} required>
                                    <option value="키오스크/포스" >키오스크/포스</option>
                                    <option value="출입인증기/CCTV/인터넷" >출입인증기/CCTV/인터넷</option>
                                    <option value="부가상품" >부가상품</option>
                                </select>

                                <input
                                    className="input1"
                                    type="text"
                                    name="title"
                                    id="pname"
                                    maxLength="100"
                                    placeholder="상품명"
                                    onChange={onChangeTitle}
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="shortDesc" className="form-label">한줄 설명</label>
                                <input
                                    type="text"
                                    name="shortDesc"
                                    id="shortDesc"
                                    maxLength="150"
                                    placeholder="상품 한줄 설명"
                                    onChange={onChangeShortDesc}
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="details" className="form-label">상세 설명</label>
                                <textarea
                                    name="details"
                                    id="details"
                                    rows="5"
                                    maxLength="500"
                                    placeholder="상품 상세 설명"
                                    onChange={onChangeDetails}
                                ></textarea>
                            </div>

                            <div className="form-group">
                                <label htmlFor="thumbnail" className="form-label">상품 썸네일 이미지</label>
                                
                                <div className="ImgInputWrap">
                                <label>
                                    {mainImg === '' ? '+' : <img
                                        style={{
                                        width: '100%',
                                        height: '100%',
                                        objectFit: 'cover',
                                        objectPosition: 'center'
                                            }}
                                        src={mainImg} />}
                                            <input onChange={onMainImgSelected} name="mainImg" accept="image/*" type="file" />
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
                                <button onClick={onChangeButton} type="button" className="square-button-fa">수정하기</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

     
        </>
    );
}

export default ProductInsert;
