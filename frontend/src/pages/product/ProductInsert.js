import "@styles/ProductInsert.css";
import React, { useState, useEffect } from 'react';

const onChangeTitle = (e) => {
    console.log(e.target.value); // 입력값 출력 (추후 상태 관리 가능)
};

function ProductInsert() {


    const [type, setType] = useState(''); 

    const onClick_kioskButton =() =>{
        setType('kiosk');
    };
    const onClick_cctvButton =() =>{
        setType('cctv');
    };
    const onClick_otherButton =() =>{
        setType('other');
    };


    const [kioskList, setKioskList] = useState(''); 
    const [cctvList, setCctvList] = useState(''); 
    const [otherList, setOtherList] = useState(''); 


    const [title, setTitle] = useState('');
    const [shortDesc, setShortDesc] = useState('');
    const [details, setDetails] = useState('');
    const [kind, setKind] = useState('');
 

    const onChangeKind = (e) =>{
        setKind(e.target.value); 
    }
   

    
    const onChangeTitle = e =>{
        setTitle(e.target.value); //작성된 제목
    }
    const onChangeShortDesc = e =>{
        setShortDesc(e.target.value); //작성된 제목
    }
    const onChangeDetails = e =>{
        setDetails(e.target.value);
    }
    
    const onChangeButton = e =>{
        
        const obj = {
            title : title,
            shortDesc : shortDesc,
            details  :details
        }
        console.log(obj);
        
        
        if(kind === 'kiosk'){
            setKioskList([...kioskList, obj]); //객체랑 키오스크 리스트랑 합쳐서 하나의 배열로 만듦
        } else if(kind === 'cctv'){
            setCctvList([...cctvList, obj]);
        } else{
            setOtherList([...otherList, obj]);
        }
    }

    const [mainImg, setMainImg] = useState('');  // 썸네일 이미지 한장만 저장장
    const [imgList, setImgList] = useState([]);  // 상세 이미지 여러장 저장
    
    const [productList, setProductList] = useState([]); // 작성한 정보를 객체로 만들어서 담을 곳

    // 썸네일 이미지 추가 함수
    const onMainImgSelected = (e) => {
        const reader = new FileReader();
        reader.readAsDataURL(e.target.files[0]);

        reader.onload = () => {
            setMainImg(reader.result); // 썸네일 이미지 저장
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
            ]); // 상세 이미지 리스트에 추가
        };
    };

    
    const [button, setButton] = useState('');

    const [formState, setFormState] = useState({
        title : '',
        mainImg : '',
        shortDesc : '',
        details : ''
    });


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
                                    <option value="kiosk" >키오스크/포스</option>
                                    <option value="cctv" >출입인증기/CCTV/인터넷</option>
                                    <option value="other" >부가상품</option>
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
                                <button onClick={onChangeButton} type="submit" className="square-button-fa">수정하기</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

     
        </>
    );
}

export default ProductInsert;
