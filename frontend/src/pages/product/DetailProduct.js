import "@styles/ProductDetail.css";
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const DetailProduct = () => {
    const { productId } = useParams(); // URL에서 id 가져오기
    const [product, setProduct] = useState(null); // 상품 정보 상태
    const [images, setImages] = useState([]);

    useEffect(() => {
        // 상품 상세 정보 API 호출
        axios.get(`http://localhost:8989/product/${productId}`)
            .then(response => {
                setProduct(response.data.responseData); // 응답 데이터 설정
            })
            .catch(error => {
                console.error('Error fetching product details:', error);
            });
    }, [productId]);

    if (!product) {
        return <div>Loading...</div>; // 로딩 상태
    }

    return (
        <div className="product-detail">
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

