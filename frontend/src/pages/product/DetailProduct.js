import "@styles/ProductDetail.css";
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Link, useNaviState } from "react-router-dom";


const DetailProduct = () => {
    const { productId } = useParams(); // URL에서 id 가져오기
    const [product, setProduct] = useState(null); 

    
    
    const [images, setImages] = useState([]);
    
    useEffect(() => {  //렌더링 될때마다 실행
        // 상품 상세 정보 API 호출
        axios.get(`http://localhost:8989/product/${productId}`)
            .then(response => {
                console.log("response:",response);
                setProduct(response.data.data.product); 
                setImages(response.data.data.productImages);
            })
            .catch(error => {
                console.error('Error fetching product details:', error);
            });
    }, [productId]);

    if (!product) {
        return <div>Loading...</div>; // 로딩 상태
    }

    //삭제함수
    const productDelete =  () => {
        if (window.confirm("정말 삭제하시겠습니까?")) {
            axios.delete(`http://localhost:8989/product/admin/${productId}`)
            .then(response =>{
                console.log(response);
                alert("상품 삭제 완료!");
            })
            .catch(error => {
                console.error('Error fetching product details:', error);
            });
        }
    }
    
   //수정폼으로 넘기는 함수
    const productChange = () => {
       
    }


    return (
        <div className="product-detail">
            <div className="buttonsDetail">
                <button type="button" className="change-button">
                    <Link to={`/productEdit/${productId}`} style={{ textDecoration: "none"}}>수정하기</Link>
                </button>
                <button onClick={productDelete} type="button" className="delete-button">삭제하기</button>
            </div>
            <h1>{product.productName}</h1>
            <p>카테고리: {product.productCategory}</p>
            <p>{product.shortDescription}</p>
            <p>{product.detailedDescription}</p>
            
            <div>
                <h2>썸네일</h2>
                <img src={`http://localhost:8989${product.thumbnailImage}`} alt="thumbnail" style={{ width: '300px' }} />
            </div>

            <div>
                <h2>상세 이미지들</h2>
                <div className="product-images"> 
                    {images
                        .slice() // 원본 배열을 복사하여 정렬 (immutable)
                        .sort((a, b) => a.displayOrder - b.displayOrder) // displayOrder 기준 정렬
                        .map((image, index) => (
                            <img
                                key={image.imageId}
                                src={`http://localhost:8989${image.imageUrl}`}
                                alt={`Detail Image ${index + 1}`}
                                style={{ width: '150px', marginRight: '10px' }}
                            />
                        ))}
                </div>

            </div>
        </div>
    );
};

export default DetailProduct;

