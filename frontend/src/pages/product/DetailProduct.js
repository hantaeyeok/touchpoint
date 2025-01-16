import "@styles/ProductDetail.css";
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

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
                setImages(response.data.data.images);
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
            axios.delete(`http://localhost:8989/product/${productId}`)
            .then(response =>{
                console.log(response);
                alert("상품 삭제 완료!");
            })
            .catch(error => {
                console.error('Error fetching product details:', error);
            });
        }
    }
    
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



    return (
        <div className="product-detail">
            <div className="buttonsDetail">
                <button onClick={productChange} type="button" className="change-button">수정하기</button>
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
                    {images.map((image, index) => (
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

