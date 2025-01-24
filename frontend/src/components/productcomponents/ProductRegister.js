import React from 'react';
import axios from 'axios';
import ProductForm from '@components/productcomponents/ProductForm';

const ProductRegister = () => {
    const handleRegister = (productData ,mainImg, imgList) => {

        const formData = new FormData();
        const thumbnailImagePath = productData.thumbnailImage ? productData.thumbnailImage.name : ''; // 파일명이 필요하면 name을 사용


        formData.append('product', JSON.stringify({
            ...productData,
            thumbnailImage: thumbnailImagePath, // 경로로 처리
          }));
          
          formData.append('upfile', mainImg); // 이미지 파일 자체를 전송
      
        imgList.forEach((imageFile) => {
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
            alert('상품이 등록되었습니다!');
        })
        .catch(error => {
            if (error.response) {
                console.error('Server Error:', error.response.data);
            } else {
                console.error('Error:', error.message);
            }
        });
    };

    return (
        <div>
            <h2>상품 등록</h2>
            <ProductForm initialData={null} onSubmit={handleRegister} />
        </div>
    );
};

export default ProductRegister;
