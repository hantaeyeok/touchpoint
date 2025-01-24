/*import { use, useState } from "react";
import "@styles/ProductInsert.css";
import { useParams } from 'react-router-dom';
import axios from 'axios';

const ProductChange = ({ initialData, onSubmit }) =>{

    const { productId } = useParams(); // URL에서 id 가져오기
    console.log("productId:",productId);

    const handleOnSubmit = (product) => {
        console.log("Received product:", product); // 여기서 product를 사용
      };

    const [mainImg, setMainImg] = useState('');  // 썸네일 이미지 한장만 저장
    const [imgList, setImgList] = useState([]);  // 상세 이미지 여러장 저장

    const onChangeKind = () => {

    }

    const[formData, setFormData] = useState(initialData || {});
        //initialData: 초기 데이터를 전달받아 폼을 채울 때 사용 (예: 수정 시 기존 데이터를 폼에 넣음)
        //onSubmit: 폼 데이터를 부모 컴포넌트로 전달하기 위한 콜백 함수

    const handleChange = (e) =>{  //폼의 각 입력 필드에서 값이 변경될 때 호출
        const{title, value} = e.target;
        setFormData({...formData, [title]: value});  //기존의 formData를 복사하고, 변경된 name 필드의 값을 업데이트
    };

    const handleSubmit = (e) => {  //폼 제출 버튼을 클릭했을 때 호출
        e.preventDefault();    //페이지 새로고침x
        onSubmit(formData);    //formData를 부모 컴포넌트로 전달
    };


    //수정함수
    const productChange = () => {
        axios.post(`http://localhost:8989/product/${productId}`)
        .then(response =>{
            console.log(response);
        })
        .catch(error => {
            console.error('Error fetching product details:', error);
        });
    }

    return(
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
                        <div className="title">상품 수정</div>
                        <form onSubmit={handleOnSubmit}>
                            <div className="form-group">
                                <label htmlFor="form-label" className="form-label">카테고리</label>
                                <select name="category" id="category" onChange={onChangeKind} required>
                                    <option value={formData.productCategory || "키오스크/포스"} >키오스크/포스</option>
                                    <option value={formData.productCategory || "출입인증기/CCTV/인터넷"} >출입인증기/CCTV/인터넷</option>
                                    <option value={formData.productCategory || "부가상품"} >부가상품</option>
                                </select>

                                <input
                                    className="input1"
                                    type="text"
                                    name="productTitle"
                                    value={formData.productTitle || ""}
                                    id="pname"
                                    maxLength="100"
                                    placeholder="상품명"
                                    onChange={handleChange}
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
                                    onChange={handleChange}
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
                                    onChange={handleChange}
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
                                            <input onChange={handleChange} name="mainImg" accept="image/*" type="file" />
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
                                            <input onChange={handleChange} accept="image/*" type="file" />
                                        </label>
                                    )
                                }
                                        <label>
                                            +
                                            <input onChange={handleChange} accept="image/*" type="file" />
                                        </label> 
                                </div>
                            </div>

                            <div className="buttons">
                                <button type="button" className="square-button">취소하기</button>
                                <button onClick={handleSubmit} type="button" className="square-button-fa">등록하기</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </>
    );
};
export default ProductChange;*/